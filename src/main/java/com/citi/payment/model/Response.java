package com.citi.payment.model;

import java.util.ArrayList;

public class Response {

	ArrayList<Object> data;
	ArrayList<Object> error;

	public Response() {
		super();
		data = new ArrayList<>();
		error = new ArrayList<>();
	}

	public ArrayList<Object> getData() {
		return data;
	}

	public void addDataObject(Object data) {
		this.data.add(data);
	}

	public void setData(ArrayList<Object> data) {
		this.data = data;
	}

	public ArrayList<Object> getError() {
		return error;
	}

	public void setError(Object error) {
		this.error.add(error);
	}

}
