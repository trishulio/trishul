package io.trishul.test.json;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;

public class MockJsonGenerator extends JsonGenerator {
    private final StringBuffer buffer;

    public MockJsonGenerator() {
        this.buffer = new StringBuffer();
    }

    public String json() {
        return this.buffer.toString();
    }

    @Override
    public JsonGenerator setCodec(ObjectCodec oc) {
        throw new NoSuchMethodError("Not implemented");
    }

    @Override
    public ObjectCodec getCodec() {
        throw new NoSuchMethodError("Not implemented");
    }

    @Override
    public Version version() {
        throw new NoSuchMethodError("Not implemented");
    }

    @Override
    public JsonGenerator enable(Feature f) {
        throw new NoSuchMethodError("Not implemented");
    }

    @Override
    public JsonGenerator disable(Feature f) {
        throw new NoSuchMethodError("Not implemented");
    }

    @Override
    public boolean isEnabled(Feature f) {
        throw new NoSuchMethodError("Not implemented");
    }

    @Override
    public int getFeatureMask() {
        throw new NoSuchMethodError("Not implemented");
    }

    @Override
    public JsonGenerator setFeatureMask(int values) {
        throw new NoSuchMethodError("Not implemented");
    }

    @Override
    public JsonGenerator useDefaultPrettyPrinter() {
        throw new NoSuchMethodError("Not implemented");
    }

    @Override
    public void writeStartArray() throws IOException {
        this.buffer.append("[");
    }

    @Override
    public void writeEndArray() throws IOException {
        this.buffer.deleteCharAt(this.buffer.length());
        this.buffer.append("]");
    }

    @Override
    public void writeStartObject() throws IOException {
        if (buffer.length() > 1
                && Set.of(']', '}').contains(this.buffer.charAt(this.buffer.length() - 1))) {}
        this.buffer.append("{");
    }

    @Override
    public void writeEndObject() throws IOException {
        this.buffer.append("}");
    }

    @Override
    public void writeFieldName(String name) throws IOException {
        if (buffer.length() > 1
                && !Set.of('{', '[').contains(this.buffer.charAt(this.buffer.length() - 1))) {
            this.buffer.append(",");
        }
        this.buffer.append(String.format("\"%s\":", name));
    }

    @Override
    public void writeFieldName(SerializableString name) throws IOException {
        this.buffer.append(String.format("\"%s\":", name.getValue()));
    }

    @Override
    public void writeString(String text) throws IOException {
        this.buffer.append(String.format("\"%s\"", text));
    }

    @Override
    public void writeString(char[] text, int offset, int len) throws IOException {
        String s = String.valueOf(text, offset, len);
        this.buffer.append(String.format("\"%s\"", s));
    }

    @Override
    public void writeString(SerializableString text) throws IOException {
        this.buffer.append(String.format("\"%s\"", text.getValue()));
    }

    @Override
    public void writeRawUTF8String(byte[] text, int offset, int length) throws IOException {
        String s = new String(text, offset, length);
        this.buffer.append(String.format("\"%s\"", s));
    }

    @Override
    public void writeUTF8String(byte[] text, int offset, int length) throws IOException {
        String s = new String(text, offset, length, "UTF-8");
        this.buffer.append(String.format("\"%s\"", s));
    }

    @Override
    public void writeRaw(String text) throws IOException {
        this.buffer.append(String.format("\"%s\"", text));
    }

    @Override
    public void writeRaw(String text, int offset, int len) throws IOException {
        String s = new String(text.getBytes(), offset, len);
        this.buffer.append(String.format("\"%s\"", s));
    }

    @Override
    public void writeRaw(char[] text, int offset, int len) throws IOException {
        String s = new String(text, offset, len);
        this.buffer.append(String.format("\"%s\"", s));
    }

    @Override
    public void writeRaw(char c) throws IOException {
        String s = Character.toString(c);
        this.buffer.append(String.format("\"%s\"", s));
    }

    @Override
    public void writeRawValue(String text) throws IOException {
        this.buffer.append(String.format("\"%s\"", text));
    }

    @Override
    public void writeRawValue(String text, int offset, int len) throws IOException {
        String s = new String(text.getBytes(), offset, len);
        this.buffer.append(String.format("\"%s\"", s));
    }

    @Override
    public void writeRawValue(char[] text, int offset, int len) throws IOException {
        String s = new String(text, offset, len);
        this.buffer.append(String.format("\"%s\"", s));
    }

    @Override
    public void writeBinary(Base64Variant bv, byte[] data, int offset, int len) throws IOException {
        throw new NoSuchMethodError("Not implemented");
    }

    @Override
    public int writeBinary(Base64Variant bv, InputStream data, int dataLength) throws IOException {
        throw new NoSuchMethodError("Not implemented");
    }

    @Override
    public void writeNumber(int v) throws IOException {
        String s = Integer.toString(v);
        this.buffer.append(s);
    }

    @Override
    public void writeNumber(long v) throws IOException {
        String s = Long.toString(v);
        this.buffer.append(s);
    }

    @Override
    public void writeNumber(BigInteger v) throws IOException {
        String s = v.toString();
        this.buffer.append(s);
    }

    @Override
    public void writeNumber(double v) throws IOException {
        String s = Double.toString(v);
        this.buffer.append(s);
    }

    @Override
    public void writeNumber(float v) throws IOException {
        String s = Float.toString(v);
        this.buffer.append(s);
    }

    @Override
    public void writeNumber(BigDecimal v) throws IOException {
        String s = v.toString();
        this.buffer.append(s);
    }

    @Override
    public void writeNumber(String encodedValue) throws IOException {
        this.buffer.append(encodedValue);
    }

    @Override
    public void writeBoolean(boolean state) throws IOException {
        String s = Boolean.toString(state);
        this.buffer.append(s);
    }

    @Override
    public void writeNull() throws IOException {
        this.buffer.append("null");
    }

    @Override
    public void writeObject(Object pojo) throws IOException {
        this.buffer.append(pojo.toString());
    }

    @Override
    public void writeTree(TreeNode rootNode) throws IOException {
        this.buffer.append(rootNode.toString());
    }

    @Override
    public JsonStreamContext getOutputContext() {
        throw new NoSuchMethodError("Not implemented");
    }

    @Override
    public void flush() throws IOException {
        throw new NoSuchMethodError("Not implemented");
    }

    @Override
    public boolean isClosed() {
        throw new NoSuchMethodError("Not implemented");
    }

    @Override
    public void close() throws IOException {
        throw new NoSuchMethodError("Not implemented");
    }
}
