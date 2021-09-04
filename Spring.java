import java.awt.*;

public class Spring extends GameObject {
	private boolean playerWasTouching;
	private int animationTimer;

	public void collision (Player player) {
		if (hasCollided(player) && !onCorner(player)) {
			player.setJumpVel(-14);
			playerWasTouching = true;
		} else if (playerWasTouching){
			player.setJumpVel(-7.7);
			playerWasTouching = false;
			if (player.getPlayerVel().getyComp() < 0)
				animationTimer = 10;
		}
	}
	
	public Spring (int x, int y) {
		super(new PVector(x, y));
		playerWasTouching = false;
		setColor(Color.GRAY);
		animationTimer = 0;
	}
	
	public void drawGameObject (Graphics g) {
		if (animationTimer == 0)
			super.drawGameObject(g);
		else {
			g.setColor(Color.GRAY);
			if (animationTimer > 5)
				g.fillRect((int) getPos().getxComp(), (int) getPos().getyComp() - 2*(10-animationTimer), 
						GamePanel.tileSize, GamePanel.tileSize - (10-animationTimer));
			else
				g.fillRect((int) getPos().getxComp(), (int) getPos().getyComp() - 2*animationTimer, 
						GamePanel.tileSize, GamePanel.tileSize - animationTimer);
			animationTimer--;
		}
	}
}