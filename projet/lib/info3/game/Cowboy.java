package info3.game;
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

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Automaton.Aef;

/**
 * A simple class that holds the images of a sprite for an animated cowbow.
 *
 */
public class Cowboy {
	protected Aef m_automaton;

	public Aef getM_automaton() {
		return m_automaton;
	}

	public void setM_automaton(Aef m_automaton) {
		this.m_automaton = m_automaton;
	}

	public void setFixe(boolean fixe) {
		this.fixe = fixe;
	}

	public void setIcon_width(int icon_width) {
		this.icon_width = icon_width;
	}

	public void setIcon_height(int icon_height) {
		this.icon_height = icon_height;
	}

	BufferedImage[] m_images;
	int m_imageIndex;
	long m_imageElapsed;
	long m_moveElapsed;
	int m_x, m_y;
	int m_width;
	boolean fixe;
	int icon_width;
	int icon_height;
	CowboyListener m_listener;

	public Cowboy(boolean fixe) throws IOException {
		m_images = loadSprite("lib/resources/spritesheet.png", 2, 2);
		this.fixe = fixe;
		if (!fixe) {
			CowboyListener l = new CowboyListener(this);
			this.set_Listener(l);
		}
		icon_width = m_images[0].getWidth();
		icon_height = m_images[0].getHeight();
	}

	/*
	 * Simple animation here, the cowbow
	 */
	public void tick(long elapsed) {
		if (fixe) {
			m_imageElapsed += elapsed;
			if (m_imageElapsed > 200) {
				m_imageElapsed = 0;
				m_imageIndex = (m_imageIndex + 1) % m_images.length;

			}

//			if (m_moveElapsed > 24 & m_width != 0) {
//				m_moveElapsed = 0;
//				m_x = (m_x + 2) % m_width;
//			}
		} else {
			m_moveElapsed += elapsed;
		}

	}

	public void paint(Graphics g, int width, int height) {
		m_width = width;
		BufferedImage img = m_images[m_imageIndex];
		int scale = 1;

		g.drawImage(img, m_x, m_y, scale * img.getWidth(), scale * img.getHeight(), null);
	}

	public void set_fixe(boolean val) {
		this.fixe = val;

	}

	public boolean get_fixe() {
		return fixe;
	}

	public int get_x() {
		return m_x;
	}

	public int get_y() {
		return m_y;
	}

	public void set_x(int x) {
		this.m_x = x;
	}

	public void set_y(int y) {
		this.m_y = y;
	}

	public void move_up() {
		if (!fixe && m_moveElapsed > 24 & m_width != 0) {
			m_moveElapsed = 0;
			this.m_y = this.m_y - 1;

		}
	}

	public void move_down() {
		if (!fixe && m_moveElapsed > 24 & m_width != 0) {
			m_moveElapsed = 0;
			this.m_y = this.m_y + 2;
		}
	}

	public void move_right() {
		if (!fixe && m_moveElapsed > 24 & m_width != 0) {
			m_moveElapsed = 0;
			this.m_x = this.m_x + 2;
		}
	}

	public void move_left() {
		if (!fixe && m_moveElapsed > 24 & m_width != 0) {
			m_moveElapsed = 0;
			this.m_x = this.m_x - 2;
		}
	}

	public void set_Listener(CowboyListener l) {
		this.m_listener = l;
	}

	public boolean intersect(Cowboy a) {
		if ((a.get_x() >= this.m_x - (this.icon_width / 2)) && (a.get_x() <= this.m_x + (this.icon_width / 2))
				&& ((a.get_y() <= this.m_y + (this.icon_height / 2))
						&& (a.get_y() >= this.m_y - (this.icon_height / 2))))
			return true;

		return false;

	}

	public static BufferedImage[] loadSprite(String filename, int nrows, int ncols) throws IOException {
		File imageFile = new File(filename);
		if (imageFile.exists()) {
			BufferedImage image = ImageIO.read(imageFile);

			int width = image.getWidth(null) / ncols;
			int height = image.getHeight(null) / nrows;

			BufferedImage[] images = new BufferedImage[nrows * ncols];
			for (int i = 0; i < nrows; i++) {
				for (int j = 0; j < ncols; j++) {
					int x = j * width;
					int y = i * height;
					images[(i * ncols) + j] = image.getSubimage(x, y, width, height);
				}
			}
			return images;
		}
		return null;
	}

	public static class CowboyListener implements KeyListener {
		Cowboy m_cow_boy;

		public CowboyListener(Cowboy m_cow_boy) {
			this.m_cow_boy = m_cow_boy;

		}

		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("cowboy key pressed");
			int code = e.getKeyCode();
			switch (code) {
			case 38:
				m_cow_boy.move_up();
				break;
			case 40:
				m_cow_boy.move_down();
				break;
			case 39:
				m_cow_boy.move_right();
				break;
			case 37:
				m_cow_boy.move_left();
				break;

			}

		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}
	}
}
