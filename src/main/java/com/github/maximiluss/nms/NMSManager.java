package com.github.maximiluss.nms;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.github.maximiluss.QCore;

public class NMSManager {

	private QCore plugin;

	private String nms = "net.minecraft.server.";
	private String nmsVersion;

	public NMSManager(QCore plugin) {
		this.plugin = plugin;
		nmsVersion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
	}

	public void logsVersion() {
		plugin.log(plugin.getConsolPrefix() + ChatColor.GOLD + "NMS Version : " + ChatColor.YELLOW + nmsVersion);
		plugin.log(plugin.getConsolPrefix() + ChatColor.YELLOW + "PacketPlayOutPlayerListHeaderFooter "
				+ ChatColor.GREEN + "Wrapped");
		plugin.log(plugin.getConsolPrefix() + ChatColor.YELLOW + "PacketPlayOutTime " + ChatColor.GREEN + "Wrapped");
		plugin.log(plugin.getConsolPrefix() + ChatColor.YELLOW + "PacketPlayOutTitle " + ChatColor.GREEN + "Wrapped");
		plugin.log(
				plugin.getConsolPrefix() + ChatColor.YELLOW + "PacketPlayOutSubTitle " + ChatColor.GREEN + "Wrapped");
		plugin.log(plugin.getConsolPrefix() + ChatColor.YELLOW + "PacketPlayOutSpawnEntityLiving " + ChatColor.GREEN
				+ "Wrapped");
	}

	/*
	 * NMS Utils getServerVersion -> return exemple(v1_8_R3)
	 * 
	 * getNmsClass return Class<?> in the net.minecraft.server
	 * 
	 */
	public String getServerVersion() {
		return nmsVersion;
	}

	public Class<?> getNmsClass(final String nmsClassName) {
		try {
			return Class.forName(nms + nmsVersion + "." + nmsClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 
	 * PlayerHandler Utils getHandler -> return EntityPlayer getHandlerField return
	 * field in EntityPlayer
	 * 
	 */

	public Object getHandler(Player player) {
		try {
			return player.getClass().getMethod("getHandle", (Class<?>[]) new Class[0]).invoke(player, new Object[0]);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Object getHanderField(Player player, String field) {
		return getHandlerField(getHandler(player), field);
	}

	public Object getHandlerField(Object handler, String field) {
		try {
			return handler.getClass().getField(field).get(handler);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 
	 * getter to simplifie the AbstractPlayer and the NMSManager
	 * 
	 */
	public Object getPlayerConnection(Object handler) {
		Object playerConnection = getHandlerField(handler, "playerConnection");
		return playerConnection;
	}

	public int getPlayerPing(Player player) {
		try {
			Object handler = getHandler(player);
			return (int) handler.getClass().getField("ping").get(handler);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/*
	 * 
	 * ChatSerealizer Builder
	 * 
	 */

	public Class<?> getChatSerealizerClass() {
		try {
			Class<?> chatSerealizer = null;
			if (nmsVersion.equalsIgnoreCase("v1_9_R1") || nmsVersion.equalsIgnoreCase("v1_9_R2")
					|| nmsVersion.equalsIgnoreCase("v1_10_R1") || nmsVersion.equalsIgnoreCase("v1_11_R1")) {
				chatSerealizer = getNmsClass("ChatComponentText");
			} else if (nmsVersion.equalsIgnoreCase("v1_8_R2") || nmsVersion.equalsIgnoreCase("v1_8_R3")) {
				chatSerealizer = getNmsClass("IChatBaseComponent$ChatSerializer");
			} else {
				chatSerealizer = getNmsClass("ChatSerializer");
			}
			return chatSerealizer;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Object buildChatSerealizer(String message) {
		try {
			Object chatSerealizer = null;
			if (nmsVersion.equalsIgnoreCase("v1_9_R1") || nmsVersion.equalsIgnoreCase("v1_9_R2")
					|| nmsVersion.equalsIgnoreCase("v1_10_R1") || nmsVersion.equalsIgnoreCase("v1_11_R1")) {
				chatSerealizer = getNmsClass("ChatComponentText").getConstructor(String.class)
						.newInstance(ChatColor.translateAlternateColorCodes('&', message));
			} else if (nmsVersion.equalsIgnoreCase("v1_8_R2") || nmsVersion.equalsIgnoreCase("v1_8_R3")) {
				chatSerealizer = getNmsClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class)
						.invoke(null, "{'text': '" + message + "'}");
			} else {
				chatSerealizer = getNmsClass("ChatSerializer").getMethod("a", String.class).invoke(null,
						"{'text': '" + message + "'}");
			}
			return chatSerealizer;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getChatSerealizerB(String jsonMessage) {
		Class<?> chatSerealizer = getChatSerealizerClass();
		try {
			chatSerealizer.getMethod("a", String.class).invoke(null, jsonMessage);
			for (Method m : chatSerealizer.getMethods()) {
				System.out.println(m.getName());
			}
			return (String) chatSerealizer.getMethod("b").invoke(null);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 
	 * Packet Utils sender
	 * 
	 */

	public void sendPacket(Object playerConnection, Object packet) {
		try {
			playerConnection.getClass().getMethod("sendPacket", getNmsClass("Packet")).invoke(playerConnection, packet);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
	}

	public void sendPacket(Player player, Object packet) {
		sendPacket(getPlayerConnection(getHandler(player)), packet);
	}

	/*
	 * 
	 * PacketSender Utils for simplify the AbstractPlayer And PlayerTabCompletEvent
	 * 
	 */

	public void sendTabListPacket(Player player, String head, String foot) {
		Class<?> serealizer = getChatSerealizerClass();

		Object header = null;
		Object footer = null;

		if (serealizer == null || serealizer.getMethods()[1] == null || head == null || foot == null) {
			plugin.logErr("serealizer null");
			return;
		}

		try {
			header = serealizer.getMethods()[1].invoke(null, "{'text': '" + head + "'}");
			footer = serealizer.getMethods()[1].invoke(null, "{'text': '" + foot + "'}");
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e1) {
			e1.printStackTrace();
		}

		try {
			Object packetListHeaderFooter = getNmsClass("PacketPlayOutPlayerListHeaderFooter")
					.getConstructor(getNmsClass("IChatBaseComponent")).newInstance(header);
			Field f = packetListHeaderFooter.getClass().getDeclaredField("b");
			f.setAccessible(true);
			f.set(packetListHeaderFooter, footer);
			sendPacket(player, packetListHeaderFooter);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	private Class<?> getEnuTitleAction() {
		if (getServerVersion().equalsIgnoreCase("v1_8_R1")) {
			return getNmsClass("EnumTitleAction");
		} else {
			return getNmsClass("PacketPlayOutTitle$EnumTitleAction");
		}
	}

	public void sendTitlePacket(Player player, String title) {
		try {
			Object packetTitle = getNmsClass("PacketPlayOutTitle").getConstructor(getEnuTitleAction(),
					getNmsClass("IChatBaseComponent"), int.class, int.class, int.class).newInstance(
							getEnuTitleAction().getField("TITLE").get(null), buildChatSerealizer(title), 20, 20, 20);
			sendPacket(player, packetTitle);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	public void sendSubTitlePacket(Player player, String subTitle) {
		try {
			Object packetSubTitle = getNmsClass("PacketPlayOutChat")
					.getConstructor(getNmsClass("IChatBaseComponent"), byte.class)
					.newInstance(buildChatSerealizer(subTitle), (byte) 2);
			sendPacket(player, packetSubTitle);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	public void sendTimePacket(Player player, int fadin, int delay, int fadout) {
		try {
			Object packetTime = getNmsClass("PacketPlayOutTitle").getConstructor(int.class, int.class, int.class)
					.newInstance(fadin, delay, fadout);
			sendPacket(player, packetTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendSpawnEntityLivingPacket(Player player, Object entity) {
		try {
			Object packetSpawnEntityLiving = getNmsClass("PacketPlayOutSpawnEntityLiving")
					.getConstructor(getNmsClass("EntityLiving")).newInstance(entity);
			sendPacket(player, packetSpawnEntityLiving);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	public void sendDestroyEntityLivingPacket(Player player, Object entity) {
		try {
			int entityID = (int) entity.getClass().getMethod("getId").invoke(null);
			Object packetSpawnEntityLiving = getNmsClass("PacketPlayOutEntityDestroy").getConstructor(int[].class)
					.newInstance(entityID);
			sendPacket(player, packetSpawnEntityLiving);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
}
