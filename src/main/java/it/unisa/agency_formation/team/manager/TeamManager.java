package it.unisa.agency_formation.team.manager;

import it.unisa.agency_formation.autenticazione.domain.Dipendente;
import it.unisa.agency_formation.team.domain.Team;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TeamManager {
    /**
     * Questa funzionalità è l'interfaccia del metodo che permette di creare un team
     *
     * @param team     , specifica il team da salvare
     * @param idUtente , identifica il TM che crea il team
     * @return boolean (false = la funzionalità non va a buon fine,
     * true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query
     */

    boolean creaTeam(Team team, int idUtente) throws SQLException;

    /**
     * Questa funzionalità è l'interfaccia del metodo che permette di rimuovere un dipendente da un team
     *
     * @param idDip , identifica il dipendente da rimuovere
     * @return boolean (false = la funzionalità non va a buon fine,
     * true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query
     */

    boolean rimuoviDipendente(int idDip) throws SQLException;

    /**
     * Questa funzionalità è l'interfaccia del metodo che permette di visualizzare tutti i team creati da uno specifico TM
     *
     * @param idUtente , identifica l'utente
     * @return {@literal ArrayList<@link Team>} se i team esistono , null altrimenti
     * @throws SQLException errore nella query errore nella query
     */

    ArrayList<Team> visualizzaTeams(int idUtente) throws SQLException;

    /**
     * Questa funzionalità è l'interfaccia del metodo che permette di visualizzare tutti i team
     *
     * @return {@literal ArrayList<@link Team>} se ci sono team , null altrimenti
     * @throws SQLException errore nella query errore nella query
     */

    ArrayList<Team> visualizzaTuttiTeams() throws SQLException;

    /**
     * Questa funzionalità è l'interfaccia del metodo che permette di recuperare l'ultimo team creato
     *
     * @return l'id dell'ultimo team creato, 0 altrimenti
     * @throws SQLException errore nella query errore nella query
     */

    int viewLastIdTeam() throws SQLException;

    /**
     * Questa funzionalità è l'interfaccia del metodo che permette di recuperare tutti i dipendenti di uno specifico team
     *
     * @param idTeam , identifica il team
     * @return {@literal ArrayList<@link Integer>} se ci sono dipendenti nel team , null altrimenti
     * @throws SQLException errore nella query errore nella query
     */

    ArrayList<Integer> recuperaIdDipendentiDelTeam(int idTeam) throws SQLException;

    /**
     * Questa funzionalità è l'interfaccia del metodo che permette di modificare lo stato dei dipendenti di un team appena sciolto
     *
     * @param idDip , identifica il dipendente
     * @return boolean (false = la funzionalità non va a buon fine,
     * true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query
     */

    boolean updateDipsDisso(int idDip) throws SQLException;

    /**
     * Questa funzionalità è l'interfaccia del metodo che permette di sciogliere il team
     *
     * @param idTeam , identifica il team da sciogliere
     * @return boolean (false = la funzionalità non va a buon fine,
     * true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query
     */

    boolean sciogliTeam(int idTeam) throws SQLException;

    /**
     * Questa funzionalità è l'interfaccia del metodo che permette di recuperare dipendenti di un team
     *
     * @return {@literal ArrayList<@link Dipendente>} se ci sono dipendenti nel team , null altrimenti
     * @throws SQLException errore nella query errore nella query
     */

    ArrayList<Dipendente> recuperaDipendentiDelTeam() throws SQLException;

    /**
     * Questa funzionalità è l'interfaccia del metodo che permette di ritornare un team attraverso il suo id
     *
     * @param idTeam , identifica il team
     * @return Team se il team esiste , null altrimenti
     * @throws SQLException errore nella query errore nella query
     */

    Team getTeamById(int idTeam) throws SQLException;

    /**
     * Questa funzionalità è l'interfaccia del metodo che permette di modificare le competenze
     *
     * @param competence , sperifica con quale competenza dev'essere aggiornato
     * @param idTeam     , identifica il team
     * @return boolean (false = la funzionalità non va a buon fine,
     * true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query
     */

    boolean specificaLeCompetenze(String competence, int idTeam) throws SQLException;
}
