//? if <26.4 {
package cc.cassian.effects.backport;

import cc.cassian.effects.EmbersAndIcicles;
import cc.cassian.effects.mixin.backport.BlockBehaviourAccessor;
import cc.cassian.effects.registry.ModItems;
import cc.cassian.effects.registry.ModSoundEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.SoundType;

import java.util.List;
import java.util.Map;
//? if <26 {
/*import net.minecraft.world.entity.npc.villager.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
*///?}

public final class FrozenCaves {
	public static final Map<ResourceKey<Biome>, Integer> FROSTBITE_SPAWNS = Map.of(
			biome("frozen_ocean"), 25,
			biome("deep_frozen_ocean"), 25,
			biome("frozen_peaks"), 25,
			biome("frozen_river"), 25,
			biome("ice_spikes"), 25,
			biome("jagged_peaks"), 10,
			biome("snowy_plains"), 10,
			biome("snowy_slopes"), 10
	);

	public record TabInsertion(ResourceKey<CreativeModeTab> tab, Item after, Item item) {
	}

	public static List<TabInsertion> tabInsertions() {
		return List.of(
				new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, Items.BLUE_ICE, ModItems.BRITTLE_ICE),
				new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, Items.POINTED_DRIPSTONE, ModItems.ICICLE),
				new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, Items.AMETHYST_CLUSTER, ModItems.ICE_CRYSTAL),
				new TabInsertion(CreativeModeTabs.INGREDIENTS, Items.SNOWBALL, ModItems.ICE_BALL),
				new TabInsertion(CreativeModeTabs.SPAWN_EGGS, Items.DROWNED_SPAWN_EGG, ModItems.FROSTBITE_SPAWN_EGG)
		);
	}

	//? if <26 {
	/*public static List<VillagerTrades.ItemListing> wanderingTraderTrades() {
		return List.of(
				(trader, random) -> offer(2, new ItemStack(ModItems.ICE_CRYSTAL)),
				(trader, random) -> offer(1, new ItemStack(ModItems.ICICLE, 2))
		);
	}

	private static MerchantOffer offer(final int emeralds, final ItemStack result) {
		return new MerchantOffer(new ItemCost(Items.EMERALD, emeralds), result, 5, 1, 0.05F);
	}
	*///?}

	public static void setup() {
		if (EmbersAndIcicles.CONFIG.newIceSounds) {
			setSound(Blocks.ICE, ModSoundEvents.ICE);
			setSound(Blocks.PACKED_ICE, ModSoundEvents.PACKED_ICE);
			setSound(Blocks.BLUE_ICE, ModSoundEvents.BLUE_ICE);
			setSound(Blocks.FROSTED_ICE, ModSoundEvents.FROSTED_ICE);
		}

		DispenserBlock.registerProjectileBehavior(ModItems.ICE_BALL);
	}

	private static void setSound(final Block block, final SoundType soundType) {
		((BlockBehaviourAccessor) block).embers$setSoundType(soundType);
	}

	private static ResourceKey<Biome> biome(final String name) {
		return ResourceKey.create(Registries.BIOME, Identifier.withDefaultNamespace(name));
	}
}
//?}
