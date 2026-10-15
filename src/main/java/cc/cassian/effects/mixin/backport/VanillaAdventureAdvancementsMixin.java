//? if <26.4 {
package cc.cassian.effects.mixin.backport;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.data.advancements.packs.VanillaAdventureAdvancements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.material.FlowingFluid;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.stream.Stream;

@Mixin(VanillaAdventureAdvancements.class)
public abstract class VanillaAdventureAdvancementsMixin {

	@WrapOperation(method = "createAdventuringTime", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/MultiNoiseBiomeSourceParameterList$Preset;usedBiomes()Ljava/util/stream/Stream;"))
	private Stream<ResourceKey<Biome>> embers$allowDatagenWithoutFrozenCaves(MultiNoiseBiomeSourceParameterList.Preset instance, Operation<Stream<ResourceKey<Biome>>> original) {
		return original.call(instance).filter(key->key.identifier().getNamespace().equals("minecraft"));
	}
}
//?}
