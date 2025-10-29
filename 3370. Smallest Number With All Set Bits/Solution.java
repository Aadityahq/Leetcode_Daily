class Solution {
    public int smallestNumber(int n) {
        int x = 1;
        while (x < n) {
            x = (x << 1) | 1; // keep generating numbers like 1, 3, 7, 15, ...
        }
        return x;
    }
}
