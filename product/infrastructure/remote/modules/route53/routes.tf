
# Record to the alb
resource "aws_route53_record" "route_53_alb_record" {
  zone_id = aws_route53_zone.route_53_hosted_zone.zone_id
  name    = var.environment == "prod" ? "${var.domain}" : "${var.environment}.${var.domain}"
  type    = "A"

  alias {
    name                   = var.frontend_alb_dns_name
    zone_id                = var.frontend_alb_zone_id
    evaluate_target_health = true
  }
}
