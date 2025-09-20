# Implement Router

## Problem
Design a data structure that can efficiently manage data packets in a network router.  
Each data packet has the following attributes:

- **source**: A unique identifier for the machine that generated the packet.  
- **destination**: A unique identifier for the target machine.  
- **timestamp**: The time at which the packet arrived at the router.  

Implement the following methods:

1. **Router(int memoryLimit)**  
   Initializes the Router with a fixed memory limit.  
   - `memoryLimit` = maximum number of packets the router can store.  
   - If adding a new packet would exceed this limit, the oldest packet is removed.

2. **boolean addPacket(int source, int destination, int timestamp)**  
   - Adds a new packet to the router.  
   - A packet is a **duplicate** if another packet with the same `(source, destination, timestamp)` exists.  
   - Return `true` if added, otherwise `false`.

3. **int[] forwardPacket()**  
   - Forwards the next packet in **FIFO (First In, First Out)** order.  
   - Removes the packet from the router.  
   - Returns `[source, destination, timestamp]`.  
   - If there are no packets, return `[]`.

4. **int getCount(int destination, int startTime, int endTime)**  
   - Returns the number of packets (still stored in the router) that have:  
     - the given `destination`, and  
     - `timestamp` in the inclusive range `[startTime, endTime]`.

---

## Examples

### Example 1
**Input**  
["Router", "addPacket", "addPacket", "addPacket", "addPacket",
"addPacket", "forwardPacket", "addPacket", "getCount"]
[[3], [1, 4, 90], [2, 5, 90], [1, 4, 90],
[3, 5, 95], [4, 5, 105], [], [5, 2, 110], [5, 100, 110]]



**Output**  
[null, true, true, false, true, true, [2, 5, 90], true, 1]



**Explanation**  
- Router initialized with `memoryLimit = 3`.  
- Add (1,4,90) → added → return true.  
- Add (2,5,90) → added → return true.  
- Add (1,4,90) → duplicate → return false.  
- Add (3,5,95) → added → return true.  
- Add (4,5,105) → exceeds memory → oldest (1,4,90) removed → return true.  
- Forward → returns `[2,5,90]` and removes it.  
- Add (5,2,110) → added → return true.  
- getCount(5, 100, 110) → only `[4,5,105]` matches → return 1.  

---

### Example 2
**Input**  
["Router", "addPacket", "forwardPacket", "forwardPacket"]
[[2], [7, 4, 90], [], []]

markdown
Copy code

**Output**  
[null, true, [7, 4, 90], []]

markdown
Copy code

**Explanation**  
- Router initialized with `memoryLimit = 2`.  
- Add (7,4,90) → true.  
- Forward → returns `[7,4,90]`.  
- Forward → no packets left → returns `[]`.  

---

## Approach
1. Use a **queue** to store packets in FIFO order.  
2. Use a **set** to track duplicates by storing `(source, destination, timestamp)` keys.  
3. For `addPacket`:  
   - If duplicate → return false.  
   - If router is full → remove oldest packet.  
   - Insert packet into queue and set.  
4. For `forwardPacket`:  
   - Remove from queue and set.  
   - Return packet if available, else return empty array.  
5. For `getCount`:  
   - Iterate over queue and count packets matching destination and time range.  

---

## Complexity
- **addPacket:** O(1) (amortized, due to queue and set operations)  
- **forwardPacket:** O(1)  
- **getCount:** O(n) (need to scan queue)  
- **Space:** O(n), where `n ≤ memoryLimit`

---

## Snippet
The key logic for merging packets into the router:

```java
if (queue.size() >= memoryLimit) {
    int[] oldest = queue.poll();
    set.remove(oldest[0] + "," + oldest[1] + "," + oldest[2]);
}
if (set.contains(key)) return false;
queue.offer(new int[]{source, destination, timestamp});
set.add(key);
return true;