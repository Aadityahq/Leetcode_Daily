class Solution {
    public int countCommas(int n) {

        // int countComma = 0;
        // //Brute-Force approach

        // for(int i = 1000; i <=n; i++) {
        //     countComma++;
        // }

        // return countComma;
        
        if(n<1000) return 0;

        return (n-1000) + 1; // when we have to find the integer in the inclusive range [1, n],
                            // the formula would be [Right - LEft] + 1;
    }
}