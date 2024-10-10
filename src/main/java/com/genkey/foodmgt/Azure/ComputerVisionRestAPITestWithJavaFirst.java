package com.genkey.foodmgt.Azure;

// // This sample uses the Apache HTTP client from HTTP Components (http://hc.apache.org/httpcomponents-client-ga/)

import com.microsoft.azure.cognitiveservices.vision.computervision.implementation.ComputerVisionImpl;

import com.microsoft.azure.cognitiveservices.vision.computervision.ComputerVision;
import com.microsoft.azure.cognitiveservices.vision.computervision.ComputerVisionClient;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.Line;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.OperationStatusCodes;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.ReadOperationResult;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.ReadResult;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

//import static com.genkey.foodmgt.services.api.ComputerVisionQuickstart.Authenticate;


public class ComputerVisionRestAPITestWithJavaFirst {

    public static String endpoint = "https://comp-visio.cognitiveservices.azure.com/";
    //    public static String endpoint = "https://foodmgtvisionservice.cognitiveservices.azure.com";
    public static String key = "67d3237db8db44c9a5a90cbb17da5d2c";



//    private static byte[] ReadFromFile(String path)
//
////        String path = "C:\\Users\\Work Pc\\Desktop\\football-management\\football-management\\src\\main\\resources\\Skype_Picture_2022_02_22T12_52_56_825Z.jpg";
//        System.out.println("Read with local file: " + path);
//        byte[] localImageBytes = null;
//        try {
//            File rawImage = new File(path);
//            localImageBytes = Files.readAllBytes(rawImage.toPath());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("LocalImageByte:" + localImageBytes);
//        return localImageBytes;

//
    // }


    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        //ComputerVisionClient compVisClient = Authenticate(key, endpoint);
       // ReadFromUrl((ComputerVisionClient) compVisClient);
//        URIBuilder builder1 = new URIBuilder(endpoint+"vision/v3.2/read/analyzeResults/"+operationLocation);
//
//        URI uri = builder1.build();
//        HttpPost request1 = new HttpPost(uri);
//        request1.setHeader("Content-Type", "application/json");
//        request1.setHeader("Ocp-Apim-Subscription-Key", key);
//        String body = "{\"url\": \"https://raw.githubusercontent.com/Azure-Samples/cognitive-services-sample-data-files/master/ComputerVision/Images/printed_text.jpg\"}";
//        StringEntity reqEntity = new StringEntity(body);
//        request1.setEntity(reqEntity);
//
//        HttpResponse response = httpclient.execute(request1);
//        HttpEntity entity1 = response.getEntity();
//        System.out.println(entity1.getContent())
    }

    private static <vision> void ReadFromUrl(ComputerVisionClient client ){
        //String operationLocation = "";
        String imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/af/Atomist_quote_from_Democritus.png/338px-Atomist_quote_from_Democritus.png";
        try {
            ComputerVisionImpl vision = (ComputerVisionImpl) client.computerVision();
            URIBuilder builder = new URIBuilder(endpoint + "vision/v3.2/read/analyze");
            HttpClient httpclient = HttpClients.createDefault();

//            builder.setParameter("language", "{string}");
//            builder.setParameter("pages", "{string}");
//            builder.setParameter("readingOrder", "{string}");
//            builder.setParameter("model-version", "{string}");

            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", key);


            // Request body
//            String path = "C:\\Users\\Work Pc\\Desktop\\football-management\\football-management\\src\\main\\resources\\Skype_Picture_2022_02_22T12_52_56_825Z.jpg";
//            String data = ReadFromFile(path).toString();
            String body = "{\n" +
                    "    \"url\": \"https://raw.githubusercontent.com/Azure-Samples/cognitive-services-sample-data-files/master/ComputerVision/Images/printed_text.jpg\"\n" +
                    "}";
            StringEntity reqEntity = new StringEntity(body);
            request.setEntity(reqEntity);
//Extracting the operation Id
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            String header = response.getAllHeaders()[1].toString();
            String[] headerArray = header.split("/");
            String operationLocation = headerArray[headerArray.length - 1];
            System.out.println(operationLocation);

            getAndPrintReadResult(vision, operationLocation);

            if (entity != null) {
                System.out.println(EntityUtils.toString(entity));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * Polls for Read result and prints results to console
     * @param vision Computer Vision instance
     * @return operationLocation returned in the POST Read response header
     */
    private static void getAndPrintReadResult(ComputerVision vision, String operationLocation) throws InterruptedException {
        System.out.println("Polling for Read results ...");

        // Extract OperationId from Operation Location


        boolean pollForResult = true;
        ReadOperationResult readResults = null;

        while (pollForResult) {
            // Poll for result every second
            Thread.sleep(1000);
            System.out.println(vision.getReadResult(UUID.fromString(operationLocation)));
            readResults = vision.getReadResult(UUID.fromString(operationLocation));

            // The results will no longer be null when the service has finished processing the request.
            if (readResults != null) {
                // Get request status
                OperationStatusCodes status = readResults.status();
                System.out.println(status.toString());

                if (status == OperationStatusCodes.FAILED || status == OperationStatusCodes.SUCCEEDED) {
                    pollForResult = false;
                }
            }
        }

        // Print read results, page per page
        for (ReadResult pageResult : readResults.analyzeResult().readResults()) {
            System.out.println("");
            System.out.println("Printing Read results for page " + pageResult.page());
            StringBuilder builder = new StringBuilder();

            for (Line line : pageResult.lines()) {
                builder.append(line.text());
                builder.append("\n");
            }

            System.out.println(builder.toString());
        }
    }


}




