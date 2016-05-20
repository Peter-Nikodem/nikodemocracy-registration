package net.nikodem.service

import net.nikodem.model.json.ElectionInformation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import spock.lang.Ignore
import spock.lang.Specification


class TabulationCommunicationServiceSpec extends Specification {
    @Ignore
    def " test "(){
        given:
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(new MappingJackson2HttpMessageConverter());
        ElectionInformation electionInfo = new ElectionInformation("electionId","sup?",['a','b'],['da','ds','sad'])
        when:
        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:8082/elections",
                electionInfo, String.class);
        then:
        result.statusCode.'2xxSuccessful'
    }
}
