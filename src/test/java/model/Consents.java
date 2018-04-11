package model;

public class Consents 
{
  private String consentType;
  private String consentValue;
  private String lastUpdatedBy;
  private String consentMessage;
  
  
 /* public Consents( String consentType , String consentVal , String  lastUpdatedBy ,String consentMessage){
	  
	  consentType1 = consentType;
	 consentVal2 = consentVal;
	 lastUpdatedBy3 = lastUpdatedBy;
	 consentMessage4 = consentMessage;
	  
  }
  */
  
public String getConsentType() {
	return consentType;
}

public void setConsentType(String consentType) {
	this.consentType = consentType;
}

public String getConsentValue() {
	return consentValue;
}

public void setConsentValue(String consentValue) {
	this.consentValue = consentValue;
}

public String getLastUpdatedBy() {
	return lastUpdatedBy;
}
public void setLastUpdatedBy(String lastUpdatedBy) {
	this.lastUpdatedBy = lastUpdatedBy;
}


public String getConsentMessage() {
	return consentMessage;
}
public void setConsentMessage(String consentMessage) {
	this.consentMessage = consentMessage;
}
}
