import java.util.*;

class Solution {
    public long maximumHappinessSum(int[] happiness, int k) {
        // Sort in ascending order
        Arrays.sort(happiness);

        long sum = 0;
        int n = happiness.length;
        int decrease = 0;

        // Pick from the end (largest values)
        for (int i = n - 1; i >= 0 && k > 0; i--) {
            int effectiveHappiness = happiness[i] - decrease;
            if (effectiveHappiness <= 0) break;

            sum += effectiveHappiness;
            decrease++;
            k--;
        }

        return sum;
    }
}
