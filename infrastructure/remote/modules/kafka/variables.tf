variable "environment" {
  description = "The environment to deploy (dev, test, prod)"
  type        = string

  validation {
    condition     = contains(["dev", "test", "prod"], var.environment)
    error_message = "The environment must be one of 'dev', 'test', or 'prod'."
  }
}

variable "subnet_ids" {
  description = "List of subnet IDs for the Kafka cluster"
  type        = list(string)
}

variable "security_group_id" {
  description = "Security group ID for the Kafka cluster"
  type        = string

}