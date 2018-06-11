package application.backend.service.s3;

public interface S3Services {
	public void downloadFile(String keyName);

	public void uploadFile(String keyName, String uploadFilePath);

	public String ensureBucketExists(String bucketName);
}
