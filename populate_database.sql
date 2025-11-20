-- ===================================================================
-- Script para poblar la base de datos de MiFinanza con datos de ejemplo
-- ===================================================================

-- Habilitar el uso de claves foráneas en SQLite
PRAGMA foreign_keys = ON;

-- 1. Insertar Usuario
-- Contraseña para '3333' (esto es un ejemplo, en una app real se usaría un hash)
INSERT INTO Usuario (id, nombre, email, password) VALUES (1, 'Federico', 'admin@test.com', '3333');

-- 2. Insertar Categorías
-- Categorías de GASTO
INSERT INTO Categoria (id, nombre, descripcion) VALUES (1, 'Vivienda', 'Gastos relacionados con el hogar');
INSERT INTO Categoria (id, nombre, descripcion) VALUES (2, 'Alimentación', 'Compras de supermercado, restaurantes, etc.');
INSERT INTO Categoria (id, nombre, descripcion) VALUES (3, 'Transporte', 'Gasolina, transporte público, mantenimiento del coche');
INSERT INTO Categoria (id, nombre, descripcion) VALUES (4, 'Ocio', 'Cine, conciertos, hobbies, etc.');
INSERT INTO Categoria (id, nombre, descripcion) VALUES (5, 'Salud', 'Farmacia, médicos, seguros');
INSERT INTO Categoria (id, nombre, descripcion) VALUES (6, 'Ropa y Accesorios', 'Compra de ropa, zapatos, etc.');
INSERT INTO Categoria (id, nombre, descripcion) VALUES (7, 'Facturas y Servicios', 'Electricidad, agua, internet, teléfono');

-- Subcategorías de Vivienda (parent_id = 1)
INSERT INTO Categoria (id, nombre, descripcion, parent_id) VALUES (11, 'Alquiler', 'Pago mensual del alquiler', 1);
INSERT INTO Categoria (id, nombre, descripcion, parent_id) VALUES (12, 'Mantenimiento', 'Arreglos y mantenimiento del hogar', 1);

-- Subcategorías de Alimentación (parent_id = 2)
INSERT INTO Categoria (id, nombre, descripcion, parent_id) VALUES (21, 'Supermercado', 'Compra de alimentos y productos básicos', 2);
INSERT INTO Categoria (id, nombre, descripcion, parent_id) VALUES (22, 'Restaurantes', 'Comidas y cenas fuera de casa', 2);

-- Subcategorías de Transporte (parent_id = 3)
INSERT INTO Categoria (id, nombre, descripcion, parent_id) VALUES (31, 'Gasolina', 'Combustible para el vehículo', 3);
INSERT INTO Categoria (id, nombre, descripcion, parent_id) VALUES (32, 'Transporte Público', 'Billetes de autobús, metro, etc.', 3);


-- Categorías de INGRESO
INSERT INTO Categoria (id, nombre, descripcion) VALUES (8, 'Salario', 'Ingreso por nómina mensual');
INSERT INTO Categoria (id, nombre, descripcion) VALUES (9, 'Ventas', 'Ingresos por ventas de productos o servicios');
INSERT INTO Categoria (id, nombre, descripcion) VALUES (10, 'Regalos', 'Dinero recibido como regalo');

-- 3. Insertar Cuentas
INSERT INTO Cuenta (id, nombre_cuenta, saldo, id_usuario) VALUES (1, 'Cartera', 150.75, 1);
INSERT INTO Cuenta (id, nombre_cuenta, saldo, id_usuario) VALUES (2, 'Banco Principal', 2500.00, 1);
INSERT INTO Cuenta (id, nombre_cuenta, saldo, id_usuario) VALUES (3, 'Ahorros', 5000.50, 1);

-- 4. Insertar Métodos de Pago
INSERT INTO MetodoPago (id, nombre_metodo) VALUES (1, 'Efectivo');
INSERT INTO MetodoPago (id, nombre_metodo) VALUES (2, 'Tarjeta de Débito');
INSERT INTO MetodoPago (id, nombre_metodo) VALUES (3, 'Tarjeta de Crédito');
INSERT INTO MetodoPago (id, nombre_metodo) VALUES (4, 'Transferencia Bancaria');

-- 5. Insertar Ingresos de ejemplo (asociados al usuario 1)
INSERT INTO Ingreso (monto, fecha, descripcion, fuente, id_cuenta, id_categoria, id_usuario) VALUES
(1800.00, date('now', '-15 days'), 'Salario de Octubre', 'Mi Empresa', 2, 8, 1),
(250.00, date('now', '-10 days'), 'Venta de un mueble antiguo', 'Marketplace', 1, 9, 1),
(50.00, date('now', '-5 days'), 'Regalo de cumpleaños', 'Familiar', 1, 10, 1);

-- 6. Insertar Gastos de ejemplo (asociados al usuario 1)
-- Gastos de Vivienda (Cat 11)
INSERT INTO Gasto (monto, fecha, descripcion, comercio, id_cuenta, id_metodopago, id_categoria, id_usuario) VALUES
(800.00, date('now', '-14 days'), 'Alquiler del piso', 'Inmobiliaria Centro', 2, 4, 11, 1),
(65.50, date('now', '-10 days'), 'Factura de la luz', 'Iberdrola', 2, 2, 7, 1);

-- Gastos de Alimentación (Cat 21, 22)
INSERT INTO Gasto (monto, fecha, descripcion, comercio, id_cuenta, id_metodopago, id_categoria, id_usuario) VALUES
(45.20, date('now', '-8 days'), 'Compra semanal', 'Mercadona', 2, 2, 21, 1),
(22.50, date('now', '-6 days'), 'Cena con amigos', 'Restaurante La Pizzería', 1, 1, 22, 1),
(15.80, date('now', '-3 days'), 'Desayuno', 'Cafetería El Sol', 1, 1, 22, 1);

-- Gastos de Transporte (Cat 31, 32)
INSERT INTO Gasto (monto, fecha, descripcion, comercio, id_cuenta, id_metodopago, id_categoria, id_usuario) VALUES
(50.00, date('now', '-12 days'), 'Llenar el depósito', 'Gasolinera Repsol', 2, 3, 31, 1),
(12.50, date('now', '-4 days'), 'Bono de autobús', 'EMT', 1, 1, 32, 1);

-- Gastos de Ocio (Cat 4)
INSERT INTO Gasto (monto, fecha, descripcion, comercio, id_cuenta, id_metodopago, id_categoria, id_usuario) VALUES
(9.50, date('now', '-7 days'), 'Entrada de cine', 'Cines Yelmo', 1, 2, 4, 1);

-- 7. Insertar Presupuestos de ejemplo para el mes actual
-- Presupuesto para Alimentación (Cat 2)
INSERT INTO Presupuesto (monto_total, monto_actual, fecha_inicio, fecha_fin, id_categoria, id_usuario) VALUES
(400.00, 83.50, date('now', 'start of month'), date('now', 'start of month', '+1 month', '-1 day'), 2, 1);

-- Presupuesto para Ocio (Cat 4)
INSERT INTO Presupuesto (monto_total, monto_actual, fecha_inicio, fecha_fin, id_categoria, id_usuario) VALUES
(100.00, 9.50, date('now', 'start of month'), date('now', 'start of month', '+1 month', '-1 day'), 4, 1);

-- Presupuesto para Transporte (Cat 3)
INSERT INTO Presupuesto (monto_total, fecha_inicio, fecha_fin, id_categoria, id_usuario) VALUES
(150.00, date('now', 'start of month'), date('now', 'start of month', '+1 month', '-1 day'), 3, 1);


-- ===================================================================
-- Fin del script de población
-- ===================================================================