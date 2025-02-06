
# Create a Route 53 Hosted Zone 
resource "aws_route53_zone" "route_53_hosted_zone" {
  name = var.domain

  lifecycle {
    prevent_destroy = true
  }
}
