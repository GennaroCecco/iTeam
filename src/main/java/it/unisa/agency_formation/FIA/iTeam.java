package it.unisa.agency_formation.FIA;

import org.jfree.ui.RefineryUtilities;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class iTeam {

    private static final double avg_Skills = 5.0;
    private static final int numberOfMemberForTournament = 3;
    private static final DecimalFormat df = new DecimalFormat("###.##");

    /* L'evaluate sceglie i migliori individui della generazione attuale, ritornando un insieme
    di invidui la cui differenza con avg_Skills sia più vicina a 0 */
    public static ArrayList<TeamRefactor> evaluate
            (ArrayList<TeamRefactor> population, ArrayList<String> skills) {
        ArrayList<TeamRefactor> toReturn = new ArrayList<>();
        double best = 5.0;
        for (TeamRefactor team : population) {
            team.calcolaFitness(skills);
            if ((avg_Skills - team.getValoreTeam()) < best) {
                toReturn.add(team);
                best = team.getValoreTeam();
            }
        }
        return toReturn;
    }

    /* L'evolve contiene tutti i passi che l'algoritmo deve seguire ciclicamente
    (ad ogni ciclo corrisponde una generazione) */
    public static TeamRefactor evolve(ArrayList<TeamRefactor> population, ArrayList<String> skillsRichieste) {
        int numIterazioni = 50;
        double bestScore = 0.0;

        ArrayList<Double> scoreTeam = new ArrayList<>();
        ArrayList<Integer> gen = new ArrayList<>();
        ArrayList<TeamRefactor> newPool = population;

        System.out.println("Vediamo cosa posso fare...");
        TeamRefactor teamBest = new TeamRefactor();
        for (int i = 0; i < numIterazioni; i++) {
            ArrayList<TeamRefactor> pool;
            char[] animationChars = new char[]{'|', '/', '-', '\\'};
            System.out.print("Processing: " + i*2 + "% " + animationChars[i % 4] + "\r");
            pool = newPool;
            newPool = new ArrayList<>();
            int tournamentSize = new Random().nextInt(population.size());

            ArrayList<TeamRefactor> offSpring = new ArrayList<>();
            ArrayList<TeamRefactor> parents = Selection.tournamentSelection(pool, skillsRichieste,tournamentSize,
                   numberOfMemberForTournament);

            for (int j = 0; j < parents.size()-1; j = j + 2) {
                TeamRefactor team1 = parents.get(j);
                TeamRefactor team2 = parents.get(j+1);
                ArrayList<TeamRefactor> crossedTeams = Crossover.onePointCrossover(team1, team2);
                TeamRefactor crossedTeam1 = crossedTeams.get(0);
                TeamRefactor crossedTeam2 = crossedTeams.get(1);
                offSpring.add(crossedTeam1);
                offSpring.add(crossedTeam2);
            }
            for (int j = 0; j < offSpring.size(); j++) {
                newPool.add(Mutation.mutation(offSpring.get(j), pool));
            }
            ArrayList<TeamRefactor> elitismo = new ArrayList<>();
            //elitismo = Elitism.elitism(newPool,offSpring,skillsRichieste);
            //ArrayList<TeamRefactor> evaluatedPop = evaluate(elitismo, skillsRichieste);
            ArrayList<TeamRefactor> evaluatedPop = evaluate(newPool, skillsRichieste);
            for (int j = 0; j < evaluatedPop.size(); j++) {
                evaluatedPop.get(j).calcolaFitness(skillsRichieste);
                if (evaluatedPop.get(j).getValoreTeam() >= bestScore) {
                    teamBest = evaluatedPop.get(j);
                    bestScore = evaluatedPop.get(j).getValoreTeam();
                }
            }
            //newPool = elitismo;
            if (i % 5 == 0 || bestScore == avg_Skills) {
                gen.add(i);
                teamBest.calcolaFitness(skillsRichieste);
                scoreTeam.add(teamBest.getValoreTeam());
                for (DipendenteRefactor dip : teamBest.getDipendenti()) {
                    System.out.println("Generazione: " + i + " ID: " + dip.getId() + " Nome: " + dip.getNome() + " Cognome: " + dip.getCognome());
                }
                System.out.println("Valutazione: " + df.format(teamBest.getValoreTeam()));
            }
            if (bestScore == avg_Skills) {
                LinearChart chart = new LinearChart(
                        "iTeam",
                        "Team valutati");
                chart.createDataset(scoreTeam, gen);
                chart.pack();
                RefineryUtilities.centerFrameOnScreen(chart);
                chart.setVisible(true);
                return teamBest;
            }
        }
        LinearChart chart = new LinearChart(
                "iTeam",
                "Team valutati");
        chart.createDataset(scoreTeam, gen);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
        return teamBest;

    }


    public static void main(String[] args) throws IOException {
        ArrayList<DipendenteRefactor> data = DataFromDataset.fromDataSet();
        ArrayList<String> skillsRichieste = new ArrayList<>();
        skillsRichieste.add("C++");
        skillsRichieste.add("CSS");
        skillsRichieste.add("Python");
        ArrayList<TeamRefactor> population = Population.initPopulation(1000000, data, skillsRichieste);
        TeamRefactor team = null;
        System.out.println("Dim pop: " + population.size());
        team = evolve(population, skillsRichieste);
        if (team != null) {
            System.out.println("Team Migliore");
            for (DipendenteRefactor dip : team.getDipendenti()) {
                System.out.println("Dipendente: " + " ID: " + dip.getId() + " Nome: " + dip.getNome() + " Cognome: " + dip.getCognome());
            }
            System.out.println("Valutazione: " + df.format(team.getValoreTeam()));
            System.out.println("-----------------");
        } else {
            System.out.println("Scusami ho avuto dei problemi, potresti ri-eseguirmi");
        }
    }

}