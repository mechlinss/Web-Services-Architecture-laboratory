#!/bin/sh
set -e

# Substitute environment variables in NGINX configuration
envsubst '${API_URL}' < /etc/nginx/templates/default.conf.template > /etc/nginx/conf.d/default.conf

# Execute the CMD
exec "$@"
