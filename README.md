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
| `ActualizarCliente(String nombre, String apellido, int edad, String correo)` | Actualiza los datos de un cliente en las tablas `Persona` y `Cliente` en la base de datos. **Con los debidos métodos utilizados:** `conectar()` de `Conexion`, `prepareStatement(String sql)` de `Connection`, `setString(int parameterIndex, String x)`, `setInt(int parameterIndex, int x)` de `PreparedStatement`, `executeUpdate()`. |
| `generarContraseñaAleatoria` | Genera una contraseña aleatoria de una longitud especificada utilizando caracteres alfanuméricos. **Con los debidos métodos utilizados:**: `nextInt(int bound)` de `SecureRandom`: Para generar un número aleatorio dentro del rango de la longitud de la cadena de caracteres. |
| `validarUsuario(String correo, String contraseña)` | Verifica si el usuario con el correo y contraseña proporcionados es un administrador o un asalariado. Si las credenciales son correctas, abre la ventana correspondiente (`Admin` o `Empleado`) y pasa los datos del usuario. Utiliza la conexión de la clase `Conexion` y muestra mensajes de error si las credenciales son incorrectas o hay problemas con la conexión. |
| `GuardarDatosPersona(String Nombre, String A_Paterno, String A_Materno, String CURP, String Direccion, String Telefono, int Edad)` | Inserta una nueva persona en la tabla `Persona` con los datos proporcionados. Utiliza la conexión de la clase `Conexion` y muestra un mensaje de error si la inserción falla. |
| `obtenerUltimoIdMembresia()` | Obtiene el último `id_membresia` insertado en la tabla `membresia`. Utiliza la conexión de la clase `Conexion` y muestra un mensaje de error si la consulta falla. |
| `preguntarAbrirPDF(String filePath)` | Pregunta al usuario si desea abrir un archivo PDF en la ubicación especificada. Si el usuario acepta y el archivo existe, intenta abrir el archivo. Muestra mensajes de error si el archivo no existe o no se puede abrir. |
| `generarGraficoPastel()` | Genera un gráfico de pastel que muestra el porcentaje de clientes por edad utilizando `JFreeChart`. Utiliza la conexión de la clase `Conexion` para obtener los datos y muestra un mensaje de error si hay problemas con la consulta. |
| `generarGraficoBarras()` | Genera un gráfico de barras que muestra el número de personas con membresías utilizando `JFreeChart`. Utiliza la conexión de la clase `Conexion` para obtener los datos y muestra un mensaje de error si hay problemas con la consulta. |
| `enviarCorreoConPDF(String correoDestino, String archivoPDF)` | Envía un correo electrónico con un archivo PDF adjunto al correo destino proporcionado. Utiliza la configuración de SMTP para Gmail y muestra un mensaje de error si hay problemas con el envío del correo. |
| `crearPDF`, `crearPDF_EC`,  `crearPDFEmpleado`, `crearPDFEmpleado_ED`, `crearPDFAdministrador`, `crearPDFAdministrador_ED`             | Se encargan de generar un PDF de normal y de edicion de registro de cliente, Empleado, Administraador obteniendo información de la base de datos y formateando el contenido con la biblioteca iText. De `Conexion` Para obtener la conexión a la base de datos. `prepareStatement(String sql)` de `Connection`: Para preparar la consulta SQL. `JFileChooser`, `setDialogTitle(String title)`, `setSelectedFile(File file)`, `showSaveDialog(Component parent)`: Para seleccionar la ubicación de guardado del PDF. `PdfWriter`, `PdfDocument`, `Document`: Para escribir el PDF.`setTextAlignment(TextAlignment alignment)`: Para alinear el texto. `add(IBlockElement element)`: Para agregar elementos al documento PDF. `close()`: Para cerrar el documento. `enviarCorreoConPDF(String email, String filePath)`: Para enviar el PDF por correo (método externo). `preguntarAbrirPDF(String filePath)`: Para preguntar al usuario si desea abrir el PDF. Se hace uso del poliformismo ya que un mimo metodo se llama a diferentes lugares. |
| `GuardarCorreoYFechaCliente(int id_cliente, String correo_cliente, Date fecha_inscripcion)` | Inserta un nuevo cliente en la tabla Cliente con los datos proporcionados (id_cliente, correo_cliente, fecha_inscripcion). Utiliza la conexión de la clase Conexion y muestra un mensaje de error si la inserción falla. |
| `GuardarDatosEmpleado(int id_empleado, String correo_empleado, int Salario, String Horario, Date FechaContratacion)` | Inserta un nuevo empleado en la tabla Empleado con los datos proporcionados (id_empleado, correo_empleado, Salario, Horario, FechaContratacion). Utiliza la conexión de la clase Conexion y muestra un mensaje de error si la inserción falla. |
| `GuardarRolEmpleado(int id_asalariado, String contra_asa, String Rol)` | Inserta un nuevo asalariado en la tabla Asalariado con los datos proporcionados (id_asalariado, contra_asa, Rol). Utiliza la conexión de la clase Conexion y muestra un mensaje de error si la inserción falla. |
| `GuardarContraAdmin(int id_admin, String contra_admin)` | Inserta un nuevo administrador en la tabla Admin con los datos proporcionados (id_admin, contra_admin). Utiliza la conexión de la clase Conexion y muestra un mensaje de error si la inserción falla. |
| `guardarMembresia(String Nombre, String Precio, String Descripcion)` | Inserta una nueva membresía en la tabla `membresia` con los datos proporcionados (nombre, precio, descripcion). Utiliza la conexión de la clase `Conexion` y muestra un mensaje de error si la inserción falla. |
| `guardarSucursal(String nombre, String direccion, Date fecha)` | Inserta una nueva sucursal en la tabla `gimnasio` con los datos proporcionados (nombre, direccion, fecha_apertura). Utiliza la conexión de la clase `Conexion` y muestra un mensaje de error si la inserción falla. |
| `setMembresia(String n, int id)` | Busca una membresía por nombre en la tabla `membresia` y, si la encuentra, inserta una nueva membresía con los datos obtenidos y el `id` proporcionado en la tabla `membresia`. Utiliza la conexión de la clase `Conexion` y muestra un mensaje de error si la inserción falla. |
| `setGimnasio(String n, int id, Date fecha)` | Busca un gimnasio por nombre en la tabla `gimnasio` y, si lo encuentra, inserta un nuevo gimnasio con los datos obtenidos y el `id` proporcionado en la tabla `gimnasio`. Utiliza la conexión de la clase `Conexion` y muestra un mensaje de error si la inserción falla. |
| `obtenerUltimoIdMembresia()` | Obtiene el último `id_membresia` insertado en la tabla `membresia`. Utiliza la conexión de la clase `Conexion` y muestra un mensaje de error si la consulta falla. |
| `ActualizarCliente(int id_cliente, String Nombre, String A_Paterno, String A_Materno, String CURP, String Direccion, String Telefono, int Edad, String correo)` | Actualiza los datos del cliente en las tablas `Persona` y `Cliente` con los datos proporcionados. Utiliza la conexión de la clase `Conexion` y muestra un mensaje de error si la actualización falla. |
| `ActualizarMembresia(String NombreNuevoMembresia, int id_cliente, String NombreNuevoSucursal)` | Este método actualiza la membresía y sucursal del cliente. Se utiliza en el evento del botón Editar del CRUD para clientes. Actualiza los datos de la membresía y sucursal en base a consultas SQL. |
| `ActualizarFecha(int id_persona), public void ActualizarFechaEmpleados(int id_persona)` | Actualiza las fechas de inscripción y baja de un cliente o empleado. Se utiliza en el evento action performed del JmenuItem del PopUpClientes. Si la fecha de baja no es nula, se actualiza a nula y la fecha de inscripción a la fecha actual; en caso contrario, la fecha de baja se actualiza a la fecha actual. |
| `Connection obtenerConexion()` | Crea y obtiene una conexión a una base de datos. |
| `setGimnasioEmpleado(String n, int id, Date fecha)` | Coloca a un empleado en una sucursal al momento de registrarlo en el CRUD de empleados. Selecciona datos de la sucursal (nombre, dirección, fecha de apertura) y los inserta en la tabla de gimnasio junto con el ID del empleado. |
| `ActualizarEmpleado(int id_empleado, String Nombre, String A_Paterno, String A_Materno, String CURP, String Direccion, String Telefono, int Edad, int Salario, String Horario)` | Actualiza los datos personales y de trabajo de un empleado. Realiza un UPDATE en base al ID del empleado y los demás parámetros. |
| `cambiarContraseniaAdmin(int id_empleado, String contra)` | Cambia la contraseña del administrador en base a su ID. |
| `ActualizarSucursal(int id_empleado, String Nombre_Sucursal)` | Cambia la sucursal asociada a un empleado, actualizando el registro en la tabla Gimnasio con los nuevos datos. |
| `cambiarContraseniaEmpleado(int id_empleado, String contra, String rol)` | Cambia la contraseña y el rol de un empleado en base al ID del asalariado seleccionado. |
| ` Editar_MembresiasBD(String nombreAntiguo, String descripcionAntigua, int precioAntiguo, String nombre, int precio, String descripcion)` | Edita los registros de las membresías en la base de datos. Aplica "SET SQL_SAFE_UPDATES = 0" y "SET SQL_SAFE_UPDATES = 1" para setear los datos nuevos donde están los registros de los datos viejos. |
| `eliminarMembresias(String nombreMembresia)` | Elimina todos los registros de las membresías y sus clientes asociados. Borra en orden todos los registros de la base de datos: Gimnasio – Membresía – Cliente – Persona. |
| `Editar_SucursalesBD(String nombreAntiguo, String direccion_A, java.sql.Date fecha_A, String nombre_N, String direccion_N, java.sql.Date Fecha_N)` | Modifica los registros de las sucursales en la base de datos ocupando "SET SQL_SAFE_UPDATES = 0" y "SET SQL_SAFE_UPDATES = 1". Setea los datos nuevos donde haya registros de los datos viejos. |
| `ActualizarFechaSucursal(String nombreS)` | Actualiza las fechas de apertura y cierre de una sucursal en base al nombre. Si la fecha de cierre no es nula, se actualiza a nula y la fecha de apertura a la fecha actual; en caso contrario, la fecha de baja se actualiza a la fecha actual. |
| `public void eliminarSucursales(String nombreS)` | Elimina todos los registros asociados a una sucursal, incluyendo membresías, clientes y empleados. Sigue el orden: Gimnasio – Membresía – Cliente – Persona y Gimnasio – Asalariado | Admin – Empleado – Persona. |

### JFrame Login

| Elemento | Descripción |
| --- | --- |
| **Botón "Login" (`btnLogin`)** | Llama al método `btnLoginActionPerformed` para obtener el correo y contraseña del usuario, validar los campos y llamar al método `validarUsuario` de la clase `Metodos` para autenticar al usuario. Si los campos están vacíos, muestra un mensaje de error. |


### JFrame Empleado

Aqui se muestra una tabla detallada que incluye la descripción de los métodos proporcionados junto con los botones que interactúan con estos métodos en nuestra interfaz gráfica:

| Botón/Elemento               | Función                                                      | Código Asociado                                                                                       |
|------------------------------|--------------------------------------------------------------|-------------------------------------------------------------------------------------------------------|
| `TablaClientes`              | Seleccionar cliente y mostrar sus datos en los campos         | `fila = TablaClientes.getSelectedRow(); int id = Integer.parseInt((String) TablaClientes.getValueAt(fila, 0)); ` |
| `btnEditar`                  | Validar y actualizar datos del cliente seleccionado           | `String Nombre = txtNombre.getText().trim(); `             |
| `btnRegistro`                | Registrar un nuevo cliente con validaciones y asociaciones    | `String Nombre = txtNombre.getText().trim(); `                 |
| `btnEditar1`                 | Crear PDF con los datos del cliente seleccionado              | `Clases.Metodos objetoRegistro = new Clases.Metodos(); fila = TablaClientes.getSelectedRow(); int id = Integer.parseInt((String) TablaClientes.getValueAt(fila, 0)); ...` (creación de PDF) |
| `PLinicio`                   | Cambiar a la vista de inicio y actualizar estadísticas       | `pnVentanas.setSelectedIndex(0); lblTextoMorado.setText("Inicio"); Graficas(); clientesNuevosxMes(); IngresosTotales_UltimoMes(); promedio_edad();` |
| `PLusuario`                  | Cerrar la ventana actual y abrir la ventana de login          | `this.dispose(); login log = new login(); log.setVisible(true);`                                       |
| `PLsalir`                    | Confirmar y cerrar el programa                                | `int response = JOptionPane.showConfirmDialog(Empleado.this, "¿Seguro que deseas cerrar el programa?", "Confirmar Cierre", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE); if (response == JOptionPane.YES_OPTION) { this.dispose(); }` |


### Descripción de las funciones:

| Método | Descripción |
| --- | --- |
| **TablaClientesMouseClicked** | - Obtiene la fila seleccionada en la tabla `TablaClientes`. <br> - Realiza una consulta a la base de datos para obtener más detalles del cliente seleccionado. <br> - Muestra los datos obtenidos en varios campos de texto en la interfaz. |
| **btnEditarMouseClicked** | - Obtiene y valida los datos de los campos de texto. <br> - Actualiza los datos del cliente en la base de datos. <br> - Muestra un mensaje de éxito o error según el resultado de la operación. <br> - Crea un PDF con los datos actualizados del cliente. |
| **btnRegistroMouseClicked** | - Obtiene y valida los datos de los campos de texto. <br> - Registra un nuevo cliente en la base de datos. <br> - Asocia la membresía y sucursal al nuevo cliente. <br> - Muestra un mensaje de éxito o error según el resultado de la operación. <br> - Crea un PDF con los datos del nuevo cliente. |
| **btnEditar1MouseClicked** | - Obtiene el ID del cliente seleccionado en la tabla. <br> - Crea un PDF con los datos del cliente. |
| **PLinicioMouseClicked** | - Cambia la vista a la pestaña de inicio. <br> - Actualiza el texto de `lblTextoMorado` a "Inicio". <br> - Llama a varias funciones para actualizar las estadísticas en la vista. |
| **PLusuarioMouseClicked** | - Cierra la ventana actual. <br> - Crea y muestra una nueva instancia de la ventana de login. |
| **PLsalirMouseClicked** | - Muestra un cuadro de diálogo para confirmar la salida del programa. <br> - Si el usuario confirma, cierra el programa. |

Estas funciones están diseñadas para manejar la interacción del usuario con la interfaz de usuario (UI), asegurando que los datos se obtengan, validen, muestren y actualicen correctamente en la base de datos y la interfaz gráfica.


### Más metodos de JframeEmpleado

| Elemento | Descripción |
| --- | --- |
| **Botón "Mostrar Todas las Membresías"** | Llama al método `mostrarTodasM()` para llenar la tabla `TablaMembresias` con todas las membresías disponibles. |
| **Campo de Texto `txtBuscarM`** | Permite al usuario filtrar las membresías por nombre antes de llamar al método `mostrarTodasM()`. |
| **ComboBox `cboMembresias`** | Se llena con los nombres de las membresías cuando se llama a `seleccionarMembresias()`. |
| **Botón "Generar Gráficas"** | Llama al método `Graficas()` para generar y mostrar gráficos de pastel y barras en `jPanel3` y `jPanel6`. |
| **Etiqueta `lblCLN`** | Muestra el número de clientes nuevos del mes actual. Se actualiza con el método `clientesNuevosxMes()`. |
| **Botón "Actualizar Clientes Nuevos"** | Llama al método `clientesNuevosxMes()` para actualizar la información mostrada en `lblCLN`. |
| **Etiqueta `lblIngresos`** | Muestra los ingresos totales por membresías del último mes. Se actualiza con el método `IngresosTotales_UltimoMes()`. |
| **Botón "Actualizar Ingresos"** | Llama al método `IngresosTotales_UltimoMes()` para actualizar la información mostrada en `lblIngresos`. |
| **Etiqueta `lblPromedio`** | Muestra la edad promedio de los clientes activos. Se actualiza con el método `promedio_edad()`. |
| **Botón "Actualizar Promedio Edad"** | Llama al método `promedio_edad()` para actualizar la información mostrada en `lblPromedio`. |
| **ComboBox `cboSucursales`** | Se llena con los nombres de las sucursales activas (no cerradas) cuando se llama a `seleccionarSucursales()`. |


### Detalles de la Interfaz:

| **Método** | **Descripción** | **Botón/Componente Asociado** |
|------------|-----------------|-------------------------------|
| `mostrarTodasM()` | Muestra todas las membresías en una tabla, incluyendo el número, nombre, precio, descripción y cantidad de clientes asociados. Si hay un filtro de búsqueda, se aplica a la consulta. | **Botón**: "Mostrar Todas las Membresías" <br> **Campo de Texto**: `txtBuscarM` (para filtrar) <br> **Tabla**: `TablaMembresias` |
| `seleccionarMembresias()` | Llena un `JComboBox` con los nombres de todas las membresías distintas. | **ComboBox**: `cboMembresias` |
| `Graficas()` | Genera gráficos de pastel y de barras utilizando `JFreeChart` y los muestra en paneles de la interfaz gráfica. | **Botón**: "Generar Gráficas" <br> **Paneles**: `jPanel3`, `jPanel6` |
| `clientesNuevosxMes()` | Muestra la cantidad de nuevos clientes inscritos en el mes actual en una etiqueta (`lblCLN`). | **Etiqueta**: `lblCLN` <br> **Botón**: "Actualizar Clientes Nuevos" |
| `IngresosTotales_UltimoMes()` | Calcula y muestra los ingresos totales por membresías del último mes en una etiqueta (`lblIngresos`). | **Etiqueta**: `lblIngresos` <br> **Botón**: "Actualizar Ingresos" |
| `promedio_edad()` | Calcula y muestra la edad promedio de los clientes activos en una etiqueta (`lblPromedio`). | **Etiqueta**: `lblPromedio` <br> **Botón**: "Actualizar Promedio Edad" |
| `seleccionarSucursales()` | Llena un `JComboBox` con los nombres de todas las sucursales que no han sido cerradas. | **ComboBox**: `cboSucursales` |


Esta tabla proporciona una visión clara de cómo los métodos y los componentes de la interfaz de usuario (botones y otros elementos) están relacionados. 

### JFrame Administrador

| **Método** | **Descripción** | **Botón/Componente Asociado** |
|------------|-----------------|-------------------------------|
| `btnRegistroMMouseClicked` | Registra una nueva membresía con los datos proporcionados y actualiza la lista de membresías. | **Botón**: "Registrar Membresía" (`btnRegistroM`) <br> **Campos de Texto**: `txtNombreM`, `txtPrecioM`, `txtDescripcionM` |
| `TablaMembresiasMouseClicked` | Rellena los campos de texto con los datos de la membresía seleccionada en la tabla. | **Tabla**: `TablaMembresias` <br> **Campos de Texto**: `txtNombreM`, `txtPrecioM`, `txtDescripcionM` |
| `btnEliminarMouseClicked` | Elimina la membresía seleccionada en la tabla después de una confirmación del usuario. | **Botón**: "Eliminar Membresía" (`btnEliminar`) <br> **Tabla**: `TablaMembresias` |
| `btnEditarMMouseClicked` | Edita la membresía seleccionada en la tabla con los datos proporcionados y actualiza la lista de membresías. | **Botón**: "Editar Membresía" (`btnEditarM`) <br> **Tabla**: `TablaMembresias` <br> **Campos de Texto**: `txtNombreM`, `txtPrecioM`, `txtDescripcionM` |
| `btnRegistroGMouseClicked` | Registra una nueva sucursal con los datos proporcionados y actualiza la lista de sucursales. | **Botón**: "Registrar Sucursal" (`btnRegistroG`) <br> **Campos de Texto**: `txtNombreG`, `txtDireccionG` |
| `btnEditarGMouseClicked` | Edita la sucursal seleccionada en la tabla con los datos proporcionados y actualiza la lista de sucursales. | **Botón**: "Editar Sucursal" (`btnEditarG`) <br> **Tabla**: `TablaSucursales` <br> **Campos de Texto**: `txtNombreG`, `txtDireccionG` |
| `btnEliminarG1MouseClicked` | Elimina la sucursal seleccionada en la tabla después de una confirmación del usuario. | **Botón**: "Eliminar Sucursal" (`btnEliminarG1`) <br> **Tabla**: `TablaSucursales` |
| `AltaSActionPerformed` | Actualiza la fecha de alta de la sucursal seleccionada en la tabla y actualiza la lista de sucursales. | **Botón**: "Alta Sucursal" (`AltaS`) <br> **Tabla**: `TablaSucursales` |
| `btnEnviar_EMMouseClicked` | Genera un PDF con los datos del empleado seleccionado en la tabla. | **Botón**: "Generar PDF Empleado" (`btnEnviar_EM`) <br> **Tabla**: `TablaEmpleados` |
| `btnEditar1MouseClicked` | Genera un PDF con los datos del cliente seleccionado en la tabla. | **Botón**: "Generar PDF Cliente" (`btnEditar1`) <br> **Tabla**: `TablaClientes` |
| `btnEditarA1MouseClicked` | Genera un PDF con los datos del administrador seleccionado en la tabla. | **Botón**: "Generar PDF Administrador" (`btnEditarA1`) <br> **Tabla**: `TablaAdministradores` |
| `mostrarDatos` | Muestra los datos de los clientes en la tabla `TablaClientes`, aplicando un filtro si se proporciona un texto de búsqueda. | **Tabla**: `TablaClientes` <br> **Campo de Texto**: `txtBuscar` |

### Detalles de la Interfaz:

| Elemento | Descripción |
| --- | --- |
| `btnRegistroM` | Llama al método `btnRegistroMMouseClicked` para registrar una nueva membresía. |
| `btnEditarM` | Llama al método `btnEditarMMouseClicked` para editar la membresía seleccionada. |
| `btnEliminar` | Llama al método `btnEliminarMouseClicked` para eliminar la membresía seleccionada. |
| Tabla `TablaMembresias` | Al hacer clic en una fila, se llama al método `TablaMembresiasMouseClicked` para rellenar los campos de texto con los datos de la membresía seleccionada. |
| `btnRegistroG` | Llama al método `btnRegistroGMouseClicked` para registrar una nueva sucursal. |
| `btnEditarG` | Llama al método `btnEditarGMouseClicked` para editar la sucursal seleccionada. |
| `btnEliminarG1` | Llama al método `btnEliminarG1MouseClicked` para eliminar la sucursal seleccionada. |
| `AltaS` | Llama al método `AltaSActionPerformed` para actualizar la fecha de alta de la sucursal seleccionada. |
| Tabla `TablaSucursales` | Se utiliza para seleccionar la sucursal para edición o eliminación. |
| `btnEnviar_EM` | Llama al método `btnEnviar_EMMouseClicked` para generar un PDF del empleado seleccionado. |
| `btnEditar1` | Llama al método `btnEditar1MouseClicked` para generar un PDF del cliente seleccionado. |
| `btnEditarA1` | Llama al método `btnEditarA1MouseClicked` para generar un PDF del administrador seleccionado. |
| Tabla `TablaClientes` | Se utiliza para mostrar los datos de los clientes y aplica un filtro de búsqueda si se proporciona texto en `txtBuscar`. |

Esta tabla proporciona una visión clara de cómo los métodos y los componentes de la interfaz de usuario están relacionados.


## Adjunto tabla utlizada en MySQL para este proyecto

**Link De La Base De Datos**
https://drive.google.com/drive/folders/1zjAfUqpT_eQNNIVgnnhofl81WGQQpRUw?usp=sharing 



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
  







