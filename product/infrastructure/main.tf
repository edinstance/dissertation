
terraform {
  required_version = ">= 1.9"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = ">= 5.60.0"
    }
  }
}

provider "aws" {
  region  = "eu-west-2"
  profile = "default"
}


module "frontend_ecr" {
  source = "./modules/ecr"

  name   = "${var.environment}-frontend-ecr"
}

module "backend_ecr" {
  source = "./modules/ecr"

  name   = "${var.environment}-frontend-ecr"
}