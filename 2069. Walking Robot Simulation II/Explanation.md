# 🧠 Problem Understanding (Simple)

* Robot starts at **(0,0)** facing **East**
* Grid boundaries:

  * x: `[0 → width-1]`
  * y: `[0 → height-1]`

### Movement Rules:

1. Move forward 1 step
2. If next step is out of bounds:

   * turn **90° counterclockwise**
   * try again

---

# 🔑 Key Insight (IMPORTANT)

The robot **moves only on the boundary (perimeter)** after starting.

👉 It forms a loop around the rectangle.

### Total cycle length:

[
\text{cycle} = 2 * (width + height) - 4
]

So instead of moving `num` steps:

```java
num = num % cycle;
```

This avoids unnecessary simulation.

---

# ⚠️ Edge Case (VERY IMPORTANT)

If:

```java
num % cycle == 0
```

👉 Robot completes full loop but **direction changes** to **South**

---

# 🧩 Approach

1. Store:

   * `x, y` position
   * `dir` (0=East, 1=North, 2=West, 3=South)

2. Use direction arrays:

```java
int[][] dirs = {
    {1, 0},   // East
    {0, 1},   // North
    {-1, 0},  // West
    {0, -1}   // South
};
```

3. For each step:

   * Try moving
   * If invalid → rotate `(dir + 1) % 4`

---

# ✅ Java Solution (Optimized)

```java
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
```

---

# 🧠 Why This Works

### 1. Cycle Optimization

Instead of:

```
O(num)
```

We reduce to:

```
O(cycle) ≈ O(width + height)
```

---

### 2. Boundary Walking

Robot never goes inside — only edges:

* Bottom row → East
* Right column → North
* Top row → West
* Left column → South

---

### 3. Direction Handling

Turning:

```java
dir = (dir + 1) % 4;
```

→ ensures counterclockwise rotation

---

# 🔍 Dry Run (Quick)

```
width = 6, height = 3

cycle = 2*(6+3)-4 = 14
```

```
step(2) → (2,0)
step(2) → (4,0)
step(2):
    → (5,0)
    → turn North → (5,1)
```

---

# ⏱️ Complexity

| Operation | Complexity        |
| --------- | ----------------- |
| step()    | O(width + height) |
| getPos()  | O(1)              |
| getDir()  | O(1)              |

---

# 💡 Interview Tip

If interviewer asks:
👉 “Why modulo cycle?”

Answer:

> Because robot repeats the same path after one full perimeter traversal, so extra steps are redundant.

---

