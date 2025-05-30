# Backend

This directory will contain all the backend code for the project. It will include both the backend application as well as the data models. 


## Setting up Dynamodb

You can either run this application using hosted dynamodb or locally, to run it locally run the docker compose file and then follow the steps in the [infrastructure](../infrastructure/README.md) folder to set it up. Or you can follow the steps in the [infrastructure](../infrastructure/README.md) folder to deploy it to AWS, and if you do that you just need to set the enviroment variable to the correct host.

## Running the application

To run the application you can follow the steps in both the [application](./application/) and [database](./database/) directory for individual instructions. Or you can create a [.env](./.env) file based on the [.env.example](./.env.example) file and then run ```docker-compose -f docker-compose-backend.yml up```.