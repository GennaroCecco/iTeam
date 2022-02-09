package it.unisa.agency_formation.formazione.control;


import it.unisa.agency_formation.autenticazione.domain.Dipendente;
import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManager;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManagerImpl;
import it.unisa.agency_formation.formazione.domain.Skill;
import it.unisa.agency_formation.formazione.manager.FormazioneManager;
import it.unisa.agency_formation.formazione.manager.FormazioneManagerImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/SkillControl")
public class SkillControl extends HttpServlet {

    /**
     * Questo metodo controlla le operazioni per effettuare l'aggiunta di una skill
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
            Skill skill = new Skill();
            String skillName = request.getParameter("skillName");
            String skillDescr = request.getParameter("skillDescription");
            int skillLivello = Integer.parseInt(request.getParameter("quantity"));
            if (skillName != null && skillDescr != null) {
                if (skillName.trim().length() == 0) {
                    response.getWriter().write("1"); // Skillnome vuoto
                } else {
                    skill.setNomeSkill(skillName);
                }
                if (skillDescr.trim().length() == 0) {
                    response.getWriter().write("2"); //Skilldesc vuoto
                } else {
                    skill.setDescrizioneSkill(skillDescr);
                }
                try {
                    Dipendente dip = getDipendenteByIdFromManager(user.getId());
                    if (dip != null) {
                        if (!addSkillFromManager(skill)) {
                            response.getWriter().write("2"); // aggiunta in skill non avvenuta con successo.
                            String descrizione = "Si è verificato un problema con l'aggiunta della skill. Riprova";
                            response.sendRedirect("./static/Error.jsp?descrizione=" + descrizione);
                            return;
                        }
                        int idSkill = getLastIdSkillCreatedFromManager();
                        if (!addSkillDipFromManager(idSkill, dip.getIdDipendente(), skillLivello)) {
                            response.getWriter().write("3"); // aggiunta in skillDip non avvenuta con successo.
                            String descrizione = "Si è verificato un problema con l'aggiunta della skill. Riprova";
                            response.sendRedirect("./static/Error.jsp?descrizione=" + descrizione);
                            return;
                        }
                        response.getWriter().write("4"); // aggiunta avvenuta con successo.
                        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/ProfiloControl");
                        dispatcher.forward(request, response);
                    } else {
                        response.getWriter().write("5"); // aggiunta fallita.
                        response.sendRedirect("./static/Profilo.jsp");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                response.getWriter().write("6"); //skillNome e skillDescr null
                response.sendRedirect("./static/Profilo.jsp");
            }
        } else {
            response.getWriter().write("7"); //user null oppure non dipendente
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
     * Questo metodo permette di aggiungere una nuova skill utilizzando il manager
     *
     * @param skill skill interessata
     * @return boolean (true = la skill è stata aggiunta correttamente , false = altrimenti)
     * @throws SQLException errore nella query
     */

    public static boolean addSkillFromManager(Skill skill) throws SQLException {
        FormazioneManager formazioneManager = new FormazioneManagerImpl();
        return formazioneManager.aggiungiSkill(skill);
    }

    /**
     * Questo metodo permette di ottenere l'ultima skill creata utilizzando il manager
     *
     * @return l'id dell'ultima skill creata
     * @throws SQLException errore nella query
     */

    public static int getLastIdSkillCreatedFromManager() throws SQLException {
        FormazioneManager formazioneManager = new FormazioneManagerImpl();
        return formazioneManager.getUltimaSkill();
    }

    /**
     * Questo metodo permette di aggiungere una skill posseduta dal dipendente utilizzando il manager
     *
     * @param idSkill      id della skill
     * @param idDipendente id del dipendente
     * @param skillLivello livello di competenza e ocnoscenza della skill
     * @return boolean (true = operazione andata a buon fine, false = altrimenti)
     * @throws SQLException errore nella query
     */

    public static boolean addSkillDipFromManager(int idSkill, int idDipendente, int skillLivello) throws SQLException {
        FormazioneManager formazioneManager = new FormazioneManagerImpl();
        return formazioneManager.addSkillDipendente(idSkill, idDipendente, skillLivello);
    }

    /**
     * Questo metodo permette di ottenere il dipendente attraverso il suo id utilizzando il manager
     *
     * @param idDip id del dipendente
     * @return il dipendente interessato
     * @throws SQLException errore nella query
     */

    public static Dipendente getDipendenteByIdFromManager(int idDip) throws SQLException {
        AutenticazioneManager autenticazioneManager = new AutenticazioneManagerImpl();
        return autenticazioneManager.getDipendente(idDip);
    }
}
