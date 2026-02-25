import java.util.*;

class Solution {
    public int[] sortByBits(int[] arr) {
        
        // Convert int[] to Integer[] for custom sorting
        Integer[] temp = new Integer[arr.length];
        for(int i = 0; i < arr.length; i++) {
            temp[i] = arr[i];
        }
        
        // Custom sort
        Arrays.sort(temp, (a, b) -> {
            
            int countA = Integer.bitCount(a);
            int countB = Integer.bitCount(b);
            
            // First sort by bit count
            if(countA != countB) {
                return countA - countB;
            }
            
            // If bit count same, sort by number
            return a - b;
        });
        
        // Convert back to int[]
        for(int i = 0; i < arr.length; i++) {
            arr[i] = temp[i];
        }
        
        return arr;
    }
}