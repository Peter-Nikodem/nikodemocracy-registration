package net.nikodem.service

import net.nikodem.repository.ElectionRepository
import spock.lang.Specification

/**
 * @author Peter Nikodem 
 */
class ElectionCreationServiceSpec extends Specification {
    ElectionCreationService electionCreationService
    ElectionRepository electionRepositoryMock

    def setup() {
        electionRepositoryMock = Mock(ElectionRepository)
        electionCreationService = new ElectionCreationService()
        electionCreationService.setElectionRepository(electionRepositoryMock)
    }









}
