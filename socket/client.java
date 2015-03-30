import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
 
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.util.Log;
 
/**
 * Java Socket Client
 */
public class SocketClient {
    private final String SOCKET_NAME = "htfsk";
    private LocalSocket client;
    private LocalSocketAddress address;
    private boolean isConnected = false;
    private int connetTime = 1;
 
    public SocketClient() {
        client = new LocalSocket();
        address = new LocalSocketAddress(SOCKET_NAME, LocalSocketAddress.Namespace.RESERVED);
        new ConnectSocketThread().start();
    }
 
    /**
     * Send message
     * @param msg
     * @return Socket response from server
     */
    public String sendMsg(String msg) {
        if (!isConnected) {
            return "Connect fail";
        }
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream());
            out.println(msg);
            out.flush();
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Nothing return";
    }
 
    /**
     * Async Socket,try re-connect by 10 times.
     * @author Administrator
     */
    private class ConnectSocketThread extends Thread {
        @Override
        public void run() {
            while (!isConnected && connetTime <= 10) {
                try {
                    sleep(1000);
                    Log.i("SocketClient","Try to connect socket;ConnectTime:"+connetTime);
                    client.connect(address);
                    isConnected = true;
                } catch (Exception e) {
                    connetTime++;
                    isConnected = false;
                    Log.i("SocketClient","Connect fail");
                }
            }
        }
    }
 

    public void closeSocket() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
}
