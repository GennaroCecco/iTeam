package it.unisa.agency_formation.FIA;

import java.util.ArrayList;
import java.util.Random;



public class Mutation {
    private static final double prob_mutation = 0.6;

    public Mutation(){}


    /*La mutation ci permette di modificare i geni dell’individuo corrente con geni
     di un individuo scelto casualmente dalla popolazione */
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
}
