CREATE TABLE IF NOT EXISTS POKEMON(
	 ID VARCHAR(64) NOT NULL PRIMARY KEY,
	 NAME VARCHAR(64) NOT NULL,
	 TYPE VARCHAR(64) NOT NULL,
	 AGE INT NOT NULL
	  
);

Insert INTO POKEMON ( ID,NAME,TYPE,AGE) VALUES ('p1','Pikachu','Electric',8);
Insert INTO POKEMON ( ID,NAME,TYPE,AGE) VALUES ('p2','Eevee','Normal',3);
Insert INTO POKEMON ( ID,NAME,TYPE,AGE) VALUES ('p3','Charizard','Flying',9);