class Solution {
    public ListNode rotateRight(ListNode head, int k) {

        // Edge cases
        if (head == null || head.next == null || k == 0) {
            return head;
        }

        // Step 1: Find length and tail
        int length = 1;
        ListNode tail = head;

        while (tail.next != null) {
            tail = tail.next;
            length++;
        }

        // Step 2: Reduce k
        k = k % length;

        // If no rotation needed
        if (k == 0) {
            return head;
        }

        // Step 3: Make circular list
        tail.next = head;

        // Step 4: Find new tail
        int stepsToNewTail = length - k - 1;

        ListNode newTail = head;

        for (int i = 0; i < stepsToNewTail; i++) {
            newTail = newTail.next;
        }

        // Step 5: New head
        ListNode newHead = newTail.next;

        // Break the circle
        newTail.next = null;

        return newHead;
    }
}