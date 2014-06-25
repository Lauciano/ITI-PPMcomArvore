import com.colloquial.arithcode.ArithEncoder;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
	public static void main(String args[]){
		
		Contexto raiz = new Contexto(0);
		Leitor leitor = new Leitor("texto.txt"); // Arquivo a ser lido
                FileOutputStream fop = null;
                try {
                    fop = new FileOutputStream(new File("saida.txt").getAbsoluteFile());
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                
		ArithEncoder encoder = new ArithEncoder(fop);
                
		//Chamada
		//Par�metros
		//O contexto raiz
		//O leitor
		//O contexto m�ximo k passado como par�metro
		// Aten��o: ainda n�o est� tratando o mecanismo de exclus�o
		//          mesmo assim funciona para o caso de n�o us�-lo
		//ArrayList<Intervalo> intv = Contexto.geraCodigo(raiz, leitor, 2);
		//System.out.println("Saida:");
		//for(Intervalo i : intv){
		//	System.out.println(i);
		//}
		
		//Chamada
		//Par�metros
		//O contexto raiz
		//O leitor
		//O contexto m�ximo k passado como par�metro
		// Aten��o: ainda n�o est� tratando o mecanismo de exclus�o
		//          mesmo assim funciona para o caso de n�o us�-lo
		// Retorno:
		//		Inteiro		low
		//					high
		//					total
		ArrayList<Codigo> inteiro = Contexto.geraCodigoInteiro(raiz, leitor, 2);
		System.out.println("Saida:");
		for(Codigo i : inteiro){
                    System.out.println(i);
                    try {
                        encoder.encode(i.getLow(), i.getHigh(), i.getTotal());
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}
		
		return;
	}
}
