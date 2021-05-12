import java.util.ArrayList;
import java.util.Arrays;


//			Map Application
//	        Author: Maksim Zakharau, 256629 
//			Data: October 2020;

public class MapApplication {
	private static final String GREETING_MESSAGE = 
			"Welcome to Map Application\n" + 
	        "Author: Maksim Zakharau\n" + 
			"Data: october 2020 y.\n";
	
	private static final String MENU = 
			"     Main Menu             \n" +
			"1 - Show the map			\n" +
			"2 - Read map from file	    \n" +
			"3 - Save map to file	    \n" +
			"4 - Change region and view his capital	    \n" +
			"0 - Close application      \n";	
	
	public MapApplication() throws MapException{
		
	}
	
	ConsoleUserDialog UI = new ConsoleUserDialog(); 
	Map map = null;
	
	public static void main(String[] args) throws MapException {
		MapApplication application = new MapApplication();
		application.runMainLoop();
	}
	
	
	public void runMainLoop() throws MapException {
		
		UI.printMessage(GREETING_MESSAGE);
		if (map == null) {
		map = createMap();
		}
		while (true) {
			UI.clearConsole();
			

			try {
				UI.printMessage("Choose the number of action: ");
				int number = UI.enterInt(MENU);
				switch (number) {
				case 1:
					showMap(map);
					break;
				case 2: {
					String file_name = UI.enterString("Write a file name: ");
					map = Map.readFromFile(file_name);
					UI.printInfoMessage("File " + file_name + " succesfully loaded.");
				}
					break;
				case 3: {
					String file_name = UI.enterString("Get a name of file: ");
					Map.printToFile(file_name, map);
					UI.printInfoMessage("File " + file_name+  " succesfully writed");
				}
				case 4: {
					UI.printMessage("Available regions" + Arrays.deepToString(Map.Region.values()));
					String region_name = UI.enterString("Write a name of region: ");
					map.setRegion(region_name);
					UI.printMessage("Capital of this region is " + map.getRegion().getCity());
				
				}

					break;
				case 0:
					UI.printInfoMessage("Application has been closed.");
					System.exit(0);
				default:{
					UI.printInfoMessage("Wrong number of action, please retry.");
					}
				}
				
			} catch (MapException e) { 
				UI.printErrorMessage(e.getMessage());
			}
		} 
	}
	@SuppressWarnings("static-access")
	public void showMap(Map map) {
		StringBuilder sb = new StringBuilder();
		
		if (map != null) {
			sb.append("Current map"+"\n");
			sb.append( "Country: " + map.getCountry() + "\n" );
			sb.append( "Scale: " + map.getScale() + "\n" );
			sb.append( "Last viewed region: " + map.getRegion().valueOf(map.getRegion().toString()) + "\n");
			sb.append( "Capital of region: " + map.getRegion() + "\n" );
			sb.append( "All cities of country: " +  "\n" );
		} else
			sb.append( "Lack of data" + "\n" );
		UI.printMessage( sb.toString() );
		map.printCityArray((ArrayList<String>) map.getCitylist());
	}
	
	
	

	public Map createMap() throws MapException{
		UI.printInfoMessage("This app version supports only Belarus map, country automatically has been changed to 'Belarus'");
		int scale = UI.enterInt("Enter the map scale: ");
		Map map;
		try { 
			map = new Map("Belarus", scale);
			
		} catch (MapException e) {    
			UI.printErrorMessage(e.getMessage());
			return null;
		}
		return map;
	}
}

 
