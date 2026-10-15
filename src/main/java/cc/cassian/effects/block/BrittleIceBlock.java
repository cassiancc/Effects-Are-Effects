//? if <26.4 {
package cc.cassian.effects.block;

import cc.cassian.effects.registry.ModSoundEvents;
import cc.cassian.effects.registry.ModTags;
//? if <26.3
//import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
//? if <26 {
/*import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
*///?}

public class BrittleIceBlock extends HalfTransparentBlock {
	//? if <26.3 {
	/*public static final MapCodec<BrittleIceBlock> CODEC = simpleCodec(BrittleIceBlock::new);
	*///?}
	public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
	public static final int MAX_AGE = 3;
	private static final int TICK_DELAY = 24;

	public BrittleIceBlock(final BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
	}

	//? if <26.3 {
	/*@Override
	public MapCodec<BrittleIceBlock> codec() {
		return CODEC;
	}
	*///?}

	@Override
	public void stepOn(final Level level, final BlockPos pos, final BlockState onState, final Entity entity) {
		if (level instanceof ServerLevel serverLevel
				&& this.shouldEntityIncreaseCracking(entity)
				&& !serverLevel.getBlockTicks().hasScheduledTick(pos, this)) {
			if (this.slightlyMelt(onState, serverLevel, pos)) {
				this.propagateMelting(serverLevel, pos);
			}

			serverLevel.scheduleTick(pos, this, TICK_DELAY);
		}
	}

	protected boolean shouldEntityIncreaseCracking(final Entity entity) {
		return entity instanceof LivingEntity && !entity
				//? if <1.21.2
				//.getType()
				.is(ModTags.DOES_NOT_BREAK_BRITTLE_ICE);
	}

	protected void propagateMelting(final ServerLevel level, final BlockPos pos) {
		BlockPos.MutableBlockPos neighborPos = new BlockPos.MutableBlockPos();

		for (Direction direction : Direction.values()) {
			neighborPos.setWithOffset(pos, direction);
			BlockState neighbour = level.getBlockState(neighborPos);
			if (neighbour.is(this) && !this.slightlyMelt(neighbour, level, neighborPos)) {
				level.scheduleTick(neighborPos, this, TICK_DELAY);
			}
		}
	}

	protected boolean slightlyMelt(final BlockState state, final ServerLevel level, final BlockPos pos) {
		int age = state.getValue(AGE);
		if (age < MAX_AGE) {
			level.setBlock(pos, state.setValue(AGE, age + 1), Block.UPDATE_CLIENTS);
			level.playSound(null, pos, ModSoundEvents.BRITTLE_ICE_CRACK, SoundSource.BLOCKS, 1.0F, 1.0F);
			if (age == 0) {
				//? if >=26 {
				level.levelEvent(2014, pos, getId(state));
				//?} else {
				/*level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 24, 0.3, 0.3, 0.3, 0.0);
				*///?}
			}

			level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
			return false;
		} else {
			level.destroyBlock(pos, false);
			return true;
		}
	}

	@Override
	protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}
}
//?}
