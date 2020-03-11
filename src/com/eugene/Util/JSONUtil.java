package com.eugene.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.eugene.Message.Message;
import com.eugene.Model.Song;

public class JSONUtil extends Message {

	private String filename;
	private String filepath;
	ArrayList<String> filenames = new ArrayList<String>();
	private JSONParser parser = new JSONParser();

	public JSONUtil(String filepath, String filename) {
		this.filepath = filepath;
		this.filename = filepath + filename;
	}

	public void parseJson() {
		try (FileReader reader = new FileReader(filename)) {
			Object json = parser.parse(reader);
			JSONObject jsonArr = (JSONObject) json;
			JSONArray folders = (JSONArray) jsonArr.get("Folders");
			JSONObject folder = null;
			JSONArray lyrics = null;

			Iterator<JSONObject> it = folders.iterator();
			while (it.hasNext())
				folder = it.next();

			lyrics = (JSONArray) folder.get("Lyrics");

			it = lyrics.iterator();
			while (it.hasNext())
				parseSongs(it.next());

		} catch (ParseException e) {
			msg("Parse exception", true);
			e.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void parseSongs(JSONObject songObj) {
		String cclid = (String) songObj.get("CCLID");
		String[] copyright = convertToStringArr((JSONArray) songObj.get("Copyright"));
		String disclaimer = (String) songObj.get("Disclaimer");
		String[] lyricParts = convertLyricsToStrArr((JSONArray) songObj.get("LyricParts"));
		String songId = (String) songObj.get("SongID");
		String songNumber = (String) songObj.get("SongNumber");
		String title = (String) songObj.get("Title");
		String[] authors = convertToStringArr((JSONArray) songObj.get("Authors"));

		
		
		msg(cclid + "\t" + "\t" + disclaimer + "\t" + songId + 
				"\t" + songNumber + "\t" + title, true);
		printArray(copyright);
		printArray(lyricParts);
		printArray(authors);
		
		Song song = new Song(cclid, copyright, disclaimer, lyricParts, songId, 
				title, songNumber, authors);
		
		writeToFile(song);
		
	}
	
	private void printArray(String[] arr) {
		StringBuilder outStr = new StringBuilder();
		for(int i = 0; i< arr.length; i++) {
			outStr.append(arr[i]).append("\n");
		}
		
		msg(outStr, true);
	}

	private String[] convertToStringArr(JSONArray jsonarr) {
		int size = jsonarr.size();
		String[] tempArr = new String[size];

		for (int i = 0; i < size; i++) {
			tempArr[i] = (String) jsonarr.get(i);
		}

		return tempArr;

	}

	private String[] convertLyricsToStrArr(JSONArray lyricParts) {
		int size = lyricParts.size();
		String[] tempArr = new String[size];
		int i = 0;
		Iterator<JSONObject> it = lyricParts.iterator();
		while (it.hasNext()) {
			tempArr[i] = extractLyrics((JSONObject) it.next()) + "\n";
			//msg(tempArr[i]);
			i++;
		}

		return tempArr;
	}

	private String extractLyrics(JSONObject lyricPart) {
		String stanza = (String) lyricPart.get("Lyrics");
		String partType = determinePartType((Long) lyricPart.get("PartType"), (Long) lyricPart.get("PartTypeNumber"));
		
		return partType + stanza; 
	}
	
	private void writeToFile(Song song) {
		String safeFilePath = song.getTitle().replaceAll("\\W\\s+", "").trim() + ".txt";
		safeFilePath = System.getProperty("user.dir") + "\\work\\out\\" + safeFilePath;
		
		msg(safeFilePath);
		
		File file = new File(safeFilePath);
		
		try {
			if(file.createNewFile()) {
				msg(safeFilePath + " was created");
			} else {
				msg("FILE ALREADY EXISTS");
			}
			
			FileWriter writer = new FileWriter(file);
			writer.write(song.toString());
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//0 - verse
	//1 - chorus
	//2 - bridge
	//3 - ending 
	private String determinePartType(long partType, long partTypeNumber) {
		String outStr = "";
		switch((int)partType) {
			case 0:
				outStr = "Verse " + (int)partTypeNumber;
				break;
			case 1:
				outStr = "Chorus " + (int)partTypeNumber;
				break;
			case 2:
				outStr = "Bridge " + (int)partTypeNumber;
				break;
			case 3:
				outStr = "Ending " + (int)partTypeNumber;
				break;
			default:
				outStr = "ERROR";
				break;	
		}
		
		return outStr + "\n";
		
	}

}
