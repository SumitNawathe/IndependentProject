import java.awt.Color;

public class DoorL extends GameObject {
	private boolean playerWasTouching;
	
	public void collision(Player player) {
		if (hasCollided(player) && !player.getInRoomTransition()) {
			if (!playerWasTouching) {
				player.getGamePanel().shiftRoomL();
				player.setInRoomTransition(true);
				playerWasTouching = true;
				player.getGamePanel().setExitCollision(true);
			}
		} else
			playerWasTouching = false;
	}
	
	public DoorL (int x, int y) {
		super(new PVector(x, y));
		setColor(Color.BLACK);
	}
}