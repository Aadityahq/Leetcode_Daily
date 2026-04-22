import java.util.*;

class Solution {
    public List<String> twoEditWords(String[] queries, String[] dictionary) {
        List<String> result = new ArrayList<>();

        for (String query : queries) {
            if (isValid(query, dictionary)) {
                result.add(query);
            }
        }

        return result;
    }

    private boolean isValid(String query, String[] dictionary) {
        for (String word : dictionary) {
            int diff = 0;

            for (int i = 0; i < query.length(); i++) {
                if (query.charAt(i) != word.charAt(i)) {
                    diff++;
                }
                if (diff > 2) break;
            }

            if (diff <= 2) return true;
        }
        return false;
    }
}