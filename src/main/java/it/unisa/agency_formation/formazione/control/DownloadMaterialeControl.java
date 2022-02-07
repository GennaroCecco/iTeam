package it.unisa.agency_formation.formazione.control;

import it.unisa.agency_formation.autenticazione.domain.Dipendente;
import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManager;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManagerImpl;
import it.unisa.agency_formation.formazione.domain.Documento;
import it.unisa.agency_formation.formazione.manager.FormazioneManager;
import it.unisa.agency_formation.formazione.manager.FormazioneManagerImpl;

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

@WebServlet("/DownloadMaterialeControl")
public class DownloadMaterialeControl extends HttpServlet {
    private static final String directory = System.getProperty("user.home");

    /**
     * Questo metodo controlla le operazioni per effettuare il download del materiale di formazione
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
            ServletContext context = request.getServletContext();
            Documento documento = null;
            try {
                Dipendente dipendente = getDipendentefromManager(user.getId());
                if (dipendente == null) {
                    response.getWriter().write("1"); //dipendente null
                } else {
                    documento = getDocumentofromManager(dipendente.getTeam().getIdTeam());
                    if (documento == null) {
                        response.getWriter().write("2"); //documento null
                        String descrizione = "Documento inesistente";
                        response.sendRedirect("./static/Error.jsp?descrizione=" + descrizione);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (documento != null) {
                String pathMateriale = directory + documento.getMaterialeDiFormazione();
                File file = new File(pathMateriale);
                FileInputStream fileIn = new FileInputStream(file);
                String mimeType = context.getMimeType(pathMateriale);
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
                response.getWriter().write("3"); //documento scaricato
            } else {
                response.getWriter().write("4"); //documento non scaricato
            }
        } else {
            response.getWriter().write("5");
            request.getSession().invalidate();
            response.sendRedirect("./static/Login.jsp");
        }
    }

    /**
     * Questo metodo richiama il doGet
     *
     * @param request  , request
     * @param response , response
     * @throws ServletException errore Servlet
     * @throws IOException      errore input output
     */

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Questo metodo permette di ottenere un dipendente utilizzando il manager
     *
     * @param idUtente id del dipendente
     * @return il dipendente interessato
     * @throws SQLException errore nella query
     */

    public static Dipendente getDipendentefromManager(int idUtente) throws SQLException {
        AutenticazioneManager autenticazioneManager = new AutenticazioneManagerImpl();
        return autenticazioneManager.getDipendente(idUtente);
    }

    /**
     * Questo metodo permette di ottenere i documenti interessati utilizzando il manager
     *
     * @param idTeam id del team
     * @return il documento interessato
     * @throws SQLException errore nella query
     */

    public static Documento getDocumentofromManager(int idTeam) throws SQLException {
        FormazioneManager formazioneManager = new FormazioneManagerImpl();
        return formazioneManager.getMaterialeByIdTeam(idTeam);
    }
}
