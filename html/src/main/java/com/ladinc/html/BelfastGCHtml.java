package com.ladinc.html;

import com.ladinc.core.BelfastGC;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class BelfastGCHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new BelfastGC();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
