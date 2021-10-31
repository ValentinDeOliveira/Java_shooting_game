package Model.automatons;

import java.util.HashMap;
import java.util.Map;

public class AutomatonFactory {
	static Map<String, AutomatonTypes> automatonTypes = new HashMap<>();

	public static AutomatonTypes getEntityType(String className) {
		AutomatonTypes result = automatonTypes.get(className);
		if (result == null) {
			result = new AutomatonTypes(AutomatonsModel.m_automatons.get(className));
			automatonTypes.put(className, result);
		}
		return result;
	}
}
