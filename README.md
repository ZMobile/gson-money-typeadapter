# Gson Money Type Adapter

[![Build Status](https://travis-ci.org/zalando-incubator/gson-money-typeadapter.svg?branch=master)](https://travis-ci.org/zalando-incubator/gson-money-typeadapter)
[![codecov](https://codecov.io/gh/zalando-incubator/gson-money-typeadapter/branch/master/graph/badge.svg)](https://codecov.io/gh/zalando-incubator/gson-money-typeadapter)


The _Gson Money TypeAdapter_ supports the serialization and deserialization of [JavaMoney](https://github.com/JavaMoney) data types by [GSON](https://github.com/google/gson).

### Serialization

The _Gson Money TypeAdapter_ is able to serialize the following data types:

| Data Types | JSON |
|:-----------:|:------:|
|   `javax.money.CurrencyUnit` | `"EUR"`  |
|   `javax.money.MonetaryAmount` | `{"amount": 12.34, "currency": "EUR"}`  |


### Deserialization

The _Gson Money TypeAdapter_ uses by default the `Monetary.getDefaultAmountFactory()` during _deserialization_. Also,
 it supports all the default `MonetaryAmount` implementations.

- `org.javamoney.moneta.FastMoney`
- `org.javamoney.moneta.Money`
- `org.javamoney.moneta.RoundedMoney`

## Requirements
- Java 7 or higher
- Gson
- JavaMoney

## Installation

Add the following dependency to the project:

```xml
<dependency>
    <groupId>org.zalando</groupId>
    <artifactId>gson-money-typeadapter</artifactId>
    <version>${gson-money-typeadapter.version}</version>
</dependency>
```

## How to Use

Register the class `MoneyTypeAdapterFactory` to the `GsonBuilder` as follows:

```java
Gson gson = new GsonBuilder().registerTypeAdapterFactory(new MoneyTypeAdapterFactory()).create();
```

In case that a custom implementation of `MonetaryAmountFactory<T>` exists, register `MoneyTypeAdapterFactory` as:

```java
MonetaryAmountFactory customMonetaryAmountFactory = new customMonetaryAmountFactory();

Gson gson = new GsonBuilder().registerTypeAdapterFactory(new MoneyTypeAdapterFactory(customMonetaryAmountFactory)).create();
