package cc.cassian.effects.references;

import cc.cassian.effects.EmbersAndIcicles;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class ModItemIds {
	//? if <26.4 {
	public static final ResourceKey<Item> ICE_BALL = register("ice_ball");
	public static final ResourceKey<Item> BRITTLE_ICE = register("brittle_ice");
	public static final ResourceKey<Item> ICE_CRYSTAL = register("ice_crystal");
	public static final ResourceKey<Item> ICICLE = register("icicle");
	public static final ResourceKey<Item> FROSTBITE_SPAWN_EGG = register("frostbite_spawn_egg");
	//?}

	private static ResourceKey<Item> register(String path) {
		return ResourceKey.create(Registries.ITEM, EmbersAndIcicles.of(path));
	}

}
