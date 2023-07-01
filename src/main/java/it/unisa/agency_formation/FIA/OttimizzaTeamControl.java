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

/* OttimizzaTeamControl avvia l'algoritmo di iTeam */
@WebServlet("/OttimizzaTeam")
public class OttimizzaTeamControl extends HttpServlet {

    private static DecimalFormat df = new DecimalFormat("###.##");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idTeam = Integer.parseInt(req.getParameter("idTeam"));
        long startTime = System.nanoTime();
        try {
            Team team = getTeamFromManager(idTeam);
            String competenze = team.getCompetenza();
            ArrayList<String> comp = new ArrayList<>(Arrays.asList(competenze.split(",")));

            String skill1 = comp.get(0);
            String skill2 = comp.get(1);
            String skill3 = comp.get(2);


            if (skill1 != null && skill2 != null && skill3 != null) {
                //inizio a prendere TUTTI i dipendenti dal dataset
                ArrayList<DipendenteRefactor> data = DataFromDataset.fromDataSet();
                ArrayList<String> skillsRichieste = new ArrayList<>();
                skillsRichieste.add(skill1);
                skillsRichieste.add(skill2);
                skillsRichieste.add(skill3);
                ArrayList<TeamRefactor> population = Population.initPopulation(data.size(), data, skillsRichieste);

                TeamRefactor teamRefactor = iTeam.evolve(population, skillsRichieste);
                long endTime = System.nanoTime();
                int second = (int) ((endTime - startTime) / 1000000000);
                req.setAttribute("tempoEsecuzione", second);
                req.setAttribute("team", teamRefactor);
                req.setAttribute("teamDB", team);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/iTeam.jsp");
                dispatcher.forward(req, resp);
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