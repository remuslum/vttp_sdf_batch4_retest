package task2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

public class Browser {
    public static void main(String[] args) throws IOException{
        String[] inputs = args[0].split(":");
        Socket socket = new Socket(inputs[0], Integer.parseInt(inputs[1]));

        while (true) { 
            //Read message to client
            InputStream is = socket.getInputStream();
            Reader reader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(reader);

            //Write message to server
            OutputStream os = socket.getOutputStream();
            Writer writer = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(writer);

            Console console = System.console();
            String messageToServer = console.readLine("> ");

            bw.write(messageToServer + "\n");
            bw.flush();

            String messageFromServer = "";
            while((messageFromServer = br.readLine()) != null){
                System.out.println(messageFromServer);
            }


        }
    }
}
