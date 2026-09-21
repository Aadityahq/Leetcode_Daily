class Solution {
    public int reverseDegree(String s) {
    int n = s.length();
    int sum = 0;
    for(int i = 0; i < n; i++) {
        char ch = s.charAt(i); 

        //reverse position of alphabet =>  26 - currentPosition + 1;
        int reversePosition = 26 - ( ch - 'a');

        int position = i + 1;
        // sum = sum + reversePosition * currentPosition;
        sum += reversePosition * position;
    }   
    return sum;         
    }
}