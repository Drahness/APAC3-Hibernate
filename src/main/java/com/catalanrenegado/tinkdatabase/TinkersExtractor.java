package com.catalanrenegado.tinkdatabase;

import com.catalanrenegado.tinkdatabase.database.DatabaseConnection;
import com.catalanrenegado.tinkdatabase.identity.ItemID;
import com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.*;
import com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.stats.EntityBowHead;
import com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.stats.EntityFletching;
import com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.stats.EntityHandle;
import com.catalanrenegado.tinkdatabase.relations.PartTypes_Materials;
import org.hibernate.Session;
import org.hibernate.dialect.Database;

public class TinkersExtractor { // NO_UCD (unused code)
	public static final String MODID = "tinkdatabase";
	public static final String NAME = "Tinkers Database";
	public static final String VERSION = "0.9";

	public static void main(String[] args) {
		DatabaseConnection conn = new DatabaseConnection.Builder().setProperty("hibernate.hbm2ddl.auto", "update")
				.setProperty("hibernate.connection.url",
						"jdbc:mysql://localhost/tinkerdata?useLegacyDatetimeCode=false&serverTimezone=UTC")
				.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
				.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57InnoDBDialect")
				.setProperty("hibernate.connection.username", "root")
				.setProperty("hibernate.connection.password", "root")
				.addAnnotatedClass(EntityAtlasSprite.class)
				.addAnnotatedClass(SpriteTConstructBase.class)
				.addAnnotatedClass(EntityMaterial.class)
				.addAnnotatedClass(EntityMod.class)
				.addAnnotatedClass(EntityToolCore.class)
				.addAnnotatedClass(EntityBowCore.class)
				.addAnnotatedClass(EntityBowHead.class)
				.addAnnotatedClass(EntityHandle.class)
				.addAnnotatedClass(EntityFletching.class)
				.addAnnotatedClass(EntityComponent.class)
				.addAnnotatedClass(ItemID.class)
				.addAnnotatedClass(EntityAtlasSprite.class)
				.addAnnotatedClass(PartTypes_Materials.class)

				.build();
		Session s = conn.getSession();
		s.beginTransaction();
		s.createQuery("FROM AtlasSprites");
	}
	/*@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MODULE_HUB.add(ModuleTConstruct.getInstance());
		MODULE_HUB.add(ModulePlusTic.getInstance());
		MODULE_HUB.add(ModuleConArmory.getInstance());
		proxy.init(event);
		proxy.extract(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		connection = proxy.openFactory();
		proxy.extract(event);
	}

	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {
		proxy.extract(event);
		proxy.createInstances(event);*/
		/*log.info(Pattern.getTextureIdentifier(TinkerTools.arrowHead));
		log.info(Pattern.getTextureIdentifier(TinkerTools.arrowShaft));
		log.info(Pattern.getTextureIdentifier(TinkerTools.axeHead));*/
		/*
		
		
		for (Item iterable_element : TinkerRegistry.getPatternItems()) {
			log.info(iterable_element);
			//log.info(Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(iterable_element.getRegistryName().toString())); // null
		}
		for (Item iterable_element : TinkerRegistry.getCastItems()) {
			log.info(iterable_element);
			//log.info(Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(iterable_element.getRegistryName().toString())); // null
		}
		for (ToolCore iterable_element : TinkerRegistry.getToolForgeCrafting()) {
			log.info(iterable_element);
			//log.info(Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(iterable_element.getRegistryName().toString())); // null
			log.info(Pattern.getTextureIdentifier(iterable_element));
		}
		*/
		//log.info(Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(""));
		
		//log.info(Instancer.getEntityNullable(AbstractCore.class, new EntityBowCore(TinkerRangedWeapons.crossBow)));
		// funciona tal cual
		

		/*
		Collection<Material> heads = TinkerRegistry.getAllMaterialsWithStats(MaterialTypes.HEAD);
		Collection<Material> extra =  TinkerRegistry.getAllMaterialsWithStats(MaterialTypes.EXTRA);
		Collection<Material> handle = TinkerRegistry.getAllMaterialsWithStats(MaterialTypes.HANDLE);
		
		Random r = new Random();
		
		List<Material> matList = Lists.newArrayList();
		
		int i,
		counter = 0;
		
		i = r.nextInt(heads.size());
		for (Material material : heads) {
			if(counter == i) {
				matList.add(material);
				counter = 0;
			}
			else {
				counter++;
			}
		}
		
		i = r.nextInt(extra.size());
		for (Material material : extra) {
			if(counter == i) {
				matList.add(material);
				counter = 0;
			}
			else {
				counter++;
			}
		}
		
		i = r.nextInt(handle.size());
		for (Material material : handle) {
			if(counter == i) {
				matList.add(material);
				counter = 0;
			}
			else {
				counter++;
			}
		}
		
		Writer.writeToFile(matList.toString(), "tagTestings");
		
		NBTTagCompound toolNBT = ((LongSword) TinkerMeleeWeapons.longSword).buildTagData(matList).get();
		
		//toolNBT.
		
		Writer.writeToFile(toolNBT.toString(), "tagTestings");
		log.info(matList);
		log.info(toolNBT);
		TinkerRegistry.getToolForgeCrafting();
*/
	/*}
	private static ItemStack test;
	@EventHandler
	public void serverStarting(FMLServerStartingEvent e) {
		Random r = new Random();
		List<Material> mats = Utils.getAllMaterials();
		int cobaltI = 0;
		for (int i = 0; i < mats.size(); i++) {
			if(mats.get(i).identifier.equalsIgnoreCase("cobalt")) {
				cobaltI = i;
				break;
			}
		}
		List<Material> cobalt = Lists.newArrayList();
		cobalt.add(mats.get(cobaltI));
		cobalt.add(mats.get(cobaltI));
		cobalt.add(mats.get(cobaltI));
		cobalt.add(mats.get(cobaltI));
		ItemStack test = TinkerMeleeWeapons.cleaver.buildItem(cobalt);
		log.info(ToolHelper.getActualAttack(test));
		log.info(test.getTagCompound());
		log.info(ToolHelper.getActualDamage(test, null));
	}
	@EventHandler
	public void serverStarted(FMLServerStartedEvent e) {
		Random r = new Random();
		List<Material> mats = Utils.getAllMaterials();
		int cobaltI = 0;
		for (int i = 0; i < mats.size(); i++) {
			if(mats.get(i).identifier.equalsIgnoreCase("cobalt")) {
				cobaltI = i;
				break;
			}
		}
		List<Material> cobalt = Lists.newArrayList();
		cobalt.add(mats.get(cobaltI));
		cobalt.add(mats.get(cobaltI));
		cobalt.add(mats.get(cobaltI));
		cobalt.add(mats.get(cobaltI));
		ItemStack test = TinkerMeleeWeapons.cleaver.buildItem(cobalt);
		log.info(ToolHelper.getActualAttack(test));
		log.info(test.getTagCompound());
		log.info(ToolHelper.getActualDamage(test, null));
		log.info(test + " itemstack");
		log.info(Minecraft.getMinecraft().player + " is null?");
		log.info(ToolHelper.getActualDamage(test, Minecraft.getMinecraft().player));
		//SharedMonsterAttributes.ATTACK_DAMAGE
	}*/
}
