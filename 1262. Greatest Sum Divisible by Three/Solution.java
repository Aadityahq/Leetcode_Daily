import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Solution {
    public int maxSumDivThree(int[] nums) {
        int total = 0;

        // Lists to store numbers with remainder 1 and 2
        List<Integer> mod1 = new ArrayList<>();
        List<Integer> mod2 = new ArrayList<>();

        // Calculate total sum and categorize by remainders
        for (int n : nums) {
            total += n;

            if (n % 3 == 1) mod1.add(n);
            else if (n % 3 == 2) mod2.add(n);
        }

        // Sort so smallest values come first
        Collections.sort(mod1);
        Collections.sort(mod2);

        // If the total is already divisible by 3
        if (total % 3 == 0) return total;

        int remainder = total % 3;

        int optionA = Integer.MAX_VALUE; 
        int optionB = Integer.MAX_VALUE;

        if (remainder == 1) {
            // Option A: remove smallest mod1
            if (!mod1.isEmpty()) optionA = mod1.get(0);

            // Option B: remove two smallest mod2
            if (mod2.size() >= 2) optionB = mod2.get(0) + mod2.get(1);
        } else { // remainder == 2
            // Option A: remove smallest mod2
            if (!mod2.isEmpty()) optionA = mod2.get(0);

            // Option B: remove two smallest mod1
            if (mod1.size() >= 2) optionB = mod1.get(0) + mod1.get(1);
        }

        // Subtract minimum possible amount
        return total - Math.min(optionA, optionB);
    }
}
