import java.util.*;

class Solution {

    private static final Map<Integer, Map<Integer, Integer>> FACTOR_COUNTS = new HashMap<>();

    static {
        FACTOR_COUNTS.put(0, Map.of());
        FACTOR_COUNTS.put(1, Map.of());
        FACTOR_COUNTS.put(2, Map.of(2, 1));
        FACTOR_COUNTS.put(3, Map.of(3, 1));
        FACTOR_COUNTS.put(4, Map.of(2, 2));
        FACTOR_COUNTS.put(5, Map.of(5, 1));
        FACTOR_COUNTS.put(6, Map.of(2, 1, 3, 1));
        FACTOR_COUNTS.put(7, Map.of(7, 1));
        FACTOR_COUNTS.put(8, Map.of(2, 3));
        FACTOR_COUNTS.put(9, Map.of(3, 2));
    }

    public String smallestNumber(String num, long t) {

        Pair primeInfo = getPrimeCount(t);

        if (!primeInfo.ok)
            return "-1";

        Map<Integer, Integer> need = primeInfo.map;

        Map<Integer, Integer> factorCount = getFactorCount(need);

        if (sum(factorCount) > num.length())
            return construct(factorCount);

        Map<Integer, Integer> prefix = getPrimeCount(num);

        int firstZero = num.indexOf('0');

        if (firstZero == -1) {
            firstZero = num.length();

            if (contains(prefix, need))
                return num;
        }

        for (int i = num.length() - 1; i >= 0; i--) {

            int d = num.charAt(i) - '0';

            prefix = subtract(prefix, FACTOR_COUNTS.get(d));

            int remain = num.length() - i - 1;

            if (i > firstZero)
                continue;

            for (int bigger = d + 1; bigger <= 9; bigger++) {

                Map<Integer, Integer> after =
                        getFactorCount(
                                subtract(
                                        subtract(need, prefix),
                                        FACTOR_COUNTS.get(bigger)));

                if (sum(after) <= remain) {

                    int ones = remain - sum(after);

                    return num.substring(0, i)
                            + bigger
                            + "1".repeat(ones)
                            + construct(after);
                }
            }
        }

        factorCount = getFactorCount(need);

        return "1".repeat(num.length() + 1 - sum(factorCount))
                + construct(factorCount);
    }

    static class Pair {
        Map<Integer, Integer> map;
        boolean ok;

        Pair(Map<Integer, Integer> m, boolean b) {
            map = m;
            ok = b;
        }
    }

    private Pair getPrimeCount(long t) {

        Map<Integer, Integer> cnt = new HashMap<>();

        cnt.put(2, 0);
        cnt.put(3, 0);
        cnt.put(5, 0);
        cnt.put(7, 0);

        int[] p = {2, 3, 5, 7};

        for (int x : p) {

            while (t % x == 0) {

                cnt.put(x, cnt.get(x) + 1);

                t /= x;
            }
        }

        return new Pair(cnt, t == 1);
    }

    private Map<Integer, Integer> getPrimeCount(String s) {

        Map<Integer, Integer> ans = new HashMap<>();

        ans.put(2, 0);
        ans.put(3, 0);
        ans.put(5, 0);
        ans.put(7, 0);

        for (char c : s.toCharArray()) {

            Map<Integer, Integer> m = FACTOR_COUNTS.get(c - '0');

            for (var e : m.entrySet())
                ans.put(e.getKey(), ans.get(e.getKey()) + e.getValue());
        }

        return ans;
    }

    private Map<Integer, Integer> subtract(Map<Integer, Integer> a,
                                           Map<Integer, Integer> b) {

        Map<Integer, Integer> res = new HashMap<>(a);

        for (var e : b.entrySet()) {

            int k = e.getKey();

            res.put(k, Math.max(0, res.get(k) - e.getValue()));
        }

        return res;
    }

    private boolean contains(Map<Integer, Integer> have,
                             Map<Integer, Integer> need) {

        for (int p : List.of(2, 3, 5, 7))

            if (have.get(p) < need.get(p))
                return false;

        return true;
    }

    private int sum(Map<Integer, Integer> m) {

        int s = 0;

        for (int v : m.values())
            s += v;

        return s;
    }

    private Map<Integer, Integer> getFactorCount(Map<Integer, Integer> cnt) {

        int c8 = cnt.get(2) / 3;
        int rem2 = cnt.get(2) % 3;

        int c9 = cnt.get(3) / 2;
        int rem3 = cnt.get(3) % 2;

        int c4 = rem2 / 2;
        int c2 = rem2 % 2;

        int c6 = 0;

        if (c2 == 1 && rem3 == 1) {
            c2 = 0;
            rem3 = 0;
            c6 = 1;
        }

        if (rem3 == 1 && c4 == 1) {
            c2 = 1;
            c6 = 1;
            rem3 = 0;
            c4 = 0;
        }

        Map<Integer, Integer> ans = new HashMap<>();

        ans.put(2, c2);
        ans.put(3, rem3);
        ans.put(4, c4);
        ans.put(5, cnt.get(5));
        ans.put(6, c6);
        ans.put(7, cnt.get(7));
        ans.put(8, c8);
        ans.put(9, c9);

        return ans;
    }

    private String construct(Map<Integer, Integer> m) {

        StringBuilder sb = new StringBuilder();

        for (int d = 2; d <= 9; d++) {

            int cnt = m.getOrDefault(d, 0);

            while (cnt-- > 0)
                sb.append(d);
        }

        return sb.toString();
    }
}