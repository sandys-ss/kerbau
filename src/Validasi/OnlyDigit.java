/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validasi;

import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author SANDYS
 */
public class OnlyDigit {

    public OnlyDigit() {
    }
    
    public PlainDocument getOnlyDigit () {
        PlainDocument filterdigit = new PlainDocument() {

            @Override
            public void insertString(int i, String string, AttributeSet as) throws BadLocationException {
                StringBuffer buffer = new StringBuffer();
                int s = 0;
                char[] datainput = string.toCharArray();
                
                //cek data input
                for (int a = 0; a < datainput.length; a++) {
                    //filter jika data String
                    boolean isOnlyDigit = Character.isDigit(datainput[a]);
                    if (isOnlyDigit == true) {
                        datainput[s] = datainput[a];
                        s++;
                    } else {
                        JOptionPane.showMessageDialog(null, "data harus angka", "Peringatan",
                            JOptionPane.WARNING_MESSAGE);
                    }
                }
                    buffer.append(datainput, 0, s);
                    super.insertString(i, new String(buffer), as);
            }
            
        };
            return filterdigit;
    }
    
}
