package net.eunoiaym.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class AttachDto {
	private String origin;
	private String path;
	private String uuid;
	private String ext;
	private String mime;
	private Long size;
	private boolean image;
	
	public AttachDto(String fullPath) {
		ext = fullPath.substring(fullPath.lastIndexOf(".")+1);
		fullPath = fullPath.replace("."+ext, "");
		path = fullPath.substring(0, fullPath.lastIndexOf("/"));
		uuid = fullPath.substring(fullPath.lastIndexOf("/")+1);
	}
	
	public String getThumb() {
		return path + "/s_" + uuid + "." + ext;
	}
	
	public String getFullPath() {
		return path + "/" + uuid + "." + ext;
	}
}
