import java.io.*;
import java.util.*;

/**
 * Atividade01
 */
public class Atividade01 {
	public static Scanner            entrada 				= new Scanner(System.in).useDelimiter("\\n");
	public static ArrayList<String>  nome 					= new ArrayList<>();
	public static ArrayList<String>  endereco 			= new ArrayList<>();
	public static ArrayList<String>  nomeGerente 		= new ArrayList<>();
	public static ArrayList<Double>  saldo 					= new ArrayList<>();
	public static ArrayList<String> telefone 				= new ArrayList<>();
	public static ArrayList<String>  cpf 						= new ArrayList<>();
	public static ArrayList<Integer> conta 					= new ArrayList<>();
	public static ArrayList<Integer> numeroAgencia	= new ArrayList<>();

	public static void clearScreen() {  
    System.out.print("\033[H\033[2J");
    System.out.flush();
	}

	public static void gerarEspacos(int qtd){
		for (int i = 0; i < qtd; i++) {
			System.out.println("");
		}
	}

	public static int menuInicial(){
		int opcao;
		System.out.println("*************** Menu Principal ***************");
		System.out.println("");
		System.out.println("**********************************************");
		System.out.println("* Opção   Descrição                          *");
		System.out.println("*                                            *");
		System.out.println("*   1   - Cadastrar Cliente                  *");
		System.out.println("*   2   - Mostrar dados cadastrais (cliente) *");
		System.out.println("*   3   - Mostrar saldo atual                *");
		System.out.println("*   4   - Efetuar depósito                   *");
		System.out.println("*   5   - Receber depósito                   *");
		System.out.println("*   6   - Efetuar pagamento de boletos       *");
		System.out.println("*   7   - Efetuar transferência entre contas *");
		System.out.println("*   8   - Sair do sistema                    *");
		System.out.println("*                                            *");
		System.out.println("**********************************************");
		System.out.print("*  Opção:  ");
		opcao = entrada.nextInt();
		return opcao;
	}

	public static void cadastrarCliente(){
		System.out.println("***** Cadastro de Cliente *****");
		System.out.println("");
		System.out.printf("* Nro. Agência: ");
			numeroAgencia.add(entrada.nextInt());
		System.out.printf("* Nome Gerente: ");
			nomeGerente.add(entrada.next());
		System.out.printf("*        Conta: ");
			conta.add(entrada.nextInt());
		System.out.printf("*         Nome: ");
			nome.add(entrada.next());
		System.out.printf("*          CPF: ");
			cpf.add(entrada.next());
		System.out.printf("*     Telefone: ");
			telefone.add(entrada.next());
		System.out.printf("*     Endereço: ");
			endereco.add(entrada.next());
		System.out.println("");
		System.out.println("Cadastro realizado com sucesso...");
		System.out.println("");
		mostrarDadosCliente(nome.size() - 1);
		System.out.println("Pressione qualquer tecla para voltar ao menu inicial...");
		entrada.next();
	}

	public static void mostrarDadosCliente(int i){
		System.out.println("");
		System.out.println("***** Resumo do Cadastro ******");
		System.out.println("");
		System.out.printf("\n* Nro. Agência: %-7d", numeroAgencia.get(i));
		System.out.printf("\n* Nome Gerente: %-10S", nomeGerente.get(i));
		System.out.printf("\n*        Conta: %-5d", conta.get(i));
		System.out.printf("\n*         Nome: %-10S", nome.get(i));
		System.out.printf("\n*          CPF: %-11S", cpf.get(i));
		System.out.printf("\n*     Telefone: %-15S", telefone.get(i));
		System.out.printf("\n*     Endereço: %-15S", endereco.get(i));
		System.out.printf("\n*        Saldo: %-15S", saldo.get(i));
		gerarEspacos(qtd);
	}

	public static boolean listarClientes(){
		if (nome.size() < 1) {
			System.out.println("Nenhum cliente encontrado");
			System.out.println("");
			System.out.println("Pressione qualquer tecla para voltar ao menu inicial...");
			entrada.next();
			return false;
		} else {
			System.out.println("");
			System.out.println("************************************** Lista de Clientes **************************************");
			System.out.println("*                                                                                             *");
				System.out.printf("* Indice * Nome                 * CPF             * Conta    * Agência * Gerente              *\n");
			for (int i = 0; i < nome.size(); i++) {
				System.out.printf("* %-6d * %-20S * %-15S * %-8d * %-7d * %-20S *\n",
					i+1, nome.get(i), cpf.get(i), conta.get(i), numeroAgencia.get(i), nomeGerente.get(i));
			}
			System.out.println("***********************************************************************************************");
			System.out.println("");
			return true;
		}
	}

	public static void mostrarSaldo(int i){
		System.out.println("");
		System.out.println("**** Saldo Bancário ****");
		System.out.println("");
		System.out.println("* Agência: " + numeroAgencia.get(i));
		System.out.println("*   Conta: " + conta.get(i));
		System.out.printf("*    Nome: %S", nome.get(i));
		System.out.println("\n*   Saldo: R$ " + saldo.get(i));
		System.out.println("");
	}

	public static boolean validarClienteExiste(int i){
		if (i > -1 && i < nome.size()) {
			return true;
		} else {
			System.out.println("");
			System.out.println("Índice não encontrado");
			System.out.println("");
			return false;
		}
	}

	public static boolean validarContaExiste(int param_Conta){
		boolean existeConta = conta.contains(param_Conta);
			if (!existeConta) {
				System.out.println("");
				System.out.println("Conta não encontrada. Digite uma conta válida");	
				System.out.println("");
				return false;
			}	
		return true;
	}

	public static boolean validarSaldoSuficiente(double param_valorDeposito, 
																							 int param_indiceDepositante){
		if (param_valorDeposito > saldo.get(param_indiceDepositante)) {
			System.out.printf("\nSaldo insuficiente.\n");
			return false;
		}
		return true;
	}

	public static void depositar(){
		int contaDepositante, agenciaDestinatario, contaDestinatario, indiceDepositante;
		double valorDeposito;
		String nomeDestinatario, cpfDestinatario;

		do {
			System.out.printf("Conta Origem: ");
			contaDepositante = entrada.nextInt();
		} while (!validarContaExiste(contaDepositante));

		indiceDepositante = conta.indexOf(contaDepositante);

		do {
			System.out.printf("\n   Saldo atual: %6.2f", saldo.get(indiceDepositante));
			System.out.printf("\nValor Depósito: ");
			valorDeposito = entrada.nextDouble();
		} while (validarSaldoSuficiente(valorDeposito, indiceDepositante)	);
		System.out.printf("   Saldo final: %6.2f\n", (saldo.get(indiceDepositante) - valorDeposito));

		gerarEspacos(2);
		System.out.println("Dados do destinatário");
		System.out.printf("\n   Nome: ");
		nomeDestinatario = entrada.next();
		System.out.printf("    CPF: ");
		cpfDestinatario = entrada.next();
		System.out.printf("Agência: ");
		agenciaDestinatario = entrada.nextInt();
		System.out.printf("  Conta: ");
		contaDestinatario = entrada.nextInt();
		System.out.println("");

		System.out.println(" Resumo da Operação");
		System.out.println("");
		System.out.printf("Valor depositado: %6.2f\n", valorDeposito);
		System.out.println("");
		System.out.println("Remetente");
		System.out.printf("\n   Nome: %S", nome.get(indiceDepositante));
		System.out.printf("\n    CPF: %S", cpf.get(indiceDepositante));
		System.out.printf("\nAgência: %d", numeroAgencia.get(indiceDepositante));
		System.out.printf("\n  Conta: %d", conta.get(indiceDepositante));
		gerarEspacos(3);
		System.out.println("Destinatário");
		System.out.printf("\n   Nome: %S", nomeDestinatario);
		System.out.printf("\n    CPF: %S", cpfDestinatario);
		System.out.printf("\nAgência: %d", agenciaDestinatario);
		System.out.printf("\n  Conta: %d", contaDestinatario);
		System.out.println("");

		System.out.printf("\nConfirmar o depósito? (s/n): ");
		if (entrada.next().equals("s")) {
			
		} else {
			System.out.println("");
			System.out.println("Depósito cancelado.");
			System.out.println("");
			System.out.println("Pressione qualquer tecla para voltar ao menu principal");
			entrada.next();
		}
	}
	
	public static void main(String[] args) {
		nome.add("Paulo");
		endereco.add("Av Francisco");
		nomeGerente.add("Lucas");
		saldo.add(100.0);
		telefone.add("(28) 99968-6050");
		cpf.add("170.561.397.79");
		conta.add(251368);
		numeroAgencia.add(231);

		nome.add("Henrique");
		endereco.add("Av Mardegan");
		nomeGerente.add("Edson");
		saldo.add(500.0);
		telefone.add("(28) 3515-3100");
		cpf.add("250.301.381.79");
		conta.add(99654);
		numeroAgencia.add(104);

		int opcaoMenuInicial;
		int indiceCliente;
		do {
			clearScreen();
			opcaoMenuInicial = menuInicial();
			switch (opcaoMenuInicial) {
				case 1:
					clearScreen();
					cadastrarCliente();
					break;
				case 2:
					indiceCliente = 0;
					do {
						clearScreen();
						if (listarClientes()) {
							System.out.printf("Digite o índice da conta correspondente (Digite 0 p/ voltar ao menu): ");
							indiceCliente = entrada.nextInt() - 1;
							if (indiceCliente != -1)  {
								if (validarClienteExiste(indiceCliente)) {
									mostrarDadosCliente(indiceCliente);	
								}
							System.out.println("Digite qualquer tecla para continuar...");
							entrada.next();
							}
						}
					} while (indiceCliente != -1);
					break;
				case 3:
					indiceCliente = 0;
					do {
						clearScreen();
						if (listarClientes()) {
							System.out.printf("Digite o índice da conta correspondente (Digite 0 p/ voltar ao menu): ");
							indiceCliente = entrada.nextInt() - 1;
							if (indiceCliente != -1)  {
								if (validarClienteExiste(indiceCliente)) {
									mostrarSaldo(indiceCliente);	
								}
								System.out.println("Digite qualquer tecla para continuar...");
								entrada.next();
							}
						}
					} while (indiceCliente != -1);
					break;
				case 4:
					clearScreen();
					depositar();
					break;
				case 8:
					System.out.println("Saindo do sistema");
					break;
				default:
					System.out.println("");
					System.out.println("Opção inválida. Digite uma das opções do menu");
					System.out.println("");
					System.out.println("Digite qualquer tecla para tentar novamente...");
					entrada.next();
					System.out.println("");
					break;
			}
		} while (opcaoMenuInicial != 8);
	}
}