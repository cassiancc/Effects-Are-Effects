//? fabric {
package cc.cassian.effects.fabric;

import cc.cassian.effects.EmbersAndIcicles;
import cc.cassian.effects.registry.ModEffects;
import cc.cassian.effects.registry.ModItems;
import cc.cassian.effects.registry.ModPotions;
import net.fabricmc.api.ModInitializer;
//~ if <26 'creativetab.v1.CreativeModeTabEvents'->'itemgroup.v1.ItemGroupEvents'
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.level.block.Blocks;
//? if =1.21.1 {
/*import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
*///?}
//? if >26.1.2 {
import net.fabricmc.fabric.api.advancement.v1.AdvancementEvents;
import net.minecraft.advancements.predicates.MobEffectsPredicate;
import net.minecraft.advancements.triggers.EffectsChangedTrigger;
//?}
//? if <26.3 && >26 {
/*import net.fabricmc.fabric.api.registry.FabricPotionBrewingBuilder;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
*///?}
//? if >=26.3
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
//? if <26.4 {
import cc.cassian.effects.backport.FrozenCaves;
import cc.cassian.effects.entity.Frostbite;
import cc.cassian.effects.mixin.backport.SpawnPlacementsInvoker;
import cc.cassian.effects.registry.ModEntityTypes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
//?}


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
				// Create a new loot pool that will hold the ice cubes.
				LootPool.Builder pool = LootPool.lootPool()
						// Add ice cubes...
						//~ if <26.3 'ContextIntProviders'->'UniformGenerator'
						.add(LootItem.lootTableItem(ModItems.ICE_BALL).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 3))))
						// ...only if the block would not be dropped.
						.when(InvertedLootItemCondition.invert(silkTouch(provider)));

				// Add the loot pool to the loot table
				builder.withPool(pool);
			}
		});

		DefaultItemComponentEvents.MODIFY.register(context -> {
			if (EmbersAndIcicles.CONFIG.iceCubes) {
				context.modify(ModItems.ICE_BALL, (builder -> {
					builder.set(DataComponents.ITEM_NAME, Component.translatable("item.embers_and_icicles.ice_cube"));
				}));
			}
		});

		//? if <26.4 {
		//~ if <26 'CreativeModeTabEvents.modifyOutputEvent'->'ItemGroupEvents.modifyEntriesEvent'
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(event -> {
			//~ if <26 'insertAfter'->'addAfter'
			event.insertAfter(Items.SNOWBALL, ModItems.ICE_BALL);
		});
		registerFrozenCaves();
		//?}
	}

	//? if <26.4 {
	private static void registerFrozenCaves() {
		FabricDefaultAttributeRegistry.register(ModEntityTypes.FROSTBITE, Frostbite.createAttributes());
		SpawnPlacementsInvoker.embers$register(ModEntityTypes.FROSTBITE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				ModEntityTypes::checkFrostbiteSpawnRules);
		FrozenCaves.FROSTBITE_SPAWNS.forEach((biome, weight) ->
				BiomeModifications.addSpawn(BiomeSelectors.includeByKey(biome), MobCategory.MONSTER, ModEntityTypes.FROSTBITE, weight, 4, 4));

		for (FrozenCaves.TabInsertion insertion : FrozenCaves.tabInsertions()) {
			//~ if <26 'CreativeModeTabEvents.modifyOutputEvent'->'ItemGroupEvents.modifyEntriesEvent'
			CreativeModeTabEvents.modifyOutputEvent(insertion.tab()).register(event -> {
				//~ if <26 'insertAfter'->'addAfter'
				event.insertAfter(insertion.after(), insertion.item());
			});
		}
	}
	//?}
}
//?}
