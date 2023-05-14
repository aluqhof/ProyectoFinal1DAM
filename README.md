# ProyectoFinal1DAM - Entrega parcial 12/05

## Credenciales para probar las funcionalidades del sitio web
### Usuario
- nombre de usuario: user 
- password: 1234

### Administrador
- nombre de usuario: admin 
- password: admin

## Funcionalidades disponibles para poner a prueba
- CRUD de reservas: añadir, editar y eliminar reserva en el panel de administradores. 
- Ruta: http://localhost:9000/admin/reservas
- Se ha añadido seguridad:
    - El usuario registrado puede acceder a la página de inicio y reservar pistas mediante el botón "reservar una pista" o mediante el enlace: http://localhost:9000/reserva-pista
    - El usuario no registrado solo puede acceder a la página de inicio. Cuando intenta realizar una acción, como reservar una pista, se le redirige al formulario de inicio de sesión para que se identifique.
    - El administrador puede acceder a todas las rutas del sitio web, especialmente a las herramientas del panel de administradores, en el que solo se ha completado la sección de reservas por el momento.
