package com.github.maximiluss.mcp.packets.manager;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.reflect.FieldUtils;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Protocol;
import com.comphenix.protocol.PacketType.Sender;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.accessors.Accessors;
import com.github.maximiluss.QCore;
import com.github.maximiluss.mcp.packets.APacket;
import com.github.maximiluss.mcp.packets.server.SPacket;
import com.google.common.collect.BiMap;

import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.Packet;

public class PacketsManager {

	private QCore plugin;

	private final Map<Class<? extends APacket>, PacketType> packetToType;

	public PacketsManager(QCore plugin) {
		this.plugin = plugin;
		this.packetToType = new HashMap<Class<? extends APacket>, PacketType>();

	}

	@SuppressWarnings("unchecked")
	public void registerPacket(Class<? extends APacket> packetClass, int packetId, Sender sender) {

		final PacketType packetType = new PacketType(Protocol.PLAY, sender, packetId, -1);

		packetToType.put(packetClass, packetType);

		final EnumProtocol protocol = EnumProtocol.PLAY;
		final EnumProtocolDirection direction = packetType.isClient() ? EnumProtocolDirection.SERVERBOUND
				: EnumProtocolDirection.CLIENTBOUND;

		try {

			Map<EnumProtocolDirection, BiMap<Integer, Class<? extends Packet<?>>>> theMap = (Map<EnumProtocolDirection, BiMap<Integer, Class<? extends Packet<?>>>>) FieldUtils
					.readField(protocol, "j", true);

			BiMap<Integer, Class<? extends Packet<?>>> biMap = theMap.get(direction);
			biMap.put(packetId, (Class<? extends Packet<?>>) packetClass);
			theMap.put(direction, biMap);
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		}

		Map<Class<?>, EnumProtocol> map = (Map<Class<?>, EnumProtocol>) Accessors
				.getFieldAccessor(EnumProtocol.class, Map.class, true).get(protocol);
		map.put(packetClass, protocol);
	}

	public void sendPacket(Player player, SPacket packet) {

		try {
			PacketContainer container = new PacketContainer(packetToType.get(packet.getClass()), packet);
			plugin.getProtocolManager().sendServerPacket(player, container);
		} catch (Exception e) {
			if (e instanceof NullPointerException) {
				System.out.println("nullPointer broooooo");
				sendPacket(player, packet);
			} else {
				e.printStackTrace();
			}
		}
	}

	public PacketType getPacketType(Class<? extends APacket> clazz) {
		return packetToType.get(clazz);
	}

}
