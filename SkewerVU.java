import java.awt.*;

public class SkewerVU extends Skewer {
	public void drawGameObject(Graphics g) {
		updateLength();
		
		g.setColor(Color.BLACK);
		g.fillRect((int) getPos().getxComp() + 10, (int) getPos().getyComp() - getLength() + 10, 40, getLength() - 10);
		
		for (int y = (int) getPos().getyComp() - getLength() + 10; y < getPos().getyComp(); y += 10) {
			int[] yPos = {y, y+10, y+5};
			int[] xPos1 = {(int) getPos().getxComp()+10, (int) getPos().getxComp()+10, (int) getPos().getxComp()};
			g.fillPolygon(new Polygon(xPos1, yPos, 3));
			int[] xPos2 = {(int) getPos().getxComp()+50, (int) getPos().getxComp()+50, (int) getPos().getxComp()+60};
			g.fillPolygon(new Polygon(xPos2, yPos, 3));
		}
		for (int x = (int) getPos().getxComp()+10; x < getPos().getxComp() + 50; x += 10) {
			int[] yPos = {(int) getPos().getyComp() - getLength() + 10, (int) getPos().getyComp() - getLength() + 10, 
					(int) getPos().getyComp() - getLength()};
			int[] xPos = {x, x+10, x+5};
			g.fillPolygon(new Polygon(xPos, yPos, 3));
		}
	}
	
	public SkewerVU (PVector pos, int min, int max) {
		super(pos, min, max);
	}

	public boolean hasCollided(Player player) {
		return (isInRange(player.getPlayerPos().getxComp(), getPos().getxComp(), getPos().getxComp() + 60)
				&& (isInRange(player.getPlayerPos().getyComp(), getPos().getyComp() - getLength(), getPos().getyComp())))
				|| (isInRange(player.getPlayerPos().getxComp() + GamePanel.tileSize, getPos().getxComp(), getPos().getxComp() + 60)
				&& (isInRange(player.getPlayerPos().getyComp(), getPos().getyComp() - getLength(), getPos().getyComp())))
				|| (isInRange(player.getPlayerPos().getxComp(), getPos().getxComp(), getPos().getxComp() + 60)
				&& (isInRange(player.getPlayerPos().getyComp() + GamePanel.tileSize, getPos().getyComp() - getLength(), getPos().getyComp())))
				|| (isInRange(player.getPlayerPos().getxComp() + GamePanel.tileSize, getPos().getxComp(), getPos().getxComp() + 60)
				&& (isInRange(player.getPlayerPos().getyComp() + GamePanel.tileSize, getPos().getyComp() - getLength(), getPos().getyComp())));
	}
}