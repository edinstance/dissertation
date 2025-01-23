variable "environment" {
  description = "The environment to deploy (dev, test, prod)"
  type        = string

  validation {
    condition     = contains(["dev", "test", "prod"], var.environment)
    error_message = "The environment must be one of 'dev', 'test', or 'prod'."
  }
}

variable "nextauth_secret" {
    description = "The secret for NextAuth"
    type        = string
}

variable "nextauth_url" {
    description = "The URL for NextAuth"
    type        = string
}

variable "backend_graphql_endpoint" {
    description = "The endpoint for the backend GraphQL"
    type        = string
}

variable "frontend_cognito_client_id" {
    description = "The Cognito client ID"
    type        = string
}

variable "cognito_user_pool_id" {
    description = "The Cognito user pool ID"
    type        = string
}

variable "api_key" {
    description = "The API key"
    type        = string
}

variable "stripe_publishable_key" {
    description = "The Stripe publishable key"
    type        = string
}

variable "stripe_secret_key" {
    description = "The Stripe secret key"
    type        = string
}

variable "stripe_price_id" {
    description = "The Stripe price ID"
    type        = string
}