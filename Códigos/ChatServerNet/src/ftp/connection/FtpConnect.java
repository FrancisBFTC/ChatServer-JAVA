
package ftp.connection;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

import javax.swing.JOptionPane;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

public class FtpConnect {
	public FTPClient ftp;
	public boolean SuccessDown = false;
	public boolean SuccessUp = false;
	public String[] listComp = new String[20];
	public boolean connected = false, userConnected = false;
	public int major = 0;
	public boolean finded = false;
	public String readeds = "";
	public int readsCont = 0;
	public String[] arrayNames;
	
	public FtpConnect() throws IOException{
		ftp = new FTPClient();
		FTPClientConfig config = new FTPClientConfig(FTPClientConfig.SYST_NT);
		ftp.configure(config);
		
		try {
			int reply;
			String host = "185.176.43.64";
			String user = "2163708";
			String pass = "1324354657687980sitedoprojeto";
			ftp.connect(host);
			connected = true;
			System.out.println("Conectado à " + host + ".");
		    System.out.print(ftp.getReplyString());
		    ftp.enterLocalPassiveMode();
		    reply = ftp.getReplyCode();
			userConnected = false;
		
			if(FTPReply.isPositiveCompletion(reply)){
				ftp.login(user, pass);
				changePath("logons");
				userConnected = true;
				
			}else{
				userConnected = false;
			}
			ftp.setFileType(ftp.BINARY_FILE_TYPE);
			
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "<html><font color='red'>"
					+ "Não foi possível conectar ao servidor! Verifique a conexão com"
					+ "sua internet ou aguarde uns minutos e tente novamente."
					+ "</font></html>");
			System.exit(0);
			ftp.logout();
	        ftp.disconnect();
	        connected = false;
	        userConnected = false;
		}
		
	}
	
	public void download(String fileLogin, String directory) throws IOException{
		File file = new File(directory+fileLogin);
		OutputStream FileDownload = new BufferedOutputStream(new FileOutputStream(file));
		if(ftp.retrieveFile(fileLogin, FileDownload)){
			SuccessDown = true;
			
		}else{
			JOptionPane.showMessageDialog(null, "<html><font color='red'>"
					+ "Não foi possível autenticar o login! Verifique "
					+ "a sua internet e tente novamente."
					+ "</font></html>");
			SuccessDown = false;
		}
		FileDownload.close();
	}
	
	public void upload(String fileLogin, String directory) throws IOException{
		File file = new File(directory+fileLogin);
		InputStream FileUpload = new BufferedInputStream(new FileInputStream(file));
		if(ftp.storeFile(fileLogin, FileUpload)){
			SuccessUp = true;
		}else{
			JOptionPane.showMessageDialog(null, "<html><font color='red'>"
					+ "Não foi possível efetuar o registro! Verifique "
					+ "a sua internet e tente novamente."
					+ "</font></html>");
		
			SuccessUp = false;
		}
		FileUpload.close();
	}
	
	public String getNetwork(){
		InetAddress localAddress = ftp.getLocalAddress();
		String MyIp = ""+localAddress;
		return MyIp;
	}
	
	public void disconnect(){
		if(ftp.isConnected()){
			try {
				
				ftp.logout();
				ftp.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "não é possivel deslogar do servidor");
			}
			
		}else{
			try {
				
				ftp.logout();
				ftp.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "não é possivel deslogar do servidor");
			}
			
		}
	}
	
	public void getInfoFiles() throws IOException{
		
	      changePath("infos");
	      String[] arqs = ftp.listNames();
	      int index = 0;
	      for(String filedir : arqs){
	    	  if(filedir.contains(".info")){
	    		  listComp[index] = new String(filedir);
	    		  index = index+1;
	    	  }
	    	
	      }
	}
	
	public void getMsgFiles(String name, String network, boolean down) throws IOException{
			String getPath = new File("").getAbsolutePath();
			// changePath("mensages");
	      int number = 0;
	      String[] arqs = ftp.listNames();
	      arrayNames = new String[arqs.length];
	      major = 0;
	  	finded = false;
	  	readeds = "";
	  	readsCont = 0;
	      for(String filedir : arqs){
	    	  if(filedir.contains("newsletter")){
	    		  if(name.equals("All")){
	    			  if(filedir.contains("-"+network+"].msg")){
			    		  finded = true;
			    		  readsCont = readsCont + 1;
			    		  if(down){
			    			 download(filedir, getPath+"/msgs/"); 
			    		  }
			    		String numberFile = filedir.substring(filedir.indexOf("#")+1, filedir.lastIndexOf("["));
			    		readeds += "#"+numberFile+"{"+network+"-nonread}"+numberFile+"#\r\n";
			    		number = Integer.parseInt(numberFile);
			    		String nameMensage = filedir.substring(filedir.indexOf("_")+1, filedir.lastIndexOf("-"));
			    		arrayNames[number] = nameMensage;
			    		if(number > major){
			    			major = number;
			    		}
			    		  
			    	  }else{
			    		  if(filedir.contains("-"+network+"{read}].msg")){
				    		  finded = true;
				    		  
				    		  if(down){
				    			 download(filedir, getPath+"/read/"); 
				    		  }
				    		  System.out.println("Baixou!");
				    		String numberFile = filedir.substring(filedir.indexOf("#")+1, filedir.lastIndexOf("["));
				    		readeds += "#"+numberFile+"{"+network+"-read}"+numberFile+"#\r\n";
				    		number = Integer.parseInt(numberFile);
				    		String nameMensage = filedir.substring(filedir.indexOf("_")+1, filedir.lastIndexOf("-"));
				    		arrayNames[number] = nameMensage;
				    		if(number > major){
				    			major = number;
				    		}
				    		  
				    	  }
			    		  
			    	  }
	    		  }else{
	    			  if(filedir.contains("[newsletter_"+name+"-"+network+"].msg")){
			    		  finded = true;
			    		  readsCont = readsCont + 1;
			    		  if(down){
			    			 download(filedir, getPath+"/msgs/"); 
			    		  }
			    		String numberFile = filedir.substring(filedir.indexOf("#")+1, filedir.lastIndexOf("["));
			    		readeds += "#"+numberFile+"{"+network+"-nonread}"+numberFile+"#\r\n";
			    		number = Integer.parseInt(numberFile);
			    		if(number > major){
			    			major = number;
			    		}
			    		  
			    	  }else{
			    		  if(filedir.contains("[newsletter_"+name+"-"+network+"{read}].msg")){
				    		  finded = true;
				    		  
				    		  if(down){
				    			 download(filedir, getPath+"/read/"); 
				    		  }
				    		String numberFile = filedir.substring(filedir.indexOf("#")+1, filedir.lastIndexOf("["));
				    		readeds += "#"+numberFile+"{"+network+"-read}"+numberFile+"#\r\n";
				    		number = Integer.parseInt(numberFile);
				    		if(number > major){
				    			major = number;
				    		}
				    		  
				    	  }
			    		  
			    	  }
	    		  }
	    		  
	    	  }
	    	 
	    	
	      }
	    //  changePath("infos");
	}
	
	public void changePath(String path) throws IOException{
		if(path.equals("")){
			ftp.changeWorkingDirectory("/planetamusical.atwebpages.com/013476489/");
		}else{
			ftp.changeWorkingDirectory("/planetamusical.atwebpages.com/013476489/"+path+"/");
		}
		
	}
	
	public void replace(String oldFile, String newFile) throws IOException{
		String getPath = new File("").getAbsolutePath();
		changePath("mensages");
		ftp.deleteFile(oldFile);
		upload(newFile, getPath+"/read/");
	}
	
	public void deleteFile(String file) throws IOException{
		ftp.deleteFile(file);
		
	}
	
	public void renameFile(String f, String t) throws IOException{
		ftp.rename(f, t);
	}
	
}
