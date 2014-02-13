/*
 * NewGameDialog.java
 *
 * Created on 1 de diciembre de 2007, 20:28
 */

package org.pvs.superpalitos.gui;

import org.pvs.superpalitos.SuperPalitos;

/**
 *
 * @author  angel
 */
public class NewGameDialog extends javax.swing.JDialog {
	private static final long serialVersionUID = -5859808805533620435L;
	
	/** A return status code - returned if Cancel button has been pressed */
  public static final int RET_CANCEL = 0;
  /** A return status code - returned if OK button has been pressed */
  public static final int RET_OK = 1;
  
  /** Creates new form NewGameDialog */
  public NewGameDialog(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();
    setLocationRelativeTo(parent);
  }
  
  /** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
  public int getReturnStatus() {
    return returnStatus;
  }
  
  public int getMode() {
    if(onePlayerRB.isSelected() && easyRB.isSelected()) {
      return SuperPalitos.JUEGO_1J_FACIL;
    }
    if(onePlayerRB.isSelected() && normalRB.isSelected()) {
      return SuperPalitos.JUEGO_1J_NORMAL;
    }
    if(onePlayerRB.isSelected() && hardRB.isSelected()) {
      return SuperPalitos.JUEGO_1J_DIFICIL;
    }
    return SuperPalitos.JUEGO_2J;
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    playersBG = new javax.swing.ButtonGroup();
    difficultyBG = new javax.swing.ButtonGroup();
    okButton = new javax.swing.JButton();
    cancelButton = new javax.swing.JButton();
    jLabel1 = new javax.swing.JLabel();
    onePlayerRB = new javax.swing.JRadioButton();
    twoPlayersRB = new javax.swing.JRadioButton();
    jLabel2 = new javax.swing.JLabel();
    easyRB = new javax.swing.JRadioButton();
    normalRB = new javax.swing.JRadioButton();
    hardRB = new javax.swing.JRadioButton();
    jSeparator1 = new javax.swing.JSeparator();

    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/pvs/superpalitos/gui/Bundle"); // NOI18N
    setTitle(bundle.getString("newDialog.tittle")); // NOI18N
    setModal(true);
    setResizable(false);
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        closeDialog(evt);
      }
    });

    okButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/pvs/superpalitos/gui/apply.png"))); // NOI18N
    okButton.setText(bundle.getString("newDialog.ok")); // NOI18N
    okButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        okButtonActionPerformed(evt);
      }
    });

    cancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/pvs/superpalitos/gui/cancel.png"))); // NOI18N
    cancelButton.setText(bundle.getString("newDialog.cancel")); // NOI18N
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cancelButtonActionPerformed(evt);
      }
    });

    jLabel1.setFont(new java.awt.Font("Dialog", 1, 11));
    jLabel1.setText(bundle.getString("newDialog.players")); // NOI18N

    playersBG.add(onePlayerRB);
    onePlayerRB.setSelected(true);
    onePlayerRB.setText(bundle.getString("newDialog.1player")); // NOI18N
    onePlayerRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        onePlayerRBActionPerformed(evt);
      }
    });

    playersBG.add(twoPlayersRB);
    twoPlayersRB.setText(bundle.getString("newDialog.2players")); // NOI18N
    twoPlayersRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        twoPlayersRBActionPerformed(evt);
      }
    });

    jLabel2.setFont(new java.awt.Font("Dialog", 1, 11));
    jLabel2.setText(bundle.getString("newDialog.difficulty")); // NOI18N

    difficultyBG.add(easyRB);
    easyRB.setText(bundle.getString("newDialog.easy")); // NOI18N

    difficultyBG.add(normalRB);
    normalRB.setSelected(true);
    normalRB.setText(bundle.getString("newDialog.normal")); // NOI18N

    difficultyBG.add(hardRB);
    hardRB.setText(bundle.getString("newDialog.hard")); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(cancelButton))
          .addComponent(jLabel1)
          .addGroup(layout.createSequentialGroup()
            .addGap(12, 12, 12)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(onePlayerRB)
              .addComponent(twoPlayersRB))
            .addGap(8, 8, 8))
          .addComponent(jLabel2)
          .addGroup(layout.createSequentialGroup()
            .addGap(12, 12, 12)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(normalRB)
              .addComponent(easyRB)
              .addComponent(hardRB)))
          .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
        .addContainerGap())
    );

    layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, okButton});

    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jLabel1)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(onePlayerRB)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(twoPlayersRB)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(jLabel2)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(easyRB)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(normalRB)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(hardRB)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(cancelButton)
          .addComponent(okButton))
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents
  
  private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
    doClose(RET_OK);
  }//GEN-LAST:event_okButtonActionPerformed
  
  private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
    doClose(RET_CANCEL);
  }//GEN-LAST:event_cancelButtonActionPerformed
  
  /** Closes the dialog */
  private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
    doClose(RET_CANCEL);
  }//GEN-LAST:event_closeDialog

  private void onePlayerRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onePlayerRBActionPerformed
    easyRB.setEnabled(true);
    normalRB.setEnabled(true);
    hardRB.setEnabled(true);
}//GEN-LAST:event_onePlayerRBActionPerformed

  private void twoPlayersRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_twoPlayersRBActionPerformed
    easyRB.setEnabled(false);
    normalRB.setEnabled(false);
    hardRB.setEnabled(false);
  }//GEN-LAST:event_twoPlayersRBActionPerformed
  
  private void doClose(int retStatus) {
    returnStatus = retStatus;
    setVisible(false);
    dispose();
  }
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton cancelButton;
  private javax.swing.ButtonGroup difficultyBG;
  private javax.swing.JRadioButton easyRB;
  private javax.swing.JRadioButton hardRB;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JSeparator jSeparator1;
  private javax.swing.JRadioButton normalRB;
  private javax.swing.JButton okButton;
  private javax.swing.JRadioButton onePlayerRB;
  private javax.swing.ButtonGroup playersBG;
  private javax.swing.JRadioButton twoPlayersRB;
  // End of variables declaration//GEN-END:variables
  
  private int returnStatus = RET_CANCEL;
}