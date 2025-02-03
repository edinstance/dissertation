

output "alb_zone_id" {
    value = aws_lb.frontend_alb.zone_id
}

output "alb_dns_name" {
    value = aws_lb.frontend_alb.dns_name
}