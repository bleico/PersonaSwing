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
    
    public Controlador() {
    }
    
    public Controlador(Modelo m, Vista v) {
        
        this.m = m;
        this.v = v;
        this.v.btnEnviar.addActionListener(this);
        this.v.btnConsultar.addActionListener(this);
        this.v.btnActualizar.addActionListener(this);
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
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        else if (v.btnConsultar == e.getSource()) {
            try {
                m.consultar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo consultar" + ex);
            }
        }
        
         
    }
    
}
