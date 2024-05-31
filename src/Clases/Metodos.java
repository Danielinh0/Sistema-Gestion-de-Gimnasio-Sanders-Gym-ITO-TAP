/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import Listos.Admin;
import Listos.Empleado;
import Listos.login;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Daniel Eduardo y su intimo amigo Luis
 */

public class Metodos {

    // Método para generar una contraseña aleatoria de 10 caracteres
    public static String generarContraseñaAleatoria(int longitud) {
        SecureRandom random = new SecureRandom();
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(longitud);
        for (int i = 0; i < longitud; i++) {
            sb.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }
        return sb.toString();
    }
    public void validarUsuario(String correo, String contraseña, JFrame loginFrame) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = null;

            try {
                // Crear una instancia de la clase Conexion
                Conexion objetoConexion = new Conexion();
                con = objetoConexion.conectar();

                // Consulta SQL con JOIN para verificar si el usuario es un administrador o un asalariado
                String consulta = "SELECT p.Nombre, p.A_Paterno, p.A_Materno, p.Direccion, p.Telefono, p.Edad, e.correo_empleado, e.id_empleado,a.contra_asa, ad.contra_admin "
                        + "FROM Persona p "
                        + "JOIN Empleado e ON p.id_persona = e.id_empleado "
                        + "LEFT JOIN Asalariado a ON e.id_empleado = a.id_asalariado "
                        + "LEFT JOIN Admin ad ON e.id_empleado = ad.id_admin "
                        + "WHERE e.correo_empleado = ? and e.fecha_despido is null";

                // Obtener la conexión y preparar la consulta
                ps = con.prepareStatement(consulta);
                ps.setString(1, correo);
                rs = ps.executeQuery();

                // Verificar los resultados
                if (rs.next()) {
                    String contraAsalariado = rs.getString("contra_asa");
                    String contraAdmin = rs.getString("contra_admin");

                    int idEmpleado = rs.getInt("id_empleado");

                    String nombre = rs.getString("Nombre");
                    String aPaterno = rs.getString("A_Paterno");
                    String aMaterno = rs.getString("A_Materno");
                    String direccion = rs.getString("Direccion");
                    String telefono = rs.getString("Telefono");
                    int edad = rs.getInt("Edad");
                    String correoEmpleado = rs.getString("correo_empleado");

                    String consultaSucursal = "Select nombre from gimnasio where empleado_g = ?";
                    ps = con.prepareStatement(consultaSucursal);
                    ps.setInt(1, idEmpleado);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        String nombreSucursal = rs.getString("nombre");

                        if (contraAdmin != null && contraAdmin.equals(contraseña)) {
                            // Credenciales de admin válidas
                            Admin a1 = new Admin();
                            a1.setVisible(true);
                            a1.setUserDataA(nombre, aPaterno, aMaterno, direccion, telefono, edad, correoEmpleado);
                            a1.setSucursal(nombreSucursal);
                            loginFrame.dispose();  // Cerrar ventana de login desde el metodo

                        } else if (contraAsalariado != null && contraAsalariado.equals(contraseña)) {
                            // Credenciales de asalariado válidas
                            Empleado e1 = new Empleado();
                            e1.setVisible(true);
                            e1.setUserDataE(nombre, aPaterno, aMaterno, direccion, telefono, edad, correoEmpleado);
                            e1.setSucursal(nombreSucursal);
                            loginFrame.dispose();  // Cerrar ventana de login desde el metodo
                        } else {
                            // Contraseña incorrecta
                            JOptionPane.showMessageDialog(null, "Correo o contraseña incorrectos");
                        }
                    } else {
                        // Correo no encontrado
                        JOptionPane.showMessageDialog(null, "Correo o contraseña incorrectos");
                    }

                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.toString());
                System.out.println("Error: " + e);
            } finally {
                // Asegurarse de cerrar los recursos en el bloque finally
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                    if (con != null) {
                        con.close();
                    }
                } catch (Exception e) {
                    System.out.println("Error al cerrar los recursos: " + e);
                }
            }
        }
    public void GuardarDatosPersona(String Nombre, String A_Paterno, String A_Materno, String CURP, String Direccion, String Telefono, int Edad) {
        try {
            PreparedStatement ps = null;

            // Crear una instancia de la clase Conexion
            Clases.Conexion objetoConexion = new Clases.Conexion();

            // Consulta SQL para insertar una nueva Persona en la tabla Persona
            String consulta = "INSERT INTO Persona (Nombre, A_Paterno, A_Materno, CURP, Direccion, Telefono, Edad) VALUES (?, ?, ?, ?, ?, ?, ?)";

            // Obtener la conexión y preparar la consulta
            ps = objetoConexion.conectar().prepareStatement(consulta);

            // Establecer los valores de los parámetros en la consulta
            ps.setString(1, Nombre);
            ps.setString(2, A_Paterno);
            ps.setString(3, A_Materno);
            ps.setString(4, CURP);
            ps.setString(5, Direccion);
            ps.setString(6, Telefono);
            ps.setInt(7, Edad);

            // Ejecutar la consulta de inserción
            int filasAfectadas = ps.executeUpdate();

            // Comprobar si se insertó el usuario correctamente
            if (filasAfectadas <= 0) {
                JOptionPane.showMessageDialog(null, "Error al registrar");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
            System.out.println("Error" + e);
        }
    }
    public void GuardarCorreoYFechaCliente(int id_cliente, String correo_cliente, Date fecha_inscripcion) {
        try {
            PreparedStatement ps = null;

            // Crear una instancia de la clase Conexion
            Clases.Conexion objetoConexion = new Clases.Conexion();

            // Consulta SQL para insertar un nuevo cliente en la tabla Cliente
            String consulta = "INSERT INTO Cliente (id_cliente, correo_cliente, fecha_inscripcion) VALUES (?, ?, ?)";

            // Obtener la conexión y preparar la consulta
            ps = objetoConexion.conectar().prepareStatement(consulta);

            // Establecer los valores de los parámetros en la consulta
            ps.setInt(1, id_cliente);
            ps.setString(2, correo_cliente);
            ps.setDate(3, new java.sql.Date(fecha_inscripcion.getTime()));

            // Ejecutar la consulta de inserción
            int filasAfectadas = ps.executeUpdate();

            // Comprobar si se insertó el usuario correctamente
            if (filasAfectadas <= 0) {

                JOptionPane.showMessageDialog(null, "Error al registrar correo y fecha de inscripción");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }

    }
    public void GuardarDatosEmpleado(int id_empleado, String correo_empleado, int Salario, String Horario, Date FechaContratacion) {
        try {
            PreparedStatement ps = null;

            // Crear una instancia de la clase Conexion
            Clases.Conexion objetoConexion = new Clases.Conexion();

            // Consulta SQL para insertar un nuevo cliente en la tabla Cliente
            String consulta = "INSERT INTO Empleado (id_empleado, correo_empleado, Salario, Horario, FechaContratacion) VALUES (?, ?, ?, ?, ?)";

            // Obtener la conexión y preparar la consulta
            ps = objetoConexion.conectar().prepareStatement(consulta);

            // Establecer los valores de los parámetros en la consulta
            ps.setInt(1, id_empleado);
            ps.setString(2, correo_empleado);
            ps.setInt(3, Salario);
            ps.setString(4, Horario);
            ps.setDate(5, new java.sql.Date(FechaContratacion.getTime()));

            // Ejecutar la consulta de inserción
            int filasAfectadas = ps.executeUpdate();

            // Comprobar si se insertó el usuario correctamente
            if (filasAfectadas <= 0) {

                JOptionPane.showMessageDialog(null, "Error al registrar");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
    }
    public void GuardarRolEmpleado(int id_asalariado, String contra_asa, String Rol) {
        try {
            PreparedStatement ps = null;

            // Crear una instancia de la clase Conexion
            Clases.Conexion objetoConexion = new Clases.Conexion();

            // Consulta SQL para insertar un nuevo cliente en la tabla Cliente
            String consulta = "INSERT INTO Asalariado (id_asalariado, contra_asa, rol) VALUES (?, ?, ?)";

            // Obtener la conexión y preparar la consulta
            ps = objetoConexion.conectar().prepareStatement(consulta);

            // Establecer los valores de los parámetros en la consulta
            ps.setInt(1, id_asalariado);
            ps.setString(2, contra_asa);
            ps.setString(3, Rol);

            // Ejecutar la consulta de inserción
            int filasAfectadas = ps.executeUpdate();

            // Comprobar si se insertó el usuario correctamente
            if (filasAfectadas <= 0) {

                JOptionPane.showMessageDialog(null, "Error al registrar");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
    }
    public void GuardarContraAdmin(int id_admin, String contra_admin) {
        try {
            PreparedStatement ps = null;

            // Crear una instancia de la clase Conexion
            Clases.Conexion objetoConexion = new Clases.Conexion();

            // Consulta SQL para insertar un nuevo cliente en la tabla Cliente
            String consulta = "INSERT INTO Admin (id_admin, contra_admin) VALUES (?, ?)";

            // Obtener la conexión y preparar la consulta
            ps = objetoConexion.conectar().prepareStatement(consulta);

            // Establecer los valores de los parámetros en la consulta
            ps.setInt(1, id_admin);
            ps.setString(2, contra_admin);

            // Ejecutar la consulta de inserción
            int filasAfectadas = ps.executeUpdate();

            // Comprobar si se insertó el usuario correctamente
            if (filasAfectadas <= 0) {

                JOptionPane.showMessageDialog(null, "Error al registrar");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
    }
    public int obtenerUltimoIdPersona() {
        int id_persona = -1;
        try {
            PreparedStatement ps = null;
            ResultSet rs = null;

            // Crear una instancia de la clase Conexion
            Clases.Conexion objetoConexion = new Clases.Conexion();

            // Consulta SQL para obtener el último id_persona insertado
            String consulta = "SELECT MAX(id_persona) AS id_persona FROM Persona";

            // Obtener la conexión y preparar la consulta
            ps = objetoConexion.conectar().prepareStatement(consulta);
            rs = ps.executeQuery();

            // Obtener el resultado
            if (rs.next()) {
                id_persona = rs.getInt("id_persona");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
            System.out.println("Error: " + e);
        }
        return id_persona;
    }
    public void guardarMembresia(String Nombre, String Precio, String Descripcion) {
        String estado = "Activa";

        try {
            PreparedStatement ps = null;

            // Crear una instancia de la clase Conexion
            Clases.Conexion objetoConexion = new Clases.Conexion();

            // Consulta SQL para insertar una nueva Persona en la tabla Persona
            String consulta = "INSERT INTO membresia (nombre, precio, descripcion, estado) VALUES (?, ?, ?, ?)";

            // Obtener la conexión y preparar la consulta
            ps = objetoConexion.conectar().prepareStatement(consulta);

            // Establecer los valores de los parámetros en la consulta
            ps.setString(1, Nombre);
            ps.setString(2, Precio);
            ps.setString(3, Descripcion);
            ps.setString(4, estado);

            // Ejecutar la consulta de inserción
            int filasAfectadas = ps.executeUpdate();

            // Comprobar si se insertó el usuario correctamente
            if (filasAfectadas <= 0) {
                JOptionPane.showMessageDialog(null, "Error al registrar");
            }

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
            System.out.println("Error" + e);
        }

    }
    public void guardarSucursal(String nombre, String direccion, Date fecha) {

        try {
            PreparedStatement ps = null;

            // Crear una instancia de la clase Conexion
            Clases.Conexion objetoConexion = new Clases.Conexion();

            // Consulta SQL para insertar un nuevo cliente en la tabla Cliente
            String consulta = "INSERT INTO gimnasio (nombre, direccion, fecha_apertura) VALUES (?, ?, ?)";

            // Obtener la conexión y preparar la consulta
            ps = objetoConexion.conectar().prepareStatement(consulta);

            // Establecer los valores de los parámetros en la consulta
            ps.setString(1, nombre);
            ps.setString(2, direccion);
            ps.setDate(3, new java.sql.Date(fecha.getTime()));

            // Ejecutar la consulta de inserción
            int filasAfectadas = ps.executeUpdate();

            // Comprobar si se insertó el usuario correctamente
            if (filasAfectadas <= 0) {

                JOptionPane.showMessageDialog(null, "Error al registrar correo y fecha de inscripción");
            }

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }

    }
    public void setMembresia(String n, int id) {
        Conexion con = new Conexion();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String consulta = "select distinct nombre, precio, descripcion, estado from membresia where nombre = ?";

        try {
            cn = con.conectar();
            ps = cn.prepareStatement(consulta);
            ps.setString(1, n);
            rs = ps.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString(1);
                String precio = rs.getString(2);
                String descripcion = rs.getString(3);
                String estado = rs.getString(4);

                String insertConsulta = "INSERT INTO membresia (nombre, precio, descripcion, Estado, cliente_m) VALUES (?, ?, ?, ?, ?)";
                ps = cn.prepareStatement(insertConsulta);

                ps.setString(1, nombre);
                ps.setString(2, precio);
                ps.setString(3, descripcion);
                ps.setString(4, estado);
                ps.setInt(5, id);

                int filasAfectadas = ps.executeUpdate();
                // Aquí podrías agregar algún tipo de comprobación o manejo adicional si lo necesitas.
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró una membresía con el nombre especificado.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime la traza de la excepción en la consola para propósitos de depuración.
            JOptionPane.showMessageDialog(null, "Error al insertar la membresía: " + e.getMessage());
        } finally {
            // Siempre cierra los recursos en un bloque finally.
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void setGimnasio(String n, int id, Date fecha) {
        Conexion con = new Conexion();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String consulta = "select distinct nombre, direccion, fecha_apertura from gimnasio where nombre = ?";

        try {
            cn = con.conectar();
            ps = cn.prepareStatement(consulta);
            ps.setString(1, n);
            rs = ps.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString(1);
                String direccion = rs.getString(2);
                Date hoy = rs.getDate(3);

                String insertConsulta = "Insert into gimnasio (nombre,direccion,fecha_apertura ,membresia_g) values (?, ?, ?, ?)";
                ps = cn.prepareStatement(insertConsulta);

                ps.setString(1, nombre);
                ps.setString(2, direccion);
                ps.setDate(3, (java.sql.Date) hoy);
                ps.setInt(4, id);

                int filasAfectadas = ps.executeUpdate();
                // Aquí podrías agregar algún tipo de comprobación o manejo adicional si lo necesitas.
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró una membresía con el nombre especificado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al insertar la membresía: " + e.getMessage());
        } finally {
            // Siempre cierra los recursos en un bloque finally.
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public int obtenerUltimoIdMembresia() {
        int id_membresia = -1;
        try {
            PreparedStatement ps = null;
            ResultSet rs = null;

            // Crear una instancia de la clase Conexion
            Clases.Conexion objetoConexion = new Clases.Conexion();

            // Consulta SQL para obtener el último id_persona insertado
            String consulta = "SELECT MAX(id_membresia) AS id_membresia FROM membresia";

            // Obtener la conexión y preparar la consulta
            ps = objetoConexion.conectar().prepareStatement(consulta);
            rs = ps.executeQuery();

            // Obtener el resultado
            if (rs.next()) {
                id_membresia = rs.getInt("id_membresia");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
            System.out.println("Error: " + e);
        }
        return id_membresia;
    }
    public void ActualizarCliente(int id_cliente, String Nombre, String A_Paterno, String A_Materno, String CURP, String Direccion, String Telefono, int Edad, String correo) {
        try {
            PreparedStatement ps = null;
            Connection con = new Conexion().conectar();

            String consulta = "UPDATE Persona SET Nombre = ?, A_Paterno = ?, A_Materno = ?, CURP = ?, Direccion = ?, Telefono = ?, Edad = ? WHERE id_persona = ?";
            ps = con.prepareStatement(consulta);
            ps.setString(1, Nombre);
            ps.setString(2, A_Paterno);
            ps.setString(3, A_Materno);
            ps.setString(4, CURP);
            ps.setString(5, Direccion);
            ps.setString(6, Telefono);
            ps.setInt(7, Edad);
            ps.setInt(8, id_cliente);
            ps.executeUpdate();

            consulta = "UPDATE Cliente SET correo_cliente = ? WHERE id_cliente = ?";
            ps = con.prepareStatement(consulta);
            ps.setString(1, correo);
            ps.setInt(2, id_cliente);
            ps.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar los datos: " + e.getMessage());
            e.printStackTrace();
        }

    }
    public void ActualizarMembresia(String NombreNuevoMembresia, int id_cliente, String NombreNuevoSucursal) {

        String consultaMembresia = "select distinct nombre, precio, descripcion from membresia where nombre = ?";
        String updateMembresia = "update membresia set nombre = ?, precio = ?, descripcion = ? where cliente_m = ?";
        ///////////////////////////MEMBRESIA

        String consultaIdMembresia = "select id_membresia from membresia where cliente_m = ?";

        String consultaGimnasio = "select distinct nombre, direccion, fecha_apertura from gimnasio where nombre = ?";
        String updateGimnasio = "update gimnasio set nombre = ?, direccion = ?, fecha_apertura = ? where membresia_g = ?";

        try (Connection cn = new Conexion().conectar(); PreparedStatement psMembresia = cn.prepareStatement(consultaMembresia); PreparedStatement psUpdateMembresia = cn.prepareStatement(updateMembresia); //////////////////////////MEMBRESIA UPDATE
                 PreparedStatement psIdMembresia = cn.prepareStatement(consultaIdMembresia); PreparedStatement psGimnasio = cn.prepareStatement(consultaGimnasio); ////////////////////////////Gimnasio Update
                 PreparedStatement psUpdateGimnasio = cn.prepareStatement(updateGimnasio)) {

            psMembresia.setString(1, NombreNuevoMembresia);
            try (ResultSet rsMembresia = psMembresia.executeQuery()) {
                if (rsMembresia.next()) {
                    String nombre_membresia = rsMembresia.getString(1);
                    String precio = rsMembresia.getString(2);
                    String descripcion = rsMembresia.getString(3);

                    // Actualizar la membresía del cliente
                    psUpdateMembresia.setString(1, nombre_membresia);
                    psUpdateMembresia.setString(2, precio);
                    psUpdateMembresia.setString(3, descripcion);
                    psUpdateMembresia.setInt(4, id_cliente);
                    psUpdateMembresia.executeUpdate();
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró una membresía con el nombre especificado.");
                    return;
                }
            }

            // Obtener id_membresia del cliente
            psIdMembresia.setInt(1, id_cliente);
            try (ResultSet rsIdMembresia = psIdMembresia.executeQuery()) {
                if (rsIdMembresia.next()) {
                    int id_membresia = rsIdMembresia.getInt(1);

                    // Obtener detalles de la nueva sucursal
                    psGimnasio.setString(1, NombreNuevoSucursal);
                    try (ResultSet rsGimnasio = psGimnasio.executeQuery()) {
                        if (rsGimnasio.next()) {
                            String nombre_g = rsGimnasio.getString(1);
                            String direccion_g = rsGimnasio.getString(2);
                            java.sql.Date fecha = rsGimnasio.getDate(3);

                            // Actualizar la sucursal del gimnasio
                            psUpdateGimnasio.setString(1, nombre_g);
                            psUpdateGimnasio.setString(2, direccion_g);
                            psUpdateGimnasio.setDate(3, fecha);
                            psUpdateGimnasio.setInt(4, id_membresia);
                            psUpdateGimnasio.executeUpdate();
                        } else {
                            JOptionPane.showMessageDialog(null, "No se encontró una sucursal con el nombre especificado.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró membresía para el cliente especificado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar la membresía: " + e.getMessage());
        }
    }
    public void ActualizarFecha(int id_persona) {
        Conexion con = new Conexion();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        Date hoy = new Date();
        java.sql.Date fechaVacia = null;

        String consulta = "select fecha_inscripcion, fecha_baja from cliente where id_cliente = ?";

        try {
            cn = con.conectar();
            ps = cn.prepareStatement(consulta);
            ps.setInt(1, id_persona);
            rs = ps.executeQuery();

            if (rs.next()) {
                java.sql.Date fecha = rs.getDate(1);
                java.sql.Date fechaB = rs.getDate(2);

                if (fechaB != null) {
                    String consulta2 = "update cliente set fecha_inscripcion = ?, fecha_baja = ? where id_cliente = ?";
                    ps.close(); // Cerrar el PreparedStatement antes de reutilizarlo
                    ps = cn.prepareStatement(consulta2);
                    ps.setDate(1, new java.sql.Date(hoy.getTime()));
                    ps.setDate(2, fechaVacia);
                    ps.setInt(3, id_persona);
                    ps.executeUpdate();
                } else {
                    String consulta3 = "update cliente set fecha_baja = ? where id_cliente = ?";
                    ps.close(); // Cerrar el PreparedStatement antes de reutilizarlo
                    ps = cn.prepareStatement(consulta3);
                    ps.setDate(1, new java.sql.Date(hoy.getTime()));
                    ps.setInt(2, id_persona);
                    ps.executeUpdate(); // Asegurarse de ejecutar la actualización
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Imprime la traza de la excepción en la consola para propósitos de depuración
            JOptionPane.showMessageDialog(null, "Error al actualizar la fecha: " + e.getMessage());
        } finally {
            // Siempre cierra los recursos en un bloque finally
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    public void crearPDF(int id_persona) throws FileNotFoundException, MalformedURLException, IOException {
        Conexion con = new Conexion();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String consulta = "SELECT p.nombre, p.A_Materno, p.A_Paterno, c.correo_cliente, m.nombre AS nombre_membresia, m.precio as precio_membresia ,g.nombre AS nombre_gimnasio "
                + "FROM persona p JOIN cliente c "
                + "ON p.id_persona = c.id_cliente JOIN membresia m "
                + "ON c.id_cliente = m.cliente_m JOIN gimnasio g ON m.id_membresia = g.membresia_g WHERE p.id_persona = ?";

        try {
            cn = con.conectar();
            ps = cn.prepareStatement(consulta);
            ps.setInt(1, id_persona);
            rs = ps.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString(1);
                String a_paterno = rs.getString(2);
                String a_materno = rs.getString(3);
                String correo = rs.getString(4);
                String tipoM = rs.getString(5);
                int precio = rs.getInt(6);
                String sucursal = rs.getString(7);

                String precio_m = String.valueOf(precio);

                // Fecha y hora actuales
                LocalDateTime fechaActual = LocalDateTime.now();
                LocalDateTime horaMenos = fechaActual.minusHours(1);

                DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:mm:ss");
                DateTimeFormatter fecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String fechaFinal = hora.format(horaMenos) + " con la fecha de " + fecha.format(fechaActual);

                // Contenido del PDF
                Paragraph titulo = new Paragraph("Asunto: Acuse de Registro");
                Paragraph texto1 = new Paragraph()
                        .add("Por medio del presente documento se hace constancia que el usuario ")
                        .add(nombre).add(" ").add(a_paterno).add(" ").add(a_materno + " con el correo ").add(correo)
                        .add(" se ha registrado correctamente a las ")
                        .add(fechaFinal)
                        .add(" con la ")
                        .add(tipoM)
                        .add(" con el precio de $")
                        .add(precio_m).add(" en la sucursal ")
                        .add(sucursal)
                        .setTextAlignment(TextAlignment.JUSTIFIED);

                Paragraph despedida = new Paragraph("¡Disfruta tu estancia con nosotros!");

                // Nombre del PDF
                String nombrePDF = "Registro de " + nombre + " " + a_paterno + " " + a_materno + ".pdf";

                // Ruta de la imagen (asegúrate de que sea correcta)
                Image logoE = new Image(ImageDataFactory.create(getClass().getResource("/Imagenes/logo.png")));

                logoE.scaleToFit(250, 250);

                // Seleccionar la ubicación para guardar el archivo
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar PDF");
                fileChooser.setSelectedFile(new File(nombrePDF));
                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    try (FileOutputStream fos = new FileOutputStream(fileToSave); PdfWriter writer = new PdfWriter(fos); PdfDocument pdf = new PdfDocument(writer); Document document = new Document(pdf, PageSize.A5)) {

                        float x = (pdf.getDefaultPageSize().getWidth() - logoE.getImageScaledWidth()) / 2;
                        float y = pdf.getDefaultPageSize().getHeight() - logoE.getImageScaledHeight() - 50;

                        logoE.setFixedPosition(x, y);

                        document.add(logoE);
                        document.add(new Paragraph("\n"));

                        Div espacioDiv = new Div().setHeight(280);
                        document.add(espacioDiv);

                        PdfFont f1 = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
                        PdfFont f2 = PdfFontFactory.createFont(StandardFonts.HELVETICA);

                        titulo.setFont(f1);
                        texto1.setFont(f2);

                        document.add(titulo);
                        document.add(texto1);

                        Div espacioDiv2 = new Div().setHeight(20);
                        document.add(espacioDiv2);

                        despedida.setFont(f1);
                        document.add(despedida);

                        document.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("Error al crear el archivo PDF: " + e.getMessage());
                        e.printStackTrace();
                    } catch (IOException e) {
                        System.out.println("Error al manejar el archivo PDF: " + e.getMessage());
                        e.printStackTrace();
                    }

                    preguntarAbrirPDF(fileToSave.getAbsolutePath());
                   
                    enviarCorreoConPDF(correo, fileToSave.getAbsolutePath() , nombre, sucursal, precio, tipoM);
                }

            }
        } catch (SQLException e) {
            System.out.println("Error de base de datos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
                e.printStackTrace();
            }
        }

    }
    public void crearPDF_EC(int id_persona) throws FileNotFoundException, MalformedURLException, IOException {
        Conexion con = new Conexion();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String consulta = "SELECT p.nombre, p.A_Materno, p.A_Paterno, c.correo_cliente, m.nombre AS nombre_membresia, m.precio as precio_membresia ,g.nombre AS nombre_gimnasio "
                + "FROM persona p JOIN cliente c "
                + "ON p.id_persona = c.id_cliente JOIN membresia m "
                + "ON c.id_cliente = m.cliente_m JOIN gimnasio g ON m.id_membresia = g.membresia_g WHERE p.id_persona = ?";

        try {
            cn = con.conectar();
            ps = cn.prepareStatement(consulta);
            ps.setInt(1, id_persona);
            rs = ps.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString(1);
                String a_paterno = rs.getString(2);
                String a_materno = rs.getString(3);
                String correo = rs.getString(4);
                String tipoM = rs.getString(5);
                int precio = rs.getInt(6);
                String sucursal = rs.getString(7);

                String precio_m = String.valueOf(precio);

                // Fecha y hora actuales
                LocalDateTime fechaActual = LocalDateTime.now();
                LocalDateTime horaMenos = fechaActual.minusHours(1);

                DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:mm:ss");
                DateTimeFormatter fecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String fechaFinal = hora.format(horaMenos) + " con la fecha de " + fecha.format(fechaActual);

                // Contenido del PDF
                Paragraph titulo = new Paragraph("Asunto: Acuse de Reingreso de Datos");
                Paragraph texto1 = new Paragraph()
                        .add("Por medio del presente documento se hace constancia que el usuario ")
                        .add(nombre).add(" ").add(a_paterno).add(" ").add(a_materno + " con el correo ").add(correo)
                        .add(" se ha registrado correctamente a las ")
                        .add(fechaFinal)
                        .add(" con la ")
                        .add(tipoM)
                        .add(" con el precio de $")
                        .add(precio_m).add(" en la sucursal ")
                        .add(sucursal)
                        .setTextAlignment(TextAlignment.JUSTIFIED);

                Paragraph despedida = new Paragraph("¡Disfruta tu estancia con nosotros!");

                // Nombre del PDF
                String nombrePDF = "Registro de " + nombre + " " + a_paterno + " " + a_materno + ".pdf";

                // Ruta de la imagen (asegúrate de que sea correcta)
                Image logoE = new Image(ImageDataFactory.create(getClass().getResource("/Imagenes/logo.png")));

                logoE.scaleToFit(250, 250);

                // Seleccionar la ubicación para guardar el archivo
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar PDF");
                fileChooser.setSelectedFile(new File(nombrePDF));
                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    try (FileOutputStream fos = new FileOutputStream(fileToSave); PdfWriter writer = new PdfWriter(fos); PdfDocument pdf = new PdfDocument(writer); Document document = new Document(pdf, PageSize.A5)) {

                        float x = (pdf.getDefaultPageSize().getWidth() - logoE.getImageScaledWidth()) / 2;
                        float y = pdf.getDefaultPageSize().getHeight() - logoE.getImageScaledHeight() - 50;

                        logoE.setFixedPosition(x, y);

                        document.add(logoE);
                        document.add(new Paragraph("\n"));

                        Div espacioDiv = new Div().setHeight(280);
                        document.add(espacioDiv);

                        PdfFont f1 = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
                        PdfFont f2 = PdfFontFactory.createFont(StandardFonts.HELVETICA);

                        titulo.setFont(f1);
                        texto1.setFont(f2);

                        document.add(titulo);
                        document.add(texto1);

                        Div espacioDiv2 = new Div().setHeight(20);
                        document.add(espacioDiv2);

                        despedida.setFont(f1);
                        document.add(despedida);

                        document.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("Error al crear el archivo PDF: " + e.getMessage());
                        e.printStackTrace();
                    } catch (IOException e) {
                        System.out.println("Error al manejar el archivo PDF: " + e.getMessage());
                        e.printStackTrace();
                    }

                    preguntarAbrirPDF(fileToSave.getAbsolutePath());
                    // Llamar al método para enviar el correo con el PDF adjunto
                    enviarCorreoConPDFE(correo, fileToSave.getAbsolutePath() , nombre, sucursal, precio, tipoM);
                }

            }
        } catch (SQLException e) {
            System.out.println("Error de base de datos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
                e.printStackTrace();
            }
        }

    }
    public void crearPDFEmpleado(int id_persona) throws FileNotFoundException, MalformedURLException, IOException {
        Conexion con = new Conexion();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String consulta = "SELECT p.id_persona, p.Nombre, p.A_Paterno, p.A_Materno, e.correo_empleado, a.contra_asa, e.horario, e.salario, a.rol, g.nombre AS nombre_gimnasio "
                + "FROM persona p "
                + "JOIN empleado e ON p.id_persona = e.id_empleado "
                + "JOIN asalariado a ON e.id_empleado = a.id_asalariado "
                + "JOIN gimnasio g ON e.id_empleado = g.empleado_g "
                + "WHERE p.id_persona = ?";

        try {
            cn = con.conectar();
            ps = cn.prepareStatement(consulta);
            ps.setInt(1, id_persona);
            rs = ps.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString(2);
                String a_paterno = rs.getString(3);
                String a_materno = rs.getString(4);
                String correo_e = rs.getString(5);
                String Contra = rs.getString(6);
                String Horario = rs.getString(7);
                int Salario = rs.getInt(8);
                String rol = rs.getString(9);
                String Sucursal = rs.getString(10);

                // Fecha y hora actuales
                LocalDateTime fechaActual = LocalDateTime.now();
                LocalDateTime horaMenos = fechaActual.minusHours(1);

                DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:mm:ss");
                DateTimeFormatter fecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String fechaFinal = hora.format(horaMenos) + " con la fecha de " + fecha.format(fechaActual);

                // Contenido del PDF
                Paragraph titulo = new Paragraph("Asunto: Acuse de Registro de Empleado");
                Paragraph texto1 = new Paragraph()
                        .add("Por medio del presente documento se hace constancia que el usuario ")
                        .add(nombre + " " + a_paterno + " " + a_materno + " con el correo correspondiente:" + correo_e)
                        .add(" se ha registrado correctamente a las ")
                        .add(fechaFinal)
                        .add(" con la contraseña de dicho correo: ")
                        .add(Contra)
                        .add(" con el Horario de ")
                        .add(Horario)
                        .add(" en la sucursal ")
                        .add(Sucursal)
                        .add(" con un salario de $")
                        .add(String.valueOf(Salario))
                        .add(" con el rol de ")
                        .add(rol)
                        .setTextAlignment(TextAlignment.JUSTIFIED);

                Paragraph despedida = new Paragraph("¡Esperamos que disfrute su estancia con nosotros!");

                // Nombre del PDF
                String nombrePDF = "Registro de " + nombre + " " + a_paterno + " " + a_materno + ".pdf";

                // Ruta de la imagen (asegúrate de que sea correcta)
                Image logoE = new Image(ImageDataFactory.create(getClass().getResource("/Imagenes/logo.png")));

                logoE.scaleToFit(250, 250);

                // Seleccionar la ubicación para guardar el archivo
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar PDF");
                fileChooser.setSelectedFile(new File(nombrePDF));
                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    try (FileOutputStream fos = new FileOutputStream(fileToSave); PdfWriter writer = new PdfWriter(fos); PdfDocument pdf = new PdfDocument(writer); Document document = new Document(pdf, PageSize.A5)) {

                        float x = (pdf.getDefaultPageSize().getWidth() - logoE.getImageScaledWidth()) / 2;
                        float y = pdf.getDefaultPageSize().getHeight() - logoE.getImageScaledHeight() - 50;

                        logoE.setFixedPosition(x, y);

                        document.add(logoE);
                        document.add(new Paragraph("\n"));

                        Div espacioDiv = new Div().setHeight(280);
                        document.add(espacioDiv);

                        PdfFont f1 = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
                        PdfFont f2 = PdfFontFactory.createFont(StandardFonts.HELVETICA);

                        titulo.setFont(f1);
                        texto1.setFont(f2);

                        document.add(titulo);
                        document.add(texto1);

                        Div espacioDiv2 = new Div().setHeight(20);
                        document.add(espacioDiv2);

                        despedida.setFont(f1);
                        document.add(despedida);

                        document.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("Error al crear el archivo PDF: " + e.getMessage());
                        e.printStackTrace();
                    } catch (IOException e) {
                        System.out.println("Error al manejar el archivo PDF: " + e.getMessage());
                        e.printStackTrace();
                    }

                    preguntarAbrirPDF(fileToSave.getAbsolutePath());
                    // Llamar al método para enviar el correo con el PDF adjunto
                    //enviarCorreoConPDF(correo_e, fileToSave.getAbsolutePath());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error de base de datos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    public void crearPDFEmpleado_ED(int id_persona) throws FileNotFoundException, MalformedURLException, IOException {
        Conexion con = new Conexion();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String consulta = "SELECT p.id_persona, p.Nombre, p.A_Paterno, p.A_Materno, e.correo_empleado, a.contra_asa, e.horario, e.salario, a.rol, g.nombre AS nombre_gimnasio "
                + "FROM persona p "
                + "JOIN empleado e ON p.id_persona = e.id_empleado "
                + "JOIN asalariado a ON e.id_empleado = a.id_asalariado "
                + "JOIN gimnasio g ON e.id_empleado = g.empleado_g "
                + "WHERE p.id_persona = ?";

        try {
            cn = con.conectar();
            ps = cn.prepareStatement(consulta);
            ps.setInt(1, id_persona);
            rs = ps.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString(2);
                String a_paterno = rs.getString(3);
                String a_materno = rs.getString(4);
                String correo_e = rs.getString(5);
                String Contra = rs.getString(6);
                String Horario = rs.getString(7);
                int Salario = rs.getInt(8);
                String rol = rs.getString(9);
                String Sucursal = rs.getString(10);

                // Fecha y hora actuales
                LocalDateTime fechaActual = LocalDateTime.now();
                LocalDateTime horaMenos = fechaActual.minusHours(1);

                DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:mm:ss");
                DateTimeFormatter fecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String fechaFinal = hora.format(horaMenos) + " con la fecha de " + fecha.format(fechaActual);

                // Contenido del PDF
                Paragraph titulo = new Paragraph("Asunto: Acuse de Edicion de Datos de Empleado");
                Paragraph texto1 = new Paragraph()
                        .add("Por medio del presente documento se hace constancia que el usuario ")
                        .add(nombre + " " + a_paterno + " " + a_materno + " con el correo correspondiente:" + correo_e)
                        .add(" se ha registrado correctamente a las ")
                        .add(fechaFinal)
                        .add(" con la contraseña de dicho correo: ")
                        .add(Contra)
                        .add(" con el Horario de ")
                        .add(Horario)
                        .add(" en la sucursal ")
                        .add(Sucursal)
                        .add(" con un salario de $")
                        .add(String.valueOf(Salario))
                        .add(" con el rol de ")
                        .add(rol)
                        .setTextAlignment(TextAlignment.JUSTIFIED);

                Paragraph despedida = new Paragraph("¡Esperamos que disfrute su estancia con nosotros!");

                // Nombre del PDF
                String nombrePDF = "Registro de " + nombre + " " + a_paterno + " " + a_materno + ".pdf";

                // Ruta de la imagen (asegúrate de que sea correcta)
                Image logoE = new Image(ImageDataFactory.create(getClass().getResource("/Imagenes/logo.png")));

                logoE.scaleToFit(250, 250);

                // Seleccionar la ubicación para guardar el archivo
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar PDF");
                fileChooser.setSelectedFile(new File(nombrePDF));
                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    try (FileOutputStream fos = new FileOutputStream(fileToSave); PdfWriter writer = new PdfWriter(fos); PdfDocument pdf = new PdfDocument(writer); Document document = new Document(pdf, PageSize.A5)) {

                        float x = (pdf.getDefaultPageSize().getWidth() - logoE.getImageScaledWidth()) / 2;
                        float y = pdf.getDefaultPageSize().getHeight() - logoE.getImageScaledHeight() - 50;

                        logoE.setFixedPosition(x, y);

                        document.add(logoE);
                        document.add(new Paragraph("\n"));

                        Div espacioDiv = new Div().setHeight(280);
                        document.add(espacioDiv);

                        PdfFont f1 = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
                        PdfFont f2 = PdfFontFactory.createFont(StandardFonts.HELVETICA);

                        titulo.setFont(f1);
                        texto1.setFont(f2);

                        document.add(titulo);
                        document.add(texto1);

                        Div espacioDiv2 = new Div().setHeight(20);
                        document.add(espacioDiv2);

                        despedida.setFont(f1);
                        document.add(despedida);

                        document.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("Error al crear el archivo PDF: " + e.getMessage());
                        e.printStackTrace();
                    } catch (IOException e) {
                        System.out.println("Error al manejar el archivo PDF: " + e.getMessage());
                        e.printStackTrace();
                    }

                    preguntarAbrirPDF(fileToSave.getAbsolutePath());
                    // Llamar al método para enviar el correo con el PDF adjunto
                    //enviarCorreoConPDF(correo_e, fileToSave.getAbsolutePath());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error de base de datos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    public void crearPDFAdministrador(int id_persona) throws FileNotFoundException, MalformedURLException, IOException {
        Conexion con = new Conexion();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String consulta = "SELECT p.id_persona, p.Nombre, p.A_Paterno, p.A_Materno, e.correo_empleado, ad.contra_admin, e.horario, e.salario, g.nombre AS nombre_gimnasio "
                + "FROM persona p "
                + "JOIN empleado e ON p.id_persona = e.id_empleado "
                + "JOIN admin ad ON e.id_empleado = ad.id_admin "
                + "JOIN gimnasio g ON e.id_empleado = g.empleado_g "
                + "WHERE p.id_persona = ?";

        try {
            cn = con.conectar();
            ps = cn.prepareStatement(consulta);
            ps.setInt(1, id_persona);
            rs = ps.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString(2);
                String a_paterno = rs.getString(3);
                String a_materno = rs.getString(4);
                String correo_a = rs.getString(5);
                String ContraA = rs.getString(6);
                String Horario = rs.getString(7);
                int Salario = rs.getInt(8);
                String Sucursal = rs.getString(9);

                // Fecha y hora actuales
                LocalDateTime fechaActual = LocalDateTime.now();
                LocalDateTime horaMenos = fechaActual.minusHours(1);

                DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:mm:ss");
                DateTimeFormatter fecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String fechaFinal = hora.format(horaMenos) + " con la fecha de " + fecha.format(fechaActual);

                // Contenido del PDF
                Paragraph titulo = new Paragraph("Asunto: Acuse de Registro de Administrador");
                Paragraph texto1 = new Paragraph()
                        .add("Por medio del presente documento se hace constancia que el Usuario ")
                        .add(nombre + " " + a_paterno + " " + a_materno + " con el correo creado de: " + correo_a)
                        .add(" se ha registrado correctamente a las ")
                        .add(fechaFinal)
                        .add(" con la contraseña de dicho correo: ")
                        .add(ContraA)
                        .add(" con un Horario ")
                        .add(Horario)
                        .add(" con un salario de $")
                        .add(String.valueOf(Salario))
                        .add(" en la sucursal ")
                        .add(Sucursal)
                        .setTextAlignment(TextAlignment.JUSTIFIED);

                Paragraph despedida = new Paragraph("¡Esperamos que disfrute su estancia con nosotros!");

                // Nombre del PDF
                String nombrePDF = "Registro de " + nombre + " " + a_paterno + " " + a_materno + ".pdf";

                // Ruta de la imagen (asegúrate de que sea correcta)
                Image logoE = new Image(ImageDataFactory.create(getClass().getResource("/Imagenes/logo.png")));

                logoE.scaleToFit(250, 250);

                // Seleccionar la ubicación para guardar el archivo
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar PDF");
                fileChooser.setSelectedFile(new File(nombrePDF));
                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    try (FileOutputStream fos = new FileOutputStream(fileToSave); PdfWriter writer = new PdfWriter(fos); PdfDocument pdf = new PdfDocument(writer); Document document = new Document(pdf, PageSize.A5)) {

                        float x = (pdf.getDefaultPageSize().getWidth() - logoE.getImageScaledWidth()) / 2;
                        float y = pdf.getDefaultPageSize().getHeight() - logoE.getImageScaledHeight() - 50;

                        logoE.setFixedPosition(x, y);

                        document.add(logoE);
                        document.add(new Paragraph("\n"));

                        Div espacioDiv = new Div().setHeight(280);
                        document.add(espacioDiv);

                        PdfFont f1 = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
                        PdfFont f2 = PdfFontFactory.createFont(StandardFonts.HELVETICA);

                        titulo.setFont(f1);
                        texto1.setFont(f2);

                        document.add(titulo);
                        document.add(texto1);

                        Div espacioDiv2 = new Div().setHeight(20);
                        document.add(espacioDiv2);

                        despedida.setFont(f1);
                        document.add(despedida);

                        document.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("Error al crear el archivo PDF: " + e.getMessage());
                        e.printStackTrace();
                    } catch (IOException e) {
                        System.out.println("Error al manejar el archivo PDF: " + e.getMessage());
                        e.printStackTrace();
                    }

                    preguntarAbrirPDF(fileToSave.getAbsolutePath());
                    // Llamar al método para enviar el correo con el PDF adjunto
                    //enviarCorreoConPDF(correo_e, fileToSave.getAbsolutePath());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error de base de datos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    public void crearPDFAdministrador_ED(int id_persona) throws FileNotFoundException, MalformedURLException, IOException {
        Conexion con = new Conexion();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String consulta = "SELECT p.id_persona, p.Nombre, p.A_Paterno, p.A_Materno, e.correo_empleado, ad.contra_admin, e.horario, e.salario, g.nombre AS nombre_gimnasio "
                + "FROM persona p "
                + "JOIN empleado e ON p.id_persona = e.id_empleado "
                + "JOIN admin ad ON e.id_empleado = ad.id_admin "
                + "JOIN gimnasio g ON e.id_empleado = g.empleado_g "
                + "WHERE p.id_persona = ?";

        try {
            cn = con.conectar();
            ps = cn.prepareStatement(consulta);
            ps.setInt(1, id_persona);
            rs = ps.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString(2);
                String a_paterno = rs.getString(3);
                String a_materno = rs.getString(4);
                String correo_a = rs.getString(5);
                String ContraA = rs.getString(6);
                String Horario = rs.getString(7);
                int Salario = rs.getInt(8);
                String Sucursal = rs.getString(9);

                // Fecha y hora actuales
                LocalDateTime fechaActual = LocalDateTime.now();
                LocalDateTime horaMenos = fechaActual.minusHours(1);

                DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:mm:ss");
                DateTimeFormatter fecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String fechaFinal = hora.format(horaMenos) + " con la fecha de " + fecha.format(fechaActual);

                // Contenido del PDF
                Paragraph titulo = new Paragraph("Asunto: Acuse de Edicion de Datos para Administrador");
                Paragraph texto1 = new Paragraph()
                        .add("Por medio del presente documento se hace constancia que el Usuario ")
                        .add(nombre + " " + a_paterno + " " + a_materno + " con el correo creado de: " + correo_a)
                        .add(" se ha registrado correctamente a las ")
                        .add(fechaFinal)
                        .add(" con la contraseña de dicho correo: ")
                        .add(ContraA)
                        .add(" con un Horario ")
                        .add(Horario)
                        .add(" con un salario de $")
                        .add(String.valueOf(Salario))
                        .add(" en la sucursal ")
                        .add(Sucursal)
                        .setTextAlignment(TextAlignment.JUSTIFIED);

                Paragraph despedida = new Paragraph("¡Esperamos que disfrute su estancia con nosotros!");

                // Nombre del PDF
                String nombrePDF = "Registro de " + nombre + " " + a_paterno + " " + a_materno + ".pdf";

                // Ruta de la imagen (asegúrate de que sea correcta)
                Image logoE = new Image(ImageDataFactory.create(getClass().getResource("/Imagenes/logo.png")));

                logoE.scaleToFit(250, 250);

                // Seleccionar la ubicación para guardar el archivo
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar PDF");
                fileChooser.setSelectedFile(new File(nombrePDF));
                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    try (FileOutputStream fos = new FileOutputStream(fileToSave); PdfWriter writer = new PdfWriter(fos); PdfDocument pdf = new PdfDocument(writer); Document document = new Document(pdf, PageSize.A5)) {

                        float x = (pdf.getDefaultPageSize().getWidth() - logoE.getImageScaledWidth()) / 2;
                        float y = pdf.getDefaultPageSize().getHeight() - logoE.getImageScaledHeight() - 50;

                        logoE.setFixedPosition(x, y);

                        document.add(logoE);
                        document.add(new Paragraph("\n"));

                        Div espacioDiv = new Div().setHeight(280);
                        document.add(espacioDiv);

                        PdfFont f1 = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
                        PdfFont f2 = PdfFontFactory.createFont(StandardFonts.HELVETICA);

                        titulo.setFont(f1);
                        texto1.setFont(f2);

                        document.add(titulo);
                        document.add(texto1);

                        Div espacioDiv2 = new Div().setHeight(20);
                        document.add(espacioDiv2);

                        despedida.setFont(f1);
                        document.add(despedida);

                        document.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("Error al crear el archivo PDF: " + e.getMessage());
                        e.printStackTrace();
                    } catch (IOException e) {
                        System.out.println("Error al manejar el archivo PDF: " + e.getMessage());
                        e.printStackTrace();
                    }

                    preguntarAbrirPDF(fileToSave.getAbsolutePath());
                    // Llamar al método para enviar el correo con el PDF adjunto
                    //enviarCorreoConPDF(correo_e, fileToSave.getAbsolutePath());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error de base de datos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    private void preguntarAbrirPDF(String filePath) {
        int res = JOptionPane.showConfirmDialog(null,
                "Se creó el registro en PDF en la ubicación especificada.\n\n¿Desea ver el archivo?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION);

        if (res == JOptionPane.YES_OPTION) {
            File archivo = new File(filePath);
            if (archivo.exists()) {
                try {
                    Desktop.getDesktop().open(archivo);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null,
                            "Error al tratar de abrir el archivo 'Registro.pdf'");
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "El archivo 'Registro.pdf' no existe en la ubicación especificada.");
            }
        }
    }
    
    private Connection obtenerConexion() {
        Conexion conexion = new Conexion();
        return conexion.conectar();
    }

    public JFreeChart generarGraficoPastel() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        int totalClientes = 0;

        try {
            // Utilizamos la conexión existente desde la clase Conexion
            try (Connection con = obtenerConexion(); Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

                // Primera consulta para calcular el total de clientes
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM Cliente WHERE fecha_baja IS NULL");
                if (rs.next()) {
                    totalClientes = rs.getInt("total");
                }
                rs.close();

                // Comprobar si hay clientes
                if (totalClientes == 0) {
                    JOptionPane.showMessageDialog(null, "No hay clientes sin fecha de baja.");
                    return null;
                }

                // Segunda consulta para obtener los datos por edad
                String query = "SELECT p.Edad, COUNT(*) AS cantidad "
                        + "FROM Persona p "
                        + "JOIN Cliente c ON p.id_persona = c.id_cliente "
                        + "WHERE c.fecha_baja IS NULL "
                        + "GROUP BY p.Edad "
                        + "ORDER BY p.Edad";
                rs = stmt.executeQuery(query);

                // Agregar datos al dataset
                while (rs.next()) {
                    int edad = rs.getInt("Edad");
                    int cantidad = rs.getInt("cantidad");
                    double porcentaje = (double) cantidad / totalClientes * 100;
                    dataset.setValue("Edad " + edad, porcentaje);
                }

                rs.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        // Verificar si el dataset está vacío
        if (dataset.getItemCount() == 0) {
            JOptionPane.showMessageDialog(null, "No hay datos para mostrar en el gráfico.");
            return null;
        }

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Clientes por Edad",
                dataset,
                false, true, false);

        // Configurar el gráfico de pastel para mostrar porcentajes
        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}",
                NumberFormat.getNumberInstance(),
                NumberFormat.getPercentInstance()));

        Color baseColor = new Color(122, 72, 221);

        // Generar un espectro de colores derivados del color base
        Color[] colors = new Color[10];
        for (int i = 0; i < colors.length; i++) {
            float[] hsbVals = Color.RGBtoHSB(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), null);
            float hue = hsbVals[0] + (float) i / (float) colors.length * 0.1f; // Ajuste del tono
            colors[i] = Color.getHSBColor(hue, hsbVals[1], hsbVals[2]);
        }

        // Asignar colores a cada sección del gráfico de pastel
        int colorIndex = 0;
        for (Object key : dataset.getKeys()) {
            plot.setSectionPaint((Comparable) key, colors[colorIndex % colors.length]);
            colorIndex++;
        }

        plot.setLabelBackgroundPaint(Color.WHITE);
        plot.setLabelPaint(new Color(54, 33, 89));
        plot.setBackgroundPaint(Color.WHITE);
        plot.setLabelFont(new Font("Segoe UI", Font.PLAIN, 12));

        // Cambiar la fuente del título del gráfico
        pieChart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 24));

        // Cambiar la fuente de la leyenda del gráfico
        if (pieChart.getLegend() != null) {
            pieChart.getLegend().setItemFont(new Font("Segoe UI", Font.PLAIN, 12));
        }

        pieChart.setBorderVisible(false);
        plot.setOutlineVisible(false); // Asegúrate de que el contorno de la trama esté oculto
        plot.setShadowPaint(null);

        return pieChart;
    }

    public JFreeChart generarGraficoBarras() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            Connection con = obtenerConexion();
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT nombre, COUNT(*) AS cantidad FROM Membresia WHERE cliente_m != 0 GROUP BY nombre");

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");
                dataset.addValue(cantidad, "Personas", nombre);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFreeChart barChart = ChartFactory.createBarChart(
                "Número de Personas con membresias",
                null, // Quitar el texto del eje X (Membresia)
                "Cantidad",
                dataset,
                PlotOrientation.VERTICAL,
                false, // Quitar la leyenda (Personas)
                true,
                false);

        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        Color customColor = new Color(122, 72, 221);
        renderer.setSeriesPaint(0, customColor);

        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);

        renderer.setBarPainter(new StandardBarPainter());
        renderer.setShadowVisible(false);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
        domainAxis.setMaximumCategoryLabelWidthRatio(0.8f);
        domainAxis.setCategoryMargin(0.3);

        return barChart;
    }
    public void enviarCorreoConPDF(String correoDestino, String archivoPDF, String nombre, String nombreS, int precio, String tipoM) {
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587"); // Cambia a 465 si usas SSL

    String usuario = "pruebademicomponente@gmail.com"; // Cambia esto por tu correo de Gmail
    String clave = "dssjlwbildsrtnak"; // Cambia esto por tu contraseña de Gmail

    javax.mail.Session session = javax.mail.Session.getInstance(props, new javax.mail.Authenticator() {
        protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
            return new javax.mail.PasswordAuthentication(usuario, clave);
        }
    });

    try {
        javax.mail.Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(usuario));
        message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(correoDestino));
        message.setSubject("Registro de Membresía");

        String htmlContent = String.format(
            "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
            + "<html dir=\"ltr\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" lang=\"es\">"
            + "<head>"
            + "<meta charset=\"UTF-8\">"
            + "<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">"
            + "<meta name=\"x-apple-disable-message-reformatting\">"
            + "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
            + "<meta content=\"telephone=no\" name=\"format-detection\">"
            + "<title>Registro de Membresía</title>"
            + "<style type=\"text/css\">"
            + "body {"
            + "    width: 100%%;"
            + "    font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif;"
            + "    -webkit-text-size-adjust: 100%%;"
            + "    -ms-text-size-adjust: 100%%;"
            + "    padding: 0;"
            + "    margin: 0;"
            + "}"
            + ".es-wrapper {"
            + "    width: 100%%;"
            + "    height: 100%%;"
            + "    background-color: #FAFAFA;"
            + "    display: flex;"
            + "    justify-content: center;"
            + "    align-items: center;"
            + "}"
            + ".es-content-body {"
            + "    background-color: #FFFFFF;"
            + "    width: 600px;"
            + "    margin: auto;"
            + "    padding: 20px;"
            + "    border: 1px solid #CCCCCC;"
            + "}"
            + ".es-content-body h1 {"
            + "    color: #333333;"
            + "    font-size: 44px;"
            + "    font-weight: bold;"
            + "}"
            + ".es-content-body p {"
            + "    color: #666666;"
            + "    font-size: 14px;"
            + "}"
            + "</style>"
            + "</head>"
            + "<body>"
            + "<div class=\"es-wrapper\">"
            + "    <table class=\"es-content-body\" cellpadding=\"0\" cellspacing=\"0\">"
            + "        <tr>"
            + "            <td align=\"center\">"
            + "                <h1>¡Gracias por tu inscripción!</h1>"
            + "                <p><strong>Estimado %s,</strong></p>"
            + "                <p>Te damos la bienvenida a nuestra familia de fitness. Nos complace informarte que has sido registrado exitosamente en nuestro sistema.</p>"
            + "                <p><strong>Detalles de tu membresía:</strong></p>"
            + "                <p>Tipo de membresía: %s<br>Precio: $%d</p>"
            + "                <p>Esperamos que disfrutes de todos los beneficios que ofrecemos y logres alcanzar tus objetivos de salud y bienestar.</p>"
            + "                <p>¡Gracias por elegirnos!</p>"
            + "                <p>Saludos cordiales,<br>El equipo de %s</p>"
            + "            </td>"
            + "        </tr>"
            + "    </table>"
            + "</div>"
            + "</body>"
            + "</html>",
            nombre, tipoM, precio, nombreS
        );

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(htmlContent, "text/html; charset=utf-8");

        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.attachFile(new File(archivoPDF));

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        multipart.addBodyPart(attachmentPart);

        message.setContent(multipart);

        Transport.send(message);

        JOptionPane.showMessageDialog(null, "Correo enviado exitosamente a " + correoDestino);

    } catch (MessagingException | IOException e) {
        e.printStackTrace();
    }
}
    
    
    public void enviarCorreoConPDFE(String correoDestino, String archivoPDF, String nombre, String nombreS, int precio, String tipoM) {
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587"); // Cambia a 465 si usas SSL

    String usuario = "pruebademicomponente@gmail.com"; // Cambia esto por tu correo de Gmail
    String clave = "dssjlwbildsrtnak"; // Cambia esto por tu contraseña de Gmail

    javax.mail.Session session = javax.mail.Session.getInstance(props, new javax.mail.Authenticator() {
        protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
            return new javax.mail.PasswordAuthentication(usuario, clave);
        }
    });

    try {
        javax.mail.Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(usuario));
        message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(correoDestino));
        message.setSubject("Registro de Membresía");

        String htmlContent = String.format(
            "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
            + "<html dir=\"ltr\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" lang=\"es\">"
            + "<head>"
            + "<meta charset=\"UTF-8\">"
            + "<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">"
            + "<meta name=\"x-apple-disable-message-reformatting\">"
            + "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
            + "<meta content=\"telephone=no\" name=\"format-detection\">"
            + "<title>Registro de Membresía</title>"
            + "<style type=\"text/css\">"
            + "body {"
            + "    width: 100%%;"
            + "    font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif;"
            + "    -webkit-text-size-adjust: 100%%;"
            + "    -ms-text-size-adjust: 100%%;"
            + "    padding: 0;"
            + "    margin: 0;"
            + "}"
            + ".es-wrapper {"
            + "    width: 100%%;"
            + "    height: 100%%;"
            + "    background-color: #FAFAFA;"
            + "    display: flex;"
            + "    justify-content: center;"
            + "    align-items: center;"
            + "}"
            + ".es-content-body {"
            + "    background-color: #FFFFFF;"
            + "    width: 600px;"
            + "    margin: auto;"
            + "    padding: 20px;"
            + "    border: 1px solid #CCCCCC;"
            + "}"
            + ".es-content-body h1 {"
            + "    color: #333333;"
            + "    font-size: 44px;"
            + "    font-weight: bold;"
            + "}"
            + ".es-content-body p {"
            + "    color: #666666;"
            + "    font-size: 14px;"
            + "}"
            + "</style>"
            + "</head>"
            + "<body>"
            + "<div class=\"es-wrapper\">"
            + "    <table class=\"es-content-body\" cellpadding=\"0\" cellspacing=\"0\">"
            + "        <tr>"
            + "            <td align=\"center\">"
            + "                <h1>Edición de datos</h1>"
            + "                <p><strong>Estimado %s,</strong></p>"
            + "                <p>Por este medio se te notifica que se han actualizado correctamente tus datos en nuestro sistema.</p>"
            + "                <p><strong>Detalles de tu membresía:</strong></p>"
            + "                <p>Tipo de membresía: %s<br>Precio: $%d</p>"
            + "                <p>Esperamos que sigas disfrutando de todos los beneficios que ofrecemos y logres alcanzar tus objetivos de salud y bienestar.</p>"
            + "                <p>¡Gracias por elegirnos!</p>"
            + "                <p>Saludos cordiales,<br>El equipo de %s</p>"
            + "            </td>"
            + "        </tr>"
            + "    </table>"
            + "</div>"
            + "</body>"
            + "</html>",
            nombre, tipoM, precio, nombreS
        );

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(htmlContent, "text/html; charset=utf-8");

        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.attachFile(new File(archivoPDF));

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        multipart.addBodyPart(attachmentPart);

        message.setContent(multipart);

        Transport.send(message);

        JOptionPane.showMessageDialog(null, "Correo enviado exitosamente a " + correoDestino);

    } catch (MessagingException | IOException e) {
        e.printStackTrace();
    }
}
    
    public void setGimnasioEmpleado(String n, int id, Date fecha) {
        Conexion con = new Conexion();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String consulta = "select distinct nombre, direccion, fecha_apertura from gimnasio where nombre = ?";

        try {
            cn = con.conectar();
            ps = cn.prepareStatement(consulta);
            ps.setString(1, n);
            rs = ps.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString(1);
                String direccion = rs.getString(2);
                Date fechita = rs.getDate(3);

                String insertConsulta = "Insert into gimnasio (nombre,direccion, fecha_apertura, empleado_g) values (?, ?, ?, ?)";
                ps = cn.prepareStatement(insertConsulta);

                ps.setString(1, nombre);
                ps.setString(2, direccion);
                ps.setDate(3, (java.sql.Date) fechita);
                ps.setInt(4, id);

                int filasAfectadas = ps.executeUpdate();

            }

        } catch (SQLException e) {
            e.printStackTrace(); // Imprime la traza de la excepción en la consola para propósitos de depuración.
            JOptionPane.showMessageDialog(null, "Error al insertar la sucursal: " + e.getMessage());
        } finally {
            // Siempre cierra los recursos en un bloque finally.
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void ActualizarFechaEmpleados(int id_persona) {
        String consultaEmpleados = "select fechacontratacion, fecha_despido from empleado where id_empleado = ?";
        Date hoy = new Date();
        java.sql.Date fechaVacia = null;

        try (Connection cn = new Conexion().conectar(); PreparedStatement ps = cn.prepareStatement(consultaEmpleados)) {

            ps.setInt(1, id_persona);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    java.sql.Date fechaD = rs.getDate("fecha_despido");

                    if (fechaD != null) {
                        String consulta2 = "update empleado set fechacontratacion = ?, fecha_despido = ? where id_empleado = ?";

                        try (PreparedStatement psUpdate = cn.prepareStatement(consulta2)) {
                            psUpdate.setDate(1, new java.sql.Date(hoy.getTime()));
                            psUpdate.setDate(2, fechaVacia);
                            psUpdate.setInt(3, id_persona);
                            psUpdate.executeUpdate();
                        }
                    } else {
                        String consulta3 = "update empleado set fecha_despido = ? where id_empleado = ?";

                        try (PreparedStatement psUpdate = cn.prepareStatement(consulta3)) {
                            psUpdate.setDate(1, new java.sql.Date(hoy.getTime()));
                            psUpdate.setInt(2, id_persona);
                            psUpdate.executeUpdate();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el empleado con ID: " + id_persona);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime la traza de la excepción en la consola para propósitos de depuración
            JOptionPane.showMessageDialog(null, "Error al actualizar la fecha del empleado: " + e.getMessage());
        }
    }
    public void ActualizarEmpleado(int id_empleado, String Nombre, String A_Paterno, String A_Materno, String CURP, String Direccion, String Telefono, int Edad, int Salario, String Horario) {
        try {
            PreparedStatement ps = null;
            Connection con = new Conexion().conectar();

            String consulta = "UPDATE Persona SET Nombre = ?, A_Paterno = ?, A_Materno = ?, CURP = ?, Direccion = ?, Telefono = ?, Edad = ? WHERE id_persona = ?";
            ps = con.prepareStatement(consulta);
            ps.setString(1, Nombre);
            ps.setString(2, A_Paterno);
            ps.setString(3, A_Materno);
            ps.setString(4, CURP);
            ps.setString(5, Direccion);
            ps.setString(6, Telefono);
            ps.setInt(7, Edad);
            ps.setInt(8, id_empleado);
            ps.executeUpdate();

            consulta = "UPDATE Empleado SET salario = ?, horario = ? WHERE id_empleado = ?";
            ps = con.prepareStatement(consulta);
            ps.setInt(1, Salario);
            ps.setString(2, Horario);
            ps.setInt(3, id_empleado);
            ps.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar los datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void cambiarContraseniaAdmin(int id_empleado, String contra) {

        try {
            PreparedStatement ps = null;
            Connection con = new Conexion().conectar();

            String consulta = "UPDATE admin SET contra_admin = ?  WHERE id_admin = ?";
            ps = con.prepareStatement(consulta);

            ps.setString(1, contra);
            ps.setInt(2, id_empleado);
            ps.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar los datos: " + e.getMessage());
            e.printStackTrace();
        }

    }
    public void ActualizarSucursal(int id_empleado, String Nombre_Sucursal) {

        String consultaGimnasio = "select distinct nombre, direccion, fecha_apertura from gimnasio where nombre = ?";
        String updateGimnasio = "update gimnasio set nombre = ?, direccion = ?, fecha_apertura = ? where empleado_g = ?";

        try (Connection cn = new Conexion().conectar(); PreparedStatement psGimnasio = cn.prepareStatement(consultaGimnasio); PreparedStatement psUpdateGimnasio = cn.prepareStatement(updateGimnasio)) {

            psGimnasio.setString(1, Nombre_Sucursal);

            try (
                    ResultSet rsNombre_Sucursal = psGimnasio.executeQuery()) {

                if (rsNombre_Sucursal.next()) {

                    String nombre = rsNombre_Sucursal.getString(1);
                    String direccion = rsNombre_Sucursal.getString(2);
                    java.sql.Date fecha = rsNombre_Sucursal.getDate(3);

                    psUpdateGimnasio.setString(1, nombre);
                    psUpdateGimnasio.setString(2, direccion);
                    psUpdateGimnasio.setDate(3, fecha);
                    psUpdateGimnasio.setInt(4, id_empleado);
                    psUpdateGimnasio.executeUpdate();

                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error la sucursal para el empleado: " + e.getMessage());
        }

    }
    public void cambiarContraseniaEmpleado(int id_empleado, String contra, String rol) {

        try {
            PreparedStatement ps = null;
            Connection con = new Conexion().conectar();

            String consulta = "UPDATE asalariado SET contra_asa = ?, rol = ?  WHERE id_asalariado = ?";
            ps = con.prepareStatement(consulta);

            ps.setString(1, contra);
            ps.setString(2, rol);
            ps.setInt(3, id_empleado);
            ps.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar los datos: " + e.getMessage());
            e.printStackTrace();
        }

    }
    public void Editar_MembresiasBD(String nombreAntiguo, String descripcionAntigua, int precioAntiguo, String nombre, int precio, String descripcion) {

        Connection conn = new Conexion().conectar();
        PreparedStatement pstmt = null;
        Statement stmt = null;

        try {

            stmt = conn.createStatement();
            stmt.execute("SET SQL_SAFE_UPDATES = 0");

            String sqlActualizacion = "UPDATE membresia "
                    + "SET nombre = ?, precio = ?, descripcion = ? "
                    + "WHERE nombre = ? AND descripcion = ? AND precio = ?";

            pstmt = conn.prepareStatement(sqlActualizacion);

            pstmt.setString(1, nombre);
            pstmt.setInt(2, precio);
            pstmt.setString(3, descripcion);
            pstmt.setString(4, nombreAntiguo);
            pstmt.setString(5, descripcionAntigua);
            pstmt.setInt(6, precioAntiguo);

            int filasActualizadas = pstmt.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("Se actualizaron " + filasActualizadas + " filas.");
            } else {
                System.out.println("No se encontraron filas para actualizar.");
            }

            stmt.execute("SET SQL_SAFE_UPDATES = 1");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar PreparedStatement y Connection
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void eliminarMembresias(String nombreMembresia) {
        Conexion con = new Conexion();
        Connection cn = null;

        String consultaIdMembresia = "SELECT id_membresia, cliente_m FROM membresia WHERE nombre = ?";
        String consultaBorrarGimnasio = "DELETE FROM gimnasio WHERE membresia_g = ?";
        String consultaBorrarMembresia = "DELETE FROM membresia WHERE id_membresia = ?";
        String consultaBorrarCliente = "DELETE FROM cliente WHERE id_cliente = ?";
        String consultaBorrarPersona = "DELETE FROM persona WHERE id_persona = ?";
        String consultaCountMembresiasCliente = "SELECT COUNT(*) FROM membresia WHERE cliente_m = ?";

        try {
            cn = con.conectar();
            cn.setAutoCommit(false); // Inicia una transacción

            try (PreparedStatement psIdMembresia = cn.prepareStatement(consultaIdMembresia)) {
                psIdMembresia.setString(1, nombreMembresia);
                try (ResultSet rs = psIdMembresia.executeQuery()) {
                    while (rs.next()) {
                        int idMembresia = rs.getInt("id_membresia");
                        int idCliente = rs.getInt("cliente_m");

                        // Eliminar registros de la tabla gimnasio que dependen de membresia
                        try (PreparedStatement psBorrarGimnasio = cn.prepareStatement(consultaBorrarGimnasio)) {
                            psBorrarGimnasio.setInt(1, idMembresia);
                            psBorrarGimnasio.executeUpdate();
                        }

                        // Eliminar registros de la tabla membresia
                        try (PreparedStatement psBorrarMembresia = cn.prepareStatement(consultaBorrarMembresia)) {
                            psBorrarMembresia.setInt(1, idMembresia);
                            psBorrarMembresia.executeUpdate();
                        }

                        // Verificar si no hay más membresías asociadas al cliente
                        try (PreparedStatement psCountMembresias = cn.prepareStatement(consultaCountMembresiasCliente)) {
                            psCountMembresias.setInt(1, idCliente);
                            try (ResultSet rsCount = psCountMembresias.executeQuery()) {
                                if (rsCount.next() && rsCount.getInt(1) == 0) {
                                    // Eliminar registros de la tabla cliente y persona
                                    try (PreparedStatement psBorrarCliente = cn.prepareStatement(consultaBorrarCliente)) {
                                        psBorrarCliente.setInt(1, idCliente);
                                        psBorrarCliente.executeUpdate();
                                    }

                                    try (PreparedStatement psBorrarPersona = cn.prepareStatement(consultaBorrarPersona)) {
                                        psBorrarPersona.setInt(1, idCliente);
                                        psBorrarPersona.executeUpdate();
                                    }
                                }
                            }
                        }
                    }
                }
            }

            cn.commit(); // Confirma la transacción
        } catch (SQLException e) {
            try {
                if (cn != null) {
                    cn.rollback(); // Revierte la transacción en caso de error
                }
            } catch (SQLException ex) {
                ex.printStackTrace(); // Manejo de errores al revertir la transacción
            }
            JOptionPane.showMessageDialog(null, "Error al eliminar la membresía: " + e.getMessage());
        } finally {
            try {
                if (cn != null) {
                    cn.setAutoCommit(true); // Restaura el modo de autocommit
                    cn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace(); // Manejo de errores al cerrar recursos
            }
        }
    }
    public void Editar_SucursalesBD(String nombreAntiguo, String direccion_A, java.sql.Date fecha_A, String nombre_N, String direccion_N, java.sql.Date Fecha_N) {

        String sqlActualizacion = "UPDATE gimnasio SET nombre = ?, direccion = ?, fecha_apertura = ? WHERE nombre = ? AND direccion = ? AND fecha_apertura = ?";

        try (Connection conn = new Conexion().conectar(); PreparedStatement pstmt = conn.prepareStatement(sqlActualizacion); Statement stmt = conn.createStatement()) {

            stmt.execute("SET SQL_SAFE_UPDATES = 0");

            pstmt.setString(1, nombre_N);
            pstmt.setString(2, direccion_N);
            pstmt.setDate(3, Fecha_N);
            pstmt.setString(4, nombreAntiguo);
            pstmt.setString(5, direccion_A);
            pstmt.setDate(6, fecha_A);

            int filasActualizadas = pstmt.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("Se actualizaron " + filasActualizadas + " filas.");
            } else {
                System.out.println("No se encontraron filas para actualizar.");
            }

            stmt.execute("SET SQL_SAFE_UPDATES = 1");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void ActualizarFechaSucursal(String nombreS) {

        String consultaEmpleados = "select distinct fecha_apertura, fecha_cierre from gimnasio where nombre = ?";

        Date hoy = new Date();
        java.sql.Date fechaVacia = null;

        try (Connection cn = new Conexion().conectar(); PreparedStatement ps = cn.prepareStatement(consultaEmpleados); Statement stmt = cn.createStatement()) {
            ps.setString(1, nombreS);

            stmt.execute("SET SQL_SAFE_UPDATES = 0");

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    java.sql.Date fechaD = rs.getDate("fecha_cierre");

                    if (fechaD != null) {

                        String consulta2 = "update gimnasio set fecha_apertura = ?, fecha_cierre = ? where nombre = ?";

                        try (PreparedStatement psUpdate = cn.prepareStatement(consulta2)) {

                            psUpdate.setDate(1, new java.sql.Date(hoy.getTime()));
                            psUpdate.setDate(2, fechaVacia);
                            psUpdate.setString(3, nombreS);
                            psUpdate.executeUpdate();

                        }
                    } else {
                        String consulta3 = "update gimnasio set fecha_cierre = ? where nombre = ?";
                        try (PreparedStatement psUpdate = cn.prepareStatement(consulta3)) {

                            psUpdate.setDate(1, new java.sql.Date(hoy.getTime()));
                            psUpdate.setString(2, nombreS);
                            psUpdate.executeUpdate();

                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, " Selecciona correctamente la tabla");
                }
            }

            stmt.execute("SET SQL_SAFE_UPDATES = 1");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la fecha del empleado: " + e.getMessage());
        }
    }
    public void eliminarSucursales(String nombreS) {
        Conexion con = new Conexion();
        Connection cn = null;

        String consultaIdGimnasio = "SELECT membresia_g, empleado_g FROM gimnasio WHERE nombre = ?";
        String consultaIdMembresia = "SELECT cliente_m FROM membresia WHERE id_membresia = ?";

        String consultaBorrarGimnasio = "DELETE FROM gimnasio WHERE membresia_g = ?";
        String consultaBorrarMembresia = "DELETE FROM membresia WHERE id_membresia = ?";
        String consultaBorrarCliente = "DELETE FROM cliente WHERE id_cliente = ?";
        String consultaBorrarPersona = "DELETE FROM persona WHERE id_persona = ?";

        String consultaBorrarGimnasioE = "DELETE FROM gimnasio WHERE empleado_g = ?";
        String consultaBorrarAsalariado = "DELETE FROM asalariado WHERE id_asalariado = ?";
        String consultaBorrarAdministrador = "DELETE FROM admin WHERE id_admin = ?";
        String consultaBorrarEmpleado = "DELETE FROM empleado WHERE id_empleado = ?";

        // Nueva consulta para borrar registros de gimnasio con empleado_g y membresia_g como NULL y nombre coincidente
        String consultaBorrarGimnasioNull = "DELETE FROM gimnasio WHERE empleado_g IS NULL AND membresia_g IS NULL AND nombre = ?";

        try {
            cn = con.conectar();
            cn.setAutoCommit(false); // Inicia una transacción

            try (PreparedStatement psIdGimnasio = cn.prepareStatement(consultaIdGimnasio)) {
                psIdGimnasio.setString(1, nombreS);
                try (ResultSet rs = psIdGimnasio.executeQuery()) {
                    while (rs.next()) {
                        int id_membresia = rs.getInt("membresia_g");
                        int id_empleado = rs.getInt("empleado_g");

                        try (PreparedStatement psIdMembresia = cn.prepareStatement(consultaIdMembresia)) {
                            psIdMembresia.setInt(1, id_membresia);
                            try (ResultSet rsM = psIdMembresia.executeQuery()) {
                                while (rsM.next()) {
                                    int id_cliente = rsM.getInt("cliente_m");

                                    try (PreparedStatement psBorrarGimnasio = cn.prepareStatement(consultaBorrarGimnasio)) {
                                        psBorrarGimnasio.setInt(1, id_membresia);
                                        psBorrarGimnasio.executeUpdate();
                                    }
                                    try (PreparedStatement psBorrarMembresia = cn.prepareStatement(consultaBorrarMembresia)) {
                                        psBorrarMembresia.setInt(1, id_membresia);
                                        psBorrarMembresia.executeUpdate();
                                    }
                                    try (PreparedStatement psBorrarCliente = cn.prepareStatement(consultaBorrarCliente)) {
                                        psBorrarCliente.setInt(1, id_cliente);
                                        psBorrarCliente.executeUpdate();
                                    }
                                    try (PreparedStatement psBorrarPersona = cn.prepareStatement(consultaBorrarPersona)) {
                                        psBorrarPersona.setInt(1, id_cliente);
                                        psBorrarPersona.executeUpdate();
                                    }
                                }
                            }
                        }

                        try (PreparedStatement psBorrarGimnasioE = cn.prepareStatement(consultaBorrarGimnasioE)) {
                            psBorrarGimnasioE.setInt(1, id_empleado);
                            psBorrarGimnasioE.executeUpdate();
                        }
                        try (PreparedStatement psBorrarAsalariado = cn.prepareStatement(consultaBorrarAsalariado)) {
                            psBorrarAsalariado.setInt(1, id_empleado);
                            psBorrarAsalariado.executeUpdate();
                        }
                        try (PreparedStatement psAdmin = cn.prepareStatement(consultaBorrarAdministrador)) {
                            psAdmin.setInt(1, id_empleado);
                            psAdmin.executeUpdate();
                        }
                        try (PreparedStatement psBorrarEmpleado = cn.prepareStatement(consultaBorrarEmpleado)) {
                            psBorrarEmpleado.setInt(1, id_empleado);
                            psBorrarEmpleado.executeUpdate();
                        }
                        try (PreparedStatement psBorrarPersona = cn.prepareStatement(consultaBorrarPersona)) {
                            psBorrarPersona.setInt(1, id_empleado);
                            psBorrarPersona.executeUpdate();
                        }
                    }
                }
            }

            // Ejecutar la consulta para eliminar registros de gimnasio donde empleado_g y membresia_g sean NULL y el nombre coincida
            try (PreparedStatement psBorrarGimnasioNull = cn.prepareStatement(consultaBorrarGimnasioNull)) {
                psBorrarGimnasioNull.setString(1, nombreS);
                psBorrarGimnasioNull.executeUpdate();
            }

            cn.commit(); // Hace commit de la transacción si todo fue exitoso

        } catch (SQLException e) {
            if (cn != null) {
                try {
                    cn.rollback(); // Hace rollback en caso de error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace(); // Maneja la excepción y la registra
        } finally {
            if (cn != null) {
                try {
                    cn.setAutoCommit(true);
                    cn.close(); // Cierra la conexión
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
