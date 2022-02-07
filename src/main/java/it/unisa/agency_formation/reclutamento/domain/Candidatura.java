package it.unisa.agency_formation.reclutamento.domain;

import java.sql.Date;
import java.sql.Timestamp;

public class Candidatura {
    private int idCandidatura;
    private String curriculum;
    private String documentiAggiuntivi;
    private StatiCandidatura stato;
    private Date dataCandidatura;
    private int idCandidato;
    private int idHR;
    private Timestamp dataOraColloquio;

    /**
     * Questo metodo permette di creare una candidatura senza specificare i parametri
     */
    public Candidatura() {
    }

    /**
     * Questo metodo permette di tornare l'id della candidatura
     *
     * @return id della candidatura
     */

    public int getIdCandidatura() {
        return idCandidatura;
    }

    /**
     * Questo metodo permette di settare id della candidatura
     *
     * @param idCandidature , id della candidatura
     */

    public void setIdCandidatura(int idCandidature) {
        this.idCandidatura = idCandidature;
    }

    /**
     * Questo metodo permette di tornare lo stato della candidatura
     *
     * @return {@link StatiCandidatura} stato della candidatura
     */

    public StatiCandidatura getStato() {
        return stato;
    }

    /**
     * Questo metodo permette di settare lo stato della candidatura
     *
     * @param state , lo stato della candidatura
     */

    public void setStato(StatiCandidatura state) {
        this.stato = state;
    }

    /**
     * Questo metodo permette di tornare la data del colloquio
     *
     * @return Date , la data del colloquio
     */

    public Date getDataCandidatura() {
        return dataCandidatura;
    }

    /**
     * Questo metodo permette di settare la data del colloquio
     *
     * @param dataCand , data del colloquio
     */

    public void setDataCandidatura(Date dataCand) {
        this.dataCandidatura = dataCand;
    }

    /**
     * Questo metodo permette di tornare l'ora del colloquio
     *
     * @return ora del colloquio
     */

    public Timestamp getDataOraColloquio() {
        return dataOraColloquio;
    }

    /**
     * Questo metodo permette di settare l'ora del colloquio
     *
     * @param dataOraCol , ora del colloquio
     */

    public void setDataOraColloquio(Timestamp dataOraCol) {
        this.dataOraColloquio = dataOraCol;
    }

    /**
     * Questo metodo permette di tornare l'id del candidato
     *
     * @return id del candidato
     */

    public int getIdCandidato() {
        return idCandidato;
    }

    /**
     * Questo metodo permette di settare id del candidato
     *
     * @param IdCandidato , id del candidato
     */

    public void setIdCandidato(int IdCandidato) {
        this.idCandidato = IdCandidato;
    }

    /**
     * Questo metodo permette di tornare l'id dell'HR
     *
     * @return l'id dell'HR
     */

    public int getIdHR() {
        return idHR;
    }

    /**
     * Questo metodo permette di settare l'id dell'HR
     *
     * @param IdHR , id dell'HR
     */

    public void setIdHR(int IdHR) {
        this.idHR = IdHR;
    }

    /**
     * Questo metodo permette di tornare la directory contenente il curriculum
     *
     * @return la directory contenente il curriculum
     */

    public String getCurriculum() {
        return curriculum;
    }

    /**
     * Questo metodo permette di settare la directory contenente il curriculum
     *
     * @param cv , directory contenente il curriculum
     */

    public void setCurriculum(String cv) {
        this.curriculum = cv;
    }

    /**
     * Questo metodo permette di tornare la directory contentente i documenti aggiuntivi
     *
     * @return la directory che contiene i documenti aggiuntivi
     */

    public String getDocumentiAggiuntivi() {
        return documentiAggiuntivi;
    }

    /**
     * Questo metodo permette di settare la directory contentente i documenti aggiuntivi
     *
     * @param docAggiuntivi , directory contentente i documenti aggiuntivi
     */

    public void setDocumentiAggiuntivi(String docAggiuntivi) {
        this.documentiAggiuntivi = docAggiuntivi;
    }
}
