package it.unisa.agency_formation.formazione.control;

import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.formazione.domain.Documento;
import it.unisa.agency_formation.formazione.manager.FormazioneManager;
import it.unisa.agency_formation.formazione.manager.FormazioneManagerImpl;

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

@WebServlet("/UploadMaterialeControl")
@MultipartConfig
public class UploadMaterialeControl extends HttpServlet {
    private static final String pathRelative = "\\AgencyFormationFile\\MaterialeFormazione\\";
    private static final String pathAbsolute = System.getProperty("user.home") + pathRelative;
    private static final int MAXDIM = 10485760; //10MB

    /**
     * Questo metodo controlla le operazioni per effettuare l'upload del materiale di formazione
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
            if (request.getParameter("idTeam") == null) {
                response.getWriter().write("2"); //idTeam non passato
                String errore1 = "Id del Team non passato";
                response.sendRedirect("./static/Error.jsp?descrizione=" + errore1);
            } else {
                int idTeam = Integer.parseInt(request.getParameter("idTeam"));
                File file = new File(pathAbsolute + "\\" + "IdTeam-" + idTeam);
                Documento documento = new Documento();
                file.mkdirs();
                if (request.getPart("materiale") == null) {
                    response.getWriter().write("3"); //materiale non passato
                    String errore2 = "Errore. File non selezionato.Riprova";
                    response.sendRedirect("./static/Error.jsp?descrizione=" + errore2);
                } else {
                    Part part = request.getPart("materiale");
                    if (part.getSize() > MAXDIM) {
                        response.getWriter().write("4"); //file troppo grande
                        String errore3 = "Dimensioni file eccessive";
                        response.sendRedirect("./static/Error.html?descrizione=" + errore3);
                        return;
                    }
                    part.write(file.getAbsolutePath() + "\\" + part.getSubmittedFileName());
                    String pathMaterialeFormazione = pathRelative + "\\" + "IdTeam-" + idTeam + "\\" + part.getSubmittedFileName();
                    documento.setMaterialeDiFormazione(pathMaterialeFormazione);
                    documento.setIdTeam(idTeam);
                    documento.setIdHR(user.getId());
                    try {
                        boolean esito = saveDocumentFromManager(documento);
                        if (esito) {
                            response.getWriter().write("5"); //documento salvato
                            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/ListaTeam");
                            dispatcher.forward(request, response);
                        } else {
                            response.getWriter().write("6"); //documento non salvato
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            response.getWriter().write("1"); //user null oppure non dipendente
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
     * Questo metodo permette di salvare un documento utilizzando il manager
     *
     * @param documento da salvare
     * @return boolean ( true = il documento viene salvato correttamente, false = altrimenti)
     * @throws SQLException errore nella query
     */

    public static boolean saveDocumentFromManager(Documento documento) throws SQLException {
        FormazioneManager formazioneManager = new FormazioneManagerImpl();
        return formazioneManager.salvaDocumento(documento);

    }

}
