package it.unisa.agency_formation.FIA;

import java.util.ArrayList;
import java.util.Comparator;

public class Elitism {
    private static final double elitism_size = 0.1;
    public static ArrayList<TeamRefactor> ordina(ArrayList<TeamRefactor> popolazione, ArrayList<String> skills) {
        ArrayList<TeamRefactor> toReturn = popolazione;
        for (int i = 0; i < toReturn.size(); i++) {
            toReturn.get(i).calcolaFitness(skills);
        }
        toReturn.sort(Comparator.comparing(TeamRefactor::getValoreTeam).reversed());
        return toReturn;
    }

    /*L'elitismo ha lo scopo di salvare una piccola parte di popolazione come "Elité".
     In essa ci saranno gli individui migliori per quella generazione e restituisce in poche generazioni
     una soluzione molto vicina all'ottimo */
    public static ArrayList<TeamRefactor> elitism(ArrayList<TeamRefactor> popolazione, ArrayList<TeamRefactor> off, ArrayList<String> skills) {
        ArrayList<TeamRefactor> population = ordina(popolazione,skills);
        ArrayList<TeamRefactor> offSpring = ordina(off,skills);
        int compElite = (int) (population.size() * elitism_size);
        ArrayList<TeamRefactor> newPop = new ArrayList<>();
        int secondIndex = population.size() - compElite;
        for (int i = 0; i < compElite; i++) {
            newPop.add(population.get(i));
        }
        for (int i = 0; i < secondIndex; i++) {
            newPop.add(offSpring.get(i));
        }
        return newPop;

    }
}
