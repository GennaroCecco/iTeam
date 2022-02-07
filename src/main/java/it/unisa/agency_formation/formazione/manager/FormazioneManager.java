package it.unisa.agency_formation.formazione.manager;

import it.unisa.agency_formation.formazione.domain.Documento;
import it.unisa.agency_formation.formazione.domain.Skill;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FormazioneManager {
    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di salvare un documento
     *
     * @param documento, rappresenta il documento da salvare
     * @return boolean (true = il documento è stato salvato, false = altrimenti)
     * @throws SQLException errore nella query errore nella query
     */
    boolean salvaDocumento(Documento documento) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di recuperare il materiale di formazione in base al team
     *
     * @param idTeam , rappresenta l'id del team
     * @return Documento se esiste, null altrimenti
     * @throws SQLException errore nella query errore nella query
     */

    Documento getMaterialeByIdTeam(int idTeam) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di salvare una skill
     *
     * @param skill rappresenta la skill da salvare
     * @return boolean (true = la skill è stata salvata, false = altrimenti)
     * @throws SQLException errore nella query errore nella query
     */
    boolean aggiungiSkill(Skill skill) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di recuperare l'id dell'ultima skill inserita
     *
     * @return l'id della skill, altrimenti -1
     * @throws SQLException errore nella query errore nella query
     */

    int getUltimaSkill() throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di aggiungere una skill al dipendente
     *
     * @param idSkill      , rappresenta l'id della skill da associare
     * @param idDip        , rappresenta l'id del dipendente
     * @param skillLivello , rappresenta il livello della skill
     * @return boolean (true = l'inserimento è andato a buon fine, false = altrimenti)
     * @throws SQLException errore nella query errore nella query
     */

    boolean addSkillDipendente(int idSkill, int idDip, int skillLivello) throws SQLException;

    /**
     * Questo metodo è l'interfaccia della funzionalità che permette di recuperare le skill di un dipendente
     *
     * @param idDipendente , rappresenta l'id del dipendente
     * @return {@literal ArrayList<@link Skill>} se esistono skill, null altrimenti
     * @throws SQLException errore nella query errore nella query
     */

    ArrayList<Skill> recuperoSkillConIdDipendente(int idDipendente) throws SQLException;
}
