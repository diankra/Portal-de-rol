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
  
  
![alt text] (https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Diagrama%20de%20navegaci%C3%B3n.jpg)


  ## Diagrama UML
  
  ![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/modelo%20de%20datos%20fase%201/DaD%20UML%20(v2).png)
  
  ## Diagrama Entidad/Relación
  
![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/DiagramaEntidadRelacion.jpg)

  ## Páginas diseñadas
  
    - Página principal:
    
![alt text](https://github.com/diankra/Portal-de-rol/blob/master/Capturas_pantallas/pagina_principal.png?raw=true)
    
    - Foro general
    
![alt text](https://github.com/diankra/Portal-de-rol/blob/master/Capturas_pantallas/foro_general.png?raw=true)
    
    - Hilo
    
![alt text](https://github.com/diankra/Portal-de-rol/blob/master/Capturas_pantallas/hilo_foro.png?raw=true)
    
    - Crear hilo
    
![alt text](https://github.com/diankra/Portal-de-rol/blob/master/Capturas_pantallas/crear_hilo.png?raw=true)
    
    - Escribir mensaje
    
![alt text](https://github.com/diankra/Portal-de-rol/blob/master/Capturas_pantallas/escribir_mensaje.png?raw=true)
    
    - Partidas públicas:
    
![alt text](https://github.com/diankra/Portal-de-rol/blob/master/Capturas_pantallas/partidas_publicas.png?raw=true)
    
    - Partida pública:
    
![alt text](https://github.com/diankra/Portal-de-rol/blob/master/Capturas_pantallas/partida_publica.png?raw=true)
    
    - Crear partida
    
![alt text](https://github.com/diankra/Portal-de-rol/blob/master/Capturas_pantallas/crear_partida.png?raw=true)
     
    -Partida privada:
    
![alt text](https://github.com/diankra/Portal-de-rol/blob/master/Capturas_pantallas/partida_privada.png?raw=true)
    
    - Escribir para una partida privada:
    
![alt text](https://github.com/diankra/Portal-de-rol/blob/master/Capturas_pantallas/mensaje_partida_privada.png?raw=true)
    
    - Crear fichas:
    
![alt text](https://github.com/diankra/Portal-de-rol/blob/master/Capturas_pantallas/crear_fichas.png?raw=true)
    
    - Crear ficha de personaje:
    
![alt text](https://github.com/diankra/Portal-de-rol/blob/master/Capturas_pantallas/ficha_heroe.png?raw=true)
    
    - Visualizar ficha de personaje:
    
![alt text](https://github.com/diankra/Portal-de-rol/blob/master/Capturas_pantallas/creada_ficha_heroe.png?raw=true)
    
    - Crear ficha de enemigo:
    
![alt text](https://github.com/diankra/Portal-de-rol/blob/master/Capturas_pantallas/ficha_enemigos.png?raw=true)
    
    - Visualizar ficha de enemigo:
    
![alt text](https://github.com/diankra/Portal-de-rol/blob/master/Capturas_pantallas/creada_ficha_enemigo.png?raw=true)
    
     - Crear ficha de habilidad:
     
![alt text](https://github.com/diankra/Portal-de-rol/blob/master/Capturas_pantallas/ficha_enemigos.png?raw=true)
    
    - Visualizar ficha de habilidad:
    
![alt text](https://github.com/diankra/Portal-de-rol/blob/master/Capturas_pantallas/creada_ficha_habilidad.png?raw=true)
    
     - Crear ficha de localización:
     
![alt text](https://github.com/diankra/Portal-de-rol/blob/master/Capturas_pantallas/ficha_localizaciones.png?raw=true)
    
    - Visualizar ficha de localización:
    
![alt text](https://github.com/diankra/Portal-de-rol/blob/master/Capturas_pantallas/creada_ficha_localizaciones.png?raw=true)

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
