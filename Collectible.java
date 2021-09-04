import java.awt.*;
import java.io.*;
import sun.audio.*;

public class Collectible extends GameObject {
	final static int radius = 10;
	private boolean isVisible;
	private int animationTimer;
	
	public void drawGameObject(Graphics g) {
		if (isVisible) {
			int dist = (int) (radius * Math.abs(Math.sin((2 * Math.PI / 40) * animationTimer)));
			g.setColor(Color.YELLOW);
			g.fillOval((int) getPos().getxComp()-dist, (int) getPos().getyComp()-radius, 2*dist, 2*radius);
			g.setColor(Color.BLACK);
			g.drawOval((int) getPos().getxComp()-dist, (int) getPos().getyComp()-radius, 2*dist, 2*radius);
			animationTimer++;
		}
	}

	public void collision (Player player) {
		if (isVisible && (PVector.distance(player.getPlayerPos(), getPos()) <= radius ||
				PVector.distance(PVector.add(player.getPlayerPos(), new PVector(GamePanel.tileSize, 0)), getPos()) <= radius ||
				PVector.distance(PVector.add(player.getPlayerPos(), new PVector(0, GamePanel.tileSize)), getPos()) <= radius ||
				PVector.distance(PVector.add(player.getPlayerPos(), new PVector(GamePanel.tileSize, GamePanel.tileSize)), getPos()) <= radius ||
				PVector.distance(PVector.add(player.getPlayerPos(), new PVector(GamePanel.tileSize/2, 0)), getPos()) <= radius ||
				PVector.distance(PVector.add(player.getPlayerPos(), new PVector(GamePanel.tileSize, GamePanel.tileSize/2)), getPos()) <= radius ||
				PVector.distance(PVector.add(player.getPlayerPos(), new PVector(GamePanel.tileSize/2, GamePanel.tileSize)), getPos()) <= radius ||
				PVector.distance(PVector.add(player.getPlayerPos(), new PVector(0, GamePanel.tileSize/2)), getPos()) <= radius)) {
			isVisible = false;
			player.incrementScore();
			
			try {
				InputStream in = new FileInputStream("Z:/CompSciA/Independent Project/Files/350876__cabled-mess__coin-c-09.wav");
				AudioStream audioStream = new AudioStream(in);
				AudioPlayer.player.start(audioStream);
			} catch (Exception e) {}
			    
		}
	}
	
	public Collectible (int x, int y) {
		super(new PVector(x, y));
		isVisible = true;
		setColor(Color.YELLOW);
		animationTimer = 0;
	}
}