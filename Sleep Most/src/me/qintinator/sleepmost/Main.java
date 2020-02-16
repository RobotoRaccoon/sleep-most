package me.qintinator.sleepmost;
import me.qintinator.sleepmost.bstats.Metrics;
import me.qintinator.sleepmost.commands.SleepmostCommand;
import me.qintinator.sleepmost.eventlisteners.*;
import me.qintinator.sleepmost.interfaces.IMessageService;
import me.qintinator.sleepmost.statics.Bootstrapper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	
	@Override
	public void onEnable() {

		Metrics metric = new Metrics(this);

		saveDefaultConfig();
		Bootstrapper bootstrapper = Bootstrapper.getBootstrapper();
		bootstrapper.initialize(this);
		IMessageService messageService = bootstrapper.getMessageService();

		Bukkit.getPluginCommand("sleepmost").setExecutor(new SleepmostCommand(bootstrapper.getSleepService(),messageService));

		Bukkit.getPluginManager().registerEvents(new OnSleep(bootstrapper.getSleepService(), bootstrapper.getMessageService(), bootstrapper.getCooldownService()), this);
		Bukkit.getPluginManager().registerEvents(new OnLeave(bootstrapper.getCooldownService()), this);
		Bukkit.getPluginManager().registerEvents(new OnSleepSkip(bootstrapper.getSleepService(), bootstrapper.getMessageService()), this);
		Bukkit.getPluginManager().registerEvents(new OnMobTarget(bootstrapper.getSleepService()), this);
		Bukkit.getPluginManager().registerEvents(new OnPlayerWorldChange(bootstrapper.getSleepService()), this);
		Bukkit.getPluginManager().registerEvents(new OnPlayerJoin(bootstrapper.getUpdateService()), this);

		Runnable updateChecker =
				() -> {
					boolean hasUpdate = bootstrapper.getUpdateService().hasUpdate();
					if(hasUpdate)
						Bukkit.getLogger().info("UPDATE FOUND: A newer version of sleep-most is available to download!");
		};

		updateChecker.run();

	}
}
