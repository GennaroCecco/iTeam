package it.unisa.agency_formation.autenticazione.control;

import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManager;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManagerImpl;
import it.unisa.agency_formation.reclutamento.domain.Candidatura;
import it.unisa.agency_formation.reclutamento.manager.ReclutamentoManager;
import it.unisa.agency_formation.reclutamento.manager.ReclutamentoManagerImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/LoginControl")
public class LoginControl extends HttpServlet {

    /**
     * Questo metodo controlla i valori presi dalle view per effettuare il login
     *
     * @param request  , request
     * @param response , response
     * @throws ServletException errore Servlet
     * @throws IOException      errore input output
     */

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        HttpSession session = request.getSession();
        Utente user = (Utente) session.getAttribute("user");
        if (user != null) {
            switch (user.getRole()) {
                case CANDIDATO:
                    Candidatura candidatura = null;
                    try {
                        candidatura = getCandidaturafromManager(user.getId());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (candidatura != null) {
                        request.setAttribute("candidatura", candidatura);
                    }
                    response.getWriter().write("6"); //sono un candidato
                    dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/HomeCandidato.jsp");
                    dispatcher.forward(request, response);
                    break;
                case DIPENDENTE:
                    response.getWriter().write("7"); //sono un dipendente
                    dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/HomeDipendente.jsp");
                    dispatcher.forward(request, response);
                    break;
                case TM:
                    response.getWriter().write("8"); //sono un TM
                    dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/HomeTM.jsp");
                    dispatcher.forward(request, response);
                    break;
                case HR:
                    response.getWriter().write("9"); //sono un HR
                    dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/HomeHR.jsp");
                    dispatcher.forward(request, response);
                    break;
            }
        } else {
            String email = request.getParameter("email");
            String pwd = request.getParameter("password");

            if (email != null && pwd != null) {
                if (email.trim().length() == 0) {
                    response.getWriter().write("1"); //email vuota
                }
                if (pwd.trim().length() == 0) {
                    response.getWriter().write("2"); //password vuota
                }
                try {
                    user = loginFromManager(email, pwd);
                    if (user != null) {
                        session = request.getSession(true);
                        session.setAttribute("user", user);
                        response.getWriter().write("3"); //utente loggato
                        switch (user.getRole()) {
                            case CANDIDATO:
                                Candidatura candidatura = getCandidaturafromManager(user.getId());
                                if (candidatura != null) {
                                    request.setAttribute("candidatura", candidatura);
                                }
                                response.getWriter().write("-10"); //utente CAND
                                dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/HomeCandidato.jsp");
                                dispatcher.forward(request, response);
                                break;
                            case DIPENDENTE:
                                response.getWriter().write("-11"); //utente DIP
                                dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/HomeDipendente.jsp");
                                dispatcher.forward(request, response);
                                break;
                            case TM:
                                response.getWriter().write("-12"); //utente TM
                                dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/HomeTM.jsp");
                                dispatcher.forward(request, response);
                                break;
                            case HR:
                                response.getWriter().write("-13"); //utente HR
                                dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/HomeHR.jsp");
                                dispatcher.forward(request, response);
                                break;
                        }
                    } else {
                        response.getWriter().write("4"); //utente non valido
                        String error = "Email o Password errata";
                        response.sendRedirect("./static/Login.jsp?error=" + error);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                response.getWriter().write("5"); //email e password null
                response.sendRedirect("./static/Login.jsp");
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
     * Questo metodo permette di tornare la candidatura di uno specifico candidato utilizzando il manager
     *
     * @param idCandidato , id del candidato
     * @return {@link Candidatura} la candidatura interessata
     * @throws SQLException errore nella query
     */
    public static Candidatura getCandidaturafromManager(int idCandidato) throws SQLException {
        ReclutamentoManager reclutamentoManager = new ReclutamentoManagerImpl();
        return reclutamentoManager.getCandidaturaById(idCandidato);
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
