package net.nikodem.testdata

import net.nikodem.model.dto.VoteAuthorizationRequest
import net.nikodem.model.dto.VoterRegistrationRequest


class ExampleVoter {
    public static final ExampleVoter ALICE = new ExampleVoter('Alice', 'rabbitHole')
    public static final ExampleVoter BOB = new ExampleVoter('Bob', 'testObsessed')
    public static final ExampleVoter CECIL = new ExampleVoter('Cecil', 'evenMoreTestObsessed')
    public static final ExampleVoter DENIS = new ExampleVoter('Denis', "PogChampKappa")
    public static final ExampleVoter EVE = new ExampleVoter('Eve', 'ohSoEvil')
    public static final ExampleVoter INVALID = new ExampleVoter('Mute', '')

    public static final List<ExampleVoter> GOOD_GUYS = [ALICE, BOB, CECIL, DENIS]
    public static final List<ExampleVoter> ACTUAL_GUYS = [BOB, CECIL, DENIS]
    public static final List<ExampleVoter> EVERYONE = [ALICE, BOB, CECIL, DENIS, EVE]

    final String username;
    final String password;
    final String repeatedPassword;

    private ExampleVoter(String username, String password) {
        this.username = username
        this.password = password
        this.repeatedPassword = password
    }

    VoterRegistrationRequest getVoterRegistrationRequest() {
        return new VoterRegistrationRequest(username, password, repeatedPassword)
    }

    VoteAuthorizationRequest getVoteAuthorizationRequestFor(String electionId) {
        return new VoteAuthorizationRequest(username, password, electionId)
    }
}
