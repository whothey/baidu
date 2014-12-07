import java.util.ArrayList;

class Fluxo {
	public int i,qtLinha;
	public double compara1,compara2,detecta;
	public char op;
	public String aux;
	public String aux1;
	public String detect;

	public void ContFluxo(){
		op=0;
		aux="nada";
		aux1="nada";
		detect="nada";
	}

	public int vectorExists(String name, ArrayList vetores)
	{
		for (int i = 0; i < vetores.size(); i++) {
			BaiduVector vetor = (BaiduVector) vetores.get(i);
			if (vetor.getName().equals(name)) return i;
		}

		return -1;
	}
	
	public int ControlaFluxo(String posicao, String [] guardaVariavel, double [] guardaValores, int linhasGuardaVariavel,int linha, ArrayList vetores){//linha que esta executando
		
		for(i=0; i<posicao.length(); i++){//verificando os tokens existentes
			if(posicao.charAt(i) == '@' || posicao.charAt(i) == '<' || posicao.charAt(i) == '>' || posicao.charAt(i) == '#'){//procura pelos tokens comparativos
				op = posicao.charAt(i);//pegou o token comparativo
			}
		}
		for(i=0;i<posicao.length();i++){//agora vamos pegar quem esta sendo compara1
			aux=posicao.substring(posicao.indexOf("(")+1, posicao.indexOf(op));//pegando a primeira string a ser comparada
			aux1=posicao.substring(posicao.indexOf(op)+1, posicao.indexOf(")"));//pegando a segunda string a ser comparada
		}
		compara1 = difere(aux,linhasGuardaVariavel,guardaVariavel,guardaValores, vetores);//passando a string a ser analisada
		compara2 = difere(aux1,linhasGuardaVariavel,guardaVariavel,guardaValores, vetores);//passando a string depois do token comparativo

		if(op == '@'){//igual igual
			
			if(compara1==compara2){
				
				return linha;//condicao valida entao continua executando na proxima linha.
			}
			else{//verifica onde esta o fim e retorna a linha que deve ser executada
				return -1;
			}
		}
		
		if(op=='<'){
			if(compara1<compara2){
				return linha;//condicao valida entao continua executando na proxima linha.
			}
			else{
				return -1;
			}
		}
		
		if(op=='>'){
			if(compara1>compara2){
				return linha;//condicao valida entao continua executando na proxima linha.
			}
			else{
				return -1;
			}
		}
		
		if(op=='#'){//diferente
			if(compara1!=compara2){
				return linha;//condicao valida entao continua executando na proxima linha.
			} else {
				return -1;
			}
		}

		return 0;
	}

	public double difere(String analisa,int linhasGuardaVariavel,String [] guardaVariavel,double [] guardaValores, ArrayList vetores){ 
		double aux=0.0;
		boolean isVector;
		String vetorStr;

		if(analisa.matches("^[0.0-9.9]*@")){//expressao regular para verificar se é numero
			aux = Double.parseDouble(analisa);//casting de string para double	
				
		}
		else{//nao eh uma variavel
			//chamada de erro
			try {
				vetorStr = BaiduVector.extractVectorFromStr(analisa);
				isVector = BaiduVector.isVector(vetorStr);
			} catch (InvalidVectorIndexException e) {
				throw new RuntimeException(e.getMessage());
			}

			if (isVector) {
				String nomeVetor = BaiduVector.vectorName(vetorStr);
				int vetorPosicao = this.vectorExists(nomeVetor, vetores);
				BaiduVector vetor = (BaiduVector) vetores.get(vetorPosicao);
				int index;
				
				try {
					index = BaiduVector.vectorIndexes(vetorStr);
				} catch (InvalidVectorIndexException e) {
					throw new RuntimeException(e.getMessage());
				}

				return vetor.getIndex(index);
			}

			for(i=0; i<linhasGuardaVariavel; i++){
				if(guardaVariavel[i].equals(analisa)){//se a variavel ja foi declarada
					aux = guardaValores[i];
				}	
			}	
		}
		return aux;
	}
}