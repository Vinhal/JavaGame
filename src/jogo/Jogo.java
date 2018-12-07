package jogo;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;

public class Jogo extends JFrame {

	/**
	 * Serial gerado automaticamente apenas para efeito de serialização
	 */
	private static final long serialVersionUID = 1L;

	public Jogo() {
		construirMenuBar();
		construirFase();
		configurarTela();
	}

	public static void main(String[] args) {
		new Jogo();
	}

	private JMenuBar construirMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		menuBar.setBorder(new LineBorder(Color.red));
		JMenu menu = new JMenu("Opções");

		JMenuItem sair = new JMenuItem("Sair");
		sair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});

		menu.add(sair);
		menuBar.add(menu);
		setJMenuBar(menuBar);

		return menuBar;
	}

	private JPanel construirFase() {
		Fase fase = new Fase();
		add(fase);
		return fase;
	}

	private void configurarTela() {
		setSize(500, 420);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setTitle("AlienTrix");
	}
}

