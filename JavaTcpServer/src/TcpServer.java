import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import commUtil.Comm;
import connectionPool.TcpConnectionPool;
import connectionPool.model.ConnectionPoolData;

public class TcpServer 
{
	static ServerThreadPool  pThreadPool    = null;
	static TcpConnectionPool pConnectionPool = null;
	
	public static void main(String[] args)
	{
		pThreadPool     = ServerThreadPool.getInstance();
		pConnectionPool = new TcpConnectionPool();
		
		for(;;) {
			
			ConnectionPoolData pServerSock = pConnectionPool.getSocket();
			
			try {
				
				if(pServerSock != null) {
					
					Socket socket = pConnectionPool.serverSocket.accept();
					pServerSock.setSocket(socket);
					pThreadPool.useThread(socket, pServerSock, pConnectionPool);
				}
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	static void serverSample()
	{
		ServerSocket serverSocket = null;
        
        try {
        	
            // 서버소켓을 생성하고 5000번 포트와 결합(bind) 시킨다.
            serverSocket = new ServerSocket(Comm.serverPort);
            System.out.println(Comm.getTime() + " 서버가 준비되었습니다.");
        } 
        catch (IOException e) {
        	
            e.printStackTrace();
        } // try - catch
        
        DataOutputStream dos = null;
		DataInputStream  ios = null;
    	OutputStream out     = null;
    	InputStream  in      = null;
    	Socket       socket  = null;
    	
        try {
        	
        	socket = serverSocket.accept();
        	System.out.println(Comm.getTime() + " 연결요청을 기다립니다.");
            System.out.println(Comm.getTime() + " " + serverSocket.getLocalSocketAddress());
            // 서버소켓은 클라이언트의 연결요청이 올 때까지 실행을 멈추고 계속 기다린다.
            // 클라이언트의 연결요청이 오면 클라이언트 소켓과 통신할 새로운 소켓을 생성한다.                
            System.out.println(Comm.getTime() + socket.getInetAddress() + " 로부터 연결요청이 들어왔습니다.");
            
        	while(true) {
        		
        		System.out.println("#########################");
                // 소켓의 출력스트림을 얻는다.
                out = socket.getOutputStream(); 
                in  = socket.getInputStream();
                
                ios = new DataInputStream(in);   // 기본형 단위로 처리하는 보조스트림            
                dos = new DataOutputStream(out); // 기본형 단위로 처리하는 보조스트림
                
                byte[] buffer = new byte[1024 * 10]; 
                
                int nReadLength = ios.read(buffer);
                String strRecieveData = new String(buffer, "UTF-8");
                
                System.out.println(Comm.getTime() + " receive data   : " + strRecieveData);
                System.out.println(Comm.getTime() + " message length : " + nReadLength);
                // 원격 소켓(remote socket)에 데이터를 보낸다.

                if(nReadLength == -1) {
                	break;
                }
                
                dos.writeUTF("서버로부터의 메세지입니다.");
                System.out.println(Comm.getTime() + " 데이터를 전송했습니다.");
        	}
            
        } 
        catch (IOException e) {
        	
            e.printStackTrace();
        } // try - catch
        finally {
        	
        	try {
        		
        		dos.close();
                socket.close();     		
        	}
        	catch(IOException socketEx) {
        		
        		socketEx.printStackTrace();
        	}
        	// 스트림과 소켓을 달아준다.
        }
	}
}
