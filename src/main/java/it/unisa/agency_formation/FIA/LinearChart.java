package it.unisa.agency_formation.FIA;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.ArrayList;

public class LinearChart  extends ApplicationFrame{
    private DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
    public LinearChart( String applicationTitle , String chartTitle ) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Generazione","Valutazione",
                this.dataset,
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 800 , 800 ) );
        setContentPane( chartPanel );
    }

    public void createDataset(ArrayList<Double> score, ArrayList<Integer> gen) {

        for(int i=0;i<score.size();i++){
            this.dataset.addValue( score.get(i),"team", gen.get(i) );
        }

    }

    public static void main( String[ ] args ) {
        LinearChart chart = new LinearChart(
                "iTeam" ,
                "Team valutati");

        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );
    }
}

