package com.miniprojet4.Serveurs;

import com.miniprojet4.Obj.*;
import org.json.JSONObject;

public class ServeurJeu {

    public static void main(String[] args) {
        try {

            ServeurTCP serveur = new ServeurTCP(56263);
            serveur.attendreClient();
            System.out.println("Client connecté");

            Morpion morpion = new Morpion();
            boolean tourX = true;

            morpion.afficherGrille();

            if (serveur.recevoir().equals("START")) {
                JSONObject jsonMorpion = new JSONObject();
                jsonMorpion.put("grille", morpion.afficherGrilleString());
                serveur.envoyer(jsonMorpion.toString());

                while (true) {
                    String position = serveur.recevoir().toString();
                    System.out.println("Message reçu : " + position);

                    int pos = Integer.parseInt(position) - 1;
                    Morpion.Cellule joueur = tourX ? Morpion.Cellule.X : Morpion.Cellule.O;
                    boolean valide = morpion.jouer(pos, joueur);
                    if (!valide) {
                        serveur.envoyer("INCORRECTE");
                        continue;
                    }

                    if (morpion.verifierGagnant(joueur)) {
                        jsonMorpion.put("grille", morpion.afficherGrilleString());
                        jsonMorpion.put("status", "WIN");
                        serveur.envoyer(jsonMorpion.toString());
                        morpion.reset();
                    } else if (morpion.isGrillePleine()) {
                        jsonMorpion.put("grille", morpion.afficherGrilleString());
                        jsonMorpion.put("status", "EGALITE");
                        serveur.envoyer(jsonMorpion.toString());
                        morpion.reset();
                    } else {
                        jsonMorpion.put("grille", morpion.afficherGrilleString());
                        jsonMorpion.put("status", "CONTINUE");
                        serveur.envoyer(jsonMorpion.toString());

                        tourX = !tourX;
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Erreur serveur : " + e.getMessage());
        }
    }
}
