variable "environment" {
  description = "The environment to deploy (dev, test, prod)"
  type        = string

  validation {
    condition     = contains(["local", "dev", "test", "prod"], var.environment)
    error_message = "The environment must be one of 'dev', 'test', or 'prod'."
  }
}


variable "allowed_origins" {
  description = "The allowed origins for CORS configuration"
  type        = list(string)

  validation {
    condition     = length(var.allowed_origins) > 0
    error_message = "The allowed_origins variable must contain at least one origin."
  }
}