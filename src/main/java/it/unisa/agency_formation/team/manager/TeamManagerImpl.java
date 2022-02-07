package it.unisa.agency_formation.team.manager;

import it.unisa.agency_formation.autenticazione.domain.Dipendente;
import it.unisa.agency_formation.team.DAO.TeamDAO;
import it.unisa.agency_formation.team.DAO.TeamDAOImpl;
import it.unisa.agency_formation.team.domain.Team;

import java.sql.SQLException;
import java.util.ArrayList;

public class TeamManagerImpl implements TeamManager {
    public static TeamDAO daoTeam = new TeamDAOImpl();
    /**
     * Questo metodo permette di creare un team
     *
     * @param team , specifica il team da salvare
     * @param idUtente , identifica il TM che crea il team
     * @return boolean (false = la funzionalità non va a buon fine,
     * true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query*/

    @Override
    public boolean creaTeam(Team team, int idUtente) throws SQLException {
       return daoTeam.salvaTeam(team, idUtente);
    }

    /**
     * Questo metodo permette di rimuovere un dipendente da un team
     *
     * @param idDip , identifica il dipendente da rimuovere
     * @return boolean (false = la funzionalità non va a buon fine,
     * true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query*/

    @Override
    public boolean rimuoviDipendente(int idDip) throws SQLException {
        return daoTeam.rimuoviDipendente(idDip);
    }

    /**
     * Questo metodo permette di visualizzare tutti i team creati da uno specifico TM
     *
     * @param idUtente , identifica l'utente
     * @return {@literal ArrayList<@link Team>} se i team esistono , null altrimenti
     * @throws SQLException errore nella query errore nella query*/

    @Override
    public ArrayList<Team> visualizzaTeams(int idUtente) throws SQLException {
        return daoTeam.recuperaTeamDiUnTM(idUtente);
    }

    /**
     * Questo metodo permette di visualizzare tutti i team
     *
     * @return {@literal ArrayList<@link Team>} se ci sono team , null altrimenti
     * @throws SQLException errore nella query errore nella query*/

    @Override
    public ArrayList<Team> visualizzaTuttiTeams() throws SQLException {
        return daoTeam.recuperaTuttiTeam();
    }

    /**
     * Questo metodo permette di recuperare l'ultimo team creato
     *
     * @return l'id dell'ultimo team creato, 0 altrimenti
     * @throws SQLException errore nella query errore nella query*/

    @Override
    public int viewLastIdTeam() throws SQLException {
        return daoTeam.recuperaIdUltimoTeamCreato();
    }

    /**
     * Questo metodo permette di recuperare tutti i dipendenti di uno specifico team
     *
     * @param idTeam , identifica il team
     * @return {@literal ArrayList<@link Integer>} se ci sono dipendenti nel team , null altrimenti
     * @throws SQLException errore nella query errore nella query*/

    @Override
    public ArrayList<Integer> recuperaIdDipendentiDelTeam(int idTeam) throws SQLException {
        return daoTeam.recuperaIdTeamMemberFromTeam(idTeam);
    }

    /**
     * Questo metodo permette di modificare lo stato dei dipendenti di un team appena sciolto
     *
     * @param idDip , identifica il dipendente
     * @return boolean (false = la funzionalità non va a buon fine,
     *                  true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query*/

    @Override
    public boolean updateDipsDisso(int idDip) throws SQLException {
       return daoTeam.updateDipStateDissolution(idDip);

    }

    /**
     * Questo metodo permette di sciogliere il team
     *
     * @param idTeam , identifica il team da sciogliere
     * @return boolean (false = la funzionalità non va a buon fine,
     *                  true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query*/

    @Override
    public boolean sciogliTeam(int idTeam) throws SQLException {
        return daoTeam.rimuoviTeam(idTeam);

    }

    /**
     * Questo metodo permette di recuperare dipendenti di un team
     *
     * @return {@literal ArrayList<@link Dipendente>} se ci sono dipendenti nel team , null altrimenti
     * @throws SQLException errore nella query errore nella query*/

    @Override
    public ArrayList<Dipendente> recuperaDipendentiDelTeam() throws SQLException {
        return daoTeam.recuperaDipendentiDelTeam();

    }

    /**
     * Questo metodo permette di ritornare un team attraverso il suo id
     *
     * @param idTeam , identifica il team
     * @return Team se il team esiste , null altrimenti
     * @throws SQLException errore nella query errore nella query*/

    @Override
    public Team getTeamById(int idTeam) throws SQLException {
        return daoTeam.recuperaTeamById(idTeam);
    }

    /**
     * Questo metodo permette di modificare le competenze
     *
     * @param competence , sperifica con quale competenza dev'essere aggiornato
     * @param idTeam , identifica il team
     * @return boolean (false = la funzionalità non va a buon fine,
     *                  true = la funzionalità va a buon fine)
     * @throws SQLException errore nella query errore nella query*/

    @Override
    public boolean specificaLeCompetenze(String competence, int idTeam) throws SQLException {
        return daoTeam.specificaCompetenze(competence, idTeam);
    }

}
