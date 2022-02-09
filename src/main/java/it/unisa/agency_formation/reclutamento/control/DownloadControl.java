package it.unisa.agency_formation.reclutamento.control;

import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.reclutamento.domain.Candidatura;
import it.unisa.agency_formation.reclutamento.manager.ReclutamentoManager;
import it.unisa.agency_formation.reclutamento.manager.ReclutamentoManagerImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

@WebServlet("/DownloadControl")
public class DownloadControl extends HttpServlet {

    private final String directory = System.getProperty("user.home");

    /**
     * Questo metodo controlla le operazioni per effettuare il download del curriculum e possibili documenti aggiuntivi
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

            String whatDownload = request.getParameter("toDownload");
            int idCandidato = Integer.parseInt(request.getParameter("idCandidato"));
            Candidatura candidatura = null;
            ServletContext context = request.getServletContext();
            try {
                candidatura = getCandidaturaFromManager(idCandidato);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (candidatura != null) {
                if (whatDownload.equalsIgnoreCase("curriculum")) {
                    String pathCurriculum = directory + candidatura.getCurriculum();
                    File file = new File(pathCurriculum);
                    FileInputStream fileIn = new FileInputStream(file);
                    String mimeType = context.getMimeType(pathCurriculum);
                    if (mimeType == null) {
                        mimeType = "application/octet-stream";
                    }
                    response.setContentType(mimeType);
                    response.setContentLength((int) file.length());
                    String headerKey = "Content-Disposition";
                    String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
                    response.setHeader(headerKey, headerValue);
                    OutputStream outStream = response.getOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = fileIn.read(buffer)) != -1) {
                        outStream.write(buffer, 0, bytesRead);
                    }
                    fileIn.close();
                    outStream.close();
                    response.getWriter().write("3"); // curriculum download
                } else if (whatDownload.equalsIgnoreCase("documenti")) {
                    String pathDocumenti = directory + candidatura.getDocumentiAggiuntivi();
                    File file = new File(pathDocumenti);
                    FileInputStream fileIn = new FileInputStream(file);
                    String mimeType = context.getMimeType(pathDocumenti);
                    if (mimeType == null) {
                        mimeType = "application/octet-stream";
                    }
                    response.setContentType(mimeType);
                    response.setContentLength((int) file.length());
                    String headerKey = "Content-Disposition";
                    String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
                    response.setHeader(headerKey, headerValue);
                    OutputStream outStream = response.getOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = fileIn.read(buffer)) != -1) {
                        outStream.write(buffer, 0, bytesRead);
                    }
                    fileIn.close();
                    outStream.close();
                    response.getWriter().write("4"); // materiale download
                }
            } else {
                response.getWriter().write("1"); //file non esistente
                String descrizione = "file inesistente";
                response.sendRedirect("./static/Error.jsp?descrizione=" + descrizione);
            }
        } else {
            response.getWriter().write("2"); // utente null o ruolo non adatto
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
     * Questo metodo permette di ottenere la candidatura attraverso l'id del candidato utilizzando il manager
     *
     * @param idCandidato id del candidato
     * @return candidatura interessata
     * @throws SQLException errore nella query
     */

    public static Candidatura getCandidaturaFromManager(int idCandidato) throws SQLException {
        ReclutamentoManager reclutamento = new ReclutamentoManagerImpl();
        return reclutamento.getCandidaturaById(idCandidato);
    }
}
