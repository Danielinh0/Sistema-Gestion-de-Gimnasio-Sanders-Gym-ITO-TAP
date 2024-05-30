# Sistema Gestion De Gimnasio Sanders GYM
¡Bienvenido al Sistema de Gestión de un Gimnasio! Este software ha sido diseñado para ayudar a los gimnasios a gestionar eficientemente sus operaciones diarias. Con una interfaz fácil de usar y una amplia gama de funcionalidades, este sistema asegura que tanto el personal administrativo como los miembros del gimnasio tengan una experiencia fluida y eficiente.

## Características y Funcionalidades Principales

### *Gestión de Miembros*
- Registro de Miembros: Permite registrar nuevos miembros con toda la información relevante, como nombre, apellido, edad, correo electrónico y tipo de membresía.
- Actualización de Datos: Facilita la actualización de los datos de los miembros existentes.
- Historial de Membresías: Mantiene un registro del historial de membresías y pagos de cada miembro.
### *Gestión de Empleados*
- Registro de Empleados: Permite añadir y gestionar los empleados del gimnasio, asignándoles roles específicos (administrador, entrenador, recepcionista, etc.).
- Control de Acceso: Los empleados pueden iniciar sesión con sus credenciales y acceder a las funcionalidades según sus roles.
### *Generación de Informes*
- Informes PDF: Genera informes detallados en formato PDF sobre la información de los miembros, estado de las membresías y más.
- Gráficos: Genera gráficos de pastel y de barras para visualizar la distribución de clientes por edad y tipo de membresía.
### *Gestión de Suscripciones*
- Planes de Membresía: Permite definir y gestionar diferentes planes de membresía (mensual, trimestral, anual, etc.).
- Control de Pagos: Realiza un seguimiento de los pagos de los miembros y genera alertas para las renovaciones pendientes.
### *Comunicación*
- Notificaciones por Correo Electrónico: Envía correos electrónicos con archivos PDF adjuntos para notificar a los miembros sobre actualizaciones, vencimientos de membresías y promociones.
- Confirmaciones de Registro: Envía confirmaciones automáticas por correo electrónico al registrar nuevos miembros o empleados.
  
## Tecnologías Utilizadas
- Java: El lenguaje principal utilizado para la implementación del sistema.
- JDBC: Para la conexión y manipulación de la base de datos.
- iText: Para la generación de archivos PDF.
- JFreeChart: Para la creación de gráficos.
- Swing: Para la interfaz gráfica de usuario (GUI).
- MySQL: Como base de datos para almacenar toda la información del gimnasio.


## Clases Utilizadas
### Clase Conexión
La clase Conexión tiene como objetivo establecer una conexión con una base de datos MySQL y sus principales funciones y características:

| Método                     | Descripción                                                                                                     |
|----------------------------|-----------------------------------------------------------------------------------------------------------------|
| `conectar()`               | Intenta establecer una conexión con la base de datos utilizando los valores de `URL`, `USER` y `CLAVE`. Si la conexión es exitosa, se asigna al atributo `con`. Si ocurre un error, se imprime un mensaje con los detalles del error. Finalmente, el método devuelve el objeto `Connection`. |


### Clase métodos
En esta clase tenemos todos los métodos ocupados para el funcionamiento correcto del programa, a continuación se hará el listado de los métodos más importantes.


| Método                     | Descripción                                                                                                     |
|----------------------------|-----------------------------------------------------------------------------------------------------------------|
| `validarUsuario(String correo, String contraseña)` | Verifica las credenciales de un usuario (correo y contraseña) y abre la ventana correspondiente (administrador o empleado) si las credenciales son correctas. Si las credenciales son incorrectas, muestra un mensaje de error. **Con los debidos métodos utilizados:** `conectar()` de `Conexion`, `setVisible(true)`, `setUserDataA(...)`, `setUserDataE(...)`, `setSucursal(...)`. |
| `GuardarDatosPersona(String nombre, String apellido, int edad, String correo)` | Inserta los datos de una persona en la base de datos. **Con los debidos métodos utilizados:** `conectar()` de `Conexion`, `prepareStatement(String sql)` de `Connection`, `setString(int parameterIndex, String x)`, `setInt(int parameterIndex, int x)` , `executeUpdate()`. |
| `preguntarAbrirPDF(String filePath)` | Muestra un diálogo para confirmar si se desea abrir el archivo PDF en la ubicación especificada. Si se selecciona "Sí" y el archivo existe, lo abre. Si hay un error al abrirlo o el archivo no existe, muestra un mensaje de error. **Con los debidos métodos utilizados:** `showConfirmDialog(...)` y `showMessageDialog(...)` de `JOptionPane`, `getDesktop().open(...)` , `exists()` de `File`. |
| `ActualizarCliente(String nombre, String apellido, int edad, String correo)` | Actualiza los datos de un cliente en las tablas `Persona` y `Cliente` en la base de datos. **Con los debidos métodos utilizados:** `conectar()` de `Conexion`, `prepareStatement(String sql)` de `Connection`, `setString(int parameterIndex, String x)`, `setInt(int parameterIndex, int x)` de `PreparedStatement`, `executeUpdate()`. |
| `crearPDF(int idPersona)` | Genera un archivo PDF con los datos de una persona, incluyendo su registro y detalles de membresía. **Con los debidos métodos utilizados:** `conectar()` de `Conexion`, `prepareStatement(String sql)` de `Connection`, `setInt(int parameterIndex, int x)`, `executeQuery()` de `PreparedStatement`, Métodos de la biblioteca iText (`PdfWriter`, `PdfDocument`, `Document`, etc.), `showSaveDialog(...)` de `JFileChooser`, `showConfirmDialog(...)` y `showMessageDialog(...)` de `JOptionPane`, `open(File file)` de `Desktop`, `preguntarAbrirPDF(String filePath)` y `enviarCorreoConPDF(String correoDestino, String archivoPDF)`. |
| `generarGraficoPastel()` | Genera un gráfico de pastel con la distribución de clientes por edad. **Con los debidos métodos utilizados:** `obtenerConexion()`, `createStatement()` de `Connection`, `executeQuery(String sql)` de `Statement`, `setValue(...)` de `DefaultPieDataset`, `createPieChart(...)` de `ChartFactory`, `setLabelGenerator(...)` de `PiePlot`, `showMessageDialog(...)`. |
| `generarGraficoBarras()` | Genera un gráfico de barras con el número de personas que tienen diferentes membresías. **Con los debidos métodos utilizados:** `obtenerConexion()`, `createStatement()` de `Connection`, `executeQuery(String sql)` de `Statement`, `addValue(...)` de `DefaultCategoryDataset`, `createBarChart(...)` de `ChartFactory`. |
| `enviarCorreoConPDF(String correoDestino, String archivoPDF)` | Envía un correo electrónico con un archivo PDF adjunto. **Con los debidos métodos utilizados:** `getInstance(Properties props, Authenticator authenticator)` de `Session`, `setFrom(...)`, `setRecipients(...)`, `setSubject(...)`, `setContent(...)` de `Message`, Métodos de `MimeBodyPart` y `Multipart`, `send(Message msg)` de `Transport`. |
| `generarContraseñaAleatoria` | Genera una contraseña aleatoria de una longitud especificada utilizando caracteres alfanuméricos. **Con los debidos métodos utilizados:**: `nextInt(int bound)` de `SecureRandom`: Para generar un número aleatorio dentro del rango de la longitud de la cadena de caracteres. |
| `GuardarDatosEmpleado`      | Guarda los datos de un empleado en la tabla `Empleado` de la base de datos. **Con los debidos métodos utilizados:**: `conectar()` de `Conexion`: Para obtener la conexión a la base de datos. `prepareStatement(String sql)` de `Connection`: Para preparar la consulta SQL. `setInt(int parameterIndex, int x)`, `setString(int parameterIndex, String x)`, y `setDate(int parameterIndex, Date x)` de `PreparedStatement`: Para establecer los valores de los parámetros en la consulta. `executeUpdate()` de `PreparedStatement`: Para ejecutar la consulta de inserción. `showMessageDialog(...)` de `JOptionPane`: Para mostrar mensajes de error. |
| `obtenerUltimoIdPersona`    | Obtiene el último `id_persona` insertado en la tabla `Persona`. **Con los debidos métodos utilizados:**: `conectar()` de `Conexion`: Para obtener la conexión a la base de datos. `prepareStatement(String sql)` de `Connection`: Para preparar la consulta SQL. `executeQuery()` de `PreparedStatement`: Para ejecutar la consulta. `getInt(String columnLabel)` de `ResultSet`: Para obtener el valor del resultado de la consulta. `showMessageDialog(...)` de `JOptionPane`: Para mostrar mensajes de error. |
| `crearPDF_EC`               | Genera un PDF de edicion de registro de cliente, obteniendo información de la base de datos y formateando el contenido con la biblioteca iText. **Con los debidos métodos utilizados:**: `conectar()` de `Conexion`: Para obtener la conexión a la base de datos. `prepareStatement(String sql)` de `Connection`: Para preparar la consulta SQL. `setInt(int parameterIndex, int x)` de `PreparedStatement`: Para establecer los valores de los parámetros en la consulta. `executeQuery()` de `PreparedStatement`: Para ejecutar la consulta. `getString(int columnIndex)` y `getInt(int columnIndex)` de `ResultSet`: Para obtener los resultados de la consulta. `LocalDateTime.now()`, `minusHours(int hours)`, `DateTimeFormatter.ofPattern(String pattern)`, `format(TemporalAccessor temporal)`: Para obtener y formatear la fecha y hora actuales. `Paragraph.add(String text)`: Para agregar texto a un párrafo. `ImageDataFactory.create(String src)`: Para crear una imagen. `scaleToFit(float width, float height)`, `setFixedPosition(float x, float y)`: Para ajustar y posicionar la imagen. `JFileChooser`, `setDialogTitle(String title)`, `setSelectedFile(File file)`, `showSaveDialog(Component parent)`: Para seleccionar la ubicación de guardado del PDF. `PdfWriter`, `PdfDocument`, `Document`: Para escribir el PDF. `PdfFontFactory.createFont(String fontProgram)`: Para establecer las fuentes. `setTextAlignment(TextAlignment alignment)`: Para alinear el texto. `add(IBlockElement element)`: Para agregar elementos al documento PDF. `close()`: Para cerrar el documento. `enviarCorreoConPDF(String email, String filePath)`: Para enviar el PDF por correo (método externo). `preguntarAbrirPDF(String filePath)`: Para preguntar al usuario si desea abrir el PDF (método externo). |
| `crearPDFEmpleado`          | Genera un PDF de registro de empleado, obteniendo información de la base de datos y formateando el contenido con la biblioteca iText. **Con los debidos métodos utilizados:**: `conectar()` de `Conexion`: Para obtener la conexión a la base de datos. `prepareStatement(String sql)` de `Connection`: Para preparar la consulta SQL. `setInt(int parameterIndex, int x)` de `PreparedStatement`: Para establecer los valores de los parámetros en la consulta. `executeQuery()` de `PreparedStatement`: Para ejecutar la consulta. `getString(int columnIndex)` y `getInt(int columnIndex)` de `ResultSet`: Para obtener los resultados de la consulta. `LocalDateTime.now()`, `minusHours(int hours)`, `DateTimeFormatter.ofPattern(String pattern)`, `format(TemporalAccessor temporal)`: Para obtener y formatear la fecha y hora actuales. `Paragraph.add(String text)`: Para agregar texto a un párrafo. `ImageDataFactory.create(String src)`: Para crear una imagen. `scaleToFit(float width, float height)`, `setFixedPosition(float x, float y)`: Para ajustar y posicionar la imagen. `JFileChooser`, `setDialogTitle(String title)`, `setSelectedFile(File file)`, `showSaveDialog(Component parent)`: Para seleccionar la ubicación de guardado del PDF. `PdfWriter`, `PdfDocument`, `Document`: Para escribir el PDF. `PdfFontFactory.createFont(String fontProgram)`: Para establecer las fuentes. `setTextAlignment(TextAlignment alignment)`: Para alinear el texto. `add(IBlockElement element)`: Para agregar elementos al documento PDF. `close()`: Para cerrar el documento. `enviarCorreoConPDF(String email, String filePath)`: Para enviar el PDF por correo (método externo). `preguntarAbrirPDF(String filePath)`: Para preguntar al usuario si desea abrir el PDF (método externo). |
| `crearPDFEmpleado_ED`       | Genera un PDF de edición de datos de empleado, obteniendo información de la base de datos y formateando el contenido con la biblioteca iText. **Con los debidos métodos utilizados:**: `conectar()` de `Conexion`: Para obtener la conexión a la base de datos. `prepareStatement(String sql)` de `Connection`: Para preparar la consulta SQL. `setInt(int parameterIndex, int x)` de `PreparedStatement`: Para establecer los valores de los parámetros en la consulta. `executeQuery()` de `PreparedStatement`: Para ejecutar la consulta. `getString(int columnIndex)` y `getInt(int columnIndex)` de `ResultSet`: Para obtener los resultados de la consulta. `LocalDateTime.now()`, `minusHours(int hours)`, `DateTimeFormatter.ofPattern(String pattern)`, `format(TemporalAccessor temporal)`: Para obtener y formatear la fecha y hora actuales. `Paragraph.add(String text)`: Para agregar texto a un párrafo. `ImageDataFactory.create(String src)`: Para crear una imagen. `scaleToFit(float width, float height)`, `setFixedPosition(float x, float y)`: Para ajustar y posicionar la imagen. `JFileChooser`, `setDialogTitle(String title)`, `setSelectedFile(File file)`, `showSaveDialog(Component parent)`: Para seleccionar la ubicación de guardado del PDF. `PdfWriter`, `PdfDocument`, `Document`: Para escribir el PDF. `PdfFontFactory.createFont(String fontProgram)`: Para establecer las fuentes. `setTextAlignment(TextAlignment alignment)`: Para alinear el texto. `add(IBlockElement element)`: Para agregar elementos al documento PDF. `close()`: Para cerrar el documento. `enviarCorreoConPDF(String email, String filePath)`: Para enviar el PDF por correo (método externo). `preguntarAbrirPDF(String filePath)`: Para preguntar al usuario si desea abrir el PDF (método externo). |
| `crearPDFAdministrador`     | Genera un PDF de registro de administrador, obteniendo información de la base de datos y formateando el contenido con la biblioteca iText. **Con los debidos métodos utilizados:**: `conectar()` de `Conexion`: Para obtener la conexión a la base de datos. `prepareStatement(String sql)` de `Connection`: Para preparar la consulta SQL. `setInt(int parameterIndex, int x)` de `PreparedStatement`: Para establecer los valores de los parámetros en la consulta. `executeQuery()` de `PreparedStatement`: Para ejecutar la consulta. `getString(int columnIndex)` y `getInt(int columnIndex)` de `ResultSet`: Para obtener los resultados de la consulta. `LocalDateTime.now()`, `minusHours(int hours)`, `DateTimeFormatter.ofPattern(String pattern)`, `format(TemporalAccessor temporal)`: Para obtener y formatear la fecha y hora actuales. `Paragraph.add(String text)`: Para agregar texto a un párrafo. `ImageDataFactory.create(String src)`: Para crear una imagen. `scaleToFit(float width, float height)`, `setFixedPosition(float x, float y)`: Para ajustar y posicionar la imagen. `JFileChooser`, `setDialogTitle(String title)`, `setSelectedFile(File file)`, `showSaveDialog(Component parent)`: Para seleccionar la ubicación de guardado del PDF. `PdfWriter`, `PdfDocument`, `Document`: Para escribir el PDF. `PdfFontFactory.createFont(String fontProgram)`: Para establecer las fuentes. `setTextAlignment(TextAlignment alignment)`: Para alinear el texto. `add(IBlockElement element)`: Para agregar elementos al documento PDF. `close()`: Para cerrar el documento. `enviarCorreoConPDF(String email, String filePath)`: Para enviar el PDF por correo (método externo). `preguntarAbrirPDF(String filePath)`: Para preguntar al usuario si desea abrir el PDF (método externo). |
| `crearPDFAdministrador_ED`  | Genera un PDF de edición de datos de administrador, obteniendo información de la base de datos y formateando el contenido con la biblioteca iText. **Con los debidos métodos utilizados:**: `conectar()` de `Conexion`: Para obtener la conexión a la base de datos. `prepareStatement(String sql)` de `Connection`: Para preparar la consulta SQL. `setInt(int parameterIndex, int x)` de `PreparedStatement`: Para establecer los valores de los parámetros en la consulta. `executeQuery()` de `PreparedStatement`: Para ejecutar la consulta. `getString(int columnIndex)` y `getInt(int columnIndex)` de `ResultSet`: Para obtener los resultados de la consulta. `LocalDateTime.now()`, `minusHours(int hours)`, `DateTimeFormatter.ofPattern(String pattern)`, `format(TemporalAccessor temporal)`: Para obtener y formatear la fecha y hora actuales. `Paragraph.add(String text)`: Para agregar texto a un párrafo. `ImageDataFactory.create(String src)`: Para crear una imagen. `scaleToFit(float width, float height)`, `setFixedPosition(float x, float y)`: Para ajustar y posicionar la imagen. `JFileChooser`, `setDialogTitle(String title)`, `setSelectedFile(File file)`, `showSaveDialog(Component parent)`: Para seleccionar la ubicación de guardado del PDF. `PdfWriter`, `PdfDocument`, `Document`: Para escribir el PDF. `PdfFontFactory.createFont(String fontProgram)`: Para establecer las fuentes. `setTextAlignment(TextAlignment alignment)`: Para alinear el texto. `add(IBlockElement element)`: Para agregar elementos al documento PDF. `close()`: Para cerrar el documento. `enviarCorreoConPDF(String email, String filePath)`: Para enviar el PDF por correo (método externo). `preguntarAbrirPDF(String filePath)`: Para preguntar al usuario si desea abrir el PDF (método externo). |


## Instalación

### Descarga del repositorio y sus librerias

Para comenzar la instalación necesitas del repositorio el cual puedes descargarlo en este link.

https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP.git 

o bien directamente desde la opción “Code” y después Download ZIP.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/d60bb060-f598-4c6f-8034-10b4e4d4e8c7)

Al igual que las librerias correspondientes las cuales puedes descargar en este link.

https://drive.google.com/drive/folders/1UY92ffaZnVUB86nuV167org4QWpWMY0Y?usp=sharing.


Una vez descargados el repositorio y sus debidas librerías tendríamos algo similar a esto:

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/7de8be15-fb2b-47b6-a5db-94f977ddbd9b)

Descomprimiremos todos los archivos para posteriormente pasar a la resolución de problemas en Netbeans.

Recordando que nuestro proyecto principal es aquel llamado Sistema-Gestion-de-Gimanasio-Sanders-GYM

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/936a942a-4a75-4dea-bc05-38776cda7516)

### Dentro De Netbeans

Una vez en netbeans nos dirigiremos al apartado de abrir proyecto

![image](https://github.com/Danielinh0/ITO_U2_PDFBoton/assets/168349271/1de2729e-3155-4370-a6fd-97dc05fbb197)

En el buscaremos la carpeta descomprimida del programa de gestión y según sea el lugar que se haya hecho. Lo abrimos y proseguimos a la parte de la instalación de las librerías de este mismo.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/465c32c9-c31f-46a8-a47b-5565755a999b)


El proyecto nos muestra que hay algunos errores dentro de este.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/8ce13475-f57c-40f8-9085-29aed27e404b)


Para la solución de este mismo haremos lo siguiente:
Clic derecho sobre el proyecto, nos desplegara opciones para nuestro proyecto, pero nos centraremos en dos: Resolve Project Problems y Properties.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/4e1b89a0-ed6c-4dc9-8a57-f97dce204d60)


La primera opción nos mostrara los documentos o bien los jar que hacen falta, como se muestra a continuación.


![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/bf6f12cd-8b49-454a-a5b1-716f33c7c640)

Para ello tenemos que usar las librerías que anteriormente descargamos, por si no los has descargado aquí dejo el link de estas librerías: 

https://drive.google.com/drive/folders/1UY92ffaZnVUB86nuV167org4QWpWMY0Y?usp=sharing 


Para ello nos iremos a la segunda opción “Properties” 

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/4e1b89a0-ed6c-4dc9-8a57-f97dce204d60)

Nos mostrara la siguiente ventana en la cual nos debemos dirigir al apartado de “libraries”, una vez dentro tendremos que eliminar manualmente uno por uno cada librería rota.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/63678d7a-6219-4865-838e-b6087f27822f)

Una vez vaciada las librerias rotas, tendremos que meter las librerías nuevas manualmente con el signo de “+” que se encuentra al lado izquierdo de “remove”. En cada librería vienen únicamente los jar´s que se ocuparon y esos mismos son los que van a agregar.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/4c15bcaa-19a4-4366-aabe-93f04ed6aebe)


Para agregar dichos jar se hace lo siguiente clic al botón “+” nos abre esa pequeña ventana en donde presionaremos “Add JAR/Folder”.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/57d92b12-c384-425b-9b02-4e0e77db2067)


Buscaremos la ubicación de estas librerías anteriormente descargadas.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/061d0cbe-9bfd-4db5-9ff7-343088261b40)


Se deberá hacer eso en cada carpeta de cada librería.

Librería JfreeChart y sus respectivos Jar, seleccionamos e importamos.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/7af829ca-861c-482e-975d-fc25239583f7)

Librería Itext y sus respectivos Jar, seleccionamos e importamos.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/d415fcd9-7237-44fa-8342-037887e6cbd3)

Librerías JavaxMail Y sus respectivos Jar, seleccionamos e importamos.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/1aef93d9-946f-46bb-b8f1-caf02c1e69c5)

Librería MySqlConnection y su respectivo jar, seleccionamos e importamos.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/4504d071-a7c7-419c-9e88-834e2f7bdb80)

Finalmente, los errores desaparecieron, pero ahora vamos a limpiar este mismo.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/ec337f60-72e9-4a5a-b5c9-dbc2131bf725)

Para limpiarlo daremos Clic derecho sobre el proyecto, apareciendo así la opción de clean and build, el cual daremos clic.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/d22ba9b9-4155-4fdd-9704-4ceabb59f4b6)

Hemos terminado de instalar la aplicación, ahora iremos a las pruebas de funcionamiento.

## Modelo Vista Controlador

Antes de mostrar el funcionamiento tenemos que hablar de los siguientes frames dentro del paquete *Listos*.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/aab56315-cb2c-4188-80d2-33313b691d04)


Cada uno tiene una función diferente, pero todas se relacionan, hablaremos en general del MVC (Modelo Vista Controlador)

### Modelo
En la parte del `Modelo` tenemos en cuenta lo siguiente
Se hace uso de una base de datos 

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/d15613e5-2fdb-42a9-9923-d3cae025a17c)

Al igual que una conexión para enlazar el programa a MySQL.

```java
  
  public class Conexion {
    Connection con;
    // Constantes para la URL de conexión, el usuario y la contraseña de la base de datos
    public static final String URL = "jdbc:mysql://localhost:3306/gimnasio3";
    public static final String USER = "root";
    public static final String CLAVE = "270404110408140211";

    // Método que intenta establecer una conexión con la base de datos
    public Connection conectar() {
        try {
            con = DriverManager.getConnection(URL, USER, CLAVE);
        } catch (SQLException e) {
            System.out.println("Error :" + e);
        }
        return con;
    }
}
```


Al igual que un metodo capaz de guardar estos datos dentro de esta base MySQL:
```java
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
```

### Vista
Para la parte de `Vista` lo siguiente es fundamental.

Se nos presenta una forma visualmente y a detalle de cada característica sobre algún objeto en este caso dicho de otra forma en un `cliente`.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/25d7e2b0-614b-42ed-9a75-cf02652a1387)

De la misma manera en un `Empleado` o `Administrador`, son mostrados de manera visual y a detalle en nuestro programa. Este mismo representa una interfaz de usuario y a su vez se encarga de mostrar los datos completos del modelo al usuario e interacciones del usuario.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/67e21261-ce00-42a0-981c-4e7cbd4ccc64)

### Controlador

Para la parte del “Controlador” tenemos mucho sobre eso, desde los métodos más simples a métodos complejos.

Plasmados en una serie de botones

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/8d910dcf-28e0-4075-aee5-0a1a8b1bb25c)

Para también después plasmar toda la información en una serie de graficas visuales:

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/a44ff962-1fac-47e3-b323-0b6a0f4c2611)


## Funcionamiento

Entramos al funcionamiento del programa., para ello se mostrará con capturas parte por parte lo esencial de este mismo. E indicando en que apartados se ocupa

Comenzamos con el hecho de que se tiene 3 frames muy importantes ya que sin ellos este programa no funcionaria.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/164ad50c-c701-49ec-b1a5-765c5edf2dfb)


También se tiene en cuenta las clases creadas en otros paquetes, pero dentro del mismo proyecto.
En nuestro caso tenemos una clase con la conexión de la base de datos y una clase con los métodos que se ocuparan en este programa.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/9a0c48a8-dbd4-4ea5-938a-2fc73c7acbc1)


Comenzamos de lleno con el frame de login.
Este frame es el inicio de todo lo que se verá más adelante, dentro de este se ocupa el método `ValidarUsuario` sobre el botón `login`

  ![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/dfa27815-09f7-4cc9-beca-6ed92ec03ea3)


Las primeras impresiones de este frame es que necesita de un correo y una contraseña única y dada por nuestra empresa, eso solamente si se es `Empleado` o `Administrador`.
Una validación muy agrable es el de los campos vacíos, el usuario no puede logearse sin haber requisitado cada espacio en blanco.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/d0d7d887-1327-4de4-9389-1c07572f5a9b)

En esta ocasión vamos a tomar algún `Administrador` de nuestra base de datos.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/62c35013-f5f3-46f8-9888-a75989e4305c)

Al igual su contraseña, que es generada de manera automática por un método llamado `GenerarContraseñaAleatoria`.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/4a8edd51-e8cc-48d5-b0a4-511c32c53446)


También hay que tener cuidado al momento de ingresar los datos para logearse ya que, si estos están mal, aparecerá un mensaje indicando el error.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/8cefef0d-050b-4f63-b0b7-5b0095a94b27)

Y este al instante setteara los campos en blanco

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/8fdb2308-4526-4418-b6e8-5839fd109279)


En esta ocasión nos logearemos con un perfil de `Administrador`, al momento de presionar al botón, se nos abrirá el frame indicado, gracias al metodo `ValidarUsuario`.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/70cd3ce8-957c-4776-84bc-45cc9434ad6c)

Mostrando así el frame de administrador u empleado dependiendo de quien haya iniciado sesión, mostrando sus datos y graficas en tiempo real sobre lo que sucede en dicho negocio, estas graficas fueron generadas gracias al método `GenerarGraficoPastel` y a `GenerarGraficoBarras`

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/1a312462-b7c2-461c-b96a-c3537d75acc5)


En caso de que este correo y contraseña sean de parte de un `Empleado` el frame indicado sera este:

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/9a8f82e0-01c5-40a4-8b42-e667581b278c)

Nota: Cabe recalcar que, para la lógica de nuestro negocio, el administrador siempre tendrá más opciones dentro del programa que un empleado *Asalariado*.

El administrador podrá ingresar empleados nuevos o administradores nuevos, gracias a los botones `Registrar` estos nuevos `empleados` o `administradores` serán agregados a la base de datos, también este botón es capaz de generar correos y contraseñas gracias al método `GenerarContraseñaAleatoria`. Estos correos y contraseñas que se les generan son para que despues ellos puedan logearse a este programa y tengan su respectivo rol.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/88180336-039f-44fe-8a3e-d68b728a5469)


También nos generara un pdf con los datos asociados a dicho administrador o empleado dependiendo de lo que se esté registrando. Para ello tenemos este ejemplo

Crearemos un nuevo administrador con estos datos

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/a1ece0fe-7cad-4959-bf39-a7307a2dae18)


Al momento de dar en el botón *Registrar* se nos mostrara las siguientes ventanas de texto. Esto gracias al método `GuardarDatosEmpleado` y también al método `GuardarDatosPersona`

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/afda6e10-4536-49b5-950d-9f3ff9e10362)


Podremos Guardar El Pdf dentro de nuestro equipo en el lugar que sea de mayor conveniencia, para hacer esto se hizo uso del método `CrearPDFAdmin `, dento de este se ocupa una librería llamada `FileChooser` la cual la implementamos y hace lo mostrado.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/80f6f9f6-9dc0-4165-bbca-017df84dd0de)


Una vez se haya encontrado la carpeta ideal, solo es cuestión de guardarlo.
Para después saltar de nuevo a nuestro programa con la siguiente ventana, esto es generado también por el método `GenerarPDFAdmin`

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/c75aea9d-5165-42c2-8d37-d6c2e3147d84)


Si se aceptar abrir el archivo se nos mostrara el siguiente PDF.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/a1fafb42-128a-43d2-b46c-6bf60f93416f)


Este procedimiento es básicamente lo mismo para `empleado` y `cliente`.

Agregamos un empleado Nuevo y su PDF es el siguiente.  Cabe recalcar que cada PDF según sea el tipo de usuario tendrá un asunto diferente.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/4e844463-d6f3-4bc1-ad4e-1e7015df2090)

Para la lógica dentro de un empleado que registra Únicamente clientes es la misma que un `Administrador` que registra clientes.

Para este caso, al ser un cliente tenemos un método `EnviarPDF`
Lo que hace este es enviar el PDF al correo que se nos proporcionó durante el registro.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/d89fd5a3-0d8c-4baf-806b-f775e927f0d2)

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/75c5980d-103d-4fcf-be46-9846e9559f3e)


Las membresías también son únicas para el `Administrador`, ya que ellos pueden ponerle el nombre y su debido precio según sus necesidades al igual que una descripción llamativa para los clientes que la requieran.
Se nos presentara en la tabla la membresia la cual hayamos registrado, nos contara las personas que la hayan contratado las membresias que se tienen a lo largo de su empresa, sacando de esta manera también los gráficos correspondientes.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/7580d839-8ed3-4c74-a0a9-c76f71570994)

Cabe recalcar que tenemos una opción muy útil, la cual es buscar tanto clientes, empleados, membresías y administradores, al igual que sucursales dentro de la tabla que se tiene.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/594f3dc9-6be8-4e78-8b0f-fc84bd304d0d)

Para el siguiente Panel, tenemos las sucursales que al igual que las membresías solo los administradores son capaces de modificar, agregar o eliminar.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/44ed547c-f740-44ee-85d3-2555b495f2af)


Finalmente, y no menos importante tenemos a los botones de:
Los paneles que nos hacen volver al inicio de sesión y/o salir del programa respectivamente

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/597bb666-6526-4e68-9351-11d565e42d3c)

# Autores

Daniel Eduardo Garcia Salvador    
@danielinh_0

Luis Gael Fernadez Crisanto       
@gaelf_04


# Agradecimientos
- A mi gato por acompañarme durante toda esta travesia.
- Y a mis ganas de seguir adelante
  







