#!/usr/bin/env bash
set -euo pipefail

: "${USERNAME:?USERNAME not set or empty}"
: "${REPO:?REPO not set or empty}"
ENVIRONMENT="${1:-${ENVIRONMENT:-prod}}"  # test | staging | prod
BUILD_NUMBER="$(date '+%d.%m.%Y.%H.%M.%S')"
: "${TAG:="${BUILD_NUMBER}-${ENVIRONMENT}"}"



FULL_IMAGE="$USERNAME/$REPO:$TAG"
CACHE_IMAGE="$USERNAME/$REPO:buildcache"
BUILDER_NAME="multiarch-builder"


if ! docker buildx inspect "$BUILDER_NAME" >/dev/null 2>&1; then
  echo "🔧  Creating buildx builder '$BUILDER_NAME' with docker-container driver…"
  docker buildx create --name "$BUILDER_NAME" --driver docker-container --use
else
  docker buildx use "$BUILDER_NAME"
fi

if ! docker buildx inspect "$BUILDER_NAME" | grep -q "linux/arm64"; then
  echo "🔧  Registering binfmt for cross‑arch builds…"
  docker run --privileged --rm tonistiigi/binfmt:latest --install all
  docker buildx inspect "$BUILDER_NAME" --bootstrap > /dev/null
fi

# === Docker Login ===
if ! docker info | grep -q "Username: $USERNAME"; then
  echo "🔐  Logging into Docker Hub…"
  docker login
fi

docker buildx build \
  --platform=linux/amd64 \
  -t "${FULL_IMAGE}" \
  -t "${USERNAME}/${REPO}:latest" \
  --push \
  .

printf '\n✅  Done! Multi‑arch image pushed as: %s\n' "$FULL_IMAGE"