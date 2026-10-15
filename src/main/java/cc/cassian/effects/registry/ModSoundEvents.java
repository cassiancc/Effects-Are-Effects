package cc.cassian.effects.registry;

import cc.cassian.effects.EmbersAndIcicles;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
//? if <26.4
import net.minecraft.world.level.block.SoundType;

public class ModSoundEvents {
	//? if <26.4 {
	public static final SoundEvent APPLY_EFFECT_FREEZING = register("event.mob_effect.freezing");
	public static final SoundEvent ICE_BALL_BREAK = register("entity.ice_ball.break");
	public static final SoundEvent ICE_BALL_THROW = register("entity.ice_ball.throw");

	public static final SoundEvent BLUE_ICE_BREAK = register("block.blue_ice.break");
	public static final SoundEvent BLUE_ICE_FALL = register("block.blue_ice.fall");
	public static final SoundEvent BLUE_ICE_HIT = register("block.blue_ice.hit");
	public static final SoundEvent BLUE_ICE_PLACE = register("block.blue_ice.place");
	public static final SoundEvent BLUE_ICE_STEP = register("block.blue_ice.step");

	public static final SoundEvent BRITTLE_ICE_BREAK = register("block.brittle_ice.break");
	public static final SoundEvent BRITTLE_ICE_CRACK = register("block.brittle_ice.crack");
	public static final SoundEvent BRITTLE_ICE_FALL = register("block.brittle_ice.fall");
	public static final SoundEvent BRITTLE_ICE_HIT = register("block.brittle_ice.hit");
	public static final SoundEvent BRITTLE_ICE_PLACE = register("block.brittle_ice.place");
	public static final SoundEvent BRITTLE_ICE_STEP = register("block.brittle_ice.step");

	public static final SoundEvent FROSTED_ICE_BREAK = register("block.frosted_ice.break");
	public static final SoundEvent FROSTED_ICE_FALL = register("block.frosted_ice.fall");
	public static final SoundEvent FROSTED_ICE_HIT = register("block.frosted_ice.hit");
	public static final SoundEvent FROSTED_ICE_PLACE = register("block.frosted_ice.place");
	public static final SoundEvent FROSTED_ICE_STEP = register("block.frosted_ice.step");

	public static final SoundEvent ICE_BREAK = register("block.ice.break");
	public static final SoundEvent ICE_FALL = register("block.ice.fall");
	public static final SoundEvent ICE_HIT = register("block.ice.hit");
	public static final SoundEvent ICE_PLACE = register("block.ice.place");
	public static final SoundEvent ICE_STEP = register("block.ice.step");

	public static final SoundEvent ICE_CRYSTAL_BREAK = register("block.ice_crystal.break");
	public static final SoundEvent ICE_CRYSTAL_FALL = register("block.ice_crystal.fall");
	public static final SoundEvent ICE_CRYSTAL_HIT = register("block.ice_crystal.hit");
	public static final SoundEvent ICE_CRYSTAL_PLACE = register("block.ice_crystal.place");
	public static final SoundEvent ICE_CRYSTAL_STEP = register("block.ice_crystal.step");

	public static final SoundEvent ICICLE_BREAK = register("block.icicle.break");
	public static final SoundEvent ICICLE_FALL = register("block.icicle.fall");
	public static final SoundEvent ICICLE_HIT = register("block.icicle.hit");
	public static final SoundEvent ICICLE_LAND = register("block.icicle.land");
	public static final SoundEvent ICICLE_PLACE = register("block.icicle.place");
	public static final SoundEvent ICICLE_STEP = register("block.icicle.step");

	public static final SoundEvent PACKED_ICE_BREAK = register("block.packed_ice.break");
	public static final SoundEvent PACKED_ICE_FALL = register("block.packed_ice.fall");
	public static final SoundEvent PACKED_ICE_HIT = register("block.packed_ice.hit");
	public static final SoundEvent PACKED_ICE_PLACE = register("block.packed_ice.place");
	public static final SoundEvent PACKED_ICE_STEP = register("block.packed_ice.step");

	public static final SoundEvent DROWNED_CONVERTED_TO_ZOMBIE = register("entity.drowned.converted_to_zombie");
	public static final SoundEvent ZOMBIE_CONVERTED_TO_FROSTBITE = register("entity.zombie.converted_to_frostbite");
	public static final SoundEvent FROSTBITE_CONVERTED_TO_ZOMBIE = register("entity.frostbite.converted_to_zombie");
	public static final SoundEvent FROSTBITE_AMBIENT = register("entity.frostbite.ambient");
	public static final SoundEvent FROSTBITE_DEATH = register("entity.frostbite.death");
	public static final SoundEvent FROSTBITE_HURT = register("entity.frostbite.hurt");
	public static final SoundEvent FROSTBITE_STEP = register("entity.frostbite.step");

	public static final SoundType ICE = new SoundType(1.0F, 1.0F, ICE_BREAK, ICE_STEP, ICE_PLACE, ICE_HIT, ICE_FALL);
	public static final SoundType PACKED_ICE = new SoundType(1.0F, 1.0F, PACKED_ICE_BREAK, PACKED_ICE_STEP, PACKED_ICE_PLACE, PACKED_ICE_HIT, PACKED_ICE_FALL);
	public static final SoundType BLUE_ICE = new SoundType(1.0F, 1.0F, BLUE_ICE_BREAK, BLUE_ICE_STEP, BLUE_ICE_PLACE, BLUE_ICE_HIT, BLUE_ICE_FALL);
	public static final SoundType FROSTED_ICE = new SoundType(1.0F, 1.0F, FROSTED_ICE_BREAK, FROSTED_ICE_STEP, FROSTED_ICE_PLACE, FROSTED_ICE_HIT, FROSTED_ICE_FALL);
	public static final SoundType BRITTLE_ICE = new SoundType(1.0F, 1.0F, BRITTLE_ICE_BREAK, BRITTLE_ICE_STEP, BRITTLE_ICE_PLACE, BRITTLE_ICE_HIT, BRITTLE_ICE_FALL);
	public static final SoundType ICE_CRYSTAL = new SoundType(1.0F, 1.0F, ICE_CRYSTAL_BREAK, ICE_CRYSTAL_STEP, ICE_CRYSTAL_PLACE, ICE_CRYSTAL_HIT, ICE_CRYSTAL_FALL);
	public static final SoundType ICICLE = new SoundType(1.0F, 1.0F, ICICLE_BREAK, ICICLE_STEP, ICICLE_PLACE, ICICLE_HIT, ICICLE_FALL);
	//?}

	private static SoundEvent register(String s) {
		return Registry.register(BuiltInRegistries.SOUND_EVENT, EmbersAndIcicles.of(s), SoundEvent.createVariableRangeEvent(EmbersAndIcicles.of(s)));
	}

	public static void init() {

	}
}
