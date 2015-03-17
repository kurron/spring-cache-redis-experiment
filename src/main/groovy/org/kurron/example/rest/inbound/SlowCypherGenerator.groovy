package org.kurron.example.rest.inbound

import org.apache.commons.codec.digest.DigestUtils
import org.springframework.stereotype.Service

/**
 * A fake implementation of a cypher text generator.
 */
@Service
class SlowCypherGenerator implements CypherGenerator {
    @Override
    String encode( String plainText ) {
        DigestUtils.md5Hex( plainText )
    }
}
