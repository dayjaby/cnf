package rubix;

import java.io.FileInputStream;

import sat.DIMACSDecoder;
import sat.Index;
import sat.Variable;

public class RubixDecoder extends RubixPermutation {

	static String convertStreamToString(java.io.InputStream is) {
		try {
			return new java.util.Scanner(is).useDelimiter("\\A").next();
		} catch (java.util.NoSuchElementException e) {
			return "";
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		int states = Integer.parseInt(args[0]);
		FileInputStream map = new FileInputStream("rubix.map");
		FileInputStream out = new FileInputStream("rubix.out");
		DIMACSDecoder decoder = new DIMACSDecoder();
		decoder.decodeMapping(convertStreamToString(map));
		decoder.decodeSATOutput(convertStreamToString(out));
		Index solvedState = new Index();
		Index s = new Index(1);
		Variable stateSolved = new Variable("s", s);
		for (; s.get() <= states; s.incr()) {
			if (decoder.getValue(stateSolved))
				solvedState = new Index(s);
		}
		System.out.println("The solved state is " + solvedState.get());

		RubixCube cube = RubixCube.solved();
		// Read rubix cube from the boolean values
		for (s = new Index(1); s.get() <= solvedState.get(); s.incr()) {
			//System.out.println("~~~~~~  State " + s.get() + ":");
			for (Index i = new Index(0); i.get() < 6; i.incr()) {
				for(Index t = new Index(0); t.get()<3; t.incr()) {
					if(decoder.getValue(new Variable("t",s,i,t))) {
						switch(i.get()) {
						case 0: System.out.print("F"); break;
						case 1: System.out.print("B"); break;
						case 2: System.out.print("L"); break;
						case 3: System.out.print("R"); break;
						case 4: System.out.print("U"); break;
						case 5: System.out.print("D"); break;
						}
						switch(t.get()) {
						case 1: System.out.print("2"); break;
						case 2: System.out.print("'"); break;
						}
					}
				}
				for (Index j = new Index(0); j.get() < 9; j.incr()) {
					int color = 0;
					for (Index k = new Index(0); k.get() < 3; k.incr()) {
						Variable colorBit = new Variable("c", i, j,
								s, k);
						if (decoder.getValue(colorBit)) {
							color += 1 << k.get();
						}
					}
					cube.setColor(i.get(), j.get(), color);
				}
			}
			//cube.print();
		}
		System.out.println();
	}

}
