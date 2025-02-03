# This creates an Internet Gateway
resource "aws_internet_gateway" "igw" {
  vpc_id = aws_vpc.vpc.id
  tags = {
    Name = "${var.environment}-igw"
  }
}

# This creates an Elastic IP for each Availability Zone
resource "aws_eip" "elastic_ip" {
  domain = "vpc" 
  count = length(var.availability_zones)
}

# This creates a NAT Gateway in each Availability Zone
resource "aws_nat_gateway" "nat_gateway" {
  count = length(var.availability_zones)
  allocation_id = aws_eip.elastic_ip[count.index].id
  subnet_id = element(aws_subnet.public_subnet[*].id, count.index)

  tags = {
    Name = "${var.environment}-${element(var.availability_zones, count.index)}-nat-gateway"
  }
}