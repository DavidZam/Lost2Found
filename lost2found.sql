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
  modelo varchar(20) DEFAULT NULL,
  marca varchar(20) DEFAULT NULL,
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

CREATE TABLE tarjeta_bancaria (
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

CREATE TABLE tarjeta_transporte (
  idObjeto int(4) NOT NULL,
  numTarjeta int(10) DEFAULT NULL,
  datosPropietario varchar(30) DEFAULT NULL,
  contenido varchar(20) DEFAULT NULL,
  PRIMARY KEY (idObjeto),
  CONSTRAINT fk_idObjeto2 FOREIGN KEY (idObjeto) REFERENCES anuncio_objeto (id)  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cartera`
--

CREATE TABLE cartera (
  idObjeto int(4) NOT NULL,
  documentacion text,
  PRIMARY KEY (idObjeto),
  CONSTRAINT fk_idObjeto3 FOREIGN KEY (idObjeto) REFERENCES anuncio_objeto (id) 
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `telefono`
--

CREATE TABLE telefono (
  idObjeto int(4) NOT NULL,
  tara varchar(10) DEFAULT NULL,
  IMEI int(15) DEFAULT NULL,
  PRIMARY KEY (idObjeto),
  CONSTRAINT fk_idObjeto4 FOREIGN KEY (idObjeto) REFERENCES anuncio_objeto (id) 
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `otro`
--

CREATE TABLE otro (
  idObjeto int(4) NOT NULL,
  otro varchar(10) DEFAULT NULL,
  PRIMARY KEY (idObjeto),
  CONSTRAINT fk_idObjeto5 FOREIGN KEY (idObjeto) REFERENCES anuncio_objeto (id)   
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lugar_transporte`
--

CREATE TABLE lugar_transporte (
  idLugarTte int(4) NOT NULL AUTO_INCREMENT,
  linea varchar(10) NOT NULL,
  estacion varchar(10) NOT NULL,
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

