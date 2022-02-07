package it.unisa.agency_formation.team.control;

import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManager;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManagerImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AddTeamControl")
public class AddTeamControl extends HttpServlet {

    /**
     * Questo metodo controlla le operazioni per effettuare l'aggiunta del dipendente nel team
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
            String action = req.getParameter("action");
            try {
                if (action.equalsIgnoreCase("aggiungi")) {
                    int idDip = Integer.parseInt(req.getParameter("id"));
                    if (idDip > 0) {
                        int idTeam = Integer.parseInt(req.getParameter("idTeam")); //messo questo controllo
                        if (!setTeamDipendenteFromManager(idDip, idTeam)) {
                            resp.getWriter().write("1"); // errore setTeam
                            String descrizione = "impossibile inserire il dipendente nel Team specificato";
                            resp.sendRedirect("./static/Error.jsp?descrizione=" + descrizione);
                            return;
                        }
                        resp.getWriter().write("2"); // set ok
                        dispatcher = req.getServletContext().getRequestDispatcher("/ListaTeam");
                        dispatcher.forward(req, resp);
                    } else {
                        resp.getWriter().write("3"); // idDip <1
                        resp.sendRedirect("/static/CreaTeam.jsp");
                    }
                } else {
                    resp.getWriter().write("4");
                    resp.sendRedirect("/static/CreaTeam.jsp");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            resp.getWriter().write("5");
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
     * Questo metodo permette di aggiungere un dipendente in un team utilizzando il manager
     *
     * @param idDip  id del dipendente
     * @param idTeam id del team
     * @return boolean (true = la modifica viene effettuata correttamente, false = altrimenti)
     * @throws SQLException errore nella query
     */

    public static boolean setTeamDipendenteFromManager(int idDip, int idTeam) throws SQLException {
        AutenticazioneManager autenticazioneManager = new AutenticazioneManagerImpl();
        return autenticazioneManager.setTeamDipendente(idDip, idTeam);
    }
}
