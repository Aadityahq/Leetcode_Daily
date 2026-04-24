class Solution {
    public int furthestDistanceFromOrigin(String moves) {

        int count = 0;
        int countDash = 0;
        int result = 0;

        for(int i=0; i<moves.length(); i++) {
            char ch = moves.charAt(i);

            if(ch == 'L') {
                count--;
            } else if(ch == 'R') {
                count++;
            }
            else {
                countDash++;
            }
        }

        if(count == 0) {
             result = countDash;
        }
        else if(count < 0) {
            count = Math.abs(count);
            result = count + countDash;
        }
        else if(count > 0) {
            result = count + countDash;
        }
        return result;
    }
}