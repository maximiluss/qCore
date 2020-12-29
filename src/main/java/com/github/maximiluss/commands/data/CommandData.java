package com.github.maximiluss.commands.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.maximiluss.QCore;
import com.github.maximiluss.commands.QPlayerCommandTimer;
import com.github.maximiluss.commands.utils.AbstractCommand;
import com.github.maximiluss.commands.utils.CommandDataTimer;
import com.github.maximiluss.sql.Data;
import com.github.maximiluss.sql.builder.SqlRequetteContain;
import com.github.maximiluss.sql.builder.SqlRequetteCreate;
import com.github.maximiluss.sql.builder.SqlRequetteDelete;
import com.github.maximiluss.sql.builder.SqlRequetteInsert;
import com.github.maximiluss.sql.builder.SqlRequetteSelect;
import com.github.maximiluss.sql.builder.SqlRequetteUpdate;
import com.github.maximiluss.sql.builder.utils.Condition;
import com.github.maximiluss.sql.builder.utils.Constraint;
import com.github.maximiluss.sql.builder.utils.Separator;
import com.github.maximiluss.sql.builder.utils.Constraint.ConstraintValues;
import com.github.maximiluss.utils.ObjectBuilder;
import com.github.maximiluss.utils.PlayerTimerBuilder;

public class CommandData extends Data {

	private QCore plugin;

	public CommandData(QCore plugin) {
		super();
		this.plugin = plugin;
		if (plugin.getUseSql())
			Commandload();
	}

	@Override
	protected void createTable() {
		new SqlRequetteCreate(getC(), "commandsStorage", Arrays.asList("command", "uuid", "current"),
				Arrays.asList("VARCHAR(90)", "VARCHAR(90)", "BIGINT(30)"),
				new Constraint(ConstraintValues.PRIMARY, Arrays.asList("command", "uuid"))).send();
	}

	@SuppressWarnings("unchecked")
	public void Commandload() {

		for (AbstractCommand cmd : plugin.getCommandManager().getAllCommands()) {
			if (cmd instanceof QPlayerCommandTimer) {
				QPlayerCommandTimer cmdTimer = (QPlayerCommandTimer) cmd;
				SqlRequetteSelect select = new SqlRequetteSelect(getC(), "commandsStorage",
						Arrays.asList("uuid", "current"), new Condition(Arrays.asList("command"), null),
						Arrays.asList(cmdTimer.getClass().getName()));
				List<ObjectBuilder> builder = (List<ObjectBuilder>) select.send();
				List<PlayerTimerBuilder> tmBuilder = new ArrayList<>();
				for (ObjectBuilder objBuilder : builder) {
					objBuilder.addObject(cmdTimer.getCmdTimer());
					PlayerTimerBuilder tm = (PlayerTimerBuilder) objBuilder.build(PlayerTimerBuilder.class);
					if (tm.hisFished()) {
						delete(new CommandDataTimer(tm, cmdTimer));
					} else {
						tmBuilder.add(tm);
					}
				}
				cmdTimer.setTimer(tmBuilder);
			}
		}
	}

	@Override
	public void save() {
		for (AbstractCommand cmd : plugin.getCommandManager().getAllCommands()) {
			if (cmd instanceof QPlayerCommandTimer) {
				QPlayerCommandTimer cmdTimer = (QPlayerCommandTimer) cmd;
				for (PlayerTimerBuilder t : cmdTimer.getTimer()) {
					if (!t.hisFished()) {
						if (!contain(new CommandDataTimer(t, cmdTimer))) {
							save(new CommandDataTimer(t, cmdTimer));
						} else {
							update(new CommandDataTimer(t, cmdTimer));
						}
					}
				}
			}
		}

	}

	private void save(CommandDataTimer cmdUtils) {
		new SqlRequetteInsert(getC(), "commandsStorage", Arrays.asList("command", "uuid", "current"),
				Arrays.asList(cmdUtils.getCommand().getClass().getName(), cmdUtils.getPlayer(), cmdUtils.getCurrent()))
		.send();
	}

	private void update(CommandDataTimer cmdUtils) {
		new SqlRequetteUpdate(getC(), "commandsStorage", Arrays.asList("current"),
				new Condition(Arrays.asList("command", "uuid"), Arrays.asList(Separator.AND)),
				Arrays.asList(cmdUtils.getCurrent(), cmdUtils.getCommand().getClass().getName(), cmdUtils.getPlayer()))
		.send();
	}

	private void delete(CommandDataTimer cmdUtils) {
		new SqlRequetteDelete(getC(), "commandsStorage",
				new Condition(Arrays.asList("command", "uuid"), Arrays.asList(Separator.AND)),
				Arrays.asList(cmdUtils.getCommand().getClass().getName(), cmdUtils.getPlayer())).send();
	}

	@Override
	protected <T> boolean contain(T val) {
		CommandDataTimer cmdUtils = (CommandDataTimer) val;
		boolean reponce = (boolean) new SqlRequetteContain(getC(), "commandsStorage", Arrays.asList("command", "uuid"),
				new Condition(Arrays.asList("command", "uuid"), Arrays.asList(Separator.AND)),
				Arrays.asList(cmdUtils.getCommand().getClass().getName(), cmdUtils.getPlayer())).send();
		return reponce;
	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub

	}

}
