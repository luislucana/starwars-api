package br.com.starwars.web.handler;

public class JsonResponseMessageBody {
	
	public JsonResponseMessageBody() {
	}
	
	public JsonResponseMessageBody(String message) {
		this.message = message;
	}
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
