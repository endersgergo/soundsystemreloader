package hu.ender.soundreloader;

import hu.ender.soundreloader.mixin.SoundEngineAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

public class SoundReloader implements ClientModInitializer {
	private static final KeyBinding reloadKey;

	static {
		reloadKey = new KeyBinding("soundreloader.key", GLFW.GLFW_KEY_L, "soundreloader.category");
	}
	@Override
	public void onInitializeClient() {
		KeyBindingHelper.registerKeyBinding(reloadKey);
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (reloadKey.wasPressed()) {
				client.player.sendMessage(new LiteralText("[SoundSystem] Reloading..."), false);
				((SoundEngineAccessor)MinecraftClient.getInstance().getSoundManager()).getSoundSystem().reloadSounds();
				client.player.sendMessage((new LiteralText("[SoundSystem] Reloaded!")), false);
			}
		});
	}
}
