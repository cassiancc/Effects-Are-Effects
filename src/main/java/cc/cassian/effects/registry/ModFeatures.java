package cc.cassian.effects.registry;

//? if <26.4 {
import cc.cassian.effects.EmbersAndIcicles;
import cc.cassian.effects.worldgen.CrevasseFeature;
import cc.cassian.effects.worldgen.LargeSpeleothemFeature;
import cc.cassian.effects.worldgen.SpeleothemClusterFeature;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
//?}
//? if <26.3
//import cc.cassian.effects.worldgen.IceCaveOreFeature;

public class ModFeatures {
	//? if <26.4 {
	//? if >=26.3 {
	static {
		Registry.register(BuiltInRegistries.FEATURE_TYPE, EmbersAndIcicles.of("crevasse"), CrevasseFeature.CODEC);
		Registry.register(BuiltInRegistries.FEATURE_TYPE, EmbersAndIcicles.of("speleothem_cluster"), SpeleothemClusterFeature.CODEC);
		Registry.register(BuiltInRegistries.FEATURE_TYPE, EmbersAndIcicles.of("large_speleothem"), LargeSpeleothemFeature.CODEC);
	}
	//?} else {
	/*public static final CrevasseFeature CREVASSE = Registry.register(BuiltInRegistries.FEATURE, EmbersAndIcicles.of("crevasse"), new CrevasseFeature());
	public static final SpeleothemClusterFeature SPELEOTHEM_CLUSTER = Registry.register(BuiltInRegistries.FEATURE, EmbersAndIcicles.of("speleothem_cluster"), new SpeleothemClusterFeature());
	public static final LargeSpeleothemFeature LARGE_SPELEOTHEM = Registry.register(BuiltInRegistries.FEATURE, EmbersAndIcicles.of("large_speleothem"), new LargeSpeleothemFeature());
	public static final IceCaveOreFeature ICE_CAVE_ORE = Registry.register(BuiltInRegistries.FEATURE, EmbersAndIcicles.of("ice_cave_ore"), new IceCaveOreFeature());
	*///?}
	//?}

	public static void init() {
	}
}
