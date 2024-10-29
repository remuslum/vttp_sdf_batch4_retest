package task2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, Exception{
        int port = Integer.parseInt(args[0]);
        String path = "/Users/remuslum/Downloads/vttp_sdf/vttp_sdf_batch4_retest/task2";
        String fileName = args[1];

        ServerSocket server = new ServerSocket(port);
        while(true){
            System.out.println("Waiting for connection...");

            Socket connection = server.accept();

            System.out.println("Connection accepted!");

            //Read message to server
            InputStream is = connection.getInputStream();
            Reader reader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(reader);

            //Write message to client
            OutputStream os = connection.getOutputStream();

            HttpWriter httpWriter = new HttpWriter(os);

            String messageFromClient = "";
            while((messageFromClient = br.readLine()) != null){
                List<String> inputs = Arrays.asList(messageFromClient.split(" "));
                List<String> filteredInputs = inputs.stream().filter(s -> s.contains(".html")).map(s -> s.replaceFirst("/", "")).toList();
                File file = new File(path + File.separator + fileName + File.separator + filteredInputs.get(0));
                System.out.println(file.exists());

                if(file.exists()){
                    FileReader fileReader = new FileReader(file);
                    BufferedReader fileBufferedReader = new BufferedReader(fileReader);
                    String line = "";
                    StringBuilder sb = new StringBuilder();
                    while((line = fileBufferedReader.readLine()) != null){
                        sb.append(line);
                    }
                    fileBufferedReader.close();
                    String htmlContents = sb.toString();
                    httpWriter.writeString("HTTP/1.1 200 OK\r\n");
                    httpWriter.writeString("Content-Type: text/html\r\n");
                    httpWriter.writeString("\r\n");
                    httpWriter.writeBytes(htmlContents.getBytes("UTF-8"));
                    httpWriter.writeString("\r\n");
                } else {
                    httpWriter.writeString("HTTP/1.1 404 Not Found\r\n");
                    httpWriter.writeString("Content-Type: text/html\r\n");
                    httpWriter.writeString("\r\n");
                    httpWriter.writeString("Resource " + filteredInputs.get(0) + " not found\r\n");
                }
            }
            httpWriter.flush();
            
        }

    }
    
}
