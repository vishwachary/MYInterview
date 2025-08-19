# MYInterview
Final Answer (in plain terms):

In my CMS use case, the read and write for each file is synchronous because writing depends on reading the content. However, the processing of multiple files happens in parallel, which is asynchronous between files. This improves performance because while one file is being written, another can be read from the database simultaneously using threads or async programming with CompletableFuture.

 How to explain this in an interview:

"In our CMS, each publication contains thousands of XML files stored in the database. For each file, we need to read it from the database and then write it to disk — this operation is synchronous for each file because we can’t write before reading. However, to improve performance, we process multiple files in parallel using threads or CompletableFuture. This allows us to asynchronously fetch and write many files at once while maintaining the read→write order for each file individually."


