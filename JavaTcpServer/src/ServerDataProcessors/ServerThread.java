package ServerDataProcessors;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import commUtil.Comm;
import connectionPool.TcpConnectionPool;
import connectionPool.model.ConnectionPoolData;

public class ServerThread extends Thread
{
	public boolean bUse = false;
	private Socket socket = null;
	private ConnectionPoolData pConnectionData = null;
	private TcpConnectionPool  sockets = null;
	
	public ServerThread()
	{
	}
	
	public void run()
	{
		this.bUse = false;
		
		try {
			
			this.bUse = true;
			pConnectionData.bUsedSocket = true;
			
			this.connection(socket, pConnectionData, sockets);
		} 
		catch(Exception e) {
			
			e.printStackTrace();
			System.out.println("wait exception : " + e);
		}
		
		System.out.println("thead run ");
	}
	
	public void useThread(Socket socket, ConnectionPoolData pConnectionData, TcpConnectionPool sockets)
	{
		this.socket = socket;
		this.pConnectionData = pConnectionData;
		this.sockets = sockets;
		
		this.start();
	}
	
	/*
	 * comment : 서버에 연결한 client를 연결하고 이를 처리하는 클래
	 * param : 
	 * 		socket <- 사용할 socket 
	 * 
	 * */
	public boolean connection(Socket socket, ConnectionPoolData pConnectionData, TcpConnectionPool sockets)
	{
		DataOutputStream dos = null;
		DataInputStream  ios = null;
    	OutputStream out     = null;
    	InputStream  in      = null;
    	
        try {
        	
        	byte[] buffer = new byte[1024 * 10];
        	System.out.println("##################################");
    		System.out.println(Comm.getTime() + " " + pConnectionData.pstrConnectId + " 연결요청을 기다립니다.");
            // 서버소켓은 클라이언트의 연결요청이 올 때까지 실행을 멈추고 계속 기다린다.
            // 클라이언트의 연결요청이 오면 클라이언트 소켓과 통신할 새로운 소켓을 생성한다.
            System.out.println(Comm.getTime() + socket.getInetAddress() + ":" + Comm.serverPort  + " 로부터 연결요청이 들어왔습니다.");
             
            // 소켓의 출력스트림을 얻는다.
            out = socket.getOutputStream();
            in  = socket.getInputStream();
            
            dos = new DataOutputStream(out); // 기본형 단위로 처리하는 보조스트림
            ios = new DataInputStream(in);   // 기본형 단위로 처리하는 보조스트림
            
        	while(true) {
        		
                int nReadLength = ios.read(buffer);
                String strRecieveData = new String(buffer, "UTF-8");
                
                System.out.println(Comm.getTime() + " recieve data   : " + strRecieveData);
                System.out.println(Comm.getTime() + " message length : " + nReadLength);
                
                if(nReadLength == -1) {
                	
                	System.out.println("disconnect " + pConnectionData.pstrConnectId);
                	break;
                }
                else {
                	
                	for(int i = 0; i < sockets.erServerSocket.size(); i++) {
                		
                		ConnectionPoolData pConnect = sockets.erServerSocket.elementAt(i);
                		
                		if(pConnect.bUsedSocket) {
                			
                			out = pConnect.socket.getOutputStream();
                			dos = new DataOutputStream(out);
                			
                			dos.writeUTF("서버로부터의 메세지입니다.");
                			System.out.println(Comm.getTime() + " 데이터를 전송했습니다.");
                		}
                	}
                }
                
        	}
        } 
        catch (IOException e) {
        	
            e.printStackTrace();
        } // try - catch
        finally {
        	
        	try {
        		
        		dos.close();
                ios.close();
                
                // socket 반환 로직 추가 
                socket.close();
                pConnectionData.bUsedSocket = false;
        	}
        	catch(IOException socketEx) {
        		
        		socketEx.printStackTrace();
        	}
        	// 스트림과 소켓을 달아준다.
        }
		
		return false;
	}
}
