# POC_MedHead

Ce repository est une Proof Of Concept (POC) pour vérifier la faisabilité d'une création d'un service de rendes-vous d'urgence pour trouver l'hôpital le plus proche d'une position donnée avec un lit disponible dans une spécialité donnée.

####Seuls un début de front (app) et un service users (api) sont disponibles pour le moment.

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

Télécharger le fichier "england-latest.osm.pbf" à l'adresse suivante: https://download.openstreetmap.fr/extracts/europe/united_kingdom/
Puis placer le dans le module ms-localize: src/main/resources/

### Execution
1. Dans l'éditeur de code Java, lancer l'application 'MsUsersApplication'.
2. Dans un autre terminal, se rendre dans l'application front 'app', lancer la commande suivante :
```bash
npm run start
```

