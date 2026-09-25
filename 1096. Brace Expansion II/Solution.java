public class Solution {
    
}
import java.util.*;

class Solution {

    private int index = 0;

    public List<String> braceExpansionII(String expression) {
        Set<String> result = parse(expression);

        List<String> ans = new ArrayList<>(result);
        Collections.sort(ans);

        return ans;
    }

    private Set<String> parse(String s) {

        Set<String> result = new HashSet<>();

        while (index < s.length() && s.charAt(index) != '}' 
                && s.charAt(index) != ',') {

            Set<String> current;

            // If we find a '{', recursively parse its contents
            if (s.charAt(index) == '{') {
                index++; // skip '{'

                current = parse(s);

                index++; // skip '}'
            }

            // Otherwise, it is a single lowercase letter
            else {
                current = new HashSet<>();
                current.add(String.valueOf(s.charAt(index)));
                index++;
            }

            // Concatenate current with result
            if (result.isEmpty()) {
                result.addAll(current);
            } else {
                result = multiply(result, current);
            }
        }

        // Handle comma-separated expressions
        while (index < s.length() && s.charAt(index) == ',') {

            index++; // skip ','

            Set<String> next = parse(s);

            result.addAll(next);
        }

        return result;
    }

    private Set<String> multiply(Set<String> a, Set<String> b) {

        Set<String> result = new HashSet<>();

        for (String x : a) {
            for (String y : b) {
                result.add(x + y);
            }
        }

        return result;
    }
}