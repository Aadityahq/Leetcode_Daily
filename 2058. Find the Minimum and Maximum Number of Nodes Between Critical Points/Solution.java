class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {

        int firstCritical = -1;
        int previousCritical = -1;

        int minDistance = Integer.MAX_VALUE;
        int maxDistance = -1;

        int index = 1;

        ListNode prev = head;
        ListNode curr = head.next;

        while (curr != null && curr.next != null) {

            ListNode next = curr.next;

            // Check whether curr is a critical point
            boolean isCritical =
                    (curr.val > prev.val && curr.val > next.val) ||
                    (curr.val < prev.val && curr.val < next.val);

            if (isCritical) {

                // First critical point
                if (firstCritical == -1) {
                    firstCritical = index;
                }

                // We already have a previous critical point
                if (previousCritical != -1) {

                    int distance = index - previousCritical;

                    minDistance = Math.min(minDistance, distance);
                }

                previousCritical = index;

                // Distance between first and current critical point
                maxDistance = index - firstCritical;
            }

            prev = curr;
            curr = next;
            index++;
        }

        // Fewer than two critical points
        if (firstCritical == previousCritical) {
            return new int[]{-1, -1};
        }

        return new int[]{minDistance, maxDistance};
    }
}