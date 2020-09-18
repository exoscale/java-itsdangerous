# java-itsdangerous: HMAC'd payloads for web sessions in Java

A Java library designed to sign and verity tokens using the
[itsdangerous](https://palletsprojects.com/p/itsdangerous/) scheme.


![](https://github.com/exoscale/itsdanjerous/workflows/Clojure%20CI/badge.svg)
[![Clojars Project](https://img.shields.io/clojars/v/exoscale/itsdanjerous.svg)](https://clojars.org/exoscale/itsdanjerous)

## Key concepts

ItsDangerous relies on the following shared knowledge:

- A private key
- A misnamed *salt*, which isn't the usual salt found in cryptographic systems.
  In ItsDangerous it is used to namespace signed tokens. Precisions at
  https://itsdangerous.palletsprojects.com/en/1.1.x/serializer/#the-salt
- An algorithm (currently SHA1 or SHA256)

These must be decided out of band between signing and verifying parties.


## Usage

To sign a payload, you will first need a working configuration for the
shared knowledge elements:

``` java
import exoscale.itsdangerous.Config;
import exoscale.itsdangerous.Algorithm;
import java.util.List;

// ...

final Config cfg = new Config(saltString, Algorithm.SHA256, List.of("new-secret", "old-secret"));
```

The configuration can then be used to sign and verify payloads:

``` java
final String token = cfg.sign("HELLO");
final String payload = cfg.verify(token); // yields "HELLO"
```

## Token validity

By default, a produced token contains a timestamp. This timestamp is the UNIX
epoch in seconds.

When verifying with the additional `maxAge` argument, an additional check
is performed on the age of the provided payload.

``` java
// Ensure token was signed within the last 30 seconds
final String payload = cfg.verify(token, 30);
```
