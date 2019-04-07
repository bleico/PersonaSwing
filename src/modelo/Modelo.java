package modelo;

import javax.swing.table.DefaultTableModel;
import controlador.Controlador;
import java.util.Calendar;
import javax.swing.JOptionPane;
import vista.Vista;
import static vista.Vista.comboSexo;
import java.sql.*;

public class Modelo {

    DefaultTableModel modelo = new DefaultTableModel();
    Vista v;
    Controlador c;

    Connection cn;
    Connection conn = Conexion();

    public void cargarMetodos() {
        mostrarTabla();
    }

    public Connection Conexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://localhost/sistema", "root", "");
            System.out.println("Conexion Exitosa");
        } catch (Exception e) {
            System.out.println(e);
        }
        return cn;
    }

    public static void main(String[] args) {
        Modelo m = new Modelo();
        m.Conexion();
    }

    void mostrarTabla() {

        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Edad");
        modelo.addColumn("Sexo");
        modelo.addColumn("Fecha");
        v.tabla.setModel(modelo);

        v.comboSexo.addItem("--Selecciona--");
        v.comboSexo.addItem("Hombre");
        v.comboSexo.addItem("Mujer");
    }

    public void enviarDatos() {

        String dia = Integer.toString(v.jFecha.getCalendar().get(Calendar.DAY_OF_MONTH));
        String mes = Integer.toString(v.jFecha.getCalendar().get(Calendar.MONTH) + 1);
        String año = Integer.toString(v.jFecha.getCalendar().get(Calendar.YEAR));
        String fecha = (dia + " / " + mes + " / " + año);

        try {

            PreparedStatement pst = conn.prepareStatement("INSERT INTO usuarios (Nombre,Apellido,Edad,Sexo,Fecha)"
                    + "VALUES(?,?,?,?,?)");

            pst.setString(1, v.txtNombre.getText());
            pst.setString(2, v.txtApellido.getText());
            pst.setString(3, v.txtEdad.getText());

            if (v.comboSexo.getSelectedItem().toString().equals("--Selecciona--")) {
                JOptionPane.showMessageDialog(null, "Debes elegir un sexo");
            } else {
                v.txtEdad.setText("");
                v.txtNombre.setText("");
                v.txtApellido.setText("");
                pst.setString(4, comboSexo.getSelectedItem().toString() );
                pst.setString(5, fecha);
                pst.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "Datos Guardados");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Datos no Guardados");
        }
    }
}
