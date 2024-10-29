package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TextParser {
    public TextParser() {

    }

    public List<String> getReplacedText(String template, List<Map<String, String>> csvData){
        List<String> replacedText = new ArrayList<>();
        csvData.forEach(d -> {
            replacedText.add(formSentence(replaceUnderscoreVariables(splitByUnderscore(extractUnderscoreVariables(template)), d)));
        });
        return replacedText;
    }

    private List<String> extractUnderscoreVariables(String text){
        //split string into multiple strings
        return Arrays.asList(text.split("\\r?\\n"));
    }

    private List<List<String>> splitByUnderscore(List<String> text){
        List<List<String>> temp = new ArrayList<>();
        for(String item:text) {
            temp.add(Arrays.asList(item.split("__")));
        }
        return temp;
    }

    private List<List<String>> replaceUnderscoreVariables(List<List<String>> template, Map<String,String> csvData) {
        for(List<String> line:template){
            for(String word:line) {
                if (csvData.get(word) != null){
                    line.set(line.indexOf(word), csvData.get(word));
                }
            }
        }
        return template;
    }

    private String formSentence(List<List<String>> replacedTemplate){
        StringBuilder sb =new StringBuilder();
        for(List<String> line:replacedTemplate) {
            for(String word:line){
                if(!word.isEmpty()){
                    if(word.contains("\\n")){
                        String[] wordSplit = word.split("\\\\n");
                        for(int i = 0; i < wordSplit.length; i++){
                            sb.append(wordSplit[i]);
                            if (i != wordSplit.length - 1){
                                sb.append("\n");
                            } 
                        }
                    } else {
                        sb.append(word);
                    }
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }


        
}
