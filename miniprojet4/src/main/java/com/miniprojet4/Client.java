package com.miniprojet4;

import com.miniprojet4.Obj.ClientTCP;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            ClientTCP jeu = new ClientTCP("127.0.0.1", 5100);
           
            jeu.send("START");
            String reponse = (String) jeu.receive();
            if (reponse.startsWith("ERROR")) {
                System.out.println("Aucun serveur disponible.");
                return;
            }

            System.out.println(reponse);
            
            boolean fini = false;
            int essai = 1;
            int user = 1;
            System.out.println("Instructions : Envoyez un chiffre de 1 à 9 pour sélectionner une case (1 = haut-gauche, 2 = haut-milieu, etc.).");
            
            while (!fini) {
                //JSONObject userJson = new JSONObject(propositions);
                System.out.print("User " + user + " > ");
                String proposition = sc.nextLine();

                try {
                    int choix = Integer.parseInt(proposition);
                    if (choix < 1 || choix > 9) {
                        System.out.println("Veuillez entrer un chiffre entre 1 et 9 (un emplacement disponible) : ");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Veuillez entrer un chiffre valide.");
                    continue;
                }
                
                jeu.send(proposition);
                String retour = (String) jeu.receive();
                
                if (retour.startsWith("INCORRECTE")) {
                    System.out.println("Case déjà jouée ou invalide. Réessayez.");
                } else if (retour.startsWith("WIN")) {
                    System.out.println("BRAVO ! User" + user + "Vous avez gagnez ! : " + retour.split(":")[1]);
                    fini = true;
                } else if (retour.startsWith("LOSE")) {
                    String[] data = retour.split(":");
                    System.out.println("PERDU... " + data[1] + ", Score consolation : " + data[2]);
                    fini = true;
                } else if (retour.startsWith("EGALITE")) {
                    //String[] data = retour.split(":");
                    System.out.println("Egalité ! ");
                    fini = true;
                } else if (retour.startsWith("CONTINUE")) {
                    System.out.println("Round " + essai  + " : " + retour.split(":")[1]);
                    essai++;
                    if (user == 1 ) {
                        user++;
                    } else {
                        user--;
                    }
                }
            }
            jeu.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
