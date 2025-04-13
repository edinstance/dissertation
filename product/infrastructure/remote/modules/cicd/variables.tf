variable "environment" {
  description = "The environment to deploy (dev, test, prod)"
  type        = string

  validation {
    condition     = contains(["dev", "test", "prod"], var.environment)
    error_message = "The environment must be one of 'dev', 'test', or 'prod'."
  }
}

variable "aws_region" {
  description = "The AWS region to deploy to"
  type        = string
}

variable "aws_account_id" {
  description = "The AWS account ID"
  type        = string
}

variable "codeconnection_arn" {
  description = "The ARN of the codeconnection"
  type        = string
}

# CodeDeploy
variable "codedeploy_service_role_arn" {
  description = "The ARN of the IAM role for CodeDeploy"
  type        = string
}

variable "frontend_ecs_service_name" {
  description = "The name of the ECS service for the frontend"
  type        = string
}

variable "frontend_alb_listener_arn" {
  description = "The ARN of the ALB listener for the frontend"
  type        = string
}

variable "frontend_alb_target_group_blue_name" {
  description = "The name of the blue target group for the frontend ALB"
  type        = string
}

variable "frontend_alb_target_group_green_name" {
  description = "The name of the green target group for the frontend ALB"
  type        = string
}

variable "backend_ecs_service_name" {
  description = "The name of the ECS service for the backend"
  type        = string
}

variable "backend_alb_listener_arn" {
  description = "The ARN of the ALB listener for the backend"
  type        = string
}

variable "backend_alb_target_group_blue_name" {
  description = "The name of the blue target group for the backend ALB"
  type        = string
}

variable "backend_alb_target_group_green_name" {
  description = "The name of the green target group for the backend ALB"
  type        = string
}


variable "apollo_gateway_ecs_service_name" {
  description = "The name of the ECS service for the Apollo Gateway"
  type        = string
}

variable "apollo_gateway_alb_listener_arn" {
  description = "The ARN of the ALB listener for the apollo gateway"
  type        = string
}

variable "apollo_gateway_alb_target_group_blue_name" {
  description = "The name of the blue target group for the apollo gateway ALB"
  type        = string
}

variable "apollo_gateway_alb_target_group_green_name" {
  description = "The name of the green target group for the apollo gateway ALB"
  type        = string
}

variable "ecs_cluster_name" {
  description = "The name of the ECS cluster"
  type        = string
}

# CodePipeline
variable "codepipeline_iam_role_arn" {
  description = "The ARN of the IAM role for CodePipeline"
  type        = string
}

variable "codepipeline_artifact_bucket" {
  description = "The name of the S3 bucket for CodePipeline artifacts"
  type        = string
}

# CodeBuild
variable "codebuild_iam_role_arn" {
  description = "The name of the IAM role for CodeBuild"
  type        = string
}

# Networking
variable "vpc_id" {
  description = "The ID of the VPC"
  type        = string
}

variable "database_subnet_ids" {
  description = "The IDs of the subnets for the database"
  type        = list(string)
}


variable "database_codebuild_sg_id" {
  description = "The ID of the security group for the database CodeBuild"
  type        = string
}

# Secrets
variable "rds_secrets_arn" {
  description = "The ARN of the RDS secrets"
  type        = string
}

variable "gitlab_user" {
  description = "The GitLab user for CI/CD pipeline"
  type        = string
}

variable "gitlab_token" {
  description = "The GitLab token for CI/CD pipeline"
  type        = string
}

# SSM
variable "gitlab_terraform_config_arn" {
  description = "The ARN of the SSM parameter for GitLab Terraform config"
  type        = string
}
