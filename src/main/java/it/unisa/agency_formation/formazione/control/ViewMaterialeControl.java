package it.unisa.agency_formation.formazione.control;

import it.unisa.agency_formation.autenticazione.domain.Dipendente;
import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManager;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManagerImpl;
import it.unisa.agency_formation.formazione.domain.Documento;
import it.unisa.agency_formation.formazione.manager.FormazioneManager;
import it.unisa.agency_formation.formazione.manager.FormazioneManagerImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/ViewMaterialeControl")
public class ViewMaterialeControl extends HttpServlet {

    /**
     * Questo metodo controlla le operazioni per visualizzare il materiale di formazione
     *
     * @param request  , request
     * @param response , response
     * @throws ServletException errore Servlet
     * @throws IOException      errore input output
     */

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente user = (Utente) request.getSession().getAttribute("user");
        if (user != null && user.getRole() == RuoliUtenti.DIPENDENTE) {
            try {
                Dipendente dipendente = getDipendenteFromManager(user.getId());
                if (dipendente == null) {
                    response.getWriter().write("1"); //errore retrieve dipendente
                    String err1 = "Errore recupero dipendente";
                    response.sendRedirect("./static/Error.jsp?descrizione=" + err1);
                } else {
                    int idTeam = dipendente.getTeam().getIdTeam();
                    Documento documento = getDocumentoFromManager(idTeam);
                    if (documento != null) {
                        response.getWriter().write("2"); //il documento esiste
                    } else {
                        response.getWriter().write("3"); //il documento non esiste
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            response.getWriter().write("4"); // user null o ruolo non corretto
            request.getSession().invalidate();
            response.sendRedirect("./static/Login.jsp");
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
     * Questo metodo permette di ottenere un dipendente attraverso il suo id utilizzando il manager
     *
     * @param idUtente id del dipendente
     * @return il dipendente interessato
     * @throws SQLException errore nella query
     */
    public static Dipendente getDipendenteFromManager(int idUtente) throws SQLException {
        AutenticazioneManager autenticazioneManager = new AutenticazioneManagerImpl();
        return autenticazioneManager.getDipendente(idUtente);
    }

    /**
     * Questo metodo permette di ottenere un documento appartenente ad un team utilizzando il manager
     *
     * @param idTeam , id del team
     * @return documento interessato
     * @throws SQLException errore nella query
     */
    public static Documento getDocumentoFromManager(int idTeam) throws SQLException {
        FormazioneManager formazioneManager = new FormazioneManagerImpl();
        return formazioneManager.getMaterialeByIdTeam(idTeam);
    }
}
