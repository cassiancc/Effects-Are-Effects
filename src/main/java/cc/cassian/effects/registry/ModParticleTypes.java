package cc.cassian.effects.registry;

import cc.cassian.effects.EffectsAreEffects;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModParticleTypes {
	//? if <26.4 {
	public static final SimpleParticleType FREEZING = register("freezing", false);


	private static SimpleParticleType register(final String name, final boolean overrideLimiter) {
		return Registry.register(BuiltInRegistries.PARTICLE_TYPE, EffectsAreEffects.of(name), FabricParticleTypes.simple(overrideLimiter));
	}
	//?}

	public static void init() {

	}
}
