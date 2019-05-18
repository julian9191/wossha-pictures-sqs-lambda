package com.wossha.pictures.sqs.dto;

public class PictureFileDTO {
	private Integer id;
	private String uuid;
	private String username;
	private String name;
	private String fileType;
	private String type;
	private Integer fileSize;
	private byte[] value;
	
	public PictureFileDTO(Integer id, String uuid, String username, String name, String fileType, String type,
			Integer fileSize, byte[] value) {
		this.id = id;
		this.uuid = uuid;
		this.username = username;
		this.name = name;
		this.fileType = fileType;
		this.type = type;
		this.fileSize = fileSize;
		this.value = value;
	}

	public PictureFileDTO() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public byte[] getValue() {
		return value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}	
	
}
