package it.unisa.agency_formation.reclutamento.control;

import it.unisa.agency_formation.autenticazione.domain.Dipendente;
import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.StatiDipendenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManager;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManagerImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/CandidatoAssuntoControl")
public class CandidatoAssuntoControl extends HttpServlet {

    /**
     * Questo metodo controlla le operazioni per completare l'assunzione del candidato rendendolo dipendente
     *
     * @param request  , request
     * @param response , response
     * @throws ServletException errore Servlet
     * @throws IOException      errore input output
     */

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente user = (Utente) request.getSession().getAttribute("user");
        if (user == null || user.getRole() != RuoliUtenti.CANDIDATO) {
            response.getWriter().write("1"); // user null o ruolo non adatto
            request.getSession().invalidate();
            response.sendRedirect("./static/Login.jsp");
        } else {
            try {
                int idDipendente = user.getId();
                    int annoNascita = Integer.parseInt(request.getParameter("annoDipendente"));
                    String residenza = request.getParameter("residenzaDipendente");
                    String telefono = request.getParameter("telefonoDipendente");
                    if (residenza != null && telefono != null) {
                        Dipendente dipendente = new Dipendente();
                        dipendente.setIdDipendente(idDipendente);
                        dipendente.setAnnoNascita(annoNascita);
                        dipendente.setResidenza(residenza);
                        dipendente.setTelefono(telefono);
                        dipendente.setStato(StatiDipendenti.DISPONIBILE);
                        boolean esito = assumiCandidatoFromManager(dipendente);
                        if (!esito) {
                            response.getWriter().write("2"); //errore assunzione
                            String descrizione = "Si è verificato un errore nell'assunzione";
                            response.sendRedirect("./static/Error.jsp?descrizione=" + descrizione);
                        } else {
                            request.getSession().invalidate();
                            response.getWriter().write("3"); // assunzione
                            response.sendRedirect("./static/Login.jsp");
                        }
                    } else {
                        response.getWriter().write("4"); // residenza o telefono o null
                        String descrizione = "Residenza o Telefono null";
                        response.sendRedirect("./static/Error.jsp?descrizione=" + descrizione);
                    }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
     * Questo metodo permette di assumere un candidato rendendolo dipendente utilizzando il manager
     *
     * @param dipendente nuovo dipendente
     * @return boolean (true = dipendente creato correttamente, false = altrimenti)
     * @throws SQLException errore nella query
     */

    public static boolean assumiCandidatoFromManager(Dipendente dipendente) throws SQLException {
        AutenticazioneManager autenticazioneManager = new AutenticazioneManagerImpl();
        return autenticazioneManager.assumiCandidato(dipendente);
    }
}
