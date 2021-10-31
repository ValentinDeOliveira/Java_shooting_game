package view.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import Model.Map.Map;
import view.Constants;
import view.GameCamera;

public class Ennemy extends Entity {
	static BufferedImage[] m_images;
	int m_index_image;
	int m_elapsed_photo;

	public Ennemy(Model.Moveable.Ennemy e) {
		super("src/view/resources/Entity/ennemy/", e);
		
		float f = e.getOrientation();
		this.m_index_image = (int) f;
		if (((Model.Moveable.Moveable) this.getEntityModel()).getCurrentHitbox() == null) {
			int width = m_images[m_index_image].getWidth();
			int height = m_images[m_index_image].getHeight();
			((Model.Moveable.Moveable) this.getEntityModel()).initHitbox(width, height);
		}

		

	}

	@Override
	public void paint(Graphics g, GameCamera m) {

		int width = m_images[m_index_image].getWidth();
		int height = m_images[m_index_image].getHeight();
		double scale = 0.5;
		int x = (int) this.getEntityModel().getX();
		int y = (int) this.getEntityModel().getY();
		g.drawImage(m_images[m_index_image], x - (int) (m.getxOffsset()), y - (int) (m.getYOffset()),
				(int) (scale * width), (int) (scale * height), null);

	}

	@Override
	public void tick(long elapsed, Map map) throws Exception {
		m_elapsed_photo += elapsed;

		// m_elapsed_photo=0;
		float f = this.getEntityModel().getOrientation();
		this.m_index_image = (int) f;
		this.getEntityModel().tick(elapsed, map);

	}

	public static void loadImageEnnemy() {
		m_images = new BufferedImage[Constants.ENNEMY_NUMBER_OF_POSITIONS];
		String r = "src/view/resources/Entity/ennemy/";
		String x = r + "moveDown.png";
		File imageFile = new File(x);

		if (imageFile.exists()) {
			BufferedImage image = null;
			try {
				image = ImageIO.read(imageFile);
				m_images[Constants.MOVE_DOWN_ENNEMY] = image;
			} catch (Exception ex) {
			}

		}
		x = r + "moveLeft.png";
		imageFile = new File(x);

		if (imageFile.exists()) {
			BufferedImage image = null;
			try {
				image = ImageIO.read(imageFile);
				m_images[Constants.MOVE_LEFT_ENNEMY] = image;
			} catch (Exception ex) {
			}

		}
		x = r + "moveRight.png";
		imageFile = new File(x);

		if (imageFile.exists()) {
			BufferedImage image = null;
			try {
				image = ImageIO.read(imageFile);
				m_images[Constants.MOVE_RIGHT_ENNEMY] = image;
			} catch (Exception ex) {
			}

		}
		x = r + "moveUp.png";
		imageFile = new File(x);

		if (imageFile.exists()) {
			BufferedImage image = null;
			try {
				image = ImageIO.read(imageFile);
				m_images[Constants.MOVE_UP_ENNEMY] = image;
			} catch (Exception ex) {
			}

		}

	}

}