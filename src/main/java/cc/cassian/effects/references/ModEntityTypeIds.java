package cc.cassian.effects.references;

import cc.cassian.effects.EffectsAreEffects;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;

public class ModEntityTypeIds {
	//? if <26.4
	public static final ResourceKey<EntityType<?>> ICE_BALL = register("ice_ball");

	private static ResourceKey<EntityType<?>> register(String path) {
		return ResourceKey.create(Registries.ENTITY_TYPE, EffectsAreEffects.of(path));
	}
}
