package com.github.maximiluss.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder {

	private ItemStack item;
	private String name;
	private List<String> lore;
	private Map<Enchantment, Integer> enchant = new HashMap<>();

	public ItemBuilder(ItemStack item) {
		this.item = item;
		if (item.hasItemMeta() && item.getItemMeta().getEnchants() != null)
			enchant = item.getItemMeta().getEnchants();
	}

	public ItemBuilder(Material material) {
		item = new ItemStack(material);
	}

	public ItemBuilder(Material material, int count) {
		item = new ItemStack(material, count);
	}

	public ItemBuilder(Material material, int count, String name) {
		item = new ItemStack(material, count);
		this.name = name;
	}

	public ItemBuilder(Material material, int count, String name, byte data) {
		item = new ItemStack(material, count, data);
		this.name = name;
	}

	public ItemBuilder(Material material, int count, String name, List<String> lore) {
		item = new ItemStack(material, count);
		this.name = name;
		this.lore = lore;
	}

	public ItemBuilder(Material material, int count, String name, byte data, List<String> lore) {
		item = new ItemStack(material, count, data);
		this.name = name;
		this.lore = lore;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addLore(String... ligne) {
		for (String l : ligne) {
			lore.add(l);
		}
	}

	public void setAmount(int amount) {
		item.setAmount(amount);
	}

	public void addEnchant(Enchantment enchant, int value) {
		this.enchant.put(enchant, value);
	}

	public void setOwner(String owner) {
		if (item.getType().equals(Material.SKULL_ITEM)) {
			SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
			skullMeta.setOwner(owner);
			item.setItemMeta(skullMeta);
		}
	}

	public ItemStack build() {

		ItemMeta itemMeta = item.getItemMeta();
		if (name != null)
			itemMeta.setDisplayName(name);
		if (lore != null)
			itemMeta.setLore(lore);
		if (enchant.size() != 0) {
			itemMeta.getEnchants().clear();
			itemMeta.getEnchants().putAll(enchant);

		}

		item.setItemMeta(itemMeta);

		return item;
	}
}
