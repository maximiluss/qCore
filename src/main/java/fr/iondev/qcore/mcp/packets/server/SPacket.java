package fr.iondev.qcore.mcp.packets.server;

import java.io.IOException;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;

import fr.iondev.qcore.mcp.packets.APacket;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListener;

public abstract class SPacket extends APacket {

	@Override
	public void handle(PacketListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readPacketData(PacketDataSerializer data) throws IOException {
		// TODO Auto-generated method stub

	}

	public void writeString(PacketDataSerializer data, String msg) {
		data.writeInt(msg.length());
		data.a(msg);
	}

	public void writeUUID(PacketDataSerializer data, UUID uuid) {
		data.a(uuid);
	}

	public void writeNBTTagCompound(PacketDataSerializer data, NBTTagCompound nbt) {
		data.a(nbt);
	}

	public void writeItemStack(PacketDataSerializer data, net.minecraft.server.v1_8_R3.ItemStack is) {
		data.a(is);
	}

	public void writeItemStackBukkit(PacketDataSerializer data, org.bukkit.inventory.ItemStack is) {
		writeItemStack(data, CraftItemStack.asNMSCopy(is));
	}

	public void writeBlockPosition(PacketDataSerializer data, BlockPosition pos) {
		data.a(pos);
	}

	public void writeBlockPosition(PacketDataSerializer data, Location loc) {
		this.writeBlockPosition(data, new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
	}

	public void writeEnum(PacketDataSerializer data, final Enum<?> in) {
		data.a(in);
	}

	public void writeByteArray(PacketDataSerializer data, byte... bytes) {
		data.a(bytes);
	}

}
