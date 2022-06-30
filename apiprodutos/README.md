:package: Products API

<b>Description:</b><br/>
API for Product management<br/>

<b>Swagger documentation at:</b><br/>
http://localhost:8082/swagger-ui.html

<b>RabbitMQ Implementation:</b><br/>
If the root folder docker compose is already up, messages will be exchanged with the Deliveries Api<br/>
<i>Messages received:</i> checks stock and decreases it<br/>
<i>Messages sent:</i> Updated Delivery Entity to be saved on the other API