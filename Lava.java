import java.awt.*;

public class Lava extends GameObject {
	private int animationTimer;
	private PVector point;

	public void collision (Player player) {
		if (hasCollided(player) && !onCorner(player)) {
			player.getGamePanel().killPlayer();
		}	
	}
	
	public void drawGameObject (Graphics g) {
		super.drawGameObject(g);
		g.setColor(Color.ORANGE);
		if (animationTimer > 6) {
			for (int i = 0; i < 8; i++)
				g.drawLine((int) (point.getxComp() + Math.cos(i*Math.PI/4)), (int) (point.getyComp() + Math.sin(i*Math.PI/4)), 
						(int) (point.getxComp() + 2*Math.cos(i*Math.PI/4)), (int) (point.getyComp() + 2*Math.sin(i*Math.PI/4)));
		} else if (animationTimer > 3) {
			for (int i = 0; i < 16; i++)
				g.drawLine((int) (point.getxComp() + Math.cos(i*Math.PI/8)), (int) (point.getyComp() + Math.sin(i*Math.PI/4)), 
						(int) (point.getxComp() + 4*Math.cos(i*Math.PI/4)), (int) (point.getyComp() + 4*Math.sin(i*Math.PI/4)));
		} else if (animationTimer > 0) {
			for (int i = 0; i < 24; i++)
				g.drawLine((int) (point.getxComp() + 2*Math.cos(i*Math.PI/12)), (int) (point.getyComp() + 2*Math.sin(i*Math.PI/4)), 
						(int) (point.getxComp() + 6*Math.cos(i*Math.PI/4)), (int) (point.getyComp() + 6*Math.sin(i*Math.PI/4)));
		} else {
			double rand = Math.random();
			if (rand > 0.8) {
				point = new PVector(getPos().getxComp() + GamePanel.tileSize*Math.random(), getPos().getyComp() - 1);
				animationTimer = 10;
			}
		}
		animationTimer--;
	}
	
	public Lava (int x, int y) {
		super(new PVector(x, y));
		setColor(Color.ORANGE);
		animationTimer = 0;
	}
}