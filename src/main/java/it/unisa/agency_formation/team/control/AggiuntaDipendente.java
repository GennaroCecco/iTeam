package it.unisa.agency_formation.team.control;


import it.unisa.agency_formation.autenticazione.domain.Dipendente;
import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManager;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManagerImpl;
import it.unisa.agency_formation.formazione.domain.Skill;
import it.unisa.agency_formation.formazione.manager.FormazioneManager;
import it.unisa.agency_formation.formazione.manager.FormazioneManagerImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/AggiuntaDipendente")
public class AggiuntaDipendente extends HttpServlet {

    /**
     * Questo metodo controlla le operazioni per effettuare la visualizzazione dei dipendenti disponibili
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
           /*visualizzo tutti i dipendenti Dispo*/
           int idTeam = Integer.parseInt(req.getParameter("idTeam"));
           try {
               ArrayList<Dipendente> dipendenti = getTuttiDipendentiFromManager();
               if (dipendenti != null && dipendenti.size() > 0) {
                   for (Dipendente dipendente : dipendenti) {
                       ArrayList<Skill> skills;
                       if (getSkillDipendenteFromManager(dipendente.getIdDipendente()) != null && getSkillDipendenteFromManager(dipendente.getIdDipendente()).size() > 0) {
                           skills = getSkillDipendenteFromManager(dipendente.getIdDipendente());
                           dipendente.setSkills(skills);
                       }
                   }
               }
               req.setAttribute("dipendenti", dipendenti);
               req.setAttribute("idTeam", idTeam);
               resp.getWriter().write("1");
               dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/ListaDipendenti.jsp");
               dispatcher.forward(req, resp);
           } catch (SQLException e) {
               e.printStackTrace();
           }
       } else {
           resp.getWriter().write("2");
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
     * Questo metodo permette di ottenere le skill possedute da un dipendente utilizzando il manager
     *
     * @param idDip id del dipendnete interessato
     * @return {@literal ArrayList<@link Skill>} lista delle skill
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
