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

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import srl.wordlist.*;
import java.util.*;
import javax.swing.event.DocumentListener;

/**
 *
 * @author  john
 */
public class WordListPanel extends javax.swing.JPanel {

    WordListSet wl;
    List<String> oldTable;
    private boolean userChangeFlag = true;

    /** Creates new form WordListPanel */
    public WordListPanel(WordListSet wordList) {
        oldTable = new Vector<String>();
        wl = wordList;
        initComponents();
        int i = 0;
        TreeSet<String> wordLists = new TreeSet<String>(wl.getLists());
        for (String s : wordLists) {
            listCombo.insertItemAt(s, i++);
            if (i == 1) {
                listCombo.setSelectedItem(s);
            }
        }
        listCombo.insertItemAt("New List...", i);
        mainList.getModel().addTableModelListener(new TableModelListener() {

            public void tableChanged(TableModelEvent e) {
                onTableChange(e);
            }
        });
        commentField.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                String list = (String) listCombo.getSelectedItem();
                if (!wl.getLists().contains(list)) {
                    return;
                }
                wl.comment.put(list, commentField.getText());
            }

            public void removeUpdate(DocumentEvent e) {
                String list = (String) listCombo.getSelectedItem();
                if (!wl.getLists().contains(list)) {
                    return;
                }
                wl.comment.put(list, commentField.getText());
            }

            public void changedUpdate(DocumentEvent e) {
                String list = (String) listCombo.getSelectedItem();
                if (!wl.getLists().contains(list)) {
                    return;
                }
                wl.comment.put(list, commentField.getText());
            }
        });
    }

    void deleteListAction() {
        deleteListActionPerformed(null);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        listCombo = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        deleteList = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        mainList = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        commentField = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setName("Form"); // NOI18N

        listCombo.setName("listCombo"); // NOI18N
        listCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listComboActionPerformed(evt);
            }
        });

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(srl.gui.SRLGUIApp.class).getContext().getResourceMap(WordListPanel.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        deleteList.setIcon(resourceMap.getIcon("deleteList.icon")); // NOI18N
        deleteList.setText(resourceMap.getString("deleteList.text")); // NOI18N
        deleteList.setToolTipText(resourceMap.getString("deleteList.toolTipText")); // NOI18N
        deleteList.setName("deleteList"); // NOI18N
        deleteList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteListActionPerformed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        mainList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Word List"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        mainList.setToolTipText(resourceMap.getString("mainList.toolTipText")); // NOI18N
        mainList.setName("mainList"); // NOI18N
        mainList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mainListKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(mainList);

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        commentField.setColumns(20);
        commentField.setRows(3);
        commentField.setName("commentField"); // NOI18N
        jScrollPane2.setViewportView(commentField);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(srl.gui.SRLGUIApp.class).getContext().getActionMap(WordListPanel.class, this);
        jButton1.setAction(actionMap.get("removeElem")); // NOI18N
        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        jButton2.setAction(actionMap.get("addElem")); // NOI18N
        jButton2.setIcon(resourceMap.getIcon("jButton2.icon")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 892, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(listCombo, 0, 781, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(deleteList))
                    .add(layout.createSequentialGroup()
                        .add(jLabel2)
                        .add(18, 18, 18)
                        .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 810, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(jButton2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel1)
                        .add(listCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(deleteList))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 53, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton1)
                    .add(jButton2))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    private void deleteListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteListActionPerformed
        String list = (String) listCombo.getSelectedItem();
        if (!wl.getLists().contains(list)) {
            return;
        }
        if(listCombo.getItemCount() == 2) {
            userChangeFlag = false;
            listCombo.setSelectedIndex(-1);
            userChangeFlag = true;
        }
        removeList(list);
        switchToSelectedList();
        SRLGUIApp.getApplication().addUndoableEdit(new RemoveListEdit(list));
    }//GEN-LAST:event_deleteListActionPerformed

    private void removeList(String list) {
        DefaultComboBoxModel dcbm = (DefaultComboBoxModel) listCombo.getModel();
        dcbm.removeElement(list);
        wl.removeList(list);
    }
    int lastSelectedListIndex = -1;

    private void listComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listComboActionPerformed
        if(!userChangeFlag || listCombo.getSelectedIndex() == -1)
            return;
        if (listCombo.getSelectedItem().equals("New List...")) {
            String name = JOptionPane.showInputDialog(this, "List name ", "");
            if (name != null && name.length() > 0) {
                if (!name.matches("[A-Za-z0-9_]+")) {
                    JOptionPane.showMessageDialog(this, name + " is not a valid list name. Must contain only alphanumeric characters and underscores", "Could not add list", JOptionPane.WARNING_MESSAGE);
                    listCombo.setSelectedIndex(-1);
                    return;
                }
                if (!wl.addList(name)) {
                    JOptionPane.showMessageDialog(this, "There is already a list named " + name, "Could not add list", JOptionPane.WARNING_MESSAGE);
                    listCombo.setSelectedIndex(-1);
                    return;
                }

                addList(name);
                SRLGUIApp.getApplication().addUndoableEdit(new NewListEdit(name));
                SRLGUIApp.getApplication().proj.corpus.listenToWordList(name, WordListSet.getWordList(name));
            } else {
                listCombo.setSelectedIndex(lastSelectedListIndex);
            }
        }
        switchToSelectedList();
    }//GEN-LAST:event_listComboActionPerformed

    void addListAction() {
         String name = JOptionPane.showInputDialog(this, "List name ", "");
            if (name != null && name.length() > 0) {
                if (!name.matches("[A-Za-z0-9_]+")) {
                    JOptionPane.showMessageDialog(this, name + " is not a valid list name. Must contain only alphanumeric characters and underscores", "Could not add list", JOptionPane.WARNING_MESSAGE);
                    listCombo.setSelectedIndex(-1);
                    return;
                }
                if (!wl.addList(name)) {
                    JOptionPane.showMessageDialog(this, "There is already a list named " + name, "Could not add list", JOptionPane.WARNING_MESSAGE);
                    listCombo.setSelectedIndex(-1);
                    return;
                }

                addList(name);
                SRLGUIApp.getApplication().addUndoableEdit(new NewListEdit(name));
                SRLGUIApp.getApplication().proj.corpus.listenToWordList(name, WordListSet.getWordList(name));
            } else {
                listCombo.setSelectedIndex(lastSelectedListIndex);
            }
         switchToSelectedList();
    }

    private void addList(String name) {
         listCombo.insertItemAt(name, listCombo.getItemCount() - 1);
         listCombo.setSelectedItem(name);
         jButton1.setEnabled(true);
         jButton2.setEnabled(true);
    }

    private void mainListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mainListKeyPressed
        if (evt.getKeyCode() == evt.VK_DELETE) {
            String list = (String) listCombo.getSelectedItem();
            if (!wl.getLists().contains(list)) {
                return;
            }
            int idx = mainList.getSelectedRow();
            if (idx == -1) {
                return;
            }
            String oldVal = oldTable.get(idx);
            WordListEntry oldWle = wl.getEntry(oldVal);

            Set<WordListEntry> wordList = WordListSet.getWordList(list);
            if (!wordList.contains(oldWle)) {
                return;
            }
            userChangeFlag = false;
            ((DefaultTableModel) mainList.getModel()).removeRow(idx);
            wordList.remove(oldWle);
            oldTable.remove(oldVal);
            SRLGUIApp.getApplication().addUndoableEdit(new ChangeListEdit(idx, oldVal, "",(String)listCombo.getSelectedItem()));
            userChangeFlag = true;
        }
    }//GEN-LAST:event_mainListKeyPressed

    private void switchToSelectedList() {
        userChangeFlag = false;
        lastSelectedListIndex = listCombo.getSelectedIndex();
        String list = (String) listCombo.getSelectedItem();
        if(list == null)
            return;
        if (!wl.getLists().contains(list)) {
            if(list.equals("")) {
                jButton1.setEnabled(false);
                jButton2.setEnabled(false);
            }
            return;
        }
        jButton1.setEnabled(true);
        jButton2.setEnabled(true);
        DefaultTableModel dlm = (DefaultTableModel) mainList.getModel();
        dlm.setRowCount(0);
        oldTable.clear();
        int i = 0;
        TreeSet<WordListEntry> l = new TreeSet<WordListEntry>(WordListSet.getWordList(list));
        for (WordListEntry s : l) {
            String[] rowData = {s.toString()};
            dlm.insertRow(i++, rowData);
            oldTable.add(s.toString());
        }
        String[] rowData = {""};
        dlm.insertRow(i, rowData);
        oldTable.add("");
        commentField.setText(wl.comment.get(list));
        userChangeFlag = true;
    }

    private void onTableChange(TableModelEvent e) {
        if (!userChangeFlag) {
            return;
        }
        String list = (String) listCombo.getSelectedItem();
        if (!wl.getLists().contains(list)) {
            return;
        }
        int idx = e.getFirstRow();
        String oldVal, newVal = (String) mainList.getValueAt(idx, 0);
        oldVal = oldTable.get(idx);
        changeElem(idx, oldVal, newVal,list);
        SRLGUIApp.getApplication().addUndoableEdit(new ChangeListEdit(idx, oldVal, newVal, (String)listCombo.getSelectedItem()));
    }

    @org.jdesktop.application.Action
    public void addElem() {
        String s = JOptionPane.showInputDialog(this, "Element", "");
        if (s != null && !s.equals("")) {
            DefaultTableModel dlm = (DefaultTableModel) mainList.getModel();
            int idx = dlm.getRowCount() - 1;
            changeElem(idx, "", s,(String)listCombo.getSelectedItem());
            SRLGUIApp.getApplication().addUndoableEdit(new ChangeListEdit(idx, "", s, (String)listCombo.getSelectedItem()));
        }
    }

    @org.jdesktop.application.Action
    public void removeElem() {
        DefaultTableModel dlm = (DefaultTableModel) mainList.getModel();
        int idx = mainList.getSelectedRow();
        if (idx == -1 || idx == mainList.getRowCount() - 1) {
            return;
        }
        changeElem(idx, (String)mainList.getValueAt(idx,0), "", (String)listCombo.getSelectedItem());
        SRLGUIApp.getApplication().addUndoableEdit(new ChangeListEdit(idx, (String)mainList.getValueAt(idx,0), "", (String)listCombo.getSelectedItem()));
    }

    private void changeElem(int idx, String oldVal, String newVal, String list) {
        boolean changeUI = list.equals((String) listCombo.getSelectedItem());
        if (!wl.getLists().contains(list)) {
            return;
        }

        WordListEntry oldWle;
        try {
            oldWle = wl.getEntry(oldVal);
            if (newVal.length() == 0) {
                Set<WordListEntry> wordList = WordListSet.getWordList(list);
                if (!wordList.contains(oldWle)) {
                    return;
                }
                userChangeFlag = false;
                if(changeUI)
                    ((DefaultTableModel) mainList.getModel()).removeRow(idx);
                wordList.remove(oldWle);
                if(changeUI)
                    oldTable.remove(idx);
                userChangeFlag = true;
                return;
            }
            if (oldVal.length() != 0) {
                WordListSet.getWordList(list).remove(oldWle);
            } else {
                userChangeFlag = false;
                DefaultTableModel dlm = (DefaultTableModel) mainList.getModel();
                if (changeUI && (!oldTable.get(oldTable.size() - 1).equals("") || oldTable.size() - 1 == idx)) {
                    String[] rowData = {""};
                    dlm.addRow(rowData);
                    oldTable.add("");
                    dlm.setValueAt(newVal,idx,0);
                }
                userChangeFlag = true;
            }
        } catch (Exception x) {
            x.printStackTrace();
            return;
        }
        WordListSet.getWordList(list).add(wl.getEntry(newVal));
        if(changeUI)
            oldTable.set(idx, newVal);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea commentField;
    private javax.swing.JButton deleteList;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox listCombo;
    private javax.swing.JTable mainList;
    // End of variables declaration//GEN-END:variables

    private class NewListEdit extends SimpleUndoableEdit {
        String listName;

        public NewListEdit(String listName) {
            this.listName = listName;
        }

        public String getPresentationName() {
            return "Add list " + listName;
        }

        public void redo() throws CannotRedoException {
            undone = false;
            addList(listName);
        }

        public void undo() throws CannotUndoException {
            undone = true;
            removeList(listName);
        }
    }

    private class RemoveListEdit extends SimpleUndoableEdit {
        String listName;

        public RemoveListEdit(String listName) {
            this.listName = listName;
        }

        public String getPresentationName() {
            return "Remove list " + listName;
        }

        public void redo() throws CannotRedoException {
            undone =false;
            removeList(listName);
        }

        public void undo() throws CannotUndoException {
            undone = true;
            addList(listName);
        }
    }

    private class ChangeListEdit extends SimpleUndoableEdit {
        int idx;
        String oldVal, newVal;
        String listName;

        public ChangeListEdit(int idx, String oldVal, String newVal, String listName) {
            this.idx = idx;
            this.oldVal = oldVal;
            this.newVal = newVal;
            this.listName = listName;
        }



        public String getPresentationName() {
            if(oldVal.equals("")) {
                return "Add element " + newVal + " to list";
            }
            if(newVal.equals("")) {
                return "Remove element " + oldVal + " from list";
            }
            return "Change " + oldVal + " to " + newVal + " in list";
        }

        public void redo() throws CannotRedoException {
            undone = false;
            changeElem(idx, oldVal, newVal, listName);
        }

        public void undo() throws CannotUndoException {
            undone = true;
            changeElem(idx, newVal, oldVal, listName);
        }
    }
}
