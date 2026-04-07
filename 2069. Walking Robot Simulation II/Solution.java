class Robot {
    int width, height;
    int x, y;
    int dir; // 0=East, 1=North, 2=West, 3=South
    
    int[][] dirs = {
        {1, 0},   // East
        {0, 1},   // North
        {-1, 0},  // West
        {0, -1}   // South
    };
    
    String[] dirNames = {"East", "North", "West", "South"};

    public Robot(int width, int height) {
        this.width = width;
        this.height = height;
        this.x = 0;
        this.y = 0;
        this.dir = 0; // East
    }
    
    public void step(int num) {
        int cycle = 2 * (width + height) - 4;
        
        num = num % cycle;
        
        // special case
        if (num == 0) {
            if (x == 0 && y == 0) {
                dir = 3; // South
            }
            return;
        }
        
        while (num > 0) {
            int nx = x + dirs[dir][0];
            int ny = y + dirs[dir][1];
            
            // check boundary
            if (nx < 0 || ny < 0 || nx >= width || ny >= height) {
                dir = (dir + 1) % 4; // turn CCW
            } else {
                x = nx;
                y = ny;
                num--;
            }
        }
    }
    
    public int[] getPos() {
        return new int[]{x, y};
    }
    
    public String getDir() {
        return dirNames[dir];
    }
}