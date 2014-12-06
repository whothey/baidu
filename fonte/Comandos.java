import java.util.Scanner;

class Comandos{
	Operacoes oper=new Operacoes();
	
	public void verificaSaida(String verificaPosicao, String [] guardaVariavel, double [] guardaValores, int linhasGuardaVariavel){//recebe a linha na qual existe a palavra IMPRIMA
		String aux="nada";
		double auxiliar=0;
		
		if(verificaPosicao.startsWith("IMPRIMA('")){
			aux=verificaPosicao.substring(verificaPosicao.indexOf("('")+2, verificaPosicao.indexOf("');"));
			System.out.println(aux);
		
		} else if(verificaPosicao.startsWith("IMPRIMA(")){
			aux=verificaPosicao.substring(verificaPosicao.indexOf("(")+1, verificaPosicao.indexOf(");"));
			oper.Error(guardaVariavel,aux,linhasGuardaVariavel); // verifico se a variavel foi declarada
			auxiliar= buscaValor(aux, guardaVariavel, guardaValores, linhasGuardaVariavel);
			System.out.println(auxiliar);
		}		
	}
	
	public void verificaEntrada(String verificaPosicao, String [] guardaVariavel, double [] guardaValores, int linhasGuardaVariavel){ //método que faz um comando de entrada
		String aux="nada"; 
		aux=verificaPosicao.substring(verificaPosicao.indexOf("(")+1, verificaPosicao.indexOf(");")); // pega a variavel que é pra ser escrita
		oper.Error(guardaVariavel,aux,linhasGuardaVariavel); // verifico se a variavel foi declarada
		Scanner s= new Scanner(System.in); 
		double auxiliar = s.nextDouble(); // leio um valor para a variavel
		oper.valorAtual(auxiliar, aux, guardaVariavel, guardaValores, linhasGuardaVariavel); // atualizo o valor da variavel através do que o usuario informou
	}
	
	public double buscaValor(String aux3, String [] guardaVariavel, double [] guardaValores, int linhasGuardaVariavel) { 
		
		double auxiliar=0;
		int i=0;
	
		for(i=0; i<linhasGuardaVariavel; i++){
			if(guardaVariavel[i].equals(aux3)){
				auxiliar = guardaValores[i];//auxiliar que é um double recebe o valor da variavel que esta no vetor guarda valores
			}	
		}	
			
		return auxiliar;
	}
}