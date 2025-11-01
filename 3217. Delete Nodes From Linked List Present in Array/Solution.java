import java.util.*;

class Solution {
    public ListNode modifiedList(int[] nums, ListNode head) {
        Set<Integer> removeSet = new HashSet<>();
        for (int num : nums) removeSet.add(num);

        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode curr = head, prev = dummy;

        while (curr != null) {
            if (removeSet.contains(curr.val)) {
                // Skip the current node
                prev.next = curr.next;
            } else {
                prev = curr;
            }
            curr = curr.next;
        }

        return dummy.next;
    }
}
