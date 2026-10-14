package cc.cassian.effects.mixin.freezing;

import cc.cassian.effects.registry.ModEffects;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
//~ if >26.3 'cc.cassian.effects.effect'-> 'net.minecraft.world.effect'
import cc.cassian.effects.effect.FreezingMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MobEffect.class)
public class MobEffectMixin {
	@WrapMethod(method = "onEffectAdded")
	private void avoidSpammingFreezingSound(LivingEntity mob, int amplifier, Operation<Void> original) {
		if ((MobEffect) (Object) this instanceof FreezingMobEffect && !mob.hasEffect(ModEffects.FREEZING)) {
			original.call(mob, amplifier);
		}
	}
}