package it.unisa.agency_formation.team.control;

import it.unisa.agency_formation.autenticazione.domain.Dipendente;
import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManager;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManagerImpl;
import it.unisa.agency_formation.formazione.domain.Skill;
import it.unisa.agency_formation.formazione.manager.FormazioneManager;
import it.unisa.agency_formation.formazione.manager.FormazioneManagerImpl;
import it.unisa.agency_formation.team.domain.Team;
import it.unisa.agency_formation.team.manager.TeamManager;
import it.unisa.agency_formation.team.manager.TeamManagerImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/ListaDipendentiControl")
public class ListaDipendentiControl extends HttpServlet {

    /**
     * Questo metodo controlla le operazioni per effettuare la visualizzazione di tutti i dipendenti
     *
     * @param req  , request
     * @param resp , response
     * @throws ServletException errore Servlet
     * @throws IOException      errore input output
     */

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Utente user = (Utente) req.getSession().getAttribute("user");
        if (user != null && user.getRole() == RuoliUtenti.TM) {
            RequestDispatcher dispatcher;
            /*visualizzo tutti i dipendenti*/
            try {
                ArrayList<Dipendente> dipendenti = getTuttiDipendentiFromManager();
                if (dipendenti != null && dipendenti.size() > 0) {
                    for (Dipendente dipendente : dipendenti) {
                        ArrayList<Skill> skills;
                        if (dipendente.getTeam() != null && dipendente.getTeam().getIdTeam() > 0) {
                            dipendente.setTeam(getTeamIdFromManager(dipendente.getTeam().getIdTeam()));
                        }
                        if (getSkillDipendenteFromManager(dipendente.getIdDipendente()) != null && getSkillDipendenteFromManager(dipendente.getIdDipendente()).size() > 0) {
                            skills = getSkillDipendenteFromManager(dipendente.getIdDipendente());
                            dipendente.setSkills(skills);
                        }
                    }
                    req.setAttribute("dipendenti", dipendenti);
                    resp.getWriter().write("2");
                    dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/VisualizzaDipendenti.jsp");
                    dispatcher.forward(req, resp);
                } else {
                    resp.getWriter().write("1");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            resp.getWriter().write("3");
            req.getSession().invalidate();
            resp.sendRedirect("./static/Login.jsp");
        }
    }

    /**
     * Questo metodo richiama il doGet
     *
     * @param req  , request
     * @param resp , response
     * @throws ServletException errore Servlet
     * @throws IOException      errore input output
     */


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * Questo metodo permette di ottenere un team attraverso il suo id utilizzando il manager
     *
     * @param idTeam id del team
     * @return il team interessato
     * @throws SQLException errore nella query
     */

    public static Team getTeamIdFromManager(int idTeam) throws SQLException {
        TeamManager teamManager = new TeamManagerImpl();
        return teamManager.getTeamById(idTeam);
    }

    /**
     * Questo metodo permette di ottenere tutte le skill di un dipendente utilizzando il manager
     *
     * @param idDip id del dipendente
     * @return {@literal ArrayList<@link Skill>} lista di skill possedute da un dipendente
     * @throws SQLException errore nella query
     */

    public static ArrayList<Skill> getSkillDipendenteFromManager(int idDip) throws SQLException {
        FormazioneManager formazioneManager = new FormazioneManagerImpl();
        return formazioneManager.recuperoSkillConIdDipendente(idDip);
    }

    /**
     * Questo metodo permette di ottenere tutti i dipendenti utilizzando il manager
     *
     * @return {@literal ArrayList<@link Dipendente>} lista di tutti i dipendenti
     * @throws SQLException errore nella query
     */

    public static ArrayList<Dipendente> getTuttiDipendentiFromManager() throws SQLException {
        AutenticazioneManager autenticazioneManager = new AutenticazioneManagerImpl();
        return autenticazioneManager.getTuttiDipendenti();
    }


}
