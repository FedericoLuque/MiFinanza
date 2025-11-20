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
import java.awt.Component;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
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

        // Cargar Cuentas
        List<Cuenta> cuentas = cuentaDAO.listarPorUsuario(idUsuario);
        view.setCuentasGastoModel(new DefaultComboBoxModel(cuentas.toArray()));
        view.setCuentasIngresoModel(new DefaultComboBoxModel(cuentas.toArray()));

        // Cargar Categorías en una lista plana
        List<Categoria> categoriasRaiz = categoriaDAO.listarJerarquia();
        List<Categoria> flatCategorias = new ArrayList<>();
        for (Categoria cat : categoriasRaiz) {
            flatCategorias.add(cat);
            for (Categoria sub : cat.getSubCategorias()) {
                flatCategorias.add(sub);
            }
        }
        
        // Crear el ComboBoxModel con la lista plana
        DefaultComboBoxModel<Categoria> catModel = new DefaultComboBoxModel<>(flatCategorias.toArray(new Categoria[0]));
        view.setCategoriasGastoModel(catModel);
        view.setCategoriasIngresoModel(catModel); // Usar el mismo modelo
        
        // Asignar un Renderer custom para la indentación visual
        var renderer = new CategoriaRenderer();
        view.getCbCategoriaGasto().setRenderer(renderer);
        view.getCbCategoriaIngreso().setRenderer(renderer);

        // Cargar Métodos de Pago
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
    
    // Clase interna para renderizar las categorías con indentación
    class CategoriaRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            // Llama al método de la superclase para obtener el JLabel por defecto
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof Categoria) {
                Categoria cat = (Categoria) value;
                // Si la categoría tiene un padre, añade una indentación
                if (cat.getParentId() != null) {
                    label.setText("  └ " + cat.getNombre());
                } else {
                    label.setText(cat.getNombre());
                }
            }
            return label;
        }
    }
}