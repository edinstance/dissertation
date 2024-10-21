# Infrastructure 

This directory will contain all of the infrastrucure as code that will define the architecture and some of the services this project will use. 

## Linting 

To lint the infrastructure code, intall [TFLint](https://github.com/terraform-linters/tflint) and then navigate to this directory and run this command ```tflint --recursive```. The config for TFLint is set up in the [.tflint.hcl](.tflint.hcl) file. 