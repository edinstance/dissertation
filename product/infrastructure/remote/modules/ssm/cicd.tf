resource "aws_ssm_parameter" "gitlab_terraform_config" {
  name  = "/${var.environment}/cicd/GITLAB_TERRAFORM_CONFIG"
  type  = "String"
  value = jsonencode(var.gitlab_terraform_config)
}

resource "aws_ssm_parameter" "code_connect_src" {
  name  = "/${var.environment}/cicd/CODE_CONNECT_SRC"
  type  = "String"
  value = var.code_connect_src
}