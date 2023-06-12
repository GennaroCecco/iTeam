package it.unisa.agency_formation.FIA;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

public class Selection {

    private static final double prob_truncation = 0.5;
    private static double totalSum = 0.0;

    public Selection() {
    }

    /*La tournament selection prevede una tournamentsize di team. Per ogni torneo vengono messi a confronto
     numberOfIndividualForTournament e tra questi viene scelto il migliore. Questo tipo di selezione */
    public static ArrayList<TeamRefactor> tournamentSelection(ArrayList<TeamRefactor> popolazione,
                                                              ArrayList<String> skills, int tournamentSize,
                                                              int NumberOfIndividualForTournament) {
        ArrayList<TeamRefactor> toReturn = new ArrayList<>();
        for (int j = 0; j < tournamentSize; j++) {
            TeamRefactor best = null;
            ArrayList<TeamRefactor> pop = new ArrayList<>();
            for (int i = 0; i < NumberOfIndividualForTournament; i++) {
                TeamRefactor ind = popolazione.get(new Random().nextInt(popolazione.size()));
                while (pop.contains(ind)) {
                    ind = popolazione.get(new Random().nextInt(popolazione.size()));
                }
                pop.add(ind);
            }
            //start tournament
            for (int i = 0; i < pop.size(); i++) {
                TeamRefactor ind = pop.get(i);
                ind.calcolaFitness(skills);
                if (best == null || ind.getValoreTeam() > best.getValoreTeam()) {
                    best = ind;
                }
            }
            toReturn.add(best);
        }
        return toReturn;
    }

    public static HashMap<TeamRefactor, Double> setProbabilityForRoulette(ArrayList<TeamRefactor> popolazione, ArrayList<String> skills) {
        HashMap<TeamRefactor, Double> probability = new HashMap<>();

        for (int i = 0; i < popolazione.size(); i++) {
            popolazione.get(i).calcolaFitness(skills);
            totalSum += popolazione.get(i).getValoreTeam();
        }
        for (int i = 0; i < popolazione.size(); i++) {
            popolazione.get(i).calcolaFitness(skills);
            probability.put(popolazione.get(i), totalSum / popolazione.get(i).getValoreTeam());
        }
        return probability;
    }
    /*La roulette wheel assegna ad ogni individuo una parte proporzionata per valore di valutazione
     a confronto con il resto delle valutazioni */
    public static ArrayList<TeamRefactor> rouletteWheel(ArrayList<TeamRefactor> popolazione, ArrayList<String> skills) {
        double random = new Random().nextDouble() * totalSum;

        HashMap<TeamRefactor, Double> population = new HashMap<>();
        population = setProbabilityForRoulette(popolazione, skills);
        ArrayList<TeamRefactor> toReturn = new ArrayList<>();
        double sum = 0;
        for (int i = 0; i < popolazione.size(); i++) {
            sum += population.get(popolazione.get(i));
            if (random < sum) {
                toReturn.add(popolazione.get(i));
            }
        }
        return toReturn;
    }


    public static HashMap<TeamRefactor, Double> getProbabilityForRank(ArrayList<TeamRefactor> popolazione) {
        popolazione.sort(Comparator.comparing(TeamRefactor::getValoreTeam).reversed());
        HashMap<TeamRefactor, Double> probabilities = new HashMap<>();
        for (int i = 0; i < popolazione.size(); i++) {
            double prob = i / (popolazione.size() * (popolazione.size() - 1));
            probabilities.put(popolazione.get(i), prob);
        }
        return probabilities;
    }
    /*La Rank Selection non si basa sul valore di fit ma bensì sulla classificazione degli individui.
     Viene assegnato il grado 1 al peggiore individuo e n al migliore.
     In base al rango ogni individuo avrà la rispettiva probabilità di essere selezionato.*/
    public static ArrayList<TeamRefactor> rankSelection(ArrayList<TeamRefactor> popolazione, ArrayList<String> skills) {
        double sum = 0.0;
        HashMap<TeamRefactor, Double> probabilites = new HashMap<>();
        ArrayList<TeamRefactor> selectedPop = new ArrayList<>();
        probabilites = getProbabilityForRank(popolazione);
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

    /*La truncation selection prevede un restringimento della popolazione del 50% */
    public static ArrayList<TeamRefactor> truncationSelection(ArrayList<TeamRefactor> popolazione) {
        popolazione.sort(Comparator.comparing(TeamRefactor::getValoreTeam).reversed());
        ArrayList<TeamRefactor> selectedPop = new ArrayList<>();
        int portion = (int) (popolazione.size() * prob_truncation);
        for (int i = 0; i < portion; i++) {
            selectedPop.add(popolazione.get(i));
        }

        return selectedPop;
    }
}
