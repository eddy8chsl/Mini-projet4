package com.miniprojet4.Serveurs;

import com.miniprojet4.Obj.*;


public class Client {

    public static void main(String[] args) {
        try {
            ClientTCP client = new ClientTCP("localhost", 56263);
            client.send("dffd");


            // while (true) {
            //     String message = "0"; // Simuler une position de jeu
            //     client.envoyer(message);
            //     Object response = client.recevoir();
            //     if (response == null)
            //         break;
            //     System.out.println("Réponse du serveur : " + response.toString());
            // }

        } catch (Exception e) {
            System.err.println("Erreur client : " + e.getMessage());
        }
    }

}
