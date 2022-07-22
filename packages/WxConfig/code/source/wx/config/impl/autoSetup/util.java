package wx.config.impl.autoSetup;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.util.JournalLogger;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.softwareag.util.IDataMap;
// --- <<IS-END-IMPORTS>> ---

public final class util

{
	// ---( internal utility methods )---

	final static util _instance = new util();

	static util _newInstance() { return new util(); }

	static util _cast(Object o) { return (util)o; }

	// ---( server methods )---




	public static final void replaceReferencesWithValues (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(replaceReferencesWithValues)>> ---
		// @sigtype java 3.5
		// [i] field:0:optional wxConfigPkgName
		// [i] record:0:required autoSetupCfg
		// [o] record:0:required autoSetupCfg
		IDataMap pipeMap = new IDataMap(pipeline);
		
		String wxConfigPkgName = pipeMap.getAsString("wxConfigPkgName");
		IData autoSetupCfg = pipeMap.getAsIData("autoSetupCfg");
		
		replaceReferencesWithValuesRecursive(wxConfigPkgName, autoSetupCfg); 
			
			
			
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	public static IData replaceReferencesWithValuesRecursive(String wxConfigPkgName, IData iData) {
		IDataMap iDataMap = new IDataMap(iData);
		
		Set<String> keySet = iDataMap.keySet();
		for (String key : keySet) {
			Object object = iDataMap.get(key);
			
			if (object instanceof String) {
				String valueString = (String) object;
				Pattern pattern = Pattern.compile("\\$\\{([^\\$\\{\\}]*)\\}");
				Matcher matcher = pattern.matcher(valueString);
				while (matcher.find()) {
					int groupCount = matcher.groupCount();
					if (groupCount == 1) {
						String extractedValueString = matcher.group(1);
						//debugLogInfo("extractedValueString: " + extractedValueString);
						Pattern patternPkg = Pattern.compile("pkg:([^;]*);(.*)");
						Matcher matcherPkg = patternPkg.matcher(extractedValueString);
						
						String pkgName = null;
						String keyName = null;
						if (matcherPkg.find()) {
							// ${pkg:...;...}
							//debugLogInfo("replace: pkg");
							pkgName = matcherPkg.group(1);
							keyName = matcherPkg.group(2);
						} else {
							pkgName = wxConfigPkgName;
							keyName = extractedValueString;
						}
						//debugLogInfo("replace: " + pkgName + ", " + keyName);
						
						String value = getValue(pkgName, keyName, "true",  null,  null,  null,  null);
						//debugLogInfo("value: " + value);
						// value not configured? -> use empty string
						if (value == null) {
							value = "";
						}
						valueString = valueString.replace(matcher.group(), value);
						iDataMap.put(key, valueString);
					}
				}
			} else if (object instanceof IData) {
				replaceReferencesWithValuesRecursive(wxConfigPkgName, (IData) object);
			} else if (object instanceof IData[]) {
				for(IData iDataOfArray : (IData[]) object) {
					replaceReferencesWithValuesRecursive(wxConfigPkgName, iDataOfArray);
				}
			}
		}
	
		return iData;
	}
	
	public static String getValue(String wxConfigPkgName, String key, String noServiceException, String substituteVariables,
			String ignoreGlobalValues, String scanAllConfigurations,
			String noVariableInterpolation) {
		
		String propertyValue = " ";
		// input
		IData configParam = IDataFactory.create();
		IDataCursor configParamCursor = configParam.getCursor();
		IDataUtil.put(configParamCursor, "key", key);
		IDataUtil.put(configParamCursor, "wxConfigPkgName", wxConfigPkgName);
		IDataUtil.put(configParamCursor, "noServiceException", noServiceException);
		configParamCursor.destroy();
	
		// output
		IData configOutput = IDataFactory.create();
		try {
			configOutput = Service.doInvoke("wx.config.pub", "getValue", configParam);
		} catch (Exception e) {
			return propertyValue;
		}
		IDataCursor configOutputCursor = configOutput.getCursor();
		propertyValue = IDataUtil.getString(configOutputCursor, "propertyValue");
		configOutputCursor.destroy();
		
		return propertyValue;
	}
	
			
	private static final String LOG_FUNCTION = "WxConfigLight";
	private static void debugLogError(String message) {
	    JournalLogger.log(4,  JournalLogger.FAC_FLOW_SVC, JournalLogger.ERROR, LOG_FUNCTION, message);
	}
	
	private static void debugLogInfo(String message) {
	    JournalLogger.log(4,  JournalLogger.FAC_FLOW_SVC, JournalLogger.INFO, LOG_FUNCTION, message);
	}
		
		
		
	// --- <<IS-END-SHARED>> ---
}

