//? if <26.4 {
package cc.cassian.effects.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
//? if >=26.3 {
import net.minecraft.world.level.chunk.ChunkGenerator;
//?} else {
/*import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
*///?}

import java.util.Optional;

//? if >=26.3 {
public record LargeSpeleothemFeature(LargeSpeleothemFeature.Configuration config) implements Feature {
	public static final MapCodec<LargeSpeleothemFeature> CODEC = Configuration.CODEC.xmap(LargeSpeleothemFeature::new, LargeSpeleothemFeature::config);

	@Override
	public MapCodec<LargeSpeleothemFeature> codec() {
		return CODEC;
	}

	@Override
	public boolean place(final WorldGenLevel level, final ChunkGenerator chunkGenerator, final RandomSource random, final BlockPos origin) {
		return place(this.config, level, random, origin);
	}
//?} else {
/*public class LargeSpeleothemFeature extends Feature<LargeSpeleothemFeature.Configuration> {
	public LargeSpeleothemFeature() {
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

		Block baseBlock = config.baseBlock.getBlock();
		Optional<Column> column = Column.scan(level, origin, config.floorToCeilingSearchRange, SpeleothemUtils::isEmptyOrWater,
				state -> SpeleothemUtils.isBaseOrLava(state, baseBlock, config.replaceableBlocks));
		if (column.isEmpty() || !(column.get() instanceof Column.Range range) || range.height() < 4) {
			return false;
		}

		int minRadius = WorldgenCompat.minValue(config.columnRadius);
		int maxColumnRadiusBasedOnColumnHeight = (int) (range.height() * config.maxColumnRadiusToCaveHeightRatio);
		int maxColumnRadius = Mth.clamp(maxColumnRadiusBasedOnColumnHeight, minRadius, WorldgenCompat.maxValue(config.columnRadius));
		int radius = Mth.randomBetweenInclusive(random, minRadius, maxColumnRadius);
		LargeSpeleothem stalactite = makeSpeleothem(config.baseBlock, origin.atY(range.ceiling() - 1), false, random, radius,
				config.stalactiteBluntness, config.heightScale);
		LargeSpeleothem stalagmite = makeSpeleothem(config.baseBlock, origin.atY(range.floor() + 1), true, random, radius,
				config.stalagmiteBluntness, config.heightScale);
		WindOffsetter wind;
		if (stalactite.isSuitableForWind(config.minRadiusForWind, config.minBluntnessForWind)
				&& stalagmite.isSuitableForWind(config.minRadiusForWind, config.minBluntnessForWind)) {
			wind = new WindOffsetter(origin.getY(), random, config.windSpeed, 16 - radius);
		} else {
			wind = WindOffsetter.noWind();
		}

		boolean stalactiteBaseEmbeddedInStone = stalactite.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(level, wind);
		boolean stalagmiteBaseEmbeddedInStone = stalagmite.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(level, wind);
		if (stalactiteBaseEmbeddedInStone) {
			stalactite.placeBlocks(level, random, wind);
		}

		if (stalagmiteBaseEmbeddedInStone) {
			stalagmite.placeBlocks(level, random, wind);
		}

		return true;
	}

	private static LargeSpeleothem makeSpeleothem(final BlockState baseBlock, final BlockPos root, final boolean pointingUp, final RandomSource random,
												  final int radius, final FloatProvider bluntness, final FloatProvider heightScale) {
		return new LargeSpeleothem(baseBlock, root, pointingUp, radius, bluntness.sample(random), heightScale.sample(random));
	}

	private static final class LargeSpeleothem {
		private final BlockState baseBlock;
		private BlockPos root;
		private final boolean pointingUp;
		private int radius;
		private final double bluntness;
		private final double scale;

		private LargeSpeleothem(final BlockState baseBlock, final BlockPos root, final boolean pointingUp, final int radius, final double bluntness, final double scale) {
			this.baseBlock = baseBlock;
			this.root = root;
			this.pointingUp = pointingUp;
			this.radius = radius;
			this.bluntness = bluntness;
			this.scale = scale;
		}

		private int getHeight() {
			return this.getHeightAtRadius(0.0F);
		}

		private boolean moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(final WorldGenLevel level, final WindOffsetter wind) {
			while (this.radius > 1) {
				BlockPos.MutableBlockPos newRoot = this.root.mutable();
				int maxTries = Math.min(10, this.getHeight());

				for (int i = 0; i < maxTries; i++) {
					if (level.getBlockState(newRoot).is(Blocks.LAVA)) {
						return false;
					}

					if (SpeleothemUtils.isCircleMostlyEmbeddedInStone(level, wind.offset(newRoot), this.radius)) {
						this.root = newRoot;
						return true;
					}

					newRoot.move(this.pointingUp ? Direction.DOWN : Direction.UP);
				}

				this.radius /= 2;
			}

			return false;
		}

		private int getHeightAtRadius(final float checkRadius) {
			return (int) SpeleothemUtils.getSpeleothemHeight(checkRadius, this.radius, this.scale, this.bluntness);
		}

		private void placeBlocks(final WorldGenLevel level, final RandomSource random, final WindOffsetter wind) {
			for (int dx = -this.radius; dx <= this.radius; dx++) {
				for (int dz = -this.radius; dz <= this.radius; dz++) {
					float currentRadius = Mth.sqrt(dx * dx + dz * dz);
					if (currentRadius > this.radius) {
						continue;
					}

					int height = this.getHeightAtRadius(currentRadius);
					if (height <= 0) {
						continue;
					}

					if (random.nextFloat() < 0.2) {
						height = (int) (height * Mth.randomBetween(random, 0.8F, 1.0F));
					}

					BlockPos.MutableBlockPos pos = this.root.offset(dx, 0, dz).mutable();
					boolean hasBeenOutOfStone = false;
					int maxY = this.pointingUp ? level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, pos.getX(), pos.getZ()) : Integer.MAX_VALUE;

					for (int i = 0; i < height && pos.getY() < maxY; i++) {
						BlockPos windAdjustedPos = wind.offset(pos);
						if (SpeleothemUtils.isEmptyOrWaterOrLava(level, windAdjustedPos)) {
							hasBeenOutOfStone = true;
							level.setBlock(windAdjustedPos, this.baseBlock.getBlock().defaultBlockState(), Block.UPDATE_CLIENTS);
						} else if (hasBeenOutOfStone && level.getBlockState(windAdjustedPos).is(BlockTags.BASE_STONE_OVERWORLD)) {
							break;
						}

						pos.move(this.pointingUp ? Direction.UP : Direction.DOWN);
					}
				}
			}
		}

		private boolean isSuitableForWind(final int minRadiusForWind, final float minBluntnessForWind) {
			return this.radius >= minRadiusForWind && this.bluntness >= minBluntnessForWind;
		}
	}

	private static final class WindOffsetter {
		private final int originY;
		private final @Nullable Vec3 windSpeed;
		private final int maxOffset;

		private WindOffsetter(final int originY, final RandomSource random, final FloatProvider windSpeedRange, final int maxOffset) {
			this.originY = originY;
			this.maxOffset = maxOffset;
			float speed = windSpeedRange.sample(random);
			float direction = Mth.randomBetween(random, 0.0F, (float) Math.PI);
			this.windSpeed = new Vec3(Mth.cos(direction) * speed, 0.0, Mth.sin(direction) * speed);
		}

		private WindOffsetter() {
			this.originY = 0;
			this.windSpeed = null;
			this.maxOffset = 0;
		}

		private static WindOffsetter noWind() {
			return new WindOffsetter();
		}

		private BlockPos offset(final BlockPos pos) {
			if (this.windSpeed == null) {
				return pos;
			}

			int dy = this.originY - pos.getY();
			Vec3 totalWindAdjust = this.windSpeed.scale(dy);
			int dx = Mth.clamp(Mth.floor(totalWindAdjust.x), -this.maxOffset, this.maxOffset);
			int dz = Mth.clamp(Mth.floor(totalWindAdjust.z), -this.maxOffset, this.maxOffset);
			return pos.offset(dx, 0, dz);
		}
	}

	public record Configuration(
			BlockState baseBlock,
			HolderSet<Block> replaceableBlocks,
			int floorToCeilingSearchRange,
			IntProvider columnRadius,
			FloatProvider heightScale,
			float maxColumnRadiusToCaveHeightRatio,
			FloatProvider stalactiteBluntness,
			FloatProvider stalagmiteBluntness,
			FloatProvider windSpeed,
			int minRadiusForWind,
			float minBluntnessForWind
	) /*? if <26.3 {*//*implements FeatureConfiguration*//*?}*/ {
		public static final MapCodec<Configuration> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
				BlockState.CODEC.fieldOf("base_block").forGetter(Configuration::baseBlock),
				WorldgenCompat.blocks().fieldOf("replaceable_blocks").forGetter(Configuration::replaceableBlocks),
				Codec.intRange(1, 512).optionalFieldOf("floor_to_ceiling_search_range", 30).forGetter(Configuration::floorToCeilingSearchRange),
				WorldgenCompat.intProvider(1, 16).fieldOf("column_radius").forGetter(Configuration::columnRadius),
				WorldgenCompat.floatProvider(0.0F, 20.0F).fieldOf("height_scale").forGetter(Configuration::heightScale),
				Codec.floatRange(0.1F, 1.0F).fieldOf("max_column_radius_to_cave_height_ratio").forGetter(Configuration::maxColumnRadiusToCaveHeightRatio),
				WorldgenCompat.floatProvider(0.1F, 10.0F).fieldOf("stalactite_bluntness").forGetter(Configuration::stalactiteBluntness),
				WorldgenCompat.floatProvider(0.1F, 10.0F).fieldOf("stalagmite_bluntness").forGetter(Configuration::stalagmiteBluntness),
				WorldgenCompat.floatProvider(0.0F, 2.0F).fieldOf("wind_speed").forGetter(Configuration::windSpeed),
				Codec.intRange(0, 100).fieldOf("min_radius_for_wind").forGetter(Configuration::minRadiusForWind),
				Codec.floatRange(0.0F, 5.0F).fieldOf("min_bluntness_for_wind").forGetter(Configuration::minBluntnessForWind)
		).apply(i, Configuration::new));
	}
}
//?}
