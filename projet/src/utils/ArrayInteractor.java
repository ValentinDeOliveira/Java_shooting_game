package utils;

import java.util.LinkedList;

import Model.Constants_model.TextureTypes;
import Model.Entity;
import Model.Map.Chunk;

public class ArrayInteractor {

	public ArrayInteractor() {
	}

	private Chunk[][] copy2dArray(Chunk[][] array) {
		Chunk tmp[][] = new Chunk[array.length][array[0].length];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				tmp[i][j] = array[i][j];
			}
		}

		return tmp;
	}

	public Chunk[][] shiftLastCol(Chunk[][] array, LinkedList<Entity> entities, LinkedList<Chunk> mud) {
		Chunk tmp[][] = copy2dArray(array);

		for (int i = 0; i < array.length; i++) {
			entities.remove(array[i][0].getEntity());
			array[i][0] = null;
			for (int j = 0; j < array[i].length - 1; j++) {
				array[i][j + 1] = tmp[i][j];
			}
		}

		return array;
	}

	public Chunk[][] shiftFirstCol(Chunk[][] array, LinkedList<Entity> entities, LinkedList<Chunk> mud) {
		Chunk tmp[][] = copy2dArray(array);
		int j;

		for (int i = 0; i < array.length; i++) {
			for (j = array[i].length - 1; j > 0; j--) {
				array[i][j - 1] = tmp[i][j];
			}
		}

		for (int i = 0; i < array.length; i++) {
			entities.remove(array[i][array[i].length - 1].getEntity());
			array[i][array[i].length - 1] = null;
		}

		return array;
	}

	public Chunk[][] shiftLastRow(Chunk[][] array, LinkedList<Entity> entities, LinkedList<Chunk> mud) {

		Chunk tmp[][] = copy2dArray(array);

		for (int i = 0; i < array.length - 1; i++) {
			for (int j = 0; j < array[i].length; j++) {
				array[i + 1][j] = tmp[i][j];
			}
		}

		for (int j = 0; j < array[0].length; j++) {
			entities.remove(array[0][j].getEntity());
			array[0][j] = null;
		}

		return array;
	}

	public Chunk[][] shiftFirstRow(Chunk[][] array, LinkedList<Entity> entities, LinkedList<Chunk> mud) {

		Chunk tmp[][] = copy2dArray(array);
		int j;

		for (int i = array.length - 1; i > 0; i--) {
			for (j = 0; j < array[i].length; j++) {
				array[i - 1][j] = tmp[i][j];
			}
		}

		for (int i = 0; i < array[0].length; i++) {
			Entity e = array[array.length - 1][i].getEntity();
			entities.remove(e);
			array[array.length - 1][i] = null;
		}

		return array;
	}

	public LinkedList<Chunk> removeAllColMud(int col, Chunk[][] array, LinkedList<Chunk> mud) {
		for (int i = 0; i < array[0].length; i++) {
			Chunk c = array[col][i];
			if (c.getTexture().getType().equals(TextureTypes.MUD)) {
				mud.remove(c);
			}
		}

		return mud;
	}

	public LinkedList<Chunk> removeAllRowMud(int row, Chunk[][] array, LinkedList<Chunk> mud) {
		for (int i = 0; i < array.length; i++) {
			Chunk c = array[i][row];
			if (c.getTexture().getType().equals(TextureTypes.MUD)) {
				mud.remove(c);
			}
		}

		return mud;
	}

}
