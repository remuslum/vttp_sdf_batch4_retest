import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileManager {
    private final String fileDirectory = "/Users/remuslum/Downloads/vttp_sdf/vttp_sdf_batch4_retest/data";;

    public FileManager() {
        
    }

    public List<Map<String, String>> readCSV(String fileName) throws IOException{
        File file = new File(fileDirectory + File.separator + fileName);

        //Initialise Readers
        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        
        String headers = br.readLine();
        String[] headersInList = headers.split(",");

        List<Map<String, String>> fileContents = new ArrayList<>();
        String line = "";
        while((line = br.readLine()) != null){
            Map<String, String> temp = new HashMap<>();
            String[] inputs = line.split(",");
            for(int i  = 0; i < inputs.length; i++) {
                temp.put(headersInList[i], inputs[i]);
            }
            fileContents.add(temp);
        }
        br.close();
        return fileContents;
    }

    public String readTextFile(String fileName) throws IOException {
        File file = new File(fileDirectory + File.separator + fileName);
        
        //Initialise Readers
        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);

        StringBuilder sb = new StringBuilder();
        String line = "";
        while((line = br.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        br.close();
        return sb.toString();
    }

}