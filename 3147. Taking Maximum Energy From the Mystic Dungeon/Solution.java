class Solution {
    public int maximumEnergy(int[] energy, int k) {
        int n = energy.length;
        int maxEnergy = Integer.MIN_VALUE;

        for (int i = n - 1 - k; i >= 0; i--) {
            energy[i] += energy[i + k];
        }

        for (int val : energy) {
            maxEnergy = Math.max(maxEnergy, val);
        }

        return maxEnergy;
    }
}
