1935. Maximum Number of Words You Can Type — Solution (Java)

Problem (short)
Given text (words separated by single spaces) and brokenLetters (distinct lowercase letters that cannot be typed), return the number of words in text that can be typed fully (i.e., they contain no broken letter).

Idea / Algorithm

Put the broken letters into a Set<Character> for O(1) membership checks.

Split text into words with text.split(" ").

For each word, check every character — if any character is in the broken set, the word is not typable.

Count words that are fully typable.

Time complexity: O(n + m) where n = total characters in text and m = length of brokenLetters.
Space complexity: O(1) extra (excluding output), or O(|brokenLetters|) for the set (≤ 26).