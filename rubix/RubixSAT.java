package rubix;

import sat.Clause;
import sat.Expression;
import sat.Index;
import sat.Iterator;
import sat.Literal;
import sat.Loops;
import sat.StateBasedSATProblem;
import sat.Value;
import sat.Variable;

public class RubixSAT extends StateBasedSATProblem<RubixCube> {

	protected Expression oneSolvedState(final int states) {
		return exactlyOne(new Variable("s"), 1, states);
	}

	protected Expression removeSymmetries(int states) {
		final Iterator s = new Iterator();
		final Iterator i = new Iterator();
		return Loops.and(1, states, s, new Value<Expression>() {
			public Expression get() {
				return new Expression(new Clause(new Literal(new Variable("t",
						s, new Index(0)), true), new Literal(new Variable("t",
						s.plus(1), new Index(1)), true)), new Clause(
						new Literal(new Variable("t", s, new Index(2)), true),
						new Literal(new Variable("t", s.plus(1), new Index(3)),
								true)), new Clause(new Literal(new Variable(
						"t", s, new Index(4)), true), new Literal(new Variable(
						"t", s.plus(1), new Index(5)), true))).and(Loops.and(0,
						5, i, new Value<Expression>() {
							public Expression get() {
								return new Expression(new Clause(new Literal(
										new Variable("t", s, i), true),
										new Literal(new Variable("t",
												s.plus(1), i), true)));
							}
						}));
			}
		});
	}

	protected Expression initialState(final RubixCube rubix) {
		final Iterator i = new Iterator();
		final Iterator j = new Iterator();
		return Loops.and(0, 5, i, new Value<Expression>() {
			public Expression get() {
				return Loops.and(0, 8, j, new Value<Expression>() {
					public Expression get() {
						return color(i, j, new Index(1),
								new Index(rubix.getColor(i.get(), j.get())));
					}
				});
			}
		});
	}

	/** Returns an expression that defines a state as solved **/
	public Expression solvedState(final int states) {
		final Iterator i = new Iterator();
		final Iterator j = new Iterator();
		final Iterator s = new Iterator();
		return Loops.and(1, states, s, new Value<Expression>() {
			public Expression get() {
				return Loops.and(0, 5, i, new Value<Expression>() {
					public Expression get() {
						return Loops.and(0, 8, j, new Value<Expression>() {
							public Expression get() {
								return colorCorrect(i, j, s).or(
										new Clause(new Literal(new Variable(
												"s", s), true)));
							}
						});
					}
				});
			}
		});
	}

	protected Expression colorBit(Index face, Index facelet, Index state,
			final Index cface, final Index cfacelet, final Index cstate,
			Index bit) {
		return new Expression(new Clause(new Literal(new Variable("c", face,
				facelet, state, bit)), new Literal(new Variable("c", cface,
				cfacelet, cstate, bit), true)), new Clause(new Literal(
				new Variable("c", face, facelet, state, bit), true),
				new Literal(new Variable("c", cface, cfacelet, cstate, bit))));
	}
	
	protected Expression colorsValid(final int states) {
		final Iterator i = new Iterator();
		final Iterator j = new Iterator();
		final Iterator s = new Iterator();
		return Loops.and(1, states, s, new Value<Expression>() {
			public Expression get() {
				return Loops.and(0, 5, i, new Value<Expression>() {
					public Expression get() {
						return Loops.and(0, 8, j, new Value<Expression>() {
							public Expression get() {
								return color(i,j,s,new Index(6)).negate().
										and(color(i,j,s,new Index(7)).negate());
							}
						});
					}
				});
			}
		});
	}

	// color equals another color
	protected Expression color(final Index face, final Index facelet,
			final Index state, final Index cface, final Index cfacelet,
			final Index cstate) {
		final Iterator k = new Iterator();
		return Loops.and(0, 2, k, new Value<Expression>() {
			public Expression get() {
				return colorBit(face, facelet, state, cface, cfacelet, cstate,
						k);
			}
		});
	}

	protected Expression color(final Index face, final Index facelet,
			final Index state, final Index color) {
		final Iterator k = new Iterator();
		return Loops.and(0, 2, k, new Value<Expression>() {
			public Expression get() {
				boolean isColorBitSet = (color.get() >> k.get() & 1) == 1;

				return new Expression(new Clause(new Literal(new Variable("c",
						face, facelet, state, k), !isColorBitSet)));
			}
		});
	}

	protected Expression colorCorrect(Index face, Index facelet, Index state) {
		return color(face, facelet, state, face);
	}

	protected Expression faceletMove(final RubixPermutation permutation,
			final Index i, final Index j, final Index f, final Index s) {
		FaceletLocation loc = FaceletLocation.get(f.get(), j.get());
		FaceletLocation nloc = permutation.relocate(loc);
		if (nloc.equals(loc)) {
			return color(new Index(nloc.getFace()),
					new Index(nloc.getFacelet()), s.plus(1),
					new Index(loc.getFace()), new Index(loc.getFacelet()), s)
					.or(new Clause(new Literal(new Variable("t", s, i), true)));
		} else {
			final Iterator t = new Iterator();
			return Loops.and(0, 2, t, new Value<Expression>() {
				public Expression get() {
					RubixPermutation perm = permutation;
					switch (t.get()) {
					case 1:
						perm = perm.twice();
						break;
					case 2:
						perm = perm.reverse();
						break;
					}
					FaceletLocation loc = FaceletLocation.get(f.get(), j.get());
					FaceletLocation nloc = perm.relocate(loc);

					return color(new Index(nloc.getFace()),
							new Index(nloc.getFacelet()), s.plus(1),
							new Index(loc.getFace()),
							new Index(loc.getFacelet()), s).or(
							new Clause(new Literal(new Variable("t", s, i, t),
									true)));
				}
			});
		}
	}

	protected Expression moves(final int states) {
		final Iterator s = new Iterator();
		final Iterator i = new Iterator();
		final Iterator j = new Iterator();
		final Iterator f = new Iterator();

		return colorsValid(states).and(removeSymmetries(states)).and(
				Loops.and(0, 5, i, new Value<Expression>() {
					public Expression get() {
						RubixPermutation perm = RubixPermutation.IDENTITY;

						switch (i.get()) {
						case 0:
							perm = RubixPermutation.FRONT;
							break;
						case 1:
							perm = RubixPermutation.BACK;
							break;
						case 2:
							perm = RubixPermutation.LEFT;
							break;
						case 3:
							perm = RubixPermutation.RIGHT;
							break;
						case 4:
							perm = RubixPermutation.UP;
							break;
						case 5:
							perm = RubixPermutation.DOWN;
							break;
						}
						final RubixPermutation permutation = perm;
						return Loops.and(1, states, s, new Value<Expression>() {
							public Expression get() {

								return Loops.and(0, 8, j,
										new Value<Expression>() {
											public Expression get() {
												return Loops
														.and(0,
																5,
																f,
																new Value<Expression>() {
																	public Expression get() {
																		return faceletMove(
																				permutation,
																				i,
																				j,
																				f,
																				s);
																	}
																});
											}
										});
							}
						});
					}
				}));
	}

	protected Expression exactlyOneMoveInEachState(int states) {
		final Iterator s = new Iterator();
		final Iterator i = new Iterator();
		return Loops.and(1, states - 1, s, new Value<Expression>() {
			public Expression get() {
				return exactlyOne(new Variable("t", s), 0, 5).and(
						Loops.and(0, 5, i, new Value<Expression>() {
							public Expression get() {
								return faceTurn(i, s);
							}
						}));
			}
		});
	}

	// e.g. -dk V exactly-one(Dk, Dk', Dk2)
	protected Expression faceTurn(Index face, Index state) {
		return exactlyOne(new Variable("t", state, face), 0, 2).or(
				new Clause(new Literal(new Variable("t", state, face), true)));
	}

	int uniques = 0;

	protected String getUniqueName() {
		return "UNIQUENAME" + (uniques++);
	}
}
