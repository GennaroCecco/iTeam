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

    public double calcolaFitness(ArrayList<String> skillsRichieste){
        double toReturn = 0.0;
        for(DipendenteRefactor dip:dipendenti){
            ArrayList<String> stringheTrovate = new ArrayList<>();
            for(int i=0;i<skillsRichieste.size();i++){
                if(dip.getSkills().containsKey(skillsRichieste.get(i))) {
                    if (!stringheTrovate.contains(skillsRichieste.get(i))) {
                        toReturn += dip.getSkills().get(skillsRichieste.get(i));
                        stringheTrovate.add(skillsRichieste.get(i));
                    }
                }
            }
        }
        toReturn= toReturn/dipendenti.size();
        valoreTeam = toReturn;
        return toReturn;
    }

    public double getValoreTeam() {
        return valoreTeam;
    }
}
