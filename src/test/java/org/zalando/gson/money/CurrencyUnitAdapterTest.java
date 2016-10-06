package org.zalando.gson.money;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import org.junit.runner.RunWith;

import org.mockito.runners.MockitoJUnitRunner;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyUnitAdapterTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Gson gson = new GsonBuilder().registerTypeAdapterFactory(new MoneyTypeAdapterFactory())
                                         .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    @Test
    public void serializeNullCurrencyUnitValueAsObject() throws Exception {
        assertTrue("null".equals(gson.toJson(null, CurrencyUnit.class)));
    }

    @Test
    public void serializeConcreteCurrencyUnit() throws Exception {
        final Iterator<CurrencyUnit> amounts = buildCurrencyUnit();

        assertThat(gson.toJson(amounts.next(), CurrencyUnit.class), equalTo("\"CHF\""));
        assertThat(gson.toJson(amounts.next(), CurrencyUnit.class), equalTo("\"DKK\""));
        assertThat(gson.toJson(amounts.next(), CurrencyUnit.class), equalTo("\"EUR\""));
        assertThat(gson.toJson(amounts.next(), CurrencyUnit.class), equalTo("\"GBP\""));
        assertThat(gson.toJson(amounts.next(), CurrencyUnit.class), equalTo("\"NOK\""));
        assertThat(gson.toJson(amounts.next(), CurrencyUnit.class), equalTo("\"PLN\""));
        assertThat(gson.toJson(amounts.next(), CurrencyUnit.class), equalTo("\"SEK\""));
    }

    @Test
    public void deserializeNullCurrencyUnit() throws Exception {
        assertNull(gson.fromJson("null", CurrencyUnit.class));
    }

    @Test
    public void deserializeCurrencyUnitCollection() throws Exception {
        TypeToken<Collection<CurrencyUnit>> token = new TypeToken<Collection<CurrencyUnit>>() { };

        final Collection<CurrencyUnit> amountCollection = gson.fromJson("[null, \"EUR\"]", token.getType());

        assertThat(amountCollection, hasSize(2));
        assertThat(amountCollection, contains(nullValue(), equalTo(buildCurrencyUnit("EUR"))));
    }

    @Test
    public void deserializeWithUnknownCurrencyException() throws Exception {

        thrown.expect(JsonSyntaxException.class);
        thrown.expectMessage(equalTo("UnknownCurrencyException [currencyCode=EEE]"));

        gson.fromJson("\"EEE\"", CurrencyUnit.class);
    }

    private static Iterator<CurrencyUnit> buildCurrencyUnit() {
        return Arrays.asList(buildCurrencyUnit("CHF"), //
                         buildCurrencyUnit("DKK"), //
                         buildCurrencyUnit("EUR"), //
                         buildCurrencyUnit("GBP"), //
                         buildCurrencyUnit("NOK"), //
                         buildCurrencyUnit("PLN"), //
                         buildCurrencyUnit("SEK")).iterator();
    }

    private static CurrencyUnit buildCurrencyUnit(final String currencyCode) {
        return Monetary.getCurrency(currencyCode);
    }
}
