package com.estacionamiento.gui;

import com.estacionamiento.model.Estacionamiento;
import com.estacionamiento.model.Vehiculo;
import com.estacionamiento.model.Ticket;
import com.estacionamiento.model.Espacio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ParkingApp extends JFrame {
    private Estacionamiento estacionamiento;
    
    // Components for "Entrada"
    private JTextField txtPlaca;
    private JRadioButton rbCarro, rbMoto;
    private JTextArea areaTicket;
    
    // Components for "Salida"
    private JTextField txtPlacaSalida;
    private JTextField txtCosto;
    
    // Components for "Estado"
    private JTable tableEstado;
    private DefaultTableModel modelEstado;
    
    // Components for "Historial"
    private JTable tableHistorial;
    private DefaultTableModel modelHistorial;

    public ParkingApp() {
        estacionamiento = new Estacionamiento();
        initUI();
    }

    private void initUI() {
        setTitle("Sistema de Gestión de Estacionamiento");
        setSize(600, 500);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Registrar Entrada", createEntradaPanel());
        tabbedPane.addTab("Registrar Salida", createSalidaPanel());
        tabbedPane.addTab("Estado", createEstadoPanel());
        tabbedPane.addTab("Historial", createHistorialPanel());

        add(tabbedPane);
    }

    private JPanel createEntradaPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        txtPlaca = new JTextField();
        JPanel row1 = new JPanel(new BorderLayout());
        row1.add(new JLabel("Placa: "), BorderLayout.WEST);
        row1.add(txtPlaca, BorderLayout.CENTER);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rbCarro = new JRadioButton("Carro", true);
        rbMoto = new JRadioButton("Moto");
        ButtonGroup group = new ButtonGroup();
        group.add(rbCarro);
        group.add(rbMoto);
        row2.add(new JLabel("Tipo: "));
        row2.add(rbCarro);
        row2.add(rbMoto);

        JButton btnRegistrar = new JButton("Registrar Entrada");
        btnRegistrar.setBackground(Color.decode("#2E75B6"));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrar.addActionListener(this::registrarEntrada);

        formPanel.add(row1);
        formPanel.add(row2);
        formPanel.add(btnRegistrar);

        areaTicket = new JTextArea();
        areaTicket.setEditable(false);
        areaTicket.setFont(new Font("Courier New", Font.PLAIN, 12));
        areaTicket.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(areaTicket), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createSalidaPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        txtPlacaSalida = new JTextField();
        txtCosto = new JTextField("10.0"); // Valor por defecto

        formPanel.add(new JLabel("Placa del Vehículo:"));
        formPanel.add(txtPlacaSalida);
        formPanel.add(new JLabel("Costo a Cobrar ($):"));
        formPanel.add(txtCosto);

        JButton btnSalida = new JButton("Registrar Salida y Cobrar");
        btnSalida.addActionListener(this::registrarSalida);
        
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(btnSalida, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createEstadoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"# Espacio", "Tipo", "Estado", "Vehículo"};
        modelEstado = new DefaultTableModel(columns, 0);
        tableEstado = new JTable(modelEstado);
        
        panel.add(new JScrollPane(tableEstado), BorderLayout.CENTER);
        
        panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                actualizarEstado();
            }
        });

        return panel;
    }

    private JPanel createHistorialPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"Placa", "Tipo", "Entrada", "Salida", "Costo"};
        modelHistorial = new DefaultTableModel(columns, 0);
        tableHistorial = new JTable(modelHistorial);
        
        panel.add(new JScrollPane(tableHistorial), BorderLayout.CENTER);

        panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                actualizarHistorial();
            }
        });

        return panel;
    }

    private void registrarEntrada(ActionEvent e) {
        String placa = txtPlaca.getText();
        String tipo = rbCarro.isSelected() ? "Carro" : "Moto";

        try {
            Vehiculo v = new Vehiculo(placa, tipo);
            Ticket t = estacionamiento.registrarEntrada(v);
            areaTicket.setText(t.imprimirTicket());
            txtPlaca.setText("");
            JOptionPane.showMessageDialog(this, "Entrada registrada con éxito.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarSalida(ActionEvent e) {
        String placa = txtPlacaSalida.getText();
        String costoStr = txtCosto.getText();

        try {
            double costo = Double.parseDouble(costoStr);
            Ticket t = estacionamiento.registrarSalida(placa, costo);
            JOptionPane.showMessageDialog(this, "Salida registrada.\n\n" + t.imprimirTicket());
            txtPlacaSalida.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Costo inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarEstado() {
        modelEstado.setRowCount(0);
        for (Espacio e : estacionamiento.getEspacios()) {
            modelEstado.addRow(new Object[]{
                e.getNumero(),
                e.getTipo(),
                e.isDisponible() ? "Disponible" : "Ocupado",
                e.getVehiculo() != null ? e.getVehiculo().getPlaca() : "-"
            });
        }
    }

    private void actualizarHistorial() {
        modelHistorial.setRowCount(0);
        List<Ticket> historial = estacionamiento.getHistorialTickets();
        for (Ticket t : historial) {
            modelHistorial.addRow(new Object[]{
                t.getVehiculo().getPlaca(),
                t.getVehiculo().getTipo(),
                t.getHoraEntrada(),
                t.getHoraSalida(),
                "$" + String.format("%.2f", t.getCosto())
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ParkingApp().setVisible(true);
        });
    }
}
