FROM public.ecr.aws/docker/library/node:23-alpine AS base

FROM base AS deps

RUN apk add --no-cache curl

HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
    CMD curl -f http://localhost:4000/graphql\?query\=%7B__typename%7D || exit 1

WORKDIR /app

COPY package*.json ./

RUN npm ci

COPY src/ ./src
COPY dist/ ./dist

EXPOSE 4000

ENV PORT=3000
ENV HOSTNAME=0.0.0.0

# Start the server
CMD ["node", "./dist/index.js"]