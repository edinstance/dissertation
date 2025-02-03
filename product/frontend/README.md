# Frontend Application

This is the frontend application for this final year project. Below are the steps for running it.

## Running

You can either run the application locally or using Docker:

### Locally

To run locally move into this directory and first build the application then run it.

```
npm run build
```

Then,

```
npm run start
```

### Docker

You can run the appication by using Docker. First you have to build the image and then run it.

Build the image using this command.

```
docker build -t final-project-frontend .
```

Then run it.

```
docker run final-project-frontend
```
