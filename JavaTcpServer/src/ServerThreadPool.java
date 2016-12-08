import java.net.Socket;
import java.util.Vector;

import ServerDataProcessors.ServerThread;
import commUtil.Comm;
import connectionPool.TcpConnectionPool;
import connectionPool.model.ConnectionPoolData;


public class ServerThreadPool 
{
	private Vector<ServerThread> erPool = null;
	private static ServerThreadPool g_obj = null;
	
	public static ServerThreadPool getInstance()
	{
		if(g_obj == null) {
			
			g_obj = new ServerThreadPool();
		}
		
		return g_obj;
	}
	
	public ServerThreadPool()
	{
		super();
		
		this.erPool = new Vector<ServerThread>();
		
		
	}
	
	public void useThread(Socket socket, ConnectionPoolData pConnectionData, TcpConnectionPool sockets)
	{
		if(this.erPool.size() < Comm.maximumThreadCount ) {
			
			ServerThread pServerThread = new ServerThread(); 
			erPool.addElement(pServerThread);
			
			pServerThread.useThread(socket, pConnectionData, sockets);
		}
	}
	
	public ServerThread getServerThread()
	{
		
		return null;
	}
}
