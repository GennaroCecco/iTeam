package it.unisa.agency_formation.FIA;

import java.util.HashMap;

public class DipendenteRefactor {
    private int id;
    private String nome,cognome,email;
    private HashMap<String,Integer> skills;

    public DipendenteRefactor(int id, String nome, String cognome, String email, HashMap<String, Integer> skills) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.skills = skills;
    }

    public DipendenteRefactor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HashMap<String, Integer> getSkills() {
        return skills;
    }

    public void setSkills(HashMap<String, Integer> skills) {
        this.skills = skills;
    }
}
