import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class WriteToTxt {
    private final String path;

    public WriteToTxt(String arg) {
        this.path = arg;
    }

    //записываем таблицу в txt файл

    public void WriteLastLine(String report) throws IOException {
        //File file = new File(this.path);
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(this.path), Charset.forName("UTF-16").newEncoder());
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


