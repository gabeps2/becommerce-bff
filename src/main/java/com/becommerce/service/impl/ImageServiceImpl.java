package com.becommerce.service.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.becommerce.model.PartnerModel;
import com.becommerce.model.PreSignUrlSchema;
import com.becommerce.model.ProductModel;
import com.becommerce.model.Service;
import com.becommerce.service.AuthenticateUserService;
import com.becommerce.service.PartnerService;
import com.stripe.model.Product;
import com.stripe.param.ProductCreateParams;
import io.micronaut.http.HttpStatus;
import com.becommerce.service.ImageService;
import io.micronaut.context.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.net.URL;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import static com.becommerce.model.enums.ErrorEnum.EXTERNAL_ERROR;
import static com.becommerce.model.enums.ErrorEnum.GENERATE_ERROR;

@Singleton
@Named
public class ImageServiceImpl extends Service implements ImageService {
    @Value("${S3_ACCESS_KEY}")
    private String S3_ACCESS_KEY;
    @Value("${S3_SECRET_KEY}")
    private String S3_SECRET_KEY;
    @Value("${S3_BUCKET_NAME}")
    private String S3_BUCKET_NAME;
    @Value("${S3_REGION}")
    private String S3_REGION;
    @Inject
    private AuthenticateUserService authenticateUserService;

    @Inject
    private PartnerService partnerService;

    private final String DEFAULT_PATH = "PUBLIC/PARTNERS/";
    private final String PREFIX_URL = "https://%s.s3.amazonaws.com/";

    @Override
    public PreSignUrlSchema getPreSignUrl(String token) {
        Optional<PartnerModel> partnerModel = partnerService.getByUserId(authenticateUserService.getSubject(token));
        if (partnerModel.isEmpty()) throw throwsException(GENERATE_ERROR, HttpStatus.PRECONDITION_FAILED);

        String encodedFileName = Base64.getEncoder().encodeToString(partnerModel.get().getName().getBytes());

        String fileLocation = DEFAULT_PATH + encodedFileName + "/";
        String fileName = encodedFileName + "_" + UUID.randomUUID() + ".png";
        String key = fileLocation + fileName;
        String objectUrl = String.format(PREFIX_URL + key, S3_BUCKET_NAME);

        return PreSignUrlSchema.builder()
                .uploadUrl(generatePutPreSignUrl(key))
                .objectUrl(objectUrl)
                .build();
    }

    @Override
    public String generatePutPreSignUrl(String fileName) {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(S3_ACCESS_KEY, S3_SECRET_KEY);
        final AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(
                new AWSStaticCredentialsProvider(awsCreds)
        ).withRegion(S3_REGION).build();

        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60;
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(S3_BUCKET_NAME, fileName)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(expiration);
        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }
}
