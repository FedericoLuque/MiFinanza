package com.mycompany.mifinanza.controllers;

import com.mycompany.mifinanza.dao.CuentaDAO;
import com.mycompany.mifinanza.models.Cuenta;
import com.mycompany.mifinanza.utils.Sesion;
import com.mycompany.mifinanza.views.CuentasView;
import java.util.List;
import javax.swing.JOptionPane;

public class CuentasController {

    private final CuentasView view;
    private final CuentaDAO dao;

    public CuentasController(CuentasView view, CuentaDAO dao) {
        this.view = view;
        this.dao = dao;
    }

    public void initController() {
        // Configurar el listener para el botón de añadir
        view.getBtnAnadirCuenta().addActionListener(e -> anadirCuenta());
        
        // Cargar las cuentas al iniciar
        cargarCuentas();
    }

    public void cargarCuentas() {
        if (Sesion.getUsuario() == null) {
            JOptionPane.showMessageDialog(view, "No hay un usuario activo.", "Error", JOptionPane.ERROR_MESSAGE);
            view.dispose();
            return;
        }
        int idUsuario = Sesion.getUsuario().getId();
        List<Cuenta> lista = dao.listarPorUsuario(idUsuario);
        
        // La vista se encarga de la presentación, el controlador le pasa los datos
        view.mostrarCuentas(lista);
    }

    private void anadirCuenta() {
        String nombre = view.getTxtNombreCuenta().getText();
        if (nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Escribe un nombre para la cuenta.");
            return;
        }

        if (Sesion.getUsuario() == null) {
            JOptionPane.showMessageDialog(view, "Sesión inválida. Por favor, reinicia la aplicación.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idUsuario = Sesion.getUsuario().getId();
        Cuenta nuevaCuenta = new Cuenta(nombre, idUsuario);

        if (dao.insertar(nuevaCuenta)) {
            JOptionPane.showMessageDialog(view, "Cuenta guardada con éxito.");
            view.getTxtNombreCuenta().setText(""); // Limpiar el campo de texto
            cargarCuentas(); // Actualizar la lista
        } else {
            JOptionPane.showMessageDialog(view, "Error al guardar la cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
