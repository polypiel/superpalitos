/*
 * NewConnectionDialog.java
 *
 * Created on 2 de diciembre de 2007, 19:17
 */

package com.angelcalvo.superpalitos.gui;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.angelcalvo.superpalitos.SuperPalitos;

/**
 *
 * @author  angel
 */
public class NewConnectionDialog extends javax.swing.JDialog {
	private static final long serialVersionUID = 2192497287671539868L;
	
	/** A return status code - returned if Cancel button has been pressed */
  public static final int RET_CANCEL = 0;
  /** A return status code - returned if OK button has been pressed */
  public static final int RET_OK = 1;
  
  public static final int INICIO = 0;
  public static final int CONECTANDO = 1;
  
	private ImageIcon okImg, cancelImg;
  private int state, port;
  private SuperPalitos sp;
  
  /** Creates new form NewConnectionDialog */
  public NewConnectionDialog(java.awt.Frame parent, SuperPalitos sp, ImageIcon okImg, ImageIcon cancelImg) {
    super(parent, true);
    this.sp = sp;
  	this.okImg = okImg;
  	this.cancelImg = cancelImg;
  	
    initComponents();
    
    portTF.setText(String.valueOf(sp.getPuerto()));
    setLocationRelativeTo(parent);
  }
  
  /** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
  public int getReturnStatus() {
    return returnStatus;
  }
  
  private void accept() {
    if(checkData()) {
      dirTF.setEnabled(false);
      portTF.setEnabled(false);
      okButton.setEnabled(false);
      connProgress.setIndeterminate(true);
      
      //Todo conectar
      state = CONECTANDO;
      sp.conectar(dirTF.getText(), port);
    } else {
      //TODO quitar
      JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("com/angelcalvo/superpalitos/gui/Bundle").getString("Datos_incorrectos"), java.util.ResourceBundle.getBundle("com/angelcalvo/superpalitos/gui/Bundle").getString("Error"), JOptionPane.ERROR_MESSAGE);
    }
  }
    
  private void cancel() {
    if(state == CONECTANDO) {
      state = INICIO;
      setTitle(java.util.ResourceBundle.getBundle("com/angelcalvo/superpalitos/gui/Bundle").getString("JDConectar"));
      dirTF.setEnabled(true);
      portTF.setEnabled(true);
      okButton.setEnabled(true);
      connProgress.setIndeterminate(false);
    } else {
      doClose(RET_CANCEL);
    }
  }
    
  private boolean checkData() {
    try {
      port = Integer.parseInt(portTF.getText());
      String dir = dirTF.getText().trim();
      return !dir.isEmpty() && port > 1023 && port < 65535;
    } catch(NumberFormatException exp) {
      return false;
    }
  }
    
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    okButton = new javax.swing.JButton();
    cancelButton = new javax.swing.JButton();
    jSeparator1 = new javax.swing.JSeparator();
    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    dirTF = new javax.swing.JTextField();
    portTF = new javax.swing.JTextField();
    connProgress = new javax.swing.JProgressBar();
    statusLabel = new javax.swing.JLabel();

    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com/angelcalvo/superpalitos/gui/Bundle"); // NOI18N
    setTitle(bundle.getString("connDialog.title")); // NOI18N
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        closeDialog(evt);
      }
    });

    okButton.setIcon(okImg); // NOI18N
    okButton.setText(bundle.getString("connDialog.ok")); // NOI18N
    okButton.addActionListener(e -> { okButtonActionPerformed(e); });

    cancelButton.setIcon(cancelImg); // NOI18N
    cancelButton.setText(bundle.getString("connDialog.cancel")); // NOI18N
    cancelButton.addActionListener(e -> {cancelButtonActionPerformed(e); });

    jLabel1.setText(bundle.getString("connDialog.dir")); // NOI18N

    jLabel2.setText(bundle.getString("connDialog.port")); // NOI18N

    statusLabel.setText(bundle.getString("connDialog.connecting")); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(cancelButton))
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jLabel1)
              .addComponent(jLabel2))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(portTF, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(dirTF, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)))
          .addComponent(connProgress, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
          .addComponent(statusLabel))
        .addContainerGap())
    );

    layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, okButton});

    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1)
          .addComponent(dirTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel2)
          .addComponent(portTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
        .addComponent(statusLabel)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(connProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(cancelButton)
          .addComponent(okButton))
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents
  
  private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
    accept();
  }//GEN-LAST:event_okButtonActionPerformed
  
  private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
    cancel();
  }//GEN-LAST:event_cancelButtonActionPerformed
  
  /** Closes the dialog */
  private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
    cancel();
  }//GEN-LAST:event_closeDialog
  
  private void doClose(int retStatus) {
    returnStatus = retStatus;
    setVisible(false);
    dispose();
  }
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton cancelButton;
  private javax.swing.JProgressBar connProgress;
  private javax.swing.JTextField dirTF;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JSeparator jSeparator1;
  private javax.swing.JButton okButton;
  private javax.swing.JTextField portTF;
  private javax.swing.JLabel statusLabel;
  // End of variables declaration//GEN-END:variables
  
  private int returnStatus = RET_CANCEL;
}
