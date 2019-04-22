Create database shs; 
use shs; 

#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------

# 911
# Afin d'appliquer le modèle physique des données, nous avons généré le code sql depuis le MCD automatiquement.
# Sur ce script, nous avons supprimé de toutes les tables capteur[Autre] (CapteurFumee, CapteurHygro, CapteurOuverture...), la clé étrangère
# vers la table capteur et défini l'identifiant de chacune de ces tables comme clé étrangère vers l'identifiant de la table capteurs.

#------------------------------------------------------------
# Table: Adresses
#------------------------------------------------------------

CREATE TABLE Adresses(
        ID_Addresse        Int  Auto_increment  NOT NULL ,
        Numero_Addresse    Int NOT NULL ,
        Rue_Adresse        Varchar (35) NOT NULL ,
        Ville_Adresse      Varchar (35) NOT NULL ,
        CodePostal_Adresse Varchar (5) NOT NULL ,
        Pays_Adresse       Varchar (30) NOT NULL
	,CONSTRAINT Adresse_PK PRIMARY KEY (ID_Addresse)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Residences
#------------------------------------------------------------

CREATE TABLE Residences(
        ID_Residence  Int  Auto_increment  NOT NULL ,
        Nom_Residence Varchar (50) NOT NULL ,
        ID_Addresse   Int NOT NULL
	,CONSTRAINT Residences_PK PRIMARY KEY (ID_Residence)

	,CONSTRAINT Residences_Adresse_FK FOREIGN KEY (ID_Addresse) REFERENCES Adresses(ID_Addresse)
	,CONSTRAINT Residences_Adresse_AK UNIQUE (ID_Addresse)
)ENGINE=InnoDB;

#------------------------------------------------------------
# Table: Residents
#------------------------------------------------------------

CREATE TABLE Residents(
        ID_Resident          Int  Auto_increment  NOT NULL ,
        NumChambre_Resident  Int NOT NULL ,
        Nom_Resident         Varchar (30) NOT NULL ,
        Prenom_Resident      Varchar (30) NOT NULL ,
        Age_Resident         Int NOT NULL ,
        Sexe_Resident        Bool NOT NULL ,
        Information_Resident Varchar (300) NOT NULL COMMENT "Informations médicales sur le résident (antécédents, traitement actuel)"  ,
        ID_Residence         Int NOT NULL
	,CONSTRAINT Residents_PK PRIMARY KEY (ID_Resident)

	,CONSTRAINT Residents_Residences_FK FOREIGN KEY (ID_Residence) REFERENCES Residences(ID_Residence)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Personnels
#------------------------------------------------------------
CREATE TABLE Personnels(
        ID_Personnel          Int  Auto_increment  NOT NULL ,
        Identifiant_Personnel Varchar (20) NOT NULL ,
        MotDePasse_Personnel  Varchar (40) NOT NULL ,
        Nom_Personnel         Varchar (30) NOT NULL ,
        Prenom_Personnel      Varchar (30) NOT NULL ,
        Fonction_Personnel    Varchar (30) NOT NULL ,
        Numero_Personnel      Varchar (12) NOT NULL ,
        ID_Addresse           Int NOT NULL
	,CONSTRAINT Personnel_PK PRIMARY KEY (ID_Personnel)

	,CONSTRAINT Personnel_Adresse_FK FOREIGN KEY (ID_Addresse) REFERENCES Adresses(ID_Addresse)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Etages
#------------------------------------------------------------
CREATE TABLE Etages(
        ID_Etage    Int  Auto_increment  NOT NULL ,
        Image_Etage VARCHAR(30) NOT NULL, 
        Niveau_Etage Int NOT NULL ,
        ID_Residence      Int NOT NULL
	,CONSTRAINT Etage_PK PRIMARY KEY (ID_Etage)
	,CONSTRAINT Etage_Residences_FK FOREIGN KEY (ID_Residence) REFERENCES Residences(ID_Residence)
)ENGINE=InnoDB;

#------------------------------------------------------------
# Table: Emplacements
#------------------------------------------------------------
CREATE TABLE Emplacements(
        ID_Emplacement   Int  Auto_increment  NOT NULL ,
        Nom_Emplacement  Varchar (30) NOT NULL ,
        ID_Etage      Int NOT NULL,
        X Int NOT NULL,
		Y Int NOT NULL,
		Width Int NOT NULL,
		Height Int NOT NULL
	,CONSTRAINT Emplacement_PK PRIMARY KEY (ID_Emplacement)
	,CONSTRAINT Emplacement_Etage_FK FOREIGN KEY (ID_Etage) REFERENCES Etages(ID_Etage)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Referentiel_Capteurs
#------------------------------------------------------------
CREATE TABLE Referentiel_Capteurs(
		Type_Capteur Varchar(30) NOT NULL,
        CONSTRAINT Referentiel_Capteurs PRIMARY KEY (Type_Capteur)
);


#------------------------------------------------------------
# Table: Capteurs
#------------------------------------------------------------
CREATE TABLE Capteurs(
        ID_Capteur     Int  Auto_increment  NOT NULL ,
        Type_Capteur   Varchar(30) NOT NULL COMMENT "Enumeration des différents type de capteur à définir"  ,
        Etat_Capteur   Bool NOT NULL ,
        ID_Emplacement Int NOT NULL ,
        Mac_Capteur VarChar(17) 
	,CONSTRAINT Capteurs_PK PRIMARY KEY (ID_Capteur)
	,CONSTRAINT Capteurs_Type_FK FOREIGN KEY (Type_Capteur) REFERENCES Referentiel_Capteurs(Type_Capteur) 
	,CONSTRAINT Capteurs_Emplacement_FK FOREIGN KEY (ID_Emplacement) REFERENCES Emplacements(ID_Emplacement)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Notifications
#------------------------------------------------------------

CREATE TABLE Notifications(
        ID_Notification        Int  Auto_increment  NOT NULL ,
        Niveau_Notification    Int NOT NULL ,
        Date_Notification      TIMESTAMP NOT NULL ,
        Message_Notification   Varchar (60) ,
        ID_Capteur             Int NOT NULL
	,CONSTRAINT Notifications_PK PRIMARY KEY (ID_Notification)

	,CONSTRAINT Notifications_Capteurs_FK FOREIGN KEY (ID_Capteur) REFERENCES Capteurs(ID_Capteur)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: CapteursFumee
#------------------------------------------------------------

CREATE TABLE CapteursFumee(
        ID_CapteurFumee          Int NOT NULL COMMENT "Clé étrangère vers ID_Capteur de Capteur"  ,
        Seuil_CapteurFumee Int NOT NULL
	,CONSTRAINT CapteurFumee_PK PRIMARY KEY (ID_CapteurFumee)
	,CONSTRAINT CapteurFumee_Capteurs_FK FOREIGN KEY (ID_CapteurFumee) REFERENCES Capteurs(ID_Capteur)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: CapteursTemperature
#------------------------------------------------------------

CREATE TABLE CapteursTemperature(
        ID_CapteurTemperature           Int NOT NULL ,
        Min_CapteurTemperature 			Float NOT NULL ,
        Max_CapteurTemperature 			Float NOT NULL 
	,CONSTRAINT CapteurTemperature_PK PRIMARY KEY (ID_CapteurTemperature)
	,CONSTRAINT CapteurTemperature_Capteurs_FK FOREIGN KEY (ID_CapteurTemperature) REFERENCES Capteurs(ID_Capteur)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: CapteursOuverture
#------------------------------------------------------------

CREATE TABLE CapteursOuverture(
        ID_CapteurOuverture    Int NOT NULL ,
        Debut_CapteurOuverture Time NOT NULL,
		Fin_CapteurOuverture   Time NOT NULL
	,CONSTRAINT CapteurOuverture_PK PRIMARY KEY (ID_CapteurOuverture)
	,CONSTRAINT CapteurOuverture_Capteurs_FK FOREIGN KEY (ID_CapteurOuverture) REFERENCES Capteurs(ID_Capteur)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: CapteursHygro
#------------------------------------------------------------

CREATE TABLE CapteursHygro(
        ID_CapteurHygro    Int NOT NULL ,
        Seuil_CapteurHygro Int NOT NULL
	,CONSTRAINT CapteurHygro_PK PRIMARY KEY (ID_CapteurHygro)
	,CONSTRAINT CapteurHygro_Capteurs_FK FOREIGN KEY (ID_CapteurHygro) REFERENCES Capteurs(ID_Capteur)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: CapteurPresence
#------------------------------------------------------------

CREATE TABLE CapteursPresence(
        ID_CapteurPresence    Int NOT NULL ,
        Debut_CapteurPresence Time NOT NULL,
        Fin_CapteurPresence Time NOT NULL
	,CONSTRAINT CapteurPresence_PK PRIMARY KEY (ID_CapteurPresence)
	,CONSTRAINT CapteurPresence_Capteurs_FK FOREIGN KEY (ID_CapteurPresence) REFERENCES Capteurs(ID_Capteur)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: CapteursAppel
#------------------------------------------------------------

CREATE TABLE CapteursAppel(
        ID_CapteurAppel           Int NOT NULL ,
        NiveauAlerte_CapteurAppel Int NOT NULL
	,CONSTRAINT CapteurAppel_PK PRIMARY KEY (ID_CapteurAppel)
	,CONSTRAINT CapteurAppel_Capteurs_FK FOREIGN KEY (ID_CapteurAppel) REFERENCES Capteurs(ID_Capteur)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: CapteursRFID
#------------------------------------------------------------

CREATE TABLE CapteursRFID(
        ID_CapteurRFID              Int NOT NULL ,
        NiveauAlerte_CapteurRFID    Int NOT NULL
	,CONSTRAINT CapteurRFID_PK PRIMARY KEY (ID_CapteurRFID)
	,CONSTRAINT CapteurRFID_Capteurs_FK FOREIGN KEY (ID_CapteurRFID) REFERENCES Capteurs(ID_Capteur)
)ENGINE=InnoDB;

#------------------------------------------------------------
# Table: dépendre de
#------------------------------------------------------------

CREATE TABLE dependre_de(
        ID_Personnel Int NOT NULL ,
        ID_Residence Int NOT NULL
	,CONSTRAINT dependre_de_PK PRIMARY KEY (ID_Personnel,ID_Residence)

	,CONSTRAINT dependre_de_Personnel_FK FOREIGN KEY (ID_Personnel) REFERENCES Personnels(ID_Personnel)
	,CONSTRAINT dependre_de_Residences0_FK FOREIGN KEY (ID_Residence) REFERENCES Residences(ID_Residence)
)ENGINE=InnoDB;


ALTER TABLE Notifications CHANGE Date_Notification Date_Notification TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

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


insert into Referentiel_Capteurs(Type_Capteur) values('Capteur de température');
insert into Referentiel_Capteurs(Type_Capteur) values('Capteur de fumée');
insert into Referentiel_Capteurs(Type_Capteur) values('Capteur ouverture');
insert into Referentiel_Capteurs(Type_Capteur) values('Capteur hygrométrique');
insert into Referentiel_Capteurs(Type_Capteur) values('Capteur de présence');
insert into Referentiel_Capteurs(Type_Capteur) values('Capteur appel');
insert into Referentiel_Capteurs(Type_Capteur) values('Bracelet RFID');

insert into Adresses (Numero_Addresse, Rue_Adresse, Ville_Adresse, CodePostal_Adresse, Pays_Adresse) values ('15', 'Rue de Hella' , 'Paris' , '75000', 'France');

insert into Personnels (Identifiant_Personnel, MotDePasse_Personnel, Nom_Personnel, Prenom_Personnel, Fonction_Personnel, Numero_Personnel, ID_Addresse) values ('Justin911', '911', 'Aguesse', 'Justin', 'Admin', '0607080901', 1);

insert into Residences (Nom_Residence, ID_Addresse) values ('R1', 1);

insert into Etages (Image_Etage, Niveau_Etage, ID_Residence) values ('Etage1.JPG',0, 1);
insert into Etages (Image_Etage, Niveau_Etage, ID_Residence) values ('Etage2.png',1, 1);

INSERT INTO Emplacements (Nom_Emplacement, ID_Etage, X, Y, Width, Height)
 VALUES
 ('Couloir 3', '1', 605, 144, 42, 37),
 ('Couloir 3', '1', 605, 186, 42, 37),
 ('Couloir 3', '1', 675, 102, 42, 37),
 ('Couloir 3', '1', 675, 144, 42, 37),
 ('Couloir 3', '1', 675, 186, 42, 37),
 ('Couloir 3', '1', 605, 228, 42, 37),
 ('Couloir 3', '1', 675, 228, 42, 37),
 ('Couloir 3', '1', 505, 274, 42, 37),
 ('Couloir 3', '1', 505, 320, 42, 37),
 ('Couloir 3', '1', 505, 363, 42, 37),
 ('Couloir 3', '1', 505, 406, 42, 37),
 ('Couloir 3', '1', 505, 448, 42, 37),
 ('Couloir 3', '1', 505, 492, 42, 37),

 ('Couloir 2', '1', 821, 274, 42, 37),
 ('Couloir 2', '1', 874, 274, 42, 37),
 ('Couloir 2', '1', 931, 274, 42, 37),
 ('Couloir 2', '1', 985, 274, 42, 37),
 ('Couloir 2', '1', 1053, 274, 42, 37),
 ('Couloir 2', '1', 1118, 274, 42, 37),
 ('Couloir 2', '1', 1177, 274, 42, 37),
 ('Couloir 2', '1', 1234, 274, 42, 37),
 ('Couloir 2', '1', 1290, 274, 42, 37),
 ('Couloir 2', '1', 1344, 274, 42, 37),
 ('Couloir 2', '1', 1398, 274, 42, 37),
 ('Couloir 2', '1', 1452, 274, 42, 37),
 
 ('Toilette 1', '1', 505, 546, 42, 37),
 ('Toilette 1', '1', 505, 605, 42, 37),
 ('Toilette 2', '1', 771, 546, 42, 37),
 ('Toilette 2', '1', 771, 605, 42, 37),
 
 ('Cafétaria', '1', 505, 767, 42, 37),
 ('Cafétaria', '1', 505, 721, 42, 37),
 ('Cafétaria', '1', 505, 665, 42, 37),
 ('Cafétaria', '1', 771, 721, 42, 37),
 ('Cafétaria', '1', 771, 665, 42, 37),
 ('Cafétaria', '1', 771, 767, 42, 37),
 
 ('Cuisine', '1', 505, 826, 42, 37),
 ('Cuisine', '1', 505, 876, 42, 37),
 ('Cuisine', '1', 771, 876, 42, 37),
 ('Cuisine', '1', 771, 832, 42, 37),

 ('Terrasse', '1', 821, 380, 42, 37),
 ('Terrasse', '1', 874, 380, 42, 37),
 ('Terrasse', '1', 931, 380, 42, 37),
 ('Terrasse', '1', 985, 380, 42, 37),
 ('Terrasse', '1', 1039, 380, 42, 37),
 ('Terrasse', '1', 1094, 380, 42, 37),
 ('Terrasse', '1', 1148, 380, 42, 37),
 ('Terrasse', '1', 1200, 380, 42, 37),
 ('Terrasse', '1', 1254, 380, 42, 37),
 ('Terrasse', '1', 1308, 380, 42, 37),
 ('Terrasse', '1', 1360, 380, 42, 37),
 ('Terrasse', '1', 1412, 380, 42, 37),
 ('Terrasse', '1', 1464, 380, 42, 37),
 
 ('Couloir 1', '1', 1506, 274, 42, 37),
 ('Couloir 1', '1', 1563, 274, 42, 37),
 ('Couloir 1', '1', 1619, 274, 42, 37),
 ('Couloir 1', '1', 1673, 274, 42, 37),
 ('Couloir 1', '1', 1727, 274, 42, 37),
 ('Couloir 1', '1', 1781, 274, 42, 37),
 
 ('Jardin', '1', 1331, 492, 42, 37),
 ('Jardin', '1', 1277, 492, 42, 37),
 ('Jardin', '1', 1223, 492, 42, 37),
 ('Jardin', '1', 1171, 492, 42, 37),
 ('Jardin', '1', 1117, 492, 42, 37),
 ('Jardin', '1', 1062, 492, 42, 37),
 ('Jardin', '1', 1008, 492, 42, 37),
 ('Jardin', '1', 954, 492, 42, 37),
 ('Jardin', '1', 954, 546, 42, 37),
 ('Jardin', '1', 954, 605, 42, 37),
 ('Jardin', '1', 954, 665, 42, 37),
 ('Jardin', '1', 954, 721, 42, 37),
 ('Jardin', '1', 954, 787, 42, 37),
 ('Jardin', '1', 954, 856, 42, 37),
 ('Jardin', '1', 954, 546, 42, 37),
 ('Jardin', '1', 1331, 546, 42, 37),
 ('Jardin', '1', 1331, 605, 42, 37),
 ('Jardin', '1', 1331, 665, 42, 37),
 ('Jardin', '1', 1331, 721, 42, 37),
 ('Jardin', '1', 1331, 787, 42, 37),
 ('Jardin', '1', 1331, 856, 42, 37),

 ('Couloir 1', '1', 1619, 387, 42, 37),
 ('Couloir 1', '1', 1619, 433, 42, 37),
 ('Couloir 1', '1', 1619, 476, 42, 37),
 ('Couloir 1', '1', 1619, 519, 42, 37),
 ('Couloir 1', '1', 1619, 561, 42, 37),
 ('Couloir 1', '1', 1619, 605, 42, 37),
 ('Couloir 1', '1', 1619, 655, 42, 37),
 ('Couloir 1', '1', 1619, 701, 42, 37),
 ('Couloir 1', '1', 1619, 744, 42, 37),
 ('Couloir 1', '1', 1619, 787, 42, 37),
 ('Couloir 1', '1', 1619, 829, 42, 37),
 ('Couloir 1', '1', 1619, 873, 42, 37),
 ('Couloir 1', '1', 1683, 387, 42, 37),
 ('Couloir 1', '1', 1683, 433, 42, 37),
 ('Couloir 1', '1', 1683, 476, 42, 37),
 ('Couloir 1', '1', 1683, 519, 42, 37),
 ('Couloir 1', '1', 1683, 561, 42, 37),
 ('Couloir 1', '1', 1683, 605, 42, 37),
 ('Couloir 1', '1', 1683, 648, 42, 37),
 ('Couloir 1', '1', 1683, 694, 42, 37),
 ('Couloir 1', '1', 1683, 737, 42, 37),
 ('Couloir 1', '1', 1683, 780, 42, 37),
 ('Couloir 1', '1', 1683, 826, 42, 37),
 ('Couloir 1', '1', 1683, 876, 42, 37),

 ('Salle personnel', '1', 1521, 492, 42, 37),
 ('Salle personnel', '1', 1521, 546, 42, 37),

 ('Salle sport', '1', 1521, 701, 42, 37),
 ('Salle sport', '1', 1521, 655, 42, 37),
 ('Salle sport', '1', 1521, 605, 42, 37),
 
 ('Salle sport', '1', 1521, 826, 42, 37),
 ('Salle sport', '1', 1521, 767, 42, 37),
 ('Salle sport', '1', 1521, 876, 42, 37),
 
 ('Salle activité', '1', 1751, 113, 42, 37),
 ('Salle activité', '1', 1697, 113, 42, 37),
 ('Salle activité', '1', 1643, 113, 42, 37),
 ('Salle activité', '1', 1589, 113, 42, 37),
 ('Salle activité', '1', 1533, 113, 42, 37);












