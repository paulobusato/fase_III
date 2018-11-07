import java.io.*;
import java.util.*;

public class Atividade01 {
	public static Scanner            entrada 				= new Scanner(System.in).useDelimiter("\\n");
	public static ArrayList<String>  nome 					= new ArrayList<>();
	public static ArrayList<String>  endereco 			= new ArrayList<>();
	public static ArrayList<String>  nomeGerente		= new ArrayList<>();
	public static ArrayList<Double>  saldo 					= new ArrayList<>();
	public static ArrayList<String>  telefone				= new ArrayList<>();
	public static ArrayList<String>  cpf 						= new ArrayList<>();
	public static ArrayList<Integer> conta 					= new ArrayList<>();
	public static ArrayList<Integer> numeroAgencia	= new ArrayList<>();

	public static void clearScreen() {  
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static int validarContaExiste(int _conta){
		if (!conta.contains(_conta)) {
			System.out.printf("\nConta não encontrada. Digite uma conta válida\n\n");
			return -1;
		}
		return conta.indexOf(_conta);
	}

	public static boolean validarSaldoSuficiente(double _valorDeposito, int _indiceDepositante){
		if (_valorDeposito <= 0) {
			System.out.printf("\nO valor deve ser maior que zero.\n");
			return false;
		}
		if (_valorDeposito > saldo.get(_indiceDepositante)) {
			System.out.printf("\nSaldo insuficiente.\n");
			return false;
		}
		return true;
	}

	public static void mensagemVoltarMenu(){
		System.out.print("\nPressione qualquer tecla para voltar ao menu inicial...\n");
		entrada.next();
	}

	public static int menuInicial(){
		System.out.printf("*************** Menu Principal ***************\n\n");
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
		return entrada.nextInt();
	}

	public static void cadastrarCliente(){
		boolean continua;
		boolean TentarOutraConta = false;
		System.out.printf("***** Cadastro de Cliente *****\n\n");
		System.out.printf("* Nro. Agência: ");
			numeroAgencia.add(entrada.nextInt());
		System.out.printf("* Nome Gerente: ");
			nomeGerente.add(entrada.next());
		do {
			System.out.printf("*        Conta: ");
			int novaConta = entrada.nextInt();
			if (conta.contains(novaConta)) {
				System.out.printf("\nA conta já existe.");
				System.out.printf("\nDeseja inserir um outro numero? (s/n): ");
				TentarOutraConta = entrada.next().toLowerCase().equals("s");
			} else {
				conta.add(novaConta);
				System.out.printf("*         Nome: ");
					nome.add(entrada.next());
				System.out.printf("*          CPF: ");
					cpf.add(entrada.next());
				System.out.printf("*     Telefone: ");
					telefone.add(entrada.next());
				System.out.printf("*     Endereço: ");
					endereco.add(entrada.next());
				System.out.printf("\nPara finalizar o cadastro é necessário realizar um depósito. " +
													"O valor mínimo é de 100 reais.");
				double valorDeposito;
				do {
					System.out.printf("\nValor: ");
					valorDeposito = entrada.nextDouble();
					if (valorDeposito < 100) {
						System.out.printf("\nO valor depositado é insuficiente.");
						System.out.printf("\nDeseja tentar inserir um novo valor? (s/n): ");
						continua = entrada.next().toLowerCase().equals("s");
					} else {
						continua = false;
					}
				} while (continua);

				if (valorDeposito < 100) {
					System.out.printf("\nCadastrado não foi efetivado\n");
					int UltimoRegistro = nome.size() - 1;
					nome.remove(UltimoRegistro);
					endereco.remove(UltimoRegistro);
					nomeGerente.remove(UltimoRegistro);
					telefone.remove(UltimoRegistro);
					cpf.remove(UltimoRegistro);
					conta.remove(UltimoRegistro);
					numeroAgencia.remove(UltimoRegistro);
				} else {
					System.out.println("\nCadastro realizado com sucesso...\n\n");
					saldo.add(valorDeposito);
					mostrarDadosCliente(conta.get(conta.size() - 1));
					break;
				}
			}
		} while (TentarOutraConta);
		mensagemVoltarMenu();
	}

	public static void mostrarDadosCliente(int _conta){
		int indice = validarContaExiste(_conta);
		System.out.print("\n***** Resumo do Cadastro ******\n\n");
		System.out.printf("\n* Nro. Agência: %-7d", numeroAgencia.get(indice));
		System.out.printf("\n* Nome Gerente: %-10S", nomeGerente.get(indice));
		System.out.printf("\n*        Conta: %-5d", conta.get(indice));
		System.out.printf("\n*         Nome: %-10S", nome.get(indice));
		System.out.printf("\n*          CPF: %-11S", cpf.get(indice));
		System.out.printf("\n*     Telefone: %-15S", telefone.get(indice));
		System.out.printf("\n*     Endereço: %-15S", endereco.get(indice));
		System.out.printf("\n*        Saldo: %-15S\n\n", saldo.get(indice));
	}

	public static void mostrarDadosResumido(String _nome, String _cpf, int _agencia, int _conta){
		System.out.printf("\n   Nome: %S", _nome);
		System.out.printf("\n    CPF: %S", _cpf);
		System.out.printf("\nAgência: %d", _agencia);
		System.out.printf("\n  Conta: %d", _conta);
	}

	public static boolean listarClientes(){
		if (nome.size() < 1) {
			System.out.print("Nenhum cliente encontrado\n\n");
			mensagemVoltarMenu();
			return false;
		} else {
			System.out.println("\n******************************** Lista de Clientes ********************************");
			System.out.println("*                                                                                 *");
				System.out.printf("* Agência * Conta * Nome                 * CPF             * Gerente              *\n");
			for (int i = 0; i < nome.size(); i++) {
				System.out.printf("* %-7d * %-5d * %-20S * %-15S * %-20S *\n",
				numeroAgencia.get(i), conta.get(i), nome.get(i), cpf.get(i) , nomeGerente.get(i));
			}
			System.out.print("***********************************************************************************\n\n");
			return true;
		}
	}

	public static void mostrarSaldo(int _conta){
		int indice = validarContaExiste(_conta);
		System.out.printf("\n**** Saldo Bancário ****\n");
		System.out.printf("\n* Agência: %d", numeroAgencia.get(indice));
		System.out.printf("\n*   Conta: %d", conta.get(indice));
		System.out.printf("\n*    Nome: %S", nome.get(indice));
		System.out.printf("\n*   Saldo: R$ %6.2f\n\n", saldo.get(indice));
		System.out.printf("Deseja salvar o saldo bancário? (s/n)");
		if (entrada.next().toLowerCase().equals("s")) { salvarSaldoBancario(indice); };
	}

	public static void deposito(String _tipoDeposito){
		int _conta, _agencia, _indice;
		double _valorDeposito;
		String _nome, _cpf, _texto;
		boolean efetuar = _tipoDeposito.equals("efetuar") ? true : false;
		_texto = efetuar ? "Conta Origem: " : "Conta Destino: ";

		do {
			System.out.printf(_texto);
			_indice = validarContaExiste(entrada.nextInt());
		} while (_indice == -1);

		if (efetuar) {
			do {
				System.out.printf("\n   Saldo atual: %6.2f", saldo.get(_indice));	
				System.out.printf("\nValor Depósito: ");
				_valorDeposito = entrada.nextDouble();
			} while (!validarSaldoSuficiente(_valorDeposito, _indice));
			System.out.printf("   Saldo final: %6.2f\n", (saldo.get(_indice) - _valorDeposito));	
		} else {
			System.out.printf("\nValor Depósito: ");
			_valorDeposito = entrada.nextDouble();
		}

		_texto = efetuar ? "Dados do favorecido" : "Dados do remetente";
		System.out.printf("\n\n%S\n", _texto);
		System.out.printf("\n   Nome: ");
		_nome = entrada.next();
		System.out.printf("    CPF: ");
		_cpf = entrada.next();
		System.out.printf("Agência: ");
		_agencia = entrada.nextInt();
		System.out.printf("  Conta: ");
		_conta = entrada.nextInt();

		System.out.println("\nResumo da Operação\n\n");
		System.out.printf("Valor depositado: %6.2f\n\n", _valorDeposito);
		System.out.println("Remetente");
		if (efetuar) {
			mostrarDadosResumido(nome.get(_indice), cpf.get(_indice), numeroAgencia.get(_indice), conta.get(_indice));	
		} else {
			mostrarDadosResumido(_nome, _cpf, _agencia, _conta);	
		}
		System.out.printf("\n\n\nDestinatário\n");
		if (_tipoDeposito.equals("receber")) {
			mostrarDadosResumido(nome.get(_indice), cpf.get(_indice), numeroAgencia.get(_indice), conta.get(_indice));	
		} else {
			mostrarDadosResumido(_nome, _cpf, _agencia, _conta);	
		}

		System.out.printf("\n\nConfirmar o depósito? (s/n): ");
		if (entrada.next().equals("s")) {
			if (efetuar) {
				saldo.set(_indice, saldo.get(_indice) - _valorDeposito);
				comprovanteDeposito(_valorDeposito, conta.get(_indice), numeroAgencia.get(_indice), nome.get(_indice), _conta, _agencia, _nome);
			} else {
				saldo.set(_indice, saldo.get(_indice) + _valorDeposito);
				comprovanteDeposito(_valorDeposito, _conta, _agencia, _nome, conta.get(_indice), numeroAgencia.get(_indice), nome.get(_indice));
			}
			
		} else {
			System.out.println("\nDepósito cancelado.\n\n");
			mensagemVoltarMenu();
		}
	}

	public static void pagarBoleto(){
		int codBoleto, contaBancaria;
		double valorBoleto, saldoConta;

		System.out.printf("**** Pagamento de boleto ****\n\n");
		do {
			System.out.printf("        Conta: ");
			contaBancaria = entrada.nextInt();
		} while (validarContaExiste(contaBancaria) == -1);
		System.out.printf("  Cód. Boleto: ");
		codBoleto = entrada.nextInt();
		boolean continua, temSaldo;
		do {
			saldoConta = saldo.get(conta.indexOf(contaBancaria));
			System.out.printf("\n        Saldo: %5.2f", saldoConta);
			System.out.printf("\n        Valor: ");
			valorBoleto = entrada.nextDouble();
			System.out.printf("\n  Saldo final: %5.2f", (saldoConta - valorBoleto));
			temSaldo = validarSaldoSuficiente(valorBoleto, conta.indexOf(contaBancaria));
			if (!temSaldo) {
				System.out.printf("\nDeseja inserir um novo valor? (s/n): ");
				continua = entrada.next().toLowerCase().equals("s");
			} else {
				continua = false;
			}
		} while (continua);

		if (temSaldo) {
			System.out.printf("\n\nConfirmar o depósito? (s/n): ");
			if (entrada.next().equals("s")) {
				saldo.set(conta.indexOf(contaBancaria), saldo.get(conta.indexOf(contaBancaria)) - valorBoleto);
				System.out.printf("\nPagamento realizado com sucesso.\n");
			} else {
				System.out.printf("\nPagamento cancelado.\n");
			}	
		}
		mensagemVoltarMenu();
	}

	public static void transferenciaEntreContas(){
		int contaOrigem, contaDestino, indiceOrigem, indiceDestino;
		double valorTransferencia;
		System.out.printf("**** Transferência entre contas ****\n\n");
		System.out.printf("Dados do remetente\n\n");
		do {
			System.out.printf("Conta Origem: ");
			contaOrigem = entrada.nextInt();
		} while (validarContaExiste(contaOrigem) == -1);
		indiceOrigem = conta.indexOf(contaOrigem);

		if (saldo.get(indiceOrigem) > 0) {
			mostrarDadosResumido(nome.get(indiceOrigem), cpf.get(indiceOrigem), numeroAgencia.get(indiceOrigem), conta.get(indiceOrigem));
			do {
				System.out.printf("\n\n   Saldo (+): %6.2f", saldo.get(indiceOrigem));
				System.out.printf("\n   Valor (-): ");
				valorTransferencia = entrada.nextDouble();
			} while (!validarSaldoSuficiente(valorTransferencia, indiceOrigem));
			System.out.printf("   Saldo (=): %6.2f\n", (saldo.get(indiceOrigem) - valorTransferencia));
			
			System.out.println("\n\nDados do destinatário\n\n");
			do {
				System.out.printf("Conta Destino: ");
				contaDestino = entrada.nextInt();
			} while (validarContaExiste(contaDestino) == -1);
			indiceDestino = conta.indexOf(contaDestino);
			mostrarDadosResumido(nome.get(indiceDestino), cpf.get(indiceDestino), numeroAgencia.get(indiceDestino), conta.get(indiceDestino));
			
			System.out.printf("\n\nConfirmar o depósito? (s/n): ");
			if (entrada.next().equals("s")) {
				saldo.set(indiceOrigem, saldo.get(indiceOrigem) - valorTransferencia);
				saldo.set(indiceDestino, saldo.get(indiceDestino) + valorTransferencia);
				comprovanteDeposito(valorTransferencia, conta.get(indiceOrigem), numeroAgencia.get(indiceOrigem), nome.get(indiceOrigem), conta.get(indiceDestino), numeroAgencia.get(indiceDestino), nome.get(indiceDestino));
			} else {
				System.out.println("\nDepósito cancelado.\n\n");
				mensagemVoltarMenu();
			}
		} else {
			System.out.println("\nA conta selecionado não possui saldo.\n\n");
			mensagemVoltarMenu();
		}
	}

	public static void comprovanteDeposito(double _valorDeposito, int _contaDepositante, int _agenciaDepositante, String _nomeDepositante, int _contaFavorecido, int _agenciaFavorecido, String _nomeFavorecido){
		try {
			FileWriter dir = new FileWriter("comprovante.txt");
			PrintWriter arquivo = new PrintWriter(dir);
			
			arquivo.printf("Comprovante de Movimento Bancário");
			arquivo.printf("\n   Valor: R$ %6.2f", _valorDeposito);
			arquivo.printf("\n\nDados do depositante");
			arquivo.printf("\n   Conta: %d", _contaDepositante);
			arquivo.printf("\n Agência: %d", _agenciaDepositante);
			arquivo.printf("\n    Nome: %S", _nomeDepositante);
			arquivo.printf("\n\nDados do favorecido");
			arquivo.printf("\n   Conta: %d", _contaFavorecido);
			arquivo.printf("\n Agência: %d", _agenciaFavorecido);
			arquivo.printf("\n    Nome: %S \n", _nomeFavorecido);

			arquivo.close();
		} catch (Exception e) {
			System.out.println("Não foi possível salvar o comprovante de pagamento");
		}
	}

	public static void salvarSaldoBancario(int _indice){
		try {
			FileWriter dir = new FileWriter("comprovante.txt");
			PrintWriter arquivo = new PrintWriter(dir);
			
			arquivo.printf("**** Saldo Bancário ****");
			arquivo.printf("\n* Agência: " + numeroAgencia.get(_indice));
			arquivo.printf("\n*   Conta: " + conta.get(_indice));
			arquivo.printf("\n*    Nome: %S", nome.get(_indice));
			arquivo.printf("\n*   Saldo: R$ \n\n" + saldo.get(_indice));

			arquivo.close();
		} catch (Exception e) {
			System.out.println("Não foi possível salvar o saldo bancario");
		}
	}
	
	public static void main(String[] args) {
		nome.add("Paulo");
		endereco.add("Av Francisco");
		nomeGerente.add("Lucas");
		saldo.add(100.0);
		telefone.add("(28) 99968-6050");
		cpf.add("170.561.397.79");
		conta.add(1);
		numeroAgencia.add(231);

		nome.add("Henrique");
		endereco.add("Av Mardegan");
		nomeGerente.add("Edson");
		saldo.add(500.0);
		telefone.add("(28) 3515-3100");
		cpf.add("250.301.381.79");
		conta.add(2);
		numeroAgencia.add(104);

		int opcaoMenuInicial;
		int conta;
		do {
			clearScreen();
			opcaoMenuInicial = menuInicial();
			switch (opcaoMenuInicial) {
				case 1:
					clearScreen();
					cadastrarCliente();
					break;
				case 2:
					conta = 0;
					do {
						clearScreen();
						if (listarClientes()) {
							System.out.printf("Digite a conta (Digite 0 p/ voltar ao menu): ");
							conta = entrada.nextInt();
							if (conta != 0)  {
								if (validarContaExiste(conta) != -1) {
									mostrarDadosCliente(conta);	
								}
							System.out.println("Digite qualquer tecla para continuar...");
							entrada.next();
							}
						}
					} while (conta != 0);
					break;
				case 3:
					conta = 0;
					do {
						clearScreen();
						if (listarClientes()) {
							System.out.printf("Digite a conta (Digite 0 p/ voltar ao menu): ");
							conta = entrada.nextInt();
							if (conta != 0)  {
								if (validarContaExiste(conta) != -1) {
									mostrarSaldo(conta);	
								}
							System.out.println("Digite qualquer tecla para continuar...");
							entrada.next();
							}
						}
					} while (conta != 0);
					break;
				case 4:
					clearScreen();
					deposito("efetuar");
					break;
				case 5:
					clearScreen();
					deposito("receber");
					break;
				case 6:
					clearScreen();
					pagarBoleto();
					break;
				case 7:
					clearScreen();
					transferenciaEntreContas();
					break;
				case 8:
					System.out.println("Saindo do sistema");
					break;
				default:
					System.out.println("\nOpção inválida. Digite uma das opções do menu\n\n");
					mensagemVoltarMenu();
					break;
			}
		} while (opcaoMenuInicial != 8);
	}
}