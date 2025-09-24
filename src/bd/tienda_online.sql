CREATE DATABASE IF NOT EXISTS TiendaOnline;
USE TiendaOnline;

SELECT * FROM Usuarios;
ALTER TABLE Usuarios ADD COLUMN foto_perfil VARCHAR(255) DEFAULT 'default_profile.png';
DESC USUARIOS;
-- ========================
-- TABLA USUARIOS
-- ========================
CREATE TABLE Usuarios (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    contrasenia VARCHAR(255) NOT NULL, 
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    foto_perfil VARCHAR(255) DEFAULT 'default_profile.png'
);

-- ========================
-- TABLA CATEGORIAS
-- ========================
CREATE TABLE Categorias (
    idCategoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- ========================
-- TABLA PRODUCTOS
-- ========================
CREATE TABLE Productos (
    idProducto INT AUTO_INCREMENT PRIMARY KEY,
    idCategoria INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    presentacion VARCHAR(100), 
    precio DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (idCategoria) REFERENCES Categorias(idCategoria)
);

-- ========================
-- TABLA VENTAS
-- ========================
CREATE TABLE Ventas (
    idVenta INT AUTO_INCREMENT PRIMARY KEY,
    idUsuario INT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    subtotal DECIMAL(10,2) NOT NULL,
    descuento DECIMAL(10,2) DEFAULT 0,
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (idUsuario) REFERENCES Usuarios(idUsuario)
);

-- ========================
-- TABLA DETALLE_VENTAS
-- ========================
CREATE TABLE Detalle_Ventas (
    idDetalle INT AUTO_INCREMENT PRIMARY KEY,
    idVenta INT NOT NULL,
    idProducto INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (idVenta) REFERENCES Ventas(idVenta),
    FOREIGN KEY (idProducto) REFERENCES Productos(idProducto)
);

-- ========================
-- INSERTAR CATEGORIAS
-- ========================
INSERT INTO Categorias (nombre) VALUES
('Leche entera'),
('Leche deslactosada'),
('Leche saborizada'),
('Yogurt'),
('Mantequilla y Margarina'),
('Galletas'),
('Botanas'),
('Pastelitos'),
('Limpieza'),
('Bebidas');

-- ========================
-- INSERTAR PRODUCTOS
-- ========================

-- Leche entera
INSERT INTO Productos (idCategoria, nombre, presentacion, precio) VALUES
(1, 'Lala', '1 L', 28.50),
(1, 'Santa Clara', '1 L (6 piezas)', 230.00),
(1, 'Alpura', '1 L (6 piezas)', 180.00),
(1, 'Nutrileche', '1 L', 25.00);

-- Leche deslactosada
INSERT INTO Productos (idCategoria, nombre, presentacion, precio) VALUES
(2, 'Lala', '1 L (6 piezas)', 159.00),
(2, 'Alpura', '1 L', 30.00),
(2, 'Santa Clara', '1 L', 40.00);

-- Leche saborizada
INSERT INTO Productos (idCategoria, nombre, presentacion, precio) VALUES
(3, 'Lala Yomi Vainilla', '180 ml', 10.00),
(3, 'Lala Yomi Chocolate', '180 ml', 10.00),
(3, 'Lala Yomi Fresa', '180 ml', 10.00),
(3, 'Alpura Vainilla', '180 ml', 11.00),
(3, 'Alpura Fresa', '180 ml', 11.00),
(3, 'Alpura Chocolate', '180 ml', 11.00),
(3, 'Santa Clara Vainilla', '180 ml', 13.00),
(3, 'Santa Clara Chocolate', '180 ml', 13.00),
(3, 'Santa Clara Fresa', '180 ml', 13.00);

-- Yogurt
INSERT INTO Productos (idCategoria, nombre, presentacion, precio) VALUES
(4, 'Lala Fresa', '220 g (8 piezas)', 70.00),
(4, 'Alpura Natural', '1 kg', 42.00),
(4, 'Danone Griego', '150 g', 18.00);

-- Mantequilla y margarina
INSERT INTO Productos (idCategoria, nombre, presentacion, precio) VALUES
(5, 'Lala sin sal', '90 g', 24.00),
(5, 'Primavera', '225 g', 18.00);

-- Galletas
INSERT INTO Productos (idCategoria, nombre, presentacion, precio) VALUES
(6, 'Marinela Canelitas', '300 g', 37.90),
(6, 'Chokiees', '300 g', 107.00),
(6, 'Sponch', '700 g (4 paquetes)', 79.50),
(6, 'Pasticetas', '400 g', 65.90),
(6, 'Surtido Marinela', '450 g', 73.50);

-- Botanas
INSERT INTO Productos (idCategoria, nombre, presentacion, precio) VALUES
(7, 'Sabritas Flamin Hot', '20 g', 20.00),
(7, 'Sabritas Limón', '20 g', 20.00),
(7, 'Sabritas Adobadas', '20 g', 20.00),
(7, 'Doritos Rojos', '75 g', 18.00),
(7, 'Doritos Verdes', '35 g', 18.00),
(7, 'Cheetos Queso', '80 g', 15.00),
(7, 'Cheetos Flamin Hot', '80 g', 15.00),
(7, 'Cheetos Bolita', '80 g', 15.00),
(7, 'Cacahuates', '70 g', 20.00);

-- Pastelitos
INSERT INTO Productos (idCategoria, nombre, presentacion, precio) VALUES
(8, 'Gansito Marinela', '50 g', 20.90),
(8, 'Pingüinos Marinela', '80 g', 27.90),
(8, 'Choco Roles Marinela', '122 g (2 piezas)', 27.90),
(8, 'Gansito Marinela', '3 piezas', 50.90);

-- Limpieza
INSERT INTO Productos (idCategoria, nombre, presentacion, precio) VALUES
(9, 'Pinol El Original', '5.1 L', 179.00),
(9, 'Fabuloso', '6 L', 199.00),
(9, 'Cloralex', '1 L', 68.00),
(9, 'Clorox', '1 L', 65.00),
(9, 'Vanish', '1 L', 90.00),
(9, 'Ariel Líquido Poder y Cuidado', '8.5 L', 374.25),
(9, 'Persil en Polvo Color', '9 kg', 439.00),
(9, 'Ariel Líquido Color', '2.8 L (45 lavadas)', 149.00),
(9, 'Ariel en Polvo con Downy', '750 g', 35.00),
(9, 'Ariel Expert Líquido', '5 L (80 lavadas)', 194.90),
(9, 'Salvo Limón Líquido', '1.4 L', 69.00),
(9, 'Salvo Polvo', '1 kg', 39.00),
(9, 'Salvo Lavatrastes Limón', '900 ml', 55.00),
(9, 'Salvo Lavatrastes Limón', '500 ml', 32.90);

-- Bebidas
INSERT INTO Productos (idCategoria, nombre, presentacion, precio) VALUES
(10, 'Coca Cola', '600 ml', 18.00),
(10, 'Coca Cola', '2 L', 35.00),
(10, 'Pepsi', '600 ml', 17.00),
(10, 'Agua Bonafont', '1.5 L', 15.00),
(10, 'Jugo Jumex Mango', '1 L', 25.00);
