# Database

## How to Run

This project can be ran in two ways, either locally or using docker. 

### Locally

- First create a PostgreSQL database and insure liquibase is installed.
- Then set the enviroment variables in the [.env](.env) file, and example of what is needed is in the [.env.example](.env.example) file.
- Finally run this liquibase command to update the database 
```
liquibase --url=jdbc:postgresql://localhost:5432/${POSTGRES_DB} --username=${POSTGRES_USER} --password=${POSTGRES_PASSWORD} --changelog-file=/changelogs/changelog-master.xml update
```

### Docker 

To run the project using docker you need to: 

- Set the enviroment variables in the [.env](.env) file, and example of what is needed is in the [.env.example](.env.example) file.
- Then run this docker command
```
docker-compose up -d
```