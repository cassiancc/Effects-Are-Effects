package cc.cassian.effects.registry;

import cc.cassian.effects.EmbersAndIcicles;
//? fabric
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModParticleTypes {
	//? if <26.4 {
	public static final SimpleParticleType FREEZING = register("freezing", false);


	private static SimpleParticleType register(final String name, final boolean overrideLimiter) {
		//~ if fabric 'new SimpleParticleType'-> 'FabricParticleTypes.simple'
		return Registry.register(BuiltInRegistries.PARTICLE_TYPE, EmbersAndIcicles.of(name), FabricParticleTypes.simple(overrideLimiter));
	}
	//?}

	public static void init() {

	}
}
