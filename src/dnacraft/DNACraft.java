package dnacraft;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import dnacraft.common.CommonProxy;

@Mod( modid = "DNACraft", name = "DNACraft", version = "0.0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class DNACraft {
	
	@Instance( value = "DNACraft" )
	public static DNACraft instance;

	@SidedProxy( clientSide = "dnacraft.client.ClientProxy", serverSide = "dnacraft.common.CommonProxy" )
	public static CommonProxy proxy;
	
	@Mod.PreInit
	public void preInit( FMLPreInitializationEvent evt )
	{
		Configuration configFile = new Configuration(evt.getSuggestedConfigurationFile());
		
		// get config here
		
		configFile.save();		
	}
	
	@Mod.Init
	public void init( FMLInitializationEvent evt )
	{
		proxy.init();
		proxy.registerRenderInformation();
	}
}