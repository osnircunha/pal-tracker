#!/usr/bin/env bash

ROUTE_URL="apps.pikes.pal.pivotal.io"
echo "Deploying $1 to $ROUTE_URL with with route $2"

pwd

ls -lt

#cf push pal-tracker -n osnir-pal -d apps.pikes.pal.pivotal.io
cf push $1 -n $2 -d $ROUTE_URL