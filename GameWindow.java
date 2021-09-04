import javax.swing.*;
import java.awt.event.*;

public class GameWindow {
	private JFrame gameFrame;
	private GamePanel currentGamePanel;
	private TitleScreenListener titleScreenListener;
	private LevelMenuListener levelMenuListener;
	private GamePanelListener gamePanelListener;
	private HelpScreenListener helpScreenListener;
	private int[] leastDeaths;
	private boolean[] levelCompletion;
	private TitleScreen titleScreen;
	static final int HELP_SCREEN = -2;
	static final int TITLE_SCREEN = -1;
	static final int LEVEL_MENU = 0;
	
	public int[] getLeastDeaths () {
		return leastDeaths;
	}
	
	public boolean[] getLevelCompletion () {
		return levelCompletion;
	}
	
	char[][] tileMap1 = {
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1},
			{1, 2, 2, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
			{1, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1},
			{1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1},
			{1, 3, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1},
			{1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1},
			{1, 2, 2, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 1, 1, 2, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1},
			{1, 4, 0, 0, 0, 0, 1, 1, 1, 1, 2, 0, 0, 1, 2, 2, 1, 0, 2, 0, 0, 1},
			{1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 2, 0, 2, 0, 0, 2, 0, 0, 1, 3, 1},
			{1, 1, 1, 6, 6, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
	char[][] tileMap2 = {
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 0, 0, 0, 0, 0, 2, 2, 0, 2, 2, 2, 0, 0, 0, 2, 1, 0, 0, 2, 1},
			{1, 1, 2, 2, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1},
			{1, 5, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 5},
			{1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 5},
			{1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 5, 0, 0, 0, 0, 1, 0, 0, 2, 2, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 5},
			{1, 0, 4, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1},
			{1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 2, 2, 1, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 2, 1, 1, 6, 6, 6, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 6, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1}};
	char[][] tileMap3 = {
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1},
			{1, 0, 0, 2, 2, 0, 0, 2, 2, 2, 0, 0, 2, 2, 2, 0, 0, 2, 2, 0, 0, 5},
			{5, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 1},
			{1, 2, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 5},
			{1, 0, 0, 0, 1, 2, 0, 0, 2, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{5, 0, 0, 0, 1, 3, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
			{1, 2, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 5, 0, 2, 1},
			{1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 2, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1},
			{1, 4, 0, 1, 7, 7, 1, 1, 1, 7, 7, 7, 7, 0, 0, 0, 7, 7, 7, 7, 7, 1},
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 6, 6, 1, 1, 1, 1, 1, 1}};
	char[][] tileMap4 = {
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 6, 6, 6, 1, 1, 1, 1, 1, 1},
			{1, 0, 2, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1},
			{1, 0, 2, 1, 0, 0, 0, 0, 0, 0, 1, 2, 2, 1, 0, 0, 2, 2, 0, 0, 0, 1, 1, 2, 0, 2, 0, 0, 0, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1},
			{1, 0, 2, 1, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 5, 0, 0, 1, 0, 0, 0, 0, 1, 2, 2, 1, 2, 2, 2, 2, 0, 0, 0, 0, 0, 5},
			{5, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 2, 0, 1, 1, 2, 2, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 2, 0, 0, 2, 1},
			{1, 2, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 1, 1, 0, 0, 2, 5, 1, 1, 1, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 5},
			{5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 5, 1, 1, 1, 1, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 2, 1},
			{1, 2, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 8, 0, 0, 0, 1, 1, 1, 1, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 2, 1},
			{5, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 4, 0, 0, 0, 0, 5, 2, 0, 0, 0, 9, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 4, 2, 0, 0, 0, 0, 0, 0, 0, 0, 5},
			{1, 2, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 5, 2, 0, 0, 0, 9, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 2, 1},
			{5, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 5, 1, 1, 1, 3, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1},
			{1, 2, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 5, 1, 1, 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
			{5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 8, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 8, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
			{1, 7, 7, 7, 7, 7, 7, 7, 7, 3, 1, 1, 1, 3, 7, 7, 7, 7, 7, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 6, 1, 3, 1, 1, 1, 3, 1, 6, 6, 6, 1, 1, 1, 1}};
	char[][] tileMap5 = {
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 1, 2, 0, 0, 0, 1, 2, 2, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 1, 1, 2, 0, 2, 1, 2, 1, 2, 1, 2, 0, 2, 2, 2, 0, 0, 0, 1},
			{1, 2, 2, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1},
			{1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 1, 0, 0, 0, 0, 0, 0, 2, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 1, 0, 0, 0, 0, 0, 0, 2, 0, 1},
			{1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 2, 1},
			{1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 1, 0, 0, 0, 2, 1},
			{1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 2, 2, 1, 0, 0, 2, 0, 1},
			{1, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 1},
			{1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 3, 3, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1}};
	char[][] tileMap6 = {
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 2, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 9, 8, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 1, 2, 4, 2, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 9, 8, 0, 4, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 1, 2, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 2, 2, 1, 0, 0, 1},
			{1, 1, 0, 0, 2, 5, 1, 0, 0, 1, 1, 0, 0, 1, 5, 0, 0, 2, 0, 2, 0, 1, 1, 0, 0, 0, 0, 1, 0, 2, 2, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 1, 0, 0, 2, 5, 1, 0, 0, 5, 1, 0, 0, 1, 1, 0, 0, 0, 2, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
			{1, 5, 2, 0, 0, 1, 1, 0, 0, 5, 1, 0, 0, 1, 5, 0, 0, 2, 0, 2, 0, 1, 1, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1, 0, 0, 5},
			{1, 5, 2, 0, 0, 1, 5, 0, 0, 1, 1, 0, 0, 2, 2, 0, 0, 0, 2, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 2, 2, 1},
			{1, 1, 0, 0, 0, 1, 5, 0, 0, 1, 1, 0, 0, 2, 2, 0, 0, 2, 0, 2, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 2, 2, 1},
			{1, 1, 0, 0, 0, 1, 1, 0, 0, 5, 2, 0, 0, 1, 1, 0, 0, 0, 2, 0, 0, 1, 1, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
			{1, 1, 0, 0, 0, 1, 1, 0, 0, 5, 2, 0, 0, 1, 1, 0, 0, 2, 0, 2, 0, 1, 1, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
			{1, 1, 0, 0, 0, 1, 5, 0, 0, 1, 2, 0, 0, 1, 1, 0, 0, 0, 2, 0, 0, 1, 1, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 2, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1},
			{1, 2, 0, 2, 0, 0, 0, 0, 0, 1, 1, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 6, 6, 6, 6, 1, 6, 6, 6, 6, 1, 6, 6, 6, 6, 1, 1, 1, 1, 1},
			{1, 6, 1, 3, 1, 1, 1, 1, 1, 1, 1, 3, 3, 1, 1, 1, 1, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
	MovingPlatform[] mp5 = {new MovingPlatformH(new PVector(8*30, 10*30), 14*30, 8*30, 1),
									new MovingPlatformV(new PVector(1*30, 14*30), 10*30, 14*30, 1),
									new MovingPlatformV(new PVector(18*30, 14*30), 5*30, 14*30, 1),
									new MovingPlatformH(new PVector(6*30, 5*30), 15*30, 6*30, 2)};
	GameObject[] ego6 = {new SkewerHR(new PVector(0, 30*5), 20, 30*6),
			new SkewerHL(new PVector(6*30, 7*30), 20, 30*6),
			new SkewerVD(new PVector(7*30, 0), 40, 11*30),
			new SkewerVD(new PVector(11*30, 0), 40, 11*30),
			new SkewerHL(new PVector(22*30, 12*30), 20, 6*30),
			new SkewerHR(new PVector(14*30, 9*30+15), 20, 6*30),
			new SkewerHL(new PVector(22*30, 7*30), 20, 6*30),
			new SkewerHL(new PVector(22*30, 4*30+15), 20, 6*30),
			new Lava(5*30 + 15, 14*30),
			new SkewerVD(new PVector(25*30, 8*30), 20, 4*30),
			new SkewerVD(new PVector(27*30, 8*30), 20, 4*30),
			new SkewerVD(new PVector(30*30, 8*30), 20, 4*30),
			new SkewerVD(new PVector(32*30, 8*30), 20, 4*30),
			new SkewerVD(new PVector(35*30, 8*30), 20, 4*30),
			new SkewerVD(new PVector(37*30, 8*30), 20, 4*30)};
	MovingPlatform[] mp6 = {new MovingPlatformH(new PVector(23*30, 4*30), 43*30, 23*30, 2)};
	char[][][] tileMaps = {tileMap1, tileMap2, tileMap3, tileMap4, tileMap5, tileMap6};
	MovingPlatform[][] movingPlatforms = {null, null, null, null, mp5, mp6};
	GameObject[][] extraGameObjects = {null, null, null, null, null, ego6};
	
	
	public static void main (String[] args) {
		GameWindow gameWindow = new GameWindow();
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gameWindow.makeGameWindow(); 
            }
        });
	}
	
	public void makeScreen (int num) {
		gameFrame.getContentPane().removeAll();
		switch(num) {
			case HELP_SCREEN:
				gameFrame.getContentPane().add(new HelpScreen(this));
				gameFrame.removeKeyListener(levelMenuListener);
				gameFrame.addKeyListener(helpScreenListener);
				break;
			case TITLE_SCREEN:
				gameFrame.getContentPane().add(new TitleScreen(this));
				gameFrame.removeKeyListener(levelMenuListener);
				gameFrame.addKeyListener(titleScreenListener);
				break;
			case LEVEL_MENU:
				gameFrame.getContentPane().add(new LevelMenu(this));
				gameFrame.removeKeyListener(titleScreenListener);
				gameFrame.removeKeyListener(gamePanelListener);
				gameFrame.addKeyListener(levelMenuListener);
				break;
			default:
				currentGamePanel = new GamePanel(new Level(tileMaps[num-1], extraGameObjects[num-1]), this, num, movingPlatforms[num-1]);
				gameFrame.getContentPane().add(currentGamePanel);
				gameFrame.removeKeyListener(levelMenuListener);
				gameFrame.addKeyListener(gamePanelListener);
				currentGamePanel.setPlayerCanMove(true);
				break;
		}
		gameFrame.revalidate();
		gameFrame.repaint();
	}
	
	private void makeGameWindow () {
		gameFrame = new JFrame("Independent Project CompSciA");
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setSize(660, 480);
		
		titleScreenListener = new TitleScreenListener();
		levelMenuListener = new LevelMenuListener();
		gamePanelListener = new GamePanelListener();
		helpScreenListener = new HelpScreenListener();
		
		leastDeaths = new int[tileMaps.length];
		for (int i = 0; i < leastDeaths.length; i++)
			leastDeaths[i] = 100;
		
		levelCompletion = new boolean[tileMaps.length];
		for (int i = 0; i < levelCompletion.length; i++)
			levelCompletion[i] = false;
		
		titleScreen = new TitleScreen(this);
		gameFrame.addKeyListener(titleScreenListener);
		gameFrame.add(titleScreen);
		gameFrame.setVisible(true);
		gameFrame.setResizable(false);
	}
	
	class TitleScreenListener implements KeyListener {
		public void keyPressed (KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				titleScreen.getTimer().cancel();
				titleScreen.getTimer().purge();
				makeScreen(LEVEL_MENU);
			}
		}
		public void keyReleased (KeyEvent e) {}
		public void keyTyped (KeyEvent e) {}
	}
	
	class LevelMenuListener implements KeyListener {
		public void keyPressed (KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				makeScreen(TITLE_SCREEN);
		}
		public void keyReleased (KeyEvent e) {}
		public void keyTyped (KeyEvent e) {}
	}
	
	class GamePanelListener implements KeyListener {
		public void keyPressed (KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
				currentGamePanel.getPlayer().startMovingLeft();
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT|| e.getKeyCode() == KeyEvent.VK_D)
				currentGamePanel.getPlayer().startMovingRight();
			else if (e.getKeyCode() == KeyEvent.VK_UP|| e.getKeyCode() == KeyEvent.VK_W)
				currentGamePanel.getPlayer().jump();
		}
		public void keyReleased (KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
				currentGamePanel.getPlayer().stopMovingLeft();
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT|| e.getKeyCode() == KeyEvent.VK_D)
				currentGamePanel.getPlayer().stopMovingRight();
		}
		public void keyTyped (KeyEvent e) {}
	}
	
	class HelpScreenListener implements KeyListener {
		public void keyPressed (KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				makeScreen(LEVEL_MENU);
		}
		public void keyReleased (KeyEvent e) {}
		public void keyTyped (KeyEvent e) {}
	}
}