class Solution {

    int answer = Integer.MAX_VALUE;

    public int minScore(int n, int[][] roads) {

        List<int[]>[] graph = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] road : roads) {
            int u = road[0];
            int v = road[1];
            int d = road[2];

            graph[u].add(new int[]{v, d});
            graph[v].add(new int[]{u, d});
        }

        boolean[] visited = new boolean[n + 1];

        dfs(1, graph, visited);

        return answer;
    }

    private void dfs(int city, List<int[]>[] graph, boolean[] visited) {

        visited[city] = true;

        for (int[] next : graph[city]) {

            int neighbor = next[0];
            int distance = next[1];

            answer = Math.min(answer, distance);

            if (!visited[neighbor]) {
                dfs(neighbor, graph, visited);
            }
        }
    }
}