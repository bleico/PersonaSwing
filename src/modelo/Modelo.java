package modelo;

import javax.swing.table.DefaultTableModel;
import controlador.Controlador;
import java.util.Calendar;
import javax.swing.JOptionPane;
import vista.Vista;
import static vista.Vista.comboSexo;
import java.sql.*;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class Modelo {

    Vista v;
    Connection cn;
    Connection conn = conexion();

    public void cargarMetodos() {
        mostrarTabla("");
        Bloquear();
    }

    public Connection conexion() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://localhost/sistema", "root", "");
            System.out.println("Conexion Exitosa");
        } catch (Exception e) {
            System.out.println(e);
        }
        return cn;
    }

    public void Bloquear() {

        v.txtNombre.setEnabled(false);
        v.txtApellido.setEnabled(false);
        v.txtEdad.setEnabled(false);
        v.comboSexo.setEnabled(false);
        v.txtFecha.setEnabled(false);
        v.jFecha.setEnabled(false);
        v.btnEnviar.setEnabled(false);
        v.btnActualizar.setEnabled(false);
        v.btnCancelar.setEnabled(false);
        v.btnNuevo.setEnabled(true);
    }

    public void Desbloquear() {

        v.txtNombre.setEnabled(true);
        v.txtApellido.setEnabled(true);
        v.txtEdad.setEnabled(true);
        v.comboSexo.setEnabled(true);
        v.txtFecha.setEnabled(true);
        v.jFecha.setEnabled(true);
        v.btnEnviar.setEnabled(true);
        v.btnActualizar.setEnabled(false);
        v.btnCancelar.setEnabled(true);
        v.btnNuevo.setEnabled(false);
    }

    public void Cancelar() {

        Bloquear();
        v.txtID.setText("");
        v.txtNombre.setText("");
        v.txtApellido.setText("");
        v.txtEdad.setText("");
        v.comboSexo.setSelectedItem("--Selecciona--");
        v.txtFecha.setText("");
        v.jFecha.setDateFormatString("");
    }

    public void GenerarReporte() {
        try {
            //Ruta donde se encuentra el archivo de reporte
            String ruta = "C:\\Users\\bleid\\OneDrive\\Programacion\\Java\\TCS\\PersonaSwing\\src\\"
                    + "vista\\ReporteUsuarios.jrxml";
            JasperReport rj = JasperCompileManager.compileReport(ruta); //compila el reporte
            JasperPrint mr = JasperFillManager.fillReport(rj, null, cn);
            JasperViewer view = new JasperViewer(mr, false);
            view.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo mostrar el Reporte");
        }
    }

    public void buscar() {
        if (v.txtID.equals("")) {
            JOptionPane.showMessageDialog(null, "Campo vacio");
        } else {
            mostrarTabla(v.txtID.getText());
        }
    }

    public void eliminar() {
        int fila = v.tabla.getSelectedRow();

        if (fila >= 0) {
            try {
                String id = v.tabla.getValueAt(fila, 0).toString();
                PreparedStatement pst = conn.prepareStatement("DELETE FROM usuarios WHERE ID = '"
                        + id + "' ");
                pst.executeUpdate();
                mostrarTabla("");
                JOptionPane.showMessageDialog(null, "Dato Eliminado");

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Dato no eliminado\n" + e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        }
    }

    public void actualizar() {
        String id = v.txtID.getText();

        if (v.txtNombre.getText().equals("") || v.txtApellido.getText().equals("") || v.txtEdad.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Faltan campos por rellenar");
        } else {
            try {
                PreparedStatement pst = conn.prepareStatement("UPDATE usuarios SET "
                        + "Nombre = '" + v.txtNombre.getText() + "',"
                        + "Apellido = '" + v.txtApellido.getText() + "',"
                        + "Edad = '" + v.txtEdad.getText() + "',"
                        + "Sexo = '" + v.comboSexo.getSelectedItem() + "',"
                        + "Fecha = '" + v.txtFecha.getText() + "' "
                        + "WHERE ID = '" + id + "'");

                pst.executeUpdate();
                mostrarTabla("");
                JOptionPane.showMessageDialog(null, "Datos Aactualizados");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public void consultar() {
        Desbloquear();
        v.btnEnviar.setEnabled(false);
        v.btnActualizar.setEnabled(true);
        int fila = v.tabla.getSelectedRow();

        if (fila >= 0) {
            v.txtID.setText(v.tabla.getValueAt(fila, 0).toString());
            v.txtNombre.setText(v.tabla.getValueAt(fila, 1).toString());
            v.txtApellido.setText(v.tabla.getValueAt(fila, 2).toString());
            v.txtEdad.setText(v.tabla.getValueAt(fila, 3).toString());
            v.comboSexo.setSelectedItem(v.tabla.getValueAt(fila, 4).toString());
            v.txtFecha.setText(v.tabla.getValueAt(fila, 5).toString());
            v.jFecha.setDateFormatString(v.tabla.getValueAt(fila, 5).toString());
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        }
    }

    void mostrarTabla(String valor) {

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Edad");
        modelo.addColumn("Sexo");
        modelo.addColumn("Fecha");
        v.tabla.setModel(modelo);

        v.comboSexo.addItem("--Selecciona--");
        v.comboSexo.addItem("Hombre");
        v.comboSexo.addItem("Mujer");

        String sql = "";
        if (valor.equals("")) {

            sql = "SELECT * FROM usuarios";
        } else {
            sql = "SELECT * FROM usuarios WHERE ID= '" + valor + "'";
        }

        String datos[] = new String[6];

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                modelo.addRow(datos);
            }

            v.tabla.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo mostrar los datos\n" + e);
        }
    }

    public void enviarDatos() {

        String dia = Integer.toString(v.jFecha.getCalendar().get(Calendar.DAY_OF_MONTH));
        String mes = Integer.toString(v.jFecha.getCalendar().get(Calendar.MONTH) + 1);
        String año = Integer.toString(v.jFecha.getCalendar().get(Calendar.YEAR));
        String fecha = (dia + " / " + mes + " / " + año);

        if (v.txtNombre.getText().equals("") || v.txtApellido.getText().equals("") || v.txtEdad.getText().equals("")) {

            JOptionPane.showMessageDialog(null, "Faltan campos por rellenar");
        } else {
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
                    pst.setString(4, comboSexo.getSelectedItem().toString());
                    pst.setString(5, fecha);
                    pst.executeUpdate();

                    mostrarTabla("");

                    JOptionPane.showMessageDialog(null, "Datos Guardados");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Datos no Guardados\n" + e);
            }
        }
    }
}
