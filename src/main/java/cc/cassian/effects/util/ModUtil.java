package cc.cassian.effects.util;

//? fabric {
import cc.cassian.effects.EmbersAndIcicles;
import net.fabricmc.loader.api.FabricLoader;
//?} else {
/*import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;
*///?}
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

//? if >=26.3 {
import net.minecraft.world.level.storage.loot.predicates.LootPredicates;
import net.minecraft.core.Holder;
//?} else if >26 {
/*import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.core.component.predicates.DataComponentPredicates;
import net.minecraft.core.component.predicates.EnchantmentsPredicate;
//~ if <26.2 'predicates'->'criterion' {
import net.minecraft.advancements.predicates.EnchantmentPredicate;
import net.minecraft.advancements.predicates.MinMaxBounds;
//~}
*///?} else {
/*import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
*///?}

import java.nio.file.Path;
import java.util.List;

public class ModUtil {
	//~ if >26.2 'LootItemCondition.Builder'->'Holder.Reference<LootItemCondition>'
	public static Holder.Reference<LootItemCondition> silkTouch(HolderLookup.Provider provider) {
		return
		//? if >=26.3 {
		provider.lookupOrThrow(Registries.PREDICATE).getOrThrow(LootPredicates.TOOL_CAN_SILK_TOUCH);
		 //?} else if >26 {
		/*MatchTool.toolMatches(
				//~ if =26.1 'predicates'->'criterion' {
				net.minecraft.advancements.predicates.ItemPredicate.Builder.item()
						.withComponents(
								net.minecraft.advancements.predicates.DataComponentMatchers.Builder.components()
				//~}
										.partial(
												DataComponentPredicates.ENCHANTMENTS,
												EnchantmentsPredicate.enchantments(
														List.of(
																new EnchantmentPredicate(provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH), MinMaxBounds.Ints.atLeast(1))
														)
												)
										)
										.build()
						)
		);
		*///?} else {
				/*MatchTool.toolMatches(
						net.minecraft.advancements.criterion.ItemPredicate.Builder.item()
								.withSubPredicate(
										net.minecraft.advancements.criterion.ItemSubPredicates.ENCHANTMENTS,
										net.minecraft.advancements.criterion.ItemEnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH), MinMaxBounds.Ints.atLeast(1))))
								)
				);
		*///?}
	}

	public static Path getConfigDir() {
		//? fabric
		return FabricLoader.getInstance().getConfigDir();
		//? neoforge
		//return FMLPaths.CONFIGDIR.get();
	}

	public static boolean isModLoaded(String mod) {
		//? fabric
		return FabricLoader.getInstance().isModLoaded(mod);
		//? neoforge
		//return ModList.get().isLoaded(mod);
	}

	public static void addAlias(Registry<?> registry, String path) {
		registry.addAlias(EmbersAndIcicles.of(path), Identifier.withDefaultNamespace(path));
	}
}
