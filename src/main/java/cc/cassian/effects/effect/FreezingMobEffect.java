//? if <26.4 {
package cc.cassian.effects.effect;

import cc.cassian.effects.registry.ModParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class FreezingMobEffect extends MobEffect {
	public FreezingMobEffect(final MobEffectCategory category, final int color) {
		super(category, color, ModParticleTypes.FREEZING);
	}
}

//?}