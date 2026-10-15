package cc.cassian.effects.mixin.freezing;

import cc.cassian.effects.registry.ModEffects;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//? if <26.4 {
import cc.cassian.effects.effect.FreezingMobEffect;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
//?}
//? if >=26 && <26.4
import net.minecraft.server.level.ServerLevel;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	//? if <26.4 {
	@ModifyReturnValue(at = @At(value = "RETURN"), method = "canBeAffected")
	private boolean preventFreezing(boolean original,final MobEffectInstance newEffect) {
		if (this
				//? if <1.21.2
				//.getType()
				.is(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES))
			return !newEffect.is(ModEffects.FREEZING);
		return original;
	}

	//? if >=26 {
	@ModifyExpressionValue(at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/LivingEntity;isInPowderSnow:Z"), method = "aiStep")
	private boolean keepFreezing(boolean original) {
		return original || ((LivingEntity) (Object) this).hasEffect(ModEffects.FREEZING);
	}
	//?}

	//? if >=26 {
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z"), method = "aiStep")
	private boolean increaseFreezeDamage(LivingEntity instance, ServerLevel level, DamageSource source, float damage, Operation<Boolean> original) {
		if (source.is(DamageTypeTags.IS_FREEZING) && instance.hasEffect(ModEffects.FREEZING)) {
			damage = FreezingMobEffect.DAMAGE_VALUE;
		}
		return original.call(instance, level, source, damage);
	}
	//?} else {
	/*@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"), method = "aiStep")
	private boolean increaseFreezeDamage(LivingEntity instance, DamageSource source, float damage, Operation<Boolean> original) {
		if (source.is(DamageTypeTags.IS_FREEZING) && instance.hasEffect(ModEffects.FREEZING)) {
			damage = FreezingMobEffect.DAMAGE_VALUE;
		}
		return original.call(instance, source, damage);
	}
	*///?}
	//?}
}
