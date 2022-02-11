package it.unisa.agency_formation.FIA;
import it.unisa.agency_formation.autenticazione.DAO.DipendenteDAO;
import it.unisa.agency_formation.autenticazione.DAO.DipendenteDAOImpl;
import it.unisa.agency_formation.autenticazione.DAO.UtenteDAO;
import it.unisa.agency_formation.autenticazione.DAO.UtenteDAOImpl;
import it.unisa.agency_formation.autenticazione.domain.Dipendente;
import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.StatiDipendenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.formazione.DAO.SkillDAO;
import it.unisa.agency_formation.formazione.DAO.SkillDAOImpl;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java.sql.SQLException;


public class Popolazione {

    public static void main(String[] args) throws IOException, SQLException {
        String[] HEADERS = {"id", "name", "surname", "email",
                "skill1", "skill2", "skill3", "level1", "level2", "level3"};
        Reader in = new FileReader((System.getProperty("user.home") + "\\IdeaProjects\\iTeam\\Dataset\\dataset.csv"));
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withHeader(HEADERS)
                .withFirstRecordAsHeader()
                .parse(in);
        UtenteDAO utenteDAO = new UtenteDAOImpl();
        DipendenteDAO dipendenteDAO = new DipendenteDAOImpl();
        SkillDAO skillDAO = new SkillDAOImpl();
        int i = 0;
        for (CSVRecord record : records) {
            if(i==500){break;}
            String id = record.get("id");
            String name = record.get("name");
            String surname = record.get("surname");
            String email = record.get("email");
            String skill1 = record.get("skill1");
            String skill2 = record.get("skill2");
            String skill3 = record.get("skill3");
            String livello1 = record.get("level1");
            String livello2 = record.get("level2");
            String livello3 = record.get("level3");
            Utente user = new Utente();
            user.setId(Integer.parseInt(id));
            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setPwd("lol");
            user.setRole(RuoliUtenti.DIPENDENTE);
            Dipendente dipendente = new Dipendente();
            dipendente.setIdDipendente(Integer.parseInt(id));
            dipendente.setResidenza("Fisciano");
            dipendente.setTelefono("1587952"+id);
            dipendente.setStato(StatiDipendenti.DISPONIBILE);
            dipendente.setAnnoNascita(2000);
            utenteDAO.salvaUtente(user);
            dipendenteDAO.salvaDipendente(dipendente);
            skillDAO.salvaSkillDipendente(skillDAO.recuperaSkillByNome(skill1).getIdSkill(),Integer.parseInt(id),Integer.parseInt(livello1));
            skillDAO.salvaSkillDipendente(skillDAO.recuperaSkillByNome(skill2).getIdSkill(),Integer.parseInt(id),Integer.parseInt(livello2));
            skillDAO.salvaSkillDipendente(skillDAO.recuperaSkillByNome(skill3).getIdSkill(),Integer.parseInt(id),Integer.parseInt(livello3));
            i++;
        }
    }
}
