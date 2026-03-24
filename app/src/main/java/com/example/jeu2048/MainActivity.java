package com.example.jeu2048;

import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.MotionEvent;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private TextView[][] cells = new TextView[4][4];
    private int[][] grid = new int[4][4];

    // ✅ Une seule variable utilisée → plus de warning
    private float startX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);

        createGrid();
        initializeGame();
        setupSwipe();
    }

    /**
     * Création de la grille 4x4
     */
    private void createGrid() {

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {

                TextView textView = new TextView(this);

                textView.setText("");
                textView.setTextSize(24);
                textView.setGravity(Gravity.CENTER);
                textView.setBackgroundColor(Color.LTGRAY);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = 0;
                params.rowSpec = GridLayout.spec(row, 1f);
                params.columnSpec = GridLayout.spec(col, 1f);
                params.setMargins(8, 8, 8, 8);

                textView.setLayoutParams(params);

                cells[row][col] = textView;
                gridLayout.addView(textView);
            }
        }
    }

    /**
     * Initialise la grille
     */
    private void initializeGame() {

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                grid[row][col] = 0;
            }
        }

        addRandomTile();
        addRandomTile();

        updateUI();
    }

    /**
     * Ajoute une tuile aléatoire
     */
    private void addRandomTile() {

        Random random = new Random();
        int row, col;

        do {
            row = random.nextInt(4);
            col = random.nextInt(4);
        } while (grid[row][col] != 0);

        grid[row][col] = random.nextInt(10) < 9 ? 2 : 4;
    }

    /**
     * Met à jour l'affichage
     */
    private void updateUI() {

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {

                int value = grid[row][col];

                if (value == 0) {
                    cells[row][col].setText("");
                } else {
                    cells[row][col].setText(String.valueOf(value));
                }
            }
        }
    }

    /**
     * Déplacement vers la gauche
     */
    private void moveLeft() {
        boolean moved = false;

        for (int row = 0; row < 4; row++) {
            int[] newRow = new int[4];
            int position = 0;

            for (int col = 0; col < 4; col++) {
                if (grid[row][col] != 0) newRow[position++] = grid[row][col];
            }

            for (int col = 0; col < 3; col++) {
                if (newRow[col] != 0 && newRow[col] == newRow[col + 1]) {
                    newRow[col] *= 2;
                    newRow[col + 1] = 0;
                    moved = true;
                }
            }

            int[] finalRow = new int[4];
            position = 0;
            for (int col = 0; col < 4; col++) {
                if (newRow[col] != 0) finalRow[position++] = newRow[col];
            }

            if (!java.util.Arrays.equals(grid[row], finalRow)) moved = true;
            grid[row] = finalRow;
        }

        if (moved) {
            addRandomTile();
            updateUI();
        }
    }
    /**
     * Gestion du swipe (gauche uniquement)
     */
    private void setupSwipe() {
        gridLayout.setOnTouchListener((v,event) -> {
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    startX = event.getX();
                    break;

                case MotionEvent.ACTION_UP:
                    float endX = event.getX();
                    float dx = endX - startX;

                    if (dx < 0) {
                        moveLeft();
                    }
                    break;
            }

            return true;
        });
    }
}