class Solution {
    public String removeOuterParentheses(String s) {
        
        StringBuilder result = new StringBuilder();
        int balance = 0;

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                // If balance is greater than 0,
                // this '(' is not the outermost '('
                if (balance > 0) {
                    result.append(ch);
                }

                balance++;
            } 
            else {
                balance--;

                // If balance is greater than 0,
                // this ')' is not the outermost ')'
                if (balance > 0) {
                    result.append(ch);
                }
            }
        }

        return result.toString();
    }
}