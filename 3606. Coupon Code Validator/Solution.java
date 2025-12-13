import java.util.*;

class Solution {
    public List<String> validateCoupons(
            String[] code,
            String[] businessLine,
            boolean[] isActive) {

        // Required business line order
        String[] order = {"electronics", "grocery", "pharmacy", "restaurant"};
        Set<String> validBusiness = new HashSet<>(Arrays.asList(order));

        // Map to store valid codes by business line
        Map<String, List<String>> map = new HashMap<>();
        for (String b : order) {
            map.put(b, new ArrayList<>());
        }

        // Regex for valid coupon code
        String regex = "[A-Za-z0-9_]+";

        for (int i = 0; i < code.length; i++) {

            // Check active
            if (!isActive[i]) continue;

            // Check business line
            if (!validBusiness.contains(businessLine[i])) continue;

            // Check code validity
            if (code[i] == null || code[i].isEmpty()) continue;
            if (!code[i].matches(regex)) continue;

            // Valid coupon
            map.get(businessLine[i]).add(code[i]);
        }

        // Prepare result
        List<String> result = new ArrayList<>();

        for (String b : order) {
            List<String> list = map.get(b);
            Collections.sort(list); // lexicographical order
            result.addAll(list);
        }

        return result;
    }
}
