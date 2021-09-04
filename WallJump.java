import java.awt.Color;
import java.awt.Graphics;

public class WallJump extends GameObject {
	private boolean playerWasTouching;
	private Player player;

	public void collision (Player player) {
		this.player = player;
		if (hasCollided(player)) {
			player.setIsOnGround(true);
			player.setCanJumpOverride(true);
			playerWasTouching = true;
		} else if (playerWasTouching){
			player.setIsOnGround(false);
			playerWasTouching = false;
		}
	}
	
	public void drawGameObject (Graphics g) {
		super.drawGameObject(g);
		if (playerWasTouching && player.getPlayerVel().getyComp() > 0) {
			if (player.getPlayerPos().getxComp() + GamePanel.tileSize > getPos().getxComp() && 
					player.getPlayerPos().getxComp() < getPos().getxComp() + GamePanel.tileSize)
				return;
			
			int y = 0;
			if (player.getPlayerPos().getyComp() <= getPos().getyComp()) { //top
				y = (int) (getPos().getyComp() + (player.getPlayerPos().getyComp() + GamePanel.tileSize - 
						getPos().getyComp())*Math.random());
			} else { //bottom
				y = (int) (getPos().getyComp() + GamePanel.tileSize - (getPos().getyComp() + GamePanel.tileSize - 
						player.getPlayerPos().getyComp())*Math.random());
			}
			
			int size = (int) (8*Math.random());
			g.setColor(Color.LIGHT_GRAY);
			
			if (player.getPlayerPos().getxComp() >= getPos().getxComp() + GamePanel.tileSize) { //right size
				g.fillOval((int) (getPos().getxComp() + GamePanel.tileSize - size), y, 2*size, 2*size);
			} else { //left side
				g.fillOval((int) (getPos().getxComp() - size), y, 2*size, 2*size);
			}
		}
	}
	
	public WallJump (int x, int y) {
		super(new PVector(x, y));
		playerWasTouching = false;
		setColor(new Color(0, 153, 0));
	}
}