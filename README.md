



## Clases Utilizadas
`Clase Conexión`
La clase Conexión tiene como objetivo establecer una conexión con una base de datos MySQL y sus principales funciones y características:
Aquí tienes la descripción del método `conectar()` en el formato que necesitas para tu README de GitHub:

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
