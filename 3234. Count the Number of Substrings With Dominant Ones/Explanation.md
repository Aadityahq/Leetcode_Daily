# ✅ **Problem Recap**

For every substring `s[l..r]`, count it if:

[
\text{ones}(l,r) \ge (\text{zeros}(l,r))^2
]

Zeros grow linearly, but their **square grows fast**, so only small zero counts matter.

---

# 🚀 FULL EXPLANATION OF THE CORRECT SOLUTION

Your solution uses three key insights:

---

# ⭐ Insight 1 — Substrings with **zero zeros** (all-1 substrings)

These are ALWAYS dominant because:

[
\text{zeros} = 0 \quad\Rightarrow\quad \text{ones} \ge 0
]

So we simply count all "runs of consecutive ones".

Example:
`1110` → run of length 3 → contributes:

[
3 \cdot 4 / 2 = 6
]

### Why we do this?

Because counting all substrings made only of ones is easy and very fast.
This removes a big portion of substrings from the difficult logic.

Your code:

```java
while (i < n) {
    if (s.charAt(i) == '0') { i++; continue; }
    int j = i;
    while (j < n && s.charAt(j) == '1') j++;
    long len = j - i;
    ans += len * (len + 1) / 2;
    i = j;
}
```

---

# ⭐ Insight 2 — Substrings with **few zeros**

Let’s denote:

* `z = number of zeros in the substring`
* Requirement:

[
ones \ge z^2
]

Because `z²` grows fast:

* `z=1` → need 1 one
* `z=2` → need 4 ones
* `z=3` → need 9 ones
* `z=4` → need 16 ones
* `z=5` → need 25 ones
* `z=6` → need 36 ones

But in a substring, number of ones cannot exceed length.

Therefore:

> **We only need to check substrings with z ≤ √n.**

Your code does:

```java
int B = (int)Math.sqrt(n) + 2;   // about 200 for n=40000
```

This is the **zero limit**.

---

# ⭐ Insight 3 — Preprocessing the string

We pre-store:

### 1. `pref[i]` = number of ones in `s[0..i-1]`

Lets us compute ones in O(1):

[
ones(l,r) = pref[r+1] - pref[l]
]

### 2. List of zero positions

Example: s = `101001` → `Z = [1,3,4]`

This helps us identify substrings containing exactly `z` zeros by using `Z[a]` to `Z[b]`.

Your code:

```java
List<Integer> Z = new ArrayList<>();
for (int i = 0; i < n; i++)
    if (s.charAt(i) == '0') Z.add(i);
```

---

# ⭐ Insight 4 — Fix number of zeros = z

You iterate:

```java
for (int z = 1; z <= B; z++)
```

For each fixed `z` (1, 2, 3, … up to sqrt(n)):

Find all substrings that contain exactly `z` zero positions.

If `Z[a]` and `Z[b]` are zero-index positions:

[
b = a + z - 1
]

So the substring contains zeros at:

[
Z[a], Z[a+1], ..., Z[b]
]

This substring can start anywhere between:

[
Lmin = Z[a-1] + 1 \quad \text{(or 0 if a=0)}
]
[
Lmax = Z[a]
]

And end anywhere between:

[
Rmin = Z[b]
]
[
Rmax = Z[b+1] - 1 \quad \text{(or n-1 if b is last zero)}
]

So we have:

* valid range of left endpoints: `[Lmin, Lmax]`
* valid range of right endpoints: `[Rmin, Rmax]`

---

# ⭐ Insight 5 — Use sliding pointer to check ones ≥ z²

For each left endpoint `l`, we need to find the smallest `r` such that:

[
ones(l, r) \ge z^2
]

You use **two-pointer (sliding)** on `r`:

```java
while (r <= Rmax && pref[r + 1] - pref[l] < need) r++;
```

If no `r` works — skip this `l`.

Else all `r` from this point to `Rmax` form valid substrings.

Add:

[
Rmax - r + 1
]

to the answer.

---

# ⭐ Why this is Optimal?

### 1. All-ones substrings handled in O(n)

### 2. Zero counts only go up to √n

### 3. For each zero-block (z zeros), we only scan r forward → O(n) total

### Total Time:

[
O(n\sqrt{n}) \quad \text{(max n=40k → ~8M ops → passes)}
]

This is the intended complexity for this problem.

---

# 🎯 **SUMMARY — WHY This Method Works**

1. **Dominant condition**: ones ≥ (zeros)²
   → large zero counts impossible → reduce search space.

2. **Split substrings into:**

   * zero-zero substrings → count all `1` runs
   * substrings with 1,2,...,√n zeros

3. **Use zero positions** to determine valid ranges for L and R.

4. **Use prefix sums** to quickly get number of ones inside a substring.

5. **Use sliding pointer** to count all valid (l,r) pairs efficiently.

This is mathematically optimal and guaranteed correct.

---

