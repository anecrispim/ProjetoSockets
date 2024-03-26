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

public class Endereco {
	private String rua;
	private String cidade;
	private String estado;
	private int numero;
	
	public Endereco(String rua, String cidade, String estado, int num) {
		this.rua = rua;
		this.cidade = cidade;
		this.estado = estado;
		this.numero = num;
	}
	
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(rua);
		builder.append("; ");
		builder.append(cidade);
		builder.append("; ");
		builder.append(estado);
		builder.append("; ");
		builder.append(numero);
		return builder.toString();
	}
	
	private void insert() {
		GsonBuilder builder = new GsonBuilder();
	    Gson gson = builder.create();
	    FileWriter writer;
	    List<Endereco> list = select();
	    if (list == null) {
	    	list = new ArrayList<Endereco>();
		    list.add(this);
	    } else {
	    	list.add(this);
	    }
		try {
			writer = new FileWriter("json/Endereco.json");
			writer.write(gson.toJson(list));
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateInsert() {
		this.delete(this);
		this.insert();
	}
	
	private List<Endereco> select() {
	    BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(
							 new FileReader("json/Endereco.json"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    Type listType = new TypeToken<ArrayList<Endereco>>(){}.getType();
	    List<Endereco> lista = new ArrayList<Endereco>();
	    lista = new Gson().fromJson(bufferedReader, listType);
	    return lista;
	}
	
	private boolean delete(Endereco endereco) {
		List<Endereco> lista = this.select();
		
		if (lista == null) {
			return false;
		}
		
		for (Endereco e : lista) {
			if (endereco.toString().equals(e.toString())) {
				lista.remove(e);
				e.restaurarLista(lista);
				return true;
			}
		}
		return false;
	}
	
	private void restaurarLista(List<Endereco> list) {
		GsonBuilder builder = new GsonBuilder();
	    Gson gson = builder.create();
	    FileWriter writer;
		try {
			writer = new FileWriter("json/Endereco.json");
			writer.write(gson.toJson(list));
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
