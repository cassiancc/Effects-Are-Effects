//? if <26.4 {
package cc.cassian.effects.mixin.backport;

import cc.cassian.effects.registry.ModBiomes;
import cc.cassian.effects.worldgen.IceCaveBiomeInjection;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(OverworldBiomeBuilder.class)
public abstract class OverworldBiomeBuilderMixin {
	@Shadow
	@Final
	private Climate.Parameter deepOceanContinentalness;

	@Shadow
	@Final
	private Climate.Parameter oceanContinentalness;

	@Shadow
	@Final
	private Climate.Parameter FULL_RANGE;

	@Shadow
	private void addUndergroundBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomes, Climate.Parameter temperature,
									 Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion,
									 Climate.Parameter weirdness, float offset, ResourceKey<Biome> second) {
		throw new AssertionError();
	}

	@Inject(method = "addUndergroundBiomes", at = @At("TAIL"))
	private void embers$addIceCaves(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomes, CallbackInfo ci) {
		if (!IceCaveBiomeInjection.shouldInject()) {
			return;
		}

		this.addUndergroundBiome(
				biomes,
				Climate.Parameter.span(-1.0F, -0.8F),
				Climate.Parameter.span(-1.0F, 0.7F),
				Climate.Parameter.span(this.deepOceanContinentalness, this.oceanContinentalness),
				this.FULL_RANGE,
				Climate.Parameter.span(0.5F, 1.1F),
				0.0F,
				ModBiomes.ICE_CAVES
		);
	}
}
//?}
