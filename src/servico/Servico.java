package servico;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servico {

	public static void main(String[] args) {
		final int PORTA = 8080;

        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            System.out.println("Servidor aguardando conexao...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket);

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Receber mensagem do cliente
                String requestCliente = in.readLine();
                String msgRetornoServidor = processaAcao(requestCliente);

                // Enviar resposta ao cliente
                out.println(msgRetornoServidor);

                // Fechar conexão com cliente
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static String processaAcao(String request) {
		String[] acoes = request.split(";");
		
		switch (acoes[0]) {
		case "INSERT": {
			if (acoes.length == 8) {
				return "Erro: requisicao nao aceita.";
			}
			
			Endereco ed = new Endereco(acoes[4], acoes[5], acoes[6], Integer.parseInt(acoes[7]));
			
			if (acoes[3].length() == 11) {
				PessoaFisica pf = new PessoaFisica(acoes[1], ed, acoes[2], acoes[3]);
				pf.incluir();
				return "";
			} else if (acoes[3].length() == 14) {
				PessoaJuridica pj = new PessoaJuridica(acoes[1], ed, acoes[2], acoes[3]);
				pj.incluir();
				return "";
			} else {
				return "Erro: CPF/CNPJ Invalidos.";
			}
		}
		case "UPDATE": {
			if (acoes.length == 8) {
				return "Erro: requisicao nao aceita.";
			}
			
			Endereco ed = new Endereco(acoes[4], acoes[5], acoes[6], Integer.parseInt(acoes[7]));
			
			if (acoes[3].length() == 11) {
				PessoaFisica pf = new PessoaFisica(acoes[1], ed, acoes[2], acoes[3]);
				return pf.update(acoes[1], ed, acoes[2], acoes[3]);
			} else if (acoes[3].length() == 14) {
				PessoaJuridica pj = new PessoaJuridica(acoes[1], ed, acoes[2], acoes[3]);
				return pj.update(acoes[1], ed, acoes[2], acoes[3]);
			} else {
				return "Erro: CPF/CNPJ Invalidos.";
			}
		}
		case "GET": {
			if (acoes.length == 2) {
				return "Erro: requisicao nao aceita.";
			}
			if (acoes[1].length() == 11) {
				PessoaFisica pf = new PessoaFisica();
				return pf.get(acoes[1]);
			} else if (acoes[1].length() == 14) {
				PessoaJuridica pj = new PessoaJuridica();
				return pj.get(acoes[1]);
			} else {
				return "Erro: CPF/CNPJ Invalidos.";
			}
		}
		case "DELETE": {
			if (acoes.length == 2) {
				return "Erro: requisicao nao aceita.";
			}
			if (acoes[1].length() == 11) {
				PessoaFisica pf = new PessoaFisica();
				if (pf.delete(acoes[1], false)) {
					return "Pessoa fisica removida com sucesso.";
				}
				return "Pessoa fisica nao encontrada.";
				
			} else if (acoes[1].length() == 14) {
				PessoaJuridica pj = new PessoaJuridica();
				if (pj.delete(acoes[1], false)) {
					return "Pessoa juridica removida com sucesso.";
				}
				return "Pessoa juridica nao encontrada.";
			} else {
				return "Erro: CPF/CNPJ Invalidos.";
			}
		}
		case "LIST-PF": {
			if (acoes.length == 1) {
				return "Erro: requisicao nao aceita.";
			}
			PessoaFisica pf = new PessoaFisica();
			return pf.list();
			
		}
		case "LIST-PJ": {
			if (acoes.length == 1) {
				return "Erro: requisicao nao aceita.";
			}
			PessoaJuridica pj = new PessoaJuridica();
			return pj.list();
			
		}
		default:
			return "Erro: requisicao nao aceita.";
		}
	}
}
