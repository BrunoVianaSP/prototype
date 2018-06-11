package application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
@EnableJpaRepositories(basePackages = "application.backend.persistence.repositories")
@EntityScan(basePackages = "application.backend.persistence.domain.backend")
@EnableTransactionManagement
//@PropertySource("file:///${user.home}/.champions/application-commom.properties")
// @PropertySource("file:///${user.home}/.champions/stripe.properties")
//@PropertySource("file:///application-commom.properties")
//@PropertySource("file:///stripe.properties")
@PropertySource("classpath:application-commom.properties")
@PropertySource("classpath:stripe.properties")
public class ApplicationConfig {

	@Value("${jsa.aws.access_key_id}")
	private String awsId;

	@Value("${jsa.aws.secret_access_key}")
	private String awsKey;

	@Value("${jsa.s3.region}")
	private String region;

	@Bean
	public AmazonS3 s3client() {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsId, awsKey);
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region))
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();

		return s3Client;
	}

}