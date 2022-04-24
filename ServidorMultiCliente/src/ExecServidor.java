import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ExecServidor {
    public static List<ClienteConectado> clientes = new ArrayList<>();
    
    public static void main(String[] args) {
        ExecServidor es = new ExecServidor();
        es.aguardaCliente();
    }
    
    public void aguardaCliente(){
        ClienteConectado smc;
        Socket s;
        try {
            ServerSocket ss = new ServerSocket(80);
            
            while(true){
                s = ss.accept();
                smc = new ClienteConectado(s);
                smc.configurarFluxos();
                clientes.add(smc);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
