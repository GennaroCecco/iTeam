package it.unisa.agency_formation.team.domain;

import it.unisa.agency_formation.formazione.domain.Documento;

public class Team {
    private int idTeam;
    private String NomeProgetto;
    private int NumeroDipendenti;
    private String NomeTeam;
    private String Descrizione;
    private String Competenza;
    private Documento documento;
    private int idTM;

    /**
     * Questo metodo permette di creare un team inserendo tutti i dati necessari
     *
     * @param nomeProgetto     , nome del progetto
     * @param numeroDipendenti , numero dei dipendenti
     * @param nomeTeam         , nome del team
     * @param descrizione      , descrizione del team
     * @param competenza       , competenze necessarie
     * @param IdTM             , identificatore del TM
     */

    public Team(String nomeProgetto, int numeroDipendenti, String nomeTeam, String descrizione, String competenza, int IdTM) {
        this.NomeProgetto = nomeProgetto;
        this.NumeroDipendenti = numeroDipendenti;
        this.NomeTeam = nomeTeam;
        this.Descrizione = descrizione;
        this.Competenza = competenza;
        this.idTM = IdTM;
    }

    /**
     * Questo metodo permette di creare un team senza specificare i parametri
     */

    public Team() {
    }

    /**
     * Questo metodo permette di tornare l'id del team
     *
     * @return l'id del team
     */

    public int getIdTeam() {
        return idTeam;
    }

    /**
     * Questo metodo permette di settare l'id del team
     *
     * @param IdTeam , l'id del team
     */

    public void setIdTeam(int IdTeam) {
        this.idTeam = IdTeam;
    }

    /**
     * Questo metodo permette di tornare il nome del progetto
     *
     * @return il nome del progetto
     */

    public String getNomeProgetto() {
        return NomeProgetto;
    }

    /**
     * Questo metodo permette di settare il nome del progetto
     *
     * @param nameProgetto , il nome del progetto
     */

    public void setNomeProgetto(String nameProgetto) {
        NomeProgetto = nameProgetto;
    }

    /**
     * Questo metodo permette di tornare il numero massimo di membri
     *
     * @return il numero massimo di membri
     */

    public int getNumeroDipendenti() {
        return NumeroDipendenti;
    }

    /**
     * Questo metodo permette di settare il numero massimo di membri
     *
     * @param numDipendenti , il numero massimo di membri
     */

    public void setNumeroDipendenti(int numDipendenti) {
        NumeroDipendenti = numDipendenti;
    }

    /**
     * Questo metodo permette di tornare il nome del team
     *
     * @return il nome del team
     */

    public String getNomeTeam() {
        return NomeTeam;
    }

    /**
     * Questo metodo permette di settare il nome del team
     *
     * @param nameTeam , il nome del team
     */

    public void setNomeTeam(String nameTeam) {
        NomeTeam = nameTeam;
    }

    /**
     * Questo metodo permette di tornare la descrizione del team
     *
     * @return la descrizione del team
     */

    public String getDescrizione() {
        return Descrizione;
    }

    /**
     * Questo metodo permette di settare la descrizione del team
     *
     * @param description ,  la descrizione del team
     */

    public void setDescrizione(String description) {
        Descrizione = description;
    }

    /**
     * Questo metodo permette di tornare le competenze necessarie per lo svolgimento del progetto
     *
     * @return le competenze necessarie
     */

    public String getCompetenza() {
        return Competenza;
    }

    /**
     * Questo metodo permette di settare le competenze necessarie per lo svolgimento del progetto
     *
     * @param competence , le competenze necessarie
     */

    public void setCompetenza(String competence) {
        Competenza = competence;
    }

    /**
     * Questo metodo permette di tornare l'id del TM che ha creato il tem
     *
     * @return l'id del TM
     */

    public int getIdTM() {
        return idTM;
    }

    /**
     * Questo metodo permette di settare l'id del TM che ha creato il tem
     *
     * @param IdTM , l'id del TM
     */

    public void setIdTM(int IdTM) {
        this.idTM = IdTM;
    }

    /**
     * Questo metodo permette di tornare il documento per la formazione del team
     *
     * @return il documento
     */

    public Documento getDocumento() {
        return documento;
    }

    /**
     * Questo metodo permette di settare il documento per la formazione del team
     *
     * @param document , il documento
     */

    public void setDocumento(Documento document) {
        this.documento = document;
    }
}
