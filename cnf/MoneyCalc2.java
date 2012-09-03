/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package cnf;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeMap;
/**
*
* @author VOLT
*/
public class MoneyCalc2 {
        /**
         * @param args the command line arguments
         */
        private static Float coins[] = {100f, 50f, 20f, 10f, 5f, 1f, .25f, .10f, .05f, .01f};
        private static LinkedList<Float> currentCoins = new LinkedList<Float>();
        public static void main(String[] args) {
                long start = System.currentTimeMillis();
                currentCoins.addAll(Arrays.asList(coins));
                TreeMap<Float, Integer> set = new TreeMap<>();
                float due = 9300f;
                Float currVal = null;
                while ((currVal = currentCoins.peek()) != null) {
                        if (due - currVal < 0) {
                                currentCoins.removeFirst();
                        } else {
                                int last = 0;
                                Integer lastI = set.get(currVal);
                                if (lastI != null) {
                                        last = lastI;
                                }
                                last++;
                                set.put(currVal, last);
                                due -= currVal;
                                due = ((float) ((int) (due * 100))) / 100;
                        }
                }
                Object[] keys = set.keySet().toArray();
                for (Object key : keys) {
                        Float keyd = (Float) key;
                        System.out.println("$" + keyd + " x" + set.get(keyd));
                }
                System.out.println(System.currentTimeMillis() - start);
        }
}