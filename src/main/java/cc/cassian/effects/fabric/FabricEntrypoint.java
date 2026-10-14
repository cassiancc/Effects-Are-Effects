//? fabric {
package cc.cassian.effects.fabric;

import cc.cassian.effects.EffectsAreEffects;
import cc.cassian.effects.registry.ModEffects;
import cc.cassian.effects.registry.ModItems;
import cc.cassian.effects.registry.ModPotions;
import net.fabricmc.api.ModInitializer;
//? if =1.21.1 {
/*import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
*///?}
//? if >26.1.2 {
import net.fabricmc.fabric.api.advancement.v1.AdvancementEvents;
import net.minecraft.advancements.predicates.MobEffectsPredicate;
import net.minecraft.advancements.triggers.EffectsChangedTrigger;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potions;
//?}
//? if <26.3 && >26 {
/*import net.fabricmc.fabric.api.registry.FabricPotionBrewingBuilder;
*///?}

public class FabricEntrypoint implements ModInitializer {
	@Override
	public void onInitialize() {

 		EffectsAreEffects.onInitialize();
		//? if >26.1.2 {
		AdvancementEvents.MODIFY.register((identifier, builder, advancementSource, provider) -> {
			String name = "effects_are_effects";
			if (identifier.getNamespace().equals("minecraft")) {
				if (identifier.getPath().equals("nether/all_potions") || identifier.getPath().equals("nether/all_effects")) {
					builder.addCriterion(name, EffectsChangedTrigger.TriggerInstance.hasEffects(
									MobEffectsPredicate.Builder.effects()
											.and(ModEffects.FREEZING)
											.and(ModEffects.BURNING)
							)
					);
					builder.requireCriterion(name);
				}
			}
		});
		//?}
		//? if <26.3 && >26 {
		/*FabricPotionBrewingBuilder.BUILD.register(builder -> {
			builder.addStartMix(
					// Ingredient
					ModItems.ICE_BALL,
					// Output potion.
					ModPotions.FREEZING
			);
		});
		*///?}
		//? if =1.21.1 {
		/*FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
			builder.addStartMix(
					// Ingredient
					ModItems.ICE_BALL,
					// Output potion.
					ModPotions.FREEZING
			);
		});
		*///?}
	}
}
//?}
