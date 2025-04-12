
# Global variables
variable "environment" {
  description = "The environment to deploy (dev, test, prod)"
  type        = string

  validation {
    condition     = contains(["dev", "test", "prod"], var.environment)
    error_message = "The environment must be one of 'dev', 'test', or 'prod'."
  }
}

variable "availability_zones" {
  description = "The availability zones to deploy the application to"
  type        = list(string)

  default = ["eu-west-2a", "eu-west-2b", "eu-west-2c"]
}

variable "domain" {
  description = "The domain name (e.g. example.com)"
  type        = string
}

# Codebuild
variable "code_connect_src" {
  description = "The source provider for codeconnect, e.g., GitHub"
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

variable "gitlab_terraform_config" {
  description = "The GitLab Terraform config, it should contain the terraform address, lock address and unlock address"
  type = object({
    address        = string
    unlock_address = string
    lock_address   = string
  })
}


# ECS
variable "frontend_image_tag" {
  description = "The tag of the frontend Docker image to deploy"
  type        = string
  default     = "latest"
}

variable "backend_image_tag" {
  description = "The tag of the backend Docker image to deploy"
  type        = string
  default     = "latest"
}

# SSM
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

variable "spring_active_profile" {
  description = "The active profile for Spring"
  type        = string

  validation {
    condition     = contains(["dev", "test", "prod"], var.spring_active_profile)
    error_message = "The active profile must be one of 'dev', 'test', or 'prod'."
  }
}

variable "postgres_user" {
  description = "The username for the PostgreSQL database"
  type        = string
}

variable "postgres_password" {
  description = "The password for the PostgreSQL database"
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