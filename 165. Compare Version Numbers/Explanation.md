# 165. Compare Version Numbers

## Problem
Compare two version strings `version1` and `version2`.

- Each version consists of revisions separated by dots `.`
- Revisions are integers (ignore leading zeros)
- Missing revisions = 0

**Return:**
- `-1` if version1 < version2
- `1` if version1 > version2
- `0` if equal

---

## Examples
- Input: `version1 = "1.2"`, `version2 = "1.10"` → Output: `-1`
- Input: `version1 = "1.01"`, `version2 = "1.001"` → Output: `0`
- Input: `version1 = "1.0"`, `version2 = "1.0.0.0"` → Output: `0`

---

## Approach
1. Split both versions by `.`
2. Compare revisions one by one
3. If one version has fewer parts → treat missing as `0`
4. Return result based on comparison

---

## Java Solution
```java
class Solution {
    public int compareVersion(String version1, String version2) {
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");
        int n = Math.max(v1.length, v2.length);

        for (int i = 0; i < n; i++) {
            int num1 = i < v1.length ? Integer.parseInt(v1[i]) : 0;
            int num2 = i < v2.length ? Integer.parseInt(v2[i]) : 0;

            if (num1 < num2) return -1;
            if (num1 > num2) return 1;
        }
        return 0;
    }
}
