package it.unisa.agency_formation.team.DAO;

import it.unisa.agency_formation.autenticazione.domain.Dipendente;
import it.unisa.agency_formation.team.domain.Team;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TeamDAO {
    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di salvare un team
     *
     * @param team     , {@literal team != null} specifica il team da salvare
     * @param idUtente , {@literal idUtente > 0} identifica l'utente
     * @return boolean (false = i parametri non vengono rispettati o la funzionalità non va a buon fine,
     * true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query
     */
    boolean salvaTeam(Team team, int idUtente) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di eliminare un dipendente
     *
     * @param idTeam , {@literal idTeam > 0} identifica il team
     * @return boolean (false = i parametri non vengono rispettati o la funzionalità non va a buon fine,
     * true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query
     */
    boolean rimuoviTeam(int idTeam) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di recuperare un team attraverso il suo id
     *
     * @param idTeam , {@literal idTeam > 0} identifica il team
     * @return Team , il team interessato
     * @throws SQLException errore nella query errore nella query
     */
    Team recuperaTeamById(int idTeam) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di rimuovere un dipendente da un team
     *
     * @param idDipendente , {@literal idDipendente > 0}  identifica il dipendente da rimuovere
     * @return boolean (false = i parametri non vengono rispettati o la funzionalità non va a buon fine,
     * true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query
     */
    boolean rimuoviDipendente(int idDipendente) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di recuperare tutti i team presenti nella piattaforma
     *
     * @return {@literal ArrayList<@link Team>} , una lista di team
     * @throws SQLException errore nella query errore nella query
     *                      nella post-condizione l'arraylist di team non dev'essere vuota
     */
    ArrayList<Team> recuperaTuttiTeam() throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di recuperare la lista dei team di un TM
     *
     * @param idUtente , {@literal idUtente > 0}  identifica l'utente
     * @return {@literal ArrayList<@link Team>} una lista di team
     * @throws SQLException errore nella query errore nella query
     *                      nella post-condizione l'arraylist dei team non dev'essere vuoto
     */
    ArrayList<Team> recuperaTeamDiUnTM(int idUtente) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di modificare le competenze di un team
     *
     * @param competence , {@literal competence != null }  specifica la competenza con il quale aggiornare
     * @param idTeam     , {@literal idTeam > 0}  identifica il team
     * @return boolean (false = i parametri non vengono rispettati o la funzionalità non va a buon fine,
     * true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query
     */
    boolean specificaCompetenze(String competence, int idTeam) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di recuperare l'id dell'ultimo team creato
     *
     * @return int , l'id dell'ultimo team creato
     * @throws SQLException errore nella query errore nella query
     */
    int recuperaIdUltimoTeamCreato() throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di recuperare gli id dei dipendenti che appartengono ad un determinato team
     *
     * @param idTeam, {@literal idTeam > 0}  identifica il team
     * @return {@literal ArrayList<@link Integer>} una lista di idDipendenti membri di un team
     * @throws SQLException errore nella query errore nella query
     */
    ArrayList<Integer> recuperaIdTeamMemberFromTeam(int idTeam) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di modificare lo stato di un dipendente a disponibile
     *
     * @param idDip , {@literal idDip > 0}  identifica il dipendente
     * @return boolean (false = i parametri non vengono rispettati o la funzionalità non va a buon fine,
     * true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query
     */
    boolean updateDipStateDissolution(int idDip) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di recuperare tutti i dipendenti
     *
     * @return {@literal ArrayList<@link Dipendente>} , una lista di dipendenti
     * @throws SQLException errore nella query errore nella query
     */
    ArrayList<Dipendente> recuperaDipendentiDelTeam() throws SQLException;
}
