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
  - Usuario: Los usuarios podrán ser jugadores o másters dependiendo de la partida que jueguen. Dispondrán de la lista de partidas en las que estén participando junto con el rol que desempeñan en cada una de ellas y la ficha de personaje asociada, en caso de haberla. También tendrán un histórico de partidas jugadas.
  - (Hereda de Usuario) Administrador: Tipo de usuario especial que además de todas las funciones básicas puede editar y eliminar mensajes en el foro y banear usuarios.
  - Foro: Estará formado por hilos que podrá iniciar cualquier usuario y que podrán contener respuestas en forma de mensajes. Estos hilos podrán ser cerrados o eliminados por su creador o un administrador
  - (Hereda de Foro) Partida: Las partidas serán creadas por un usuario que actuará como máster. Este será el responsable de las condiciones de la partida (número de jugadores, fichas necesarias, duración... y actuará como moderador de su propia partida). La partida no comienza hasta que no lo determine el máster, y podrá ser cerrada por el mismo o un administrador. 
  - Mensaje: Es publicado por un usuario en el foro o en una partida y contiene texto, que puede ser modificado o borrado por su autor o por un Administrador.
  - Fichas: Hay dos tipos de fichas, las fichas de personaje y las fichas de partida. Las fichas de personaje las crearán los jugadores para una o varias partidas en las que participen. Las fichas de partida las crearán los másters para las partidas que dirijan y engloban elementos propios de estas, como monstruos, ubicaciones...
  
## Funcionalidades de servicio interno
  - Almacenamiento y escalado de imágenes
  - Envío de mensajes a los usuarios con las actualizaciones de las partidas en las que participen o estén suscritos
  - Funcionalidades por determinar 
  
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

Tablero de Trello: https://trello.com/b/gaM8JvpQ/portal-de-rol-dad


## Fase 1


  - Diagrama de navegación
  
  
    ![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/Diagrama%20de%20navegaci%C3%B3n.jpg)


  - Diagrama UML
  
    ![alt text](https://raw.githubusercontent.com/diankra/Portal-de-rol/master/modelo%20de%20datos%20fase%201/dad_uml_fase1.png)
  
    


