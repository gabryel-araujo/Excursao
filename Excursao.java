import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Excursao {
	private int codigo;
	private double preco;
	private int max;
	private ArrayList<String> reservas = new ArrayList<>();

	public Excursao(int codigo, double preco, int max) throws Exception {
		if (codigo <= 0 || preco <= 0 || max <= 0)
			throw new Exception("Os valores devem ser maiores que 0!");
		this.codigo = codigo;
		this.preco = preco;
		this.max = max;
	}

	public Excursao(int codigo)throws Exception {
		if(codigo <=0)
			throw new Exception("O valor deve ser maior que 0!");
		this.codigo = codigo;
		this.carregar();
	}

	public void criarReserva(String cpf, String nome) throws Exception {
		if (reservas.size() == max)
			throw new Exception("Reservas esgotadas!");
		for (int i = 0; i < reservas.size(); i++) {
			String[] s = reservas.get(i).split("/");
			if (s[1].equals(nome))
				throw new Exception("Nome já cadastrado na lista de reservas!");
		}
		reservas.add(cpf + '/' + nome);
	}

	public void cancelarReserva(String cpf, String nome) throws Exception {
		boolean encontrado = false;
		for (String n : reservas) {
			if (n.equals(cpf + '/' + nome)) {
				reservas.remove(n);
				encontrado = true;
				break;
			}
		}
		if (!encontrado)
			throw new Exception("Nome e/ou CPF não encontrados!");

	}

	public void cancelarReserva(String cpf) throws Exception {
		boolean encontrado = false;
		ArrayList<String> listaAux = new ArrayList<>();
		for (int i = 0; i < reservas.size(); i++) {
			String[] s = reservas.get(i).split("/");
			if (s[0].equals(cpf)) {
				encontrado = true;
				listaAux.add(reservas.get(i));
			}
		}
		if (!encontrado)
			throw new Exception("CPF não encontrado!");

		reservas.removeAll(listaAux);
	}

	public ArrayList<String> listarReservasPorCpf(String digitos) {
		if (digitos.equals(""))
			return reservas;
		ArrayList<String> reservasCPF = new ArrayList<>();
		for (String n : reservas) {
			if (n.contains(digitos))
				reservasCPF.add(n);
		}
		return reservasCPF;
	}

	public ArrayList<String> listarReservasPorNome(String texto) {
		if (texto.equals(""))
			return reservas;
		ArrayList<String> reservasNome = new ArrayList<>();
		for (String n : reservas) {
			if (n.contains(texto))
				reservasNome.add(n);
		}
		return reservasNome;
	}

	public double calcularValorTotal() {
		return reservas.size() * preco;
	}

	@Override
	public String toString() {
		return "Código da Excursão: " + codigo + " Preço: " + preco + " Max: " + max + " Total Reservas = "
				+ reservas.size();
	}

	public void carregar() throws Exception {
		try {
			File f = new File(new File(".\\" + codigo + ".txt").getCanonicalPath()); // pasta do projeto
			// System.out.println(f.getAbsolutePath()); //ver caminho do S.O.
			Scanner arquivo = new Scanner(f);
			String linha, reserva;
			String[] partes;
			preco = Double.parseDouble(arquivo.nextLine().substring(7)); // primeira linha de cabecalho é descartada
			max = Integer.parseInt(arquivo.nextLine().substring(5));
			arquivo.nextLine();
			arquivo.nextLine();
			while (arquivo.hasNextLine()) {
				linha = arquivo.nextLine(); // leitura de uma linha
//				partes = linha.split("/"); // separa os campos da linha
//				reserva = partes[0]; // PARAMOS AQUI
				reservas.add(linha); // coloca a reserva na lista de reservas principal.
			}
		} catch (FileNotFoundException e) {
			// criar arquivo vazio se o arquivo nao existir
			File f = new File(new File(".\\" + codigo + ".txt").getCanonicalPath()); // pasta do projeto
			FileWriter arquivo2 = new FileWriter(f, false); // append=false apaga todo conteudo
			arquivo2.close();
		}
	}

	public void gravar() throws Exception {
		// (re)gravar no arquivo codigo.txt, o preço,a quantidade máxima e as reservas.
		try {
			File f = new File(new File(".\\" + codigo + ".txt").getCanonicalPath()); // pasta do projeto
			FileWriter arquivo = new FileWriter(f, false); // append=false apaga o conteudo anterior
			arquivo.write("Preço: " + preco + "\n" + "Max: " + max + "\n" + "\n" + "Reservas:"  + "\n"); // grava
																										// primeira
																										// linha
																										// de cabecalho
			String reserva;
			for (int i = 0; i < reservas.size(); i++) {
					reserva = reservas.get(i);
					arquivo.write(reserva + "\n");
				}
			arquivo.close();
		} catch (IOException e) {
			throw new Exception("problema na gravacao do arquivo de saida");
		}
	}
}