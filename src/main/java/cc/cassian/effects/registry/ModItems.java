package cc.cassian.effects.registry;

//? if <26
//import cc.cassian.effects.EmbersAndIcicles;
//? if <26.4
import cc.cassian.effects.item.IceBallItem;
import cc.cassian.effects.references.ModItemIds;
import cc.cassian.effects.util.ModUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

public class ModItems {
	//? if <26.4 {
	public static final Item ICE_BALL = registerItem(ModItemIds.ICE_BALL, IceBallItem::new, new Item.Properties().stacksTo(16));
	public static final Item BRITTLE_ICE = registerBlock(ModItemIds.BRITTLE_ICE, ModBlocks.BRITTLE_ICE);
	public static final Item ICE_CRYSTAL = registerBlock(ModItemIds.ICE_CRYSTAL, ModBlocks.ICE_CRYSTAL);
	public static final Item ICICLE = registerBlock(ModItemIds.ICICLE, ModBlocks.ICICLE);
	//? if >=26 {
	public static final Item FROSTBITE_SPAWN_EGG = registerItem(ModItemIds.FROSTBITE_SPAWN_EGG, SpawnEggItem::new,
			new Item.Properties().spawnEgg(ModEntityTypes.FROSTBITE));
	//?} else {
	
	/*public static final boolean LEGACY_SPAWN_EGG = EmbersAndIcicles.CONFIG.legacySpawnEgg;
	public static final Item FROSTBITE_SPAWN_EGG = registerItem(ModItemIds.FROSTBITE_SPAWN_EGG,
			properties -> new SpawnEggItem(ModEntityTypes.FROSTBITE, LEGACY_SPAWN_EGG ? 0x5A8A84 : 0xFFFFFF, LEGACY_SPAWN_EGG ? 0xC8E6ED : 0xFFFFFF, properties), new Item.Properties());
	*///?}
	//?} else {
	/*public static final Item ICE_BALL = Items.ICE_BALL;
	public static final Item BRITTLE_ICE = Items.BRITTLE_ICE;
	public static final Item ICE_CRYSTAL = Items.ICE_CRYSTAL;
	public static final Item ICICLE = Items.ICICLE;
	public static final Item FROSTBITE_SPAWN_EGG = Items.FROSTBITE_SPAWN_EGG;
	*///?}

	private static Item registerItem(final ResourceKey<Item> id, final Function<Item.Properties, Item> itemFactory, final Item.Properties properties) {
		Item item = itemFactory.apply(properties
				//? if >1.21.2
				.setId(id)
		);
		return Registry.register(BuiltInRegistries.ITEM, id, item);
	}

	private static Item registerBlock(final ResourceKey<Item> id, final Block block) {
		return registerItem(id, properties -> new BlockItem(block, properties), new Item.Properties()
				//? if >1.21.2
				.useBlockDescriptionPrefix()
		);
	}

	public static void init() {
		//? if >26.3 {
		/*ModUtil.addAlias(BuiltInRegistries.ITEM, "ice_ball");
		ModUtil.addAlias(BuiltInRegistries.ITEM, "brittle_ice");
		ModUtil.addAlias(BuiltInRegistries.ITEM, "ice_crystal");
		ModUtil.addAlias(BuiltInRegistries.ITEM, "icicle");
		ModUtil.addAlias(BuiltInRegistries.ITEM, "frostbite_spawn_egg");
		*///?}
	}
}
