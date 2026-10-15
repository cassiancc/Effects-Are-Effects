//? if <26.4 {
package cc.cassian.effects.mixin.backport;

import cc.cassian.effects.EmbersAndIcicles;
import cc.cassian.effects.entity.FreezingConvertible;
import cc.cassian.effects.registry.ModAttachments;
import cc.cassian.effects.registry.ModEntityTypes;
import cc.cassian.effects.registry.ModSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//~ if >=26.2 'EntityType;'->'EntityTypes;'
import net.minecraft.world.entity.EntityTypes;
//? if >=26 {
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ConversionParams;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
//?} else {
/*import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Shadow;
*///?}

@Mixin(Zombie.class)
public abstract class ZombieMixin extends Monster implements FreezingConvertible {
	@Unique
	private static final int EMBERS$FREEZING_AFFLICTION_TICKS = 140;
	@Unique
	private static final int EMBERS$FREEZING_CONVERSION_TICKS = 300;

	@Unique
	private int embers$freezingTime;
	@Unique
	private int embers$freezingConversionTime;

	protected ZombieMixin(final EntityType<? extends Monster> type, final Level level) {
		super(type, level);
	}

	//? if <26 {
	/*@Shadow
	protected abstract void convertToZombieType(EntityType<? extends Zombie> entityType);
	*///?}

	@Override
	public boolean embers$isFreezingConverting() {
		return ModAttachments.isFreezingConverting(this);
	}

	@Unique
	private void embers$setFreezingConverting(final boolean converting) {
		ModAttachments.setFreezingConverting(this, converting);
	}

	@Unique
	private @Nullable EntityType<? extends Zombie> embers$convertsToWhenFreezing() {
		EntityType<?> type = this.getType();
		//~ if >=26.2 'EntityType.'->'EntityTypes.'
		if (type == EntityTypes.ZOMBIE) {
			return ModEntityTypes.FROSTBITE;
		}
		//~ if >=26.2 'EntityType.'->'EntityTypes.'
		if (type == EntityTypes.HUSK || type == EntityTypes.DROWNED) {
			//~ if >=26.2 'EntityType.'->'EntityTypes.'
			return EntityTypes.ZOMBIE;
		}
		return null;
	}

	@Unique
	private SoundEvent embers$freezingConversionSound() {
		EntityType<?> type = this.getType();
		//~ if >=26.2 'EntityType.'->'EntityTypes.'
		if (type == EntityTypes.HUSK) {
			return SoundEvents.HUSK_CONVERTED_TO_ZOMBIE;
		}
		//~ if >=26.2 'EntityType.'->'EntityTypes.'
		if (type == EntityTypes.DROWNED) {
			return ModSoundEvents.DROWNED_CONVERTED_TO_ZOMBIE;
		}
		return ModSoundEvents.ZOMBIE_CONVERTED_TO_FROSTBITE;
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void embers$tickFreezingConversion(final CallbackInfo ci) {
		if (this.level().isClientSide() || !this.isAlive() || this.isNoAi() || !EmbersAndIcicles.CONFIG.freezingConversions) {
			return;
		}

		EntityType<? extends Zombie> convertsTo = this.embers$convertsToWhenFreezing();
		if (convertsTo == null) {
			return;
		}

		if (this.isInPowderSnow) {
			if (this.embers$isFreezingConverting()) {
				this.embers$freezingConversionTime--;
				if (this.embers$freezingConversionTime < 0) {
					this.embers$doFreezingConversion(convertsTo);
				}
			} else {
				this.embers$freezingTime++;
				if (this.embers$freezingTime >= EMBERS$FREEZING_AFFLICTION_TICKS) {
					this.embers$startFreezingConversion(EMBERS$FREEZING_CONVERSION_TICKS);
				}
			}
		} else {
			this.embers$freezingTime = -1;
			this.embers$setFreezingConverting(false);
		}
	}

	@Unique
	private void embers$startFreezingConversion(final int time) {
		this.embers$freezingConversionTime = time;
		this.embers$setFreezingConverting(true);
	}

	@Unique
	private void embers$doFreezingConversion(final EntityType<? extends Zombie> convertsTo) {
		SoundEvent sound = this.embers$freezingConversionSound();
		//? if >=26 {
		if (!(this.level() instanceof ServerLevel serverLevel)) {
			return;
		}
		Zombie converted = this.convertTo(convertsTo, ConversionParams.single(this, true, true), zombie -> ((ZombieInvoker) zombie).embers$handleAttributes(
				serverLevel.getCurrentDifficultyAt(zombie.blockPosition()).getSpecialMultiplier(), EntitySpawnReason.CONVERSION
		));
		//?} else {
		/*this.convertToZombieType(convertsTo);
		Zombie converted = (Zombie) (Object) this;
		*///?}
		if (converted != null && !converted.isSilent()) {
			this.level().playSound(null, converted.blockPosition(), sound, SoundSource.HOSTILE, 2.0F, this.getRandom().nextFloat() * 0.1F + 0.9F);
		}
	}

	//? if >=26 {
	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	private void embers$saveFreezingConversion(final ValueOutput output, final CallbackInfo ci) {
		output.putInt("FrostbiteConversionTime", this.embers$isFreezingConverting() ? this.embers$freezingConversionTime : -1);
		output.putInt("FreezingTime", this.isInPowderSnow ? this.embers$freezingTime : -1);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	private void embers$loadFreezingConversion(final ValueInput input, final CallbackInfo ci) {
		this.embers$freezingTime = input.getIntOr("FreezingTime", 0);
		int conversionTime = input.getIntOr("FrostbiteConversionTime", -1);
		if (conversionTime != -1) {
			this.embers$startFreezingConversion(conversionTime);
		} else {
			this.embers$setFreezingConverting(false);
		}
	}
	//?} else {
	/*@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	private void embers$saveFreezingConversion(final CompoundTag tag, final CallbackInfo ci) {
		tag.putInt("FrostbiteConversionTime", this.embers$isFreezingConverting() ? this.embers$freezingConversionTime : -1);
		tag.putInt("FreezingTime", this.isInPowderSnow ? this.embers$freezingTime : -1);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	private void embers$loadFreezingConversion(final CompoundTag tag, final CallbackInfo ci) {
		this.embers$freezingTime = tag.getInt("FreezingTime");
		if (tag.contains("FrostbiteConversionTime", 99) && tag.getInt("FrostbiteConversionTime") > -1) {
			this.embers$startFreezingConversion(tag.getInt("FrostbiteConversionTime"));
		}
	}
	*///?}
}
//?}
