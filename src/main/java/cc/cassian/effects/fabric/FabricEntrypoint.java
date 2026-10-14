//? fabric {
package cc.cassian.effects.fabric;

import cc.cassian.effects.EmbersAndIcicles;
import cc.cassian.effects.registry.ModEffects;
import cc.cassian.effects.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.level.block.Blocks;
//? if =1.21.1 {
/*import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
*///?}
//? if >26.1.2 {
import net.fabricmc.fabric.api.advancement.v1.AdvancementEvents;
import net.minecraft.advancements.predicates.MobEffectsPredicate;
import net.minecraft.advancements.triggers.EffectsChangedTrigger;
//?}
//? if <26.3 && >26 {
/*import net.fabricmc.fabric.api.registry.FabricPotionBrewingBuilder;
*///?}

import static cc.cassian.effects.util.ModUtil.silkTouch;

public class FabricEntrypoint implements ModInitializer {
	@Override
	public void onInitialize() {

 		EmbersAndIcicles.onInitialize();
		//? if >26.1.2 {
		AdvancementEvents.MODIFY.register((identifier, builder, advancementSource, provider) -> {
			String name = "embers_and_icicles";
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


		LootTableEvents.MODIFY.register((key, builder, source, provider) -> {
			// If the loot table is for Ice, and it is not overridden by a user:
			if (EmbersAndIcicles.CONFIG.iceDropsIceBalls && Blocks.ICE.getLootTable()
					//? if >26
					.orElseThrow()
					== key && source.isBuiltin()) {
				// Create a new loot pool that will hold the diamonds.
				LootPool.Builder pool = LootPool.lootPool()
						// Add diamonds...
						.add(LootItem.lootTableItem(ModItems.ICE_BALL))
						// ...only if the block would not be dropped.
						.when(InvertedLootItemCondition.invert(silkTouch(provider)));

				// Add the loot pool to the loot table
				builder.withPool(pool);
			}
		});
	}
}
//?}
