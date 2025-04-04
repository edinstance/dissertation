variable "codebuild_iam_role_arn" {
  description = "The name of the IAM role for CodeBuild"
  type        = string
}

variable "codebuild_src" {
  description = "The source provider for CodeBuild, e.g., GITHUB"
  type        = string
}

variable "codebuild_src_url" {
  description = "The source url for CodeBuild"
  type        = string
}


variable "vpc_id" {
  description = "The ID of the VPC"
  type        = string
}

variable "database_subnet_ids" {
  description = "The IDs of the subnets for the database"
  type        = list(string)
}


variable "database_codebuild_sg_id" {
    description = "The ID of the security group for the database CodeBuild"
    type        = string
}

variable "rds_secrets_arn" {
  description = "The ARN of the RDS secrets"
  type        = string
}