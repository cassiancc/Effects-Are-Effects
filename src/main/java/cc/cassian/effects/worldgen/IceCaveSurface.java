//? if <26.4 {
package cc.cassian.effects.worldgen;

import cc.cassian.effects.EmbersAndIcicles;
import cc.cassian.effects.registry.ModBiomes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.RandomState;
import org.jspecify.annotations.Nullable;
//? if >=26.3 {
import net.minecraft.world.level.levelgen.synth.Noise;
//?} else {
/*import net.minecraft.world.level.levelgen.synth.NormalNoise;
*///?}

public final class IceCaveSurface {
	private static final BlockState PACKED_ICE = Blocks.PACKED_ICE.defaultBlockState();
	private static final BlockState CALCITE = Blocks.CALCITE.defaultBlockState();
	private static boolean warnedMissingNoise;

	private static @Nullable BlockState bandFor(final double noise) {
		if (noise >= -0.6F && noise <= -0.5) {
			return CALCITE;
		} else if (noise >= -0.5 && noise <= 0.2F) {
			return PACKED_ICE;
		} else if (noise >= 0.2F && noise <= 0.3F) {
			return CALCITE;
		} else if (noise >= 0.3F && noise <= 0.5) {
			return PACKED_ICE;
		} else if (noise >= 0.5 && noise <= 0.7F) {
			return CALCITE;
		}
		return null;
	}

	public static void apply(final ChunkAccess chunk, final RandomState randomState, final BiomeManager biomeManager) {
		LevelChunkSection[] sections = chunk.getSections();
		boolean[] candidate = new boolean[sections.length];
		boolean any = false;

		for (int i = 0; i < sections.length; i++) {
			if (sections[i].getBiomes().maybeHas(IceCaveSurface::isIceCaves)) {
				candidate[i] = true;
				any = true;
			}
		}

		if (!any) {
			return;
		}

		//? if >=26.3 {
		Noise noise;
		//?} else {
		/*NormalNoise noise;
		*///?}
		try {
			noise = randomState.getOrCreateNoise(ModBiomes.ICE_CAVE_GRADIENT);
		} catch (RuntimeException e) {
			if (!warnedMissingNoise) {
				warnedMissingNoise = true;
				EmbersAndIcicles.LOGGER.error("Missing noise {}, ice cave bands will not generate", ModBiomes.ICE_CAVE_GRADIENT, e);
			}
			return;
		}

		int minX = chunk.getPos().getMinBlockX();
		int minZ = chunk.getPos().getMinBlockZ();
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

		for (int i = 0; i < sections.length; i++) {
			boolean nearIceCaves = candidate[i] || i > 0 && candidate[i - 1] || i < sections.length - 1 && candidate[i + 1];
			LevelChunkSection section = sections[i];
			if (!nearIceCaves || section.hasOnlyAir()) {
				continue;
			}

			int minY = SectionPos.sectionToBlockCoord(chunk.getSectionYFromSectionIndex(i));

			for (int y = 0; y < 16; y++) {
				for (int z = 0; z < 16; z++) {
					for (int x = 0; x < 16; x++) {
						BlockState state = section.getBlockState(x, y, z);
						if (!state.is(Blocks.STONE) && !state.is(Blocks.DEEPSLATE)) {
							continue;
						}

						int blockX = minX + x;
						int blockY = minY + y;
						int blockZ = minZ + z;
						//? if >=26.3 {
						BlockState band = bandFor(noise.get(blockX, blockY, blockZ));
						//?} else {
						/*BlockState band = bandFor(noise.getValue(blockX, blockY, blockZ));
						*///?}
						if (band != null && isIceCaves(biomeManager.getBiome(pos.set(blockX, blockY, blockZ)))) {
							section.setBlockState(x, y, z, band, false);
						}
					}
				}
			}
		}
	}

	private static boolean isIceCaves(final Holder<Biome> biome) {
		return biome.is(ModBiomes.ICE_CAVES);
	}
}
//?}
