# Infrastructure 

This directory will contain all of the infrastrucure as code that will define the architecture and some of the services this project will use. 

## Local and remote deployments

This project uses gitlab as the backend as well as local state files for managing local infrastructure for testing. The [shared directory](./shared/) contains all the infrastructure that is shared between the local and remote modules.

### Remote

All the IAC in the [remote directory](./remote/) is infrastructure that will be deployed to AWS and the state stored in gitlab, there is more information about deploying this in the [readme](./remote/README.md).

### Local

All the IAC in the [local directory](./local/) is infrastructure that can be deployed locally for testing and development.

### Shared

All the IAC in the [shared directory](./shared/) is infrastructure that can be deployed to AWS and locally.

## Code connect

The aws_codeconnections_connection resource is created in the state PENDING. Authentication with the connection provider must be completed in the AWS Console. See the AWS documentation for details.

## DNS

Once the Route53 hosted zone is setup, get the nameservers for the domain and then set your custom domain to use them.

## SES

You need to enable production mode for SES, for more information look [here](./modules/ses/README.md).

### Enviroment variables

You can either set the enviroment variables throught the command line or you can use the ```example.tfvars``` files to create variable files without the example in them and terraform will use the values from them instead. The variable files should end with ```.auto.tfvars``` so that they are automattically picked up by terraform when applying state.

### Create and destroy infrastructure manually

To deploy this infrastructure you can use the following [terraform](https://www.terraform.io) commands:

- First [install terraform](https://developer.hashicorp.com/terraform/install?product_intent=terraform) using that link.
- Then move to the base directory.
- Then validate the configuration using ```terraform validate```
- If it is successful then you can plan it using ```terraform plan```, this will show changes required by the current configuration.
- If you are happy with the plan you can create or update the infrastructure using ```terraform apply```.
- Finally if you want to destroy the infrastructure you can run ```terraform destroy```

If you want to only deploy certain moduels you can use ```--target module.example```.

## Linting 

To lint the infrastructure code, intall [TFLint](https://github.com/terraform-linters/tflint) and then navigate to this directory and run this command ```tflint --recursive```. The config for TFLint is set up in the [.tflint.hcl](.tflint.hcl) file. 

## Security Scaning

To run a security scan on the infrastructure code you can use [Trivy](https://trivy.dev). Once Trivy is installed navigate to this directory and run this command ```trivy config .```.