package it.unisa.agency_formation.FIA;

import it.unisa.agency_formation.autenticazione.domain.Dipendente;
import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManager;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManagerImpl;
import it.unisa.agency_formation.formazione.manager.FormazioneManager;
import it.unisa.agency_formation.formazione.manager.FormazioneManagerImpl;
import it.unisa.agency_formation.team.domain.Team;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

/* SaveTeamFromAI salva i dipendenti del team, il quale viene registrato, forniti dall'AI all'interno del database */
@WebServlet("/SalvaTeam")
public class SaveTeamFromAI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idTeam = Integer.parseInt(request.getParameter("idTeam"));
        String[] idDips = (String[]) request.getParameterValues("dip");
        for (String idDip : idDips) {
            String[] HEADERS = {"id", "name", "surname", "email",
                    "skill1", "skill2", "skill3", "level1", "level2", "level3"};
            Reader in = new FileReader(System.getProperty("user.home") + "\\IdeaProjects\\iTeam\\Dataset\\dataset.csv");
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader(HEADERS)
                    .withFirstRecordAsHeader()
                    .parse(in);
            for (CSVRecord record : records) {
                String id = record.get("id");
                if (Integer.parseInt(id) == Integer.parseInt(idDip)) {
                    String name = record.get("name");
                    String surname = record.get("surname");
                    String email = record.get("email");

                    Utente user = new Utente();
                    user.setId(Integer.parseInt(idDip));
                    user.setRole(RuoliUtenti.DIPENDENTE);
                    user.setEmail(email);
                    user.setName(name);
                    user.setSurname(surname);
                    user.setPwd("lol");
                    try {
                        saveUserFromManager(user);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        for (String idDip : idDips) {
            String[] HEADERS = {"id", "name", "surname", "email",
                    "skill1", "skill2", "skill3", "level1", "level2", "level3"};
            Reader in = new FileReader(System.getProperty("user.home") + "\\IdeaProjects\\iTeam\\Dataset\\dataset.csv");
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader(HEADERS)
                    .withFirstRecordAsHeader()
                    .parse(in);
            for (CSVRecord record : records) {
                String id = record.get("id");
                if (Integer.parseInt(id) == Integer.parseInt(idDip)) {
                    String skill1 = record.get("skill1");
                    String skill2 = record.get("skill2");
                    String skill3 = record.get("skill3");
                    String livello1 = record.get("level1");
                    String livello2 = record.get("level2");
                    String livello3 = record.get("level3");
                    Dipendente dip = new Dipendente();
                    dip.setId(Integer.parseInt(idDip));
                    dip.setAnnoNascita(2000);
                    Team teamTemp = new Team();
                    teamTemp.setIdTeam(idTeam);
                    dip.setTeam(teamTemp);
                    dip.setResidenza("FIA");
                    dip.setTelefono("025785" + idDip);
                    try {
                        saveDipFromManager(dip, Integer.parseInt(idDip));
                        int idSkill1 = getIdSkillFromManager(skill1);
                        int idSkill2 = getIdSkillFromManager(skill2);
                        int idSkill3 = getIdSkillFromManager(skill3);
                        saveSkillDipFromManager(Integer.parseInt(idDip), idSkill1, Integer.parseInt(livello1));
                        saveSkillDipFromManager(Integer.parseInt(idDip), idSkill2, Integer.parseInt(livello2));
                        saveSkillDipFromManager(Integer.parseInt(idDip), idSkill3, Integer.parseInt(livello3));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ListaTeam");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private static boolean saveUserFromManager(Utente user) throws SQLException {
        AutenticazioneManager manager = new AutenticazioneManagerImpl();
        return manager.salvaUtente2(user);
    }

    private static boolean saveDipFromManager(Dipendente dip, int id) throws SQLException {
        AutenticazioneManager manager = new AutenticazioneManagerImpl();
        manager.salvaDipendente2(dip, id);
        return manager.setTeamDipendente(id, dip.getTeam().getIdTeam());
    }

    private static boolean saveSkillDipFromManager(int idDip, int idSkill, int livello) throws SQLException {
        FormazioneManager manager = new FormazioneManagerImpl();
        return manager.addSkillDipendente(idSkill, idDip, livello);
    }

    private static int getIdSkillFromManager(String name) throws SQLException {
        FormazioneManager manager = new FormazioneManagerImpl();
        return manager.getIdSkill(name);
    }
}
