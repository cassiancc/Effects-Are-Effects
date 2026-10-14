package cc.cassian.effects.references;

import cc.cassian.effects.EffectsAreEffects;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class ModItemIds {
	public static final ResourceKey<Item> ICE_BALL = register("ice_ball");

	private static ResourceKey<Item> register(String path) {
		return ResourceKey.create(Registries.ITEM, EffectsAreEffects.of(path));
	}

}
