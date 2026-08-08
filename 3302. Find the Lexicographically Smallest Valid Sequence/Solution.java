class Solution {
    public int[] validSequence(String word1, String word2) {
        int length1 = word1.length();
        int length2 = word2.length();
        int[] indices = new int[length1];
        int j = length2 - 1;

        // Reverse traversal to fill indices
        for (int i = length1 - 1; i >= 0; i--) {
            if (j >= 0 && word1.charAt(i) == word2.charAt(j)) {
                indices[i] = (i == length1 - 1) ? 1 : indices[i + 1] + 1;
                j--;
            } else {
                indices[i] = (i == length1 - 1) ? 0 : indices[i + 1];
            }
        }

        j = 0;
        List<Integer> result = new ArrayList<>();
        int finalIndex = -1;

        // Forward traversal to build the result
        for (int i = 0; i < length1; i++) {
            if (word1.charAt(i) == word2.charAt(j)) {
                result.add(i);
                j++;
                if (j == length2) {
                    break;
                }
            } else {
                if ((i == length1 - 1 ? 0 : indices[i + 1]) >= length2 - j - 1) {
                    result.add(i);
                    j++;
                    finalIndex = i + 1;
                    break;
                }
            }
        }

        if (result.size() == length2) {
            return convertToArray(result);
        }

        if (finalIndex == -1) {
            return new int[0];
        }

        for (int i = finalIndex; i < length1; i++) {
            if (word1.charAt(i) == word2.charAt(j)) {
                result.add(i);
                j++;
            }
            if (j == length2) {
                break;
            }
        }

        return result.size() == length2 ? convertToArray(result) : new int[0];
    }

    private int[] convertToArray(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).toArray();
    }
}