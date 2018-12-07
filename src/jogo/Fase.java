package jogo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Fase extends JPanel implements ActionListener {

	private static final long serialVersionUID = -5079021684583630134L;

	private Image fundo;

	private int contador;

	private int record;

	private Nave nave;

	private Timer timer;

	private List<Inimigo> inimigos;

	private boolean emJogo;

	private String mode = "";

	private int[][] coordenadas = { { 2380, 290 }, { 2600, 590 }, { 1380, 500 },
			{ 1780, 209 }, { 1580, 139 }, { 1880, 239 }, { 1790, 259 },
			{ 1760, 300 }, { 1790, 500 }, { 1980, 210 }, { 1560, 450 }, { 1510, 450 },
			{ 1930, 159 }, { 1590, 156 }, { 1530, 60 }, { 1340, 590 }, { 1290, 300 },
			{ 1920, 200 }, { 1900, 259 }, { 1660, 425 } };

	public Fase() {
		setFocusable(true);
		setDoubleBuffered(true);
		addKeyListener(new TecladoAdapter());

		ImageIcon referencia = new ImageIcon(Jogo.class.getResource("imgs/background.png"));
		fundo = referencia.getImage();
		nave = new Nave();

		emJogo = true;

		inicializarInimigos();

		timer = new Timer(5, this);
		timer.start();
	}

	public void inicializarInimigos() {
		inimigos = new ArrayList<>();

		for (int i = 0; i < coordenadas.length; i++) {
			inimigos.add(new Inimigo(coordenadas[i][0], coordenadas[i][1]));
		}
	}

	public void paint(Graphics g) {
		Graphics2D graficos = (Graphics2D) g;
		graficos.drawImage(fundo, 0, 0, null);

		if (emJogo) {
			graficos.drawImage(nave.getImagem(), nave.getX(), nave.getY(), this);

			List<Missel> misseis = nave.getMisseis();

			for (int i = 0; i < misseis.size(); i++) {
				Missel m = (Missel) misseis.get(i);
				graficos.drawImage(m.getImagem(), m.getX(), m.getY(), this);
			}

			for (int i = 0; i < inimigos.size(); i++) {
				Inimigo in = inimigos.get(i);
				graficos.drawImage(in.getImagem(), in.getX(), in.getY(), this);
			}

			if(inimigos.size() < 10){
				inicializarInimigos();
			}

			graficos.setColor(Color.white);
			graficos.drawString("Pontuação: " + contador , 5, 15);
			graficos.setColor(Color.yellow);
			graficos.drawString("Record: " + record , 200, 15);
			graficos.setColor(Color.red);
			graficos.drawString(mode, 320, 15);
		} else {
			ImageIcon fimJogo = new ImageIcon(Jogo.class.getResource("imgs/game_over.jpg"));

			if (contador > record){
				record = contador;
			}
			contador = 0;
			mode = "";

			graficos.drawImage(fimJogo.getImage(), 0, 0, null);
		}
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<Missel> misseis = nave.getMisseis();

		for (int i = 0; i < misseis.size(); i++) {
			Missel m = (Missel) misseis.get(i);

			if (m.isVisible()) {
				m.mexer();
			} else {
				misseis.remove(i);
			}
		}

		for (int i = 0; i < inimigos.size(); i++) {
			Inimigo in = inimigos.get(i);

			if (in.isVisible()) {
				in.mexer();
			} else {
				inimigos.remove(i);
			}
		}

		if (contador > 10) {
			nave.setEmModoEspecial(true);
			mode = "MODO ESPECIAL";
		}

		nave.mexer();
		checarColisoes();
		repaint();
	}

	public void checarColisoes() {
		Rectangle formaNave = nave.getBounds();
		Rectangle formaInimigo;
		Rectangle formaMissel;

		for (int i = 0; i < inimigos.size(); i++) {

			Inimigo tempInimigo = inimigos.get(i);
			formaInimigo = tempInimigo.getBounds();

			if (formaNave.intersects(formaInimigo)) {
				nave.setVisivel(false);
				tempInimigo.setVisible(false);
				emJogo = false;
			}
		}

		List<Missel> misseis = nave.getMisseis();

		for (int i = 0; i < misseis.size(); i++) {
			Missel tempMissel = misseis.get(i);
			formaMissel = tempMissel.getBounds();

			for (int j = 0; j < inimigos.size(); j++) {
				Inimigo tempInimigo = inimigos.get(j);
				formaInimigo = tempInimigo.getBounds();

				if (formaMissel.intersects(formaInimigo)) {
					contador++;
					tempInimigo.setVisible(false);
					tempMissel.setVisible(false);
				}
			}
		}
	}

	private class TecladoAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				emJogo = true;
				nave = new Nave();
				inicializarInimigos();
			}

			nave.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			nave.keyReleased(e);
		}
	}
}
