# Reactive Commerce

Welcome to RedElastic Commerce, a reference application that demonstrates a broad scope of features in the Play framework in context of the commerce domain.  There are several demo files available in this template.

## Running the Application

Prior to running the application, you will need to have a Postgres instance running. If you're using a Mac or Linux, you can get up and running in minutes with [postgres.app](http://postgresapp.com/).

And you'll need to have cassandra:

```sh
brew install cassandra
cassandra
```

After you have Postgres and Cassandra running, you can execute `sbt run` or `activator run`

## Testing the Application

Because Play 2.5 uses Guice heavily, it is not enough to rely on compilation alone to detect errors. We must implement a reasonable test suite to detect injection errors.

To execute the tests: `sbt test`

### Tests

**PricingControllerTest.java**

Demonstrates a minimal test of a controller.

**PricingServiceTest.java**

Demonstrates how to test bounded contexts directly.

# Composition

The following section explains each major piece of the overall architecture.

## Controllers

**ApplicationController.java**

Shows how to use `CompletionStage<T>` for asynchronous processing along with a custom `ExecutionContext` wrapper that bridges the gap between Java (Executor) and Scala (ExecutionContext).

Includes an example implementation of parallel fibonacci.

**PricingController.java**

Shows how to do lazy loading for data required by the front end. Renders data as JSON for integration with AJAX calls from JavaScript for pricing information.

We architect pricing information asynchronously as pricing will be provided by a completely different subdomain.

**ProductController.java**

The main controller that renders the home page, which is a list of product data.

`index()` returns a `Result` to demonstrate the difference between synchronous and asynchronous programming (Result is synchronous, CompletionStage(Result) is asynchronous).

## Components

**Module.java**

Shows how to use [Guice](https://github.com/google/guice) to bind all the components needed by your application.

**Filters.java**

Configured filters that are applied to every request.

## Services

**ApplicationTimer.java**

An example of a component that starts when the application starts and stops when the application stops.

**LongRunningComputation.java**

An example computation that can be injected and executed by controllers. Computes fibonacci based on the limit passed in.

## Filters

**CorsFilter.java**

TODO

**ResponseCodeMonitorFilter.java**

TODO

## ec

**CustomExecutor.java**

Abstract Java Executor for isolating work in different fork-join-pools and thread-pools. We need to provide this wrapper in order to load the configuration of thread pools and fork join from `application.conf` and pass the executor to `CompletionStage<T>`.

**MemoryHungryExecutor.java**

TODO

## contexts

This package contains all of the business logic of your application. Each subfolder represents a subdomain within your application.

### api

This folder contains the API and all value objects of the subdomain. It can be considered the [anti-corruption layer] of the subdomain in DDD terminology.

### live

This is the implementation of the subdomain’s logic when in production.

### stub

This is the implementation of the subdomain’s logic when in test or development. Ideally you would not depend on external resources unless testing or developing in production mode.

