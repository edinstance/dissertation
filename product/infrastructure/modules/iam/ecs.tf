
# IAM Role for ECS Task Execution
resource "aws_iam_role" "ecs_task_execution_role" {
  name = "ECSTaskExecutionRole"

  assume_role_policy = jsonencode({
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "Service": "ecs-tasks.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
})
}

# IAM policy for ssm access
resource "aws_iam_policy" "ssm_access_policy" {
  name        = "ecs_ssm_access_policy"
  description = "Policy to allow ECS tasks to access SSM parameters"

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "ssm:GetParameter",
          "ssm:GetParameters",
          "ssm:GetParametersByPath"
        ]
        Resource = "*"
      },
    ]
  })
}

# Attach Managed Policy
resource "aws_iam_role_policy_attachment" "ecs_task_execution_managed_policy_attach" {
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
  role       = aws_iam_role.ecs_task_execution_role.name
}

# Attach the SSM Access Policy to the ECS Task Execution Role
resource "aws_iam_role_policy_attachment" "ecs_task_execution_ssm_policy_attach" {
  policy_arn = aws_iam_policy.ssm_access_policy.arn
  role       = aws_iam_role.ecs_task_execution_role.name
}