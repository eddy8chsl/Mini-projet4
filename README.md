# Mini projet 4 - Architectures répartie

**Groupe :** Eddy et Alisa

## Description du Projet

Pour ce mini-projet 4, nous avons développé une version multijoueur du jeu **Morpion** (Tic-Tac-Toe) en architecture client-serveur.

### Technologies utilisées

- **Langage :** Java
- **Protocole de communication :** TCP
- **Format des données :** JSON (sérialisation avec `JSONObject`)
- **Composants principaux :**
  - `ClientTCP.java` : Gestion de la connexion client
  - `ServeurTCP.java` : Gestion de la connexion serveur
  - `Middleware.java` : Gestion de communication entre le serveur et client
  - `Morpion.java` : Logique du jeu

---

## Répartition des tâches:

- ServeurJeu -> Eddy et Alisa
- Client -> Eddy
- Objet Morpion -> Alisa

## Graphe Du projet

![graphe](/miniprojet4/src/main/resources/img/graphe.png "Graphe du mini projet 4")

## Client[56263]

- Deux utilisateurs U1 et U2 se connectent au client C afin de jouer au jeu « Morpion ».
- Chaque joueur envoie un chiffre compris entre 1 et 9, correspondant à la position d’une case dans la grille.
- Un joueur gagne s’il aligne trois symboles identiques (X ou O) verticalement, horizontalement ou en diagonale.
- Si toutes les cases de la grille sont occupées sans qu’aucun joueur n’ait réalisé d’alignement, la partie se termine par une égalité.

## ServeurJeu[56263] (Morpion)

- Connexion au client afin d’initialiser la grille du jeu « Morpion ».
- Le serveur attribue le rôle de joueur 1 ou joueur 2 en fonction du tour.
- À chaque tour, le serveur vérifie si un joueur remplit les conditions de victoire ou déclare une égalité si la grille est entièrement remplie.

## Middleware[5100]

- Connection entre le Client et Serveur

## Règles du Jeu

Deux joueurs s'affrontent sur une grille de 3×3 :

- **Joueur 1 (User 1)** utilise le symbole **X**
- **Joueur 2 (User 2)** utilise le symbole **O**
- Chaque joueur choisit une case en saisissant un chiffre entre **1 et 9**
- Les cases vides sont représentées par le symbole **VIDE**

### Conditions de victoire

- **Victoire :** Aligner trois symboles identiques (verticalement, horizontalement ou en diagonale)
- **Égalité :** Toutes les cases sont remplies sans alignement gagnant

### Disposition de la grille

```
1 | 2 | 3
---------
4 | 5 | 6
---------
7 | 8 | 9
```

_Remplacer par des "#" dans le jeu_

---

## Comment Jouer ?

### Étapes de lancement

1. **Démarrer le serveur :**

   ```bash
   java com.miniprojet4.Serveurs.ServeurJeu
   ```

   Le serveur attend maintenant la connexion d'un client sur le port 56263.

2. **Démarrer le client :**

   ```bash
   java com.miniprojet4.Client
   ```

   Le client se connecte automatiquement au serveur.

3. **Jouer :**
   - Les instructions s'affichent dans le terminal
   - User 1 et User 2 jouent à tour de rôle
   - Saisissez un chiffre entre 1 et 9 pour choisir une case
   - La grille se met à jour après chaque coup
   - Le jeu se termine lorsqu'un joueur gagne ou en cas d'égalité

---

## Notes

- Le joueur 1 commence toujours avec le symbole **X**
- Les coups invalides (case déjà occupée ou hors limite) sont rejetés et le joueur doit rejouer
