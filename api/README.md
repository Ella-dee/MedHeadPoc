# POC_MedHead

Ce repository est une Proof Of Concept (POC) pour vérifier la faisabilité d'une création d'un service de rendes-vous d'urgence pour trouver l'hôpital le plus proche d'une position donnée avec un lit disponible dans une spécialité donnée.

## Prérequis au projet
Afin de pouvoir exécuter l'application sur votre poste, vous devez d'abord installer les dépendances suivantes :
* JVM version 17
* Apache Maven 3.9.1
* Angular 3.16
* Pg Admin 4

## Executer le projet
### Installation
Créer une nouvelle base de données sous PgAdmin nommée 'medhead-users'.
Créer une nouvelle base de données sous PgAdmin nommée 'medhead-hospitals'.

### Execution
1. Dans l'éditeur de code Java, lancer les applications 'MsUsersApplication' et 'MsLocalizeApplication'.
2. Dans un autre terminal, se rendre à la racine de l'application front 'app', lancer la commande suivante :
```bash
npm run start
```
3. Dans le navigateur, se rendre à l'adresse localhost:4200

### Utilisation
Parcours utilisateur:
1. création de compte (pour un nouvel utilisateur)
2. login
3. enregistrer les informations patient (adresse anglaise suggérée dans le formulaire)
4. affichage profil si l'adresse patient est trouvée/formulaire à resoumettre sinon
5. retour à la page d'accueil pour faire une recherche
6. choisir un groupe de spécialité et une spécialité, cliquer sur recherche
7. la liste des hopitaux s'affiche dans l'ordre de proximité avec possibilité de réserver un lit

## Executer les tests
Les tests sont exécutés automatiquement à chaque push sur le repo vie la pipeline créée en actions github.
Chaque microservice possède sa pipeline. Le résultat des tests est visible sur sonarcloud.io.
Pour les exécuter en local:
### Back End (API)
1. Lancer Docker
2. Se rendre dans l'arborescence racine API du projet dans l'IDE, lancer la commande suivante:
```bash
   mvn test
   ```
### Front End (APP)
Se rendre dans l'arborescence racine APP du projet dans l'IDE, lancer la commande suivante:
```bash
   ng test
```
Pour les tests E2E lancer la commande suivante:
```bash
   npx cypress open
```

