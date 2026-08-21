class Solution {

    public long findKthSmallest(int[] coins, int k) {
        int n = coins.length;

        // We can remove redundant coins.
        // For example, if 3 is present, 6 and 9 are unnecessary
        // because every multiple of 6 and 9 is already a multiple of 3.
        Arrays.sort(coins);

        List<Integer> filtered = new ArrayList<>();

        for (int coin : coins) {
            boolean redundant = false;

            for (int previous : filtered) {
                if (coin % previous == 0) {
                    redundant = true;
                    break;
                }
            }

            if (!redundant) {
                filtered.add(coin);
            }
        }

        int[] arr = new int[filtered.size()];
        for (int i = 0; i < filtered.size(); i++) {
            arr[i] = filtered.get(i);
        }

        long low = 1;
        long high = (long) arr[0] * k;

        while (low < high) {
            long mid = low + (high - low) / 2;

            if (count(mid, arr) >= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    private long count(long x, int[] coins) {
        int n = coins.length;
        long total = 0;

        int totalMasks = 1 << n;

        for (int mask = 1; mask < totalMasks; mask++) {
            long lcm = 1;
            int bits = 0;
            boolean valid = true;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    bits++;

                    long gcd = gcd(lcm, coins[i]);

                    // Calculate LCM safely:
                    // lcm(a, b) = a / gcd(a, b) * b
                    lcm = lcm / gcd * coins[i];

                    // If LCM is greater than x,
                    // x / lcm will be 0.
                    // No need to continue calculating.
                    if (lcm > x) {
                        valid = false;
                        break;
                    }
                }
            }

            if (!valid) {
                continue;
            }

            long multiples = x / lcm;

            if (bits % 2 == 1) {
                total += multiples;
            } else {
                total -= multiples;
            }
        }

        return total;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }
}