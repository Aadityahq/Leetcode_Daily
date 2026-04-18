class Solution {
    
    // Function to reverse the number
    private int reverse(int n) {
        int rev = 0;
        
        while (n > 0) {
            int digit = n % 10;   // get last digit
            rev = rev * 10 + digit; // build reversed number
            n = n / 10;          // remove last digit
        }
        
        return rev;
    }
    
    public int mirrorDistance(int n) {
        int reversed = reverse(n);
        return Math.abs(n - reversed);
    }
}