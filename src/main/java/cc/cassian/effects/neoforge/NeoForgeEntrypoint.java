//? neoforge {
/*package cc.cassian.effects.neoforge;

import cc.cassian.effects.EmbersAndIcicles;
import cc.cassian.effects.registry.ModItems;
import cc.cassian.effects.registry.ModPotions;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;
//? if <26.3 {
/^import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
^///?}

@EventBusSubscriber
public class NeoForgeEntrypoint {
	@SubscribeEvent
	public static void register(RegisterEvent event) {
		if (event.getRegistryKey().equals(Registries.BLOCK)) {
			EmbersAndIcicles.onInitialize();
		}
	}

	//? if <26.3 {
	/^@SubscribeEvent
	public static void register(RegisterBrewingRecipesEvent event) {
		event.getBuilder().addStartMix(
				// Ingredient
				ModItems.ICE_BALL,
				// Output potion.
				ModPotions.FREEZING
		);
	}
	^///?}

}
*///?}
