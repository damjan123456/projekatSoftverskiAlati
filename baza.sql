/*
SQLyog Community v13.3.0 (64 bit)
MySQL - 8.0.32 : Database - biblioteka
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
USE `biblioteka`;

/*Table structure for table `bibliotekar` */

DROP TABLE IF EXISTS `bibliotekar`;

CREATE TABLE `bibliotekar` (
  `idBibliotekar` bigint NOT NULL AUTO_INCREMENT,
  `ime` varchar(50) NOT NULL,
  `prezime` varchar(50) NOT NULL,
  `brojTel` varchar(11) NOT NULL,
  `korisnickoIme` varchar(50) NOT NULL,
  `sifra` varchar(50) NOT NULL,
  PRIMARY KEY (`idBibliotekar`),
  UNIQUE KEY `brojTel` (`brojTel`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `bibliotekar` */

insert  into `bibliotekar`(`idBibliotekar`,`ime`,`prezime`,`brojTel`,`korisnickoIme`,`sifra`) values 
(1,'Marko','Markovic','0611234567','marko','marko'),
(2,'Janko','Jankovic','0621234567','janko','janko'),
(3,'Mara','Maric','0631234567','mara','mara');

/*Table structure for table `bibliotekarsertifikat` */

DROP TABLE IF EXISTS `bibliotekarsertifikat`;

CREATE TABLE `bibliotekarsertifikat` (
  `idBibliotekar` bigint NOT NULL,
  `idSertifikat` bigint NOT NULL,
  `datumIzdavanja` date NOT NULL,
  PRIMARY KEY (`idBibliotekar`,`idSertifikat`),
  KEY `idSertifikat` (`idSertifikat`),
  CONSTRAINT `bibliotekarsertifikat_ibfk_1` FOREIGN KEY (`idBibliotekar`) REFERENCES `bibliotekar` (`idBibliotekar`) ON UPDATE RESTRICT,
  CONSTRAINT `bibliotekarsertifikat_ibfk_2` FOREIGN KEY (`idSertifikat`) REFERENCES `sertifikat` (`idSertifikat`) ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `bibliotekarsertifikat` */

insert  into `bibliotekarsertifikat`(`idBibliotekar`,`idSertifikat`,`datumIzdavanja`) values 
(1,1,'2025-08-19'),
(1,2,'2025-07-22'),
(2,3,'2025-08-04');

/*Table structure for table `citalac` */

DROP TABLE IF EXISTS `citalac`;

CREATE TABLE `citalac` (
  `idCitalac` bigint NOT NULL AUTO_INCREMENT,
  `ime` varchar(50) NOT NULL,
  `prezime` varchar(50) NOT NULL,
  `brojTel` varchar(11) NOT NULL,
  `idMesto` bigint NOT NULL,
  PRIMARY KEY (`idCitalac`),
  UNIQUE KEY `brojTel` (`brojTel`),
  KEY `citalac_ibfk_1` (`idMesto`),
  CONSTRAINT `citalac_ibfk_1` FOREIGN KEY (`idMesto`) REFERENCES `mesto` (`idMesto`) ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `citalac` */

insert  into `citalac`(`idCitalac`,`ime`,`prezime`,`brojTel`,`idMesto`) values 
(1,'Ognjen','Ognjenovic','061283749',2),
(3,'Dusko','Dugousko','0634444555',1),
(4,'Brzi','Gonzales','062549384',6),
(7,'MIkica','Taric','062349862',5),
(9,'Damjan','Djuric','060123476',1);

/*Table structure for table `knjiga` */

DROP TABLE IF EXISTS `knjiga`;

CREATE TABLE `knjiga` (
  `idKnjiga` bigint NOT NULL AUTO_INCREMENT,
  `naslov` varchar(50) NOT NULL,
  `autor` varchar(50) NOT NULL,
  `cenaZaNepovracaj` double NOT NULL,
  PRIMARY KEY (`idKnjiga`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `knjiga` */

insert  into `knjiga`(`idKnjiga`,`naslov`,`autor`,`cenaZaNepovracaj`) values 
(1,'Na Drini cuprija','Ivo Andric',1500),
(2,'Seobe','Milos Crnjanski',1300),
(3,'Dervis i smrt','Mesa Selimovic',1000),
(4,'Gorski vijenac','Petar II Petrovic Njegos',1200);

/*Table structure for table `mesto` */

DROP TABLE IF EXISTS `mesto`;

CREATE TABLE `mesto` (
  `idMesto` bigint NOT NULL AUTO_INCREMENT,
  `naziv` varchar(50) NOT NULL,
  PRIMARY KEY (`idMesto`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `mesto` */

insert  into `mesto`(`idMesto`,`naziv`) values 
(1,'Beograd'),
(2,'Dubrovnik'),
(3,'Mostar'),
(4,'Ohrid'),
(5,'Kotor'),
(6,'Sarajevo'),
(7,'Sofija');

/*Table structure for table `sertifikat` */

DROP TABLE IF EXISTS `sertifikat`;

CREATE TABLE `sertifikat` (
  `idSertifikat` bigint NOT NULL AUTO_INCREMENT,
  `naziv` varchar(50) NOT NULL,
  `institucija` varchar(50) NOT NULL,
  PRIMARY KEY (`idSertifikat`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sertifikat` */

insert  into `sertifikat`(`idSertifikat`,`naziv`,`institucija`) values 
(1,'Obuka za rad u softveru biblioteke','Bibliotekarski centar'),
(2,'Kurs digitalne pismenosti','Narodna biblioteka'),
(3,'Sertifikat za rad sa korisnicima','Kulturni centar'),
(7,'Novi','Novi'),
(8,'Sert','Sert'),
(9,'nov','nov'),
(10,'Novi Sertifikat','Rezidencija Sertifikat');

/*Table structure for table `stavkazapisaoiznajmljivanju` */

DROP TABLE IF EXISTS `stavkazapisaoiznajmljivanju`;

CREATE TABLE `stavkazapisaoiznajmljivanju` (
  `idZapis` bigint NOT NULL,
  `rb` bigint NOT NULL AUTO_INCREMENT,
  `datumVracanja` date NOT NULL,
  `maxDatumVracanja` date NOT NULL,
  `kolicina` int NOT NULL,
  `iznos` double NOT NULL,
  `cenaZaNepovracaj` double NOT NULL,
  `vracenoNaVreme` tinyint(1) NOT NULL,
  `idKnjiga` bigint NOT NULL,
  PRIMARY KEY (`idZapis`,`rb`),
  KEY `rb` (`rb`),
  KEY `stavkazapisaoiznajmljivanju_ibfk_2` (`idKnjiga`),
  CONSTRAINT `stavkazapisaoiznajmljivanju_ibfk_1` FOREIGN KEY (`idZapis`) REFERENCES `zapisoiznajmljivanju` (`idZapis`) ON UPDATE RESTRICT,
  CONSTRAINT `stavkazapisaoiznajmljivanju_ibfk_2` FOREIGN KEY (`idKnjiga`) REFERENCES `knjiga` (`idKnjiga`) ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `stavkazapisaoiznajmljivanju` */

insert  into `stavkazapisaoiznajmljivanju`(`idZapis`,`rb`,`datumVracanja`,`maxDatumVracanja`,`kolicina`,`iznos`,`cenaZaNepovracaj`,`vracenoNaVreme`,`idKnjiga`) values 
(1,1,'2025-09-10','2025-09-12',2,0,1500,1,1),
(1,2,'2003-10-11','2003-10-11',2,0,0,1,2),
(1,3,'2024-11-11','2024-10-11',5,7500,1500,0,1),
(8,1,'2025-10-11','2025-10-11',2,0,0,1,1),
(8,2,'2025-10-12','2025-10-11',2,2600,1300,0,2),
(9,1,'2024-10-11','2023-10-11',2,2600,1300,0,2),
(9,2,'2024-10-11','2023-10-11',2,2400,1200,0,3),
(10,1,'2025-10-12','2025-10-11',2,2600,1300,0,2),
(10,2,'2025-10-12','2025-10-12',1,0,0,1,1),
(11,1,'2025-10-02','2025-10-05',1,0,0,1,2),
(11,2,'2025-10-04','2025-10-05',2,0,0,1,1);

/*Table structure for table `zapisoiznajmljivanju` */

DROP TABLE IF EXISTS `zapisoiznajmljivanju`;

CREATE TABLE `zapisoiznajmljivanju` (
  `idZapis` bigint NOT NULL AUTO_INCREMENT,
  `datumIznajmljivanja` date NOT NULL,
  `ukupanIznos` double NOT NULL,
  `idCitalac` bigint NOT NULL,
  `idBibliotekar` bigint NOT NULL,
  PRIMARY KEY (`idZapis`),
  KEY `zapisoiznajmljivanju_ibfk_1` (`idCitalac`),
  KEY `zapisoiznajmljivanju_ibfk_2` (`idBibliotekar`),
  CONSTRAINT `zapisoiznajmljivanju_ibfk_1` FOREIGN KEY (`idCitalac`) REFERENCES `citalac` (`idCitalac`) ON UPDATE RESTRICT,
  CONSTRAINT `zapisoiznajmljivanju_ibfk_2` FOREIGN KEY (`idBibliotekar`) REFERENCES `bibliotekar` (`idBibliotekar`) ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `zapisoiznajmljivanju` */

insert  into `zapisoiznajmljivanju`(`idZapis`,`datumIznajmljivanja`,`ukupanIznos`,`idCitalac`,`idBibliotekar`) values 
(1,'2025-09-02',0,1,1),
(8,'2025-11-11',2600,4,1),
(9,'2024-12-12',5000,4,2),
(10,'2024-11-12',2600,3,2),
(11,'2025-10-05',0,7,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
