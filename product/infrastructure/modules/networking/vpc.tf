
# TODO: Consider adding VPC Flow Logs (MEDIUM Security Risk)
# https://avd.aquasec.com/misconfig/avd-aws-0178


# This creates a VPC with CIDR block
resource "aws_vpc" "vpc" {
    cidr_block = var.vpc_cidr
    tags = {
        Name = "${var.environment}-vpc"
    }

    enable_dns_hostnames = true
}



