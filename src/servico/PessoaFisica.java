package servico;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class PessoaFisica extends Pessoa {
	private String cpf;
	private String estadoCivil;
	
	public PessoaFisica(String nome, Endereco endereco, String cpf, String estadoCivil) {
		super(nome, endereco);
		setCpf(cpf);
		setEstadoCivil(estadoCivil);
	}
	
	public PessoaFisica() {
		
	}
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		if (cpf.length() == 11) {
			this.cpf = cpf;
		}
	}
	public String getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.getNome());
		builder.append("; ");
		builder.append(cpf);
		builder.append("; ");
		builder.append(estadoCivil);
		builder.append("; ");
		builder.append(super.getEndereco().toString());
		return builder.toString();
	}
	
	public void incluir() {
		GsonBuilder builder = new GsonBuilder();
	    Gson gson = builder.create();
	    FileWriter writer;
	    List<PessoaFisica> list = select();
	    if (list == null) {
	    	list = new ArrayList<PessoaFisica>();
		    list.add(this);
	    } else {
	    	list.add(this);
	    }
		try {
			writer = new FileWriter("json/PessoaFisica.json");
			writer.write(gson.toJson(list));
		    writer.close();
		    this.getEndereco().updateInsert();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String update (String nome, Endereco endereco, String cpf, String estadoCivil) {
		if (delete(cpf, false)) {
			PessoaFisica pf = new PessoaFisica(nome, endereco, cpf, estadoCivil);
			pf.incluir();
			pf.getEndereco().updateInsert();
			return "Pessoa fisica alterada com sucesso.";
		} else {
			return "Pessoa fisica não encontrada.";
		}
	}
	
	private List<PessoaFisica> select() {
	    BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(
							 new FileReader("json/PessoaFisica.json"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    Type listType = new TypeToken<ArrayList<PessoaFisica>>(){}.getType();
	    List<PessoaFisica> lista = new ArrayList<PessoaFisica>();
	    lista = new Gson().fromJson(bufferedReader, listType);
	    return lista;
	}
	
	public String get(String cpf) {
		List<PessoaFisica> lista = this.select();
		if (lista == null) {
			return "Sem pessoas fisicas cadastradas.";
		}
		
		for (PessoaFisica pf : lista) {
			if (cpf.equals(pf.getCpf())) {
				return pf.toString();
			}
		}
		return "Pessoa fisica não encontrada.";
	}
	
	public String list() {
		List<PessoaFisica> lista = this.select();
		
		if (lista == null) {
			return "Nenhum registro encontrado";
		}
		
		String list = lista.size()+"\n";
		
		for (PessoaFisica pf : lista) {
			list = list+pf.toString()+"\n";
		}
		return list;
		
	}
	
	public boolean delete(String cpf, boolean mostraMsg) {
		List<PessoaFisica> lista = this.select();
		
		if (lista == null) {
			if (mostraMsg) {
				System.out.println("Sem pessoas fisicas cadastradas.");
			}
			return false;
		}
		
		for (PessoaFisica pf : lista) {
			if (cpf.equals(pf.getCpf())) {
				lista.remove(pf);
				pf.restaurarLista(lista);
				if (mostraMsg) {
					System.out.println("Pessoa fisica removida com sucesso.");
				}
				return true;
			}
		}
		if (mostraMsg) {
			System.out.println("Pessoa fisica não encontrada.");
		}
		return false;
	}
	
	private void restaurarLista(List<PessoaFisica> list) {
		GsonBuilder builder = new GsonBuilder();
	    Gson gson = builder.create();
	    FileWriter writer;
		try {
			writer = new FileWriter("json/PessoaFisica.json");
			writer.write(gson.toJson(list));
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
