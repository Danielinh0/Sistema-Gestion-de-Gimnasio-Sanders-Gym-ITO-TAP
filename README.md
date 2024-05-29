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

### Modelo Vista Controlador

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


### Funcionamiento

Entramos al funcionamiento del programa., para ello se mostrará con capturas parte por parte lo esencial de este mismo. E indicando en que apartados se ocupa

Comenzamos con el hecho de que se tiene 3 frames muy importantes ya que sin ellos este programa no funcionaria.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/164ad50c-c701-49ec-b1a5-765c5edf2dfb)


También se tiene en cuenta las clases creadas en otros paquetes, pero dentro del mismo proyecto.
En nuestro caso tenemos una clase con la conexión de la base de datos y una clase con los métodos que se ocuparan en este programa.

![image](https://github.com/Danielinh0/Sistema-Gestion-de-Gimnasio-Sanders-Gym-ITO-TAP/assets/168355421/9a0c48a8-dbd4-4ea5-938a-2fc73c7acbc1)


Comenzamos de lleno con el frame de login.
Este frame es el inicio de todo lo que se verá más adelante, dentro de este se ocupa el método `ValidarUsuario` sobre el botón `login `






















