import java.awt.*;

public abstract class GameObject {
	private PVector pos;
	private Color color;
	
	public Color getColor () {
		return color;
	}
	
	public void setColor (Color color) {
		this.color = color;
	}
	
	public PVector getPos () {
		return pos;
	}
	
	public void setPos (PVector pos) {
		this.pos = pos;
	}
	
	public abstract void collision (Player player);
	
	public void drawGameObject (Graphics g) {
		g.setColor(color);
		g.fillRect((int) pos.getxComp(), (int) pos.getyComp(), GamePanel.tileSize, GamePanel.tileSize);
	}
	
	public GameObject (PVector pos) {
		this.pos = new PVector(pos.getxComp(), pos.getyComp());
	}
	
	public void moveLeft () {
		pos = new PVector(pos.getxComp() - 5, pos.getyComp());
	}
	
	public void moveRight () {
		pos = new PVector(pos.getxComp() + 5, pos.getyComp());
	}
	
	public static boolean isInRange (double value, double low, double high) {
		if (value >= low && value <= high)
			return true;
		return false;
	}
	
	public boolean hasCollided (Player player) {
		return (isInRange(player.getPlayerPos().getxComp(), pos.getxComp(), pos.getxComp() + GamePanel.tileSize)
				&& (isInRange(player.getPlayerPos().getyComp(), pos.getyComp(), pos.getyComp() + GamePanel.tileSize)))
				|| (isInRange(player.getPlayerPos().getxComp() + GamePanel.tileSize, pos.getxComp(), pos.getxComp() + GamePanel.tileSize)
				&& (isInRange(player.getPlayerPos().getyComp(), pos.getyComp(), pos.getyComp() + GamePanel.tileSize)))
				|| (isInRange(player.getPlayerPos().getxComp(), pos.getxComp(), pos.getxComp() + GamePanel.tileSize)
				&& (isInRange(player.getPlayerPos().getyComp() + GamePanel.tileSize, pos.getyComp(), pos.getyComp() + GamePanel.tileSize)))
				|| (isInRange(player.getPlayerPos().getxComp() + GamePanel.tileSize, pos.getxComp(), pos.getxComp() + GamePanel.tileSize)
				&& (isInRange(player.getPlayerPos().getyComp() + GamePanel.tileSize, pos.getyComp(), pos.getyComp() + GamePanel.tileSize)));
	}
	
	public boolean onCorner (Player player) {
		return ((player.getPlayerPos().getxComp() + GamePanel.tileSize == pos.getxComp()
				|| player.getPlayerPos().getxComp() == pos.getxComp() + GamePanel.tileSize)
				&& (player.getPlayerPos().getyComp() + GamePanel.tileSize == pos.getyComp()
				|| player.getPlayerPos().getyComp() == pos.getyComp() + GamePanel.tileSize));
	}
}