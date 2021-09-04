public class MovingPlatformH extends MovingPlatform {
	private boolean goingR;
	private int RBound, LBound;
	
	public MovingPlatformH (PVector pos, int RBound, int LBound, double speed) {
		super(pos, speed);
		this.RBound = RBound;
		this.LBound = LBound;
		goingR = true;
	}
	
	public void update () {
		if (getPos().getxComp() + 90 >= RBound)
			goingR = false;
		else if (getPos().getxComp() <= LBound)
			goingR = true;
		
		if (goingR)
			setPos(PVector.add(getPos(), new PVector(getSpeed(), 0)));
		else
			setPos(PVector.add(getPos(), new PVector(-1*getSpeed(), 0)));
	}
	
	public void moveLeft () {
		super.moveLeft();
		RBound -= 5;
		LBound -= 5;
	}
	
	public void moveRight () {
		super.moveRight();
		RBound += 5;
		LBound += 5;
	}
}