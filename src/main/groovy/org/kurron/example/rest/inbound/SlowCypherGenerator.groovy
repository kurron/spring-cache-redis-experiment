package org.kurron.example.rest.inbound

import org.apache.commons.codec.digest.DigestUtils
import org.kurron.feedback.AbstractFeedbackAware
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

import static org.kurron.example.rest.feedback.ExampleFeedbackContext.CYPHER_TEXT_GENERATION

/**
 * A fake implementation of a cypher text generator.
 */
@Service
class SlowCypherGenerator  extends AbstractFeedbackAware implements CypherGenerator {
    @Cacheable( 'cypher-text' )
    @Override
    String encode( String plainText ) {
        feedbackProvider.sendFeedback( CYPHER_TEXT_GENERATION, plainText )
        DigestUtils.md5Hex( plainText )
    }
}
