import java.util.*;

class Solution {
    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private long lcm(long a, long b) {
        return a / gcd(a, b) * b;  // prevent overflow
    }

    public List<Integer> replaceNonCoprimes(int[] nums) {
        Stack<Long> stack = new Stack<>();

        for (int num : nums) {
            long cur = num;
            // Keep merging while gcd > 1
            while (!stack.isEmpty()) {
                long top = stack.peek();
                long g = gcd(top, cur);
                if (g == 1) break;   // coprime → stop merging
                stack.pop();
                cur = lcm(top, cur); // replace with lcm
            }
            stack.push(cur);
        }

        // Convert stack back to list of integers
        List<Integer> result = new ArrayList<>();
        for (long val : stack) {
            result.add((int) val);
        }
        return result;
    }
}
