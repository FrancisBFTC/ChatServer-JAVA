package chat.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.server.SocketSecurityException;

import chat.cryptography.encryptAES;
import chat.register.ChatLogin;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

import ftp.connection.FtpConnect;

public class ChatClient extends JFrame{
	
	/**
	 * 
	 */
	//Variaveis e objetos do sistema
	private static final long serialVersionUID = 1L;
	public BufferedReader buffer;
	public BufferedReader bufferData;
	public String getPath = new File("").getAbsolutePath();
	public String saveChars1 = "", saveChars2 = "";
	public FtpConnect connect;
	public String[] listComp1 = new String[20];
	public String nameComputer = "";
	public String ipComputer = "";
	public boolean status = true;
	public static String iv = "ChatServerA9Yc00";
    public String keys = "realitynoexists*";
    public String nameComp = "";
    public String userLogin;
    public String passLogin;
	private BufferedReader bufferRead;
	public JLabel[] divComputers = new JLabel[20];
	public JLabel[] computers = new JLabel[20];
	public JLabel[] statusUser = new JLabel[20];
	public JFrame[] frameComputers = new JFrame[20];
	public JLabel[] labelBody = new JLabel[20];
	public JLabel[] labelBottom = new JLabel[20];
	public JButton[] buttomBottom = new JButton[20];
	public JTextField[] inputText = new JTextField[20];
	public JLabel[] labelBlockUser = new JLabel[20];
	public JLabel[] labelMsgUser = new JLabel[20];
	public JLabel[] labelDocUser = new JLabel[20];
	public JLabel[] labelSoundUser = new JLabel[20];
	public JEditorPane[] editorChat = new JEditorPane[20];
	public JScrollPane[] scrollChat = new JScrollPane[20];
	public JLabel[] italic = new JLabel[20];
	public JLabel[] strong = new JLabel[20];
	public JLabel[] underlined = new JLabel[20];
	public JLabel[] linked = new JLabel[20];
	public JLabel[] emoji1 = new JLabel[20];
	public JLabel[] emoji2 = new JLabel[20];
	public JLabel[] emoji3 = new JLabel[20];
	public JLabel[] emoji4 = new JLabel[20];
	public JLabel[] emoji5 = new JLabel[20];
	public JLabel[] emoji6 = new JLabel[20];
	public String[] arrayIps = new String[20];
	public String[] arrayComps = new String[20];
	public String mailBases = "";
	public String verifyStatus = "";
	public String[] mensagesBase = new String[20];
	public String[] atualStatus = new String[20];
	public ImageIcon userOn, userOff;
	public boolean userInside = false;
	private MouseListener CompListener;
	private MouseListener BlockListener;
	
	
	private JFrame[] frameMsg = new JFrame[20];
	private JLabel[] panelMsg = new JLabel[20];
	private JLabel[] from = new JLabel[20];
	private JLabel[] to = new JLabel[20];
	private JLabel[] myRecurses = new JLabel[20];
	private JLabel[] italics = new JLabel[20];
	private JLabel[] strongs = new JLabel[20];
	private JLabel[] underlineds = new JLabel[20];
	private JLabel[] linkeds = new JLabel[20];
	private JLabel[] emoji1s = new JLabel[20];
	private JLabel[] emoji2s = new JLabel[20];
	private JLabel[] emoji3s = new JLabel[20];
	private JLabel[] emoji4s = new JLabel[20];
	private JLabel[] emoji5s = new JLabel[20];
	private JLabel[] emoji6s = new JLabel[20];
	private JLabel[] labelWrite = new JLabel[20];
	private JLabel[] labelRead = new JLabel[20];
	private JLabel[] labelSend = new JLabel[20];
	private JButton[] deleteMsg = new JButton[500];
	private JEditorPane[] editorMail = new JEditorPane[20];
	private JScrollPane[] scrollMail = new JScrollPane[20];
	private JEditorPane[] editorMail1 = new JEditorPane[20];
	private JScrollPane[] scrollMail1 = new JScrollPane[20];
	private JLabel[] labelNotifyers = new JLabel[20];
	private boolean removeListener = false, removeListener1 = false;
	public int serverNumber = 0;
	//Método Thread do Servidor do chat (Lado servidor)
	public Runnable server1 = new Runnable(){
		@Override
		public void run(){
			while(true){
				try {
					ServerSocket server = new ServerSocket(7171);
					Socket socketReceive = server.accept();
					++serverNumber;
					System.out.println("#"+serverNumber+" - O computador "+socketReceive.getInetAddress()+" Se conectou ao Servidor 7171");
					byte[] byteReceive = new byte[socketReceive.getReceiveBufferSize()];
					
					BufferedInputStream input = new BufferedInputStream(socketReceive.getInputStream());
					input.read(byteReceive);
					
					String byteToString = new String(byteReceive, "ISO-8859-1");
					
						String idReceive = byteToString.substring(byteToString.indexOf("[id{")+4, byteToString.lastIndexOf("}id]"));
						
						int num = 0;
						while(!arrayComps[num].equals(idReceive)){num = num + 1;}
							byteToString = byteToString.replace("[id{"+idReceive+"}id]", "");
							if(byteToString.contains("file:\\")){
							String RepDirEmot = byteToString.substring(byteToString.indexOf("file:\\")+6, byteToString.lastIndexOf("\\imgs\\"));
							byteToString = byteToString.replace(RepDirEmot, getPath);
							}
							String getChat = editorChat[num].getText().replace("<html>", "").replace("</html>", "")
								.replace("<head>", "").replace("</head>", "").replace("<body>", "")
								.replace("</body>", "");
							editorChat[num].setText(getChat+"<br><font color='blue'>"+idReceive+" diz</font> : "+byteToString);
						
							
					
						
					
					
					
					server.close();
					socketReceive.close();
					input.close();
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	//Método Thread do Servidor de atualização de Status (Lado Servidor)
	public Runnable server2 = new Runnable(){
		@Override
		public void run(){
			while(true){
				ServerSocket server;
				try {
					server = new ServerSocket(8181);
					Socket socketStatus = server.accept();
					++serverNumber;
					System.out.println("#"+serverNumber+" - O computador "+socketStatus.getInetAddress()+" Se conectou ao Servidor 8181");
					byte[] byteStatus = new byte[socketStatus.getReceiveBufferSize()];
					BufferedInputStream input = new BufferedInputStream(socketStatus.getInputStream());
					input.read(byteStatus);
					String stringByte = new String(byteStatus, "ISO-8859-1");
					//Atualização do Status de logon
					if(stringByte.contains("#")){
						
						
						String hisStatus = stringByte.substring(stringByte.indexOf("[")+1, stringByte.lastIndexOf("]")).trim();
						String nameOfStatus = stringByte.substring(stringByte.indexOf("#")+1, stringByte.lastIndexOf("[")).trim();
						String ipStatus = hisStatus.substring(hisStatus.indexOf("{")+1, hisStatus.lastIndexOf("}")).trim();
						ImageIcon userOns = new ImageIcon(getClass().getResource("/chat/image/userOn.jpg"));
						ImageIcon userOffs = new ImageIcon(getClass().getResource("/chat/image/userOff.jpg"));
						int num = 0;
						while(!arrayComps[num].equals(nameOfStatus)){
							System.out.println("========================================");
							System.out.println("Numeração : "+num);
							System.out.println("Seu status : "+hisStatus);
							System.out.println("Nome do status : "+nameOfStatus);
							System.out.println("Ip do Status : "+ipStatus);
							System.out.println("Some setado : "+arrayComps[num]);
							System.out.println("========================================");
							num = num + 1;
						}
						
						if(hisStatus.contains("on")){
							int num1 = num;
							statusUser[num1].setIcon(userOns);
							statusUser[num1].setName("on");
							statusUser[num1].setToolTipText("online");
							verifyStatus = verifyStatus.replace(hisStatus, "{"+ipStatus+"}on");
							atualStatus[num1] = "on";
							}else{
							int num1 = num;
							statusUser[num1].setIcon(userOffs);
							statusUser[num1].setName("off");
							statusUser[num1].setToolTipText("offline");
							verifyStatus = verifyStatus.replace(hisStatus, "{"+ipStatus+"}off");
							atualStatus[num1] = "off";
						}
					}
					
					//Atualização do Status de Bloqueio
					if(stringByte.contains("$")){
						String hisStatus = stringByte.substring(stringByte.indexOf("[")+1, stringByte.lastIndexOf("]")).trim();
						String nameOfComputer = stringByte.substring(stringByte.indexOf("$")+1, stringByte.lastIndexOf("[")).trim();
						String ipStatus = hisStatus.substring(hisStatus.indexOf("{")+1, hisStatus.lastIndexOf("}")).trim();
						
						int num = 0;
						while(!arrayComps[num].equals(nameOfComputer)){num = num + 1;}
						
						ImageIcon msgUser1 = new ImageIcon(getClass().getResource("/chat/image/msgUser1.jpg"));
						ImageIcon blockUser1 = new ImageIcon(getClass().getResource("/chat/image/blockUser1.jpg"));
						ImageIcon msgUser = new ImageIcon(getClass().getResource("/chat/image/msgUser.jpg"));
						ImageIcon blockUser = new ImageIcon(getClass().getResource("/chat/image/blockUser.jpg"));
						
						if(hisStatus.contains("yes")){
							int num1 = num;
							new Thread(new Runnable(){
								
								@Override
								public void run(){
									
									computers[num1].setForeground(Color.gray);
									computers[num1].setCursor(null);
									computers[num1].setEnabled(false);
									labelMsgUser[num1].setEnabled(false);
									labelBlockUser[num1].setEnabled(false);
									divComputers[num1].setToolTipText("Bloqueado");
									
									labelMsgUser[num1].setCursor(null);
									labelBlockUser[num1].setCursor(null);
									labelMsgUser[num1].setIcon(msgUser1);
									labelBlockUser[num1].setIcon(blockUser1);
								}
							}).start();
							
							
				
						}else{
							int num1 = num;
							new Thread(new Runnable(){
								@Override
								public void run(){
									computers[num1].setForeground(Color.blue);
									computers[num1].setCursor(new Cursor(Cursor.HAND_CURSOR));
									computers[num1].setEnabled(true);
									labelMsgUser[num1].setEnabled(true);
									labelBlockUser[num1].setEnabled(true);
									
									labelMsgUser[num1].setCursor(new Cursor(Cursor.HAND_CURSOR));
									labelBlockUser[num1].setCursor(new Cursor(Cursor.HAND_CURSOR));
									labelMsgUser[num1].setIcon(msgUser);
									labelBlockUser[num1].setIcon(blockUser);
									divComputers[num1].setToolTipText(null);
								}
							}).start();
							
							
						}
					}
					
					
					input.close();
					socketStatus.close();
					server.close();
					
				} catch (IOException e) {}
				
			}
			
		
			
			
			
				
		}
	};
	
	//Servidor de alerta sonoro
	public Runnable server3 = new Runnable(){
		@Override
		public void run(){
			while(true){
				try {
					ServerSocket socket = new ServerSocket(5151);
					Socket Receive = socket.accept();
					++serverNumber;
					System.out.println("#"+serverNumber+" - O computador "+Receive.getInetAddress()+" Se conectou ao Servidor 5151");
					byte[] byteReceive = new byte[Receive.getReceiveBufferSize()];
					
					BufferedInputStream input = new BufferedInputStream(Receive.getInputStream());
					input.read(byteReceive);
					
					input.close();
					Receive.close();
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	};
	
	//1
	//Servidor de recebimento de arquivos
	public Runnable server4 = new Runnable(){
		@Override
		public void run(){
			UIManager.put("OptionPane.cancelButtonText", "Cancelar");
			UIManager.put("OptionPane.noButtonText", "Recusar");
			UIManager.put("OptionPane.yesButtonText", "Aceitar");
				while(true){
					try {
						
						ServerSocket sockets = new ServerSocket(6161);
						Socket Receives = sockets.accept();
						InputStream in = Receives.getInputStream();
						InputStreamReader inputReader = new InputStreamReader(in);
						BufferedReader reader = new BufferedReader(inputReader);
						
						String fileName = reader.readLine();
						String IpClient = Receives.getInetAddress().getHostAddress();
						System.out.println("O ip da origem : "+IpClient);
						
						int i = 0;
						while(!IpClient.equals(arrayIps[i])){
							i = i + 1;
						}
						
						int confirm = JOptionPane.showConfirmDialog(null, "O usuário "+arrayComps[i]+" deseja enviar o arquivo '"+fileName+"'");
						
						FileOutputStream output = null;
						if(confirm == JOptionPane.YES_OPTION){
							File file = new File(getPath+"\\\\"+fileName);
							output = new FileOutputStream(file);
							int size = 4096;
							byte[] buffer = new byte[size];
							int reads = -1;
							while((reads = in.read(buffer, 0, size)) != -1){
								System.out.println(reads);
								output.write(buffer, 0, reads);
							}
						
							output.flush();
							
						}
						
						sockets.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		}
	};
	
	//Método construtor do ChatServer
	public ChatClient() throws IOException{
		this.setBounds(200, 200, 250, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Chat School");
		
		//Execução multi-tarefa dos servidores
		new Thread(server1).start();
		new Thread(server2).start();
		new Thread(server3).start();
		new Thread(server4).start();
		
		//Envio para o servidor Web dos Status de Logon
		connect = new FtpConnect();
		connect.getInfoFiles();
		
		String ip = connect.getNetwork();
		ip = ip.replace("/", "");
		capturateInfo(ip+".log");
		String dataSave = "%"+nameComp+"$"+ip+"&.info";
		connect.download(dataSave, getPath+"/log/");
		
		FileReader ReadData = new FileReader(getPath+"/log/"+dataSave);
		bufferData = new BufferedReader(ReadData);
		String dataread = bufferData.readLine();
		
		dataread = dataread.replace("Status{off}Status", "Status{on}Status");
		bufferData.close();
		ReadData.close();
		FileWriter fileWriter = new FileWriter(getPath+"/log/"+dataSave);
		fileWriter.write(dataread);
		fileWriter.close();
		connect.upload(dataSave, getPath+"/log/");
		
		//Instanciações das figuras do software
		ImageIcon imageOff = new ImageIcon(getClass().getResource("/chat/image/off.jpg"));
		ImageIcon online = new ImageIcon(getClass().getResource("/chat/image/online.jpg"));
		ImageIcon offline = new ImageIcon(getClass().getResource("/chat/image/offline.jpg"));
		ImageIcon block = new ImageIcon(getClass().getResource("/chat/image/block.jpg"));
		ImageIcon info = new ImageIcon(getClass().getResource("/chat/image/info.jpg"));
		userOn = new ImageIcon(getClass().getResource("/chat/image/userOn.jpg"));
		userOff = new ImageIcon(getClass().getResource("/chat/image/userOff.jpg"));
		ImageIcon config = new ImageIcon(getClass().getResource("/chat/image/config.jpg"));
		ImageIcon msg = new ImageIcon(getClass().getResource("/chat/image/mensage.jpg"));
		ImageIcon msgUser = new ImageIcon(getClass().getResource("/chat/image/msgUser.jpg"));
		ImageIcon blockUser = new ImageIcon(getClass().getResource("/chat/image/blockUser.jpg"));
		ImageIcon msgUser1 = new ImageIcon(getClass().getResource("/chat/image/msgUser1.jpg"));
		ImageIcon blockUser1 = new ImageIcon(getClass().getResource("/chat/image/blockUser1.jpg"));
		ImageIcon block1 = new ImageIcon(getClass().getResource("/chat/image/block1.jpg"));
		ImageIcon imgItalic = new ImageIcon(getClass().getResource("/chat/image/italic.jpg"));
		ImageIcon imgStrong = new ImageIcon(getClass().getResource("/chat/image/strong.jpg"));
		ImageIcon imgUnderlined = new ImageIcon(getClass().getResource("/chat/image/underlined.jpg"));
		ImageIcon smile = new ImageIcon(getClass().getResource("/chat/image/smile.jpg"));
		ImageIcon sad = new ImageIcon(getClass().getResource("/chat/image/sad.jpg"));
		ImageIcon happy = new ImageIcon(getClass().getResource("/chat/image/happy.jpg"));
		ImageIcon nervous = new ImageIcon(getClass().getResource("/chat/image/nervous.jpg"));
		ImageIcon impressioned = new ImageIcon(getClass().getResource("/chat/image/impressioned.jpg"));
		ImageIcon seriosly = new ImageIcon(getClass().getResource("/chat/image/seriosly.jpg"));
		ImageIcon link = new ImageIcon(getClass().getResource("/chat/image/link.jpg"));
		ImageIcon send = new ImageIcon(getClass().getResource("/chat/image/send.jpg"));
		ImageIcon writer = new ImageIcon(getClass().getResource("/chat/image/writer.jpg"));
		ImageIcon msgMail = new ImageIcon(getClass().getResource("/chat/image/msgMail.jpg"));
		ImageIcon docUser = new ImageIcon(getClass().getResource("/chat/image/docUser.jpg"));
		ImageIcon docUser1 = new ImageIcon(getClass().getResource("/chat/image/docUser1.jpg"));
		ImageIcon soundUser = new ImageIcon(getClass().getResource("/chat/image/soundUser.jpg"));
		ImageIcon soundUser1 = new ImageIcon(getClass().getResource("/chat/image/soundUser1.jpg"));
		
		//Painel e Topo do software
		JLabel backPanel = new JLabel();
		backPanel.setText("<html><div style='background:#aaffff;width:250;height:500;'></div></html>");
		backPanel.setBounds(0, 0, 250, 500);
		this.add(backPanel);
		this.setResizable(false);
		
		JLabel divTop = new javax.swing.JLabel();
		divTop.setText("<html><div style='background:#0000ff;width:250;height:90;'></div></html>");
		divTop.setBounds(0, 0, 250, 90);
		backPanel.add(divTop);
		
		JLabel nameChat = new javax.swing.JLabel();
		nameChat.setText("ChatServer");
		nameChat.setFont(new Font("Calibri", 1, 20));
		nameChat.setBounds(20, 20, 100, 30);
		nameChat.setForeground(Color.white);
		divTop.add(nameChat);
		
		JLabel perfileName = new JLabel();
		perfileName.setText("<html><div style='width:80;height:20;color:white;'>"+nameComp+"</div></html>");
		perfileName.setBounds(150, 5, 70, 20);
		perfileName.setCursor(new Cursor(Cursor.HAND_CURSOR));
		divTop.add(perfileName);
		
		JLabel perfileBlock = new JLabel();
		perfileBlock.setBounds(210, 3, 20, 20);
		perfileBlock.setIcon(null);
		divTop.add(perfileBlock);
		
		JLabel recursesDiv = new javax.swing.JLabel();
		recursesDiv.setText("<html><div style='background:#0000aa;width:250;height:20;'></div></html>");
		recursesDiv.setBounds(0, 70, 250, 20);
		divTop.add(recursesDiv);
		
		JLabel labelNotifyer = new JLabel();
		labelNotifyer.setBounds(78, 1, 16, 10);
		labelNotifyer.setForeground(Color.red);
		labelNotifyer.setFont(new Font("Calibri", 1, 12));
		labelNotifyer.setVisible(false);
		labelNotifyer.setCursor(new Cursor(Cursor.HAND_CURSOR));
		recursesDiv.add(labelNotifyer);
		
		
		
		//Loop que pega a lista de usuários com IPs e seta no software
		String list = "";
		int alignY = 100;
		for(int i = 0; i < connect.listComp.length; i++){
			list = connect.listComp[i];
			
			if(connect.listComp[i] != null){
				   
					connect.download(list, getPath+"//log//");
					
					
						
					FileReader reader = new FileReader(getPath+"/log/"+list);
					buffer = new BufferedReader(reader);
					String read = buffer.readLine();
				    atualStatus[i] = read.substring(read.indexOf("Status{")+7, read.lastIndexOf("}Status"));
					String atualBlocked = read.substring(read.indexOf("Blocked{")+8, read.lastIndexOf("}Blocked"));
					String youBlocked = read.substring(read.indexOf("Blocks{")+7, read.lastIndexOf("}Blocks"));
					
					if(youBlocked.contains("$"+nameComp+"{")){
						atualBlocked = "yes";
					}else{
						if(atualBlocked.equals("yes")){
							atualBlocked = "yes";
						}else{
							atualBlocked = "no";
						}
						
					}
					/*Pega o computador registrado e o ip de cada usuário
					*através do arquivo de informações
					*/
					nameComputer = list.substring(list.indexOf("%")+1, list.lastIndexOf("$"));
					ipComputer = list.substring(list.indexOf("$")+1, list.lastIndexOf("&"));
					
					arrayIps[i] = ipComputer;
					arrayComps[i] = nameComputer;
					
					System.out.println("array["+i+"] - "+arrayComps[i]+" : "+arrayIps[i]);
					
					if(!list.contains("%"+nameComp+"$"+ip+"&.info")){
						
					divComputers[i] = new JLabel();
					divComputers[i].setText("<html><div style='background:#6affff;width:250;height:30;'></div></html>");
					divComputers[i].setBounds(0, alignY, 250, 30);
					backPanel.add(divComputers[i]);
					
					computers[i] = new JLabel();
					computers[i].setText(nameComputer);
					computers[i].setName(ipComputer);
					computers[i].setBounds(20, 10, 70, 15);
					divComputers[i].add(computers[i]);
					
					
					//No ato do login, envia pra cada usuário o Status online
					statusUser[i] = new JLabel();
					if(atualStatus[i].equals("on")){
						statusUser[i].setIcon(userOn);
						statusUser[i].setName("on");
						statusUser[i].setToolTipText("online");
						if(!arrayComps[i].equals(nameComp)){
							verifyStatus += "#"+nameComputer+"[{"+arrayIps[i]+"}on]"+nameComputer;
							try{
								String text = "#"+nameComp+"[{"+arrayIps[i]+"}on]"+nameComp;
								Socket socketStatus = new Socket(arrayIps[i], 8181);
								BufferedOutputStream outputStatus = new BufferedOutputStream(socketStatus.getOutputStream());
								
								byte[] byteText = text.getBytes();
								outputStatus.write(byteText);
								outputStatus.flush();
		                        outputStatus.close();
		                        socketStatus.close();
								
								
								}catch(UnknownHostException unknown){unknown.printStackTrace();}catch(IOException io){io.printStackTrace();}
							
						}
						
					}else{
						statusUser[i].setIcon(userOff);
						statusUser[i].setName("off");
						statusUser[i].setToolTipText("offline");
						verifyStatus += "#"+nameComputer+"[{"+computers[i].getName()+"}off]"+nameComputer;
					}
					
					statusUser[i].setBounds(0, 10, 20, 15);
					divComputers[i].add(statusUser[i]);
					
					//A partir daqui, começa a criação do batepapo para usuários, respectivamente.
					labelMsgUser[i] = new JLabel();
					labelMsgUser[i].setBounds(100, 10, 25, 20);
					labelMsgUser[i].setName(ipComputer);
					divComputers[i].add(labelMsgUser[i]);
					
					labelBlockUser[i] = new JLabel();
					labelBlockUser[i].setBounds(130, 8, 20, 20);
					labelBlockUser[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
					labelBlockUser[i].setName("$"+nameComputer+"{"+ipComputer+"}"+nameComputer);
					labelBlockUser[i].setToolTipText("Bloquear Usuário");
					divComputers[i].add(labelBlockUser[i]);
					
					labelDocUser[i] = new JLabel();
					labelDocUser[i].setBounds(180, 10, 20, 20);
					labelDocUser[i].setName(ipComputer);
					divComputers[i].add(labelDocUser[i]);
					
					labelSoundUser[i] = new JLabel();
					labelSoundUser[i].setBounds(155, 9, 20, 20);
					labelSoundUser[i].setName(ipComputer);
					divComputers[i].add(labelSoundUser[i]);
					
					labelNotifyers[i] = new JLabel();
					labelNotifyers[i].setBounds(78, 1, 16, 10);
					labelNotifyers[i].setForeground(Color.red);
					labelNotifyers[i].setFont(new Font("Calibri", 1, 12));
					labelNotifyers[i].setVisible(false);
					labelNotifyers[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
					divComputers[i].add(labelNotifyers[i]);
					
					//Seta notificações de mensagens
					connect.changePath("mensages");
					connect.getMsgFiles(arrayComps[i], arrayIps[i], false);
					connect.changePath("infos");
					if(connect.readsCont > 0){
						labelNotifyers[i].setVisible(true);
						labelNotifyers[i].setText(""+connect.readsCont);
						labelNotifyers[i].setToolTipText(""+connect.readsCont+" Novas mensagens");
					}else{
						labelNotifyers[i].setVisible(false);
					}
					connect.readeds = "";
					
					frameComputers[i] = new JFrame();
					frameComputers[i].setBounds(100, 100, 490, 390);
					frameComputers[i].setTitle("Chat - "+computers[i].getText());
					frameComputers[i].setResizable(false);
					
					labelBody[i] = new JLabel();
					labelBody[i].setText("<html><div style='width:490;height:390;'></div></html>");
					labelBody[i].setBounds(0, 0, 490, 390);
					labelBody[i].setName(nameComputer);
					frameComputers[i].add(labelBody[i]);
					
					labelBottom[i] = new JLabel();
					labelBottom[i].setText("<html><div style='background:blue;width:490;height:40;'></div></html>");
					labelBottom[i].setBounds(0, 316, 490, 50);
					labelBody[i].add(labelBottom[i]);
					
					italic[i] = new JLabel();
					italic[i].setIcon(imgItalic);
					italic[i].setBounds(0, 25, 20, 20);
					italic[i].setToolTipText("Itálico");
					italic[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
					labelBottom[i].add(italic[i]);
					
					strong[i] = new JLabel();
					strong[i].setIcon(imgStrong);
					strong[i].setBounds(25, 25, 20, 20);
					strong[i].setToolTipText("Negrito");
					strong[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
					labelBottom[i].add(strong[i]);
					
					underlined[i] = new JLabel();
					underlined[i].setIcon(imgUnderlined);
					underlined[i].setBounds(60, 25, 20, 20);
					underlined[i].setToolTipText("Sublinhado");
					underlined[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
					labelBottom[i].add(underlined[i]);
					
					linked[i] = new JLabel();
					linked[i].setIcon(link);
					linked[i].setBounds(90, 25, 20, 20);
					linked[i].setToolTipText("Linkado");
					linked[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
					labelBottom[i].add(linked[i]);
					
					emoji1[i] = new JLabel();
					emoji1[i].setIcon(smile);
					emoji1[i].setBounds(125, 25, 20, 20);
					emoji1[i].setToolTipText("Sorrindo");
					emoji1[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
					labelBottom[i].add(emoji1[i]);
					
					emoji2[i] = new JLabel();
					emoji2[i].setIcon(sad);
					emoji2[i].setBounds(160, 25, 20, 20);
					emoji2[i].setToolTipText("Triste");
					emoji2[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
					labelBottom[i].add(emoji2[i]);
					
					emoji3[i] = new JLabel();
					emoji3[i].setIcon(happy);
					emoji3[i].setBounds(195, 25, 20, 20);
					emoji3[i].setToolTipText("Super Feliz");
					emoji3[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
					labelBottom[i].add(emoji3[i]);
					
					emoji4[i] = new JLabel();
					emoji4[i].setIcon(nervous);
					emoji4[i].setBounds(230, 25, 20, 20);
					emoji4[i].setToolTipText("Nervoso");
					emoji4[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
					labelBottom[i].add(emoji4[i]);
					
					emoji5[i] = new JLabel();
					emoji5[i].setIcon(impressioned);
					emoji5[i].setBounds(265, 25, 20, 20);
					emoji5[i].setToolTipText("Impressionado");
					emoji5[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
					labelBottom[i].add(emoji5[i]);
					
					emoji6[i] = new JLabel();
					emoji6[i].setIcon(seriosly);
					emoji6[i].setBounds(290, 25, 20, 20);
					emoji6[i].setToolTipText("Sério");
					emoji6[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
					labelBottom[i].add(emoji6[i]);
					
					buttomBottom[i] = new JButton();
					buttomBottom[i].setText("Enviar");
					buttomBottom[i].setBounds(410, 5, 70, 20);
					buttomBottom[i].setForeground(Color.blue);
					buttomBottom[i].setBackground(Color.decode("#aaffff"));
					buttomBottom[i].setName(ipComputer);
					buttomBottom[i].setBorder(BorderFactory.createLineBorder(Color.blue, 1, true));
					buttomBottom[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
					labelBottom[i].add(buttomBottom[i]);
					
					inputText[i] = new JTextField();
					inputText[i].setBounds(0, 5, 400, 20);
					inputText[i].setName(ipComputer);
					inputText[i].setBackground(Color.decode("#aaffff"));
					labelBottom[i].add(inputText[i]);
					
					editorChat[i] = new JEditorPane();
					editorChat[i].setContentType("text/html");
					editorChat[i].setEditable(false);
					labelBody[i].add(editorChat[i]);
					
					scrollChat[i] = new JScrollPane(editorChat[i]);
					scrollChat[i].setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
					scrollChat[i].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollChat[i].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
					scrollChat[i].setBounds(0, 0, 485, 320);
					labelBody[i].add(scrollChat[i]);
					
					
					
					//Seta notificações de mensagens
					connect.changePath("mensages");
					connect.getMsgFiles(arrayComps[i], ip, false);
					connect.changePath("infos");
					if(connect.readsCont > 0){
						labelNotifyer.setVisible(true);
						labelNotifyer.setText(""+connect.readsCont);
						labelNotifyer.setToolTipText(""+connect.readsCont+" Novas mensagens");
					}else{
						labelNotifyer.setVisible(false);
					}
					connect.readeds = "";
					
					
					//Verifica se o usuário é bloqueado, se não é, seta as ações
					if(atualBlocked.equals("no")){
						computers[i].setForeground(Color.blue);
						computers[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
						computers[i].setEnabled(true);
						
						labelMsgUser[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
						labelMsgUser[i].setToolTipText("Enviar Mensagem");
						labelBlockUser[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
						labelBlockUser[i].setToolTipText("Bloquear Usuário");
						labelMsgUser[i].setIcon(msgUser);
						labelBlockUser[i].setIcon(blockUser);
					    
						//pendente correção
						labelDocUser[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
						labelSoundUser[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
						labelDocUser[i].setIcon(docUser);
						labelSoundUser[i].setIcon(soundUser);
						labelDocUser[i].setToolTipText("Enviar Arquivo");
						labelSoundUser[i].setToolTipText("Alerta Sonoro");
						perfileBlock.setIcon(null);
						
						//para abrir chat do usuário
						int n = i;
						CompListener = new MouseListener() {
							
							@Override
							public void mouseReleased(MouseEvent arg0) {}
							
							@Override
							public void mousePressed(MouseEvent arg0) {
								Component arg = (Component) arg0.getSource();
								if(arg.isEnabled()){
									frameComputers[n].setVisible(true);
								}
								
							}
							
							@Override
							public void mouseExited(MouseEvent arg0) {}
							
							@Override
							public void mouseEntered(MouseEvent arg0) {}
							
							@Override
							public void mouseClicked(MouseEvent arg0) {}
						};
						computers[n].addMouseListener(CompListener);
						
						//Para abrir mensagens do usuário
						labelMsgUser[n].addMouseListener(new MouseListener() {
							
							

							@Override
							public void mouseReleased(MouseEvent arg0) {
								labelMsgUser[n].setBorder(null);
							}
							
							@Override
							public void mousePressed(MouseEvent arg0) {
								labelMsgUser[n].setBorder(BorderFactory.createLineBorder(Color.blue));
								
								int cont = 0;
								mailBases = "";
								try {
									String ip = connect.getNetwork();
									ip = ip.replace("/", "");
									connect.changePath("mensages");
									connect.getMsgFiles(arrayComps[n], ip, true);
									System.out.println("arrayComp => "+arrayComps[n]);
									/*Caso as mensagens foram encontradas, Seta as mensagens
									*e no ato da leitura, as reenvia pra o Servidor Web com
									*a identificação de lidas.
									*/
									if(connect.finded){
										for(int i = connect.major; i >= 0; i--){
											String numeration = "#"+i+"{"+ip+"-";
											int rl = numeration.length();
											String readers = connect.readeds.substring(
											connect.readeds.indexOf("#"+i+"{"+ip+"-")+rl, connect.readeds.lastIndexOf("}"+i+"#"));
											
											String myMsgFiles = "";
											FileReader msgReader;
											if(readers.equals("nonread")){
												myMsgFiles = "#"+i+"[newsletter_"+arrayComps[n]+"-"+ip+"].msg";
												msgReader = new FileReader(getPath+"\\\\msgs\\\\"+myMsgFiles);
											}else{
												myMsgFiles = "#"+i+"[newsletter_"+arrayComps[n]+"-"+ip+"{read}].msg";
												msgReader = new FileReader(getPath+"\\\\read\\\\"+myMsgFiles);
											}
											
											deleteMsg[cont] = new JButton();
											deleteMsg[cont].setText("Excluir");
											deleteMsg[cont].setName(myMsgFiles);
											int val = cont;
											
											deleteMsg[cont].addMouseListener(new MouseListener(){
												@Override
												public void mouseReleased(MouseEvent arg0) {	}
												@Override
												public void mousePressed(MouseEvent arg0){	
													
													String ip = connect.getNetwork();
													ip = ip.replace("/", "");
													try {
														connect.changePath("mensages");
														
														connect.deleteFile(deleteMsg[val].getName());
														
														String[] arqs = connect.ftp.listNames();
														int f = 0;
														for(String dirFile : arqs){
															if(dirFile.contains("[newsletter_"+arrayComps[n]+"-"+ip)){
																
																if(dirFile.contains("{read}")){
																	connect.renameFile(dirFile
																	, "#"+f+"[newsletter_"+arrayComps[n]+"-"+ip+"{read}].msg");
																}else{
																	connect.renameFile(dirFile
																			, "#"+f+"[newsletter_"+arrayComps[n]+"-"+ip+"].msg");
																}	
																
																f = f + 1;
															}
														}
														connect.changePath("infos");
														
													} catch (IOException e) {}
													
													mailBases = "";
													try {
														
														connect.changePath("mensages");
														connect.getMsgFiles(arrayComps[n], ip, true);
														if(connect.finded){
															for(int i = connect.major; i >= 0; i--){
																
																String myMsgFiles = "";
																FileReader msgReader;
																
																	myMsgFiles = "#"+i+"[newsletter_"+arrayComps[n]+"-"+ip+"{read}].msg";
																	msgReader = new FileReader(getPath+"\\\\read\\\\"+myMsgFiles);
																
																
																BufferedReader msgBuffer = new BufferedReader(msgReader);
																String readMsg = msgBuffer.readLine();
																String eachFile = "";
																while(readMsg != null){
																	mailBases += readMsg;
																	eachFile += readMsg + "\r\n";
																	readMsg = msgBuffer.readLine();
																}
																msgBuffer.close();
																
															
																String RepDirEmot1 = mailBases.substring(mailBases.indexOf("file:\\")+6, mailBases.lastIndexOf("\\imgs\\"));
																mailBases = mailBases.replace(RepDirEmot1, getPath);
																mailBases = mailBases.replace("<html>", "").replace("</html>", "")
																		.replace("<head>", "").replace("</head>", "").replace("<body>", "")
																		.replace("</body>", "");
																
																
																
																
															}
														}
														connect.changePath("infos");
														editorMail1[n].setText(mailBases);
														mailBases = "";
														connect.readeds = "";

														connect.finded = false;
														connect.major = 0;
														deleteMsg[val].setVisible(false);
													} catch (IOException e) {
													
														e.printStackTrace();
													}
													
												}
												@Override
												public void mouseEntered(MouseEvent arg0){	}
												@Override
												public void mouseExited(MouseEvent arg0){	}
												@Override
												public void mouseClicked(MouseEvent arg0){	}	
												
											});
											
											BufferedReader msgBuffer = new BufferedReader(msgReader);
											String readMsg = msgBuffer.readLine();
											String eachFile = "";
											while(readMsg != null){
												mailBases += readMsg;
												eachFile += readMsg + "\r\n";
												readMsg = msgBuffer.readLine();
											}
											msgBuffer.close();
											
											
											if(readers.equals("nonread")){
												File file = new File(getPath+"\\\\msgs\\\\"+myMsgFiles);
												file.delete();
												String myMsgFiles1 = "#"+i+"[newsletter_"+arrayComps[n]+"-"+ip+"{read}].msg";
												FileWriter msgWriter = new FileWriter(getPath+"\\\\read\\\\"+myMsgFiles1);
												msgWriter.write(eachFile);
												msgWriter.close();
												connect.replace(myMsgFiles, myMsgFiles1);
											}
											
											
											String RepDirEmot1 = mailBases.substring(mailBases.indexOf("file:\\")+6, mailBases.lastIndexOf("\\imgs\\"));
											mailBases = mailBases.replace(RepDirEmot1, getPath);
											mailBases = mailBases.replace("<html>", "").replace("</html>", "")
													.replace("<head>", "").replace("</head>", "").replace("<body>", "")
													.replace("</body>", "");
									
											
											connect.finded = false;
											connect.major = 0;
											
											cont = cont + 1;
										}
									}
									connect.changePath("infos");
									connect.readeds = "";
									connect.finded = false;
									connect.major = 0;
									
								
								} catch (IOException e2) {
								
									e2.printStackTrace();
								}
								/*A partir daqui, Constrói painel do correio eletrônico interno
								*(Mensagens)
								*/
								frameMsg[n] = new JFrame();
								frameMsg[n].setTitle("Mensagens");
								frameMsg[n].setBounds(300, 100, 490, 430);
								frameMsg[n].setResizable(false);
								
								panelMsg[n] = new JLabel();
								panelMsg[n].setText("<html><div style='background:#aaffff;width:490;height:430;'></div></html>");
								panelMsg[n].setBounds(0, 0, 490, 430);
								frameMsg[n].add(panelMsg[n]);
								
								from[n] = new JLabel();
								from[n].setText("<html><font color='blue'>de : </font>todos</html>");
								from[n].setBounds(10, 10, 100, 20);
								panelMsg[n].add(from[n]);
								
								to[n] = new JLabel();
								to[n].setText("<html><font color='blue'>para : </font>"+nameComp+"</html>");
								to[n].setBounds(10, 30, 100, 20);
								panelMsg[n].add(to[n]);
								
								from[n].setText("<html><font color='blue'>de : </font>"+arrayComps[n]+"</html>");
								to[n].setText("<html><font color='blue'>para : </font>"+nameComp+"</html>");
								
								myRecurses[n] = new JLabel();
								myRecurses[n].setText("<html><div style='background:blue;width:500;height:25;'><hr><br><hr></div></html>");
								myRecurses[n].setBounds(0, 50, 500, 25);
								panelMsg[n].add(myRecurses[n]);
								
								italics[n] = new JLabel();
								italics[n].setIcon(imgItalic);
								italics[n].setBounds(0, 2, 20, 20);
								italics[n].setToolTipText("Itálico");
								italics[n].setCursor(new Cursor(Cursor.HAND_CURSOR));
								myRecurses[n].add(italics[n]);
								
								strongs[n] = new JLabel();
								strongs[n].setIcon(imgStrong);
								strongs[n].setBounds(25, 2, 20, 20);
								strongs[n].setToolTipText("Negrito");
								strongs[n].setCursor(new Cursor(Cursor.HAND_CURSOR));
								myRecurses[n].add(strongs[n]);
								
								
								
								underlineds[n] = new JLabel();
								underlineds[n].setIcon(imgUnderlined);
								underlineds[n].setBounds(60, 2, 20, 20);
								underlineds[n].setToolTipText("Sublinhado");
								underlineds[n].setCursor(new Cursor(Cursor.HAND_CURSOR));
								myRecurses[n].add(underlineds[n]);
								
								linkeds[n] = new JLabel();
								linkeds[n].setIcon(link);
								linkeds[n].setBounds(90, 2, 20, 20);
								linkeds[n].setToolTipText("Linkado");
								linkeds[n].setCursor(new Cursor(Cursor.HAND_CURSOR));
								myRecurses[n].add(linkeds[n]);
								
								emoji1s[n] = new JLabel();
								emoji1s[n].setIcon(smile);
								emoji1s[n].setBounds(125, 2, 20, 20);
								emoji1s[n].setToolTipText("Sorrindo");
								emoji1s[n].setCursor(new Cursor(Cursor.HAND_CURSOR));
								myRecurses[n].add(emoji1s[n]);
								
								emoji2s[n] = new JLabel();
								emoji2s[n].setIcon(sad);
								emoji2s[n].setBounds(160, 2, 20, 20);
								emoji2s[n].setToolTipText("Triste");
								emoji2s[n].setCursor(new Cursor(Cursor.HAND_CURSOR));
								myRecurses[n].add(emoji2s[n]);
								
								emoji3s[n] = new JLabel();
								emoji3s[n].setIcon(happy);
								emoji3s[n].setBounds(195, 2, 20, 20);
								emoji3s[n].setToolTipText("Super Feliz");
								emoji3s[n].setCursor(new Cursor(Cursor.HAND_CURSOR));
								myRecurses[n].add(emoji3s[n]);
								
								emoji4s[n] = new JLabel();
								emoji4s[n].setIcon(nervous);
								emoji4s[n].setBounds(230, 2, 20, 20);
								emoji4s[n].setToolTipText("Nervoso");
								emoji4s[n].setCursor(new Cursor(Cursor.HAND_CURSOR));
								myRecurses[n].add(emoji4s[n]);
								
								emoji5s[n] = new JLabel();
								emoji5s[n].setIcon(impressioned);
								emoji5s[n].setBounds(265, 2, 20, 20);
								emoji5s[n].setToolTipText("Impressionado");
								emoji5s[n].setCursor(new Cursor(Cursor.HAND_CURSOR));
								myRecurses[n].add(emoji5s[n]);
								
								emoji6s[n] = new JLabel();
								emoji6s[n].setIcon(seriosly);
								emoji6s[n].setBounds(295, 2, 20, 20);
								emoji6s[n].setToolTipText("Sério");
								emoji6s[n].setCursor(new Cursor(Cursor.HAND_CURSOR));
								myRecurses[n].add(emoji6s[n]);
								
								labelWrite[n] = new JLabel();
								labelWrite[n].setIcon(writer);
								labelWrite[n].setBounds(395, 2, 20, 20);
								labelWrite[n].setToolTipText("Escrever");
								labelWrite[n].setCursor(new Cursor(Cursor.HAND_CURSOR));
								myRecurses[n].add(labelWrite[n]);
								
								labelRead[n] = new JLabel();
								labelRead[n].setIcon(msgMail);
								labelRead[n].setBounds(425, 2, 25, 20);
								labelRead[n].setToolTipText("Ler");
								labelRead[n].setCursor(new Cursor(Cursor.HAND_CURSOR));
								myRecurses[n].add(labelRead[n]);
								
								labelSend[n] = new JLabel();
								labelSend[n].setIcon(send);
								labelSend[n].setBounds(455, 2, 20, 20);
								labelSend[n].setToolTipText("Enviar");
								labelSend[n].setCursor(new Cursor(Cursor.HAND_CURSOR));
								myRecurses[n].add(labelSend[n]);
								
								
								editorMail[n] = new JEditorPane();
								editorMail[n].setContentType("text/html");
								editorMail[n].setEditable(true);
								editorMail[n].setVisible(false);
								panelMsg[n].add(editorMail[n]);
								
								scrollMail[n] = new JScrollPane(editorMail[n]);
								scrollMail[n].setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
								scrollMail[n].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
								scrollMail[n].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
								scrollMail[n].setBounds(0, 80, 485, 320);
								scrollMail[n].setVisible(false);
								panelMsg[n].add(scrollMail[n]);
								
								
								editorMail1[n] = new JEditorPane();
								editorMail1[n].setContentType("text/html");
								editorMail1[n].setEditable(false);
								editorMail1[n].setVisible(true);
								
								panelMsg[n].add(editorMail1[n]);
								
								
								
								scrollMail1[n] = new JScrollPane(editorMail1[n]);
								scrollMail1[n].setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
								scrollMail1[n].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
								scrollMail1[n].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
								scrollMail1[n].setBounds(0, 80, 485, 320);
								scrollMail1[n].setVisible(true);
								
							
						
								editorMail1[n].setText(mailBases);
								
								
								int addY = 10;
								int dm = 0;
								while(dm < cont){
									
									deleteMsg[dm].setBounds(350, addY, 100, 20);
									editorMail1[n].add(deleteMsg[dm]);
									dm = dm + 1;
									
									addY = addY + 160;
									
								}
						
								scrollMail1[n].getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
									
									@Override
									public void adjustmentValueChanged(AdjustmentEvent arg0) {
										scrollMail1[n].getVerticalScrollBar().setValue(0);
										if(removeListener){
											scrollMail1[n].getVerticalScrollBar().removeAdjustmentListener(this);
											removeListener = false;
										}
									}
								});
								
							
								scrollMail1[n].getVerticalScrollBar().addMouseListener(new MouseListener() {
									
									@Override
									public void mouseReleased(MouseEvent arg0) {}
									
									@Override
									public void mousePressed(MouseEvent arg0) {
										removeListener = true;
										scrollMail1[n].getVerticalScrollBar().removeMouseListener(this);
									}
									
									@Override
									public void mouseExited(MouseEvent arg0) {}
									
									@Override
									public void mouseEntered(MouseEvent arg0) {}
									
									@Override
									public void mouseClicked(MouseEvent arg0) {}
								});
								
								panelMsg[n].add(scrollMail1[n]);
								
								
								
								
								
								//Torna palavras itálicas
								italics[n].addMouseListener(new MouseListener() {
									
									@Override
									public void mouseReleased(MouseEvent arg0) {
										italics[n].setBorder(null);
									}
									
									@Override
									public void mousePressed(MouseEvent arg0) {
										italics[n].setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
										String allText = editorMail[n].getText();
										String selected = editorMail[n].getSelectedText();
										String modifyer = allText.replace(selected, " <i>"+selected+"</i> ");
										
										editorMail[n].setText(modifyer);
									}
									
									@Override
									public void mouseExited(MouseEvent arg0) {}
									
									@Override
									public void mouseEntered(MouseEvent arg0) {}
									
									@Override
									public void mouseClicked(MouseEvent arg0) {}
								});
								//Torna palavras negritas
								strongs[n].addMouseListener(new MouseListener() {
									
									@Override
									public void mouseReleased(MouseEvent arg0) {
										strongs[n].setBorder(null);
									}
									
									@Override
									public void mousePressed(MouseEvent arg0) {
										strongs[n].setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
										String allText = editorMail[n].getText();
										String selected = editorMail[n].getSelectedText();
										String modifyer = allText.replace(selected, " <b>"+selected+"</b> ");
										
										editorMail[n].setText(modifyer);
									}
									
									@Override
									public void mouseExited(MouseEvent arg0) {}
									
									@Override
									public void mouseEntered(MouseEvent arg0) {}
									
									@Override
									public void mouseClicked(MouseEvent arg0) {}
								});
								//Torna palavras sublinhadas
								underlineds[n].addMouseListener(new MouseListener() {
									
									@Override
									public void mouseReleased(MouseEvent arg0) {
										underlineds[n].setBorder(null);
									}
									
									@Override
									public void mousePressed(MouseEvent arg0) {
										underlineds[n].setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
										String allText = editorMail[n].getText();
										String selected = editorMail[n].getSelectedText();
										String modifyer = allText.replace(selected, " <u>"+selected+"</u> ");
										
										editorMail[n].setText(modifyer);
									}
									
									@Override
									public void mouseExited(MouseEvent arg0) {}
									
									@Override
									public void mouseEntered(MouseEvent arg0) {}
									
									@Override
									public void mouseClicked(MouseEvent arg0) {}
								});
								//Torna palavras linkadas
								linkeds[n].addMouseListener(new MouseListener() {
									
									@Override
									public void mouseReleased(MouseEvent arg0) {
										linkeds[n].setBorder(null);
									}
									
									@Override
									public void mousePressed(MouseEvent arg0) {
										linkeds[n].setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
										String optionLink = JOptionPane.showInputDialog(null, "Digite um link", "Mensagens Linkadas", JOptionPane.PLAIN_MESSAGE);
										String allText = editorMail[n].getText();
										String selected = editorMail[n].getSelectedText();
										String modifyer = allText.replace(selected, " <a href='"+optionLink+"'>"+selected+"</a> ");
										
										editorMail[n].setText(modifyer);
										
										
									}
									
									@Override
									public void mouseExited(MouseEvent arg0) {}
									
									@Override
									public void mouseEntered(MouseEvent arg0) {}
									
									@Override
									public void mouseClicked(MouseEvent arg0) {}
								});
								//Seta expressão sorridente
								emoji1s[n].addMouseListener(new MouseListener() {
									
									@Override
									public void mouseReleased(MouseEvent arg0) {
										emoji1s[n].setBorder(null);
									}
									
									@Override
									public void mousePressed(MouseEvent arg0) {
										emoji1s[n].setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
										String allText = editorMail[n].getText();
										String modifyer = allText.replace("</html>", "");
										modifyer = modifyer.replace("</body>", "<img src='file:\\"+getPath+"\\imgs\\smile.jpg' width='20' height='20'/></body></html>");
										
										
										editorMail[n].setText(modifyer);
									}
									
									@Override
									public void mouseExited(MouseEvent arg0) {}
									
									@Override
									public void mouseEntered(MouseEvent arg0) {}
									
									@Override
									public void mouseClicked(MouseEvent arg0) {}
								});
								//Seta expressão triste
								emoji2s[n].addMouseListener(new MouseListener() {
									
									@Override
									public void mouseReleased(MouseEvent arg0) {
										emoji2s[n].setBorder(null);
									}
									
									@Override
									public void mousePressed(MouseEvent arg0) {
										emoji2s[n].setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
										String allText = editorMail[n].getText();
										String modifyer = allText.replace("</html>", "");
										modifyer = modifyer.replace("</body>", "<img src='file:\\"+getPath+"\\imgs\\sad.jpg' width='20' height='20'/></body></html>");
										
										
										editorMail[n].setText(modifyer);
									}
									
									@Override
									public void mouseExited(MouseEvent arg0) {}
									
									@Override
									public void mouseEntered(MouseEvent arg0) {}
									
									@Override
									public void mouseClicked(MouseEvent arg0) {}
								});
								//Seta expressão feliz
								emoji3s[n].addMouseListener(new MouseListener() {
									
									@Override
									public void mouseReleased(MouseEvent arg0) {
										emoji3s[n].setBorder(null);
									}
									
									@Override
									public void mousePressed(MouseEvent arg0) {
										emoji3s[n].setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
										String allText = editorMail[n].getText();
										String modifyer = allText.replace("</html>", "");
										modifyer = modifyer.replace("</body>", "<img src='file:\\"+getPath+"\\imgs\\happy.jpg' width='20' height='20'/></body></html>");
										
										
										editorMail[n].setText(modifyer);
									}
									
									@Override
									public void mouseExited(MouseEvent arg0) {}
									
									@Override
									public void mouseEntered(MouseEvent arg0) {}
									
									@Override
									public void mouseClicked(MouseEvent arg0) {}
								});
								//Seta expressão nervosa
								emoji4s[n].addMouseListener(new MouseListener() {
									
									@Override
									public void mouseReleased(MouseEvent arg0) {
										emoji4s[n].setBorder(null);
									}
									
									@Override
									public void mousePressed(MouseEvent arg0) {
										emoji4s[n].setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
										String allText = editorMail[n].getText();
										String modifyer = allText.replace("</html>", "");
										modifyer = modifyer.replace("</body>", "<img src='file:\\"+getPath+"\\imgs\\nervous.jpg' width='20' height='20'/></body></html>");
										
										
										editorMail[n].setText(modifyer);
									}
									
									@Override
									public void mouseExited(MouseEvent arg0) {}
									
									@Override
									public void mouseEntered(MouseEvent arg0) {}
									
									@Override
									public void mouseClicked(MouseEvent arg0) {}
								});
								//Seta expressão assustada
								emoji5s[n].addMouseListener(new MouseListener() {
									
									@Override
									public void mouseReleased(MouseEvent arg0) {
										emoji5s[n].setBorder(null);
									}
									
									@Override
									public void mousePressed(MouseEvent arg0) {
										emoji5s[n].setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
										String allText = editorMail[n].getText();
										String modifyer = allText.replace("</html>", "");
										modifyer = modifyer.replace("</body>", "<img src='file:\\"+getPath+"\\imgs\\impressioned.jpg' width='20' height='20'/></body></html>");
										
										
										editorMail[n].setText(modifyer);
									}
									
									@Override
									public void mouseExited(MouseEvent arg0) {}
									
									@Override
									public void mouseEntered(MouseEvent arg0) {}
									
									@Override
									public void mouseClicked(MouseEvent arg0) {}
								});
								//Seta expressão séria
								emoji6s[n].addMouseListener(new MouseListener() {
									
									@Override
									public void mouseReleased(MouseEvent arg0) {
										emoji6s[n].setBorder(null);
									}
									
									@Override
									public void mousePressed(MouseEvent arg0) {
										emoji6s[n].setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
										String allText = editorMail[n].getText();
										String modifyer = allText.replace("</html>", "");
										modifyer = modifyer.replace("</body>", "<img src='file:\\"+getPath+"\\imgs\\seriosly.jpg' width='20' height='20'/></body></html>");
										
										
										editorMail[n].setText(modifyer);
									}
									
									@Override
									public void mouseExited(MouseEvent arg0) {}
									
									@Override
									public void mouseEntered(MouseEvent arg0) {}
									
									@Override
									public void mouseClicked(MouseEvent arg0) {}
								});
								//Ação para escrever mensagens no Editor
								labelWrite[n].addMouseListener(new MouseListener() {
									
									@Override
									public void mouseReleased(MouseEvent arg0) {
										labelWrite[n].setBorder(null);
										editorMail[n].setVisible(true);
										scrollMail[n].setVisible(true);
										editorMail1[n].setVisible(false);
										scrollMail1[n].setVisible(false);
										from[n].setText("<html><font color='blue'>de : </font>"+nameComp+"</html>");
										to[n].setText("<html><font color='blue'>para : </font>"+arrayComps[n]+"</html>");
									}
									
									@Override
									public void mousePressed(MouseEvent arg0) {
										labelWrite[n].setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
									}
									
									@Override
									public void mouseExited(MouseEvent arg0) {}
									
									@Override
									public void mouseEntered(MouseEvent arg0) {}
									
									@Override
									public void mouseClicked(MouseEvent arg0) {}
								});
								//Ação para ler mensagens do Servidor Web
								labelRead[n].addMouseListener(new MouseListener() {
									
									@Override
									public void mouseReleased(MouseEvent arg0) {
										labelRead[n].setBorder(null);
										editorMail[n].setVisible(false);
										scrollMail[n].setVisible(false);
										editorMail1[n].setVisible(true);
										scrollMail1[n].setVisible(true);
										from[n].setText("<html><font color='blue'>de : </font>"+arrayComps[n]+"</html>");
										to[n].setText("<html><font color='blue'>para : </font>"+nameComp+"</html>");
										mailBases = "";
										try {
											String ip = connect.getNetwork();
											ip = ip.replace("/", "");
											connect.changePath("mensages");
											connect.getMsgFiles(arrayComps[n], ip, true);
											if(connect.finded){
												for(int i = connect.major; i >= 0; i--){
													String numeration = "#"+i+"{"+ip+"-";
													int rl = numeration.length();
													String readers = connect.readeds.substring(
													connect.readeds.indexOf("#"+i+"{"+ip+"-")+rl, connect.readeds.lastIndexOf("}"+i+"#"));
													
													String myMsgFiles = "";
													FileReader msgReader;
													if(readers.equals("nonread")){
														myMsgFiles = "#"+i+"[newsletter_"+arrayComps[n]+"-"+ip+"].msg";
														msgReader = new FileReader(getPath+"\\\\msgs\\\\"+myMsgFiles);
													}else{
														myMsgFiles = "#"+i+"[newsletter_"+arrayComps[n]+"-"+ip+"{read}].msg";
														msgReader = new FileReader(getPath+"\\\\read\\\\"+myMsgFiles);
													}
													
													BufferedReader msgBuffer = new BufferedReader(msgReader);
													String readMsg = msgBuffer.readLine();
													String eachFile = "";
													while(readMsg != null){
														mailBases += readMsg;
														eachFile += readMsg + "\r\n";
														readMsg = msgBuffer.readLine();
													}
													msgBuffer.close();
													
													
													if(readers.equals("nonread")){
														File file = new File(getPath+"\\\\msgs\\\\"+myMsgFiles);
														file.delete();
														String myMsgFiles1 = "#"+i+"[newsletter_"+arrayComps[n]+"-"+ip+"{read}].msg";
														FileWriter msgWriter = new FileWriter(getPath+"\\\\read\\\\"+myMsgFiles1);
														msgWriter.write(eachFile);
														msgWriter.close();
														connect.replace(myMsgFiles, myMsgFiles1);
													}
													
													System.out.println("======Recebendo - labelRead[n]========");
													System.out.println(arrayComps[n]+": "+numeration+readers+"...");
													System.out.println("======================================");
													
													String RepDirEmot1 = mailBases.substring(mailBases.indexOf("file:\\")+6, mailBases.lastIndexOf("\\imgs\\"));
													mailBases = mailBases.replace(RepDirEmot1, getPath);
													mailBases = mailBases.replace("<html>", "").replace("</html>", "")
															.replace("<head>", "").replace("</head>", "").replace("<body>", "")
															.replace("</body>", "").replace("", "");
													editorMail1[n].setText(mailBases);
													
													
													
												}
											}
											connect.changePath("infos");
											mailBases = "";
											connect.readeds = "";

											connect.finded = false;
											connect.major = 0;
										} catch (IOException e) {
										
											e.printStackTrace();
										}
										
										labelNotifyers[n].setVisible(false);
										
									}
									
									@Override
									public void mousePressed(MouseEvent arg0) {
										labelRead[n].setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
									}
									
									@Override
									public void mouseExited(MouseEvent arg0) {}
									
									@Override
									public void mouseEntered(MouseEvent arg0) {}
									
									@Override
									public void mouseClicked(MouseEvent arg0) {}
								});
								//Ação para enviar mensagens ao Servidor Web
								labelSend[n].addMouseListener(new MouseListener() {
									
									@Override
									public void mouseReleased(MouseEvent arg0) {
										labelSend[n].setBorder(null);
										String themeSend = 
										JOptionPane.showInputDialog(null, "Assunto: ", "Assunto da mensagem", JOptionPane.PLAIN_MESSAGE);
										
										String replaceCode = editorMail[n].getText().replace("<body>", "<body><hr><br><p>de : "+nameComp+"<br>para : null</p><p><b>Assunto : "+themeSend+"</b></p>");
										replaceCode = replaceCode.replace("</body>", "<br><hr></body>");
										FileWriter writeMail;
										
										
												String atualIp = arrayIps[n];
												String atualComp = arrayComps[n];
												replaceCode = replaceCode.replace("<br>para : null</p>", "<br>para : "+atualComp+"</p>");
												
												try {
													connect.changePath("mensages");
													connect.getMsgFiles("All", arrayIps[n], false);
													int increment = 0;
													if(!connect.finded){increment = 0;}else{increment = connect.major + 1;}
													String data = "#"+increment+"[newsletter_"+nameComp+"-"+atualIp+"].msg";
													writeMail = new FileWriter(getPath+"\\\\msgs\\\\"+data);
													writeMail.write(replaceCode);
													writeMail.close();
										
													connect.upload(data, getPath+"/msgs/");
													connect.readeds = "";
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												
												
										
										try {
											connect.changePath("infos");
											connect.readeds = "";

											connect.finded = false;
											connect.major = 0;
										} catch (IOException e) {}
										
									}
									
									@Override
									public void mousePressed(MouseEvent arg0) {
										labelSend[n].setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
									}
									
									@Override
									public void mouseExited(MouseEvent arg0) {}
									
									@Override
									public void mouseEntered(MouseEvent arg0) {}
									
									@Override
									public void mouseClicked(MouseEvent arg0) {}
								});
								
								frameMsg[n].setVisible(true);
								labelNotifyers[n].setVisible(false);
							}
							
							@Override
							public void mouseExited(MouseEvent arg0) {}
							
							@Override
							public void mouseEntered(MouseEvent arg0) {}
							
							@Override
							public void mouseClicked(MouseEvent arg0) {}
						});
						
						BlockListener = new MouseListener() {
							
							@Override
							public void mouseReleased(MouseEvent arg0) {
								Component arg = (Component) arg0.getSource();
								if(arg.isEnabled()){
									labelBlockUser[n].setBorder(null);
									String ip = connect.getNetwork();
									ip = ip.replace("/", "");
									String subName = labelBlockUser[n].getName().substring(labelBlockUser[n].getName().indexOf("$")+1,
											labelBlockUser[n].getName().lastIndexOf("{"));
									
									if(JOptionPane.showConfirmDialog(null, "Deseja bloquear as mensagens de "+subName+"?", "Bloqueio de usuários", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
											== JOptionPane.YES_OPTION){
										String dataSave = "%"+nameComp+"$"+ip+"&.info";
										try {
											FileReader ReadData = new FileReader(getPath+"/log/"+dataSave);
											bufferData = new BufferedReader(ReadData);
											String dataread = bufferData.readLine();
											
											String subStringBlock = dataread.substring(dataread.indexOf("Blocks{")+7, dataread.lastIndexOf("}Blocks"));
											String replaceBlock = subStringBlock+", "+labelBlockUser[n].getName();
											dataread = dataread.replace("Blocks{"+subStringBlock+"}Blocks", "Blocks{"+replaceBlock+"}Blocks");
											bufferData.close();
											ReadData.close();
											FileWriter fileWriter = new FileWriter(getPath+"/log/"+dataSave);
											fileWriter.write(dataread);
											fileWriter.close();
											connect.upload(dataSave, getPath+"/log/");
										} catch (FileNotFoundException e) {} catch (IOException e) {}
										
									  if(!arrayComps[n].equals(nameComp)){
										if(statusUser[n].getName().equals("on")){
											try{
												String text = "$"+nameComp+"[{"+arrayIps[n]+"}yes]"+nameComp;
												Socket socketStatus = new Socket(arrayIps[n], 8181);
												BufferedOutputStream outputStatus = new BufferedOutputStream(socketStatus.getOutputStream());
								
												byte[] byteText = text.getBytes();
												outputStatus.write(byteText);
												outputStatus.flush();
												outputStatus.close();
												socketStatus.close();
								
								
											}catch(UnknownHostException unknown){unknown.printStackTrace();}catch(IOException io){io.printStackTrace();}
											
										}
									  }
									
									}else{
										String dataSave = "%"+nameComp+"$"+ip+"&.info";
										try {
											FileReader ReadData = new FileReader(getPath+"/log/"+dataSave);
											bufferData = new BufferedReader(ReadData);
											String dataread = bufferData.readLine();
											
											String subStringBlock = dataread.substring(dataread.indexOf("Blocks{")+7, dataread.lastIndexOf("}Blocks"));
											String replaceBlock = subStringBlock.replace(", "+labelBlockUser[n].getName(), "");
											dataread = dataread.replace("Blocks{"+subStringBlock+"}Blocks", "Blocks{"+replaceBlock+"}Blocks");
											bufferData.close();
											ReadData.close();
											FileWriter fileWriter = new FileWriter(getPath+"/log/"+dataSave);
											fileWriter.write(dataread);
											fileWriter.close();
											connect.upload(dataSave, getPath+"/log/");
										} catch (FileNotFoundException e) {} catch (IOException e) {}
										
									  if(!arrayComps[n].equals(nameComp)){
										if(statusUser[n].getName().equals("on")){
											try{
												String text = "$"+nameComp+"[{"+arrayIps[n]+"}no]"+nameComp;
												Socket socketStatus = new Socket(arrayIps[n], 8181);
												BufferedOutputStream outputStatus = new BufferedOutputStream(socketStatus.getOutputStream());
								
												byte[] byteText = text.getBytes();
												outputStatus.write(byteText);
												outputStatus.flush();
												outputStatus.close();
												socketStatus.close();
								
								
											}catch(UnknownHostException unknown){unknown.printStackTrace();}catch(IOException io){io.printStackTrace();}
											
										}
									  }
									}
								}
								
								
							}
							
							@Override
							public void mousePressed(MouseEvent arg0) {
								Component arg = (Component) arg0.getSource();
								if(arg.isEnabled()){
									labelBlockUser[n].setBorder(BorderFactory.createLineBorder(Color.blue));
								}
								
							}
							
							@Override
							public void mouseExited(MouseEvent arg0) {}
							
							@Override
							public void mouseEntered(MouseEvent arg0) {}
							
							@Override
							public void mouseClicked(MouseEvent arg0) {}
						};
						
						//para bloquear respectivo usuário
						labelBlockUser[n].addMouseListener(BlockListener);
						//2
						labelDocUser[n].addMouseListener(new MouseListener() {
							
							@Override
							public void mouseReleased(MouseEvent arg0) {
								Component arg = (Component) arg0.getSource();
								if(arg.isEnabled()){
									labelDocUser[n].setBorder(null);
									
									String ip = connect.getNetwork();
									ip = ip.replace("/", "");
									
									JFileChooser file = new JFileChooser(); 
									File arquivo;
									String path = "";
							        file.setFileSelectionMode(JFileChooser.FILES_ONLY);
							        int i= file.showSaveDialog(null);
							        if (i==1){
							        	path = "";
							        } else {
							        	arquivo = file.getSelectedFile();
							        	path = arquivo.getPath();
							        }
							       
							        int accept = JOptionPane.showConfirmDialog(null, "Deseja mesmo enviar o arquivo '"+path+"'?");
									
										if(accept == JOptionPane.YES_OPTION){
											try {
												File files = new File(path);
												
												FileInputStream inputStream = new FileInputStream(files);
												
												Socket server = new Socket(labelDocUser[n].getName(), 6161);
												OutputStream output = server.getOutputStream();
												OutputStreamWriter outputWriter = new OutputStreamWriter(output);
												
												BufferedWriter writer = new BufferedWriter(outputWriter);
												writer.write(files.getName()+"\n");
												writer.flush();
												
												int size = 4096;
												byte[] buffer = new byte[size];
												int reads = -1;
												while((reads = inputStream.read(buffer, 0, size)) != -1){
													output.write(buffer, 0, reads);
												}
												
												
												JOptionPane.showMessageDialog(null, "<html><font color='blue'>Arquivo Enviado!</font></html>");
												
												output.close();
												server.close();
											} catch (FileNotFoundException e) {} catch (UnknownHostException e) {
												e.printStackTrace();
											} catch (IOException e) {
												e.printStackTrace();
											}
										}
									
									
								}
							}
							
							@Override
							public void mousePressed(MouseEvent arg0) {
								Component arg = (Component) arg0.getSource();
								if(arg.isEnabled()){
									labelDocUser[n].setBorder(BorderFactory.createLineBorder(Color.blue));
								}
							}
							
							@Override
							public void mouseExited(MouseEvent arg0) {}
							
							@Override
							public void mouseEntered(MouseEvent arg0) {}
							
							@Override
							public void mouseClicked(MouseEvent arg0) {}
						});
						//Para enviar conversas ao servidor do usuário (Lado cliente)
						inputText[n].addKeyListener(new KeyListener() {
							
							@Override
							public void keyTyped(KeyEvent arg0) {}
							
							@Override
							public void keyReleased(KeyEvent arg0) {}
							
							@Override
							public void keyPressed(KeyEvent event) {
								int key = event.getKeyCode();
					
								if(key == KeyEvent.VK_ENTER){
		
									String memoryChars = "";
									char[] characters = inputText[n].getText().toCharArray();
									
									int v = 0;
									for(int j = 0; j < characters.length; j++){
										if(v == 62){
											memoryChars += characters[j]+"<br>";
											if(memoryChars.contains("{:)}")){
												memoryChars = memoryChars.replace("{:)}", "<img src='file:\\"+getPath+"\\imgs\\smile.jpg' width='20' height='20'/>");
												saveChars1 = "{:)}";
												saveChars2 = "<img src='file:\\"+getPath+"\\imgs\\smile.jpg' width='20' height='20'/>";
											}
											if(memoryChars.contains("{:(}")){
												memoryChars = memoryChars.replace("{:(}", "<img src='file:\\"+getPath+"\\imgs\\sad.jpg' width='20' height='20'/>");
												saveChars1 = "{:(}";
												saveChars2 = "<img src='file:\\"+getPath+"\\imgs\\sad.jpg' width='20' height='20'/>";
											}
											if(memoryChars.contains("{:D}")){
												memoryChars = memoryChars.replace("{:D}", "<img src='file:\\"+getPath+"\\imgs\\happy.jpg' width='20' height='20'/>");
												saveChars1 = "{:D}";
												saveChars2 = "<img src='file:\\"+getPath+"\\imgs\\happy.jpg' width='20' height='20'/>";
											}
											if(memoryChars.contains("{:z}")){
												memoryChars = memoryChars.replace("{:z}", "<img src='file:\\"+getPath+"\\imgs\\nervous.jpg' width='20' height='20'/>");
												saveChars1 = "{:z}";
												saveChars2 = "<img src='file:\\"+getPath+"\\imgs\\nervous.jpg' width='20' height='20'/>";
											}
											if(memoryChars.contains("{:|}")){
												memoryChars = memoryChars.replace("{:|}", "<img src='file:\\"+getPath+"\\imgs\\seriosly.jpg' width='20' height='20'/>");
												saveChars1 = "{:|}";
												saveChars2 = "<img src='file:\\"+getPath+"\\imgs\\seriosly.jpg' width='20' height='20'/>";
											}
											if(memoryChars.contains("{:o}")){
												memoryChars = memoryChars.replace("{:o}", "<img src='file:\\"+getPath+"\\imgs\\impressioned.jpg' width='20' height='20'/>");
												saveChars1 = "{:o}";
												saveChars2 = "<img src='file:\\"+getPath+"\\imgs\\impressioned.jpg' width='20' height='20'/>";
											}
											v = 0;
										}else{
											memoryChars += characters[j];
											if(memoryChars.contains("{:)}")){
												memoryChars = memoryChars.replace("{:)}", "<img src='file:\\"+getPath+"\\imgs\\smile.jpg' width='20' height='20'/>");
												saveChars1 = "{:)}";
												saveChars2 = "<img src='file:\\"+getPath+"\\imgs\\smile.jpg' width='20' height='20'/>";
											}
											if(memoryChars.contains("{:(}")){
												memoryChars = memoryChars.replace("{:(}", "<img src='file:\\"+getPath+"\\imgs\\sad.jpg' width='20' height='20'/>");
												saveChars1 = "{:(}";
												saveChars2 = "<img src='file:\\"+getPath+"\\imgs\\sad.jpg' width='20' height='20'/>";
											}
											if(memoryChars.contains("{:D}")){
												memoryChars = memoryChars.replace("{:D}", "<img src='file:\\"+getPath+"\\imgs\\happy.jpg' width='20' height='20'/>");
												saveChars1 = "{:D}";
												saveChars2 = "<img src='file:\\"+getPath+"\\imgs\\happy.jpg' width='20' height='20'/>";
											}
											if(memoryChars.contains("{:z}")){
												memoryChars = memoryChars.replace("{:z}", "<img src='file:\\"+getPath+"\\imgs\\nervous.jpg' width='20' height='20'/>");
												saveChars1 = "{:z}";
												saveChars2 = "<img src='file:\\"+getPath+"\\imgs\\nervous.jpg' width='20' height='20'/>";
											}
											if(memoryChars.contains("{:|}")){
												memoryChars = memoryChars.replace("{:|}", "<img src='file:\\"+getPath+"\\imgs\\seriosly.jpg' width='20' height='20'/>");
												saveChars1 = "{:|}";
												saveChars2 = "<img src='file:\\"+getPath+"\\imgs\\seriosly.jpg' width='20' height='20'/>";
											}
											if(memoryChars.contains("{:o}")){
												memoryChars = memoryChars.replace("{:o}", "<img src='file:\\"+getPath+"\\imgs\\impressioned.jpg' width='20' height='20'/>");
												saveChars1 = "{:o}";
												saveChars2 = "<img src='file:\\"+getPath+"\\imgs\\impressioned.jpg' width='20' height='20'/>";
											}
											v = v + 1;
										}
										
									}
									
									if(atualStatus[n].equals("on")){
										String identification = "[id{"+nameComp+"}id] ";
										mensagesBase[n] = editorChat[n].getText().replace("<html>", "").replace("</html>", "")
												.replace("<head>", "").replace("</head>", "").replace("<body>", "")
												.replace("</body>", "");
										mensagesBase[n] += "<br><font color='blue'>"+nameComp+" diz</font>"
												+ " : "+memoryChars;
										
										editorChat[n].setText(mensagesBase[n]);
										
										inputText[n].setText("");
										String text = null;
										try{
											//.replaceAll(saveChars2, saveChars1)
											text = identification+memoryChars;
											
											
											Socket socketSend = new Socket(inputText[n].getName(), 7171);
											BufferedOutputStream output = new BufferedOutputStream(socketSend.getOutputStream());
											
											byte[] byteText = text.getBytes();
											output.write(byteText);
											output.flush();
				                            output.close();
				                            socketSend.close();
											
											
										}catch(UnknownHostException unknown){unknown.printStackTrace();}catch(IOException io){io.printStackTrace();}
										
										System.out.println("O texto é : "+text);
										
									}else{
										JOptionPane.showMessageDialog(null, "O usuário está offline! Não é possível enviar uma mensagem.");
									}
									
									
								}
							}
						});
						
						//Para enviar conversas ao servidor do usuário (Lado cliente)
						buttomBottom[i].addMouseListener(new MouseListener() {
							
							@Override
							public void mouseReleased(MouseEvent arg0) {}
							
							@Override
							public void mousePressed(MouseEvent arg0) {
								String memoryChars = "";
								char[] characters = inputText[n].getText().toCharArray();
								
								int v = 0;
								for(int j = 0; j < characters.length; j++){
									if(v == 62){
										memoryChars += characters[j]+"<br>";
										if(memoryChars.contains("{:)}")){
											memoryChars = memoryChars.replace("{:)}", "<img src='file:\\"+getPath+"\\imgs\\smile.jpg' width='20' height='20'/>");
											saveChars1 = "{:)}";
											saveChars2 = "<img src='file:\\"+getPath+"\\imgs\\smile.jpg' width='20' height='20'/>";
										}
										if(memoryChars.contains("{:(}")){
											memoryChars = memoryChars.replace("{:(}", "<img src='file:\\"+getPath+"\\imgs\\sad.jpg' width='20' height='20'/>");
											saveChars1 = "{:(}";
											saveChars2 = "<img src='file:\\"+getPath+"\\imgs\\sad.jpg' width='20' height='20'/>";
										}
										if(memoryChars.contains("{:D}")){
											memoryChars = memoryChars.replace("{:D}", "<img src='file:\\"+getPath+"\\imgs\\happy.jpg' width='20' height='20'/>");
											saveChars1 = "{:D}";
											saveChars2 = "<img src='file:\\"+getPath+"\\imgs\\happy.jpg' width='20' height='20'/>";
										}
										if(memoryChars.contains("{:z}")){
											memoryChars = memoryChars.replace("{:z}", "<img src='file:\\"+getPath+"\\imgs\\nervous.jpg' width='20' height='20'/>");
											saveChars1 = "{:z}";
											saveChars2 = "<img src='file:\\"+getPath+"\\imgs\\nervous.jpg' width='20' height='20'/>";
										}
										if(memoryChars.contains("{:|}")){
											memoryChars = memoryChars.replace("{:|}", "<img src='file:\\"+getPath+"\\imgs\\seriosly.jpg' width='20' height='20'/>");
											saveChars1 = "{:|}";
											saveChars2 = "<img src='file:\\"+getPath+"\\imgs\\seriosly.jpg' width='20' height='20'/>";
										}
										if(memoryChars.contains("{:o}")){
											memoryChars = memoryChars.replace("{:o}", "<img src='file:\\"+getPath+"\\imgs\\impressioned.jpg' width='20' height='20'/>");
											saveChars1 = "{:o}";
											saveChars2 = "<img src='file:\\"+getPath+"\\imgs\\impressioned.jpg' width='20' height='20'/>";
										}
										v = 0;
									}else{
										memoryChars += characters[j];
										if(memoryChars.contains("{:)}")){
											memoryChars = memoryChars.replace("{:)}", "<img src='file:\\"+getPath+"\\imgs\\smile.jpg' width='20' height='20'/>");
											saveChars1 = "{:)}";
											saveChars2 = "<img src='file:\\"+getPath+"\\imgs\\smile.jpg' width='20' height='20'/>";
										}
										if(memoryChars.contains("{:(}")){
											memoryChars = memoryChars.replace("{:(}", "<img src='file:\\"+getPath+"\\imgs\\sad.jpg' width='20' height='20'/>");
											saveChars1 = "{:(}";
											saveChars2 = "<img src='file:\\"+getPath+"\\imgs\\sad.jpg' width='20' height='20'/>";
										}
										if(memoryChars.contains("{:D}")){
											memoryChars = memoryChars.replace("{:D}", "<img src='file:\\"+getPath+"\\imgs\\happy.jpg' width='20' height='20'/>");
											saveChars1 = "{:D}";
											saveChars2 = "<img src='file:\\"+getPath+"\\imgs\\happy.jpg' width='20' height='20'/>";
										}
										if(memoryChars.contains("{:z}")){
											memoryChars = memoryChars.replace("{:z}", "<img src='file:\\"+getPath+"\\imgs\\nervous.jpg' width='20' height='20'/>");
											saveChars1 = "{:z}";
											saveChars2 = "<img src='file:\\"+getPath+"\\imgs\\nervous.jpg' width='20' height='20'/>";
										}
										if(memoryChars.contains("{:|}")){
											memoryChars = memoryChars.replace("{:|}", "<img src='file:\\"+getPath+"\\imgs\\seriosly.jpg' width='20' height='20'/>");
											saveChars1 = "{:|}";
											saveChars2 = "<img src='file:\\"+getPath+"\\imgs\\seriosly.jpg' width='20' height='20'/>";
										}
										if(memoryChars.contains("{:o}")){
											memoryChars = memoryChars.replace("{:o}", "<img src='file:\\"+getPath+"\\imgs\\impressioned.jpg' width='20' height='20'/>");
											saveChars1 = "{:o}";
											saveChars2 = "<img src='file:\\"+getPath+"\\imgs\\impressioned.jpg' width='20' height='20'/>";
										}
										v = v + 1;
									}
									
								}
								if(atualStatus[n].equals("on")){
									String identification = "[id{"+nameComp+"}id] ";
									mensagesBase[n] = editorChat[n].getText().replace("<html>", "").replace("</html>", "")
											.replace("<head>", "").replace("</head>", "").replace("<body>", "")
											.replace("</body>", "");
									mensagesBase[n] += "<br><font color='blue'>"+nameComp+" diz</font>"
											+ " : "+memoryChars;
									
									editorChat[n].setText(mensagesBase[n]);
									
									inputText[n].setText("");
									
									try{
										//.replaceAll(saveChars2, saveChars1)
										String text = identification+memoryChars;
										System.out.println("O texto é : "+text);
										Socket socketSend = new Socket(inputText[n].getName(), 7171);
										BufferedOutputStream output = new BufferedOutputStream(socketSend.getOutputStream());
										
										byte[] byteText = text.getBytes();
										output.write(byteText);
										output.flush();
			                            output.close();
			                            socketSend.close();
										
										
									}catch(UnknownHostException unknown){unknown.printStackTrace();}catch(IOException io){io.printStackTrace();}
									
									
									
								}else{
									JOptionPane.showMessageDialog(null, "O usuário está offline! Não é possível enviar uma mensagem.");
								}
							}
							
							@Override
							public void mouseExited(MouseEvent arg0) {}
							
							@Override
							public void mouseEntered(MouseEvent arg0) {}
							
							@Override
							public void mouseClicked(MouseEvent arg0) {}
						});
						//torna palavras itálico
						italic[n].addMouseListener(new MouseListener() {
							
							@Override
							public void mouseReleased(MouseEvent arg0) {
								italic[n].setBorder(null);
							}
							
							@Override
							public void mousePressed(MouseEvent arg0) {
								italic[n].setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
								String allText = inputText[n].getText();
								String selected = inputText[n].getSelectedText();
								String modifyer = allText.replace(selected, " <i>"+selected+"</i> ");
								
								inputText[n].setText(modifyer);
								
								
							}
							
							@Override
							public void mouseExited(MouseEvent arg0) {}
							
							@Override
							public void mouseEntered(MouseEvent arg0) {}
							
							@Override
							public void mouseClicked(MouseEvent arg0) {}
						});
						//torna palavras negrito
						strong[n].addMouseListener(new MouseListener() {
							
							@Override
							public void mouseReleased(MouseEvent arg0) {
								strong[n].setBorder(null);
							}
							
							@Override
							public void mousePressed(MouseEvent arg0) {
								strong[n].setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
								String allText = inputText[n].getText();
								String selected = inputText[n].getSelectedText();
								String modifyer = allText.replace(selected, " <b>"+selected+"</b> ");
								
								inputText[n].setText(modifyer);
							}
							
							@Override
							public void mouseExited(MouseEvent arg0) {}
							
							@Override
							public void mouseEntered(MouseEvent arg0) {}
							
							@Override
							public void mouseClicked(MouseEvent arg0) {}
						});
						//torna palavras sublinhadas
						underlined[n].addMouseListener(new MouseListener() {
							
							@Override
							public void mouseReleased(MouseEvent arg0) {
								underlined[n].setBorder(null);
							}
							
							@Override
							public void mousePressed(MouseEvent arg0) {
								underlined[n].setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
								String allText = inputText[n].getText();
								String selected = inputText[n].getSelectedText();
								String modifyer = allText.replace(selected, " <u>"+selected+"</u> ");
								
								inputText[n].setText(modifyer);
							}
							
							@Override
							public void mouseExited(MouseEvent arg0) {}
							
							@Override
							public void mouseEntered(MouseEvent arg0) {}
							
							@Override
							public void mouseClicked(MouseEvent arg0) {}
						});
						//torna palavras linkadas
						linked[n].addMouseListener(new MouseListener() {
							
							@Override
							public void mouseReleased(MouseEvent arg0) {
								linked[n].setBorder(null);
							}
							
							@Override
							public void mousePressed(MouseEvent arg0) {
								linked[n].setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
								String optionLink = JOptionPane.showInputDialog(null, "Digite um link", "Mensagens Linkadas", JOptionPane.PLAIN_MESSAGE);
								String allText = inputText[n].getText();
								String selected = inputText[n].getSelectedText();
								String modifyer = allText.replace(selected, " <a href='"+optionLink+"'>"+selected+"</a> ");
								
								inputText[n].setText(modifyer);
								
								
							}
							
							@Override
							public void mouseExited(MouseEvent arg0) {}
							
							@Override
							public void mouseEntered(MouseEvent arg0) {}
							
							@Override
							public void mouseClicked(MouseEvent arg0) {}
						});
						//Seta expressão sorridente
						emoji1[n].addMouseListener(new MouseListener() {
							
							@Override
							public void mouseReleased(MouseEvent arg0) {
								emoji1[n].setBorder(null);
							}
							
							@Override
							public void mousePressed(MouseEvent arg0) {
								emoji1[n].setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
								String allText = inputText[n].getText();
								String simbol = "{:)}";
								inputText[n].setText(allText+" "+simbol);
							}
							
							@Override
							public void mouseExited(MouseEvent arg0) {}
							
							@Override
							public void mouseEntered(MouseEvent arg0) {}
							
							@Override
							public void mouseClicked(MouseEvent arg0) {}
						});
						//Seta expressão triste
						emoji2[n].addMouseListener(new MouseListener() {
							
							@Override
							public void mouseReleased(MouseEvent arg0) {
								emoji2[n].setBorder(null);
							}
							
							@Override
							public void mousePressed(MouseEvent arg0) {
								emoji2[n].setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
								String allText = inputText[n].getText();
								String simbol = "{:(}";
								inputText[n].setText(allText+" "+simbol);
							}
							
							@Override
							public void mouseExited(MouseEvent arg0) {}
							
							@Override
							public void mouseEntered(MouseEvent arg0) {}
							
							@Override
							public void mouseClicked(MouseEvent arg0) {}
						});
						//Seta expressão feliz
						emoji3[n].addMouseListener(new MouseListener() {
							
							@Override
							public void mouseReleased(MouseEvent arg0) {
								emoji3[n].setBorder(null);
							}
							
							@Override
							public void mousePressed(MouseEvent arg0) {
								emoji3[n].setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
								String allText = inputText[n].getText();
								String simbol = "{:D}";
								inputText[n].setText(allText+" "+simbol);
							}
							
							@Override
							public void mouseExited(MouseEvent arg0) {}
							
							@Override
							public void mouseEntered(MouseEvent arg0) {}
							
							@Override
							public void mouseClicked(MouseEvent arg0) {}
						});
						//Seta expressão nervosa
						emoji4[n].addMouseListener(new MouseListener() {
							
							@Override
							public void mouseReleased(MouseEvent arg0) {
								emoji4[n].setBorder(null);
							}
							
							@Override
							public void mousePressed(MouseEvent arg0) {
								emoji4[n].setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
								String allText = inputText[n].getText();
								String simbol = "{:z}";
								inputText[n].setText(allText+" "+simbol);
							}
							
							@Override
							public void mouseExited(MouseEvent arg0) {}
							
							@Override
							public void mouseEntered(MouseEvent arg0) {}
							
							@Override
							public void mouseClicked(MouseEvent arg0) {}
						});
						//Seta expressão assustada
						emoji5[n].addMouseListener(new MouseListener() {
							
							@Override
							public void mouseReleased(MouseEvent arg0) {
								emoji5[n].setBorder(null);
							}
							
							@Override
							public void mousePressed(MouseEvent arg0) {
								emoji5[n].setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
								String allText = inputText[n].getText();
								String simbol = "{:o}";
								inputText[n].setText(allText+" "+simbol);
							}
							
							@Override
							public void mouseExited(MouseEvent arg0) {}
							
							@Override
							public void mouseEntered(MouseEvent arg0) {}
							
							@Override
							public void mouseClicked(MouseEvent arg0) {}
						});
						//Seta expressão séria
						emoji6[n].addMouseListener(new MouseListener() {
							
							@Override
							public void mouseReleased(MouseEvent arg0) {
								emoji6[n].setBorder(null);
							}
							
							@Override
							public void mousePressed(MouseEvent arg0) {
								emoji6[n].setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
								String allText = inputText[n].getText();
								String simbol = "{:|}";
								inputText[n].setText(allText+" "+simbol);
							}
							
							@Override
							public void mouseExited(MouseEvent arg0) {}
							
							@Override
							public void mouseEntered(MouseEvent arg0) {}
							
							@Override
							public void mouseClicked(MouseEvent arg0) {}
						});
						
					}else{
						//Bloqueia determinado usuário
						computers[i].setForeground(Color.gray);
						computers[i].setCursor(null);
						computers[i].setEnabled(false);
						
						labelMsgUser[i].setCursor(null);
						labelBlockUser[i].setCursor(null);
						labelMsgUser[i].setIcon(msgUser1);
						labelBlockUser[i].setIcon(blockUser1);
						
						//pendente correção
						labelDocUser[i].setCursor(null);
						labelSoundUser[i].setCursor(null);
						labelDocUser[i].setIcon(docUser1);
						labelSoundUser[i].setIcon(soundUser1);
						perfileBlock.setIcon(block1);
					}
					
					
					
					alignY = alignY + 40;
					System.out.println("computador : "+nameComputer);
					System.out.println("ip : "+ipComputer);
				}
			}else{
				break;
			}
				
			
		}
		
		
		
		
		
		
		//A partir daqui, constrói o menu de recursos
		
		
		JLabel labelOff = new JLabel();
		labelOff.setIcon(imageOff);
		labelOff.setBounds(210, 1, 20, 20);
		labelOff.setToolTipText("Deslogar");
		labelOff.setCursor(new Cursor(Cursor.HAND_CURSOR));
		recursesDiv.add(labelOff);
		
		JLabel labelStatus = new JLabel();
		labelStatus.setIcon(online);
		labelStatus.setBounds(180, 1, 20, 20);
		labelStatus.setToolTipText("Ativado");
		labelStatus.setCursor(new Cursor(Cursor.HAND_CURSOR));
		recursesDiv.add(labelStatus);
		
		JLabel labelBlock = new JLabel();
		labelBlock.setIcon(block);
		labelBlock.setBounds(150, 1, 20, 20);
		labelBlock.setToolTipText("Bloquear Usuarios");
		labelBlock.setCursor(new Cursor(Cursor.HAND_CURSOR));
		recursesDiv.add(labelBlock);
		
		JLabel labelInfo = new JLabel();
		labelInfo.setIcon(info);
		labelInfo.setBounds(130, 0, 10, 20);
		labelInfo.setToolTipText("Informações da conta");
		labelInfo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		recursesDiv.add(labelInfo);
		
		JLabel labelConfig = new JLabel();
		labelConfig.setIcon(config);
		labelConfig.setBounds(95, 1, 20, 20);
		labelConfig.setToolTipText("Configurações");
		labelConfig.setCursor(new Cursor(Cursor.HAND_CURSOR));
		recursesDiv.add(labelConfig);
		
		
		
		JLabel labelMsg = new JLabel();
		labelMsg.setIcon(msg);
		labelMsg.setBounds(60, 1, 20, 20);
		labelMsg.setToolTipText("Mensagens");
		labelMsg.setCursor(new Cursor(Cursor.HAND_CURSOR));
		recursesDiv.add(labelMsg);
		
		
		
		
		//Ação de Logout do sistema
		labelOff.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				labelOff.setBorder(null);
				String ip = connect.getNetwork();
				ip = ip.replace("/", "");
				String dataSave = "%"+nameComp+"$"+ip+"&.info";
				
				try {
					connect.changePath("infos");
					FileReader ReadData = new FileReader(getPath+"/log/"+dataSave);
					bufferData = new BufferedReader(ReadData);
					String dataread = bufferData.readLine();
					dataread = dataread.replace("Status{on}Status", "Status{off}Status");
					bufferData.close();
					ReadData.close();
					FileWriter fileWriter = new FileWriter(getPath+"/log/"+dataSave);
					fileWriter.write(dataread);
					fileWriter.close();
					connect.upload(dataSave, getPath+"/log/");
				} catch (FileNotFoundException e1) {
					System.exit(0);
				} catch (IOException e) {
					System.exit(0);
				}
				
				int num = 0;
				while(arrayIps[num] != null){
				  if(!arrayComps[num].equals(nameComp)){
					if(statusUser[num].getName().equals("on")){
						try{
							String text = "#"+nameComp+"[{"+arrayIps[num]+"}off]"+nameComp;
							Socket socketStatus = new Socket(arrayIps[num], 8181);
							BufferedOutputStream outputStatus = new BufferedOutputStream(socketStatus.getOutputStream());
			
							byte[] byteText = text.getBytes();
							outputStatus.write(byteText);
							outputStatus.flush();
							outputStatus.close();
							socketStatus.close();
			
			
						}catch(UnknownHostException unknown){unknown.printStackTrace();}catch(IOException io){io.printStackTrace();}
						
					}
					
				  }
				  num = num + 1;
				}
				
				System.exit(0);
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				labelOff.setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		//Ação de esconder ou mostrar status
		labelStatus.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				labelStatus.setBorder(null);
				if(labelStatus.getToolTipText().equals("Ativado")){
				
					String ip = connect.getNetwork();
					ip = ip.replace("/", "");
					String dataSave = "%"+nameComp+"$"+ip+"&.info";
					//Registra no servidor web atualização de status
					try {
						FileReader ReadData = new FileReader(getPath+"/log/"+dataSave);
						bufferData = new BufferedReader(ReadData);
						String dataread = bufferData.readLine();
						dataread = dataread.replace("Status{on}Status", "Status{off}Status");
						bufferData.close();
						ReadData.close();
						
						FileWriter fileWriter = new FileWriter(getPath+"/log/"+dataSave);
						fileWriter.write(dataread);
						fileWriter.close();
						connect.upload(dataSave, getPath+"/log/");
						
						JOptionPane.showMessageDialog(null, "<html><font color='red'>você está offline para os usuários!</font></html>");
						labelStatus.setIcon(offline);
						labelStatus.setToolTipText("Desativado");
						status = false;
					} catch (IOException e) {}
					//Envia aos servidores atualização de status
					int num = 0;
					while(arrayIps[num] != null){
					  if(!arrayComps[num].equals(nameComp)){
						if(statusUser[num].getName().equals("on")){
							try{
								String text = "#"+nameComp+"[{"+arrayIps[num]+"}off]"+nameComp;
								Socket socketStatus = new Socket(arrayIps[num], 8181);
								BufferedOutputStream outputStatus = new BufferedOutputStream(socketStatus.getOutputStream());
				
								byte[] byteText = text.getBytes();
								outputStatus.write(byteText);
								outputStatus.flush();
								outputStatus.close();
								socketStatus.close();
				
				
							}catch(UnknownHostException unknown){unknown.printStackTrace();}catch(IOException io){io.printStackTrace();}
							
						}
					
					  }
					  num = num + 1;
					}
					
				}else{
					String ip = connect.getNetwork();
					ip = ip.replace("/", "");
					String dataSave = "%"+nameComp+"$"+ip+"&.info";
					//Registra no servidor web atualização de status
					try {
						FileReader ReadData = new FileReader(getPath+"/log/"+dataSave);
						bufferData = new BufferedReader(ReadData);
						String dataread = bufferData.readLine();
						dataread = dataread.replace("Status{off}Status", "Status{on}Status");
						bufferData.close();
						ReadData.close();
						FileWriter fileWriter = new FileWriter(getPath+"/log/"+dataSave);
						fileWriter.write(dataread);
						fileWriter.close();
						connect.upload(dataSave, getPath+"/log/");
						
						JOptionPane.showMessageDialog(null, "<html><font color='green'>você está online para os usuários!</font></html>");
						labelStatus.setIcon(online);
						labelStatus.setToolTipText("Ativado");
						status = true;
					} catch (IOException e) {}
					//Envia aos servidores atualização de status
					int num = 0;
					while(arrayIps[num] != null){
					  if(!arrayComps[num].equals(nameComp)){
						if(statusUser[num].getName().equals("on")){
							try{
								String text = "#"+nameComp+"[{"+arrayIps[num]+"}on]"+nameComp;
								Socket socketStatus = new Socket(arrayIps[num], 8181);
								BufferedOutputStream outputStatus = new BufferedOutputStream(socketStatus.getOutputStream());
				
								byte[] byteText = text.getBytes();
								outputStatus.write(byteText);
								outputStatus.flush();
								outputStatus.close();
								socketStatus.close();
				
				
							}catch(UnknownHostException unknown){unknown.printStackTrace();}catch(IOException io){io.printStackTrace();}
							
						}
						
					   }
					  num = num + 1;
					}
				}
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				labelStatus.setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		
		
		//Ação de exibir informações da conta
		labelInfo.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				labelInfo.setBorder(null);
				String network = connect.getNetwork();
			network = network.replace("/", "");
				if(status){
					JOptionPane.showMessageDialog(null, "<html>Ip do computador : "+network+"<br>"
							+ "Nome do computador : "+nameComp+"<br>"
						    + "Usuário : "+userLogin+"<br>"
						    + "Senha : "+passLogin+"<br>"
						    + "Status : <font color='green'>Online</font></html>");
				}else{
					JOptionPane.showMessageDialog(null, "<html>Ip do computador : "+network+"<br>"
							+ "Nome do computador : "+nameComp+"<br>"
						    + "Usuário : "+userLogin+"<br>"
						    + "Senha : "+passLogin+"<br>"
						    + "Status : <font color='red'>Offline</font></html>");
				}
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				labelInfo.setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		//Ação de bloqueio de mensagens
		labelBlock.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				labelBlock.setBorder(null);
				String ip = connect.getNetwork();
				ip = ip.replace("/", "");
				String dataSave = "%"+nameComp+"$"+ip+"&.info";
				if(JOptionPane.showConfirmDialog(null, "Deseja ficar bloqueado na rede?", "Bloqueio de usuários", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
						== JOptionPane.YES_OPTION){
					//registra no servidor web o bloqueio
					try {
						FileReader ReadData = new FileReader(getPath+"/log/"+dataSave);
						bufferData = new BufferedReader(ReadData);
						String dataread = bufferData.readLine();
						dataread = dataread.replace("Blocked{no}Blocked", "Blocked{yes}Blocked");
						bufferData.close();
						ReadData.close();
						FileWriter fileWriter = new FileWriter(getPath+"/log/"+dataSave);
						fileWriter.write(dataread);
						fileWriter.close();
						connect.upload(dataSave, getPath+"/log/");
					} catch (FileNotFoundException e) {} catch (IOException e) {}
					//Envia ao servidores o bloqueio
					int num = 0;
					while(arrayIps[num] != null){
					  if(!arrayComps[num].equals(nameComp)){
						if(statusUser[num].getName().equals("on")){
							try{
								String text = "$"+nameComp+"[{"+arrayIps[num]+"}yes]"+nameComp;
								Socket socketStatus = new Socket(arrayIps[num], 8181);
								BufferedOutputStream outputStatus = new BufferedOutputStream(socketStatus.getOutputStream());
				
								byte[] byteText = text.getBytes();
								outputStatus.write(byteText);
								outputStatus.flush();
								outputStatus.close();
								socketStatus.close();
				
				
							}catch(UnknownHostException unknown){unknown.printStackTrace();}catch(IOException io){io.printStackTrace();}
							
						}
						
					  }
					  num = num + 1;
					}
					
				}else{
					//registra no servidor web o bloqueio
					try {
						FileReader ReadData = new FileReader(getPath+"/log/"+dataSave);
						bufferData = new BufferedReader(ReadData);
						String dataread = bufferData.readLine();
						dataread = dataread.replace("Blocked{yes}Blocked", "Blocked{no}Blocked");
						bufferData.close();
						ReadData.close();
						FileWriter fileWriter = new FileWriter(getPath+"/log/"+dataSave);
						fileWriter.write(dataread);
						fileWriter.close();
						connect.upload(dataSave, getPath+"/log/");
					} catch (FileNotFoundException e) {} catch (IOException e) {}
					//Envia ao servidores o bloqueio
					int num = 0;
					while(arrayIps[num] != null){
					  if(!arrayComps[num].equals(nameComp)){
						if(statusUser[num].getName().equals("on")){
							try{
								String text = "$"+nameComp+"[{"+arrayIps[num]+"}no]"+nameComp;
								Socket socketStatus = new Socket(arrayIps[num], 8181);
								BufferedOutputStream outputStatus = new BufferedOutputStream(socketStatus.getOutputStream());
				
								byte[] byteText = text.getBytes();
								outputStatus.write(byteText);
								outputStatus.flush();
								outputStatus.close();
								socketStatus.close();
				
				
							}catch(UnknownHostException unknown){unknown.printStackTrace();}catch(IOException io){io.printStackTrace();}
							
						}
						
					  }
					  num = num + 1;
					}
					
				}
					
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				labelBlock.setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		//Ação de alteração de dados da conta
		labelConfig.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				labelConfig.setBorder(null);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				labelConfig.setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
				/*Instancia novamente Classe de registro de login
				*Com outras configurações
				*/
				ChatLogin register = new ChatLogin();
				register.configuration = true;
				register.window.setVisible(true);
				register.register.setVisible(false);
				register.login.setVisible(false);
				register.inputUser.setVisible(false);
				register.inputPass.setVisible(false);
				register.button.setVisible(false);
				register.registerName.setVisible(true);
				register.registerUser.setVisible(true);
				register.registerPass.setVisible(true);
				register.nameComputer.setVisible(true);
				register.confirmPass.setVisible(true);
				register.registerConfirm.setVisible(true);
				register.registerButton.setVisible(true);
				register.labelPass.setBounds(30, 80, 50, 20);
				register.window.setTitle("Chat Server - Alteração de dados");
				register.registerName.setText(nameComp);
				register.registerName.setEnabled(false);
				register.registerUser.setText(userLogin);
				register.registerPass.setText(passLogin);
				register.registerConfirm.setText(passLogin);
				/*Thread que verifica se dados já foram alterados
				*e atualiza variáveis para exibição dos mesmos
				*/
				new Thread(new Runnable(){
					@Override
					public void run(){
						while(!register.boolLogin){}
						userLogin = register.userLogin;
						passLogin = register.passLogin;
					}
				}).start();
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		//Ação de mensagens para todos os usuários
		labelMsg.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				labelMsg.setBorder(null);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				labelMsg.setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
				
				mailBases = "";
				try {
					String ip = connect.getNetwork();
					ip = ip.replace("/", "");
					connect.changePath("mensages");
					connect.getMsgFiles("All", ip, true);
					/*Caso as mensagens foram encontradas, Seta as mensagens
					*e no ato da leitura, as reenvia pra o Servidor Web com
					*a identificação de lidas.
					*/
					if(connect.finded){
						for(int i = connect.major; i >= 0; i--){
							String numeration = "#"+i+"{"+ip+"-";
							int rl = numeration.length();
							String readers = connect.readeds.substring(
							connect.readeds.indexOf("#"+i+"{"+ip+"-")+rl, connect.readeds.lastIndexOf("}"+i+"#"));
							
							String myMsgFiles = "";
							if(readers.equals("nonread")){
								myMsgFiles = "#"+i+"[newsletter_"+connect.arrayNames[i]+"-"+ip+"].msg";
							}else{
								myMsgFiles = "#"+i+"[newsletter_"+connect.arrayNames[i]+"-"+ip+"{read}].msg";
							}
							
							FileReader msgReader;
							if(readers.equals("nonread")){
								msgReader = new FileReader(getPath+"\\\\msgs\\\\"+myMsgFiles);
							}else{
								msgReader = new FileReader(getPath+"\\\\read\\\\"+myMsgFiles);
							}
							
							BufferedReader msgBuffer = new BufferedReader(msgReader);
							String readMsg = msgBuffer.readLine();
							String eachFile = "";
							while(readMsg != null){
								mailBases += readMsg;
								eachFile += readMsg + "\r\n";
								readMsg = msgBuffer.readLine();
							}
							msgBuffer.close();
							
							
							if(readers.equals("nonread")){
								File file = new File(getPath+"\\\\msgs\\\\"+myMsgFiles);
								file.delete();
								String myMsgFiles1 = "#"+i+"[newsletter_"+connect.arrayNames[i]+"-"+ip+"{read}].msg";
								FileWriter msgWriter = new FileWriter(getPath+"\\\\read\\\\"+myMsgFiles1);
								msgWriter.write(eachFile);
								msgWriter.close();
								connect.replace(myMsgFiles, myMsgFiles1);
							}
							
							System.out.println("======Recebendo - labelMsg============");
							System.out.println("Todos : "+numeration+readers+"...");
							System.out.println("======================================");
							String RepDirEmot1 = mailBases.substring(mailBases.indexOf("file:\\")+6, mailBases.lastIndexOf("\\imgs\\"));
							mailBases = mailBases.replace(RepDirEmot1, getPath);
							mailBases = mailBases.replace("<html>", "").replace("</html>", "")
									.replace("<head>", "").replace("</head>", "").replace("<body>", "")
									.replace("</body>", "");
					
							
							
							
						}
					}
					connect.changePath("infos");
					connect.readeds = "";

					connect.finded = false;
					connect.major = 0;
					
				} catch (IOException e2) {
				
					e2.printStackTrace();
				}
				/*A partir daqui, Constrói painel do correio eletrônico interno
				*(Mensagens)
				*/
				JFrame frameMsg = new JFrame();
				frameMsg.setTitle("Mensagens");
				frameMsg.setBounds(300, 100, 490, 430);
				frameMsg.setResizable(false);
				
				JLabel panelMsg = new JLabel();
				panelMsg.setText("<html><div style='background:#aaffff;width:490;height:430;'></div></html>");
				panelMsg.setBounds(0, 0, 490, 430);
				frameMsg.add(panelMsg);
				
				JLabel from = new JLabel();
				from.setText("<html><font color='blue'>de : </font>todos</html>");
				from.setBounds(10, 10, 100, 20);
				panelMsg.add(from);
				
				JLabel to = new JLabel();
				to.setText("<html><font color='blue'>para : </font>"+nameComp+"</html>");
				to.setBounds(10, 30, 100, 20);
				panelMsg.add(to);
				
				JLabel myRecurses = new JLabel();
				myRecurses.setText("<html><div style='background:blue;width:500;height:25;'><hr><br><hr></div></html>");
				myRecurses.setBounds(0, 50, 500, 25);
				panelMsg.add(myRecurses);
				
				JLabel italics = new JLabel();
				italics.setIcon(imgItalic);
				italics.setBounds(0, 2, 20, 20);
				italics.setToolTipText("Itálico");
				italics.setCursor(new Cursor(Cursor.HAND_CURSOR));
				myRecurses.add(italics);
				
				JLabel strongs = new JLabel();
				strongs.setIcon(imgStrong);
				strongs.setBounds(25, 2, 20, 20);
				strongs.setToolTipText("Negrito");
				strongs.setCursor(new Cursor(Cursor.HAND_CURSOR));
				myRecurses.add(strongs);
				
				JLabel underlineds = new JLabel();
				underlineds.setIcon(imgUnderlined);
				underlineds.setBounds(60, 2, 20, 20);
				underlineds.setToolTipText("Sublinhado");
				underlineds.setCursor(new Cursor(Cursor.HAND_CURSOR));
				myRecurses.add(underlineds);
				
				JLabel linkeds = new JLabel();
				linkeds.setIcon(link);
				linkeds.setBounds(90, 2, 20, 20);
				linkeds.setToolTipText("Linkado");
				linkeds.setCursor(new Cursor(Cursor.HAND_CURSOR));
				myRecurses.add(linkeds);
				
				JLabel emoji1s = new JLabel();
				emoji1s.setIcon(smile);
				emoji1s.setBounds(125, 2, 20, 20);
				emoji1s.setToolTipText("Sorrindo");
				emoji1s.setCursor(new Cursor(Cursor.HAND_CURSOR));
				myRecurses.add(emoji1s);
				
				JLabel emoji2s = new JLabel();
				emoji2s.setIcon(sad);
				emoji2s.setBounds(160, 2, 20, 20);
				emoji2s.setToolTipText("Triste");
				emoji2s.setCursor(new Cursor(Cursor.HAND_CURSOR));
				myRecurses.add(emoji2s);
				
				JLabel emoji3s = new JLabel();
				emoji3s.setIcon(happy);
				emoji3s.setBounds(195, 2, 20, 20);
				emoji3s.setToolTipText("Super Feliz");
				emoji3s.setCursor(new Cursor(Cursor.HAND_CURSOR));
				myRecurses.add(emoji3s);
				
				JLabel emoji4s = new JLabel();
				emoji4s.setIcon(nervous);
				emoji4s.setBounds(230, 2, 20, 20);
				emoji4s.setToolTipText("Nervoso");
				emoji4s.setCursor(new Cursor(Cursor.HAND_CURSOR));
				myRecurses.add(emoji4s);
				
				JLabel emoji5s = new JLabel();
				emoji5s.setIcon(impressioned);
				emoji5s.setBounds(265, 2, 20, 20);
				emoji5s.setToolTipText("Impressionado");
				emoji5s.setCursor(new Cursor(Cursor.HAND_CURSOR));
				myRecurses.add(emoji5s);
				
				JLabel emoji6s = new JLabel();
				emoji6s.setIcon(seriosly);
				emoji6s.setBounds(295, 2, 20, 20);
				emoji6s.setToolTipText("Sério");
				emoji6s.setCursor(new Cursor(Cursor.HAND_CURSOR));
				myRecurses.add(emoji6s);
				
				JLabel labelWrite = new JLabel();
				labelWrite.setIcon(writer);
				labelWrite.setBounds(395, 2, 20, 20);
				labelWrite.setToolTipText("Escrever");
				labelWrite.setCursor(new Cursor(Cursor.HAND_CURSOR));
				myRecurses.add(labelWrite);
				
				JLabel labelRead = new JLabel();
				labelRead.setIcon(msgMail);
				labelRead.setBounds(425, 2, 25, 20);
				labelRead.setToolTipText("Ler");
				labelRead.setCursor(new Cursor(Cursor.HAND_CURSOR));
				myRecurses.add(labelRead);
				
				JLabel labelSend = new JLabel();
				labelSend.setIcon(send);
				labelSend.setBounds(455, 2, 20, 20);
				labelSend.setToolTipText("Enviar");
				labelSend.setCursor(new Cursor(Cursor.HAND_CURSOR));
				myRecurses.add(labelSend);
				
				
				JEditorPane editorMail = new JEditorPane();
				editorMail.setContentType("text/html");
				editorMail.setEditable(true);
				editorMail.setVisible(false);
				panelMsg.add(editorMail);
				
				JScrollPane scrollMail = new JScrollPane(editorMail);
				scrollMail.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				scrollMail.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				scrollMail.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
				scrollMail.setBounds(0, 80, 485, 320);
				scrollMail.setVisible(false);
				panelMsg.add(scrollMail);
				
				JEditorPane editorMail1 = new JEditorPane();
				editorMail1.setContentType("text/html");
				editorMail1.setEditable(false);
				editorMail1.setVisible(true);
				panelMsg.add(editorMail1);
				
				
				JScrollPane scrollMail1 = new JScrollPane(editorMail1);
				scrollMail1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				scrollMail1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				scrollMail1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
				scrollMail1.setBounds(0, 80, 485, 320);
				scrollMail1.setVisible(true);
				panelMsg.add(scrollMail1);
				
				editorMail1.setText(mailBases);
				scrollMail1.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
					
					@Override
					public void adjustmentValueChanged(AdjustmentEvent arg0) {
						scrollMail1.getVerticalScrollBar().setValue(0);
						if(removeListener1){
							scrollMail1.getVerticalScrollBar().removeAdjustmentListener(this);
							removeListener1 = false;
						}
					}
				});
				
			
				scrollMail1.getVerticalScrollBar().addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent arg0) {}
					
					@Override
					public void mousePressed(MouseEvent arg0) {
						removeListener1 = true;
						scrollMail1.getVerticalScrollBar().removeMouseListener(this);
					}
					
					@Override
					public void mouseExited(MouseEvent arg0) {}
					
					@Override
					public void mouseEntered(MouseEvent arg0) {}
					
					@Override
					public void mouseClicked(MouseEvent arg0) {}
				});
				
				//Torna palavras itálicas
				italics.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent arg0) {
						italics.setBorder(null);
					}
					
					@Override
					public void mousePressed(MouseEvent arg0) {
						italics.setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
						String allText = editorMail.getText();
						String selected = editorMail.getSelectedText();
						String modifyer = allText.replace(selected, " <i>"+selected+"</i> ");
						
						editorMail.setText(modifyer);
					}
					
					@Override
					public void mouseExited(MouseEvent arg0) {}
					
					@Override
					public void mouseEntered(MouseEvent arg0) {}
					
					@Override
					public void mouseClicked(MouseEvent arg0) {}
				});
				//Torna palavras negritas
				strongs.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent arg0) {
						strongs.setBorder(null);
					}
					
					@Override
					public void mousePressed(MouseEvent arg0) {
						strongs.setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
						String allText = editorMail.getText();
						String selected = editorMail.getSelectedText();
						String modifyer = allText.replace(selected, " <b>"+selected+"</b> ");
						
						editorMail.setText(modifyer);
					}
					
					@Override
					public void mouseExited(MouseEvent arg0) {}
					
					@Override
					public void mouseEntered(MouseEvent arg0) {}
					
					@Override
					public void mouseClicked(MouseEvent arg0) {}
				});
				//Torna palavras sublinhadas
				underlineds.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent arg0) {
						underlineds.setBorder(null);
					}
					
					@Override
					public void mousePressed(MouseEvent arg0) {
						underlineds.setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
						String allText = editorMail.getText();
						String selected = editorMail.getSelectedText();
						String modifyer = allText.replace(selected, " <u>"+selected+"</u> ");
						
						editorMail.setText(modifyer);
					}
					
					@Override
					public void mouseExited(MouseEvent arg0) {}
					
					@Override
					public void mouseEntered(MouseEvent arg0) {}
					
					@Override
					public void mouseClicked(MouseEvent arg0) {}
				});
				//Torna palavras linkadas
				linkeds.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent arg0) {
						linkeds.setBorder(null);
					}
					
					@Override
					public void mousePressed(MouseEvent arg0) {
						linkeds.setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
						String optionLink = JOptionPane.showInputDialog(null, "Digite um link", "Mensagens Linkadas", JOptionPane.PLAIN_MESSAGE);
						String allText = editorMail.getText();
						String selected = editorMail.getSelectedText();
						String modifyer = allText.replace(selected, " <a href='"+optionLink+"'>"+selected+"</a> ");
						
						editorMail.setText(modifyer);
						
						
					}
					
					@Override
					public void mouseExited(MouseEvent arg0) {}
					
					@Override
					public void mouseEntered(MouseEvent arg0) {}
					
					@Override
					public void mouseClicked(MouseEvent arg0) {}
				});
				//Seta expressão sorridente
				emoji1s.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent arg0) {
						emoji1s.setBorder(null);
					}
					
					@Override
					public void mousePressed(MouseEvent arg0) {
						emoji1s.setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
						String allText = editorMail.getText();
						String modifyer = allText.replace("</html>", "");
						modifyer = modifyer.replace("</body>", "<img src='file:\\"+getPath+"\\imgs\\smile.jpg' width='20' height='20'/></body></html>");
						
						
						editorMail.setText(modifyer);
					}
					
					@Override
					public void mouseExited(MouseEvent arg0) {}
					
					@Override
					public void mouseEntered(MouseEvent arg0) {}
					
					@Override
					public void mouseClicked(MouseEvent arg0) {}
				});
				//Seta expressão triste
				emoji2s.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent arg0) {
						emoji2s.setBorder(null);
					}
					
					@Override
					public void mousePressed(MouseEvent arg0) {
						emoji2s.setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
						String allText = editorMail.getText();
						String modifyer = allText.replace("</html>", "");
						modifyer = modifyer.replace("</body>", "<img src='file:\\"+getPath+"\\imgs\\sad.jpg' width='20' height='20'/></body></html>");
						
						
						editorMail.setText(modifyer);
					}
					
					@Override
					public void mouseExited(MouseEvent arg0) {}
					
					@Override
					public void mouseEntered(MouseEvent arg0) {}
					
					@Override
					public void mouseClicked(MouseEvent arg0) {}
				});
				//Seta expressão feliz
				emoji3s.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent arg0) {
						emoji3s.setBorder(null);
					}
					
					@Override
					public void mousePressed(MouseEvent arg0) {
						emoji3s.setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
						String allText = editorMail.getText();
						String modifyer = allText.replace("</html>", "");
						modifyer = modifyer.replace("</body>", "<img src='file:\\"+getPath+"\\imgs\\happy.jpg' width='20' height='20'/></body></html>");
						
						
						editorMail.setText(modifyer);
					}
					
					@Override
					public void mouseExited(MouseEvent arg0) {}
					
					@Override
					public void mouseEntered(MouseEvent arg0) {}
					
					@Override
					public void mouseClicked(MouseEvent arg0) {}
				});
				//Seta expressão nervosa
				emoji4s.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent arg0) {
						emoji4s.setBorder(null);
					}
					
					@Override
					public void mousePressed(MouseEvent arg0) {
						emoji4s.setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
						String allText = editorMail.getText();
						String modifyer = allText.replace("</html>", "");
						modifyer = modifyer.replace("</body>", "<img src='file:\\"+getPath+"\\imgs\\nervous.jpg' width='20' height='20'/></body></html>");
						
						
						editorMail.setText(modifyer);
					}
					
					@Override
					public void mouseExited(MouseEvent arg0) {}
					
					@Override
					public void mouseEntered(MouseEvent arg0) {}
					
					@Override
					public void mouseClicked(MouseEvent arg0) {}
				});
				//Seta expressão assustada
				emoji5s.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent arg0) {
						emoji5s.setBorder(null);
					}
					
					@Override
					public void mousePressed(MouseEvent arg0) {
						emoji5s.setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
						String allText = editorMail.getText();
						String modifyer = allText.replace("</html>", "");
						modifyer = modifyer.replace("</body>", "<img src='file:\\"+getPath+"\\imgs\\impressioned.jpg' width='20' height='20'/></body></html>");
						
						
						editorMail.setText(modifyer);
					}
					
					@Override
					public void mouseExited(MouseEvent arg0) {}
					
					@Override
					public void mouseEntered(MouseEvent arg0) {}
					
					@Override
					public void mouseClicked(MouseEvent arg0) {}
				});
				//Seta expressão séria
				emoji6s.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent arg0) {
						emoji6s.setBorder(null);
					}
					
					@Override
					public void mousePressed(MouseEvent arg0) {
						emoji6s.setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
						String allText = editorMail.getText();
						String modifyer = allText.replace("</html>", "");
						modifyer = modifyer.replace("</body>", "<img src='file:\\"+getPath+"\\imgs\\seriosly.jpg' width='20' height='20'/></body></html>");
						
						
						editorMail.setText(modifyer);
					}
					
					@Override
					public void mouseExited(MouseEvent arg0) {}
					
					@Override
					public void mouseEntered(MouseEvent arg0) {}
					
					@Override
					public void mouseClicked(MouseEvent arg0) {}
				});
				//Ação para escrever mensagens no Editor
				labelWrite.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent arg0) {
						labelWrite.setBorder(null);
						editorMail.setVisible(true);
						scrollMail.setVisible(true);
						editorMail1.setVisible(false);
						scrollMail1.setVisible(false);
						from.setText("<html><font color='blue'>de : </font>"+nameComp+"</html>");
						to.setText("<html><font color='blue'>para : </font>todos</html>");
					}
					
					@Override
					public void mousePressed(MouseEvent arg0) {
						labelWrite.setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
					}
					
					@Override
					public void mouseExited(MouseEvent arg0) {}
					
					@Override
					public void mouseEntered(MouseEvent arg0) {}
					
					@Override
					public void mouseClicked(MouseEvent arg0) {}
				});
				//Ação para ler mensagens do Servidor Web
				labelRead.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent arg0) {
						labelRead.setBorder(null);
						editorMail.setVisible(false);
						scrollMail.setVisible(false);
						editorMail1.setVisible(true);
						scrollMail1.setVisible(true);
						from.setText("<html><font color='blue'>de : </font>todos</html>");
						to.setText("<html><font color='blue'>para : </font>"+nameComp+"</html>");
						mailBases = "";
						try {
							String ip = connect.getNetwork();
							ip = ip.replace("/", "");
							connect.changePath("mensages");
							connect.getMsgFiles("All", ip, true);
							if(connect.finded){
								for(int i = connect.major; i >= 0; i--){
									String numeration = "#"+i+"{"+ip+"-";
									int rl = numeration.length();
									String readers = connect.readeds.substring(
									connect.readeds.indexOf("#"+i+"{"+ip+"-")+rl, connect.readeds.lastIndexOf("}"+i+"#"));
									
									String myMsgFiles = "";
									FileReader msgReader;
									if(readers.equals("nonread")){
										myMsgFiles = "#"+i+"[newsletter_"+connect.arrayNames[i]+"-"+ip+"].msg";
										msgReader = new FileReader(getPath+"\\\\msgs\\\\"+myMsgFiles);
									}else{
										myMsgFiles = "#"+i+"[newsletter_"+connect.arrayNames[i]+"-"+ip+"{read}].msg";
										msgReader = new FileReader(getPath+"\\\\read\\\\"+myMsgFiles);
									}
									
									
									BufferedReader msgBuffer = new BufferedReader(msgReader);
									String readMsg = msgBuffer.readLine();
									String eachFile = "";
									while(readMsg != null){
										mailBases += readMsg;
										eachFile += readMsg + "\r\n";
										readMsg = msgBuffer.readLine();
									}
									msgBuffer.close();
									
									
									if(readers.equals("nonread")){
										File file = new File(getPath+"\\\\msgs\\\\"+myMsgFiles);
										file.delete();
										String myMsgFiles1 = "#"+i+"[newsletter_"+connect.arrayNames[i]+"-"+ip+"{read}].msg";
										FileWriter msgWriter = new FileWriter(getPath+"\\\\read\\\\"+myMsgFiles1);
										msgWriter.write(eachFile);
										msgWriter.close();
										connect.replace(myMsgFiles, myMsgFiles1);
									}
									
									System.out.println("======Recebendo - labelRead===========");
									System.out.println("Todos : "+numeration+readers+"...");
									System.out.println("======================================");
									String RepDirEmot1 = mailBases.substring(mailBases.indexOf("file:\\")+6, mailBases.lastIndexOf("\\imgs\\"));
									mailBases = mailBases.replace(RepDirEmot1, getPath);
									mailBases = mailBases.replace("<html>", "").replace("</html>", "")
											.replace("<head>", "").replace("</head>", "").replace("<body>", "")
											.replace("</body>", "");
									editorMail1.setText(mailBases);
									
									
									
									
								}
							}
							connect.changePath("infos");
							mailBases = "";
							connect.readeds = "";

							connect.finded = false;
							connect.major = 0;
						} catch (IOException e) {
						
							e.printStackTrace();
						}
						
						labelNotifyer.setVisible(false);
						
					}
					
					@Override
					public void mousePressed(MouseEvent arg0) {
						labelRead.setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
					}
					
					@Override
					public void mouseExited(MouseEvent arg0) {}
					
					@Override
					public void mouseEntered(MouseEvent arg0) {}
					
					@Override
					public void mouseClicked(MouseEvent arg0) {}
				});
				//Ação para enviar mensagens ao Servidor Web
				labelSend.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent arg0) {
						labelSend.setBorder(null);
						String themeSend = JOptionPane.showInputDialog(null, "Assunto: ", "Assunto da mensagem", JOptionPane.PLAIN_MESSAGE);
						String replaceCode = editorMail.getText().replace("<body>", "<body><hr><br><p>de : "+nameComp+"<br>para : null</p><p><b>Assunto : "+themeSend+"</b></p>");
						replaceCode = replaceCode.replace("</body>", "<br><hr></body>");
						FileWriter writeMail;
						
						
						for(int i = 0; i < 20; i++){
							
							if(arrayIps[i] != null && arrayComps[i] != null){
								if(!arrayComps[i].equals(nameComp)){
									String atualIp = arrayIps[i];
									String atualComp = arrayComps[i];
									replaceCode = replaceCode.replace("<br>para : null</p>", "<br>para : "+atualComp+"</p>");
									
									try {
										connect.changePath("mensages");
										connect.getMsgFiles("All", atualIp, false);
										int increment = 0;
										if(!connect.finded){increment = 0;}else{increment = connect.major + 1;}
										String data = "#"+increment+"[newsletter_"+nameComp+"-"+atualIp+"].msg";
										writeMail = new FileWriter(getPath+"\\\\msgs\\\\"+data);
										writeMail.write(replaceCode);
										writeMail.close();
										connect.major = 0;
										connect.upload(data, getPath+"/msgs/");
									
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								
							}else{
								break;
							}
						}
						try {
							connect.changePath("infos");
							connect.readeds = "";

							connect.finded = false;
							connect.major = 0;
						} catch (IOException e) {}
						
					}
					
					@Override
					public void mousePressed(MouseEvent arg0) {
						labelSend.setBorder(BorderFactory.createLineBorder(Color.decode("#aaffff")));
					}
					
					@Override
					public void mouseExited(MouseEvent arg0) {}
					
					@Override
					public void mouseEntered(MouseEvent arg0) {}
					
					@Override
					public void mouseClicked(MouseEvent arg0) {}
				});
				
				frameMsg.setVisible(true);
				labelNotifyer.setVisible(false);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
		
		this.setVisible(true);
		
	}
	
	
	
	/*Método que decodifica informações de login
	 * E seta respectivas variáveis para manipulação
	 * das mesmas
	 */
	public void capturateInfo(String fileLog) throws IOException{
		FileReader fileRead = new FileReader(getPath+"/log/"+fileLog);
		bufferRead = new BufferedReader(fileRead);
		String readLine = bufferRead.readLine();
		char[] charLines;
		char[] charLines1;
		int dataOrder = 0;
		String armazen;
		String user;
		String pass;
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
					
				}else{
					if(dataOrder == 2){
						user = decrypted;
						userLogin = user;
					}else{
						pass = decrypted;
						passLogin = pass;
						dataOrder = 0;
						
						
						
					}
				}
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			readLine = bufferRead.readLine();
		}
	}
}
