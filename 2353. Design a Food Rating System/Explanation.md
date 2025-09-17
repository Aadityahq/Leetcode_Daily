# 2353. Design a Food Rating System

## Problem

Design a food rating system that can do the following:

- Modify the rating of a food item listed in the system.
- Return the highest-rated food item for a given type of cuisine.
- If multiple foods have the same rating, return the lexicographically smaller food name.

---

## Examples

### Example 1

**Input:**
```text
["FoodRatings", "highestRated", "highestRated", "changeRating", "highestRated", "changeRating", "highestRated"]
[[["kimchi", "miso", "sushi", "moussaka", "ramen", "bulgogi"], 
    ["korean", "japanese", "japanese", "greek", "japanese", "korean"], 
    [9, 12, 8, 15, 14, 7]], 
 ["korean"], 
 ["japanese"], 
 ["sushi", 16], 
 ["japanese"], 
 ["ramen", 16], 
 ["japanese"]]
```

**Output:**
```text
[null, "kimchi", "ramen", null, "sushi", null, "ramen"]
```

**Explanation:**
- Highest rated korean food → `"kimchi"`
- Highest rated japanese food → `"ramen"`
- After updating `"sushi"` to 16 → `"sushi"`
- After updating `"ramen"` to 16 → `"ramen"` (since it's lexicographically smaller than sushi)

---

## Approach

Use two hash maps:

- `foodMap`: maps food → (cuisine, rating).
- `cuisineMap`: maps cuisine → max-heap (priority queue) of (rating, food).

For each cuisine, maintain a priority queue sorted by:
- Higher rating first.
- If tie → lexicographically smaller food.

**On `changeRating(food, newRating)`:**
- Update the rating in `foodMap`.
- Insert a new (rating, food) entry in the corresponding heap.

**On `highestRated(cuisine)`:**
- Look at the top of the heap.
- If the stored rating matches the latest rating in `foodMap`, return it.
- Otherwise, remove outdated entries until the heap top is valid (lazy deletion).

---

## Complexity

- **Time:**  
    - O(log n) for `changeRating` (heap insertion).
    - O(log n) for `highestRated` (heap cleanup in worst case).
- **Space:** O(n) for maps and heaps.

---

## Snippet

The core logic for keeping the heap valid:

```java
public String highestRated(String cuisine) {
        PriorityQueue<Food> pq = cuisineMap.get(cuisine);
        while (true) {
                Food top = pq.peek();
                // check if rating is up-to-date
                if (top.rating == foodMap.get(top.name).rating) {
                        return top.name;
                }
                pq.poll(); // remove outdated entry
        }
}
