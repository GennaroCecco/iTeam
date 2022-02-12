package it.unisa.agency_formation.FIA;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.security.DigestException;
import java.util.*;

public class TestiTeam {
    private static final double prob_mutation = 0.5;
    private static final double prob_crossover = 0.8;
    private static final double elitism_size = 0.6;
    private static final int scoreSkillDefault = 15;
    private static final int teamSize = 4;


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
            HashMap<String, Integer> toAdd = new HashMap<>();

            for (int j = 0; j < skillRichieste.size(); j++) {
                for (int z = 0; z < 3; z++) {
                    if (data.get(i).getSkills().containsKey(skillRichieste.get(z))) {
                        if (toAdd.size() > 0 && toAdd.containsKey(skillRichieste.get(z))) {

                        } else {
                            toAdd.put(skillRichieste.get(z), data.get(i).getSkills().get(skillRichieste.get(z)));
                        }
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
                if (toAdd.size() > 0) {
                    int somma = 0;
                    temp.setSkills(toAdd);
                    Collection<String> key = toAdd.keySet();
                    for (String s : key) {
                        somma += toAdd.get(s);

                    }

                    temp.setSommaLivelloSkills(somma);


                }
                popolazione.add(temp);
            }
        }
        return popolazione.size() > 0 ? popolazione : null;
    }


    public static ArrayList<DipendenteRefactor> onePointCrossover(ArrayList<DipendenteRefactor> individuo1, ArrayList<DipendenteRefactor> individuo2) {
        for (int i = 0; i < individuo1.size(); i++) {
            DipendenteRefactor temp = individuo1.get(i);
            if (new Random().nextDouble() < prob_crossover) {
                individuo1.remove(i);
                individuo1.add(i, individuo2.get(i));
                individuo2.remove(i);
                individuo2.add(i, temp);
            }
        }
        ArrayList<DipendenteRefactor> toReturn = new ArrayList<>();
        toReturn.addAll(individuo1);
        toReturn.addAll(individuo2);
        return toReturn;
    }

    public static ArrayList<DipendenteRefactor> randomMutation(ArrayList<DipendenteRefactor> individuo, ArrayList<String> skillsRichieste, ArrayList<DipendenteRefactor> data) {
        ArrayList<DipendenteRefactor> newIndividuo = new ArrayList<>();
        newIndividuo = individuo;
        ArrayList<DipendenteRefactor> dipendenteRefactors = new ArrayList<>();
        for (int i = 0; i < individuo.size(); i++) {
            DipendenteRefactor temp = null;
            if (new Random().nextDouble() < prob_mutation) {
                for (int j = 0; j < skillsRichieste.size(); j++) {
                    //String skill = skillsRichieste.get[j];
                    for (int z = 0; z < 3; z++) {
                        //if(data.get(i).getSkills().constainsKey(skill))
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
        /*for(DipendenteRefactor dipendenteRefactor : dipendenteRefactors){
            newIndividuo.getDipendenti().add(dipendenteRefactor);
        }*/

        newIndividuo=dipendenteRefactors;
        return newIndividuo;
    }

    public static ArrayList<DipendenteRefactor> evaluate(ArrayList<DipendenteRefactor> popolazione) {
        ArrayList<DipendenteRefactor> toReturn = new ArrayList<>();
        ArrayList<DipendenteRefactor> popolazioneOrdinata = ordina(popolazione);
        for (int i = 0; i < teamSize; i++) {
            for (DipendenteRefactor dipendente : popolazioneOrdinata) {
                if (dipendente.getSommaLivelloSkills()<=scoreSkillDefault&&dipendente.getSommaLivelloSkills()>=0){
                    toReturn.add(dipendente);
                }
            }
        }
        return toReturn;
    }
    public static ArrayList<DipendenteRefactor> ordina(ArrayList<DipendenteRefactor> daOrdinare){
        ArrayList<DipendenteRefactor> toReturn = daOrdinare;
        toReturn.sort(Comparator.comparing(DipendenteRefactor::getSommaLivelloSkills).reversed());
        return toReturn;
    }

    public static ArrayList<DipendenteRefactor> elitism(ArrayList<DipendenteRefactor> popolazione, ArrayList<DipendenteRefactor> offSpring){
        ArrayList<DipendenteRefactor> pop1 = evaluate(popolazione);
        ArrayList<DipendenteRefactor> pop2 = evaluate(offSpring);
        ArrayList<DipendenteRefactor> newPop = new ArrayList<>();
        int compElite = (int)(popolazione.size()*elitism_size);
        System.out.println(compElite);
        //ci mancano due sort
        for(int i=0;i<compElite;i++){
            newPop.add(popolazione.get(i));
        }
        for (int i=compElite;i<offSpring.size()-compElite;i++){
            newPop.add(offSpring.get(i));
        }

        return newPop;
    }

    public static void evolve(ArrayList<DipendenteRefactor> popolazione, ArrayList<String> skillsRichieste){
        int numIter = 50;

        for(int i=0;i<numIter;i++){
            ArrayList<DipendenteRefactor> genitori = new ArrayList<>();
            for(int j=0;j<50-1;j=j+4){
                ArrayList<DipendenteRefactor> ind1 = new ArrayList<>();
                ArrayList<DipendenteRefactor> ind2 = new ArrayList<>();
                ind1.add(popolazione.get(j));
                ind1.add(popolazione.get(j+1));
                ind2.add(popolazione.get(j+2));
                ind2.add(popolazione.get(j+3));
                genitori = onePointCrossover(ind1,ind2);
            }
            ArrayList<DipendenteRefactor> offSpring = randomMutation(genitori,skillsRichieste,popolazione);
            ArrayList<DipendenteRefactor> pop = elitism(popolazione,offSpring);
            ArrayList<DipendenteRefactor> best = evaluate(pop);

            if(i%4==0) {
                for (DipendenteRefactor dip : best) {
                    System.out.println("ID: " + dip.getId());
                }
            }

        }

    }



    public static void main(String[] args) throws IOException {
        ArrayList<DipendenteRefactor> data = fromDataSet();
        if (data != null) {
            ArrayList<String> skillsRichieste = new ArrayList<>();
            skillsRichieste.add("Python");
            skillsRichieste.add("React");
            skillsRichieste.add("SQL");
            ArrayList<DipendenteRefactor> popolazione = initPopolazione(data, 500, skillsRichieste);
            if (popolazione != null) {
                System.out.println("Dimensione popolazione: " + popolazione.size());
            } else {
                System.out.println("Errore retrieve form dataSet");
            }

            /*
            ArrayList<DipendenteRefactor> stampa = ordina(popolazione);
            for(DipendenteRefactor dip : stampa){
                System.out.println("ID: "+dip.getId()+" Somma: "+dip.getSommaLivelloSkills());
            }*/
            evolve(popolazione,skillsRichieste);
        }
    }
}
