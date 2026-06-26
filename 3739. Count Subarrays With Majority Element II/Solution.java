class Solution {
    public long countMajoritySubarrays(int[] nums, int target) {
        int n = nums.length;

        int offset = n + 2;
        Fenwick bit = new Fenwick(2 * n + 5);

        long ans = 0;
        int prefix = 0;

        // Initial prefix sum = 0
        bit.update(offset, 1);

        for (int num : nums) {
            if (num == target)
                prefix++;
            else
                prefix--;

            ans += bit.query(prefix - 1 + offset);

            bit.update(prefix + offset, 1);
        }

        return ans;
    }

    class Fenwick {
        int[] tree;

        Fenwick(int size) {
            tree = new int[size + 2];
        }

        void update(int index, int delta) {
            while (index < tree.length) {
                tree[index] += delta;
                index += index & -index;
            }
        }

        int query(int index) {
            int sum = 0;
            while (index > 0) {
                sum += tree[index];
                index -= index & -index;
            }
            return sum;
        }
    }
}