/**
 * Copyright 2016 RedElastic Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package websockets;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Status;
import akka.japi.Pair;
import akka.stream.Materializer;
import akka.stream.OverflowStrategy;
import akka.stream.javadsl.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.reactivestreams.Publisher;
import play.Logger;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Websockets backed by Akka streams w/ an actor as the flow.
 * Websocket in is the source, used so subscribe to a topic
 * and websocket out is the sink, used to publish any updates to a topic
 *
 * This is an easy example to adapt to your use case.
 *
 * To Use:
 * - Implement createWebsocketActor
 * - create a controller method that returns the WebSocket from the webSocket method in a play controller.
 * - create a route to your websocket method.
 *
 * Based on the Play Java Websockets examples:
 * https://github.com/playframework/play-websocket-java/
 *
 * The Play documentation for Java Websockets references the deprecated LegacyWebSocket API.
 * This is a better starting point.
 */

public abstract class ActorBackedWebSocket {

    /**
     * Implement this to describe how to create the actor to push out data to a websocket.
     *
     * @param webSocketOut
     * @return
     */
    public abstract ActorRef createWebsocketActor(ActorRef webSocketOut);

    /**
     * Produces the websocket to return to play for use as an endpoint.
     *
     *
     * @param system
     * @param materializer
     * @return
     */
    public play.mvc.WebSocket webSocket(ActorSystem system, Materializer materializer) {
        return play.mvc.WebSocket.Json.acceptOrResult(request -> {
            if (sameOriginCheck(request)) {
                // create an actor ref source and associated publisher for sink
                final Pair<ActorRef, Publisher<JsonNode>> pair = createWebSocketConnections(materializer);
                Publisher<JsonNode> webSocketIn = pair.second();
                ActorRef webSocketOut = pair.first();

                // Create a user actor off the request id and attach it to the source
                final ActorRef webSocketActor = createWebsocketActor(webSocketOut);
                final Flow<JsonNode, JsonNode, NotUsed> flow = createWebSocketFlow(webSocketIn, webSocketActor, system);
                return CompletableFuture.completedFuture(F.Either.Right(flow));
            } else {
                Logger.error("Error found w/ origin header while creating websocket...");
                return forbiddenResult();
            }
        });
    }

    /**
     * Akka Streams for websockets backed by an actor
     * We want an actor to back the stream so it can receive updates via the event bus.
     *
     * @param webSocketIn
     * @param userActor
     * @param actorSystem
     * @return
     */
    public Flow<JsonNode, JsonNode, NotUsed> createWebSocketFlow(Publisher<JsonNode> webSocketIn, ActorRef userActor, ActorSystem actorSystem) {
        // source is what comes in: browser ws events -> play -> publisher -> userActor
        // sink is what comes out:  userActor -> websocketOut -> play -> browser ws events
        final Sink<JsonNode, NotUsed> sink = Sink.actorRef(userActor, new Status.Success("success"));
        final Source<JsonNode, NotUsed> source = Source.fromPublisher(webSocketIn);
        final Flow<JsonNode, JsonNode, NotUsed> flow = Flow.fromSinkAndSource(sink, source);

        //Ensure the websocket actor is killed once the websocket is closed.
        return flow.watchTermination((ignore, termination) -> {
            termination.whenComplete((done, throwable) -> {
                Logger.info("terminating websocket actor!");
                if (throwable != null) throwable.printStackTrace();
                actorSystem.stop(userActor);
            });

            return NotUsed.getInstance();
        });
    }

    /**
     * Creates a source to be materialized as an actor reference.
     *
     * @param materializer
     * @return
     */
    public Pair<ActorRef, Publisher<JsonNode>> createWebSocketConnections(Materializer materializer) {
        // Creating a source can be done through various means, but here we want
        // the source exposed as an actor so we can send it messages from the event bus.
        final Source<JsonNode, ActorRef> source = Source.actorRef(10, OverflowStrategy.dropTail());

        // Creates a sink to be materialized as a publisher.
        final Sink<JsonNode, Publisher<JsonNode>> sink = Sink.asPublisher(AsPublisher.WITHOUT_FANOUT);

        // Connect the source and sink into a flow and materialize.
        return source.toMat(sink, Keep.both()).run(materializer);
    }

    /**
     * "The |Origin| header field [RFC6454] is used to protect against
     * unauthorized cross-origin use of a WebSocket server by scripts using
     * the WebSocket API in a web browser."
     * http://blog.dewhurstsecurity.com/2013/08/30/security-testing-html5-websockets.html
     * @param rh
     * @return success or failure of check
     */
    public boolean sameOriginCheck(Http.RequestHeader rh) {
        final String originHeader = rh.getHeader("Origin");
        if (originHeader == null || !originMatches(originHeader)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks that the origin header is as expected to mitigate xsrf.
     * TODO Move the list of servers to the configuration.
     *
     * @param origin origin http header used to validate source of connection
     * @return
     */
    private boolean originMatches(String origin) {
        return origin.contains("localhost:9000") ||
                origin.contains("localhost:19001") ||
                origin.contains("chrome-extension://"); //Allow interactions w/ Chrome Extension: https://chrome.google.com/webstore/detail/simple-websocket-client/pfdhoblngboilpfeibdedpjgfnlcodoo?hl=en
    }

    /**
     * Produce a failed response for the origin match check.
     * @return
     */
    private CompletionStage<F.Either<Result, Flow<JsonNode, JsonNode, ?>>> forbiddenResult() {
        final F.Either<Result, Flow<JsonNode, JsonNode, ?>> left = F.Either.Left(Results.forbidden("forbidden"));
        return CompletableFuture.completedFuture(left);
    }
}
