package com.fort.webapp.sshd.monitor.pool;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MonitorPool {
	
	public static final Map<String,Map<String,MonitorIo>> pool = Collections.synchronizedMap(new HashMap<String,Map<String,MonitorIo>>()); 
	
}
