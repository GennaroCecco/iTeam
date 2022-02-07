package it.unisa.agency_formation.team.control;

import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.team.manager.TeamManager;
import it.unisa.agency_formation.team.manager.TeamManagerImpl;
import it.unisa.agency_formation.utils.Check;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/SpecificaCompetenzeControl")
public class SpecificaCompetenzeControl extends HttpServlet {

    /**
     * Questo metodo controlla le operazioni per effettuare la specifica delle competenze
     *
     * @param req  , request
     * @param resp , response
     * @throws ServletException errore Servlet
     * @throws IOException      errore input output
     */

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        Utente user = (Utente) req.getSession().getAttribute("user");
        if (user != null && user.getRole() == RuoliUtenti.TM) {
            String action = req.getParameter("action");
            if (action.equalsIgnoreCase("competenze")) { //update competenze
                int idTeam = Integer.parseInt(req.getParameter("idTeam"));
                String competence = req.getParameter("specCompetenze");
                if (Check.checkCompetence(competence)) {
                    try {
                        if (!inserimentoCompetenzeNelTeam(competence, idTeam)) {
                            resp.getWriter().write("5");
                            String descrizione = "Errore nella specifica delle competenze";
                            resp.sendRedirect("./static/Error.jsp?descrizione=" + descrizione);
                        } else {
                            resp.getWriter().write("1");
                            dispatcher = req.getServletContext().getRequestDispatcher("/ListaTeam");
                            dispatcher.forward(req, resp);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    resp.getWriter().write("4"); //errore competenza
                    resp.sendRedirect("./static/Error.jsp?descrizione=Competenze invalide");
                }
            } else {
                resp.getWriter().write("2");
                dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/ListaTeamTM.jsp");
                dispatcher.forward(req, resp);
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

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * Questo metodo permette di specificare le competenze ad un team utilizzando il manager
     *
     * @param competenze da specificare
     * @param idTeam     id del team
     * @return boolean (true= specifica andata a buon fine, false = altrimenti)
     * @throws SQLException errore nella query
     */

    public static boolean inserimentoCompetenzeNelTeam(String competenze, int idTeam) throws SQLException {
        TeamManager teamManager = new TeamManagerImpl();
        return teamManager.specificaLeCompetenze(competenze, idTeam);
    }
}
