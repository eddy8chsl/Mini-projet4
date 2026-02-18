package com.miniprojet4.Serveurs;

import org.json.JSONObject;

import com.miniprojet4.Obj.ClientTCP;
import com.miniprojet4.Obj.ServeurTCP;

public class Middleware {

    private static final int PORT_MIDDLEWARE = 5100;

    public void demarrer() {
        System.out.println("Middleware démarré sur " + PORT_MIDDLEWARE);
        try {
            ServeurTCP serveur = new ServeurTCP(PORT_MIDDLEWARE);
            ClientTCP middleware = new ClientTCP("127.0.0.1", 56263);

            serveur.attendreClient();
            Object messageStart = serveur.recevoir();

            if (messageStart.toString().equals("START")) {
                middleware.send(messageStart);
                Object grilleInitiale = middleware.receive();
                serveur.envoyer(grilleInitiale);

                while (true) {
                    Object positionClient = serveur.recevoir();

                    JSONObject jsonPosition = new JSONObject();
                    jsonPosition.put("position", Integer.parseInt(positionClient.toString()) - 1);

                    middleware.send(jsonPosition.toString());
                    Object reponse = middleware.receive();
                    System.out.println("Reponse du serveur: " + reponse);

                    serveur.envoyer(reponse);

                    try {
                        JSONObject jsonReponse = new JSONObject(reponse.toString());
                        String status = jsonReponse.getString("status");
                        if (status.equals("WIN") || status.equals("EGALITE")) {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Erreur lors de l'analyse de la réponse: " + e.getMessage());
                    }
                }
                serveur.fermerClient();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Middleware().demarrer();
    }
}
