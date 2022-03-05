package it.unisa.agency_formation.FIA;

import it.unisa.agency_formation.team.domain.Team;
import org.jfree.ui.RefineryUtilities;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;


public class iTeam {
    private static final double prob_mutation = 0.5;
    private static final double prob_crossover = 0.8;
    private static final double elitism_size = 0.6;
    private static final double avg_Skills = 5.0;
    private static final DecimalFormat df = new DecimalFormat("###.##");

    public static ArrayList<TeamRefactor> ordina(ArrayList<TeamRefactor> popolazione) {
        ArrayList<TeamRefactor> toReturn = popolazione;
        toReturn.sort(Comparator.comparing(TeamRefactor::getValoreTeam).reversed());
        return toReturn;
    }

    /************************************************************/
    //CROSSOVER
    public static ArrayList<TeamRefactor> crossover(TeamRefactor team1, TeamRefactor team2) {
        ArrayList<TeamRefactor> toReturn = new ArrayList<>();
        double prob = new Random().nextDouble();
        if (prob < prob_crossover) {
            TeamRefactor temp1 = new TeamRefactor();
            TeamRefactor temp2 = new TeamRefactor();
            ArrayList<DipendenteRefactor> dip1 = new ArrayList<>();
            ArrayList<DipendenteRefactor> dip2 = new ArrayList<>();
            int pos = new Random().nextInt(team1.getDipendenti().size());
            for (int i = 0; i < pos; i++) {
                if (!dip1.contains(team1.getDipendenti().get(i))) {
                    dip1.add(team1.getDipendenti().get(i));
                }
                if (!dip2.contains(team2.getDipendenti().get(i))) {
                    dip2.add(team2.getDipendenti().get(i));
                }
            }
            for (int i = pos; i < team1.getDipendenti().size(); i++) {
                if (!dip1.contains(team2.getDipendenti().get(i))) {
                    dip1.add(team2.getDipendenti().get(i));
                }
                if (!dip2.contains(team1.getDipendenti().get(i))) {
                    dip2.add(team1.getDipendenti().get(i));
                }
            }
            temp1.setDipendenti(dip1);
            temp2.setDipendenti(dip2);
            toReturn.add(temp1);
            toReturn.add(temp2);

        } else {
            toReturn.add(team1);
            toReturn.add(team2);
        }
        return toReturn;
    }

    /************************************************************/

    public static TeamRefactor mutation(TeamRefactor team, ArrayList<TeamRefactor> popolazione) {
        TeamRefactor newTeam = new TeamRefactor();
        newTeam.setDipendenti(team.getDipendenti());
        for (int i = 0; i < team.getDipendenti().size() - 1; i++) {
            double prob = new Random().nextDouble();
            TeamRefactor tmp = new TeamRefactor();
            ArrayList<DipendenteRefactor> dipTemp = new ArrayList<>();
            if (prob < prob_mutation) {
                int pos = new Random().nextInt(popolazione.size());
                tmp = popolazione.get(pos);
                dipTemp = tmp.getDipendenti();
                ArrayList<DipendenteRefactor> dipInNewTeam = newTeam.getDipendenti();
                if (!dipInNewTeam.contains(dipTemp.get(i))) {
                    dipInNewTeam.remove(i);
                    dipInNewTeam.add(i, dipTemp.get(i));
                }
                newTeam.setDipendenti(dipInNewTeam);
            }
        }
        return newTeam;
    }

    /************************************************************/

    public static ArrayList<TeamRefactor> elitism(ArrayList<TeamRefactor> popolazione, ArrayList<TeamRefactor> off, ArrayList<String> skills) {
        ArrayList<TeamRefactor> population = ordina(popolazione);
        ArrayList<TeamRefactor> offSpring = ordina(off);
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

    /************************************************************/

    public static ArrayList<TeamRefactor> evaluate(ArrayList<TeamRefactor> population, ArrayList<String> skills) {
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

    /************************************************************/

    public static TeamRefactor evolve(ArrayList<TeamRefactor> population, ArrayList<String> skillsRichieste) {
        int numIterazioni = 100;
        double bestScore = 0.0;
        ArrayList<TeamRefactor> pool;
        ArrayList<TeamRefactor> pop = population;
        ArrayList<Double> scoreTeam = new ArrayList<>();

        ArrayList<Integer> gen = new ArrayList<>();
        int index = -1;

        ArrayList<TeamRefactor> teamBestTemp = new ArrayList<>();
        System.out.println("Vediamo cosa posso fare...");

        for (int i = 0; i < numIterazioni; i++) {
            TeamRefactor teamBest = null;
            char[] animationChars = new char[]{'|', '/', '-', '\\'};
            System.out.print("Processing: " + i + "% " + animationChars[i % 4] + "\r");
            pop = ordina(pop);
            pool = pop;
            //crossover
            ArrayList<TeamRefactor> parents = new ArrayList<>();
            for (int j = 0; j < pool.size() - 2; j = j + 2) {
                TeamRefactor team1 = pool.get(j);
                TeamRefactor team2 = pool.get(j + 1);
                parents.add(crossover(team1, team2).get(0));
                parents.add(crossover(team1, team2).get(1));
            }

            //mutation
            ArrayList<TeamRefactor> offSpring = new ArrayList<>();
            for (TeamRefactor team : parents) {
                offSpring.add(mutation(team, pop));
            }

            //elitism and evaluate
            pop = elitism(pop, offSpring, skillsRichieste);
            ArrayList<TeamRefactor> toEvaluate = evaluate(pop, skillsRichieste);
            for (int j = 0; j < toEvaluate.size(); j++) {
                if (toEvaluate.get(j).getValoreTeam() >= bestScore) {
                    teamBest = toEvaluate.get(j);
                    bestScore = toEvaluate.get(j).getValoreTeam();

                }

            }
            if (teamBest != null) {
                index++;
                teamBestTemp.add(teamBest);
                gen.add(i);
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
                return teamBestTemp.get(index);
            }
        }

        LinearChart chart = new LinearChart(
                "iTeam",
                "Team valutati");
        chart.createDataset(scoreTeam, gen);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
        return teamBestTemp.get(index);
    }
}