import java.util.*;

class Solution {
    public int maxFrequency(int[] nums, int k, int numOperations) {
        final int N = 100002; // 1e5 + 2
        int[] freq = new int[N];
        int[] sweep = new int[N];
        int mm = N, MM = 0;

        for (int x : nums) {
            freq[x]++;
            int x0 = Math.max(1, x - k);
            int xN = Math.min(x + k + 1, N - 1);
            sweep[x0]++;
            sweep[xN]--;
            mm = Math.min(mm, x0);
            MM = Math.max(MM, xN);
        }

        int ans = 0, cnt = 0;
        for (int x = mm; x <= MM; x++) {
            cnt += sweep[x]; // prefix sum - how many ranges include x
            ans = Math.max(ans, freq[x] + Math.min(cnt - freq[x], numOperations));
        }

        return ans;
    }
}
