import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class LevelMenu extends JPanel {
	private GameWindow gameWindow;
	private int grassTimer;
	private java.util.Timer timer;
	
	public LevelMenu (GameWindow gameWindow) {
		this.gameWindow = gameWindow;
		grassTimer = 0;
		
		JButton[] buttonList = new JButton[8];
		buttonList[0] = new JButton("Back to Title Screen");
		buttonList[0].addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				gameWindow.makeScreen(GameWindow.TITLE_SCREEN);
			}
		});
		for (int i = 1; i < 7; i++) {
			buttonList[i] = new JButton("Level " + i);
			final int x = i;
			buttonList[i].addActionListener(new ActionListener () {
				public void actionPerformed(ActionEvent e) {
					gameWindow.makeScreen(x);
				}
			});
		}
		buttonList[7] = new JButton("How To Play");
		buttonList[7].addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				gameWindow.makeScreen(GameWindow.HELP_SCREEN);
			}
		});
		
		for (JButton button : buttonList)
			button.setFocusable(false);
		
		setLayout(new GridLayout(10, 3));
		
		JLabel title = new JLabel("Please select a level:", SwingConstants.CENTER);
		title.setFont(new Font(null, Font.PLAIN, 20));
		add(new JLabel(""));
		add(title);
		add(new JLabel(""));
		
		JLabel level = new JLabel("Level:", SwingConstants.CENTER);
		level.setFont(new Font(null, Font.PLAIN, 15));
		JLabel completion = new JLabel("Completion:", SwingConstants.CENTER);
		completion.setFont(new Font(null, Font.PLAIN, 15));
		JLabel deaths = new JLabel("Least Deaths:", SwingConstants.CENTER);
		deaths.setFont(new Font(null, Font.PLAIN, 15));
		add(level);
		add(completion);
		add(deaths);
		
		for (int i = 1; i < 7; i++) {
			add(buttonList[i]);
			if (gameWindow.getLevelCompletion()[i-1] == true)
				add(new JLabel("Completed!", SwingConstants.CENTER));
			else
				add(new JLabel("Incomplete", SwingConstants.CENTER));
			if (gameWindow.getLeastDeaths()[i-1] == 100)
				add(new JLabel(""));
			else
				add(new JLabel("" + gameWindow.getLeastDeaths()[i-1], SwingConstants.CENTER));
		}
		
		for (int i = 0; i < 3; i++)
			add(new JLabel(""));
		
		add(buttonList[0]);
		add(new JLabel(""));
		add(buttonList[7]);
		
		timer = new java.util.Timer();
		LevelMenu levelMenu = this;
		timer.scheduleAtFixedRate(new java.util.TimerTask () {
			public void run () {
				levelMenu.repaint();
			}
		}, 0, 25);
	}
	
	public void paintComponent (Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 800, 500);
		
		g.setColor(Color.GREEN);
		g.fillRect(0, 300, 800, 200);
		
		for (int i = 0; i < 800; i++)
			if (i % 3 == 1)
				g.drawLine(i, 300, (int) (i + 2*Math.sin(grassTimer * Math.PI / 40)), 290);
		grassTimer++;
	}
}