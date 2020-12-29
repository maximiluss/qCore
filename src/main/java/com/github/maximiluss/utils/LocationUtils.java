package com.github.maximiluss.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {

	private static String regex = ":";
	private static String regex2 = "=";
	private static String regex3 = "&";

	public static Location deserealizeLocation(String loc) {
		if (loc == null || loc.isEmpty() || loc.equalsIgnoreCase("null"))
			return null;

		String[] data = loc.split(regex);

		World world = Bukkit.getWorld(UUID.fromString(data[0]));

		double x = Double.parseDouble(data[1]);
		double y = Double.parseDouble(data[2]);
		double z = Double.parseDouble(data[3]);

		return new Location(world, x, y, z);
	}

	public static String serealizeLocation(Location loc) {

		if (loc == null)
			return "null";

		StringBuilder data = new StringBuilder("");

		data.append(loc.getWorld().getUID().toString()).append(regex);
		data.append(loc.getX()).append(regex);
		data.append(loc.getY()).append(regex);
		data.append(loc.getZ());
		return data.toString();
	}

	public static String serealizeLocations(Map<String, Location> loc) {

		if (loc == null) {
			return "null";
		}

		if (loc.size() == 0) {
			return "null";
		}
		StringBuilder data = new StringBuilder("");
		int i = 0;
		for (String name : loc.keySet()) {
			i++;
			data.append(name).append(regex2).append(serealizeLocation(loc.get(name)));
			if (i < loc.size()) {
				data.append(regex3);
			}

		}
		return data.toString();
	}

	public static Map<String, Location> deserealizeLocations(String locs) {

		if (locs == null || locs.equalsIgnoreCase("null"))
			return new HashMap<>();
		Map<String, Location> mapLocs = new HashMap<>();
		String data[] = locs.split(regex3);
		for (int i = 0; i < data.length; i++) {

			String data2[] = data[i].split(regex2);

			String name = data2[0];
			Location loc = deserealizeLocation(data2[1]);
			mapLocs.put(name, loc);
		}

		return mapLocs;
	}

}
