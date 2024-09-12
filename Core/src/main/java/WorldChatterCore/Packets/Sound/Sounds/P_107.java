package WorldChatterCore.Packets.Sound.Sounds;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public enum P_107 {

    CAVE("CAVE", "ambient.cave"),
    WEATHER_RAIN("WEATHER_RAIN", "weather.rain"),
    WEATHER_THUNDER("WEATHER_THUNDER", "entity.lightning.thunder"),

    // Player
    DEATH("DEATH", "entity.player.death"),
    DAMAGE("DAMAGE", "entity.player.hurt"),
    FALL_DAMAGE_BIG("FALL_DAMAGE_BIG", "entity.player.big_fall"),
    FALL_DAMAGE_SMALL("FALL_DAMAGE_SMALL", "entity.player.small_fall"),
    CRITICAL("CRITICAL", "entity.player.attack.crit"),
    DAMAGE_KNOCKBACK("DAMAGE_KNOCKBACK", "entity.player.attack.knockback"),
    DAMAGE_WEAK("DAMAGE_WEAK", "entity.player.attack.nodamage"),
    DAMAGE_STRONG("DAMAGE_STRONG", "entity.player.attack.strong"),
    DAMAGE_SWEEP("DAMAGE_SWEEP", "entity.player.attack.sweep"),
    BREATH("BREATH", "entity.player.breath"),
    BURP("BURP", "entity.player.burp"),
    LEVEL_UP("LEVEL_UP", "entity.player.levelup"),
    SWIM("SWIM", "entity.player.swim"),
    SPLASH("SWIM_SPLASH", "entity.player.splash"),

    DIG_CLOTH("DIG_CLOTH", "block.cloth.break"),
    DIG_GLASS("DIG_GLASS", "block.glass.break"),
    DIG_GRASS("DIG_GRASS", "block.grass.break"),
    DIG_GRAVEL("DIG_GRAVEL", "block.gravel.break"),
    DIG_SAND("DIG_SAND", "block.sand.break"),
    DIG_SNOW("DIG_SNOW", "block.snow.break"),
    DIG_STONE("DIG_STONE", "block.stone.break"),
    DIG_WOOD("DIG_WOOD", "block.wood.break"),

    STEP_CLOTH("STEP_CLOTH", "block.cloth.step"),
    STEP_GRASS("STEP_GRASS", "block.grass.step"),
    STEP_GRAVEL("STEP_GRAVEL", "block.gravel.step"),
    STEP_LADDER("STEP_LADDER", "block.ladder.step"),
    STEP_SAND("STEP_SAND", "block.sand.step"),
    STEP_SNOW("STEP_SNOW", "block.snow.step"),
    STEP_STONE("STEP_STONE", "block.stone.step"),
    STEP_WOOD("STEP_WOOD", "block.wood.step"),

    FIRE_ACTIVE("FIRE_ACTIVE", "block.fire.ambient"),
    FIRE_IGNITE("FIRE_IGNITE", "item.flintandsteel.use"),

    FIREWORKS_BLAST("FIREWORKS_BLAST", "entity.firework.blast"),
    FIREWORKS_BLAST_FAR("FIREWORKS_BLAST_FAR", "entity.firework.blast_far"),
    FIREWORKS_LARGE_BLAST("FIREWORKS_LARGE_BLAST", "entity.firework.large_blast"),
    FIREWORKS_LARGE_BLAST_FAR("FIREWORKS_LARGE_BLAST_FAR", "entity.firework.large_blast_far"),
    FIREWORKS_LAUNCH("FIREWORKS_LAUNCH", "entity.firework.launch"),
    FIREWORKS_TWINKLE("FIREWORKS_TWINKLE", "entity.firework.twinkle"),
    FIREWORKS_TWINKLE_FAR("FIREWORKS_TWINKLE_FAR", "entity.firework.twinkle_far"),

    RIDING_MINECART("RIDING_MINECART", "entity.minecart.riding"),
    INSIDE_MINECART("INSIDE_MINECART", "entity.minecart.inside"),

    NOTE_BASS("NOTE_BASS", "block.note.bass"),
    NOTE_BASE_DRUM("NOTE_BASE_DRUM", "block.note.basedrum"),
    NOTE_HARP("NOTE_HARP", "block.note.harp"),
    NOTE_HAT("NOTE_HAT", "block.note.hat"),
    NOTE_PLING("NOTE_PLING", "block.note.pling"),
    NOTE_SNARE("NOTE_SNARE", "block.note.snare"),

    PORTAL("PORTAL", "block.portal.ambient"),
    PORTAL_TRAVEL("PORTAL_TRAVEL", "block.portal.travel"),
    PORTAL_TRAVELING("PORTAL_TRAVELING", "block.portal.trigger"),

    ANVIL_BREAK("ANVIL_BREAK", "block.anvil.destroy"),
    ANVIL_LAND("ANVIL_LAND", "block.anvil.land"),
    ANVIL_USE("ANVIL_USE", "block.anvil.use"),
    ARROW_SHOOT("ARROW_SHOOT", "entity.arrow.shoot"),
    ARROW_HIT("ARROW_HIT", "entity.arrow.hit"),
    ITEM_BREAK("ITEM_BREAK", "entity.item.break"),
    CHEAT_OPEN("CHEAT_OPEN", "block.chest.open"),
    CHEST_CLOSE("CHEST_CLOSE", "block.chest.close"),
    DOOR_OPEN("DOOR_OPEN", "block.wooden_door.open"),
    DOOR_CLOSE("DOOR_CLOSE", "block.wooden_door.close"),
    IRON_DOOR_OPEN("IRON_DOOR_OPEN", "block.iron_door.open"),
    IRON_DOOR_CLOSE("IRON_DOOR_CLOSE", "block.iron_door.close"),
    DRINK("DRINK", "entity.generic.drink"),
    EAT("EAT", "entity.generic.eat"),
    EXPLOSION("EXPLOSION", "entity.generic.explode"),
    FIZZ("FIZZ", "entity.generic.fizz"),
    ORB("ORB", "entity.experience_orb.pickup"),
    POP("POP", "entity.item.pickup"),
    SUCCESSFUL_HIT("SUCCESSFUL_HIT", "entity.arrow.hit_player"),
    CLICK("CLICK", "ui.button.click"),
    WOOD_CLICK("WOOD_CLICK", "block.wood_button.click_on"),

    PISTON_IN("PISTON_IN", "block.piston.contract"),
    PISTON_OUT("PISTON_OUT", "block.piston.extend"),

    LIQUID_LAVA("LIQUID_LAVA", "liquid.lava"),
    LIQUID_LAVA_POP("LIQUID_LAVA_POP", "liquid.lavapop"),

    MOB_BAT_DEATH("MOB_BAT_DEATH", "entity.bat.death"),
    MOB_BAT_HURT("MOB_BAT_HURT", "entity.bat.hurt"),
    MOB_BAT_IDLE("MOB_BAT_IDLE", "entity.bat.ambient"),
    MOB_BAT_LOOP("MOB_BAT_LOOP", "entity.bat.loop"),
    MOB_BAT_TAKEOFF("MOB_BAT_TAKEOFF", "entity.bat.takeoff"),

    MOB_BLAZE_BREATHE("MOB_BLAZE_BREATHE", "entity.blaze.ambient"),
    MOB_BLAZE_DEATH("MOB_BLAZE_DEATH", "entity.blaze.death"),
    MOB_BLAZE_HIT("MOB_BLAZE_HIT", "entity.blaze.hit"),

    MOB_CAT_HISS("MOB_CAT_HISS", "entity.cat.hiss"),
    MOB_CAT_HIT("MOB_CAT_HIT", "entity.cat.hurt"),
    MOB_CAT_MEOW("MOB_CAT_MEOW", "entity.cat.ambient"),
    MOB_CAT_PURR("MOB_CAT_PURR", "entity.cat.purr"),
    MOB_CAT_PURREOW("MOB_CAT_PURREOW", "entity.cat.purreow"),

    MOB_COW_HURT("MOB_COW_HURT", "entity.cow.hurt"),
    MOB_COW_SAY("MOB_COW_SAY", "entity.cow.ambient"),
    MOB_COW_STEP("MOB_COW_STEP", "entity.cow.step"),
    MOB_COW_MILK("MOB_COW_MILK", "entity.cow.milk"),

    MOB_CREEPER_DEATH("MOB_CREEPER_DEATH", "entity.creeper.death"),
    MOB_CREEPER_HIT("MOB_CREEPER_HIT", "entity.creeper.hurt"),
    MOB_CREEPER_PRIMED("MOB_CREEPER_PRIMED", "entity.creeper.primed"),

    MOB_ENDERDRAGON_DEATH("MOB_ENDERDRAGON_DEATH", "entity.enderdragon.death"),
    MOB_ENDERDRAGON_GROWL("MOB_ENDERDRAGON_GROWL", "entity.enderdragon.ambient"),
    MOB_ENDERDRAGON_HIT("MOB_ENDERDRAGON_HIT", "entity.enderdragon.hit"),
    MOB_ENDERDRAGON_WINGS("MOB_ENDERDRAGON_WINGS", "entity.enderdragon.flap"),

    MOB_ENDERMAN_DEATH("MOB_ENDERMAN_DEATH", "entity.endermen.death"),
    MOB_ENDERMAN_HIT("MOB_ENDERMAN_HIT", "entity.endermen.hurt"),
    MOB_ENDERMAN_IDLE("MOB_ENDERMAN_IDLE", "entity.endermen.ambient"),
    MOB_ENDERMAN_TELEPORT("MOB_ENDERMAN_TELEPORT", "entity.endermen.teleport"),
    MOB_ENDERMAN_SCREAM("MOB_ENDERMAN_SCREAM", "entity.endermen.scream"),
    MOB_ENDERMAN_STARE("MOB_ENDERMAN_STARE", "entity.endermen.stare"),

    MOB_GHAST_AFFECTIONATE_SCREAM("MOB_GHAST_AFFECTIONATE_SCREAM", "entity.ghast.scream"),
    MOB_GHAST_CHARGE("MOB_GHAST_CHARGE", "entity.ghast.warn"),
    MOB_GHAST_DEATH("MOB_GHAST_DEATH", "entity.ghast.death"),
    MOB_GHAST_FIREBALL("MOB_GHAST_FIREBALL", "entity.ghast.shoot"),
    MOB_GHAST_MOAN("MOB_GHAST_MOAN", "entity.ghast.ambient"),
    MOB_GHAST_SCREAM("MOB_GHAST_SCREAM", "entity.ghast.hurt"),

    MOB_GUARDIAN_HIT("MOB_GUARDIAN_HIT", "entity.guardian.hurt"),
    MOB_GUARDIAN_IDLE("MOB_GUARDIAN_IDLE", "entity.guardian.ambient"),
    MOB_GUARDIAN_DEATH("MOB_GUARDIAN_DEATH", "entity.guardian.death"),
    MOB_ELDER_GUARDIAN_HIT("MOB_ELDER_GUARDIAN_HIT", "entity.elder_guardian.hurt"),
    MOB_ELDER_GUARDIAN_IDLE("MOB_ELDER_GUARDIAN_IDLE", "entity.elder_guardian.ambient"),
    MOB_ELDER_GUARDIAN_DEATH("MOB_ELDER_GUARDIAN_DEATH", "entity.guardian.elder.death"),
    MOB_GUARDIAN_LAND_HIT("MOB_GUARDIAN_LAND_HIT", "entity.guardian.hurt_land"),
    MOB_GUARDIAN_LAND_IDLE("MOB_GUARDIAN_LAND_IDLE", "entity.guardian.ambient_land"),
    MOB_GUARDIAN_LAND_DEATH("MOB_GUARDIAN_LAND_DEATH", "entity.guardian.death_land"),
    MOB_GUARDIAN_CURSE("MOB_GUARDIAN_CURSE", "entity.guardian.curse"),
    MOB_GUARDIAN_ATTACK("MOB_GUARDIAN_ATTACK", "entity.guardian.attack"),
    MOB_GUARDIAN_FLOP("MOB_GUARDIAN_FLOP", "entity.guardian.flop"),

    MOB_HORSE_ANGRY("MOB_HORSE_ANGRY", "entity.horse.angry"),
    MOB_HORSE_ARMOR("MOB_HORSE_ARMOR", "entity.horse.armor"),
    MOB_HORSE_EAT("MOB_HORSE_EAT", "entity.horse.eat"),
    MOB_HORSE_BREATHE("MOB_HORSE_BREATHE", "entity.horse.breathe"),
    MOB_HORSE_DEATH("MOB_HORSE_DEATH", "entity.horse.death"),
    MOB_HORSE_GALLOP("MOB_HORSE_GALLOP", "entity.horse.gallop"),
    MOB_HORSE_HIT("MOB_HORSE_HIT", "entity.horse.hurt"),
    MOB_HORSE_IDLE("MOB_HORSE_IDLE", "entity.horse.ambient"),
    MOB_HORSE_JUMP("MOB_HORSE_JUMP", "entity.horse.jump"),
    MOB_HORSE_LAND("MOB_HORSE_LAND", "entity.horse.land"),
    MOB_HORSE_SADDLE("MOB_HORSE_SADDLE", "entity.horse.saddle"),
    MOB_HORSE_STEP("MOB_HORSE_STEP", "entity.horse.step"),
    MOB_HORSE_STEP_WOOD("MOB_HORSE_STEP_WOOD", "entity.horse.step_wood"),

    MOB_ZOMBIE_HORSE_DEATH("MOB_ZOMBIE_HORSE_DEATH", "entity.zombie_horse.death"),
    MOB_ZOMBIE_HORSE_HIT("MOB_ZOMBIE_HORSE_HIT", "entity.zombie_horse.hurt"),
    MOB_ZOMBIE_HORSE_IDLE("MOB_ZOMBIE_HORSE_IDLE", "entity.zombie_horse.ambient"),

    MOB_SKELETON_HORSE_DEATH("MOB_SKELETON_HORSE_DEATH", "entity.skeleton_horse.death"),
    MOB_SKELETON_HORSE_HIT("MOB_SKELETON_HORSE_HIT", "entity.skeleton_horse.hurt"),
    MOB_SKELETON_HORSE_IDLE("MOB_SKELETON_HORSE_IDLE", "entity.skeleton_horse.ambient"),

    MOB_DONKEY_ANGRY("MOB_DONKEY_ANGRY", "entity.donkey.angry"),
    MOB_DONKEY_DEATH("MOB_DONKEY_DEATH", "entity.donkey.death"),
    MOB_DONKEY_HIT("MOB_DONKEY_HIT", "entity.donkey.hurt"),
    MOB_DONKEY_IDLE("MOB_DONKEY_IDLE", "entity.donkey.ambient"),

    MOB_IRONGOLEM_DEATH("MOB_IRONGOLEM_DEATH", "entity.irongolem.death"),
    MOB_IRONGOLEM_HIT("MOB_IRONGOLEM_HIT", "entity.irongolem.hurt"),
    MOB_IRONGOLEM_THROW("MOB_IRONGOLEM_THROW", "entity.irongolem.attack"),
    MOB_IRONGOLEM_WALK("MOB_IRONGOLEM_WALK", "entity.irongolem.step"),

    MOB_PIG_DEATH("MOB_PIG_DEATH", "entity.pig.death"),
    MOB_PIG_SAY("MOB_PIG_SAY", "entity.pig.ambient"),
    MOB_PIG_STEP("MOB_PIG_STEP", "entity.pig.step"),

    MOB_RABBIT_HURT("MOB_RABBIT_HURT", "entity.rabbit.hurt"),
    MOB_RABBIT_IDLE("MOB_RABBIT_IDLE", "entity.rabbit.ambient"),
    MOB_RABBIT_HOP("MOB_RABBIT_HOP", "entity.rabbit.hop"),
    MOB_RABBIT_DEATH("MOB_RABBIT_DEATH", "entity.rabbit.death"),

    MOB_SHEEP_SAY("MOB_SHEEP_SAY", "entity.sheep.ambient"),
    MOB_SHEEP_SHEAR("MOB_SHEEP_SHEAR", "entity.sheep.shear"),
    MOB_SHEEP_STEP("MOB_SHEEP_STEP", "entity.sheep.step"),

    MOB_SILVERFISH_HIT("MOB_SILVERFISH_HIT", "entity.silverfish.hurt"),
    MOB_SILVERFISH_DEATH("MOB_SILVERFISH_DEATH", "entity.silverfish.death"),
    MOB_SILVERFISH_SAY("MOB_SILVERFISH_SAY", "entity.silverfish.ambient"),
    MOB_SILVERFISH_STEP("MOB_SILVERFISH_STEP", "entity.silverfish.step"),

    MOB_SKELETON_DEATH("MOB_SKELETON_DEATH", "entity.skeleton.death"),
    MOB_SKELETON_HIT("MOB_SKELETON_HIT", "entity.skeleton.hurt"),
    MOB_SKELETON_SAY("MOB_SKELETON_SAY", "entity.skeleton.ambient"),
    MOB_SKELETON_STEP("MOB_SKELETON_STEP", "entity.skeleton.step"),

    MOB_SLIME_ATTACK("MOB_SLIME_ATTACK", "entity.slime.attack"),
    MOB_SLIME_SMALL("MOB_SLIME_DEATH", "entity.slime.small"),
    MOB_SLIME_BIG("MOB_SLIME_BIG", "entity.slime.big"),

    MOB_SPIDER_DEATH("MOB_SPIDER_DEATH", "entity.spider.death"),
    MOB_SPIDER_SAY("MOB_SPIDER_SAY", "entity.spider.ambient"),
    MOB_SPIDER_STEP("MOB_SPIDER_STEP", "entity.spider.step"),

    MOB_VILLAGER_DEATH("MOB_VILLAGER_DEATH", "entity.villager.death"),
    MOB_VILLAGER_HAGGLE("MOB_VILLAGER_HAGGLE", "entity.villager.trading"),
    MOB_VILLAGER_HIT("MOB_VILLAGER_HIT", "entity.villager.hurt"),
    MOB_VILLAGER_IDLE("MOB_VILLAGER_IDLE", "entity.villager.ambient"),
    MOB_VILLAGER_NO("MOB_VILLAGER_NO", "entity.villager.no"),
    MOB_VILLAGER_YES("MOB_VILLAGER_YES", "entity.villager.yes"),

    MOB_WOLF_BARK("MOB_WOLF_BARK", "entity.wolf.ambient"),
    MOB_WOLF_DEATH("MOB_WOLF_DEATH", "entity.wolf.death"),
    MOB_WOLF_GROWL("MOB_WOLF_GROWL", "entity.wolf.growl"),
    MOB_WOLF_HOWL("MOB_WOLF_HOWL", "entity.wolf.howl"),
    MOB_WOLF_HIT("MOB_WOLF_HIT", "entity.wolf.hurt"),
    MOB_WOLF_PANTING("MOB_WOLF_PANTING", "entity.wolf.pant"),
    MOB_WOLF_SHAKE("MOB_WOLF_SHAKE", "entity.wolf.shake"),
    MOB_WOLF_STEP("MOB_WOLF_STEP", "entity.wolf.step"),
    MOB_WOLF_WHINE("MOB_WOLF_WHINE", "entity.wolf.whine"),

    MOB_ZOMBIE_DEATH("MOB_ZOMBIE_DEATH", "entity.zombie.death"),
    MOB_ZOMBIE_HURT("MOB_ZOMBIE_HURT", "entity.zombie.hurt"),
    MOB_ZOMBIE_INFECT("MOB_ZOMBIE_INFECT", "entity.zombie.infect"),
    MOB_ZOMBIE_METAL("MOB_ZOMBIE_METAL", "entity.zombie.attack_iron_door"),
    MOB_ZOMBIE_REMEDY("MOB_ZOMBIE_REMEDY", "entity.zombie_villager.cure"),
    MOB_ZOMBIE_SAY("MOB_ZOMBIE_SAY", "entity.zombie.ambient"),
    MOB_ZOMBIE_STEP("MOB_ZOMBIE_STEP", "entity.zombie.step"),
    MOB_ZOMBIE_UNFECT("MOB_ZOMBIE_UNFECT", "entity.zombie_villager.converted"),
    MOB_ZOMBIE_WOOD("MOB_ZOMBIE_WOOD", "entity.zombie.attack_door_wood"),
    MOB_ZOMBIE_WOOD_BREAK("MOB_ZOMBIE_WOOD_BREAK", "entity.zombie.break_door_wood"),

    MOB_ZOMBIE_PIG_SAY("MOB_ZOMBIE_PIG_SAY", "entity.zombie_pig.ambient"),
    MOB_ZOMBIE_PIG_ANGRY("MOB_ZOMBIE_PIG_ANGRY", "entity.zombie_pig.angry"),
    MOB_ZOMBIE_PIG_DEATH("MOB_ZOMBIE_PIG_DEATH", "entity.zombie_pig.death"),
    MOB_ZOMBIE_PIG_HIT("MOB_ZOMBIE_PIG_HIT", "entity.zombie_pig.hurt"),

    RECORDS_11("RECORDS_11", "record.11"),
    RECORDS_13("RECORDS_13", "record.13"),
    RECORDS_BLOCKS("RECORDS_BLOCKS", "record.blocks"),
    RECORDS_CAT("RECORDS_CAT", "record.cat"),
    RECORDS_CHIRP("RECORDS_CHIRP", "record.chirp"),
    RECORDS_FAR("RECORDS_FAR", "record.far"),
    RECORDS_MALL("RECORDS_MALL", "record.mall"),
    RECORDS_MELLOHI("RECORDS_MELLOHI", "record.mellohi"),
    RECORDS_STAL("RECORDS_STAL", "record.stal"),
    RECORDS_STRAD("RECORDS_STRAD", "record.strad"),
    RECORDS_WAIT("RECORDS_WAIT", "record.wait"),
    RECORDS_WARD("RECORDS_WARD", "record.ward"),

    MUSIC_MENU("MUSIC_MENU", "music.menu"),
    MUSIC_GAME("MUSIC_GAME", "music.game"),
    MUSIC_CREATIVE("MUSIC_CREATIVE", "music.creative"),
    MUSIC_END("MUSIC_END", "music.end"),
    MUSIC_END_DRAGON("MUSIC_END_DRAGON", "music.dragon"),
    MUSIC_CREDITS("MUSIC_CREDITS", "music.credits"),
    MUSIC_NETHER("MUSIC_NETHER", "music.nether");

    private final String keyName, soundName;
    public static final ImmutableMap<String, P_107> STRING_TO_SOUND_CONSTANT;

    P_107(final String keyName, final String soundName) {
        this.keyName = keyName;
        this.soundName = soundName;
    }

    public static String getSound(final String name) {
        final P_107 sound = STRING_TO_SOUND_CONSTANT.get(name);

        return sound != null ? sound.soundName : name;
    }

    static {
        final Map<String, P_107> versions = new HashMap<>();

        for(final P_107 sound : values()) {
            versions.putIfAbsent(sound.keyName, sound);
        }

        STRING_TO_SOUND_CONSTANT = ImmutableMap.copyOf(versions);
    }
}