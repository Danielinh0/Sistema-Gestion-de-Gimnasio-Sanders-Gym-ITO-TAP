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
`Clase Conexión`
La clase Conexión tiene como objetivo establecer una conexión con una base de datos MySQL y sus principales funciones y características:

| Método                     | Descripción                                                                                                     |
|----------------------------|-----------------------------------------------------------------------------------------------------------------|
| `conectar()`               | Intenta establecer una conexión con la base de datos utilizando los valores de `URL`, `USER` y `CLAVE`. Si la conexión es exitosa, se asigna al atributo `con`. Si ocurre un error, se imprime un mensaje con los detalles del error. Finalmente, el método devuelve el objeto `Connection`. |


`Clase métodos`
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

## Instalación y Uso

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









