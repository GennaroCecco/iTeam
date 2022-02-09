package it.unisa.agency_formation.formazione.DAO;

import it.unisa.agency_formation.formazione.domain.Documento;
import it.unisa.agency_formation.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DocumentoDAOImpl implements DocumentoDAO {
    private static final String TABLE_DOCUMENTO = "documenti";

    /**
     * Questa funzionalità permette di salvare un documento
     *
     * @param doc , {@literal doc != null} rappresenta il documento da salvare
     * @return boolean true se il salvataggio va a buon fine, false altrimenti
     * @throws SQLException errore nella query errore nella query  doc!=null
     */

    public boolean salvaDocumento(Documento doc) throws SQLException {
        if (doc == null || doc.getMaterialeDiFormazione().length() > 512) {
            return false;
        }
        Connection connection = DatabaseManager.getInstance().getConnection();
        PreparedStatement save = null;
        String query = "INSERT INTO " + TABLE_DOCUMENTO + " (MaterialeDiFormazione, IdHR, IdTeam) "
                + "VALUES(?,?,?)";
        try {
            save = connection.prepareStatement(query);
            save.setString(1, doc.getMaterialeDiFormazione());
            save.setInt(2, doc.getIdHR());
            save.setInt(3, doc.getIdTeam());
            return save.executeUpdate() != 0;
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

    /**
     * Questa funzionalità permette di recuperare del materiale attraverso il team
     *
     * @param idTeam , {@literal idTeam > 0}  rappresenta l'id del team
     * @return Documento se esiste, null altrimenti
     * @throws SQLException errore nella query errore nella query
     */

    public Documento recuperaDocumentoByTeam(int idTeam) throws SQLException {
        if (idTeam < 1) {
            return null;
        }
        Connection connection = DatabaseManager.getInstance().getConnection();
        PreparedStatement stmt = null;
        ResultSet result;
        Documento documento = null;
        String query = "SELECT * FROM " + TABLE_DOCUMENTO + " WHERE IdTeam=?";
        try {
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, idTeam);
            result = stmt.executeQuery();
            if (result.next()) {
                documento = new Documento();
                documento.setIdDocumento(result.getInt("IdDocumento"));
                documento.setMaterialeDiFormazione(result.getString("MaterialeDiFormazione"));
                documento.setIdHR(result.getInt("IdHR"));
                documento.setIdTeam(result.getInt("IdTeam"));
            }
            return documento;
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }
}
