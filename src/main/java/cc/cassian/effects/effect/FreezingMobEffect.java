//? if <26.4 {
package cc.cassian.effects.effect;

import cc.cassian.effects.registry.ModParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
//? if >=26
import net.minecraft.server.level.ServerLevel;

public class FreezingMobEffect extends MobEffect {
	public static final float DAMAGE_VALUE = 1.5F;

	public FreezingMobEffect(final MobEffectCategory category, final int color) {
		super(category, color, ModParticleTypes.FREEZING);
	}

	@Override
	//? if >=26 {
	public boolean applyEffectTick(final ServerLevel serverLevel, final LivingEntity mob, final int amplification) {
	//?} else {
	/*public boolean applyEffectTick(final LivingEntity mob, final int amplification) {
	*///?}
		if (mob.canFreeze()) {
			mob.setTicksFrozen(Math.min(mob.getTicksRequiredToFreeze(), mob.getTicksFrozen() + 1));
		}
		return true;
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(final int tickCount, final int amplification) {
		return true;
	}
}

//?}
