package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("Endereco Servidor: ");
		final String ENDERECO_SERVIDOR = entrada.nextLine();
		
		System.out.println("Porta Servidor: ");
        final int PORTA = entrada.nextInt();
        
        System.out.println("O que voce deseja fazer?");
        System.out.println("1 - INSERT Pessoa fisica");
        System.out.println("2 - INSERT Pessoa juridica");
        System.out.println("3 - GET Pessoa fisica");
        System.out.println("4 - GET Pessoa juridica");
        System.out.println("5 - UPDATE Pessoa fisica");
        System.out.println("6 - UPDATE Pessoa juridica");
        System.out.println("7 - DELETE Pessoa fisica");
        System.out.println("8 - DELETE Pessoa juridica");
        System.out.println("9 - LIST Pessoa fisica");
        System.out.println("10 - LIST Pessoa juridica");
        final int ACAO = entrada.nextInt();
        
        String request = null;
        switch (ACAO) {
		case 1: {
			System.out.print("Digite o nome da pessoa: ");
			String nome = entrada.next();
			System.out.print("Digite o cpf:");
			String cpf = entrada.next();
			System.out.print("Digite o estado civil: ");
			String estadoCivil = entrada.next();
			System.out.print("Digite a rua: ");
			String rua = entrada.next();
			System.out.print("Digite a cidade: ");
			String cidade = entrada.next();
			System.out.print("Digite o estado: ");
			String estado = entrada.next();
			System.out.print("Digite o número: ");
			int num = entrada.nextInt();
			
			request = "INSERT;"+nome+";"+cpf+";"+estadoCivil+";"+rua+";"+cidade+";"+estado+";"+num;
			break;
		}
		case 2: {
			System.out.print("Digite o nome da empresa: ");
			String nome = entrada.nextLine();
			System.out.print("Digite o cnpj:");
			String cnpj = entrada.nextLine();
			System.out.print("Digite o tipo de empresa: ");
			String tipoEmpresa = entrada.nextLine();
			System.out.print("Digite a rua: ");
			String rua = entrada.nextLine();
			System.out.print("Digite a cidade: ");
			String cidade = entrada.nextLine();
			System.out.print("Digite o estado: ");
			String estado = entrada.nextLine();
			System.out.print("Digite o número: ");
			int num = entrada.nextInt();
			
			request = "INSERT;"+nome+";"+cnpj+";"+tipoEmpresa+";"+rua+";"+cidade+";"+estado+";"+num;
			break;
		}
		case 3: {
			System.out.print("Digite o cpf:");
			String cpf = entrada.nextLine();
			
			request = "GET;"+cpf;
			break;
		}
		case 4: {
			System.out.print("Digite o cnpj:");
			String cnpj = entrada.nextLine();
			
			request = "GET;"+cnpj;
			break;
		}
		case 5: {
			System.out.print("Digite o nome da pessoa: ");
			String nome = entrada.nextLine();
			System.out.print("Digite o cpf:");
			String cpf = entrada.nextLine();
			System.out.print("Digite o estado civil: ");
			String estadoCivil = entrada.nextLine();
			System.out.print("Digite a rua: ");
			String rua = entrada.nextLine();
			System.out.print("Digite a cidade: ");
			String cidade = entrada.nextLine();
			System.out.print("Digite o estado: ");
			String estado = entrada.nextLine();
			System.out.print("Digite o número: ");
			int num = entrada.nextInt();
			
			request = "UPDATE;"+nome+";"+cpf+";"+estadoCivil+";"+rua+";"+cidade+";"+estado+";"+num;
			break;
		}
		case 6: {
			System.out.print("Digite o nome da empresa: ");
			String nome = entrada.nextLine();
			System.out.print("Digite o cnpj:");
			String cnpj = entrada.nextLine();
			System.out.print("Digite o tipo de empresa: ");
			String tipoEmpresa = entrada.nextLine();
			System.out.print("Digite a rua: ");
			String rua = entrada.nextLine();
			System.out.print("Digite a cidade: ");
			String cidade = entrada.nextLine();
			System.out.print("Digite o estado: ");
			String estado = entrada.nextLine();
			System.out.print("Digite o número: ");
			int num = entrada.nextInt();
			
			request = "UPDATE;"+nome+";"+cnpj+";"+tipoEmpresa+";"+rua+";"+cidade+";"+estado+";"+num;
			break;
		}
		case 7: {
			System.out.print("Digite o cpf:");
			String cpf = entrada.nextLine();
			
			request = "DELETE;"+cpf;
			break;
		}
		case 8: {
			System.out.print("Digite o cnpj:");
			String cnpj = entrada.nextLine();
			
			request = "DELETE;"+cnpj;
			break;
		}
		case 9: {		
			request = "LIST-PF";
			break;
		}
		case 10: {		
			request = "LIST-PJ";
			break;
		}
		default:
			System.out.println("Acao Inválida");;
		}  

        try (Socket socket = new Socket(ENDERECO_SERVIDOR, PORTA)) {
            // Abrir streams de entrada e saída
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Enviar mensagem ao servidor
            out.println(request);

            // Receber resposta do servidor
            String respostaDoServidor = in.readLine();
            System.out.println(respostaDoServidor);
            
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
       
        entrada.close();
	}
}
