1-Minute Verbal Explanation (Interview-Ready)

"In this architecture, we use the Backend for Frontend (BFF) pattern. The CMS service exposes a REST API specifically designed for the Publishing Tool, so the client can request all XML files for a given publication and version. When the request is made, the CMS service asynchronously triggers a Spring Batch job — reading XML CLOBs from Oracle ADB, chunking them efficiently, and writing them to a temporary storage location.

We return a jobId immediately, allowing the client to poll job status via a separate endpoint — applying the Asynchronous Request-Reply pattern. Each service owns its own database — in this case, the CMS manages its XML data — following the Database per Service pattern.

This approach keeps the system decoupled, scalable, and client-specific — which is why the BFF pattern fits perfectly here."
