/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import org.json.simple.JSONArray;

import client.AnswerData;
import client.IOCommandesStudent;
import client.IOCommandesTeacher;
import client.Questionnaire;

/**
 *
 * @author steau_000
 */
public class ClientStudentUI extends javax.swing.JFrame {
	private IOCommandesStudent student;

    /**
     * Creates new form ClientTeacherUI
     */
    public ClientStudentUI() {
        initComponents();
    }
    
    public ClientStudentUI(String username, IOCommandesStudent student) {
        initComponents();
        this.student = student;
        this.student.setUI(this);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        answerButtonGroup = new javax.swing.ButtonGroup();
        jPanelQuestion = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPaneQuestion = new javax.swing.JTextPane();
        jLabelTypeQuestion = new javax.swing.JLabel();
        jPanelUniqueAnswer = new javax.swing.JPanel();
        radioAnwser1 = new javax.swing.JRadioButton();
        radioAnswer2 = new javax.swing.JRadioButton();
        radioAnswer3 = new javax.swing.JRadioButton();
        radioAnswer4 = new javax.swing.JRadioButton();
        radioAnswer5 = new javax.swing.JRadioButton();
        radioAnswer6 = new javax.swing.JRadioButton();
        radioAnswer7 = new javax.swing.JRadioButton();
        radioAnswer8 = new javax.swing.JRadioButton();
        jPanelMultipleAnswer = new javax.swing.JPanel();
        checkBoxAnswer1 = new javax.swing.JCheckBox();
        checkBoxAnswer2 = new javax.swing.JCheckBox();
        checkBoxAnswer3 = new javax.swing.JCheckBox();
        checkBoxAnswer4 = new javax.swing.JCheckBox();
        checkBoxAnswer5 = new javax.swing.JCheckBox();
        checkBoxAnswer6 = new javax.swing.JCheckBox();
        checkBoxAnswer7 = new javax.swing.JCheckBox();
        checkBoxAnswer8 = new javax.swing.JCheckBox();
        jPanelOpenAnswer = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textAreaOpenAnswer = new javax.swing.JTextArea();
        buttonSendAnswer = new javax.swing.JButton();
        
        

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Client Student");
        setPreferredSize(new java.awt.Dimension(800, 600));
        setResizable(false);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        
        jPanelQuestion.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Question en cours", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jTextPaneQuestion.setEditable(false);
        jTextPaneQuestion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(jTextPaneQuestion);

        jLabelTypeQuestion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelTypeQuestion.setText("TypeQuestion");

        javax.swing.GroupLayout jPanelQuestionLayout = new javax.swing.GroupLayout(jPanelQuestion);
        jPanelQuestion.setLayout(jPanelQuestionLayout);
        jPanelQuestionLayout.setHorizontalGroup(
            jPanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelQuestionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanelQuestionLayout.createSequentialGroup()
                        .addComponent(jLabelTypeQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelQuestionLayout.setVerticalGroup(
            jPanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelQuestionLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTypeQuestion))
        );

        jPanelUniqueAnswer.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Réponse", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        answerButtonGroup.add(radioAnwser1);
        radioAnwser1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        radioAnwser1.setText("Reponse 1");
        radioAnwser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioAnwser1ActionPerformed(evt);
            }
        });

        answerButtonGroup.add(radioAnswer2);
        radioAnswer2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        radioAnswer2.setText("Reponse 2");

        answerButtonGroup.add(radioAnswer3);
        radioAnswer3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        radioAnswer3.setText("Reponse 3");

        answerButtonGroup.add(radioAnswer4);
        radioAnswer4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        radioAnswer4.setText("Reponse 4");
        radioAnswer4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioAnswer4ActionPerformed(evt);
            }
        });

        answerButtonGroup.add(radioAnswer5);
        radioAnswer5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        radioAnswer5.setText("Reponse 5");

        answerButtonGroup.add(radioAnswer6);
        radioAnswer6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        radioAnswer6.setText("Reponse 6");

        answerButtonGroup.add(radioAnswer7);
        radioAnswer7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        radioAnswer7.setText("Reponse 7");

        answerButtonGroup.add(radioAnswer8);
        radioAnswer8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        radioAnswer8.setText("Reponse 8");

        javax.swing.GroupLayout jPanelUniqueAnswerLayout = new javax.swing.GroupLayout(jPanelUniqueAnswer);
        jPanelUniqueAnswer.setLayout(jPanelUniqueAnswerLayout);
        jPanelUniqueAnswerLayout.setHorizontalGroup(
            jPanelUniqueAnswerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUniqueAnswerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelUniqueAnswerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioAnswer3)
                    .addComponent(radioAnswer2)
                    .addComponent(radioAnwser1)
                    .addComponent(radioAnswer5)
                    .addComponent(radioAnswer6)
                    .addComponent(radioAnswer4)
                    .addComponent(radioAnswer8)
                    .addComponent(radioAnswer7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelUniqueAnswerLayout.setVerticalGroup(
            jPanelUniqueAnswerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUniqueAnswerLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(radioAnwser1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioAnswer2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioAnswer3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioAnswer4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioAnswer5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioAnswer6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioAnswer7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioAnswer8)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanelMultipleAnswer.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Réponse", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        checkBoxAnswer1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        checkBoxAnswer1.setText("Reponse 1");
        checkBoxAnswer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxAnswer1ActionPerformed(evt);
            }
        });

        checkBoxAnswer2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        checkBoxAnswer2.setText("Reponse 2");
        checkBoxAnswer2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxAnswer2ActionPerformed(evt);
            }
        });

        checkBoxAnswer3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        checkBoxAnswer3.setText("Reponse 3");
        checkBoxAnswer3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxAnswer3ActionPerformed(evt);
            }
        });

        checkBoxAnswer4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        checkBoxAnswer4.setText("Reponse 4");
        checkBoxAnswer4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxAnswer4ActionPerformed(evt);
            }
        });

        checkBoxAnswer5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        checkBoxAnswer5.setText("Reponse 5");
        checkBoxAnswer5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxAnswer5ActionPerformed(evt);
            }
        });

        checkBoxAnswer6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        checkBoxAnswer6.setText("Reponse 6");
        checkBoxAnswer6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxAnswer6ActionPerformed(evt);
            }
        });

        checkBoxAnswer7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        checkBoxAnswer7.setText("Reponse 7");
        checkBoxAnswer7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxAnswer7ActionPerformed(evt);
            }
        });

        checkBoxAnswer8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        checkBoxAnswer8.setText("Reponse 8");
        checkBoxAnswer8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxAnswer8ActionPerformed(evt);
            }
        });

        buttonSendAnswer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendAnswer();
            }
        });

        javax.swing.GroupLayout jPanelMultipleAnswerLayout = new javax.swing.GroupLayout(jPanelMultipleAnswer);
        jPanelMultipleAnswer.setLayout(jPanelMultipleAnswerLayout);
        jPanelMultipleAnswerLayout.setHorizontalGroup(
            jPanelMultipleAnswerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMultipleAnswerLayout.createSequentialGroup()
                .addGroup(jPanelMultipleAnswerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkBoxAnswer1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkBoxAnswer2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkBoxAnswer3, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkBoxAnswer4, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkBoxAnswer6, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkBoxAnswer5, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkBoxAnswer7, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkBoxAnswer8, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanelMultipleAnswerLayout.setVerticalGroup(
            jPanelMultipleAnswerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMultipleAnswerLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(checkBoxAnswer1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkBoxAnswer2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkBoxAnswer3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkBoxAnswer4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkBoxAnswer5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkBoxAnswer6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkBoxAnswer7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkBoxAnswer8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelOpenAnswer.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Réponse", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        textAreaOpenAnswer.setColumns(20);
        textAreaOpenAnswer.setRows(5);
        jScrollPane2.setViewportView(textAreaOpenAnswer);

        javax.swing.GroupLayout jPanelOpenAnswerLayout = new javax.swing.GroupLayout(jPanelOpenAnswer);
        jPanelOpenAnswer.setLayout(jPanelOpenAnswerLayout);
        jPanelOpenAnswerLayout.setHorizontalGroup(
            jPanelOpenAnswerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOpenAnswerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanelOpenAnswerLayout.setVerticalGroup(
            jPanelOpenAnswerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOpenAnswerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonSendAnswer.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        buttonSendAnswer.setText("Envoyer la réponse");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(334, 334, 334)
                .addComponent(buttonSendAnswer)
                .addContainerGap(333, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelQuestion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelUniqueAnswer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelMultipleAnswer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelOpenAnswer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelUniqueAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelMultipleAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelOpenAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonSendAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanelUniqueAnswer.setVisible(false);
        jPanelMultipleAnswer.setVisible(true);
        jPanelOpenAnswer.setVisible(false);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void radioAnswer4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioAnswer4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioAnswer4ActionPerformed

    private void checkBoxAnswer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxAnswer1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkBoxAnswer1ActionPerformed

    private void checkBoxAnswer2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxAnswer2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkBoxAnswer2ActionPerformed

    private void checkBoxAnswer3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxAnswer3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkBoxAnswer3ActionPerformed

    private void checkBoxAnswer4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxAnswer4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkBoxAnswer4ActionPerformed

    private void checkBoxAnswer5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxAnswer5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkBoxAnswer5ActionPerformed

    private void checkBoxAnswer6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxAnswer6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkBoxAnswer6ActionPerformed

    private void checkBoxAnswer7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxAnswer7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkBoxAnswer7ActionPerformed

    private void checkBoxAnswer8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxAnswer8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkBoxAnswer8ActionPerformed

    private void radioAnwser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioAnwser1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioAnwser1ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    	this.student.ecrireReseau("quit");
    	this.student.ecrireEcran("BYE BYE");
    	this.student.interrupt();
    }//GEN-LAST:event_formWindowClosed
    
    public void sendAnswer() {
    	System.out.println("SEND ANSWER");
		JSONArray answerArray = new JSONArray();
    	String answer = "";
    	
    	switch(student.currentQuestionType) {
    		case "OPEN" :
    			System.out.println("Rep : "+textAreaOpenAnswer.getText());
    			answer = textAreaOpenAnswer.getText();
    			break;
    		case "MULTIPLE" :
    			if(checkBoxAnswer1.isSelected())
    				answerArray.add("1");
    			if(checkBoxAnswer2.isSelected())
    				answerArray.add("2");
    			if(checkBoxAnswer3.isSelected())
    				answerArray.add("3");
    			if(checkBoxAnswer4.isSelected())
    				answerArray.add("4");
    			if(checkBoxAnswer5.isSelected())
    				answerArray.add("5");
    			if(checkBoxAnswer6.isSelected())
    				answerArray.add("6");
    			if(checkBoxAnswer7.isSelected())
    				answerArray.add("7");
    			if(checkBoxAnswer8.isSelected())
    				answerArray.add("8");
    			answer = answerArray.toString();
    			break;
    		case "UNIQUE" :
    			if(radioAnwser1.isSelected())
        			answer = "1";
    			if(radioAnswer2.isSelected())
        			answer = "2";
    			if(radioAnswer3.isSelected())
        			answer = "3";
    			if(radioAnswer4.isSelected())
        			answer = "4";
    			if(radioAnswer5.isSelected())
        			answer = "5";
    			if(radioAnswer6.isSelected())
        			answer = "6";
    			if(radioAnswer7.isSelected())
        			answer = "7";
    			if(radioAnswer8.isSelected())
        			answer = "8";
    			break;
    	}
    	student.sendAnswer(answer);
    }
    
    public void typeQuestion(String typeQ){             
        switch(typeQ){
            case "UNIQUE":
                jPanelUniqueAnswer.setVisible(true);
                jPanelMultipleAnswer.setVisible(false);
                jPanelOpenAnswer.setVisible(false);
                break;
            case "MULTIPLE":
                jPanelUniqueAnswer.setVisible(false);
                jPanelMultipleAnswer.setVisible(true);
                jPanelOpenAnswer.setVisible(false);
                break;
            case "OPEN":
                jPanelUniqueAnswer.setVisible(false);
                jPanelMultipleAnswer.setVisible(false);
                jPanelOpenAnswer.setVisible(true);
                break;
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientStudentUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientStudentUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientStudentUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientStudentUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                
                new ClientStudentUI().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.ButtonGroup answerButtonGroup;
    public javax.swing.JButton buttonSendAnswer;
    public javax.swing.JCheckBox checkBoxAnswer1;
    public javax.swing.JCheckBox checkBoxAnswer2;
    public javax.swing.JCheckBox checkBoxAnswer3;
    public javax.swing.JCheckBox checkBoxAnswer4;
    public javax.swing.JCheckBox checkBoxAnswer5;
    public javax.swing.JCheckBox checkBoxAnswer6;
    public javax.swing.JCheckBox checkBoxAnswer7;
    public javax.swing.JCheckBox checkBoxAnswer8;
    public javax.swing.JLabel jLabelTypeQuestion;
    public javax.swing.JPanel jPanelMultipleAnswer;
    public javax.swing.JPanel jPanelOpenAnswer;
    public javax.swing.JPanel jPanelQuestion;
    public javax.swing.JPanel jPanelUniqueAnswer;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTextPane jTextPaneQuestion;
    public javax.swing.JRadioButton radioAnwser1;
    public javax.swing.JRadioButton radioAnswer2;
    public javax.swing.JRadioButton radioAnswer3;
    public javax.swing.JRadioButton radioAnswer4;
    public javax.swing.JRadioButton radioAnswer5;
    public javax.swing.JRadioButton radioAnswer6;
    public javax.swing.JRadioButton radioAnswer7;
    public javax.swing.JRadioButton radioAnswer8;
    public javax.swing.JTextArea textAreaOpenAnswer;
    // End of variables declaration//GEN-END:variables
}