package it.unisa.agency_formation.reclutamento.control;

import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.reclutamento.domain.Candidatura;
import it.unisa.agency_formation.reclutamento.domain.StatiCandidatura;
import it.unisa.agency_formation.reclutamento.manager.ReclutamentoManager;
import it.unisa.agency_formation.reclutamento.manager.ReclutamentoManagerImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@WebServlet("/UploadCandidatureControl")
@MultipartConfig
public class UploadCandidatureControl extends HttpServlet {

    private static final String pathRelative = "\\AgencyFormationFile\\Candidature\\";
    private static final String pathAbsolute = System.getProperty("user.home") + pathRelative;
    private static final int MAXDIM = 10485760; //10MB

    /**
     * Questo metodo controlla le operazioni per effettuare l'upload del curriculum e possibili documenti aggiuntivi
     *
     * @param request  , request
     * @param response , response
     * @throws ServletException errore Servlet
     * @throws IOException      errore input output
     */

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente user = (Utente) request.getSession().getAttribute("user");

        if (user != null && user.getRole() == RuoliUtenti.CANDIDATO) {

            if (request.getParameter("sceltaUpload") == null) {
                try {
                    Candidatura cand = getCandidaturaByIdFromManager(user.getId());
                    request.setAttribute("candidatura", cand);
                    response.getWriter().write("9");
                    RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/static/Upload.jsp");
                    dispatcher.forward(request, response);
                    return;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            //se scelta = 1 curriculum caricato
            //se scelta = 2 documenti aggiuntivi caricati
            int scelta = Integer.parseInt(request.getParameter("sceltaUpload"));
            File file = new File(pathAbsolute + "\\" + "IdUtente-" + user.getId());
            if (scelta == 1) {
                Part curriculum = request.getPart("curriculum");
                if (curriculum.getSize() > MAXDIM) {
                    response.getWriter().write("1"); //file troppo grande
                    String descrizione = "dimensioni file elevate";
                    response.sendRedirect("./static/Error.jsp?descrizione=" + descrizione);
                    return;
                } else {
                    Candidatura cand = new Candidatura();
                    file.mkdirs();
                    curriculum.write(file.getAbsolutePath() + "\\" + curriculum.getSubmittedFileName());
                    String cv = pathRelative + "\\" + "IdUtente-" + user.getId() + "\\" + curriculum.getSubmittedFileName();
                    Date date = new Date();
                    java.sql.Date data = new java.sql.Date(date.getTime());
                    cand.setCurriculum(cv);
                    cand.setStato(StatiCandidatura.NonRevisionato);
                    cand.setDataCandidatura(data);
                    cand.setIdCandidato(user.getId());
                    try {
                        if (uploadCandidatureFromManager(cand)) {
                            response.getWriter().write("4"); // curriculum caricato
                            request.setAttribute("candidatura", cand);
                        } else {
                            response.getWriter().write("5"); // curriculum non caricato
                            String descrizione = "caricamento file non andato a buon fine";
                            response.sendRedirect("./static/Error.jsp?descrizione=" + descrizione);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else if (scelta == 2) {
                Part documenti = request.getPart("documenti");
                if (documenti.getSize() > MAXDIM) {
                    response.getWriter().write("2"); //file troppo grande
                    String descrizione = "dimensioni file elevate";
                    response.sendRedirect("./static/Error.jsp?descrizione=" + descrizione);
                    return;
                } else {
                    Candidatura cand = new Candidatura();
                    documenti.write(file.getAbsolutePath() + "\\" + documenti.getSubmittedFileName());
                    String documentiAggiuntivi = pathRelative + "\\" + "IdUtente-" + user.getId() + "\\" + documenti.getSubmittedFileName();
                    cand.setIdCandidato(user.getId());
                    cand.setDocumentiAggiuntivi(documentiAggiuntivi);
                    try {
                        if (uploadCandidatureFromManager(cand)) {
                            response.getWriter().write("6"); //documenti aggiuntivi caricati
                            request.setAttribute("candidatura", cand);
                        } else {
                            response.getWriter().write("7"); //documenti aggiuntivi non caricati
                            String descrizione = "caricamento file non andato a buon fine";
                            response.sendRedirect("./static/Error.jsp?descrizione=" + descrizione);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else if (scelta == 3) {
                response.getWriter().write("3"); //ricandidatura
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/static/Upload.jsp");
                dispatcher.forward(request, response);
            }
            try {
                Candidatura candidatura = getCandidaturaFromManager(user.getId());
                request.setAttribute("candidatura", candidatura);
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/HomeCandidato.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            response.getWriter().write("8");
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

    public static Candidatura getCandidaturaFromManager(int idCandidato) throws SQLException {
        ReclutamentoManager reclutamentoManager = new ReclutamentoManagerImpl();
        return reclutamentoManager.getCandidaturaById(idCandidato);
    }

    /**
     * Questo metodo permette di caricare la candidatura utilizzando il manager
     *
     * @param candidatura la candidatura interessata
     * @return boolean (true = caricamento effettuato con successo, false = altrimenti)
     * @throws SQLException errore nella query
     */

    public static boolean uploadCandidatureFromManager(Candidatura candidatura) throws SQLException {
        ReclutamentoManager reclutamentoManager = new ReclutamentoManagerImpl();
        return reclutamentoManager.caricaCandidatura(candidatura);
    }
    /**
     * Questo metodo permette di ottenere la candidatura di un candidato utilizzando il manager
     *
     * @param idCandidato id del candidato
     * @return la candidatura interessata
     * @throws SQLException errore nella query
     */

    public static Candidatura getCandidaturaByIdFromManager(int idCandidato) throws SQLException {
        ReclutamentoManager reclutamentoManager = new ReclutamentoManagerImpl();
        return reclutamentoManager.getCandidaturaById(idCandidato);
    }
}
