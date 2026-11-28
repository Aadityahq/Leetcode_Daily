import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {

        int n = items.length;
        int maxFactor = 0;
        int minPrice = Integer.MAX_VALUE;

        // Step 1: Find maximum factor and minimum price
        for (int[] item : items) {
            maxFactor = Math.max(maxFactor, item[0]);
            minPrice = Math.min(minPrice, item[1]);
        }

        // Step 2: Count frequency of each factor
        int[] freq = new int[maxFactor + 1];

        for (int[] item : items) {
            freq[item[0]]++;
        }

        // Step 3: Count items whose factors are multiples
        int[] divisible = new int[maxFactor + 1];

        for (int i = 1; i <= maxFactor; i++) {
            for (int j = i; j <= maxFactor; j += i) {
                divisible[i] += freq[j];
            }
        }

        // Step 4: Sort items by price
        Arrays.sort(items, (a, b) ->
            Integer.compare(a[1], b[1])
        );

        long remaining = budget;
        long ans = 0;

        // Step 5: Buy bonus-generating items first
        for (int[] item : items) {

            int factor = item[0];
            int price = item[1];

            // Skip items that are not profitable
            if (price >= 2L * minPrice) {
                break;
            }

            // Number of available free-item opportunities
            int gain = divisible[factor] - 1;

            if (gain == 0) {
                continue;
            }

            // Maximum bonus purchases possible
            long take = Math.min(
                (long) gain,
                remaining / price
            );

            ans += take * 2;
            remaining -= take * price;
        }

        // Step 6: Spend remaining budget on cheapest items
        ans += remaining / minPrice;

        return (int) ans;
    }
}

