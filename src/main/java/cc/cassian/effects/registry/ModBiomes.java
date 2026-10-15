//? if <26.4 {
package cc.cassian.effects.registry;

import cc.cassian.effects.EmbersAndIcicles;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class ModBiomes {
	public static final ResourceKey<Biome> ICE_CAVES = ResourceKey.create(Registries.BIOME, EmbersAndIcicles.of("ice_caves"));
	//? if >=26.3 {
	public static final ResourceKey<NormalNoise> ICE_CAVE_GRADIENT = ResourceKey.create(Registries.NOISE, EmbersAndIcicles.of("ice_cave_gradient"));
	//?} else {
	/*public static final ResourceKey<NormalNoise.NoiseParameters> ICE_CAVE_GRADIENT = ResourceKey.create(Registries.NOISE, EmbersAndIcicles.of("ice_cave_gradient"));
	*///?}
}
//?}
