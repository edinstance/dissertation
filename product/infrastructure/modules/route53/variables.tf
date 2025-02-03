
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

variable "frontend_alb_zone_id" {
  description = "The frontend ALB zone id for Route 53"
  type        = string
}

variable "frontend_alb_dns_name" {
  description = "The frontend ALB DNS name for Route 53"
  type        = string
}
