package wx.config.impl.util;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.softwareag.util.IDataMap;
// --- <<IS-END-IMPORTS>> ---

public final class system

{
	// ---( internal utility methods )---

	final static system _instance = new system();

	static system _newInstance() { return new system(); }

	static system _cast(Object o) { return (system)o; }

	// ---( server methods )---




	public static final void sleep (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(sleep)>> ---
		// @sigtype java 3.5
		// [i] field:0:required milliseconds
		IDataCursor pipelineCursor = pipeline.getCursor();
		Integer sleepTimeInMilliSeconds = IDataUtil.getInt(pipelineCursor, "milliseconds", -1);
		pipelineCursor.destroy();
		
		if (sleepTimeInMilliSeconds != -1){
		try {
				Thread.sleep(sleepTimeInMilliSeconds);
			} catch (InterruptedException e) {
				throw new ServiceException(e);
			}
		}
		// --- <<IS-END>> ---

                
	}
}

