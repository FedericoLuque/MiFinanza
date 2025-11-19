package com.mycompany.mifinanza.controllers;

import com.mycompany.mifinanza.dao.DashboardDAO;
import com.mycompany.mifinanza.utils.Sesion;
import com.mycompany.mifinanza.views.DashboardView;
import java.awt.Color;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler;

public class DashboardController {

    private final DashboardView view;
    private final DashboardDAO dao;

    public DashboardController(DashboardView view, DashboardDAO dao) {
        this.view = view;
        this.dao = dao;
    }

    public void initController() {
        cargarGraficos();
    }

    private void cargarGraficos() {
        if (Sesion.getUsuario() == null) {
            view.dispose();
            return;
        }
        int idUsuario = Sesion.getUsuario().getId();

        // Cargar y mostrar los gráficos
        crearGraficoGastosPorCategoria(idUsuario);
        crearGraficoBalance(idUsuario);
        
        // Revalidar y repintar la vista para asegurar que los gráficos se muestren
        view.revalidate();
        view.repaint();
    }

    private void crearGraficoGastosPorCategoria(int idUsuario) {
        Map<String, Double> datosCat = dao.obtenerGastosPorCategoria(idUsuario);
        
        if (datosCat.isEmpty()) {
            view.add(new JLabel("No hay gastos para mostrar."));
        } else {
            PieChart pieChart = new PieChartBuilder()
                    .width(400).height(300)
                    .title("Gastos por Categoría")
                    .theme(Styler.ChartTheme.GGPlot2)
                    .build();

            for (Map.Entry<String, Double> entry : datosCat.entrySet()) {
                pieChart.addSeries(entry.getKey(), entry.getValue());
            }

            JPanel panelPastel = new XChartPanel<>(pieChart);
            view.setPieChartPanel(panelPastel);
        }
    }

    private void crearGraficoBalance(int idUsuario) {
        double[] balance = dao.obtenerBalanceTotal(idUsuario);
        
        CategoryChart barChart = new CategoryChartBuilder()
                .width(400).height(300)
                .title("Balance General")
                .xAxisTitle("Tipo")
                .yAxisTitle("Monto ($)")
                .theme(Styler.ChartTheme.Matlab)
                .build();
        
        barChart.getStyler().setSeriesColors(new Color[] {new Color(46, 204, 113), new Color(231, 76, 60)});

        barChart.addSeries("Ingresos", java.util.Arrays.asList(new String[]{"Total"}), java.util.Arrays.asList(new Number[]{balance[0]}));
        barChart.addSeries("Gastos", java.util.Arrays.asList(new String[]{"Total"}), java.util.Arrays.asList(new Number[]{balance[1]}));

        JPanel panelBarras = new XChartPanel<>(barChart);
        view.setBarChartPanel(panelBarras);
    }
}
