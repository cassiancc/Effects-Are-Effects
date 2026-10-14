//? neoforge {
/*package cc.cassian.effects.neoforge;

import cc.cassian.effects.EffectsAreEffects;
import cc.cassian.effects.registry.ModItems;
import cc.cassian.effects.registry.ModPotions;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@EventBusSubscriber
public class NeoForgeEntrypoint {
	@SubscribeEvent
	public static void register(RegisterEvent event) {
		if (event.getRegistryKey().equals(Registries.BLOCK)) {
			EffectsAreEffects.onInitialize();
		}
	}

	@SubscribeEvent
	public static void register(RegisterBrewingRecipesEvent event) {
		event.getBuilder().addStartMix(
				// Ingredient
				ModItems.ICE_BALL,
				// Output potion.
				ModPotions.FREEZING
		);
	}
}
*///?}