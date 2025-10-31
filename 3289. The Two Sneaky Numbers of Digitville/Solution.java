import java.util.*;

class Solution {
    public int[] getSneakyNumbers(int[] nums) {
        int n = nums.length - 2;  // since two extra numbers are added
        int[] count = new int[n];
        List<Integer> sneaky = new ArrayList<>();

        for (int num : nums) {
            count[num]++;
            if (count[num] == 2) {
                sneaky.add(num);
            }
        }

        return new int[]{sneaky.get(0), sneaky.get(1)};
    }
}
