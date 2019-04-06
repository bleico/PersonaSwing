package modelo;

import javax.swing.table.DefaultTableModel;
import controlador.Controlador;
import java.util.Calendar;
import javax.swing.JOptionPane;
import vista.Vista;
import static vista.Vista.comboSexo;

public class Modelo {

    DefaultTableModel modelo = new DefaultTableModel();

    Vista v;
    Controlador c;

    public void cargarMetodos() {
        mostrarTabla();
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

        String Datos[] = new String[5];
        String dia = Integer.toString(v.jFecha.getCalendar().get(Calendar.DAY_OF_MONTH));
        String mes = Integer.toString(v.jFecha.getCalendar().get(Calendar.MONTH) + 1);
        String año = Integer.toString(v.jFecha.getCalendar().get(Calendar.YEAR));
        String fecha = (dia + " / " + mes + " / " + año);

        //        botones.add(btnHombre);
        //        botones.add(btnMujer);
        Datos[0] = v.txtNombre.getText();

        Datos[1] = v.txtApellido.getText();

        Datos[2] = v.txtEdad.getText();

        //RadioBotones
        //        if (btnHombre.isSelected()) {
        //            Datos[3] = "Hombre";
        //        } else {
        //            if (btnMujer.isSelected()) {
        //                Datos[3] = "Mujer";
        //            } else {
        //                Datos[3] = "No se selecciono";
        //            }
        //        }
        //ComboBox
        if (v.comboSexo.getSelectedItem().toString().equals("--Selecciona--")) {
            JOptionPane.showMessageDialog(null, "Debes elegir un sexo");
        } else {
            v.txtEdad.setText("");
            v.txtNombre.setText("");
            v.txtApellido.setText("");
            Datos[3] = comboSexo.getSelectedItem().toString();
            Datos[4] = fecha;
            modelo.addRow(Datos);

        }
    }
}
