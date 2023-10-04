import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.DropMode;

public class InterfaceExcursao {

	private JFrame frame;
	private JLabel label;
	private JButton button;
	private JButton button_1;
	private JLabel label_1;
	private JSeparator separator;
	private JLabel label_2;
	private JSeparator separator_2;
	private JButton button_3;
	private JButton button_4;
	private JButton button_5;
	private JSeparator separator_3;
	private JLabel label_3;
	private JButton button_2;
	private JTextArea textArea;
	private JLabel alertas;
	private JButton button_9;
	public Excursao excursao;
	private int codigo;
	private double preco;
	private int max;
	private JPanel panel;
	private JLabel v_total;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceExcursao window = new InterfaceExcursao();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InterfaceExcursao() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Excursão");
		frame.setBounds(100, 100, 640, 349);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		label = new JLabel("Sistema de Excursão");
		label.setBounds(160, 11, 261, 38);
		label.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 30));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(label);

		button = new JButton("Criar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					alertas.setText("");
					String codigoTemp = JOptionPane.showInputDialog("Insira o código da excursão");
					if (codigoTemp.equals(null))
						return;
					codigo = Integer.parseInt(codigoTemp);
					String precoTemp = JOptionPane.showInputDialog("Insira o preço dessa excursão");
					if (precoTemp.equals(null))
						return;
					preco = Double.parseDouble(precoTemp);
					String maxTemp = JOptionPane.showInputDialog("Digite a quantidade máxima de reservas ");
					if (maxTemp.equals(null))
						return;
					max = Integer.parseInt(maxTemp);
					excursao = new Excursao(codigo, preco, max);
					alertas.setForeground(new Color(0, 128, 64));
					alertas.setText("Excursão criada com sucesso!");
					ativarBotoes();
					String novoValor = excursao.calcularValorTotal() + "";
					v_total.setText("R$" + novoValor);
					listaGeral();

				} catch (NullPointerException NPE) {
					;

				} catch (NumberFormatException NFE) {
					alertas.setForeground(new Color(230, 0, 0));
					alertas.setText("Digite um valor válido!");

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}

			}
		});
		button.setBounds(10, 102, 150, 23);
		frame.getContentPane().add(button);

		button_1 = new JButton("Recuperar");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					alertas.setText("");
					textArea.setText("");
					String codigoTemp = JOptionPane.showInputDialog("Digite o código");
					if (codigoTemp.equals(null))
						return;
					codigo = Integer.parseInt(codigoTemp);
					excursao = new Excursao(codigo);
					alertas.setForeground(new Color(0, 128, 64));
					alertas.setText("Excursão carregada com sucesso!");
					excursao.carregar();
					ativarBotoes();
					String novoValor = excursao.calcularValorTotal() + "";
					v_total.setText(novoValor);
					listaGeral();

				} catch (NullPointerException NPE) {
					;
				} catch (NumberFormatException NFE) {
					alertas.setForeground(new Color(230, 0, 0));
					alertas.setText("Digite um valor válido!");
				} catch (NoSuchElementException NSEE) {
					alertas.setForeground(new Color(230, 0, 0));
					alertas.setText("Não foi encontrada excursão com o código " + codigo);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		button_1.setBounds(10, 127, 150, 23);
		frame.getContentPane().add(button_1);

		label_1 = new JLabel("Gerenciar Excursão");
		label_1.setBounds(30, 77, 103, 23);
		label_1.setFont(new Font("Arial Narrow", Font.BOLD, 13));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(label_1);

		separator = new JSeparator();
		separator.setBounds(10, 98, 150, 2);
		frame.getContentPane().add(separator);

		label_2 = new JLabel("Gerenciar Reservas");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("Arial Narrow", Font.BOLD, 13));
		label_2.setBounds(30, 152, 103, 23);
		frame.getContentPane().add(label_2);

		separator_2 = new JSeparator();
		separator_2.setBounds(10, 173, 150, 2);
		frame.getContentPane().add(separator_2);

		button_3 = new JButton("Cancelar Grupo");
		button_3.setEnabled(false);
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alertas.setText("");
				listaGeral();
				String cpf = JOptionPane.showInputDialog("Insira o cpf: ");
				try {
					excursao.cancelarReserva(cpf);
					alertas.setForeground(new Color(0, 128, 64));
					alertas.setText("Reserva(s) cancelada(s) com sucesso!");
					excursao.gravar();
					String novoValor = excursao.calcularValorTotal() + "";
					v_total.setText("R$" + novoValor);
					listaGeral();
				} catch (Exception ex) {
					if (cpf == null)
						return;
					JOptionPane.showMessageDialog(null, ex.getMessage());

				}

			}
		});
		button_3.setBounds(10, 229, 150, 23);
		frame.getContentPane().add(button_3);

		button_4 = new JButton("Listar por CPF");
		button_4.setEnabled(false);
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					alertas.setText("");
					textArea.setText("");
					String cpf = JOptionPane.showInputDialog("Insira o cpf: ");
					ArrayList<String> listaCPF = excursao.listarReservasPorCpf(cpf);
					if (listaCPF.size() == 0) {
						alertas.setForeground(new Color(230, 0, 0));
						alertas.setText("Sem reservas para esse cpf");
					} else {
						for (String s : listaCPF) {
							textArea.append(s + "\n");
						}
					}
				} catch (NullPointerException NPE) {
					;
				}
			}
		});
		button_4.setBounds(10, 254, 150, 23);
		frame.getContentPane().add(button_4);

		button_5 = new JButton("Listar por nome");
		button_5.setEnabled(false);
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					alertas.setText("");
					textArea.setText("");
					String nome = JOptionPane.showInputDialog("Insira o nome: ").toUpperCase();
					ArrayList<String> listaNome = excursao.listarReservasPorNome(nome);
					if (listaNome.size() == 0) {
						alertas.setForeground(new Color(230, 0, 0));
						alertas.setText("Sem reservas para esse nome");
					} else {
						for (String s : listaNome)
							textArea.append(s + "\n");
					}
				} catch (NullPointerException NPE) {
					;
				}
			}
		});
		button_5.setBounds(10, 279, 150, 23);
		frame.getContentPane().add(button_5);

		separator_3 = new JSeparator();
		separator_3.setBounds(451, 173, 150, 2);
		frame.getContentPane().add(separator_3);

		label_3 = new JLabel("Valor Total");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("Arial Narrow", Font.BOLD, 13));
		label_3.setBounds(472, 152, 103, 23);
		frame.getContentPane().add(label_3);

		button_2 = new JButton("Cancelar Individual");
		button_2.setEnabled(false);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alertas.setText("");
				listaGeral();

				try {
					String nome = JOptionPane.showInputDialog("Insira o nome: ").toUpperCase();
					String cpf = JOptionPane.showInputDialog("Insira o cpf: ");
					excursao.cancelarReserva(cpf, nome);
					alertas.setForeground(new Color(0, 128, 64));
					alertas.setText("Reserva cancelada com sucesso!");
					excursao.gravar();
					String novoValor = excursao.calcularValorTotal() + "";
					v_total.setText("R$" + novoValor);
					listaGeral();
				} catch (NullPointerException NPE) {
					;
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());

				}

			}
		});
		button_2.setBounds(10, 203, 150, 23);
		frame.getContentPane().add(button_2);

		alertas = new JLabel("");
		alertas.setHorizontalAlignment(SwingConstants.CENTER);
		alertas.setFont(new Font("Arial", Font.BOLD, 13));
		alertas.setForeground(new Color(255, 0, 0));
		alertas.setBounds(143, 80, 332, 14);
		frame.getContentPane().add(alertas);

		button_9 = new JButton("Criar Reserva");
		button_9.setEnabled(false);
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alertas.setText("");
				listaGeral();
				try {
					String nome = JOptionPane.showInputDialog("Insira o nome para reserva: ").toUpperCase();
					String cpf = validarCPF(JOptionPane.showInputDialog("Insira o cpf: ")); // A variável cpf recebe o
																							// valor já formatado ou
																							// recebe "" caso possua o
																							// formato inválido.
					if (!validarNome(nome)) {
						alertas.setForeground(new Color(230, 0, 0));
						alertas.setText("Digite um nome válido! (apenas letras)");
						return;
					}
					if (cpf.equals("")) {
						alertas.setForeground(new Color(230, 0, 0));
						alertas.setText("Digite um CPF válido! (11 dígitos)");
						return;
					}
					excursao.criarReserva(cpf, nome);
					alertas.setForeground(new Color(0, 128, 64));
					alertas.setText("Reserva criada com sucesso!");
					excursao.gravar();
					String novoValor = excursao.calcularValorTotal() + "";
					v_total.setText("R$" + novoValor);
					listaGeral();
				} catch (NullPointerException NPE) {
					;
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}

			}

		});
		button_9.setBounds(10, 176, 150, 23);
		frame.getContentPane().add(button_9);

		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBounds(470, 191, 103, 50);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		v_total = new JLabel("");
		v_total.setFont(new Font("Arial Narrow", Font.BOLD, 20));
		v_total.setHorizontalAlignment(SwingConstants.CENTER);
		v_total.setBounds(0, 0, 103, 50);
		panel.add(v_total);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(170, 101, 271, 196);
		frame.getContentPane().add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
	}

	private void listaGeral() {
		textArea.setText("");
		ArrayList<String> listaNome = excursao.listarReservasPorNome("");
		for (String s : listaNome)
			textArea.append(s + "\n");
	}

	private void ativarBotoes() {
		button_2.setEnabled(true);
		button_3.setEnabled(true);
		button_4.setEnabled(true);
		button_5.setEnabled(true);
		button_9.setEnabled(true);
	}

	private boolean validarNome(String nome) {
		if (Pattern.matches("^[[A-Z]+?\s]+$", nome)) {
			return true;
		}
		return false;
	}

	private String validarCPF(String cpf) { // Função utilizada para validar o campo do CPF e formatar (removendo os '.'
											// // e '-' caso o usuário coloque).

		if (Pattern.matches("^[0-9]{11}|[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}$", cpf)) {
			if (cpf.contains(".")) {
				String parte1 = cpf.substring(0, 3);
				String parte2 = cpf.substring(4, 7);
				String parte3 = cpf.substring(8, 11);
				String parte4 = cpf.substring(12, 14);
				String cpfTratado = parte1 + parte2 + parte3 + parte4;

				return cpfTratado;
			}
			return cpf;

		}
		return "";// Retorna uma string vazia para indicar que o valor é inválido.

	}
}