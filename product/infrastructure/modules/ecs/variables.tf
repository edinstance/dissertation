variable "environment" {
  description = "The environment to deploy (dev, test, prod)"
  type        = string

  validation {
    condition     = contains(["dev", "test", "prod"], var.environment)
    error_message = "The environment must be one of 'dev', 'test', or 'prod'."
  }
}


# Networking
variable "public_subnet_ids" {
  description = "The IDs of the public subnets"
  type        = list(string)
}

variable "private_subnet_ids" {
  description = "The IDs of the private subnets"
  type        = list(string)
}

variable "vpc_id" {
  description = "The ID of the VPC"
  type        = string
}

variable "frontend_alb_sg_id" {
  description = "The ID of the security group for the frontend ALB"
  type        = string
}

variable "frontend_sg_id" {
  description = "The ID of the security group for the ECS frontend"
  type        = string
}

# ECR
variable "frontend_image_tag" {
  description = "The tag of the frontend Docker image to deploy"
  type        = string
}

variable "frontend_ecr_repo" {
    description = "The URL of the frontend ECR repository"
    type        = string
}

variable "backend_image_tag" {
  description = "The tag of the backend Docker image to deploy"
  type        = string
}

variable "backend_ecr_repo" {
    description = "The URL of the backend ECR repository"
    type        = string
}


# Frontend environment variables
variable "ecs_task_execution_role_arn" {
  description = "The ARN of the ECS task execution role"
  type        = string
}

variable "nextauth_url_arn" {
  description = "The arn of the URL for NextAuth"
  type        = string
}

variable "nextauth_secret_arn" {
  description = "The arn of the secret for NextAuth"
  type        = string
}

variable "backend_graphql_endpoint_arn" {
  description = "The arn of the endpoint for the backend GraphQL"
  type        = string
}

variable "frontend_cognito_client_id_arn" {
  description = "The arn of the Cognito client ID"
  type        = string
}

variable "cognito_user_pool_id_arn" {
  description = "The arn of the Cognito user pool ID"
  type        = string
}

variable "stripe_publishable_key_arn" {
  description = "The arn of the Stripe publishable key"
  type        = string
}

variable "stripe_secret_key_arn" {
  description = "The arn of the Stripe secret key"
  type        = string
}

variable "stripe_price_id_arn" {
  description = "The arn of the Stripe price id"
  type        = string
}

# Backend environment variables
variable "spring_active_profile_arn" {
  description = "The active profile for Spring"
  type        = string
}

variable "cognito_jwt_url_arn" {
  description = "The URL for Cognito JWT"
  type        = string
}

variable "database_url_arn" {
  description = "The URL for the database"
  type        = string
}

variable "postgres_user_arn" {
  description = "The username for the PostgreSQL database"
  type        = string
}

variable "postgres_password_arn" {
  description = "The password for the PostgreSQL database"
  type        = string
}

variable "redis_host_arn" {
  description = "The host for the Redis database"
  type        = string
}

variable "redis_port_arn" {
  description = "The port for the Redis database"
  type        = string
}

# Shared
variable "api_key_arn" {
  description = "The arn of the API key"
  type        = string
}