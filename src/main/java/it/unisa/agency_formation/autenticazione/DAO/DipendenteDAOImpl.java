package it.unisa.agency_formation.autenticazione.DAO;

import it.unisa.agency_formation.autenticazione.domain.Dipendente;
import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.StatiDipendenti;
import it.unisa.agency_formation.team.domain.Team;
import it.unisa.agency_formation.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DipendenteDAOImpl implements DipendenteDAO {
    private static final String TABLE_DIPENDENTE = "dipendenti";

    /**
     * Questa funzionalità permette di salvare un dipendente
     *
     * @param dipendente , {@literal dipendente != null} è il dipendente da registrare
     * @return boolean true se il dipendente èì stato salvato, false altrimenti
     * @throws SQLException errore nella query errore nella query*/

    public boolean salvaDipendente(Dipendente dipendente) throws SQLException {
        if (dipendente == null || dipendente.getResidenza().length() > 128
                || dipendente.getTelefono().length() > 20) {
            return false;
        }
        Connection connection = DatabaseManager.getInstance().getConnection();
        PreparedStatement save = null;
        String query = "insert into " + TABLE_DIPENDENTE + " (IdDipendente,Residenza,Telefono,Stato,AnnoDiNascita)"
                + " values(?,?,?,?,?)";
        try {
            save = connection.prepareStatement(query);
            save.setInt(1, dipendente.getIdDipendente());
            save.setString(2, dipendente.getResidenza());
            save.setString(3, dipendente.getTelefono());
            save.setBoolean(4, true);
            save.setInt(5, dipendente.getAnnoNascita());
            return save.executeUpdate() != 0;
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

    /**
     * Questa funzionalità permette di settare a 2 il ruolo dell'utente quando è assunto,
     *
     * @param id , {@literal id > 0} è l'id dell'utente candidato che diventerà dipendente
     * @return boolean true se il ruolo è stato modificato, false altrimenti
     * @throws SQLException errore nella query errore nella query
     */
    public boolean modificaRuoloUtente(int id) throws SQLException {
        if (id <= 0) {
            return false;
        }
        Connection connection = DatabaseManager.getInstance().getConnection();
        PreparedStatement update = null;
        String query = "Update Utenti Set Ruolo=2 Where IdUtente=?";
        try {
            update = connection.prepareStatement(query);
            update.setInt(1, id);
            return update.executeUpdate() != 0;
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

    /**
     * Questa funzionalità permette di recuperare un dipendente attraverso il suo id,
     *
     * @param id , {@literal id > 0} l'Id del dipendente che vogliamo recuperare
     * @return Dipendente se è presente, null altrimenti
     * @throws SQLException errore nella query errore nella query
     */
    public Dipendente recuperoDipendenteById(int id) throws SQLException {
        if (id <= 0) {
            return null;
        }
        ResultSet result;
        Connection connection = DatabaseManager.getInstance().getConnection();
        PreparedStatement retrieve = null;
        String query = "Select * from " + TABLE_DIPENDENTE + " inner join utenti on utenti.IdUtente = dipendenti.IdDipendente where IdDipendente=?";
        Dipendente user = null;
        try {
            retrieve = connection.prepareStatement(query);
            retrieve.setInt(1, id);
            result = retrieve.executeQuery();
            if (result.next()) {
                user = new Dipendente();
                Team team = new Team();
                user.setIdDipendente(result.getInt("IdDipendente"));
                user.setResidenza(result.getString("Residenza"));
                user.setTelefono(result.getString("Telefono"));
                boolean stato = result.getBoolean("Stato");
                if (!(stato)) {
                    user.setStato(StatiDipendenti.OCCUPATO);
                } else if (stato) {
                    user.setStato(StatiDipendenti.DISPONIBILE);
                }
                user.setAnnoNascita(result.getInt("AnnoDiNascita"));
                team.setIdTeam(result.getInt("IdTeam"));
                user.setTeam(team);
                user.setId(result.getInt("IdUtente"));
                user.setName(result.getString("Nome"));
                user.setSurname(result.getString("Cognome"));
                user.setPwd(result.getString("Pwd"));
                user.setEmail(result.getString("Mail"));
                user.setRole(RuoliUtenti.DIPENDENTE);
            }
            return user;
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

    /**
     * Questa funzionalità permette di recuperare tutti i dipendenti
     *
     * @return {@literal ArrayList<@link Dipendente>} lista di dipendenti se sono presenti, null altrimenti
     * @throws SQLException errore nella query errore nella query
     */
    public ArrayList<Dipendente> recuperaDipendenti() throws SQLException {
        Connection connection = DatabaseManager.getInstance().getConnection();
        PreparedStatement stmt = null;
        String query = "SELECT * FROM " + TABLE_DIPENDENTE + " inner join utenti on utenti.IdUtente = dipendenti.IdDipendente";
        ArrayList<Dipendente> dipendenti = new ArrayList<Dipendente>();
        ResultSet result;
        try {
            stmt = connection.prepareStatement(query);
            result = stmt.executeQuery();
            while (result.next()) {
                Team team = new Team();
                Dipendente dipUser = new Dipendente();
                dipUser.setIdDipendente(result.getInt("idDipendente"));
                dipUser.setResidenza(result.getString("Residenza"));
                dipUser.setTelefono(result.getString("Telefono"));
                if (!result.getBoolean("Stato")) {
                    dipUser.setStato(StatiDipendenti.OCCUPATO);
                }
                if (result.getBoolean("Stato")) {
                    dipUser.setStato(StatiDipendenti.DISPONIBILE);
                }
                dipUser.setAnnoNascita(result.getInt("AnnoDiNascita"));
                if (result.getInt("IdTeam") != 0) {
                    team.setIdTeam(result.getInt("IdTeam"));
                    dipUser.setTeam(team);
                }
                dipUser.setId(result.getInt("IdUtente"));
                dipUser.setName(result.getString("Nome"));
                dipUser.setSurname(result.getString("Cognome"));
                dipUser.setPwd(result.getString("Pwd"));
                dipUser.setEmail(result.getString("Mail"));
                dipUser.setRole(RuoliUtenti.DIPENDENTE);
                dipendenti.add(dipUser);
            }
            return dipendenti.size() > 0 ? dipendenti : null;
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

    /**
     * Questa funzionalità permette di aggiornare idTeam quando un dipendente viene aggiunto,
     *
     * @param idDip , {@literal idDip > 0} rappresenta l'id del dipendente
     * @param idTeam , {@literal idTeam > 0} rappresenta l'id del team
     * @return boolean true se il set è andato a buon fine, false altrimenti
     * @throws SQLException errore nella query errore nella query
     */
    public boolean setIdTeamDipendente(int idDip, int idTeam) throws SQLException {
        if (idDip <= 0 || idTeam <= 0) {
            return false;
        }
        Connection connection = DatabaseManager.getInstance().getConnection();
        PreparedStatement stm = null;
        String query = "update " + TABLE_DIPENDENTE + " set IdTeam = ?, Stato = ? where IdDipendente = ?";
        try {
            stm = connection.prepareStatement(query);
            stm.setInt(1, idTeam);
            stm.setBoolean(2, false);
            stm.setInt(3, idDip);
            int result = stm.executeUpdate();
            if (result < 1) {
                return false;
            }
            return true;
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }
}
