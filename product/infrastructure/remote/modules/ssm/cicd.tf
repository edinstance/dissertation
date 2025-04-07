resource "aws_ssm_parameter" "gitlab_terraform_config" {
  name  = "/${var.environment}/cicd/GITLAB_TERRAFORM_CONFIG"
  type  = "String"
  value = jsonencode(var.gitlab_terraform_config)
}
