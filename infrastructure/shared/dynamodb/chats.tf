resource "aws_dynamodb_table" "chats" {
  name         = "${var.environment}-chats"
  billing_mode = "PROVISIONED"

  read_capacity  = 1
  write_capacity = 1

  hash_key  = "conversationId"
  range_key = "createdAt"

  attribute {
    name = "conversationId"
    type = "S"
  }

  attribute {
    name = "chatId"
    type = "S"
  }

  attribute {
    name = "createdAt"
    type = "S"
  }

  attribute {
    name = "userId"
    type = "S"
  }

  global_secondary_index {
    name      = "chatId-index"
    hash_key  = "chatId"
    range_key = "createdAt"

    read_capacity  = 1
    write_capacity = 1

    projection_type = "ALL"
  }

  global_secondary_index {
    name      = "userId-index"
    hash_key  = "userId"
    range_key = "createdAt"

    read_capacity  = 1
    write_capacity = 1

    projection_type = "ALL"
  }

  ttl {
    attribute_name = "ttlTimestamp"
    enabled        = true
  }

}
