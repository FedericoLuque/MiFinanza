package com.mycompany.mifinanza.views;

import com.mycompany.mifinanza.controllers.CategoriasController;
import com.mycompany.mifinanza.controllers.CuentasController;
import com.mycompany.mifinanza.controllers.DashboardController;
import com.mycompany.mifinanza.controllers.FormularioTransaccionController;
import com.mycompany.mifinanza.controllers.HistorialTransaccionesController;
import com.mycompany.mifinanza.controllers.PresupuestosController;
import com.mycompany.mifinanza.dao.CategoriaDAO;
import com.mycompany.mifinanza.dao.CuentaDAO;
import com.mycompany.mifinanza.dao.DashboardDAO;
import com.mycompany.mifinanza.dao.MetodoPagoDAO;
import com.mycompany.mifinanza.dao.PresupuestoDAO;
import com.mycompany.mifinanza.dao.TransaccionDAO;
import com.mycompany.mifinanza.dao.UsuarioDAO;
import com.mycompany.mifinanza.utils.Sesion;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author federico
 */
public class MainView extends javax.swing.JFrame {

    private JLabel lblSaludo;

    public MainView() {
        inicializarComponentesModernos();
    }

    private void inicializarComponentesModernos() {
        // 1. Configuración básica de la ventana
        setTitle("MiFinanza - Dashboard");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        // Panel Principal (Fondo)
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(245, 248, 250)); // Un gris azulado muy suave
        setContentPane(panelPrincipal);

        // --- ZONA SUPERIOR (HEADER) ---
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setOpaque(false);
        panelHeader.setBorder(new EmptyBorder(30, 40, 20, 40));

        // Saludo Personalizado
        String nombreUsuario = (Sesion.getUsuario() != null) ? Sesion.getUsuario().getNombre() : "Usuario";
        lblSaludo = new JLabel("¡Hola, " + nombreUsuario + "! 👋");
        lblSaludo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblSaludo.setForeground(new Color(33, 33, 33)); // Gris oscuro elegante

        // --- ZONA CENTRAL (GRID DE MENÚ) ---
        // Usamos GridLayout de 2 filas y 3 columnas con espacio de 20px
        JPanel panelMenu = new JPanel(new GridLayout(2, 3, 25, 25));
        panelMenu.setOpaque(false);
        panelMenu.setBorder(new EmptyBorder(10, 40, 40, 40));

        // Añadimos los botones con iconos (Emojis para no depender de imágenes externas)
        panelMenu.add(crearTarjetaMenu("💸", "Registrar", "Ingresos y Gastos", new Color(76, 175, 80), e -> abrirTransaccion()));
        panelMenu.add(crearTarjetaMenu("💳", "Cuentas", "Gestionar Saldos", new Color(33, 150, 243), e -> abrirCuentas()));
        panelMenu.add(crearTarjetaMenu("📊", "Dashboard", "Ver Estadísticas", new Color(156, 39, 176), e -> abrirDashboard()));
        panelMenu.add(crearTarjetaMenu("📉", "Presupuestos", "Metas de Ahorro", new Color(255, 152, 0), e -> abrirPresupuestos()));
        panelMenu.add(crearTarjetaMenu("🏷️", "Categorías", "Editar Etiquetas", new Color(96, 125, 139), e -> abrirCategorias()));
        panelMenu.add(crearTarjetaMenu("📜", "Historial", "Ver Transacciones", new Color(121, 85, 72), e -> abrirHistorialTransacciones()));

        // --- ZONA DE BOTONES DE ACCIÓN RÁPIDA (HEADER) ---
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panelAcciones.setOpaque(false);

        JButton btnAjustes = new JButton("Ajustes");
        btnAjustes.setBackground(new Color(240, 240, 240));
        btnAjustes.setForeground(new Color(50, 50, 50));
        btnAjustes.setFocusPainted(false);
        btnAjustes.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btnAjustes.addActionListener(e -> abrirConfiguracion());

        JButton btnLogout = new JButton("Cerrar Sesión");
        btnLogout.setBackground(new Color(255, 235, 238));
        btnLogout.setForeground(new Color(211, 47, 47));
        btnLogout.setFocusPainted(false);
        btnLogout.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btnLogout.addActionListener(e -> cerrarSesion());

        panelAcciones.add(btnAjustes);
        panelAcciones.add(btnLogout);

        panelHeader.add(lblSaludo, BorderLayout.WEST);
        panelHeader.add(panelAcciones, BorderLayout.EAST);

        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        panelPrincipal.add(panelMenu, BorderLayout.CENTER);
    }

    /**
     * Crea un botón grande con estilo de tarjeta.
     */
    private JButton crearTarjetaMenu(String icono, String titulo, String desc, Color colorBorde, ActionListener accion) {
        JButton btn = new JButton();
        
        // Usamos HTML para formatear el texto dentro del botón
        String html = "<html><center>"
                + "<span style='font-size:28px;'>" + icono + "</span><br/><br/>"
                + "<span style='font-size:16px; font-weight:bold; color:#333;'>" + titulo + "</span><br/>"
                + "<span style='font-size:11px; color:#777;'>" + desc + "</span>"
                + "</center></html>";
        
        btn.setText(html);
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Borde izquierdo de color para identificar la sección
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 6, 0, 0, colorBorde), // Borde de color a la izquierda
                BorderFactory.createEmptyBorder(10, 10, 10, 10) // Relleno
        )); 
        // Efecto de sombra suave
        btn.putClientProperty(FlatClientProperties.STYLE, "hoverBackground: #F5F5F5");

        btn.addActionListener(accion);
        return btn;
    }

    // --- ACCIONES ---

    private void abrirTransaccion() {
        FormularioTransaccionView view = new FormularioTransaccionView();
        TransaccionDAO transaccionDAO = new TransaccionDAO();
        CuentaDAO cuentaDAO = new CuentaDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        MetodoPagoDAO metodoPagoDAO = new MetodoPagoDAO();
        
        FormularioTransaccionController controller = new FormularioTransaccionController(
            view, transaccionDAO, cuentaDAO, categoriaDAO, metodoPagoDAO
        );
        
        controller.initController();
        view.setVisible(true);
    }

    private void abrirCuentas() {
        CuentasView view = new CuentasView();
        CuentaDAO dao = new CuentaDAO();
        CuentasController controller = new CuentasController(view, dao);
        controller.initController();
        view.setVisible(true);
    }

    private void abrirPresupuestos() {
        PresupuestosView view = new PresupuestosView();
        PresupuestoDAO presupuestoDAO = new PresupuestoDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO(); // El controlador lo necesita para el modal
        PresupuestosController controller = new PresupuestosController(view, presupuestoDAO, categoriaDAO);
        controller.initController();
        view.setVisible(true);
    }

    private void abrirCategorias() {
        CategoriasView view = new CategoriasView();
        CategoriaDAO dao = new CategoriaDAO();
        CategoriasController controller = new CategoriasController(view, dao);
        controller.initController();
        view.setVisible(true);
    }

    private void abrirDashboard() {
        DashboardView view = new DashboardView();
        DashboardDAO dao = new DashboardDAO();
        DashboardController controller = new DashboardController(view, dao);
        controller.initController();
        view.setVisible(true);
    }

    private void abrirConfiguracion() {
        new ConfiguracionView().setVisible(true);
    }

    private void abrirHistorialTransacciones() {
        HistorialTransaccionesView view = new HistorialTransaccionesView();
        TransaccionDAO dao = new TransaccionDAO();
        HistorialTransaccionesController controller = new HistorialTransaccionesController(view, dao);
        controller.initController();
        view.setVisible(true);
    }

    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres cerrar sesión?", "Salir", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            Sesion.logout();
            
            // Re-abrir LoginView con su controlador
            LoginView loginView = new LoginView();
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            com.mycompany.mifinanza.controllers.LoginController loginController = new com.mycompany.mifinanza.controllers.LoginController(loginView, usuarioDAO);
            loginController.initController();
            loginView.setLocationRelativeTo(null);
            loginView.setVisible(true);
        }
    }
}