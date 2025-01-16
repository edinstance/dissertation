# Infrastructure 

This directory will contain all of the infrastrucure as code that will define the architecture and some of the services this project will use. 

### Create and destroy infrastructure manually

To deploy this infrastructure you can use the following [terraform](https://www.terraform.io) commands:

- First [install terraform](https://developer.hashicorp.com/terraform/install?product_intent=terraform) using that link.
- Then move to the directory of the service you want to deploy e.g. [cognito](./cognito/)
- Then validate the configuration using ```terraform validate```
- If it is successful then you can plan it using ```terraform plan```, this will show changes required by the current configuration.
- If you are happy with the plan you can create or update the infrastructure using ```terraform apply```.
- Finally if you want to destroy the infrastructure you can run ```terraform destroy```

## Linting 

To lint the infrastructure code, intall [TFLint](https://github.com/terraform-linters/tflint) and then navigate to this directory and run this command ```tflint --recursive```. The config for TFLint is set up in the [.tflint.hcl](.tflint.hcl) file. 

## Security Scaning

To run a security scan on the infrastructure code you can use [Trivy](https://trivy.dev). Once Trivy is installed navigate to this directory and run this command ```trivy config .```.