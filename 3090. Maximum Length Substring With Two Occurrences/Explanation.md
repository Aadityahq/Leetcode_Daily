
## 3090. Maximum Length Substring With Two Occurrences

## Understanding the Problem

We are given a string `s`.

We need to find the longest substring where every character appears at most 2 times.

### Example

bcbbbcba

Check some substrings:

* `"bc"` → `b=1, c=1` ✅

* `"bcbb"` → `b=3` ❌

* `"bbbc"` → `b=3` ❌

* `"bbcb"` → `b=3` ❌

* `"bcba"` → `b=2, c=1, a=1` ✅ length = 4

So the answer is 4.

### Why Sliding Window?

We need a continuous substring.

For substring problems involving:

* maximum / minimum length

* frequency limits

* continuous range

the sliding window technique is usually the best choice.

### Idea

Maintain a window `[left ... right]`.

* Expand `right` one character at a time.

* Count frequencies of characters inside the window.

* If any character appears more than 2 times, move `left` forward until the condition becomes valid again.

* Track the maximum valid window length.

### Step-by-Step Example

String: aaaa

| Right     | Window | Count of a | Valid? | Max |
| --------- | ------ | ---------- | ------ | --- |
| 0         | "a"    | 1          | ✅      | 1   |
| 1         | "aa"   | 2          | ✅      | 2   |
| 2         | "aaa"  | 3          | ❌      | 2   |
| move left | "aa"   | 2          | ✅      | 2   |
| 3         | "aaa"  | 3          | ❌      | 2   |
| move left | "aa"   | 2          | ✅      | 2   |

Final answer = 2.

### Java Solution

Java

class Solution {

public int maximumLengthSubstring(String s) {

int[] freq = new int[26];

int left = 0;

int maxLen = 0;

for (int right = 0; right < s.length(); right++) {

char ch = s.charAt(right);

freq[ch - 'a']++;

// If current character appears more than 2 times

while (freq[ch - 'a'] > 2) {

freq[s.charAt(left) - 'a']--;

left++;

}

maxLen = Math.max(maxLen, right - left + 1);

}

return maxLen;

}

}

### How This Works

### Frequency Array

Java

int[] freq = new int[26];

Since the string contains only lowercase English letters, we use an array of size 26.

### Expand the Window

Java

freq[ch - 'a']++;

Add the current character to the window.

### Fix Invalid Window

Java

while (freq[ch - 'a'] > 2) {

freq[s.charAt(left) - 'a']--;

left++;

}

If the current character appears 3 times, the window is invalid.

We keep removing characters from the left until its count becomes 2 or less.

### Update Answer

Java

maxLen = Math.max(maxLen, right - left + 1);

After fixing the window, it is guaranteed to be valid, so we update the maximum length.

### Dry Run

bcbbbcba

### After processing `"bcbb"`

Window = `"bcbb"`

* `b = 3`

* invalid

Move `left`:

* remove `'b'`

* counts become `b = 2`

New window = `"cbb"` (valid)

Continue expanding until the longest valid window found is length 4.

### Why Does This Work?

At every step:

* The window always remains valid (no character count > 2).

* `right` only moves forward.

* `left` only moves forward.

So each character is added and removed at most once.

This gives an efficient O(n) solution.

### Complexity

Time Complexity: O(n)

* `right` moves `n` times.

* `left` also moves at most `n` times.

Space Complexity: O(1)

* Frequency array size is always 26.

### Intuition to Remember

Whenever you see:

* longest substring

* at most K occurrences

* at most K distinct characters

* frequency constraint

think:

Sliding Window Pattern

Expand the window → check the condition → shrink if invalid → record the best answer.

That is exactly the pattern used in this problem.
