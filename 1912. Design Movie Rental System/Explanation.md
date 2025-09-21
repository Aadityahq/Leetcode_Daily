# Movie Renting System

## Problem Overview

You are designing a movie renting system for a company with multiple shops. Each shop carries a set of movies, each with a rental price. The system must allow:

1. Searching for the cheapest shops that have an **unrented copy** of a movie.
2. Renting a movie from a shop.
3. Dropping off a previously rented movie.
4. Generating a report of the **cheapest rented movies**.

---

## Functionality

The system supports four main operations:

1. **Search**
   Finds the cheapest 5 shops with an **unrented copy** of a given movie.

   * Sorted by price (ascending).
   * If prices tie, sorted by shop ID.
   * Returns fewer than 5 if fewer are available.

2. **Rent**
   Rents an **unrented copy** of a movie from a given shop.

3. **Drop**
   Returns a previously rented movie to the shop.

4. **Report**
   Returns the cheapest 5 **rented movies** sorted by:

   * Price (ascending)
   * Shop ID (ascending) if price ties
   * Movie ID (ascending) if price and shop tie

---

## Constraints

* Number of shops: `1 <= n <= 3 * 10^5`
* Movie entries: `1 <= entries.length <= 10^5`
* Price and IDs: `1 <= pricei, moviei <= 10^4`
* Operations: at most `10^5` calls
* Each shop carries at most one copy of each movie.

---

## Approach and Data Structures

### 1. Unrented Movies

* **Map:** `movieToUnrented`

  * Key: `movieId`
  * Value: `TreeSet<ShopInfo>`
* **ShopInfo:** `(price, shopId)`
* Stores all unrented copies of a movie sorted by price and shop.

### 2. Rented Movies

* **TreeSet:** `rentedMovies`
* Stores all rented movies as `(price, shopId, movieId)`
* Sorted by price → shop → movie for reporting.

### 3. Price Lookup

* **Map:** `priceMap`
* Key: `shopId_movieId` → Value: price
* Provides fast access to the rental price for any operation.

---

## Implementation Overview (Java)

```java
MovieRentingSystem movieRentingSystem = new MovieRentingSystem(n, entries);

movieRentingSystem.search(movieId);  // Returns cheapest shops with movie
movieRentingSystem.rent(shopId, movieId); // Rent a movie
movieRentingSystem.drop(shopId, movieId); // Return a movie
movieRentingSystem.report(); // Returns cheapest rented movies
```

**Core Methods:**

* `search(int movie) -> List<Integer>`
* `rent(int shop, int movie)`
* `drop(int shop, int movie)`
* `report() -> List<List<Integer>>`

---

## Example Usage

```java
MovieRentingSystem system = new MovieRentingSystem(3, 
    new int[][]{
        {0, 1, 5}, {0, 2, 6}, {0, 3, 7},
        {1, 1, 4}, {1, 2, 7}, {2, 1, 5}
    }
);

system.search(1);   // Output: [1, 0, 2]
system.rent(0, 1);  
system.rent(1, 2);
system.report();    // Output: [[0, 1], [1, 2]]
system.drop(1, 2);
system.search(2);   // Output: [0, 1]
```

---

## Time Complexity

| Operation | Complexity                                  |
| --------- | ------------------------------------------- |
| `search`  | O(log n) per insert/remove + O(5) iteration |
| `rent`    | O(log n) (TreeSet remove & add)             |
| `drop`    | O(log n) (TreeSet remove & add)             |
| `report`  | O(5) iteration over rented TreeSet          |

* Efficient for constraints: up to 300k shops and 100k operations.

---

## Key Points

* **TreeSet** keeps items sorted automatically for quick queries.
* **Maps** enable O(1) price lookup.
* Handles large input sizes efficiently.
* Supports returning multiple lists (`List<List<Integer>>`) for reports.

---

## Notes

* This design ensures that all operations (`search`, `rent`, `drop`, `report`) remain efficient for large datasets.
* Using TreeSets and Maps is crucial for meeting the performance requirements.
