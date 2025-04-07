resource "aws_iam_role" "codebuild_role" {
  name = "codebuild-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect = "Allow",
        Principal = {
          Service = "codebuild.amazonaws.com"
        },
        Action = "sts:AssumeRole"
      }
    ]
  })
}

resource "aws_iam_policy" "codebuild_policy" {
  name        = "codebuild-policy"
  description = "Policy for CodeBuild to access AWS resources"

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect = "Allow",
        Action = [
          "ec2:*",
          "ecs:*",
          "rds:*",
          "elasticache:*",
          "s3:*",
          "dynamodb:*",
          "route53:*",
          "acm:*",
          "elasticloadbalancing:*",
          "ses:*",
        ],
        Resource = "*"
      },
      {
        Effect = "Allow",
        Action = [
          "ecr:*",
        ],
        Resource = "*"
      },
      {
        Effect = "Allow",
        Action = [
          "codebuild:StartBuild",
          "codebuild:BatchGetBuilds",
          "codebuild:BatchGetProjects",
          "codebuild:ListBuildsForProject",
          "codebuild:ListProjects",
          "codedeploy:*"
        ],
        Resource = "*"
      },
      {
        Effect = "Allow",
        Action = [
          "codepipeline:*",
        ],
        Resource = "*"
      },
      {
        Effect = "Allow",
        Action = [
          "secretsmanager:*",
          "ssm:*"
        ],
        Resource = "*"
      },
      {
        Effect = "Allow",
        Action = [
          "codeconnections:UseConnection",
          "codeconnections:GetConnection",
          "codeconnections:ListConnections",
          "codestar-connections:PassConnection",
          "codeconnections:*",
          "codestar:*",
        ],
        Resource = "*"
      },
      {
        Effect = "Allow",
        Action = [
          "logs:*",
        ],
        Resource = "*"
      },
      {
        Effect = "Allow",
        Action = [
          "cognito-idp:*",
        ],
        Resource = "*"
      },
      {
        Effect = "Allow",
        Action = [
          "iam:*",
        ],
        Resource = "*"
      },
    ]
  })
}

resource "aws_iam_role_policy_attachment" "codebuild_policy_attachment" {
  role       = aws_iam_role.codebuild_role.name
  policy_arn = aws_iam_policy.codebuild_policy.arn
}
