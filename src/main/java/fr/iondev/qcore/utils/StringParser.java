package fr.iondev.qcore.utils;

public class StringParser {

	public static boolean getInt(String chaine) {
		try {
			Integer.parseInt(chaine);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static boolean getDouble(String chaine) {
		try {
			Double.parseDouble(chaine);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static boolean getFloat(String chaine) {
		try {
			Float.parseFloat(chaine);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

}
