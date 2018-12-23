package br.com.caesar.sistemas.caesarservice.util;

public class ValidacaoUtil {
	private static int CPF_P1[] = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 0 };
	private static int CPF_P2[] = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };

	public static boolean cpfValido(String cpf) {
		if (StringUtil.isEmpty(cpf))
			return false; // Se o CPF for vazio, não é válido

		String cpfStr = cpf.replaceAll("[^0-9]", "");
		if (cpfStr.length() > 11 || cpfStr.length() == 0)
			return false; // Se o CPF contiver mais de 11 números ou nenhum
							// número, não é válido

		while (cpfStr.length() < 11)
			cpfStr = '0' + cpfStr; // Completa com zeros à esquerda, se for
									// necessário

		int soma1 = 0, soma2 = 0;

		for (int i = 0; i < 10; i++) {
			soma1 += Character.getNumericValue(cpfStr.charAt(i)) * CPF_P1[i];
			soma2 += Character.getNumericValue(cpfStr.charAt(i)) * CPF_P2[i];
		}

		int dv1 = soma1 % 11;
		dv1 = (dv1 < 2) ? 0 : 11 - dv1; // Calcula o 1º dígito verificador

		int dv2 = soma2 % 11;
		dv2 = (dv2 < 2) ? 0 : 11 - dv2; // Calcula o 2º dígito verificador

		// Verifica se os dígitos batem com os dígitos informados no CPF
		return Character.getNumericValue(cpfStr.charAt(9)) == dv1
				&& Character.getNumericValue(cpfStr.charAt(10)) == dv2;
	}
}
