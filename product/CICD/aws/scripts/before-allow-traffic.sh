#!/bin/bash
set -e

echo "Starting BeforeAllowTraffic hook..."

# Get the target group ARN for the new deployment
SERVICE_NAME="${ENVIRONMENT}-${SERVICE_TYPE}-service"
CLUSTER_NAME="${ENVIRONMENT}-cluster"

MAX_ATTEMPTS=30
ATTEMPT=0

TARGET_GROUPS=$(aws elbv2 describe-target-groups --names "${ENVIRONMENT}-${SERVICE_TYPE}-tg-blue" "${ENVIRONMENT}-${SERVICE_TYPE}-tg-green" --query 'TargetGroups[*].TargetGroupArn' --output text)

echo "Checking health of target groups: ${TARGET_GROUPS}"

while [ $ATTEMPT -lt $MAX_ATTEMPTS ]; do
  HEALTHY=true
  
  for TG in $TARGET_GROUPS; do
    # Get target health
    HEALTH_STATUS=$(aws elbv2 describe-target-health --target-group-arn $TG)
    
    # Check if there are any targets and if they're all healthy
    TARGET_COUNT=$(echo $HEALTH_STATUS | jq '.TargetHealthDescriptions | length')
    
    if [ "$TARGET_COUNT" -eq 0 ]; then
      echo "No targets registered in target group: $TG. Skipping."
      continue
    fi
    
    UNHEALTHY_COUNT=$(echo $HEALTH_STATUS | jq '.TargetHealthDescriptions | map(select(.TargetHealth.State != "healthy")) | length')
    
    if [ "$UNHEALTHY_COUNT" -gt 0 ]; then
      echo "Target group $TG has unhealthy targets. ($UNHEALTHY_COUNT unhealthy)"
      HEALTHY=false
      break
    else
      echo "All targets in target group $TG are healthy!"
    fi
  done
  
  if [ "$HEALTHY" = true ]; then
    echo "All target groups have healthy targets!"
    exit 0
  fi
  
  echo "Waiting for all targets to become healthy... (Attempt: $ATTEMPT/$MAX_ATTEMPTS)"
  ATTEMPT=$((ATTEMPT + 1))
  sleep 10
done

echo "Failed to validate target health within the allowed time"
exit 1