package it.unisa.agency_formation.FIA;

import java.util.ArrayList;
import java.util.HashMap;

public class Population {

    public static ArrayList<TeamRefactor> initPopulation(int size, ArrayList<DipendenteRefactor> data,
                                                                                            ArrayList<String> skillsRichieste) {
        ArrayList<DipendenteRefactor> popolazione = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            DipendenteRefactor temp = null;
            HashMap<String, Integer> toAdd = new HashMap<>();
            for (int j = 0; j < skillsRichieste.size(); j++) {
                for (int z = 0; z < 3; z++) {
                    if (data.get(i).getSkills().containsKey(skillsRichieste.get(z))) {
                        if (toAdd.size() > 0 && toAdd.containsKey(skillsRichieste.get(z))) {
                            //nothing
                        } else {
                            toAdd.put(skillsRichieste.get(z), data.get(i).getSkills().get(skillsRichieste.get(z)));
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
                popolazione.add(temp);
            }
        }
        ArrayList<TeamRefactor> population = new ArrayList<>();
        int z=0;
        for(int i=4;i<popolazione.size();i=i+4){
            ArrayList<DipendenteRefactor> temp = new ArrayList<>();
            TeamRefactor team = new TeamRefactor();
            for(int j=i-4;j<i;j++){
                temp.add(popolazione.get(z));
                z++;
            }
            team.setDipendenti(temp);
            population.add(team);
        }

        return population.size() > 0 ? population : null;
    }


}
