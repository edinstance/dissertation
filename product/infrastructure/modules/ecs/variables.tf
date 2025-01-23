variable "environment" {
  description = "The environment to deploy (dev, test, prod)"
  type        = string

  validation {
    condition     = contains(["dev", "test", "prod"], var.environment)
    error_message = "The environment must be one of 'dev', 'test', or 'prod'."
  }
}

# Frontend
variable "frontend_image_tag" {
  description = "The tag of the frontend Docker image to deploy"
  type        = string
}

variable "frontend_ecr_repo" {
    description = "The URL of the frontend ECR repository"
    type        = string
}

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

variable "api_key_arn" {
  description = "The arn of the API key"
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
