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

	public static String vectorName(String str)
	{
		int startBracket = str.indexOf("[");

		if (startBracket == -1) return str;

		return str.substring(0, startBracket);
	}

	/**
	 *	Extrai o primeiro vetor da string
	 */
	public static String extractVectorFromStr(String str)
	{
		// Removendo tudo após a variável
		str = str.substring(0, str.indexOf("$"));
		str = str.substring(str.lastIndexOf(" ") + 1);

		return str;
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

	public void setIndex(int index, double value)
	{
		this.positions[index] = value;
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

	public void parseAttribuition(String line) throws InvalidVectorIndexException, InvalidValueException
	{
		int index = BaiduVector.vectorIndexes(line);
		String valueStr = line.substring(line.indexOf("=") + 1, line.lastIndexOf(";"));
		Float value;

		try {
			value = Float.parseFloat(valueStr);
		} catch (NumberFormatException e) {
			throw new InvalidValueException();
		}

		setIndex(index, value);
	}
}