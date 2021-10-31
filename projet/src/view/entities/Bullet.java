package view.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import Model.Map.Map;
import view.GameCamera;

public class Bullet extends Entity {

	BufferedImage[] m_images;
	int m_index_image;
	int m_elapsed_photo;
	
	public Bullet(Model.Weapon.Bullet e) {
		super ("src/view/resources/utils/bullet.png",e);
		int row = view.Constants.BULLET_SPRITE_ROW;
		int coulumn = view.Constants.BULLET_SPRITE_COULUMN;
		String r = this.getPath();
		m_images = loadSprite(r, row, coulumn);
		m_index_image = 0;
		int width=m_images[m_index_image].getWidth();
		int height=m_images[m_index_image].getHeight();
		((Model.Moveable.Moveable)this.getEntityModel()).initHitbox(width, height);
		
		
	}
	

	@Override
	public void paint(Graphics g, GameCamera m) {
		int width = m_images[m_index_image].getWidth();
		int height = m_images[m_index_image].getHeight();
		double scale = view.Constants.BULLET_SCALE;
		int x = (int) this.getEntityModel().getX();
		int y = (int) this.getEntityModel().getY();
		
		g.drawImage(m_images[m_index_image], x - (int) (m.getxOffsset()), y - (int) (m.getYOffset()),
				(int) (scale * width), (int) (scale * height), null);

		
		
	}

	@Override
	public void tick(long elapsed, Map map) throws Exception {
		
		m_elapsed_photo += elapsed;
		if (m_elapsed_photo > 50) {
			this.getEntityModel().tick(elapsed,map);
			m_elapsed_photo = 0;
			m_index_image ++;
			if (m_index_image==view.Constants.BULLET_SPRITE_NUMBER-1) {
				m_index_image--;
			}
			

		}
		this.getEntityModel().tick(elapsed, map);
		
		
	}
	public static BufferedImage[] loadSprite(String filename, int nrows, int ncols) {
		File imageFile = new File(filename);
		BufferedImage image = null;
		if (imageFile.exists()) {
			try {
				image = ImageIO.read(imageFile);
			} catch (Exception ex) {
				System.exit(-1);
			}
			;

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
	


}
