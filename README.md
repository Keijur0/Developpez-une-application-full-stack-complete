# MDD App (OCR Projet 6)

## Introduction
Après avoir récupéré le projet sur GitHub, il faudra démarrer 3 éléments pour utiliser l'application:
- La base de données
- Le serveur backend (API)
- Le serveur frontend

## Pré-requis
Avant de récupérer le projet, il faudra avoir installé un certain nombre d'éléments au préablable:

### Serveur MySQL
Disponible à cette adresse:

`https://dev.mysql.com/downloads/installer/`

### Apache Maven
Disponible à cette adresse:

`https://maven.apache.org/download.cgi`

### Java Development Kit (JDK), comprenant Java Runtime Environment (JRE)
Disponible à cette adresse:

`https://www.oracle.com/java/technologies/downloads/`

Ce projet utilise Java version 11

### Node Package Manager (NPM)
Utiliser la commande `npm install` dans l'invite de commandes (cmd), en mode administrateur si besoin

## Récupérer le projet
Pour récupérer le projet, il est possible de télécharger le projet sur GitHub en format compressé (.zip), ou de le récupérer en le clonant à l'aide de cette commande (à condition que Git soit installé sur votre poste):

`git clone https://github.com/Keijur0/Developpez-une-application-full-stack-complete.git`

Décompresser l'archive (.zip) à l'emplacement souhaité si besoin.

## Mettre en place le serveur MySQL
Après avoir effectué l'installation de MySQL Server, il faut créer la variable d'environnement MySQL, qui pointe vers le répertoire `/bin` de MySQL Server.

### Se connecter à la base de données
Une fois effectué, aller dans l'invite de commandes et utilisez la commande suivante:

`mysql -u <nom d'utilisateur> -p`

Utilisez le nom d'utilisateur et le mot de passe que vous avez créé lors de l'installation de MySQL Server.

Vous aurez besoin de ces identifiants lors du lancement de l'API.

Je nommerai ces éléments `<nom d'utilisateur>` et  `<mot de passe>` dans la commande de lancement.

### Créer la base de données
Pour créer la base de données, utilisez la commande suivante:

`CREATE DATABASE mdd`

C'est le nom de la base de données indiquée dans les paramètres de l'API. Si vous souhaitez l'appeler autrement, il sera nécessaire d'ajuster les propriétés de l'API.

### Création des tables
Tout d'abord, quittez MySQL Server en tappant `exit` pour vous retrouver dans l'invite de commandes.

Tappez ensuite `mysql -u <nom d'utilisateur> -p mdd < <chemin/vers/le/fichier/script.sql>`

Puis tappez le `<mot de passe>` associé à ce compte utilisateur.

(Le fichier se trouve ici: `ressources\sql`)

### Vérification de la base
Pour vérifier si la base a été installée tappez:

`mysql -u <nom d'utilisateur> -p` puis Validez. Ensuite tappez le `<mot de passe>` pour vous connecter au serveur

`SHOW DATABASES;` pour vérifier si `mdd` se trouve parmi les bases existantes

`USE mdd;` pour choisir d'utiliser cette base

`SHOW TABLES;` pour montrer les tables de la base

`SHOW COLUMNS FROM <nom de la table>` pour voir le nom des colonnes de chaque table

## Installation des dépendances

### Frontend
Placez vous dans le dossier `/front` du projet, exécutez la commande `npm install`.

### Backend
Placez vous dans le dossier `/back` du projet, exécutez la commande `mvn clean install`.

## Lancer le projet Java (API / Backend)
Pour lancer le projet Java, il vous faut d'abord créer les variables d'environnement pour Java et Maven, pointant vers leurs dossiers respectifs `/bin`.

Ensuite, vous aurez besoin de générer une clé secrète à partir de ce site: `https://asecuritysite.com/encryption/plain`.

Choisissez une clé de `256-bit` et cliquez sur `Determine`.

La `Hex Key` sera la `<clé secrète>` dans la commande suivante.

Une fois effectué, placez-vous dans le dossier `/back` du projet et tappez la commande:

`mvn spring-boot:run -Dspring-boot.run.arguments="--spring.datasource.username=<nom d'utilisateur> --spring.datasource.password=<mot de passe> --secret.key=<clé secrète>"`

## Lancer le projet Angular (Frontend)
Pour lancer le projet Angular, placez-vous dans le dossier `/front` du projet et tappez la commande:

`npm run start` ou `ng serve`

## Utiliser l'application
Pour utiliser l'application, rendez vous à l'url suivante sur votre navigateur web:

`http://localhost:4200`

Il n'y a pas d'identifiants par défaut, vous pouvez créer votre propre compte utilisateur en vous inscrivant sur le site.

## Accéder à la documentation des endpoints

`http://localhost:9000/swagger-ui.html`