# frontend.Dockerfile - multi-stage build for a React frontend
#
# Build command (run from this folder, pointing the context at the actual
# React app module you want to containerize - e.g. the Week-4 Module-11
# React app):
#
#   docker build -f frontend.Dockerfile -t dn5-frontend:latest ../../Week-4-Frontend-Frameworks/11-React-App
#
# The build context (the path argument) must contain package.json and the
# app source at its root for the COPY instructions below to resolve
# correctly. This Dockerfile also expects nginx.conf to exist alongside it
# (in 15-Docker-Containerization/), which is copied into the final image.

# ---------------------------------------------------------------------------
# Stage 1: build the static React bundle with Node
# ---------------------------------------------------------------------------
FROM node:18-alpine AS build

WORKDIR /build

# Copy package manifests first to leverage Docker layer caching.
COPY package*.json ./
RUN npm ci

COPY . .
RUN npm run build

# ---------------------------------------------------------------------------
# Stage 2: serve the static bundle with nginx
# ---------------------------------------------------------------------------
FROM nginx:1.25-alpine

# React build output - adjust "dist" to "build" if your tooling (e.g.
# Create React App) outputs to build/ instead of dist/ (Vite default).
COPY --from=build /build/dist /usr/share/nginx/html

# Custom SPA-friendly server config (see nginx.conf in this folder).
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
