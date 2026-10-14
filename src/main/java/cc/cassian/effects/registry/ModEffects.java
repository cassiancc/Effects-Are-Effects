package cc.cassian.effects.registry;

import cc.cassian.effects.EffectsAreEffects;
//~ if >26.3 'cc.cassian.effects.effect'-> 'net.minecraft.world.effect'
import cc.cassian.effects.effect.FreezingMobEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;

public class ModEffects {
	public static final Holder<MobEffect> BURNING =
			Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, EffectsAreEffects.of("burning"), new BurningEffect(MobEffectCategory.HARMFUL, 0xeb6607));
	//? if <26.4 {
	public static final Holder<MobEffect> FREEZING = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,
			EffectsAreEffects.of("freezing"), new FreezingMobEffect(MobEffectCategory.HARMFUL, 10415854).withSoundOnAdded(ModSoundEvents.APPLY_EFFECT_FREEZING)
	);
	//?} else {
	/*public static final Holder<MobEffect> FREEZING = MobEffects.FREEZING;
	*///?}

	public static void init() {

	}

	private static class BurningEffect extends MobEffect {
		public BurningEffect(MobEffectCategory category, int color) {
			super(category, color);
		}
	}

}
