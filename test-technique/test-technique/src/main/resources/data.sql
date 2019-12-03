DROP TABLE IF EXISTS salarie;
 
CREATE TABLE salarie (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  prenom VARCHAR(100) NOT NULL,
  fonction VARCHAR(150) NOT NULL,
  date_naissance date NOT NULL,
  adresse VARCHAR(250) NOT NULL,
  salaire double NOT NULL,
  annee_experience INT NOT NULL
);
 
INSERT INTO salarie (prenom, fonction, date_naissance, adresse, salaire, annee_experience) VALUES
  ('Dennis', 'Chef de projet', '1988-10-20', 'Paris', 4000, 8),
  ('Marie', 'Chef de projet', '1990-05-05', 'Nancy', 3600, 6),
  ('Pierre', 'Développeur', '1991-05-05', 'Nancy', 3200, 4),
  ('Simmon', 'Développeur', '1990-11-14', 'Paris', 3400, 5);