package br.com.caesar.sistemas.caesarservice.exception;

@SuppressWarnings(value = { "serial" })
public class AutenticacaoException extends Exception {

	private String motivo;

	public AutenticacaoException(String motivo) {
		this.motivo = motivo;
	}

	public String getMotivo() {
		return this.motivo;
	}

}
