package it.unisa.agency_formation.formazione.domain;

public class Documento {
    private String materialeDiFormazione;
    private int idHR;
    private int idTeam;
    private int idDocumento;

    /**
     * Questo metodo permette di creare un documento senza specificare i parametri
     */

    public Documento() {
    }

    /**
     * Questo metodo permette di creare un documento inserendo tutti i dati necessari
     *
     * @param IdDocumento           , identificatore del documento
     * @param MaterialeDiFormazione , materiale di formazione
     * @param IdHR                  , identificatore HR
     * @param IdTeam                , identificatore team
     */

    public Documento(int IdDocumento, String MaterialeDiFormazione, int IdHR, int IdTeam) {
        this.idDocumento = IdDocumento;
        this.materialeDiFormazione = MaterialeDiFormazione;
        this.idHR = IdHR;
        this.idTeam = IdTeam;
    }

    /**
     * Questo metodo permette di tornare il materiale di formazione
     *
     * @return materiale di formazione
     */

    public String getMaterialeDiFormazione() {
        return materialeDiFormazione;
    }

    /**
     * Questo metodo permette di settare il materiale di formazione
     *
     * @param materialeFormazione , il materiale di formazione
     */

    public void setMaterialeDiFormazione(String materialeFormazione) {
        this.materialeDiFormazione = materialeFormazione;
    }

    /**
     * Questo metodo permette di tornare l'id dell'HR
     *
     * @return identificatore HR
     */

    public int getIdHR() {
        return idHR;
    }

    /**
     * Questo metodo permette di settare l'id dell'HR
     *
     * @param idUtente identificatore dell'HR
     */

    public void setIdHR(int idUtente) {
        this.idHR = idUtente;
    }

    /**
     * Questo metodo permette di tornare l'id del team
     *
     * @return identificatore team
     */

    public int getIdTeam() {
        return idTeam;
    }

    /**
     * Questo metodo permette di settare l'id del team
     *
     * @param id del team
     */

    public void setIdTeam(int id) {
        this.idTeam = id;
    }

    /**
     * Questo metodo permette di tornare l'id del documento
     *
     * @return identificatore del documento
     */

    public int getIdDocumento() {
        return idDocumento;
    }

    /**
     * Questo metodo permette di settare l'id del documento
     *
     * @param idDoc , id del documento
     */

    public void setIdDocumento(int idDoc) {
        this.idDocumento = idDoc;
    }
}
