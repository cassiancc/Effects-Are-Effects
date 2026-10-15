//? if <26.4 {
package cc.cassian.effects.mixin.backport;

import cc.cassian.effects.EmbersAndIcicles;
import cc.cassian.effects.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LiquidBlock.class)
public abstract class LiquidBlockMixin {
	@Shadow
	@Final
	protected FlowingFluid fluid;

	@Inject(method = "shouldSpreadLiquid", at = @At("HEAD"), cancellable = true)
	private void embers$freezeNextToBlueIce(Level level, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (!EmbersAndIcicles.CONFIG.blueIceMakesBrittleIce || this.fluid != Fluids.WATER) {
			return;
		}

		for (Direction direction : Direction.Plane.HORIZONTAL) {
			if (level.getBlockState(pos.relative(direction)).is(Blocks.BLUE_ICE)) {
				level.setBlockAndUpdate(pos, ModBlocks.BRITTLE_ICE.defaultBlockState());
				cir.setReturnValue(false);
				return;
			}
		}
	}
}
//?}
