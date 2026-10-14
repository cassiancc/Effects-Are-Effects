package cc.cassian.effects.mixin.freezing;

import cc.cassian.effects.registry.ModEffects;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//~ if <26 'InsideBlockEffectType'->'LivingEntity'
@Mixin(net.minecraft.world.entity.InsideBlockEffectType.class)
public class InsideBlockEffectTypeMixin {
	//? if >26 {
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setTicksFrozen(I)V"), method = "lambda$static$0")
	private static void init(Entity instance, int numberOfTicks, Operation<Void> original) {
	//?} else {
	/*@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setTicksFrozen(I)V", ordinal = 0), method = "aiStep")
	private static void init(LivingEntity instance, int i, Operation<Void> original) {
	*///?}
		if (instance instanceof LivingEntity livingEntity) {
			livingEntity.addEffect(new MobEffectInstance(ModEffects.FREEZING, 1, 0, true, true));
		}
	}

	//? if <26 {
	/*@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setTicksFrozen(I)V", ordinal = 1), method = "aiStep")
	private static void clear(LivingEntity instance, int i, Operation<Void> original) {
		if (instance instanceof LivingEntity livingEntity) {
			livingEntity.removeEffect(ModEffects.FREEZING);
		}
	}
	*///?}
}