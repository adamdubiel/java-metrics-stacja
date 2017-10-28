JVM Metrics Workshop - listener app
===================================

Run this app:

```
./gradlew run
```

It will listen at port `8081` by default:

```
curl localhost:8081/metrics
```

POST JSON to `/events`:

```
curl -X POST localhost:8081/events -d '{"id": "someId", "name": "restaurant #1"}'
```

Observe `event.received.*` metrics to see response time stats:

```
curl localhost:8081/metrics
```
