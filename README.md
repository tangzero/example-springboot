## Example of java app with springboot

## Kubernetes deployment

For deploy the pods

    kubectl create -f deployment.yml
    
For expose the port of the pods

    kubectl create -f services.

    
For expose the app on localhost

    kubectl port-forward deployment/deployment-apps 8989:8080  