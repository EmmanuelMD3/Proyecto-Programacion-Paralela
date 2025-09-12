-- Crear base de datos
CREATE DATABASE IF NOT EXISTS tienda_online;
USE tienda_online;

-- ========================
-- TABLA USUARIOS
-- ========================
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    contrasenia VARCHAR(255) NOT NULL, -- Guardar hash, no texto plano
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ========================
-- TABLA PRODUCTOS
-- ========================
CREATE TABLE productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    categoria VARCHAR(50) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    presentacion VARCHAR(100), -- Ej: 1L, 180ml, 6 piezas, etc.
    precio DECIMAL(10,2) NOT NULL
);

-- ========================
-- TABLA VENTAS
-- ========================
CREATE TABLE ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    subtotal DECIMAL(10,2) NOT NULL,
    descuento DECIMAL(10,2) DEFAULT 0,
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- ========================
-- TABLA DETALLE_VENTAS
-- ========================
CREATE TABLE detalle_ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    venta_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (venta_id) REFERENCES ventas(id),
    FOREIGN KEY (producto_id) REFERENCES productos(id)
);

-- ========================
-- INSERTAR PRODUCTOS
-- ========================

-- Leche entera
INSERT INTO productos (categoria, nombre, presentacion, precio) VALUES
('Leche entera', 'Lala', '1 L', 28.50),
('Leche entera', 'Santa Clara', '1 L (6 piezas)', 230.00),
('Leche entera', 'Alpura', '1 L (6 piezas)', 180.00),
('Leche entera', 'Nutrileche', '1 L', 25.00);

-- Leche deslactosada
INSERT INTO productos (categoria, nombre, presentacion, precio) VALUES
('Leche deslactosada', 'Lala', '1 L (6 piezas)', 159.00),
('Leche deslactosada', 'Alpura', '1 L', 30.00),
('Leche deslactosada', 'Santa Clara', '1 L', 40.00);

-- Leche saborizada
INSERT INTO productos (categoria, nombre, presentacion, precio) VALUES
('Leche saborizada', 'Lala Yomi Vainilla', '180 ml', 10.00),
('Leche saborizada', 'Lala Yomi Chocolate', '180 ml', 10.00),
('Leche saborizada', 'Lala Yomi Fresa', '180 ml', 10.00),
('Leche saborizada', 'Alpura Vainilla', '180 ml', 11.00),
('Leche saborizada', 'Alpura Fresa', '180 ml', 11.00),
('Leche saborizada', 'Alpura Chocolate', '180 ml', 11.00),
('Leche saborizada', 'Santa Clara Vainilla', '180 ml', 13.00),
('Leche saborizada', 'Santa Clara Chocolate', '180 ml', 13.00),
('Leche saborizada', 'Santa Clara Fresa', '180 ml', 13.00);

-- Yogurt
INSERT INTO productos (categoria, nombre, presentacion, precio) VALUES
('Yogurt bebible', 'Lala Fresa', '220 g (8 piezas)', 70.00),
('Yogurt natural', 'Alpura Natural', '1 kg', 42.00),
('Yogurt griego', 'Danone Griego', '150 g', 18.00);

-- Mantequilla y margarina
INSERT INTO productos (categoria, nombre, presentacion, precio) VALUES
('Mantequilla', 'Lala sin sal', '90 g', 24.00),
('Margarina', 'Primavera', '225 g', 18.00);

-- Snacks - Galletas
INSERT INTO productos (categoria, nombre, presentacion, precio) VALUES
('Galletas', 'Marinela Canelitas', '300 g', 37.90),
('Galletas', 'Chokiees', '300 g', 107.00),
('Galletas', 'Sponch', '700 g (4 paquetes)', 79.50),
('Galletas', 'Pasticetas', '400 g', 65.90),
('Galletas', 'Surtido Marinela', '450 g', 73.50);

-- Snacks - Botanas
INSERT INTO productos (categoria, nombre, presentacion, precio) VALUES
('Botanas', 'Sabritas Flamin Hot', '20 g', 20.00),
('Botanas', 'Sabritas Limón', '20 g', 20.00),
('Botanas', 'Sabritas Adobadas', '20 g', 20.00),
('Botanas', 'Doritos Rojos', '75 g', 18.00),
('Botanas', 'Doritos Verdes', '35 g', 18.00),
('Botanas', 'Cheetos Queso', '80 g', 15.00),
('Botanas', 'Cheetos Flamin Hot', '80 g', 15.00),
('Botanas', 'Cheetos Bolita', '80 g', 15.00),
('Botanas', 'Cacahuates', '70 g', 20.00);

-- Snacks - Pastelitos
INSERT INTO productos (categoria, nombre, presentacion, precio) VALUES
('Pastelitos', 'Gansito Marinela', '50 g', 20.90),
('Pastelitos', 'Pingüinos Marinela', '80 g', 27.90),
('Pastelitos', 'Choco Roles Marinela', '122 g (2 piezas)', 27.90),
('Pastelitos', 'Gansito Marinela', '3 piezas', 50.90);

-- Productos de limpieza
INSERT INTO productos (categoria, nombre, presentacion, precio) VALUES
('Limpieza', 'Pinol El Original', '5.1 L', 179.00),
('Limpieza', 'Fabuloso', '6 L', 199.00),
('Limpieza', 'Cloralex', '1 L', 68.00),
('Limpieza', 'Clorox', '1 L', 65.00),
('Limpieza', 'Vanish', '1 L', 90.00),
('Limpieza', 'Ariel Líquido Poder y Cuidado', '8.5 L', 374.25),
('Limpieza', 'Persil en Polvo Color', '9 kg', 439.00),
('Limpieza', 'Ariel Líquido Color', '2.8 L (45 lavadas)', 149.00),
('Limpieza', 'Ariel en Polvo con Downy', '750 g', 35.00),
('Limpieza', 'Ariel Expert Líquido', '5 L (80 lavadas)', 194.90),
('Limpieza', 'Salvo Limón Líquido', '1.4 L', 69.00),
('Limpieza', 'Salvo Polvo', '1 kg', 39.00),
('Limpieza', 'Salvo Lavatrastes Limón', '900 ml', 55.00),
('Limpieza', 'Salvo Lavatrastes Limón', '500 ml', 32.90);

-- Categoría adicional: Bebidas (ejemplo)
INSERT INTO productos (categoria, nombre, presentacion, precio) VALUES
('Bebidas', 'Coca Cola', '600 ml', 18.00),
('Bebidas', 'Coca Cola', '2 L', 35.00),
('Bebidas', 'Pepsi', '600 ml', 17.00),
('Bebidas', 'Agua Bonafont', '1.5 L', 15.00),
('Bebidas', 'Jugo Jumex Mango', '1 L', 25.00);
