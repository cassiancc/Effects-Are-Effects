//? if <26 {
/*package cc.cassian.effects.client;

import cc.cassian.effects.EmbersAndIcicles;
import cc.cassian.effects.registry.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;

public class LegacySpawnEgg {
	// Swaps the Frostbite Spawn Egg to its old two-tone model, see the legacySpawnEgg config option.
	public static void register() {
		ItemProperties.register(ModItems.FROSTBITE_SPAWN_EGG, EmbersAndIcicles.of("legacy_spawn_egg"),
				(stack, level, entity, seed) -> ModItems.LEGACY_SPAWN_EGG ? 1 : 0);
	}
}
*///?}
