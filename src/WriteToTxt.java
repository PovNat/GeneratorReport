import java.io.*;

public class WriteToTxt {
    private final String path;

    public WriteToTxt(String arg) {
        this.path = arg;
    }

    //записываем таблицу в txt файл

    public void WriteLastLine(String report) throws IOException {
        File file = new File(this.path);
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


