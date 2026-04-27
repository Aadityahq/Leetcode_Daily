# 1391. Check if There is a Valid Path in a Grid

## Problem
Given an m × n grid containing one of four possible types of connections:
- 1: vertical line (connects up and down)
- 2: horizontal line (connects left and right)
- 3: connects down and right
- 4: connects down and left
- 5: connects up and right
- 6: connects up and left

Determine if there is a valid path from the top-left (0, 0) to the bottom-right (m-1, n-1) cell.

## Solution Approach
The solution uses a **simulation-based approach** with precomputed transition tables:

1. **TRANS table**: Maps each cell type and incoming direction to the outgoing direction
2. **DIRS array**: Maps direction indices (0-3) to actual coordinate changes
3. **START table**: Determines valid starting directions from each cell type

**Key checks**:
- If start cell is type 5 (up-right) or end cell is type 4 (down-left), no valid path exists
- Simulate the path by following connections until hitting boundaries or reaching the end

**Time Complexity**: O(m × n) - visits each cell at most once
**Space Complexity**: O(1) - uses only constant extra space
