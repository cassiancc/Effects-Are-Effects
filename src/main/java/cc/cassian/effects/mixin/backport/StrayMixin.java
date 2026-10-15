//? if <26.4 {
package cc.cassian.effects.mixin.backport;

import cc.cassian.effects.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.skeleton.Stray;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//~ if >=26 'MobSpawnType'-> 'EntitySpawnReason' {
import net.minecraft.world.entity.EntitySpawnReason;

@Mixin(Stray.class)
public abstract class StrayMixin {
	@Inject(method = "checkStraySpawnRules", at = @At("HEAD"), cancellable = true)
	private static void embers$spawnWithoutPowderSnow(EntityType<Stray> type, ServerLevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos,
													  RandomSource random, CallbackInfoReturnable<Boolean> cir) {
	//~}
		if (level.getBiome(pos).is(ModTags.SPAWNS_STRAYS_WITHOUT_POWDER_SNOW)) {
			cir.setReturnValue(Monster.checkMonsterSpawnRules(type, level, spawnReason, pos, random));
		}
	}
}
//?}
