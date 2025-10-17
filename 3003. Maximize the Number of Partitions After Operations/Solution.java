class Solution {

    static class Data {
        int segCnt;
        int hasC; // bitmask for 26 lowercase letters
        int cnt;

        Data(int segCnt, int hasC, int cnt) {
            this.segCnt = segCnt;
            this.hasC = hasC;
            this.cnt = cnt;
        }
    }

    public int maxPartitionsAfterOperations(String s, int k) {
        int n = s.length();
        Data[] pref = new Data[n];
        Data[] suff = new Data[n];

        int seg = 0, cnt = 0;
        int mask = 0;

        // prefix computation
        for (int i = 0; i < n - 1; i++) {
            int idx = s.charAt(i) - 'a';
            if ((mask & (1 << idx)) == 0) {
                cnt++;
                if (cnt > k) {
                    seg++;
                    cnt = 1;
                    mask = 0;
                }
                mask |= (1 << idx);
            }
            pref[i + 1] = new Data(seg, mask, cnt);
        }

        // suffix computation
        seg = cnt = 0;
        mask = 0;
        for (int i = n - 1; i > 0; i--) {
            int idx = s.charAt(i) - 'a';
            if ((mask & (1 << idx)) == 0) {
                cnt++;
                if (cnt > k) {
                    seg++;
                    cnt = 1;
                    mask = 0;
                }
                mask |= (1 << idx);
            }
            suff[i - 1] = new Data(seg, mask, cnt);
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            Data left = (i < n ? pref[i] : null);
            Data right = (i < n ? suff[i] : null);

            int segL = (left != null ? left.segCnt : 0);
            int Lmask = (left != null ? left.hasC : 0);
            int Lcnt = (left != null ? left.cnt : 0);

            int segR = (right != null ? right.segCnt : 0);
            int Rmask = (right != null ? right.hasC : 0);
            int Rcnt = (right != null ? right.cnt : 0);

            int segs = segL + segR + 1;

            int mergedMask = Lmask | Rmask;
            int bitCount = Integer.bitCount(mergedMask);

            int add = 0;
            if (Math.min(bitCount + 1, 26) <= k) {
                add = 0;
            } else {
                add = (Lcnt == k && Rcnt == k && bitCount < 26) ? 2 : 1;
            }

            ans = Math.max(ans, segs + add);
        }

        return ans;
    }
}
