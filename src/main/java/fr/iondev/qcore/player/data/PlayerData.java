package fr.iondev.qcore.player.data;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.iondev.qcore.QCore;
import fr.iondev.qcore.player.qCorePlayer;
import fr.iondev.qcore.player.manager.PlayerManager;
import fr.iondev.qcore.sql.Data;
import fr.iondev.qcore.sql.builder.SqlRequetteContain;
import fr.iondev.qcore.sql.builder.SqlRequetteCreate;
import fr.iondev.qcore.sql.builder.SqlRequetteInsert;
import fr.iondev.qcore.sql.builder.SqlRequetteSelect;
import fr.iondev.qcore.sql.builder.SqlRequetteUpdate;
import fr.iondev.qcore.sql.builder.utils.Condition;
import fr.iondev.qcore.sql.builder.utils.Constraint;
import fr.iondev.qcore.sql.builder.utils.Constraint.ConstraintValues;
import fr.iondev.qcore.utils.ObjectBuilder;

public class PlayerData extends Data {

	private PlayerManager playerM;

	public PlayerData(Connection c, PlayerManager playerM) {
		super();
		this.playerM = playerM;
		load();
	}

	@Override
	protected void createTable() {
		new SqlRequetteCreate(getC(), "corePlayersStorage", Arrays.asList("uuid", "name", "tuers", "morts"),
				Arrays.asList("VARCHAR(90)", "VARCHAR(90)", "INT(30)", "INT(30)"),
				new Constraint(ConstraintValues.PRIMARY, Arrays.asList("uuid"))).send();

	}

	@Override
	protected void load() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			load(player);
		}
	}

	@SuppressWarnings("unchecked")
	public void load(Player player) {
		if (playerM == null)
			playerM = QCore.plugin.getPlayerManager();
		if (!contain(player))
			playerM.initPlayer(player);

		SqlRequetteSelect select = new SqlRequetteSelect(getC(), "corePlayersStorage", Arrays.asList("tuers", "morts"),
				new Condition(Arrays.asList("uuid"), null), Arrays.asList(player.getUniqueId().toString()));
		List<ObjectBuilder> builder = (List<ObjectBuilder>) select.send();
		for (ObjectBuilder obj : builder) {
			playerM.loadPlayer(player, (int) obj.getObject(2), (int) obj.getObject(3), false);
		}
	}

	@Override
	public void save() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			save(player);
		}
	}

	public void save(Player player) {
		qCorePlayer cP = playerM.getCorePlayer(player);
		if (!contain(player)) {
			new SqlRequetteInsert(getC(), "corePlayersStorage", Arrays.asList("uuid", "name", "tuers", "morts"),
					Arrays.asList(player.getUniqueId().toString(), player.getName(), cP.getTuer(), cP.getMorts()))
							.send();
			return;
		}

		new SqlRequetteUpdate(getC(), "corePlayersStorage", Arrays.asList("name", "tuers", "morts"),
				new Condition(Arrays.asList("uuid"), null),
				Arrays.asList(player.getName(), cP.getTuer(), cP.getMorts(), player.getUniqueId().toString())).send();
	}

	@Override
	protected <T> boolean contain(T val) {
		Player player = (Player) val;
		boolean reponce = (boolean) new SqlRequetteContain(getC(), "corePlayersStorage", Arrays.asList("uuid"),
				new Condition(Arrays.asList("uuid"), null), Arrays.asList(player.getUniqueId().toString())).send();
		return reponce;
	}

}
