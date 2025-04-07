variable "environment" {
  description = "The environment to deploy (dev, test, prod)"
  type        = string

  validation {
    condition     = contains(["dev", "test", "prod"], var.environment)
    error_message = "The environment must be one of 'dev', 'test', or 'prod'."
  }
}

variable "codepipeline_iam_role_arn" {
  description = "The ARN of the IAM role for CodePipeline"
  type        = string
}

variable "codebuild_iam_role_arn" {
  description = "The ARN of the IAM role for CodeBuilde"
  type        = string
}