

# ECS
output "ecs_task_execution_role_arn" {
  value = aws_iam_role.ecs_task_execution_role.arn
}

output "ecs_task_role_arn" {
  value = aws_iam_role.ecs_task_role.arn
}

# Codebuild
output "codebuild_role_arn" {
  value = aws_iam_role.codebuild_role.arn
}

# Codepipeline
output "codepipeline_role_arn" {
  value = aws_iam_role.codepipeline_role.arn
}