
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

# ECS
module "ecs" {
  source = "./modules/ecs"

  environment = var.environment
}

# Networking
module "networking" {
  source = "./modules/networking"

  environment         = var.environment
}

# SSM
module "ssm" {
  source = "./modules/ssm"

  environment = var.environment

  nextauth_url = var.nextauth_url
  nextauth_secret = var.nextauth_secret
  backend_graphql_endpoint = var.backend_graphql_endpoint
  frontend_cognito_client_id = module.cognito.frontend_client_id
  cognito_user_pool_id = module.cognito.cognito_user_pool_id
  api_key = var.api_key
  stripe_publishable_key = var.stripe_publishable_key
  stripe_secret_key = var.stripe_secret_key
  stripe_price_id = var.stripe_price_id
}