
import java.util.*;

class Spreadsheet {
    private Map<String, Integer> cells;

    // Initialize the spreadsheet
    public Spreadsheet(int rows) {
        cells = new HashMap<>();
    }
    
    // Set a cell's value
    public void setCell(String cell, int value) {
        cells.put(cell, value);
    }
    
    // Reset a cell to 0
    public void resetCell(String cell) {
        cells.remove(cell); // Remove from map → default value is 0
    }
    
    // Evaluate a formula of the form "=X+Y"
    public int getValue(String formula) {
        formula = formula.substring(1); // Remove the '='
        String[] parts = formula.split("\\+"); // Split into X and Y
        return getTokenValue(parts[0]) + getTokenValue(parts[1]);
    }

    // Helper: get value of a token (either integer or cell reference)
    private int getTokenValue(String token) {
        if (token.chars().allMatch(Character::isDigit)) {
            return Integer.parseInt(token);
        }
        return cells.getOrDefault(token, 0);
    }
}

/**
 * Example Usage:
 * Spreadsheet obj = new Spreadsheet(3);
 * obj.setCell("A1", 10);
 * obj.resetCell("A1");
 * int val = obj.getValue("=A1+5"); // returns 5
 */
