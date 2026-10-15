//? if <26.4 {
package cc.cassian.effects.backport;

import cc.cassian.effects.EmbersAndIcicles;
import cc.cassian.effects.mixin.backport.BlockBehaviourAccessor;
import cc.cassian.effects.registry.ModItems;
import cc.cassian.effects.registry.ModSoundEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.SoundType;

import java.util.List;

public final class FrozenCaves {
	public record TabInsertion(ResourceKey<CreativeModeTab> tab, Item after, Item item) {
	}

	public static List<TabInsertion> tabInsertions() {
		return List.of(
				new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, Items.BLUE_ICE, ModItems.BRITTLE_ICE),
				new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, Items.POINTED_DRIPSTONE, ModItems.ICICLE),
				new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, Items.AMETHYST_CLUSTER, ModItems.ICE_CRYSTAL),
				new TabInsertion(CreativeModeTabs.INGREDIENTS, Items.SNOWBALL, ModItems.ICE_BALL)
		);
	}

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
}
//?}
