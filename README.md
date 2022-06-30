<h2>:cyclone: Kotlin Microservices Shop</h2>

<b>Description:</b><br/>
Microservices Shop composed with Kotlin Rest APIs in Spring Framework.<br/>
Databases used are MongoDB and MySQL

<b>Swagger documentation of each at:</b><br/>
usuários: http://localhost:8081/swagger-ui.html <br/>
produtos: http://localhost:8082/swagger-ui.html <br/>
cep: http://localhost:8083/swagger-ui.html <br/>
compras: http://localhost:8084/swagger-ui.html <br/>
entregas: http://localhost:8085/swagger-ui.html <br/>


<b>About RabbitMQ</b><br/>
Setting up RabbitMQ requires Docker Compose:

Intstalling it:<br/>
<i>sudo apt-get update</i><br/>
<i>sudo apt-get install docker-compose</i><br/>

Version verify:<br/>
<i>docker-compose -v</i><br/>

Inside the "/docker" folder exists the docker-compose.yml file.<br/>
When inside it, run the command:<br/>
<i>sudo docker-compose up -d</i><br/>

Accessing RabbitMq:<br/>
http://localhost:15672/ <br/>
credentials:<br/>
admin <br/>
123456