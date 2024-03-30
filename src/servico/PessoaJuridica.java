package servico;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class PessoaJuridica extends Pessoa {
	private String cnpj;
	private String tipoEmpresa;
	
	public PessoaJuridica(String nome, Endereco endereco, String cnpj, String tipoEmpresa) {
		super(nome, endereco);
		setCnpj(cnpj);
		setTipoEmpresa(tipoEmpresa);
	}
	
	public PessoaJuridica() {
		
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		if (cnpj.length() == 14) {
			this.cnpj = cnpj;
		}
	}

	public String getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(String tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.getNome());
		builder.append("; ");
		builder.append(cnpj);
		builder.append("; ");
		builder.append(tipoEmpresa);
		builder.append("; ");
		builder.append(super.getEndereco().toString());
		return builder.toString();
	}
	
	public void incluir() {
		GsonBuilder builder = new GsonBuilder();
	    Gson gson = builder.create();
	    FileWriter writer;
	    List<PessoaJuridica> list = select();
	    if (list == null) {
	    	list = new ArrayList<PessoaJuridica>();
		    list.add(this);
	    } else {
	    	list.add(this);
	    }
		try {
			writer = new FileWriter("json/PessoaJuridica.json");
			writer.write(gson.toJson(list));
		    writer.close();
		    this.getEndereco().updateInsert();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String update (String nome, Endereco endereco, String cnpj, String tipoEmpresa) {
		if (delete(cnpj, false)) {
			PessoaJuridica pj = new PessoaJuridica(nome, endereco, cnpj, tipoEmpresa);
			pj.incluir();
			pj.getEndereco().updateInsert();
			return "Pessoa juridica alterada com sucesso.";
		} else {
			return "Pessoa juridica não encontrada.";
		}
	}
	
	private List<PessoaJuridica> select() {
	    BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(
							 new FileReader("json/PessoaJuridica.json"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    Type listType = new TypeToken<ArrayList<PessoaJuridica>>(){}.getType();
	    List<PessoaJuridica> lista = new ArrayList<PessoaJuridica>();
	    lista = new Gson().fromJson(bufferedReader, listType);
	    return lista;
	}
	
	public String get(String cnpj) {
		List<PessoaJuridica> lista = this.select();
		if (lista == null) {
			return "Sem pessoas juridicas cadastradas.";
		}
		
		for (PessoaJuridica pj : lista) {
			if (cnpj.equals(pj.getCnpj())) {
				return pj.toString();
			}
		}
		return "Pessoa juridica não encontrada.";
	}
	
	public String list() {
		List<PessoaJuridica> lista = this.select();
		
		if (lista == null) {
			return "Nenhum registro encontrado";
		}
		
		String list = lista.size()+" \n ";
		
		for (PessoaJuridica pj : lista) {
			list = list+pj.toString()+" \n ";
		}
		return list;
	}
	
	public boolean delete(String cnpj, boolean mostraMsg) {
		List<PessoaJuridica> lista = this.select();
		
		if (lista == null) {
			if (mostraMsg) {
				System.out.println("Sem pessoas juridicas cadastradas.");
			}
			return false;
		}
		
		for (PessoaJuridica pj : lista) {
			if (cnpj.equals(pj.getCnpj())) {
				lista.remove(pj);
				pj.restaurarLista(lista);
				if (mostraMsg) {
					System.out.println("Pessoa juridica removida com sucesso.");
				}
				return true;
			}
		}
		if (mostraMsg) {
			System.out.println("Pessoa juridica não encontrada.");
		}
		return false;
	}
	
	private void restaurarLista(List<PessoaJuridica> list) {
		GsonBuilder builder = new GsonBuilder();
	    Gson gson = builder.create();
	    FileWriter writer;
		try {
			writer = new FileWriter("json/PessoaJuridica.json");
			writer.write(gson.toJson(list));
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
