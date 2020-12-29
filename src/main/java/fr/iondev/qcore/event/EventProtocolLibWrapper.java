package fr.iondev.qcore.event;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;

import fr.iondev.qcore.QCore;
import fr.iondev.qcore.logger.Logger;
import fr.iondev.qcore.player.listener.PlayerListener;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;

public class EventProtocolLibWrapper implements Listener {

	private QCore pl;
	private Logger logger = QCore.getQLogger();

	private EventWrapper wrapper;

	public EventProtocolLibWrapper(QCore pl, EventWrapper wrapper) {
		this.pl = pl;
		this.wrapper = wrapper;
	}

	public void setupProtocolManager() {
		packetTabComplet();
		logger.log(pl.getPrefix() + ChatColor.YELLOW + " packetTabComplet" + ChatColor.GREEN + " Wrapped");
		packetServerList();
		logger.log(pl.getPrefix() + ChatColor.YELLOW + " ServerListPacket" + ChatColor.GREEN + " Wrapped");
		packetServerChat();
		logger.log(pl.getPrefix() + ChatColor.YELLOW + " ServerChatPacket" + ChatColor.GREEN + " Wrapped");
	}

	public void packetTabComplet() {
		pl.getProtocolManager().addPacketListener(new PacketAdapter(pl, ListenerPriority.NORMAL,
				new PacketType[] { PacketType.Play.Client.TAB_COMPLETE }) {
			@EventHandler(priority = EventPriority.HIGHEST)
			public void onPacketReceiving(PacketEvent event) {
				if (event.getPacketType() == PacketType.Play.Client.TAB_COMPLETE) {
					PacketContainer packet = event.getPacket();
					String message = ((String) packet.getSpecificModifier(String.class).read(0)).toLowerCase();
					PlayerTabCompletEvent playerTabCompletEvent = new PlayerTabCompletEvent(event.getPlayer(), message);
					wrapper.call(playerTabCompletEvent);
					event.setCancelled(playerTabCompletEvent.isCancelled());
				}
			}
		});
	}

	@SuppressWarnings("deprecation")
	public void packetServerList() {
		pl.getProtocolManager().addPacketListener(new PacketAdapter(pl, ListenerPriority.NORMAL,
				new PacketType[] { PacketType.Status.Server.OUT_SERVER_INFO }) {
			@EventHandler(priority = EventPriority.HIGHEST)
			public void onPacketSending(PacketEvent event) {
				WrappedServerPing ping = (WrappedServerPing) event.getPacket().getServerPings().read(0);
				List<WrappedGameProfile> players = new ArrayList<WrappedGameProfile>();
				PlayerServerListPingerEvent listPingerEvent = new PlayerServerListPingerEvent(event.getPlayer(),
						ping.getVersionProtocol(), ping.getVersionName());
				wrapper.call(listPingerEvent);
				players.add(new WrappedGameProfile(UUID.randomUUID(), listPingerEvent.getMessage()));
				ping.setPlayers(players);
				ping.setVersionProtocol(listPingerEvent.getPingVersion());
				ping.setVersionName(listPingerEvent.getPingVersionName());
			}

		});
	}

	private Player sender = null;
	private String message = "";

	public void packetServerChat() {
		pl.getProtocolManager().addPacketListener(new PacketAdapter(pl, ListenerPriority.HIGHEST,
				new PacketType[] { PacketType.Play.Server.CHAT, PacketType.Play.Client.CHAT }) {

			@EventHandler(priority = EventPriority.HIGHEST)
			public void onPacketSending(PacketEvent event) {
				if (event.getPacket().getType().equals(PacketType.Play.Server.CHAT)) {

					StructureModifier<WrappedChatComponent> component = event.getPacket().getChatComponents();
					for (WrappedChatComponent c : component.getValues()) {
						if (c == null || !c.getJson().contains("[chatMessage]"))
							return;
						Player player = event.getPlayer();

						String format = ChatSerializer.a(ChatColor.translateAlternateColorCodes('§', c.getJson())).c();
						PlayerChatFormatingEvent chatFormatingEvent = new PlayerChatFormatingEvent(player, sender,
								PlayerListener.chatFormating.replace("[chatMessage]", ""), message,
								format.replace("[chatMessage]", ""));
						wrapper.call(chatFormatingEvent);
						if (chatFormatingEvent.getComponent().isEmpty()) {
							c.setJson(c.getJson().replace("[chatMessage]", ""));
							event.getPacket().getChatComponents().write(0, c);
						} else {
							event.setCancelled(true);
							TextComponent[] textC = {};
							for (int i = 0; i < chatFormatingEvent.getComponent().size(); i++) {
								textC[i] = chatFormatingEvent.getComponent().get(i).build();
							}
							player.spigot().sendMessage(textC);
						}
					}

				}
			}

			@Override
			public void onPacketReceiving(PacketEvent event) {
				Player senderr = event.getPlayer();
				PacketContainer packet = event.getPacket();
				String msg = packet.getStrings().read(0);
				sender = senderr;
				message = msg;
			}
		});
	}
}
