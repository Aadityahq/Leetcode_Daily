public class Solution {
    
}
class Solution {
    public boolean sumGame(String num) {
        int n = num.length();
        int half = n / 2;

        int leftSum = 0;
        int rightSum = 0;

        int leftQuestion = 0;
        int rightQuestion = 0;

        for (int i = 0; i < n; i++) {
            char ch = num.charAt(i);

            if (i < half) {
                if (ch == '?') {
                    leftQuestion++;
                } else {
                    leftSum += ch - '0';
                }
            } else {
                if (ch == '?') {
                    rightQuestion++;
                } else {
                    rightSum += ch - '0';
                }
            }
        }

        // Alice gets one extra move
        if ((leftQuestion + rightQuestion) % 2 == 1) {
            return true;
        }

        // Bob wins only when he can perfectly balance both halves
        return leftSum - rightSum !=
                (rightQuestion - leftQuestion) / 2 * 9;
    }
}