class Solution {
    public boolean stoneGameIX(int[] stones) {
        int[] cnt = new int[3];

        for (int stone : stones) {
            cnt[stone % 3]++;
        }

        return check(cnt[0], cnt[1], cnt[2])
            || check(cnt[0], cnt[2], cnt[1]);
    }

    private boolean check(int zero, int one, int two) {
        if (one == 0) {
            return false;
        }

        // Alice takes one '1'
        one--;

        int pairs = Math.min(one, two);

        // 1 + (1,2) pairs + possible extra 1
        int moves = 1 + pairs * 2;

        if (one > two) {
            moves++;
            one--;
        }

        // 0-remainder stones only change turn parity
        moves += zero;

        return moves % 2 == 1 && one != two;
    }
}