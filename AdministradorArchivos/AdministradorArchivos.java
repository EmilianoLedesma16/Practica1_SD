import java.io.*;
import java.util.concurrent.*;
import java.util.regex.*;

class FileProcessor implements Callable<String> {
    private final File file;
    private final String keyword;
    
    public FileProcessor(File file, String keyword) {
        this.file = file;
        this.keyword = keyword;
    }
    
    @Override
    public String call() {
        int lineCount = 0;
        int keywordCount = 0;

        Pattern pattern = Pattern.compile("\\b" + keyword + "\\b", Pattern.CASE_INSENSITIVE);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    keywordCount++;
                }
            }
        } catch (IOException e) {
            return "Error procesando " + file.getName();
        }
        
        return "Archivo: " + file.getName() + " | Líneas: " + lineCount + " | '" + keyword + "' encontrado: " + keywordCount + " veces";
    }
}

public class AdministradorArchivos {
    public static void main(String[] args) {
        File folder = new File("./Archivos"); // Carpeta con archivos de prueba
        String keyword = "error"; // Palabra clave a buscar
        
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("La carpeta de archivos no existe");
            return;
        }

        long startTime = System.nanoTime();
        
        ExecutorService executor = Executors.newFixedThreadPool(4);
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                Future<String> result = executor.submit(new FileProcessor(file, keyword));
                try {
                    System.out.println(result.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        executor.shutdown();

        long endTime = System.nanoTime();
        long duration = (endTime-startTime) / 1_000_000; //Conversión a ms
        System.out.println("\n Tiempo total de ejecución: "+duration+" ms"); 
    }
}
