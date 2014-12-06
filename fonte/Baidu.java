import java.io.File;

class Baidu{

	public static void main(String args[]){
	    File file = null;
		try {
		    
			String arquivo = args[0];
			file = new File (arquivo);
			
		}catch (Exception ko) {
			
			System.out.println("FILE NOT FOUND");
			
		}
				
		Decifra le = new Decifra(file);
		
	}


}