package Model;

import java.awt.Toolkit;

public class Constants_model {
	public static final double PLAYER_SCALE = 0.6;
	public static final double DOG_SCALE = 2;
	public static final double ENNEMY_SCALE = 0.5;
	public static final double BOSS_SCALE = 1;
	public static final double BULLET_SCALE = 1;


	public final int PLAYER_HP = 100;
	public final int ENNEMY_HP = 1;
	public final int DOG_HP = 5;

	public final int ENNEMY_SPAWN_ODD = -1;
	public final int MUD_SPAWN_ODD = 5;

	public final String PATH_TO_SPRITES = "lib/resources/";

	public final String PATH_TO_AUTOMATON = "src/Automaton/exemple.gal";
	public final String PLAYER_SPRITE = PATH_TO_SPRITES + "rifle/idle/survivor-idle_rifle_0.png";
	public final String DOG_SPRITE = PATH_TO_SPRITES + "dog2.png";
	public final String ENNEMY_SPRITE = PATH_TO_SPRITES + "spritesheet_ennemy.png";
	public final String MUD_SPRITE = PATH_TO_SPRITES + "mud.png";

	public final String DECORATIONS[] = { "herbe.png", "hiver.png" };

	public final int CHUNK_WIDTH = 100;
	public final int CHUNK_HEIGHT = 100;

	public final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	public final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

	public final int SPEED = 20;

	public final int HUNDRED_PX_OFFSET = 100;

	public final int NB_PIXELS_TO_DETECT_ENNEMIES = 50;

	public final int MINIMAP_WIDTH = SCREEN_WIDTH / 6;
	public final int MINIMAP_HEIGHT = MINIMAP_WIDTH / 2;

	public final int CIRCLE_RADIUS = 5;

	public final int MUD_SPEED_SLOWING = SPEED / 4;

	// 0 for normal, 1 for mud
	// public final int[] TextureTypes = { 0, 1 };
	public enum TextureTypes {
		NORMAL, MUD
	};

	// ratio of the invisible screen, in this case, this ratio will be about 50%
	// meaning that the width of the invisible part will be of 2880 px
	// and the height will be 1620
	public final double INVISIBLE_RATIO = 0.3;

	public static final int SUMMER = 0;
	public static final int WINTER_1 = 1;
	public static final int WINTER_2 = 2;
	public static final int WINTER_3 = 3;
	public static final int WINTER_4 = 4;
	public static final int WINTER_5 = 5;
	public static final int WINTER_6 = 6;
	public static final int WINTER_7 = 7;
	public static final int WINTER_8 = 8;
	public static final int WINTER_9 = 9;
	public static final int WINTER_10 = 10;
	public static final int WINTER_11 = 11;
	public static final int WINTER_12 = 12;
	public static final int WINTER = 13;

	public enum ClassNames {
		MOVEABLE, ENNEMY
	}
}
