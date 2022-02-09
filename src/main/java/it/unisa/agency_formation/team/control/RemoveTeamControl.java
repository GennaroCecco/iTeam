package it.unisa.agency_formation.team.control;

import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
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

@WebServlet("/RemoveTeamControl")
public class RemoveTeamControl extends HttpServlet {

    /**
     * Questo metodo controlla le operazioni per effettuare la rimozione di un dipendente dal team
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
            int idDip = Integer.parseInt(req.getParameter("idDip"));
            if (idDip > 0) {
                try {
                    if (!rimuoviDipendenteFromManager(idDip)) {
                        resp.getWriter().write("4"); //dipendente non rimosso
                        String descrizione = "rimozione dipendente non effettuata";
                        resp.sendRedirect("./static/Error.jsp?descrizione=" + descrizione);
                        return;
                    }
                    resp.getWriter().write("1");
                    dispatcher = req.getServletContext().getRequestDispatcher("/ListaTeam");
                    dispatcher.forward(req, resp);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                resp.getWriter().write("2");
                dispatcher = req.getServletContext().getRequestDispatcher("/ListaTeam");
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

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * Questo metodo permette di rimuovere un dipendente utilizzando il manager
     *
     * @param idDip id del dipendente da rimuovere
     * @return boolean (true  =il dipendente è stato rimosso con successo, false altrimenti)
     * @throws SQLException errore nella query
     */

    public static boolean rimuoviDipendenteFromManager(int idDip) throws SQLException {
        TeamManager teamManager = new TeamManagerImpl();
        return teamManager.rimuoviDipendente(idDip);
    }
}
