class BaiduVector
{
	private String name;
	private int length;
	private double[] positions;

	public BaiduVector(String name, int indexes)
	{
		this.name = name;
		this.length = indexes;

		positions = new double[indexes];
	}

	/**
	 *	Compara se uma String é na verdade um vetor, em outras palavras:
	 *	Compara se a string termina com "[inteiro]"
	 *
	 *	@return boolean
	 */
	public static boolean isVector(String str) throws InvalidVectorIndexException
	{
		int startBracket = str.indexOf("["); // Procura por "["
		int endBracket   = str.indexOf("]"); // Procura por "]"

		// Retorna false caso não encontre algum dos Brackets
		if (startBracket == -1 || endBracket == -1) return false;

		// Pega a string entre os brackets: []
		String indexAnalysis = str.substring(startBracket + 1, endBracket);

		// Verifica se o número que está entre os Brackets é um inteiro
		try {
			Integer.parseInt(indexAnalysis);
		} catch (NumberFormatException e) {
			throw new InvalidVectorIndexException("\"" + indexAnalysis + "\" não é um índice válido para o vetor.");
		}

		return true;
	}

	public static int vectorIndexes(String str) throws InvalidVectorIndexException
	{
		int startBracket = str.indexOf("["); // Procura por "["
		int endBracket   = str.indexOf("]"); // Procura por "]"

		// Retorna false caso não encontre algum dos Brackets
		if (startBracket == -1 || endBracket == -1)
			throw new InvalidVectorIndexException("Não foi reonhecido brackets do vetor: " + str);

		// Pega a string entre os brackets: []
		String indexAnalysis = str.substring(startBracket + 1, endBracket);

		// Verifica se o número que está entre os Brackets é um inteiro
		try {
			return Integer.parseInt(indexAnalysis);
		} catch (NumberFormatException e) {
			throw new InvalidVectorIndexException("\"" + indexAnalysis + "\" não é um índice válido para o vetor.");
		}
	}

	/**
	 *	Como estamos basendo o vetor da Baidu em cima dos vetores do Java,
	 *	teremos os mesmos erros causados no Java.
	 *	Exemplo: acessos á posições indevidas.
	 */
	public double getIndex(int index)
	{
		return this.positions[index];
	}

	public String getName()
	{
		return this.name;
	}

	// Guess what.
	public int getLength()
	{
		return this.length;
	}
}