//? if <26.4 {
package cc.cassian.effects.mixin.backport.client;

import cc.cassian.effects.entity.FreezingConvertible;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.world.entity.monster.zombie.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
//? if >=26 {
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//?} else {
/*import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
*///?}

@Mixin(AbstractZombieRenderer.class)
public abstract class AbstractZombieRendererMixin {
	//? if >=26 {
	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/monster/zombie/Zombie;Lnet/minecraft/client/renderer/entity/state/ZombieRenderState;F)V", at = @At("TAIL"))
	private void embers$shakeWhileFreezing(Zombie entity, ZombieRenderState state, float partialTicks, CallbackInfo ci) {
		if (entity instanceof FreezingConvertible convertible && convertible.embers$isFreezingConverting()) {
			state.isConverting = true;
		}
	}
	//?} else {
	/*@Inject(method = "isShaking(Lnet/minecraft/world/entity/monster/zombie/Zombie;)Z", at = @At("RETURN"), cancellable = true)
	private void embers$shakeWhileFreezing(Zombie entity, CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValueZ() && entity instanceof FreezingConvertible convertible && convertible.embers$isFreezingConverting()) {
			cir.setReturnValue(true);
		}
	}
	*///?}
}
//?}
