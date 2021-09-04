import java.awt.*;

public abstract class MovingPlatform {
	private PVector pos;
	private Player player;
	private boolean isOnTop;
	private double speed;
	
	public Player getPlayer () {
		return player;
	}
	
	public boolean getIsOnTop () {
		return isOnTop;
	}
	
	public double getSpeed () {
		return speed;
	}
	
	public PVector getPos () {
		return pos;
	}
	
	public void setPos (PVector newPos) {
		pos = newPos;
	}
	
	public MovingPlatform (PVector pos, double speed) {
		this.pos = pos;
		this.speed = speed;
	}
	
	public boolean[] collision (Player player) {
		this.player = player;
		PVector playerPos = new PVector(player.proposeUpdate().getxComp(), player.proposeUpdate().getyComp());
		if (playerPos.getxComp() > pos.getxComp() + 90
				|| playerPos.getxComp() + GamePanel.tileSize < pos.getxComp()
				|| playerPos.getyComp() > pos.getyComp() + 10
				|| playerPos.getyComp() + GamePanel.tileSize < pos.getyComp()) {
			isOnTop = false;
			return null;
		}
		
		if (playerPos.getyComp() + GamePanel.tileSize <= pos.getyComp() + 10
				&& playerPos.getxComp() + GamePanel.tileSize>= pos.getxComp()
				&& playerPos.getxComp() <= pos.getxComp() + 90
				&& player.getPlayerVel().getyComp() >= 0) {
			player.setPlayerPos(new PVector(player.getPlayerPos().getxComp(), pos.getyComp() - GamePanel.tileSize));
			if (player.getPlayerVel().getyComp() >= 0) {
				player.setPlayerVel(new PVector(player.getPlayerVel().getxComp(), 0));
				player.setIsOnGround(true);
				isOnTop = true;
				boolean[] c = {true, false};
				return c;
			} else {
				isOnTop = false;
				boolean[] c = {true, true};
				return c;
			}
		}
		
		return null;
	}
	
	public abstract void update();
	
	public void paintPlatform (Graphics g) {
		g.setColor(new Color(178, 34, 34));
		g.fillRect((int) pos.getxComp(), (int) pos.getyComp(), 90, 10);
		g.setColor(Color.BLACK);
		g.drawRect((int) pos.getxComp(), (int) pos.getyComp(), 90, 10);
	}
	
	public void moveLeft () {
		pos = new PVector(pos.getxComp() - 5, pos.getyComp());
	}
	
	public void moveRight () {
		pos = new PVector(pos.getxComp() + 5, pos.getyComp());
	}
}