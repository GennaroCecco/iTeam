package it.unisa.agency_formation.autenticazione.DAO;

import it.unisa.agency_formation.autenticazione.domain.Utente;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UtenteDAO {
    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di salvare un utente
     *
     * @param user , {@literal user != null} user è l'utente da registrare
     * @return boolean true se l'utente è stato salvato con successo, false altrimenti
     * @throws SQLException errore nella query errore nella query
     */
    boolean salvaUtente(Utente user) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di recuperare un utente
     *
     * @param email , {@literal email != null } è l'email dell'utente
     * @param pwd   , {@literal pwd != null} è la password dell'utente
     * @return Utente che si è registrato in precedenza, null se non è presente nel db
     * @throws SQLException errore nella query errore nella query
     */
    Utente login(String email, String pwd) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di recuperare i candidati che hanno presentato la propria candidatura
     *
     * @return {@literal ArrayList<@link Utente>} se ci sono candidati altrimenti null
     * @throws SQLException errore nella query errore nella query
     */
    ArrayList<Utente> doRetrieveCandidatoConCandidatura() throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di recuperare i candidati che svolgeranno il colloquio
     *
     * @return {@literal ArrayList<@link Utente>} ritorna un array se ci sono candidati per il colloquio, altrimenti null
     * @throws SQLException errore nella query errore nella query
     */
    ArrayList<Utente> recuperoCandidatiColloquio() throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di controllare se una mail è già esistente
     *
     * @param email , email da controllare
     * @return boolean (true = non esiste altra mail identica, false = altrimenti)
     * @throws SQLException errore nella query errore nella query
     */
    boolean checkEmail(String email) throws SQLException;

}

