# ECS Task Definition
resource "aws_ecs_task_definition" "frontend_task" {
    family                   = "frontend"
    network_mode             = "awsvpc"
    requires_compatibilities = ["FARGATE"]
    cpu                      = "512"   # 0.5 vCPU
    memory                   = "1024"  # 1 GB
    execution_role_arn       = var.ecs_task_execution_role_arn

    container_definitions = jsonencode([
        {
            name        = "frontend"
            image       = "${var.frontend_ecr_repo}:${var.frontend_image_tag}"
            essential   = true
            portMappings = [
                {
                    containerPort = 3000
                    hostPort      = 3000
                    protocol      = "tcp"
                }
            ]
            secrets = [
                {
                    name  = "NEXTAUTH_SECRET"
                    valueFrom = var.nextauth_secret_arn
                },
                {
                    name  = "NEXTAUTH_URL"
                    valueFrom = var.nextauth_url_arn
                },
                {
                    name  = "BACKEND_GRAPHQL_ENDPOINT"
                    valueFrom = var.backend_graphql_endpoint_arn
                },
                {
                    name  = "COGNITO_CLIENT_ID"
                    valueFrom = var.frontend_cognito_client_id_arn
                },
                {
                    name  = "COGNITO_USER_POOL_ID"
                    valueFrom = var.cognito_user_pool_id_arn
                },
                {
                    name  = "API_KEY"
                    valueFrom = var.api_key_arn
                },
                {
                    name  = "STRIPE_PUBLISHABLE_KEY"
                    valueFrom = var.stripe_publishable_key_arn
                },
                {
                    name  = "STRIPE_SECRET_KEY"
                    valueFrom = var.stripe_secret_key_arn
                },
                {
                    name  = "STRIPE_PRICE_ID"
                    valueFrom = var.stripe_price_id_arn
                }
            ]
        }
    ])
}
