# POC_MedHead
![Java CI - ms-users](https://github.com/Ella-dee/MedHeadPoc/actions/workflows/ms-users_ci-cd.yml/badge.svg) ![Java CI - ms-localize](https://github.com/Ella-dee/MedHeadPoc/actions/workflows/ms-localize_ci-cd.yml/badge.svg) ![Angular ci - app ](https://github.com/Ella-dee/MedHeadPoc/actions/workflows/app-ci.yml/badge.svg)

Ce repository est une Proof Of Concept (POC) pour vérifier la faisabilité d'une création d'un service de rendes-vous d'urgence pour trouver l'hôpital le plus proche d'une position donnée avec un lit disponible dans une spécialité donnée.

## Prérequis au projet
Afin de pouvoir exécuter l'application sur votre poste, vous devez d'abord installer les composants suivant :
* JVM version 17
* Apache Maven 3.9.1
* Angular 3.16
* Pg Admin 4

## Executer le projet
### Installation
Créer une nouvelle base de données sous PgAdmin nommée 'medhead-users'.
Créer une nouvelle base de données sous PgAdmin nommée 'medhead-hospitals'.

### Execution avec IDE
1. Dans l'éditeur de code Java, lancer les applications 'MsUsersApplication' et 'MsLocalizeApplication'.
2. Dans un autre terminal, se rendre à la racine de l'application front 'app', lancer la commande suivante :
```bash
npm run start
```
3. Dans le navigateur, se rendre à l'adresse localhost:4200

### Execution avec artefacts
#### Compilation du projet en local
1. Démarrer Docker
2. Dans l'éditeur de code Java, se placer à la racine de chaque microservice et lancer la commande:
```bash
mvn clean package
```
Les microservices sont packagés en .jar dans le dossier 'target'.
3. Dans un autre terminal, se rendre à la racine de l'application front 'app', lancer la commande suivante :
```bash
npm run build
```
L'application est compilée en plusieurs fichiers .js dans le dossier 'dist'.
#### Téléchargement des artefacts
1. Dans github Actions, se rendre dans les builds de ms-users et ms-localize et récupérer les artifacts. 
2. Les dézipper dans un répertoire.
#### Lancement des artefacts
1. Ouvrir deux terminaux à la racine où se trouvent les jar des microservices java
2. Lancer les commandes suivantes:
```bash
java -jar NOMduJAR.jar
```
3. Dans un autre terminal se rendre à la racine du front, lancer la commande suivante:
```bash
npm run start
```

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
   npm run test
```
Pour les tests E2E, il faut démarrer les deux microservices Springboot et l'application front.
Puis dans un terminal à la racine de l'application front end lancer les commandes suivantes:
```bash
   npx cypress open //ouvre l'interface cypress
   npx cypress run --browser chrome //lance cypress et ses résultats dans la console
```
Choisir "E2E Testing" et un navigateur.


