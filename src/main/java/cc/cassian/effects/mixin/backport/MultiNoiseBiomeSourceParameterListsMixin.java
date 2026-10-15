//? if <26.4 {
package cc.cassian.effects.mixin.backport;

import cc.cassian.effects.worldgen.IceCaveBiomeInjection;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterLists;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MultiNoiseBiomeSourceParameterLists.class)
public abstract class MultiNoiseBiomeSourceParameterListsMixin {
	@WrapMethod(method = "bootstrap")
	private static void embers$skipIceCavesInBuiltinBootstrap(BootstrapContext<MultiNoiseBiomeSourceParameterList> context, Operation<Void> original) {
		IceCaveBiomeInjection.setBuildingBuiltinRegistries(true);
		try {
			original.call(context);
		} finally {
			IceCaveBiomeInjection.setBuildingBuiltinRegistries(false);
		}
	}
}
//?}
