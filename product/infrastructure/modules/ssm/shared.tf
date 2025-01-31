resource "aws_ssm_parameter" "api_key" {
    name  = "/${var.environment}/backend/API_KEY"
    type  = "String"
    value = var.api_key
}