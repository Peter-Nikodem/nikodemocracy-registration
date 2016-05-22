package net.nikodem.testdata

import net.nikodem.model.dto.ElectionRegistrationRequest

class ExampleElections {
    public static final ElectionRegistrationRequest CYCLOPS_CONUNDRUM = new ElectionRegistrationRequest('cyclops',
            'If cyclops closes its eye, is it blinking or winking?',
            ['blinking', 'winking'],
            ExampleVoter.GOOD_GUYS.collect { it.username })

    public static final ElectionRegistrationRequest A_FEW_GOOD_MEN_PROBLEM = new ElectionRegistrationRequest('jack',
            'You want the truth?',
            ['Yes', "I can handle the truth", "Here's Johny"],
            ExampleVoter.ACTUAL_GUYS.collect { it.username })
    public static final ElectionRegistrationRequest INVALID_ELECTION_REQUEST = new ElectionRegistrationRequest('mute',
            '',
            [],
            [])

}
