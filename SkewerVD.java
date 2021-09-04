import java.awt.*;

public class SkewerVD extends Skewer {
	public void drawGameObject(Graphics g) {
		updateLength();
		
		g.setColor(Color.BLACK);
		g.fillRect((int) getPos().getxComp() + 10, (int) getPos().getyComp(), 40, getLength() - 10);
		
		for (int y = (int) getPos().getyComp(); y < getPos().getyComp() + getLength() - 10; y += 10) {
			int[] xPos1 = {(int) getPos().getxComp() + 10, (int) getPos().getxComp() + 10, (int) getPos().getxComp()};
			int[] yPos = {y, y+10, y+5};
			g.fillPolygon(new Polygon(xPos1, yPos, 3));
			int[] xPos2 = {(int) getPos().getxComp() + 50, (int) getPos().getxComp() + 50, (int) getPos().getxComp() + 60};
			g.fillPolygon(new Polygon(xPos2, yPos, 3));
		}
		for (int x = (int) getPos().getxComp()+10; x < getPos().getxComp() + 50; x += 10) {
			int[] xPos = {x, x+10, x+5};
			int[] yPos = {(int) getPos().getyComp() + getLength() - 10, (int) getPos().getyComp() + getLength() - 10, 
					(int) getPos().getyComp() + getLength()};
			g.fillPolygon(new Polygon(xPos, yPos, 3));
		}
	}
	
	public SkewerVD(PVector pos, int min, int max) {
		super(pos, min, max);
	}

	public boolean hasCollided(Player player) {
		return (isInRange(player.getPlayerPos().getxComp(), getPos().getxComp(), getPos().getxComp() + 60)
				&& (isInRange(player.getPlayerPos().getyComp(), getPos().getyComp(), getPos().getyComp() + getLength())))
				|| (isInRange(player.getPlayerPos().getxComp() + GamePanel.tileSize, getPos().getxComp(), getPos().getxComp() + 60)
				&& (isInRange(player.getPlayerPos().getyComp(), getPos().getyComp(), getPos().getyComp() + getLength())))
				|| (isInRange(player.getPlayerPos().getxComp(), getPos().getxComp(), getPos().getxComp() + 60)
				&& (isInRange(player.getPlayerPos().getyComp() + GamePanel.tileSize, getPos().getyComp(), getPos().getyComp() + getLength())))
				|| (isInRange(player.getPlayerPos().getxComp() + GamePanel.tileSize, getPos().getxComp(), getPos().getxComp() + 60)
				&& (isInRange(player.getPlayerPos().getyComp() + GamePanel.tileSize, getPos().getyComp(), getPos().getyComp() + getLength())));
	}
}