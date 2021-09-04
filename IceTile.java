import java.awt.*;

public class IceTile extends Tile {
	static final int tileValue = 2;
	
	public IceTile (int xTile, int yTile) {
		super(xTile, yTile);
	}
	
	public int collision() {
		return 2;
	}
	
	public void drawTile(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect((int) getPos().getxComp(), (int) getPos().getyComp(), 
				GamePanel.tileSize, GamePanel.tileSize);
	}
}