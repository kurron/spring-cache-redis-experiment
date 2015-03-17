/*
 * Copyright (c) 2015. Ronald D. Kurr kurr@jvmguy.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kurron.example.rest.inbound

import org.apache.commons.codec.digest.DigestUtils
import org.kurron.example.rest.ApplicationProperties
import org.kurron.feedback.AbstractFeedbackAware
import org.kurron.stereotype.InboundRestGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

import static org.kurron.example.rest.feedback.ExampleFeedbackContext.CYPHER_TEXT_COMPLETE
import static org.kurron.example.rest.feedback.ExampleFeedbackContext.CYPHER_TEXT_GENERATION
import static org.springframework.web.bind.annotation.RequestMethod.GET

/**
 * Handles inbound REST requests.
 */
@InboundRestGateway
@RequestMapping( value = '/slow' )
class SlowInboundGateway extends AbstractFeedbackAware {

    @Autowired
    private CypherGenerator generator

    @Autowired
    SlowInboundGateway( final CypherGenerator aGenerator ) {
        generator = aGenerator
    }

    /**
     * Retrieves an asset based on the provided id. If an error occurs, the global error handler kicks in and our json
     * fault document will be returned.
     * @param id the id of the asset to retrieve.
     * @return the response entity containing the requested asset.
     */
    @RequestMapping( value = '/{id}', method = GET )
    ResponseEntity<SlowHypermediaControl> retrieve( @PathVariable( 'id' ) final String id ) {
        feedbackProvider.sendFeedback( CYPHER_TEXT_GENERATION, id )
        def control = new SlowHypermediaControl()
        control.httpCode = HttpStatus.OK.value()
        control.cypherText = generator.encode( id )
        def headers = new HttpHeaders( contentType: SlowHypermediaControl.MEDIA_TYPE )
        feedbackProvider.sendFeedback(CYPHER_TEXT_COMPLETE, control.cypherText )
        new ResponseEntity( control, headers, HttpStatus.OK )
    }

    /**
     * Builds out a new instance of the control with values that make sense for most contexts. Further
     * customization is expected after this call.
     * @param status the HTTP status to save in the control.
     * @return partially populated control.
     */
    }
