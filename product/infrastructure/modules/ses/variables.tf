
variable "environment" {
  description = "The environment to deploy (dev, test, prod)"
  type        = string

  validation {
    condition     = contains(["dev", "test", "prod"], var.environment)
    error_message = "The environment must be one of 'dev', 'test', or 'prod'."
  }
}

variable "domain" {
  description = "The domain name (e.g., example.com)"
  type        = string
}

variable "aws_route53_zone_id" {
  description = "The Route 53 zone id"
  type        = string
}