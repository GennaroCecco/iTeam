package it.unisa.agency_formation.FIA;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

public class Selection {
    private static final int tournament_size = 2;
    private static final double numberOfIndividuals = new Random().nextDouble();
    public Selection() {
    }


    public static TeamRefactor tournamentSelection(ArrayList<TeamRefactor> popolazione,
                                                   ArrayList<String> skills) {
        TeamRefactor best = null;
        for(int i=0;i<tournament_size;i++){
            TeamRefactor ind = popolazione.get(new Random().nextInt((int)(popolazione.size()*numberOfIndividuals)));
            ind.calcolaFitness(skills);
            if (best == null || ind.getValoreTeam() > best.getValoreTeam()) {
                best = ind;
            }
        }
        return best;

    }

    public static HashMap<TeamRefactor, Double> setProbabilityForRoulette(ArrayList<TeamRefactor> popolazione, ArrayList<String> skills) {
        HashMap<TeamRefactor, Double> probability = new HashMap<>();
        double totalSum = 0.0;
        for (int i = 0; i < popolazione.size(); i++) {
            popolazione.get(i).calcolaFitness(skills);
            totalSum += popolazione.get(i).getValoreTeam();
        }
        for (int i = 0; i < popolazione.size(); i++) {
            popolazione.get(i).calcolaFitness(skills);
            probability.put(popolazione.get(i), popolazione.get(i).getValoreTeam() / totalSum);
        }
        return probability;
    }


    public static TeamRefactor rouletteWheel(ArrayList<TeamRefactor> popolazione, ArrayList<String> skills) {
        double random = new Random().nextDouble();
        HashMap<TeamRefactor, Double> population = new HashMap<>();
        population = setProbabilityForRoulette(popolazione, skills);
        double sum = 0;
        for (int i = 0; i < popolazione.size(); i++) {
            sum += population.get(popolazione.get(i));
            if (random < sum) {
                return popolazione.get(i);
            }
        }
        return null;
    }


    public static HashMap<TeamRefactor, Double> getProbabilityForRank(ArrayList<TeamRefactor> popolazione, ArrayList<String> skills) {
        popolazione.sort(Comparator.comparing(TeamRefactor::getValoreTeam).reversed());
        HashMap<TeamRefactor, Double> probabilities = new HashMap<>();
        for (int i = 0; i < popolazione.size(); i++) {
            double prob = i / (popolazione.size() * (popolazione.size() - 1));
            probabilities.put(popolazione.get(i), prob);
        }
        return probabilities;
    }

    public static ArrayList<TeamRefactor> rankSelection(ArrayList<TeamRefactor> popolazione, ArrayList<String> skills) {
        double sum = 0.0;
        HashMap<TeamRefactor, Double> probabilites = new HashMap<>();
        ArrayList<TeamRefactor> selectedPop = new ArrayList<>();
        probabilites = getProbabilityForRank(popolazione, skills);
        for (int i = 0; i < popolazione.size(); i++) {
            popolazione.get(i).calcolaFitness(skills);
            sum += popolazione.get(i).getValoreTeam();
        }
        for (int i = 0; i < popolazione.size(); i++) {
            Random r = new Random();
            double randomValue = 0.0 + (sum - 0.0) * r.nextDouble();
            for (int j = 0; j < popolazione.size(); j++) {
                if (probabilites.get(popolazione.get(j)) < randomValue) {
                    selectedPop.add(popolazione.get(j));
                    break;
                }
            }
        }
        return selectedPop;
    }


    public static ArrayList<TeamRefactor> truncationSelection(ArrayList<TeamRefactor> popolazione, ArrayList<String> skills){
        popolazione.sort(Comparator.comparing(TeamRefactor::getValoreTeam).reversed());
        ArrayList<TeamRefactor> selectedPop = new ArrayList<>();
        int portion = (int) (popolazione.size()*0.5);
        for (int i=0; i< portion; i++){
            selectedPop.add(popolazione.get(i));
        }

        return selectedPop;
    }
}
