package com.mycompany.mifinanza.controllers;

import com.mycompany.mifinanza.dao.CategoriaDAO;
import com.mycompany.mifinanza.dao.PresupuestoDAO;
import com.mycompany.mifinanza.models.Categoria;
import com.mycompany.mifinanza.models.Presupuesto;
import com.mycompany.mifinanza.utils.Sesion;
import com.mycompany.mifinanza.views.ModalCrearPresupuesto;
import com.mycompany.mifinanza.views.PresupuestosView;
import java.awt.Component;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class PresupuestosController {

    private final PresupuestosView view;
    private final PresupuestoDAO presupuestoDAO;
    private final CategoriaDAO categoriaDAO;
    private ModalCrearPresupuesto modal;

    public PresupuestosController(PresupuestosView view, PresupuestoDAO presupuestoDAO, CategoriaDAO categoriaDAO) {
        this.view = view;
        this.presupuestoDAO = presupuestoDAO;
        this.categoriaDAO = categoriaDAO;
    }

    public void initController() {
        view.getBtnCrear().addActionListener(e -> abrirModalParaCrear());
        cargarPresupuestos();
    }

    public void cargarPresupuestos() {
        if (Sesion.getUsuario() == null) return;
        int idUsuario = Sesion.getUsuario().getId();
        List<Presupuesto> lista = presupuestoDAO.listarConProgreso(idUsuario);
        view.mostrarPresupuestos(lista);
    }

    private void abrirModalParaCrear() {
        modal = new ModalCrearPresupuesto(view, true);

        // Cargar categorías en el ComboBox del modal con jerarquía
        List<Categoria> categoriasRaiz = categoriaDAO.listarJerarquia();
        List<Categoria> flatCategorias = new ArrayList<>();
        for (Categoria cat : categoriasRaiz) {
            flatCategorias.add(cat);
            // Solo añadimos un nivel de subcategorías para presupuestos
            flatCategorias.addAll(cat.getSubCategorias());
        }

        DefaultComboBoxModel<Categoria> catModel = new DefaultComboBoxModel<>(flatCategorias.toArray(new Categoria[0]));
        modal.setCategoriasModel(catModel);

        // Asignar un Renderer custom para la indentación visual
        modal.getCbCategoria().setRenderer(new CategoriaRenderer());

        // Quitar listeners viejos y poner el nuestro
        for (var listener : modal.getBtnGuardar().getActionListeners()) {
            modal.getBtnGuardar().removeActionListener(listener);
        }
        modal.getBtnGuardar().addActionListener(e -> guardarNuevoPresupuesto());

        modal.setVisible(true);
    }

    private void guardarNuevoPresupuesto() {
        try {
            Categoria cat = (Categoria) modal.getCbCategoria().getSelectedItem();
            double monto = Double.parseDouble(modal.getTxtMonto().getText());
            LocalDate fechaIn = LocalDate.parse(modal.getTxtFechaInicio().getText());
            LocalDate fechaFi = LocalDate.parse(modal.getTxtFechaFin().getText());

            if (cat == null) {
                JOptionPane.showMessageDialog(modal, "Selecciona una categoría.");
                return;
            }
            if (fechaFi.isBefore(fechaIn)) {
                JOptionPane.showMessageDialog(modal, "La fecha final no puede ser antes de la inicial.");
                return;
            }

            Presupuesto nuevo = new Presupuesto(monto, fechaIn, fechaFi, cat.getId());
            int idUsuario = Sesion.getUsuario().getId();

            if (presupuestoDAO.insertar(nuevo, idUsuario)) {
                JOptionPane.showMessageDialog(modal, "Presupuesto creado con éxito.");
                modal.dispose(); // Cerrar el modal
                cargarPresupuestos(); // Refrescar la vista principal
            } else {
                JOptionPane.showMessageDialog(modal, "Error al guardar el presupuesto.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(modal, "El monto debe ser un número válido.");
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(modal, "El formato de fecha es incorrecto. Usa YYYY-MM-DD.");
        }
    }
    
    // Clase interna para renderizar las categorías con indentación
    class CategoriaRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Categoria) {
                Categoria cat = (Categoria) value;
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