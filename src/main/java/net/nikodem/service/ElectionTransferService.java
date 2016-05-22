package net.nikodem.service;

import net.nikodem.model.dto.*;
import net.nikodem.model.exception.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.http.converter.json.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

@Service
public class ElectionTransferService {

    private RestTemplate restTemplate;

    public ElectionTransferService() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(new MappingJackson2HttpMessageConverter());
    }

    @Autowired
    @Value("${tabulation-authority.base-url}")
    private String tabulationAuthorityUrl;

    public void postElection(ElectionInformation electionInfo) {
        try {
            HttpStatus responseStatus = transferElectionDetails(electionInfo);
            if (!responseStatus.is2xxSuccessful()) {
                throw new ElectionTransferFailedException();
            }
        }
        catch (HttpStatusCodeException e){
            throw new ElectionTransferFailedException();
        }
    }

    private HttpStatus transferElectionDetails(ElectionInformation electionInfo) {
        return restTemplate.postForEntity(tabulationAuthorityUrl + "/elections", electionInfo, String.class)
                .getStatusCode();
    }

    protected RestTemplate getRestTemplate() {
        return this.restTemplate;
    }

    protected void setTabulationAuthorityUrl(String url) {
        tabulationAuthorityUrl = url;
    }
}
