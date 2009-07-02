/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PluginManagerDialog.java
 *
 * Created on Jun 18, 2009, 9:45:37 PM
 */

package srl.gui;

import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import mccrae.tools.jar.JarClassLoader;
import org.jdesktop.application.Action;

/**
 *
 * @author john
 */
public class PluginManagerDialog extends javax.swing.JFrame {

    /** Creates new form PluginManagerDialog */
    public PluginManagerDialog() {
        initComponents();
        DefaultTableModel dtm = (DefaultTableModel)jTable1.getModel();
        int i = 0;
        for(SRLPlugin plugin : SRLGUIApp.getApplication().plugins) {
            Object[] val = new Object[2];
            val[0] = plugin.getDisplayName();
            val[1] = getPluginIndex(plugin) >= 0;
            dtm.addRow(val);
            i++;
        }
        dtm.addTableModelListener(new TableModelListener() {

            public void tableChanged(TableModelEvent arg0) {
                if(arg0.getFirstRow() < 0 || arg0.getColumn() < 0)
                    return;
                DefaultTableModel dtm = (DefaultTableModel)jTable1.getModel();
                SRLGUIApp app = SRLGUIApp.getApplication();
                if(dtm.getValueAt(arg0.getFirstRow(), arg0.getColumn()).equals(Boolean.TRUE)) {
                    app.addIndexedPreference(SRLGUIApp.PLUGIN_LOAD_JAR_KEY,
                            app.pluginJARs.get(arg0.getFirstRow()));
                    app.addIndexedPreference(SRLGUIApp.PLUGIN_LOAD_CLASS_KEY,
                            app.pluginClass.get(arg0.getFirstRow()));
                } else {
                    int idx = getPluginIndex(app.plugins.get(arg0.getFirstRow()));

                    app.removeIndexedPreference(SRLGUIApp.PLUGIN_LOAD_JAR_KEY,idx);
                    app.removeIndexedPreference(SRLGUIApp.PLUGIN_LOAD_CLASS_KEY,idx);

                }
            }
        });
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent arg0) {
                if(arg0.getFirstIndex() < 0)
                    return;
                jTextArea1.setText(SRLGUIApp.getApplication().plugins.get(arg0.getFirstIndex()).getComment());
            }
        });
    }

    private int getPluginIndex(SRLPlugin plugin) {
         SRLGUIApp app = SRLGUIApp.getApplication();
         String[] pluginClasses = app.getIndexedPreferences(SRLGUIApp.PLUGIN_LOAD_CLASS_KEY);
         String[] pluginJARs = app.getIndexedPreferences(SRLGUIApp.PLUGIN_LOAD_JAR_KEY);
         for(int i = 0; i < app.pluginClass.size(); i++) {
             if(app.plugins.get(i) == plugin) {
                for(int j = 0; j < pluginClasses.length; j++) {
                     if(app.pluginClass.get(i).equals(pluginClasses[j]) &&
                             app.pluginJARs.get(i).equals(pluginJARs[j]))
                        return j;
                }
             }
         }
         return -1;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(srl.gui.SRLGUIApp.class).getContext().getResourceMap(PluginManagerDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(srl.gui.SRLGUIApp.class).getContext().getActionMap(PluginManagerDialog.class, this);
        jButton1.setAction(actionMap.get("removePlugin")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        jButton2.setAction(actionMap.get("addPlugin")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jButton3.setAction(actionMap.get("close")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane2.setViewportView(jTextArea1);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Always Load"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setName("jTable1"); // NOI18N
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 248, Short.MAX_VALUE)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PluginManagerDialog().setVisible(true);
            }
        });
    }

    JFileChooser jfc = new JFileChooser();

    @Action
    public void addPlugin() {
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("JAR Files", "jar");
        jfc.addChoosableFileFilter(fnef);
        if(jfc.showOpenDialog(jfc) == JFileChooser.APPROVE_OPTION) {
            LinkedList<SRLPlugin> plugins = new LinkedList<SRLPlugin>();
            LinkedList<String> classes = new LinkedList<String>();
            try {
                JarInputStream jis = new JarInputStream(new FileInputStream(jfc.getSelectedFile()));
                JarEntry je = jis.getNextJarEntry();
                while(je != null) {
                    if(je.getName().endsWith(".class")) {
                        classes.add(je.getName());
                    }
                    je = jis.getNextJarEntry();
                }
                JarClassLoader jcl = new JarClassLoader(jfc.getSelectedFile().getPath());
                for(String cl : classes) {
                    cl = cl.replaceAll("/", ".");
                    cl = cl.substring(0, cl.length()-6);
                    Class c = jcl.loadClass(cl);
                    if(SRLPlugin.class.isAssignableFrom(c)) {
                        try {
                            plugins.add((SRLPlugin)c.getConstructor().newInstance());
                        } catch(NoSuchMethodException x) {
                            System.err.println(cl + " does not have a valid constructor");
                        }
                    }
                }
                if(plugins.size() == 0) {
                    JOptionPane.showMessageDialog(this, "There were no plug-in files in the selected JAR file", "Could not load plug-in", JOptionPane.WARNING_MESSAGE);
                } else if(plugins.size() == 1 ) {
                    SRLGUIApp.getApplication().addPlugin(plugins.get(0), jfc.getSelectedFile().getPath(), plugins.get(0).getClass().getCanonicalName());
                    DefaultTableModel dtm = (DefaultTableModel)jTable1.getModel();
                    Object[] val = new Object[2];
                    val[0] = plugins.get(0).getDisplayName();
                    val[1] = Boolean.FALSE;
                    dtm.addRow(val);
                } else {
                    String[] options = new String[plugins.size()];
                    int i = 0;
                    for(SRLPlugin plugin : plugins) {
                        options[i++] = plugin.getDisplayName();
                    }
                    Object choice = JOptionPane.showInputDialog(this,
                            "Multiple plug-ins were found, please select one",
                            "Choose a plug-in", JOptionPane.QUESTION_MESSAGE,
                            null, options, options[0]);
                    i = 0;
                    for(String s : options) {
                        if(s == choice) {
                            SRLGUIApp.getApplication().addPlugin(plugins.get(i), jfc.getSelectedFile().getPath(), plugins.get(i).getClass().getCanonicalName());
                            DefaultTableModel dtm = (DefaultTableModel)jTable1.getModel();
                            Object[] val = new Object[2];
                            val[0] = choice;
                            val[1] = Boolean.FALSE;
                            dtm.addRow(val);
                            break;
                        }
                        i++;
                    }
                }
            } catch(Exception x) {
                x.printStackTrace();
                JOptionPane.showMessageDialog(this, x.getMessage(), "Could not load plug-in", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

    }

    @Action
    public void removePlugin() {
        if(jTable1.getSelectedRow() == -1)
            return;
        SRLGUIApp app = SRLGUIApp.getApplication();
        try {
            app.removePlugin(app.plugins.get(jTable1.getSelectedRow()));
        } catch(CannotDieException x) {
            JOptionPane.showMessageDialog(this, x.getMessage(), "Unable to remove selected plug-in", JOptionPane.WARNING_MESSAGE);
        }
        DefaultTableModel dtm = (DefaultTableModel)jTable1.getModel();
        dtm.removeRow(jTable1.getSelectedRow());
    }

    @Action
    public void close() {
        setVisible(false);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

}
