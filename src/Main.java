import java.util.ArrayList;

public class Main {
	public static void main(String args[]){
		
		Contexto raiz = new Contexto(0);
		Leitor leitor = new Leitor("texto.txt"); // Arquivo a ser lido
		
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
		}
		
		return;
	}
}
