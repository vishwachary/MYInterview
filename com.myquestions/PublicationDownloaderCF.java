import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;

public class PublicationDownloaderCF {

    static final int THREAD_POOL_SIZE = 10;

    static class FileData {
        String fileName;
        String content;
        FileData(String fileName, String content) {
            this.fileName = fileName;
            this.content = content;
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection("jdbc:your-db-url");
             PreparedStatement stmt = conn.prepareStatement("SELECT filename, xtl_content FROM xml_files WHERE publication_id = ?")) {

            stmt.setInt(1, 123); // Example publication ID
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String filename = rs.getString("filename");
                Clob clob = rs.getClob("xtl_content");

                // Chain read and write operations asynchronously
                CompletableFuture<Void> future = CompletableFuture
                        .supplyAsync(() -> readClob(clob, filename), executor)
                        .thenAcceptAsync(PublicationDownloaderCF::writeToDisk, executor);

                futures.add(future);
            }
        }

        // Wait for all tasks to complete
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executor.shutdown();
    }

    // Read Clob into FileData
    static FileData readClob(Clob clob, String filename) {
        try {
            String content = clob.getSubString(1, (int) clob.length());
            return new FileData(filename, content);
        } catch (SQLException e) {
            throw new RuntimeException("Error reading Clob", e);
        }
    }

    // Write file to disk
    static void writeToDisk(FileData data) {
        try {
            File file = new File("downloads/" + data.fileName);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(data.content);
            }
            System.out.println("Written: " + data.fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
