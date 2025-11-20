-- ===================================================================
-- Script para crear la estructura de la base de datos de MiFinanza
-- ===================================================================

-- Habilitar el uso de claves foráneas en SQLite
PRAGMA foreign_keys = ON;

-- 1. Tabla de Usuarios
-- Almacena la información de los usuarios que pueden iniciar sesión.
CREATE TABLE IF NOT EXISTS Usuario (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);

-- 2. Tabla de Cuentas
-- Representa las diferentes cuentas de dinero del usuario (ej: cartera, banco).
CREATE TABLE IF NOT EXISTS Cuenta (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre_cuenta TEXT NOT NULL,
    saldo REAL NOT NULL DEFAULT 0.0,
    id_usuario INTEGER NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuario (id) ON DELETE CASCADE
);

-- 3. Tabla de Categorías
-- Organiza los ingresos y gastos. Permite subcategorías.
CREATE TABLE IF NOT EXISTS Categoria (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    descripcion TEXT,
    parent_id INTEGER,
    FOREIGN KEY (parent_id) REFERENCES Categoria (id) ON DELETE SET NULL
);

-- 4. Tabla de Métodos de Pago
-- Define los métodos que se pueden usar en los gastos (efectivo, tarjeta, etc.).
CREATE TABLE IF NOT EXISTS MetodoPago (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre_metodo TEXT NOT NULL UNIQUE
);

-- 5. Tabla de Ingresos
-- Registra todas las entradas de dinero.
CREATE TABLE IF NOT EXISTS Ingreso (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    monto REAL NOT NULL CHECK(monto > 0),
    fecha DATE NOT NULL,
    descripcion TEXT,
    fuente TEXT,
    id_cuenta INTEGER NOT NULL,
    id_categoria INTEGER NOT NULL,
    id_usuario INTEGER NOT NULL,
    FOREIGN KEY (id_cuenta) REFERENCES Cuenta (id) ON DELETE CASCADE,
    FOREIGN KEY (id_categoria) REFERENCES Categoria (id) ON DELETE RESTRICT,
    FOREIGN KEY (id_usuario) REFERENCES Usuario (id) ON DELETE CASCADE
);

-- 6. Tabla de Gastos
-- Registra todas las salidas de dinero.
CREATE TABLE IF NOT EXISTS Gasto (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    monto REAL NOT NULL CHECK(monto > 0),
    fecha DATE NOT NULL,
    descripcion TEXT,
    comercio TEXT,
    id_cuenta INTEGER NOT NULL,
    id_metodopago INTEGER NOT NULL,
    id_categoria INTEGER NOT NULL,
    id_usuario INTEGER NOT NULL,
    FOREIGN KEY (id_cuenta) REFERENCES Cuenta (id) ON DELETE CASCADE,
    FOREIGN KEY (id_metodopago) REFERENCES MetodoPago (id) ON DELETE RESTRICT,
    FOREIGN KEY (id_categoria) REFERENCES Categoria (id) ON DELETE RESTRICT,
    FOREIGN KEY (id_usuario) REFERENCES Usuario (id) ON DELETE CASCADE
);

-- 7. Tabla de Presupuestos
-- Permite a los usuarios establecer límites de gasto para categorías.
CREATE TABLE IF NOT EXISTS Presupuesto (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    monto_total REAL NOT NULL CHECK(monto_total > 0),
    monto_actual REAL NOT NULL DEFAULT 0.0,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    id_categoria INTEGER NOT NULL,
    id_usuario INTEGER NOT NULL,
    FOREIGN KEY (id_categoria) REFERENCES Categoria (id) ON DELETE CASCADE,
    FOREIGN KEY (id_usuario) REFERENCES Usuario (id) ON DELETE CASCADE,
    UNIQUE(id_categoria, id_usuario, fecha_inicio, fecha_fin) -- Evita presupuestos duplicados para el mismo periodo
);

-- Índices para mejorar el rendimiento de las consultas más comunes
CREATE INDEX IF NOT EXISTS idx_ingreso_usuario_fecha ON Ingreso (id_usuario, fecha);
CREATE INDEX IF NOT EXISTS idx_gasto_usuario_fecha ON Gasto (id_usuario, fecha);
CREATE INDEX IF NOT EXISTS idx_cuenta_usuario ON Cuenta (id_usuario);
CREATE INDEX IF NOT EXISTS idx_presupuesto_usuario ON Presupuesto (id_usuario);

-- ===================================================================
-- Fin del script de creación
-- ===================================================================
