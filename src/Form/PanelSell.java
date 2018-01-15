/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import Koneksi.Koneksi;
import Validasi.OnlyDigit;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author SANDYS
 */
public class PanelSell extends javax.swing.JPanel {

    /**
     * Creates new form PanelSell
     */
    Connection connection;
    
    public PanelSell() {
        initComponents();
        
        txtharga.setDocument(new OnlyDigit().getOnlyDigit());
        txtqty.setDocument(new OnlyDigit().getOnlyDigit());
        txtbayar.setDocument(new OnlyDigit().getOnlyDigit());
        
        isitabelmaster();
        isicombo();
        isitabeltransaksi();
        
    }
    
    public void addActionListenerCancel (ActionListener l) {
        btnCancel.addActionListener(l);
    }

    public JComboBox getCmbkodecust() {
        return cmbkodecust;
    }
    
    public void isitabelmaster () {
        
        Object header [] = {"No Part", "Nama Part", "Stock", "Harga"};
        
        DefaultTableModel model = new DefaultTableModel(null, header) {
            public boolean isCellEditable(int row, int column) {
            return false;
            }
        };
        tabelMaster.setModel(model);
        
        String sql = "SELECT * FROM tbmaster ORDER BY nopart";
        
        try {
            connection = Koneksi.sambung();
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                String kolom1 = rs.getString(1);
                String kolom2 = rs.getString(2);
                String kolom3 = rs.getString(4);
                String kolom4 = rs.getString(6);
                
                String kolom [] = {kolom1, kolom2, kolom3, kolom4 };
                
                model.addRow(kolom);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void isicombo () {
        
        String sql = "SELECT kodecustomer FROM tbcustomer ORDER BY kodecustomer";
        
        try {
            connection = Koneksi.sambung();
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                cmbkodecust.addItem(rs.getString("kodecustomer"));
            }
               rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
            cmbkodecust.setSelectedIndex(-1);
    }
    
     private void isinamacust () {
        
        String sql = "SELECT * FROM tbcustomer WHERE kodecustomer = '"+cmbkodecust.getSelectedItem()+"' ";
        
        try {
            connection = Koneksi.sambung();
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                txtnamacust.setText(rs.getString("namacustomer"));
            }
                rs.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
     
    private void isifieldmaster () {
        int xrow = tabelMaster.getSelectedRow();
        String nopartt = (String) tabelMaster.getValueAt(xrow, 0);
        String namapart = (String) tabelMaster.getValueAt(xrow, 1);
        String harga = (String) tabelMaster.getValueAt(xrow, 3);
        
        txtnopart.setText(nopartt);
        txtnamapart.setText(namapart);
        txtharga.setText(harga);
    }
    
    
    private void isicari () {
        
        Object header [] = {"No Part", "Nama Part", "Stock", "Harga"};
        DefaultTableModel model = new DefaultTableModel(null, header) {
            public boolean isCellEditable(int row, int column) {
            return false;
            }
        };
        tabelMaster.setModel(model);
        
        String sql = "SELECT * FROM tbmaster WHERE namapart = '"+txtnamapart.getText()+"' ";
        
        try {
            connection = Koneksi.sambung();
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                String kolom1 = rs.getString(1);
                String kolom2 = rs.getString(2);
                String kolom3 = rs.getString(4);
                String kolom4 = rs.getString(6);
                
                String kolom [] = {kolom1, kolom2, kolom3, kolom4 };
                
                model.addRow(kolom);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }   
    }
    
     public void add () {
        String cek = "Select * FROM tbpenjualan WHERE notransaksi='" + txtnotransaksi.getText() + "' AND"
                + " nopart ='" + txtnopart.getText() + "' AND"
                + " kodecustomer='" + cmbkodecust.getSelectedItem() + "'";
        String tambah = "UPDATE tbpenjualan SET jumlah=jumlah + ?,totalharga= totalharga + ? WHERE"
                + " notransaksi='" + txtnotransaksi.getText() + "' AND "
                + "nopart='" + txtnopart.getText() + "' AND"
                + " kodecustomer='" + cmbkodecust.getSelectedItem() + "'";
        String insert = "INSERT INTO tbpenjualan (notransaksi,tgl,kodecustomer,"
            + "nopart,namapart,hargajual,jumlah,totalharga) VALUES (?,?,?,?,?,?,?,?);" ;
        
        
        if (txtnotransaksi.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "No Transaksi Masih kosong !",
                            "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (txttgl.getDate().equals(null)) {
                JOptionPane.showMessageDialog(null, "Tanggal Masih kosong !",
                            "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (cmbkodecust.getSelectedItem().equals("")) {
                JOptionPane.showMessageDialog(null, "KodeCustomer Masih kosong !",
                            "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (txtnopart.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Nopart Masih kosong !",
                            "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (txtnamapart.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Nama Part Masih kosong !",
                            "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (txtharga.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Harga Masih kosong !",
                            "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (txtqty.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Qty Masih kosong !",
                            "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (txttotal.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Sub Total Masih kosong !",
                            "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                PreparedStatement statement = null;
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(cek);
                if (rs.next()) {
                    statement = connection.prepareStatement(tambah);
                
                    int jumlah = Integer.parseInt(txtqty.getText());
                    statement.setInt(1, jumlah);
                
                    int hargatotal = Integer.parseInt(txttotal.getText());
                    statement.setInt(2, hargatotal);
                    statement.executeUpdate();
                   
                    updatestock();
                    isitabeltransaksi();
                    isitabelmaster();
                    totalpembelian();
                    loadtransaksi();
                    clear();
                
                } else {
                    statement = connection.prepareStatement(insert);
                    statement.setString(1, txtnotransaksi.getText());
                
                    SimpleDateFormat t5 = new SimpleDateFormat("yyyy-MM-dd");
                    String tgl =  (String) t5.format (txttgl.getDate());
                    statement.setString(2, tgl);
                
                    statement.setString(3, cmbkodecust.getSelectedItem().toString());
                    statement.setString(4, txtnopart.getText());
                    statement.setString(5, txtnamapart.getText());
                    
                    int harga = Integer.parseInt(txtharga.getText());
                    statement.setInt(6, harga);
                
                    int jumlah = Integer.parseInt(txtqty.getText());
                    statement.setInt(7, jumlah);
                
                    int totalharga = Integer.parseInt(txttotal.getText());
                    statement.setInt(8, totalharga);
                    statement.executeUpdate();
               
                    updatestock();
                    String a = txtnotransaksi.getText();
                    clear();
                    isitabeltransaksi();
                    isitabelmaster();
                    txtnotransaksi.setText(a);
                    loadtransaksi();
                    totalpembelian();
                    
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } 
            }
    }
     
    private void isitabeltransaksi () {
        
        Object header [] = {"No Transaksi", "Tgl Transaksi", "Kode Customer",
                            "No Part", "Nama Part", "Harga", "jumlah", "Total Harga"};
        
        DefaultTableModel model = new DefaultTableModel(null, header) {
            public boolean isCellEditable(int row, int column) {
            return false;
            }
        };
        tabelTransaksi.setModel(model);
        
        String sql = "SELECT * FROM tbpenjualan ORDER BY notransaksi";
        
        try {
            connection = Koneksi.sambung();
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                String kolom1 = rs.getString(1);
                String kolom2 = rs.getString(2);
                String kolom3 = rs.getString(3);
                String kolom4 = rs.getString(4);
                String kolom5 = rs.getString(5);
                String kolom6 = rs.getString(6);
                String kolom7 = rs.getString(7);
                String kolom8 = rs.getString(8);
                
                String kolom [] = {kolom1, kolom2, kolom3, kolom4,
                                    kolom5, kolom6, kolom7, kolom8};
                
                model.addRow(kolom);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    private void isifieldtransaksi () {
        int xrow = tabelTransaksi.getSelectedRow();
        String notransaksi = (String) tabelTransaksi.getValueAt(xrow, 0);
        String tgl = (String) tabelTransaksi.getValueAt(xrow, 1);
        String kodesup = (String) tabelTransaksi.getValueAt(xrow, 2);
        String nopart = (String) tabelTransaksi.getValueAt(xrow, 3);
        String namapart = (String) tabelTransaksi.getValueAt(xrow, 4);
        String harga = (String) tabelTransaksi.getValueAt(xrow, 5);
        String qty = (String) tabelTransaksi.getValueAt(xrow, 6);
        String total = (String) tabelTransaksi.getValueAt(xrow, 7);
        
        txtnotransaksi.setText(notransaksi);
        
        SimpleDateFormat t5 = new SimpleDateFormat("dd-MM-yyyy");
        Date date =  Date.valueOf(tgl);
        t5.format(date);
        txttgl.setDate(date);
        
        cmbkodecust.setSelectedItem(kodesup);
        txtnopart.setText(nopart);
        txtnamapart.setText(namapart);
        txtharga.setText(harga);
        txtqty.setText(qty);
        txttotal.setText(total);
    }
    
    public void totalpembelian () {
        
        int jumlahBaris = tabelTransaksi.getRowCount();
        int totalBiaya = 0;
        int totalharga;
        
        TableModel tabelModel;
        tabelModel = tabelTransaksi.getModel();
        
        for (int i=0; i<jumlahBaris; i++){
            totalharga = Integer.parseInt(tabelModel.getValueAt(i, 7).toString());
            totalBiaya = totalBiaya + totalharga;
        }
            txttotalpembelian.setText(String.valueOf(totalBiaya));
        
    }
    
    public void kembalian () {
        
        int totalbayar = Integer.parseInt(txttotalpembelian.getText());
        int bayar = Integer.parseInt(txtbayar.getText());
        int kembalian = bayar - totalbayar;
        
        txtkembalian.setText(String.valueOf(kembalian));
        
    }
    
    private void loadtransaksi () {
        
        Object header [] = {"No Transaksi", "Tgl Transaksi", "Kode Customer",
                            "No Part", "Nama Part", "Harga", "jumlah", "Total Harga"};
        
        DefaultTableModel model = new DefaultTableModel(null, header) {
            public boolean isCellEditable(int row, int column) {
            return false;
            }
        };
        tabelTransaksi.setModel(model); 
        
        String load="Select * from tbpenjualan where notransaksi='" + txtnotransaksi.getText()+ "'";
         
         try {
            connection = Koneksi.sambung();
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(load);
            while (rs.next()) {
                String kolom1 = rs.getString(1);
                String kolom2 = rs.getString(2);
                String kolom3 = rs.getString(3);
                String kolom4 = rs.getString(4);
                String kolom5 = rs.getString(5);
                String kolom6 = rs.getString(6);
                String kolom7 = rs.getString(7);
                String kolom8 = rs.getString(8);
                
                String kolom [] = {kolom1, kolom2, kolom3, kolom4,
                                    kolom5, kolom6, kolom7, kolom8};
                
                model.addRow(kolom);
            }    
         } catch (Exception e) {
             System.out.println(e.getMessage());
         }
        
    }
    
    private void updatestock () {
        
        String nopart = txtnopart.getText();
        String jumlah = txtqty.getText();
        String sqlstock = "SELECT stock FROM tbmaster WHERE nopart ='"+nopart+"' ";
        try {
            connection = Koneksi.sambung();
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sqlstock);
            while (rs.next()) {
                String stocklama = rs.getString(1);
                int stock = Integer.parseInt(stocklama) - Integer.parseInt(jumlah);
                String sql =  "UPDATE tbmaster SET stock = '"+stock+"' WHERE nopart = '" +nopart+ "' ";
                try {
                   connection = Koneksi.sambung();
                   Statement stmn = connection.createStatement();
                   stmn.executeUpdate(sql); 
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
     private void clear () {
        
        txtnotransaksi.setText("");
        txttgl.setDate(null);
        cmbkodecust.setSelectedIndex(-1);
        txtnopart.setText("");
        txtnamapart.setText("");
        txtharga.setText("");
        txtqty.setText("");
        txttotal.setText("");
        txttotalpembelian.setText("");
        txtbayar.setText("");
        txtkembalian.setText("");
        tabelMaster.clearSelection();
        tabelTransaksi.clearSelection();
        
    }
     
     private void print () {
        
        
        try { 
              File file = new File("src/Report/Transaksi_Penjualan.jrxml");
              
              JasperDesign jasperDesign = new JasperDesign();
              jasperDesign = JRXmlLoader.load(file);
              
              Map parameter = new HashMap();
              parameter.clear();
              parameter.put("no_transaksi", txtnotransaksi.getText());
              
              JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
              JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                      parameter, Koneksi.sambung());
              JasperViewer.viewReport(jasperPrint, false); 
          } catch (Exception e) { 
              e.printStackTrace(); 
          }
        
    }
     
    private void updatestockdelete () {
        
        String nopart = txtnopart.getText();
        String jumlah = txtqty.getText();
        String sqlstock = "SELECT stock FROM tbmaster WHERE nopart ='"+nopart+"' ";
        try {
            connection = Koneksi.sambung();
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sqlstock);
            while (rs.next()) {
                String stocklama = rs.getString(1);
                int stock =  Integer.parseInt(stocklama) + Integer.parseInt(jumlah);
                String sql =  "UPDATE tbmaster SET stock = '"+stock+"' WHERE nopart = '" +nopart+ "' ";
                try {
                   connection = Koneksi.sambung();
                   Statement stmn = connection.createStatement();
                   stmn.executeUpdate(sql); 
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
     
     private void delete () {
        
        String sql = "DELETE FROM tbpenjualan WHERE notransaksi = '"+txtnotransaksi.getText()+"' "
                + "AND nopart= '"+txtnopart.getText()+"' ";
        
        if (txtnopart.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "No Transaksi masih kosong", "Informasi",
                    JOptionPane.WARNING_MESSAGE);
            txtnopart.requestFocus();
        } else {
            int pilih = JOptionPane.showConfirmDialog(null, "Yakin Mau Hapus Data ini ?",
                "Warning", JOptionPane.YES_NO_OPTION);
        
        if (pilih == JOptionPane.YES_OPTION) {
           try {
               connection = Koneksi.sambung();
               PreparedStatement statement = null;
               statement = connection.prepareStatement(sql);
               Statement stm = connection.createStatement();
               stm.execute(sql);
               stm.close();
           } catch (Exception e) {
               System.out.println(e.getMessage());
           } 
        }
            updatestockdelete();
            clear();
            isitabeltransaksi();
            isitabelmaster();

        }
    }
     
      

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBackground1 = new Component.PanelBackground();
        jLabel4 = new javax.swing.JLabel();
        epart = new javax.swing.JLabel();
        panelTransparan1 = new Component.PanelTransparan();
        jLabel13 = new javax.swing.JLabel();
        txtnotransaksi = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txttgl = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        cmbkodecust = new javax.swing.JComboBox();
        txtnamacust = new javax.swing.JTextField();
        panelTransparan2 = new Component.PanelTransparan();
        jLabel7 = new javax.swing.JLabel();
        txtnopart = new javax.swing.JTextField();
        txtharga = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtnamapart = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtqty = new javax.swing.JTextField();
        btnOK = new Component.Tombol_Master();
        jLabel8 = new javax.swing.JLabel();
        txttotal = new javax.swing.JTextField();
        btnOK1 = new Component.Tombol_Master();
        panelTransparan3 = new Component.PanelTransparan();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelMaster = new javax.swing.JTable();
        panelTransparan4 = new Component.PanelTransparan();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelTransaksi = new javax.swing.JTable();
        btnNew = new Component.Tombol_Master();
        btnCancel = new Component.Tombol_Master();
        btnPrint = new Component.Tombol_Master();
        jLabel5 = new javax.swing.JLabel();
        txttotalpembelian = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtkembalian = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtbayar = new javax.swing.JTextField();
        btnDelete = new Component.Tombol_Master();

        setMinimumSize(new java.awt.Dimension(800, 640));
        setPreferredSize(new java.awt.Dimension(800, 640));
        setLayout(new java.awt.BorderLayout());

        panelBackground1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Transaksi Penjualan");
        panelBackground1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 260, -1));

        epart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1Epart.png"))); // NOI18N
        panelBackground1.add(epart, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 0, -1, -1));

        panelTransparan1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("No Transaksi :");
        panelTransparan1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 90, -1));

        txtnotransaksi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtnotransaksi.setForeground(new java.awt.Color(255, 255, 255));
        txtnotransaksi.setOpaque(false);
        txtnotransaksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnotransaksiKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtnotransaksiKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnotransaksiKeyTyped(evt);
            }
        });
        panelTransparan1.add(txtnotransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 140, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Tgl Transaksi :");
        panelTransparan1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 90, -1));

        txttgl.setDateFormatString("dd MMM yyyy");
        txttgl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        panelTransparan1.add(txttgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 140, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Customer :");
        panelTransparan1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 90, -1));

        cmbkodecust.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        cmbkodecust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbkodecustActionPerformed(evt);
            }
        });
        cmbkodecust.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cmbkodecustPropertyChange(evt);
            }
        });
        panelTransparan1.add(cmbkodecust, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 140, -1));

        txtnamacust.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtnamacust.setForeground(new java.awt.Color(255, 255, 255));
        txtnamacust.setOpaque(false);
        txtnamacust.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnamacustKeyTyped(evt);
            }
        });
        panelTransparan1.add(txtnamacust, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 140, -1));

        panelBackground1.add(panelTransparan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 760, 70));

        panelTransparan2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("No Part :");
        panelTransparan2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 80, -1));

        txtnopart.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtnopart.setForeground(new java.awt.Color(255, 255, 255));
        txtnopart.setOpaque(false);
        txtnopart.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnopartKeyTyped(evt);
            }
        });
        panelTransparan2.add(txtnopart, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 140, -1));

        txtharga.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtharga.setForeground(new java.awt.Color(255, 255, 255));
        txtharga.setOpaque(false);
        panelTransparan2.add(txtharga, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 110, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Harga :");
        panelTransparan2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 80, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Nama Part :");
        panelTransparan2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, 40, 100, -1));

        txtnamapart.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtnamapart.setForeground(new java.awt.Color(255, 255, 255));
        txtnamapart.setOpaque(false);
        txtnamapart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamapartActionPerformed(evt);
            }
        });
        txtnamapart.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnamapartKeyTyped(evt);
            }
        });
        panelTransparan2.add(txtnamapart, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 140, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Qty :");
        panelTransparan2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 100, 90, -1));

        txtqty.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtqty.setForeground(new java.awt.Color(255, 255, 255));
        txtqty.setOpaque(false);
        txtqty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtqtyKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtqtyKeyTyped(evt);
            }
        });
        panelTransparan2.add(txtqty, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 40, -1));

        btnOK.setForeground(new java.awt.Color(255, 255, 255));
        btnOK.setText("Search");
        btnOK.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });
        panelTransparan2.add(btnOK, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, 80, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Sub Total :");
        panelTransparan2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 70, -1));

        txttotal.setBackground(new java.awt.Color(0, 0, 0));
        txttotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txttotal.setForeground(new java.awt.Color(255, 255, 255));
        txttotal.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txttotal.setEnabled(false);
        txttotal.setOpaque(false);
        txttotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttotalActionPerformed(evt);
            }
        });
        panelTransparan2.add(txttotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 140, 20));

        btnOK1.setForeground(new java.awt.Color(255, 255, 255));
        btnOK1.setText("Add");
        btnOK1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnOK1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOK1ActionPerformed(evt);
            }
        });
        panelTransparan2.add(btnOK1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, 80, -1));

        panelBackground1.add(panelTransparan2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 140, 420, 170));

        panelTransparan3.setLayout(new java.awt.BorderLayout());

        tabelMaster.setBackground(new java.awt.Color(0, 153, 153));
        tabelMaster.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tabelMaster.setForeground(new java.awt.Color(255, 255, 255));
        tabelMaster.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabelMaster.setSelectionBackground(new java.awt.Color(0, 255, 204));
        tabelMaster.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tabelMaster.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelMasterMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelMaster);

        panelTransparan3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panelBackground1.add(panelTransparan3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 330, 170));

        panelTransparan4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelTransaksi.setBackground(new java.awt.Color(0, 153, 153));
        tabelTransaksi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tabelTransaksi.setForeground(new java.awt.Color(255, 255, 255));
        tabelTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabelTransaksi.setSelectionBackground(new java.awt.Color(0, 255, 204));
        tabelTransaksi.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tabelTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelTransaksiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelTransaksi);

        panelTransparan4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 760, 180));

        btnNew.setForeground(new java.awt.Color(255, 255, 255));
        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/create_new-24.png"))); // NOI18N
        btnNew.setText("New");
        btnNew.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        panelTransparan4.add(btnNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, 100, 30));

        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cancel_file-24.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        panelTransparan4.add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 260, 100, 30));

        btnPrint.setForeground(new java.awt.Color(255, 255, 255));
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/print-24.png"))); // NOI18N
        btnPrint.setText("Print");
        btnPrint.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        panelTransparan4.add(btnPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 90, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Kembalian :");
        panelTransparan4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 250, 120, 40));

        txttotalpembelian.setBackground(new java.awt.Color(255, 204, 0));
        txttotalpembelian.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txttotalpembelian.setEnabled(false);
        panelTransparan4.add(txttotalpembelian, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 230, 40));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("TOTAL :");
        panelTransparan4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 150, 40));

        txtkembalian.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtkembalian.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtkembalian.setEnabled(false);
        txtkembalian.setOpaque(false);
        panelTransparan4.add(txtkembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 250, 200, 40));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Bayar :");
        panelTransparan4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 190, 110, 40));

        txtbayar.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txtbayar.setForeground(new java.awt.Color(255, 255, 255));
        txtbayar.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtbayar.setOpaque(false);
        txtbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbayarActionPerformed(evt);
            }
        });
        txtbayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbayarKeyReleased(evt);
            }
        });
        panelTransparan4.add(txtbayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 190, 200, 40));

        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete_file-24.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        panelTransparan4.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 260, -1, 30));

        panelBackground1.add(panelTransparan4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 760, 310));

        add(panelBackground1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txtnotransaksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnotransaksiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnotransaksiKeyPressed

    private void txtnotransaksiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnotransaksiKeyReleased
        int x = txtnotransaksi.getText().length();
        if (x > 15) {
            JOptionPane.showMessageDialog(null, "Input terlalu panjang", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            txtnotransaksi.setText("");
        }
        
        loadtransaksi();
        totalpembelian();
    }//GEN-LAST:event_txtnotransaksiKeyReleased

    private void txtnotransaksiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnotransaksiKeyTyped
        evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
    }//GEN-LAST:event_txtnotransaksiKeyTyped

    private void cmbkodecustActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbkodecustActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cmbkodecustActionPerformed

    private void cmbkodecustPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cmbkodecustPropertyChange
        isinamacust();
    }//GEN-LAST:event_cmbkodecustPropertyChange

    private void txtnamacustKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnamacustKeyTyped
        evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
    }//GEN-LAST:event_txtnamacustKeyTyped

    private void txtnopartKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnopartKeyTyped
        evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
    }//GEN-LAST:event_txtnopartKeyTyped

    private void txttotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttotalActionPerformed

    private void txtqtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtqtyKeyReleased
        int harga = Integer.parseInt(txtharga.getText());
        int qty = Integer.parseInt(txtqty.getText());
        int subtotal = harga*qty;
        String subtotalS = String.valueOf(subtotal);
        txttotal.setText(subtotalS);
    }//GEN-LAST:event_txtqtyKeyReleased

    private void txtqtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtqtyKeyTyped

    }//GEN-LAST:event_txtqtyKeyTyped

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        isicari();
    }//GEN-LAST:event_btnOKActionPerformed

    private void txtnamapartKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnamapartKeyTyped
        evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
    }//GEN-LAST:event_txtnamapartKeyTyped

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clear();
        isitabelmaster();
        isitabeltransaksi();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnOK1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOK1ActionPerformed
        add();
    }//GEN-LAST:event_btnOK1ActionPerformed

    private void tabelMasterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelMasterMouseClicked
        int x = evt.getClickCount();
        if (x == 2) {
            isifieldmaster();
        }
    }//GEN-LAST:event_tabelMasterMouseClicked

    private void txtnamapartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamapartActionPerformed
       isicari();
    }//GEN-LAST:event_txtnamapartActionPerformed

    private void tabelTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelTransaksiMouseClicked
        int x = evt.getClickCount();
        if (x == 2) {
            isifieldtransaksi();
        }
    }//GEN-LAST:event_tabelTransaksiMouseClicked

    private void txtbayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbayarKeyReleased
       
    }//GEN-LAST:event_txtbayarKeyReleased

    private void txtbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbayarActionPerformed
         kembalian();
    }//GEN-LAST:event_txtbayarActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        print();
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Component.Tombol_Master btnCancel;
    private Component.Tombol_Master btnDelete;
    private Component.Tombol_Master btnNew;
    private Component.Tombol_Master btnOK;
    private Component.Tombol_Master btnOK1;
    private Component.Tombol_Master btnPrint;
    private javax.swing.JComboBox cmbkodecust;
    private javax.swing.JLabel epart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private Component.PanelBackground panelBackground1;
    private Component.PanelTransparan panelTransparan1;
    private Component.PanelTransparan panelTransparan2;
    private Component.PanelTransparan panelTransparan3;
    private Component.PanelTransparan panelTransparan4;
    private javax.swing.JTable tabelMaster;
    private javax.swing.JTable tabelTransaksi;
    private javax.swing.JTextField txtbayar;
    private javax.swing.JTextField txtharga;
    private javax.swing.JTextField txtkembalian;
    private javax.swing.JTextField txtnamacust;
    private javax.swing.JTextField txtnamapart;
    private javax.swing.JTextField txtnopart;
    private javax.swing.JTextField txtnotransaksi;
    private javax.swing.JTextField txtqty;
    private com.toedter.calendar.JDateChooser txttgl;
    private javax.swing.JTextField txttotal;
    private javax.swing.JTextField txttotalpembelian;
    // End of variables declaration//GEN-END:variables
}
