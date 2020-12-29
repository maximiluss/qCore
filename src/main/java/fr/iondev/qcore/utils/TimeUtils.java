package fr.iondev.qcore.utils;

public class TimeUtils {

	public static String setTimeToMessage(int time) {

		if (time < 60) {
			return time + " secondes";
		}

		if (time < 3600) {
			int min = time / 60;
			time -= min * 60;
			int secondes = time;
			return min + "m " + secondes + "s";
		}
		if (time < 86400) {
			int hours = time / 3600;
			time -= hours * 3600;
			int min = time / 60;
			time -= min * 60;
			int secondes = time;
			return hours + "h " + min + "m " + secondes + "s";
		}
		if (time < 604800) {
			int days = time / 86400;
			time -= days * 86400;
			int hours = time / 3600;
			time -= hours * 3600;
			int min = time / 60;
			time -= min * 60;
			int secondes = time;
			return days + "d " + hours + "h " + min + "m " + secondes + "s";
		}
		if (time < 16934400) {
			int weeks = time / 604800;
			time -= weeks * 604800;
			int days = time / 86400;
			time -= days * 86400;
			int hours = time / 3600;
			time -= hours * 3600;
			int min = time / 60;
			time -= min * 60;
			int secondes = time;
			return weeks + "s " + days + "d " + hours + "h " + min + "m " + secondes + "s";
		}
		if (time < 203212800) {
			int months = time / 16934400;
			time -= months * 16934400;
			int weeks = time / 604800;
			time -= weeks * 604800;
			int days = time / 86400;
			time -= days * 86400;
			int hours = time / 3600;
			time -= hours * 3600;
			int min = time / 60;
			time -= min * 60;
			int secondes = time;
			return months + "m " + weeks + "s " + days + "d " + hours + "h " + min + "m " + secondes + "s";
		}
		if (time < 2032128000) {
			int years = time / 203212800;
			time -= years * 203212800;
			int months = time / 16934400;
			time -= months * 16934400;
			int weeks = time / 604800;
			time -= weeks * 604800;
			int days = time / 86400;
			time -= days * 86400;
			int hours = time / 3600;
			time -= hours * 3600;
			int min = time / 60;
			time -= min * 60;
			int secondes = time;
			return years + "a " + months + "m " + weeks + "s " + days + "d " + hours + "h " + min + "m " + secondes
					+ "s";
		}
		return "Permanent";
	}
}
