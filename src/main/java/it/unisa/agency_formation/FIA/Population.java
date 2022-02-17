package it.unisa.agency_formation.FIA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Population {

    public static ArrayList<TeamRefactor> initPopulation(int size, ArrayList<DipendenteRefactor> data,
                                                         ArrayList<String> skillsRichieste) {
        ArrayList<DipendenteRefactor> popolazione = new ArrayList<>();
        int pos = 0;
        if(size < data.size()) {
            pos = new Random().nextInt(data.size() - size);
        }
        System.out.println("Pos:"+pos);
        for (int i = pos; i < pos+size; i++) {
            DipendenteRefactor temp = null;
            HashMap<String, Integer> toAdd = new HashMap<>();
            ArrayList<String> stringheTrovate = new ArrayList<>();
            for (int j = 0; j < skillsRichieste.size(); j++) {
                for (int z = 0; z < data.get(i).getSkills().size(); z++) {
                    if (data.get(i).getSkills().containsKey(skillsRichieste.get(z))) {
                        if (!stringheTrovate.contains(skillsRichieste.get(z))) {
                            toAdd.put(skillsRichieste.get(z), data.get(i).getSkills().get(skillsRichieste.get(z)));
                            stringheTrovate.add(skillsRichieste.get(j));
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
            if (temp != null && temp.getSkills().size() >= 2) {
                popolazione.add(temp);
            }
        }
        ArrayList<TeamRefactor> population = new ArrayList<>();
        int z = 0;
        for (int i = 4; i < popolazione.size(); i = i + 4) {
            ArrayList<DipendenteRefactor> temp = new ArrayList<>();
            TeamRefactor team = new TeamRefactor();
            for (int j = i - 4; j < i; j++) {
                temp.add(popolazione.get(z));
                z++;
            }
            team.setDipendenti(temp);
            population.add(team);
        }
        return population.size() > 0 ? population : null;
    }


}