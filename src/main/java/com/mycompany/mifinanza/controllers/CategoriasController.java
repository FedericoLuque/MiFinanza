package com.mycompany.mifinanza.controllers;

import com.mycompany.mifinanza.dao.CategoriaDAO;
import com.mycompany.mifinanza.models.Categoria;
import com.mycompany.mifinanza.views.CategoriasView;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class CategoriasController {

    private final CategoriasView view;
    private final CategoriaDAO dao;

    public CategoriasController(CategoriasView view, CategoriaDAO dao) {
        this.view = view;
        this.dao = dao;
    }

    public void initController() {
        view.getBtnGuardar().addActionListener(e -> guardarCategoria());
        view.getBtnEliminar().addActionListener(e -> eliminarCategoria());
        cargarCategorias();
    }

    public void cargarCategorias() {
        List<Categoria> lista = dao.listar();
        view.mostrarCategorias(lista);
    }

    private void guardarCategoria() {
        String nombre = view.getTxtNombre().getText();
        if (nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Escribe un nombre para la categoría.");
            return;
        }

        Categoria nuevaCat = new Categoria(nombre);
        if (dao.insertar(nuevaCat)) {
            JOptionPane.showMessageDialog(view, "Categoría guardada con éxito.");
            view.getTxtNombre().setText(""); // Limpiar campo
            cargarCategorias(); // Actualizar tabla
        } else {
            JOptionPane.showMessageDialog(view, "Error al guardar la categoría.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCategoria() {
        JTable tblCategorias = view.getTblCategorias();
        int filaSeleccionada = tblCategorias.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(view, "Selecciona una categoría de la tabla para eliminar.");
            return;
        }

        int id = (int) tblCategorias.getValueAt(filaSeleccionada, 0);

        int confirm = JOptionPane.showConfirmDialog(view, 
                "¿Seguro que quieres eliminar la categoría seleccionada?", 
                "Confirmar Eliminación", 
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.eliminar(id)) {
                JOptionPane.showMessageDialog(view, "Categoría eliminada.");
                cargarCategorias(); // Actualizar tabla
            } else {
                JOptionPane.showMessageDialog(view, "Error al eliminar. Es posible que la categoría esté en uso.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
