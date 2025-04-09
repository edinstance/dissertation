
# IAM Role for ECS Task Execution
resource "aws_iam_role" "ecs_task_execution_role" {
  name = "ECSTaskExecutionRole"

  assume_role_policy = jsonencode({
    "Version" : "2012-10-17",
    "Statement" : [
      {
        "Effect" : "Allow",
        "Principal" : {
          "Service" : "ecs-tasks.amazonaws.com"
        },
        "Action" : "sts:AssumeRole"
      }
    ]
  })
}

# IAM policy for ECS Task role
resource "aws_iam_role" "ecs_task_role" {
  name = "ECSTaskRole"

  assume_role_policy = jsonencode({
    "Version" : "2012-10-17",
    "Statement" : [
      {
        "Effect" : "Allow",
        "Principal" : {
          "Service" : "ecs-tasks.amazonaws.com"
        },
        "Action" : "sts:AssumeRole"
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

resource "aws_iam_policy" "ecs_cognito_policy" {
  name        = "ECSCognitoAccessPolicy"
  description = "Policy to allow access to Cognito User Pool"

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "cognito-idp:InitiateAuth",
          "cognito-idp:AdminGetUser",
          "cognito-idp:ListUsers",
          "cognito-idp:AdminCreateUser",
          "cognito-idp:AdminUpdateUserAttributes",
          "cognito-idp:AdminDeleteUser",
          "cognito-idp:AdminSetUserPassword",
          "cognito-idp:AdminConfirmSignUp"
        ]
        Resource = var.cognito_user_pool_arn
      },
    ]
  })
}

# IAM policy for sending SES emails
resource "aws_iam_policy" "ecs_ses_policy" {
  name        = "ECSSESAccessPolicy"
  description = "Policy to allow ECS tasks to send emails via SES"

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "ses:SendEmail",
          "ses:SendRawEmail",
          "ses:SendTemplatedEmail"
        ]
        Resource = "*"
      },
    ]
  })
}

resource "aws_iam_policy" "ecs_dynamodb_admin_logs_policy" {
  name        = "${var.environment}-ECSDynamoDBAdminLogsAccessPolicy"
  description = "Policy to allow ECS tasks to read/write Admin Access Logs DynamoDB table"

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Sid    = "AllowDynamoDBReadWriteAdminLogs"
        Effect = "Allow"
        Action = [
          "dynamodb:PutItem",
          "dynamodb:UpdateItem",
          "dynamodb:DeleteItem",
          "dynamodb:BatchWriteItem",
          "dynamodb:DescribeTable"
        ]
        Resource = "${var.admin_access_logs_table_arn}"
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

# Attach the Cognito Access Policy to the ECS Task Role
resource "aws_iam_role_policy_attachment" "ecs_cognito_policy_attach" {
  policy_arn = aws_iam_policy.ecs_cognito_policy.arn
  role       = aws_iam_role.ecs_task_role.name
}

# Attach the SES Access Policy to the ECS Task Role
resource "aws_iam_role_policy_attachment" "ecs_ses_policy_attach" {
  policy_arn = aws_iam_policy.ecs_ses_policy.arn
  role       = aws_iam_role.ecs_task_role.name
}

# Attach the DynamoDB Access Policy to the ECS Task Role
resource "aws_iam_role_policy_attachment" "ecs_dynamodb_admin_logs_policy_attach" {
  policy_arn = aws_iam_policy.ecs_dynamodb_admin_logs_policy.arn
  role       = aws_iam_role.ecs_task_role.name
}