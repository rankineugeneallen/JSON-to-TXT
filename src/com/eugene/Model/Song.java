package com.eugene.Model;

import com.eugene.Constants.Constants;

public class Song extends Constants{
	
	private String CCLID = "";
	private String[] copyright; 
	private String disclaimer = "";
	private String[] lyricParts;
	private String songId = "";
	private String title = "";
	private String songNumber = "";
	private String[] authors;
	private String CCLILicense = CCLILicense();
	
	public Song() {
		super();
	}
	
	public Song(String CCLID, String[] copyright, String disclaimer, String[] lyricParts, String songId, 
				String title, String songNumber, String[] authors) {
		this.CCLID = CCLID;
		this.copyright = copyright;
		this.disclaimer = disclaimer;
		this.lyricParts = lyricParts;
		this.title = title;
		this.songNumber = songNumber;
		this.songId = songId;
		this.authors = authors;
	}

	public String getCCLID() {
		return CCLID;
	}

	public void setCCLID(String cCLID) {
		CCLID = cCLID;
	}

	public String[] getCopyright() {
		return copyright;
	}

	public void setCopyright(String[] copyright) {
		this.copyright = copyright;
	}

	public String getDisclaimer() {
		return disclaimer;
	}

	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}

	public String[] getLyricParts() {
		return lyricParts;
	}

	public void setLyricParts(String[] lyricParts) {
		this.lyricParts = lyricParts;
	}

	public String getSongId() {
		return songId;
	}

	public void setSongId(String songId) {
		this.songId = songId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSongNumber() {
		return songNumber;
	}

	public void setSongNumber(String songNumber) {
		this.songNumber = songNumber;
	}

	public String[] getAuthors() {
		return authors;
	}

	public void setAuthors(String[] authors) {
		this.authors = authors;
	}
	
	private String buildTitle() {
		StringBuilder retStr = new StringBuilder();
		
		retStr.append(title).append("\n\n");
		
		return retStr.toString();
	}
	
	private String buildLyrics() {
		StringBuilder retStr = new StringBuilder();

		for(int i = 0; i < lyricParts.length; i++)
			retStr.append(lyricParts[i]).append("\n\n");
		
		
		return retStr.toString();
	}
	
	private String buildCopyright() {
		StringBuilder retStr = new StringBuilder();
		
		retStr.append("CCLI Song # ").append(CCLID).append("\n");
		
		for(int i = 0; i < authors.length; i++) {
			retStr.append(authors[i]);
			
			if(i+1 < authors.length)
				retStr.append(" | ");
			
		}
		
		if(copyright.length > 0)
			retStr.append("\n");

		for(int j = 0; j < copyright.length; j++) {
			retStr.append(copyright[j]);
			
			if(j +1 < copyright.length)
				retStr.append("\n");
		}
		
		retStr.append("\nCCLI License # ").append(CCLILicense);
		
		return retStr.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder retStr = new StringBuilder();
		retStr.append(buildTitle())
			  .append(buildLyrics())
			  .append(buildCopyright());
		
		return retStr.toString();
	}
	
}
