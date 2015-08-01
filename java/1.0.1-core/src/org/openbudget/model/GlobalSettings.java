package org.openbudget.model;

import java.io.Serializable;

public class GlobalSettings  implements Serializable {
	
	public GlobalSettings(String currentVersion, String[] formatTypes) {
		this.currentVersion = currentVersion;
		this.formatTypes = formatTypes;
	}
	
	/**
	 * Current version of OBF converter that is applicable. User should choose correct version. 
	 * Previous versions of converter also are available.
	 * Must be defined in Realization: e.g. currentVersion=1
	 */
	protected String currentVersion;
	
	/**
	 * Must be defined in Realization: e.g. formatTypes = {"csv"}
	 */
	protected String[] formatTypes;
	
	

	public String[] getListAvailableFormatTypes(){
		return formatTypes;
	}
	
	public String getCurrentVersion() {
		return currentVersion;
	}

	protected void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}

	protected void setFormatTypes(String[] formatTypes) {
		this.formatTypes = formatTypes;
	}
	
	

}
