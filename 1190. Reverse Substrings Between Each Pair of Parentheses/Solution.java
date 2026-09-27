import java.util.*;

class Solution {
    public String reverseParentheses(String s) {

        Stack<StringBuilder> stack = new Stack<>();

        // Start with the outermost level
        stack.push(new StringBuilder());

        for (char ch : s.toCharArray()) {

            // Start a new level
            if (ch == '(') {
                stack.push(new StringBuilder());
            }

            // Finish current level
            else if (ch == ')') {

                StringBuilder current = stack.pop();

                current.reverse();

                stack.peek().append(current);
            }

            // Normal character
            else {
                stack.peek().append(ch);
            }
        }

        return stack.peek().toString();
    }
}