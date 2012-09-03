package cnf;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		RubixEncoding1 rubix = new RubixEncoding1();
		//RubixCube rc = RubixCube.createRandom(25, true);
		//RubixCube rc = RubixCube.solved();
		final Iterator i = new Iterator();
		final Iterator j = new Iterator();
		DIMACSEncoder dimacs = new DIMACSEncoder(rubix.initialState(RubixCube.solved()).and(rubix.solvedState(1)).and(rubix.atLeastOneSolvedState(1)));
		System.out.println(dimacs.getEncoding());
	}
	
	public static void testFaceTurns(RubixCube rc) {
		System.out.println("Test front: " + rc.equals(rc.turnFront().turnFrontRev()));
		System.out.println("Test back: " + rc.equals(rc.turnBack().turnBackRev()));
		System.out.println("Test left: " + rc.equals(rc.turnLeft().turnLeftRev()));
		System.out.println("Test right: " + rc.equals(rc.turnRight().turnRightRev()));
		System.out.println("Test top: " + rc.equals(rc.turnTop().turnTopRev()));
		System.out.println("Test down: " + rc.equals(rc.turnDown().turnDownRev()));
		
		RubixCube rc_copy = new RubixCube(rc);
		for(int i=0;i<3;i++) {
			rc_copy = rc_copy.turnLeftRev().turnTopRev().turnRight().turnTop();
			rc_copy = rc_copy.turnLeft().turnTopRev().turnRightRev().turnTop();
		}
		System.out.println("Test sune front: " + rc.equals(rc_copy));
		for(int i=0;i<3;i++) {
			rc_copy = rc_copy.turnFrontRev().turnDownRev().turnBack().turnDown();
			rc_copy = rc_copy.turnFront().turnDownRev().turnBackRev().turnDown();
		}

		System.out.println("Test sune down: " + rc.equals(rc_copy));		
	}
}
