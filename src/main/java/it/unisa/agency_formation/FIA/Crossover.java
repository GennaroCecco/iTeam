package it.unisa.agency_formation.FIA;

import java.util.ArrayList;
import java.util.Random;

public class Crossover {
    public Crossover() {
    }

    private static final double prob_crossover = 0.8;
    private static final double prob_uniform = 0.5;

    /*Il one point crossover sceglie casualmente un punto dell'individuo da cui effettuare lo scambio dei geni. */
    public static ArrayList<TeamRefactor> onePointCrossover(TeamRefactor team1, TeamRefactor team2) {
        TeamRefactor crossedTeam1 = new TeamRefactor();
        TeamRefactor crossedTeam2 = new TeamRefactor();
        ArrayList<TeamRefactor> toReturn = new ArrayList<>();
        ArrayList<DipendenteRefactor> son1 = new ArrayList<DipendenteRefactor>();
        ArrayList<DipendenteRefactor> son2 = new ArrayList<DipendenteRefactor>();

        ArrayList<DipendenteRefactor> dips1 = new ArrayList<DipendenteRefactor>();
        ArrayList<DipendenteRefactor> dips2 = new ArrayList<DipendenteRefactor>();
        dips1.addAll(team1.getDipendenti());
        dips2.addAll(team2.getDipendenti());

        if (new Random().nextDouble() < prob_crossover) {
            int randomOnePointCrossover = new Random().nextInt(dips1.size());
            for (int i = 0; i < randomOnePointCrossover; i++) {
                son1.add(dips1.get(i));
                son2.add(dips2.get(i));
            }
            for (int i = randomOnePointCrossover; i < dips2.size(); i++) {
                son1.add(dips2.get(i));
                son2.add(dips1.get(i));
            }
            crossedTeam1.setDipendenti(son1);
            crossedTeam2.setDipendenti(son2);
            toReturn.add(crossedTeam1);
            toReturn.add(crossedTeam2);
        } else {
            toReturn.add(team1);
            toReturn.add(team2);
        }
        return toReturn;
    }

    /* Il two point crossover sceglie casualmente due punti dell'individuo e scambia i geni compresi fra essi */
    public static ArrayList<TeamRefactor> twoPointCrossover(TeamRefactor team1, TeamRefactor team2) {
        TeamRefactor crossedTeam1 = new TeamRefactor();
        TeamRefactor crossedTeam2 = new TeamRefactor();
        ArrayList<TeamRefactor> toReturn = new ArrayList<>();
        ArrayList<DipendenteRefactor> son1 = new ArrayList<DipendenteRefactor>();
        ArrayList<DipendenteRefactor> son2 = new ArrayList<DipendenteRefactor>();

        ArrayList<DipendenteRefactor> dips1 = new ArrayList<DipendenteRefactor>();
        ArrayList<DipendenteRefactor> dips2 = new ArrayList<DipendenteRefactor>();
        dips1.addAll(team1.getDipendenti());
        dips2.addAll(team2.getDipendenti());

        if (new Random().nextDouble() < prob_crossover) {
            int randomFirstPoint = new Random().nextInt(dips1.size());
            int randomSecondPoint = new Random().nextInt(dips1.size());

            if (randomFirstPoint == team1.getDipendenti().size() - 1) {
                randomSecondPoint = team1.getDipendenti().size();
            }
            while (randomFirstPoint >= randomSecondPoint) {
                randomSecondPoint = new Random().nextInt(dips1.size());
            }

            for (int i = 0; i < randomFirstPoint; i++) {
                son1.add(dips1.get(i));
                son2.add(dips2.get(i));
            }
            for (int j = randomFirstPoint; j < randomSecondPoint; j++) {
                son1.add(dips2.get(j));
                son2.add(dips1.get(j));
            }
            for (int k = randomSecondPoint; k < team1.getDipendenti().size(); k++) {
                son1.add(dips1.get(k));
                son2.add(dips2.get(k));
            }
            crossedTeam1.setDipendenti(son1);
            crossedTeam2.setDipendenti(son2);
            toReturn.add(crossedTeam1);
            toReturn.add(crossedTeam2);
        } else {
            toReturn.add(team1);
            toReturn.add(team2);
        }
        return toReturn;
    }

    /* L'uniform crossover scambia i geni calcolandoli con la prob_uniform */
    public static ArrayList<TeamRefactor> uniformCrossover(TeamRefactor team1, TeamRefactor team2) {
        TeamRefactor crossedTeam1 = new TeamRefactor();
        TeamRefactor crossedTeam2 = new TeamRefactor();
        ArrayList<TeamRefactor> toReturn = new ArrayList<>();
        ArrayList<DipendenteRefactor> son1 = new ArrayList<DipendenteRefactor>();
        ArrayList<DipendenteRefactor> son2 = new ArrayList<DipendenteRefactor>();

        ArrayList<DipendenteRefactor> dips1 = new ArrayList<DipendenteRefactor>();
        ArrayList<DipendenteRefactor> dips2 = new ArrayList<DipendenteRefactor>();
        dips1.addAll(team1.getDipendenti());
        dips2.addAll(team2.getDipendenti());

        if (new Random().nextDouble() < prob_crossover) {
            double prob = new Random().nextDouble();
            for (int i = 0; i < dips1.size(); i++) {

                if (prob < prob_uniform) {
                    son1.add(dips2.get(i));
                    son2.add(dips1.get(i));
                } else {
                    son1.add(dips1.get(i));
                    son2.add(dips2.get(i));
                }
            }
            crossedTeam1.setDipendenti(son1);
            crossedTeam2.setDipendenti(son2);
            toReturn.add(crossedTeam1);
            toReturn.add(crossedTeam2);
        } else {
            toReturn.add(team1);
            toReturn.add(team2);
        }
        return toReturn;
    }
}