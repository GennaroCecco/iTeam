package it.unisa.agency_formation.autenticazione.domain;

public class Utente {
    private String nome;
    private String cognome;
    private String email;
    private String pwd;
    private int id;
    private RuoliUtenti ruolo;

    /**
     * Questo metodo permette di creare un Utente inserendo tutti i dati necessari
     *
     * @param name     , nome del dipendente
     * @param surname  , cognome del dipendente
     * @param mail     , mail del dipendente
     * @param password , password del dipendente
     * @param role     , ruolo del dipendente
     */

    public Utente(String name, String surname, String mail, String password, RuoliUtenti role) {
        this.nome = name;
        this.cognome = surname;
        this.email = mail;
        this.pwd = password;
        this.ruolo = role;
    }

    /**
     * Questo metodo permette di creare un Utente senza specificare i parametri
     */

    public Utente() {
    }

    /**
     * Questo metodo permette di tornare l'id dell'utente
     *
     * @return int, identificatore utente
     */

    public int getId() {
        return id;
    }

    /**
     * Questo metodo permette di tornare il nome dell'utente
     *
     * @return nome , nome dell'utente
     */

    public String getName() {
        return nome;
    }

    /**
     * Questo metodo permette di tornare il cognome dell'utente
     *
     * @return cognome , cognome dell'utente
     */

    public String getSurname() {
        return cognome;
    }

    /**
     * Questo metodo permette di tornare l'email dell'utente
     *
     * @return email dell'utente
     */
    public String getEmail() {
        return email;
    }

    /**
     * Questo metodo permette di tornare la password dell'utente
     *
     * @return password dell'utente
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * Questo metodo permette di tornare il ruolo dell'utente
     *
     * @return {@link RuoliUtenti} , ruolo dell'utente
     */
    public RuoliUtenti getRole() {
        return ruolo;
    }

    /**
     * Questo metodo permette di settare l'id dell'utente
     *
     * @param idUser , id dell'utente
     */
    public void setId(int idUser) {
        this.id = idUser;
    }

    /**
     * Questo metodo permette di settare il nome dell'utente
     *
     * @param name , nome dell'utente
     */
    public void setName(String name) {
        this.nome = name;
    }

    /**
     * Questo metodo permette di settare il cognome dell'utente
     *
     * @param surname , cognome dell'utente
     */
    public void setSurname(String surname) {
        this.cognome = surname;
    }

    /**
     * Questo metodo permette di tornare l'email dell'utente
     *
     * @param mail , email dell'utente
     */
    public void setEmail(String mail) {
        this.email = mail;
    }

    /**
     * Questo metodo permette di settare la password dell'utente
     *
     * @param password ,  password dell'utente
     */
    public void setPwd(String password) {
        this.pwd = password;
    }

    /**
     * Questo metodo permette di settare il ruolo dell'utente
     *
     * @param role , ruolo dell'utente
     */
    public void setRole(RuoliUtenti role) {
        this.ruolo = role;
    }
}
