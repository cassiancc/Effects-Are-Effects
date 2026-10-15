//? if <26.4 {
package cc.cassian.effects.backport;

import cc.cassian.effects.EmbersAndIcicles;
import cc.cassian.effects.mixin.backport.BlockBehaviourAccessor;
import cc.cassian.effects.registry.ModSoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;

public final class FrozenCaves {
	public static void setup() {
		if (EmbersAndIcicles.CONFIG.newIceSounds) {
			setSound(Blocks.ICE, ModSoundEvents.ICE);
			setSound(Blocks.PACKED_ICE, ModSoundEvents.PACKED_ICE);
			setSound(Blocks.BLUE_ICE, ModSoundEvents.BLUE_ICE);
			setSound(Blocks.FROSTED_ICE, ModSoundEvents.FROSTED_ICE);
		}
	}

	private static void setSound(final Block block, final SoundType soundType) {
		((BlockBehaviourAccessor) block).embers$setSoundType(soundType);
	}
}
//?}
