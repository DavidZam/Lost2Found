-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 19-05-2018 a las 18:08:49
-- Versión del servidor: 5.5.59-0ubuntu0.14.04.1
-- Versión de PHP: 5.5.9-1ubuntu4.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `lost2found`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `anuncio_objeto`
--

CREATE TABLE IF NOT EXISTS `anuncio_objeto` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `tipoAnuncio` varchar(10) NOT NULL,
  `horaAnuncio` varchar(10) NOT NULL,
  `diaAnuncio` varchar(10) NOT NULL,
  `horaPerdidaHallazgo` varchar(10) NOT NULL,
  `color` varchar(20) DEFAULT NULL,
  `idUsuario` int(4) NOT NULL,
  `idLugar` int(4) NOT NULL,
  `nombreTabla` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_idUsuario4` (`idUsuario`),
  KEY `fk_idLugar1` (`idLugar`),
  KEY `fk_nombreTabla1` (`nombreTabla`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=23 ;

--
-- Volcado de datos para la tabla `anuncio_objeto`
--

INSERT INTO `anuncio_objeto` (`id`, `tipoAnuncio`, `horaAnuncio`, `diaAnuncio`, `horaPerdidaHallazgo`, `color`, `idUsuario`, `idLugar`, `nombreTabla`) VALUES
(1, 'Perdida', '19:09', '2018/05/04', '22:20', '-6381922', 7, 109, 'Telefono'),
(2, 'Perdida', '19:11', '2018/05/04', '4:30', '-10401463', 7, 110, 'Cartera'),
(6, 'Hallazgo', '19:49', '2018/05/05', '16:39', '-8831940', 1, 1, 'Cartera'),
(7, 'Hallazgo', '10:33', '2018/05/05', '19:30', '-8355712', 1, 116, 'Telefono'),
(8, 'Hallazgo', '11:49', '2018/05/06', '21:30', '-12566464', 1, 117, 'Telefono'),
(10, 'Hallazgo', '13:45', '2018/05/06', '16:30', '-6381922', 7, 1, 'Tarjeta bancaria'),
(14, 'Perdida', '12:44', '2018/05/04', '18:41', '-16777216', 7, 122, 'Cartera'),
(15, 'Perdida', '19:20', '2018/05/05', '16:30', '-6381922', 7, 123, 'Telefono'),
(17, 'Perdida', '00:19', '2018/05/04', '20:30', '-12566464', 6, 125, 'Telefono'),
(21, 'Perdida', '20:50', '2018/05/05', '18:45', '-6381922', 7, 128, 'Telefono'),
(22, 'Hallazgo', '12:17', '2018/05/12', '20:30', '-1499549', 1, 129, 'Otro');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `candidatos`
--

CREATE TABLE IF NOT EXISTS `candidatos` (
  `idObjetoPerdida` int(4) NOT NULL,
  `idObjetoHallazgo` int(4) NOT NULL,
  PRIMARY KEY (`idObjetoPerdida`,`idObjetoHallazgo`),
  KEY `fk_idObjeto7` (`idObjetoHallazgo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `caracteristica`
--

CREATE TABLE IF NOT EXISTS `caracteristica` (
  `nombreCampo` varchar(20) NOT NULL,
  `nombreTabla` varchar(20) NOT NULL,
  `determinante` int(4) NOT NULL,
  PRIMARY KEY (`nombreCampo`,`nombreTabla`),
  KEY `fk_nombreTabla2` (`nombreTabla`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Cartera`
--

CREATE TABLE IF NOT EXISTS `Cartera` (
  `idObjeto` int(4) NOT NULL,
  `marca` varchar(255) DEFAULT NULL,
  `documentacion` text,
  PRIMARY KEY (`idObjeto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `Cartera`
--

INSERT INTO `Cartera` (`idObjeto`, `marca`, `documentacion`) VALUES
(2, 'Zara', ''),
(6, 'Zara', ''),
(14, 'Zara', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `chat`
--

CREATE TABLE IF NOT EXISTS `chat` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `nombreChat` varchar(100) NOT NULL,
  `idUsuario1` int(4) NOT NULL,
  `idUsuario2` int(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_idUsuario1` (`idUsuario1`),
  KEY `fk_idUsuario2` (`idUsuario2`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=26 ;

--
-- Volcado de datos para la tabla `chat`
--

INSERT INTO `chat` (`id`, `nombreChat`, `idUsuario1`, `idUsuario2`) VALUES
(24, 'Chat de David y Luis', 1, 7),
(25, 'Chat de David y Jesus', 1, 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contiene`
--

CREATE TABLE IF NOT EXISTS `contiene` (
  `idLugarContenedor` int(4) NOT NULL,
  `idLugarContenido` int(4) NOT NULL,
  PRIMARY KEY (`idLugarContenedor`),
  KEY `fk_idLugarTte2` (`idLugarContenido`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `conversor`
--

CREATE TABLE IF NOT EXISTS `conversor` (
  `nombreTabla` varchar(20) NOT NULL,
  `tipoOpenData` varchar(20) NOT NULL,
  `tipoObjetoFrances` varchar(80) NOT NULL,
  PRIMARY KEY (`tipoObjetoFrances`),
  KEY `fk_nombreTabla3` (`nombreTabla`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `conversor`
--

INSERT INTO `conversor` (`nombreTabla`, `tipoOpenData`, `tipoObjetoFrances`) VALUES
('Tarjeta bancaria', 'SNCF', 'Carte de crédit'),
('Tarjeta transporte', 'SNCF', 'Carte d´abonnement'),
('Cartera', 'SNCF', 'Porte-monnaie, portefeuille'),
('Telefono', 'SNCF', 'Téléphone portable');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estacion_opendata`
--

CREATE TABLE IF NOT EXISTS `estacion_opendata` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `hora` date NOT NULL,
  `objeto` varchar(50) NOT NULL,
  `tipoObjeto` varchar(50) NOT NULL,
  `idLugarTte` int(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_idLugarTte3` (`idLugarTte`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lugar`
--

CREATE TABLE IF NOT EXISTS `lugar` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=130 ;

--
-- Volcado de datos para la tabla `lugar`
--

INSERT INTO `lugar` (`id`) VALUES
(1),
(2),
(3),
(4),
(5),
(6),
(7),
(8),
(9),
(10),
(11),
(12),
(13),
(14),
(15),
(16),
(17),
(18),
(19),
(20),
(21),
(22),
(23),
(24),
(25),
(26),
(27),
(28),
(29),
(30),
(31),
(32),
(33),
(34),
(35),
(36),
(37),
(38),
(39),
(40),
(41),
(42),
(43),
(44),
(45),
(46),
(47),
(48),
(49),
(50),
(51),
(52),
(53),
(54),
(55),
(56),
(57),
(58),
(59),
(60),
(61),
(62),
(63),
(64),
(68),
(69),
(70),
(71),
(80),
(82),
(83),
(84),
(85),
(86),
(87),
(88),
(89),
(90),
(91),
(92),
(93),
(94),
(95),
(96),
(97),
(98),
(99),
(100),
(101),
(102),
(104),
(108),
(109),
(110),
(111),
(112),
(113),
(114),
(115),
(116),
(117),
(119),
(121),
(122),
(123),
(124),
(125),
(126),
(127),
(128),
(129);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lugar_concreto`
--

CREATE TABLE IF NOT EXISTS `lugar_concreto` (
  `idLugar` int(4) NOT NULL,
  `calle` varchar(20) NOT NULL,
  `numero` int(3) NOT NULL,
  `codigoPostal` int(5) NOT NULL,
  PRIMARY KEY (`idLugar`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `lugar_concreto`
--

INSERT INTO `lugar_concreto` (`idLugar`, `calle`, `numero`, `codigoPostal`) VALUES
(68, 'Bravo Murillo', 21, 28015),
(69, 'Eloy Gonzalo', 7, 28010),
(70, 'Santa Engracia', 38, 28010),
(71, 'Cardenal Cisneros', 3, 28010),
(83, 'Bravo Murillo', 8, 28010),
(88, 'Bravo Murillo', 8, 28010),
(95, 'Bravo Murillo', 8, 28010),
(97, 'Santisima Trinidad', 18, 28010),
(123, 'Bravo Murillo', 8, 28010),
(124, 'Bravo Murillo', 12, 28010);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lugar_mapa`
--

CREATE TABLE IF NOT EXISTS `lugar_mapa` (
  `idLugar` int(4) NOT NULL AUTO_INCREMENT,
  `latitud` decimal(10,8) NOT NULL,
  `longitud` decimal(11,8) NOT NULL,
  PRIMARY KEY (`idLugar`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=130 ;

--
-- Volcado de datos para la tabla `lugar_mapa`
--

INSERT INTO `lugar_mapa` (`idLugar`, `latitud`, `longitud`) VALUES
(108, '40.42860583', '-3.70991316'),
(109, '40.43347035', '-3.70413601'),
(110, '40.43289411', '-3.70878696'),
(111, '40.42851574', '-3.70903172'),
(112, '40.43179725', '-3.70506540'),
(113, '40.43150453', '-3.70478243'),
(114, '40.43217674', '-3.70412763'),
(115, '40.43337210', '-3.70383762'),
(116, '40.42958509', '-3.70564744'),
(117, '40.43339430', '-3.70430298'),
(119, '40.45137630', '-3.72711090'),
(125, '40.42976681', '-3.70599076'),
(126, '40.45060935', '-3.73084612'),
(129, '40.44810414', '-3.72794565');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lugar_transporte`
--

CREATE TABLE IF NOT EXISTS `lugar_transporte` (
  `idLugarTte` int(4) NOT NULL AUTO_INCREMENT,
  `tipoTte` varchar(10) NOT NULL,
  `linea` varchar(100) DEFAULT NULL,
  `estacion` varchar(30) NOT NULL,
  PRIMARY KEY (`idLugarTte`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=129 ;

--
-- Volcado de datos para la tabla `lugar_transporte`
--

INSERT INTO `lugar_transporte` (`idLugarTte`, `tipoTte`, `linea`, `estacion`) VALUES
(1, 'metro', '1 La Defense - Chateau de Vincennes', 'Champs Elysees'),
(2, 'metro', '1 La Defense - Chateau de Vincennes', 'Les Sablons'),
(3, 'metro', '1 La Defense - Chateau de Vincennes', 'St-Paul'),
(4, 'metro', '1 La Defense - Chateau de Vincennes', 'Berault'),
(5, 'metro', '1 La Defense - Chateau de Vincennes', 'St-Mande'),
(6, 'metro', '2 Porte Dauphine - Nation', 'Ternes'),
(7, 'metro', '2 Porte Dauphine - Nation', 'Monceau'),
(8, 'metro', '2 Porte Dauphine - Nation', 'Courcelles'),
(9, 'metro', '2 Porte Dauphine - Nation', 'Rome'),
(10, 'metro', '3 Pont de Levallois Becon - Gallieni', 'Wagram'),
(12, 'metro', '3 Pont de Levallois Becon - Gallieni', 'Jasmin'),
(13, 'metro', '3 Pont de Levallois Becon - Gallieni', 'Ranelagh'),
(14, 'metro', '3 Pont de Levallois Becon - Gallieni', 'Anatole France'),
(15, 'metro', '3 Pont de Levallois Becon - Gallieni', 'Iena'),
(16, 'metro', '3bis Gambetta - Porte des Lilas', 'Pelleport'),
(17, 'metro', '3bis Gambetta - Porte des Lilas', 'Saint-Fargeau'),
(18, 'metro', '4 Porte de Clignancourt - Mairie de Montrouge', 'Simplon'),
(19, 'metro', '4 Porte de Clignancourt - Mairie de Montrouge', 'Chateau d Eau'),
(20, 'metro', '4 Porte de Clignancourt - Mairie de Montrouge', 'Etienne Marcel'),
(21, 'metro', '5 Bobigny Pablo Picasso - Place d Italie', 'Saint Marcel'),
(22, 'metro', '5 Bobigny Pablo Picasso - Place d Italie', 'Campo Formio'),
(23, 'metro', '5 Bobigny Pablo Picasso - Place d Italie', 'Bastille'),
(24, 'metro', '5 Bobigny Pablo Picasso - Place d Italie', 'Richard Lenoir'),
(25, 'metro', '6 Charles de Gaulle Etoile - Nation', 'Picpus'),
(26, 'metro', '6 Charles de Gaulle Etoile - Nation', 'Bel-Air'),
(27, 'metro', '6 Charles de Gaulle Etoile - Nation', 'Chevaleret'),
(28, 'metro', '7 Louis Leblanc - Pre Saint Gervais', 'Place Monge'),
(29, 'metro', '7 Louis Leblanc - Pre Saint Gervais', 'Les Gobelins'),
(30, 'metro', '7 Louis Leblanc - Pre Saint Gervais', 'Jussieu'),
(31, 'metro', '7 Louis Leblanc - Pre Saint Gervais', 'Pyramides'),
(32, 'metro', '8 Balard - Pointe du Lac', 'Commerce'),
(33, 'metro', '8 Balard - Pointe du Lac', 'Ecole Militaire'),
(34, 'metro', '8 Balard - Pointe du Lac', 'Lourmel'),
(35, 'metro', '9 Pont de Sevres - Mairie de Montreuil', 'Robespierre'),
(36, 'metro', '9 Pont de Sevres - Mairie de Montreuil', 'Voltaire'),
(37, 'metro', '9 Pont de Sevres - Mairie de Montreuil', 'Charonne'),
(38, 'metro', '10 Boulogne Pont de Saint Cloud - Gare d Austerlitz', 'Segur'),
(39, 'metro', '10 Boulogne Pont de Saint Cloud - Gare d Austerlitz', 'Vaneau'),
(40, 'metro', '10 Boulogne Pont de Saint Cloud - Gare d Austerlitz', 'Mirabeau'),
(41, 'metro', '11 Chatelet - Mairie des Lilas', 'Belleville'),
(42, 'metro', '11 Chatelet - Mairie des Lilas', 'Rambuteau'),
(43, 'metro', '11 Chatelet - Mairie des Lilas', 'Jourdain'),
(44, 'metro', '12 Front Populaire - Mairie d Issy', 'Abbesses'),
(45, 'metro', '12 Front Populaire - Mairie d Issy', 'Pigalle'),
(46, 'metro', '12 Front Populaire - Mairie d Issy', 'Solferino'),
(47, 'metro', '13 Gare Saint Lazare - Olympiades', 'Plaisance'),
(48, 'metro', '13 Gare Saint Lazare - Olympiades', 'Gaite'),
(49, 'metro', '13 Gare Saint Lazare - Olympiades', 'Varenne'),
(50, 'bus', 'N51 Gare d Enghlen', 'Genevilliers'),
(51, 'bus', 'N51 Gare d Enghlen', 'Saint-Denis'),
(52, 'bus', 'N51 Gare d Enghlen', 'Louvre'),
(53, 'bus', 'N43 Gare de Sarcelles', 'Malrie de Stains'),
(54, 'bus', 'N43 Gare de Sarcelles', 'Porte de la Chapelle'),
(55, 'bus', 'N14 Malrie de St-Ouen', 'Port Royal'),
(56, 'bus', 'N14 Malrie de St-Ouen', 'Denfert Rochereau'),
(57, 'bus', 'N13 Malrie d Issy', 'Porte de Pantin'),
(58, 'bus', 'N14 Malrle de St-Ouen', 'Stallingrad'),
(59, 'bus', 'N151 Gare de Mantes', 'Poissy'),
(60, 'bus', 'N14 Malrle de St-Ouen', 'Charles de Gaulle'),
(61, 'bus', 'N12 Romainville Carnot', 'Belleville'),
(62, 'bus', 'N12 Romainville Carnot', 'Republique'),
(63, 'bus', 'N16 Pont de Levallois', 'Quatre Routes'),
(64, 'bus', 'N16 Pont de Levallois', 'Charles de Gaulle'),
(80, 'tren', NULL, 'Lyon Part Dieu'),
(82, 'tren', NULL, 'Lyon Perrache'),
(84, 'tren', NULL, 'Paris Montparnasse'),
(85, 'tren', NULL, 'Paris Est'),
(86, 'tren', NULL, 'Lille Europe'),
(87, 'tren', NULL, 'Paris Gare de Lyon'),
(89, 'tren', NULL, 'Strasbourg'),
(90, 'tren', NULL, 'Paris Gare du Nord'),
(91, 'tren', NULL, 'Paris Gare du Nord'),
(92, 'tren', NULL, 'Paris Gare de Lyon'),
(93, 'tren', NULL, 'Paris Gare de Lyon'),
(94, 'tren', NULL, 'Paris Gare de Lyon'),
(96, 'tren', NULL, 'Strasbourg'),
(98, 'tren', NULL, 'Paris Saint-Lazare'),
(99, 'tren', NULL, 'Nice'),
(100, 'tren', NULL, 'Lyon Part Dieu'),
(101, 'tren', NULL, 'Strasbourg'),
(102, 'tren', NULL, 'Paris Montparnasse'),
(121, 'tren', NULL, 'Strasbourg'),
(122, 'tren', NULL, 'Strasbourg'),
(127, 'tren', NULL, 'Strasbourg'),
(128, 'tren', NULL, 'Paris Est');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `matching`
--

CREATE TABLE IF NOT EXISTS `matching` (
  `idObjetoPerdida` int(4) NOT NULL,
  `idObjetoHallazgo` int(4) NOT NULL,
  `fase` int(4) NOT NULL,
  KEY `fk_idObjeto8` (`idObjetoPerdida`),
  KEY `fk_idObjeto9` (`idObjetoHallazgo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `msg`
--

CREATE TABLE IF NOT EXISTS `msg` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `texto` text NOT NULL,
  `horaMsg` varchar(10) NOT NULL,
  `idChat` int(4) NOT NULL,
  `idUsuario` int(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_idChat1` (`idChat`),
  KEY `fk_idUsuario3` (`idUsuario`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=233 ;

--
-- Volcado de datos para la tabla `msg`
--

INSERT INTO `msg` (`id`, `texto`, `horaMsg`, `idChat`, `idUsuario`) VALUES
(227, '/tw93Ua2OPRo3GfL8DI/Qj8o4d8XB0O6Pu5Hyd1qpV8ComkOw0MMR5X96sPerHmf9ob1k6+P103FRIrK8rdqyA==', '18:00', 24, 7),
(228, '0RI7V8Cp5nJCegkf2EUlVucj0TygEXvOIN5GmlGGiX/6l8LpjoCNMa+JhQ0RNxVbFZYvBiOIUfIqyj+4MmsyAg==', '18:01', 24, 1),
(229, 'T1cg7QPBis+gKvMHdI1CqcKicjq2JTWrCwp6PJN53u8=', '18:01', 24, 1),
(230, 'vXZB1WRMdvovMRqF4Gg5WfH4wTs8KvOYr2Uh6H+HYQEdXNrWIuUPpXHJUFvVikKnoPIMIZRzfp3+QAWtiK+VUQ==', '18:01', 24, 1),
(231, '5lF61r0xF8ITH1OfIKKSrLGODR/hZx7YDG2INAXbq3MFG+JV/az5WBWDjTSWLPIdWODcXsusmgRMmdLQVxTWIQ==', '18:01', 24, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Otro`
--

CREATE TABLE IF NOT EXISTS `Otro` (
  `idObjeto` int(4) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `descripcion` text,
  PRIMARY KEY (`idObjeto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `Otro`
--

INSERT INTO `Otro` (`idObjeto`, `nombre`, `descripcion`) VALUES
(22, 'Bufanda', 'Algodon');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Tarjeta bancaria`
--

CREATE TABLE IF NOT EXISTS `Tarjeta bancaria` (
  `idObjeto` int(4) NOT NULL,
  `banco` varchar(100) DEFAULT NULL,
  `datosPropietario` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idObjeto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `Tarjeta bancaria`
--

INSERT INTO `Tarjeta bancaria` (`idObjeto`, `banco`, `datosPropietario`) VALUES
(10, 'Bankia', 'Pepito Gomez');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Tarjeta transporte`
--

CREATE TABLE IF NOT EXISTS `Tarjeta transporte` (
  `idObjeto` int(4) NOT NULL,
  `datosPropietario` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idObjeto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Telefono`
--

CREATE TABLE IF NOT EXISTS `Telefono` (
  `idObjeto` int(4) NOT NULL,
  `marca` varchar(20) DEFAULT NULL,
  `modelo` varchar(20) DEFAULT NULL,
  `tara` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`idObjeto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `Telefono`
--

INSERT INTO `Telefono` (`idObjeto`, `marca`, `modelo`, `tara`) VALUES
(1, 'Apple', 'iPhone 4', ''),
(7, 'Apple', 'iPhone 5', ''),
(8, 'Apple', '5', ''),
(15, 'Xiaomi', 'Redmi Note 5', ''),
(17, 'Apple', '6', 'pantalla algo rota'),
(21, 'Apple', '9', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo`
--

CREATE TABLE IF NOT EXISTS `tipo` (
  `nombreTabla` varchar(20) NOT NULL,
  `descripcion` text NOT NULL,
  PRIMARY KEY (`nombreTabla`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tipo`
--

INSERT INTO `tipo` (`nombreTabla`, `descripcion`) VALUES
('Cartera', 'carteras que se hayan encontrado o perdido'),
('Otro', 'objetos de otro tipo no contemplado que se hayan podido encontrar o perder'),
('Tarjeta bancaria', 'tarjetas bancarias que se hayan podido encontrar o perder'),
('Tarjeta transporte', 'tarjetas de transporte que se hayan podido encontrar o perder'),
('Telefono', 'telefonos que se hayan podido encontrar o perder');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `email` varchar(20) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `contrasena` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `email`, `nombre`, `contrasena`) VALUES
(1, 'david@gmail.com', 'David', '$2y$10$/1zel.iLcMGUIWwWTtMTS.G2uiFz7IsfAmaQA3mSZsE4HvwuVpjpS'),
(4, 'caro@ucm.es', 'Carolina', '$2y$10$9dk4dT0vmADV.97CI1i9KO4TalOS91rtIL4lbrEad75ohF4uz.vs2'),
(5, 'paula@ucm.es', 'Paula Gomez', '$2y$10$mGIu7O4SCQWf9z9Ce9b7SuUhw0F2mtoUpfVFMdgN6Wa0qi0e0bn1a'),
(6, 'jesus@gmail.com', 'Jesus', '$2y$10$e3R5J8DabBsjhtu3.IHlsey4pqreM8arU4UZ0YnvoVeCVngrGl9T.'),
(7, 'luis@gmail.com', 'Luis', '$2y$10$LFz0vylGrlnNqEIdpPljxe3R1.XOnyB4i8JDsFbIVUtqL3SFQryyu');

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `anuncio_objeto`
--
ALTER TABLE `anuncio_objeto`
  ADD CONSTRAINT `fk_idLugar1` FOREIGN KEY (`idLugar`) REFERENCES `lugar` (`id`),
  ADD CONSTRAINT `fk_idUsuario4` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `fk_nombreTabla1` FOREIGN KEY (`nombreTabla`) REFERENCES `tipo` (`nombreTabla`);

--
-- Filtros para la tabla `candidatos`
--
ALTER TABLE `candidatos`
  ADD CONSTRAINT `fk_idObjeto6` FOREIGN KEY (`idObjetoPerdida`) REFERENCES `anuncio_objeto` (`id`),
  ADD CONSTRAINT `fk_idObjeto7` FOREIGN KEY (`idObjetoHallazgo`) REFERENCES `anuncio_objeto` (`id`);

--
-- Filtros para la tabla `caracteristica`
--
ALTER TABLE `caracteristica`
  ADD CONSTRAINT `fk_nombreTabla2` FOREIGN KEY (`nombreTabla`) REFERENCES `tipo` (`nombreTabla`);

--
-- Filtros para la tabla `Cartera`
--
ALTER TABLE `Cartera`
  ADD CONSTRAINT `fk_idObjeto3` FOREIGN KEY (`idObjeto`) REFERENCES `anuncio_objeto` (`id`);

--
-- Filtros para la tabla `chat`
--
ALTER TABLE `chat`
  ADD CONSTRAINT `fk_idUsuario1` FOREIGN KEY (`idUsuario1`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `fk_idUsuario2` FOREIGN KEY (`idUsuario2`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `contiene`
--
ALTER TABLE `contiene`
  ADD CONSTRAINT `fk_idLugarTte1` FOREIGN KEY (`idLugarContenedor`) REFERENCES `lugar_transporte` (`idLugarTte`),
  ADD CONSTRAINT `fk_idLugarTte2` FOREIGN KEY (`idLugarContenido`) REFERENCES `lugar_transporte` (`idLugarTte`);

--
-- Filtros para la tabla `conversor`
--
ALTER TABLE `conversor`
  ADD CONSTRAINT `fk_nombreTabla3` FOREIGN KEY (`nombreTabla`) REFERENCES `tipo` (`nombreTabla`);

--
-- Filtros para la tabla `estacion_opendata`
--
ALTER TABLE `estacion_opendata`
  ADD CONSTRAINT `fk_idLugarTte3` FOREIGN KEY (`idLugarTte`) REFERENCES `lugar_transporte` (`idLugarTte`);

--
-- Filtros para la tabla `lugar_concreto`
--
ALTER TABLE `lugar_concreto`
  ADD CONSTRAINT `fk_idLugar3` FOREIGN KEY (`idLugar`) REFERENCES `lugar` (`id`);

--
-- Filtros para la tabla `lugar_mapa`
--
ALTER TABLE `lugar_mapa`
  ADD CONSTRAINT `fk_idLugar4` FOREIGN KEY (`idLugar`) REFERENCES `lugar` (`id`);

--
-- Filtros para la tabla `lugar_transporte`
--
ALTER TABLE `lugar_transporte`
  ADD CONSTRAINT `fk_idLugar2` FOREIGN KEY (`idLugarTte`) REFERENCES `lugar` (`id`);

--
-- Filtros para la tabla `matching`
--
ALTER TABLE `matching`
  ADD CONSTRAINT `fk_idObjeto8` FOREIGN KEY (`idObjetoPerdida`) REFERENCES `anuncio_objeto` (`id`),
  ADD CONSTRAINT `fk_idObjeto9` FOREIGN KEY (`idObjetoHallazgo`) REFERENCES `anuncio_objeto` (`id`);

--
-- Filtros para la tabla `msg`
--
ALTER TABLE `msg`
  ADD CONSTRAINT `fk_idChat1` FOREIGN KEY (`idChat`) REFERENCES `chat` (`id`),
  ADD CONSTRAINT `fk_idUsuario3` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `Otro`
--
ALTER TABLE `Otro`
  ADD CONSTRAINT `fk_idObjeto5` FOREIGN KEY (`idObjeto`) REFERENCES `anuncio_objeto` (`id`);

--
-- Filtros para la tabla `Tarjeta bancaria`
--
ALTER TABLE `Tarjeta bancaria`
  ADD CONSTRAINT `fk_idObjeto1` FOREIGN KEY (`idObjeto`) REFERENCES `anuncio_objeto` (`id`);

--
-- Filtros para la tabla `Tarjeta transporte`
--
ALTER TABLE `Tarjeta transporte`
  ADD CONSTRAINT `fk_idObjeto2` FOREIGN KEY (`idObjeto`) REFERENCES `anuncio_objeto` (`id`);

--
-- Filtros para la tabla `Telefono`
--
ALTER TABLE `Telefono`
  ADD CONSTRAINT `fk_idObjeto4` FOREIGN KEY (`idObjeto`) REFERENCES `anuncio_objeto` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
