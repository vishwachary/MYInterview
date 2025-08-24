Running Apache Kafka locally with Rancher Desktop’s Kubernetes is a common developer need, but requires a few extra steps because Kafka depends on both Zookeeper and specific networking considerations. Here’s a step-by-step guide: 
downlaod and unzip helm.exe to the a folder  example here PS C:\Ddrive\softwares\helm-v3.18.6-windows-amd64\windows-amd64>
https://github.com/helm/helm/releases 

PS C:\Ddrive\softwares\helm-v3.18.6-windows-amd64\windows-amd64> helm repo add bitnami https://charts.bitnami.com/bitnami
PS C:\Ddrive\softwares\helm-v3.18.6-windows-amd64\windows-amd64> helm repo add bitnami https://charts.bitnami.com/bitnami
"bitnami" has been added to your repositories
PS C:\Ddrive\softwares\helm-v3.18.6-windows-amd64\windows-amd64> helm repo update
Hang tight while we grab the latest from your chart repositories...
...Successfully got an update from the "bitnami" chart repository
Update Complete. ⎈Happy Helming!⎈
PS C:\Ddrive\softwares\helm-v3.18.6-windows-amd64\windows-amd64>


1. Use a Helm Chart (Recommended/Easiest) 

The Bitnami Kafka Helm Chart  is the most common and developer-friendly way to deploy Kafka and Zookeeper to your local cluster. 

Step 1: Add Bitnami Helm repository   
sh
 
 
 
1
2
helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update
 
 

Step 2: Install Kafka with Zookeeper   
sh
 
 
 
1
helm install my-kafka bitnami/kafka --set replicas=1
 
 

     This command will install both Kafka and Zookeeper with 1 replica each.
     You can see all pods and services with:
    sh
     
     

     
    1
    kubectl get all
     
     
     

2. Access Kafka Locally 

Kafka is exposed only within the cluster by default. To connect from your workstation (outside the cluster), you have two common options: 
A. Port Forwarding (Simple Approach) 

Forward Kafka’s service port to your local machine: 
sh
 
 
 
1
kubectl port-forward svc/my-kafka 9092:9092
 
 

Now, you can connect to Kafka on localhost:9092. 

    Note: By default, Bitnami’s chart sets up listeners for in-cluster access (using advertised listeners with internal K8s DNS), so if you are writing clients outside Kubernetes, see Bitnami’s documentation for exposing Kafka externally  (for development, port-forward is usually enough). 
     

B. NodePort (Alternative, not recommended in production) 

Edit the Kafka service to use NodePort or use Helm set flags—see Bitnami Kafka chart docs for advanced configs. 
3. Quick Verification 

     List running pods:
    sh
     
     

     
    1
    kubectl get pods
     
     
     Once pods are ready, you can test with any Kafka client (e.g. kcat, Java Spring Kafka, etc.).
     

4. Uninstall When Done 

To delete your Kafka deployment: 
sh
 
 
 
1
helm uninstall my-kafka
 
 
Troubleshooting Tips 

     Make sure your local Docker/Kubernetes cluster has enough memory (Kafka is memory-intensive).
     If you need custom configs, pass extra --set ... flags when running helm install.
     

Best Practices/Compliance 

If considering local Kafka for professional/test workloads at Oracle, always ensure data security, network access, and compliance with corporate policies. 

Need a full sample kubectl YAML deployment instead of Helm, or advanced configuration (topics, persistence, external access, etc.)? Just let me know! 
