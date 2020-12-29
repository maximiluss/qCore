package fr.iondev.qcore.utils;

public enum EnchantMapping {

	ARROW_DAMAGE("Power"), ARROW_FIRE("Flame"), ARROW_INFINITE("Infinity"), ARROW_KNOCKBACK("Punch"),
	BINDING_CURSE("Curse of Binding"), DAMAGE_ARTHROPODS("Bane of Arthropods"), DAMAGE_UNDEAD("Smite"),
	DEPTH_STRIDER("Depth Strider"), DIG_SPEED("Efficiency"), DURABILITY("Unbreaking"), FIRE_ASPECT("Fire Aspect"),
	FROST_WALKER("Frost Walker"), KNOCKBACK("Knockback"), LOOT_BONUS_BLOCKS("Fortune"), LOOT_BONUS_MOBS("Looting"),
	LUCK("Luck of the Sea"), LURE("Lure"), MENDING("Mending"), OXYGEN("Respiration"),
	PROTECTION_ENVIRONMENTAL("Protection"), PROTECTION_EXPLOSIONS("Blast Protection"),
	PROTECTION_FALL("Feather Falling"), PROTECTION_FIRE("Fire Protection"),
	PROTECTION_PROJECTILE("Projectile Protection"), SILK_TOUCH("Silk Touch"), SWEEPING_EDGE("Sweeping Edge"),
	THORNS("Thorns"), VANISHING_CURSE("Cure of Vanishing"), WATER_WORKER("Aqua Affinity"), DAMAGE_ALL("Sharpness");

	private String mapping;

	EnchantMapping(String mapping) {
		this.mapping = mapping;
	}

	public String getMapping() {
		return this.mapping;
	}

}
