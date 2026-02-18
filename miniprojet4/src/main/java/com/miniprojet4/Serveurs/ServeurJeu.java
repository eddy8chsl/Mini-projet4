package com.miniprojet4.Serveurs;

import org.json.JSONObject;

import com.miniprojet4.Obj.Morpion;
import com.miniprojet4.Obj.ServeurTCP;

public class ServeurJeu {

    public static void main(String[] args) {
        try {
            ServeurTCP serveur = new ServeurTCP(56263);
            serveur.attendreClient();
            System.out.println("Client connecté");

            Object messageStart = serveur.recevoir();
            System.out.println("Message start reçu: " + messageStart);

            Morpion morpion = new Morpion();
            boolean tourX = true;

            morpion.afficherGrille();

            JSONObject grilleInitiale = new JSONObject();
            grilleInitiale.put("grille", morpion.afficherGrilleString());
            grilleInitiale.put("status", "CONTINUE");
            serveur.envoyer(grilleInitiale.toString());

            while (true) {
                String jsonString = serveur.recevoir().toString();
                System.out.println("Message reçu : " + jsonString);

                JSONObject json = new JSONObject(jsonString);
                int pos = json.getInt("position");

                boolean valide = morpion.jouer(pos, tourX ? Morpion.Cellule.X : Morpion.Cellule.O);

                JSONObject reponse = new JSONObject();
                reponse.put("grille", morpion.afficherGrilleString());

                if (!valide) {
                    reponse.put("status", "INCORRECTE");
                    serveur.envoyer(reponse.toString());
                    continue;
                }

                morpion.afficherGrille();

                if (morpion.verifierGagnant(tourX ? Morpion.Cellule.X : Morpion.Cellule.O)) {
                    reponse.put("status", "WIN");
                    serveur.envoyer(reponse.toString());
                    System.out.println("Partie terminée - Victoire !");
                    break;
                } else if (morpion.isGrillePleine()) {
                    reponse.put("status", "EGALITE");
                    serveur.envoyer(reponse.toString());
                    System.out.println("Partie terminée - Égalité !");
                    break;
                } else {
                    reponse.put("status", "CONTINUE");
                    serveur.envoyer(reponse.toString());
                    tourX = !tourX;
                }
            }

            serveur.fermerClient();

        } catch (Exception e) {
            System.err.println("Erreur serveur : " + e.getMessage());
            e.printStackTrace();
        }
    }

}
