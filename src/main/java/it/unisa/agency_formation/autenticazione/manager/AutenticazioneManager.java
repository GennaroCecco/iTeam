package it.unisa.agency_formation.autenticazione.manager;

import it.unisa.agency_formation.autenticazione.domain.Dipendente;
import it.unisa.agency_formation.autenticazione.domain.Utente;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AutenticazioneManager {

    /**
     * Questa funzionalità è l'interfaccia del metodo che permette di far registrare un utente,l'utente non deve essere già registrato
     *
     * @param user l'utente che verrà registrato
     * @return boolean true se l'utente viene registrato, false altrimenti
     * @throws SQLException errore nella query errore nella query
     */
    boolean registrazione(Utente user) throws SQLException;

    /**
     * Questa funzionalità è l'interfaccia del metodo che permette di far eseguire il login ad un utente già registrato in precedenza
     *
     * @param email    dell'utente
     * @param password dell'utente
     * @return utente se il login va a buon fine, null altrimenti
     * @throws SQLException errore nella query errore nella query
     */
    Utente login(String email, String password) throws SQLException;

    /**
     * Questa funzionalità è l'interfaccia del metodo che permette di recuperare un dipendente
     *
     * @param idDip , rappresenta l'id del dipendente
     * @return Dipendente se il dipendente esiste, null altrimenti
     * @throws SQLException errore nella query errore nella query
     */
    Dipendente getDipendente(int idDip) throws SQLException;

    /**
     * Questa funzionalità è l'interfaccia del metodo che permette di recuperare i candidati con la candidatura
     *
     * @return {@literal ArrayList<@link Utente>} se ci sono candidati con la candidatura, null altrimenti
     * @throws SQLException errore nella query errore nella query
     */
    ArrayList<Utente> getCandidatiConCandidatura() throws SQLException;

    /**
     * Questa funzionalità è l'interfaccia del metodo che permette di recuperare i dipendenti
     *
     * @return {@literal ArrayList<@link Dipendente>} se ci sono dipendenti, null altrimenti
     * @throws SQLException errore nella query errore nella query
     */

    ArrayList<Dipendente> getTuttiDipendenti() throws SQLException;

    /**
     * Questa funzionalità è l'interfaccia del metodo che permette di assumere un candidato
     * @param dipendente , il dipendente da assumere
     * @return boolean (true = l'assunzione va a buon fine, false = altrimenti
     * @throws SQLException errore nella query errore nella query
     */

    boolean assumiCandidato(Dipendente dipendente) throws SQLException;

    /**
     * Questa funzionalità è l'interfaccia del metodo che permette di recuperare i candidati che dovranno svolgere il colloquio
     *
     * @return {@literal ArrayList<@link Utente>} se ci sono candidati per il colloquio, null altrimenti
     * @throws SQLException errore nella query errore nella query
     */

    ArrayList<Utente> getCandidatiColloquio() throws SQLException;

    /**
     * Questa funzionalità è l'interfaccia del metodo che permette di settare l'idTeam di un dipendente
     *
     * @param idDip  rappresenta l'id del dipendente
     * @param idTeam rappresenta l'id del team
     * @return boolean (true = il set è andato a buon fine, false = altrimenti)
     * @throws SQLException errore nella query errore nella query
     */
    boolean setTeamDipendente(int idDip, int idTeam) throws SQLException;
}
