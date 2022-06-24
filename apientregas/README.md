<h2>:shopping_cart::Delivery API</h2>

<b>Description:</b><br/>
API for delivery management, built with Kotlin and MongoDB

<b>Swagger documentation at:</b><br/>
http://localhost:8085/swagger-ui.html

<b>About the MongoDB</b><br/>
If you're using a Docker, these commands can be used to reproduce the og environment:<br/><br/>
<b>Mount MongoDB Docker Container</b><br/>
<i>
sudo docker run --name mongo_shop -d -p 27017:27017 -p 28017:28017 -e MONGO_INITDB_ROOTPASSWORD=admin mongo:latest <br/><br/>
sudo docker start mongo_shop<br/><br/>
</i>
<b>Set admin user:</b><br/>
<i>
sudo docker exec -it mongo_shop mongo -u "admin" -p "admin"
</i><br/><br/>
<b>Access Mongo Shell (through the Docker):</b><br/>
<i>
sudo docker exec -it mongo_shop bash<br/>
mongo mongo_shop<br/>
use test
</i>

<p>
<b>Useful Mongo commands:</b><br/>
<i>show collections</i><br/>
<i>show databases</i><br/>
<i>db.cl_entrega.find()</i><br/>
<i>db.createUser({user: "admin",pwd: "admin",roles:["readWrite", "dbAdmin"]});</i>
</p>