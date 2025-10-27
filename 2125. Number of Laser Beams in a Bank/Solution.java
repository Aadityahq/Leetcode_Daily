class Solution {
    public int numberOfBeams(String[] bank) {
        int prevDevices = 0;   // stores device count of previous non-empty row
        int totalBeams = 0;

        for (String row : bank) {
            int currDevices = 0;

            // count number of '1's in this row
            for (char c : row.toCharArray()) {
                if (c == '1') currDevices++;
            }

            // if this row has any device
            if (currDevices > 0) {
                totalBeams += prevDevices * currDevices;  // form beams with previous row
                prevDevices = currDevices;  // update previous count
            }
        }

        return totalBeams;
    }
}
