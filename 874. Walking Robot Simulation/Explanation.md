# 🧠 Problem Understanding

You have a robot:

* Starts at **(0,0)** facing **North**
* Gets commands:

  * `-2` → turn left
  * `-1` → turn right
  * `1 to 9` → move forward step-by-step
* There are **obstacles**
* If robot hits obstacle → **stop before it**
* You must return:
  👉 **maximum distance² (x² + y²)** reached at ANY point

---

# ⚠️ Key Observations (VERY IMPORTANT)

### 1. Direction Handling

We can represent directions like this:

```
0 → North  (0, +1)
1 → East   (+1, 0)
2 → South  (0, -1)
3 → West   (-1, 0)
```

Turning:

* Right → `(dir + 1) % 4`
* Left → `(dir + 3) % 4`

---

### 2. Move Step-by-Step

Even if command = 9:
👉 move **1 step at a time**, not jump

Because:

* obstacle might be in between

---

### 3. Fast Obstacle Lookup

We store obstacles in a **HashSet**

Why?
👉 O(1) lookup

We encode `(x, y)` as string:

```
"x,y"
```

---

### 4. Track Maximum Distance

After EVERY step:

```
max = max(max, x*x + y*y)
```

---

# 🚀 Java Solution

```java
import java.util.*;

class Solution {
    public int robotSim(int[] commands, int[][] obstacles) {
        
        // Store obstacles in HashSet
        Set<String> set = new HashSet<>();
        for (int[] obs : obstacles) {
            set.add(obs[0] + "," + obs[1]);
        }
        
        // Directions: North, East, South, West
        int[][] dirs = {
            {0, 1},   // North
            {1, 0},   // East
            {0, -1},  // South
            {-1, 0}   // West
        };
        
        int x = 0, y = 0;
        int dir = 0; // start facing North
        int maxDist = 0;
        
        for (int cmd : commands) {
            
            if (cmd == -1) {
                // turn right
                dir = (dir + 1) % 4;
            } 
            else if (cmd == -2) {
                // turn left
                dir = (dir + 3) % 4;
            } 
            else {
                // move step by step
                for (int i = 0; i < cmd; i++) {
                    int nx = x + dirs[dir][0];
                    int ny = y + dirs[dir][1];
                    
                    // check obstacle
                    if (set.contains(nx + "," + ny)) {
                        break;
                    }
                    
                    x = nx;
                    y = ny;
                    
                    // update max distance
                    maxDist = Math.max(maxDist, x * x + y * y);
                }
            }
        }
        
        return maxDist;
    }
}
```

---

# 🔍 Dry Run (Example 2)

```
commands = [4, -1, 4, -2, 4]
obstacles = [[2,4]]
```

### Steps:

1. Move north → (0,4)
2. Turn right → East
3. Move → (1,4), next is (2,4) ❌ obstacle → stop
4. Turn left → North
5. Move → (1,8)

👉 Max distance =

```
1^2 + 8^2 = 65
```

---

# 💡 Why This Approach Works

### ✔ HashSet

Fast obstacle checking → O(1)

### ✔ Step-by-step movement

Prevents jumping over obstacles

### ✔ Direction array

Clean & avoids messy if-else

---

# ⏱️ Complexity

### Time:

* Commands: `O(N)`
* Each move max 9 steps → `O(9N) ≈ O(N)`

### Space:

* Obstacles set → `O(M)`

---

# 🧠 Interview Tips

If interviewer asks:

👉 *Why step-by-step?*
Because obstacles block intermediate positions.

👉 *Why HashSet?*
To avoid O(N) search each time.

👉 *Alternative?*
Could encode `(x,y)` as `long` instead of string:

```
long key = ((long)x << 32) | (y & 0xffffffffL);
```

(Faster)

---

# ✅ Summary

* Use **direction index**
* Use **HashSet for obstacles**
* Move **step-by-step**
* Track **max distance continuously**

---
