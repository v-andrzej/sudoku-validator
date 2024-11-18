package org.example;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class SudokuValidatorTest {

    @Test
    void testValidSudoku() {
        int[][] validBoard = {
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };
        assertTrue(SudokuValidator.isValidSudoku(validBoard));
    }

    @Test
    void testInvalidSudokuDuplicateInRow() {
        int[][] invalidBoard = {
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };
        invalidBoard[0][0] = 6;
        assertFalse(SudokuValidator.isValidSudoku(invalidBoard));
    }

    @Test
    void testInvalidSudokuDuplicateInSubgrid() {
        int[][] invalidBoard = {
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };
        invalidBoard[4][4] = 1;
        assertFalse(SudokuValidator.isValidSudoku(invalidBoard));
    }

    @Test
    void testIsValidFileValid() {
        List<String> validLines = Arrays.asList(
                "5,3,4,6,7,8,9,1,2",
                "6,7,2,1,9,5,3,4,8",
                "1,9,8,3,4,2,5,6,7",
                "8,5,9,7,6,1,4,2,3",
                "4,2,6,8,5,3,7,9,1",
                "7,1,3,9,2,4,8,5,6",
                "9,6,1,5,3,7,2,8,4",
                "2,8,7,4,1,9,6,3,5",
                "3,4,5,2,8,6,1,7,9"
        );
        assertTrue(SudokuValidator.isValidFile(validLines));
    }

    @Test
    void testIsValidFileInvalidRowCount() {
        List<String> invalidLines = Arrays.asList(
                "5,3,4,6,7,8,9,1,2",
                "6,7,2,1,9,5,3,4,8",
                "1,9,8,3,4,2,5,6,7"
        );
        assertFalse(SudokuValidator.isValidFile(invalidLines));
    }

    @Test
    void testIsValidFileInvalidColumnCount() {
        List<String> invalidLines = Arrays.asList(
                "5,3,4,6,7,8,9,1,2",
                "6,7,2,1,9,5,3,4",
                "1,9,8,3,4,2,5,6,7",
                "8,5,9,7,6,1,4,2,3"
        );
        assertFalse(SudokuValidator.isValidFile(invalidLines));
    }

    @Test
    void testGetRow() {
        int[][] board = {
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {9, 8, 7, 6, 5, 4, 3, 2, 1}
        };
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, SudokuValidator.getRow(board, 0).toArray());
    }

    @Test
    void testGetSubgrid() {
        int[][] board = {
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {9, 8, 7, 6, 5, 4, 3, 2, 1},
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {9, 8, 7, 6, 5, 4, 3, 2, 1}
        };
        int[] expectedSubgrid = {1, 2, 3, 9, 8, 7, 1, 2, 3};
        assertArrayEquals(expectedSubgrid, SudokuValidator.getSubgrid(board, 0, 0).toArray());
    }

    @Test
    public void testMain_WithNoArgs() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String[] args = {};
        SudokuValidator.main(args);

        assertTrue(outContent.toString().contains("Usage: java -jar validator-sudoku-1.0.jar <file1> <file2> ..."));
    }
}