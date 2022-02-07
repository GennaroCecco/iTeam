package it.unisa.agency_formation.reclutamento.control;

import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
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
@WebServlet("/RicandidaturaControl")
public class RicandidaturaControl extends HttpServlet {

    /**
     * Questo metodo controlla le operazioni per effettuare la ricandidatura di un candidato
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
            response.getWriter().write("1"); //utente null o ruolo diverso da Candidato
            request.getSession().invalidate();
            response.sendRedirect("./static/Login.jsp");
        } else {
            try {
                if (!eliminaCandidaturaFromManager(user.getId())) {
                    response.getWriter().write("2"); //eliminazione candidatura fallita
                    String descrizione = "eliminazione della candidatura non andata a buon fine";
                    response.sendRedirect("./static/Error.jsp?=" + descrizione);
                } else {
                    request.setAttribute("sceltaUpload", 3);
                    response.getWriter().write("3"); // avvenuta eliminazione candidatura
                    RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/UploadCandidatureControl");
                    dispatcher.forward(request, response);
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * Questo metodo permette di far eliminare la candidatura di un candidato utilizzando il manager
     *
     * @param idCandidato id del candidato interessato
     * @return boolean (true = l'eliminazione effetturata correttamente , false = altrimenti)
     * @throws SQLException errore nella query
     */

    public static boolean eliminaCandidaturaFromManager(int idCandidato) throws SQLException {
        ReclutamentoManager reclutamentoManager = new ReclutamentoManagerImpl();
        return reclutamentoManager.ricandidatura(idCandidato);
    }
}
