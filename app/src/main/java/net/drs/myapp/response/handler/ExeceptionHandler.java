package net.drs.myapp.response.handler;

import java.util.Date;

public class ExeceptionHandler {
	  private Date timestamp;
	  private String message;
	  private String details;

	  public ExeceptionHandler(Date timestamp, String message, String details) {
	    super();
	    this.timestamp = timestamp;
	    this.message = message;
	    this.details = details;
	  }

	  public Date getTimestamp() {
	    return timestamp;
	  }

	  public String getMessage() {
	    return message;
	  }

	  public String getDetails() {
	    return details;
	  }

	}