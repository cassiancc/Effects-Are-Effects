package cc.cassian.effects;

import cc.cassian.effects.registry.ModEffects;
import cc.cassian.effects.registry.ModPotions;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.advancement.v1.AdvancementEvents;
import net.minecraft.advancements.predicates.MobEffectsPredicate;
import net.minecraft.advancements.triggers.EffectsChangedTrigger;
import net.minecraft.resources.Identifier;

import net.minecraft.world.effect.MobEffects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EffectsAreEffects implements ModInitializer {
	public static final String MOD_ID = "effects_are_effects";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModEffects.init();
		ModPotions.init();
		AdvancementEvents.MODIFY.register((identifier, builder, advancementSource, provider) -> {
			String name = "effects_are_effects";
			if (identifier.getNamespace().equals("minecraft")) {
				if (identifier.getPath().equals("nether/all_potions") || identifier.getPath().equals("nether/all_effects")) {
					builder.addCriterion(name, EffectsChangedTrigger.TriggerInstance.hasEffects(
									MobEffectsPredicate.Builder.effects()
											.and(MobEffects.FREEZING)
											.and(ModEffects.BURNING)
							)
					);
					builder.requireCriterion(name);
				}
			}
		});
	}

	public static Identifier of(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
