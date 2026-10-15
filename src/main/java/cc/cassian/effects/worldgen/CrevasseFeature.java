//? if <26.4 {
package cc.cassian.effects.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import org.jspecify.annotations.Nullable;
//? if >=26.3 {
import net.minecraft.world.level.chunk.ChunkGenerator;
//?} else {
/*import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
*///?}

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

//? if >=26.3 {
public record CrevasseFeature(CrevasseFeature.Configuration config) implements Feature {
	public static final MapCodec<CrevasseFeature> CODEC = Configuration.CODEC.xmap(CrevasseFeature::new, CrevasseFeature::config);

	@Override
	public MapCodec<CrevasseFeature> codec() {
		return CODEC;
	}

	@Override
	public boolean place(final WorldGenLevel level, final ChunkGenerator chunkGenerator, final RandomSource random, final BlockPos origin) {
		return place(this.config, level, random, origin);
	}
//?} else {
/*public class CrevasseFeature extends Feature<CrevasseFeature.Configuration> {
	public CrevasseFeature() {
		super(Configuration.CODEC.codec());
	}

	@Override
	public boolean place(final FeaturePlaceContext<Configuration> context) {
		return place(context.config(), context.level(), context.random(), context.origin());
	}
*///?}
	private static final int MAX_STEP_HEIGHT_DIFFERENCE = 1;

	private static boolean place(final Configuration config, final WorldGenLevel region, final RandomSource random, final BlockPos pos) {
		int carveDepth = config.carveDepth.sample(random);
		if (pos.getY() <= WorldgenCompat.getMinY(region) + carveDepth) {
			return false;
		}

		int branchLength = config.maxLength / 2;
		float yaw = random.nextFloat() * (float) (Math.PI * 2);
		int fillDepth = config.fillDepth.sample(random);
		Long2ObjectOpenHashMap<CarveColumn> columns = new Long2ObjectOpenHashMap<>(50);

		for (int direction = 0; direction < 2; direction++) {
			appendBranchColumns(config, columns, region, pos, branchLength, carveDepth, yaw + direction * (float) Math.PI, random);
		}

		removeDisconnectedColumns(columns, pos);
		if (columns.size() < config.minFootprint) {
			return false;
		}

		int fillLevel = getFillLevel(columns, pos.getY(), fillDepth);

		for (CarveColumn column : columns.values().stream().sorted(CarveColumn::compareTo).toList()) {
			carveColumn(config, region, columns, column, fillLevel, random);
		}

		return true;
	}

	private static boolean wouldExposeSideLiquid(final WorldGenLevel region, final BlockPos pos) {
		for (Direction dir : Direction.Plane.HORIZONTAL) {
			if (!region.getFluidState(pos.relative(dir)).isEmpty()) {
				return true;
			}
		}

		return false;
	}

	private static Optional<Surface> findCarvableSurface(final Configuration config, final WorldGenLevel region, final BlockPos centerPos, final int searchRange) {
		BlockPos pos = centerPos.above(searchRange / 2);
		boolean isBlockAboveEmpty = region.getBlockState(pos.above()).isAir();
		int minY = Math.max(WorldgenCompat.getMinY(region), pos.getY() - searchRange);
		BlockPos.MutableBlockPos blockPos = pos.mutable();

		while (blockPos.getY() >= minY) {
			BlockState block = region.getBlockState(blockPos);
			if (config.carvableBlocks.test(region, blockPos) && isBlockAboveEmpty) {
				return Optional.of(new Surface(blockPos.immutable(), block));
			}

			isBlockAboveEmpty = block.isAir();
			blockPos.move(Direction.DOWN);
		}

		return Optional.empty();
	}

	private static @Nullable CarveColumn findColumn(final Long2ObjectOpenHashMap<CarveColumn> columns, final int x, final int z) {
		return columns.getOrDefault(WorldgenCompat.columnKey(x, z), null);
	}

	private static int getFillLevel(final Long2ObjectOpenHashMap<CarveColumn> columns, final int originSurfaceY, final int fillDepth) {
		CarveColumn lowestColumn = columns.values().stream().min(Comparator.comparingInt(a -> a.surfacePos.getY())).get();
		return Math.min(originSurfaceY - fillDepth, lowestColumn.surfacePos.getY() - 1);
	}

	private static boolean hasSteepNeighbor(final Long2ObjectOpenHashMap<CarveColumn> columns, final CarveColumn column) {
		for (Direction facing : Direction.Plane.HORIZONTAL) {
			BlockPos neighborPos = column.surfacePos.relative(facing);
			CarveColumn neighbor = findColumn(columns, neighborPos.getX(), neighborPos.getZ());
			if (neighbor != null && Math.abs(column.surfacePos.getY() - neighbor.surfacePos.getY()) > MAX_STEP_HEIGHT_DIFFERENCE) {
				return true;
			}
		}

		return false;
	}

	private static void removeDisconnectedColumns(final Long2ObjectOpenHashMap<CarveColumn> columns, final BlockPos originSurfacePos) {
		CarveColumn originColumn = findColumn(columns, originSurfacePos.getX(), originSurfacePos.getZ());
		if (originColumn == null || hasSteepNeighbor(columns, originColumn)) {
			columns.clear();
			return;
		}

		List<CarveColumn> connectedColumns = new ArrayList<>();
		LongSet processedColumns = new LongOpenHashSet();
		connectedColumns.add(originColumn);
		processedColumns.add(WorldgenCompat.columnKey(originSurfacePos.getX(), originSurfacePos.getZ()));

		for (int index = 0; index < connectedColumns.size(); index++) {
			CarveColumn column = connectedColumns.get(index);

			for (Direction facing : Direction.Plane.HORIZONTAL) {
				BlockPos neighborPos = column.surfacePos().relative(facing);
				CarveColumn neighbor = findColumn(columns, neighborPos.getX(), neighborPos.getZ());
				if (neighbor != null) {
					long neighborIndex = WorldgenCompat.columnKey(neighborPos.getX(), neighborPos.getZ());
					if (!processedColumns.contains(neighborIndex) && !hasSteepNeighbor(columns, neighbor)) {
						connectedColumns.add(neighbor);
						processedColumns.add(neighborIndex);
					}
				}
			}
		}

		columns.clear();

		for (CarveColumn column : connectedColumns) {
			columns.put(WorldgenCompat.columnKey(column.surfacePos.getX(), column.surfacePos.getZ()), column);
		}
	}

	private static void appendBranchColumns(final Configuration config, final Long2ObjectOpenHashMap<CarveColumn> columns, final WorldGenLevel region,
											final BlockPos originSurfacePos, final int branchLength, final int carveDepth, float yaw, final RandomSource random) {
		double cursorX = originSurfacePos.getX();
		double cursorZ = originSurfacePos.getZ();
		int previousSurfaceY = originSurfacePos.getY();
		float yawAcceleration = 0.0F;

		for (int step = 0; step < branchLength; step++) {
			cursorX += Math.cos(yaw);
			cursorZ += Math.sin(yaw);
			float turnDirection = (float) random.nextGaussian();
			float turnMagnitude = random.nextFloat();
			yawAcceleration = yawAcceleration * 0.5F + turnDirection * turnMagnitude * config.twistiness;
			yaw += yawAcceleration * config.turnFactor;
			int blockX = Mth.floor(cursorX);
			int blockZ = Mth.floor(cursorZ);
			Optional<Surface> surface = findCarvableSurface(config, region, new BlockPos(blockX, previousSurfaceY, blockZ), config.cursorSurfaceSearchRange);
			if (surface.isEmpty()) {
				continue;
			}

			float progress = (float) step / branchLength;
			float taper = 1.0F - progress * config.tapering;
			float radius = Math.max(config.maxWidth * 0.5F * taper, 0.5F);
			float radiusSquared = Mth.square(radius);
			int maxDepth = Math.max((int) (carveDepth * taper), 1);
			int expandedRadius = Mth.ceil(radius);

			for (int x = blockX - expandedRadius; x <= blockX + expandedRadius; x++) {
				for (int z = blockZ - expandedRadius; z <= blockZ + expandedRadius; z++) {
					int dx = x - blockX;
					int dz = z - blockZ;
					if (dx * dx + dz * dz > radiusSquared) {
						continue;
					}

					long columnIndex = WorldgenCompat.columnKey(x, z);
					if (columns.containsKey(columnIndex)) {
						continue;
					}

					Optional<Surface> columnSurface = findCarvableSurface(config, region, new BlockPos(x, surface.get().pos.getY(), z), config.columnSurfaceSearchRange);
					if (columnSurface.isEmpty()) {
						continue;
					}

					int depth = 1;
					BlockPos.MutableBlockPos testPos = columnSurface.get().pos.mutable().move(Direction.DOWN);

					while (depth < maxDepth && config.carvableBlocks.test(region, testPos)) {
						testPos.move(Direction.DOWN);
						depth++;
					}

					columns.put(columnIndex, new CarveColumn(columnSurface.get().pos, columnSurface.get().block, depth - 1));
				}
			}

			previousSurfaceY = surface.get().pos.getY();
		}
	}

	private static void sealExternalSides(final Configuration config, final WorldGenLevel region, final Long2ObjectOpenHashMap<CarveColumn> columns,
										  final CarveColumn column, final BlockPos pos, final Block placementBlock) {
		for (Direction facing : Direction.Plane.HORIZONTAL) {
			BlockPos neighborPos = pos.relative(facing);
			if (config.acceptableBarrier.test(region, neighborPos)) {
				continue;
			}

			CarveColumn neighborColumn = findColumn(columns, neighborPos.getX(), neighborPos.getZ());
			if (neighborColumn == null || !neighborColumn.containsY(pos.getY())) {
				BlockState neighborBlock = region.getBlockState(neighborPos);
				if (!neighborBlock.is(placementBlock)) {
					region.setBlock(neighborPos, column.originalSurfaceBlock, Block.UPDATE_CLIENTS);
				}
			}
		}
	}

	private static void sealFilledBottom(final Configuration config, final WorldGenLevel region, final CarveColumn column, final BlockPos pos, final Block fillBlock) {
		BlockPos belowPos = pos.below();
		if (!config.acceptableBarrier.test(region, belowPos)) {
			BlockState belowBlock = region.getBlockState(belowPos);
			if (!belowBlock.is(fillBlock)) {
				region.setBlock(belowPos, column.originalSurfaceBlock, Block.UPDATE_CLIENTS);
			}
		}
	}

	private static void carveColumn(final Configuration config, final WorldGenLevel region, final Long2ObjectOpenHashMap<CarveColumn> columns,
									final CarveColumn column, final int fillLevel, final RandomSource random) {
		BlockState air = Blocks.AIR.defaultBlockState();
		int carveMinY = column.getBottomY();
		boolean carvedAnyBlock = false;
		BlockPos.MutableBlockPos carvePos = column.surfacePos.mutable();

		while (carvePos.getY() >= carveMinY) {
			BlockState fillBlock = WorldgenCompat.getState(config.fillBlock, region, random, carvePos);
			boolean shouldFill = carvePos.getY() <= fillLevel;
			if (!shouldFill && wouldExposeSideLiquid(region, carvePos)) {
				break;
			}

			Block placementBlock = shouldFill ? fillBlock.getBlock() : air.getBlock();
			sealExternalSides(config, region, columns, column, carvePos, placementBlock);
			region.setBlock(carvePos, shouldFill ? fillBlock : air, Block.UPDATE_CLIENTS);
			if (shouldFill && carvePos.getY() == carveMinY) {
				sealFilledBottom(config, region, column, carvePos, placementBlock);
			}

			carvedAnyBlock = true;
			carvePos.move(Direction.DOWN);
		}

		if (carvedAnyBlock) {
			region.setBlock(column.surfacePos, WorldgenCompat.getState(config.surfaceBlock, region, random, column.surfacePos), Block.UPDATE_CLIENTS);
		}
	}

	private record CarveColumn(BlockPos surfacePos, BlockState originalSurfaceBlock, int depth) implements Comparable<CarveColumn> {
		int getBottomY() {
			return this.surfacePos.getY() - this.depth;
		}

		boolean containsY(final int y) {
			return y >= this.getBottomY() && y <= this.surfacePos.getY();
		}

		@Override
		public int compareTo(final CarveColumn other) {
			int xDiff = this.surfacePos.getX() - other.surfacePos.getX();
			return xDiff == 0 ? this.surfacePos.getZ() - other.surfacePos.getZ() : xDiff;
		}
	}

	private record Surface(BlockPos pos, BlockState block) {
	}

	public record Configuration(
			int maxWidth,
			int maxLength,
			int minFootprint,
			int cursorSurfaceSearchRange,
			int columnSurfaceSearchRange,
			float twistiness,
			float tapering,
			float turnFactor,
			IntProvider carveDepth,
			IntProvider fillDepth,
			BlockStateProvider surfaceBlock,
			BlockStateProvider fillBlock,
			BlockPredicate carvableBlocks,
			BlockPredicate acceptableBarrier
	) /*? if <26.3 {*//*implements FeatureConfiguration*//*?}*/ {
		public static final MapCodec<Configuration> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
				Codec.intRange(2, 8).fieldOf("max_width").forGetter(Configuration::maxWidth),
				Codec.intRange(4, 16).fieldOf("max_length").forGetter(Configuration::maxLength),
				Codec.intRange(1, 32).fieldOf("min_footprint").forGetter(Configuration::minFootprint),
				ExtraCodecs.POSITIVE_INT.fieldOf("cursor_surface_search_range").forGetter(Configuration::cursorSurfaceSearchRange),
				ExtraCodecs.POSITIVE_INT.fieldOf("column_surface_search_range").forGetter(Configuration::columnSurfaceSearchRange),
				Codec.floatRange(0.0F, 16.0F).fieldOf("twistiness").forGetter(Configuration::twistiness),
				Codec.floatRange(0.0F, 1.0F).fieldOf("tapering").forGetter(Configuration::tapering),
				Codec.floatRange(0.0F, 1.0F).fieldOf("turn_factor").forGetter(Configuration::turnFactor),
				WorldgenCompat.POSITIVE_INT_PROVIDER.fieldOf("carve_depth").forGetter(Configuration::carveDepth),
				WorldgenCompat.POSITIVE_INT_PROVIDER.fieldOf("fill_depth").forGetter(Configuration::fillDepth),
				WorldgenCompat.BLOCK_STATE_PROVIDER.fieldOf("surface_block").forGetter(Configuration::surfaceBlock),
				WorldgenCompat.BLOCK_STATE_PROVIDER.fieldOf("fill_block").forGetter(Configuration::fillBlock),
				BlockPredicate.CODEC.fieldOf("carvable_blocks").forGetter(Configuration::carvableBlocks),
				BlockPredicate.CODEC.fieldOf("acceptable_barrier").forGetter(Configuration::acceptableBarrier)
		).apply(i, Configuration::new));
	}
}
//?}
