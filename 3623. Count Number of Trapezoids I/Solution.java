class Solution {
    static final long MOD = 1_000_000_007L;

    public int countTrapezoids(int[][] points) {
        // Count how many points exist for each y-coordinate
        Map<Integer, Long> map = new HashMap<>();
        for (int[] p : points) {
            map.put(p[1], map.getOrDefault(p[1], 0L) + 1);
        }

        long S = 0;         // sum of H[y]
        long squareSum = 0; // sum of H[y]^2

        // Compute H[y] for each horizontal level
        for (long count : map.values()) {
            if (count >= 2) {
                long H = (count * (count - 1) / 2) % MOD;
                S = (S + H) % MOD;
                squareSum = (squareSum + (H * H) % MOD) % MOD;
            }
        }

        // Apply formula: (S^2 - sum(H[y]^2)) / 2
        long result = (S * S % MOD - squareSum + MOD) % MOD;

        // Modular inverse of 2 under MOD = (MOD + 1) / 2
        long inv2 = (MOD + 1) / 2;
        result = (result * inv2) % MOD;

        return (int) result;
    }
}
