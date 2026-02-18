package com.miniprojet4.Obj;

public class Morpion {

    public enum Cellule {
        VIDE {
            @Override
            public String toString() {
                return "#";
            }
        },
         X, O
    }

    private Cellule[] grille = new Cellule[9];
    private boolean gameOver = false;

    public Morpion() {
        reset();
    }

    public void reset() {
        for (int i = 0; i < 9; i++)
            grille[i] = Cellule.VIDE;
        gameOver = false;
    }

    public boolean isGrillePleine() {
        for (Cellule c : grille)
            if (c == Cellule.VIDE)
                return false;
        return true;
    }

    public Cellule getCellule(int pos) {
        return grille[pos];
    }

    public boolean jouer(int pos, Cellule joueur) {
        if (pos < 0 || pos >= 9 || grille[pos] != Cellule.VIDE || gameOver)
            return false;
        grille[pos] = joueur;
        if (verifierGagnant(joueur))
            gameOver = true;
        return true;
    }

    public boolean verifierGagnant(Cellule joueur) {
        int[][] lignes = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, { 0, 4, 8 },
                { 2, 4, 6 } };
        for (int[] ligne : lignes) {
            if (grille[ligne[0]] == joueur && grille[ligne[1]] == joueur && grille[ligne[2]] == joueur) {
                return true;
            }
        }
        return false;
    }

    public void afficherGrille() {
        for (int i = 0; i < 9; i++) {
            System.out.print(grille[i] + " ");
            if (i % 3 == 2)
                System.out.println();
        }
    }

    public String afficherGrilleString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            sb.append(grille[i]).append(" ");
            if (i % 3 == 2)
                sb.append("\n");
        }
        return sb.toString();
    }

}
