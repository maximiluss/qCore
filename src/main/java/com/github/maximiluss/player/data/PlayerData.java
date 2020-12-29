package com.github.maximiluss.player.data;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.maximiluss.QCore;
import com.github.maximiluss.player.qCorePlayer;
import com.github.maximiluss.player.manager.PlayerManager;
import com.github.maximiluss.sql.Data;
import com.github.maximiluss.sql.builder.SqlRequetteContain;
import com.github.maximiluss.sql.builder.SqlRequetteCreate;
import com.github.maximiluss.sql.builder.SqlRequetteInsert;
import com.github.maximiluss.sql.builder.SqlRequetteSelect;
import com.github.maximiluss.sql.builder.SqlRequetteUpdate;
import com.github.maximiluss.sql.builder.utils.Condition;
import com.github.maximiluss.sql.builder.utils.Constraint;
import com.github.maximiluss.sql.builder.utils.Constraint.ConstraintValues;
import com.github.maximiluss.utils.ObjectBuilder;

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
