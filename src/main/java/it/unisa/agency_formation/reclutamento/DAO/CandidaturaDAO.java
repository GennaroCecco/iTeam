package it.unisa.agency_formation.reclutamento.DAO;

import it.unisa.agency_formation.reclutamento.domain.Candidatura;
import it.unisa.agency_formation.reclutamento.domain.StatiCandidatura;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public interface CandidaturaDAO {
    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di salvare una candidatura senza gli attestati e le certificazioni
     *
     * @param candidatura , {@literal candidatura != null}  indica la candidatura che vogliamo salvare
     * @return boolean (false = i parametri non vengono rispettati o la funzionalità non va a buon fine,
     * true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query  errore nella query
     */
    boolean salvaCandidaturaSenzaDocumenti(Candidatura candidatura) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di aggiungere attestati e certificazioni
     *
     * @param document , {@literal document != null} indica la directory contentente i documenti dell'utente interessato
     * @param idUtente , {@literal idUtente > 0} , identifica l'utente
     * @return boolean (false = i parametri non vengono rispettati o la funzionalità non va a buon fine,
     * true = la funzionalitò va a buon fine)
     * @throws SQLException errore nella query errore nella query
     */
    boolean aggiungiDocumentiAggiuntivi(String document, int idUtente) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di recuperare una candidatura attraverso l’id del candidato
     *
     * @param idCandidato ,  {@literal idCandidato > 0} identifica il candidato
     * @return Candidatura interessata
     * @throws SQLException errore nella query errore nella query
     */
    Candidatura doRetrieveCandidaturaById(int idCandidato) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di recuperare tutte le candidature
     *
     * @return {@literal ArrayList<@link Candidatura>} , una lista di candidature
     * @throws SQLException errore nella query errore nella query
     */
    ArrayList<Candidatura> recuperaCandidature() throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di modificare lo stato della candidatura
     *
     * @param stato         , {@literal stato != null} indica in quale stato la candidatura dev'essere settata
     * @param idCandidatura , {@literal idCandidatura > 0} identifica la candidatura
     * @return boolean (false = i parametri non vengono rispettati o la funzionalità non va a buon fine,
     * true = la funzionalitò va a buon fine)
     * @throws SQLException errore nella query errore nella query
     */
    boolean modificaStatoCandidatura(int idCandidatura, StatiCandidatura stato) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di rimuovere una candidatura
     *
     * @param idCandidato , {@literal idCandidato > 0} identifica il candidato
     * @return boolean (false = i parametri non vengono rispettati o la funzionalità non va a buon fine,
     * true = la funzionalitò va a buon fine)
     * @throws SQLException errore nella query errore nella query
     */
    boolean rimuoviCandidatura(int idCandidato) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di rifiutare una candidatura
     *
     * @param idCandidatura , {@literal idCandidatura > 0} identifica la candidatura
     * @param idHR          , {@literal idHR > 0} identifica l'HR
     * @return boolean (false = i parametri non vengono rispettati o la funzionalità non va a buon fine,
     * true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query
     */
    boolean rifiutaCandidatura(int idCandidatura, int idHR) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette all'HR di accettare una candidatura
     *
     * @param idCandidatura , {@literal idCandidatura > 0}  identifica la candidatura
     * @param idHR          , {@literal idHR > 0 }  identifica l' HR
     * @param data          , {@literal data >= actual date}  specifica la data del colloquio
     * @return boolean (false = i parametri non vengono rispettati o la funzionalità non va a buon fine,
     * true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query
     */
    boolean accettaCandidatura(int idCandidatura, int idHR, Timestamp data) throws SQLException;


}
