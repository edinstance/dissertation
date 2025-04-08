
data "aws_region" "current" {}

# ECS Frontend Task Definition
resource "aws_ecs_task_definition" "frontend_task" {
  family                   = "${var.environment}-frontend-task"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  runtime_platform {
    operating_system_family = "LINUX"
    cpu_architecture        = "ARM64"
  }
  cpu                = "512"  # 0.5 vCPU
  memory             = "1024" # 1 GB
  execution_role_arn = var.ecs_task_execution_role_arn
  task_role_arn      = var.ecs_task_role_arn

  container_definitions = jsonencode([
    {
      name      = "${var.environment}-frontend-container"
      image     = "${var.frontend_ecr_repo}:${var.frontend_image_tag}"
      essential = true
      portMappings = [
        {
          containerPort = 3000
          hostPort      = 3000
          protocol      = "tcp"
        }
      ]
      healthCheck = {
        command = [
          "CMD-SHELL",
          "curl -f http://127.0.0.1:3000/api/health || exit 1"
        ],
        interval    = 30
        timeout     = 5
        retries     = 3
        startPeriod = 0
      }
      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = "/ecs/frontend"
          awslogs-region        = data.aws_region.current.name
          awslogs-stream-prefix = "ecs"
        }
      }
      secrets = [
        {
          name      = "NEXTAUTH_SECRET"
          valueFrom = var.nextauth_secret_arn
        },
        {
          name      = "NEXTAUTH_URL"
          valueFrom = var.nextauth_url_arn
        },
        {
          name      = "BACKEND_GRAPHQL_ENDPOINT"
          valueFrom = var.backend_graphql_endpoint_arn
        },
        {
          name      = "COGNITO_CLIENT_ID"
          valueFrom = var.frontend_cognito_client_id_arn
        },
        {
          name      = "COGNITO_USER_POOL_ID"
          valueFrom = var.cognito_user_pool_id_arn
        },
        {
          name      = "API_KEY"
          valueFrom = var.api_key_arn
        },
        {
          name      = "STRIPE_PUBLISHABLE_KEY"
          valueFrom = var.stripe_publishable_key_arn
        },
        {
          name      = "STRIPE_SECRET_KEY"
          valueFrom = var.stripe_secret_key_arn
        },
        {
          name      = "STRIPE_PRICE_ID"
          valueFrom = var.stripe_price_id_arn
        },
        {
          name      = "LAUNCHED"
          valueFrom = var.launched_arn
        },
        {
          name      = "RECAPTCHA_SITE_KEY"
          valueFrom = var.recaptcha_site_key_arn
        },
        {
          name      = "RECAPTCHA_SECRET_KEY"
          valueFrom = var.recaptcha_secret_key_arn
        },
        {
          name      = "SES_SENDER_EMAIL"
          valueFrom = var.ses_sender_email_arn
        },
        {
          name      = "SES_RECIPIENT_EMAIL"
          valueFrom = var.ses_recipient_email_arn
        },
        {
          name      = "SES_PRODUCTION"
          valueFrom = var.ses_production_arn
        }
      ]
    }
  ])
}


# ECS Backend Task Definition
resource "aws_ecs_task_definition" "backend_task" {
  family                   = "${var.environment}-backend-task"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  runtime_platform {
    operating_system_family = "LINUX"
    cpu_architecture        = "ARM64"
  }
  cpu                = "512"  # 0.5 vCPU
  memory             = "1024" # 1 GB
  execution_role_arn = var.ecs_task_execution_role_arn

  container_definitions = jsonencode([
    {
      name      = "${var.environment}-backend-container"
      image     = "${var.backend_ecr_repo}:${var.backend_image_tag}"
      essential = true
      portMappings = [
        {
          containerPort = 8080
          hostPort      = 8080
          protocol      = "tcp"
        }
      ]
      healthCheck = {
        command = [
          "CMD-SHELL",
          "curl -f http://127.0.0.1:8080/details/health || exit 1"
        ],
        interval    = 30
        timeout     = 5
        retries     = 3
        startPeriod = 0
      }
      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = "/ecs/backend"
          awslogs-region        = data.aws_region.current.name
          awslogs-stream-prefix = "ecs"
        }
      }
      secrets = [
        {
          name      = "SPRING_ACTIVE_PROFILE"
          valueFrom = var.spring_active_profile_arn
        },
        {
          name      = "COGNITO_JWT_URL"
          valueFrom = var.cognito_jwt_url_arn
        },
        {
          name      = "DATABASE_URL"
          valueFrom = var.database_url_arn
        },
        {
          name      = "POSTGRES_USER"
          valueFrom = var.postgres_user_arn
        },
        {
          name      = "POSTGRES_PASSWORD"
          valueFrom = var.postgres_password_arn
        },
        {
          name      = "REDIS_HOST"
          valueFrom = var.redis_host_arn
        },
        {
          name      = "REDIS_PORT"
          valueFrom = var.redis_port_arn
        },
        {
          name      = "API_KEY"
          valueFrom = var.api_key_arn
        },
        {
          name      = "JIRA_ACCESS_TOKEN"
          valueFrom = var.jira_access_token_arn
        },
        {
          name      = "JIRA_EMAIL"
          valueFrom = var.jira_email_arn
        },
        {
          name      = "JIRA_URL"
          valueFrom = var.jira_url_arn
        },
        {
          name      = "JIRA_PROJECT_KEY"
          valueFrom = var.jira_project_key_arn
        }
      ]
    }
  ])
}
