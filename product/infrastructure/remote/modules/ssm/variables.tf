variable "environment" {
  description = "The environment to deploy (dev, test, prod)"
  type        = string

  validation {
    condition     = contains(["dev", "test", "prod"], var.environment)
    error_message = "The environment must be one of 'dev', 'test', or 'prod'."
  }
}

# Frontend
variable "nextauth_secret" {
  description = "The secret for NextAuth"
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

variable "launched" {
  description = "Whether the product has been launched"
  type        = string
}

variable "recaptcha_site_key" {
  description = "The Recaptcha site key"
  type        = string
}

variable "recaptcha_secret_key" {
  description = "The Recaptcha secret key"
  type        = string

}

variable "ses_sender_email" {
  description = "The sender email for SES"
  type        = string
}

variable "ses_recipient_email" {
  description = "The recipient email for SES"
  type        = string
}

variable "ses_production" {
  description = "Whether SES is in production"
  type        = bool
}

# Backend
variable "spring_active_profile" {
  description = "The active profile for Spring"
  type        = string

  validation {
    condition     = contains(["dev", "test", "prod"], var.spring_active_profile)
    error_message = "The active profile must be one of 'dev', 'test', or 'prod'."
  }
}

variable "cognito_jwt_url" {
  description = "The URL for Cognito JWT"
  type        = string
}

variable "database_url" {
  description = "The URL for the database"
  type        = string
}

variable "postgres_user" {
  description = "The user for the Postgres database"
  type        = string
}

variable "postgres_password" {
  description = "The password for the Postgres database"
  type        = string
}

variable "redis_host" {
  description = "The host for Redis"
  type        = string
}

variable "redis_port" {
  description = "The port for Redis"
  type        = string
}

variable "jira_access_token" {
  description = "The access token for Jira"
  type        = string
}

variable "jira_email" {
  description = "The email for Jira"
  type        = string
}

variable "jira_url" {
  description = "The URL for Jira"
  type        = string
}

variable "jira_project_key" {
  description = "The project key for Jira"
  type        = string
}

variable "is_chat_enabled" {
  description = "Whether chat is enabled"
  type        = bool
}

variable "open_ai_key" {
  description = "The OpenAI key"
  type        = string
}

variable "open_ai_project_id" {
  description = "The project ID for OpenAI"
  type        = string
}

variable "open_ai_organization_id" {
  description = "The organization ID for OpenAI"
  type        = string
}

# Shared
variable "api_key" {
  description = "The API key"
  type        = string
}

# CICD
variable "gitlab_terraform_config" {
  description = "The GitLab Terraform config, it should contain the terraform address, lock address and unlock address"
  type = object({
    address        = string
    unlock_address = string
    lock_address   = string
  })
}

variable "code_connect_src" {
  description = "The source for codeconnect"
  type        = string
}


# Global
variable "domain" {
  description = "The domain for the application"
  type        = string
}
