class Solution {
    public int findGCD(int[] nums) {
        int smallest = 1001;
        int largest = 1;

        for(int i = 0; i < nums.length; i++)
        {
            if(largest < nums[i])
                largest = nums[i];
            if(smallest > nums[i])
                smallest = nums[i];
        }
        
        while(smallest != 0)
        {
          int temp = smallest;
          smallest = largest % smallest;
          largest = temp; 
        }
        return largest; 
    }
}