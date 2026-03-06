class Solution {
    public boolean checkOnesSegment(String s) {
        boolean zeroSeen = false;

        for (char c : s.toCharArray()) {
            if (c == '0') {
                zeroSeen = true;
            } 
            else if (zeroSeen) {
                return false;
            }
        }

        return true;
    }
}