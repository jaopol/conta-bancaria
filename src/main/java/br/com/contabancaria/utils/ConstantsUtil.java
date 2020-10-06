package br.com.contabancaria.utils;

public interface ConstantsUtil {
	
	public interface Conta{
		//MSGs conta
		public static final String MSG_ERROR_DEFAULT = "Ocorreu um erro. Tente novamente mais tarde!";
		public static final String MSG_CPF_NOME_OBRIGATORIO = "CPF e nome completo são obrigatórios para abrir a conta!";
		public static final String MSG_CPF_INVALIDO = "CPF inválido!";
		public static final String MSG_CPF_REPETIDO = "CPF já Cadastrado em uma conta";
		public static final String MSG_CADASTRO_SUCESSO = "Conta cadastrada com sucesso!";
		
	}
	
 	public interface MovimentoConta{
 		//MSGs MovimentoConta
		public static final String MSG_ERROR_DEFAULT = "Ocorreu um erro. Tente novamente mais tarde!";
		public static final String MSG_DEPOSITO_OK = "Deposito realizado com sucesso!";
		public static final String MSG_CONTA_INVALIDA = "Transação não permitida! Conta não cadastrada ou invalida!";
		public static final String MSG_RETIRADA_INVALIDA = "Saldo indisponível para retirada!";
		public static final String MSG_RETIRADA_OK = "Retirada realizada com sucesso!";
 		
 	}


}
