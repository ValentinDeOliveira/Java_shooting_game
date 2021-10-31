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
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Model.Constants_model;
import game.Game;
import graphics.info3.game.graphics.GameCanvas;
import info3.game.CanvasListener;
import info3.game.Sound;
import view.background.Background_painter;
import view.entities.Entity_Painter;

public class Game_graphics {

	static Game_graphics game;
	static int m_season;
	private long m_tickSeason;

	Game m_game;
	JFrame m_frame;
	JLabel m_text;
	GameCanvas m_canvas;
	CanvasListener m_listener;
	Sound m_music;
	Entity_Painter m_entity_painter;
	Background_painter m_background_painter;
	private GameCamera m_game_camera;
	private boolean m_game_over;

	public Game_graphics(Game g) throws Exception {
		// creating a cowboy, that would be a model
		// in an Model-View-Controller pattern (MVC)
		// m_cowboy = new Cowboy();
		// creating a listener for all the events
		// from the game canvas, that would be
		// the controller in the MVC pattern
		m_listener = new CanvasListener(this);
		// creating the game canvas to render the game,
		// that would be a part of the view in the MVC pattern
		m_canvas = new GameCanvas(m_listener);
		Constants_model constatns = new Constants_model();
		System.out.println("  - creating frame...");
		Dimension d = new Dimension(constatns.SCREEN_WIDTH, constatns.SCREEN_HEIGHT);
		m_frame = m_canvas.createFrame(d);

		System.out.println("  - setting up the frame...");

		m_background_painter = new Background_painter();
		this.m_game = g;

		setupFrame();
		m_game_camera = new GameCamera(m_canvas, 0, 0);
		m_entity_painter = new Entity_Painter(m_game_camera);
		m_background_painter = new Background_painter();
		m_game_over = false ;

	}

	/*
	 * Then it lays out the frame, with a border layout, adding a label to the north
	 * and the game canvas to the center.
	 */
	private void setupFrame() {

		m_frame.setTitle("Game");
		m_frame.setLayout(new BorderLayout());

		m_frame.add(m_canvas, BorderLayout.CENTER);

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

	public void loadMusic() {
		/*
		 * m_musicName = m_musicNames[m_musicIndex]; String filename =
		 * "src/view/resources/" + m_musicName + ".ogg"; m_musicIndex = (m_musicIndex +
		 * 1) % m_musicNames.length; try { RandomAccessFile file = new
		 * RandomAccessFile(filename, "r"); RandomFileInputStream fis = new
		 * RandomFileInputStream(file); m_canvas.playMusic(fis, 0, 1.0F); } catch
		 * (Throwable th) { th.printStacGame_graphicskTrace(System.err);
		 * System.exit(-1); }
		 */
	}

	private int m_musicIndex = 0;
	private String[] m_musicNames = new String[] { "Runaway-Food-Truck" };

	private long m_textElapsed;

	/*
	 * This method is invoked almost periodically, given the number of milli-seconds
	 * that elapsed since the last time this method was invoked.
	 */
	public void tick(long elapsed) throws Exception {
		// m_cowboy.tick(elapsed);
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

		m_tickSeason += elapsed;
		Boolean reset_season = this.m_game.getGameModel().getCurrentMap().getSeason().manageSeasonTime(m_tickSeason);
		if (reset_season) {
			m_tickSeason = 0;
		}
		if (m_entity_painter != null
				&& this.m_game.getGameModel() != null & this.m_game.getGameModel().getCurrentMap() != null
				&& !m_game_over)
			m_entity_painter.tick(this.m_game.getGameModel().getCurrentMap().getEntities(), elapsed,
					this.m_game.getGameModel().getCurrentMap());

		if (m_game_camera != null)
			m_game_camera.CenterCamera();

		if (this.m_game.getGameModel().getCurrentMap().getEntities().isEmpty()) {
			this.m_game_over = true;
		}

	}

	/*
	 * This request is to paint the Game Canvas, using the given graphics. This is
	 * called from the GameCanvasListener, called from the GameCanvas.
	 */
	public void paint(Graphics g) {

		// get the size of the canvas
		int width = m_canvas.getWidth();
		int height = m_canvas.getHeight();

		
		

		this.m_background_painter.paint(g, this.m_game.getGameModel().getCurrentMap().getSeason(), width, height);
		if (this.m_game_over) {
			this.m_background_painter.paint(g, this.m_game_over, width, height);
		}
		if (m_entity_painter != null && !m_game_over) {
			this.m_entity_painter.paint(g, this.m_game.getGameModel().getCurrentMap().getMud(),
					this.m_game.getGameModel().getCurrentMap().getSeason().getCurrentSeason());
			this.m_entity_painter.paint(g, this.m_game.getGameModel().getCurrentMap().getSeason().getCurrentSeason());
			
			
		}
		
		if (!m_game_over)
			m_game.getGameModel().getCurrentMap().getMinimap().paint(g);

		
		if (this.m_game.getGameModel() != null && this.m_game.getGameModel().getCurrentMap() != null&&!this.m_game_over) {
			m_game.getGameModel().getCurrentMap().getMinimap().paint(g);
			m_game.getGameModel().getCurrentMap().getM_level_manager().paintLevel(g, width, height);
		
		}

	}

	public void setGame(Game g) {
		this.m_game = g;
	}

	public GameCamera getGameCamera() {
		return this.m_game_camera;
	}

}