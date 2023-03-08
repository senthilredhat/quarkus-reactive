## Database Setup

`docker run --rm=true --name postgres-quarkus-hibernate -d -e POSTGRES_USER=sarah -e POSTGRES_PASSWORD=connor -e POSTGRES_DB=mydatabase -p 5433:5432 postgres:13
`

Login to postgres 

`podman exec -it postgres-quarkus-hibernate psql mydatabase sarah`

Create User1

`CREATE USER user1 WITH PASSWORD 'user1' SUPERUSER;`

## Start the app
`./gradlew quarkusDev`


## Test GRPC Service
To call, use postman and then for the gRPC method CreateOrder you use this as the message body:

`
{
"customer_id": "Outbox +ve",
"order": {
"cost": 300,
"creation_time": {
"nanos": -1240372270,
"seconds": "286"
},
"customer": "mollit amet",
"items": [
{
"name": "consequat commodo in",
"quantity": 1,
"sku_id": "dolore nisi nostrud adipisicing exercitation",
"type": "nisi sit quis",
"cost": 200
},
{
"cost": 100,
"sku_id": "cillum reprehenderit in commodo",
"type": "sit in eiusmod",
"quantity": 1,
"name": "ullamco fugiat dolor"
}
],
"status": 1
}
}
`

## Change Credentials

- Create User in Postgres and drop the old user
  - `CREATE USER user2 WITH PASSWORD 'user2' SUPERUSER;`
  - `drop user user1;`
- Post new credentials to the app using the REST endpoint
  - `curl -v -X POST  -H 'Content-Type: application/json' http://localhost:8080/admin -d '{"password":"user2","user":"user2"}'`
- Validate the new credentials are available in the credentials manager
  - `curl http://localhost:8080/admin`

## Replicate the Issue
Access the Create Order gRPC service and notice we are getting an error. Quarkus is not calling the credentials manager to get the new credentials, it's using cached values. 