package net.nikodem.service;

import net.nikodem.model.exception.*;
import net.nikodem.model.json.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.http.converter.json.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

@Service
public class ElectionTransferringService {

    private final RestTemplate restTemplate;

    public ElectionTransferringService() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(new MappingJackson2HttpMessageConverter());
    }

    @Autowired
    @Value("${tabulation-authority.base-url}")
    private String tabulationAuthorityUrl;

    public void postElection(ElectionInformation electionInfo) {
        HttpStatus responseStatus = transferElectionDetails(electionInfo);
        if (!responseStatus.is2xxSuccessful()) {
            throw new ElectionTransferFailedException();
        }
    }

    private HttpStatus transferElectionDetails(ElectionInformation electionInfo) {
        System.out.println(tabulationAuthorityUrl);
        return restTemplate.postForEntity(tabulationAuthorityUrl + "/elections", electionInfo, String.class)
                .getStatusCode();
    }
}
