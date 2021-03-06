package org.talend.daikon.exception;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.talend.daikon.exception.error.CommonErrorCodes;

/**
 * Unit test for the TDPException
 * 
 * @see TDPException
 */
public class TalendRuntimeExceptionTest {

    @Test
    public void createTalendRuntimeExceptionWithOnlyCode() {
        TalendRuntimeException talendRuntimeException = new TalendRuntimeException(CommonErrorCodes.UNABLE_TO_PARSE_JSON, null,
                null);
        assertNotNull(talendRuntimeException);
    }

    /**
     * @see TDPException#writeTo(Writer)
     */
    @Test
    public void shouldBeWrittenEntirely() throws Exception {

        TalendRuntimeException exception = new TalendRuntimeException(
                org.talend.daikon.exception.error.CommonErrorCodes.UNEXPECTED_EXCEPTION, new NullPointerException("root cause"),
                ExceptionContext.build().put("key 1", "Value 1").put("key 2", 123).put("key 3",
                        Arrays.asList(true, false, true)));

        String expected = read(TalendRuntimeExceptionTest.class.getResourceAsStream("expected-exception.json"));

        StringWriter writer = new StringWriter();
        exception.writeTo(writer);
        JSONAssert.assertEquals(expected, writer.toString(), false);
    }

    /**
     * Return the given inputstream as a String.
     * 
     * @param input the input stream to read.
     * @return the given inputstream content.
     * @throws IOException if an error occurred.
     */
    private String read(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line);
        }
        return content.toString();
    }
}
