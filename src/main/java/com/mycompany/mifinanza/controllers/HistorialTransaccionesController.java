package com.mycompany.mifinanza.controllers;

import com.mycompany.mifinanza.dao.TransaccionDAO;
import com.mycompany.mifinanza.models.Gasto;
import com.mycompany.mifinanza.models.Ingreso;
import com.mycompany.mifinanza.views.HistorialTransaccionesView;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class HistorialTransaccionesController {
    private final HistorialTransaccionesView view;
    private final TransaccionDAO transaccionDAO;

    public HistorialTransaccionesController(HistorialTransaccionesView view, TransaccionDAO transaccionDAO) {
        this.view = view;
        this.transaccionDAO = transaccionDAO;
    }

    public void initController() {
        view.getBtnCerrar().addActionListener(e -> view.dispose());
        cargarTransacciones();
    }

    private void cargarTransacciones() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0); // Limpiar tabla

        List<Ingreso> ingresos = transaccionDAO.getIngresos();
        for (Ingreso ingreso : ingresos) {
            model.addRow(new Object[]{
                    ingreso.getFecha(),
                    ingreso.getDescripcion(),
                    ingreso.getMonto(),
                    ingreso.getCategoria(),
                    ingreso.getCuenta()
            });
        }

        List<Gasto> gastos = transaccionDAO.getGastos();
        for (Gasto gasto : gastos) {
            model.addRow(new Object[]{
                    gasto.getFecha(),
                    gasto.getDescripcion(),
                    -gasto.getMonto(), // Mostrar como negativo
                    gasto.getCategoria(),
                    gasto.getCuenta()
            });
        }
    }
}
