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
public class PanelBuy extends javax.swing.JPanel {

    /**
     * Creates new form PanelBuy
     */
    
    private Connection connection;
     
    public PanelBuy() {
        initComponents();
        
        txtharga.setDocument(new OnlyDigit().getOnlyDigit());
        txtqty.setDocument(new OnlyDigit().getOnlyDigit());
        
        isitabelmaster();
        isicombo();
        isitabeltransaksi();
        
    }
    
    public void addActionListenerCancel (ActionListener l) {
        btnCancel.addActionListener(l);
    }

    public JComboBox getCmbkodesupp() {
        return cmbkodesupp;
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
                String kolom4 = rs.getString(5);
                
                String kolom [] = {kolom1, kolom2, kolom3, kolom4 };
                
                model.addRow(kolom);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void isicombo () {
        
        String sql = "SELECT kodesupplier FROM tbsupplier ORDER BY kodesupplier";
        
        try {
            connection = Koneksi.sambung();
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                cmbkodesupp.addItem(rs.getString("kodesupplier"));
            }
               rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
            cmbkodesupp.setSelectedIndex(-1);
    }
    
    private void isinamasupp () {
        
        String sql = "SELECT * FROM tbsupplier WHERE kodesupplier = '"+cmbkodesupp.getSelectedItem()+"' ";
        
        try {
            connection = Koneksi.sambung();
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                txtnamasupp.setText(rs.getString("namasupplier"));
            }
                rs.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    private void clear () {
        
        txtnotransaksi.setText("");
        txttgl.setDate(null);
        cmbkodesupp.setSelectedIndex(-1);
        txtnopart.setText("");
        txtnamapart.setText("");
        txtharga.setText("");
        txtqty.setText("");
        txttotal.setText("");
        txtcarinama.setText("");
        txttotalpembelian.setText("");
        tabelMaster.clearSelection();
        tabelTransaksi.clearSelection();
        
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
        
        String sql = "SELECT * FROM tbmaster WHERE namapart = '"+txtcarinama.getText()+"' ";
        
        try {
            connection = Koneksi.sambung();
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                String kolom1 = rs.getString(1);
                String kolom2 = rs.getString(2);
                String kolom3 = rs.getString(4);
                String kolom4 = rs.getString(5);
                
                String kolom [] = {kolom1, kolom2, kolom3, kolom4 };
                
                model.addRow(kolom);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void add () {
        String cek = "Select * FROM tbpembelian WHERE notransaksi='" + txtnotransaksi.getText() + "' AND"
                + " nopart ='" + txtnopart.getText() + "' AND"
                + " kodesupplier='" + cmbkodesupp.getSelectedItem() + "'";
        String tambah = "UPDATE tbpembelian SET jumlah=jumlah + ?,totalharga= totalharga + ? WHERE"
                + " notransaksi='" + txtnotransaksi.getText() + "' AND "
                + "nopart='" + txtnopart.getText() + "' AND"
                + " kodesupplier='" + cmbkodesupp.getSelectedItem() + "'";
        String insert = "INSERT INTO tbpembelian (notransaksi,tgl,kodesupplier,"
            + "nopart,namapart,hargabeli,jumlah,totalharga) VALUE (?,?,?,?,?,?,?,?);" ;
        
        
        if (txtnotransaksi.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "No Transaksi Masih kosong !",
                            "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (txttgl.getDate().equals(null)) {
                JOptionPane.showMessageDialog(null, "Tanggal Masih kosong !",
                            "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (cmbkodesupp.getSelectedItem().equals("")) {
                JOptionPane.showMessageDialog(null, "KodeSupplier Masih kosong !",
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
                
                    statement.setString(3, cmbkodesupp.getSelectedItem().toString());
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
        
        Object header [] = {"No Transaksi", "Tgl Transaksi", "Kode Supplier",
                            "No Part", "Nama Part", "Harga", "jumlah", "Total Harga"};
        
        DefaultTableModel model = new DefaultTableModel(null, header) {
            public boolean isCellEditable(int row, int column) {
            return false;
            }
        };
        tabelTransaksi.setModel(model);
        
        String sql = "SELECT * FROM tbpembelian ORDER BY notransaksi";
        
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
        
        cmbkodesupp.setSelectedItem(kodesup);
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
    
    private void loadtransaksi () {
        
        Object header [] = {"No Transaksi", "Tgl Transaksi", "Kode Supplier",
                            "No Part", "Nama Part", "Harga", "jumlah", "Total Harga"};
        
        DefaultTableModel model = new DefaultTableModel(null, header) {
            public boolean isCellEditable(int row, int column) {
            return false;
            }
        };
        tabelTransaksi.setModel(model); 
        
        String load="Select * from tbpembelian where notransaksi='" + txtnotransaksi.getText()+ "'";
         
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
                int stock = Integer.parseInt(jumlah) + Integer.parseInt(stocklama);
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
                int stock =  Integer.parseInt(stocklama) - Integer.parseInt(jumlah);
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
    
    private void print () {
        
        
        try { 
              File file = new File("src/Report/Transaksi_Pembelian.jrxml");
              
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
    
    private void delete () {
        
        String sql = "DELETE FROM tbpembelian WHERE notransaksi = '"+txtnotransaksi.getText()+"' "
                + "AND nopart= '"+txtnopart.getText()+"'";
        
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

        panelBackground = new Component.PanelBackground();
        panelTransparan1 = new Component.PanelTransparan();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txttotalpembelian = new javax.swing.JTextField();
        txttgl = new com.toedter.calendar.JDateChooser();
        cmbkodesupp = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        txtnamasupp = new javax.swing.JTextField();
        txtnotransaksi = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        epart = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        panelTransparan2 = new Component.PanelTransparan();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtnopart = new javax.swing.JTextField();
        txttotal = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtharga = new javax.swing.JTextField();
        txtqty = new javax.swing.JTextField();
        btnOK = new Component.Tombol_Master();
        jLabel12 = new javax.swing.JLabel();
        txtnamapart = new javax.swing.JTextField();
        panelTransparan4 = new Component.PanelTransparan();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelMaster = new javax.swing.JTable();
        panelTransparan5 = new Component.PanelTransparan();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelTransaksi = new javax.swing.JTable();
        tombol_Master3 = new Component.Tombol_Master();
        btnNew = new Component.Tombol_Master();
        btnPrint = new Component.Tombol_Master();
        btnCancel = new Component.Tombol_Master();
        btnDelete = new Component.Tombol_Master();
        jLabel11 = new javax.swing.JLabel();
        panelTransparan6 = new Component.PanelTransparan();
        txtcarinama = new javax.swing.JTextField();
        btncarinama = new Component.Tombol_Master();
        jLabel14 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(800, 640));
        setPreferredSize(new java.awt.Dimension(800, 640));
        setLayout(new java.awt.BorderLayout());

        panelBackground.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelTransparan1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Supplier :");
        panelTransparan1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 90, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Tgl Transaksi :");
        panelTransparan1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 90, -1));

        txttotalpembelian.setBackground(new java.awt.Color(255, 204, 0));
        txttotalpembelian.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txttotalpembelian.setEnabled(false);
        panelTransparan1.add(txttotalpembelian, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 230, 60));

        txttgl.setDateFormatString("dd MMM yyyy");
        txttgl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        panelTransparan1.add(txttgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 140, -1));

        cmbkodesupp.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        cmbkodesupp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbkodesuppActionPerformed(evt);
            }
        });
        cmbkodesupp.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cmbkodesuppPropertyChange(evt);
            }
        });
        panelTransparan1.add(cmbkodesupp, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 140, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("TOTAL :");
        panelTransparan1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 150, -1));

        txtnamasupp.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtnamasupp.setForeground(new java.awt.Color(255, 255, 255));
        txtnamasupp.setOpaque(false);
        txtnamasupp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnamasuppKeyTyped(evt);
            }
        });
        panelTransparan1.add(txtnamasupp, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 140, -1));

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

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("No Transaksi :");
        panelTransparan1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 90, -1));

        panelBackground.add(panelTransparan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 750, 100));

        epart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1Epart.png"))); // NOI18N
        panelBackground.add(epart, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 0, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Transaksi Pembelian");
        panelBackground.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 260, -1));

        panelTransparan2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("No Part :");
        panelTransparan2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 10, 90, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Sub Total :");
        panelTransparan2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 70, -1));

        txtnopart.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtnopart.setForeground(new java.awt.Color(255, 255, 255));
        txtnopart.setOpaque(false);
        txtnopart.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnopartKeyTyped(evt);
            }
        });
        panelTransparan2.add(txtnopart, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 140, -1));

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
        panelTransparan2.add(txttotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, 120, 20));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Nama Part :");
        panelTransparan2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, 40, 100, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Qty :");
        panelTransparan2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 100, 90, -1));

        txtharga.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtharga.setForeground(new java.awt.Color(255, 255, 255));
        txtharga.setOpaque(false);
        panelTransparan2.add(txtharga, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 110, -1));

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
        btnOK.setText("Add");
        btnOK.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });
        panelTransparan2.add(btnOK, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 98, 60, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Harga :");
        panelTransparan2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 70, 90, -1));

        txtnamapart.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtnamapart.setForeground(new java.awt.Color(255, 255, 255));
        txtnamapart.setOpaque(false);
        txtnamapart.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnamapartKeyTyped(evt);
            }
        });
        panelTransparan2.add(txtnamapart, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 140, -1));

        panelBackground.add(panelTransparan2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 410, 140));

        panelTransparan4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Tabel Part", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), java.awt.Color.white)); // NOI18N
        panelTransparan4.setForeground(new java.awt.Color(255, 255, 255));
        panelTransparan4.setToolTipText("");
        panelTransparan4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jScrollPane2.setViewportView(tabelMaster);

        panelTransparan4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 310, 210));

        panelBackground.add(panelTransparan4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 170, 330, 240));

        panelTransparan5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jScrollPane1.setViewportView(tabelTransaksi);

        panelTransparan5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 160));

        tombol_Master3.setText("Cari");
        panelTransparan5.add(tombol_Master3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 80, 30));

        btnNew.setForeground(new java.awt.Color(255, 255, 255));
        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/create_new-24.png"))); // NOI18N
        btnNew.setText("New");
        btnNew.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        panelTransparan5.add(btnNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 170, 100, 30));

        btnPrint.setForeground(new java.awt.Color(255, 255, 255));
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/print-24.png"))); // NOI18N
        btnPrint.setText("Print");
        btnPrint.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        panelTransparan5.add(btnPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 90, 30));

        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cancel_file-24.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        panelTransparan5.add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 170, 100, 30));

        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete_file-24.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        panelTransparan5.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 170, 100, 30));

        panelBackground.add(panelTransparan5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, 750, 210));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Part Yang Dibeli");
        panelBackground.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 100, -1));

        panelTransparan6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtcarinama.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtcarinama.setForeground(new java.awt.Color(255, 255, 255));
        txtcarinama.setOpaque(false);
        txtcarinama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcarinamaActionPerformed(evt);
            }
        });
        txtcarinama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcarinamaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcarinamaKeyTyped(evt);
            }
        });
        panelTransparan6.add(txtcarinama, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 170, 30));

        btncarinama.setForeground(new java.awt.Color(255, 255, 255));
        btncarinama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/view_file-24.png"))); // NOI18N
        btncarinama.setText("Cari");
        btncarinama.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btncarinama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncarinamaActionPerformed(evt);
            }
        });
        panelTransparan6.add(btncarinama, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, -1, 30));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Nama Part :");
        panelTransparan6.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 70, -1));

        panelBackground.add(panelTransparan6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 320, 280, 70));

        add(panelBackground, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txttotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttotalActionPerformed

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        add();
    }//GEN-LAST:event_btnOKActionPerformed

    private void txtnotransaksiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnotransaksiKeyTyped
        evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
    }//GEN-LAST:event_txtnotransaksiKeyTyped

    private void txtnamasuppKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnamasuppKeyTyped
        evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
    }//GEN-LAST:event_txtnamasuppKeyTyped

    private void txtnopartKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnopartKeyTyped
        evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
    }//GEN-LAST:event_txtnopartKeyTyped

    private void txtnamapartKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnamapartKeyTyped
        evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
    }//GEN-LAST:event_txtnamapartKeyTyped

    private void txtcarinamaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcarinamaKeyTyped
        evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
        
    }//GEN-LAST:event_txtcarinamaKeyTyped

    private void cmbkodesuppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbkodesuppActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_cmbkodesuppActionPerformed

    private void cmbkodesuppPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cmbkodesuppPropertyChange
        isinamasupp();
    }//GEN-LAST:event_cmbkodesuppPropertyChange

    private void tabelMasterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelMasterMouseClicked
        int x = evt.getClickCount();
        if (x==2) {
            isifieldmaster();
        }
    }//GEN-LAST:event_tabelMasterMouseClicked

    private void txtqtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtqtyKeyTyped
        
        
        
    }//GEN-LAST:event_txtqtyKeyTyped

    private void txtqtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtqtyKeyReleased
        int harga = Integer.parseInt(txtharga.getText());
        int qty = Integer.parseInt(txtqty.getText());
        int subtotal = harga*qty;
        String subtotalS = String.valueOf(subtotal);
        txttotal.setText(subtotalS);
    }//GEN-LAST:event_txtqtyKeyReleased

    private void btncarinamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncarinamaActionPerformed
        isicari();
    }//GEN-LAST:event_btncarinamaActionPerformed

    private void txtcarinamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcarinamaKeyPressed
     
    }//GEN-LAST:event_txtcarinamaKeyPressed

    private void txtcarinamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcarinamaActionPerformed
        isicari();
    }//GEN-LAST:event_txtcarinamaActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clear();
        isitabelmaster();
        isitabeltransaksi();
    }//GEN-LAST:event_btnNewActionPerformed

    private void tabelTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelTransaksiMouseClicked
        int x = evt.getClickCount();
        if (x == 2) {
            isifieldtransaksi();
        }
    }//GEN-LAST:event_tabelTransaksiMouseClicked

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
    private Component.Tombol_Master btnPrint;
    private Component.Tombol_Master btncarinama;
    private javax.swing.JComboBox cmbkodesupp;
    private javax.swing.JLabel epart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private Component.PanelBackground panelBackground;
    private Component.PanelTransparan panelTransparan1;
    private Component.PanelTransparan panelTransparan2;
    private Component.PanelTransparan panelTransparan4;
    private Component.PanelTransparan panelTransparan5;
    private Component.PanelTransparan panelTransparan6;
    private javax.swing.JTable tabelMaster;
    private javax.swing.JTable tabelTransaksi;
    private Component.Tombol_Master tombol_Master3;
    private javax.swing.JTextField txtcarinama;
    private javax.swing.JTextField txtharga;
    private javax.swing.JTextField txtnamapart;
    private javax.swing.JTextField txtnamasupp;
    private javax.swing.JTextField txtnopart;
    private javax.swing.JTextField txtnotransaksi;
    private javax.swing.JTextField txtqty;
    private com.toedter.calendar.JDateChooser txttgl;
    private javax.swing.JTextField txttotal;
    private javax.swing.JTextField txttotalpembelian;
    // End of variables declaration//GEN-END:variables
}
