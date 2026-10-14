package cc.cassian.effects.util;


import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
//? if >26 {
//~ if =26.1 'predicates'->'criterion' {
import net.minecraft.advancements.predicates.EnchantmentPredicate;
import net.minecraft.advancements.predicates.MinMaxBounds;
//~}
import net.minecraft.core.component.predicates.DataComponentPredicates;
import net.minecraft.core.component.predicates.EnchantmentsPredicate;
//?}
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
//? if =1.21.1 {
/*import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
*///?}
//? if >26.1.2 {
import net.minecraft.core.component.predicates.EnchantmentsPredicate;
import net.minecraft.advancements.predicates.EnchantmentPredicate;
import net.minecraft.advancements.predicates.MobEffectsPredicate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
//?}
//? if >=26.3 {
import net.minecraft.world.level.storage.loot.predicates.LootPredicates;
 //?}

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
}
