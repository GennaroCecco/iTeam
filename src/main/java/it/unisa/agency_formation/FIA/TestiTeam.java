package it.unisa.agency_formation.FIA;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class TestiTeam {
    private static final double prob_mutation = 0.5;
    private static final double prob_crossover = 0.8;
    private static final double elitism_size = 0.6;


    public static ArrayList<TeamRefactor> ordina(ArrayList<TeamRefactor> popolazione, ArrayList<String> skills) {
        ArrayList<TeamRefactor> toReturn = popolazione;
        for (int i = 0; i < toReturn.size(); i++) {
            toReturn.get(i).calcolaFitness(skills);
        }
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
                if (dip1.contains(team1.getDipendenti().get(i))) {
                } else {
                    dip1.add(team1.getDipendenti().get(i));
                }
            }
            for (int i = pos; i < team1.getDipendenti().size(); i++) {
                if (dip1.contains(team2.getDipendenti().get(i))) {
                } else {
                    dip1.add(team2.getDipendenti().get(i));
                }
            }
            temp1.setDipendenti(dip1);
            for (int i = 0; i < pos; i++) {
                if (dip2.contains(team2.getDipendenti().get(i))) {
                } else {
                    dip2.add(team2.getDipendenti().get(i));
                }
            }
            for (int i = pos; i < team2.getDipendenti().size(); i++) {
                if (dip2.contains(team1.getDipendenti().get(i))) {

                } else {
                    dip2.add(team1.getDipendenti().get(i));
                }
            }
            temp2.setDipendenti(dip2);
            toReturn.add(temp1);
            toReturn.add(temp2);

        } else {
            toReturn.add(team1);
            toReturn.add(team2);
        }
        return toReturn;
    }

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

    public static ArrayList<TeamRefactor> elitism(ArrayList<TeamRefactor> popolazione, ArrayList<TeamRefactor> off, ArrayList<String> skills) {
        ArrayList<TeamRefactor> population = ordina(popolazione, skills);
        ArrayList<TeamRefactor> offSpring = ordina(off, skills);

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

    public static ArrayList<TeamRefactor> evaluate(ArrayList<TeamRefactor> population, ArrayList<String> skills) {
        ArrayList<TeamRefactor> toReturn = new ArrayList<>();
        //ArrayList<TeamRefactor> population = ordina(popolazione, skills);
        for (TeamRefactor team : population) {
            team.calcolaFitness(skills);
            if (team.getValoreTeam() >= 1.0) {
                toReturn.add(team);
            }
        }
        return toReturn;
    }


    public static TeamRefactor evolve(ArrayList<TeamRefactor> population, ArrayList<String> skillsRichieste) {
        int numIterazioni = 100;
        double bestScore = 0.0;
        int index=-1;
        ArrayList<TeamRefactor> toPrint = new ArrayList<>();
        System.out.println("Vediamo cosa posso fare...");
        int flagStop = 0;
        ArrayList<TeamRefactor> pool = new ArrayList<>();
        ArrayList<TeamRefactor> toSort = new ArrayList<>();
        TeamRefactor teamBest = null;
        for (int i = 0; i < numIterazioni; i++) {

            char[] animationChars = new char[]{'|', '/', '-', '\\'};
            System.out.print("Processing: " + i + "% " + animationChars[i % 4] + "\r");
            pool = population;

            //crossover
            ArrayList<TeamRefactor> parents = new ArrayList<>();
            for (int j = 0; j < population.size()-2; j = j + 2) {
                pool = ordina(population, skillsRichieste);
                TeamRefactor team1 = pool.get(j);
                TeamRefactor team2 = pool.get(j+1);
                parents.add(crossover(team1, team2).get(0));
                parents.add(crossover(team1, team2).get(1));
            }

            //mutation
            ArrayList<TeamRefactor> offSpring = new ArrayList<>();
            for (TeamRefactor team : parents) {
                offSpring.add(mutation(team, population));
            }

            //elitism and evaluate
            population = elitism(population, offSpring, skillsRichieste);
            ArrayList<TeamRefactor> toEvaluate = evaluate(population, skillsRichieste);

            for (int j = 0; j < toEvaluate.size(); j++) {
                toEvaluate.get(j).calcolaFitness(skillsRichieste);
                if (toEvaluate.get(j).getValoreTeam() > bestScore) {
                    teamBest = toEvaluate.get(j);
                    bestScore = teamBest.getValoreTeam();
                }
            }
            toSort.add(teamBest);
            if(i>0) {
                if (teamBest == toSort.get(i-1)) {
                    flagStop++;
                }else{
                    flagStop=0;
                }
            }
            //if(i%5==0) {
                if (teamBest != null) {
                    for (DipendenteRefactor dip : teamBest.getDipendenti()) {
                        System.out.println("Generazione: " + i + " ID: " + dip.getId() + " Nome: " + dip.getNome() + " Cognome: " + dip.getCognome());
                    }
                    System.out.println("Valutazione: " + df.format(teamBest.getValoreTeam()));
                    System.out.println("-----------------");
                }
           // }
            if (flagStop == 3) {
                System.out.println("Processing: Done!");
                toSort.sort(Comparator.comparing(TeamRefactor::getValoreTeam).reversed());
                return toSort.get(0);
            }
        }
        System.out.println("Processing: Done!");
        toSort.sort(Comparator.comparing(TeamRefactor::getValoreTeam).reversed());
        return toSort.get(0);
    }

    //MAIN

    /*****************************************************************************/
    public static void main(String[] args) throws IOException {
        ArrayList<DipendenteRefactor> data = DataFromDataset.fromDataSet();
        ArrayList<String> skillsRichieste = new ArrayList<>();
        skillsRichieste.add("Java");
        skillsRichieste.add("CSS");
        skillsRichieste.add("HTML");
        ArrayList<TeamRefactor> population = Population.initPopulation(10000, data, skillsRichieste);
        System.out.println("Dim Pop: " + population.size());
        TeamRefactor team = evolve(population, skillsRichieste);
        System.out.println("Team Migliore");
        for (DipendenteRefactor dip : team.getDipendenti()) {
            System.out.println("Dipendente: " + " ID: " + dip.getId() + " Nome: " + dip.getNome() + " Cognome: " + dip.getCognome());
        }
        System.out.println("Valutazione: " + df.format(team.getValoreTeam()));
        System.out.println("-----------------");
    }
    private static DecimalFormat df = new DecimalFormat("###.##");
}