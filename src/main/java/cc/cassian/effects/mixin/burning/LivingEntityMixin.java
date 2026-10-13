package cc.cassian.effects.mixin.burning;

import cc.cassian.effects.registry.ModEffects;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffectInstance;onEffectStarted(Lnet/minecraft/world/entity/LivingEntity;)V"), method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z")
	private void setOnFire(MobEffectInstance newEffect, @Nullable Entity source, CallbackInfoReturnable<Boolean> cir, @Local(name = "changed") boolean changed) {
		if (changed && newEffect.is(ModEffects.BURNING) && !this.isOnFire()) {
			this.setRemainingFireTicks(newEffect.getDuration());
			this.setSharedFlagOnFire(true);
		}
	}
}