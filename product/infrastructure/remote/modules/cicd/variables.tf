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

variable "codebuild_src" {
  description = "The source provider for CodeBuild, e.g., GITHUB"
  type        = string
}

variable "codebuild_src_url" {
  description = "The source url for CodeBuild"
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