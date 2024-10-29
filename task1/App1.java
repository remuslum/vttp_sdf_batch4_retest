import file.FileManager;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import parser.TextParser;


public class App1 {
    public static void main(String[] args) throws IOException {
        String csvFileName = args[0];
        String templateFileName = args[1];

        FileManager fileManager = new FileManager();
        TextParser textParser = new TextParser();

        List<Map<String, String>> csvFileContents = fileManager.readCSV(csvFileName);
        String textFileContents = fileManager.readTextFile(templateFileName);

        List<String> replacedText = textParser.getReplacedText(textFileContents, csvFileContents);
        replacedText.forEach(System.out::println);
    }
}
