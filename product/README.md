# Product

This directory contains all of the code for the application, it is split up into [frontend](/product/frontend/), [backend](/product/backend/) and [infrastructure](/product/infrastructure/). Each of these sections will be further divided as needed, but this provides a good initial separation.

## Running

The frontend and backend can be ran individually and the steps to do so is in the individual directories. Or they can both be ran together by using docker-compose. To do this first create a .env file in [this](./docker/) directory based on the enviroment variables in this [example file](./docker/.env.example) or pass the same variables in using the -e flag in the next command. Then naviagte to this directory and run 
```
docker-compose -f docker/docker-compose-local.yml up
```

## Dependencies

### Stripe

The project uses [Stripe](https://stripe.com/gb) for payements and subsriptions, before running the application you need to set it up. 

Then create a product that is recurring for the subscription, then use the price_id of the product and use it in the frontend enviroment variables [here](./frontend/.env.example)

### AWS

This product is dependent on [AWS Cognito](https://docs.aws.amazon.com/cognito/latest/developerguide/what-is-amazon-cognito.html), to set it up you can follow this [README.md](./infrastructure/README.md) and there will be steps to set it up in the guide in my project.