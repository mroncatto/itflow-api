<!-- PROJECT SHIELDS -->
[![Tags][tag-shield]][tag-url]
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]

<!-- PROJECT LOGO -->

<p align="center">
    <img src="https://user-images.githubusercontent.com/31529014/126075479-e6d1340d-6119-4348-b454-9bdf4cf6f662.png" alt="Logo" width="440" height="130"> 


<h3 align="center">Gestión de TI [ API ]</h3>

  <p align="center">
    Gestión de servicios de tecnologías de la información
    <br />
    <a href="https://github.com/mroncatto/itflow-api"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/mroncatto/itflow-api">View Demo</a>
    ·
    <a href="https://github.com/mroncatto/itflow-api/issues">Report Bug</a>
    ·
    <a href="https://github.com/mroncatto/itflow-api/issues">Request Feature</a>
  </p>
</p>



<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Tabla de contenido</summary>
  <ol>
    <li>
      <a href="#sobre-el-proyecto">Sobre el proyecto</a>
      <ul>
        <li><a href="#construido-con">Construido con</a></li>
      </ul>
    </li>
    <li>
      <a href="#empezando">Empezando</a>
      <ul>
        <li><a href="#requisitos">Requisitos</a></li>
        <li><a href="#instalacion">Instalación</a></li>
      </ul>
    </li>
    <li><a href="#uso">Uso</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contribuyendo">Contribuyendo</a></li>
    <li><a href="#licencia">Licencia</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## Sobre el proyecto
Este proyecto se trata de una aplicación **Helpdesk** que facilita la gestión de procesos y servicios de una empresa o sector de tecnología de la información.

Este helpdesk se concentra en control de tickets e inventario de activos donde estos dos modulos se entrelazan para proveer muchas funcionalidades:
```md
- Helpdesk
   - Tickets
   - Tareas
   - Agenda
   - Chat
   - Equipos y Grupos
   - Empresas y Sectores
   - Portal de Usuarios
- Inventario
   - Activos
      - Red
         - Switch
         - Router
         - Patch Panel
         - Rack
      - Energia
         - Nobreak
         - Puntos de energia
      - Sofware
         - Licencias / Distribución
      - Server
         - Puertos
         - Servicios
         - Datacenter
```
### Construido con

Esta API utiliza las siguientes tecnologias:
<br />
<img align="left" alt="Java" src="https://img.icons8.com/color/48/000000/java-coffee-cup-logo--v1.png"/>
<img align="left" alt="Spring" src="https://img.icons8.com/color/48/000000/spring-logo.png"/>
<img align="left" alt="PostgreSQL" src="https://img.icons8.com/color/48/000000/postgreesql.png" />
<img align="left" alt="Docker" src="https://img.icons8.com/fluent/48/000000/docker.png"/>
<img align="left" alt="AWS" src="https://img.icons8.com/color/48/000000/amazon-web-services.png" style="background:white"/>
<br />
<br />
<!-- GETTING STARTED -->
### Empezando

Esta API funciona en conjunto con [itflow-web](`https://github.com/mroncatto/itflow-web`)

### Requisitos

* Docker o Postgres instalado

### Instalación

1. Clona el repo
   ```sh
   git clone https://github.com/mroncatto/itflow-api
   ```
2. Realiza ajustes a (`application-dev.yml`) si necesario
3. Levanta el servicio con Eclipse o IntelliJ IDE

<!-- USAGE EXAMPLES -->
## Uso

- Utiliza [Postman](https://www.postman.com/downloads/) u otro servicio para manipular [metodos HTTP](https://www.w3schools.com/tags/ref_httpmethods.asp)
- Abre la documentación (`http://localhost:8080/`)


<!-- ROADMAP -->
## Roadmap

Hecha un vistazo en el [Proyecto](https://github.com/users/mroncatto/projects/8) para ver la lista de features propuestas e issues existente.



<!-- CONTRIBUTING -->
## Contribuyendo

Las contribuciones son lo que hacen que la comunidad de código abierto sea un lugar tan increíble para aprender, inspirar y crear. Cualquier contribución que haga es **muy apreciada**.

1. Realiza un Fork del Project
2. Crea tu Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Comita tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Realiza un Push de la branch (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request



<!-- LICENSE -->
## Licencia

Distribuido bajo la licencia MIT. Hecha un vistazo en [Licencia MIT](https://github.com/mroncatto/itflow-api/blob/master/LICENSE) para obtener más información.






<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/mroncatto/itflow-api?style=for-the-badge
[contributors-url]: https://github.com/mroncatto/itflow-api/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/mroncatto/itflow-api.svg?style=for-the-badge
[forks-url]: https://github.com/mroncatto/itflow-api/network/members
[tag-shield]: https://img.shields.io/github/v/tag/mroncatto/itflow-api?style=for-the-badge
[tag-url]: https://github.com/mroncatto/itflow-api/tags
[issues-shield]: https://img.shields.io/github/issues/mroncatto/itflow-api.svg?style=for-the-badge
[issues-url]: https://github.com/mroncatto/itflow-api/issues
[license-shield]: https://img.shields.io/github/license/mroncatto/itflow-api?style=for-the-badge
[license-url]: https://github.com/mroncatto/itflow-api/blob/main/LICENSE.TXT?style=for-the-badge
