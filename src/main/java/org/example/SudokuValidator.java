package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class SudokuValidator {

    public static boolean isValidSudoku(int[][] board) {
        return IntStream.range(0, 9).allMatch(row -> isValidGroup(getRow(board, row)))
                && IntStream.range(0, 9).allMatch(col -> isValidGroup(getColumn(board, col)))
                && IntStream.range(0, 9).allMatch(i -> {
            int startRow = (i / 3) * 3;
            int startCol = (i % 3) * 3;
            return isValidGroup(getSubgrid(board, startRow, startCol));
        });
    }

    private static boolean isValidGroup(IntStream groupStream) {
        int[] group = groupStream.toArray();
        boolean[] seen = new boolean[10];
        for (int num : group) {
            if (num < 1 || num > 9 || seen[num]) {
                return false;
            }
            seen[num] = true;
        }
        return true;
    }

    static IntStream getRow(int[][] board, int row) {
        return Arrays.stream(board[row]);
    }

    static IntStream getColumn(int[][] board, int col) {
        return IntStream.range(0, 9).map(row -> board[row][col]);
    }

    static IntStream getSubgrid(int[][] board, int startRow, int startCol) {
        return IntStream.range(0, 9).map(i -> board[startRow + i / 3][startCol + i % 3]);
    }

    static boolean isValidFile(List<String> lines) {
        if (lines.size() != 9) {
            return false;
        }

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length != 9) {
                return false;
            }
            for (String part : parts) {
                try {
                    int num = Integer.parseInt(part.trim());
                    if (num < 1 || num > 9) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java -jar validator-sudoku-1.0.jar <file1> <file2> ...");
            return;
        }

        for (String filePath : args) {
            System.out.println("Validating file: " + filePath);
            try {
                List<String> lines = Files.readAllLines(Paths.get(filePath));
                if (!isValidFile(lines)) {
                    System.out.println(filePath + ": [0] INVALID FILE");
                    continue;
                }

                int[][] board = lines.stream()
                        .map(line -> Arrays.stream(line.split(","))
                                .mapToInt(Integer::parseInt)
                                .toArray())
                        .toArray(int[][]::new);

                System.out.println(filePath + ": " + (isValidSudoku(board) ? "[1] VALID" : "[0] INVALID"));
            } catch (IOException e) {
                System.err.println("Error reading file " + filePath + ": " + e.getMessage());
            }
        }
    }
}
