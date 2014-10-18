package com.ladinc.core.contorllers;

import java.util.ArrayList;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.ladinc.core.contorllers.listeners.KeyboardAndMouseListener;
import com.ladinc.core.contorllers.listeners.MCPListenerClient;
import com.ladinc.core.contorllers.listeners.desktop.XboxListener;
import com.ladinc.mcp.MCP;
import com.ladinc.mcp.RedirectOption;

public class MyControllerManager {

	public ArrayList<IControls> inActiveControls;
	public ArrayList<IControls> controls;
	
//	public ArrayList<Identifier> usedIdentifiers;
//	public ArrayList<Identifier> orderedIdentifiers;
	
	public MCP moreControllers;
	
	public String ipAddr;
	
	public MyControllerManager()
	{
		inActiveControls = new ArrayList<IControls>();
		controls = new ArrayList<IControls>();
		
		setUpControls();
//		resetIdentifiers();
		
		setUpMCP();
	}
	
	private void setUpMCP()
    {
    	moreControllers = MCP.tryCreateAndStartMCPWithPort(8888);
    	
    	//Clear redirectOptions
    	moreControllers.redirectOptions.clear();

    	//Add Our Page
    	moreControllers.redirectOptions.add(new RedirectOption("postman", "Postman"));
        
        ipAddr = moreControllers.getAddressForClients();
        if(ipAddr.equals(":8888"))
        {
        	ipAddr = "No Network";
        }
        
        Gdx.app.error("Main-MCP", "Connection Address: " + ipAddr);
        
//        HackEventManager.moreControllers = moreControllers;
//        
       moreControllers.addMCPListener(new MCPListenerClient());
    }
	
//	public void resetIdentifiers()
//	{
//		usedIdentifiers = new ArrayList<Identifier>();
//		orderedIdentifiers = new ArrayList<Identifier>();
//		orderedIdentifiers.add(Identifier.red);
//		orderedIdentifiers.add(Identifier.blue);
//		orderedIdentifiers.add(Identifier.yellow);
//		orderedIdentifiers.add(Identifier.green);
//		orderedIdentifiers.add(Identifier.orange);
//		orderedIdentifiers.add(Identifier.darkblue);
//		orderedIdentifiers.add(Identifier.purple);
//		
//		
//	}
	
//	public Identifier getNextAvailableIdentifer()
//	{
//		
//		
//		for (Identifier ident : orderedIdentifiers) 
//		{
//			 if(!usedIdentifiers.contains(ident))
//			 {
//				 usedIdentifiers.add(ident);
//				 return ident;
//			 }
//		}
//		
//		//All previous identifiers must be used
//		return Identifier.red;
//	}
	
	private void setUpControls()
	{
		this.controls.clear();
		this.inActiveControls.clear();
    	
        for (Controller controller : Controllers.getControllers()) {
            Gdx.app.debug("ControllerManager", "setUpControls - " + controller.getName());
            addControllerToList(controller);
            

        }
        if(Gdx.app.getType() == ApplicationType.Desktop)
        {
        	KeyboardAndMouseListener inputProcess = new KeyboardAndMouseListener();
        	inActiveControls.add(inputProcess.controls);
        	Gdx.input.setInputProcessor(inputProcess);
        }
	}
	
	public void addControllerToList(Controller controller)
    {
		switch (Gdx.app.getType())
		{
			case Desktop:
				Gdx.app.debug("ControllerManager", "addControllerToList - Desktop");
				
				Gdx.app.debug("ControllerManager", "Added Listener for Windows Xbox Controller");
	        	
				XboxListener desktopListener = new XboxListener();
	            controller.addListener(desktopListener);
	            inActiveControls.add(desktopListener.controls);
	            
				break;
			
		
		}
    }
	
	public boolean checkForNewControllers()
	{
		ArrayList<IControls> tempControllers = (ArrayList<IControls>) inActiveControls.clone();
		boolean foundNew = false;
		
		for (IControls cont : tempControllers) 
		{
			if(cont.isActive())
			{
				if(!this.controls.contains(cont))
				{
//					cont.setIdentifier(getNextAvailableIdentifer());
					this.controls.add(cont);
					this.inActiveControls.remove(cont);
					foundNew = true;
				}
			}
		}
		
		return foundNew;
	}
	
}