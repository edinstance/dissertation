
# Output VPC ID
output "vpc_id" {
  value = aws_vpc.vpc.id
}

# Output all public subnet IDs
output "public_subnet_ids" {
  value = aws_subnet.public_subnet[*].id
}

# Output all private subnet IDs
output "private_subnet_ids" {
  value = aws_subnet.private_subnet[*].id
}

output "frontend_alb_sg_id" {
  value = aws_security_group.frontend_alb_sg.id
}

output "backend_alb_sg_id" {
  value = aws_security_group.backend_alb_sg.id
}

output "frontend_sg_id" {
  value = aws_security_group.frontend_sg.id
}

output "backend_sg_id" {
  value = aws_security_group.backend_sg.id
}

output "db_sg_id" {
  value = aws_security_group.database_sg.id
}

output "redis_sg_id" {
  value = aws_security_group.redis_sg.id
}

output "database_codebuild_sg_id" {
  value = aws_security_group.codebuild_database_sg.id
}