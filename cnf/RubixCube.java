package cnf;

import java.util.Random;

public class RubixCube {

	int[][] colors = new int[6][9];

	public RubixCube(RubixCube rc) {
		this(rc.colors);
	}

	public RubixCube(int[][] colors) {
		for (int i = 0; i < 6; i++) {
			System.arraycopy(colors[i], 0, this.colors[i], 0, 9);
		}
	}
	
	public int getColor(int face, int facelet) {
		return colors[face][facelet];
	}

	public static RubixCube solved() {
		return new RubixCube(new int[][] {
				new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				new int[] { 2, 2, 2, 2, 2, 2, 2, 2, 2 },
				new int[] { 3, 3, 3, 3, 3, 3, 3, 3, 3 },
				new int[] { 4, 4, 4, 4, 4, 4, 4, 4, 4 },
				new int[] { 5, 5, 5, 5, 5, 5, 5, 5, 5 },

		});
	}

	public static RubixCube createRandom(int i, boolean verbose) {
		RubixCube rc = solved();
		Random rnd = new Random();
		while (i-- > 0) {
			int n = rnd.nextInt(18);
			int nmod3 = n % 3;
			switch ((n - nmod3) / 3) {
			case 0:
				for (int x = 0; x <= nmod3; x++)
					rc = rc.turnFront();
				if (verbose)
					System.out.print("F");
				break;
			case 1:
				for (int x = 0; x <= nmod3; x++)
					rc = rc.turnBack();
				if (verbose)
					System.out.print("B");
				break;
			case 2:
				for (int x = 0; x <= nmod3; x++)
					rc = rc.turnLeft();
				if (verbose)
					System.out.print("L");
				break;
			case 3:
				for (int x = 0; x <= nmod3; x++)
					rc = rc.turnRight();
				if (verbose)
					System.out.print("R");
				break;
			case 4:
				for (int x = 0; x <= nmod3; x++)
					rc = rc.turnTop();
				if (verbose)
					System.out.print("T");
				break;
			case 5:
				for (int x = 0; x <= nmod3; x++)
					rc = rc.turnDown();
				if (verbose)
					System.out.print("D");
			}
			if (verbose) {
				if (nmod3 == 1)
					System.out.print("2");
				else if (nmod3 == 2)
					System.out.print("'");
			}
		}
		if(verbose) System.out.println("");
		return rc;
	}

	void print() {
		for (int i = 0; i < 6; i++) {
			System.out.println("Face " + i + ":");
			for (int x = 0; x < 9; x++) {
				System.out.print(colors[i][x]);
				if (x % 3 == 2)
					System.out.println("");
			}
		}
	}

	public boolean equals(RubixCube rc) {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 9; j++) {
				if (colors[i][j] != rc.colors[i][j])
					return false;
			}
		}
		return true;
	}

	public RubixCube turnFront() {
		RubixCube rc = new RubixCube(colors);
		turnFaceClockwise(rc, 0);

		rc.colors[2][2] = colors[5][0];
		rc.colors[2][5] = colors[5][1];
		rc.colors[2][8] = colors[5][2];

		rc.colors[3][0] = colors[4][6];
		rc.colors[3][3] = colors[4][7];
		rc.colors[3][6] = colors[4][8];

		rc.colors[4][6] = colors[2][8];
		rc.colors[4][7] = colors[2][5];
		rc.colors[4][8] = colors[2][2];

		rc.colors[5][0] = colors[3][6];
		rc.colors[5][1] = colors[3][3];
		rc.colors[5][2] = colors[3][0];
		return rc;
	}

	public RubixCube turnFront2() {
		return turnFront().turnFront();
	}

	public RubixCube turnFrontRev() {
		return turnFront().turnFront().turnFront();
	}

	public RubixCube turnBack() {
		RubixCube rc = new RubixCube(colors);
		turnFaceClockwise(rc, 1);

		rc.colors[2][0] = colors[4][2];
		rc.colors[2][3] = colors[4][1];
		rc.colors[2][6] = colors[4][0];

		rc.colors[3][2] = colors[5][8];
		rc.colors[3][5] = colors[5][7];
		rc.colors[3][8] = colors[5][6];

		rc.colors[4][0] = colors[3][2];
		rc.colors[4][1] = colors[3][5];
		rc.colors[4][2] = colors[3][8];

		rc.colors[5][6] = colors[2][0];
		rc.colors[5][7] = colors[2][3];
		rc.colors[5][8] = colors[2][6];
		return rc;
	}

	public RubixCube turnBack2() {
		return turnBack().turnBack();
	}

	public RubixCube turnBackRev() {
		return turnBack().turnBack().turnBack();
	}

	public RubixCube turnLeft() {
		RubixCube rc = new RubixCube(colors);
		turnFaceClockwise(rc, 2);

		rc.colors[0][0] = colors[4][0];
		rc.colors[0][3] = colors[4][3];
		rc.colors[0][6] = colors[4][6];

		rc.colors[1][8] = colors[5][0];
		rc.colors[1][5] = colors[5][3];
		rc.colors[1][2] = colors[5][6];

		rc.colors[4][0] = colors[1][8];
		rc.colors[4][3] = colors[1][5];
		rc.colors[4][6] = colors[1][2];

		rc.colors[5][0] = colors[0][0];
		rc.colors[5][3] = colors[0][3];
		rc.colors[5][6] = colors[0][6];
		return rc;
	}

	public RubixCube turnLeft2() {
		return turnLeft().turnLeft();
	}

	public RubixCube turnLeftRev() {
		return turnLeft().turnLeft().turnLeft();
	}

	public RubixCube turnRight() {
		RubixCube rc = new RubixCube(colors);
		turnFaceClockwise(rc, 3);

		rc.colors[0][2] = colors[5][2];
		rc.colors[0][5] = colors[5][5];
		rc.colors[0][8] = colors[5][8];

		rc.colors[1][0] = colors[4][8];
		rc.colors[1][3] = colors[4][5];
		rc.colors[1][6] = colors[4][2];

		rc.colors[4][2] = colors[0][2];
		rc.colors[4][5] = colors[0][5];
		rc.colors[4][8] = colors[0][8];

		rc.colors[5][2] = colors[1][6];
		rc.colors[5][5] = colors[1][3];
		rc.colors[5][8] = colors[1][0];
		return rc;
	}

	public RubixCube turnRight2() {
		return turnRight().turnRight();
	}

	public RubixCube turnRightRev() {
		return turnRight().turnRight().turnRight();
	}

	// Front, Back, Left, Right, Top, Down
	public RubixCube turnTop() {
		RubixCube rc = new RubixCube(colors);
		turnFaceClockwise(rc, 4);

		rc.colors[0][0] = colors[3][0];
		rc.colors[0][1] = colors[3][1];
		rc.colors[0][2] = colors[3][2];

		rc.colors[1][0] = colors[2][0];
		rc.colors[1][1] = colors[2][1];
		rc.colors[1][2] = colors[2][2];

		rc.colors[2][0] = colors[0][0];
		rc.colors[2][1] = colors[0][1];
		rc.colors[2][2] = colors[0][2];

		rc.colors[3][0] = colors[1][0];
		rc.colors[3][1] = colors[1][1];
		rc.colors[3][2] = colors[1][2];

		return rc;
	}

	public RubixCube turnTop2() {
		return turnTop().turnTop();
	}

	public RubixCube turnTopRev() {
		return turnTop().turnTop().turnTop();
	}

	public RubixCube turnDown() {
		RubixCube rc = new RubixCube(colors);
		turnFaceClockwise(rc, 5);

		rc.colors[0][6] = colors[2][6];
		rc.colors[0][7] = colors[2][7];
		rc.colors[0][8] = colors[2][8];

		rc.colors[1][6] = colors[3][6];
		rc.colors[1][7] = colors[3][7];
		rc.colors[1][8] = colors[3][8];

		rc.colors[2][6] = colors[1][6];
		rc.colors[2][7] = colors[1][7];
		rc.colors[2][8] = colors[1][8];

		rc.colors[3][6] = colors[0][6];
		rc.colors[3][7] = colors[0][7];
		rc.colors[3][8] = colors[0][8];

		return rc;
	}

	public RubixCube turnDown2() {
		return turnDown().turnDown();
	}

	public RubixCube turnDownRev() {
		return turnDown().turnDown().turnDown();
	}

	private void turnFaceClockwise(RubixCube rc, int i) {
		rc.colors[i][0] = colors[i][6];
		rc.colors[i][1] = colors[i][3];
		rc.colors[i][2] = colors[i][0];
		rc.colors[i][3] = colors[i][7];
		rc.colors[i][4] = colors[i][4];
		rc.colors[i][5] = colors[i][1];
		rc.colors[i][6] = colors[i][8];
		rc.colors[i][7] = colors[i][5];
		rc.colors[i][8] = colors[i][2];
	}

	private void turnFaceCounterClockwise(int i) {
		colors[i][6] = colors[i][0];
		colors[i][4] = colors[i][1];
		colors[i][0] = colors[i][2];
		colors[i][7] = colors[i][3];
		colors[i][4] = colors[i][4];
		colors[i][1] = colors[i][5];
		colors[i][8] = colors[i][6];
		colors[i][5] = colors[i][7];
		colors[i][2] = colors[i][8];
	}
}
