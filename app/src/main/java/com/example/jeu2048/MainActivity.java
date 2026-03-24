package com.example.jeu2048;

import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.view.Gravity;

import com.example.jeu2048.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Grille visuelle
    private GridLayout gridLayout;

    // Tableau 2D contenant les TextView (affichage)
    private TextView[][] cells = new TextView[4][4];

    // Tableau 2D contenant les valeurs numériques (logique du jeu)
    private int[][] grid = new int[4][4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Lie le layout XML à l'Activity
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);

        // Création des 16 cases visuelles
        createGrid();

        // Initialisation du jeu
        initializeGame();
    }

    /**
     * Création dynamique de la grille 4x4
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
     * Initialise la grille logique et ajoute 2 tuiles
     */
    private void initializeGame() {

        // Remet toutes les cases à 0
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                grid[row][col] = 0;
            }
        }

        // Ajouter 2 tuiles au démarrage
        addRandomTile();
        addRandomTile();

        // Synchroniser affichage
        updateUI();
    }

    /**
     * Ajoute une tuile 2 (90%) ou 4 (10%) dans une case vide
     */
    private void addRandomTile() {

        Random random = new Random();
        int row, col;

        // Cherche une case vide
        do {
            row = random.nextInt(4);
            col = random.nextInt(4);
        } while (grid[row][col] != 0);

        // 90% de chance d'avoir 2, 10% d'avoir 4
        grid[row][col] = random.nextInt(10) < 9 ? 2 : 4;
    }

    /**
     * Met à jour l'affichage en fonction du tableau logique
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
}