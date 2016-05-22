package net.nikodem.service.crypto;

import java.security.*;
import java.util.*;

/**
 * Generator of securely random alphanumeric strings.
 * <p>
 * Not thread-safe.
 *
 * @link http//www.stackoverflow.com/questions/7111651/how-to-generate-a-secure-random-alphanumeric-string-in-java
 * -efficiently
 */
public class VoterKeyGenerator {

    private static final int VOTER_KEY_LENGTH = 16;
    private static final char[] ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456879".toCharArray();
    private static final int NUMBER_OF_ALPHANUMERIC_CHARACTERS = ALPHANUMERIC_CHARACTERS.length;
    private static final float NUMBER_ENTROPY_BITS_NEEDED_FOR_ONE_RANDOM_ALPHANUMERIC_CHARACTER = (float) Math.log
            (NUMBER_OF_ALPHANUMERIC_CHARACTERS);
    private static final float NUMBER_OF_ENTROPY_BITS_GENERATED_BY_SECURE_NEXT_LONG = 64;

    private SecureRandom secureRandom = new SecureRandom();
    private Random random = new Random();
    private float secureEntropyInBits = 0;

    public VoterKeyGenerator() {
    }

    public String generateNextRandomVoterKey() {
        char[] buffer = new char[VOTER_KEY_LENGTH];
        for (int i = 0; i < VOTER_KEY_LENGTH; i++) {
            buffer[i] = getSecureRandomCharacter();
        }
        return new String(buffer);
    }

    private char getSecureRandomCharacter() {
        if (notEnoughSecureEntropyLeft()) {
            addSecureEntropy();
        }
        substractUsedSecureEntropy();
        return getRandomAlphanumericCharacter();
    }

    private boolean notEnoughSecureEntropyLeft() {
        return secureEntropyInBits < NUMBER_ENTROPY_BITS_NEEDED_FOR_ONE_RANDOM_ALPHANUMERIC_CHARACTER;
    }

    private void addSecureEntropy() {
        random.setSeed(secureRandom.nextLong());
        secureEntropyInBits = NUMBER_OF_ENTROPY_BITS_GENERATED_BY_SECURE_NEXT_LONG;
    }

    private void substractUsedSecureEntropy() {
        secureEntropyInBits -= NUMBER_ENTROPY_BITS_NEEDED_FOR_ONE_RANDOM_ALPHANUMERIC_CHARACTER;
    }

    private char getRandomAlphanumericCharacter() {
        return ALPHANUMERIC_CHARACTERS[getRandomAlphanumericIndex()];
    }

    private int getRandomAlphanumericIndex() {
        return random.nextInt(NUMBER_OF_ALPHANUMERIC_CHARACTERS);
    }
}
