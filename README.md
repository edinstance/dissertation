# Final Year Project

It is split into two main sections, the [documents](./documents/) and the [product](./product/).

# Product

The product is comprised of the [frontend](./product/frontend/), [backend](./product/backend/), [CICD](./product/CICD/),  [Docker Containers](./product/Docker/) and the [Infrastructure](./product/infrastructure/). There are multiple ways of running this application but there are some required items. There is more detailed steps inside the respective directories but this is a higher level overview on how to run the application.

## Required

### Stripe
First you need to setup [Stripe](https://stripe.com/gb) so that you can accept payments from subscriptions as well as enable payments between users, and for this you need to sign up for Stripe connect. Then you need to create a subscription and get the priceId of it so that it can be used inside the application. 

### Cognito

Whether deploying with either method, Cognito must be setup, and for this you will need to create an AWS account and setup the correct IAM roles so that you can get AWS credentials with access to the resources needed to create the infrastructure. To setup Cognito, open an command line, ensure terraform is installed, setup your AWS access keys using the AWS CLi. Then navigate to the [remote infrastructure directory](./product/infrastructure/remote/) and run: ```terraform apply -target=module.cognito``` This will setup Cognito, then you can use the AWS console to get the required environment variables for it.

## Docker
To run the application using Docker, you first need to ensure Docker is installed on the platform that you are building the application on. If it is then you can proceed to setting it up. Open a Command line and navigate to the [docker directory](./product/docker/) in the product, then setup the environment variables by following the [README](./product/docker/README.md) and finally run the containers using: ```docker compose -f docker-compose-local.yml up -d``` Once this is done then to apply the local infrastructure go to the [local infrastructure directory](./product/infrastructure/local/) and run  ```terraform apply ``` like how it was done for setting up Cognito. After all of that the application will be ready.


## AWS
When deploying to AWS the steps are slightly different, it is recommended that you initially build the [Frontend](./product/frontend/) and [Backend](./product/backend/) Docker images, to do so go to their respective directories and follow the instructions. 

Next you want to build the infrastructure using terraform, to do so navigate to the [remote terraform directory](./product/infrastructure/remote/), setup the enviroment variables using the example files and the instrustions in the [README](./product/infrastructure/README.md), then setup the terraform backend to use GitLab terraform states as discussed above or use a different terraform backend, then run: ``` terraform apply ```

This will start to create the infrastructure, but there are some manual steps that need completing these are also contained in the main infrastructure [README](./product/infrastructure/README.md), first you need to complete the codeconnections authentication so that the CI/CD pipelines will be able to run correctly, then you need to enable production mode for SES. These steps both have guides from amazon for doing this. Finally you need to get the name servers of the Route53 hosted zone and set your domain to point at them. 

Another tip would be to push the Docker images to ECR with the latest tag before all of the deployment is completed, this will ensure the ECS services are failing to deploy while waiting for the CI/CD pipelines to finish as there will be an existing image in the repository. The steps for this will be inside ECR under view push commands. 

After the infrastructure is built and the CI/CD pipeline is completed the application will be ready to receive traffic.