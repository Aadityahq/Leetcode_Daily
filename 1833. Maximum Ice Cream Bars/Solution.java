class Solution {
    public int maxIceCream(int[] costs, int coins) {
        int maxCost = 0;

        // Find maximum cost
        for (int cost : costs) {
            maxCost = Math.max(maxCost, cost);
        }

        // Frequency array for counting sort
        int[] freq = new int[maxCost + 1];

        for (int cost : costs) {
            freq[cost]++;
        }

        int count = 0;

        // Buy bars from cheapest to most expensive
        for (int price = 1; price <= maxCost && coins >= price; price++) {

            if (freq[price] == 0) {
                continue;
            }

            int canBuy = Math.min(freq[price], coins / price);

            count += canBuy;
            coins -= canBuy * price;
        }

        return count;
    }
}