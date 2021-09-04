import java.awt.*;

public class GroundTile extends Tile {
	static final int tileValue = 1;
	private int grassTimer;
	
	public GroundTile (int xTile, int yTile) {
		super(xTile, yTile);
		grassTimer = 0;
	}
	
	public int collision() {
		return 1;
	}
	
	public void drawTile(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect((int) getPos().getxComp(), (int) getPos().getyComp(), GamePanel.tileSize, GamePanel.tileSize);
		
		for (int i = 0; i < GamePanel.tileSize; i++)
			if (i % 3 == 1)
				g.drawLine((int) getPos().getxComp() + i, (int) getPos().getyComp(), 
						(int) (getPos().getxComp() + i + 2*Math.sin(grassTimer * Math.PI / 32)), (int) getPos().getyComp()-6);
		grassTimer++;
	}
}