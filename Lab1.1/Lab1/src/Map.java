import java.io.*;
import java.util.*;

//Map Application
//Author: Maksim Zakharau, 256629 
//Data: October 2020;

public class Map {
	private String country;
	private int scale;
	private Region region;
	private List<String> citylist;

	public Map(String country, int scale) throws MapException {
		this.setCountry(country);
		this.setScale(scale);
		setRegion(Region.UNDEFINED);
		setCitylist(createcityarray());
	}

	public ArrayList<String> createcityarray() {
		ArrayList<String> cityarray = new ArrayList<String>();
		for (int i = 0; i < (Region.values()).length - 1; i++) {

			cityarray.add(Region.values()[i].getCity());
		}
		return cityarray;
	}

	public void printCityArray(ArrayList<String> list) {
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				System.out.println(list.get(i) + ".");
			} else
				System.out.println(list.get(i) + ", ");

		}
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) throws MapException {
		if (scale < 1)
			throw new MapException("Scale cant be less than 1");
		this.scale = scale;
	}

	public void setScale(String scale) throws MapException {
		if (scale == null || scale.equals("")) {
			setScale(1);
			return;
		}
		try {
			setScale(Integer.parseInt(scale));
		} catch (NumberFormatException e) {
			throw new MapException("Scale needs to be an integer");

		}
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public void setRegion(String region) throws MapException {
		if (region == null || region.equals("")) {
			this.region = Region.UNDEFINED;
			throw new MapException("Cannot find the region");
		}
		for (Region regions : Region.values()) {
			if (regions.getCity() == region) {
				this.region = regions;
				return;
			}
		}
		
	}

	public List<String> getCitylist() {
		return citylist;
	}

	public void setCitylist(List<String> list) {
		this.citylist = list;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) throws MapException {
		if ((country == null) || country.equals(""))
			throw new MapException("You need to write a name");
		this.country = country;
	}

	@Override
	public String toString() {
		return country + " " + scale;
	}

	public static void printToFile(PrintWriter writer, Map map) {
		writer.println(map.country + "#" + map.scale + "#" + map.region);
	}

	public static void printToFile(String file_name, Map person) throws MapException {
		try (PrintWriter writer = new PrintWriter(file_name)) {
			printToFile(writer, person);
		} catch (FileNotFoundException e) {
			throw new MapException("File not found " + file_name);
		}
	}

	public static Map readFromFile(BufferedReader reader) throws MapException {
		try {
			String line = reader.readLine();
			String[] txt = line.split("#");
			Map map = new Map(txt[0], Integer.parseInt(txt[1]));
			map.setRegion(txt[2]);
			return map;
		} catch (IOException e) {
			throw new MapException("Error on the file reading process");
		}
	}

	public static Map readFromFile(String file_name) throws MapException {
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(file_name)))) {
			return Map.readFromFile(reader);
		} catch (FileNotFoundException e) {
			throw new MapException("File not found " + file_name);
		} catch (IOException e) {
			throw new MapException("Error on the file reading process");
		}
	}

	enum Region {
		VITEBSKI("Vitebsk"), GRODNENSKI("Grodno"), MINSKI("Minsk"), BRESTSKI("Brest"), GOMELSKI("Gomel"),
		MOGILEVSKI("Mogilev"), UNDEFINED("Undefined");

		private String city;

		Region(String city) {
			setCity(city);
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

	}
}

class MapException extends Exception {

	private static final long serialVersionUID = 1L;

	public MapException(String message) {
		super(message);
	}
}