
class Solution {
    public int minInsertions(String s) {
        int insertions = 0;
        int open = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == '(') {
                open++;
            } else {
                // Check whether another ')' follows
                if (i + 1 < s.length() && s.charAt(i + 1) == ')') {
                    // We have a pair of closing brackets
                    i++;
                } else {
                    // Insert one ')' to complete the pair
                    insertions++;
                }

                // Match the closing pair with an opening '('
                if (open > 0) {
                    open--;
                } else {
                    // No opening '(' exists, so insert one
                    insertions++;
                }
            }
        }

        // Each remaining '(' needs two closing brackets
        insertions += open * 2;

        return insertions;
    }
}
