package it.unisa.agency_formation.autenticazione.domain;

import it.unisa.agency_formation.formazione.domain.Skill;
import it.unisa.agency_formation.team.domain.Team;

import java.util.ArrayList;

public class Dipendente extends Utente {
    private int idDipendente;
    private int annoNascita;
    private String residenza;
    private String telefono;
    private StatiDipendenti stato;
    private Team team;
    private ArrayList<Skill> skills;

    /**
     * Questo metodo permette di creare un dipendente inserendo tutti i dati necessari
     *
     * @param name        , nome del dipendente
     * @param surname     , cognome del dipendente
     * @param mail        , mail del dipendente
     * @param password    , password del dipendente
     * @param role        , ruolo del dipendente
     * @param idDip       , identificatore del dipendente
     * @param yearNascita , anno nascita del dipendente
     * @param residence   , residenza del dipendente
     * @param tel         , numero di telefono del dipendente
     * @param state       , stato del dipendente
     */
    public Dipendente(String name, String surname, String mail, String password, RuoliUtenti role, int idDip, int yearNascita, String residence, String tel, StatiDipendenti state) {
        super(name, surname, mail, password, role);
        this.idDipendente = idDip;
        this.annoNascita = yearNascita;
        this.residenza = residence;
        this.telefono = tel;
        this.stato = state;
    }

    /**
     * Questo metodo permette di creare un dipendente senza specificare i parametri
     */

    public Dipendente() {
    }

    /**
     * Questo metodo permette di tornare l'id del dipendente
     *
     * @return l' identificatore del dipendente
     */

    public int getIdDipendente() {
        return idDipendente;
    }

    /**
     * Questo metodo permette di settare l'id del dipendente
     *
     * @param idUtente , identificatore del dipendente
     */

    public void setIdDipendente(int idUtente) {
        this.idDipendente = idUtente;
    }

    /**
     * Questo metodo permette di tornare l'anno di nascita del dipendente
     *
     * @return l' anno di nascita del dipendente
     */

    public int getAnnoNascita() {
        return annoNascita;
    }

    /**
     * Questo metodo permette di settare l'anno di nascita del dipendente
     *
     * @param yearNascita , anno di nascita del dipendente
     */

    public void setAnnoNascita(int yearNascita) {
        this.annoNascita = yearNascita;
    }

    /**
     * Questo metodo permette di tornare la residenza del dipendente
     *
     * @return la residenza interessata
     */

    public String getResidenza() {
        return residenza;
    }

    /**
     * Questo metodo permette di settare la residenza del dipendente
     *
     * @param residenz , la residenza interessata
     */

    public void setResidenza(String residenz) {
        this.residenza = residenz;
    }

    /**
     * Questo metodo permette di tornare il numero di telefono del dipendente
     *
     * @return il numero del dipendente
     */

    public String getTelefono() {
        return telefono;
    }

    /**
     * Questo metodo permette di settare il numero di telefono del dipendente
     *
     * @param tel , il telefono del dipendente
     */

    public void setTelefono(String tel) {
        this.telefono = tel;
    }

    /**
     * Questo metodo permette di tornare lo stato del dipendente
     *
     * @return lo stato del dipendente
     */

    public StatiDipendenti getStato() {
        return stato;
    }

    /**
     * Questo metodo permette di settare lo stato del dipendente
     *
     * @param state , lo stato del dipendente
     */

    public void setStato(StatiDipendenti state) {
        this.stato = state;
    }

    /**
     * Questo metodo permette di settare il team il quale il dipendente è membro
     *
     * @param Team , il team interessato
     */

    public void setTeam(Team Team) {
        this.team = Team;
    }

    /**
     * Questo metodo permette di tornare il team il quale il dipendente è membro
     *
     * @return il team interessato
     */

    public Team getTeam() {
        return team;
    }

    /**
     * Questo metodo permette di tornare tutte le skill del dipendente
     *
     * @return {@literal ArrayList<@link Skill>} , lista delle skill
     */

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    /**
     * Questo metodo permette di settare tutte le skill del dipendente
     *
     * @param skill , lista delle skill
     */

    public void setSkills(ArrayList<Skill> skill) {
        this.skills = skill;
    }
}
