
terraform {
  required_version = ">= 1.9"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = ">= 5.60.0"
    }
  }
  backend "http" {

  }
}

provider "aws" {
  region = "eu-west-2"

  profile = "default"

  default_tags {
    tags = {
      Environment = var.environment
      Project     = "SubShop"
    }
  }
}

data "aws_region" "current" {}

data "aws_caller_identity" "current" {}

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

  # DNS
  acm_certificate_arn = module.route53.certificate_arn

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
  launched_arn                   = module.ssm.launched_arn
  recaptcha_site_key_arn         = module.ssm.recaptcha_site_key_arn
  recaptcha_secret_key_arn       = module.ssm.recaptcha_secret_key_arn
  ses_sender_email_arn           = module.ssm.ses_sender_email_arn
  ses_recipient_email_arn        = module.ssm.ses_recipient_email_arn
  ses_production_arn             = module.ssm.ses_production_arn

  # Backend enviroment variables
  spring_active_profile_arn = module.ssm.spring_active_profile_arn
  cognito_jwt_url_arn       = module.ssm.cognito_jwt_url_arn
  database_url_arn          = module.ssm.database_url_arn
  postgres_user_arn         = module.ssm.postgres_user_arn
  postgres_password_arn     = module.ssm.postgres_password_arn
  redis_host_arn            = module.ssm.redis_host_arn
  redis_port_arn            = module.ssm.redis_port_arn
  jira_access_token_arn     = module.ssm.jira_access_token_arn
  jira_email_arn            = module.ssm.jira_email_arn
  jira_url_arn              = module.ssm.jira_url_arn
  jira_project_key_arn      = module.ssm.jira_project_key_arn
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

# Databases
module "rds" {
  source = "./modules/rds"

  environment        = var.environment
  availability_zones = var.availability_zones
  private_subnet_ids = module.networking.private_subnet_ids
  db_sg_id           = module.networking.db_sg_id
}

module "elasticache" {
  source = "./modules/elasticache"

  environment        = var.environment
  availability_zones = var.availability_zones
  private_subnet_ids = module.networking.private_subnet_ids
  redis_sg_id        = module.networking.redis_sg_id
}

module "dynamodb" {
  source = "../shared/dynamodb"

  environment = var.environment
}

# IAM
module "iam" {
  source = "./modules/iam"

  environment                 = var.environment
  cognito_user_pool_arn       = module.cognito.cognito_user_pool_arn
  admin_access_logs_table_arn = module.dynamodb.admin_access_logs_table_arn
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
  launched                   = var.launched
  recaptcha_site_key         = var.recaptcha_site_key
  recaptcha_secret_key       = var.recaptcha_secret_key
  ses_sender_email           = var.ses_sender_email
  ses_recipient_email        = var.ses_recipient_email
  ses_production             = var.ses_production

  # Backend
  spring_active_profile = var.spring_active_profile
  cognito_jwt_url       = "https://cognito-idp.${data.aws_region.current.name}.amazonaws.com/${module.cognito.cognito_user_pool_id}"
  database_url          = "jdbc:postgresql://${module.rds.database_url}/"
  postgres_user         = var.postgres_user
  postgres_password     = var.postgres_password
  redis_host            = module.elasticache.redis_host
  redis_port            = "6789"
  jira_access_token     = var.jira_access_token
  jira_email            = var.jira_email
  jira_url              = var.jira_url
  jira_project_key      = var.jira_project_key
}

module "ses" {
  source = "./modules/ses"

  environment         = var.environment
  domain              = var.domain
  aws_route53_zone_id = module.route53.zone_id
}

module "cicd" {
  source = "./modules/cicd"

  environment        = var.environment
  aws_account_id     = data.aws_caller_identity.current.account_id
  aws_region         = data.aws_region.current.name
  codeconnection_arn = aws_codeconnections_connection.codeconnection.arn

  codepipeline_iam_role_arn    = module.iam.codepipeline_role_arn
  codepipeline_artifact_bucket = module.s3.codepipeline_artifact_bucket_name

  codebuild_iam_role_arn = module.iam.codebuild_role_arn
  codebuild_src          = var.codebuild_src
  codebuild_src_url      = var.codebuild_src_url

  vpc_id = module.networking.vpc_id

  database_subnet_ids      = module.networking.private_subnet_ids
  database_codebuild_sg_id = module.networking.database_codebuild_sg_id
  rds_secrets_arn          = module.rds.rds_secrets_arn
}


module "s3" {
  source = "./modules/s3"

  environment               = var.environment
  codepipeline_iam_role_arn = module.iam.codepipeline_role_arn
  codebuild_iam_role_arn    = module.iam.codebuild_role_arn
}

# The aws_codeconnections_connection resource is created in the state PENDING. 
# Authentication with the connection provider must be completed in the AWS Console. 
# See the AWS documentation for details.
resource "aws_codeconnections_connection" "codeconnection" {
  name          = "code connection"
  provider_type = var.code_connect_src
}
