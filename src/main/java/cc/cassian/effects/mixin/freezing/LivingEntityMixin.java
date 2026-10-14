package cc.cassian.effects.mixin.freezing;

import cc.cassian.effects.registry.ModEffects;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	//? if <26.4 {
	@ModifyReturnValue(at = @At(value = "RETURN"), method = "canBeAffected")
	private boolean preventFreezing(boolean original,final MobEffectInstance newEffect) {
		if (this.is(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES))
			return !newEffect.is(ModEffects.FREEZING);
		return original;
	}
	//?}
}