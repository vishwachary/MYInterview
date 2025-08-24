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
 
 PS C:\Ddrive\softwares\helm-v3.18.6-windows-amd64\windows-amd64> helm install my-kafka bitnami/kafka --set replicas=1
NAME: my-kafka
LAST DEPLOYED: Sun Aug 24 23:39:04 2025
NAMESPACE: default
STATUS: deployed
REVISION: 1
TEST SUITE: None
NOTES:
CHART NAME: kafka
CHART VERSION: 32.4.2
APP VERSION: 4.0.0

⚠ WARNING: Since August 28th, 2025, only a limited subset of images/charts are available for free.
    Subscribe to Bitnami Secure Images to receive continued support and security updates.
    More info at https://bitnami.com and https://github.com/bitnami/containers/issues/83267

** Please be patient while the chart is being deployed **

Kafka can be accessed by consumers via port 9092 on the following DNS name from within your cluster:

    my-kafka.default.svc.cluster.local

Each Kafka broker can be accessed by producers via port 9092 on the following DNS name(s) from within your cluster:

    my-kafka-controller-0.my-kafka-controller-headless.default.svc.cluster.local:9092
    my-kafka-controller-1.my-kafka-controller-headless.default.svc.cluster.local:9092
    my-kafka-controller-2.my-kafka-controller-headless.default.svc.cluster.local:9092

The CLIENT listener for Kafka client connections from within your cluster have been configured with the following security settings:
    - SASL authentication

To connect a client to your Kafka, you need to create the 'client.properties' configuration files with the content below:

security.protocol=SASL_PLAINTEXT
sasl.mechanism=SCRAM-SHA-256
sasl.jaas.config=org.apache.kafka.common.security.scram.ScramLoginModule required \
    username="user1" \
    password="$(kubectl get secret my-kafka-user-passwords --namespace default -o jsonpath='{.data.client-passwords}' | base64 -d | cut -d , -f 1)";

To create a pod that you can use as a Kafka client run the following commands:

    kubectl run my-kafka-client --restart='Never' --image docker.io/bitnami/kafka:4.0.0-debian-12-r10 --namespace default --command -- sleep infinity
    kubectl cp --namespace default /path/to/client.properties my-kafka-client:/tmp/client.properties
    kubectl exec --tty -i my-kafka-client --namespace default -- bash

    PRODUCER:
        kafka-console-producer.sh \
            --producer.config /tmp/client.properties \
            --bootstrap-server my-kafka.default.svc.cluster.local:9092 \
            --topic test

    CONSUMER:
        kafka-console-consumer.sh \
            --consumer.config /tmp/client.properties \
            --bootstrap-server my-kafka.default.svc.cluster.local:9092 \
            --topic test \
            --from-beginning

WARNING: There are "resources" sections in the chart not set. Using "resourcesPreset" is not recommended for production. For production installations, please set the following values according to your workload needs:
  - controller.resources
  - defaultInitContainers.prepareConfig.resources
+info https://kubernetes.io/docs/concepts/configuration/manage-resources-containers/
PS C:\Ddrive\softwares\helm-v3.18.6-windows-amd64\windows-amd64>

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
