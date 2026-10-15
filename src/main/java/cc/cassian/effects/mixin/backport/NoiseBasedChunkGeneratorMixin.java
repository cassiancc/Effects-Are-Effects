//? if <26.4 {
package cc.cassian.effects.mixin.backport;

import cc.cassian.effects.worldgen.IceCaveSurface;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//? if >=26.3 {
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.material.rule.MaterialRule;
import java.util.Set;
//?} else {
/*import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.StructureManager;
*///?}

@Mixin(NoiseBasedChunkGenerator.class)
public abstract class NoiseBasedChunkGeneratorMixin {
	//? if >=26.3 {
	@Inject(method = "buildSurface", at = @At("TAIL"))
	private void embers$buildIceCaveBands(ChunkAccess protoChunk, NoiseChunk noiseChunk, RandomState randomState, BiomeManager biomeManager,
										  Set<Holder<Biome>> possibleBiomes, MaterialRule materialRule, CallbackInfo ci) {
		IceCaveSurface.apply(protoChunk, randomState, biomeManager);
	}
	//?} else {
	/*@Inject(method = "buildSurface(Lnet/minecraft/server/level/WorldGenRegion;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/chunk/ChunkAccess;)V",
			at = @At("TAIL"))
	private void embers$buildIceCaveBands(WorldGenRegion region, StructureManager structureManager, RandomState randomState, ChunkAccess protoChunk,
										  CallbackInfo ci) {
		IceCaveSurface.apply(protoChunk, randomState, region.getBiomeManager());
	}
	*///?}
}
//?}
