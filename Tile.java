import java.awt.*;

public abstract class Tile {
	private PVector pos;
	
	
	public PVector getPos () {
		return pos;
	}
	
	public Tile (PVector pos) {
		this.pos = pos;
	}
	
	public Tile (int x, int y) {
		this(new PVector(x, y));
	}
	
	public void shiftLeft () {
		pos = new PVector(pos.getxComp() - 5, pos.getyComp());
	}
	
	public void shiftRight () {
		pos = new PVector(pos.getxComp() + 5, pos.getyComp());
	}
	
	public abstract int collision();
	public abstract void drawTile(Graphics g);
}