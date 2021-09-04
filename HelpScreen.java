import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HelpScreen extends JPanel {
	private JButton back, more;
	private int counter;
	
	public HelpScreen (GameWindow gameWindow) {
		counter = 0;
		
		back = new JButton("Back");
		back.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				gameWindow.makeScreen(GameWindow.LEVEL_MENU);
			}
		});
		back.setFocusable(false);
		back.setLocation(20, 20);
		
		HelpScreen helpScreen = this;
		more = new JButton("More");
		more.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				counter++;
				helpScreen.repaint();
			}
		});
		more.setFocusable(false);
		more.setLocation(340, 450);
		
		setLayout(null);
		add(back);
		add(more);
		Insets insets = this.getInsets();
		Dimension size = back.getPreferredSize();
		back.setBounds(10 + insets.left, 10 + insets.top, size.width, size.height);
		size = more.getPreferredSize();
		more.setBounds(313 + insets.left, 414 + insets.top, size.width, size.height);
	}
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
		g.drawString("How To Play", 250, 40);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		
		if (counter % 2 == 0) {
			g.setColor(Color.BLACK);
			g.drawString("Hold the left and right", 10, 100);
			g.drawString("arrow keys to move.", 10, 120);
			g.drawString("Press up to jump.", 10, 140);
			g.setColor(Color.WHITE);
			g.fillRect(200, 60, 100, 100);
			g.setColor(Color.GREEN);
			g.fillRect(200, 130, 100, 30);
			g.setColor(Color.RED);
			g.fillRect(235, 100, 30, 30);
			
			g.setColor(Color.BLACK);
			g.drawString("Collect coins to", 10, 220);
			g.drawString("earn points.", 10, 250);
			g.setColor(Color.WHITE);
			g.fillRect(200, 180, 100, 100);
			g.setColor(Color.GREEN);
			g.fillRect(200, 250, 100, 30);
			g.setColor(Color.RED);
			g.fillRect(220, 220, 30, 30);
			g.setColor(Color.YELLOW);
			g.fillOval(265, 225, 20, 20);
			g.setColor(Color.BLACK);
			g.drawOval(265, 225, 20, 20);
			
			g.setColor(Color.BLACK);
			g.drawString("You can jump higher", 10, 340);
			g.drawString("off a spring.", 10, 370);
			g.setColor(Color.WHITE);
			g.fillRect(200, 300, 100, 100);
			g.setColor(Color.GREEN);
			g.fillRect(200, 370, 100, 30);
			g.setColor(Color.GRAY);
			g.fillRect(235, 370, 30, 30);
			g.setColor(Color.RED);
			g.fillRect(235, 310, 30, 30);
			
			g.setColor(Color.BLACK);
			g.drawString("Ice is", 350, 100);
			g.drawString("slippery!", 350, 130);
			g.setColor(Color.WHITE);
			g.fillRect(520, 60, 100, 100);
			g.setColor(Color.CYAN);
			g.fillRect(520, 130, 100, 30);
			g.setColor(Color.RED);
			g.fillRect(575, 100, 30, 30);
			
			g.setColor(Color.BLACK);
			g.drawString("You can jump off", 350, 210);
			g.drawString("these tiles on", 350, 230);
			g.drawString("the wall.", 350, 250);
			g.setColor(Color.WHITE);
			g.fillRect(520, 180, 100, 100);
			g.setColor(Color.GREEN);
			g.fillRect(520, 180, 30, 100);
			g.setColor(new Color(0, 153, 0));
			g.fillRect(520, 215, 30, 30);
			g.setColor(Color.RED);
			g.fillRect(550, 205, 30, 30);
			
			g.setColor(Color.BLACK);
			g.drawString("Falling in the lava", 350, 340);
			g.drawString("will kill you.", 350, 370);
			g.setColor(Color.WHITE);
			g.fillRect(520, 300, 100, 100);
			g.setColor(Color.ORANGE);
			g.fillRect(520, 370, 100, 30);
			g.setColor(Color.RED);
			for (int i = 0; i < 24; i++)
				g.drawLine((int) ((555+GamePanel.tileSize/2) + 20*Math.cos(i*Math.PI/12)), 
						(int) ((340+GamePanel.tileSize/2) + 20*Math.sin(i*Math.PI/12)),
						(int) ((555+GamePanel.tileSize/2) + (20+30)*Math.cos(i*Math.PI/12)),
						(int) ((340+GamePanel.tileSize/2) + (20+30)*Math.sin(i*Math.PI/12)));
		} else {
			g.setColor(Color.BLACK);
			g.drawString("Doors will take you", 10, 100);
			g.drawString("to other rooms.", 10, 130);
			g.setColor(Color.WHITE);
			g.fillRect(200, 60, 100, 100);
			g.setColor(Color.GREEN);
			g.fillRect(200, 130, 100, 30);
			g.setColor(Color.RED);
			g.fillRect(215, 100, 30, 30);
			g.setColor(Color.BLACK);
			g.fillRect(270, 60, 30, 70);
			
			g.setColor(Color.BLACK);
			g.drawString("Spikes are very", 350, 100);
			g.drawString("dangerous.", 350, 130);
			g.setColor(Color.WHITE);
			g.fillRect(520, 60, 100, 100);
			g.setColor(Color.GREEN);
			g.fillRect(520, 130, 100, 30);
			g.setColor(Color.RED);
			g.fillRect(585, 100, 30, 30);
			g.setColor(Color.BLACK);
			g.fillRect(520, 80, 30, 30);
			for (int x = 520; x < 550; x += 10) {
				int[] xPos = {x, x+10, x+5};
				int[] yPos1 = {80, 80, 70};
				g.fillPolygon(new Polygon(xPos, yPos1, 3));
				int[] yPos2 = {110, 110, 120};
				g.fillPolygon(new Polygon(xPos, yPos2, 3));
			}
			for (int y = 80; y < 110; y += 10) {
				int[] xPos = {550, 550, 560};
				int[] yPos = {y, y+10, y+5};
				g.fillPolygon(new Polygon(xPos, yPos, 3));
			}
			
			g.setColor(Color.BLACK);
			g.drawString("Moving platforms", 10, 220);
			g.drawString("will hold you.", 10, 250);
			g.setColor(Color.WHITE);
			g.fillRect(200, 180, 100, 100);
			g.setColor(new Color(178, 34, 34));
			g.fillRect(205, 260, 90, 10);
			g.setColor(Color.BLACK);
			g.drawRect(205, 260, 90, 10);
			g.setColor(Color.RED);
			g.fillRect(235, 230, 30, 30);
			
			g.setColor(Color.BLACK);
			g.drawString("You can jump up", 350, 210);
			g.drawString("from under the", 350, 230);
			g.drawString("moving platforms.", 350, 250);
			g.setColor(Color.WHITE);
			g.fillRect(520, 180, 100, 100);
			g.setColor(new Color(178, 34, 34));
			g.fillRect(525, 220, 90, 10);
			g.setColor(Color.BLACK);
			g.drawRect(525, 220, 90, 10);
			g.setColor(Color.RED);
			g.fillRect(555, 210, 30, 30);
			g.setColor(Color.BLACK);
			g.drawLine(560, 250, 560, 270);
			g.drawLine(570, 250, 570, 260);
			g.drawLine(580, 250, 580, 270);
			
			g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
			g.drawString("Have Fun!", 290, 360);
		}
	}

}