package it.unisa.agency_formation.formazione.DAO;

import it.unisa.agency_formation.formazione.domain.Documento;

import java.sql.SQLException;

public interface DocumentoDAO {
    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di salvare un documento
     *
     * @param doc , {@literal doc != null} rappresenta il documento da salvare
     * @return boolean true se il salvataggio va a buon fine, false altrimenti
     * @throws SQLException errore nella query errore nella query  doc!=null
     */
    boolean salvaDocumento(Documento doc) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di recuperare del materiale attraverso il team
     *
     * @param idTeam , {@literal idTeam > 0}  rappresenta l'id del team
     * @return Documento se esiste, null altrimenti
     * @throws SQLException errore nella query errore nella query
     */

    Documento recuperaDocumentoByTeam(int idTeam) throws SQLException;
}
