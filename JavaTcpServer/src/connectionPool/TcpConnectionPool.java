package connectionPool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import commUtil.Comm;
import connectionPool.model.ConnectionPoolData;

public class TcpConnectionPool 
{
	public Vector<ConnectionPoolData> erServerSocket = null;
	public ServerSocket serverSocket = null;	
	
	public TcpConnectionPool()
	{
		System.out.println("최대 커넥션 풀 카운트 : " + Comm.maximumConnectionCount);
		
		try {
			this.serverSocket = new ServerSocket(Comm.serverPort);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
		this.erServerSocket = new Vector<ConnectionPoolData>();
		
		for(int i = 0; i < Comm.maximumConnectionCount; i++) {
			
			// 서버소켓을 생성하고 5001번 포트와 결합(bind) 시킨다.
			ConnectionPoolData connectionData = new ConnectionPoolData();
			this.erServerSocket.addElement(connectionData);
			connectionData.pstrConnectId = new Integer(i).toString();
			
			System.out.println(Comm.getTime() + " ready connection id : " + connectionData.pstrConnectId);
		}
		
		System.out.println(Comm.getTime() + " port number : " + Comm.serverPort + " 서버가 준비되었습니다.");
	}
	
	public ServerSocket getServerSocket()
	{
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(Comm.serverPort);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return serverSocket;
	}
	
	public void releaseSocket(Socket socket)
	{
		for(int i = 0; i < erServerSocket.size(); i++) {
			
//			erServerSocket[i];
		}
	}
	
	public ConnectionPoolData getSocket()
	{
		ConnectionPoolData pData = null;
		
		for(int i = 0; i < erServerSocket.size(); i++) {
			
			pData = erServerSocket.elementAt(i);
			
			if(!pData.bUsedSocket) {
				
				break;
			}
		}
		
		return pData;
	}
}
