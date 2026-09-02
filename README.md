Justificacion de mi Stack tecnologico.

Estoy consciente de que Java es un lenguaje bastante verboso, con un tipado fuerte y muy estructurado, y que su curva de aprendizaje es un poco más lenta, sobre todo si se compara con frameworks enfocados en desarrollo rápido como Django para Python. Sin embargo, elegí irme por este camino porque esa misma rigurosidad me obliga a crear muy buenas bases técnicas, especialmente en programación orientada a objetos.
Además, implementar un framework como Spring Boot facilita muchísimo construir APIs REST, aplicaciones web y microservicios. Esto es gracias a que viene con muchas dependencias empaquetadas, como Spring Data JPA, la cual básicamente me da operaciones CRUD solo definiendo una interfaz, en combinación con Hibernate, que es un ORM que simplifica la interacción con la base de datos para evitar escribir código SQL manualmente, se obtiene un resultado muy potente y productivo.
Teniendo en cuenta que en la actualidad contamos con la inteligencia artificial, es más fácil poder entender todos los procesos que suceden “por debajo” o de forma automática, simplemente consultando de dónde salió ese código, cómo y por qué. Siento que, teniendo estas bases sólidas con Java, adaptarme el día de mañana a otros lenguajes más ligeros o modernos va a ser muchísimo más sencillo.
Por otro lado, algo muy importante y una realidad aquí en Chile, es que Java sigue siendo un gigante absoluto, sobre todo en sectores bien fuertes en el mercado como la banca y el retail. Considero que al construir un portafolio con estas tecnologías, me aseguro de estar practicando con las mismas herramientas que demandan las grandes empresas, lo que me da una expectativa muy real y positiva al momento de salir a buscar oportunidades laborales enfocadas en el backend.

Guia de prueba.

La siguiente guia se divide en 2 fases, la primera fase el registro y el inicio de sesion se hacen a nivel de explorador y la segunda fase para crear y modificar los proyectos se realizan a traves de la herramienta postman.

Fase 1: Pruebas desde el Navegador (Frontend)

Paso 1: Registrar un nuevo usuario

Abre tu navegador web (Chrome, Edge, etc.) y ve a http://localhost:8080/registro.
Llena el formulario con un correo de prueba (ej. admin@techsolutions.com) y una contraseña (ej. 123456).
Haz clic en "Registrar cuenta".
Resultado esperado: Verás el spinner de carga, luego la alerta verde de "¡Registrado!" y serás redirigido automáticamente a la pantalla de login. (Internamente, Spring Boot cifró tu clave con BCrypt y la guardó en MySQL).

Paso 2: Iniciar sesión y capturar el JWT

Estando en http://localhost:8080/login, ingresa las mismas credenciales que acabas de crear.
Haz clic en "Entrar".
Resultado esperado: Verás la alerta de "¡Bienvenido!".
Paso crítico (Obtener el token para Postman): Como el token se guardó oculto en el navegador, necesitamos extraerlo.
Presiona la tecla F12 (o clic derecho -> Inspeccionar) para abrir las Herramientas de Desarrollador.
Ve a la pestaña Application (Aplicación) o Storage (Almacenamiento).
En el menú lateral izquierdo, despliega Local Storage (Almacenamiento local) y haz clic en http://localhost:8080.
Verás una fila con la clave jwt_token. Haz doble clic en el valor (el texto largo que empieza con eyJhb...), cópialo completo y guárdalo

Fase 2: Pruebas desde Postman (Backend y Seguridad)

Ahora que "iniciaste sesión" y tienes tu credencial (el JWT), vamos a Postman a gestionar los proyectos.

Paso 3: Crear un Proyecto Nuevo (POST)

Abre Postman y crea una nueva petición.
Método: POST
URL: http://localhost:8080/api/proyectos
Autorización:
Ve a la pestaña Authorization.
Selecciona el Type Bearer Token.
En el campo "Token", pega el JWT exacto que copiaste del navegador.
Body:
Ve a la pestaña Body, selecciona raw y luego JSON.
Pega este contenido:
{
  "nombre": "Proyecto IPSS",
  "fechaInicio": "2026-09-01",
  "estado": "En Planificación",
  "responsable": "Boris Belmar",
  "monto": 5000.0,
  "createdBy": 1
}
Haz clic en Send.
Resultado esperado: Un código 200 OK. En la respuesta inferior verás el JSON de tu proyecto creado, y notarás que MySQL le asignó automáticamente un "id"

Paso 4: Actualizar el Proyecto (PUT)

En Postman, cambia la misma petición para actualizar el proyecto que acabas de crear (el que tiene ID 1).
Método: PUT
URL: http://localhost:8080/api/proyectos/1 (Nota el /1 al final).
Autorización: Mantén el mismo Bearer Token.
Body: Realizar cualquier cambio, para efectos de esta guia sugiero cambiarle el estado y el monto.
{
  "nombre": "Proyecto IPSS",
  "fechaInicio": "2026-09-01",
  "estado": "En Desarrollo",
  "responsable": "Boris Belmar",
  "monto": 8500.0,
  "createdBy": 1
}
Haz clic en Send.
Resultado esperado: Un código 200 OK. La respuesta te devolverá el objeto modificado, confirmando que los datos se actualizaron en la base de datos.

Paso extra de validación (Opcional): Para comprobar que la seguridad es impenetrable, ve a la pestaña Authorization en Postman, borra un par de letras del final del token o borrarlo por completo y dale a Send. Te devolverá automáticamente un 403 Forbidden, demostrando que nadie puede falsificar la firma
