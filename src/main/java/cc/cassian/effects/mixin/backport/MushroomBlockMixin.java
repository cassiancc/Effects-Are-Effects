//? if <26.4 {
package cc.cassian.effects.mixin.backport;

import cc.cassian.effects.EmbersAndIcicles;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MushroomBlock.class)
public abstract class MushroomBlockMixin {
	@Unique
	private static final ThreadLocal<Boolean> EMBERS$SPREADING = ThreadLocal.withInitial(() -> false);

	@Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
	private void embers$placeAnywhere(BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (EmbersAndIcicles.CONFIG.mushroomPlacement && !EMBERS$SPREADING.get() && Block.canSupportCenter(level, pos.below(), Direction.UP)) {
			cir.setReturnValue(true);
		}
	}

	@WrapOperation(method = "randomTick", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;canSurvive(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z"))
	private boolean embers$keepSpreadingRules(BlockState state, LevelReader level, BlockPos pos, Operation<Boolean> original) {
		EMBERS$SPREADING.set(true);
		try {
			return original.call(state, level, pos);
		} finally {
			EMBERS$SPREADING.set(false);
		}
	}
}
//?}
