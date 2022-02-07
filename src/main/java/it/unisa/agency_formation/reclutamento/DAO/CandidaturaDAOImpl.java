package it.unisa.agency_formation.reclutamento.DAO;

import it.unisa.agency_formation.reclutamento.domain.Candidatura;
import it.unisa.agency_formation.reclutamento.domain.StatiCandidatura;
import it.unisa.agency_formation.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class CandidaturaDAOImpl implements CandidaturaDAO {
    private  static final String TABLE_CANDIDATURA = "Candidature";

    /**
     * Questa funzionalità permette di salvare una candidatura senza gli attestati e le certificazioni
     *
     * @param candidatura , {@literal candidatura != null}  indica la candidatura che vogliamo salvare
     * @return boolean (false = i parametri non vengono rispettati o la funzionalità non va a buon fine,
     * true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query  errore nella query
     */
    public boolean salvaCandidaturaSenzaDocumenti(Candidatura candidatura) throws SQLException {
        if (candidatura == null || candidatura.getCurriculum().length() > 512) {
            return false;
        }
        Connection connection = DatabaseManager.getInstance().getConnection();
        PreparedStatement save = null;
        String query = "insert into "
                + TABLE_CANDIDATURA
                + "(Curriculum, Stato, DataCandidatura,IdCandidato) "
                + "VALUES(?,?,?,?)";
        try {
            save = connection.prepareStatement(query);
            save.setString(1, candidatura.getCurriculum());
            save.setString(2, "NonRevisionato");
            save.setDate(3, (Date) candidatura.getDataCandidatura());
            save.setInt(4, candidatura.getIdCandidato());
            int result = save.executeUpdate();
            return result != 0;
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

    /**
     * Questa funzionalità permette di aggiungere attestati e certificazioni
     *
     * @param document , {@literal document != null} indica la directory contentente i documenti dell'utente interessato
     * @param idUtente , {@literal idUtente > 0} , identifica l'utente
     * @return boolean (false = i parametri non vengono rispettati o la funzionalità non va a buon fine,
     * true = la funzionalitò va a buon fine)
     * @throws SQLException errore nella query errore nella query
     */

    public boolean aggiungiDocumentiAggiuntivi(String document, int idUtente) throws SQLException {
        if (document == null || idUtente < 1 || document.length() > 512) {
            return false;
        }
        Connection connection = DatabaseManager.getInstance().getConnection();
        PreparedStatement update = null;
        String query = "update " + TABLE_CANDIDATURA + " set DocumentiAggiuntivi= ? where IdCandidato=?";
        try {
            update = connection.prepareStatement(query);
            update.setString(1, document);
            update.setInt(2, idUtente);
            int result = update.executeUpdate();
            return result != -1;
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

    /**
     * Questa funzionalità permette di recuperare una candidatura attraverso l’id del candidato
     *
     * @param idCandidato ,  {@literal idCandidato > 0} identifica il candidato
     * @return Candidatura interessata
     * @throws SQLException errore nella query errore nella query
     */
    public Candidatura doRetrieveCandidaturaById(int idCandidato) throws SQLException {
        if (idCandidato < 1) {
            return null;
        }
        ResultSet result;
        Connection connection = DatabaseManager.getInstance().getConnection();
        PreparedStatement retrieve = null;
        String query = "Select * from " + TABLE_CANDIDATURA + " where IdCandidato=?";
        Candidatura cand = null;
        try {
            retrieve = connection.prepareStatement(query);
            retrieve.setInt(1, idCandidato);
            result = retrieve.executeQuery();
            if (result.next()) {
                cand = new Candidatura();
                cand.setIdCandidatura(result.getInt("IdCandidatura"));
                cand.setCurriculum(result.getString("Curriculum"));
                cand.setDocumentiAggiuntivi(result.getString("DocumentiAggiuntivi"));
                switch (result.getString("Stato")) {
                    case "NonRevisionato":
                        cand.setStato(StatiCandidatura.NonRevisionato);
                        break;
                    case "Accettata":
                        cand.setStato(StatiCandidatura.Accettata);
                        break;
                    case "Rifiutata":
                        cand.setStato(StatiCandidatura.Rifiutata);
                        break;
                    case "Assunzione":
                        cand.setStato(StatiCandidatura.Assunzione);
                        break;
                }
                cand.setDataCandidatura(result.getDate("DataCandidatura"));
                cand.setDataOraColloquio(result.getTimestamp("DataOraColloquio"));
                cand.setIdCandidato(result.getInt("IdCandidato"));
                cand.setIdHR(result.getInt("IdHR"));
            }
            if (cand != null) {
                return cand;
            } else {
                return null;
            }
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

    /**
     * Questa funzionalità permette di recuperare tutte le candidature
     * @return  {@literal ArrayList<@link Candidatura>} , una lista di candidature
     * @throws SQLException errore nella query errore nella query
     */
    public ArrayList<Candidatura> recuperaCandidature() throws SQLException {
        Connection connection = DatabaseManager.getInstance().getConnection();
        ResultSet result;
        PreparedStatement retrieve = null;
        String query = "Select * from " + TABLE_CANDIDATURA + " where Stato=?";
        ArrayList<Candidatura> candidature = new ArrayList<>();
        try {
            retrieve = connection.prepareStatement(query);
            retrieve.setString(1, "NonRevisionato");
            result = retrieve.executeQuery();
            while (result.next()) {
                Candidatura cand = new Candidatura();
                cand.setIdCandidatura(result.getInt("IdCandidatura"));
                cand.setCurriculum(result.getString("Curriculum"));
                cand.setDocumentiAggiuntivi(result.getString("DocumentiAggiuntivi"));
                cand.setStato(StatiCandidatura.NonRevisionato);
                cand.setDataCandidatura(result.getDate("DataCandidatura"));
                cand.setDataOraColloquio(result.getTimestamp("DataOraColloquio"));
                cand.setIdCandidato(result.getInt("IdCandidato"));
                cand.setIdHR(result.getInt("IdHR"));
                candidature.add(cand);
            }
            return candidature.size() > 0 ? candidature : null;
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

    /**
     * Questa funzionalità permette di modificare lo stato della candidatura
     *
     * @param stato , {@literal stato != null} indica in quale stato la candidatura dev'essere settata
     * @param idCandidatura , {@literal idCandidatura > 0} identifica la candidatura
     * @return boolean (false = i parametri non vengono rispettati o la funzionalità non va a buon fine,
     *                  true = la funzionalitò va a buon fine)
     * @throws SQLException errore nella query errore nella query
     */
    public boolean modificaStatoCandidatura(int idCandidatura, StatiCandidatura stato) throws SQLException {
        if (idCandidatura < 1 || stato == null) {
            return false;
        }
        Connection connection = DatabaseManager.getInstance().getConnection();
        PreparedStatement retrieve = null;
        String query = "update " + TABLE_CANDIDATURA + " set Stato= ? where IdCandidatura=?";
        try {
            retrieve = connection.prepareStatement(query);
            switch (stato) {
                case Accettata:
                    retrieve.setString(1, "Accettata");
                    break;
                case Rifiutata:
                    retrieve.setString(1, "Rifiutata");
                    break;
                case NonRevisionato:
                    retrieve.setString(1, "NonRevisionato");
                    break;
                case Assunzione:
                    retrieve.setString(1, "Assunzione");
                    break;
            }
            retrieve.setInt(2, idCandidatura);
            int result = retrieve.executeUpdate();
            return result != -1;
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

    /**
     * Questa funzionalità permette di rimuovere una candidatura
     *
     * @param idCandidato , {@literal idCandidato > 0} identifica il candidato
     * @return boolean (false = i parametri non vengono rispettati o la funzionalità non va a buon fine,
     *                  true = la funzionalitò va a buon fine)
     * @throws SQLException errore nella query errore nella query
     */
    public boolean rimuoviCandidatura(int idCandidato) throws SQLException {
        if (idCandidato < 1) {
            return false;
        }
        Connection connection = DatabaseManager.getInstance().getConnection();
        String query = "DELETE FROM " + TABLE_CANDIDATURA + " WHERE idCandidato=?";
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, idCandidato);
            int result = stmt.executeUpdate();
            return result != 0;
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

    /**
     * Questa funzionalità permette di rifiutare una candidatura
     *
     * @param idCandidatura , {@literal idCandidatura > 0} identifica la candidatura
     * @param idHR , {@literal idHR > 0} identifica l'HR
     * @return boolean (false = i parametri non vengono rispettati o la funzionalità non va a buon fine,
     *                  true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query
     */
    public boolean rifiutaCandidatura(int idCandidatura, int idHR) throws SQLException {
        if (idCandidatura < 1 || idHR < 1) {
            return false;
        }
        Connection connection = DatabaseManager.getInstance().getConnection();
        String delete = "deleted";
        modificaStatoCandidatura(idCandidatura, StatiCandidatura.Rifiutata);

        String query = "update " + TABLE_CANDIDATURA + " set Curriculum=?, DocumentiAggiuntivi=?, IdHR=? where IdCandidatura=?";
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, delete);
            stmt.setString(2, delete);
            stmt.setInt(3, idHR);
            stmt.setInt(4, idCandidatura);
            int result = stmt.executeUpdate();
            return result != 0;
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

    /**
     * questa funzionalità permette all'HR di accettare una candidatura
     *
     *  @param idCandidatura , {@literal idCandidatura > 0}  identifica la candidatura
     * @param idHR , {@literal idHR > 0 }  identifica l' HR
     * @param data , {@literal data >= actual date}  specifica la data del colloquio
     * @return boolean (false = i parametri non vengono rispettati o la funzionalità non va a buon fine,
     *                  true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query
     */
    public boolean accettaCandidatura(int idCandidatura, int idHR, Timestamp data) throws SQLException {
        if (idCandidatura < 1 || idHR < 1 || data.before(new java.util.Date())) {
            return false;
        }
        modificaStatoCandidatura(idCandidatura, StatiCandidatura.Accettata);
        Connection connection = DatabaseManager.getInstance().getConnection();
        String query = "update " + TABLE_CANDIDATURA + " set Stato=?, IdHR=?, DataOraColloquio=? where IdCandidatura=?";
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, "Accettata");
            stmt.setInt(2, idHR);
            stmt.setTimestamp(3, data);
            stmt.setInt(4, idCandidatura);
            int result = stmt.executeUpdate();
            return result != 0;
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

}
