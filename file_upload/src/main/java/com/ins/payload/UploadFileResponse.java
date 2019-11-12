package com.ins.payload;

import java.time.LocalDateTime;

public class UploadFileResponse {
	private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
    private LocalDateTime date;
    
    public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size,LocalDateTime date) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
        this.date=date;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "UploadFileResponse [fileName=" + fileName + ", fileDownloadUri=" + fileDownloadUri + ", fileType="
				+ fileType + ", size=" + size + ", date=" + date + "]";
	}

}
