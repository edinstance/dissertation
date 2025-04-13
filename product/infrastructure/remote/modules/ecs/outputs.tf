

output "alb_zone_id" {
  value = aws_lb.frontend_alb.zone_id
}

output "alb_dns_name" {
  value = aws_lb.frontend_alb.dns_name
}

output "frontend_ecs_service_name" {
  value = aws_ecs_service.frontend_service.name
}

output "frontend_alb_listener_arn" {
  value = aws_lb_listener.frontend_https.arn
}

output "frontend_alb_target_group_blue_name" {
  value = aws_lb_target_group.alb_frontend_tg_blue.name
}

output "frontend_alb_target_group_green_name" {
  value = aws_lb_target_group.alb_frontend_tg_green.name
}

output "backend_ecs_service_name" {
  value = aws_ecs_service.backend_service.name
}

output "backend_alb_listener_arn" {
  value = aws_lb_listener.backend_http.arn
}

output "backend_alb_target_group_blue_name" {
  value = aws_lb_target_group.alb_backend_tg_blue.name
}

output "backend_alb_target_group_green_name" {
  value = aws_lb_target_group.alb_backend_tg_green.name
}

output "apollo_gateway_ecs_service_name" {
  value = aws_ecs_service.apollo_gateway_service.name
}

output "apollo_gateway_alb_listener_arn" {
  value = aws_lb_listener.backend_http.arn
}

output "apollo_gateway_alb_target_group_blue_name" {
  value = aws_lb_target_group.alb_apollo_gatway_tg_blue.name
}

output "apollo_gateway_alb_target_group_green_name" {
  value = aws_lb_target_group.alb_apollo_gatway_tg_green.name
}

output "ecs_cluster_name" {
  value = aws_ecs_cluster.cluster.name
}
