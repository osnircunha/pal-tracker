#!/usr/bin/env bash

ROUTE_URL="$2.apps.pikes.pal.pivotal.io"
echo "Deploying $1 with route $ROUTE_URL"

cf push $1 --route-path $ROUTE_URL