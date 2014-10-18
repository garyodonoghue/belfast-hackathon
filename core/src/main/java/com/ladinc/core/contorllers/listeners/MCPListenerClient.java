package com.ladinc.core.contorllers.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ladinc.core.contorllers.GamePadControls;
import com.ladinc.core.contorllers.GamePadControls.AnalogStick;
import com.ladinc.mcp.interfaces.MCPContorllersListener;

public class MCPListenerClient implements  MCPContorllersListener
{

	public static List<String> ids = new ArrayList<String>();
	
	public static GamePadControls gpc = new GamePadControls(null);
	
	@Override
	public void analogMove(int arg0, String arg1, float x, float y) 
	{		
		gpc.setAnalogMovementX(AnalogStick.left, x);
		gpc.setAnalogMovementY(AnalogStick.left, y);
		
	}

	@Override
	public void buttonDown(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buttonUp(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void orientation(int arg0, float arg1, float arg2, float arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pass(Map<String, String> header, Map<String, String> params,
			Map<String, String> files) 
	{
		
		
		String rating;
		String id;
		
		if(params != null)
		{
			rating = params.get("rating");
			id = params.get("id");
			
			if(!ids.contains(id))
			{
				ids.add(id);
			}
			
			//HackEventManager.recievedHackEvent(rating, id);
		}
		
	}

}
