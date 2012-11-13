package rubix;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import sat.DIMACSEncoder;
import sat.Expression;
import sat.StateBasedSATProblem;

public class RubixToDIMACS extends RubixPermutation {

	private static class Settings {
		public void parseSetting(String arg) {
			if (arg.equals("-h") || arg.equals("--help")) {
				System.out.println("Usage: RubixToDIMACS -r=RANDOMS [-verbose] [n=MAXMOVES] [e=ENCODING] [OUTPUT] [MAPPING]");
				System.out.println("   or: RubixToDIMACS -m=SEQUENCE [-verbose] [n=MAXMOVES] [e=ENCODING] [OUTPUT] [MAPPING]");
				System.out.println("   or: RubixToDIMACS [OUTPUT]");
				System.out
						.println("\nWrite the DIMACS encoding of a rubix cube,");
				System.out
						.println("specified either by a sequence of moves or");
				System.out
						.println("an amount of random moves, to OUTPUT or stdout.");
				System.out.println("\nThe arguments are:");
				System.out
						.println("-r=RANDOMS \t\tset the amount of random moves");
				System.out
						.println("-m=SEQUENCE\t\ta sequence of characters describing rubix cube moves:");
				System.out
						.println("           \t\tF = Clockwise Front Turn, F2 = Double Front Turn,");
				System.out
						.println("           \t\tF' = Counter-clockwise Front Turn");
				System.out
						.println("           \t\tSimilar: B(ack), L(eft), R(ight), U(p) and D(own)");
				System.out
						.println("           \t\tExample: The `sune` move would have the sequence:");
				System.out.println("           \t\t      -m=L'U'RULU'R'U");
				System.out.println("-verbose   \t\tIf set, print the move sequence to stderr");
				System.out.println("-n=MAXMOVES\t\tset the limit of moves to MAXMOVES");
				System.out.println("           \t\tThe default value is 50");
				System.out.println("-e=ENCODING\t\tDefines the class file that is used to encode the cube");
				System.out.println("           \t\tThe default is rubix.RubixSAT");
				System.out.println("OUTPUT     \t\tSpecifies the output file");
				System.out.println("MAPPING    \t\tSpecifies the mapping file");
				System.exit(0);
			} else if (arg.startsWith("-r=") && moves.equals("")) {
				random = Integer.parseInt(arg.substring(3));
			} else if (arg.startsWith("-m=") && random == -1) {
				moves = arg.substring(3);
			} else if (arg.startsWith("-e=")) {
				encoding = arg.substring(3);
			} else if (arg.charAt(0) != '-' && output.length()==0) {
				output = arg;
			} else if (arg.charAt(0) != '-') {
				mapping = arg;
			} else if (arg.startsWith("-n=")) {
				max = Integer.parseInt(arg.substring(3));
			} else if (arg.equals("-verbose")) {
				verbose = true;
			} else {
				System.err.println("Illegal argument: " + arg);
				System.exit(0);
			}
		}
		public boolean verbose = false;
		public int max = 50;
		public int random = -1;
		public String moves = "";
		public String output = "";
		public String mapping = "";
		public String encoding = "rubix.RubixSAT";
	}

	public static void main(String[] args) throws Exception {
		Settings settings = new Settings();
		for (String arg : args) {
			settings.parseSetting(arg);
		}
		
		
		OutputStream outp;
		if (settings.output.length() == 0) {
			outp = System.out;
		} else {
			File file = new File(settings.output);
			File dir = file.getParentFile();
			if (dir != null && !dir.exists())
				dir.mkdirs();
			outp = new FileOutputStream(file);
		}

		/** Create the rubix cube **/
		RubixCube rubixCube = RubixCube.solved();
		if (settings.random >= 0) {
			rubixCube = rubixCube.applyPermutation(RubixCube.getRandomPermutation(settings.random, settings.verbose));
		} else if (settings.moves.length() > 0) {
			rubixCube = rubixCube.applyPermutation(RubixCube.parseMoves(settings.moves.toCharArray()));
			System.err.println(settings.moves);
		} else {
			rubixCube = rubixCube.applyPermutation(RubixCube.getRandomPermutation(15, settings.verbose));
		}
		StateBasedSATProblem<RubixCube> rubixSAT = (StateBasedSATProblem<RubixCube>) Class.forName(settings.encoding).getConstructor().newInstance();
		Expression rubixExpr = rubixSAT.getExpression(rubixCube,settings.max+1);
		System.err.println("Cube expression calculated");
		DIMACSEncoder dimacs = new DIMACSEncoder(rubixExpr);
		dimacs.writeDIMACSTo(outp);
		if(settings.mapping.length()>0) {
			File file = new File(settings.mapping);
			File dir = file.getParentFile();
			if (dir != null && !dir.exists())	
				dir.mkdirs();
			dimacs.writeMappingTo(new PrintStream(file));
		}
	}

}
