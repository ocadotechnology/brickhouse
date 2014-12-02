package brickhouse.udf.json;

import junit.framework.Assert;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Timestamp;

public class JsonUDFTest {

    @Test
    public void testConvertToCamelCase() {
        String underScore = "this_text_has_underscores";

        String camel = FromJsonUDF.ToCamelCase(underScore);
        System.out.println(camel);

        Assert.assertEquals("thisTextHasUnderscores", camel);
    }

    @Test
    public void testConvertFromCamelCase() {
        String camel = "thisTextIsInCamelCase";

        String under = FromJsonUDF.FromCamelCase(camel);
        System.out.println(under);

        Assert.assertEquals("this_text_is_in_camel_case", under);
    }

    @Test
    public void testWritingJsonStrings() throws IOException {
        StringWriter writer = new StringWriter();
        JsonGenerator gen = new JsonFactory().createJsonGenerator(writer);

        gen.writeStartObject();
        gen.writeFieldName("a");
        ToJsonUDF.writeString(gen, "{\"a\": 1}");
        gen.writeEndObject();

        gen.writeStartObject();
        gen.writeFieldName("b");
        ToJsonUDF.writeString(gen, "[1, 2]");
        gen.writeEndObject();

        gen.writeStartObject();
        gen.writeFieldName("c");
        ToJsonUDF.writeString(gen, "x");
        gen.writeEndObject();


        gen.close();
        writer.close();
        Assert.assertEquals(writer.toString(), "{\"a\":{\"a\": 1}} {\"b\":[1, 2]} {\"c\":\"x\"}");
    }

    @Ignore
    @Test
    public void testWritingTimestamps() throws IOException {
        StringWriter writer = new StringWriter();
        JsonGenerator gen = new JsonFactory().createJsonGenerator(writer);

        ToJsonUDF.writeTimestamp(gen, new Timestamp(0), ISODateTimeFormat.dateTimeNoMillis());

        gen.close();
        writer.close();
        System.out.println(writer.toString());
    }
}