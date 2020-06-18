import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        SettingsLoader parser = new SettingsLoader("in/settings.xml"); // парсим xml-файл с настройками под таблицу
        ArrayList<Integer> pageVals = parser.getPageSet();   // получаем ArrayList с параметрами страницы таблицы
        ArrayList<Integer> colsWidth = parser.getColumsVals();  //получаем ArrayList пареметрами полей
        ArrayList<String> colsTitle = parser.getColumsTitles(); //получаем ArrayList с названиями полей

        ReadSourceData reader = new ReadSourceData();
        ArrayList<ArrayList<String>> data = reader.getSourceData();  //получаем ArrayList из ArrayLists со всем данными для таблицы

        String report = "";


        ReportBuilder reportBuilder = new ReportBuilder(data, pageVals, colsWidth, colsTitle);


        for (int i = 0; i < data.get(0).size(); i++) {      // построчный сбор данных, сгрупированных в столбцы
            ArrayList<String> row = new ArrayList<>();
            for (int j = 0; j < data.size(); j++) {
                row.add(data.get(j).get(i));
            }
            report = report + reportBuilder.getLines(row, colsWidth, pageVals.get(0));

        }

        String rep;
        rep = reportBuilder.getPages(report, pageVals.get(1));

        WriteToTxt wtt = new WriteToTxt();   //запись в txt файл
        wtt.WriteLastLine(rep);
    }
}
