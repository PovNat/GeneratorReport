import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class WriteToTxt {

    //записываем таблицу в txt файл

    public void WriteLastLine(String report) throws IOException {
        File file = new File("out/example-report.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        try {
            writer.write(report);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                writer.close();
            } catch (Exception e) {
            }
        }
    }
}


