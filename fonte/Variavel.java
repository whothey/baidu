import java.util.ArrayList;

class Variavel{

	public void procuraVariavel(String codigo[],int contLinhas){//procura as variaveis
	
		int i=0, s=0, linhasGuardaVariavel=0, a=0, b=0,z=0, volta=0;//contador de linhas do vetor de variaveis
		String verificaPosicao, compara="nada", aux="nada";//string intermediaria que faz manipulação de cada linha do vetor do codigo
		
		Variavel objVariavel = new Variavel();
		Operacoes objOperacoes = new Operacoes();
		Comandos  objComandos = new Comandos();
		Fluxo objFluxo = new Fluxo();
		FluxoLaco f = new FluxoLaco();
		boolean foundVector = false;

		String [] guardaVariavel = new String[2000];//VETOR DE VARIAVEIS
		double [] guardaValores = new double[2000];//VALORES DE CADA VARIAVEL

		ArrayList<BaiduVector> vetores = new ArrayList<BaiduVector>();
		
		for(i=0;i<contLinhas;i++){//percorre o codigo procurando por variaveis

			verificaPosicao = codigo[i];//a string recebe a linha do vetor de strings
			
			if(verificaPosicao.endsWith("$")){ //identifica que é uma declaração de variavel
				verificaPosicao = verificaPosicao.substring(3);//guarda a partir do terceiro caractere

				try {
					foundVector = BaiduVector.isVector(verificaPosicao);
				} catch (InvalidVectorIndexException e) {
					throw new RuntimeException(e.getMessage());
				}

				if (foundVector) {
					String nome = verificaPosicao.substring(0, verificaPosicao.indexOf("["));
					int indexes;
					
					try {
						indexes = BaiduVector.vectorIndexes(verificaPosicao);
					} catch (InvalidVectorIndexException e) {
						throw new RuntimeException(e.getMessage());
					}

					BaiduVector vetor = new BaiduVector(nome, indexes);
					vetores.add(vetor);
				} else {
					guardaVariavel[s]=verificaPosicao.replaceAll("$","");//retira o $ da variavel
					guardaValores[s]=0;//cada variavel recebe 0 inicialmente - verificar se é null a variavel
					s++;
					linhasGuardaVariavel++;
				}

			}

		
			if(verificaPosicao.contains("=")){//procura pelo token de atribuição (=);
				objOperacoes.verificaOperador(verificaPosicao,guardaVariavel,guardaValores,linhasGuardaVariavel);//verifica o operador
			}	
			if(verificaPosicao.contains("IMPRIMA(")){//procura pela palavra chave IMPRIMA
				objComandos.verificaSaida(verificaPosicao,guardaVariavel,guardaValores,linhasGuardaVariavel);//verifica e imprime string
			}
			if(verificaPosicao.contains("ESCREVA(")){
			objComandos.verificaEntrada(verificaPosicao,guardaVariavel,guardaValores,linhasGuardaVariavel);
			}
			if (verificaPosicao.contains("SE(")) {
				
				a=i;//se caso não entrar no if a controla o avanço
				i = objFluxo.ControlaFluxo(verificaPosicao,guardaVariavel,guardaValores,linhasGuardaVariavel,i);//ocorrencia do if, manda inclusive qual é a linha em que ele esta
				//se i recebe 0 eh pq o if não eh valido então procuro pelo fim se apartir de i
				
				if(i==-1){
					z=1;
					compara="FIM;";
					for (a+=1; a<contLinhas; a++) {//procura pelo FIM
						if(codigo[a].contains("SE(")){
							z++;
						}
						if(codigo[a].contains("FIM;")){
							z--;
						}
						if(codigo[a].equals(compara)  && z == 0){//encrementa as linhas do código
							i=a;//i recebe um indice anterior ao que deve proseguir no if e continua ate que seja falso
							break;
						}
					}
				}
				
			} 

			else if(verificaPosicao.contains("FIM;")) {
				continue;
			}	
			else if(verificaPosicao.contains("LACO(")){//controle de laco
				b=i; // b recebe a linha de execução
				i = objFluxo.ControlaFluxo(verificaPosicao,guardaVariavel,guardaValores,linhasGuardaVariavel,i);//retorna linha do laco onde deve executar
				if(i!=-1){
					volta=i;
					f.push(volta);
				}
			} if(i==-1){//se caso a condição do while não for verdadeira
					compara="FIMLACO;";
					for(b+=1; b<contLinhas; b++) {//procura pelo FIMLACO;
						if(codigo[b].equals(compara)){//encrementa as linhas do código
							i=b;//i recebe um indice anterior ao que deve proseguir no for e continua o laco ate q de falso
							break;

						}
					}
				}
			 else if(verificaPosicao.contains("FIMLACO;")) {
				if(f.pointer>-1){
					i=f.pop()-1;//menos 1 para que depois do encremento a linha correta seja verificada
				}
				
			}
		}
	}
}