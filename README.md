# Portal-de-rol
Práctica para Desarrollo de Aplicaciones Distribuidas que consiste en la implementación de una página web que actúe como portal para que gente situada en distintos lugares pueda jugar partidas de rol.

## Descripción
La práctica estará enfocada a jugar partidas de rol mediante foro. Estas partidas podrán clasificarse como públicas, es decir, las puede ver todo el mundo que acceda a la página, tenga usuario o no; o privadas, de modo que solo las puedan ver las personas que estén participando en estas. 

El portal tiene un foro público y una sala de partidas. En la sala de partidas solo se jugarán partidas de rol y en el foro público se podrán publicar otro tipo de mensajes, como noticias, hilos de discusión sobre temas relacionados, búsqueda de participantes para partidas de rol, etcétera. 

## Funcionalidades
Las personas que accedan al portal sin tener un usuario creado podrán:
  - Crearse un perfil
  - Ver partidas públicas
  - Ver el foro público
  
Los usuarios podrán:
  - Configurar su perfil
  - Ver partidas públicas 
  - Ver las partidas privadas en las que participen ellos
  - Crear una partida como máster
  - Moderar las partidas en las que se actúe como máster
  - Unirse a una partida como jugador
  - Añadir y editar fichas de personaje
  - Publicar mensajes en sus partidas
  - Publicar mensajes en el foro general
  - Eliminar sus propios hilos del foro general
  - Editar fichas de partida (monstruos, objetos, lugares...)
  - Suscribirse a partidas públicas
  
Los administradores podrán:
  - Realizar todas las funciones de usuario
  - Borrar mensajes de otros usuarios
  - Administrar partidas de otros usuarios
  - Banear usuarios

  
## Entidades
  *ENTIDADES PRINCIPALES*
  
  - Usuario: Poseen nombre, correo y contraseña. Pueden ser administradores o no, en función del boolean is_Admin. Pueden crear fichas de jugador, crear hilos y mensajes y participar en partidas como jugadores o másters. Además, si es administrador, va ligado al Foro como admin., pudiendo editar y borrar hilos y mensajes.
  - Foro: En él se contienen Hilos y Partidas (un subtipo de estos), además de un Usuario que actuará como administrador.
  - Hilo: Tiene un título y está formado por Mensajes. Además, tiene un autor que es el Usuario que lo ha creado, el cual puede borrarlo.
  - Partida: Hereda de Hilo, pudiendo ser pública o privada (boolean Privada). Su autor actúa además como master, y tiene Usuarios como jugadores y Fichas de Mundo.
  - Mensaje: Es publicado por un Usuario en un Hilo y contiene texto, que puede ser modificado o borrado por su autor.
  - Ficha_Jugador: Hereda de Ficha (es una clase abstracta, pero no funciona como entidad en la BBDD) y tiene parámetros como nombre, raza, clase y tipo (jugador o personaje no jugable), además de un Usuario como autor.
  - Ficha_Mundo: También hereda de Ficha y tiene nombre, tipo y descripción. Forma parte de una Partida, ya que hace referencia a todos aquellos elementos propios de ella (enemigos, tesoros, localizaciones...)
    
  *RELACIONES DEFINIDAS COMO ENTIDAD EN UML*
  
  - Foro_Hilos (RELACIÓN): Indica que el Foro contiene una lista de Hilos, con propagación de borrado en cascada.
  - Foro_Partidas (RELACIÓN): Indica que el Foro contiene una lista de Partidas, con propagación de borrado en cascada.
  - Partida_Jugadores (RELACIÓN): Conecta los Usuarios y las Partidas en las que participan representando una relación bidireccional de tipo N-M, ya que un jugador puede estar en varias partidas y estas a su vez pueden contener varios jugadores.
  
## Funcionalidades de servicio interno
  - Almacenamiento y escalado de imágenes
  - Envío de mensajes a los usuarios con las actualizaciones de las partidas en las que participen o estén suscritos
  - Funcionalidades por determinar 
  
Tablero de Trello: https://trello.com/b/gaM8JvpQ/portal-de-rol-dad


# Fase 2


  ## Diagrama de navegación
  
  
  ![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Diagrama%20de%20navegaci%C3%B3n.jpg)
  


  ## Diagrama UML
  
 ![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/modelo%20de%20datos%20fase%201/DaD%20UML%20(v2).png)
  
  ## Diagrama Entidad/Relación
  
![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/DiagramaEntidadRelacion.jpg)

  ## Páginas diseñadas
  
  - Página principal: 
        Es la página a la que se accede al abrir el portal. Permite acceder al Foro General, ver la lista de Partidas públicas (como espectador), crear un nuevo Usuario o bien registrarse con uno ya existente, crear una Partida nueva como máster, acceder a una Partida en la que se participe como jugador (sea privada o pública) y entrar a la sección de creación de Fichas.
    
![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Capturas_pantallas/Foro/1%20-%20Principal.jpg)
    
 - Foro general: 
        En él se muestran los Hilos de mensajes existentes, además de dar la opción de crear uno nuevo.
    
![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Capturas_pantallas/Foro/2%20-%20Foro%20general.jpg)
    
 - Hilo: 
        En el Hilo se muestran todos los Mensajes que lo conformen (con su autor) y se da la opción de escribir un mensaje nuevo o bien borrar uno ya existente, lo cual solo puede hacer el propio autor.
    
![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Capturas_pantallas/Foro/18%20-%20Hilo.jpg)
    
 - Crear hilo: 
        Esta página permite crear un Hilo nuevo introduciendo su título y un Mensaje, que aparecerá como primer mensaje una vez creado.
    
![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Capturas_pantallas/Foro/16%20-%20Crear%20hilo.jpg)
    
 - Escribir mensaje: 
        Aquí se escribe el contenido del Mensaje, el cual se mostrará en el Hilo correspondiente una vez enviado.
    
![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Capturas_pantallas/Foro/20%20-%20Escribir%20mensaje.jpg)
    
 - Partidas públicas: 
        En esta sección aparece la lista de Partidas públicas, a las cuales se puede acceder como espectador.
    
![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Capturas_pantallas/Foro/3%20-%20Partidas%20publicas.jpg)
    
 - Partida pública:
        Aquí se pueden ver los mensajes que se publican en las partidas públicas. También es posible escribir mensajes, que aparecerán marcados como "Espectador".
    
![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Capturas_pantallas/Foro/3%20-%20Partidas%20publicas.jpg)
    
 - Crear partida:
        Esta página permite crear partidas nuevas. Se les asigna un nombre, se indica si son públicas o privadas, se añaden los nombres de los usuarios que se quieren incluir como participantes y, finalmente, se escribe una descripción que actuará como primer mensaje. 
    
![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Capturas_pantallas/Foro/6%20-%20Crear%20partida.jpg)
     
- Partida privada:
        Aquí se pueden ver los mensajes de una partida privada. Además de escribir mensajes, también permite añadir fichas a la partida, consultar las que ya se han incluido y acceder a la página de creación de fichas.
    
![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Capturas_pantallas/Foro/28%20-%20Partiva%20privada%20mensaje.jpg)
    
- Escribir para una partida privada:
        Esta página funciona como la página de escritura de mensajes del foro general y las partidas públicas. Además, se incluye la posibilidad de seleccionar uno de los personajes del jugador para que en el mensaje aparezca que habla su personaje.
    
![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Capturas_pantallas/Foro/29%20-%20Partida%20privada%20escribir%20mensaje.jpg)
    
- Crear fichas:
        En esta página se selecciona qué tipo de ficha se quiere crear. 
    
![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Capturas_pantallas/Foro/8%20-%20Crear%20ficha.jpg)
    
- Crear ficha de personaje:
        Aquí se pueden indicar las características que definirán al personaje
    
![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Capturas_pantallas/Foro/9%20-%20Fichas%20personajes.jpg)
    
- Visualizar ficha de personaje:
        En esta página podremos observar las características especificadas en la página anterior durante la creación del personaje.
    
![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Capturas_pantallas/Foro/22%20-%20Ficha%20jugador%20creada.jpg)
    
- Crear ficha de enemigo:
        En esta página podremos indicar las características que definirán a un enemigo.
    
![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Capturas_pantallas/Foro/10%20-%20Fichas%20enemigos.jpg)
    
- Visualizar ficha de enemigo:
        En esta página podremos observar las características especificadas en la página anterior durante la creación del enemigo.
    
![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Capturas_pantallas/Foro/23%20-%20Ficha%20enemigo%20creada.jpg)
    
- Crear ficha de habilidad:
        En esta página crearemos las habilidades de los personajes y monstruos, definiendo su origen, nombre y una descripción de uso y resultados de la misma.
     
![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Capturas_pantallas/Foro/12%20-%20Fichas%20habilidades.jpg)
    
- Visualizar ficha de habilidad:
        En esta página podremos observar las características especificadas en la página anterior durante la creación de la habilidad.
    
![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Capturas_pantallas/Foro/25%20-%20Ficha%20habilidad%20creada.jpg)
    
- Crear ficha de localización:
        En esta pagina crearemos las localizaciones, definiendo su origen, temperatura, nombre y una descripción.
     
![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Capturas_pantallas/Foro/13%20-%20Fichas%20localizaciones.jpg)
    
- Visualizar ficha de localización:
        En esta página podremos observar las características especificadas en la página anterior durante la creación de la localización.
    
![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Capturas_pantallas/Foro/26%20-%20Ficha%20localizacion%20creada.jpg)

# Fase 3



## Instrucciones de compilación en Spring:
Ejecutar el proyecto como "Maven build..."

En el apartado "Goals" escribir: clean package y marcar la casilla "Skip test"
Nos generará un .jar que será nuestra aplicación, posteriormente la trasladaremos a la carpeta de la maquina virtual.

## Instrucciones de virtualización con Vagrant y VirtualBox:
Lo primero es tener instalado tanto VirtualBox como vagrant, no entraremos en eso aquí.

Pasos para crear la Maquina virtual:
Crear la carpeta donde se guarde todo lo relacionado con la virtual machine.

Crear el vagrantfile con:
- vagrant init ubuntu/trusty32

Añadir la box y el ubuntu/trusty32 con:
- vagrant box add ubuntu/trusty32

La maquina virtual está creada, este proceso solo se debe realizar una vez si no se destruye la maquina.

Dentro del vagrantfile hay que descomentar las siguientes lineas:

- Linea 26
- Linea 31
- Linea 35
- Linea 40

Para levantar la maquina virtual:

- Vagrant up

Para acceder a la maquina:

- Vagrant ssh

Hemos accedido a la maquina, ahora hay que instalar Java y SQL antes de lanzar el portal.

Instalación Java

- sudo add-apt-repository ppa:openjdk-r/ppa
- sudo apt-get update
- sudo apt-get install openjdk-8-jre
- sudo update-alternatives --config java //Esta linea solo aplica en el caso de que caso de que hayas instalado otra versión de java.

Instalación SQL:

- sudo apt-get install mysql-server

Lanzar SQL:

- mysql -u root -p

La contraseña en este caso de prueba es 1234.

- Crear base de datos: CREATE DATABASE portalderol;

Con esto la base de datos estaría creada.

- Salir de SQL con exit.

Portal de rol:

- Lanzar todo el servicio interno en distintas cmds.
- Lanzar el portal de rol.

Acceder mediante navegador con la direccion IP:

https//192.168.33.10:8443

##Diagrama de clases

Se ha dividido el diagrama en cuatro partes para mejorar su inteligibilidad. Además, se ha utilizado el siguiente código de colores:
- Azul: ENTITY
- Morado: MAPPED SUPERCLASS
- Marrón: COMPONENT
- Amarillo: SERVICE
- Verde: CLASS (Sin anotaciones)
- Rojo: REPOSITORY (Interface)
- Blanco: CONFIGURATION
- Naranja: CONTROLLER
- Gris: REST CONTROLLER
- Celeste: TEMPLATES

1. Relaciones entre clases: Aquí se detallan las clases principales (Las entidades de JPA) y algunas clases adicionales relacionadas. Se detallan sus atributos y métodos notables, _omitiendo los constructores, getters y setters_, por simplicidad.

![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Diagramas%20de%20clases/DaD%20clases%201.png)

2. Repositorios y clases de seguridad: Aquí se muestran los repositorios (interfaces) conectados a la entidad que contienen. También se muestran ciertas clases relacionadas con la seguridad, la autenticación de usuarios y CSRF. _Las clases que ya aparecían en el diagrama 1 se muestran aquí sin detalles, solo en nombre_.

![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Diagramas%20de%20clases/DaD%20clases%202.png)

3. Controladores y plantillas: Aquí aparecen detallados los controllers, sus atributos, métodos y los repositorios y clases que utiliza. Además, se relaciona cada Controller con la lista de plantillas html que maneja. Se ha dividido el diagrama en dos imágenes debido a su gran tamaño.

![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Diagramas%20de%20clases/DaD%20clases%203_1.png)

![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Diagramas%20de%20clases/DaD%20clases%203_2.png)

4. Servicios REST y relación con la aplicación principal: Se muestran los RestController y clases adicionales de cada apiREST y se relacionan con el Controller del Portal de Rol que utiliza el servicio.

![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Diagramas%20de%20clases/DaD%20clases%20REST.png)


## Integrantes
  - Marta Sebastián Valverde
      - Correo: m.sebastianv.2016@alumnos.urjc.es
      - Github: https://github.com/diankra
  - Héctor Fernández Rubio
      - Correo: h.fernandezru@alumnos.urjc.es
      - Github: https://github.com/Dalgus
  - Javier Fernández Osuna
      - Correo: j.hernandez.2016@alumnos.urjc.es
      - Github: https://github.com/PandaJavi
