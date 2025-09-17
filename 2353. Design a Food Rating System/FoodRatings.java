import java.util.*;

class FoodRatings {

    // Map: foodName -> (cuisine, latest rating)
    private Map<String, Food> foodMap;
    // Map: cuisine -> max heap of foods
    private Map<String, PriorityQueue<Food>> cuisineMap;

    // Helper class to store food info (immutable)
    private static class Food {
        String name;
        String cuisine;
        int rating;

        Food(String name, String cuisine, int rating) {
            this.name = name;
            this.cuisine = cuisine;
            this.rating = rating;
        }
    }

    public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
        foodMap = new HashMap<>();
        cuisineMap = new HashMap<>();

        for (int i = 0; i < foods.length; i++) {
            Food food = new Food(foods[i], cuisines[i], ratings[i]);
            foodMap.put(foods[i], food);

            cuisineMap
                .computeIfAbsent(cuisines[i], k -> new PriorityQueue<>(
                    (a, b) -> a.rating == b.rating 
                        ? a.name.compareTo(b.name) 
                        : b.rating - a.rating
                ))
                .add(food);
        }
    }
    
    public void changeRating(String foodName, int newRating) {
        Food oldFood = foodMap.get(foodName);
        // create a NEW Food object with updated rating
        Food updatedFood = new Food(foodName, oldFood.cuisine, newRating);

        // update map
        foodMap.put(foodName, updatedFood);

        // push into heap
        cuisineMap.get(oldFood.cuisine).add(updatedFood);
    }
    
    public String highestRated(String cuisine) {
        PriorityQueue<Food> pq = cuisineMap.get(cuisine);
        
        while (true) {
            Food top = pq.peek();
            // Lazy deletion: only accept if rating matches latest rating in map
            if (top.rating == foodMap.get(top.name).rating) {
                return top.name;
            }
            pq.poll(); // remove outdated entry
        }
    }
}
