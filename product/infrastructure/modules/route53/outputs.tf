
output "certificate_arn" {
  value = aws_acm_certificate.acm_certificate.arn
}

output "zone_id" {
  value = aws_route53_zone.route_53_hosted_zone.zone_id
}
