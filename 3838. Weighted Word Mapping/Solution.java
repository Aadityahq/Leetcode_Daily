class Solution {
    public String mapWordWeights(String[] words, int[] weights) {
        StringBuilder ans = new StringBuilder();

        for (String word : words) {
            int totalWeight = 0;

            for (char ch : word.toCharArray()) {
                totalWeight += weights[ch - 'a'];
            }

            int mod = totalWeight % 26;
            ans.append((char)('z' - mod));
        }

        return ans.toString();
    }
}