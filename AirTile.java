import java.awt.*;

public class AirTile extends Tile {
	static final int tileValue = 0;
	
	public AirTile (int xTile, int yTile) {
		super(xTile, yTile);
	}
	
	public int collision() {
		return 0;
	}
	
	public void drawTile (Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect((int) getPos().getxComp(), (int) getPos().getyComp(), 
				GamePanel.tileSize, GamePanel.tileSize);
	}
}