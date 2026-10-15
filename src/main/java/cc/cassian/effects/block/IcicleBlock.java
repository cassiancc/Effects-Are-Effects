//? if <26.4 {
package cc.cassian.effects.block;

import cc.cassian.effects.registry.ModSoundEvents;
import cc.cassian.effects.registry.ModTags;
//? if <26.3
//import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.ThrownTrident;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.SpeleothemThickness;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;
//? if >=26 {
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.ScheduledTickAccess;
//?} else {
/*import net.minecraft.world.level.block.state.properties.DirectionProperty;
*///?}

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class IcicleBlock extends Block implements Fallable, SimpleWaterloggedBlock {
	//? if <26.3 {
	/*public static final MapCodec<IcicleBlock> CODEC = simpleCodec(IcicleBlock::new);
	*///?}
	//? if >=26 {
	public static final EnumProperty<Direction> TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
	//?} else {
	/*public static final DirectionProperty TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
	*///?}
	public static final EnumProperty<SpeleothemThickness> THICKNESS = BlockStateProperties.SPELEOTHEM_THICKNESS;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;

	private static final int MAX_GROWTH_LENGTH = 4;
	private static final int BREAKS_IF_ABOVE_LIGHT_LEVEL = 4;
	private static final float DRIP_PROBABILITY_PER_ANIMATE_TICK = 0.02F;
	private static final float GROWTH_PROBABILITY_PER_RANDOM_TICK = 0.011377778F;
	private static final double MIN_TRIDENT_VELOCITY_TO_BREAK = 0.6;
	private static final int STALACTITE_MAX_DAMAGE = 40;
	private static final int MAX_STALACTITE_HEIGHT_FOR_DAMAGE_CALCULATION = 6;
	private static final float STALAGMITE_FALL_DISTANCE_OFFSET = 2.5F;
	private static final float STALAGMITE_FALL_DAMAGE_MODIFIER = 2.0F;

	private static final VoxelShape SHAPE_TIP_MERGE = Block.box(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
	private static final VoxelShape SHAPE_TIP_UP = Block.box(5.0, 0.0, 5.0, 11.0, 11.0, 11.0);
	private static final VoxelShape SHAPE_TIP_DOWN = Block.box(5.0, 5.0, 5.0, 11.0, 16.0, 11.0);
	private static final VoxelShape SHAPE_FRUSTUM = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
	private static final VoxelShape SHAPE_MIDDLE = Block.box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
	private static final VoxelShape SHAPE_BASE = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
	private static final double STALACTITE_DRIP_START_PIXEL = SHAPE_TIP_DOWN.min(Direction.Axis.Y);

	public IcicleBlock(final BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any()
				.setValue(TIP_DIRECTION, Direction.UP)
				.setValue(THICKNESS, SpeleothemThickness.TIP)
				.setValue(WATERLOGGED, false)
				.setValue(ATTACHED, false));
	}

	//? if <26.3 {
	/*@Override
	public MapCodec<IcicleBlock> codec() {
		return CODEC;
	}
	*///?}

	@Override
	protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(TIP_DIRECTION, THICKNESS, WATERLOGGED, ATTACHED);
	}

	@Override
	protected boolean canSurvive(final BlockState state, final LevelReader level, final BlockPos pos) {
		return this.isValidPlacement(level, pos, state.getValue(TIP_DIRECTION));
	}

	@Override
	//? if >=26 {
	protected BlockState updateShape(final BlockState state, final LevelReader level, final ScheduledTickAccess ticks, final BlockPos pos,
									 final Direction directionToNeighbour, final BlockPos neighbourPos, final BlockState neighbourState, final RandomSource random) {
	//?} else {
	/*protected BlockState updateShape(final BlockState state, final Direction directionToNeighbour, final BlockState neighbourState, final LevelAccessor level,
									 final BlockPos pos, final BlockPos neighbourPos) {
		LevelAccessor ticks = level;
	*///?}
		if (state.getValue(WATERLOGGED)) {
			ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}

		if (directionToNeighbour != Direction.UP && directionToNeighbour != Direction.DOWN) {
			return state;
		}

		Direction tipDirection = state.getValue(TIP_DIRECTION);
		if (tipDirection == Direction.DOWN && ticks.getBlockTicks().hasScheduledTick(pos, this)) {
			return state;
		}

		if (directionToNeighbour == tipDirection.getOpposite() && !this.canSurvive(state, level, pos)) {
			ticks.scheduleTick(pos, this, tipDirection == Direction.DOWN ? 2 : 1);
			return state;
		}

		boolean mergeOpposingTips = state.getValue(THICKNESS) == SpeleothemThickness.TIP_MERGE;
		return state.setValue(THICKNESS, this.calculateThickness(level, pos, tipDirection, mergeOpposingTips));
	}

	@Override
	public @Nullable BlockState getStateForPlacement(final BlockPlaceContext context) {
		LevelAccessor level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Direction defaultTipDirection = context.getNearestLookingVerticalDirection().getOpposite();
		Direction tipDirection = this.calculateTipDirection(level, pos, defaultTipDirection);
		if (tipDirection == null) {
			return null;
		}

		boolean mergeOpposingTips = !context.isSecondaryUseActive();
		SpeleothemThickness thickness = this.calculateThickness(level, pos, tipDirection, mergeOpposingTips);
		BlockState state = this.defaultBlockState()
				.setValue(TIP_DIRECTION, tipDirection)
				.setValue(THICKNESS, thickness)
				.setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER);
		boolean attached = isStalactiteStartPos(state, level, pos) || isStalagmiteStartPos(state, level, pos);
		return state.setValue(ATTACHED, attached);
	}

	private @Nullable Direction calculateTipDirection(final LevelReader level, final BlockPos pos, final Direction defaultTipDirection) {
		if (this.isValidPlacement(level, pos, defaultTipDirection)) {
			return defaultTipDirection;
		} else if (this.isValidPlacement(level, pos, defaultTipDirection.getOpposite())) {
			return defaultTipDirection.getOpposite();
		} else {
			return null;
		}
	}

	private SpeleothemThickness calculateThickness(final LevelReader level, final BlockPos pos, final Direction tipDirection, final boolean mergeOpposingTips) {
		Direction baseDirection = tipDirection.getOpposite();
		BlockState inFrontState = level.getBlockState(pos.relative(tipDirection));
		if (isSpeleothemWithDirection(inFrontState, baseDirection) && inFrontState.is(this)) {
			return !mergeOpposingTips && inFrontState.getValue(THICKNESS) != SpeleothemThickness.TIP_MERGE
					? SpeleothemThickness.TIP
					: SpeleothemThickness.TIP_MERGE;
		} else if (!isSpeleothemWithDirection(inFrontState, tipDirection)) {
			return SpeleothemThickness.TIP;
		} else {
			SpeleothemThickness inFrontThickness = inFrontState.getValue(THICKNESS);
			if (inFrontThickness != SpeleothemThickness.TIP && inFrontThickness != SpeleothemThickness.TIP_MERGE) {
				BlockState behindState = level.getBlockState(pos.relative(baseDirection));
				return !isSpeleothemWithDirection(behindState, tipDirection) ? SpeleothemThickness.BASE : SpeleothemThickness.MIDDLE;
			} else {
				return SpeleothemThickness.FRUSTUM;
			}
		}
	}

	private boolean isValidPlacement(final LevelReader level, final BlockPos pos, final Direction tipDirection) {
		BlockPos behindPos = pos.relative(tipDirection.getOpposite());
		BlockState behindState = level.getBlockState(behindPos);
		return behindState.isFaceSturdy(level, behindPos, tipDirection) || isSpeleothemWithDirection(behindState, tipDirection) && behindState.is(this);
	}

	private static boolean isSpeleothemWithDirection(final BlockState state, final Direction tipDirection) {
		return state.is(ModTags.SPELEOTHEMS)
				&& state.hasProperty(TIP_DIRECTION)
				&& state.hasProperty(THICKNESS)
				&& state.getValue(TIP_DIRECTION) == tipDirection;
	}

	@Override
	protected VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
		return switch (state.getValue(THICKNESS)) {
			case TIP_MERGE -> SHAPE_TIP_MERGE;
			case TIP -> state.getValue(TIP_DIRECTION) == Direction.DOWN ? SHAPE_TIP_DOWN : SHAPE_TIP_UP;
			case FRUSTUM -> SHAPE_FRUSTUM;
			case MIDDLE -> SHAPE_MIDDLE;
			case BASE -> SHAPE_BASE;
		};
	}

	@Override
	protected boolean isCollisionShapeFullBlock(final BlockState state, final BlockGetter level, final BlockPos pos) {
		return false;
	}

	@Override
	protected boolean isPathfindable(final BlockState state, final PathComputationType type) {
		return false;
	}

	@Override
	protected FluidState getFluidState(final BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected void onProjectileHit(final Level level, final BlockState state, final BlockHitResult blockHit, final Projectile projectile) {
		BlockPos pos = blockHit.getBlockPos();
		//? if >=26 {
		if (level instanceof ServerLevel serverLevel
				&& projectile.mayInteract(serverLevel, pos)
				//~ if <26.3 'mayBreak(serverLevel, pos)'->'mayBreak(serverLevel)'
				&& projectile.mayBreak(serverLevel, pos)
		//?} else {
		/*if (!level.isClientSide()
				&& projectile.mayInteract(level, pos)
				&& projectile.mayBreak(level)
		*///?}
				&& projectile instanceof ThrownTrident
				&& projectile.getDeltaMovement().length() > MIN_TRIDENT_VELOCITY_TO_BREAK) {
			level.destroyBlock(pos, true);
		}
	}

	@Override
	//? if >=26 {
	public void fallOn(final Level level, final BlockState state, final BlockPos pos, final Entity entity, final double fallDistance) {
	//?} else {
	/*public void fallOn(final Level level, final BlockState state, final BlockPos pos, final Entity entity, final float fallDistance) {
	*///?}
		if (state.getValue(TIP_DIRECTION) == Direction.UP && state.getValue(THICKNESS) == SpeleothemThickness.TIP) {
			entity.causeFallDamage(fallDistance + STALAGMITE_FALL_DISTANCE_OFFSET, STALAGMITE_FALL_DAMAGE_MODIFIER, level.damageSources().stalagmite());
		} else {
			super.fallOn(level, state, pos, entity, fallDistance);
		}
	}

	@Override
	protected void tick(final BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource random) {
		if (isStalagmite(state) && !this.canSurvive(state, level, pos)) {
			level.destroyBlock(pos, true);
		} else {
			spawnFallingStalactite(state, level, pos);
		}
	}

	private static void spawnFallingStalactite(final BlockState state, final ServerLevel level, final BlockPos pos) {
		BlockPos.MutableBlockPos fallPos = pos.mutable();
		BlockState fallState = state;

		while (isStalactite(fallState)) {
			FallingBlockEntity entity = FallingBlockEntity.fall(level, fallPos, fallState);
			if (isTip(fallState, true)) {
				int size = Math.max(1 + pos.getY() - fallPos.getY(), MAX_STALACTITE_HEIGHT_FOR_DAMAGE_CALCULATION);
				entity.setHurtsEntities(size, STALACTITE_MAX_DAMAGE);
				break;
			}

			fallPos.move(Direction.DOWN);
			fallState = level.getBlockState(fallPos);
		}
	}

	@Override
	public void onBrokenAfterFall(final Level level, final BlockPos pos, final FallingBlockEntity entity) {
		if (!entity.isSilent()) {
			level.playSound(null, pos, ModSoundEvents.ICICLE_LAND, SoundSource.BLOCKS, 2.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
		}
	}

	@Override
	public DamageSource getFallDamageSource(final Entity entity) {
		return entity.damageSources().fallingStalactite(entity);
	}

	@Override
	public void animateTick(final BlockState state, final Level level, final BlockPos pos, final RandomSource random) {
		if (isFreeHangingStalactite(state) && random.nextFloat() <= DRIP_PROBABILITY_PER_ANIMATE_TICK) {
			double x = pos.getX() + 0.5;
			double y = pos.getY() + STALACTITE_DRIP_START_PIXEL - 0.0625;
			double z = pos.getZ() + 0.5;
			level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.SNOW.defaultBlockState()), x, y, z, 0.0, 0.0, 0.0);
		}
	}

	@Override
	protected void randomTick(final BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource random) {
		if (!isStartPos(state, level, pos) && this.wouldMelt(level, pos)) {
			if (!isStalagmite(state) && (!isTip(state, true) || level.getBlockState(pos.below()).isAir())) {
				spawnFallingStalactite(state, level, pos);
			} else {
				level.destroyBlock(pos, true);
			}
		} else if (random.nextFloat() < GROWTH_PROBABILITY_PER_RANDOM_TICK && isStalactiteStartPos(state, level, pos)) {
			this.growStalactiteIfPossible(state, level, pos);
		}
	}

	private static boolean isStartPos(final BlockState state, final LevelReader level, final BlockPos pos) {
		return isStalactiteStartPos(state, level, pos) || isStalagmiteStartPos(state, level, pos);
	}

	private boolean wouldMelt(final LevelReader level, final BlockPos pos) {
		//? if >=26 {
		boolean evaporatesWater = level.environmentAttributes().getValue(EnvironmentAttributes.WATER_EVAPORATES, pos);
		//?} else {
		/*boolean evaporatesWater = level.dimensionType().ultraWarm();
		*///?}
		return evaporatesWater
				|| level.getBrightness(LightLayer.BLOCK, pos) > BREAKS_IF_ABOVE_LIGHT_LEVEL
				|| level.getBlockState(pos.below()).is(ModTags.MELTS_ICICLE_ABOVE)
				|| level.getBlockState(pos.below(2)).is(ModTags.MELTS_ICICLE_ABOVE);
	}

	private boolean canGrow(final LevelReader level, final BlockPos pos) {
		return level.getBlockState(pos.above()).is(Blocks.PACKED_ICE) && !this.wouldMelt(level, pos) && isStalactite(level.getBlockState(pos));
	}

	private void growStalactiteIfPossible(final BlockState startState, final ServerLevel level, final BlockPos startPos) {
		if (!this.canGrow(level, startPos)) {
			return;
		}

		BlockPos tipPos = findTip(startState, level, startPos, MAX_GROWTH_LENGTH, false);
		if (tipPos == null) {
			return;
		}

		BlockState tipState = level.getBlockState(tipPos);
		if (isFreeHangingStalactite(tipState) && this.canTipGrow(tipState, level, tipPos)) {
			BlockPos targetPos = tipPos.below();
			BlockState existing = level.getBlockState(targetPos);
			if (existing.isAir() || existing.is(Blocks.WATER)) {
				BlockState newState = this.defaultBlockState()
						.setValue(TIP_DIRECTION, Direction.DOWN)
						.setValue(THICKNESS, SpeleothemThickness.TIP)
						.setValue(WATERLOGGED, level.getFluidState(targetPos).getType() == Fluids.WATER);
				level.setBlockAndUpdate(targetPos, newState);
			}
		}
	}

	private boolean canTipGrow(final BlockState tipState, final ServerLevel level, final BlockPos tipPos) {
		Direction growDirection = tipState.getValue(TIP_DIRECTION);
		BlockPos growPos = tipPos.relative(growDirection);
		BlockState stateAtGrowPos = level.getBlockState(growPos);
		if (!stateAtGrowPos.getFluidState().isEmpty()) {
			return false;
		}

		return stateAtGrowPos.isAir()
				|| isTip(stateAtGrowPos, false) && stateAtGrowPos.getValue(TIP_DIRECTION) == growDirection.getOpposite() && stateAtGrowPos.is(this);
	}

	private static @Nullable BlockPos findTip(final BlockState state, final LevelAccessor level, final BlockPos pos, final int maxSearchLength, final boolean includeMergedTip) {
		if (isTip(state, includeMergedTip)) {
			return pos;
		}

		Direction searchDirection = state.getValue(TIP_DIRECTION);
		BiPredicate<BlockPos, BlockState> pathPredicate = (p, s) -> s.is(state.getBlock()) && s.getValue(TIP_DIRECTION) == searchDirection;
		return findBlockVertical(level, pos, searchDirection.getAxisDirection(), pathPredicate, s -> isTip(s, includeMergedTip), maxSearchLength).orElse(null);
	}

	private static Optional<BlockPos> findBlockVertical(final LevelAccessor level, final BlockPos pos, final Direction.AxisDirection axisDirection,
														final BiPredicate<BlockPos, BlockState> pathPredicate, final Predicate<BlockState> targetPredicate,
														final int maxSteps) {
		Direction direction = Direction.get(axisDirection, Direction.Axis.Y);
		BlockPos.MutableBlockPos mutablePos = pos.mutable();

		for (int i = 1; i < maxSteps; i++) {
			mutablePos.move(direction);
			BlockState state = level.getBlockState(mutablePos);
			if (targetPredicate.test(state)) {
				return Optional.of(mutablePos.immutable());
			}

			if (level.isOutsideBuildHeight(mutablePos.getY()) || !pathPredicate.test(mutablePos, state)) {
				return Optional.empty();
			}
		}

		return Optional.empty();
	}

	private static boolean isStalagmite(final BlockState state) {
		return isSpeleothemWithDirection(state, Direction.UP);
	}

	private static boolean isStalactite(final BlockState state) {
		return isSpeleothemWithDirection(state, Direction.DOWN);
	}

	private static boolean isStalactiteStartPos(final BlockState state, final LevelReader level, final BlockPos pos) {
		return isStalactite(state) && !level.getBlockState(pos.above()).is(state.getBlock());
	}

	private static boolean isStalagmiteStartPos(final BlockState state, final LevelReader level, final BlockPos pos) {
		return isStalagmite(state) && !level.getBlockState(pos.below()).is(state.getBlock());
	}

	private static boolean isTip(final BlockState state, final boolean includeMergedTip) {
		if (!state.is(ModTags.SPELEOTHEMS) || !state.hasProperty(THICKNESS)) {
			return false;
		}

		SpeleothemThickness thickness = state.getValue(THICKNESS);
		return thickness == SpeleothemThickness.TIP || includeMergedTip && thickness == SpeleothemThickness.TIP_MERGE;
	}

	private static boolean isFreeHangingStalactite(final BlockState state) {
		return isStalactite(state) && state.getValue(THICKNESS) == SpeleothemThickness.TIP && !state.getValue(WATERLOGGED);
	}
}
//?}
