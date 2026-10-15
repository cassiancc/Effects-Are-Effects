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
	//~ if >26.3 'Lnet/minecraft/world/entity/Entity;setTicksFrozen(I)V'->'Lnet/minecraft/world/entity/Entity;freezeForTicks(I)V'
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setTicksFrozen(I)V"), method = "lambda$static$0")
	private static void addFreezing(Entity instance, int numberOfTicks, Operation<Void> original) {
	//?} else {
	/*@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setTicksFrozen(I)V", ordinal = 0), method = "aiStep")
	private void addFreezing(LivingEntity instance, int numberOfTicks, Operation<Void> original) {
	*///?}
		if (instance instanceof LivingEntity livingEntity) {
			livingEntity.addEffect(new MobEffectInstance(ModEffects.FREEZING, 1, 0, true, true));
		}
	}

	//? if <26 {
	
	/*@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setTicksFrozen(I)V", ordinal = 1), method = "aiStep")
	private void keepFreezing(LivingEntity instance, int ticks, Operation<Void> original) {
		if (!instance.hasEffect(ModEffects.FREEZING)) {
			original.call(instance, ticks);
		}
	}
	*///?}
}
