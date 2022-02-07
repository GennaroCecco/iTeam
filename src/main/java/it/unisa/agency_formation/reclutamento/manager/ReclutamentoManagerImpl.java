package it.unisa.agency_formation.reclutamento.manager;

import it.unisa.agency_formation.reclutamento.DAO.CandidaturaDAO;
import it.unisa.agency_formation.reclutamento.DAO.CandidaturaDAOImpl;
import it.unisa.agency_formation.reclutamento.domain.Candidatura;
import it.unisa.agency_formation.reclutamento.domain.StatiCandidatura;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ReclutamentoManagerImpl implements ReclutamentoManager {
    public static CandidaturaDAO daoCandidatura = new CandidaturaDAOImpl();
    /**
     * Questo metodo permette di caricare la cadidatura
     *
     * @param candidatura, specifica la candidaatura da creare
     * @return boolean (false = i parametri non vengono rispettati o la candidaturà è gia esistente,
     *                  true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query*/
    @Override
    public boolean caricaCandidatura(Candidatura candidatura) throws SQLException {
        if (candidatura == null) {
            return false;
        } else {
            if (candidatura.getCurriculum() != null && candidatura.getDocumentiAggiuntivi() == null) {
                if (!alreadyLoaded(candidatura.getIdCandidato())) {
                    return daoCandidatura.salvaCandidaturaSenzaDocumenti(candidatura);
                } else {
                    return false;
                }
            } else {
                return daoCandidatura.aggiungiDocumentiAggiuntivi(candidatura.getDocumentiAggiuntivi(), candidatura.getIdCandidato());
            }
        }
    }

    /**
     * Questo metodo permette di restituire tutte le candidature caricate
     * @return {@literal ArrayList<@link Candidatura>} , una lista con tutte le candidature
     * @throws SQLException errore nella query errore nella query*/

    @Override
    public ArrayList<Candidatura> getTutteCandidature() throws SQLException {
        return daoCandidatura.recuperaCandidature();
    }

    /**
     * Questo metodo permette di ricandidarsi
     *
     * @param idCandidato , identifica il candidato
     * @return boolean (false = la funzionalità non va a buon fine,
     *                  true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query*/

    @Override
    public boolean ricandidatura(int idCandidato) throws SQLException {
        return daoCandidatura.rimuoviCandidatura(idCandidato);
    }

    /**
     * Questo metodo permette di modificare lo stato della candidatura
     *
     * @param idCandidatura , identifica la candidatura
     * @param stato , specifica con quale stato dev'essere modificata la candidatura
     * @return boolean (false = la funzionalità non va a buon fine,
     *                  true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query*/

    @Override
    public boolean modificaStatoCandidatura(int idCandidatura, StatiCandidatura stato) throws SQLException {
        return daoCandidatura.modificaStatoCandidatura(idCandidatura, stato);
    }

    /**
     * Questo metodo permette di ritornare la candidatura in base all'id del candidato
     * @param idCandidato , identifica il candidato
     * @return Candidatura , la candidatura interessata
     * @throws SQLException errore nella query errore nella query*/

    @Override
    public Candidatura getCandidaturaById(int idCandidato) throws SQLException {
        return daoCandidatura.doRetrieveCandidaturaById(idCandidato);
    }

    /**
     * Questo metodo permette di accettare una candidatura e fissare un colloquio
     * @param idCandidatura , identifica la candidatura
     * @param idHR , identifica l'HR
     * @param data ,specifica la data del colloquio
     * @return boolean (false = la funzionalità non va a buon fine,
     *                  true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query*/

    @Override
    public boolean accettaCandidatura(int idCandidatura, int idHR, Timestamp data) throws SQLException {
        return daoCandidatura.accettaCandidatura(idCandidatura, idHR, data);
    }

    /**
     * Questo metodo permette di rifiutare la candidatura
     * @param idCandidatura , identifica la candidatura
     * @param idHR , identifica l' HR
     * @return boolean (false = la funzionalità non va a buon fine,
     *                  true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query*/

    @Override
    public boolean rifiutaCandidatura(int idCandidatura, int idHR) throws SQLException {
        return daoCandidatura.rifiutaCandidatura(idCandidatura, idHR);
    }

    /**
     * Questo metodo permette di controllare se la candidatura sia già esistente
     * @param idUtente, identifica il candidato
     * @return boolean (false = la candidatura non esiste,
     *                  true = la candidatura esiste)
     * @throws SQLException errore nella query errore nella query*/

    private boolean alreadyLoaded(int idUtente) throws SQLException {
        if (daoCandidatura.doRetrieveCandidaturaById(idUtente) == null) {
            return false;
        }
        return true;
    }
}
