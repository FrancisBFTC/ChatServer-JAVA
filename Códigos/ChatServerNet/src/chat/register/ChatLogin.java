package chat.register;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;


import chat.client.ChatClient;
import chat.cryptography.encryptAES;
import ftp.connection.FtpConnect;

public class ChatLogin {
	
	public javax.swing.JFrame window;
	public javax.swing.JLabel panel1, panel2, panel3, nameChat, labelUser, labelPass, register, login;
	public javax.swing.JLabel nameComputer, confirmPass;
	public javax.swing.JTextField inputUser, registerName, registerUser, registerPass, registerConfirm;
	public javax.swing.JPasswordField inputPass;
	public javax.swing.JButton button;
	public javax.swing.JButton registerButton;
	
	public String userText = "";
	public String passText;
	public String nameText = "";
	public String regUserText = "";
	public String regPassText = "";
	public String regConfText = "";
	public String nameComp = "";
	public FtpConnect connect;
	public encryptAES cryptoAes;
	public static String iv = "ChatServerA9Yc00";
    public String keys = "realitynoexists*";
    public String userLogin, passLogin;
    public BufferedReader bufferData;
    public boolean configuration = false;
    public boolean boolLogin = false;
	
	
	public ChatLogin(){
		window = new javax.swing.JFrame();
		window.setTitle("Chat Server - Loge-se");
		window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		window.setBounds(200, 200, 500, 400);
		window.setResizable(false);
		
		panel1 = new javax.swing.JLabel();
		panel1.setText("<html><div style='background:#0000ff;width:500;height:400;'></div></html>");
		panel1.setBounds(0, 0, 500, 400);
		window.add(panel1);
		
		nameChat = new javax.swing.JLabel();
		nameChat.setText("ChatServer");
		nameChat.setFont(new Font("Calibri", 1, 20));
		nameChat.setBounds(20, 20, 200, 30);
		nameChat.setForeground(Color.white);
		panel1.add(nameChat);
		
		panel2 = new javax.swing.JLabel();
		panel2.setText("<html><div style='background:#aaffff;width:500;height:400;'></div></html>");
		panel2.setBounds(0, 70, 500, 300);
		panel1.add(panel2);
		
		panel3 = new javax.swing.JLabel();
		panel3.setText("<html><div style='background:white;width:500;height:300;'></div></html>");
		panel3.setBounds(90, 50, 300, 200);
		panel3.setBorder(BorderFactory.createMatteBorder(10, 10, 40, 40, Color.decode("#0000ff")));
		//panel2.setBorder(BorderFactory.createLineBorder(Color.blue, 3, true));
		panel2.add(panel3);
		
		nameComputer = new javax.swing.JLabel();
		nameComputer.setText("Nome:");
		nameComputer.setBounds(30, 20, 50, 20);
		nameComputer.setForeground(Color.blue);
		nameComputer.setVisible(false);
		panel3.add(nameComputer);
		
		labelUser = new javax.swing.JLabel();
		labelUser.setText("Usuário:");
		labelUser.setBounds(30, 50, 50, 20);
		labelUser.setForeground(Color.blue);
		panel3.add(labelUser);
		
		labelPass = new javax.swing.JLabel();
		labelPass.setText("Senha:");
		labelPass.setBounds(30, 90, 50, 20);
		labelPass.setForeground(Color.blue);
		panel3.add(labelPass);
		
		confirmPass = new javax.swing.JLabel();
		confirmPass.setText("Confirme:");
		confirmPass.setBounds(30, 110, 60, 20);
		confirmPass.setForeground(Color.blue);
		confirmPass.setVisible(false);
		panel3.add(confirmPass);
		
		inputUser = new javax.swing.JTextField();
		inputUser.setBounds(90, 50, 150, 20);
		inputUser.setBorder(BorderFactory.createLineBorder(Color.blue, 2, true));
		inputUser.setForeground(Color.blue);
		inputUser.setVisible(true);
		panel3.add(inputUser);
		
		inputPass = new javax.swing.JPasswordField();
		inputPass.setBounds(90, 90, 150, 20);
		inputPass.setBorder(BorderFactory.createLineBorder(Color.blue, 2, true));
		inputPass.setForeground(Color.blue);
		inputPass.setVisible(true);
		panel3.add(inputPass);
		
		registerName = new javax.swing.JTextField();
		registerName.setBounds(90, 20, 150, 20);
		registerName.setBorder(BorderFactory.createLineBorder(Color.blue, 2, true));
		registerName.setForeground(Color.blue);
		registerName.setVisible(false);
		panel3.add(registerName);
		
		registerUser = new javax.swing.JTextField();
		registerUser.setBounds(90, 50, 150, 20);
		registerUser.setBorder(BorderFactory.createLineBorder(Color.blue, 2, true));
		registerUser.setForeground(Color.blue);
		registerUser.setVisible(false);
		panel3.add(registerUser);
		
		registerPass = new javax.swing.JTextField();
		registerPass.setBounds(90, 80, 150, 20);
		registerPass.setBorder(BorderFactory.createLineBorder(Color.blue, 2, true));
		registerPass.setForeground(Color.blue);
		registerPass.setVisible(false);
		panel3.add(registerPass);
		
		registerConfirm = new javax.swing.JTextField();
		registerConfirm.setBounds(90, 110, 150, 20);
		registerConfirm.setBorder(BorderFactory.createLineBorder(Color.blue, 2, true));
		registerConfirm.setForeground(Color.blue);
		registerConfirm.setVisible(false);
		panel3.add(registerConfirm);
		
		register = new javax.swing.JLabel();
		register.setText("Cadastre-se");
		register.setForeground(Color.blue);
		register.setBounds(30, 130, 70, 20);
		register.setCursor(new Cursor(Cursor.HAND_CURSOR));
		register.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.blue));
		register.setVisible(true);
		panel3.add(register);
		
		login = new javax.swing.JLabel();
		login.setText("Login");
		login.setForeground(Color.blue);
		login.setBounds(30, 135, 35, 20);
		login.setCursor(new Cursor(Cursor.HAND_CURSOR));
		login.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.blue));
		login.setVisible(false);
		panel3.add(login);
		
		button = new javax.swing.JButton();
		button.setBounds(160, 120, 80, 30);
		button.setText("Entrar");
		button.setForeground(Color.blue);
		button.setBackground(Color.decode("#aaffff"));
		button.setBorder(BorderFactory.createLineBorder(Color.blue, 1, true));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setVisible(true);
		panel3.add(button);
		
		registerButton = new javax.swing.JButton();
		registerButton.setBounds(170, 137, 70, 20);
		registerButton.setText("Cadastrar");
		registerButton.setForeground(Color.blue);
		registerButton.setBackground(Color.decode("#aaffff"));
		registerButton.setBorder(BorderFactory.createLineBorder(Color.blue, 1, true));
		registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		registerButton.setVisible(false);
		panel3.add(registerButton);
		
		
		button.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@SuppressWarnings("deprecation")
			@Override
			public void mousePressed(MouseEvent arg0) {
				userText = inputUser.getText();
				passText = inputPass.getText();
				String armazen = "";
				String user = "";
				String pass = "";
				
				window.setVisible(false);
				if(userText.isEmpty()){
					JOptionPane.showMessageDialog(null, "<html><font color='red'>O campo usuário está vazio!</font></html>");
				}else{
					if(passText.isEmpty()){
						JOptionPane.showMessageDialog(null, "<html><font color='red'>O campo senha está vazia!</font></html>");
					}else{
						
						try {
							connect = new FtpConnect();
							
							if(connect.connected || connect.userConnected){
								String ip = connect.getNetwork();
								ip = ip.replace("/", "");
								String fileLog = ip+".log";
								String getPath = new File("").getAbsolutePath();
								
								connect.changePath("logons");
								connect.download(fileLog, getPath+"/log/");
								connect.changePath("infos");
								FileReader fileRead = new FileReader(getPath+"/log/"+fileLog);
								BufferedReader bufferRead = new BufferedReader(fileRead);
								String readLine = bufferRead.readLine();
								char[] charLines;
								char[] charLines1;
								int dataOrder = 0;
								while(readLine != null){
									dataOrder = dataOrder + 1;
									armazen = "";
									int j = 0;
									int bytes = 0;
									charLines1 = readLine.toCharArray();
									for(int i = 0; i < readLine.length(); i++){
										if(charLines1[i] == ' ' && charLines1[i] != 'y'){
											bytes = bytes + 1;	
										}
									}
									byte[] value = new byte[bytes];
									charLines = readLine.toCharArray();
									int intChar = 0;
									for(int i = 0; i < readLine.length(); i++){
										
										if(charLines[i] == ' '){
												if(!armazen.equals("") && !armazen.equals("yy")){
													
													intChar = Integer.parseInt(armazen);
													value[j] = (byte) intChar;
													armazen = "";
													j = j + 1;
												}
											
										}else{
											armazen += charLines[i];
										}
									}
					
									
									try {
										String decrypted = encryptAES.decrypt(value, keys, iv);
										if(dataOrder == 1){
											nameComp = decrypted;
											System.out.println("Nome : "+nameComp);
										}else{
											if(dataOrder == 2){
												user = decrypted;
											}else{
												pass = decrypted;
												dataOrder = 0;
												
											
												
												
												if(userText.equals(user) && passText.equals(pass)){
													userLogin = user;
													passLogin = pass;
													connect.disconnect();
													new ChatClient();
													
												}else{
													JOptionPane.showMessageDialog(null, "<html><font color='red'>O login está incorreto!tente novamente!</font></html>");
													window.setVisible(true);
												}
												
											}
										}
										
										
										
									} catch (Exception e) {
										e.printStackTrace();
									}
									readLine = bufferRead.readLine();
								}
								
								bufferRead.close();
								fileRead.close();
							}else{
								JOptionPane.showMessageDialog(null, "<html><font color='red'>Não foi possível fazer o login! Verifique"
										+ "Sua conexão com a internet e tente novamente!</font></html>");
							}
							
							
							
						} catch (IOException e) {}
						
					}
				}
				
				
				
				
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				button.setForeground(Color.blue);
				button.setBorder(BorderFactory.createLineBorder(Color.blue, 1, true));
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				button.setForeground(Color.decode("#aaaaff"));
				button.setBorder(BorderFactory.createLineBorder(Color.decode("#aaaaff"), 1, true));
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		
		registerButton.addMouseListener(new MouseListener() {
			
			private FileWriter printFile;
			private FileWriter printData;
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				nameText = registerName.getText();
				regUserText = registerUser.getText();
				regPassText = registerPass.getText();
				regConfText = registerConfirm.getText();
				
				
				userLogin = regUserText;
				passLogin = regPassText;
				
				
				int sizeUser = regUserText.length();
				int sizePass = regPassText.length();
				
				
				if(sizeUser >= 4){
					if(sizePass >= 4){
						if(regPassText.equals(regConfText)){
							window.setVisible(false);
							try {
								connect = new FtpConnect();
								
								String ip = connect.getNetwork();
								ip = ip.replace("/", "");
								String fileLog = ip+".log";
								String getPath = new File("").getAbsolutePath();
								String armazen = "";
								String regUser = "";
								String regPass = "";
								String regName = "";
								try{
									byte[] encrypted = encryptAES.encrypt(regUserText, keys, iv);
								
									for(int i = 0; i < encrypted.length; i++){
										armazen += encrypted[i]+" ";
									}
									regUser= armazen+"yy";
									
									armazen = "";
									byte[] encrypt = encryptAES.encrypt(regPassText, keys, iv);
									
									for(int i = 0; i < encrypt.length; i++){
										armazen += encrypt[i]+" ";
									}
									regPass = armazen+"yy";
									armazen = "";
									
									byte[] encrypts = encryptAES.encrypt(nameText, keys, iv);
									
									for(int i = 0; i < encrypts.length; i++){
										armazen += encrypts[i]+" ";
									}
									regName = armazen+"yy";
									armazen = "";
									
								}catch(Exception e){}
								printFile = new FileWriter(getPath+"/log/"+fileLog);
								printFile.write(regName+"\r\n");
								printFile.write(regUser+"\r\n");
								printFile.write(regPass+"\r\n");
								printFile.close();
								
								String dataSave = "%"+nameText+"$"+ip+"&.info";
								printData = new FileWriter(getPath+"/log/"+dataSave);
								printData.write("Status{off}Status, Blocks{}Blocks, Blocked{no}Blocked");
								printData.close();
								
								
								connect.upload(fileLog, getPath+"/log/");
								connect.changePath("infos");
								connect.upload(dataSave, getPath+"/log/");

								connect.disconnect();
								
								
								
								try {
									Runtime.getRuntime().exec("CMD /c del log\\"+fileLog);
									//Runtime.getRuntime().exec("CMD /c del log\\DataSave.php");
								} catch (IOException run) {
									
									run.printStackTrace();
								}
								if(connect.SuccessUp){
									if(!configuration){
										JOptionPane.showMessageDialog(null, "<html><font color='blue'>Cadastro efetuado com sucesso! "
												+ "Faça o seu login.</font></html>");
										window.setVisible(true);
									}else{
										JOptionPane.showMessageDialog(null, "<html><font color='blue'>Dados alterados com sucesso! "
												+ "Faça o seu login.</font></html>");
										window.setVisible(false);
									}
									
										
									
								}
								
							} catch (IOException e) {
								String exception = e.getMessage();
								JOptionPane.showMessageDialog(null, "<html><font color='red'>"+exception+"</font></html>");
								window.setVisible(true);
							} 
						}else{
							JOptionPane.showMessageDialog(window, "<html><font color='red'>As senhas não se combinam na confirmação!</font></html>");

						}
					}else{
						JOptionPane.showMessageDialog(window, "<html><font color='red'>Senha no mínimo 4 caracteres</font></html>");

					}
				}else{
					JOptionPane.showMessageDialog(window, "<html><font color='red'>Usuário no mínimo 4 caracteres</font></html>");
				}
				
				boolLogin = true;
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				registerButton.setForeground(Color.blue);
				registerButton.setBorder(BorderFactory.createLineBorder(Color.blue, 1, true));
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				registerButton.setForeground(Color.decode("#aaaaff"));
				registerButton.setBorder(BorderFactory.createLineBorder(Color.decode("#aaaaff"), 1, true));
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		inputUser.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {}
			
			@Override
			public void keyReleased(KeyEvent arg0) {}
			
			@Override
			public void keyPressed(KeyEvent key) {
				int code = key.getKeyCode();
				if(code == KeyEvent.VK_ENTER){
					userText = inputUser.getText();
					passText = inputPass.getText();
					String armazen = "";
					String user = "";
					String pass = "";
					
					
					if(userText.isEmpty()){
						JOptionPane.showMessageDialog(null, "<html><font color='red'>O campo usuário está vazio!</font></html>");
					}else{
						if(passText.isEmpty()){
							JOptionPane.showMessageDialog(null, "<html><font color='red'>O campo senha está vazia!</font></html>");
						}else{
							window.setVisible(false);
							try {
								connect = new FtpConnect();
								
								if(connect.connected || connect.userConnected){
									String ip = connect.getNetwork();
									ip = ip.replace("/", "");
									String fileLog = ip+".log";
									String getPath = new File("").getAbsolutePath();
									
									connect.changePath("logons");
									connect.download(fileLog, getPath+"/log/");
									connect.changePath("infos");
									FileReader fileRead = new FileReader(getPath+"/log/"+fileLog);
									BufferedReader bufferRead = new BufferedReader(fileRead);
									String readLine = bufferRead.readLine();
									char[] charLines;
									char[] charLines1;
									int dataOrder = 0;
									while(readLine != null){
										dataOrder = dataOrder + 1;
										armazen = "";
										int j = 0;
										int bytes = 0;
										charLines1 = readLine.toCharArray();
										for(int i = 0; i < readLine.length(); i++){
											if(charLines1[i] == ' ' && charLines1[i] != 'y'){
												bytes = bytes + 1;	
											}
										}
										byte[] value = new byte[bytes];
										charLines = readLine.toCharArray();
										int intChar = 0;
										for(int i = 0; i < readLine.length(); i++){
											
											if(charLines[i] == ' '){
													if(!armazen.equals("") && !armazen.equals("yy")){
														
														intChar = Integer.parseInt(armazen);
														value[j] = (byte) intChar;
														armazen = "";
														j = j + 1;
													}
												
											}else{
												armazen += charLines[i];
											}
										}
						
										
										try {
											String decrypted = encryptAES.decrypt(value, keys, iv);
											if(dataOrder == 1){
												nameComp = decrypted;
												System.out.println("Nome : "+nameComp);
											}else{
												if(dataOrder == 2){
													user = decrypted;
												}else{
													pass = decrypted;
													dataOrder = 0;
													
												
													
													
													if(userText.equals(user) && passText.equals(pass)){
														userLogin = user;
														passLogin = pass;
														connect.disconnect();
														new ChatClient();
														
													}else{
														JOptionPane.showMessageDialog(null, "<html><font color='red'>O login está incorreto!tente novamente!</font></html>");
														window.setVisible(true);
													}
													
												}
											}
											
											
											
										} catch (Exception e) {
											e.printStackTrace();
										}
										readLine = bufferRead.readLine();
									}
									
									bufferRead.close();
									fileRead.close();
								}else{
									JOptionPane.showMessageDialog(null, "<html><font color='red'>Não foi possível fazer o login! Verifique"
											+ "Sua conexão com a internet e tente novamente!</font></html>");
								}
								
								
								
							} catch (IOException e) {}
							
						}
					}
				}
			}
		});
		
		inputPass.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				if(code == KeyEvent.VK_ENTER){
					userText = inputUser.getText();
					passText = inputPass.getText();
					String armazen = "";
					String user = "";
					String pass = "";
					
					
					if(userText.isEmpty()){
						JOptionPane.showMessageDialog(null, "<html><font color='red'>O campo usuário está vazio!</font></html>");
					}else{
						if(passText.isEmpty()){
							JOptionPane.showMessageDialog(null, "<html><font color='red'>O campo senha está vazia!</font></html>");
						}else{
							window.setVisible(false);
							try {
								connect = new FtpConnect();
								
								if(connect.connected || connect.userConnected){
									String ip = connect.getNetwork();
									ip = ip.replace("/", "");
									String fileLog = ip+".log";
									String getPath = new File("").getAbsolutePath();
									
									connect.changePath("logons");
									connect.download(fileLog, getPath+"/log/");
									connect.changePath("infos");
									FileReader fileRead = new FileReader(getPath+"/log/"+fileLog);
									BufferedReader bufferRead = new BufferedReader(fileRead);
									String readLine = bufferRead.readLine();
									char[] charLines;
									char[] charLines1;
									int dataOrder = 0;
									while(readLine != null){
										dataOrder = dataOrder + 1;
										armazen = "";
										int j = 0;
										int bytes = 0;
										charLines1 = readLine.toCharArray();
										for(int i = 0; i < readLine.length(); i++){
											if(charLines1[i] == ' ' && charLines1[i] != 'y'){
												bytes = bytes + 1;	
											}
										}
										byte[] value = new byte[bytes];
										charLines = readLine.toCharArray();
										int intChar = 0;
										for(int i = 0; i < readLine.length(); i++){
											
											if(charLines[i] == ' '){
													if(!armazen.equals("") && !armazen.equals("yy")){
														
														intChar = Integer.parseInt(armazen);
														value[j] = (byte) intChar;
														armazen = "";
														j = j + 1;
													}
												
											}else{
												armazen += charLines[i];
											}
										}
						
										
										try {
											String decrypted = encryptAES.decrypt(value, keys, iv);
											if(dataOrder == 1){
												nameComp = decrypted;
												System.out.println("Nome : "+nameComp);
											}else{
												if(dataOrder == 2){
													user = decrypted;
												}else{
													pass = decrypted;
													dataOrder = 0;
													
												
													
													
													if(userText.equals(user) && passText.equals(pass)){
														userLogin = user;
														passLogin = pass;
														connect.disconnect();
														new ChatClient();
														
													}else{
														JOptionPane.showMessageDialog(null, "<html><font color='red'>O login está incorreto!tente novamente!</font></html>");
														window.setVisible(true);
													}
													
												}
											}
											
											
											
										} catch (Exception e3) {
											e3.printStackTrace();
										}
										readLine = bufferRead.readLine();
									}
									
									bufferRead.close();
									fileRead.close();
								}else{
									JOptionPane.showMessageDialog(null, "<html><font color='red'>Não foi possível fazer o login! Verifique"
											+ "Sua conexão com a internet e tente novamente!</font></html>");
								}
								
								
								
							} catch (IOException e3) {}
							
						}
					}
				}
			}
		});
		
		register.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				register.setVisible(false);
				login.setVisible(true);
				inputUser.setVisible(false);
				inputPass.setVisible(false);
				button.setVisible(false);
				registerName.setVisible(true);
				registerUser.setVisible(true);
				registerPass.setVisible(true);
				nameComputer.setVisible(true);
				confirmPass.setVisible(true);
				registerConfirm.setVisible(true);
				registerButton.setVisible(true);
				labelPass.setBounds(30, 80, 50, 20);
				window.setTitle("Chat Server - Cadastre-se");
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				register.setForeground(Color.blue);
				register.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.blue));
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				register.setForeground(Color.decode("#aaaaff"));
				register.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#aaaaff")));
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		
		login.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {
				login.setVisible(false);
				register.setVisible(true);
				inputUser.setVisible(true);
				inputPass.setVisible(true);
				button.setVisible(true);
				registerName.setVisible(false);
				registerUser.setVisible(false);
				registerPass.setVisible(false);
				nameComputer.setVisible(false);
				confirmPass.setVisible(false);
				registerConfirm.setVisible(false);
				registerButton.setVisible(false);
				labelPass.setBounds(30, 90, 50, 20);
				window.setTitle("Chat Server - Loge-se");
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				login.setForeground(Color.blue);
				login.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.blue));
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				login.setForeground(Color.decode("#aaaaff"));
				login.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#aaaaff")));

			}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
	}
	
	public static byte[] encrypt(String text, String key) throws Exception{
		Cipher encrypt = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
		encrypt.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv.getBytes()));
		return encrypt.doFinal(text.getBytes());
	}
	
	public static String decrypt(byte[] decrypted, String key) throws Exception{
        Cipher decript = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec SecretKey = new SecretKeySpec(key.getBytes(), "AES");
        decript.init(Cipher.DECRYPT_MODE, SecretKey,new IvParameterSpec(iv.getBytes()));
        return new String(decript.doFinal(decrypted));
	 }
	
	
}
