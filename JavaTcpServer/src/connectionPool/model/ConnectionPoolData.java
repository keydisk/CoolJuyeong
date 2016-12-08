package connectionPool.model;

import java.net.Socket;

public class ConnectionPoolData 
{
	public Socket socket        = null;
	public String pstrConnectId = "";
	public Boolean bUsedSocket  = false;
	
	public ConnectionPoolData()
	{
		
	}
	
	public void setSocket(Socket socket)
	{
		this.socket = socket;
		this.bUsedSocket = true;
	}
}
