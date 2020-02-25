package chat.register;

public class Account {
	public static String userLogin;
	public static String passLogin;
	public static String nameLogin;
	static ChatLogin chat;
	
	public static void login(){
		chat = new ChatLogin();
		nameLogin = chat.nameComp;
		userLogin = chat.userLogin;
		passLogin = chat.passLogin;
	}
}
