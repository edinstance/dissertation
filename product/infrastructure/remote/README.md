# Remote Infrastructure

This is the infrastructure that is to be deployed remotely, to deploy it first you need to initalise terraform with the correct backend configuration. This is currently setup to use a Gitlab http backend, and the backend config variables below are required to be set when deploying.

```
terraform init \
          -backend-config="address=https://example.com/backend" \
          -backend-config="lock_address=https://example.com/lock" \
          -backend-config="unlock_address=https://example.com/unlock" \
          -backend-config="username=${GITLAB_USERNAME}" \
          -backend-config="password=${GITLAB_TOKEN}"
```