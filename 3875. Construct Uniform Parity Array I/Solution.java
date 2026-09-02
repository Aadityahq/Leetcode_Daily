public class Solution {
    public static boolean uniformParity(int[] nums1) {
        return true;
        
    }
}


// class Solution {
//     public boolean uniformArray(int[] nums1) {
//         int n = nums1.length;
//         int[] nums2 = new int[n];
//         int oddIndex = -1;
//         for(int i = 0; i < nums1.length; i++) {
//             if(nums1[i] % 2 != 0) {
//                 oddIndex = i;
//                 break;
//             }

//         if(oddIndex == -1) 
//             return true;
//         }

//         for(int i = 0; i < n; i++) {
//             if(nums1[i] % 2 == 0) 
//                 nums2[i] = nums1[i] - nums1[oddIndex];
//             else 
//                 nums2[i] = nums1[i];
//         }
//         return true;
        
//     }
// }