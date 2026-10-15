package cc.cassian.effects.mixin.burning;

import cc.cassian.effects.util.ModUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;

//~ if >26.1 'Gui'-> 'Hud'
@Mixin(net.minecraft.client.gui.Hud.class)
public class HudMixin {
	//~ if >26.1 'Gui'-> 'Hud'
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Hud$HeartType;getSprite(ZZZ)Lnet/minecraft/resources/Identifier;"),
			//~ if <26 'extract'->'render'
			method = "extractHeart")
	private Identifier burningHearts(@Coerce Object instance, boolean isHardcore, boolean isHalf, boolean isBlink, Operation<Identifier> original) {
		if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.isOnFire()) {
			return ModUtil.getSprite(isHardcore, isHalf, isBlink);
		}
		return original.call(instance, isHardcore, isHalf, isBlink);
	}
}
