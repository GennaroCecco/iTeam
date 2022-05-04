package it.unisa.agency_formation.FIA;

import org.jfree.ui.RefineryUtilities;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class iTeam {

    private static final double prob_mutation = 0.6;
    private static final double prob_crossover = 0.8;
    private static final double avg_Skills = 5.0;
    private static final int tournament_size = 2;
    private static final DecimalFormat df = new DecimalFormat("###.##");


    public static TeamRefactor tournament_selection(ArrayList<TeamRefactor> popolazione, ArrayList<String> skills) {
        TeamRefactor best = null;
        for (int i = 0; i < tournament_size; i++) {
            TeamRefactor ind = popolazione.get(new Random().nextInt(popolazione.size()));
            ind.calcolaFitness(skills);
            //if(i>0 && best!=null){best.calcolaFitness(skills);}
            if (best == null || ind.getValoreTeam() > best.getValoreTeam()) {
                best = ind;
            }
        }
        return best;
    }

    public static TeamRefactor getBest(ArrayList<TeamRefactor> generazione, ArrayList<String> skills) {
        TeamRefactor best = null;
        for (TeamRefactor team : generazione) {
            team.calcolaFitness(skills);
            if (best == null || team.getValoreTeam() > best.getValoreTeam()) {
                best = team;
            }
        }
        return best;
    }

    public static TeamRefactor getSecondBest(ArrayList<TeamRefactor> generazione, ArrayList<String> skills) {
        TeamRefactor best = null;
        TeamRefactor secondBest = null;
        for (TeamRefactor team : generazione) {
            team.calcolaFitness(skills);
            if (best == null || team.getValoreTeam() > best.getValoreTeam()) {
                secondBest = best;
                best = team;
            }
        }
        return secondBest;
    }

    public static ArrayList<TeamRefactor> crossover(TeamRefactor team1, TeamRefactor team2) {
        TeamRefactor crossedTeam1 = new TeamRefactor();
        TeamRefactor crossedTeam2 = new TeamRefactor();
        ArrayList<TeamRefactor> toReturn = new ArrayList<>();
        ArrayList<DipendenteRefactor> son1 = new ArrayList<DipendenteRefactor>();
        ArrayList<DipendenteRefactor> son2 = new ArrayList<DipendenteRefactor>();

        ArrayList<DipendenteRefactor> dips1 = new ArrayList<DipendenteRefactor>();
        ArrayList<DipendenteRefactor> dips2 = new ArrayList<DipendenteRefactor>();
        //double prob = new Random().nextDouble();
        dips1.addAll(team1.getDipendenti());
        dips2.addAll(team2.getDipendenti());

        if (new Random().nextDouble() < prob_crossover) {
            int randomPointCrossover = new Random().nextInt(dips1.size());
            for(int i=0;i<randomPointCrossover;i++){
                son1.add(dips1.get(i));
                son2.add(dips2.get(i));
            }
            for(int i=randomPointCrossover;i<dips2.size();i++){
                son1.add(dips2.get(i));
                son2.add(dips1.get(i));
            }
            crossedTeam1.setDipendenti(son1);
            crossedTeam2.setDipendenti(son2);
            toReturn.add(crossedTeam1);
            toReturn.add(crossedTeam2);
        }else{
            toReturn.add(team1);
            toReturn.add(team2);
        }

        return toReturn;
    }

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


    public static TeamRefactor evolve(ArrayList<TeamRefactor> population, ArrayList<String> skillsRichieste) {
        int numIterazioni = 100;
        double bestScore = 0.0;
        ArrayList<TeamRefactor> pool;

        ArrayList<Double> scoreTeam = new ArrayList<>();
        ArrayList<Integer> gen = new ArrayList<>();
        ArrayList<TeamRefactor> newPool = population;

        System.out.println("Vediamo cosa posso fare...");
        TeamRefactor teamBest = null;
        for (int i = 0; i < numIterazioni; i++) {
            char[] animationChars = new char[]{'|', '/', '-', '\\'};
            System.out.print("Processing: " + i + "% " + animationChars[i % 4] + "\r");
            pool = newPool;
            newPool = new ArrayList<>();
            ArrayList<TeamRefactor> offSpring = new ArrayList<>();
            for (int j = 0; j < pool.size(); j=j+2) {
                TeamRefactor team1 = tournament_selection(pool, skillsRichieste);
                TeamRefactor team2 = tournament_selection(pool, skillsRichieste);
                TeamRefactor crossedTeam1 = crossover(team1, team2).get(0);
                TeamRefactor crossedTeam2 = crossover(team1, team2).get(1);
                offSpring.add(crossedTeam1);
                offSpring.add(crossedTeam2);
            }

            for(int j=0;j<pool.size();j++){
                newPool.add(mutation(offSpring.get(j),pool));
            }

            ArrayList<TeamRefactor> evaluatedPop = evaluate(newPool, skillsRichieste);
            for (int j = 0; j < evaluatedPop.size(); j++) {
                evaluatedPop.get(j).calcolaFitness(skillsRichieste);
                if (evaluatedPop.get(j).getValoreTeam() > bestScore) {
                    teamBest = evaluatedPop.get(j);
                    bestScore = evaluatedPop.get(j).getValoreTeam();
                }
            }
            if (i%5==0 || bestScore==avg_Skills) {
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
