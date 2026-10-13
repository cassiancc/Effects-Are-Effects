package cc.cassian.effects.references;

import cc.cassian.effects.EffectsAreEffects;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.alchemy.Potion;

public class ModPotionIds {
	public static final ResourceKey<Potion> BURNING = register("burning");
	public static final ResourceKey<Potion> LONG_BURNING = register("long_burning");

	private static ResourceKey<Potion> register(final String name) {
		return ResourceKey.create(Registries.POTION, EffectsAreEffects.of(name));
	}
}
