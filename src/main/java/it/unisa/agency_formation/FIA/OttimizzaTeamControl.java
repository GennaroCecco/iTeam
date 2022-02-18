package it.unisa.agency_formation.FIA;

import it.unisa.agency_formation.team.domain.Team;
import it.unisa.agency_formation.team.manager.TeamManager;
import it.unisa.agency_formation.team.manager.TeamManagerImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet("/OttimizzaTeam")
public class OttimizzaTeamControl extends HttpServlet {

    //ci permette di avere un tempo di esecuzione di circa 3,5 secondi
    private static final int SIZE_POPULATION = 10000;
    private static DecimalFormat df = new DecimalFormat("###.##");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idTeam = Integer.parseInt(req.getParameter("idTeam"));
        System.out.println("idtEAM:;"+idTeam);
        try {
            Team team = getTeamFromManager(idTeam);
            System.out.println(team.getNomeTeam());
            String competenze = team.getCompetenza();
            System.out.println(competenze);
            String skill1 = null;
            String skill2 = null;
            String skill3 = null;
            ArrayList<String> comp = new ArrayList<>(Arrays.asList(competenze.split(",")));
            if (comp.size() > 3) {
                //problema, ci sono più di 3 skill
            } else {
                skill1 = comp.get(0);
                skill2 = comp.get(1);
                skill3 = comp.get(2);
            }

            if (skill1 != null && skill2 != null && skill3 != null) {
                //inizio a prendere TUTTI i dipendenti dal dataset
                ArrayList<DipendenteRefactor> data = DataFromDataset.fromDataSet();
                ArrayList<String> skillsRichieste = new ArrayList<>();
                skillsRichieste.add(skill1);
                skillsRichieste.add(skill2);
                skillsRichieste.add(skill3);
                //inizio a restringere il campo, prendo e creo team che hanno dipendenti con quelle skill
                ArrayList<TeamRefactor> population = Population.initPopulation(SIZE_POPULATION, data, skillsRichieste);
                long startTime = System.nanoTime();
                TeamRefactor teamRefactor = iTeam.evolve(population, skillsRichieste);
                long endTime = System.nanoTime();
                int second=(int) ((endTime - startTime)/1000000000);
                req.setAttribute("tempoEsecuzione",second);
                req.setAttribute("team",teamRefactor);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/TeamAI.jsp");
                dispatcher.forward(req,resp);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public static Team getTeamFromManager(int idTeam) throws SQLException {
        TeamManager teamManager = new TeamManagerImpl();
        return teamManager.recuperaTeamById2(idTeam);
    }

}
