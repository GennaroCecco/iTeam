package it.unisa.agency_formation.autenticazione.control;

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


@WebServlet("/ProfiloControl")
public class ProfiloControl extends HttpServlet {

    /**
     * Questo metodo controlla le operazioni per visualizzare e modificare il profilo
     *
     * @param request  , request
     * @param response , response
     * @throws ServletException errore Servlet
     * @throws IOException      errore input output
     */

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente user = (Utente) request.getSession().getAttribute("user");
        try {
            if (user != null && user.getRole() == RuoliUtenti.DIPENDENTE) {
                Dipendente dip = getAllDataDipFromManager(user.getId());
                ArrayList<Skill> skills;
                skills = getSkillDipendenteFromManager(dip.getIdDipendente());
                if (skills != null && skills.size() >= 1) {
                    dip.setSkills(skills);
                }
                response.getWriter().write("1"); // retrieve data ok
                request.setAttribute("dip", dip);
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/static/Profilo.jsp");
                dispatcher.forward(request, response);
            } else {
                response.getWriter().write("2"); //errore
                request.getSession().invalidate();
                response.sendRedirect("./static/Login.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
     * Questo metodo permette di ottenere tutti i dati del dipendente utilizzando il manager
     *
     * @param id del dipendente
     * @return il dipendente interessato
     * @throws SQLException errore nella query
     */

    public static Dipendente getAllDataDipFromManager(int id) throws SQLException {
        AutenticazioneManager autenticazioneManager = new AutenticazioneManagerImpl();
        return autenticazioneManager.getDipendente(id);
    }

    /**
     * Questo metodo permette di ottenere tutte le skill di un dipendente utilizzando il manager
     *
     * @param idDip id del dipendente
     * @return {@literal ArrayList<@link Skill>} le skills del dipendente
     * @throws SQLException errore nella query
     */

    public static ArrayList<Skill> getSkillDipendenteFromManager(int idDip) throws SQLException {
        FormazioneManager formazioneManager = new FormazioneManagerImpl();
        return formazioneManager.recuperoSkillConIdDipendente(idDip);
    }
}
