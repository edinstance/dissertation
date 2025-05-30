resource "aws_ssm_parameter" "domain" {
  name  = "/${var.environment}/global/domain"
  type  = "String"
  value = var.domain
}
