//package ru.ezhov.test.help;
//
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.logging.Logger;
//
//public class Frmt extends javax.swing.JFrame {
//
//
//    public Connection c = null;
//    public DefaultComboBoxModel dcb = new DefaultComboBoxModel();
//    PreparedStatement pst;
//    public String k;
//    public String sm;
//    private static List<String> lt;
//    private MyListModel dlm;
//
//    public void LoadList() {
//
//        // Запись из бд SQLite в Combobox
//        try {
//            String d = "Wrd";
//            String qury = "SELECT name FROM sqlite_master WHERE type = 'table' ;";
//            pst = c.prepareStatement(qury);
//            ResultSet rs = pst.executeQuery();
//            while (rs.next()) {
//
//                dcb.addElement(rs.getString("name"));
//
//            }
//            jComboBox1.setModel(dcb);
//            DLm(d);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//
//        // Выбор элемента из Combobox
//        jComboBox1.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent ie) {
//                String sa = (String) jComboBox1.getSelectedItem();
//                DLm(sa);
//
//            }
//        });
//    }
//
//
//    public void DLm(String sa) {
//        // Запись в jList
//        k = sa;
//        System.out.println(k);
//        try {
//            String qry = "Select word from '" + sa + "' ;";
//            pst = c.prepareStatement(qry);
//            ResultSet rs = pst.executeQuery();
//            MyListModel dlm = new MyListModel(lt);
//            while (rs.next()) {
//                String st = (rs.getString("word"));
//                sm = st;
//                lt.add(rs.getString(st));
//            }
//            jList1.setModel(dlm);
//            dlm.find(sm);
//            pst.close();
//            rs.close();
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        jTextField1.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyReleased(KeyEvent e) {
//                dlm.find(jTextField1.getText());
//            }
//        });
//
//// Читаем jList в jTextArea
//        jList1.getSelectionModel().addListSelectionListener(e -> {
//            String std = jList1.getSelectedValue();
//            try {
//                Statement st = c.createStatement();
//                String qry = "Select id, word, meaning from '" + sa + "' where word = '" + std + "' ;";
//                ResultSet rs = st.executeQuery(qry);
//                while (rs.next()) {
//                    int i = rs.getInt(1);
//                    String is = String.valueOf(i);
//                    String w = rs.getString(2);
//                    String s = rs.getString(3);
//                    //jTextArea1.setText(s);
//                    jTextField1.setText(w);
//                    jTextPane1.setText(s);
//                    jLabel1.setText(sa);
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(Frmt.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//
//        });
//
//    }
//
//    class MyListModel extends DefaultListModel<String> {
//        private List<String> h;
//
//        //Требует создать конструктор.
//        private MyListModel(List<String> lt) {
//            h = lt;
//        }
//
//        private MyListModel(String... strings) {
//
//            h = Arrays.asList(strings);
//            fillList(lt);
//        }
//
//
//        public void find(String text) {
//            List<String> search = new ArrayList<>();
//            if ("".equals(text)) {
//                search = lt;
//            } else {
//                search.removeAll(search);
//                for (String s : lt) {
//                    if (s.toLowerCase().trim().contains(text.toLowerCase().trim())) {
//                        search.add(s);
//                    }
//                }
//            }
//
//            fillList(search);
//        }
//
//        private void fillList(List<String> list) {
//            removeAllElements();
//            for (String s : list) {
//                addElement(s);
//            }
//            fireContentsChanged(this, 0, list.size());
//        }
//
//
//    }