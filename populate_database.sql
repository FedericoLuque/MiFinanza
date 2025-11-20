-- ===================================================================
-- Script para poblar la base de datos de MiFinanza con datos de ejemplo
-- Usuario: Federico
-- ===================================================================

-- 1. Insertar Usuario
-- La contraseña se guarda en texto plano. En una app real, ¡nunca hagas esto!
-- Deberías usar un hash (ej. bcrypt).
INSERT INTO Usuario (id, nombre, email, password) 
VALUES (1, 'Federico', 'admin@test.com', '3211');

-- 2. Insertar Categorías y Subcategorías
-- Las categorías principales no tienen 'parent_id' (es NULL)
INSERT INTO Categoria (id, nombre, descripcion, parent_id) VALUES
(1, 'Alimentación', 'Gastos relacionados con comida.', NULL),
(2, 'Vivienda', 'Gastos del hogar.', NULL),
(3, 'Transporte', 'Gastos para moverse.', NULL),
(4, 'Ocio', 'Gastos en entretenimiento y hobbies.', NULL),
(5, 'Salud', 'Gastos médicos y de bienestar.', NULL),
(6, 'Ingresos', 'Fuentes de ingresos.', NULL);

-- Subcategorías de Alimentación (parent_id = 1)
INSERT INTO Categoria (id, nombre, descripcion, parent_id) VALUES
(7, 'Supermercado', 'Compras de alimentos y productos básicos.', 1),
(8, 'Restaurantes', 'Comidas fuera de casa.', 1),
(9, 'Cafeterías', 'Cafés y snacks.', 1);

-- Subcategorías de Vivienda (parent_id = 2)
INSERT INTO Categoria (id, nombre, descripcion, parent_id) VALUES
(10, 'Alquiler/Hipoteca', 'Pago mensual de la vivienda.', 2),
(11, 'Servicios Públicos', 'Luz, agua, gas, internet.', 2),
(12, 'Mantenimiento', 'Reparaciones y mejoras del hogar.', 2);

-- Subcategorías de Transporte (parent_id = 3)
INSERT INTO Categoria (id, nombre, descripcion, parent_id) VALUES
(13, 'Combustible', 'Gasolina o diésel para el vehículo.', 3),
(14, 'Transporte Público', 'Billetes de bus, metro, tren.', 3),
(15, 'Mantenimiento Vehículo', 'Reparaciones, seguro, impuestos.', 3);

-- Subcategorías de Ocio (parent_id = 4)
INSERT INTO Categoria (id, nombre, descripcion, parent_id) VALUES
(16, 'Cine y Streaming', 'Entradas, suscripciones a Netflix, etc.', 4),
(17, 'Deportes', 'Gimnasio, equipamiento.', 4),
(18, 'Viajes', 'Vacaciones y escapadas.', 4);

-- Subcategorías de Ingresos (parent_id = 6)
INSERT INTO Categoria (id, nombre, descripcion, parent_id) VALUES
(19, 'Salario', 'Ingreso por nómina.', 6),
(20, 'Ventas', 'Ingresos por ventas de productos o servicios.', 6),
(21, 'Regalos', 'Dinero recibido como regalo.', 6);


-- 3. Insertar Cuentas para el usuario Federico (id_usuario = 1)
INSERT INTO Cuenta (id, nombre_cuenta, saldo, id_usuario) VALUES
(1, 'Cartera', 150.75, 1),
(2, 'Cuenta Bancaria Principal', 2500.00, 1),
(3, 'Ahorros', 5000.00, 1);

-- 4. Insertar Métodos de Pago
INSERT INTO MetodoPago (id, nombre_metodo) VALUES
(1, 'Efectivo'),
(2, 'Tarjeta de Débito'),
(3, 'Tarjeta de Crédito'),
(4, 'Transferencia Bancaria');

-- 5. Insertar Presupuestos para Federico (id_usuario = 1)
-- Presupuesto mensual para "Alimentación" y sus subcategorías
INSERT INTO Presupuesto (id, monto_total, monto_actual, fecha_inicio, fecha_fin, id_categoria, id_usuario) VALUES
(1, 600.00, 115.50, '2025-11-01', '2025-11-30', 1, 1);

-- Presupuesto mensual para "Ocio"
INSERT INTO Presupuesto (id, monto_total, monto_actual, fecha_inicio, fecha_fin, id_categoria, id_usuario) VALUES
(2, 250.00, 75.00, '2025-11-01', '2025-11-30', 4, 1);

-- 6. Insertar Ingresos de ejemplo (asociados al usuario 1)
-- Nótese que las fechas están en formato 'YYYY-MM-DD'
INSERT INTO Ingreso (monto, fecha, descripcion, fuente, id_cuenta, id_categoria, id_usuario) VALUES
(2200.00, '2025-11-01', 'Nómina Noviembre', 'Mi Empresa', 2, 19, 1),
(150.00, '2025-11-10', 'Venta de artículo de segunda mano', 'Wallapop', 1, 20, 1),
(50.00, '2025-11-15', 'Regalo de cumpleaños', 'Familiar', 1, 21, 1);

-- 7. Insertar Gastos de ejemplo (asociados al usuario 1)
-- Los montos de los gastos también actualizan el presupuesto y el saldo de la cuenta.
-- Gasto 1: Supermercado (afecta al presupuesto de Alimentación)
INSERT INTO Gasto (monto, fecha, descripcion, comercio, id_cuenta, id_metodopago, id_categoria, id_usuario) VALUES
(85.50, '2025-11-05', 'Compra semanal', 'Mercadona', 2, 2, 7, 1);

-- Gasto 2: Restaurante (afecta al presupuesto de Alimentación)
INSERT INTO Gasto (monto, fecha, descripcion, comercio, id_cuenta, id_metodopago, id_categoria, id_usuario) VALUES
(30.00, '2025-11-08', 'Cena con amigos', 'Pizzería local', 1, 1, 8, 1);

-- Gasto 3: Combustible
INSERT INTO Gasto (monto, fecha, descripcion, comercio, id_cuenta, id_metodopago, id_categoria, id_usuario) VALUES
(50.00, '2025-11-12', 'Llenar depósito', 'Repsol', 2, 3, 13, 1);

-- Gasto 4: Gimnasio (afecta al presupuesto de Ocio)
INSERT INTO Gasto (monto, fecha, descripcion, comercio, id_cuenta, id_metodopago, id_categoria, id_usuario) VALUES
(40.00, '2025-11-02', 'Cuota mensual', 'Gimnasio VivaGym', 2, 2, 17, 1);

-- Gasto 5: Cine (afecta al presupuesto de Ocio)
INSERT INTO Gasto (monto, fecha, descripcion, comercio, id_cuenta, id_metodopago, id_categoria, id_usuario) VALUES
(35.00, '2025-11-18', 'Entradas y palomitas', 'Cinesa', 1, 1, 16, 1);

-- Gasto 6: Factura de la luz
INSERT INTO Gasto (monto, fecha, descripcion, comercio, id_cuenta, id_metodopago, id_categoria, id_usuario) VALUES
(65.00, '2025-11-10', 'Factura electricidad Octubre', 'Iberdrola', 2, 4, 11, 1);

-- ===================================================================
-- Nota final: Después de ejecutar este script, los saldos de las cuentas
-- y los montos actuales de los presupuestos pueden no estar sincronizados.
-- La lógica de la aplicación (DAO) se encarga de actualizar estos valores
-- de forma atómica cuando se registra un gasto/ingreso a través de la UI.
-- Este script es solo para tener datos visuales iniciales.
-- ===================================================================

