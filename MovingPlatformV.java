public class MovingPlatformV extends MovingPlatform {
	private boolean goingD;
	private int UBound, DBound;
	
	public MovingPlatformV (PVector pos, int UBound, int DBound, double speed) {
		super(pos, speed);
		this.UBound = UBound;
		this.DBound = DBound;
		goingD = false;
	}
	
	public void update () {
		if (getPos().getyComp() + 10 == DBound)
			goingD = false;
		else if (getPos().getyComp() == UBound)
			goingD = true;
		
		if (goingD)
			setPos(PVector.add(getPos(), new PVector(0, getSpeed())));
		else
			setPos(PVector.add(getPos(), new PVector(0, -1*getSpeed())));
		
		if (getIsOnTop()) 
			if(goingD)
				getPlayer().getPlayerVel().add(new PVector(0, getSpeed()));
	}
}