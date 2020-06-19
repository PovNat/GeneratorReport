import java.util.ArrayList;
import java.util.Collections;

public class ReportBuilder {

    // инициализируем параметры отчета

    private final ArrayList<ArrayList<String>> data;
    private final ArrayList<Integer> pageVals;
    private final ArrayList<Integer> colsWidth;
    private final ArrayList<String> colsTitle;

    public ReportBuilder(ArrayList<ArrayList<String>> data, ArrayList<Integer> pageVals, ArrayList<Integer> colsWidth, ArrayList<String> colsTitle) {
        this.data = data;
        this.pageVals = pageVals;
        this.colsWidth = colsWidth;
        this.colsTitle = colsTitle;
    }

    public String getHeader() {

        // формируем заголовок таблицы

        ArrayList<String> fields = new ArrayList<>();

        for (int i = 0; i < this.colsTitle.size(); i++) {
            fields.add(this.colsTitle.get(i) + String.join("", Collections.nCopies(Integer.valueOf(this.colsWidth.get(i)) - this.colsTitle.get(i).length(), " ")));
        }
        String cols = "| " + String.join(" | ", fields) + " |";
        String strip = String.join("", Collections.nCopies(Integer.valueOf(this.pageVals.get(0)), "-"));
        return cols + "\n" + strip;
    }

    public ArrayList<String> getSplit(String word) {

        //разбиение строки на подстроки
        //если в строке четное количество символов- бьет по середине
        //если нечетное- левая часть принимает на один символ меньше, чем правая

        ArrayList<String> words = new ArrayList<>();
        int len = word.length();
        if (len % 2 == 0) {
            words.add(word.substring(0, len / 2));
            words.add(word.substring(len / 2));
        } else {
            words.add(word.substring(0, len / 2));
            words.add(word.substring((int) (len / 2)));
        }

        return words;
    }

    public void addToArray(String word, ArrayList<String> array, Integer limit) {

        //формируем массив поля рекурсивной функцией из условия ширины колонки

        if (word.length() <= limit) {
            array.add(word);
        } else {
            for (String el : this.getSplit(word)) {     //если слово не помещается, оно бьется по середине
                addToArray(el, array, limit);           //создание рекурсии
            }
        }
    }

    public ArrayList<String> getShortSplit(String word, Integer limit) {

        //формируем поле при условии, что в одной строке может быть несколько слов

        ArrayList<String> arr = new ArrayList<>();
        ArrayList<String> res = new ArrayList<>();
        String[] words = word.split("[^A-Za-zа-яА-Я0-9\\-\\/]");
        for (int i = 0; i < words.length; i++) {
            if (i + 1 < words.length) {
                String pair = words[i] + " " + words[i + 1];
                if (pair.length() <= limit) {
                    arr.add(pair.trim());
                    words[i + 1] = "";
                } else {
                    if (words[i] != null && !words[i].isEmpty()) {
                        arr.add(words[i].trim());
                    }
                }
            } else {
                if (words[i] != null && !words[i].isEmpty()) {
                    arr.add(words[i].trim());
                }
            }
        }
        for (String ar : arr) {
            this.addToArray(ar, res, limit);
        }
        return res;
    }

    public String decorator(String word, int limit) {

        //формирует поле таблицы с учетом пробелов перед/до разделителя столбца

        String val = word + String.join("", Collections.nCopies(Integer.valueOf(limit - word.length()), " "));
        return val;
    }


    public String getLines(ArrayList<String> data, ArrayList<Integer> limit, Integer width) {

        //формирует строки из набора массивов

        ArrayList<ArrayList<String>> lines = new ArrayList<>();
        String record = "";
        int maxSize = 0;

        for (int i = 0; i < data.size(); i++) {
            lines.add(this.getShortSplit(data.get(i), limit.get(i)));
        }

        for (ArrayList<String> line : lines) {
            if (line.size() > maxSize) {
                maxSize = line.size();
            }
        }

        if (maxSize > 1) {
            for (int i = 0; i < lines.size(); i++) {
                int diffInSize = maxSize - lines.get(i).size();
                if (diffInSize > 0) {
                    for (int j = lines.get(i).size(); j < maxSize; j++) {
                        lines.get(i).add("");
                    }
                }
            }
        }

        for (int i = 0; i < maxSize; i++) {
            ArrayList<String> decoratedLines = new ArrayList<>();
            for (int j = 0; j < lines.size(); j++) {
                decoratedLines.add(this.decorator(lines.get(j).get(i), limit.get(j)));
            }
            record = record + "| " + String.join(" | ", decoratedLines) + " |\n";
        }
        record = record + String.join("", Collections.nCopies(width, "-")) + "\n";

        return record;
    }

    public String getPages(String rawRecord, int heigth) {

         //формирует страницы из строк


        String[] rawRecordArr = rawRecord.split("\r\n|\r|\n");
        int numOfLines = rawRecordArr.length;
        String pages = "";
        String lastRecord = "";
        int count = 0;

        while (numOfLines > 0) {
            pages = pages + this.getHeader()+"\n";
            for(int i=0; i<heigth-3; i++){
                lastRecord=rawRecordArr[count];
                if (i==0 & lastRecord.contains("--------")){
                    count++;
                    numOfLines--;
                    continue;
                }
                else if (i==heigth-4 & lastRecord.contains("--------")){
                    count++;
                    numOfLines--;
                    break;
                } else {
                    pages+=lastRecord+"\n";
                    count++;
                    numOfLines--;
                }
                if (numOfLines==0){
                    break;
                }
            }
            pages += "~\n";
        }
        return pages;
    }
}

