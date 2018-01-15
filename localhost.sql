-- phpMyAdmin SQL Dump
-- version 2.11.9.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Waktu pembuatan: 24. Nopember 2015 jam 14:30
-- Versi Server: 5.0.67
-- Versi PHP: 5.2.6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `dbpart`
--
CREATE DATABASE `dbpart` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `dbpart`;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbcustomer`
--

CREATE TABLE IF NOT EXISTS `tbcustomer` (
  `kodecustomer` varchar(15) NOT NULL,
  `namacustomer` varchar(35) NOT NULL,
  `telp` varchar(15) NOT NULL,
  `alamat` varchar(50) NOT NULL,
  PRIMARY KEY  (`kodecustomer`),
  KEY `kodecustomer` (`kodecustomer`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tbcustomer`
--

INSERT INTO `tbcustomer` (`kodecustomer`, `namacustomer`, `telp`, `alamat`) VALUES
('1000-05', 'BERSAUDARA MOTOR', '08888', 'JALNA'),
('1000-01', 'ANEKA MOTOR', '08888', 'JALAN'),
('1000-92', 'FLET USER 4', '0888', 'JALAN BANDANG'),
('1000-53', 'TOMS TRONIX', '0888', 'JALAN'),
('112', 'QWERTY', '1111', 'SDSDSDS');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbmaster`
--

CREATE TABLE IF NOT EXISTS `tbmaster` (
  `nopart` varchar(15) NOT NULL,
  `namapart` varchar(35) NOT NULL,
  `type` varchar(15) NOT NULL,
  `stock` int(4) NOT NULL,
  `hargabeli` int(12) NOT NULL,
  `hargajual` int(12) NOT NULL,
  PRIMARY KEY  (`nopart`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tbmaster`
--

INSERT INTO `tbmaster` (`nopart`, `namapart`, `type`, `stock`, `hargabeli`, `hargajual`) VALUES
('15601BZ030', 'OIL FILTER', 'INNOVA', 60, 42000, 48000),
('15601BZ010', 'OIL FILTER', 'AVANZA', 190, 45000, 50000),
('52119BZ330', 'FRONT BUMPER', 'AVANZA', 10, 850000, 920000),
('53301BZ220', 'ENGGINE HOOD', 'AVANZA', 2, 1750000, 1950000),
('67005BZ030', 'BACK DOOR', 'INNOVA', 2, 1750000, 2000000),
('178010C010', 'AIR FILTER', 'AVANZA', 11, 750000, 1000000),
('123', 'ABSORBER', 'AVANZA', 3, 250000, 350000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbpembelian`
--

CREATE TABLE IF NOT EXISTS `tbpembelian` (
  `notransaksi` varchar(15) NOT NULL,
  `tgl` date NOT NULL,
  `kodesupplier` varchar(15) NOT NULL,
  `nopart` varchar(15) NOT NULL,
  `namapart` varchar(35) NOT NULL,
  `hargabeli` int(10) NOT NULL,
  `jumlah` int(5) NOT NULL,
  `totalharga` int(10) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tbpembelian`
--

INSERT INTO `tbpembelian` (`notransaksi`, `tgl`, `kodesupplier`, `nopart`, `namapart`, `hargabeli`, `jumlah`, `totalharga`) VALUES
('001', '2015-11-01', '001', '52119BZ330', 'FRONT BUMPER', 920000, 1, 920000),
('001', '2015-11-01', '001', '561010K090', 'GLASS FRONT ', 1500000, 3, 4500000),
('001', '2015-11-01', '002', '52119BZ330', 'FRONT BUMPER', 920000, 1, 920000),
('002', '2015-11-06', '002', '15601BZ010', 'OIL FILTER', 45000, 20, 900000),
('003', '2015-11-24', '007', '15601BZ030', 'OIL FILTER', 42000, 10, 420000),
('003', '2015-11-24', '007', '178010C010', 'AIR FILTER', 750000, 1, 750000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbpenjualan`
--

CREATE TABLE IF NOT EXISTS `tbpenjualan` (
  `notransaksi` varchar(15) NOT NULL,
  `tgl` date NOT NULL,
  `kodecustomer` varchar(15) NOT NULL,
  `nopart` varchar(15) NOT NULL,
  `namapart` varchar(35) NOT NULL,
  `hargajual` int(10) NOT NULL,
  `jumlah` int(5) NOT NULL,
  `totalharga` int(10) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tbpenjualan`
--

INSERT INTO `tbpenjualan` (`notransaksi`, `tgl`, `kodecustomer`, `nopart`, `namapart`, `hargajual`, `jumlah`, `totalharga`) VALUES
('001', '2015-11-04', '1000-53', '52119BZ330', 'FRONT BUMPER', 920000, 3, 2760000),
('001', '2015-11-04', '1000-05', '15601BZ010', 'OIL FILTER', 50000, 1, 50000),
('001', '2015-11-04', '1000-01', '15601BZ010', 'OIL FILTER', 50000, 10, 500000),
('002', '2015-11-06', '1000-05', '53301BZ220', 'ENGGINE HOOD', 1950000, 1, 1950000),
('003', '2015-11-24', '112', '123', 'ABSORBER', 350000, 7, 2450000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbsupplier`
--

CREATE TABLE IF NOT EXISTS `tbsupplier` (
  `kodesupplier` varchar(15) NOT NULL,
  `namasupplier` varchar(35) NOT NULL,
  `telp` varchar(15) NOT NULL,
  `alamat` varchar(50) NOT NULL,
  PRIMARY KEY  (`kodesupplier`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tbsupplier`
--

INSERT INTO `tbsupplier` (`kodesupplier`, `namasupplier`, `telp`, `alamat`) VALUES
('002', 'KALLA TOYOTA', '08888', 'JALAN-JALAN'),
('001', 'AUTO 2000', '0888', 'JALN-JALAN'),
('007', 'JAMES', '11111', 'ASASASA');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbuser`
--

CREATE TABLE IF NOT EXISTS `tbuser` (
  `user` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY  (`user`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tbuser`
--

INSERT INTO `tbuser` (`user`, `password`) VALUES
('sandy', 'sandy'),
('admin', 'admin'),
('husain', 'husain'),
('ardi', 'ardi');
