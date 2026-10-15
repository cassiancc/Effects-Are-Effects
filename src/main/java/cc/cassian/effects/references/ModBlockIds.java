package cc.cassian.effects.references;

import cc.cassian.effects.EmbersAndIcicles;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

public class ModBlockIds {
	//? if <26.4 {
	public static final ResourceKey<Block> BRITTLE_ICE = register("brittle_ice");
	public static final ResourceKey<Block> ICE_CRYSTAL = register("ice_crystal");
	public static final ResourceKey<Block> ICICLE = register("icicle");
	//?}

	private static ResourceKey<Block> register(String path) {
		return ResourceKey.create(Registries.BLOCK, EmbersAndIcicles.of(path));
	}
}
