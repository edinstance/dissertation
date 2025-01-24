
resource "aws_security_group" "alb_ecs_sg" {
    vpc_id = aws_vpc.vpc.id

    ## Allow inbound on port 80 from internet (all traffic)
    ingress {
        protocol         = "tcp"
        from_port        = 80
        to_port          = 80
        cidr_blocks      = ["0.0.0.0/0"]
    }

    ## Allow outbound to the private subnet
    egress {
        protocol         = "tcp"
        from_port        = 0
        to_port          = 0
        cidr_blocks      = var.private_subnet_cidrs
    }
}