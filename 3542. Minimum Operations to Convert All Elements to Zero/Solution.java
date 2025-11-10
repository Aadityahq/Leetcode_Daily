import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int minOperations(int[] nums) {
        int ans = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(0); // sentinel

        for (int num : nums) {
            // remove values greater than current, they can't be extended past this point
            while (!stack.isEmpty() && stack.peek() > num) {
                stack.pop();
            }
            // if current is greater than stack top, we need a new operation (a new "layer")
            if (stack.isEmpty() || stack.peek() < num) {
                ans++;
                stack.push(num);
            }
            // if stack.peek() == num: nothing to do (same layer)
        }

        return ans;
    }
}
