package it.unisa.agency_formation.reclutamento.domain;

public class Colloquio {
    private StatiCandidatura stato;
    private int idCandidato;
    private int idHR;

    /**
     * Questo metodo permette di creare un colloquio senza specificare i parametri
     */

    public Colloquio() {
    }

    /**
     * Questo metodo permette di tornare lo stato della candidatura
     *
     * @return {@link StatiCandidatura} , stato della candidatura
     */

    public StatiCandidatura getStato() {
        return stato;
    }

    /**
     * Questo metodo permette di settare lo stato della candidatura
     *
     * @param state , stato della candidatura
     */

    public void setStato(StatiCandidatura state) {
        this.stato = state;
    }

    /**
     * Questo metodo permette di tornare l'id del candidato
     *
     * @return l'id del candidato
     */

    public int getIdCandidato() {
        return idCandidato;
    }

    /**
     * Questo metodo permette di settare l'id del candidato
     *
     * @param id del candidato
     */

    public void setIdCandidato(int id) {
        this.idCandidato = id;
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
     * @param IdHR , l'id dell'HR
     */

    public void setIdHR(int IdHR) {
        this.idHR = IdHR;
    }


}


