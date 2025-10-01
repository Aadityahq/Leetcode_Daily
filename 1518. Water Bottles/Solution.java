class Solution {
    public int numWaterBottles(int numBottles, int numExchange) {
        int total = numBottles;
        int empty = numBottles;

        while (empty >= numExchange) {
            int newBottles = empty / numExchange; // how many new full bottles
            total += newBottles;
            empty = (empty % numExchange) + newBottles; // leftovers + new empties
        }

        return total;
    }
}
