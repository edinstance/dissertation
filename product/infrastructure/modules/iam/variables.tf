variable "environment" {
  description = "The environment to deploy (dev, test, prod)"
  type        = string

  validation {
    condition     = contains(["dev", "test", "prod"], var.environment)
    error_message = "The environment must be one of 'dev', 'test', or 'prod'."
  }
}

variable "cognito_user_pool_arn" {
  description = "The arn of the Cognito user pool"
  type        = string
}

variable "admin_access_logs_table_arn" {
  description = "The ARN of the DynamoDB table for admin access logs"
  type        = string
}