/* 
 * Copyright (c) 2008, National Institute of Informatics
 *
 * This file is part of SRL, and is free
 * software, licenced under the GNU Library General Public License,
 * Version 2, June 1991.
 *
 * A copy of this licence is included in the distribution in the file
 * licence.html, and is also available at http://www.fsf.org/licensing/licenses/info/GPLv2.html.
 */
package srl.gui;

import java.io.IOException;
import javax.swing.event.DocumentEvent;
import org.apache.lucene.document.Document;
import srl.rule.*;
import javax.swing.table.*;
import java.util.*;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentListener;
import mccrae.tools.process.StopSignal;
import mccrae.tools.strings.Strings;
import mccrae.tools.struct.Pair;
import org.apache.lucene.analysis.TokenStream;
import srl.rule.parser.ParseException;
import srl.corpus.*;
import srl.rule.parser.TokenMgrError;

/**
 *
 * @author  john
 */
public class RuleSetPanel extends javax.swing.JPanel implements Closeable {

    RuleSet ruleSet;
    int ruleCount;
    boolean userChangeFlag = true;
    HashMap<String, Rule> ruleLookup;
    int oldSelectIndex = -1;

    /** Creates new form RuleSetPanel */
    public RuleSetPanel(RuleSet ps) {
        initComponents();
        ruleSet = ps;
        DefaultListModel dlm = (DefaultListModel) ruleIDList.getModel();
        ruleLookup = new HashMap<String, Rule>();

        for (int i = 0; i < ps.rules.size(); i++) {
            dlm.addElement(ps.rules.get(i).first + ": " + ps.rules.get(i).second.toString());
            ruleLookup.put(ps.rules.get(i).first, ps.rules.get(i).second);
        }
        commentField.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                if (userChangeFlag) {
                    onCommentChange();
                }
            }

            public void removeUpdate(DocumentEvent e) {
                if (userChangeFlag) {
                    onCommentChange();
                }
            }

            public void changedUpdate(DocumentEvent e) {
                if (userChangeFlag) {
                    onCommentChange();
                }
            }
        });
        if (ps.rules.size() > 0) {
            ruleIDList.setSelectedIndex(0);
            ruleEditor.setEnabled(true);
            idEditor.setEnabled(true);
            commentField.setEnabled(true);
            onRuleSelect();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        matchesTable = new javax.swing.JTable();
        matchesLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        commentField = new javax.swing.JEditorPane();
        idEditor = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        removeButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ruleIDList = new javax.swing.JList();
        ruleEditor = new srl.gui.AutoCompleteTextField();

        setName("Form"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        matchesTable.setAutoCreateRowSorter(true);
        matchesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Document Name", "Matches"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        matchesTable.setName("matchesTable"); // NOI18N
        jScrollPane2.setViewportView(matchesTable);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(srl.gui.SRLGUIApp.class).getContext().getResourceMap(RuleSetPanel.class);
        matchesLabel.setText(resourceMap.getString("matchesLabel.text")); // NOI18N
        matchesLabel.setName("matchesLabel"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        commentField.setEnabled(false);
        commentField.setMaximumSize(new java.awt.Dimension(2147483647, 120));
        commentField.setName("commentField"); // NOI18N
        jScrollPane3.setViewportView(commentField);

        idEditor.setText(resourceMap.getString("idEditor.text")); // NOI18N
        idEditor.setEnabled(false);
        idEditor.setName("idEditor"); // NOI18N
        idEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idEditorActionPerformed(evt);
            }
        });

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        removeButton.setText(resourceMap.getString("removeButton.text")); // NOI18N
        removeButton.setToolTipText(resourceMap.getString("removeButton.toolTipText")); // NOI18N
        removeButton.setName("removeButton"); // NOI18N
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        addButton.setText(resourceMap.getString("addButton.text")); // NOI18N
        addButton.setToolTipText(resourceMap.getString("addButton.toolTipText")); // NOI18N
        addButton.setName("addButton"); // NOI18N
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        jScrollPane1.setMaximumSize(new java.awt.Dimension(258, 32767));
        jScrollPane1.setName("jScrollPane1"); // NOI18N

        ruleIDList.setModel(new DefaultListModel());
        ruleIDList.setMaximumSize(new java.awt.Dimension(250, 2000000));
        ruleIDList.setName("ruleIDList"); // NOI18N
        ruleIDList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                ruleIDListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(ruleIDList);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jLabel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(addButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(removeButton))
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel2)
                    .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(removeButton)
                        .add(addButton)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE))
        );

        ruleEditor.setText(resourceMap.getString("ruleEditor.text")); // NOI18N
        ruleEditor.setEnabled(false);
        ruleEditor.setName("ruleEditor"); // NOI18N
        ruleEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ruleEditorActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(idEditor, 0, 0, Short.MAX_VALUE)
                            .add(matchesLabel))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(ruleEditor, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE))
                    .add(jLabel3))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(idEditor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel4))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(matchesLabel))
                            .add(ruleEditor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 330, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        DefaultListModel dlm = (DefaultListModel) ruleIDList.getModel();
        String ruleID = JOptionPane.showInputDialog(this, "Rule ID:", "");
        if (ruleID == null) {
            return;
        }
        if (ruleLookup.containsKey(ruleID)) {
            JOptionPane.showMessageDialog(this, "ID already exists");
            return;
        }
        Rule rule = new Rule(ruleSet.ruleType);
        if (ruleSet.ruleType == Rule.TEMPLATE_RULE) {
            rule.addHead("head", "X");
        }
        ruleSet.rules.add(new Pair<String, Rule>((String) ruleID, rule));
        ruleLookup.put(ruleID, rule);
        dlm.addElement(ruleID + ": " + rule.toString());
        SRLGUIApp.getApplication().setModified();
    }//GEN-LAST:event_addButtonActionPerformed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        userChangeFlag = false;
        int idx = ruleIDList.getSelectedIndex();
        if (idx < 0) {
            return;
        }
        ruleLookup.remove(ruleSet.rules.get(idx).first);
        DefaultListModel dlm = (DefaultListModel) ruleIDList.getModel();
        dlm.removeElementAt(idx);
        ruleSet.rules.remove(idx);
        SRLGUIApp.getApplication().setModified();
        userChangeFlag = true;
        oldSelectIndex = -1;
    }//GEN-LAST:event_removeButtonActionPerformed

    public boolean onClose() {
        if (ruleIDList.getSelectedIndex() == -1) {
            return true;
        }
        try {
            Rule rule = Rule.ruleFromString(ruleEditor.getText(), ruleSet.ruleType);
            rule.comment = commentField.getText();
            ruleSet.rules.get(ruleIDList.getSelectedIndex()).second = rule;
            ruleSet.rules.get(ruleIDList.getSelectedIndex()).first = idEditor.getText();
        } catch (ParseException x) {
            if (JOptionPane.showConfirmDialog(this, "Rule syntax is not correct, discard changes?", "Can't save rule", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                return false;
            }
        } catch (TokenMgrError e) {
            if (JOptionPane.showConfirmDialog(this, "Rule syntax is not correct, discard changes?", "Can't save rule", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                return false;
            }
        }
        return true;
    }
    private boolean resetRuleEditor = true;

    private void ruleIDListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_ruleIDListValueChanged
        if (oldSelectIndex >= 0 && oldSelectIndex != ruleIDList.getSelectedIndex()) {
            try {
                Rule rule = Rule.ruleFromString(ruleEditor.getText(), ruleSet.ruleType);
                rule.comment = commentField.getText();
                ruleSet.rules.get(oldSelectIndex).second = rule;
                ruleSet.rules.get(oldSelectIndex).first = idEditor.getText();
                validateRule(rule);
            } catch (ParseException x) {
                if (JOptionPane.showConfirmDialog(this, "Rule syntax is not correct, discard changes?", "Can't save rule", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                    int temp = oldSelectIndex;
                    oldSelectIndex = -1;
                    resetRuleEditor = false;
                    ruleIDList.setSelectedIndex(temp);
                    return;
                }
            } catch (TokenMgrError x) {
                if (JOptionPane.showConfirmDialog(this, "Rule syntax is not correct, discard changes?", "Can't save rule", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                    int temp = oldSelectIndex;
                    oldSelectIndex = -1;
                    resetRuleEditor = false;
                    ruleIDList.setSelectedIndex(temp);
                    return;
                }
            }
        }
        if (ruleIDList.getSelectedIndex() >= 0) {
            onRuleSelect();
        }
    }//GEN-LAST:event_ruleIDListValueChanged

private void idEditorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idEditorActionPerformed
    if (ruleIDList.getSelectedIndex() == -1) {
        return;
    }
    ruleSet.rules.get(ruleIDList.getSelectedIndex()).first = idEditor.getText();
    DefaultListModel dlm = (DefaultListModel) ruleIDList.getModel();
    String ruleID = ruleSet.rules.get(ruleIDList.getSelectedIndex()).first;
    dlm.setElementAt(ruleID + ": " + ruleEditor.getText(), ruleIDList.getSelectedIndex());
    SRLGUIApp.getApplication().setModified();
}//GEN-LAST:event_idEditorActionPerformed

private void ruleEditorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ruleEditorActionPerformed
                                       
        if (ruleIDList.getSelectedIndex() == -1) {
            return;
        }
        Rule rule;
        try {
            rule = Rule.ruleFromString(ruleEditor.getText(), ruleSet.ruleType);
            ruleSet.rules.get(ruleIDList.getSelectedIndex()).second = rule;
            DefaultListModel dlm = (DefaultListModel) ruleIDList.getModel();
            String ruleID = ruleSet.rules.get(ruleIDList.getSelectedIndex()).first;
            dlm.setElementAt(ruleID + ": " + rule.toString(), ruleIDList.getSelectedIndex());
            rule.comment = commentField.getText();
            validateRule(rule);
            SRLGUIApp.getApplication().setModified();
            if (matcherThread != null && matcherThread.isAlive()) {
                matchFinder.sig.stop();
            }
            matcherThread = new Thread(matchFinder = new RuleMatchFinder(rule));
            matchesLabel.setText("Matching...");
            if (!rule.body.isEmpty()) {
                matcherThread.start();
            }
        } catch (ParseException x) {
            JOptionPane.showMessageDialog(this, x.getMessage(), "Rule error", JOptionPane.WARNING_MESSAGE);
        }
    
}//GEN-LAST:event_ruleEditorActionPerformed

    private void validateRule(Rule rule) {
        for(TypeExpr te : rule.body) {
            if(te instanceof Literal) {
                Literal l = (Literal)te;
                TokenStream ts = SRLGUIApp.getApplication().proj.corpus.getProcessor().getTokenStream(l.getVal());
                List<String> tokens = new LinkedList<String>();
                while(true) {
                    try {
                        org.apache.lucene.analysis.Token s = ts.next();
                        if(s == null)
                            break;
                        tokens.add(s.termText());
                    } catch(IOException x) {
                        x.printStackTrace();
                        break;
                    }
                }
                if(tokens.size() != 1) {
                    JOptionPane.showMessageDialog(this, "Literal is not single token so will not match: \n\"" + l.getVal() +
                            "\" should be \"" + Strings.join("\" \"", tokens) + "\"", "Invalid literal", JOptionPane.WARNING_MESSAGE);
                }
            } else if(te instanceof Entity) {
                Pair<String,String> ent = new Pair<String,String>(((Entity)te).entityType, ((Entity)te).entityValue);
                if(!SRLGUIApp.getApplication().proj.entities.contains(ent)) {
                    int opt = JOptionPane.showConfirmDialog(this, "Unknown entity type/value: " + ent.first + "/" + ent.second + ". Add to project?", 
                            "Unknown entity", JOptionPane.YES_NO_OPTION);
                    if(opt == JOptionPane.YES_OPTION) {
                        SRLGUIApp.getApplication().proj.entities.add(ent);
                    }
                }
            }
        }
    }

private Thread matcherThread;
    private RuleMatchFinder matchFinder;

    private void onRuleSelect() {
        if (oldSelectIndex == ruleIDList.getSelectedIndex()) {
            return;
        }
        Rule r = ruleSet.rules.get(ruleIDList.getSelectedIndex()).second;
        if (resetRuleEditor) {
            ruleEditor.setText(r.toString());
            idEditor.setText(ruleSet.rules.get(ruleIDList.getSelectedIndex()).first);
        } else {
            resetRuleEditor = true;
        }
        commentField.setText(r.comment);
        ((DefaultTableModel) matchesTable.getModel()).setRowCount(0);
        if (matcherThread != null && matcherThread.isAlive()) {
            matchFinder.sig.stop();
        }
        matcherThread = new Thread(matchFinder = new RuleMatchFinder(r));
        matchesLabel.setText("Matching...");
        if (!r.body.isEmpty()) {
            matcherThread.start();
        }
        oldSelectIndex = ruleIDList.getSelectedIndex();
            ruleEditor.setEnabled(true);
            idEditor.setEnabled(true);
            commentField.setEnabled(true);
    }
    
    private void onCommentChange() {
        if(ruleIDList.getSelectedIndex() < 0)
            return;
        ruleSet.rules.get(ruleIDList.getSelectedIndex()).second.comment =
                commentField.getText();
    }

    private class RuleMatchFinder implements Runnable {

        Rule rule;
        StopSignal sig;

        RuleMatchFinder(Rule r) {
            this.rule = r;
            sig = new StopSignal();
        }

        public void run() {
            final Corpus corpus = SRLGUIApp.getApplication().proj.corpus;
            try {
                final List<String> docs = new LinkedList<String>();
                final List<String> vars = new LinkedList<String>();
                sig = new StopSignal();
                corpus.query(rule.getCorpusQuery(), new Corpus.QueryHit() {

                    public void hit(Document d, StopSignal signal) {
                        if (signal.isStopped()) {
                            return;
                        }
                        SrlDocument doc = new SrlDocument(d, corpus.getProcessor(), ruleSet.ruleType == Rule.TEMPLATE_RULE);
                        List<HashMap<Entity, SrlMatchRegion>> results = rule.getMatch(doc, false);
                        if (results != null) {
                            for (HashMap<Entity, SrlMatchRegion> result : results) {
                                docs.add(doc.getName());
                                StringBuffer s = new StringBuffer();

                                for (Map.Entry<Entity, SrlMatchRegion> entry : result.entrySet()) {
                                    s.append(entry.getKey().var + "=" + entry.getValue().toString() + "; ");
                                }
                                vars.add(s.substring(0, s.length() > 0 ? s.length() - 2 : 0));
                            }
                        }
                    }
                }, sig);
                if (sig.isStopped()) {
                    sig.confirmStop();
                    return;
                }
                DefaultTableModel dtm = (DefaultTableModel) matchesTable.getModel();
                dtm.setRowCount(0);
                Iterator<String> varIter = vars.iterator();
                for (String s : docs) {
                    Object[] rowData = new Object[2];
                    rowData[0] = s;
                    rowData[1] = varIter.next();
                    dtm.addRow(rowData);
                }
                matchesLabel.setText("Matches: " + docs.size());
            } catch (IOException x) {
                JOptionPane.showMessageDialog(RuleSetPanel.this, x.getMessage(), "Disk Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JEditorPane commentField;
    private javax.swing.JTextField idEditor;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel matchesLabel;
    private javax.swing.JTable matchesTable;
    private javax.swing.JButton removeButton;
    private srl.gui.AutoCompleteTextField ruleEditor;
    private javax.swing.JList ruleIDList;
    // End of variables declaration//GEN-END:variables
}
