package it.unisa.agency_formation.autenticazione.control;

import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManager;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManagerImpl;
import it.unisa.agency_formation.utils.Check;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/RegistrazioneControl")
public class RegistrazioneControl extends HttpServlet {

    /**
     * Questo metodo controlla le operazioni per effettuare una registrazione
     *
     * @param request  , request
     * @param response , response
     * @throws ServletException errore Servlet
     * @throws IOException      errore input output
     */

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente user = new Utente();
            if (Check.checkName(request.getParameter("nome"))
                    && Check.checkSurname(request.getParameter("cognome"))
                    && Check.checkEmail(request.getParameter("email"))) {
                user.setName(request.getParameter("nome"));
                user.setSurname(request.getParameter("cognome"));
                user.setEmail(request.getParameter("email"));
                user.setPwd(request.getParameter("pwd"));
                user.setRole(RuoliUtenti.CANDIDATO); //il ruolo = 1 perchè il candidato è l'unico che si registra
                try {
                    if (registrazioneFromManager(user)) {
                        Utente result = loginFromManager(user.getEmail(), user.getPwd());
                        request.getSession().setAttribute("user", result);
                        response.getWriter().write("5"); //registrazione avvenuta con successo
                        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/HomeCandidato.jsp");
                        dispatcher.forward(request, response);
                        return;
                    } else {
                        response.getWriter().write("4"); // errore nella registrazione
                        String error = "Esiste già un utente con questa e-mail";
                        response.sendRedirect("./static/Registrazione.jsp?error=" + error);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                if (!Check.checkName(request.getParameter("nome"))) {
                    response.getWriter().write("1"); //nome non corretto
                }
                if (!Check.checkSurname(request.getParameter("cognome"))) {
                    response.getWriter().write("2"); //cognome non corretto
                }
                if (!Check.checkEmail(request.getParameter("email"))) {
                    response.getWriter().write("3"); //email non corretto
                }

                String descrizione = "Siamo spiacenti si è verificato un errore con la registrazione.Riprova";
                response.sendRedirect("./static/Error.jsp?descrizione=" + descrizione);
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
     * Questo metodo permette di registrare un utente utilizzando il manager
     *
     * @param user utente da registrare
     * @return boolean (true = utente registrato correttamente , false = altrimenti)
     * @throws SQLException errore nella query
     */

    public static boolean registrazioneFromManager(Utente user) throws SQLException {
        AutenticazioneManager autenticazioneManager = new AutenticazioneManagerImpl();
        return autenticazioneManager.registrazione(user);
    }

    /**
     * Questo metodo permette di effettuare il login di un utente utilizzando il manager
     *
     * @param email , email dell'utente
     * @param pwd   , password dell'utente
     * @return utente loggato
     * @throws SQLException errore nella query
     */

    public static Utente loginFromManager(String email, String pwd) throws SQLException {
        AutenticazioneManager autenticazioneManager = new AutenticazioneManagerImpl();
        return autenticazioneManager.login(email, pwd);
    }
}
