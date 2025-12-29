import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {

    // Map to store pair -> possible top blocks
    private Map<String, List<Character>> map = new HashMap<>();

    public boolean pyramidTransition(String bottom, List<String> allowed) {

        // Build mapping
        for (String s : allowed) {
            String key = s.substring(0, 2);
            char top = s.charAt(2);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(top);
        }

        // Start DFS
        return dfs(bottom);
    }

    private boolean dfs(String current) {

        // If only one block left, pyramid is built
        if (current.length() == 1) {
            return true;
        }

        // Generate all possible next rows
        return buildNextRow(current, 0, new StringBuilder());
    }

    private boolean buildNextRow(String current, int index, StringBuilder nextRow) {

        // If next row is fully built, recurse
        if (index == current.length() - 1) {
            return dfs(nextRow.toString());
        }

        String pair = current.substring(index, index + 2);

        // If no allowed triangle exists, fail early
        if (!map.containsKey(pair)) {
            return false;
        }

        // Try all possible top blocks
        for (char c : map.get(pair)) {
            nextRow.append(c);
            if (buildNextRow(current, index + 1, nextRow)) {
                return true;
            }
            nextRow.deleteCharAt(nextRow.length() - 1); // backtrack
        }

        return false;
    }
}
