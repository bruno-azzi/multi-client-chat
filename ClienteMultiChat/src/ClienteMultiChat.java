import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteMultiChat extends Thread{
    Socket s;
    BufferedReader in;
    BufferedReader teclado;
    PrintWriter out;
  
    public static void main(String[] args) {
        ClienteMultiChat cmc = new ClienteMultiChat();                
        cmc.conectar("localhost", 80);
        cmc.start();
        
        while(true) {
            cmc.enviarMsg();
        }
    }
    
    public void conectar(String destino, Integer porta){
        try {
            s = new Socket(destino, porta);
            in = new BufferedReader(
                    new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream());
            teclado = new BufferedReader(
                    new InputStreamReader(System.in));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void enviarMsg(){
        try {
            out.println(teclado.readLine());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
    
    public void receberMsg(){
        String msg = "";
        try {
            msg = in.readLine();
            System.out.println(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true){
            receberMsg();
        }
    }
}
