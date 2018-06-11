package application.backend.service.s3;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import application.utils.Utility;

@Service
public class S3ServicesImpl implements S3Services {

	/** The application logger */
	private static final Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3client;

	@Value("${jsa.s3.bucket}")
	private String bucketName;

	@Override
	public void downloadFile(String keyName) {

		try {

			System.out.println("Downloading an object");
			S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, keyName));
			System.out.println("Content-Type: " + s3object.getObjectMetadata().getContentType());
			Utility.displayText(s3object.getObjectContent());
			LOG.info("===================== Import File - Done! =====================");

		} catch (AmazonServiceException ase) {
			LOG.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
			LOG.info("Error Message:    " + ase.getMessage());
			LOG.info("HTTP Status Code: " + ase.getStatusCode());
			LOG.info("AWS Error Code:   " + ase.getErrorCode());
			LOG.info("Error Type:       " + ase.getErrorType());
			LOG.info("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			LOG.info("Caught an AmazonClientException: ");
			LOG.info("Error Message: " + ace.getMessage());
		} catch (IOException ioe) {
			LOG.info("IOE Error Message: " + ioe.getMessage());
		}
	}

	@Override
	public void uploadFile(String keyName, String uploadFilePath) {

		try {

			File file = new File(uploadFilePath);
			s3client.putObject(new PutObjectRequest(bucketName, keyName, file));
			LOG.info("===================== Upload File - Done! =====================");

		} catch (AmazonServiceException ase) {
			LOG.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
			LOG.info("Error Message:    " + ase.getMessage());
			LOG.info("HTTP Status Code: " + ase.getStatusCode());
			LOG.info("AWS Error Code:   " + ase.getErrorCode());
			LOG.info("Error Type:       " + ase.getErrorType());
			LOG.info("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			LOG.info("Caught an AmazonClientException: ");
			LOG.info("Error Message: " + ace.getMessage());
		}
	}

	/**
	 * Returns the root URL where the bucket name is located.
	 * <p>
	 * Please note that the URL does not contain the bucket name
	 * </p>
	 * 
	 * @param bucketName
	 *            The bucket name
	 * @return the root URL where the bucket name is located.
	 */
	public String ensureBucketExists(String bucketName) {

		// String bucketUrl = null;
		//
		// try {
		// Bucket bucket = null;
		// if (!s3client.doesBucketExist(bucketName)) {
		// LOG.info("Bucket {} doesn't exists...Creating one");
		// bucket = s3client.createBucket(bucketName);
		// LOG.info("Created bucket: {}", bucketName);
		// }
		//// bucketUrl = s3client.getResourceUrl(bucketName, null) + bucketName;
		// } catch (AmazonClientException ace) {
		// LOG.error("An error occurred while connecting to S3. Will not execute action"
		// +
		// " for bucket: {}", bucketName, ace);
		// }
		//
		//
		// return bucketUrl;
		return "ensureBucketExists MUST BE CORRECTLY IMPLEMENTED!";
	}

}
