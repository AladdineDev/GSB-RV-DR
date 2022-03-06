<img src="app/src/main/resources/logo.png" align="right" width="200px"/>

GSB RV DR [![Version](https://img.shields.io/badge/version-1.0.0-2fba00.svg?style=flat-square)](#readme) [![Licence](https://img.shields.io/badge/licence-MIT-2fba00.svg?style=flat-square)](https://github.com/Aaldn/GSB-RV-DR/blob/master/LICENSE.md)
========================

GSB RV DR – par [@Aaldn](https://github.com/Aaldn)

[![Java 17](https://img.shields.io/badge/Java-17-0074bd.svg?style=flat-square&logo=java)](https://openjdk.java.net/) [![JavaFX 17](https://img.shields.io/badge/JavaFX-17-53829e.svg?style=flat-square&logo=java)](https://openjfx.io/) [![Gradle 7](https://img.shields.io/badge/Gradle-7.6-02303a.svg?style=flat-square&logo=gradle)](https://gradle.org/) [![Mariadb 10](https://img.shields.io/badge/MariaDB-10.5-c0765a.svg?style=flat-square&logo=mariadb)](https://mariadb.org/) 

Application d'enregistrement et de suivi des rapports de visite.

<img src="screenshots/Connexion.png" width="49.5%"></img> <img src="screenshots/Rapports.png" width="48.5%"></img> 

### Documentation

  * [Documentation utilisateur](docs/Documentation-Utilisateur.pdf) _(À venir)_
  * [Documentation technique](docs/Documentation-Technique.pdf) _(À venir)_

### Contexte

1. [GSB - Fiche descriptive](docs/01-GSB-AppliRV-FicheDescriptive.pdf)
2. [GSB - Cas d'utilisation](docs/02-GSB-AppliRV-DR-UC.pdf)
3. [GSB - Modèle Entité-Association](docs/03-GSB-AppliRV-MEA.pdf)

### Prérequis

  * [Java](http://jdk.java.net/17/) (+ définition de la variable d'environnement [JAVA_HOME](https://www.baeldung.com/java-home-on-windows-7-8-10-mac-os-x-linux#1-single-user))

  * [MariaDB](https://mariadb.org/download/?t=mariadb&o=true&p=mariadb&r=10.5.12&os=Linux&cpu=x86_64&i=systemd)

> En cas de difficulté, reportez-vous à la documentation officielle de [JavaFX](https://openjfx.io/openjfx-docs/) et de [MariaDB](https://mariadb.com/kb/en/documentation/).

## Installation

Tout d'abord, clonez ce dépôt puis placez-vous au sein du projet :

```bash
$ git clone https://github.com/Aaldn/GSB-RV-DR
$ cd GSB-RV-DR
```

Ensuite, créez la base de données et exécutez le script de peuplement :

```bash
$ mariadb -e "source sql/gsbrv.sql; source sql/peupler_gsbrv.sql;"
```

Enfin, lancez l'application :

```bash
$ ./gradlew run
```

## Licence

Voir le fichier [LICENSE.md](https://github.com/Aaldn/GSB-RV-DR/blob/master/LICENSE.md) fourni.