//? if <26.4 {
package cc.cassian.effects.entity;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.function.IntSupplier;

public class FrostbiteRangedAttackGoal extends Goal {
	private final Frostbite mob;
	private final double speedModifier;
	private final IntSupplier attackIntervalMin;
	private final IntSupplier attackIntervalMax;
	private final float attackRadius;
	private final float attackRadiusSqr;
	private LivingEntity target;
	private int attackTime = -1;
	private int seeTime;

	public FrostbiteRangedAttackGoal(final Frostbite mob, final double speedModifier, final IntSupplier attackIntervalMin, final IntSupplier attackIntervalMax,
									 final float attackRadius) {
		this.mob = mob;
		this.speedModifier = speedModifier;
		this.attackIntervalMin = attackIntervalMin;
		this.attackIntervalMax = attackIntervalMax;
		this.attackRadius = attackRadius;
		this.attackRadiusSqr = attackRadius * attackRadius;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		LivingEntity bestTarget = this.mob.getTarget();
		if (bestTarget != null && bestTarget.isAlive() && this.mob.shouldDoRangedAttack()) {
			this.target = bestTarget;
			return true;
		}
		return false;
	}

	@Override
	public boolean canContinueToUse() {
		return (this.canUse() || this.target.isAlive() && !this.mob.getNavigation().isDone()) && this.mob.shouldDoRangedAttack();
	}

	@Override
	public void start() {
		super.start();
		this.mob.setAggressive(true);
		this.mob.setRanged(true);
	}

	@Override
	public void stop() {
		this.target = null;
		this.seeTime = 0;
		this.attackTime = -1;
		this.mob.setAggressive(false);
		this.mob.setRanged(false);
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		double targetDistSqr = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
		boolean hasLineOfSight = this.mob.getSensing().hasLineOfSight(this.target);
		if (hasLineOfSight) {
			this.seeTime++;
		} else {
			this.seeTime = 0;
		}

		boolean targetIsOutOfRange = targetDistSqr > this.attackRadiusSqr;
		this.mob.getNavigation().moveTo(this.target, this.speedModifier);
		this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
		if (--this.attackTime == 0) {
			if (!hasLineOfSight || targetIsOutOfRange) {
				return;
			}

			float dist = (float) Math.sqrt(targetDistSqr) / this.attackRadius;
			float power = Mth.clamp(dist, 0.1F, 1.0F);
			this.mob.performRangedAttack(this.target, power);
			this.attackTime = Mth.floor(dist * (this.attackIntervalMax.getAsInt() - this.attackIntervalMin.getAsInt()) + this.attackIntervalMin.getAsInt());
		} else if (this.attackTime < 0) {
			this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(targetDistSqr) / this.attackRadius, this.attackIntervalMin.getAsInt(), this.attackIntervalMax.getAsInt()));
		}
	}
}
//?}
