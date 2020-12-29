package com.github.maximiluss.utils;

import java.util.Arrays;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemSerializer {

	private static String regex = "//";
	private static String regex2 = ":";
	private static String regex3 = " ";

	@SuppressWarnings("deprecation")
	public static String serialize(ItemStack item) {
		StringBuilder builder = new StringBuilder("");
		// type
		builder.append(item.getType().getId()).append(regex);
		// quantité
		builder.append(item.getAmount()).append(regex);
		// data
		builder.append(item.getData().getData()).append(regex);
		// dura
		builder.append(item.getDurability()).append(regex);
		// enchant
		int i = 1;
		if (item.getEnchantments().size() != 0) {
			for (Enchantment enchant : item.getEnchantments().keySet()) {
				builder.append(enchant.getName() + regex2 + item.getEnchantments().get(enchant));
				if (item.getEnchantments().size() > i) {
					builder.append(regex3);
					i++;
				}
			}
		} else {
			builder.append("-1");
		}

		if (item.hasItemMeta()) {
			ItemMeta itM = item.getItemMeta();
			// name
			builder.append(regex);
			if (itM.hasDisplayName()) {
				builder.append(itM.getDisplayName());
			} else {
				builder.append("-1");
			}
			// lore
			builder.append(regex);
			if (itM.hasLore()) {

				for (String ligne : itM.getLore()) {
					builder.append(ligne);
					if (itM.getLore().indexOf(ligne) < itM.getLore().size() - 1) {
						builder.append(regex2);
					}
				}
			} else {
				builder.append("-1");
			}
			// book enchant
			builder.append(regex);
			int i2 = 1;
			if (itM instanceof EnchantmentStorageMeta) {
				System.out.println("testttttt");
				for (Enchantment enchant : ((EnchantmentStorageMeta) itM).getStoredEnchants().keySet()) {
					builder.append(enchant.getName() + regex2
							+ ((EnchantmentStorageMeta) itM).getStoredEnchants().get(enchant));
					if (((EnchantmentStorageMeta) itM).getEnchants().size() > i2) {
						builder.append(regex3);
						i2++;
					}
				}
			} else {
				builder.append("-1");
			}

			// leather Color
			builder.append(regex);
			if (itM instanceof LeatherArmorMeta) {
				Color c = ((LeatherArmorMeta) itM).getColor();
				builder.append(c.getRed()).append(regex2).append(c.getGreen()).append(regex2).append(c.getBlue());
			} else {
				builder.append("-1");
			}
			// skull owner
			builder.append(regex);
			if (itM instanceof SkullMeta) {
				builder.append(((SkullMeta) itM).getOwner());
			} else {
				builder.append("-1");
			}
		}
		return builder.toString();
	}

	@SuppressWarnings("deprecation")
	public static ItemStack deserialize(String itemS) {
		String data[] = itemS.split(regex);

		ItemStack item = new ItemStack(Material.getMaterial(Integer.parseInt(data[0])), Integer.parseInt(data[1]),
				Byte.parseByte(data[2]));
		item.setDurability(Short.parseShort(data[3]));

		ItemMeta itM = item.getItemMeta();

		if (!data[4].equals("-1")) {
			String enchData[] = data[4].split(regex3);
			for (String ench : enchData) {
				String e[] = ench.split(regex2);
				itM.addEnchant(Enchantment.getByName(e[0]), Integer.parseInt(e[1]), true);
			}
		}

		if (data.length >= 6) {
			if (!data[5].equals("-1"))
				itM.setDisplayName(data[5]);

			if (!data[6].equals("-1"))
				itM.setLore(Arrays.asList(data[6].split(regex2)));

			if (itM instanceof EnchantmentStorageMeta) {
				String enchData[] = data[7].split(regex3);
				for (String ench : enchData) {
					String e[] = ench.split(regex2);
					((EnchantmentStorageMeta) itM).addStoredEnchant(Enchantment.getByName(e[0]), Integer.parseInt(e[1]),
							true);
				}
			}

			if (itM instanceof LeatherArmorMeta) {
				String cdata[] = data[8].split(regex2);
				((LeatherArmorMeta) itM).setColor(Color.fromRGB(Integer.parseInt(cdata[0]), Integer.parseInt(cdata[1]),
						Integer.parseInt(cdata[2])));
			}

			if (itM instanceof SkullMeta)
				((SkullMeta) itM).setOwner(data[9]);

		}
		item.setItemMeta(itM);

		return item;
	}

}
