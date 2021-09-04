import java.awt.Color;

public class DoorR extends GameObject {
	private boolean playerWasTouching;
	
	public void collision(Player player) {
		if (hasCollided(player) && !player.getInRoomTransition()) {
			if (!playerWasTouching) {
				player.getGamePanel().shiftRoomR();
				player.setInRoomTransition(true);
				playerWasTouching = true;
				player.getGamePanel().setExitCollision(true);
			}
		} else
			playerWasTouching = false;
	}
	
	public DoorR (int x, int y) {
		super(new PVector(x, y));
		setColor(Color.BLACK);
	}
}