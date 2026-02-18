package com.miniprojet4;

import com.miniprojet4.Obj.ClientTCP;
import com.miniprojet4.Obj.Morpion;
import java.util.Scanner;

import org.json.JSONObject;

public class Client {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            ClientTCP jeu = new ClientTCP("127.0.0.1", 56263);
           
            jeu.send("START");
            String reponse = (String) jeu.receive();
            if (reponse.startsWith("ERROR")) {
                System.out.println("Aucun serveur disponible.");
                return;
            }

            JSONObject grilleJson = new JSONObject(reponse);
            System.out.println(grilleJson.getString("grille"));
           
            
            boolean fini = false;
            int essai = 1;
            int user = 1;
            System.out.println("Instructions : Envoyez un chiffre de 1 à 9 pour sélectionner une case (1 = haut-gauche, 2 = haut-milieu, etc.).");
            
            while (!fini) {
                System.out.print("User " + user + " > ");
                String proposition = sc.nextLine();

                try {
                    int choix = Integer.parseInt(proposition);
                    if (choix < 1 || choix > 9) {
                        System.out.println("Veuillez entrer un chiffre entre 1 et 9 : ");
                        continue;
                    }
                    proposition = String.valueOf(choix);
                } catch (NumberFormatException e) {
                    System.out.println("Veuillez entrer un chiffre valide.");
                    continue;
                }
                
                jeu.send(proposition);
                String retour = (String) jeu.receive();

                if (retour.equals("INCORRECTE")) {
                    System.out.println("Case déjà jouée ou invalide. Réessayez.");
                } else {
                    JSONObject reponseJson = new JSONObject(retour);
                    String status = reponseJson.getString("status");
                    String grille = reponseJson.getString("grille");
                    
                    System.out.println("\n" + grille);
                    
                    if (status.equals("WIN")) {
                        System.out.println("BRAVO ! User " + user + " a gagné !");
                        fini = true;
                    } else if (status.equals("EGALITE")) {
                        System.out.println("Égalité ! Match nul.");
                        fini = true;
                    } else if (status.equals("CONTINUE")) {
                        System.out.println("Round " + essai);
                        essai++;
                        if (user == 1) {
                            user = 2;
                        } else {
                            user = 1;
                        }
                    }
                }
            }
            jeu.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
