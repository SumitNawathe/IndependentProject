import java.awt.*;

public class SkewerHL extends Skewer {	
	public void drawGameObject(Graphics g) {
		updateLength();
		
		g.setColor(Color.BLACK);
		g.fillRect((int) getPos().getxComp() - getLength() + 10, (int) getPos().getyComp() + 10, getLength() - 10, 40);
		
		for (int x = (int) getPos().getxComp() - getLength() + 10; x < getPos().getxComp(); x += 10) {
			if (x + 10 > getPos().getxComp())
				break;
			int[] xPos = {x, x+10, x+5};
			int[] yPos1 = {(int) getPos().getyComp()+10, (int) getPos().getyComp()+10, (int) getPos().getyComp()};
			g.fillPolygon(new Polygon(xPos, yPos1, 3));
			int[] yPos2 = {(int) getPos().getyComp()+50, (int) getPos().getyComp()+50, (int) getPos().getyComp()+60};
			g.fillPolygon(new Polygon(xPos, yPos2, 3));
		}
		for (int y = (int) getPos().getyComp()+10; y < getPos().getyComp() + 50; y += 10) {
			int[] xPos = {(int) getPos().getxComp() - getLength() + 10, (int) getPos().getxComp() - getLength() + 10, 
					(int) getPos().getxComp() - getLength()};
			int[] yPos = {y, y+10, y+5};
			g.fillPolygon(new Polygon(xPos, yPos, 3));
		}
	}
	
	public SkewerHL (PVector pos, int min, int max) {
		super(pos, min, max);
	}
	
	public boolean hasCollided (Player player) {
		return (isInRange(player.getPlayerPos().getxComp(), getPos().getxComp() - getLength(), getPos().getxComp())
				&& (isInRange(player.getPlayerPos().getyComp(), getPos().getyComp(), getPos().getyComp() + 60)))
				|| (isInRange(player.getPlayerPos().getxComp() + GamePanel.tileSize, getPos().getxComp() - getLength(), getPos().getxComp())
				&& (isInRange(player.getPlayerPos().getyComp(), getPos().getyComp(), getPos().getyComp() + 60)))
				|| (isInRange(player.getPlayerPos().getxComp(), getPos().getxComp() - getLength(), getPos().getxComp())
				&& (isInRange(player.getPlayerPos().getyComp() + GamePanel.tileSize, getPos().getyComp(), getPos().getyComp() + 60)))
				|| (isInRange(player.getPlayerPos().getxComp() + GamePanel.tileSize, getPos().getxComp() - getLength(), getPos().getxComp())
				&& (isInRange(player.getPlayerPos().getyComp() + GamePanel.tileSize, getPos().getyComp(), getPos().getyComp() + 60)));
	}
}