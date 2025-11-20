package com.mycompany.mifinanza.controllers;

import com.mycompany.mifinanza.dao.CategoriaDAO;
import com.mycompany.mifinanza.models.Categoria;
import com.mycompany.mifinanza.views.CategoriasView;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class CategoriasController {

    private final CategoriasView view;
    private final CategoriaDAO dao;

    public CategoriasController(CategoriasView view, CategoriaDAO dao) {
        this.view = view;
        this.dao = dao;
    }

    public void initController() {
        // Asignar listeners a los botones
        view.getBtnAnadirRaiz().addActionListener(e -> anadirCategoria(true));
        view.getBtnAnadirSub().addActionListener(e -> anadirCategoria(false));
        view.getBtnEliminar().addActionListener(e -> eliminarCategoria());
        view.getBtnRenombrar().addActionListener(e -> renombrarCategoria());
        
        // Cargar el árbol al iniciar
        cargarArbolCategorias();
    }

    private void cargarArbolCategorias() {
        List<Categoria> categoriasRaiz = dao.listarJerarquia();
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Todas las Categorías");
        
        for (Categoria cat : categoriasRaiz) {
            DefaultMutableTreeNode catNode = new DefaultMutableTreeNode(cat);
            rootNode.add(catNode);
            // Añadir sub-categorías recursivamente
            anadirNodosHijos(catNode, cat.getSubCategorias());
        }
        
        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        view.setTreeModel(treeModel);
    }

    private void anadirNodosHijos(DefaultMutableTreeNode parentNode, List<Categoria> hijos) {
        for (Categoria hijo : hijos) {
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(hijo);
            parentNode.add(childNode);
            // Si este hijo también tuviera hijos, se llamaría recursivamente de nuevo
            anadirNodosHijos(childNode, hijo.getSubCategorias());
        }
    }

    private DefaultMutableTreeNode getSelectedNode() {
        JTree tree = view.getTreeCategorias();
        TreePath path = tree.getSelectionPath();
        if (path == null) {
            return null;
        }
        return (DefaultMutableTreeNode) path.getLastPathComponent();
    }

    private void anadirCategoria(boolean esRaiz) {
        String nombre = view.getTxtNombre().getText();
        if (nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Integer parentId = null;
        if (!esRaiz) {
            DefaultMutableTreeNode parentNode = getSelectedNode();
            if (parentNode == null || !(parentNode.getUserObject() instanceof Categoria)) {
                JOptionPane.showMessageDialog(view, "Selecciona una categoría padre para añadir la subcategoría.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Categoria parentCat = (Categoria) parentNode.getUserObject();
            parentId = parentCat.getId();
        }

        Categoria nueva = new Categoria(nombre);
        if (dao.insertar(nueva, parentId)) {
            view.getTxtNombre().setText("");
            cargarArbolCategorias();
        } else {
            JOptionPane.showMessageDialog(view, "Error al guardar la categoría.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCategoria() {
        DefaultMutableTreeNode selectedNode = getSelectedNode();
        if (selectedNode == null || !(selectedNode.getUserObject() instanceof Categoria)) {
            JOptionPane.showMessageDialog(view, "Selecciona una categoría para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Categoria catAEliminar = (Categoria) selectedNode.getUserObject();
        
        int confirm = JOptionPane.showConfirmDialog(view,
                "¿Seguro que quieres eliminar '" + catAEliminar.getNombre() + "'?\nEsto eliminará también todas sus subcategorías.",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.eliminar(catAEliminar.getId())) {
                cargarArbolCategorias();
            } else {
                JOptionPane.showMessageDialog(view, "Error al eliminar. La categoría podría estar en uso.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void renombrarCategoria() {
        DefaultMutableTreeNode selectedNode = getSelectedNode();
        if (selectedNode == null || !(selectedNode.getUserObject() instanceof Categoria)) {
            JOptionPane.showMessageDialog(view, "Selecciona una categoría para renombrar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String nuevoNombre = view.getTxtNombre().getText();
        if (nuevoNombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "El nuevo nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Categoria catARenombrar = (Categoria) selectedNode.getUserObject();
        catARenombrar.setNombre(nuevoNombre);
        
        if (dao.actualizar(catARenombrar)) {
            view.getTxtNombre().setText("");
            cargarArbolCategorias();
        } else {
            JOptionPane.showMessageDialog(view, "Error al renombrar la categoría.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}