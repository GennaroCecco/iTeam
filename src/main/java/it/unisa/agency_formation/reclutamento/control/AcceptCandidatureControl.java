package it.unisa.agency_formation.reclutamento.control;

import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.reclutamento.domain.Candidatura;
import it.unisa.agency_formation.reclutamento.manager.ReclutamentoManager;
import it.unisa.agency_formation.reclutamento.manager.ReclutamentoManagerImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/AcceptCandidatureControl")
public class AcceptCandidatureControl extends HttpServlet {

    /**
     * Questo metodo controlla le operazioni per effettuare l'accettazione di una candidatura
     *
     * @param request  , request
     * @param response , response
     * @throws ServletException errore Servlet
     * @throws IOException      errore input output
     */

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente user = (Utente) request.getSession().getAttribute("user");
        if (user != null && user.getRole() == RuoliUtenti.HR) {
            int idCandidato = Integer.parseInt(request.getParameter("idCandidato"));
            String data = request.getParameter("data1");
            String tempo = request.getParameter("time");
            String dataOra = data + " " + tempo;
            try {
                Date data1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(dataOra);
                Timestamp timestamp = new Timestamp(data1.getTime());
                try {
                    Candidatura candidatura = getCandidaturaFromManager(idCandidato);
                    if (candidatura != null && acceptCandidatureFromManager(candidatura.getIdCandidatura(), user.getId(), timestamp)) {
                        response.getWriter().write("1"); //accettazione avvenuta
                    } else {
                        response.getWriter().write("2"); //accettazione non avvenuta
                        String descrizione = "Errore nell'accettazione della candidatura.";
                        response.sendRedirect("./static/Error.jsp?descrizione=" + descrizione);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            response.getWriter().write("3"); // user null o ruolo non adeguato
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
     * Questo metodo permette di ottenere una candidatura attraverso l'id del candidato utilizzando il manager
     *
     * @param idCandidato interessato
     * @return la candidatura interessata
     * @throws SQLException errore nella query
     */

    public static Candidatura getCandidaturaFromManager(int idCandidato) throws SQLException {
        ReclutamentoManager reclutamentoManager = new ReclutamentoManagerImpl();
        return reclutamentoManager.getCandidaturaById(idCandidato);
    }

    /**
     * Questo metodo permette di accettare la candidatura di un candidato utilizzando il manager
     *
     * @param idCandidatura id della candiatura da accettare
     * @param idHR          id dell'HR che accetta la candidatura
     * @param timestamp     data del colloquio
     * @return boolean (true = accettazione effettuata correttamente, false = altrimenti)
     * @throws SQLException errore nella query
     */

    public static boolean acceptCandidatureFromManager(int idCandidatura, int idHR, Timestamp timestamp) throws SQLException {
        ReclutamentoManager reclutamentoManager = new ReclutamentoManagerImpl();
        return reclutamentoManager.accettaCandidatura(idCandidatura, idHR, timestamp);
    }

}
