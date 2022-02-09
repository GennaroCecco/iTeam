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
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import static java.util.Objects.requireNonNull;


@WebServlet("/RejectCandidatureControl")
public class RejectCandidatureControl extends HttpServlet {

    private static final String path = "\\AgencyFormationFile\\Candidature\\";
    private static final String pathAbsolute = System.getProperty("user.home") + path;

    /**
     * Questo metodo controlla le operazioni per effettuare il rifiuto della candidatura
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
            try {
                Candidatura candidatura = getCandidatura(idCandidato);
                File toDelete = new File(pathAbsolute + "IdUtente-" + candidatura.getIdCandidato());

                delete(toDelete);
                if (rejectCandidatura(candidatura.getIdCandidatura(), user.getId())) {
                    response.getWriter().write("1"); //rifiuto ok
                } else {
                    response.getWriter().write("2"); //rifiuto non avvenuto
                    String descrizione = "rifiuto non andato a buon fine";
                    response.sendRedirect("./static/Error.jsp?descrizione=" + descrizione);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            response.getWriter().write("3"); //rifiuto ok
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
     * Questo metodo permette di ottenere la candidatura di un candidato utilizzando il manager
     *
     * @param idCandidato id del candidato
     * @return la candidatura interessata
     * @throws SQLException errore nella query
     */

    public static Candidatura getCandidatura(int idCandidato) throws SQLException {
        ReclutamentoManager reclutamentoManager = new ReclutamentoManagerImpl();
        return reclutamentoManager.getCandidaturaById(idCandidato);
    }

    /**
     * Questo metodo permette di rifiutare una candidatura utilizzando il manager
     *
     * @param idCandidatura id della candidatura
     * @param idHR          che rifiuta la candidatura
     * @return boolean (true = la candidatura viene rifiutata correttamente, false = altrimenti)
     * @throws SQLException errore nella query
     */

    public static boolean rejectCandidatura(int idCandidatura, int idHR) throws SQLException {
        ReclutamentoManager reclutamentoManager = new ReclutamentoManagerImpl();
        return reclutamentoManager.rifiutaCandidatura(idCandidatura, idHR);
    }

    /**
     * Questo metodo permette di eliminare un file
     * @param file file da eliminare
     */

    public void delete(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        for (File subFile : requireNonNull(file.listFiles())) {
            if (subFile.isDirectory()) {
                delete(subFile);
            } else {
                subFile.delete();
            }
        }
        file.delete();
    }
}
