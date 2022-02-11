package it.unisa.agency_formation.FIA;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TestiTeam {
    private static final double prob_mutation = 0.5;
    private static final double prob_crossover = 0.8;
    private static final double elitism_size = 0.6;


    public static ArrayList<DipendenteRefactor> fromDataSet() throws IOException {
        String[] HEADERS = {"id", "name", "surname", "email",
                "skill1", "skill2", "skill3", "level1", "level2", "level3"};
        Reader in = new FileReader((System.getProperty("user.home") + "\\IdeaProjects\\iTeam\\Dataset\\dataset.csv"));
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withHeader(HEADERS)
                .withFirstRecordAsHeader()
                .parse(in);
        int i = 0;
        ArrayList<DipendenteRefactor> data = new ArrayList<>();
        for (CSVRecord record : records) {
            if (i == 500) {
                break;
            }
            DipendenteRefactor temp = new DipendenteRefactor();
            HashMap<String, Integer> mapTemp = new HashMap<>();
            String id = record.get("id");
            String name = record.get("name");
            String surname = record.get("surname");
            String email = record.get("email");
            String skill1 = record.get("skill1");
            String skill2 = record.get("skill2");
            String skill3 = record.get("skill3");
            String livello1 = record.get("level1");
            String livello2 = record.get("level2");
            String livello3 = record.get("level3");
            temp.setId(Integer.parseInt(id));
            temp.setNome(name);
            temp.setCognome(surname);
            temp.setEmail(email);
            mapTemp.put(skill1, Integer.parseInt(livello1));
            mapTemp.put(skill2, Integer.parseInt(livello2));
            mapTemp.put(skill3, Integer.parseInt(livello3));
            temp.setSkills(mapTemp);
            data.add(temp);
        }
        return data.size() > 0 ? data : null;
    }


    public static ArrayList<DipendenteRefactor> initPopolazione(ArrayList<DipendenteRefactor> data, int n, ArrayList<String> skillRichieste) {
        ArrayList<DipendenteRefactor> popolazione = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            DipendenteRefactor temp = null;
            for (int j = 0; j < skillRichieste.size(); j++) {
                for (int z = 0; z < 3; z++) {
                    if (data.get(i).getSkills().containsKey(skillRichieste.get(z))) {
                        temp = new DipendenteRefactor();
                        temp.setId(data.get(i).getId());
                        temp.setNome(data.get(i).getNome());
                        temp.setCognome(data.get(i).getCognome());
                        temp.setEmail(data.get(i).getEmail());
                        temp.setSkills(data.get(i).getSkills());
                    }
                }
            }
            if (temp != null) {
                popolazione.add(temp);
            }
        }
        return popolazione.size() > 0 ? popolazione : null;
    }


    public static ArrayList<TeamRefactor> onePointCrossover(TeamRefactor individuo1, TeamRefactor individuo2) {
        for (int i = 0; i < individuo1.getDipendenti().size(); i++) {
            DipendenteRefactor temp = individuo1.getDipendenti().get(i);
            if (new Random().nextDouble() < prob_crossover) {
                individuo1.getDipendenti().remove(i);
                individuo1.getDipendenti().add(i, individuo2.getDipendenti().get(i));
                individuo2.getDipendenti().remove(i);
                individuo2.getDipendenti().add(i, temp);
            }
        }
        ArrayList<TeamRefactor> toReturn = new ArrayList<>();
        toReturn.add(individuo1);
        toReturn.add(individuo2);
        return toReturn;
    }

    public TeamRefactor randomMutation(TeamRefactor individuo, ArrayList<String> skillsRichieste, ArrayList<DipendenteRefactor> data) {
        TeamRefactor newIndividuo = individuo;
        ArrayList<DipendenteRefactor> dipendenteRefactors = new ArrayList<>();
        for (int i = 0; i < individuo.getDipendenti().size(); i++) {
            DipendenteRefactor temp = null;
            if (new Random().nextDouble() < prob_mutation) {
                temp = new DipendenteRefactor();
                for (int j = 0; j < skillsRichieste.size(); j++) {
                    for (int z = 0; z < 3; z++) {
                        if (data.get(i).getSkills().containsKey(skillsRichieste.get(z))) {
                            temp = new DipendenteRefactor();
                            temp.setId(data.get(i).getId());
                            temp.setNome(data.get(i).getNome());
                            temp.setCognome(data.get(i).getCognome());
                            temp.setEmail(data.get(i).getEmail());
                            temp.setSkills(data.get(i).getSkills());
                        }
                    }
                }
                if (temp != null) {
                    dipendenteRefactors.add(temp);
                }
            }
        }
        newIndividuo.setDipendenti(dipendenteRefactors);
        return newIndividuo;
    }


    public static void main(String[] args) throws IOException {
        ArrayList<DipendenteRefactor> data = fromDataSet();
        if (data != null) {
            ArrayList<String> skillsRichieste = new ArrayList<>();
            skillsRichieste.add("Java");
            skillsRichieste.add("HTML");
            skillsRichieste.add("CSS");
            ArrayList<DipendenteRefactor> popolazione = initPopolazione(data, 500, skillsRichieste);
            if (popolazione != null) {
                System.out.println("Dimensione popolazione: " + popolazione.size());
            } else {
                System.out.println("Errore retireve form dataSet");
            }

        }
    }
}
