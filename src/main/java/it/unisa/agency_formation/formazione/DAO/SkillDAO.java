package it.unisa.agency_formation.formazione.DAO;

import it.unisa.agency_formation.formazione.domain.Skill;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SkillDAO {

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di salvare una nuova skill
     *
     * @param skill , {@literal skill != null} rappresenta la skill da salvare
     * @return boolean true se la skill è stata salvata, false altrimenti
     * @throws SQLException errore nella query errore nella query
     */

    boolean salvaSkill(Skill skill) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di recuperare una skill tramite il nome
     *
     * @param nomeSkill , {@literal nomeSkill != null}  rappresenta il nome della skill da recuperare
     * @return skill se la skill è presente, null altrimenti
     * @throws SQLException errore nella query errore nella query
     */
    Skill recuperaSkillByNome(String nomeSkill) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di salvare una skill ad un dipendente
     *
     * @param idSkill      , {@literal idSkill > 0 } rappresenta l'id della skill da associare
     * @param idDip        , {@literal idDip > 0 } rappresenta l'id del dipendente
     * @param skillLivello , {@literal skillLivello > 0 @and skilLivello < 6} rappresenta il livello della skill
     * @return boolean true se il salvataggio va a buon fine, false altrimenti
     * @throws SQLException errore nella query errore nella query
     */
    boolean salvaSkillDipendente(int idSkill, int idDip, int skillLivello) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di recuperare l'id dell'ultima skill aggiunta
     *
     * @return id ultima skill , altrimenti -1
     * @throws SQLException errore nella query errore nella query
     */
    int recuperaUltimaSkill() throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di recuperare tutte le skill di un dipendente
     *
     * @param idDip ,  {@literal idDip > 0 } rappresenta l'id del dipendente
     * @return {@literal ArrayList<@link Skill>} se il dipendente ha delle skill, null altrimenti
     * @throws SQLException errore nella query errore nella query
     */
    ArrayList<Skill> recuperoSkillsByIdDipendente(int idDip) throws SQLException;
}
