package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import vista.Vista;
import vista.Login;
import modelo.Modelo;

public class Controlador implements ActionListener {
    
    private Modelo m;
    private Vista v;
    private Login l;
    
    public Controlador(Modelo m, Vista v) {
        
        this.m = m;
        this.v = v;
        this.v.btnEnviar.addActionListener(this);
        this.v.btnConsultar.addActionListener(this);
        this.v.btnActualizar.addActionListener(this);
        this.v.btnEliminar.addActionListener(this);
        this.v.btnBuscar.addActionListener(this);
        this.v.btnReporte.addActionListener(this);
    }
    
    public void iniciar() {
        
        v.setTitle("Sistema MVC");
        v.pack();
        v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        v.setLocationRelativeTo(null);
        v.setVisible(true);
        
        m.cargarMetodos();
    }
    
    public void actionPerformed(ActionEvent e) {
        
        if (v.btnEnviar == e.getSource()) {
            try {
                m.enviarDatos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"No se pudo enviar\n" + ex);
            }
        }
        else if (v.btnConsultar == e.getSource()) {
            try {
                m.consultar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo consultar\n" + ex);
            }
        }
        else if(v.btnActualizar == e.getSource()) {
            try {
                m.actualizar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar\n" + ex);
            }
        }
        else if (v.btnEliminar == e.getSource()) {
            try {
                m.eliminar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar\n" + ex);
            }
        }
        else if (v.btnBuscar == e.getSource()) {
            try {
                m.buscar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo buscar\n" + ex);
            }
        }
        else if (v.btnReporte == e.getSource()) {
            try{
                m.buscar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo mostrar el Reporte\n" + ex);
            }
        }
         
    }
    
}
