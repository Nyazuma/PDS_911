-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema shs
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema shs
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `shs` DEFAULT CHARACTER SET latin1 ;
USE `shs` ;

-- -----------------------------------------------------
-- Table `shs`.`Adresses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shs`.`Adresses` (
  `ID_Addresse` INT(11) NOT NULL AUTO_INCREMENT,
  `Numero_Addresse` INT(11) NOT NULL,
  `Rue_Adresse` VARCHAR(35) NOT NULL,
  `Ville_Adresse` VARCHAR(35) NOT NULL,
  `CodePostal_Adresse` VARCHAR(5) NOT NULL,
  `Pays_Adresse` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`ID_Addresse`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `shs`.`Residences`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shs`.`Residences` (
  `ID_Residence` INT(11) NOT NULL AUTO_INCREMENT,
  `Nom_Residence` VARCHAR(50) NOT NULL,
  `ID_Addresse` INT(11) NOT NULL,
  PRIMARY KEY (`ID_Residence`),
  UNIQUE INDEX `Residences_Adresse_AK` (`ID_Addresse` ASC) VISIBLE,
  CONSTRAINT `Residences_Adresse_FK`
    FOREIGN KEY (`ID_Addresse`)
    REFERENCES `shs`.`Adresses` (`ID_Addresse`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `shs`.`Etages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shs`.`Etages` (
  `ID_Etage` INT(11) NOT NULL AUTO_INCREMENT,
  `Niveau_Etage` INT(11) NOT NULL,
  `ID_Residence` INT(11) NOT NULL,
  `Image_Etage` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_Etage`),
  INDEX `Etage_Residences_FK` (`ID_Residence` ASC) VISIBLE,
  CONSTRAINT `Etage_Residences_FK`
    FOREIGN KEY (`ID_Residence`)
    REFERENCES `shs`.`Residences` (`ID_Residence`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `shs`.`Emplacements`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shs`.`Emplacements` (
  `ID_Emplacement` INT(11) NOT NULL AUTO_INCREMENT,
  `Nom_Emplacement` VARCHAR(30) NOT NULL,
  `ID_Etage` INT(11) NOT NULL,
  `X` INT(11) NOT NULL,
  `Y` INT(11) NOT NULL,
  `Width` INT(11) NOT NULL,
  `Height` INT(11) NOT NULL,
  PRIMARY KEY (`ID_Emplacement`),
  INDEX `Emplacement_Etage_FK` (`ID_Etage` ASC) VISIBLE,
  CONSTRAINT `Emplacement_Etage_FK`
    FOREIGN KEY (`ID_Etage`)
    REFERENCES `shs`.`Etages` (`ID_Etage`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `shs`.`Referentiel_Capteurs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shs`.`Referentiel_Capteurs` (
  `Type_Capteur` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`Type_Capteur`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `shs`.`Capteurs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shs`.`Capteurs` (
  `ID_Capteur` INT(11) NOT NULL AUTO_INCREMENT,
  `Type_Capteur` VARCHAR(30) NOT NULL COMMENT 'Enumeration des différents type de capteur à définir',
  `Etat_Capteur` TINYINT(1) NOT NULL,
  `ID_Emplacement` INT(11) NOT NULL,
  `Mac_Capteur` VARCHAR(17) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_Capteur`),
  INDEX `Capteurs_Type_FK` (`Type_Capteur` ASC) VISIBLE,
  INDEX `Capteurs_Emplacement_FK` (`ID_Emplacement` ASC) VISIBLE,
  CONSTRAINT `Capteurs_Emplacement_FK`
    FOREIGN KEY (`ID_Emplacement`)
    REFERENCES `shs`.`Emplacements` (`ID_Emplacement`),
  CONSTRAINT `Capteurs_Type_FK`
    FOREIGN KEY (`Type_Capteur`)
    REFERENCES `shs`.`Referentiel_Capteurs` (`Type_Capteur`))
ENGINE = InnoDB
AUTO_INCREMENT = 61
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `shs`.`CapteursAppel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shs`.`CapteursAppel` (
  `ID_CapteurAppel` INT(11) NOT NULL,
  `NiveauAlerte_CapteurAppel` INT(11) NOT NULL,
  `ID_Capteur` INT(11) NOT NULL,
  PRIMARY KEY (`ID_CapteurAppel`),
  CONSTRAINT `CapteurAppel_Capteurs_FK`
    FOREIGN KEY (`ID_CapteurAppel`)
    REFERENCES `shs`.`Capteurs` (`ID_Capteur`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `shs`.`CapteursFumee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shs`.`CapteursFumee` (
  `ID_CapteurFumee` INT(11) NOT NULL COMMENT 'Clé étrangère vers ID_Capteur de Capteur',
  `Sensibilite_CapteurFumee` INT(11) NOT NULL,
  `Intervalle_CapteurFumee` INT(11) NOT NULL,
  PRIMARY KEY (`ID_CapteurFumee`),
  CONSTRAINT `CapteurFumee_Capteurs_FK`
    FOREIGN KEY (`ID_CapteurFumee`)
    REFERENCES `shs`.`Capteurs` (`ID_Capteur`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `shs`.`CapteursHygro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shs`.`CapteursHygro` (
  `ID_CapteurHygro` INT(11) NOT NULL,
  `Tempo_CapteurHygro` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_CapteurHygro`),
  CONSTRAINT `CapteurHygro_Capteurs_FK`
    FOREIGN KEY (`ID_CapteurHygro`)
    REFERENCES `shs`.`Capteurs` (`ID_Capteur`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `shs`.`CapteursOuverture`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shs`.`CapteursOuverture` (
  `ID_CapteurOuverture` INT(11) NOT NULL,
  `Tempo_CapteurOuverture` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_CapteurOuverture`),
  CONSTRAINT `CapteurOuverture_Capteurs_FK`
    FOREIGN KEY (`ID_CapteurOuverture`)
    REFERENCES `shs`.`Capteurs` (`ID_Capteur`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `shs`.`CapteursPresence`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shs`.`CapteursPresence` (
  `ID_CapteurPresence` INT(11) NOT NULL,
  `Tempo_CapteurPresence` INT(11) NULL DEFAULT NULL,
  `ID_Capteur` INT(11) NOT NULL,
  PRIMARY KEY (`ID_CapteurPresence`),
  CONSTRAINT `CapteurPresence_Capteurs_FK`
    FOREIGN KEY (`ID_CapteurPresence`)
    REFERENCES `shs`.`Capteurs` (`ID_Capteur`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `shs`.`CapteursRFID`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shs`.`CapteursRFID` (
  `ID_CapteurRFID` INT(11) NOT NULL,
  `Tempo_CapteurRFID` INT(11) NULL DEFAULT NULL,
  `LectureIdentite_CapteurRFID` TINYINT(1) NOT NULL,
  PRIMARY KEY (`ID_CapteurRFID`),
  CONSTRAINT `CapteurRFID_Capteurs_FK`
    FOREIGN KEY (`ID_CapteurRFID`)
    REFERENCES `shs`.`Capteurs` (`ID_Capteur`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `shs`.`CapteursTemperature`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shs`.`CapteursTemperature` (
  `ID_CapteurTemperature` INT(11) NOT NULL,
  `Reference_CapteurTemperature` FLOAT NOT NULL,
  `ToleranceMin_CapteurTemperature` FLOAT NOT NULL,
  `ToleranceMax_CapteurTemperature` FLOAT NOT NULL,
  PRIMARY KEY (`ID_CapteurTemperature`),
  CONSTRAINT `CapteurTemperature_Capteurs_FK`
    FOREIGN KEY (`ID_CapteurTemperature`)
    REFERENCES `shs`.`Capteurs` (`ID_Capteur`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `shs`.`Notifications`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shs`.`Notifications` (
  `ID_Notification` INT(11) NOT NULL AUTO_INCREMENT,
  `Niveau_Notification` INT(11) NOT NULL,
  `Date_Notification` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Message_Notification` VARCHAR(60) NULL DEFAULT NULL,
  `Numerique_Notification` FLOAT NULL DEFAULT NULL,
  `ID_Capteur` INT(11) NOT NULL,
  PRIMARY KEY (`ID_Notification`),
  INDEX `Notifications_Capteurs_FK` (`ID_Capteur` ASC) VISIBLE,
  CONSTRAINT `Notifications_Capteurs_FK`
    FOREIGN KEY (`ID_Capteur`)
    REFERENCES `shs`.`Capteurs` (`ID_Capteur`))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `shs`.`Personnels`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shs`.`Personnels` (
  `ID_Personnel` INT(11) NOT NULL AUTO_INCREMENT,
  `Identifiant_Personnel` VARCHAR(20) NOT NULL,
  `MotDePasse_Personnel` VARCHAR(40) NOT NULL,
  `Nom_Personnel` VARCHAR(30) NOT NULL,
  `Prenom_Personnel` VARCHAR(30) NOT NULL,
  `Fonction_Personnel` VARCHAR(30) NOT NULL,
  `Numero_Personnel` VARCHAR(12) NOT NULL,
  `ID_Addresse` INT(11) NOT NULL,
  PRIMARY KEY (`ID_Personnel`),
  INDEX `Personnel_Adresse_FK` (`ID_Addresse` ASC) VISIBLE,
  CONSTRAINT `Personnel_Adresse_FK`
    FOREIGN KEY (`ID_Addresse`)
    REFERENCES `shs`.`Adresses` (`ID_Addresse`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `shs`.`Residents`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shs`.`Residents` (
  `ID_Resident` INT(11) NOT NULL AUTO_INCREMENT,
  `NumChambre_Resident` INT(11) NOT NULL,
  `Nom_Resident` VARCHAR(30) NOT NULL,
  `Prenom_Resident` VARCHAR(30) NOT NULL,
  `Age_Resident` INT(11) NOT NULL,
  `Sexe_Resident` TINYINT(1) NOT NULL,
  `Information_Resident` VARCHAR(300) NOT NULL COMMENT 'Informations médicales sur le résident (antécédents, traitement actuel)',
  `ID_Residence` INT(11) NOT NULL,
  PRIMARY KEY (`ID_Resident`),
  INDEX `Residents_Residences_FK` (`ID_Residence` ASC) VISIBLE,
  CONSTRAINT `Residents_Residences_FK`
    FOREIGN KEY (`ID_Residence`)
    REFERENCES `shs`.`Residences` (`ID_Residence`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `shs`.`dependre_de`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shs`.`dependre_de` (
  `ID_Personnel` INT(11) NOT NULL,
  `ID_Residence` INT(11) NOT NULL,
  PRIMARY KEY (`ID_Personnel`, `ID_Residence`),
  INDEX `dependre_de_Residences0_FK` (`ID_Residence` ASC) VISIBLE,
  CONSTRAINT `dependre_de_Personnel_FK`
    FOREIGN KEY (`ID_Personnel`)
    REFERENCES `shs`.`Personnels` (`ID_Personnel`),
  CONSTRAINT `dependre_de_Residences0_FK`
    FOREIGN KEY (`ID_Residence`)
    REFERENCES `shs`.`Residences` (`ID_Residence`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `shs`.`historisation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shs`.`historisation` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `Type_Capteur` VARCHAR(45) NULL DEFAULT NULL,
  `Hist_Date` DATE NULL DEFAULT NULL,
  `Hist_Time` TIME NULL DEFAULT NULL,
  `Hist_User` VARCHAR(45) NULL DEFAULT NULL,
  `Hist_comm` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 61
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_general_ci;

USE `shs`;

DELIMITER $$
USE `shs`$$
CREATE
DEFINER=`911_user`@`%`
TRIGGER `shs`.`Capteurs_AFTER_INSERT`
AFTER INSERT ON `shs`.`Capteurs`
FOR EACH ROW
BEGIN
	INSERT INTO historisation (id,Type_Capteur,Hist_Date,Hist_Time,Hist_User,Hist_comm) VALUES
    (new.ID_Capteur, new.Type_Capteur, current_date(), current_time(), user(), "insert");
END$$

USE `shs`$$
CREATE
DEFINER=`911_user`@`%`
TRIGGER `shs`.`Capteurs_BEFORE_DELETE`
BEFORE DELETE ON `shs`.`Capteurs`
FOR EACH ROW
BEGIN
	INSERT INTO historisation (id,Type_Capteur,Hist_Date,Hist_Time,Hist_User,Hist_comm) VALUES
    (old.ID_Capteur, old.Type_Capteur, current_date(), current_time(), user(), "delete");
END$$

USE `shs`$$
CREATE
DEFINER=`911_user`@`%`
TRIGGER `shs`.`Capteurs_BEFORE_UPDATE`
BEFORE UPDATE ON `shs`.`Capteurs`
FOR EACH ROW
BEGIN
	INSERT INTO historisation (id,Type_Capteur,Hist_Date,Hist_Time,Hist_User,Hist_comm) VALUES
    (old.ID_Capteur, old.Type_Capteur, current_date(), current_time(), user(), "update");
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
