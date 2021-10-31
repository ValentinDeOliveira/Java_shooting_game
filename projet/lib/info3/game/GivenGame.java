/*
 * Copyright (C) 2020  Pr. Olivier Gruber
 * Educational software for a basic game development
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  Created on: March, 2020
 *      Author: Pr. Olivier Gruber
 */

package info3.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Automaton.Aef;
import Automaton.Direction_a;
import Automaton.action.Move_a;
import Automaton.condtion.True_a;
import Model.Map.Game_model;
import Model.Moveable.Player;
import audio.info3.game.sound.RandomFileInputStream;
import graphics.info3.game.graphics.GameCanvas;

public class GivenGame {

	static GivenGame game;
	LinkedList<Cowboy> liste_cowboy;
	Cowboy hero;
	int saison;
	int n_tick_saison;

	public static void main(String args[]) throws Exception {
		try {
			System.out.println("Game starting...");
			game = new GivenGame();
			System.out.println("Game started.");
		} catch (Throwable th) {
			th.printStackTrace(System.err);
		}
	}

	JFrame m_frame;
	JLabel m_text;
	GameCanvas m_canvas;
	CanvasListener m_listener;
	Game_model m_game;
	Sound m_music;
	Player m_player;

	GivenGame() throws Exception {
		// creating a cowboy, that would be a model
		// in an Model-View-Controller pattern (MVC)
		hero = new Cowboy(false);

		hero.set_x(500);
		hero.set_y(500);

		Cowboy b = new Cowboy(false);
		Cowboy c = new Cowboy(false);
		Cowboy e = new Cowboy(false);
		b.set_x(1000);
		b.set_y(10);
		c.set_x(-50);
		c.set_y(10);
		e.set_x(230);
		e.set_y(300);

		liste_cowboy = new LinkedList<Cowboy>();
		liste_cowboy.add(b);
		liste_cowboy.add(c);
		saison = 1;

		/*
		 * m_game = new Game_model(null, 5); //liste_cowboy.add(e);
		 * 
		 * // m_cowboy = new Cowboy(); // creating a listener for all the events // from
		 * the game canvas, that would be // the controller in the MVC pattern
		 * m_listener = new CanvasListener(this); // creating the game canvas to render
		 * the game, // that would be a part of the view in the MVC pattern m_canvas =
		 * new GameCanvas(m_listener);
		 * 
		 * m_player = new Player(200, 200, a, "lib/resources/spritesheet2.png", null);
		 */
		System.out.println("  - creating frame...");
		Dimension d = new Dimension(1920, 1080);
		m_frame = m_canvas.createFrame(d);

		m_frame.setResizable(true);
		System.out.println("  - setting up the frame...");
		setupFrame();
	}

	/*
	 * Then it lays out the frame, with a border layout, adding a label to the north
	 * and the game canvas to the center.
	 */
	private void setupFrame() {

		m_frame.setTitle("Game");
		m_frame.setLayout(new BorderLayout());
		m_frame.add(m_canvas, BorderLayout.CENTER);
		m_frame.setResizable(false);
		m_text = new JLabel();
		m_text.setText("Tick: 0ms FPS=0");
		m_frame.add(m_text, BorderLayout.NORTH);

		// center the window on the screen
		m_frame.setLocationRelativeTo(null);
		// make the vindow visible
		m_frame.setVisible(true);
	}

	/*
	 * ================================================================ All the
	 * methods below are invoked from the GameCanvas listener, once the window is
	 * visible on the screen.
	 * ==============================================================
	 */

	/*
	 * Called from the GameCanvas listener when the frame
	 */
	String m_musicName;

	void loadMusic() {
		m_musicName = m_musicNames[m_musicIndex];
		String filename = "lib/resources/" + m_musicName + ".ogg";
		m_musicIndex = (m_musicIndex + 1) % m_musicNames.length;
		try {
			RandomAccessFile file = new RandomAccessFile(filename, "r");
			RandomFileInputStream fis = new RandomFileInputStream(file);
			m_canvas.playMusic(fis, 0, 1.0F);
		} catch (Throwable th) {
			th.printStackTrace(System.err);
			System.exit(-1);
		}
	}

	private int m_musicIndex = 0;
	private String[] m_musicNames = new String[] { "Runaway-Food-Truck" };

	private long m_textElapsed;

	/*
	 * This method is invoked almost periodically, given the number of milli-seconds
	 * that elapsed since the last time this method was invoked.
	 */
	void tick(long elapsed) throws Exception {
		hero.tick(elapsed);
		// hero.getM_automaton().step(hero);
		n_tick_saison += elapsed;
		if (n_tick_saison > 3000) {
			n_tick_saison = 0;
			if (saison == 1) {
				saison = 2;
			} else {
				saison = 1;
			}
		}

		for (Iterator<Cowboy> i = liste_cowboy.iterator(); i.hasNext();) {
			(i.next()).tick(elapsed);
		}

		// Update every second
		// the text on top of the frame: tick and fps
		m_textElapsed += elapsed;
		if (m_textElapsed > 1000) {
			m_textElapsed = 0;
			float period = m_canvas.getTickPeriod();
			int fps = m_canvas.getFPS();

			String txt = "Tick=" + period + "ms";
			while (txt.length() < 15)
				txt += " ";
			txt = txt + fps + " fps   ";
			m_text.setText(txt);
		}
	}

	/*
	 * This request is to paint the Game Canvas, using the given graphics. This is
	 * called from the GameCanvasListener, called from the GameCanvas.
	 */
	void paint(Graphics g) {

		// get the size of the canvas
		int width = m_canvas.getWidth();
		int height = m_canvas.getHeight();

		// erase background
		String photo = "lib/resources/";
		if (saison == 1) {
			photo += "herbe.png";
		} else {
			g.setColor(Color.white);
			g.fillRect(0, 0, width, height);
			;
			photo += "hiver.png";
		}
		Image img = Toolkit.getDefaultToolkit().getImage(photo);

		g.drawImage(img, 0, 0, m_frame.getWidth(), m_frame.getHeight(), null);
		/*
		 * img = Toolkit.getDefaultToolkit().getImage("resources/fleur.jpeg");
		 * g.drawImage(img, 0, 0, img.getWidth(null), img.getHeight(null), null);
		 */
		// paint
		// m_game.getM_currentMap().paint(g);
		hero.paint(g, width, height);
		/*
		 * for (Iterator<Cowboy> i = liste_cowboy.iterator(); i.hasNext();) { ((Cowboy)
		 * (i.next())).paint(g, width, height); }
		 */
		// m_player.paint(g);

	}

	public boolean detecte_collision(Cowboy a) {
		for (Iterator<Cowboy> i = liste_cowboy.iterator(); i.hasNext();) {
			Cowboy tmp = ((i.next()));
			if (tmp.intersect(a)) {

				liste_cowboy.remove(tmp);
				System.out.println("collision detected");
				return true;

			}
		}
		System.out.println("collision not detected");
		return false;

	}

}
