package com.catalanrenegado.tinkdatabase.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.cfg.Configuration;
/*
import com.google.common.collect.Lists;

import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.materials.ArmorMaterialType;
import landmaster.plustic.tools.stats.BatteryCellMaterialStats;
import landmaster.plustic.tools.stats.LaserMediumMaterialStats;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.common.ModContainer;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.client.CustomTextureCreator;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialGUI;
import slimeknights.tconstruct.library.materials.MaterialTypes;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.tools.ToolCore;

public final class Utils {
	private static List<Material> materials;

	public static List<Material> getAllMaterials() {
		if (materials == null) {
			materials = Lists.newArrayList();
			materials.addAll(TinkerRegistry.getAllMaterials());
			for (int i = 0; i < materials.size(); i++) {
				if (materials.get(i) instanceof MaterialGUI) {
					materials.remove(i);
				}
			}
		}
		return materials;
	}

	public static String getConArmCore() {
		return ArmorMaterialType.CORE;
	}

	public static String getConArmMODID() {
		return "conarm";
	}

	public static String getConArmPlates() {
		return ArmorMaterialType.PLATES;
	}

	public static String getConArmTrim() {
		return ArmorMaterialType.TRIM;
	}
	
	private static List<ModContainer> mods;
	public static List<ModContainer> getMods() {
		if(mods == null) {
			mods = net.minecraftforge.fml.common.Loader.instance().getModList();
		}
		return mods;
	}
	public static ModContainer getModContainer(String domain) {
		for (ModContainer modCont : getMods()) {
			if (Finder.getIdentifier(modCont).equals(domain)) {
				return modCont;
			}
		}
		throw new RuntimeException("Mod "+domain+" not found.");
	}

	public static String getPlusticBatteryCell() {
		return BatteryCellMaterialStats.TYPE;
	}

	public static String getPlusticLaserMedium() {
		return LaserMediumMaterialStats.TYPE;
	}

	public static String getPlusticMODID() {
		return "plustic";
	}

	public static String getTconstructBow() {
		return MaterialTypes.BOW;
	}

	public static String getTconstructBowString() {
		return MaterialTypes.BOWSTRING;
	}

	public static String getTconstructExtra() {
		return MaterialTypes.EXTRA;
	}
	public static String getTconstructFletching() {
		return MaterialTypes.FLETCHING;
	}

	public static String getTconstructHandle() {
		return MaterialTypes.HANDLE;
	}

	public static String getTconstructHead() {
		return MaterialTypes.HEAD;
	}

	public static String getTconstructMODID() {
		return "tconstruct";
	}

	public static String getTconstructShaft() {
		return MaterialTypes.SHAFT;
	}

	private static Configuration getDefaultConfiguration() {
		return new Configuration()
				.setProperty("hibernate.show_sql", "true")
				.setProperty("hibernate.hbm2ddl.auto", "create-drop")
				// TODO cambiar para que la configuracion salga de un archivo de configuracion
				.setProperty("hibernate.connection.url",
						"jdbc:mysql://localhost/tinkerdata?useLegacyDatetimeCode=false&serverTimezone=UTC")
				.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
				.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57InnoDBDialect")
				.setProperty("hibernate.connection.username", "root")
				.setProperty("hibernate.connection.password", "root");
	}

	public static Configuration getConfigurationWithCreate() {
		return getDefaultConfiguration().setProperty("hibernate.hbm2ddl.auto", "update");
	}
	
	
	private static Set<ToolCore> toolCores;
	public static Set<ToolCore> getToolCores() {
		if(toolCores == null) {
			toolCores = TinkerRegistry.getToolForgeCrafting();
		}
		return toolCores;
	}
	
	private static Set<ArmorCore> armorCores;
	public static Set<ArmorCore> getArmorCores() {
		if(armorCores == null) {
			armorCores = ArmoryRegistry.getArmor();
		}
		return armorCores;
	}
	public static Map<String, Map<String, TextureAtlasSprite>> getSprites() {
		return CustomTextureCreator.sprites;
	}
	private static Collection<IModifier> modifiers;
	public static Collection<IModifier> getModifiers() {
		if(modifiers == null) {
			modifiers = TinkerRegistry.getAllModifiers();
		}
		return modifiers;
	}
}
*/