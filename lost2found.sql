-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 25-02-2018 a las 21:46:49
-- Versión del servidor: 10.1.30-MariaDB
-- Versión de PHP: 7.2.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `lost2found`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE usuario (
  id int(4) NOT NULL AUTO_INCREMENT,
  email varchar(20) NOT NULL,
  nombre varchar(20) NOT NULL,
  contrasena varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lugar`
--

CREATE TABLE lugar (
  id int(4) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id)  
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo`
--

CREATE TABLE tipo (
  nombreTabla varchar(20) NOT NULL,
  descripcion text NOT NULL,
  PRIMARY KEY (nombreTabla)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `chat`
--

CREATE TABLE chat (
  id int(4) NOT NULL AUTO_INCREMENT,
  nombreChat varchar(20) NOT NULL,
  idUsuario1 int(4) NOT NULL,
  idUsuario2 int(4) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_idUsuario1 FOREIGN KEY (idUsuario1) REFERENCES usuario (id),
  CONSTRAINT fk_idUsuario2 FOREIGN KEY (idUsuario2) REFERENCES usuario (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `msg`
--

CREATE TABLE msg (
  id int(4) NOT NULL AUTO_INCREMENT,
  texto text NOT NULL,
  horaMsg date NOT NULL,
  leido int(4) DEFAULT NULL,
  idChat int(4) NOT NULL,
  idUsuario int(4) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_idChat1 FOREIGN KEY (idChat) REFERENCES chat (id),
  CONSTRAINT fk_idUsuario3 FOREIGN KEY (idUsuario) REFERENCES usuario (id)  
) ENGINE=InnoDB DEFAULT CHARSET=latin1  AUTO_INCREMENT=1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `anuncio_objeto`
--

CREATE TABLE anuncio_objeto (
  id int(4) NOT NULL AUTO_INCREMENT,
  tipoAnuncio varchar(10) NOT NULL,
  horaAnuncio date NOT NULL,
  diaAnuncio date NOT NULL,
  horaPerdidaHallazgo date NOT NULL,
  color varchar(20) DEFAULT NULL,
  idUsuario int(4) NOT NULL,
  idLugar int(4) NOT NULL,
  nombreTabla varchar(20) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_idUsuario4 FOREIGN KEY (idUsuario) REFERENCES usuario (id),
  CONSTRAINT fk_idLugar1 FOREIGN KEY (idLugar) REFERENCES lugar (id),
  CONSTRAINT fk_nombreTabla1 FOREIGN KEY (nombreTabla) REFERENCES tipo (nombreTabla)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `candidatos`
--

CREATE TABLE candidatos (
  idObjetoPerdida int(4) NOT NULL,
  idObjetoHallazgo int(4) NOT NULL,
  PRIMARY KEY (idObjetoPerdida, idObjetoHallazgo),
  CONSTRAINT fk_idObjeto6 FOREIGN KEY (idObjetoPerdida) REFERENCES anuncio_objeto (id),
  CONSTRAINT fk_idObjeto7 FOREIGN KEY (idObjetoHallazgo) REFERENCES anuncio_objeto (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `matching`
--

CREATE TABLE matching (
  idObjetoPerdida int(4) NOT NULL,
  idObjetoHallazgo int(4) NOT NULL,
  fase int (4) NOT NULL,
  CONSTRAINT fk_idObjeto8 FOREIGN KEY (idObjetoPerdida) REFERENCES anuncio_objeto (id),
  CONSTRAINT fk_idObjeto9 FOREIGN KEY (idObjetoHallazgo) REFERENCES anuncio_objeto (id)
  );
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tarjeta_bancaria`
--

CREATE TABLE `Tarjeta bancaria` (
  idObjeto int(4) NOT NULL,
  banco varchar(10) DEFAULT NULL,
  datosPropietario varchar(30) DEFAULT NULL,
  PRIMARY KEY (idObjeto),
  CONSTRAINT fk_idObjeto1 FOREIGN KEY (idObjeto) REFERENCES anuncio_objeto (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tarjeta_transporte`
--

CREATE TABLE `Tarjeta transporte` (
  idObjeto int(4) NOT NULL,
  datosPropietario varchar(30) DEFAULT NULL,
  PRIMARY KEY (idObjeto),
  CONSTRAINT fk_idObjeto2 FOREIGN KEY (idObjeto) REFERENCES anuncio_objeto (id)  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cartera`
--

CREATE TABLE Cartera (
  idObjeto int(4) NOT NULL,
  marca varchar(20) DEFAULT NULL,
  documentacion text,
  PRIMARY KEY (idObjeto),
  CONSTRAINT fk_idObjeto3 FOREIGN KEY (idObjeto) REFERENCES anuncio_objeto (id) 
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `telefono`
--

CREATE TABLE Telefono (
  idObjeto int(4) NOT NULL,
  marca varchar(20) DEFAULT NULL,
  modelo varchar(20) DEFAULT NULL,
  IMEI int(15) DEFAULT NULL,
  tara varchar(10) DEFAULT NULL,
  PRIMARY KEY (idObjeto),
  CONSTRAINT fk_idObjeto4 FOREIGN KEY (idObjeto) REFERENCES anuncio_objeto (id) 
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `otro`
--

CREATE TABLE Otro (
  idObjeto int(4) NOT NULL,
  nombre varchar(10) DEFAULT NULL,
  descripcion text,
  PRIMARY KEY (idObjeto),
  CONSTRAINT fk_idObjeto5 FOREIGN KEY (idObjeto) REFERENCES anuncio_objeto (id)   
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lugar_transporte`
--

CREATE TABLE lugar_transporte (
  idLugarTte int(4) NOT NULL AUTO_INCREMENT,
  tipoTte varchar(10) NOT NULL,
  linea varchar(100) NOT NULL,
  estacion varchar(30) NOT NULL,
  PRIMARY KEY (idLugarTte),
  CONSTRAINT fk_idLugar2 FOREIGN KEY (idLugarTte) REFERENCES lugar (id)     
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contiene`
--

CREATE TABLE contiene (
  idLugarContenedor int(4) NOT NULL,
  idLugarContenido int(4) NOT NULL,
  PRIMARY KEY (idLugarContenedor),
  CONSTRAINT fk_idLugarTte1 FOREIGN KEY (idLugarContenedor) REFERENCES lugar_transporte (idLugarTte),
  CONSTRAINT fk_idLugarTte2 FOREIGN KEY (idLugarContenido) REFERENCES lugar_transporte (idLugarTte)       
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lugar_concreto`
--

CREATE TABLE lugar_concreto (
  idLugar int(4) NOT NULL,
  calle varchar(20) NOT NULL,
  numero int(3) NOT NULL,
  codigoPostal int(5) NOT NULL,
  PRIMARY KEY (idLugar),
  CONSTRAINT fk_idLugar3 FOREIGN KEY (idLugar) REFERENCES lugar (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estacion_opendata`
--

CREATE TABLE estacion_opendata (
  id int(4) NOT NULL AUTO_INCREMENT,
  fecha date NOT NULL,
  hora date NOT NULL,
  objeto varchar(50) NOT NULL,
  tipoObjeto varchar(50) NOT NULL,
  idLugarTte int(4) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_idLugarTte3 FOREIGN KEY (idLugarTte) REFERENCES lugar_transporte (idLugarTte)   
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `caracteristica`
--

CREATE TABLE caracteristica (
  nombreCampo varchar(20) NOT NULL,
  nombreTabla varchar(20) NOT NULL,
  determinante int(4) NOT NULL,
  PRIMARY KEY (nombreCampo, nombreTabla),
  CONSTRAINT fk_nombreTabla2 FOREIGN KEY (nombreTabla) REFERENCES tipo (nombreTabla)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

-- Insert de los datos de la tabla tipo

INSERT INTO tipo (nombreTabla, descripcion) VALUES ('cartera', 'carteras que se hayan encontrado o perdido');
INSERT INTO tipo (nombreTabla, descripcion) VALUES ('otro', 'objetos de otro tipo no contemplado que se hayan podido encontrar o perder');
INSERT INTO tipo (nombreTabla, descripcion) VALUES ('tarjeta_bancaria', 'tarjetas bancarias que se hayan podido encontrar o perder');
INSERT INTO tipo (nombreTabla, descripcion) VALUES ('tarjeta_transporte', 'tarjetas de transporte que se hayan podido encontrar o perder');
INSERT INTO tipo (nombreTabla, descripcion) VALUES ('telefono', 'telefonos que se hayan podido encontrar o perder');

-- --------------------------------------------------------

-- Insert de los datos de la tabla lugar

INSERT INTO lugar (id) VALUES ('1');
INSERT INTO lugar (id) VALUES ('2');
INSERT INTO lugar (id) VALUES ('3');
INSERT INTO lugar (id) VALUES ('4');
INSERT INTO lugar (id) VALUES ('5');
INSERT INTO lugar (id) VALUES ('6');
INSERT INTO lugar (id) VALUES ('7');
INSERT INTO lugar (id) VALUES ('8');
INSERT INTO lugar (id) VALUES ('9');
INSERT INTO lugar (id) VALUES ('10');
INSERT INTO lugar (id) VALUES ('11');
INSERT INTO lugar (id) VALUES ('12');
INSERT INTO lugar (id) VALUES ('13');
INSERT INTO lugar (id) VALUES ('14');
INSERT INTO lugar (id) VALUES ('15');
INSERT INTO lugar (id) VALUES ('16');
INSERT INTO lugar (id) VALUES ('17');
INSERT INTO lugar (id) VALUES ('18');
INSERT INTO lugar (id) VALUES ('19');
INSERT INTO lugar (id) VALUES ('20');
INSERT INTO lugar (id) VALUES ('21');
INSERT INTO lugar (id) VALUES ('22');
INSERT INTO lugar (id) VALUES ('23');
INSERT INTO lugar (id) VALUES ('24');
INSERT INTO lugar (id) VALUES ('25');
INSERT INTO lugar (id) VALUES ('26');
INSERT INTO lugar (id) VALUES ('27');
INSERT INTO lugar (id) VALUES ('28');
INSERT INTO lugar (id) VALUES ('29');
INSERT INTO lugar (id) VALUES ('30');
INSERT INTO lugar (id) VALUES ('31');
INSERT INTO lugar (id) VALUES ('32');
INSERT INTO lugar (id) VALUES ('33');
INSERT INTO lugar (id) VALUES ('34');
INSERT INTO lugar (id) VALUES ('35');
INSERT INTO lugar (id) VALUES ('36');
INSERT INTO lugar (id) VALUES ('37');
INSERT INTO lugar (id) VALUES ('38');
INSERT INTO lugar (id) VALUES ('39');
INSERT INTO lugar (id) VALUES ('40');
INSERT INTO lugar (id) VALUES ('41');
INSERT INTO lugar (id) VALUES ('42');
INSERT INTO lugar (id) VALUES ('43');
INSERT INTO lugar (id) VALUES ('44');
INSERT INTO lugar (id) VALUES ('45');
INSERT INTO lugar (id) VALUES ('46');
INSERT INTO lugar (id) VALUES ('47');
INSERT INTO lugar (id) VALUES ('48');
INSERT INTO lugar (id) VALUES ('49');
INSERT INTO lugar (id) VALUES ('50');
INSERT INTO lugar (id) VALUES ('51');
INSERT INTO lugar (id) VALUES ('52');
INSERT INTO lugar (id) VALUES ('53');
INSERT INTO lugar (id) VALUES ('54');
INSERT INTO lugar (id) VALUES ('55');
INSERT INTO lugar (id) VALUES ('56');
INSERT INTO lugar (id) VALUES ('57');
INSERT INTO lugar (id) VALUES ('58');
INSERT INTO lugar (id) VALUES ('59');
INSERT INTO lugar (id) VALUES ('60');
INSERT INTO lugar (id) VALUES ('61');
INSERT INTO lugar (id) VALUES ('62');
INSERT INTO lugar (id) VALUES ('63');
INSERT INTO lugar (id) VALUES ('64');
-- --------------------------------------------------------

-- Insert de los datos de la tabla lugar_transporte

-- --------------------------------------------------------

-- Metro --

INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('1', 'metro', '1 La Defense - Chateau de Vincennes', 'Champs Elysees');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('2', 'metro', '1 La Defense - Chateau de Vincennes', 'Les Sablons');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('3', 'metro', '1 La Defense - Chateau de Vincennes', 'St-Paul');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('4', 'metro', '1 La Defense - Chateau de Vincennes', 'Berault');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('5', 'metro', '1 La Defense - Chateau de Vincennes', 'St-Mande');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('6', 'metro', '2 Porte Dauphine - Nation', 'Ternes');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('7', 'metro', '2 Porte Dauphine - Nation', 'Monceau');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('8', 'metro', '2 Porte Dauphine - Nation', 'Courcelles');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('9', 'metro', '2 Porte Dauphine - Nation', 'Rome');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('10', 'metro', '3 Pont de Levallois Becon - Gallieni', 'Wagram');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('12', 'metro', '3 Pont de Levallois Becon - Gallieni', 'Jasmin');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('13', 'metro', '3 Pont de Levallois Becon - Gallieni', 'Ranelagh');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('14', 'metro', '3 Pont de Levallois Becon - Gallieni', 'Anatole France');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('15', 'metro', '3 Pont de Levallois Becon - Gallieni', 'Iena');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('16', 'metro', '3bis Gambetta - Porte des Lilas', 'Pelleport');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('17', 'metro', '3bis Gambetta - Porte des Lilas', 'Saint-Fargeau');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('18', 'metro', '4 Porte de Clignancourt - Mairie de Montrouge', 'Simplon');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('19', 'metro', '4 Porte de Clignancourt - Mairie de Montrouge', 'Chateau d Eau');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('20', 'metro', '4 Porte de Clignancourt - Mairie de Montrouge', 'Etienne Marcel');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('21', 'metro', '5 Bobigny Pablo Picasso - Place d Italie', 'Saint Marcel');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('22', 'metro', '5 Bobigny Pablo Picasso - Place d Italie', 'Campo Formio');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('23', 'metro', '5 Bobigny Pablo Picasso - Place d Italie', 'Bastille');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('24', 'metro', '5 Bobigny Pablo Picasso - Place d Italie', 'Richard Lenoir');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('25', 'metro', '6 Charles de Gaulle Etoile - Nation', 'Picpus');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('26', 'metro', '6 Charles de Gaulle Etoile - Nation', 'Bel-Air');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('27', 'metro', '6 Charles de Gaulle Etoile - Nation', 'Chevaleret');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('28', 'metro', '7 Louis Leblanc - Pre Saint Gervais', 'Place Monge');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('29', 'metro', '7 Louis Leblanc - Pre Saint Gervais', 'Les Gobelins');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('30', 'metro', '7 Louis Leblanc - Pre Saint Gervais', 'Jussieu');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('31', 'metro', '7 Louis Leblanc - Pre Saint Gervais', 'Pyramides');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('32', 'metro', '8 Balard - Pointe du Lac', 'Commerce');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('33', 'metro', '8 Balard - Pointe du Lac', 'Ecole Militaire');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('34', 'metro', '8 Balard - Pointe du Lac', 'Lourmel');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('35', 'metro', '9 Pont de Sevres - Mairie de Montreuil', 'Robespierre');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('36', 'metro', '9 Pont de Sevres - Mairie de Montreuil', 'Voltaire');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('37', 'metro', '9 Pont de Sevres - Mairie de Montreuil', 'Charonne');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('38', 'metro', '10 Boulogne Pont de Saint Cloud - Gare d Austerlitz', 'Segur');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('39', 'metro', '10 Boulogne Pont de Saint Cloud - Gare d Austerlitz', 'Vaneau');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('40', 'metro', '10 Boulogne Pont de Saint Cloud - Gare d Austerlitz', 'Mirabeau');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('41', 'metro', '11 Chatelet - Mairie des Lilas', 'Belleville');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('42', 'metro', '11 Chatelet - Mairie des Lilas', 'Rambuteau');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('43', 'metro', '11 Chatelet - Mairie des Lilas', 'Jourdain');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('44', 'metro', '12 Front Populaire - Mairie d Issy', 'Abbesses');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('45', 'metro', '12 Front Populaire - Mairie d Issy', 'Pigalle');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('46', 'metro', '12 Front Populaire - Mairie d Issy', 'Solferino');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('47', 'metro', '13 Gare Saint Lazare - Olympiades', 'Plaisance');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('48', 'metro', '13 Gare Saint Lazare - Olympiades', 'Gaite');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('49', 'metro', '13 Gare Saint Lazare - Olympiades', 'Varenne');

-- Autobus --

INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('50', 'bus', 'N51 Gare d Enghlen', 'Genevilliers');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('51', 'bus', 'N51 Gare d Enghlen', 'Saint-Denis');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('52', 'bus', 'N51 Gare d Enghlen', 'Louvre');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('53', 'bus', 'N43 Gare de Sarcelles', 'Malrie de Stains');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('54', 'bus', 'N43 Gare de Sarcelles', 'Porte de la Chapelle');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('55', 'bus', 'N14 Malrie de St-Ouen', 'Port Royal');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('56', 'bus', 'N14 Malrie de St-Ouen', 'Denfert Rochereau');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('57', 'bus', 'N13 Malrie d Issy', 'Porte de Pantin');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('58', 'bus', 'N14 Malrle de St-Ouen', 'Stallingrad');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('59', 'bus', 'N151 Gare de Mantes', 'Poissy');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('60', 'bus', 'N14 Malrle de St-Ouen', 'Charles de Gaulle');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('61', 'bus', 'N12 Romainville Carnot', 'Belleville');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('62', 'bus', 'N12 Romainville Carnot', 'Republique');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('63', 'bus', 'N16 Pont de Levallois', 'Quatre Routes');
INSERT INTO lugar_transporte (idLugarTte, tipoTte, linea, estacion) VALUES ('64', 'bus', 'N16 Pont de Levallois', 'Charles de Gaulle');
















