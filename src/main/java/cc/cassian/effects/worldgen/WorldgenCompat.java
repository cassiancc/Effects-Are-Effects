//? if <26.4 {
package cc.cassian.effects.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
//? if >=26 {
import net.minecraft.util.valueproviders.FloatProviders;
import net.minecraft.util.valueproviders.IntProviders;
//?}
//? if >=26.3 {
import net.minecraft.core.Holder;
import net.minecraft.core.registries.codec.RegistryCodecs;
//?} else {
/*import net.minecraft.core.RegistryCodecs;
*///?}

public final class WorldgenCompat {
	//? if >=26 {
	public static final Codec<IntProvider> POSITIVE_INT_PROVIDER = IntProviders.POSITIVE_CODEC;
	//?} else {
	/*public static final Codec<IntProvider> POSITIVE_INT_PROVIDER = IntProvider.POSITIVE_CODEC;
	*///?}
	//? if >=26.3 {
	public static final Codec<BlockStateProvider> BLOCK_STATE_PROVIDER = BlockStateProvider.CODEC.xmap(Holder::value, Holder::direct);
	//?} else {
	/*public static final Codec<BlockStateProvider> BLOCK_STATE_PROVIDER = BlockStateProvider.CODEC;
	*///?}

	public static Codec<IntProvider> intProvider(final int min, final int max) {
		//? if >=26 {
		return IntProviders.codec(min, max);
		//?} else {
		/*return IntProvider.codec(min, max);
		*///?}
	}

	public static Codec<FloatProvider> floatProvider(final float min, final float max) {
		//? if >=26 {
		return FloatProviders.codec(min, max);
		//?} else {
		/*return FloatProvider.codec(min, max);
		*///?}
	}

	public static Codec<HolderSet<Block>> blocks() {
		//? if >=26.3 {
		return RegistryCodecs.holderSet(Registries.BLOCK);
		//?} else {
		/*return RegistryCodecs.homogeneousList(Registries.BLOCK);
		*///?}
	}

	public static BlockState getState(final BlockStateProvider provider, final WorldGenLevel level, final RandomSource random, final BlockPos pos) {
		//? if >=26 {
		return provider.getState(level, random, pos);
		//?} else {
		/*return provider.getState(random, pos);
		*///?}
	}

	public static int minValue(final IntProvider provider) {
		//? if >=26 {
		return provider.minInclusive();
		//?} else {
		/*return provider.getMinValue();
		*///?}
	}

	public static int maxValue(final IntProvider provider) {
		//? if >=26 {
		return provider.maxInclusive();
		//?} else {
		/*return provider.getMaxValue();
		*///?}
	}

	public static int getMinY(final LevelHeightAccessor level) {
		//? if >=26 {
		return level.getMinY();
		//?} else {
		/*return level.getMinBuildHeight();
		*///?}
	}

	public static long columnKey(final int x, final int z) {
		return x & 0xFFFFFFFFL | (z & 0xFFFFFFFFL) << 32;
	}
}
//?}
