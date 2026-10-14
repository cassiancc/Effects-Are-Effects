package cc.cassian.effects.registry;

import cc.cassian.effects.EffectsAreEffects;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

public class ModSoundEvents {
	//? if <26.4 {
	public static final SoundEvent APPLY_EFFECT_FREEZING = register("event.mob_effect.freezing");
	public static final SoundEvent ICE_BALL_BREAK = register("entity.ice_ball.break");
	public static final SoundEvent ICE_BALL_THROW = register("entity.ice_ball.throw");
	//?}

	private static SoundEvent register(String s) {
		return Registry.register(BuiltInRegistries.SOUND_EVENT, EffectsAreEffects.of(s), SoundEvent.createVariableRangeEvent(EffectsAreEffects.of(s)));
	}

	public static void init() {

	}
}
