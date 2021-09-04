import java.awt.*;

public class Player {
	private PVector pos, vel, accel;
	private double gravity = 0.5, jumpVel = -7.7;
	private boolean isMovingSideways, isOnGround, onIce, 
		inRoomTransition, canJumpOverride;
	private int score;
	private GamePanel gamePanel;
	
	public boolean getCanJumpOverride () {
		return canJumpOverride;
	}
	
	public void setCanJumpOverride (boolean jump) {
		canJumpOverride = jump;
	}
	
	public boolean getInRoomTransition () {
		return inRoomTransition;
	}
	
	public void setInRoomTransition (boolean rt) {
		inRoomTransition = rt;
	}
	
	public boolean getOnIce () {
		return onIce;
	}
	
	public void setOnIce (boolean onIce) {
		this.onIce = onIce;
	}
	
	public GamePanel getGamePanel () {
		return gamePanel;
	}
	
	public void setJumpVel (double jumpVel) {
		this.jumpVel = jumpVel;
	}
	
	public int getScore () {
		return score;
	}
	
	public void setScore (int score) {
		this.score = score;
	}
	
	public void incrementScore () {
		score++;
	}
	
	public double getGravity () {
		return gravity;
	}
	
	public void setGravity (double gravity) {
		this.gravity = gravity;
	}
	
	public boolean getIsMovingSideways () {
		return isMovingSideways;
	}
	
	public void setIsMovingSideways (boolean isMovingSideways) {
		this.isMovingSideways = isMovingSideways;
	}
	
	public boolean getIsOnGround () {
		return isOnGround;
	}
	
	public void setIsOnGround (boolean isOnGround) {
		this.isOnGround = isOnGround;
	}
	
	public PVector getPlayerPos () {
		return new PVector(pos.getxComp(), pos.getyComp());
	}
	
	public PVector getPlayerVel () {
		return vel;
	}
	
	public void setPlayerPos (PVector pos) {
		this.pos = pos;
	}
	
	public void setPlayerVel (PVector vel) {
		this.vel = vel;
	}
	
	public void startMovingLeft () {
		if (!isMovingSideways) {
			vel.setxComp(-5.0);
			isMovingSideways = true;
		}
	}
	
	public void startMovingRight () {
		if (!isMovingSideways) {
			vel.setxComp(5.0);
			isMovingSideways = true;
		}
	}
	
	public void stopMovingLeft () {
		if (isMovingSideways && !onIce) {
			vel.setxComp(0.0);
			isMovingSideways = false;
		}
	}
	
	public void stopMovingRight () {
		if (isMovingSideways && !onIce) {
			vel.setxComp(0.0);
			isMovingSideways = false;
		}	
	}
	
	public void jump () {
		if (isOnGround) {
			vel = new PVector (vel.getxComp(), jumpVel);
			isOnGround = false;
		}
	}
	
	public Player (PVector pos, GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		this.pos = new PVector(pos.getxComp(), pos.getyComp());
		vel = new PVector(0.0, 0.0);
		accel = new PVector(0.0, gravity);
		isMovingSideways = false;
		isOnGround = false;
		score = 0;
		inRoomTransition = false;
	}
	
	public void updatePlayer () {
		pos.add(vel);
		vel.add(accel);
		if (vel.getyComp() >= GamePanel.tileSize)
			vel.setyComp(GamePanel.tileSize);
		else if (vel.getyComp() <= -1*GamePanel.tileSize)
			vel.setyComp(-1*GamePanel.tileSize);
	}
	
	public void updatePlayerX () {
		pos.setxComp(pos.getxComp() + vel.getxComp());
		vel.setxComp(vel.getxComp() + accel.getxComp());
	}
	
	public void updatePlayerY () {
		pos.setyComp(pos.getyComp() + vel.getyComp());
		vel.setyComp(vel.getyComp() + accel.getyComp());
		if (vel.getyComp() >= GamePanel.tileSize)
			vel.setyComp(GamePanel.tileSize);
		else if (vel.getyComp() <= -1*GamePanel.tileSize)
			vel.setyComp(-1*GamePanel.tileSize);
	}
	
	public PVector proposeUpdate () {
		return new PVector (pos.getxComp() + vel.getxComp() + accel.getxComp(),
				pos.getyComp() + vel.getyComp() + accel.getyComp());
	}
	
	public void paintPlayer(Graphics g){
        g.setColor(Color.RED);
        g.fillRect((int) pos.getxComp(), (int) pos.getyComp(), 
        		GamePanel.tileSize, GamePanel.tileSize);
    }
}