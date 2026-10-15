//? if <26.4 {
package cc.cassian.effects.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.Feature;
//? if >=26.3 {
import net.minecraft.world.level.chunk.ChunkGenerator;
//?} else {
/*import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
*///?}

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;

//? if >=26.3 {
public record SpeleothemClusterFeature(SpeleothemClusterFeature.Configuration config) implements Feature {
	public static final MapCodec<SpeleothemClusterFeature> CODEC = Configuration.CODEC.xmap(SpeleothemClusterFeature::new, SpeleothemClusterFeature::config);

	@Override
	public MapCodec<SpeleothemClusterFeature> codec() {
		return CODEC;
	}

	@Override
	public boolean place(final WorldGenLevel level, final ChunkGenerator chunkGenerator, final RandomSource random, final BlockPos origin) {
		return place(this.config, level, random, origin);
	}
//?} else {
/*public class SpeleothemClusterFeature extends Feature<SpeleothemClusterFeature.Configuration> {
	public SpeleothemClusterFeature() {
		super(Configuration.CODEC.codec());
	}

	@Override
	public boolean place(final FeaturePlaceContext<Configuration> context) {
		return place(context.config(), context.level(), context.random(), context.origin());
	}
*///?}

	private static boolean place(final Configuration config, final WorldGenLevel level, final RandomSource random, final BlockPos origin) {
		if (!SpeleothemUtils.isEmptyOrWater(level, origin)) {
			return false;
		}

		int height = config.height.sample(random);
		float wetness = config.wetness.sample(random);
		float density = config.density.sample(random);
		int xRadius = config.radius.sample(random);
		int zRadius = config.radius.sample(random);

		for (int dx = -xRadius; dx <= xRadius; dx++) {
			for (int dz = -zRadius; dz <= zRadius; dz++) {
				double chance = getChanceOfStalagmiteOrStalactite(config, xRadius, zRadius, dx, dz);
				BlockPos pos = origin.offset(dx, 0, dz);
				placeColumn(config, level, random, pos, dx, dz, wetness, chance, height, density);
			}
		}

		return true;
	}

	private static boolean canPlaceAt(final BlockState state, final boolean allowWaterPlacement) {
		return allowWaterPlacement ? SpeleothemUtils.isEmptyOrWater(state) : state.isAir();
	}

	private static int getAvailableHeight(final WorldGenLevel level, final BlockPos basePos, final Direction direction, final int desiredHeight,
										  final Predicate<BlockState> canPlace) {
		BlockPos pos = basePos;
		int availableHeight = 0;

		while (availableHeight < desiredHeight && canPlace.test(level.getBlockState(pos))) {
			availableHeight++;
			pos = pos.relative(direction);
		}

		return availableHeight;
	}

	private static void placeColumn(final Configuration config, final WorldGenLevel level, final RandomSource random, final BlockPos pos, final int dx, final int dz,
									final float chanceOfWater, final double chanceOfSpeleothem, final int clusterHeight, final float density) {
		PlacementOptions options = config.placementOptions;
		Predicate<BlockState> canPlace = state -> canPlaceAt(state, options.allowWaterPlacement);
		Optional<Column> baseColumn = Column.scan(level, pos, config.floorToCeilingSearchRange, canPlace, SpeleothemUtils::isNeitherEmptyNorWater);
		if (baseColumn.isEmpty()) {
			return;
		}

		Column scannedColumn = baseColumn.get();
		Column placementColumn = filterPlacementColumn(options, scannedColumn);
		OptionalInt ceiling = placementColumn.getCeiling();
		OptionalInt baseFloor = placementColumn.getFloor();
		if (ceiling.isEmpty() && baseFloor.isEmpty()) {
			return;
		}

		boolean wantPool = random.nextFloat() < chanceOfWater;
		Column column;
		if (wantPool && baseFloor.isPresent() && canPlacePool(config, level, pos.atY(baseFloor.getAsInt()))) {
			int baseFloorY = baseFloor.getAsInt();
			column = placementColumn.withFloor(OptionalInt.of(baseFloorY - 1));
			level.setBlock(pos.atY(baseFloorY), Blocks.WATER.defaultBlockState(), Block.UPDATE_CLIENTS);
		} else {
			column = placementColumn;
		}

		OptionalInt floor = column.getFloor();
		boolean wantStalactite = random.nextDouble() < chanceOfSpeleothem;
		int stalactiteHeight;
		if (ceiling.isPresent() && wantStalactite && !isLava(level, pos.atY(ceiling.getAsInt()))) {
			int ceilingThickness = config.speleothemBlockLayerThickness.sample(random);
			replaceBlocksWithBaseBlocks(config, level, pos.atY(ceiling.getAsInt()), ceilingThickness, Direction.UP);
			int maxHeightForThisColumn = getMaxHeightForThisColumn(options, scannedColumn, clusterHeight);
			if (options.placementMode == PlacementMode.FLOOR_AND_CEILING && floor.isPresent()) {
				maxHeightForThisColumn = Math.min(clusterHeight, ceiling.getAsInt() - floor.getAsInt());
			}

			stalactiteHeight = getSpeleothemHeight(config, random, dx, dz, density, maxHeightForThisColumn);
		} else {
			stalactiteHeight = 0;
		}

		boolean wantStalagmite = random.nextDouble() < chanceOfSpeleothem;
		int stalagmiteHeight;
		if (floor.isPresent() && wantStalagmite && !isLava(level, pos.atY(floor.getAsInt()))) {
			int floorThickness = config.speleothemBlockLayerThickness.sample(random);
			replaceBlocksWithBaseBlocks(config, level, pos.atY(floor.getAsInt()), floorThickness, Direction.DOWN);
			if (ceiling.isPresent()) {
				stalagmiteHeight = Math.max(0, stalactiteHeight + Mth.randomBetweenInclusive(random,
						-config.maxStalagmiteStalactiteHeightDiff, config.maxStalagmiteStalactiteHeightDiff));
			} else {
				int maxHeightForThisColumn = getMaxHeightForThisColumn(options, scannedColumn, clusterHeight);
				stalagmiteHeight = getSpeleothemHeight(config, random, dx, dz, density, maxHeightForThisColumn);
			}
		} else {
			stalagmiteHeight = 0;
		}

		int actualStalactiteHeight;
		int actualStalagmiteHeight;
		if (ceiling.isPresent() && floor.isPresent() && ceiling.getAsInt() - stalactiteHeight <= floor.getAsInt() + stalagmiteHeight) {
			int floorY = floor.getAsInt();
			int ceilingY = ceiling.getAsInt();
			int lowestStalactiteBottom = Math.max(ceilingY - stalactiteHeight, floorY + 1);
			int highestStalagmiteTop = Math.min(floorY + stalagmiteHeight, ceilingY - 1);
			int actualStalactiteBottom = Mth.randomBetweenInclusive(random, lowestStalactiteBottom, highestStalagmiteTop + 1);
			int actualStalagmiteTop = actualStalactiteBottom - 1;
			actualStalactiteHeight = ceilingY - actualStalactiteBottom;
			actualStalagmiteHeight = actualStalagmiteTop - floorY;
		} else {
			actualStalactiteHeight = stalactiteHeight;
			actualStalagmiteHeight = stalagmiteHeight;
		}

		if (ceiling.isPresent()) {
			actualStalactiteHeight = getAvailableHeight(level, pos.atY(ceiling.getAsInt() - 1), Direction.DOWN, actualStalactiteHeight, canPlace);
		}

		if (floor.isPresent()) {
			actualStalagmiteHeight = getAvailableHeight(level, pos.atY(floor.getAsInt() + 1), Direction.UP, actualStalagmiteHeight, canPlace);
		}

		boolean mergeTips = random.nextBoolean()
				&& actualStalactiteHeight > 0
				&& actualStalagmiteHeight > 0
				&& column.getHeight().isPresent()
				&& actualStalactiteHeight + actualStalagmiteHeight == column.getHeight().getAsInt();
		Block baseBlock = config.baseBlock.getBlock();
		Block pointedBlock = config.pointedBlock.getBlock();
		if (ceiling.isPresent()) {
			SpeleothemUtils.growSpeleothem(level, pos.atY(ceiling.getAsInt() - 1), Direction.DOWN, actualStalactiteHeight, mergeTips,
					baseBlock, pointedBlock, config.replaceableBlocks, options.baseBlockTransformer);
		}

		if (floor.isPresent()) {
			SpeleothemUtils.growSpeleothem(level, pos.atY(floor.getAsInt() + 1), Direction.UP, actualStalagmiteHeight, mergeTips,
					baseBlock, pointedBlock, config.replaceableBlocks, options.baseBlockTransformer);
		}
	}

	private static Column filterPlacementColumn(final PlacementOptions options, final Column scannedColumn) {
		OptionalInt floor = options.placementMode != PlacementMode.CEILING_ONLY ? scannedColumn.getFloor() : OptionalInt.empty();
		OptionalInt ceiling = options.placementMode != PlacementMode.FLOOR_ONLY ? scannedColumn.getCeiling() : OptionalInt.empty();
		return Column.create(floor, ceiling);
	}

	private static int getMaxHeightForThisColumn(final PlacementOptions options, final Column scannedColumn, final int clusterHeight) {
		return options.placementMode != PlacementMode.FLOOR_AND_CEILING && scannedColumn.getHeight().isPresent()
				? Math.min(clusterHeight, scannedColumn.getHeight().getAsInt())
				: clusterHeight;
	}

	private static boolean isLava(final LevelReader level, final BlockPos pos) {
		return level.getBlockState(pos).is(Blocks.LAVA);
	}

	private static int getSpeleothemHeight(final Configuration config, final RandomSource random, final int dx, final int dz, final float density, final int maxHeight) {
		if (random.nextFloat() > density) {
			return 0;
		}

		int distanceFromCenter = Math.abs(dx) + Math.abs(dz);
		float heightMean = (float) Mth.clampedMap(distanceFromCenter, 0.0, config.maxDistanceFromCenterAffectingHeightBias, maxHeight / 2.0, 0.0);
		return (int) ClampedNormalFloat.sample(random, heightMean, config.heightDeviation, 0.0F, maxHeight);
	}

	private static boolean canPlacePool(final Configuration config, final WorldGenLevel level, final BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		if (state.is(Blocks.WATER) || state.is(config.baseBlock.getBlock()) || state.is(config.pointedBlock.getBlock())) {
			return false;
		} else if (level.getBlockState(pos.above()).getFluidState().is(FluidTags.WATER)) {
			return false;
		}

		for (Direction direction : Direction.Plane.HORIZONTAL) {
			if (!canBeAdjacentToWater(level, pos.relative(direction))) {
				return false;
			}
		}

		return canBeAdjacentToWater(level, pos.below());
	}

	private static boolean canBeAdjacentToWater(final LevelAccessor level, final BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		return state.is(BlockTags.BASE_STONE_OVERWORLD) || state.getFluidState().is(FluidTags.WATER);
	}

	private static void replaceBlocksWithBaseBlocks(final Configuration config, final WorldGenLevel level, final BlockPos firstPos, final int maxCount, final Direction direction) {
		BlockPos.MutableBlockPos pos = firstPos.mutable();

		for (int i = 0; i < maxCount; i++) {
			if (!SpeleothemUtils.placeBaseBlockIfPossible(level, pos, config.baseBlock.getBlock(), config.replaceableBlocks)) {
				return;
			}

			pos.move(direction);
		}
	}

	private static double getChanceOfStalagmiteOrStalactite(final Configuration config, final int xRadius, final int zRadius, final int dx, final int dz) {
		int xDistanceFromEdge = xRadius - Math.abs(dx);
		int zDistanceFromEdge = zRadius - Math.abs(dz);
		int distanceFromEdge = Math.min(xDistanceFromEdge, zDistanceFromEdge);
		return Mth.clampedMap(distanceFromEdge, 0.0, config.maxDistanceFromEdgeAffectingChanceOfSpeleothem,
				config.chanceOfSpeleothemAtMaxDistanceFromCenter, 1.0);
	}

	public enum PlacementMode implements StringRepresentable {
		FLOOR_AND_CEILING("floor_and_ceiling"),
		FLOOR_ONLY("floor_only"),
		CEILING_ONLY("ceiling_only");

		public static final Codec<PlacementMode> CODEC = StringRepresentable.fromEnum(PlacementMode::values);
		private final String name;

		PlacementMode(final String name) {
			this.name = name;
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}
	}

	public record PlacementOptions(PlacementMode placementMode, SpeleothemUtils.BaseBlockTransformer baseBlockTransformer, boolean allowWaterPlacement) {
		public static final PlacementOptions DEFAULT = new PlacementOptions(PlacementMode.FLOOR_AND_CEILING, SpeleothemUtils.BaseBlockTransformer.NONE, true);
		public static final Codec<PlacementOptions> CODEC = RecordCodecBuilder.create(i -> i.group(
				PlacementMode.CODEC.fieldOf("placement_mode").forGetter(PlacementOptions::placementMode),
				SpeleothemUtils.BaseBlockTransformer.CODEC.fieldOf("base_block_transformer").forGetter(PlacementOptions::baseBlockTransformer),
				Codec.BOOL.fieldOf("allow_water_placement").forGetter(PlacementOptions::allowWaterPlacement)
		).apply(i, PlacementOptions::new));
	}

	public record Configuration(
			BlockState baseBlock,
			BlockState pointedBlock,
			HolderSet<Block> replaceableBlocks,
			int floorToCeilingSearchRange,
			IntProvider height,
			IntProvider radius,
			int maxStalagmiteStalactiteHeightDiff,
			int heightDeviation,
			IntProvider speleothemBlockLayerThickness,
			FloatProvider density,
			FloatProvider wetness,
			float chanceOfSpeleothemAtMaxDistanceFromCenter,
			int maxDistanceFromEdgeAffectingChanceOfSpeleothem,
			int maxDistanceFromCenterAffectingHeightBias,
			PlacementOptions placementOptions
	) /*? if <26.3 {*//*implements FeatureConfiguration*//*?}*/ {
		public static final MapCodec<Configuration> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
				BlockState.CODEC.fieldOf("base_block").forGetter(Configuration::baseBlock),
				BlockState.CODEC.fieldOf("pointed_block").forGetter(Configuration::pointedBlock),
				WorldgenCompat.blocks().fieldOf("replaceable_blocks").forGetter(Configuration::replaceableBlocks),
				Codec.intRange(1, 512).fieldOf("floor_to_ceiling_search_range").forGetter(Configuration::floorToCeilingSearchRange),
				WorldgenCompat.intProvider(1, 128).fieldOf("height").forGetter(Configuration::height),
				WorldgenCompat.intProvider(1, 128).fieldOf("radius").forGetter(Configuration::radius),
				Codec.intRange(0, 64).fieldOf("max_stalagmite_stalactite_height_diff").forGetter(Configuration::maxStalagmiteStalactiteHeightDiff),
				Codec.intRange(1, 64).fieldOf("height_deviation").forGetter(Configuration::heightDeviation),
				WorldgenCompat.intProvider(0, 128).fieldOf("speleothem_block_layer_thickness").forGetter(Configuration::speleothemBlockLayerThickness),
				WorldgenCompat.floatProvider(0.0F, 2.0F).fieldOf("density").forGetter(Configuration::density),
				WorldgenCompat.floatProvider(0.0F, 2.0F).fieldOf("wetness").forGetter(Configuration::wetness),
				Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_speleothem_at_max_distance_from_center").forGetter(Configuration::chanceOfSpeleothemAtMaxDistanceFromCenter),
				Codec.intRange(1, 64).fieldOf("max_distance_from_edge_affecting_chance_of_speleothem").forGetter(Configuration::maxDistanceFromEdgeAffectingChanceOfSpeleothem),
				Codec.intRange(1, 64).fieldOf("max_distance_from_center_affecting_height_bias").forGetter(Configuration::maxDistanceFromCenterAffectingHeightBias),
				PlacementOptions.CODEC.optionalFieldOf("placement_options", PlacementOptions.DEFAULT).forGetter(Configuration::placementOptions)
		).apply(i, Configuration::new));
	}
}
//?}
