terraform {
  required_version = ">= 1.9"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = ">= 5.60.0"
    }
  }
}

data "aws_region" "current" {}

resource "aws_ses_domain_identity" "ses_domain" {
  domain = var.environment == "prod" ? var.domain : "${var.environment}.${var.domain}"
}

resource "aws_ses_domain_dkim" "ses_dkim" {
  domain = aws_ses_domain_identity.ses_domain.domain
}

resource "aws_ses_domain_mail_from" "ses_mail_from" {
  count            = var.domain != null ? 1 : 0
  domain           = aws_ses_domain_identity.ses_domain.domain
  mail_from_domain = var.environment == "prod" ? "mail.${var.domain}" : "mail.${var.environment}.${var.domain}"
}
