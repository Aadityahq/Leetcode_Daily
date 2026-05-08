import java.util.*;

class Solution {

    public int minJumps(int[] nums) {

        int n = nums.length;

        if (n == 1) return 0;

        int MAX = 1_000_000;

        // Sieve for prime checking
        boolean[] isPrime = sieve(MAX);

        // Map: prime factor -> list of indices divisible by it
        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int i = 0; i < n; i++) {

            int num = nums[i];

            // Find unique prime factors
            for (int p = 2; p * p <= num; p++) {

                if (num % p == 0) {

                    map.computeIfAbsent(p, k -> new ArrayList<>()).add(i);

                    while (num % p == 0) {
                        num /= p;
                    }
                }
            }

            if (num > 1) {
                map.computeIfAbsent(num, k -> new ArrayList<>()).add(i);
            }
        }

        // BFS
        Queue<Integer> q = new LinkedList<>();
        boolean[] visited = new boolean[n];

        q.offer(0);
        visited[0] = true;

        int steps = 0;

        while (!q.isEmpty()) {

            int size = q.size();

            while (size-- > 0) {

                int idx = q.poll();

                if (idx == n - 1) {
                    return steps;
                }

                // Move left
                if (idx - 1 >= 0 && !visited[idx - 1]) {
                    visited[idx - 1] = true;
                    q.offer(idx - 1);
                }

                // Move right
                if (idx + 1 < n && !visited[idx + 1]) {
                    visited[idx + 1] = true;
                    q.offer(idx + 1);
                }

                int val = nums[idx];

                // Teleport if current number is prime
                if (isPrime[val] && map.containsKey(val)) {

                    for (int next : map.get(val)) {

                        if (!visited[next]) {
                            visited[next] = true;
                            q.offer(next);
                        }
                    }

                    // Important optimization
                    map.remove(val);
                }
            }

            steps++;
        }

        return -1;
    }

    // Sieve of Eratosthenes
    private boolean[] sieve(int n) {

        boolean[] prime = new boolean[n + 1];

        Arrays.fill(prime, true);

        prime[0] = false;
        prime[1] = false;

        for (int i = 2; i * i <= n; i++) {

            if (prime[i]) {

                for (int j = i * i; j <= n; j += i) {
                    prime[j] = false;
                }
            }
        }

        return prime;
    }
}