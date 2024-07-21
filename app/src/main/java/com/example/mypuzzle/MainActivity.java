package com.example.mypuzzle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TableLayout puzzleGrid;
    private Button[][] buttons;
    private int[][] board;
    private int numMoves;
    private CheckBox testCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        puzzleGrid = findViewById(R.id.puzzleGrid);
        buttons = new Button[4][4];
        board = new int[4][4];
        numMoves = 0;
        testCheckBox = findViewById(R.id.testCheckBox);

        initializeButtons();

        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (testCheckBox.isChecked()) {
                    initializeEasyBoard();
                } else {
                    initializeRandomBoard();
                }
                updateButtons();
            }
        });
    }

    private void initializeEasyBoard() {
        int[][] easyBoard = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {0, 13, 14, 15}
        };

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = easyBoard[i][j];
            }
        }
        numMoves = 0;
    }

    private void initializeRandomBoard() {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = numbers.get(index++);
            }
        }
        numMoves = 0;
    }

    private void initializeButtons() {
        for (int i = 0; i < 4; i++) {
            TableRow tableRow = new TableRow(this);
            for (int j = 0; j < 4; j++) {
                buttons[i][j] = new Button(this);
                buttons[i][j].setLayoutParams(new TableRow.LayoutParams());
                buttons[i][j].setTextSize(40);
                int row = i;
                int col = j;
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle button click
                        if (isValidMove(row, col)) {
                            makeMove(row, col);
                            updateButtons();
                            if (isSolved()) {
                                Toast.makeText(MainActivity.this, "Well done! You solved the puzzle in " + getMoves() + " moves!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
                tableRow.addView(buttons[i][j]);
            }
            puzzleGrid.addView(tableRow);
        }
        updateButtons();
    }

    private void updateButtons() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == 0) {
                    buttons[i][j].setText("");
                } else {
                    buttons[i][j].setText(String.valueOf(board[i][j]));
                }
            }
        }
    }

    private boolean isValidMove(int row, int col) {
        // Check if the move is valid
        if ((row > 0 && board[row - 1][col] == 0) ||
                (row < 3 && board[row + 1][col] == 0) ||
                (col > 0 && board[row][col - 1] == 0) ||
                (col < 3 && board[row][col + 1] == 0)) {
            return true;
        }
        return false;
    }

    private void makeMove(int row, int col) {
        if (row > 0 && board[row - 1][col] == 0) {
            board[row - 1][col] = board[row][col];
            board[row][col] = 0;
        } else if (row < 3 && board[row + 1][col] == 0) {
            board[row + 1][col] = board[row][col];
            board[row][col] = 0;
        } else if (col > 0 && board[row][col - 1] == 0) {
            board[row][col - 1] = board[row][col];
            board[row][col] = 0;
        } else if (col < 3 && board[row][col + 1] == 0) {
            board[row][col + 1] = board[row][col];
            board[row][col] = 0;
        }
        numMoves++;
    }

    private boolean isSolved() {
        int num = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 3 && j == 3) return true;
                if (board[i][j] != num++) return false;
            }
        }
        return true;
    }

    private int getMoves() {
        return numMoves;
    }
}
