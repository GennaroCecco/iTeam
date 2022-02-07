package it.unisa.agency_formation.autenticazione.DAO;

import it.unisa.agency_formation.autenticazione.domain.Dipendente;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DipendenteDAO {
    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di salvare un dipendente
     *
     * @param dipendente , {@literal dipendente != null} è il dipendente da registrare
     * @return boolean true se il dipendente èì stato salvato, false altrimenti
     * @throws SQLException errore nella query errore nella query
     */

    boolean salvaDipendente(Dipendente dipendente) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di settare a 2 il ruolo dell'utente quando è assunto,
     *
     * @param id , {@literal id > 0} è l'id dell'utente candidato che diventerà dipendente
     * @return boolean true se il ruolo è stato modificato, false altrimenti
     * @throws SQLException errore nella query errore nella query
     */
    boolean modificaRuoloUtente(int id) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di recuperare un dipendente attraverso il suo id,
     *
     * @param id , {@literal id > 0} l'Id del dipendente che vogliamo recuperare
     * @return Dipendente se è presente, null altrimenti
     * @throws SQLException errore nella query errore nella query
     */
    Dipendente recuperoDipendenteById(int id) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di recuperare tutti i dipendenti
     *
     * @return {@literal ArrayList<@link Dipendente>} lista di dipendenti se sono presenti, null altrimenti
     * @throws SQLException errore nella query errore nella query
     */
    ArrayList<Dipendente> recuperaDipendenti() throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di aggiornare idTeam quando un dipendente viene aggiunto,
     *
     * @param idDip  , {@literal idDip > 0} rappresenta l'id del dipendente
     * @param idTeam , {@literal idTeam > 0} rappresenta l'id del team
     * @return boolean true se il set è andato a buon fine, false altrimenti
     * @throws SQLException errore nella query errore nella query
     */
    boolean setIdTeamDipendente(int idDip, int idTeam) throws SQLException;
}
