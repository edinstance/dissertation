# Apollo gateway

This is a gateway for routing requets to the backend services. This is currently not being used but when the backend is split into seperate services it will federate them.

## Running

### Locally 

To run this locally set create a [.env](.env) file based on the [.env.example](.env.example) and then run ```npm install``` and ```npm start```

### Docker

To run this with docker you need to build the container using ```docker build -t backend-gateway``` and then when running it you need to pass the enviroment variables using arguments like ```docker run -p 4000:4000 -e API_KEY="" -e ORIGIN_URL="" -e MAIN_SUBGRAPH_URL="" backend-gateway```. If you want the container to be running with local services you should use ```host.docker.internal``` but if it is running with other contianers use their container names.