//? if <26.4 {
package cc.cassian.effects.entity;

import cc.cassian.effects.registry.ModEffects;
import cc.cassian.effects.registry.ModItems;
import cc.cassian.effects.registry.ModSoundEvents;
import cc.cassian.effects.registry.ModTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.animal.turtle.Turtle;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
//? if >=26 {
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.tags.TagKey;
//?}
//? if >=26.3 {
import net.minecraft.world.entity.ConversionParams;
import org.jspecify.annotations.Nullable;
//?}
//~ if >=26.2 'EntityType;'->'EntityTypes;'
import net.minecraft.world.entity.EntityTypes;

public class Frostbite extends Zombie implements RangedAttackMob {
	private static final int MELEE_ATTACK_TICKS = 30;
	private static final int FREEZING_TICKS_ON_MELEE = 60;
	private static final float MELEE_MODE_DISTANCE_SQR = 9.0F;
	private static final float RANGED_MODE_DISTANCE_SQR = 25.0F;
	private static final float ICE_BALL_CHANCE = 0.25F;
	private boolean isRanged;

	public Frostbite(final EntityType<? extends Frostbite> type, final Level level) {
		super(type, level);
		this.setPathfindingMalus(PathType.POWDER_SNOW, 0.0F);
		//~ if <26 'ON_TOP_OF_POWDER_SNOW'->'DANGER_POWDER_SNOW'
		this.setPathfindingMalus(PathType.ON_TOP_OF_POWDER_SNOW, 0.0F);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Zombie.createAttributes().add(Attributes.MAX_HEALTH, 24.0).add(Attributes.MOVEMENT_SPEED, 0.21F);
	}

	@Override
	protected void addBehaviourGoals() {
		this.goalSelector.addGoal(1, new FrostbiteRangedAttackGoal(this, 1.0, this::getRangedAttackIntervalMin, this::getRangedAttackIntervalMax, 16.0F));
		this.goalSelector.addGoal(2, new FrostbiteMeleeAttackGoal(this, 1.0, false));
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.0));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(ZombifiedPiglin.class));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.FROSTBITE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(final DamageSource source) {
		return ModSoundEvents.FROSTBITE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.FROSTBITE_DEATH;
	}

	@Override
	protected SoundEvent getStepSound() {
		return ModSoundEvents.FROSTBITE_STEP;
	}

	@Override
	protected void populateDefaultEquipmentSlots(final RandomSource random, final DifficultyInstance difficulty) {
		if (random.nextFloat() < ICE_BALL_CHANCE) {
			this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.ICE_BALL));
		}
	}

	//? if >=26 {
	@Override
	public TagKey<Item> getPreferredWeaponType() {
		return ModTags.FROSTBITE_PREFERRED_WEAPONS;
	}
	//?} else {
	/*@Override
	protected boolean canReplaceCurrentItem(final ItemStack candidate, final ItemStack existing) {
		if (existing.is(ModTags.FROSTBITE_PREFERRED_WEAPONS) && !candidate.is(ModTags.FROSTBITE_PREFERRED_WEAPONS)) {
			return false;
		} else if (!existing.is(ModTags.FROSTBITE_PREFERRED_WEAPONS) && candidate.is(ModTags.FROSTBITE_PREFERRED_WEAPONS)) {
			return true;
		}
		return super.canReplaceCurrentItem(candidate, existing);
	}
	*///?}

	@Override
	//? if >=26 {
	public boolean doHurtTarget(final ServerLevel level, final Entity target) {
		boolean result = super.doHurtTarget(level, target);
	//?} else {
	/*public boolean doHurtTarget(final Entity target) {
		boolean result = super.doHurtTarget(target);
	*///?}
		if (result && target instanceof LivingEntity livingEntity) {
			livingEntity.addEffect(new MobEffectInstance(ModEffects.FREEZING, FREEZING_TICKS_ON_MELEE), this);
		}

		return result;
	}

	@Override
	public void performRangedAttack(final LivingEntity target, final float power) {
		ItemStack mainHandItem = this.getMainHandItem();
		ItemStack itemStack = mainHandItem.is(ModItems.ICE_BALL) ? mainHandItem : new ItemStack(ModItems.ICE_BALL);
		//? if >=26 {
		IceBall iceBall = new IceBall(this.level(), this, itemStack);
		//?} else {
		/*IceBall iceBall = new IceBall(this.level(), this);
		iceBall.setItem(itemStack.copyWithCount(1));
		*///?}
		double xd = target.getX() - this.getX();
		double yd = target.getY(0.3333333333333333) - iceBall.getY();
		double zd = target.getZ() - this.getZ();
		double distanceToTarget = Math.sqrt(xd * xd + zd * zd);
		float uncertainty = 14 - this.level().getDifficulty().getId() * 4;
		//? if >=26 {
		if (this.level() instanceof ServerLevel serverLevel) {
			Projectile.spawnProjectileUsingShoot(iceBall, serverLevel, itemStack, xd, yd + distanceToTarget * 0.12, zd, 1.6F, uncertainty);
		}
		//?} else {
		/*iceBall.shoot(xd, yd + distanceToTarget * 0.12, zd, 1.6F, uncertainty);
		this.level().addFreshEntity(iceBall);
		*///?}

		this.playSound(ModSoundEvents.ICE_BALL_THROW, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
		//~ if >=26.3 'swing('->'swingForAttack('
		this.swingForAttack(InteractionHand.MAIN_HAND);
	}

	boolean shouldDoRangedAttack() {
		if (!this.getMainHandItem().is(ModItems.ICE_BALL)) {
			return false;
		}

		LivingEntity target = this.getTarget();
		if (target == null || !target.isAlive()) {
			return false;
		}

		float rangeThreshold = this.isRanged ? MELEE_MODE_DISTANCE_SQR : RANGED_MODE_DISTANCE_SQR;
		if (this.distanceToSqr(target) >= rangeThreshold) {
			return true;
		}

		if (this.isWithinMeleeAttackRange(target)) {
			return false;
		}

		Path path = this.getNavigation().getPath() != null ? this.getNavigation().getPath() : this.getNavigation().createPath(target, 0);
		return path != null && path.getTarget().equals(target.blockPosition()) && !path.canReach();
	}

	void setRanged(final boolean ranged) {
		this.isRanged = ranged;
	}

	private int getRangedAttackIntervalMin() {
		return (int) (20.0F * switch (this.level().getDifficulty()) {
			case PEACEFUL, NORMAL -> 1.0F;
			case EASY -> 3.0F;
			case HARD -> 0.75F;
		});
	}

	private int getRangedAttackIntervalMax() {
		return (int) (20.0F * switch (this.level().getDifficulty()) {
			case PEACEFUL, NORMAL -> 2.0F;
			case EASY -> 3.0F;
			case HARD -> 1.5F;
		});
	}

	@Override
	public void makeStuckInBlock(final BlockState state, final Vec3 speedMultiplier) {
		if (!state.is(Blocks.POWDER_SNOW)) {
			super.makeStuckInBlock(state, speedMultiplier);
		}
	}

	//? if >=26.3 {
	@Override
	protected EntityType<? extends Zombie> convertsToWhenDrowning() {
		return EntityTypes.ZOMBIE;
	}

	@Override
	protected int getConversionSound() {
		return -1;
	}

	@Override
	public <T extends Mob> @Nullable T convertTo(final EntityType<T> entityType, final ConversionParams params, final ConversionParams.AfterConversion<T> afterConversion) {
		T converted = super.convertTo(entityType, params, afterConversion);
		if (converted != null) {
			this.playConversionSound(converted);
		}
		return converted;
	}
	//?} else if >=26.2 {
	/*@Override
	protected void doUnderWaterConversion(final ServerLevel level) {
		this.convertToZombieType(level, EntityTypes.ZOMBIE);
		this.playConversionSound(this);
	}
	*///?} else if >=26 {
	/*@Override
	protected void doUnderWaterConversion(final ServerLevel level) {
		this.convertToZombieType(level, EntityType.ZOMBIE);
		this.playConversionSound(this);
	}
	*///?} else {
	/*@Override
	protected void doUnderWaterConversion() {
		this.convertToZombieType(EntityType.ZOMBIE);
		this.playConversionSound(this);
	}
	*///?}

	private void playConversionSound(final Entity converted) {
		if (!converted.isSilent()) {
			this.level().playSound(null, converted.blockPosition(), ModSoundEvents.FROSTBITE_CONVERTED_TO_ZOMBIE, SoundSource.HOSTILE, 2.0F,
					this.getRandom().nextFloat() * 0.1F + 0.9F);
		}
	}

	static class FrostbiteMeleeAttackGoal extends ZombieAttackGoal {
		FrostbiteMeleeAttackGoal(final Zombie zombie, final double speedModifier, final boolean followingTargetEvenIfNotSeen) {
			super(zombie, speedModifier, followingTargetEvenIfNotSeen);
		}

		@Override
		protected int getAttackInterval() {
			return this.adjustedTickDelay(MELEE_ATTACK_TICKS);
		}
	}
}
//?}
