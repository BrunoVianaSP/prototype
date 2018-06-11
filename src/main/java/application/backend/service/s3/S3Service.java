package application.backend.service.s3;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;

import application.exceptions.S3Exception;

@Service
public class S3Service {

	/** The application logger */
	private static final Logger LOG = LoggerFactory.getLogger(S3Service.class);

	private static final String PROFILE_PICTURE_FILE_NAME = "profilePicture";

	@Value("${aws.s3.root.bucket.name}")
	private String bucketName;

	@Value("${aws.s3.profile}")
	private String awsProfileName;

	@Value("${image.store.tmp.folder}")
	private String tempImageStore;

	@Autowired
	private S3Services s3Services;

	/**
	 * It stores the given file name in S3 and returns the key under which the file
	 * has been stored
	 * 
	 * @param uploadedFile
	 *            The multipart file uploaed by the user
	 * @param username
	 *            The username for which to upload this file
	 * @return The URL of the uploaded image
	 */
	public String storeProfileImage(MultipartFile uploadedFile, String username) {

		String profileImageUrl = null;

		try {
			if (uploadedFile != null && !uploadedFile.isEmpty()) {
				byte[] bytes = uploadedFile.getBytes();

				// The root of our temporary assets. Will create if it doesn't exist
				File tmpImageStoredFolder = new File(tempImageStore + File.separatorChar + username);
				if (!tmpImageStoredFolder.exists()) {
					LOG.info("Creating the temporary root for the S3 assets");
					tmpImageStoredFolder.mkdirs();
				}

				// The temporary file where the profile image will be stored
				File tmpProfileImageFile = new File(tmpImageStoredFolder.getAbsolutePath() + File.separatorChar
						+ PROFILE_PICTURE_FILE_NAME + "." + FilenameUtils.getExtension(uploadedFile.getOriginalFilename()));

				LOG.info("Temporary file will be saved to {}", tmpProfileImageFile.getAbsolutePath());

				try (BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(tmpProfileImageFile.getAbsolutePath())))) {
					stream.write(bytes);
				}

				profileImageUrl = this.storeProfileImageToS3(tmpProfileImageFile, username);

				// Clean up the temporary folder
				tmpProfileImageFile.delete();
			}
		} catch (FileNotFoundException e) {
			throw new S3Exception(e);
		} catch (IOException e) {
			throw new S3Exception(e);
		}

		return profileImageUrl;

	}

	/**
	 * It stores the given file name in S3 and returns the key under which the file
	 * has been stored
	 * 
	 * @param resource
	 *            The file resource to upload to S3
	 * @return The URL of the uploaded resource or null if a problem occurred
	 *
	 * @throws IllegalArgumentException
	 *             If the resource file does not exist
	 */
	private String storeProfileImageToS3(File resource, String username) {

		String resourceUrl = null;

		if (!resource.exists()) {
			LOG.error("The file {} does not exist. Throwing an exception", resource.getAbsolutePath());
			throw new S3Exception("The file " + resource.getAbsolutePath() + " doesn't exist");
		}

		String rootBucketUrl = s3Services.ensureBucketExists(bucketName);

		if (null == rootBucketUrl) {
			LOG.error("The bucket {} does not exist and the application "
					+ "was not able to create it. The image won't be stored with the profile", rootBucketUrl);
		} else {
			LOG.warn(rootBucketUrl);
			sendImage(resource, username);
		}

		return resourceUrl;

	}

	private void sendImage(File resource, String username) {
		try {
			LOG.info("Uploading image to S3!");
			String key = username + "/" + PROFILE_PICTURE_FILE_NAME + "."
					+ FilenameUtils.getExtension(resource.getName());
			s3Services.uploadFile(key, resource.getPath());
		} catch (AmazonClientException ace) {
			LOG.error("A client exception occurred while trying to store the profile"
					+ " image {} on S3. The profile image won't be stored", resource.getAbsolutePath(), ace);
			throw new S3Exception(ace);
		}
	}
}
