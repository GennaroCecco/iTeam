package it.unisa.agency_formation.autenticazione.manager;

import it.unisa.agency_formation.autenticazione.DAO.DipendenteDAOImpl;
import it.unisa.agency_formation.autenticazione.DAO.DipendenteDAO;
import it.unisa.agency_formation.autenticazione.DAO.UtenteDAOImpl;
import it.unisa.agency_formation.autenticazione.DAO.UtenteDAO;
import it.unisa.agency_formation.autenticazione.domain.Dipendente;
import it.unisa.agency_formation.autenticazione.domain.Utente;

import java.sql.SQLException;
import java.util.ArrayList;

public class AutenticazioneManagerImpl implements AutenticazioneManager {
        public static UtenteDAO daoUtente = new UtenteDAOImpl();
        public static DipendenteDAO daoDipendente = new DipendenteDAOImpl();
    /**
     * Questa funzionalità permette di far registrare un utente,l'utente non deve essere già registrato
     *
     * @param user l'utente che verrà registrato
     * @return boolean true se l'utente viene registrato, false altrimenti
     * @throws SQLException errore nella query errore nella query
     */
    @Override
    public boolean registrazione(Utente user) throws SQLException {
        if (!alreadyRegisteredUser(user)) {
            return daoUtente.salvaUtente(user);
        } else {
            return false;
        }
    }

    /**
     * Questa funzionalità permette di far eseguire il login ad un utente già registrato in precedenza
     *
     * @param email    dell'utente
     * @param password dell'utente
     * @return utente se il login va a buon fine, null altrimenti
     * @throws SQLException errore nella query errore nella query
     */
    @Override
    public Utente login(String email, String password) throws SQLException {
        return daoUtente.login(email, password);
    }

    /**
     * Questa funzionalità permette di recuperare un dipendente
     *
     * @param idDip , rappresenta l'id del dipendente
     * @return Dipendente se il dipendente esiste, null altrimenti
     * @throws SQLException errore nella query errore nella query
     */
    @Override
    public Dipendente getDipendente(int idDip) throws SQLException {
        return daoDipendente.recuperoDipendenteById(idDip);
    }

    /**
     * Questa funzionalità permette di recuperare i candidati con la candidatura
     * @return {@literal ArrayList<@link Utente>} se ci sono candidati con la candidatura, null altrimenti
     * @throws SQLException errore nella query errore nella query
     */
    @Override
    public ArrayList<Utente> getCandidatiConCandidatura() throws SQLException {
        return daoUtente.doRetrieveCandidatoConCandidatura();
    }

    /**
     * Questa funzionalità permette di recuperare i dipendenti
     * @return {@literal ArrayList<@link Dipendente>} se ci sono dipendenti, null altrimenti
     * @throws SQLException errore nella query errore nella query*/

    @Override
    public ArrayList<Dipendente> getTuttiDipendenti() throws SQLException {
        return daoDipendente.recuperaDipendenti();
    }

    /**
     * Questa funzionalità permette di assumere un candidato
     * @return boolean (true = l'assunzione va a buon fine, false = altrimenti
     *
     *  @throws SQLException errore nella query errore nella query*/

    @Override
    public boolean assumiCandidato(Dipendente dipendente) throws SQLException {
        daoDipendente.modificaRuoloUtente(dipendente.getIdDipendente());
        return (daoDipendente.salvaDipendente(dipendente));
    }

    /**
     * Questa funzionalità permette di recuperare i candidati che dovranno svolgere il colloquio
     * @return {@literal ArrayList<@link Utente>} se ci sono candidati per il colloquio, null altrimenti
     * @throws SQLException errore nella query errore nella query*/

    @Override
    public ArrayList<Utente> getCandidatiColloquio() throws SQLException {
        return daoUtente.recuperoCandidatiColloquio();
    }

    /**
     * Questa funzionalità permette di settare l'idTeam di un dipendente
     *
     * @param idDip  rappresenta l'id del dipendente
     * @param idTeam rappresenta l'id del team
     * @return boolean (true = il set è andato a buon fine, false = altrimenti)
     * @throws SQLException errore nella query errore nella query
     */
    @Override
    public boolean setTeamDipendente(int idDip, int idTeam) throws SQLException {
        return daoDipendente.setIdTeamDipendente(idDip, idTeam);
    }

    /**
     * Questa funzionalità permette di controllare se un utente sia già registrato
     *
     * @param user , utente interessato
     * @return boolean (false = l'utente non è registrato,
     *                  true = l'utente è registrato)
     * @throws SQLException errore nella query*/
    private boolean alreadyRegisteredUser(Utente user) throws SQLException {
      return daoUtente.checkEmail(user.getEmail());
    }
}
