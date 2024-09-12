package WorldChatterCore.Packets.Sound.Sounds;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public enum P_47 {

    CAVE("CAVE", "ambient.cave.cave"),
    WEATHER_RAIN("WEATHER_RAIN", "ambient.weather.rain"),
    WEATHER_THUNDER("WEATHER_THUNDER", "ambient.weather.thunder"),

    // Player
    DEATH("DEATH", "game.player.die"),
    DAMAGE("DAMAGE", "game.player.hurt"),
    FALL_DAMAGE_BIG("FALL_DAMAGE_BIG", "game.player.hurt.fall.big"),
    FALL_DAMAGE_SMALL("FALL_DAMAGE_SMALL", "game.player.hurt.fall.small"),
    BURP("BURP", "random.burp"),
    LEVEL_UP("LEVEL_UP", "random.levelup"),
    SWIM("SWIM", "game.player.swim"),
    SPLASH("SWIM_SPLASH", "game.player.swim.splash"),

    DIG_CLOTH("DIG_CLOTH", "dig.cloth"),
    DIG_GLASS("DIG_GLASS", "dig.glass"),
    DIG_GRASS("DIG_GRASS", "dig.grass"),
    DIG_GRAVEL("DIG_GRAVEL", "dig.gravel"),
    DIG_SAND("DIG_SAND", "dig.sand"),
    DIG_SNOW("DIG_SNOW", "dig.snow"),
    DIG_STONE("DIG_STONE", "dig.stone"),
    DIG_WOOD("DIG_WOOD", "dig.wood"),

    STEP_CLOTH("STEP_CLOTH", "step.cloth"),
    STEP_GRASS("STEP_GRASS", "step.grass"),
    STEP_GRAVEL("STEP_GRAVEL", "step.gravel"),
    STEP_LADDER("STEP_LADDER", "step.ladder"),
    STEP_SAND("STEP_SAND", "step.sand"),
    STEP_SNOW("STEP_SNOW", "step.snow"),
    STEP_STONE("STEP_STONE", "step.stone"),
    STEP_WOOD("STEP_WOOD", "step.wood"),

    FIRE_ACTIVE("FIRE_ACTIVE", "fire.fire"),
    FIRE_IGNITE("FIRE_IGNITE", "fire.ignite"),

    FIREWORKS_BLAST("FIREWORKS_BLAST", "fireworks.blast"),
    FIREWORKS_BLAST_FAR("FIREWORKS_BLAST_FAR", "fireworks.blast_far"),
    FIREWORKS_LARGE_BLAST("FIREWORKS_LARGE_BLAST", "fireworks.largeBlast"),
    FIREWORKS_LARGE_BLAST_FAR("FIREWORKS_LARGE_BLAST_FAR", "fireworks.largeBlast_far"),
    FIREWORKS_LAUNCH("FIREWORKS_LAUNCH", "fireworks.launch"),
    FIREWORKS_TWINKLE("FIREWORKS_TWINKLE", "fireworks.twinkle"),
    FIREWORKS_TWINKLE_FAR("FIREWORKS_TWINKLE_FAR", "fireworks.twinkle_far"),

    RIDING_MINECART("RIDING_MINECART", "minecart.base"),
    INSIDE_MINECART("INSIDE_MINECART", "minecart.inside"),

    NOTE_BASS("NOTE_BASS", "note.bass"),
    NOTE_BASE_DRUM("NOTE_BASE_DRUM", "note.bd"),
    NOTE_HARP("NOTE_HARP", "note.harp"),
    NOTE_HAT("NOTE_HAT", "note.hat"),
    NOTE_PLING("NOTE_PLING", "note.pling"),
    NOTE_SNARE("NOTE_SNARE", "note.snare"),

    PORTAL("PORTAL", "portal.portal"),
    PORTAL_TRAVEL("PORTAL_TRAVEL", "portal.travel"),
    PORTAL_TRAVELING("PORTAL_TRAVELING", "portal.trigger"),

    ANVIL_BREAK("ANVIL_BREAK", "random.anvil_break"),
    ANVIL_LAND("ANVIL_LAND", "random.anvil_land"),
    ANVIL_USE("ANVIL_USE", "random.anvil_use"),
    ARROW_SHOOT("ARROW_SHOOT", "random.bow"),
    ARROW_HIT("ARROW_HIT", "random.bowhit"),
    ITEM_BREAK("ITEM_BREAK", "random.break"),
    CHEAT_OPEN("CHEAT_OPEN", "random.chestopen"),
    CHEST_CLOSE("CHEST_CLOSE", "random.chestclosed"),
    DOOR_OPEN("DOOR_OPEN", "random.door_open"),
    DOOR_CLOSE("DOOR_CLOSE", "random.door_close"),
    DRINK("DRINK", "random.drink"),
    EAT("EAT", "random.eat"),
    EXPLOSION("EXPLOSION", "random.explode"),
    FIZZ("FIZZ", "random.fizz"),
    ORB("ORB", "random.orb"),
    POP("POP", "random.pop"),
    SUCCESSFUL_HIT("SUCCESSFUL_HIT", "random.successful_hit"),
    CLICK("CLICK", "random.click"),
    WOOD_CLICK("WOOD_CLICK", "random.wood_click"),

    PISTON_IN("PISTON_IN", "title.piston.in"),
    PISTON_OUT("PISTON_OUT", "title.piston.out"),

    LIQUID_LAVA("LIQUID_LAVA", "liquid.lava"),
    LIQUID_LAVA_POP("LIQUID_LAVA_POP", "liquid.lavapop"),

    MOB_BAT_DEATH("MOB_BAT_DEATH", "mob.bat.death"),
    MOB_BAT_HURT("MOB_BAT_HURT", "mob.bat.hurt"),
    MOB_BAT_IDLE("MOB_BAT_IDLE", "mob.bat.idle"),
    MOB_BAT_LOOP("MOB_BAT_LOOP", "mob.bat.loop"),
    MOB_BAT_TAKEOFF("MOB_BAT_TAKEOFF", "mob.bat.takeoff"),

    MOB_BLAZE_BREATHE("MOB_BLAZE_BREATHE", "mob.blaze.breathe"),
    MOB_BLAZE_DEATH("MOB_BLAZE_DEATH", "mob.blaze.death"),
    MOB_BLAZE_HIT("MOB_BLAZE_HIT", "mob.blaze.hit"),

    MOB_CAT_HISS("MOB_CAT_HISS", "mob.cat.hiss"),
    MOB_CAT_HIT("MOB_CAT_HIT", "mob.cat.hitt"),
    MOB_CAT_MEOW("MOB_CAT_MEOW", "mob.cat.meow"),
    MOB_CAT_PURR("MOB_CAT_PURR", "mob.cat.purr"),
    MOB_CAT_PURREOW("MOB_CAT_PURREOW", "mob.cat.purreow"),

    MOB_COW_HURT("MOB_COW_HURT", "mob.cow.hurt"),
    MOB_COW_SAY("MOB_COW_SAY", "mob.cow.say"),
    MOB_COW_STEP("MOB_COW_STEP", "mob.cow.step"),

    MOB_CREEPER_DEATH("MOB_CREEPER_DEATH", "mob.creeper.death"),
    MOB_CREEPER_HIT("MOB_CREEPER_HIT", "mob.creeper.say"),
    MOB_CREEPER_PRIMED("MOB_CREEPER_PRIMED", "creeper.primed"),

    MOB_ENDERDRAGON_DEATH("MOB_ENDERDRAGON_DEATH", "mob.enderdragon.end"),
    MOB_ENDERDRAGON_GROWL("MOB_ENDERDRAGON_GROWL", "mob.enderdragon.growl"),
    MOB_ENDERDRAGON_HIT("MOB_ENDERDRAGON_HIT", "mob.enderdragon.hit"),
    MOB_ENDERDRAGON_WINGS("MOB_ENDERDRAGON_WINGS", "mob.enderdragon.wings"),

    MOB_ENDERMAN_DEATH("MOB_ENDERMAN_DEATH", "mob.endermen.death"),
    MOB_ENDERMAN_HIT("MOB_ENDERMAN_HIT", "mob.endermen.hit"),
    MOB_ENDERMAN_IDLE("MOB_ENDERMAN_IDLE", "mob.endermen.idle"),
    MOB_ENDERMAN_TELEPORT("MOB_ENDERMAN_TELEPORT", "mob.endermen.portal"),
    MOB_ENDERMAN_SCREAM("MOB_ENDERMAN_SCREAM", "mob.endermen.scream"),
    MOB_ENDERMAN_STARE("MOB_ENDERMAN_STARE", "mob.endermen.stare"),

    MOB_GHAST_AFFECTIONATE_SCREAM("MOB_GHAST_AFFECTIONATE_SCREAM", "mob.ghast.affectionate_scream"),
    MOB_GHAST_CHARGE("MOB_GHAST_CHARGE", "mob.ghast.charge"),
    MOB_GHAST_DEATH("MOB_GHAST_DEATH", "mob.ghast.death"),
    MOB_GHAST_FIREBALL("MOB_GHAST_FIREBALL", "mob.ghast.fireball"),
    MOB_GHAST_MOAN("MOB_GHAST_MOAN", "mob.ghast.moan"),
    MOB_GHAST_SCREAM("MOB_GHAST_SCREAM", "mob.ghast.scream"),

    MOB_GUARDIAN_HIT("MOB_GUARDIAN_HIT", "mob.guardian.hit"),
    MOB_GUARDIAN_IDLE("MOB_GUARDIAN_IDLE", "mob.guardian.idle"),
    MOB_GUARDIAN_DEATH("MOB_GUARDIAN_DEATH", "mob.guardian.death"),
    MOB_ELDER_GUARDIAN_HIT("MOB_ELDER_GUARDIAN_HIT", "mob.guardian.elder.hit"),
    MOB_ELDER_GUARDIAN_IDLE("MOB_ELDER_GUARDIAN_IDLE", "mob.guardian.elder.idle"),
    MOB_ELDER_GUARDIAN_DEATH("MOB_ELDER_GUARDIAN_DEATH", "mob.guardian.elder.death"),
    MOB_GUARDIAN_LAND_HIT("MOB_GUARDIAN_LAND_HIT", "mob.guardian.land.hit"),
    MOB_GUARDIAN_LAND_IDLE("MOB_GUARDIAN_LAND_IDLE", "mob.guardian.land.idle"),
    MOB_GUARDIAN_LAND_DEATH("MOB_GUARDIAN_LAND_DEATH", "mob.guardian.land.death"),
    MOB_GUARDIAN_CURSE("MOB_GUARDIAN_CURSE", "mob.guardian.curse"),
    MOB_GUARDIAN_ATTACK("MOB_GUARDIAN_ATTACK", "mob.guardian.attack"),
    MOB_GUARDIAN_FLOP("MOB_GUARDIAN_FLOP", "mob.guardian.flop"),

    MOB_HORSE_ANGRY("MOB_HORSE_ANGRY", "mob.horse.angry"),
    MOB_HORSE_ARMOR("MOB_HORSE_ARMOR", "mob.horse.armor"),
    MOB_HORSE_BREATHE("MOB_HORSE_BREATHE", "mob.horse.breathe"),
    MOB_HORSE_DEATH("MOB_HORSE_DEATH", "mob.horse.death"),
    MOB_HORSE_GALLOP("MOB_HORSE_GALLOP", "mob.horse.gallop"),
    MOB_HORSE_HIT("MOB_HORSE_HIT", "mob.horse.hit"),
    MOB_HORSE_IDLE("MOB_HORSE_IDLE", "mob.horse.idle"),
    MOB_HORSE_JUMP("MOB_HORSE_JUMP", "mob.horse.jump"),
    MOB_HORSE_LAND("MOB_HORSE_LAND", "mob.horse.land"),
    MOB_HORSE_SADDLE("MOB_HORSE_SADDLE", "mob.horse.leather"),
    MOB_HORSE_STEP("MOB_HORSE_STEP", "mob.horse.soft"),
    MOB_HORSE_STEP_WOOD("MOB_HORSE_STEP_WOOD", "mob.horse.wood"),

    MOB_ZOMBIE_HORSE_DEATH("MOB_ZOMBIE_HORSE_DEATH", "mob.horse.zombie.death"),
    MOB_ZOMBIE_HORSE_HIT("MOB_ZOMBIE_HORSE_HIT", "mob.horse.zombie.hit"),
    MOB_ZOMBIE_HORSE_IDLE("MOB_ZOMBIE_HORSE_IDLE", "mob.horse.zombie.idle"),

    MOB_SKELETON_HORSE_DEATH("MOB_SKELETON_HORSE_DEATH", "mob.horse.skeleton.death"),
    MOB_SKELETON_HORSE_HIT("MOB_SKELETON_HORSE_HIT", "mob.horse.skeleton.hit"),
    MOB_SKELETON_HORSE_IDLE("MOB_SKELETON_HORSE_IDLE", "mob.horse.skeleton.idle"),

    MOB_DONKEY_ANGRY("MOB_DONKEY_ANGRY", "mob.horse.donkey.angry"),
    MOB_DONKEY_DEATH("MOB_DONKEY_DEATH", "mob.horse.donkey.death"),
    MOB_DONKEY_HIT("MOB_DONKEY_HIT", "mob.horse.donkey.hit"),
    MOB_DONKEY_IDLE("MOB_DONKEY_IDLE", "mob.horse.donkey.idle"),

    MOB_IRONGOLEM_DEATH("MOB_IRONGOLEM_DEATH", "mob.irongolem.death"),
    MOB_IRONGOLEM_HIT("MOB_IRONGOLEM_HIT", "mob.irongolem.hit"),
    MOB_IRONGOLEM_THROW("MOB_IRONGOLEM_THROW", "mob.irongolem.throw"),
    MOB_IRONGOLEM_WALK("MOB_IRONGOLEM_WALK", "mob.irongolem.walk"),

    MOB_PIG_DEATH("MOB_PIG_DEATH", "mob.pig.death"),
    MOB_PIG_SAY("MOB_PIG_SAY", "mob.pig.say"),
    MOB_PIG_STEP("MOB_PIG_STEP", "mob.pig.step"),

    MOB_RABBIT_HURT("MOB_RABBIT_HURT", "mob.rabbit.hurt"),
    MOB_RABBIT_IDLE("MOB_RABBIT_IDLE", "mob.rabbit.idle"),
    MOB_RABBIT_HOP("MOB_RABBIT_HOP", "mob.rabbit.hop"),
    MOB_RABBIT_DEATH("MOB_RABBIT_DEATH", "mob.rabbit.death"),

    MOB_SHEEP_SAY("MOB_SHEEP_SAY", "mob.sheep.say"),
    MOB_SHEEP_SHEAR("MOB_SHEEP_SHEAR", "mob.sheep.shear"),
    MOB_SHEEP_STEP("MOB_SHEEP_STEP", "mob.sheep.step"),

    MOB_SILVERFISH_HIT("MOB_SILVERFISH_HIT", "mob.silverfish.hit"),
    MOB_SILVERFISH_DEATH("MOB_SILVERFISH_DEATH", "mob.silverfish.kill"),
    MOB_SILVERFISH_SAY("MOB_SILVERFISH_SAY", "mob.silverfish.say"),
    MOB_SILVERFISH_STEP("MOB_SILVERFISH_STEP", "mob.silverfish.step"),

    MOB_SKELETON_DEATH("MOB_SKELETON_DEATH", "mob.skeleton.death"),
    MOB_SKELETON_HIT("MOB_SKELETON_HIT", "mob.skeleton.hit"),
    MOB_SKELETON_SAY("MOB_SKELETON_SAY", "mob.skeleton.say"),
    MOB_SKELETON_STEP("MOB_SKELETON_STEP", "mob.skeleton.step"),

    MOB_SLIME_ATTACK("MOB_SLIME_ATTACK", "mob.slime.attack"),
    MOB_SLIME_SMALL("MOB_SLIME_DEATH", "mob.slime.small"),
    MOB_SLIME_BIG("MOB_SLIME_BIG", "mob.slime.big"),

    MOB_SPIDER_DEATH("MOB_SPIDER_DEATH", "mob.spider.death"),
    MOB_SPIDER_SAY("MOB_SPIDER_SAY", "mob.spider.say"),
    MOB_SPIDER_STEP("MOB_SPIDER_STEP", "mob.spider.step"),

    MOB_VILLAGER_DEATH("MOB_VILLAGER_DEATH", "mob.villager.death"),
    MOB_VILLAGER_HAGGLE("MOB_VILLAGER_HAGGLE", "mob.villager.haggle"),
    MOB_VILLAGER_HIT("MOB_VILLAGER_HIT", "mob.villager.hit"),
    MOB_VILLAGER_IDLE("MOB_VILLAGER_IDLE", "mob.villager.idle"),
    MOB_VILLAGER_NO("MOB_VILLAGER_NO", "mob.villager.no"),
    MOB_VILLAGER_YES("MOB_VILLAGER_YES", "mob.villager.yes"),

    MOB_WOLF_BARK("MOB_WOLF_BARK", "mob.wolf.bark"),
    MOB_WOLF_DEATH("MOB_WOLF_DEATH", "mob.wolf.death"),
    MOB_WOLF_GROWL("MOB_WOLF_GROWL", "mob.wolf.growl"),
    MOB_WOLF_HOWL("MOB_WOLF_HOWL", "mob.wolf.howl"),
    MOB_WOLF_HIT("MOB_WOLF_HIT", "mob.wolf.hurt"),
    MOB_WOLF_PANTING("MOB_WOLF_PANTING", "mob.wolf.panting"),
    MOB_WOLF_SHAKE("MOB_WOLF_SHAKE", "mob.wolf.shake"),
    MOB_WOLF_STEP("MOB_WOLF_STEP", "mob.wolf.step"),
    MOB_WOLF_WHINE("MOB_WOLF_WHINE", "mob.wolf.whine"),

    MOB_ZOMBIE_DEATH("MOB_ZOMBIE_DEATH", "mob.zombie.death"),
    MOB_ZOMBIE_HURT("MOB_ZOMBIE_HURT", "mob.zombie.hurt"),
    MOB_ZOMBIE_INFECT("MOB_ZOMBIE_INFECT", "mob.zombie.infect"),
    MOB_ZOMBIE_METAL("MOB_ZOMBIE_METAL", "mob.zombie.metal"),
    MOB_ZOMBIE_REMEDY("MOB_ZOMBIE_REMEDY", "mob.zombie.remedy"),
    MOB_ZOMBIE_SAY("MOB_ZOMBIE_SAY", "mob.zombie.say"),
    MOB_ZOMBIE_STEP("MOB_ZOMBIE_STEP", "mob.zombie.step"),
    MOB_ZOMBIE_UNFECT("MOB_ZOMBIE_UNFECT", "mob.zombie.unfect"),
    MOB_ZOMBIE_WOOD("MOB_ZOMBIE_WOOD", "mob.zombie.wood"),
    MOB_ZOMBIE_WOOD_BREAK("MOB_ZOMBIE_WOOD_BREAK", "mob.zombie.woodbreak"),

    MOB_ZOMBIE_PIG_SAY("MOB_ZOMBIE_PIG_SAY", "mob.zombiepig.zpig"),
    MOB_ZOMBIE_PIG_ANGRY("MOB_ZOMBIE_PIG_ANGRY", "mob.zombiepig.zpigangry"),
    MOB_ZOMBIE_PIG_DEATH("MOB_ZOMBIE_PIG_DEATH", "mob.zombiepig.zpigdeath"),
    MOB_ZOMBIE_PIG_HIT("MOB_ZOMBIE_PIG_HIT", "mob.zombiepig.zpighurt"),

    RECORDS_11("RECORDS_11", "records.11"),
    RECORDS_13("RECORDS_13", "records.13"),
    RECORDS_BLOCKS("RECORDS_BLOCKS", "records.blocks"),
    RECORDS_CAT("RECORDS_CAT", "records.cat"),
    RECORDS_CHIRP("RECORDS_CHIRP", "records.chirp"),
    RECORDS_FAR("RECORDS_FAR", "records.far"),
    RECORDS_MALL("RECORDS_MALL", "records.mall"),
    RECORDS_MELLOHI("RECORDS_MELLOHI", "records.mellohi"),
    RECORDS_STAL("RECORDS_STAL", "records.stal"),
    RECORDS_STRAD("RECORDS_STRAD", "records.strad"),
    RECORDS_WAIT("RECORDS_WAIT", "records.wait"),
    RECORDS_WARD("RECORDS_WARD", "records.ward"),

    MUSIC_MENU("MUSIC_MENU", "music.menu"),
    MUSIC_GAME("MUSIC_GAME", "music.game"),
    MUSIC_CREATIVE("MUSIC_CREATIVE", "music.game.creative"),
    MUSIC_END("MUSIC_END", "music.game.end"),
    MUSIC_END_DRAGON("MUSIC_END_DRAGON", "music.game.end.dragon"),
    MUSIC_CREDITS("MUSIC_CREDITS", "music.game.end.credits"),
    MUSIC_NETHER("MUSIC_NETHER", "music.game.nether");

    private final String keyName, soundName;
    public static final ImmutableMap<String, P_47> STRING_TO_SOUND_CONSTANT;

    P_47(final String keyName, final String soundName) {
        this.keyName = keyName;
        this.soundName = soundName;
    }

    public static String getSound(final String name) {
        final P_47 sound = STRING_TO_SOUND_CONSTANT.get(name);

        return sound != null ? sound.soundName : name;
    }

    static {
        final Map<String, P_47> versions = new HashMap<>();

        for(final P_47 sound : values()) {
            versions.putIfAbsent(sound.keyName, sound);
        }

        STRING_TO_SOUND_CONSTANT = ImmutableMap.copyOf(versions);
    }
}