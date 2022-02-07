package it.unisa.agency_formation.autenticazione.DAO;

import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.reclutamento.domain.StatiCandidatura;
import it.unisa.agency_formation.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UtenteDAOImpl implements UtenteDAO {
    private static final String TABLE_UTENTE = "utenti";

    /**
     * Questo metodo permette di salvare un utente
     *
     * @param user , {@literal user != null} user è l'utente da registrare
     * @return boolean true se l'utente è stato salvato con successo, false altrimenti
     * @throws SQLException errore nella query errore nella query
     */
    @Override
    public  boolean salvaUtente(Utente user) throws SQLException {
        if (user == null || user.getName().length() > 32 || user.getSurname().length() > 32
                || user.getPwd().length() > 16 || user.getEmail().length() > 32 || user.getPwd().length() < 3) {
            return false;
        }
        Connection connection = DatabaseManager.getInstance().getConnection();
        PreparedStatement save = null;
        String query = "insert into " + TABLE_UTENTE + " (Nome,Cognome,Pwd,Mail,Ruolo)"
                + " values(?,?,?,?,?)";
        try {
            save = connection.prepareStatement(query);
            save.setString(1, user.getName());
            save.setString(2, user.getSurname());
            save.setString(3, user.getPwd());
            save.setString(4, user.getEmail());
            switch (user.getRole()) {
                case CANDIDATO:
                    save.setInt(5, 1);
                    break;
                case DIPENDENTE:
                    save.setInt(5, 2);
                    break;
                case TM:
                    save.setInt(5, 3);
                    break;
                case HR:
                    save.setInt(5, 4);
                    break;
            }
            return save.executeUpdate() != 0;
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

    /**
     * Questo metodo permette di recuperare un utente
     *
     * @param email , {@literal email != null } è l'email dell'utente
     * @param pwd   , {@literal pwd != null} è la password dell'utente
     * @return Utente che si è registrato in precedenza, null se non è presente nel db
     * @throws SQLException errore nella query errore nella query
     */
    public Utente login(String email, String pwd) throws SQLException {
        if (email == null || pwd == null || email.length() > 32 || pwd.length() > 16 || pwd.length() < 3) {
            return null;
        }
        Connection connection = DatabaseManager.getInstance().getConnection();
        ResultSet result;
        PreparedStatement retrieve = null;
        String query = "Select * from " + TABLE_UTENTE + " where Mail=? and Pwd=?";
        Utente user = null;
        try {
            retrieve = connection.prepareStatement(query);
            retrieve.setString(1, email);
            retrieve.setString(2, pwd);
            result = retrieve.executeQuery();
            if (result.next()) {
                user = new Utente();
                user.setId(result.getInt("IdUtente"));
                user.setName(result.getString("Nome"));
                user.setSurname(result.getString("Cognome"));
                user.setPwd(result.getString("Pwd"));
                user.setEmail(result.getString("Mail"));
                switch (result.getInt("Ruolo")) {
                    case 1:
                        user.setRole(RuoliUtenti.CANDIDATO);
                        break;
                    case 2:
                        user.setRole(RuoliUtenti.DIPENDENTE);
                        break;
                    case 3:
                        user.setRole(RuoliUtenti.TM);
                        break;
                    case 4:
                        user.setRole(RuoliUtenti.HR);
                        break;
                }
            }
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
        return user;
    }

    /**
     * Questa funzionalità permette di recuperare i candidati che hanno presentato la propria candidatura
     *
     * @return {@literal ArrayList<@link Utente>} se ci sono candidati altrimenti null
     * @throws SQLException errore nella query errore nella query
     */

    public ArrayList<Utente> doRetrieveCandidatoConCandidatura() throws SQLException {
        Connection connection = DatabaseManager.getInstance().getConnection();
        ResultSet result;
        PreparedStatement retrieve = null;
        String query = "select IdUtente,Nome,Cognome,Pwd,Mail,Ruolo from utenti inner join candidature "
                + "on utenti.IdUtente=candidature.IdCandidato and candidature.Stato NOT IN (?,?,?)";

        ArrayList<Utente> utenti = new ArrayList<>();
        try {
            retrieve = connection.prepareStatement(query);
            retrieve.setString(1, StatiCandidatura.Rifiutata.toString());
            retrieve.setString(2, StatiCandidatura.Accettata.toString());
            retrieve.setString(3, StatiCandidatura.Assunzione.toString());
            result = retrieve.executeQuery();
            while (result.next()) {
                Utente user = new Utente();
                user.setId(result.getInt("IdUtente"));
                user.setName(result.getString("Nome"));
                user.setSurname(result.getString("Cognome"));
                user.setPwd(result.getString("Pwd"));
                user.setEmail(result.getString("Mail"));
                user.setRole(RuoliUtenti.CANDIDATO);
                utenti.add(user);
            }
            return utenti.size() > 0 ? utenti : null;
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

    /**
     * Questa funzionalità permette di recuperare i candidati che svolgeranno il colloquio
     *
     * @return {@literal ArrayList<@link Utente>} ritorna un array se ci sono candidati per il colloquio, altrimenti null
     * @throws SQLException errore nella query errore nella query
     */

    public ArrayList<Utente> recuperoCandidatiColloquio() throws SQLException {
        Connection connection = DatabaseManager.getInstance().getConnection();
        ResultSet result;
        PreparedStatement retrieve = null;
        String query = "select * from utenti inner join candidature on "
                + "IdUtente=IdCandidato and candidature.Stato='Accettata'";
        ArrayList<Utente> utenti = new ArrayList<>();
        try {
            retrieve = connection.prepareStatement(query);
            result = retrieve.executeQuery();
            while (result.next()) {
                Utente user = new Utente();
                user.setId(result.getInt("IdUtente"));
                user.setName(result.getString("Nome"));
                user.setSurname(result.getString("Cognome"));
                user.setPwd(result.getString("Pwd"));
                user.setEmail(result.getString("Mail"));
                user.setRole(RuoliUtenti.CANDIDATO);
                utenti.add(user);
            }
            return utenti.size() > 0 ? utenti : null;
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

    /**
     * Questa funzionalità permette di controllare se una mail è già esistente
     *
     * @param email , email da controllare
     * @return boolean (true = non esiste altra mail identica, false = altrimenti)
     * @throws SQLException errore nella query errore nella query
     */

    public boolean checkEmail(String email) throws SQLException {
        if (email.length() > 32) {
            return false;
        }
        Connection connection = DatabaseManager.getInstance().getConnection();
        PreparedStatement retrieve = null;
        String query = "select count(IdUtente) as num from utenti where Mail=?";
        ResultSet resultSet = null;
        int n = -1;
        try {
            retrieve = connection.prepareStatement(query);
            retrieve.setString(1, email);
            resultSet = retrieve.executeQuery();
            if (resultSet.next()) {
                n = resultSet.getInt("num");
            }
            return n == 1;
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }


}
