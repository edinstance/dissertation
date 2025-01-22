
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

# Cognito
module "cognito" {
  source = "./modules/cognito"

  environment = var.environment
}

# ECR
module "frontend_ecr" {
  source = "./modules/ecr"

  name   = "${var.environment}-frontend-ecr"
}

module "backend_ecr" {
  source = "./modules/ecr"

  name   = "${var.environment}-backend-ecr"
}

# Networking
module "networking" {
  source = "./modules/networking"

  environment         = var.environment
}