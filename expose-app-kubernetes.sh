#!/bin/bash
kubectl expose deployment/deployment-apps --port 9191 --target-port 9191 --type ClusterIP --selector=run=webapp