package it.unisa.agency_formation.FIA;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.security.DigestException;
import java.util.*;

public class TestiTeam {
    private static final double prob_mutation = 0.5;
    private static final double prob_crossover = 0.8;
    private static final double elitism_size = 0.6;
    private static final int numPop = 500;


    //KEEP FIRST AND SECOND BEST

    /************************************************************/
    public static TeamRefactor getFirstBest(ArrayList<TeamRefactor> popolazione, ArrayList<String> skillsRichieste) {
        /*double maxFit = Double.MIN_VALUE;
        int maxFitindex = 0;
        for(int i=0;i<popolazione.size();i++){
            if(maxFit<popolazione.get(i).calcolaFitness(skillsRichieste)){
                maxFit = popolazione.get(i).calcolaFitness(skillsRichieste);
                maxFitindex = i;
            }
        }*/
        return popolazione.get(0);
    }

    public static TeamRefactor getSecondBest(ArrayList<TeamRefactor> popolazione, ArrayList<String> skillsRichieste) {
        /*int maxFit1 = 0;
        int maxFit2 = 0;
        for(int i=0;i<popolazione.size();i++){
            if(popolazione.get(i).calcolaFitness(skillsRichieste)>popolazione.get(maxFit1).calcolaFitness(skillsRichieste)){
                maxFit2 = maxFit1;
                maxFit1 = i;
            }else if(popolazione.get(i).calcolaFitness(skillsRichieste)>popolazione.get(maxFit2).calcolaFitness(skillsRichieste)){
                maxFit2 = i;
            }
        }*/
        return popolazione.get(1);
    }

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
                dip1.add(team1.getDipendenti().get(i));
            }
            for (int i = pos; i < team1.getDipendenti().size(); i++) {
                dip1.add(team2.getDipendenti().get(i));
            }
            temp1.setDipendenti(dip1);
            for (int i = 0; i < pos; i++) {
                dip2.add(team2.getDipendenti().get(i));
            }
            for (int i = pos; i < team2.getDipendenti().size(); i++) {
                dip2.add(team1.getDipendenti().get(i));
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
            if (prob < prob_mutation) {
                TeamRefactor tmp = new TeamRefactor();
                int pos = new Random().nextInt(popolazione.size());
                tmp = popolazione.get(pos);
                newTeam.getDipendenti().remove(i);
                newTeam.getDipendenti().add(tmp.getDipendenti().get(i));
            }
        }
        return newTeam;
    }

    public static ArrayList<TeamRefactor> elitism(ArrayList<TeamRefactor>popolazione, ArrayList<TeamRefactor> off, ArrayList<String> skills){
        ArrayList<TeamRefactor> population = ordina(popolazione,skills);
        ArrayList<TeamRefactor> offSpring = ordina(off,skills);

        int compElite =(int)(population.size()*elitism_size);
        ArrayList<TeamRefactor> newPop = new ArrayList<>();
        int secondIndex = population.size()-compElite;
        for(int i=0;i<compElite;i++){
            newPop.add(population.get(i));
        }
        for(int i=0;i<secondIndex;i++){
            newPop.add(offSpring.get(i));
        }
        return newPop;
    }



    public static void evolve(ArrayList<TeamRefactor> population, ArrayList<String> skillsRichieste) {
        int numIterazioni = 1000;
        double bestScore = 16.0;
        for (int i = 0; i < numIterazioni; i++) {
            System.out.println("Iterazione numero: "+i);
            ArrayList<TeamRefactor> pool = population;

            //crossover
            ArrayList<TeamRefactor> parents = new ArrayList<>();
            for (int j = 0; j < (numPop-1)/2; j++) {
                population = ordina(population, skillsRichieste);
                TeamRefactor team1 = getFirstBest(pool, skillsRichieste);
                TeamRefactor team2 = getSecondBest(pool, skillsRichieste);
                parents.add(crossover(team1, team2).get(0));
                parents.add(crossover(team1, team2).get(1));
            }

            //mutation
            ArrayList<TeamRefactor> offSpring = new ArrayList<>();
            for (TeamRefactor team : parents) {
                offSpring.add(mutation(team, population));
            }
            population = elitism(population,offSpring,skillsRichieste);
            TeamRefactor teamRefactor = null;
            for (int j = 0; j < population.size(); j++) {
                if (population.get(j).calcolaFitness(skillsRichieste) < bestScore) {
                    teamRefactor = population.get(j);
                    bestScore = population.get(j).calcolaFitness(skillsRichieste);
                }
            }
            if (teamRefactor != null) {
                for (DipendenteRefactor dip : teamRefactor.getDipendenti()) {
                    System.out.println("Genarazione: " + i + " ID: " + dip.getId() + " Nome: " + dip.getNome() + " Congome: " + dip.getCognome());
                }
                System.out.println("Valutazione"+teamRefactor.getValoreTeam());
                System.out.println("-----------------");

            }

        }
    }


    //MAIN

    /*****************************************************************************/
    public static void main(String[] args) throws IOException {
        ArrayList<DipendenteRefactor> data = DataFromDataset.fromDataSet();
        ArrayList<String> skillsRichieste = new ArrayList<>();
        skillsRichieste.add("Java");
        skillsRichieste.add("Python");
        skillsRichieste.add("HTML");
        ArrayList<TeamRefactor> population = Population.initPopulation(100000, data, skillsRichieste);
        evolve(population, skillsRichieste);

    }
}
