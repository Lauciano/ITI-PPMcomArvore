import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Leitor {

	private BufferedReader br;
	private String linhaAtual;
	private int index;
	private String nomeArq;
	private boolean segMetade;
	
	public Leitor(String nomeArq){
		
		linhaAtual = "";
		index = 0;
		this.nomeArq = nomeArq;
		segMetade = false;
		
		try{
			br = new BufferedReader(new FileReader(nomeArq));
			linhaAtual = br.readLine();
			System.out.println("Linha lida = " + linhaAtual);
		}catch(FileNotFoundException e){
			System.err.println("Arquivo não encontrado!");
		} catch (IOException e) {
			System.err.println("Erro na leitura do arquivo!");
		}
	}
	
	public ArrayList<Byte> getAlfabeto(){
		String linha;
		ArrayList<Byte> alfabeto = new ArrayList<Byte>();
		
		try {
			
			BufferedReader br2 = new BufferedReader(new FileReader(nomeArq));
			for(int i = 0; (linha = br2.readLine()) != null; i++){
				for(int j = 0; j < linha.length(); j++){
					char c = linha.charAt(j);
					byte b = (byte) (c >> 8);
					if(!alfabeto.contains(b)){
						alfabeto.add(b);
					}
					b = (byte) (c & 0xff);
					if(!alfabeto.contains(b)){
						alfabeto.add(b);
					}
				}
			}
			br.close();
			
		} catch (FileNotFoundException e) {
			System.err.println("Arquivo não encontrado!");
		} catch (IOException e){
			System.err.println("Erro na leitura do arquivo!");
		}
		
		
		
		return alfabeto;
 		
	}
	
	public Byte getNextByte(){
		
		try {
			br.ready();
		} catch (IOException e1) {
			System.err.println("Arquivo ja foi fechado!");
			return null;
		}
		
		if(index == linhaAtual.length()){
			index = 0;
			try {
				linhaAtual = br.readLine();
				if(linhaAtual == null){
					br.close();
					return null;
				}
			} catch (IOException e) {
				System.out.println("Erro na leitura do arquivo!");
			}

		}
		
		char c = linhaAtual.charAt(index);
		byte b;
		
		if(segMetade){
			b = (byte) (c & 0xff);
			segMetade = false;
			index++;
		} else {
			b = (byte) (c >> 8);
			segMetade = true;
		}
		
		return b;
	}
	
}
