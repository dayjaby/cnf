package rubix;

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
	
	public void setColor(int face, int facelet,int color) {
		colors[face][facelet] = color;
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

	public RubixCube applyPermutation(RubixPermutation perm) {
		RubixCube rc = new RubixCube(colors);
		for (FaceletLocation loc : FaceletLocation.ALL) {
			FaceletLocation newLoc = perm.relocate(loc);
			rc.colors[newLoc.getFace()][newLoc.getFacelet()] = colors[loc
					.getFace()][loc.getFacelet()];
		}
		return rc;
	}

	public static RubixPermutation parseMoves(char[] seq) {
		RubixPermutation permutation = RubixPermutation.IDENTITY;
		for (int i = 0; i < seq.length;) {
			char current = seq[i];
			i++;
			if (i < seq.length && seq[i] == '\'') {
				i++;
				permutation = permutation.concat(parseMove(current).reverse());
			} else if (i < seq.length && seq[i] == '2') {
				i++;
				permutation = permutation.concat(parseMove(current).twice());
			} else {
				permutation = permutation.concat(parseMove(current));
			}
		}
		return permutation;
	}

	private static RubixPermutation parseMove(char m) {
		switch (m) {
		case 'F':
			return RubixPermutation.FRONT;
		case 'B':
			return RubixPermutation.BACK;
		case 'L':
			return RubixPermutation.LEFT;
		case 'R':
			return RubixPermutation.RIGHT;
		case 'U':
			return RubixPermutation.UP;
		case 'D':
			return RubixPermutation.DOWN;
		default:
			return RubixPermutation.IDENTITY;
		}
	}

	public static RubixPermutation getRandomPermutation(int i, boolean verbose) {
		RubixPermutation permutation = RubixPermutation.IDENTITY;
		Random rnd = new Random();
		while (i-- > 0) {
			int n = rnd.nextInt(18);
			int nmod3 = n % 3;
			RubixPermutation perm = null;
			switch ((n - nmod3) / 3) {
			case 0:
				perm = RubixPermutation.FRONT;
				if (verbose)
					System.err.print("F");
				break;
			case 1:
				perm = RubixPermutation.BACK;
				if (verbose)
					System.err.print("B");
				break;
			case 2:
				perm = RubixPermutation.LEFT;
				if (verbose)
					System.err.print("L");
				break;
			case 3:
				perm = RubixPermutation.RIGHT;
				if (verbose)
					System.err.print("R");
				break;
			case 4:
				perm = RubixPermutation.UP;
				if (verbose)
					System.err.print("U");
				break;
			case 5:
				perm = RubixPermutation.DOWN;
				if (verbose)
					System.err.print("D");
			}
			if (nmod3 == 1) {
				perm = perm.twice();
				if (verbose)
					System.err.print("2");
			} else if(nmod3 == 2) {
				perm = perm.reverse();
				if (verbose)
					System.err.print("'");
			}

			permutation = permutation.concat(perm);
		}
		if(verbose)
		System.err.println("");
		return permutation;
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

}
