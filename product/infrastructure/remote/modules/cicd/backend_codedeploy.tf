# CodeDeploy Resources (Example - Customize based on your setup)
resource "aws_codedeploy_app" "backend_codedeploy_application" {
  compute_platform = "ECS"
  name             = "${var.environment}-backend-codedeploy-app"
}

resource "aws_codedeploy_deployment_group" "backend_codedeploy_deployment_group" {
  app_name               = aws_codedeploy_app.backend_codedeploy_application.name
  deployment_group_name  = "${var.environment}-backend-codedeploy-dg"
  service_role_arn       = var.codedeploy_service_role_arn
  deployment_config_name = "CodeDeployDefault.ECSCanary10Percent5Minutes"

  ecs_service {
    cluster_name = var.ecs_cluster_name
    service_name = var.backend_ecs_service_name
  }

  deployment_style {
    deployment_option = "WITH_TRAFFIC_CONTROL"
    deployment_type   = "BLUE_GREEN"
  }

  blue_green_deployment_config {
    deployment_ready_option {
      action_on_timeout = "CONTINUE_DEPLOYMENT"
    }

    terminate_blue_instances_on_deployment_success {
      action                           = "TERMINATE"
      termination_wait_time_in_minutes = 5
    }
  }

  load_balancer_info {
    target_group_pair_info {
      prod_traffic_route {
        listener_arns = [var.backend_alb_listener_arn]
      }

      target_group {
        name = var.backend_alb_target_group_blue_name
      }

      target_group {
        name = var.backend_alb_target_group_green_name
      }
    }
  }
}
