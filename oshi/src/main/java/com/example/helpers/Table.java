package com.example.helpers;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private final List<String[]> rows = new ArrayList<>();

    private final int horizontalPadding = 1;

    public void addRow(String... cells) {
        rows.add(cells);
    }

    public void print() {
        if (rows.isEmpty()) {
            System.out.println("(empty table)");
            return;
        }

        // Find total number of columns by finding the array with the most entries
        int cols = 0;
        for (String[] row : rows) {
            cols = Math.max(cols, row.length);
        }

        // Check max width for each column by finding the longest entry of each array
        int[] widths = new int[cols];
        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                widths[i] = Math.max(widths[i], row[i].length());
            }
        }

        // Total horizontal padding includes each side of the cell
        int totalHorizontalPadding = horizontalPadding * 2;

        // Print top border
        // + is for corners, - is for edges
        System.out.print("+");
        for (int width : widths) {
            System.out.print("-".repeat(width + totalHorizontalPadding)); // Print dashes for content width and padding
            System.out.print("+");
        }
        System.out.println(); // End of top border

        // Print each row
        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
            String[] row = rows.get(rowIndex);
            System.out.print("|");
            for (int i = 0; i < cols; i++) {
                String cell = (i < row.length) ? row[i] : ""; // Check if cell actually exists
                System.out.print(" ".repeat(horizontalPadding) + cell); // space before content and content
                System.out.print(" ".repeat(widths[i] - cell.length())); // padding after content (variable, to align)
                System.out.print(" ".repeat(horizontalPadding) + "|"); // space after content + border
            }
            System.out.println(); // End of row

            // Special Case, print separator after header (first row)
            if (rowIndex == 0 && rows.size() > 1) {
                System.out.print("+"); // Start separator with corner
                for (int width : widths) {
                    System.out.print("-".repeat(width + totalHorizontalPadding)); // Print dashes for content width and
                                                                                  // padding
                    System.out.print("+"); // End each column with corner
                }
                System.out.println(); // End of separator line
            }
        }

        // Print bottom border
        System.out.print("+"); // Start bottom border with corner
        for (int width : widths) {
            System.out.print("-".repeat(width + totalHorizontalPadding)); // Print dashes for content width and padding
            System.out.print("+"); // End each column with corner
        }
        System.out.println(); // End of bottom border
    }
}
