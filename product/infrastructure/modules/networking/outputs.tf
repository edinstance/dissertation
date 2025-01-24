
# Output all public subnet IDs
output "public_subnet_ids" {
  value = aws_subnet.public_subnet[*].id
}

# Output all private subnet IDs
output "private_subnet_ids" {
  value = aws_subnet.private_subnet[*].id
}