package dnacraft;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import dnacraft.common.CommonProxy;
import dnacraft.common.block.BlockCentrifuge;
import dnacraft.common.block.BlockSplicer;
import dnacraft.common.evolution.TraitManager;
import dnacraft.common.item.ItemGeneric;
import dnacraft.common.item.ItemUnstackable;

@Mod( modid = "DNACraft", name = "DNACraft", version = "0.0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class DNACraft {
	
	@Instance( value = "DNACraft" )
	public static DNACraft instance;

	@SidedProxy( clientSide = "dnacraft.client.ClientProxy", serverSide = "dnacraft.common.CommonProxy" )
	public static CommonProxy proxy;
	
	public static class Items
	{
		public static ItemUnstackable itemUnstackable;
		public static ItemGeneric itemGeneric;
	}
	
	public static class Blocks
	{
		public static BlockCentrifuge blockCentrifuge;
		public static BlockSplicer blockSplicer;
	}
	
	@Mod.PreInit
	public void preInit( FMLPreInitializationEvent evt )
	{
		Configuration configFile = new Configuration(evt.getSuggestedConfigurationFile());
		
		// get config here
		
		configFile.save();
		
		TraitManager.instance.put(TraitManager.COLOR_GREEN, 0.5);
		TraitManager.instance.put(TraitManager.COLOR_RED, 0.5);
	}
	
	@Mod.Init
	public void init( FMLInitializationEvent evt )
	{
		proxy.init();
		proxy.registerRenderInformation();
	}
}