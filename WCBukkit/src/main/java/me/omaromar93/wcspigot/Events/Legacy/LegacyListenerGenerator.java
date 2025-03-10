package me.omaromar93.wcspigot.Events.Legacy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

public class LegacyListenerGenerator {
    public static Object createLegacyPlayerListener() throws Exception {
        return new ByteBuddy()
                .subclass(Class.forName("org.bukkit.event.player.PlayerListener"))
                .method(ElementMatchers.named("onPlayerChat")
                        .or(ElementMatchers.named("onPlayerJoin"))
                        .or(ElementMatchers.named("onPlayerQuit")))
                .intercept(MethodDelegation.to(new LegacyEventInterceptor()))
                .make()
                .load(LegacyListenerGenerator.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded().getDeclaredConstructor().newInstance();
    }
}
