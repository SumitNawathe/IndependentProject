public abstract class Skewer extends GameObject {
	private int min, max, length, cycle;
	
	public int getMin () {
		return min;
	}
	
	public int getMax () {
		return max;
	}
	
	public int getLength () {
		return length;
	}
	
	public void setLength (int len) {
		length = len;
	}
	
	public int getCycle () {
		return cycle;
	}
	
	public void setCycle (int cycle) {
		this.cycle = cycle;
	}
	
	public void collision (Player player) {
		if (hasCollided(player))
			player.getGamePanel().killPlayer();
	}
	
	public Skewer (PVector pos, int min, int max) {
		super(pos);
		this.min = min;
		this.max = max;
		length = min;
		cycle = 0;
	}
	
	public abstract boolean hasCollided (Player player);
	
	public static boolean isInRange (double value, double low, double high) {
		if (value > low && value < high)
			return true;
		return false;
	}
	
	public void updateLength () {
		if (getCycle() < 80)
			setLength(getMin());
		else if (getCycle() < 80 + (getMax()-getMin())/5)
			setLength(getMin() + 5*(getCycle() - 80));
		else if (getCycle() < 160 + (getMax()-getMin())/5)
			setLength(getMax());
		else if (getCycle() < 160 + 2*(getMax()-getMin())/5)
			setLength(getMax() - 5*(getCycle()-160-(getMax()-getMin())/5));
		else
			setCycle(0);
		setCycle(getCycle() + 1);
	}
}