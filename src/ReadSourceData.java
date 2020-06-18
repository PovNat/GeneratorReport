import java.io.*;
import java.util.ArrayList;

public class ReadSourceData {
    public ReadSourceData() {
    }
    public ArrayList <ArrayList<String>> getSourceData() throws FileNotFoundException, UnsupportedEncodingException {

        //достаем все данные из файла

        ArrayList<String> allInfo = new ArrayList<>();
        String fileName = "in/source-data.tsv";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF16"));
        try {
            String line;

            while ((line = br.readLine()) != null) {

                for (String a : line.split("\t")) {
                    allInfo.add(a);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList <String> id = new ArrayList<>();    // делим данные согласно столбцам и
        ArrayList <String> data = new ArrayList<>();  // распределяем их в отдельные ArrayLists
        ArrayList <String> name = new ArrayList<>();

        for (int i = 0; i<allInfo.size(); i=i+3){
            id.add(allInfo.get(i));
        }
        for (int i = 1; i<allInfo.size(); i=i+3){
            data.add(allInfo.get(i));
        }
        for (int i = 2; i<allInfo.size(); i=i+3){
            name.add(allInfo.get(i));
        }

        ArrayList <ArrayList<String>> allArrList = new ArrayList<>();
        allArrList.add(id);
        allArrList.add(data);
        allArrList.add(name);


        return allArrList;
    }

}
