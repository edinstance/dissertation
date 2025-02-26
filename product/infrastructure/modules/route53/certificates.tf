
resource "aws_acm_certificate" "acm_certificate" {
  domain_name       = var.environment == "prod" ? "${var.domain}" : "${var.environment}.${var.domain}"
  validation_method = "DNS"

  subject_alternative_names = [
    "www.${var.environment == "prod" ? "${var.domain}" : "${var.environment}.${var.domain}"}",
  ]

  lifecycle {
    create_before_destroy = true
  }
}


resource "aws_route53_record" "certificate_validation" {
  for_each = {
    for dvo in aws_acm_certificate.acm_certificate.domain_validation_options : dvo.domain_name => dvo
  }

  allow_overwrite = true
  name            = each.value.resource_record_name
  records         = [each.value.resource_record_value]
  ttl             = 60
  type            = each.value.resource_record_type
  zone_id         = aws_route53_zone.route_53_hosted_zone.zone_id
}

resource "aws_acm_certificate_validation" "certificate_validation" {
  certificate_arn         = aws_acm_certificate.acm_certificate.arn
  validation_record_fqdns = [for record in aws_route53_record.certificate_validation : record.fqdn]
}
