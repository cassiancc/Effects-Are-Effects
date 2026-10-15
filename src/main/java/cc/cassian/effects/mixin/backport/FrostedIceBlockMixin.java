//? if <26.4 {
package cc.cassian.effects.mixin.backport;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FrostedIceBlock.class)
public abstract class FrostedIceBlockMixin {
	@Inject(method = "slightlyMelt", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z",
			shift = At.Shift.AFTER))
	private void embers$emitVibration(BlockState state, Level level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
	}
}
//?}
