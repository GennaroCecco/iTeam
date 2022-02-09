package it.unisa.agency_formation.formazione.domain;

public class Skill {
    private int idSkill;
    private String nomeSkill;
    private String descrizioneSkill;

    /**
     * Questo metodo permette di creare una skill senza specificare i parametri
     */

    public Skill() {
    }

    /**
     * Questo metodo permette di creare una skill inserendo tutti i dati necessari
     *
     * @param nameSkill        , nome della skill
     * @param descriptionSkill , descriizone della skill
     */

    public Skill(String nameSkill, String descriptionSkill) {
        this.nomeSkill = nameSkill;
        this.descrizioneSkill = descriptionSkill;
    }

    /**
     * Questo metodo permette di tornare l'id della Skill
     *
     * @return identificatore della skill
     */

    public int getIdSkill() {
        return idSkill;
    }

    /**
     * Questo metodo permette di settare l'id della skill
     *
     * @param id della skill
     */

    public void setIdSkill(int id) {
        this.idSkill = id;
    }

    /**
     * Questo metodo permette di tornare il nome della skill
     *
     * @return nome della skill
     */

    public String getNomeSkill() {
        return nomeSkill;
    }

    /**
     * Questo metodo permette di settare il nome della skill
     *
     * @param nameSkill ,  nome della skill
     */

    public void setNomeSkill(String nameSkill) {
        this.nomeSkill = nameSkill;
    }

    /**
     * Questo metodo permette di tornare la descrizione della skill
     *
     * @return descrizione della skill
     */

    public String getDescrizioneSkill() {
        return descrizioneSkill;
    }

    /**
     * Questo metodo permette di settare la descrizione della skill
     *
     * @param descriptionSkill , descrizione della skill
     */

    public void setDescrizioneSkill(String descriptionSkill) {
        this.descrizioneSkill = descriptionSkill;
    }
}
