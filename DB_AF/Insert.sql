use af_db;

INSERT INTO utenti ( `Nome`, `Cognome`, `Pwd`, `Mail`, `Ruolo`) VALUES ('Luigi', 'Giacchetti', 'lol', 'l.giacchetti@studenti.unisa.it', '1');	/*Utente*/
INSERT INTO utenti ( `Nome`, `Cognome`, `Pwd`, `Mail`, `Ruolo`) VALUES ('Pasquale', 'Severino', 'lol', 'p.severino@studenti.unisa.it', '2');	/*Dipendente*/
INSERT INTO utenti ( `Nome`, `Cognome`, `Pwd`, `Mail`, `Ruolo`) VALUES ('Manuel', 'Nocerino', 'lol', 'm.nocerino@studenti.unisa.it', '3');		/*TM*/
INSERT INTO utenti ( `Nome`, `Cognome`, `Pwd`, `Mail`, `Ruolo`) VALUES ('Domenico', 'Pagliuca', 'lol', 'd.pagliuca@studenti.unisa.it', '4'); 	/*HR*/
INSERT INTO utenti ( `Nome`, `Cognome`, `Pwd`, `Mail`, `Ruolo`) VALUES ('Mario', 'Rossi', 'lol', 'MarioDraghi@libero.it', '2');	/*Dipendente*/
INSERT INTO skill (`NomeSkill`, `DescrizioneSkill`) VALUES ( 'HTML', 'HTML');
INSERT INTO skill (`NomeSkill`, `DescrizioneSkill`) VALUES ('CSS', 'CSS');
INSERT INTO skill (`NomeSkill`, `DescrizioneSkill`) VALUES ('Java', 'Java');
INSERT INTO skill (`NomeSkill`, `DescrizioneSkill`) VALUES ('C', 'C');
INSERT INTO skill (`NomeSkill`, `DescrizioneSkill`) VALUES ('Python', 'Python');
INSERT INTO skill (`NomeSkill`, `DescrizioneSkill`) VALUES ('React', 'React');
INSERT INTO skill (`NomeSkill`, `DescrizioneSkill`) VALUES ('Node', 'Node');
INSERT INTO skill (`NomeSkill`, `DescrizioneSkill`) VALUES ('C++', 'C++');
INSERT INTO skill (`NomeSkill`, `DescrizioneSkill`) VALUES ('javascript', 'javascript');
INSERT INTO skill (`NomeSkill`, `DescrizioneSkill`) VALUES ('Ruby', 'Ruby');
INSERT INTO skill (`NomeSkill`, `DescrizioneSkill`) VALUES ('C#', 'C#');
INSERT INTO skill (`NomeSkill`, `DescrizioneSkill`) VALUES ('Android', 'Android');
INSERT INTO skill (`NomeSkill`, `DescrizioneSkill`) VALUES ('SQL', 'SQL');
INSERT INTO skill (`NomeSkill`, `DescrizioneSkill`) VALUES ('PHP', 'PHP');
INSERT INTO team (`NomeProgetto`, `NumeroDipendenti`, `NomeTeam`, `Descrizione`, `IdTM`) VALUES ('TechAll', '8', 'NuoveTecnologie', 'Ricerchiamo nuove tecnologie', 3);
INSERT INTO team (`NomeProgetto`, `NumeroDipendenti`, `NomeTeam`, `Descrizione`, `Competenza`, `IdTM`) VALUES ('NewTech', '1', 'Arte', 'Nuove tecnologie biomeccaniche', 'Basi di Ingegneria Meccanica', 3);
INSERT INTO dipendenti (`IdDipendente`,`Residenza`, `Telefono`, `Stato`, `AnnoDiNascita`, `IdTeam`) VALUES (2,'Fisciano', '118', '0', '2000', '1');
INSERT INTO dipendenti (`IdDipendente`,`Residenza`, `Telefono`, `Stato`, `AnnoDiNascita`, `IdTeam`) VALUES (5,'Fisciano', '777', '0', '2000', '2');
INSERT INTO skillsdipendenti (`IdDipendente`, `IdSkill`, `Livello`) VALUES ('2', '1', '5');
INSERT INTO skillsdipendenti (`IdDipendente`, `IdSkill`, `Livello`) VALUES ('2', '2', '3');


