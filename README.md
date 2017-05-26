# Reactive Commerce

Welcome to RedElastic Commerce, a reference application that demonstrates a broad scope of features in the Play framework in context of the commerce domain.  There are several demo files available in this template.

## Running the Application

Prior to running the application, you will need to have a Postgres instance running. If you're using a Mac or Linux, you can get up and running in minutes with [postgres.app](http://postgresapp.com/).

And you'll need to have Cassandra running:

```sh
docker run -p 9042:9042 Cassandra
```

And ZooKeeper for co-ordination

```sh
docker run -p 2181:2181 --name some-zookeeper --restart always -d zookeeper
```

After you have ZooKeeper and Cassandra running, you can execute `sbt run`

If you want to run a few nodes to see akka cluster in action, you can define different http and netty remoting ports.

```sh
sbt run -Dhttp.port=9000 -Dakka.remote.netty.tcp.port=0
sbt run -Dhttp.port=9001 -Dakka.remote.netty.tcp.port=0
sbt run -Dhttp.port=9002 -Dakka.remote.netty.tcp.port=0
```

The project uses constructr so no seed node configuration is required - ZooKeeper will be used to discover any running clusters.

## Testing the Application

Because Play 2.5 uses Guice heavily, it is not enough to rely on compilation alone to detect errors. We must implement a reasonable test suite to detect injection errors.

To execute the tests: `sbt test`

# Composition

The following section explains each major piece of the overall architecture. The application has started to be factored to onion architecture in the packaging approaches to help reduce "corruption" and allow us to migrate to microservices easily as the project grows.

At the outer level is "controllers" and "infrastructure." These can use code in the "core" domain (its application/service layer in the API package).

## root package

**Module.java**

Shows how to use [Guice](https://github.com/google/guice) to bind all the components needed by your application.

**Filters.java**

Configured `infrastructure.filters` that are applied to every request.

## core.cart.cluster

An event-sourcing implementation of a cart using Akka cluster & persistence.
A cart is a great place to try event sourcing approach because the data can effectively be viewed as ephemeral.
It can take a team a bit of time to understand event sourcing, especially to run it in production as there are concerns like changing messages over time that must be handled correctly.
This project uses java serialization but it should not - we'll flip it to proto or kryo w/ compatible field serializer shortly.

## controllers

**ApplicationController.java**

Shows how to use `CompletionStage<T>` for asynchronous processing along with a custom `ExecutionContext` wrapper that bridges the gap between Java (Executor) and Scala (ExecutionContext).

Includes an example implementation of parallel fibonacci.

**PricingController.java**

Shows how to do lazy loading for data required by the front end. Renders data as JSON for integration with AJAX calls from JavaScript for pricing information.

We architect pricing information asynchronously as pricing will be provided by a completely different subdomain.

**ProductController.java**

The main controller that renders the home page, which is a list of product data.

`index()` returns a `Result` to demonstrate the difference between synchronous and asynchronous programming (Result is synchronous, CompletionStage(Result) is asynchronous).

## infrastructure root

Infrastructure is a component of the architecture that is not the core domain, but the supporting components. They may talk to the core domain but the core domain should not be aware of the infrastructure.

**ApplicationTimer.java**

An example of a component that starts when the application starts and stops when the application stops.

**LongRunningComputation.java**

An example computation that can be injected and executed by controllers. Computes fibonacci based on the limit passed in.

**GracefulShutdown.java**

Demonstrates how to grab the SIGTERM to stop Play from shutting down until cleanup is done. This may not be future safe but we want to hand off anything akka cluster related before we continue with shutdown.
This is a generic approach that will work in all situations (serviced, docker w/ kubernetes or mesos, etc)

**ClusterHealthCheck.java**

We want our orchestration and load balancing tools to mark us down if we aren't a member of the cluster, so we need to expose this via a healthcheck.
For example, this will ensure that Mesos/Marathon will restart the application if it becomes dissociated from the cluster or is unable to join it.

## infrastructure.websockets

Has an example of sending product events to a websocket using an Akka EventBus. Note that there is a design problem with the core vs infrastructure. Because some of the core domain is bled into the infrastructure package, (ProductEventPubSubActor) we can see that we should refactor this. Onion architecture helps protect us from making errors like this. This will be corrected in a future version.

## infrastructure.filters

**CorsFilter.java**

Adds CORS headers to responses (as an example) 

**ResponseCodeMonitorFilter.java**

Gets metrics for response codes to provide to monitoring solution.
This is an example - you can use Cinnamon with a Typesafe subscription.

## infrastructure.ec

Contains example of executors which could be used for different work types to take work out of the default Akka executor.
We want our application to always be responsive, so we need to ensure that any work that will tie threads (blocking work, or long running tasks) will not hamper Akka from picking up and fulfilling requests.
These classes exist to create the instances in Guice context so that they may be injected.
See the application.conf for the configuration.

Note, you should not block threads in fork-join as it will cause excessive threads to be forked so do any blocking IO in thread pools.

**CustomExecutor.java**

Abstract Java Executor for isolating work in different fork-join-pools and thread-pools. We need to provide this wrapper in order to load the configuration of thread pools and fork join from `application.conf` and pass the executor to `CompletionStage<T>`..

**MemoryHungryExecutor.java**

This is a separate fork-join pool which will allocate up to two threads per core. 

**BlockingExecutor.java**

Uses a thread pool for blocking IO. Uses 50 threads - remember threads are expensive so we don't want to use an arbitrarily large quantity. 
For tuning, look at Little's Law gives us some heuristics. http://www.columbia.edu/~ks20/stochastic-I/stochastic-I-LL.pdf

## core

This package contains all of the business logic of your application. The term "core" is a reference to onion architecture and refers to the core domain model. Each subfolder represents a subdomain/bounded context within your application.
In a monolith-first approach, these domain contexts would be targets for putting in their own microservices. For example, cart is ready to move out into its own service (and will be shortly as we evolve the example).

### core.$context.api

This folder contains the API and all value objects of the subdomain. It can be considered the [anti-corruption layer] of the subdomain in DDD terminology.
While [MonolithFirst](https://martinfowler.com/bliki/MonolithFirst.html) is a commonly discussed pattern, the reality is that monoliths generally have too much tight coupling to make an application easy to split into independent services.
[Some people disagree with the approach](https://martinfowler.com/articles/dont-start-monolith.html) however we can mitigate the risks associated with a monolith first approach by employing such approaches.
The benefit of starting with a monolith is that you gain understanding about the core domain to know where bounded contexts exist before they become independent services where it becomes expensive to change them.

### core.$context.$implementation

The implementation(s) of the bounded context's logic will exist in a package descriptive of the implementation. 

### core.$context.stub

This is the implementation of the subdomain’s logic when in test or development. Ideally you would not depend on external resources unless testing or developing in production mode.

## marathon.example.json

Shows example configuration for running in mesos via Marathon