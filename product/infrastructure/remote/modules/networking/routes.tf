# This creates a public route table for each AZ
resource "aws_route_table" "public" {
  count  = length(var.availability_zones)
  vpc_id = aws_vpc.vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.igw.id
  }

  tags = {
    Name = "${var.environment}-public-rt-${element(var.availability_zones, count.index)}"
  }
}

# Associate public subnets with their corresponding public route tables
resource "aws_route_table_association" "public" {
  count          = length(var.public_subnet_cidrs)
  subnet_id      = aws_subnet.public_subnet[count.index].id
  route_table_id = aws_route_table.public[count.index % length(var.availability_zones)].id
}

# This creates a private route table for each AZ
resource "aws_route_table" "private" {
  count  = length(var.availability_zones)
  vpc_id = aws_vpc.vpc.id

  tags = {
    Name = "${var.environment}-private-rt-${element(var.availability_zones, count.index)}"
  }
}

# Associate private subnets with their corresponding private route tables
resource "aws_route_table_association" "private" {
  count          = length(var.private_subnet_cidrs)
  subnet_id      = aws_subnet.private_subnet[count.index].id
  route_table_id = aws_route_table.private[count.index % length(var.availability_zones)].id
}

# Route traffic from private subnets to the corresponding NAT Gateway
resource "aws_route" "private_nat_routes" {
  count                  = length(var.private_subnet_cidrs)
  route_table_id         = aws_route_table.private[count.index % length(var.availability_zones)].id
  destination_cidr_block = "0.0.0.0/0"
  nat_gateway_id         = aws_nat_gateway.nat_gateway[count.index % length(var.availability_zones)].id

  depends_on = [aws_route_table_association.private]
}
