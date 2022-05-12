package it.unisa.agency_formation.FIA;

import org.jfree.ui.RefineryUtilities;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class iTeam {

    private static final double prob_mutation = 0.6;
    private static final double avg_Skills = 5.0;
    private static final DecimalFormat df = new DecimalFormat("###.##");


    public static TeamRefactor mutation(TeamRefactor team, ArrayList<TeamRefactor> popolazione) {
        TeamRefactor newTeam = team;
        double prob = new Random().nextDouble();
        TeamRefactor tmp = new TeamRefactor();
        int pos = new Random().nextInt(popolazione.size());
        tmp = popolazione.get(pos);
        if (prob < prob_mutation) {
            int posDip = new Random().nextInt(tmp.getDipendenti().size());
            if (!newTeam.getDipendenti().contains(tmp.getDipendenti().get(posDip))) {
                newTeam.getDipendenti().remove(posDip);
                newTeam.getDipendenti().add(posDip, tmp.getDipendenti().get(posDip));
            }
        }
        return newTeam;
    }

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


    public static TeamRefactor evolve(ArrayList<TeamRefactor> population, ArrayList<String> skillsRichieste) {
        int numIterazioni = 100;
        double bestScore = 0.0;

        ArrayList<Double> scoreTeam = new ArrayList<>();
        ArrayList<Integer> gen = new ArrayList<>();
        ArrayList<TeamRefactor> newPool = population;

        System.out.println("Vediamo cosa posso fare...");
        TeamRefactor teamBest = new TeamRefactor();
        for (int i = 0; i < numIterazioni; i++) {
            ArrayList<TeamRefactor> pool;
            char[] animationChars = new char[]{'|', '/', '-', '\\'};
            System.out.print("Processing: " + i + "% " + animationChars[i % 4] + "\r");
            pool = newPool;
            newPool = new ArrayList<>();
            ArrayList<TeamRefactor> offSpring = new ArrayList<>();

            for (int j = 0; j < pool.size(); j = j + 2) {
                TeamRefactor team1 = Selection.tournamentSelection(pool, skillsRichieste);
                TeamRefactor team2 = Selection.tournamentSelection(pool, skillsRichieste);
                ArrayList<TeamRefactor> crossedTeams = Crossover.uniformCrossover(team1, team2);
                TeamRefactor crossedTeam1 = crossedTeams.get(0);
                TeamRefactor crossedTeam2 = crossedTeams.get(1);
                offSpring.add(crossedTeam1);
                offSpring.add(crossedTeam2);
            }

            for (int j = 0; j < offSpring.size(); j++) {
                newPool.add(mutation(offSpring.get(j), pool));
            }

            ArrayList<TeamRefactor> evaluatedPop = evaluate(newPool, skillsRichieste);
            for (int j = 0; j < evaluatedPop.size(); j++) {
                evaluatedPop.get(j).calcolaFitness(skillsRichieste);
                if (evaluatedPop.get(j).getValoreTeam() >= bestScore) {
                    teamBest = evaluatedPop.get(j);
                    bestScore = evaluatedPop.get(j).getValoreTeam();
                }
            }
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
        skillsRichieste.add("Java");
        skillsRichieste.add("Ruby");
        ArrayList<TeamRefactor> population = Population.initPopulation(data.size(), data, skillsRichieste);
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
            System.out.println("Scusami ho avuto dei problemi, potresti avere l'amabilità di ri-eseguirmi");
        }
    }

}
