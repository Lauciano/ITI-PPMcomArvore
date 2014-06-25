import java.util.ArrayList;

public class Main {
	public static void main(String args[]){
		
		Contexto raiz = new Contexto(0);
		Leitor leitor = new Leitor("texto.txt"); // Arquivo a ser lido
		
		//Chamada
		//Parâmetros
		//O contexto raiz
		//O leitor
		//O contexto máximo k passado como parâmetro
		// Atenção: ainda não está tratando o mecanismo de exclusão
		//          mesmo assim funciona para o caso de não usá-lo
		//ArrayList<Intervalo> intv = Contexto.geraCodigo(raiz, leitor, 2);
		//System.out.println("Saida:");
		//for(Intervalo i : intv){
		//	System.out.println(i);
		//}
		
		//Chamada
		//Parâmetros
		//O contexto raiz
		//O leitor
		//O contexto máximo k passado como parâmetro
		// Atenção: ainda não está tratando o mecanismo de exclusão
		//          mesmo assim funciona para o caso de não usá-lo
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
