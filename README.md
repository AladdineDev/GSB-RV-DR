<img src="app/src/main/resources/logo.png" align="right" width="200px"/>

GSB RV DR [![Licence](https://img.shields.io/badge/licence-MIT-2fba00.svg?style=flat-square)](https://github.com/AladdineDev/GSB-RV-DR/blob/master/LICENSE.md)
========================

GSB Rapports Visite - module Délégué Régional – par [@AladdineDev](https://github.com/AladdineDev)

[![Java 17](https://img.shields.io/badge/Java-17-0074bd.svg?style=flat-square&logo=java)](https://openjdk.java.net/)
[![JavaFX 17](https://img.shields.io/badge/JavaFX-17-53829e.svg?style=flat-square&logo=java)](https://openjfx.io/)
[![Gradle 7](https://img.shields.io/badge/Gradle-7.3-02303a.svg?style=flat-square&logo=gradle)](https://gradle.org/)
[![Mariadb 10](https://img.shields.io/badge/MariaDB-10.5-c0765a.svg?style=flat-square&logo=mariadb)](https://mariadb.org/) 

Application de bureau de suivi des rapports de visite.
<div><img src="screenshots/3-Connexion.png" width="49%"></img> <img src="screenshots/9-Consultation Détaillée Rapport.png" width="49%"></div>
<div><img src="screenshots/11-Consultation Praticiens (1).png" width="49%"></img> <img src="screenshots/17-Quitter.png" width="49%"></img></div>

### Documentation

  * [Documentation utilisateur](docs/Documentation-Utilisateur.pdf)
  * [Documentation technique](docs/Documentation-Technique.pdf)

### Contexte

1. [GSB - Fiche descriptive](docs/contexte/01-GSB-AppliRV-FicheDescriptive.pdf)
2. [GSB - Cas d'utilisation](docs/contexte/02-GSB-AppliRV-DR-UC.pdf)
3. [GSB - Modèle Entité-Association](docs/contexte/03-GSB-AppliRV-MEA.pdf)
4. [GSB - Cahier des charges](docs/contexte/04-GSB-AppliRV-CahierDesCharges.pdf)

### Prérequis

  * [Java 17](http://jdk.java.net/17/) + définition de la variable d'environnement [JAVA_HOME](https://www.baeldung.com/java-home-on-windows-7-8-10-mac-os-x-linux#1-single-user)
  * [MariaDB](https://mariadb.org/download/?t=mariadb&o=true&p=mariadb&r=10.5.12&os=Linux&cpu=x86_64&i=systemd)

> En cas de difficulté, reportez-vous à la documentation officielle de [JavaFX](https://openjfx.io/openjfx-docs/) et de [MariaDB](https://mariadb.com/kb/en/documentation/).

## Installation

Tout d'abord, clonez ce dépôt puis placez-vous au sein du projet :

```bash
$ git clone https://github.com/AladdineDev/GSB-RV-DR
$ cd GSB-RV-DR
```

Ensuite, exécutez le script de création de la base de données et créez son utilisateur :

```bash
$ mariadb -e "source sql/gsb_rv.sql;"
$ mariadb -e "grant all privileges on gsb_rv.* to developpeur identified by \"azerty\";"
```

Enfin, lancez l'application :

```bash
$ ./gradlew run
```

## Licence

Voir le fichier [LICENSE.md](https://github.com/AladdineDev/GSB-RV-DR/blob/master/LICENSE.md) fourni.