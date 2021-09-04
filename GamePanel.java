import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.*;

public class GamePanel extends JPanel {
	private int deathAnimationTimer, deathCounter, levelNumber, 
		timeCounter, room, CRtimeCounter;
	private Level currentLevel;
	private Player player;
	private java.util.Timer timer;
	final static int tileSize = 30;
	private boolean playerCanMove, playerIsDead, wasOnIce, exitCollision;
	private GameWindow gameWindow;
	private JButton back;
	private boolean shiftLorR; //L is false, R is true
	private MovingPlatform[] movingPlatforms;
	
	public void setExitCollision (boolean ec) {
		exitCollision = ec;
	}
	
	public boolean getPlayerCanMove () {
		return playerCanMove;
	}
	
	public void setPlayerCanMove (boolean playerCanMove) {
		this.playerCanMove = playerCanMove;
	}
	
	public Player getPlayer () {
		return player;
	}
	
	public void killPlayer () {
		playerIsDead = true;
	}
	
	public GamePanel (Level level, GameWindow gameWindow, int levelNumber, MovingPlatform[] movingPlatforms) {
		this.levelNumber = levelNumber;
		deathCounter = 0;
		this.gameWindow = gameWindow;
		currentLevel = level;
		player = new Player(currentLevel.getSpawn().get(0), this);
		timer = new java.util.Timer();
		timer.scheduleAtFixedRate(new ResolveCollision(), 80, 40);
		playerIsDead = false;
		playerCanMove = false;
		deathAnimationTimer = 0;
		wasOnIce = false;
		this.setPreferredSize(new Dimension(22*GamePanel.tileSize, 15*GamePanel.tileSize));
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		back = new JButton("Back");
		back.setAlignmentX(LEFT_ALIGNMENT);
		back.setFocusable(false);
		back.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if (CRtimeCounter == 0) {
					shiftGOtoOriginal();
					timer.cancel();
					timer.purge();
					gameWindow.makeScreen(GameWindow.LEVEL_MENU);
				}
			}
		});
		this.add(back);
		this.timeCounter = 40*180;
		shiftLorR = true;
		CRtimeCounter = 0;
		room = 0;
		this.movingPlatforms = movingPlatforms;
		exitCollision = false;
	}
	
	public class ResolveCollision extends TimerTask {
		public void run () {
			if (player.getScore() == currentLevel.getCollectibleQuota()) {
				if (deathCounter < gameWindow.getLeastDeaths()[levelNumber-1])
					gameWindow.getLeastDeaths()[levelNumber-1] = deathCounter;
				gameWindow.getLevelCompletion()[levelNumber-1] = true;
				playerCanMove = false;
				gameWindow.makeScreen(GameWindow.LEVEL_MENU);
				shiftGOtoOriginal();
				timer.cancel();
				timer.purge();
			} else if (timeCounter <= 0 && CRtimeCounter == 0) {
				playerCanMove = false;
				gameWindow.makeScreen(GameWindow.LEVEL_MENU);
				shiftGOtoOriginal();
				timer.cancel();
				timer.purge();
			}
			
			if (playerCanMove && CRtimeCounter == 0) {
				boolean canMoveH = true, canMoveV = true;
				
				if (movingPlatforms != null)
					for (MovingPlatform mp : movingPlatforms) {
						boolean[] canMove = mp.collision(player);
						if (canMove == null)
							continue;
						if (!canMove[0])
							canMoveH = false;
						if (!canMove[1])
							canMoveV = false;
					}
				
				for (GameObject object : currentLevel.getObjectArray())
					object.collision(player);
				
				if (exitCollision) {
					exitCollision = false;
					return;
				}
				
				int[][] collision = determineCollision(player.proposeUpdate());
				
				if (collision == null) {
					player.setPlayerPos(currentLevel.getSpawn().get(room).add(new PVector(-22*tileSize*room, 0)));
					return;
				}
				
				if (collision[1][0] == 2 && collision[1][1] == 2){
					player.setOnIce(true);
					wasOnIce = true;
				} else {
					if ((collision[1][0] == 1 || collision[1][1] == 1) && wasOnIce) {
						player.getPlayerVel().setxComp(0);
						if (collision[0][0] == 1)
							player.setPlayerPos(PVector.add(player.getPlayerPos(), new PVector(-5, 0)));
						else if (collision[0][1] == 1)
							player.setPlayerPos(PVector.add(player.getPlayerPos(), new PVector(5, 0)));
						collision = determineCollision(player.proposeUpdate());
						canMoveH = false;
						player.setIsMovingSideways(false);
						wasOnIce = false;
					}
					player.setOnIce(false);
				}
				
				PVector proposedUpdate = player.proposeUpdate();
				
				boolean hasCollided = false;
				int colVer = 0, colHoriz = 0;
				if (collision[0][0] != 0 && collision[0][1] != 0)
					colVer = 1;
				else if (collision[1][0] != 0 && collision[1][1] != 0)
					colVer = -1;
				if (collision[0][0] != 0 && collision[1][0] != 0)
					colHoriz = 1;
				else if (collision[0][1] != 0 && collision[1][1] != 0)
					colHoriz = -1;
				
				
				if (!hasCollided) {
					if (colVer == 1 && proposedUpdate.getyComp() % tileSize != 0
							&& determineTile(PVector.add(proposedUpdate, new PVector(15, -5))).collision() > 0) {
						movePlayerDown(proposedUpdate);
						canMoveV = false;
					} else if (colVer == -1 && proposedUpdate.getyComp() % tileSize != 0 
							&& determineTile(PVector.add(proposedUpdate, new PVector(15, 35))).collision() > 0) {
						movePlayerUp(proposedUpdate);
						canMoveV = false;
						player.setIsOnGround(true);
					}
					if (colHoriz == 1 && proposedUpdate.getxComp() % tileSize != 0) {
						movePlayerRight(proposedUpdate);
						canMoveH = false;
					} else if (colHoriz == -1 && proposedUpdate.getxComp() % tileSize != 0) {
						movePlayerLeft(proposedUpdate);
						canMoveH = false;
					}
				}
				
				if (!hasCollided) {
					if (collision[0][0] != 0) {
						Tile collisionTile = determineTile(proposedUpdate);
						if (proposedUpdate.getxComp() % tileSize == 0 || proposedUpdate.getyComp() % tileSize == 0) {
							hasCollided = true;
						} else if (player.getPlayerPos().getxComp() % tileSize == 0) {
							int[][] specialCollision = determineCollision(player.getPlayerPos());
							if (!((specialCollision[0][0] == 1 || specialCollision[0][0] == 2) && (specialCollision[0][1] == 1 || specialCollision[0][1] == 2))) {
								canMoveH = false;
							} else
								canMoveV = false;
							hasCollided = true;
						} else if (collisionTile.getPos().getxComp()-proposedUpdate.getxComp() >= 
								collisionTile.getPos().getyComp()-proposedUpdate.getyComp()) {
							movePlayerDown(proposedUpdate);
							canMoveV = false;
							hasCollided = true;
						} else {
							movePlayerRight(proposedUpdate);
							canMoveH = false;
							hasCollided = true;
						}
					} else if (collision[0][1] != 0) {
						Tile collisionTile = determineTile(PVector.add(proposedUpdate, new PVector(tileSize, 0)));
						if (proposedUpdate.getxComp() % tileSize == 0 || proposedUpdate.getyComp() % tileSize == 0) {
							hasCollided = true;
						} else if (player.getPlayerPos().getxComp() % tileSize == 0) {
							int[][] specialCollision = determineCollision(player.getPlayerPos());
							if (!((specialCollision[0][0] == 1 || specialCollision[0][0] == 2) && (specialCollision[0][1] == 1 || specialCollision[0][1] == 2))) {
								player.updatePlayerY();
								canMoveH = false;
							} else
								canMoveV = false;
							hasCollided = true;
						} else if (proposedUpdate.getxComp()-collisionTile.getPos().getxComp() >=
								collisionTile.getPos().getyComp()-proposedUpdate.getyComp()) {
							movePlayerDown(proposedUpdate);
							canMoveV = false;
							hasCollided = true;
						} else {
							movePlayerLeft(proposedUpdate);
							canMoveH = false;
							hasCollided = true;
						}
					} else if (collision[1][0] != 0) {
						Tile collisionTile = determineTile(PVector.add(proposedUpdate, new PVector(0, tileSize)));
						if (proposedUpdate.getxComp() % tileSize == 0 || proposedUpdate.getyComp() % tileSize == 0){
							hasCollided = true;
						} else if (player.getPlayerPos().getxComp() % tileSize == 0) {
							int[][] specialCollision = determineCollision(player.getPlayerPos());
							if (!((specialCollision[1][0] == 1 || specialCollision[1][0] == 2) && (specialCollision[1][1] == 1 || specialCollision[1][1] == 2))) {
								canMoveH = false;
							} else
								canMoveV = false;
							hasCollided = true;
						} else if (collisionTile.getPos().getxComp()-proposedUpdate.getxComp() >=
								proposedUpdate.getyComp()-collisionTile.getPos().getyComp()) {
							movePlayerUp(proposedUpdate);
							canMoveV = false;
							player.setIsOnGround(true);
							hasCollided = true;
						} else {
							movePlayerRight(proposedUpdate);
							canMoveH = false;
							hasCollided = true;
						}
					} else if (collision[1][1] != 0) {
						Tile collisionTile = determineTile(PVector.add(proposedUpdate, new PVector(tileSize, tileSize)));
						if (proposedUpdate.getxComp() % tileSize == 0 || proposedUpdate.getyComp() % tileSize == 0) {
							hasCollided = true;
						} else if (player.getPlayerPos().getxComp() % tileSize == 0) {
							int[][] specialCollision = determineCollision(player.getPlayerPos());
							if (!((specialCollision[1][0] == 1 || specialCollision[1][0] == 2) && (specialCollision[1][1] == 1 || specialCollision[1][1] == 2))) {
								canMoveH = false;
							} else
								canMoveV = false;
							hasCollided = true;
						} else if (proposedUpdate.getxComp()-collisionTile.getPos().getxComp() >=
								proposedUpdate.getyComp()-collisionTile.getPos().getyComp()) {
							movePlayerUp(proposedUpdate);
							canMoveV = false;
							player.setIsOnGround(true);
							hasCollided = true;
						} else {
							movePlayerLeft(proposedUpdate);
							canMoveH = false;
							hasCollided = true;
						}
					}
				}
				
				if (canMoveH && canMoveV) {
					player.updatePlayer();
					if (!player.getCanJumpOverride())
						player.setIsOnGround(false);
				}else if (canMoveH) {
					player.updatePlayerX();
				} else if (canMoveV) {
					player.updatePlayerY();
				}
				player.setCanJumpOverride(false);
			}
			if (movingPlatforms != null)
				for (MovingPlatform mp : movingPlatforms)
					mp.update();
			repaint();
			timeCounter--;
		}
	}
	
	private Tile determineTile (PVector pos) {
		int x = (int) (pos.getxComp() / tileSize);
		int y = (int) (pos.getyComp() / tileSize);
		x += 22*room;
		try {
			return currentLevel.getTileMap()[y][x];
		} catch (Exception e) {
			player.setPlayerPos(PVector.add(currentLevel.getSpawn().get(room), new PVector(-22*tileSize*room, 0)));
			return null;
		}
	}
	
	private int[][] determineCollision (PVector proposedUpdate) {
		int[][] collision = {{0, 0},
							{0, 0}};
		try {
			if (proposedUpdate.getxComp() % tileSize == 0 && proposedUpdate.getyComp() % tileSize == 0) {
				int top = determineTile(PVector.add(proposedUpdate, new PVector(0.5, -0.5*tileSize))).collision();
				int left = determineTile(PVector.add(proposedUpdate, new PVector(-0.5*tileSize, 0.5))).collision();
				int center = determineTile(PVector.add(proposedUpdate, new PVector(0.5*tileSize, 0.5))).collision();
				int right = determineTile(PVector.add(proposedUpdate, new PVector(1.5*tileSize, 0.5))).collision();
				int bottom = determineTile(PVector.add(proposedUpdate, new PVector(0.5*tileSize, 1.5*tileSize))).collision();
				collision[0][0] = Math.max(Math.max(0, top), Math.max(left, center));
				collision[0][1] = Math.max(Math.max(top, 0), Math.max(center, right));
				collision[1][0] = Math.max(Math.max(left, center), Math.max(0, bottom));
				collision[1][1] = Math.max(Math.max(center, right), Math.max(bottom, 0));
			} else if (proposedUpdate.getxComp() % tileSize == 0) {
				int topLeft = determineTile(PVector.add(proposedUpdate, new PVector(-0.5*tileSize, 0))).collision();
				int topMiddle = determineTile(PVector.add(proposedUpdate, new PVector(0.5*tileSize, 0))).collision();
				int topRight = determineTile(PVector.add(proposedUpdate, new PVector(1.5*tileSize, 0))).collision();
				int bottomLeft = determineTile(PVector.add(proposedUpdate, new PVector(-0.5*tileSize, tileSize))).collision();
				int bottomMiddle = determineTile(PVector.add(proposedUpdate, new PVector(0.5*tileSize, tileSize))).collision();
				int bottomRight = determineTile(PVector.add(proposedUpdate, new PVector(1.5*tileSize, tileSize))).collision();
				collision[0][0] = Math.max(topLeft, topMiddle);
				collision[0][1] = Math.max(topMiddle, topRight);
				collision[1][0] = Math.max(bottomLeft, bottomMiddle);
				collision[1][1] = Math.max(bottomMiddle, bottomRight);
			} else if (proposedUpdate.getyComp() % tileSize == 0) {
				int leftTop = determineTile(PVector.add(proposedUpdate, new PVector(0, -0.5*tileSize))).collision();
				int rightTop = determineTile(PVector.add(proposedUpdate, new PVector(tileSize, -0.5*tileSize))).collision();
				int leftMiddle = determineTile(PVector.add(proposedUpdate, new PVector(0, 0.5*tileSize))).collision();
				int rightMiddle = determineTile(PVector.add(proposedUpdate, new PVector(tileSize, 0.5*tileSize))).collision();
				int leftBottom = determineTile(PVector.add(proposedUpdate, new PVector(0, 1.5*tileSize))).collision();
				int rightBottom = determineTile(PVector.add(proposedUpdate, new PVector(tileSize, 1.5*tileSize))).collision();
				collision[0][0] = Math.max(leftTop, leftMiddle);
				collision[0][1] = Math.max(rightTop, rightMiddle);
				collision[1][0] = Math.max(leftMiddle, leftBottom);
				collision[1][1] = Math.max(rightMiddle, rightBottom);
			} else {
				collision = simpleDetermineCollision(proposedUpdate);
			}
			return collision;
		} catch (Exception e) {
			return null;
		}
	}
	
	private int[][] simpleDetermineCollision (PVector proposedUpdate) {
		int[][] collision = {{0, 0},
				{0, 0}};
		collision[0][0] = determineTile(proposedUpdate).collision();
		collision[0][1] = determineTile(PVector.add(proposedUpdate, new PVector(tileSize, 0))).collision();
		collision[1][0] = determineTile(PVector.add(proposedUpdate, new PVector(0, tileSize))).collision();
		collision[1][1] = determineTile(PVector.add(proposedUpdate, new PVector(tileSize, tileSize))).collision();
		return collision;
	}
	
	private void movePlayerUp (PVector proposedUpdate) {
		player.setPlayerPos(new PVector(player.getPlayerPos().getxComp(), tileSize * ((int) proposedUpdate.getyComp() / tileSize)));
		player.getPlayerVel().setyComp(0.0);
	}
	
	private void movePlayerDown (PVector proposedUpdate) {
		player.setPlayerPos(new PVector(player.getPlayerPos().getxComp(), tileSize * ((int) proposedUpdate.getyComp() / tileSize + 1)));
		player.getPlayerVel().setyComp(0.0);
	}
	
	private void movePlayerLeft (PVector proposedUpdate) {
		player.setPlayerPos(new PVector(tileSize * ((int) proposedUpdate.getxComp() / tileSize), player.getPlayerPos().getyComp()));
		player.setIsMovingSideways(false);
		player.getPlayerVel().setxComp(0.0);
	}
	
	private void movePlayerRight (PVector proposedUpdate) {
		player.setPlayerPos(new PVector(tileSize * ((int) proposedUpdate.getxComp() / tileSize + 1), player.getPlayerPos().getyComp()));
		player.setIsMovingSideways(false);
		player.getPlayerVel().setxComp(0.0);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (CRtimeCounter > 0) {
			if (!shiftLorR) { //R to L
				for (int y = 0; y < currentLevel.getTileMap().length; y++)
					for (int x = 0; x < currentLevel.getTileMap()[0].length; x++)
						currentLevel.getTileMap()[y][x].shiftRight();
				for (GameObject object : currentLevel.getObjectArray())
					object.moveRight();
				if (movingPlatforms != null)
					for (MovingPlatform mp : movingPlatforms)
						mp.moveRight();
				player.setPlayerPos(new PVector(player.getPlayerPos().getxComp() + 4, player.getPlayerPos().getyComp()));
			} else { //L to R
				for (int y = 0; y < currentLevel.getTileMap().length; y++)
					for (int x = 0; x < currentLevel.getTileMap()[0].length; x++)
						currentLevel.getTileMap()[y][x].shiftLeft();
				for (GameObject object : currentLevel.getObjectArray())
					object.moveLeft();
				if (movingPlatforms != null)
					for (MovingPlatform mp : movingPlatforms)
						mp.moveLeft();
				player.setPlayerPos(new PVector(player.getPlayerPos().getxComp() - 4, player.getPlayerPos().getyComp()));
			}
			CRtimeCounter--;
		} else
			player.setInRoomTransition(false);
		
		for (int i = 0; i < currentLevel.getTileMap()[0].length; i++)
			for (int j = 0; j < currentLevel.getTileMap().length; j++)
				currentLevel.getTileMap()[j][i].drawTile(g);
		for (GameObject object : currentLevel.getObjectArray())
			object.drawGameObject(g);
		
		if (movingPlatforms != null)
			for (MovingPlatform mp : movingPlatforms)
				mp.paintPlatform(g);
		
		if (!playerIsDead) {
			player.paintPlayer(g);
		} else {
			if (deathAnimationTimer == 0) {
				deathCounter++;
				playerCanMove = false;
			} else if (deathAnimationTimer < 40) {
				g.setColor(Color.RED);
				for (int i = 0; i < 24; i++)
					g.drawLine((int) ((player.getPlayerPos().getxComp()+GamePanel.tileSize/2) + deathAnimationTimer*Math.cos(i*Math.PI/12)), 
							(int) ((player.getPlayerPos().getyComp()+GamePanel.tileSize/2) + deathAnimationTimer*Math.sin(i*Math.PI/12)),
							(int) ((player.getPlayerPos().getxComp()+GamePanel.tileSize/2) + (deathAnimationTimer+30)*Math.cos(i*Math.PI/12)),
							(int) ((player.getPlayerPos().getyComp()+GamePanel.tileSize/2) + (deathAnimationTimer+30)*Math.sin(i*Math.PI/12)));
			} else if (deathAnimationTimer == 40) {
				player.setPlayerPos(PVector.add(currentLevel.getSpawn().get(room), new PVector(-22*30*room, 0)));
			} else if (deathAnimationTimer > 40 && deathAnimationTimer < 80) {
				g.setColor(Color.RED);
				for (int i = 0; i < 24; i++)
					g.drawLine((int) ((player.getPlayerPos().getxComp()+GamePanel.tileSize/2) + (70-deathAnimationTimer)*Math.cos(i*Math.PI/12)), 
							(int) ((player.getPlayerPos().getyComp()+GamePanel.tileSize/2) + (70-deathAnimationTimer)*Math.sin(i*Math.PI/12)),
							(int) ((player.getPlayerPos().getxComp()+GamePanel.tileSize/2) + (70-deathAnimationTimer+30)*Math.cos(i*Math.PI/12)),
							(int) ((player.getPlayerPos().getyComp()+GamePanel.tileSize/2) + (70-deathAnimationTimer+30)*Math.sin(i*Math.PI/12)));
			}
			deathAnimationTimer++;
			if (deathAnimationTimer == 80) {
				playerIsDead = false;
				playerCanMove = true;
				deathAnimationTimer = 0;
			}
		}
		
		g.setColor(Color.BLACK);
		g.drawString("Coins: " + player.getScore() + " / " + currentLevel.getCollectibleQuota(), 565, 20);
		g.drawString("Time: " + (timeCounter/40), 480, 20);
	}
	
	public void shiftRoomR () {
		room++;
		shiftLorR = true;
		CRtimeCounter = 22*tileSize/5;
	}
	
	public void shiftRoomL () {
		room--;
		shiftLorR = false;
		CRtimeCounter = 22*tileSize/5;
	}
	
	public void shiftGOtoOriginal () {
		if (room > 0)
			for (int r = 0; r < room; r++)
				for (int i = 0; i < 22*6; i++) {
					for (GameObject object : currentLevel.getObjectArray())
						object.moveRight();
					if (movingPlatforms != null)
						for (MovingPlatform mp : movingPlatforms)
							mp.moveRight();
				}

	}
}