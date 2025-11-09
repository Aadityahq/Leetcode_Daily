class Solution {
    public int countOperations(int num1, int num2) {
        int operations = 0;
        
        while (num1 != 0 && num2 != 0) {
            if (num1 >= num2) {
                operations += num1 / num2; // count how many times num2 fits in num1
                num1 %= num2;              // update num1
            } else {
                operations += num2 / num1; // count how many times num1 fits in num2
                num2 %= num1;              // update num2
            }
        }
        
        return operations;
    }
}
