import java.util.*;

class MovieRentingSystem {

    static class ShopInfo implements Comparable<ShopInfo> {
        int price, shopId;
        ShopInfo(int price, int shopId) { this.price = price; this.shopId = shopId; }
        public int compareTo(ShopInfo other) {
            if (this.price != other.price) return this.price - other.price;
            return this.shopId - other.shopId;
        }
    }

    static class RentedInfo implements Comparable<RentedInfo> {
        int price, shopId, movieId;
        RentedInfo(int price, int shopId, int movieId) {
            this.price = price;
            this.shopId = shopId;
            this.movieId = movieId;
        }
        public int compareTo(RentedInfo other) {
            if (this.price != other.price) return this.price - other.price;
            if (this.shopId != other.shopId) return this.shopId - other.shopId;
            return this.movieId - other.movieId;
        }
    }

    Map<Integer, TreeSet<ShopInfo>> movieToUnrented;
    TreeSet<RentedInfo> rentedMovies;
    Map<String, Integer> priceMap; // key = shop_movie, value = price

    public MovieRentingSystem(int n, int[][] entries) {
        movieToUnrented = new HashMap<>();
        rentedMovies = new TreeSet<>();
        priceMap = new HashMap<>();

        for (int[] e : entries) {
            int shop = e[0], movie = e[1], price = e[2];
            movieToUnrented.putIfAbsent(movie, new TreeSet<>());
            movieToUnrented.get(movie).add(new ShopInfo(price, shop));
            priceMap.put(shop + "_" + movie, price);
        }
    }

    public List<Integer> search(int movie) {
        List<Integer> res = new ArrayList<>();
        if (!movieToUnrented.containsKey(movie)) return res;

        Iterator<ShopInfo> it = movieToUnrented.get(movie).iterator();
        int count = 0;
        while (it.hasNext() && count < 5) {
            res.add(it.next().shopId);
            count++;
        }
        return res;
    }

    public void rent(int shop, int movie) {
        int price = priceMap.get(shop + "_" + movie);
        movieToUnrented.get(movie).remove(new ShopInfo(price, shop));
        rentedMovies.add(new RentedInfo(price, shop, movie));
    }

    public void drop(int shop, int movie) {
        int price = priceMap.get(shop + "_" + movie);
        rentedMovies.remove(new RentedInfo(price, shop, movie));
        movieToUnrented.get(movie).add(new ShopInfo(price, shop));
    }

    public List<List<Integer>> report() {
        List<List<Integer>> res = new ArrayList<>();
        Iterator<RentedInfo> it = rentedMovies.iterator();
        int count = 0;
        while (it.hasNext() && count < 5) {
            RentedInfo r = it.next();
            res.add(Arrays.asList(r.shopId, r.movieId));
            count++;
        }
        return res;
    }
}
