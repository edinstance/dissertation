FROM public.ecr.aws/docker/library/node:23-alpine AS base

FROM base AS deps

RUN apk add --no-cache curl

HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
    CMD curl -f http://localhost:4000/graphql\?query\=%7B__typename%7D || exit 1


COPY package.json yarn.lock* package-lock.json* pnpm-lock.yaml* ./
RUN \
  if [ -f yarn.lock ]; then yarn --frozen-lockfile; \
  elif [ -f package-lock.json ]; then npm ci; \
  elif [ -f pnpm-lock.yaml ]; then yarn global add pnpm && pnpm i; \
  else echo "Lockfile not found." && exit 1; \
  fi

COPY tsconfig.json ./
COPY src/ ./src

RUN npm run build

EXPOSE 4000

ENV PORT=3000
ENV HOSTNAME=0.0.0.0

# Start the server
CMD ["node", "/dist/index.js"]