import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Contexto {
	private boolean esc;
	private byte valor;
	private int  frequencia;
	private double probabilidade;
	private Intervalo intervalo;
	private ArrayList<Contexto> filhos;
	
	//Construtores
	//NÃO USAR !!! RECURSÃO!!! SOCORRO!!!!
	public Contexto(Contexto c){
		esc = c.esc;
		valor = c.valor;
		frequencia = c.frequencia;
		filhos = new ArrayList<Contexto>();
		for(Contexto i : c.filhos){
			filhos.add(new Contexto(i));
		}
		probabilidade = c.probabilidade;
		intervalo = new Intervalo(c.intervalo.getInicio(), c.intervalo.getFim());
	}
	
	public Contexto(int valor){
		byte v = Byte.parseByte("" + valor);
		this.esc = false;
		this.valor = v;
		this.frequencia = 1;
		this.filhos = new ArrayList<Contexto>();
		probabilidade = 1;
		intervalo = new Intervalo(0, 1);
	}
	
	public Contexto(){
		this.esc = true;
		this.valor = 0;
		this.frequencia = 1;
		this.filhos = null;
		this.probabilidade = 1;
		this.intervalo = new Intervalo(0, 1);
	}
	
	// Codificação
	public static ArrayList<Intervalo> geraCodigo(Contexto raiz, Leitor leitor, int contextoMaximo){
		ArrayList<Intervalo> codigo = new ArrayList<Intervalo>();
		ArrayList<Byte> ultimos = new ArrayList<Byte>();
		ArrayList<Byte> alfabeto = leitor.getAlfabeto();
		ArrayList<Byte> contextoTemp = new ArrayList<Byte>();
		Collections.sort(alfabeto);
		Byte lido;
		
		
		while((lido = leitor.getNextByte()) != null){
			
			System.out.println("Byte lido = " + lido);
			
			ultimos.add(lido);
			
			for(int i = -1, j = contextoMaximo; i <= contextoMaximo; i++, j--){
				System.out.println("i = " + i + " j = " + j);
				if(ultimos.size() < j) continue;
				
				if(j > 0){
					System.err.println("If");
					for(int k = 0; k < j; k++){
						contextoTemp.add(ultimos.get(ultimos.size() - 1 - k));
					}
					Contexto c = new Contexto(lido);
					raiz.addOcorrencia(c, contextoTemp, -1);
				}else if(j == 0){
					System.err.println("ElseIf");
					Contexto c = new Contexto(lido);
					raiz.addOcorrencia(c);
				}else{
					System.err.println("Else");
					System.out.println("Remove de K1");
				}
				
				System.out.println("\n\n\n");
				
			}
		}
		
		return null;
	}
	
	// Árvore
	public void atualizaIntervalo(){
		double aux = 0;
		for(Contexto c : filhos){
			c.getIntervalo().setInicio(aux);
			c.getIntervalo().setFim(c.getProbabilidade() + aux);
			aux += c.getProbabilidade();
		}
	}
	
	public void atualizaProbabilidade(){
		double total = getTotal();		
		for(Contexto c : filhos){
			c.setProbabilidade(((double)c.getFrequencia())/total);
		}
	}
	
	public void addOcorrencia(Contexto ocorrencia){
		if(!temOcorrencia(ocorrencia.getValor())){
			filhos.add(ocorrencia);
			Contexto esc = findEsc();
			if(esc == null){
				esc = new Contexto(); 
				filhos.add(esc);
			} else {
				esc.setFrequencia(esc.getFrequencia() + 1);
			}
		} else {
			for(Contexto c : filhos){
				if(c.valor == ocorrencia.getValor()){
					c.setFrequencia(c.getFrequencia()+1);
					break;
				}
			}
		}
		
		Collections.sort(filhos, Contexto.ContextoComparator);
		atualizaProbabilidade();
		atualizaIntervalo();
	}
	
	public void addOcorrencia(Contexto ocorrencia, ArrayList<Byte> contexto, int nivel){
		if(nivel > -1){
			if(this.getValor() != contexto.get(nivel)) return;
		}
		
		if(nivel == contexto.size() - 1) {
			if(!this.isEsc()){
				this.addOcorrencia(ocorrencia);
			}
			return;
		}
		
		for(Contexto c : filhos){
			c.addOcorrencia(ocorrencia, contexto, nivel + 1);
		}
	}
	
	public boolean temOcorrencia(byte v){
		if(filhos.isEmpty()) return false;
		for(Contexto c : filhos){
			if(c.getValor() == v){
				return true;
			}
		}
		return false;
	}
	
	// Getter e Setter
	public Contexto findEsc(){
		for(Contexto c : filhos){
			if(c.isEsc()){
				return c;
			}
		}
		return null;
	}
	
	public Contexto findContexto(byte valor){
		for(Contexto c : filhos){
			if(c.valor == valor){
				return c;
			}
		}
		return null;
	}
	
	public boolean isEsc() {
		return esc;
	}

	public void setEsc(boolean esc) {
		this.esc = esc;
	}

	public byte getValor() {
		return valor;
	}

	public void setValor(byte valor) {
		this.valor = valor;
	}

	public int getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(int frequencia) {
		this.frequencia = frequencia;
	}

	public ArrayList<Contexto> getFilhos() {
		return filhos;
	}

	public void setFilhos(ArrayList<Contexto> filhos) {
		this.filhos = filhos;
	}
	
	private Intervalo getIntervalo() {
		return intervalo;
	}
	
	public Intervalo getIntervalo(ArrayList<Byte> contexto, int nivel) {
		if(nivel > -1){
			if(this.getValor() != contexto.get(nivel)) return null;
		}
		
		if(nivel == contexto.size() - 1) {
			Contexto filho = null;
			if(this.isEsc()) return null;
			for(Contexto c : filhos){
				if(c.isEsc()){
					filho = c;
					break;
				}
			}
			if(filho == null) return null;
			return filho.getIntervalo();
		}
		
		for(Contexto c : filhos){
			Intervalo res = c.getIntervalo(contexto, nivel+1);
			if(res != null){
				return res;
			}
		}
		
		return null;
	}	
	
	public Intervalo getIntervalo(ArrayList<Byte> contexto, byte valor, int nivel) {
		if(nivel > -1){
			if(this.getValor() != contexto.get(nivel)) return null;
		}
		
		if(nivel == contexto.size() - 1) {
			Contexto filho = null;
			if(this.isEsc()) return null;
			for(Contexto c : filhos){
				if(!c.isEsc() && c.getValor() == valor){
					filho = c;
					break;
				}
			}
			if(filho == null) return null;
			return filho.getIntervalo();
		}
		
		for(Contexto c : filhos){
			Intervalo res = c.getIntervalo(contexto, valor, nivel+1);
			if(res != null){
				return res;
			}
		}
		
		return null;
	}
	
	private double getProbabilidade() {
		return probabilidade;
	}
	
	public double getProbabilidade(ArrayList<Byte> contexto, int nivel){
		if(nivel > -1){
			if(this.getValor() != contexto.get(nivel)) return -1;
		}
		
		if(nivel == contexto.size() - 1) {
			Contexto filho = null;
			if(this.isEsc()) return -1;
			for(Contexto c : filhos){
				if(c.isEsc()){
					filho = c;
					break;
				}
			}
			if(filho == null) return -1;
			return filho.getProbabilidade();
		}
		
		for(Contexto c : filhos){
			double res = c.getProbabilidade(contexto, nivel+1);
			if(res != -1){
				return res;
			}
		}
		
		return -1;
	}
	
	public double getProbabilidade(ArrayList<Byte> contexto, byte valor, int nivel){
		if(nivel > -1){
			if(this.getValor() != contexto.get(nivel)) return -1;
		}
		
		if(nivel == contexto.size() - 1) {
			Contexto filho = null;
			if(this.isEsc()) return -1;
			for(Contexto c : filhos){
				if(!c.isEsc() && c.getValor() == valor){
					filho = c;
					break;
				}
			}
			if(filho == null) return -1;
			return filho.getProbabilidade();
		}
		
		for(Contexto c : filhos){
			double res = c.getProbabilidade(contexto, valor, nivel+1);
			if(res != -1){
				return res;
			}
		}
		
		return -1;
	}
	
	public void setProbabilidade(double probabilidade) {
		this.probabilidade = probabilidade;
	}
	
	/*public double getProbabilidade(ArrayList<Byte> contexto, byte valor, int nivel){
		if(nivel > -1){
			if(this.getValor() != contexto.get(nivel)) return -1;
		}
		
		if(nivel == contexto.size() - 1) {
			double contador = 0;
			Contexto filho = null;
			if(this.isEsc()) return -1;
			for(Contexto c : filhos){
				contador += c.frequencia;
				if(!c.isEsc() && c.getValor() == valor){
					filho = c;
				}
			}
			if(filho == null) return -1;
			return filho.getFrequencia()/contador;
		}
		
		for(Contexto c : filhos){
			double res = c.getProbabilidade(contexto, valor, nivel+1);
			if(res != -1){
				return res;
			}
		}
		
		return -1;
	}
	
	public double getProbabilidade(ArrayList<Byte> contexto, byte valor, int posicao, int nivel) {
		if(nivel == contexto.size() - 1) {
			double contador = 0;
			Contexto filho = null;
			if(this.isEsc()) return -1;
			for(Contexto c : filhos){
				contador += c.frequencia;
				if(!c.isEsc() && c.getValor() == valor){
					filho = c;
				}
			}
			if(filho == null) return -1;
			return filho.getFrequencia()/contador;
		}
		
		for(Contexto c : filhos){
			if(c.getValor() == contexto.get(posicao)){
				return c.getProbabilidade(contexto, valor, posicao + 1, nivel + 1);
			}
		}
		
		return -1;
	}*/
	
	public int getTotal(){
		int total = 0;
		for(Contexto c : filhos){
			total += c.getFrequencia();
		}
		return total;
	}
	
	// To String
	public String toString(){
		return (valor&0xFF) + " " + frequencia;
	}
	
	public static Comparator<Contexto> ContextoComparator = new Comparator<Contexto>(){
		public int compare(Contexto inst1,Contexto inst2){
			if(inst1.isEsc()) return 1;
			if(inst2.isEsc()) return -1;
			return inst1.getValor() - inst2.getValor();
		}
	};
	
	public static void main(String args[]){
		
		Contexto raiz = new Contexto(0);
		Leitor l = new Leitor("texto.txt");
		raiz.geraCodigo(raiz, l, 1);
		
		/*Contexto raiz = new Contexto(0);
		for(int i = 0; i < 2; i++){
			Contexto c1 = new Contexto(i);
			for(int j = 0; j < 2; j++){
				Contexto c2 = new Contexto(j);
				c1.addOcorrencia(c2);
			}
			raiz.addOcorrencia(c1);
		}
		Contexto c3 = new Contexto(1);
		ArrayList<Byte> contexto = new ArrayList<Byte>();
		contexto.add((byte) 0);
		raiz.addOcorrencia(c3, contexto, (byte) 0, -1);*/
		return;
	}
}
