
package Model.Map;

import java.awt.Graphics;
import java.util.LinkedList;

import Model.Constants_model;
import Model.Constants_model.TextureTypes;
import Model.Entity;
import Model.LevelManager;
import Model.Moveable.Ennemy;
import Model.Moveable.Player;
import utils.ArrayInteractor;
import view.GameCamera;

public class Map {
	// List of entities on the map
	private LinkedList<Entity> m_entities;
	// current season (spring or winter)

	private LinkedList<Chunk> m_mud;
	// percentage of ennemies per chunks
	private double m_ennemyPercentage;

	private Chunk[][] m_chunks;

	private int m_chunksPerLine;

	private int m_chunksPerCol;

	private Minimap m_minimap;

	private Constants_model m_constants;
	// chunk where the player is
	private Chunk m_currentChunk;

	private int[] m_indexChunk;
	private Season m_season;

	private EntityGenerator m_entityGenerator;

	private boolean m_dirtyFlag;
	private LevelManager m_level_manager;

	public Map(double m_ennemyPercentage) throws Exception {
		this.m_ennemyPercentage = m_ennemyPercentage;
		m_entities = new LinkedList<Entity>();
		m_mud = new LinkedList<Chunk>();
		m_level_manager = new LevelManager();
		m_entityGenerator = new EntityGenerator(m_level_manager);
		m_constants = new Constants_model();
		m_indexChunk = new int[2];

		// position in the top left corner of the invisible map
		int absolutePositionX = (int) (0 - (m_constants.SCREEN_WIDTH * m_constants.INVISIBLE_RATIO));
		int absolutePositionY = (int) (0 - (m_constants.SCREEN_HEIGHT * m_constants.INVISIBLE_RATIO));

		Chunk c = new Chunk(absolutePositionX, absolutePositionY, m_constants.CHUNK_WIDTH, m_constants.CHUNK_HEIGHT,
				m_entityGenerator.generateTexture());

		m_chunksPerLine = (int) (((m_constants.SCREEN_WIDTH)
				+ (m_constants.SCREEN_WIDTH * m_constants.INVISIBLE_RATIO) * 2) / m_constants.CHUNK_WIDTH) + 1;
		m_chunksPerCol = (int) (((m_constants.SCREEN_HEIGHT)
				+ (m_constants.SCREEN_HEIGHT * m_constants.INVISIBLE_RATIO) * 2) / m_constants.CHUNK_HEIGHT) + 1;

		m_season = new Season(m_level_manager);
		m_chunks = new Chunk[m_chunksPerLine][m_chunksPerCol];
		m_chunks[0][0] = c;
		generateMap();
		Player player = Player.getInstance();

		m_entities.add(player);
		m_entities.add(player.getDog());
		m_minimap = new Minimap(this);
		m_dirtyFlag = true;

	}

	public void setEnnemyPercentage(double m_ennemyPercentage) {
		this.m_ennemyPercentage = m_ennemyPercentage;
	}

	/*
	 * Generate a random map following m_ennemyPercentage and m_season It will fill
	 * the m_entities collection
	 */
	private void generateMap() throws Exception {
		Chunk absoluteChunk = m_chunks[0][0];
		Constants_model constants = new Constants_model();
		int chunkWidth = constants.CHUNK_WIDTH;
		int chunkHeight = constants.CHUNK_HEIGHT;

		Player player = Player.getInstance();
		boolean status;

		for (int i = 0; i < m_chunksPerLine; i++) {
			for (int j = 0; j < m_chunksPerCol; j++) {
				Texture texture = m_entityGenerator.generateTexture();
				Chunk c = new Chunk(absoluteChunk.getX() + chunkWidth * i, absoluteChunk.getY() + chunkHeight * j,
						chunkWidth, chunkHeight, texture);
				if (texture.getType().equals(TextureTypes.MUD)) {
					m_mud.add(c);
				}

				int[] coords = player.getCenterCoordinates();

				if (c.isInChunk(coords[0], coords[1], m_constants.CHUNK_WIDTH, m_constants.CHUNK_HEIGHT)) {
					m_currentChunk = c;
					m_indexChunk[0] = i;
					m_indexChunk[1] = j;
				}
				m_chunks[i][j] = c;
			}
		}
		for (int i = 0; i < m_chunksPerLine; i++) {
			for (int j = 0; j < m_chunksPerCol; j++) {
				Chunk c = m_chunks[i][j];
				status = m_entityGenerator.generateEntityToChunk(c);
				addEntityToList(status, c);
			}
		}

		changeSpeed(m_currentChunk);
	}

	

	public LinkedList<Entity> getEntities() {
		return m_entities;
	}

	public Minimap getMinimap() {
		return m_minimap;
	}

	/**
	 * @return the top left point on the invisible part following the position of
	 *         the player
	 */
	public int[] getAbsoluteCoordinates() {
		Player player = Player.getInstance();

		int[] result = new int[2];
		result[0] = (int) (player.getX()
				- (m_constants.SCREEN_WIDTH / 2 + (m_constants.SCREEN_WIDTH * m_constants.INVISIBLE_RATIO)));
		result[1] = (int) (player.getY()
				- (m_constants.SCREEN_HEIGHT / 2 + m_constants.SCREEN_HEIGHT * m_constants.INVISIBLE_RATIO));
		return result;
	}

	/**
	 * @return the top left point on the screen following the position of the player
	 */
	public int[] getAbsoluteCoordsVisible() {
		Player player = Player.getInstance();

		// We suppose that the player is at the center of the screen
		int[] result = new int[2];
		result[0] = (int) (player.getX() - m_constants.SCREEN_WIDTH / 2);
		result[1] = (int) (player.getY() - m_constants.SCREEN_HEIGHT / 2);

		return result;
	}

	/**
	 * Test if the user changed of chunk If the user changed of chunk, we will
	 * regenerate new chunks
	 */
	public void checkIfCurrentChunkChanged() throws Exception {
		if (m_currentChunk != null) {
			Player player = Player.getInstance();
			int[] coords = player.getCenterCoordinates();

			if (m_currentChunk.isInChunk(coords[0], coords[1], m_constants.CHUNK_WIDTH, m_constants.CHUNK_HEIGHT)) {
				return;
			}
			int minI = m_indexChunk[0] - 1;
			int minJ = m_indexChunk[1] - 1;

			// else, player changed of chunk
			Chunk c;
			for (int i = minI; i <= m_indexChunk[0] + 1; i++) {
				for (int j = minJ; j <= m_indexChunk[1] + 1; j++) {
					c = m_chunks[i][j];

					if (c.isInChunk(coords[0], coords[1], m_constants.CHUNK_WIDTH, m_constants.CHUNK_HEIGHT)) {

						// we change current chunk
						m_currentChunk = m_chunks[i][j];
						changeSpeed(c);
						regenerateChunks(i, j);
						updateListeEntites();

						return;
					}
				}
			}

		}
	}

	public void updateListeEntites() {
		for (int i = 0; i < m_chunks.length; i++) {
			for (int j = 0; j < m_chunks[i].length; j++) {
				Entity e = m_chunks[i][j].getEntity();
				if (e != null && !m_entities.contains(e)) {
					m_entities.add(e);
					this.m_dirtyFlag = true;
				}
			}
		}
	}

	private void changeSpeed(Chunk c) {
		Player.getInstance().setSpeed(c.getTexture().getSpeed());
	}

	/**
	 * Regenerate chunk in function of the chunk where the user moved
	 * 
	 * @param newIndexX x position of the new chunk (where the user moved)
	 * @param newIndexY y position of the new chunk (where the user moved)
	 * @throws Exception
	 */
	private void regenerateChunks(int newIndexX, int newIndexY) throws Exception {
		int indexX = m_indexChunk[0], indexY = m_indexChunk[1];
		ArrayInteractor ai = new ArrayInteractor();

		if (newIndexX < indexX) {
			m_mud = ai.removeAllColMud(m_chunksPerLine - 1, m_chunks, m_mud);

			m_chunks = ai.shiftLastRow(m_chunks, m_entities, m_mud);
			Chunk chunk = m_chunks[1][0];
			generateColChunks(0, chunk.getX() - m_constants.CHUNK_WIDTH, chunk.getY());
		} else if (newIndexX > indexX) {
			m_mud = ai.removeAllColMud(0, m_chunks, m_mud);

			m_chunks = ai.shiftFirstRow(m_chunks, m_entities, m_mud);
			Chunk chunk = m_chunks[m_chunksPerLine - 2][0];
			generateColChunks(m_chunksPerLine - 1, chunk.getX() + m_constants.CHUNK_WIDTH, chunk.getY());
		}
		if (newIndexY < indexY) {
			m_mud = ai.removeAllRowMud(m_chunksPerCol - 1, m_chunks, m_mud);

			m_chunks = ai.shiftLastCol(m_chunks, m_entities, m_mud);
			Chunk chunk = m_chunks[0][1];
			generateRowChunks(0, chunk.getX(), chunk.getY() - m_constants.CHUNK_HEIGHT);
		} else if (newIndexY > indexY) {
			m_mud = ai.removeAllRowMud(0, m_chunks, m_mud);

			m_chunks = ai.shiftFirstCol(m_chunks, m_entities, m_mud);
			Chunk chunk = m_chunks[0][m_chunksPerCol - 2];
			generateRowChunks(m_chunksPerCol - 1, chunk.getX(), chunk.getY() + m_constants.CHUNK_HEIGHT);
		}

	}

	/**
	 * Generate a new row of chunks at the given line
	 * 
	 * @param line        line to insert new Chunks
	 * @param generateToX X position of new Chunks
	 * @param generateToY Y position of new Chunks
	 * @throws Exception
	 */
	private void generateRowChunks(int line, int generateToX, int generateToY) throws Exception {
		int chunkWidth = m_constants.CHUNK_WIDTH;
		int chunkHeight = m_constants.CHUNK_HEIGHT;

		for (int i = 0; i < m_chunksPerLine; i++) {
			Texture texture = m_entityGenerator.generateTexture();
			Chunk c = new Chunk(generateToX + chunkWidth * i, generateToY, chunkWidth, chunkHeight, texture);

			if (texture.getType().equals(TextureTypes.MUD)) {
				m_mud.add(c);
			}

			m_chunks[i][line] = c;
		}

		boolean status = false;
		for (int i = 0; i < m_chunksPerCol; i++) {
			Chunk c = m_chunks[i][line];
			status = m_entityGenerator.generateEntityToChunk(c);
			addEntityToList(status, c);
		}
	}

	/**
	 * Generate a new column of chunks at the given column
	 * 
	 * @param col         column to insert new Chunks
	 * @param generateToX X position of new Chunks
	 * @param generateToY Y position of new Chunks
	 * @throws Exception
	 */
	private void generateColChunks(int col, int generateToX, int generateToY) throws Exception {
		int chunkWidth = m_constants.CHUNK_WIDTH;
		int chunkHeight = m_constants.CHUNK_HEIGHT;

		for (int i = 0; i < m_chunksPerCol; i++) {
			Texture texture = m_entityGenerator.generateTexture();
			Chunk c = new Chunk(generateToX, generateToY + chunkHeight * i, chunkWidth, chunkHeight, texture);

			if (texture.getType().equals(TextureTypes.MUD)) {
				m_mud.add(c);
			}

			m_chunks[col][i] = c;
		}

		boolean status = false;
		for (int i = 0; i < m_chunksPerCol; i++) {
			Chunk c = m_chunks[col][i];
			status = m_entityGenerator.generateEntityToChunk(c);
			addEntityToList(status, c);
		}

	}

	public Season getSeason() {
		return m_season;
	}

	public boolean isXInMap(int x) {
		int topLeftX = m_chunks[0][0].getX();
		return x >= topLeftX && x <= topLeftX + m_constants.CHUNK_WIDTH * m_chunksPerCol;
	}

	public boolean isYInMap(int y) {
		int topLeftX = m_chunks[0][0].getY();
		int res = topLeftX + m_constants.CHUNK_HEIGHT * m_chunksPerLine;
		return y >= topLeftX && y <= res;
	}

	public boolean isInMap(int x, int y) {

		int topLeftX = m_chunks[0][0].getX();
		int bottomLeftX = m_chunks[m_chunksPerLine - 1][0].getY();

		int totalWidth = topLeftX + m_constants.CHUNK_WIDTH * (m_chunksPerLine - 1);
		int totalHeight = bottomLeftX + m_constants.CHUNK_HEIGHT * (m_chunksPerCol - 1);

		boolean t1 = x >= topLeftX && x <= totalWidth;
		boolean t2 = y >= bottomLeftX && y <= totalHeight;

		return (t1 && t2);
	}

	public boolean isInVisibileMap(int x, int y) {
		Player p = Player.getInstance();
		int[] coords = p.getCenterCoordinates();
		int topLeftX = coords[0] - m_constants.SCREEN_WIDTH / 2;
		int bottomLeftX = coords[1] - m_constants.SCREEN_HEIGHT / 2;

		int totalWidth = topLeftX + m_constants.SCREEN_WIDTH;
		int totalHeight = bottomLeftX + m_constants.SCREEN_HEIGHT;

		boolean t1 = x >= topLeftX && x <= totalWidth;
		boolean t2 = y >= bottomLeftX && y <= totalHeight;

		return (t1 && t2);
	}

	private void addEntityToList(boolean status, Chunk c) {
		if (status) {
			Entity e = c.getEntity();
			if (e instanceof Ennemy && !((Ennemy) e).getDetected()) {
				((Ennemy) e).setDetected(isInVisibileMap((int) e.getX(), (int) e.getY()));
			}

			m_entities.add(c.getEntity());
			this.m_dirtyFlag = true;
		}
	}

	public void updateMinimap() {
		for (Entity e : m_entities) {
			if (e instanceof Ennemy && !((Ennemy) e).getDetected()) {
				((Ennemy) e).setDetected(isInVisibileMap((int) e.getX(), (int) e.getY()));
			}
		}

	}

	public LinkedList<Chunk> getMud() {
		return m_mud;
	}

	public boolean getDirtyFlag() {
		return m_dirtyFlag;
	}

	public void setDirtyFlag(boolean m_dirtyFlag) {
		this.m_dirtyFlag = m_dirtyFlag;
	}
	public LevelManager getM_level_manager() {
		return m_level_manager;
	}
}
