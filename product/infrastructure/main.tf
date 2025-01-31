
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

data "aws_region" "current" {}

# Cognito
module "cognito" {
  source = "./modules/cognito"

  environment = var.environment
}

# ECR
module "frontend_ecr" {
  source = "./modules/ecr"

  name = "${var.environment}-frontend-ecr"
}

module "backend_ecr" {
  source = "./modules/ecr"

  name = "${var.environment}-backend-ecr"
}

# ECS
module "ecs" {
  source = "./modules/ecs"

  # Environment
  environment = var.environment

  # ECR
  frontend_ecr_repo  = module.frontend_ecr.repository_url
  backend_ecr_repo   = module.backend_ecr.repository_url
  frontend_image_tag = var.frontend_image_tag
  backend_image_tag  = var.backend_image_tag

  # IAM
  ecs_task_execution_role_arn = module.iam.ecs_task_execution_role_arn
  ecs_task_role_arn           = module.iam.ecs_task_role_arn

  # Networking
  public_subnet_ids  = module.networking.public_subnet_ids
  private_subnet_ids = module.networking.private_subnet_ids
  vpc_id             = module.networking.vpc_id
  frontend_alb_sg_id = module.networking.frontend_alb_sg_id
  backend_alb_sg_id  = module.networking.backend_alb_sg_id
  frontend_sg_id     = module.networking.frontend_sg_id
  backend_sg_id      = module.networking.backend_sg_id

  # Frontend enviroment variables
  nextauth_url_arn               = module.ssm.nextauth_url_arn
  nextauth_secret_arn            = module.ssm.nextauth_secret_arn
  backend_graphql_endpoint_arn   = module.ssm.backend_graphql_endpoint_arn
  frontend_cognito_client_id_arn = module.ssm.frontend_cognito_client_id_arn
  cognito_user_pool_id_arn       = module.ssm.cognito_user_pool_id_arn
  api_key_arn                    = module.ssm.api_key_arn
  stripe_publishable_key_arn     = module.ssm.stripe_publishable_key_arn
  stripe_secret_key_arn          = module.ssm.stripe_secret_key_arn
  stripe_price_id_arn            = module.ssm.stripe_price_id_arn

  # Backend enviroment variables
  spring_active_profile_arn = module.ssm.spring_active_profile_arn
  cognito_jwt_url_arn       = module.ssm.cognito_jwt_url_arn
  database_url_arn          = module.ssm.database_url_arn
  postgres_user_arn         = module.ssm.postgres_user_arn
  postgres_password_arn     = module.ssm.postgres_password_arn
  redis_host_arn            = module.ssm.redis_host_arn
  redis_port_arn            = module.ssm.redis_port_arn
}

# Networking
module "networking" {
  source = "./modules/networking"

  environment        = var.environment
  availability_zones = var.availability_zones
}

module "route53" {
  source = "./modules/route53"

  environment           = var.environment
  domain                = var.domain
  frontend_alb_zone_id  = module.ecs.alb_zone_id
  frontend_alb_dns_name = module.ecs.alb_dns_name

}

module "database" {
  source = "./modules/database"

  environment        = var.environment
  availability_zones = var.availability_zones
  private_subnet_ids = module.networking.private_subnet_ids
  db_sg_id           = module.networking.db_sg_id
  redis_sg_id        = module.networking.redis_sg_id

}

# IAM
module "iam" {
  source = "./modules/iam"

  cognito_user_pool_arn = module.cognito.cognito_user_pool_arn
}

# SSM
module "ssm" {
  source = "./modules/ssm"

  # Shared
  environment = var.environment
  api_key     = var.api_key

  # Frontend
  nextauth_url               = var.nextauth_url
  nextauth_secret            = var.nextauth_secret
  backend_graphql_endpoint   = var.backend_graphql_endpoint
  frontend_cognito_client_id = module.cognito.frontend_client_id
  cognito_user_pool_id       = module.cognito.cognito_user_pool_id
  stripe_publishable_key     = var.stripe_publishable_key
  stripe_secret_key          = var.stripe_secret_key
  stripe_price_id            = var.stripe_price_id

  # Backend
  spring_active_profile = var.spring_active_profile
  cognito_jwt_url       = "https://cognito-idp.${data.aws_region.current.name}.amazonaws.com/${module.cognito.cognito_user_pool_id}"
  database_url          = "jdbc:postgresql://${module.database.database_url}/"
  postgres_user         = var.postgres_user
  postgres_password     = var.postgres_password
  redis_host            = module.database.redis_host
  redis_port            = "6789"
}