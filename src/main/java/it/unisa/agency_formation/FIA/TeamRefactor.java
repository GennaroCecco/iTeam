package it.unisa.agency_formation.FIA;

import java.util.ArrayList;

public class TeamRefactor {

    private ArrayList<DipendenteRefactor> dipendenti;
    private double valoreTeam = 0.0;

    public TeamRefactor() {
    }

    public ArrayList<DipendenteRefactor> getDipendenti() {
        return dipendenti;
    }

    public void setDipendenti(ArrayList<DipendenteRefactor> dipendenti) {
        this.dipendenti = dipendenti;
    }

    public void calcolaFitness(ArrayList<String> skillsRichieste) {
        double toReturn = 0.0;
        for (DipendenteRefactor dip : dipendenti) {
            double temp = 0.0;
            ArrayList<String> stringheTrovate = new ArrayList<>();
            for (int i = 0; i < skillsRichieste.size(); i++) {
                if (dip.getSkills().containsKey(skillsRichieste.get(i))) {
                    if (!stringheTrovate.contains(skillsRichieste.get(i))) {
                       temp += dip.getSkills().get(skillsRichieste.get(i));
                        stringheTrovate.add(skillsRichieste.get(i));
                    }
                }
            }
            toReturn += temp/3;
        }
        toReturn = toReturn / dipendenti.size();
        valoreTeam = toReturn;
    }

    public void setValoreTeam(double valoreTeam) {
        this.valoreTeam = valoreTeam;
    }

    public double getValoreTeam() {
        return valoreTeam;
    }
}
