package it.unisa.agency_formation.reclutamento.control;

import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.reclutamento.domain.Candidatura;
import it.unisa.agency_formation.reclutamento.domain.StatiCandidatura;
import it.unisa.agency_formation.reclutamento.manager.ReclutamentoManager;
import it.unisa.agency_formation.reclutamento.manager.ReclutamentoManagerImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AssunzioneCandidatoControl")
public class AssunzioneCandidatoControl extends HttpServlet {

    /**
     * Questo metodo controlla le operazioni per effettuare l'assunzione di un candidato
     *
     * @param request  , request
     * @param response , response
     * @throws ServletException errore Servlet
     * @throws IOException      errore input output
     */

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente user = (Utente) request.getSession().getAttribute("user");
        if (user == null || user.getRole() != RuoliUtenti.HR) {
            response.getWriter().write("4"); // user null oppure ruolo non adatto
            request.getSession().invalidate();
            response.sendRedirect("./static/Login.jsp");
        } else {
            int idCandidato = Integer.parseInt(request.getParameter("idCandidato"));
            try {
                Candidatura candidatura = getCandidaturaFromManager(idCandidato);
                if (candidatura == null) {
                    response.getWriter().write("1"); //errore Candidatura
                    String descrizione = "Errore nel recuperare la candidatura";
                    response.sendRedirect("./static/Error.jsp?descrizione=" + descrizione);
                } else {
                    boolean esito = setStatoFromManager(candidatura.getIdCandidatura());
                    if (esito) {
                        response.getWriter().write("2"); // assunzione
                    } else {
                        response.getWriter().write("3"); //errore assunzione
                        String descrizione = "modifica stato candidatura non avvenuto";
                        response.sendRedirect("./static/Error.jsp?descrizione=" + descrizione);
                        return;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/HomeHR.jsp");
            dispatcher.forward(request, response);
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
     * Questo metodo permette di ottenere la candidatura attraverso l'id del candidato utilizzando il manager
     *
     * @param idCandidato id del candidato
     * @return la candidatura interessata
     * @throws SQLException errore nella query
     */

    public static Candidatura getCandidaturaFromManager(int idCandidato) throws SQLException {
        ReclutamentoManager reclutamentoManager = new ReclutamentoManagerImpl();
        return reclutamentoManager.getCandidaturaById(idCandidato);
    }

    /**
     * Questo metodo permette di settare lo stato della candidatura utilizzando il manager
     *
     * @param idCandidatura id della candidatura interessata
     * @return boolean (true = modifica eseguita con successo ,false = altrimenti)
     * @throws SQLException errore nella query
     */

    public static boolean setStatoFromManager(int idCandidatura) throws SQLException {
        ReclutamentoManager reclutamentoManager = new ReclutamentoManagerImpl();
        return reclutamentoManager.modificaStatoCandidatura(idCandidatura, StatiCandidatura.Assunzione);
    }
}
