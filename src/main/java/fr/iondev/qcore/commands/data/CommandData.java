package fr.iondev.qcore.commands.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.iondev.qcore.QCore;
import fr.iondev.qcore.commands.QPlayerCommandTimer;
import fr.iondev.qcore.commands.utils.AbstractCommand;
import fr.iondev.qcore.commands.utils.CommandDataTimer;
import fr.iondev.qcore.sql.Data;
import fr.iondev.qcore.sql.builder.SqlRequetteContain;
import fr.iondev.qcore.sql.builder.SqlRequetteCreate;
import fr.iondev.qcore.sql.builder.SqlRequetteDelete;
import fr.iondev.qcore.sql.builder.SqlRequetteInsert;
import fr.iondev.qcore.sql.builder.SqlRequetteSelect;
import fr.iondev.qcore.sql.builder.SqlRequetteUpdate;
import fr.iondev.qcore.sql.builder.utils.Condition;
import fr.iondev.qcore.sql.builder.utils.Constraint;
import fr.iondev.qcore.sql.builder.utils.Constraint.ConstraintValues;
import fr.iondev.qcore.sql.builder.utils.Separator;
import fr.iondev.qcore.utils.ObjectBuilder;
import fr.iondev.qcore.utils.PlayerTimerBuilder;

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
