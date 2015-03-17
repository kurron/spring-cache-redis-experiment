package org.kurron.example.rest.inbound

/**
 * Interface to a cypher text generator.
 */
interface CypherGenerator {

    /**
     * Encodes the plain text into cypher text.
     * @param plainText unencrypted text.
     * @return encrypted cypher text.
     */
    String encode( String plainText )
}