package servico;

public class Pessoa {
	private String nome;
	private Endereco endereco;
	
	public Pessoa(String nome, Endereco endereco) {
		setNome(nome);
		setEndereco(endereco);
	}
	
	public Pessoa() {
		
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pessoa [nome=");
		builder.append(nome);
		builder.append(", endereco=");
		builder.append(endereco.toString());
		builder.append("]");
		return builder.toString();
	}
}
