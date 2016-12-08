package commUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Comm 
{
	static final public int  maximumConnectionCount = 10;
	static final public int  maximumThreadCount     = 10;
	static final public int  serverPort     = 50013;
	static final public long ServerTimeWait = 10 * 1000; // <- client wait time
	
	static public String getTime() 
	{
        SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
        return f.format(new Date());
    }
}
