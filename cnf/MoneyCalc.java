package cnf;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoneyCalc {

	public enum Money {
		Nickel(5), Dime(10), Quarter(25), One(100), Five(500), Ten(1000), Twenty(
				2000), Fifty(5000), Hundred(10000);

		Money(int value) {
			this.value = value;
		}

		int value;

		public int getValue() {
			return value;
		}
	}

	public static void main(String[] args) {
		long t1 = System.nanoTime();
		System.out.println("100:" + calculateWays(9300));
		/*
		 * for (int n = 0; n < 13; n++) { System.out.println(n + ": " +
		 * calc100(n * 100) + "<->" + calculateWays(n*100)); }
		 */
		long t2 = System.nanoTime();
		System.out.println("Execution time: " + ((t2 - t1) * 1e-6)
				+ " milliseconds");
	}

	public static long calc5(long x) {
		return (long) Math.floor(x / 5) + 1;
	}

	public static long calc10(long x) {
		long x5 = (long) Math.floor(x / 5);
		long x10 = (long) Math.floor(x / 10);
		return x5 * (x10 + 1) - x10 * x10 + 1;
	}

	public static long calc25(long x) {
		long x10 = (long) Math.floor(x / 10);
		long x25 = (long) Math.floor(x / 25);
		long x25p1 = x25 + 1;
		double v = x % 10 < 5 ? 0 : 1;
		return (long) (x10 * x10 * x25p1 - 2.5 * x10 * x25 * x25p1 + 6.25 * x25
				* x25p1 * (x25p1 + x25) / 6 + (2 + v) * x10 * x25p1
				- (2.5 + 1.25 * v) * x25 * x25p1 + (1 + v) * (x / 50 + 1) + (0.75 + 1.5 * v)
				* ((x + 25) / 50));
	}

	/* Coming soon */
	public static long calc100(long x) {
		// 1706 = 1463 + 243
		long x100 = (long) Math.floor(x / 100);
		return calc25(x) * 4;

		 
	}

	public static long calculateWays(double price) {
		long money = (long) (price * 100);
		List<Money> moneys = Arrays.asList(Money.values());

		Collections.sort(moneys, new Comparator<Money>() {

			@Override
			public int compare(Money o1, Money o2) {
				// TODO Auto-generated method stub
				return (int) Math.signum(o2.getValue() - o1.getValue());
			}
		});

		return countWays(money, 0, moneys);
	}

	static Map<Integer, Map<Long, Long>> cache = new HashMap<Integer, Map<Long, Long>>();

	private static long countWays(long money, int i, List<Money> moneys) {
		if (i == moneys.size())
			return 1;
		if (moneys.get(i).getValue() == 5 && money >= 5)
			return calc5(money);
		if (moneys.get(i).getValue() == 10 && money >= 10)
			return calc10(money);
		if (moneys.get(i).getValue() == 25 && money >= 25)
			return calc25(money);
		/*
		 * if (moneys.get(i).getValue() == 100 && money >= 100) return
		 * calc100(money);
		 */

		if (!cache.containsKey(i)) {
			cache.put(i, new HashMap<Long, Long>());
		}
		if (cache.get(i).containsKey(money))
			return cache.get(i).get(money);
		long countCurrentCoin = (long) Math.floor(money
				/ moneys.get(i).getValue());
		Long numberOfWays = (long) 0;
		for (int n = 0; n <= countCurrentCoin; n++) {
			long moneyLeft = money - n * moneys.get(i).getValue();

			numberOfWays += countWays(moneyLeft, i + 1, moneys);
		}
		cache.get(i).put(money, numberOfWays);
		return numberOfWays;
	}
}
