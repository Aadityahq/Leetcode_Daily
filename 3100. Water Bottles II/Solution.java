class Solution {
    public int maxBottlesDrunk(int numBottles, int numExchange) {
        int totalDrunk = numBottles;
        int empty = numBottles;

        while (empty >= numExchange) {
            // Exchange empty bottles for 1 full
            empty -= numExchange;
            numExchange++; // exchange rate increases
            totalDrunk++;  // drink that bottle
            empty++;       // that bottle becomes empty
        }

        return totalDrunk;
    }
}
