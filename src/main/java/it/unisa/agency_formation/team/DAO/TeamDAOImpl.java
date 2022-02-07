package it.unisa.agency_formation.team.DAO;

import it.unisa.agency_formation.autenticazione.domain.Dipendente;
import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.StatiDipendenti;
import it.unisa.agency_formation.formazione.domain.Documento;
import it.unisa.agency_formation.team.domain.Team;
import it.unisa.agency_formation.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeamDAOImpl implements TeamDAO {
    private static final String TABLE_DIPENDENTE = "dipendenti";
    private static final String TABLE_UTENTE = "utenti";
    private static final String TABLE_TEAM = "team";

    /**
     * Questa funzionalità permette di salvare un team
     *
     * @param team , {@literal team != null} specifica il team da salvare
     * @param idUtente , {@literal idUtente > 0} identifica l'utente
     * @return boolean (false = i parametri non vengono rispettati o la funzionalità non va a buon fine,
     * true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query
     */
    public boolean salvaTeam(Team team, int idUtente) throws SQLException {
        if (team == null || idUtente < 1 || team.getNomeTeam().length() > 32
                || team.getDescrizione().length() > 512 || team.getNomeProgetto().length() > 32) {
            return false;
        } else {
            Connection connection = DatabaseManager.getInstance().getConnection();
            PreparedStatement save = null;
            String query = "insert into " + TABLE_TEAM + "(NomeProgetto,NumeroDipendenti,NomeTeam,Descrizione,Competenza,IdTM)"
                    + " values(?,?,?,?,?,?)";
            try {
                save = connection.prepareStatement(query);

                save.setString(1, team.getNomeProgetto());
                save.setInt(2, team.getNumeroDipendenti());
                save.setString(3, team.getNomeTeam());
                save.setString(4, team.getDescrizione());
                save.setString(5, null);
                save.setInt(6, idUtente);
                int result = save.executeUpdate();
                return result != 0;
            } finally {
                DatabaseManager.closeConnessione(connection);
            }
        }
    }

    /**
     * Questa funzionalità permette di eliminare un dipendente
     *
     * @param idTeam , {@literal idTeam > 0} identifica il team
     * @throws SQLException errore nella query errore nella query
     * @return boolean (false = i parametri non vengono rispettati o la funzionalità non va a buon fine,
     *                 true = la funzionalità va a buon fine)
     */
    public boolean rimuoviTeam(int idTeam) throws SQLException {
        if (idTeam < 1) {
            return false;
        }
        Connection connection = DatabaseManager.getInstance().getConnection();
        String query1 = "UPDATE " + TABLE_DIPENDENTE + " set idTeam=null WHERE idTeam=?";
        String query = "DELETE FROM " + TABLE_TEAM + " WHERE idTeam=?";
        PreparedStatement stmt1 = null;
        PreparedStatement stmt = null;
        try {
            stmt1 = connection.prepareStatement(query1);
            stmt1.setInt(1, idTeam);
            int res1 = stmt1.executeUpdate();
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, idTeam);
            int res2 = stmt.executeUpdate();
            return res1 != -1 && res2 != -1;

        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

    /**
     * Questa funzionalità permette di recuperare un team attraverso il suo id
     *
     * @param idTeam , {@literal idTeam > 0} identifica il team
     * @return Team , il team interessato
     * @throws SQLException errore nella query errore nella query
     */
    public Team recuperaTeamById(int idTeam) throws SQLException {
        if (idTeam < 1) {
            return null;
        }
        Connection connection = DatabaseManager.getInstance().getConnection();
        Team team = null;
        PreparedStatement stmt = null;
        ResultSet result;
        String query = "SELECT * FROM " + TABLE_TEAM + " left join documenti on team.IdTeam=documenti.IdTeam and team.IdTeam = ?";
        try {
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, idTeam);
            result = stmt.executeQuery();
            if (result.next()) {
                team = new Team();
                Documento documento = new Documento();
                team.setIdTeam(result.getInt("IdTeam"));
                team.setNomeProgetto(result.getString("NomeProgetto"));
                team.setNumeroDipendenti(result.getInt("NumeroDIpendenti"));
                team.setNomeTeam(result.getString("NomeTeam"));
                team.setDescrizione(result.getString("Descrizione"));
                team.setCompetenza(result.getString("Competenza"));
                team.setIdTM(result.getInt("IdTM"));
                if (result.getString("MaterialeDiFormazione") != null) {
                    documento.setMaterialeDiFormazione(result.getString("MaterialeDiFormazione"));
                    team.setDocumento(documento);
                }
            }
            return team;
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

    /**
     * questa funzionalità permette di rimuovere un dipendente da un team
     *
     * @param idDipendente , {@literal idDipendente > 0}  identifica il dipendente da rimuovere
     * @return boolean (false = i parametri non vengono rispettati o la funzionalità non va a buon fine,
     *                  true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query
     */
    public boolean rimuoviDipendente(int idDipendente) throws SQLException {
        if (idDipendente < 1) {
            return false;
        }
        Connection connection = DatabaseManager.getInstance().getConnection();
        String query = "UPDATE " + TABLE_DIPENDENTE + " SET IdTeam=NULL, Stato=1 WHERE IdDipendente=?";
        PreparedStatement stmt = null;
        int result;
        try {
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, idDipendente);
            result = stmt.executeUpdate();
            return result != -1;
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

    /**
     * Questa funzionalità permette di recuperare tutti i team presenti nella piattaforma
     * @return  {@literal ArrayList<@link Team>} , una lista di team
     * @throws SQLException errore nella query errore nella query
     * nella post-condizione l'arraylist di team non dev'essere vuota
     */
    public ArrayList<Team> recuperaTuttiTeam() throws SQLException {
        Connection connection = DatabaseManager.getInstance().getConnection();
        ArrayList<Team> teams = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_TEAM + " left join documenti on team.IdTeam=documenti.IdTeam";
        PreparedStatement stmt = null;
        ResultSet result;
        try {
            stmt = connection.prepareStatement(query);
            result = stmt.executeQuery();
            while (result.next()) {
                Team team = new Team();
                Documento documento = new Documento();
                team.setIdTeam(result.getInt("IdTeam"));
                team.setNomeProgetto(result.getString("NomeProgetto"));
                team.setNumeroDipendenti(result.getInt("NumeroDipendenti"));
                team.setNomeTeam(result.getString("NomeTeam"));
                team.setDescrizione(result.getString("Descrizione"));
                team.setCompetenza(result.getString("Competenza"));
                team.setIdTM(result.getInt("IdTM"));
                if (result.getString("MaterialeDiFormazione") != null) {
                    documento.setMaterialeDiFormazione(result.getString("MaterialeDiFormazione"));
                    team.setDocumento(documento);
                }
                teams.add(team);
            }
            if (teams.size() > 0) {
                return teams;
            } else {
                teams = null;
                return teams;
            }
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

    /**
     * Questa funzionalità permette di recuperare la lista dei team di un TM
     *
     *  @param idUtente , {@literal idUtente > 0}  identifica l'utente
     * @return  {@literal ArrayList<@link Team>} una lista di team
     * @throws SQLException errore nella query errore nella query
     * nella post-condizione l'arraylist dei team non dev'essere vuoto
     */
    public ArrayList<Team> recuperaTeamDiUnTM(int idUtente) throws SQLException {
        if (idUtente < 1) {
            return null;
        }
        Connection connection = DatabaseManager.getInstance().getConnection();
        ArrayList<Team> teams = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet result;
        String query = "SELECT * FROM " + TABLE_TEAM + " left join documenti on team.IdTeam=documenti.IdTeam where IdTM=?";
        try {
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, idUtente);
            result = stmt.executeQuery();
            while (result.next()) {
                Team team = new Team();
                Documento documento = new Documento();
                team.setIdTeam(result.getInt("IdTeam"));
                team.setNomeProgetto(result.getString("NomeProgetto"));
                team.setNumeroDipendenti(result.getInt("NumeroDipendenti"));
                team.setNomeTeam(result.getString("NomeTeam"));
                team.setDescrizione(result.getString("Descrizione"));
                team.setCompetenza(result.getString("Competenza"));
                team.setIdTM(result.getInt("IdTM"));
                if (result.getString("MaterialeDiFormazione") != null) {
                    documento.setMaterialeDiFormazione(result.getString("MaterialeDiFormazione"));
                    team.setDocumento(documento);
                }
                teams.add(team);
            }
            if (teams.size() > 0) {
                return teams;
            } else {
                return null;
            }
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

    /**
     * Questa funzionalità permette di modificare le competenze di un team
     *
     * @param competence , {@literal competence != null }  specifica la competenza con il quale aggiornare
     * @param idTeam , {@literal idTeam > 0}  identifica il team
     * @throws SQLException errore nella query errore nella query
     * @return boolean (false = i parametri non vengono rispettati o la funzionalità non va a buon fine,
     *                  true = la funzionalità va a buon fine)
     */
    public boolean specificaCompetenze(String competence, int idTeam) throws SQLException {
        if (competence == null || idTeam < 1 || competence.length() > 512) {
            return false;
        }
        Connection connection = DatabaseManager.getInstance().getConnection();
        String query = "UPDATE " + TABLE_TEAM + " SET Competenza=? WHERE IdTeam=?";
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, competence);
            stmt.setInt(2, idTeam);
            return stmt.executeUpdate() != 0;
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

    /**
     * Questa funzionalità permette di recuperare l'id dell'ultimo team creato
     * @return int , l'id dell'ultimo team creato
     * @throws SQLException errore nella query errore nella query
     */
    public int recuperaIdUltimoTeamCreato() throws SQLException {
        Connection connection = DatabaseManager.getInstance().getConnection();
        ResultSet result;
        PreparedStatement stmt = null;
        String query = " SELECT max(team.IdTeam) FROM " + TABLE_TEAM;
        int idTeam = 0;
        try {
            stmt = connection.prepareStatement(query);
            result = stmt.executeQuery();
            if (result.next()) {
                idTeam = result.getInt(1);
            }
            return idTeam;
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

    /**
     * Questa funzionalità permette di recuperare gli id dei dipendenti che appartengono ad un determinato team
     *
     * @param idTeam, {@literal idTeam > 0}  identifica il team
     * @return {@literal ArrayList<@link Integer>} una lista di idDipendenti membri di un team
     * @throws SQLException errore nella query errore nella query
     */
    public ArrayList<Integer> recuperaIdTeamMemberFromTeam(int idTeam) throws SQLException {
        if (idTeam < 1) {
            return null;
        }
        Connection connection = DatabaseManager.getInstance().getConnection();
        ResultSet result;
        PreparedStatement stmt = null;
        ArrayList<Integer> listaIdDips = new ArrayList<Integer>();
        String query = "SELECT idDipendente FROM " + TABLE_DIPENDENTE + " WHERE IdTeam=?";
        try {
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, idTeam);
            result = stmt.executeQuery();
            while (result.next()) {
                int idDipendente = result.getInt("IdDipendente");
                listaIdDips.add(idDipendente);
            }
            if (listaIdDips.size() > 0) {
                return listaIdDips;
            } else {
                listaIdDips = null;
                return listaIdDips;
            }
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

    /**
     * Questa funzionalità permette di modificare lo stato di un dipendente a disponibile
     *
     * @param idDip , {@literal idDip > 0}  identifica il dipendente
     * @return boolean (false = i parametri non vengono rispettati o la funzionalità non va a buon fine,
     *                  true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query
     */
    public boolean updateDipStateDissolution(int idDip) throws SQLException {
        if (idDip < 0) {
            return false;
        }
        Connection connection = DatabaseManager.getInstance().getConnection();
        PreparedStatement stmt = null;
        String query = "update " + TABLE_DIPENDENTE + " set Stato = ? where IdDipendente = ?";
        try {
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, 1);
            stmt.setInt(2, idDip);
            return stmt.executeUpdate() != 0;
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

    /**
     * Questa funzionalità permette di recuperare tutti i dipendenti
     * @return {@literal ArrayList<@link Dipendente>} , una lista di dipendenti
     * @throws SQLException errore nella query errore nella query
     */
    public ArrayList<Dipendente> recuperaDipendentiDelTeam() throws SQLException {
        Connection connection = DatabaseManager.getInstance().getConnection();
        PreparedStatement stmt = null;
        String query = "SELECT * FROM " + TABLE_DIPENDENTE + " inner join " + TABLE_UTENTE + " on utenti.IdUtente = dipendenti.IdDipendente";
        ArrayList<Dipendente> DipsUsers = new ArrayList<Dipendente>();
        ResultSet result;
        try {
            stmt = connection.prepareStatement(query);
            result = stmt.executeQuery();
            while (result.next()) {
                Dipendente dipUser = new Dipendente();
                Team team = new Team();
                dipUser.setIdDipendente(result.getInt("idDipendente"));
                dipUser.setResidenza(result.getString("Residenza"));
                dipUser.setTelefono(result.getString("Telefono"));
                if (!result.getBoolean("Stato")) {
                    dipUser.setStato(StatiDipendenti.OCCUPATO);

                } else if (result.getBoolean("Stato")) {
                    dipUser.setStato(StatiDipendenti.DISPONIBILE);
                }
                dipUser.setAnnoNascita(result.getInt("AnnoDiNascita"));
                team.setIdTeam(result.getInt("IdTeam"));
                dipUser.setTeam(team);
                dipUser.setId(result.getInt("IdUtente"));
                dipUser.setName(result.getString("Nome"));
                dipUser.setSurname(result.getString("Cognome"));
                dipUser.setPwd(result.getString("Pwd"));
                dipUser.setEmail(result.getString("Mail"));
                if (result.getInt("Ruolo") == 2) {
                    dipUser.setRole(RuoliUtenti.DIPENDENTE);
                }
                DipsUsers.add(dipUser);
            }
            if (DipsUsers.size() > 0) {
                return DipsUsers;
            } else {
                return null;
            }
        } finally {
            DatabaseManager.closeConnessione(connection);
        }
    }

}
