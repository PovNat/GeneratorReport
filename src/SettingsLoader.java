import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SettingsLoader {

    //парсинг файла настроек

    private ArrayList<String> fields = new ArrayList<>();
    private Element el;
    private NodeList nodeList;

    // конструктор принимает путь до файла и выдает докумен с полями

    public SettingsLoader(String pathFromConsole) throws ParserConfigurationException, IOException, SAXException {

        File fXmlFile = new File(pathFromConsole);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        this.el = doc.getDocumentElement();
        this.nodeList = el.getChildNodes();


        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i) instanceof Element) {
                for (String field : ((Element) nodeList.item(i)).getTagName().split("\n")) {
                    this.fields.add(field);
                }
            }
        }
    }

    public void getFields() {
        for (String field : this.fields) {
            System.out.println(field);
        }
    }

    public ArrayList<Integer> getPageSet() {  //получаем настройки для страницы
        this.el.normalize();
        String a = null;
        ArrayList<Integer> pageVals = new ArrayList<>();
        for (int i = 0; i < this.nodeList.getLength(); i++) {
            if (this.nodeList.item(i) instanceof Element) {
                if (((Element) this.nodeList.item(i)).getTagName().equals(this.fields.get(0))) {
                    a = this.nodeList.item(i).getTextContent();
                    String[] pageValsTemp = a.split("[^0-9]");
                    for (String valPage : pageValsTemp) {
                        if (valPage.length() > 0) {
                            pageVals.add(Integer.valueOf(valPage.trim()));
                        }
                    }
                }
            }
        }
        return pageVals;
    }

    public ArrayList<String> getColumsTitles() {    //получаем названия столбцов таблицы
        el.normalize();
        ArrayList<String> titles = new ArrayList<>();
        String a;
        for (int i = 0; i < this.nodeList.getLength(); i++) {
            if (this.nodeList.item(i) instanceof Element) {
                if (((Element) this.nodeList.item(i)).getTagName().equals(this.fields.get(1))) {
                    a = this.nodeList.item(i).getTextContent();
                    String[] colVals = a.split("\\s+");
                    for (int q = 0; q<colVals.length; q++){
                        if (((colVals[q].length() >0)) & !(q % 2 == 0)){
                            titles.add(colVals[q].trim());
                        }
                    }
                }
            }
        }

        return titles;
    }

    public ArrayList<Integer> getColumsVals(){    // получаем ширину каждого столбца
        el.normalize();
        ArrayList<Integer> width = new ArrayList<>();
        String a;
        for (int i = 0; i < this.nodeList.getLength(); i++) {
            if (this.nodeList.item(i) instanceof Element) {
                if (((Element) this.nodeList.item(i)).getTagName().equals(this.fields.get(1))) {
                    a = this.nodeList.item(i).getTextContent();
                    String[] colVals = a.split("\\s+");
                    for (int q = 0; q<colVals.length; q++){
                        if (((colVals[q].length() >0)) & (q % 2 == 0)){
                            width.add(Integer.valueOf(colVals[q].trim()));
                        }
                    }
                }
            }
        }

        return width;
    }
}
