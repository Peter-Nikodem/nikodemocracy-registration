package net.nikodem.service

import net.nikodem.model.dto.ElectionInformation
import net.nikodem.model.exception.ElectionTransferFailedException
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus

class ElectionTransferServiceSpec extends Specification {

    ElectionTransferService transferService = new ElectionTransferService();

    RestTemplate restTemplate;

    MockRestServiceServer mockServer;

    final String TABULATION_URL_EXAMPLE = 'http://nikodemocracy.net/tabulation'

    def setup() {
        restTemplate = transferService.restTemplate
        mockServer = MockRestServiceServer.createServer(restTemplate)
        transferService.tabulationAuthorityUrl = TABULATION_URL_EXAMPLE
    }

    def "Election is transfered through tabulation authority API"() {
        given:
        mockServer.expect(requestTo(TABULATION_URL_EXAMPLE + "/elections"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.ACCEPTED))
        expect:
        transferService.postElection(new ElectionInformation())
    }

    def "Without successful election transfer confirmation from tabulation authority server exception is thrown"() {
        given:
        mockServer.expect(requestTo(TABULATION_URL_EXAMPLE + "/elections"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(testedStatus))
        when:
        transferService.postElection(new ElectionInformation())
        then:
        thrown(ElectionTransferFailedException)
        where:
        testedStatus << [HttpStatus.BAD_GATEWAY, HttpStatus.FORBIDDEN, HttpStatus.PERMANENT_REDIRECT]
    }
}
