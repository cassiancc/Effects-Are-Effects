package cc.cassian.effects.mixin.freezing;

import cc.cassian.effects.registry.ModEffects;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {
	//? if >26 {
	@Inject(at = @At(value = "HEAD"), method = "clearFreeze")
	private void clear(CallbackInfo ci) {
		if (((Entity) (Object) this) instanceof LivingEntity livingEntity) {
			livingEntity.removeEffect(ModEffects.FREEZING);
		}
	}
	//?}
}