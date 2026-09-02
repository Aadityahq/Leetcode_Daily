import java.util.*;

class Solution {

    static class State {
        int r;
        int c;
        int energy;
        int mask;

        State(int r, int c, int energy, int mask) {
            this.r = r;
            this.c = c;
            this.energy = energy;
            this.mask = mask;
        }
    }

    public int minMoves(String[] classroom, int energy) {

        int m = classroom.length;
        int n = classroom[0].length();

        int startR = 0;
        int startC = 0;

        // Map each litter cell to a bit index.
        int[][] litterId = new int[m][n];
        for (int[] row : litterId) {
            Arrays.fill(row, -1);
        }

        int litterCount = 0;

        // Find start position and assign IDs to litter cells.
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (classroom[i].charAt(j) == 'S') {
                    startR = i;
                    startC = j;
                }

                if (classroom[i].charAt(j) == 'L') {
                    litterId[i][j] = litterCount++;
                }
            }
        }

        int allCollected = (1 << litterCount) - 1;

        // No litter to collect.
        if (litterCount == 0) {
            return 0;
        }

        /*
         * maxEnergy[r][c][mask] =
         * maximum energy with which we have reached
         * (r, c) after collecting litter represented by mask.
         */
        int[][][] maxEnergy = new int[m][n][1 << litterCount];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(maxEnergy[i][j], -1);
            }
        }

        Queue<State> queue = new ArrayDeque<>();

        queue.offer(new State(startR, startC, energy, 0));
        maxEnergy[startR][startC][0] = energy;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        int moves = 0;

        while (!queue.isEmpty()) {

            int size = queue.size();

            // Process one BFS level.
            while (size-- > 0) {

                State current = queue.poll();

                int r = current.r;
                int c = current.c;
                int currEnergy = current.energy;
                int mask = current.mask;

                // All litter collected.
                if (mask == allCollected) {
                    return moves;
                }

                // Cannot make another move without energy.
                if (currEnergy == 0) {
                    continue;
                }

                for (int d = 0; d < 4; d++) {

                    int nr = r + dr[d];
                    int nc = c + dc[d];

                    // Outside grid.
                    if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                        continue;
                    }

                    // Obstacle.
                    if (classroom[nr].charAt(nc) == 'X') {
                        continue;
                    }

                    // Moving costs one energy.
                    int newEnergy = currEnergy - 1;

                    // Collect litter if this cell contains one.
                    int newMask = mask;

                    if (classroom[nr].charAt(nc) == 'L') {
                        int id = litterId[nr][nc];
                        newMask |= (1 << id);
                    }

                    // Reset energy if we arrive at R.
                    if (classroom[nr].charAt(nc) == 'R') {
                        newEnergy = energy;
                    }

                    /*
                     * If we have already reached this
                     * (position, mask) with at least as much energy,
                     * this state is useless.
                     */
                    if (maxEnergy[nr][nc][newMask] >= newEnergy) {
                        continue;
                    }

                    maxEnergy[nr][nc][newMask] = newEnergy;

                    queue.offer(
                        new State(nr, nc, newEnergy, newMask)
                    );
                }
            }

            moves++;
        }

        return -1;
    }
}