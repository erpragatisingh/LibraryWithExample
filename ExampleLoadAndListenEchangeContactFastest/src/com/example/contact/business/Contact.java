package com.example.contact.business;

public class Contact {
	private String contact_id;
	private String contact_name;
	private String phone;
	private int phone_id;
	private String avatarClient;
	public Contact(String contact_id,  String contact_name,
			String phone, int phone_id,
			String avatarClient) {
		this.contact_id = contact_id;
		this.contact_name = contact_name;
		this.phone = phone;
		this.phone_id = phone_id;
		this.avatarClient = avatarClient;
		
	}
	public String getContact_id() {
		return contact_id;
	}
	public void setContact_id(String contact_id) {
		this.contact_id = contact_id;
	}
	public String getContact_name() {
		return contact_name;
	}
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getPhone_id() {
		return phone_id;
	}
	public void setPhone_id(int phone_id) {
		this.phone_id = phone_id;
	}
	
	public String getAvatarClient() {
		return avatarClient;
	}
	public void setAvatarClient(String avatarClient) {
		this.avatarClient = avatarClient;
	}
	
	
	

}
