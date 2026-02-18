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

            // Initialiser le jeu et envoyer un message de bienvenue
            serveur.envoyer("non_bienvenue");

            var jsonMorpion = new JSONObject(morpion);

            serveur.envoyer(jsonMorpion.toString());


            while (true) {

                // Recevoir le message du client

                String jsonString = serveur.recevoir().toString();
                System.out.println("Message reçu : " + jsonString);

                JSONObject json = new JSONObject(jsonString);

                int pos = json.getInt("position");

                boolean valide = morpion.jouer(pos, tourX ? Morpion.Cellule.X : Morpion.Cellule.O);
                if (!valide) {
                    // serveur.envoyerMessage("INCORRECTE");
                    continue;
                }

                if (morpion.isGrillePleine()) {
                    // serveur.envoyerMessage("EGALITE");
                    morpion.reset();
                } else if (morpion.jouer(pos, tourX ? Morpion.Cellule.X : Morpion.Cellule.O)) {
                    // serveur.envoyerMessage("WIN");
                    morpion.reset();
                } else {
                    // serveur.envoyerMessage("CONTINUE");
                }

                tourX = !tourX;
            }


        } catch (Exception e) {
            System.err.println("Erreur serveur : " + e.getMessage());
        }
    }

}
