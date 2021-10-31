package Model.Map;

import Model.Constants_model.TextureTypes;

public class Texture {
	private int m_speed;

	private TextureTypes m_type;

	public Texture(int m_speed, TextureTypes type) {
		this.m_speed = m_speed;
		this.m_type = type;
	}

	public int getSpeed() {
		return m_speed;
	}

	public TextureTypes getType() {
		return m_type;
	}

}
