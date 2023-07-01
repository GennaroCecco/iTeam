package it.unisa.agency_formation.FIA;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class LinearChart extends ApplicationFrame {
    private DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    /*LinearChart ha la funzione di mostrare il grafico delle generazioni
     e della loro rispettiva valutazione */
    public LinearChart(String applicationTitle, String chartTitle) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Generazione", "Valutazione",
                this.dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 800));
        setContentPane(chartPanel);
    }

    public void createDataset(ArrayList<Double> score, ArrayList<Integer> gen) {

        for (int i = 0; i < score.size(); i++) {
            this.dataset.addValue(score.get(i), "team", gen.get(i));
        }

    }
    @Override
    public void windowClosing(final WindowEvent event) {
        if (event.getWindow() == this) {
            dispose();
        }
    }
}

