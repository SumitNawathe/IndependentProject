import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TitleScreen extends JPanel {
	private JButton start;
	private java.util.Timer timer;
	private PseudoPlayer pseudoPlayer;
	private int grassTimer;
	
	public java.util.Timer getTimer () {
		return timer;
	}
	
	public TitleScreen (GameWindow gameWindow) {
		grassTimer = 0;
		setBackground(Color.WHITE);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(5, 5)));
		
		JLabel title = new JLabel("Greed");
		title.setFont(new Font("TimesRoman", Font.BOLD, 40));
		title.setAlignmentX(CENTER_ALIGNMENT);
		add(title);
		
		add(new Box.Filler(new Dimension(0, 0), new Dimension(300, 300), new Dimension(300, 300)));
		
		start = new JButton("Start");
		start.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				timer.cancel();
				timer.purge();
				gameWindow.makeScreen(GameWindow.LEVEL_MENU);
			}
		});
		start.setFocusable(false);
		start.setAlignmentX(CENTER_ALIGNMENT);
		start.setMinimumSize(new Dimension(0, 0));
		start.setPreferredSize(new Dimension(0, 0));
		start.setMaximumSize(new Dimension(150, 50));
		
		add(new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(10, 10)));
		start.setAlignmentY(BOTTOM_ALIGNMENT);
		add(start);
		
		pseudoPlayer = new PseudoPlayer(new PVector(0, 300-GamePanel.tileSize));
		pseudoPlayer.startMovingRight();
		TitleScreen titleScreen = this;
		timer = new java.util.Timer();
		timer.scheduleAtFixedRate(new java.util.TimerTask () {
			public void run () {
				pseudoPlayer.updatePlayer();
				if (pseudoPlayer.getPlayerPos().getyComp() > 300-GamePanel.tileSize) {
					pseudoPlayer.setPlayerPos(new PVector(pseudoPlayer.getPlayerPos().getxComp(), 300-GamePanel.tileSize));
					pseudoPlayer.setPlayerVel(new PVector(pseudoPlayer.getPlayerVel().getxComp(), 0));
				}
				else if (pseudoPlayer.getPlayerPos().getyComp() == 300-GamePanel.tileSize)
					pseudoPlayer.setIsOnGround(true);
				if (pseudoPlayer.getPlayerPos().getxComp() > 1000)
					pseudoPlayer.setPlayerPos(new PVector(-100, pseudoPlayer.getPlayerPos().getyComp()));
				double random = Math.random();
				if (random > 0.90) {
					pseudoPlayer.jump();
				}
				titleScreen.repaint();
			}
		}, 0, 25);
	}
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.GREEN);
		g.fillRect(0, 300, 800, 200);
		
		for (int i = 0; i < 800; i++)
			if (i % 3 == 1)
				g.drawLine(i, 300, (int) (i + 2*Math.sin(grassTimer * Math.PI / 40)), 290);
		grassTimer++;
		
		g.setColor(Color.RED);
		g.fillRect((int) pseudoPlayer.getPlayerPos().getxComp(), (int) pseudoPlayer.getPlayerPos().getyComp(), 
				GamePanel.tileSize, GamePanel.tileSize);
	}
	
	public class PseudoPlayer extends Player {
		public PseudoPlayer (PVector pos) {
			super(pos, null);
			super.setIsOnGround(true);
		}
		
		public void updatePlayer () {
			super.setPlayerPos(new PVector(super.getPlayerPos().getxComp() + super.getPlayerVel().getxComp(), 
					super.getPlayerPos().getyComp() + super.getPlayerVel().getyComp()));
			super.setPlayerVel(new PVector(super.getPlayerVel().getxComp(),
					super.getPlayerVel().getyComp() + super.getGravity()));
		}
		
		public void paintPseudoPlayer (Graphics g) {
			g.setColor(Color.red);
		}
	}
}