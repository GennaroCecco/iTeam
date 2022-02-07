package it.unisa.agency_formation.formazione.domain;

public class SkillDipendente {
    private int idDipendente;
    private int idSkill;
    private int livello;

    public SkillDipendente(int idDip, int idSkil, int level) {
        this.idDipendente = idDip;
        this.idSkill = idSkil;
        this.livello = level;
    }

    /**
     * Questo metodo permette di creare una SkillDipendente senza specificare i parametri
     */
    public SkillDipendente() {
    }

    /**
     * Questo metodo permette di tornare l'id del dipendente che possiede tale skill
     *
     * @return l'id del dipendente
     */

    public int getIdDipendente() {
        return idDipendente;
    }

    /**
     * Questo metodo permette di settare l'id del dipendente che possiede tale skill
     *
     * @param idDip , l'id del dipendente
     */

    public void setIdDipendente(int idDip) {
        this.idDipendente = idDip;
    }

    /**
     * Questo metodo permette di tornare l'id della skill
     *
     * @return id della skill
     */

    public int getIdSkill() {
        return idSkill;
    }

    /**
     * uesto metodo permette di settare l'id della skill
     *
     * @param id della skill
     */

    public void setIdSkill(int id) {
        this.idSkill = id;
    }

    /**
     * Questo metodo permette di tornare il livello della skill
     *
     * @return il livello della skill
     */

    public int getLivello() {
        return livello;
    }

    /**
     * uesto metodo permette di settare il livello della skill
     *
     * @param level ,  livello della skill
     */

    public void setLivello(int level) {
        this.livello = level;
    }
}
