package com.domain;

public class User {

	public static int getId() {
		return id;
	}
	public static void setId(int id) {
		User.id = id;
	}
	public static String getUser_name() {
		return user_name;
	}
	public static void setUser_name(String user_name) {
		User.user_name = user_name;
	}
	public static String getUser_email() {
		return user_email;
	}
	public static void setUser_email(String user_email) {
		User.user_email = user_email;
	}
	public static String getUser_company() {
		return user_company;
	}
	public static void setUser_company(String user_company) {
		User.user_company = user_company;
	}
	public static String getUser_school() {
		return user_school;
	}
	public static void setUser_school(String user_school) {
		User.user_school = user_school;
	}
	
	public static String getUser_password() {
		return user_password;
	}
	public static void setUser_password(String user_password) {
		User.user_password = user_password;
	}
		public static int id= 1;
		public static String user_password = null;
		public static String user_name = null;
		public static String user_email = null;
		public static String user_company = null;
		public static String user_school = null;
}
