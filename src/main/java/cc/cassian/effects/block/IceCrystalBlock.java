//? if <26.4 {
package cc.cassian.effects.block;

//? if <26.3
//import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;
//? if >=26 {
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.state.properties.EnumProperty;
//?} else {
/*import net.minecraft.world.level.block.state.properties.DirectionProperty;
*///?}

public class IceCrystalBlock extends Block implements SimpleWaterloggedBlock {
	//? if <26.3 {
	/*public static final MapCodec<IceCrystalBlock> CODEC = simpleCodec(IceCrystalBlock::new);
	*///?}
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	//? if >=26 {
	public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
	//?} else {
	/*public static final DirectionProperty FACING = BlockStateProperties.FACING;
	*///?}

	private static final int HEIGHT = 7;
	private static final int OFFSET = 3;
	private static final VoxelShape UP = Block.box(OFFSET, 0, OFFSET, 16 - OFFSET, HEIGHT, 16 - OFFSET);
	private static final VoxelShape DOWN = Block.box(OFFSET, 16 - HEIGHT, OFFSET, 16 - OFFSET, 16, 16 - OFFSET);
	private static final VoxelShape NORTH = Block.box(OFFSET, OFFSET, 16 - HEIGHT, 16 - OFFSET, 16 - OFFSET, 16);
	private static final VoxelShape SOUTH = Block.box(OFFSET, OFFSET, 0, 16 - OFFSET, 16 - OFFSET, HEIGHT);
	private static final VoxelShape EAST = Block.box(0, OFFSET, OFFSET, HEIGHT, 16 - OFFSET, 16 - OFFSET);
	private static final VoxelShape WEST = Block.box(16 - HEIGHT, OFFSET, OFFSET, 16, 16 - OFFSET, 16 - OFFSET);

	public IceCrystalBlock(final BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(FACING, Direction.UP));
	}

	//? if <26.3 {
	/*@Override
	public MapCodec<IceCrystalBlock> codec() {
		return CODEC;
	}
	*///?}

	@Override
	protected VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
		return switch (state.getValue(FACING)) {
			case NORTH -> NORTH;
			case SOUTH -> SOUTH;
			case EAST -> EAST;
			case WEST -> WEST;
			case DOWN -> DOWN;
			case UP -> UP;
		};
	}

	@Override
	protected boolean canSurvive(final BlockState state, final LevelReader level, final BlockPos pos) {
		Direction direction = state.getValue(FACING);
		BlockPos adjacentPos = pos.relative(direction.getOpposite());
		return level.getBlockState(adjacentPos).isFaceSturdy(level, adjacentPos, direction);
	}

	@Override
	//? if >=26 {
	protected BlockState updateShape(final BlockState state, final LevelReader level, final ScheduledTickAccess ticks, final BlockPos pos,
									 final Direction directionToNeighbour, final BlockPos neighbourPos, final BlockState neighbourState, final RandomSource random) {
		if (state.getValue(WATERLOGGED)) {
			ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}

		return directionToNeighbour == state.getValue(FACING).getOpposite() && !state.canSurvive(level, pos)
				? Blocks.AIR.defaultBlockState()
				: super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
	}
	//?} else {
	/*protected BlockState updateShape(final BlockState state, final Direction directionToNeighbour, final BlockState neighbourState, final LevelAccessor level,
									 final BlockPos pos, final BlockPos neighbourPos) {
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}

		return directionToNeighbour == state.getValue(FACING).getOpposite() && !state.canSurvive(level, pos)
				? Blocks.AIR.defaultBlockState()
				: super.updateShape(state, directionToNeighbour, neighbourState, level, pos, neighbourPos);
	}
	*///?}

	@Override
	public @Nullable BlockState getStateForPlacement(final BlockPlaceContext context) {
		LevelAccessor level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		return this.defaultBlockState()
				.setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER)
				.setValue(FACING, context.getClickedFace());
	}

	@Override
	protected BlockState rotate(final BlockState state, final Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	protected BlockState mirror(final BlockState state, final Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	protected FluidState getFluidState(final BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, FACING);
	}
}
//?}
