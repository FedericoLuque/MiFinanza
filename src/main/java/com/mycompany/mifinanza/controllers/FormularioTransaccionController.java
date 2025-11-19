package com.mycompany.mifinanza.controllers;

import com.mycompany.mifinanza.dao.CategoriaDAO;
import com.mycompany.mifinanza.dao.CuentaDAO;
import com.mycompany.mifinanza.dao.MetodoPagoDAO;
import com.mycompany.mifinanza.dao.TransaccionDAO;
import com.mycompany.mifinanza.models.Categoria;
import com.mycompany.mifinanza.models.Cuenta;
import com.mycompany.mifinanza.models.Gasto;
import com.mycompany.mifinanza.models.Ingreso;
import com.mycompany.mifinanza.models.MetodoPago;
import com.mycompany.mifinanza.utils.Sesion;
import com.mycompany.mifinanza.views.FormularioTransaccionView;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class FormularioTransaccionController {

    private final FormularioTransaccionView view;
    private final TransaccionDAO transaccionDAO;
    private final CuentaDAO cuentaDAO;
    private final CategoriaDAO categoriaDAO;
    private final MetodoPagoDAO metodoPagoDAO;

    public FormularioTransaccionController(FormularioTransaccionView view, TransaccionDAO transaccionDAO, CuentaDAO cuentaDAO, CategoriaDAO categoriaDAO, MetodoPagoDAO metodoPagoDAO) {
        this.view = view;
        this.transaccionDAO = transaccionDAO;
        this.cuentaDAO = cuentaDAO;
        this.categoriaDAO = categoriaDAO;
        this.metodoPagoDAO = metodoPagoDAO;
    }

    public void initController() {
        cargarCombos();
        view.getBtnGuardarGasto().addActionListener(e -> guardarGasto());
        view.getBtnGuardarIngreso().addActionListener(e -> guardarIngreso());
    }

    private void cargarCombos() {
        int idUsuario = Sesion.getUsuario().getId();
        
        List<Cuenta> cuentas = cuentaDAO.listarPorUsuario(idUsuario);
        view.setCuentasGastoModel(new DefaultComboBoxModel(cuentas.toArray()));
        view.setCuentasIngresoModel(new DefaultComboBoxModel(cuentas.toArray()));
        
        List<Categoria> categorias = categoriaDAO.listar();
        view.setCategoriasGastoModel(new DefaultComboBoxModel(categorias.toArray()));
        view.setCategoriasIngresoModel(new DefaultComboBoxModel(categorias.toArray()));
        
        List<MetodoPago> metodos = metodoPagoDAO.listar();
        view.setMetodosPagoModel(new DefaultComboBoxModel(metodos.toArray()));
    }

    private void guardarGasto() {
        try {
            double monto = Double.parseDouble(view.getTxtMontoGasto().getText());
            String desc = view.getTxtDescGasto().getText();
            String comercio = view.getTxtComercio().getText();
            Timestamp fecha = Timestamp.valueOf(view.getTxtFechaGasto().getText() + " 00:00:00");

            Cuenta cuentaSelec = (Cuenta) view.getCbCuentaGasto().getSelectedItem();
            Categoria catSelec = (Categoria) view.getCbCategoriaGasto().getSelectedItem();
            MetodoPago metodoSelec = (MetodoPago) view.getCbMetodoPago().getSelectedItem();

            if (cuentaSelec == null || catSelec == null || metodoSelec == null) {
                JOptionPane.showMessageDialog(view, "Faltan datos por seleccionar.");
                return;
            }

            Gasto nuevoGasto = new Gasto(monto, fecha, desc, comercio, cuentaSelec.getId(), metodoSelec.getId(), catSelec.getId(), Sesion.getUsuario().getId());

            if (transaccionDAO.registrarGasto(nuevoGasto)) {
                JOptionPane.showMessageDialog(view, "¡Gasto registrado y saldo actualizado!");
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Error al registrar el gasto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "El monto debe ser un número válido.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(view, "Formato de fecha incorrecto (Use YYYY-MM-DD).");
        }
    }

    private void guardarIngreso() {
        try {
            double monto = Double.parseDouble(view.getTxtMontoIngreso().getText());
            String desc = view.getTxtDescIngreso().getText();
            String fuente = view.getTxtFuente().getText();
            Timestamp fecha = Timestamp.valueOf(view.getTxtFechaIngreso().getText() + " 00:00:00");

            Cuenta cuentaSelec = (Cuenta) view.getCbCuentaIngreso().getSelectedItem();
            Categoria catSelec = (Categoria) view.getCbCategoriaIngreso().getSelectedItem();

            if (cuentaSelec == null || catSelec == null) {
                JOptionPane.showMessageDialog(view, "Selecciona cuenta y categoría.");
                return;
            }

            Ingreso nuevoIngreso = new Ingreso(monto, fecha, desc, fuente, cuentaSelec.getId(), catSelec.getId(), Sesion.getUsuario().getId());

            if (transaccionDAO.registrarIngreso(nuevoIngreso)) {
                JOptionPane.showMessageDialog(view, "¡Ingreso registrado! Dinero añadido.");
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Error al registrar ingreso.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error en los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
