package cc.cassian.effects.mixin.burning;

import cc.cassian.effects.registry.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
	@Inject(at = @At(value = "RETURN"), method = "isOnFire")
	private void init(CallbackInfoReturnable<Boolean> cir) {
		if (((Entity) (Object) this) instanceof LivingEntity livingEntity) {
			if (cir.getReturnValue()) {
				livingEntity.addEffect(new MobEffectInstance(ModEffects.BURNING, livingEntity.getRemainingFireTicks(), 0, false, true));
			} else {
				livingEntity.removeEffect(ModEffects.BURNING);
			}
		}
	}
}