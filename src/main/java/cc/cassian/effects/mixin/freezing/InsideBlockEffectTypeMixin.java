package cc.cassian.effects.mixin.freezing;

import cc.cassian.effects.registry.ModEffects;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectType;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InsideBlockEffectType.class)
public class InsideBlockEffectTypeMixin {
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setTicksFrozen(I)V"), method = "lambda$static$0")
	private static void init(Entity instance, int numberOfTicks, Operation<Void> original) {
		if (instance instanceof LivingEntity livingEntity) {
			livingEntity.addEffect(new MobEffectInstance(ModEffects.FREEZING, 1, 0, true, true));
		}
	}
}