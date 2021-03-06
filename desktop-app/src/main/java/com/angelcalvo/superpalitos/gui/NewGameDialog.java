/*
 * NewGameDialog.java
 *
 * Created on 1 de diciembre de 2007, 20:28
 */

package com.angelcalvo.superpalitos.gui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.angelcalvo.superpalitos.SuperPalitos;

/**
 *
 * @author Angel Calvo
 */
public class NewGameDialog extends JPanel {
	private static final long serialVersionUID = -5859808805533620435L;
  private ImageIcon okImg, cancelImg;
  
  public static interface DialogListener {
  	void accepted(int mode);
  	void cancelled();
  }
  private Collection<DialogListener> listeners;
  
  /** Creates new form NewGameDialog */
  public NewGameDialog(ImageIcon okImg, ImageIcon cancelImg) {
  	this.okImg = okImg;
  	this.cancelImg = cancelImg;
  	
  	setOpaque(true);
  	listeners = new ArrayList<>();
    initComponents();
  }
  
  public void addDialogListner(DialogListener listener) {
  	listeners.add(listener);
  }
  
  
  private int getGameMode() {
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

    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com/angelcalvo/superpalitos/gui/Bundle"); // NOI18N


    okButton.setIcon(okImg); // NOI18N
    okButton.setText(bundle.getString("newDialog.ok")); // NOI18N
    okButton.addActionListener(e -> { okButtonActionPerformed(e); });

    cancelButton.setIcon(cancelImg); // NOI18N
    cancelButton.setText(bundle.getString("newDialog.cancel")); // NOI18N
    cancelButton.addActionListener(e -> { cancelButtonActionPerformed(e); });

    jLabel1.setFont(new java.awt.Font("Dialog", 1, 11));
    jLabel1.setText(bundle.getString("newDialog.players")); // NOI18N

    playersBG.add(onePlayerRB);
    onePlayerRB.setSelected(true);
    onePlayerRB.setText(bundle.getString("newDialog.1player")); // NOI18N
    onePlayerRB.addActionListener(e -> { onePlayerRBActionPerformed(e); });

    playersBG.add(twoPlayersRB);
    twoPlayersRB.setText(bundle.getString("newDialog.2players")); // NOI18N
    twoPlayersRB.addActionListener(e -> { twoPlayersRBActionPerformed(e); });

    jLabel2.setFont(new java.awt.Font("Dialog", 1, 11));
    jLabel2.setText(bundle.getString("newDialog.difficulty")); // NOI18N

    difficultyBG.add(easyRB);
    easyRB.setText(bundle.getString("newDialog.easy")); // NOI18N

    difficultyBG.add(normalRB);
    normalRB.setSelected(true);
    normalRB.setText(bundle.getString("newDialog.normal")); // NOI18N

    difficultyBG.add(hardRB);
    hardRB.setText(bundle.getString("newDialog.hard")); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    setLayout(layout);
    
		//layout.addLayoutComponent(new JLabel("Nueva partida"), null);
    
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
  }// </editor-fold>//GEN-END:initComponents
  
  private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
  	listeners.forEach(l -> { l.accepted(getGameMode()); });
  }//GEN-LAST:event_okButtonActionPerformed
  
  private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
  	listeners.forEach(l -> { l.cancelled(); });
  }//GEN-LAST:event_cancelButtonActionPerformed
  
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
  
  // TODO
  @Override
  protected void paintComponent(Graphics g) {
  	g.drawImage(BoardPanel.FONDO, 0, 0, null);
    super.paintComponent(g);
  }
}
