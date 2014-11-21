/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lidroid.xutils.http.client.util;

import android.text.TextUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeaderValueParser;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.ParserCursor;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.*;

/**
 * A collection of utilities for encoding URLs.
 *
 * @since 4.0
 */
public class URLEncodedUtils {

    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final String PARAMETER_SEPARATOR = "&";
    private static final String NAME_VALUE_SEPARATOR = "=";

    /**
     * Returns true if the entity's Content-Type header is
     * <code>application/x-www-form-urlencoded</code>.
     */
    public static boolean isEncoded(final HttpEntity entity) {
        Header h = entity.getContentType();
        if (h != null) {
            HeaderElement[] elems = h.getElements();
            if (elems.length > 0) {
                String contentType = elems[0].getName();
                return contentType.equalsIgnoreCase(CONTENT_TYPE);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Returns a list of {@link org.apache.http.NameValuePair NameValuePairs} as built from the
     * URI's query portion. For example, a URI of
     * http://example.org/path/to/file?a=1&b=2&c=3 would return a list of three
     * NameValuePairs, one for a=1, one for b=2, and one for c=3.
     * <p/>
     * This is typically useful while parsing an HTTP PUT.
     *
     * @param uri uri to parse
     */
    public static List<NameValuePair> parse(final URI uri) {
        final String query = uri.getRawQuery();
        if (!TextUtils.isEmpty(query)) {
            List<NameValuePair> result = new ArrayList<NameValuePair>();
            Scanner scanner = new Scanner(query);
            parse(result, scanner);
            return result;
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Adds all parameters within the Scanner to the list of <code>parameters</code>.
     * For example,a scanner containing the string <code>a=1&b=2&c=3</code> would
     * add the {@link org.apache.http.NameValuePair NameValuePairs} a=1, b=2, and c=3 to the
     * list of parameters.
     *
     * @param parameters List to add parameters to.
     * @param scanner    Input that contains the parameters to parse.
     */
    public static void parse(
            final List<NameValuePair> parameters,
            final Scanner scanner) {
        scanner.useDelimiter(PARAMETER_SEPARATOR);
        while (scanner.hasNext()) {
            String name = null;
            String value = null;
            String token = scanner.next();
            int i = token.indexOf(NAME_VALUE_SEPARATOR);
            if (i != -1) {
                name = token.substring(0, i).trim();
                value = token.substring(i + 1).trim();
            } else {
                name = token.trim();
            }
            parameters.add(new BasicNameValuePair(name, value));
        }
    }

    private static final char[] DELIM = new char[]{'&'};

    /**
     * Returns a list of {@link org.apache.http.NameValuePair NameValuePairs} as parsed.
     *
     * @param s text to parse.
     * @since 4.2
     */
    public static List<NameValuePair> parse(final String s) {
        if (s == null) {
            return Collections.emptyList();
        }
        BasicHeaderValueParser parser = BasicHeaderValueParser.DEFAULT;
        CharArrayBuffer buffer = new CharArrayBuffer(s.length());
        buffer.append(s);
        ParserCursor cursor = new ParserCursor(0, buffer.length());
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        while (!cursor.atEnd()) {
            NameValuePair nvp = parser.parseNameValuePair(buffer, cursor, DELIM);
            if (nvp.getName().length() > 0) {
                list.add(new BasicNameValuePair(nvp.getName(), nvp.getValue()));
            }
        }
        return list;
    }

    /**
     * Returns a String that is suitable for use as an <code>application/x-www-form-urlencoded</code>
     * list of parameters in an HTTP PUT or HTTP POST.
     *
     * @param parameters The parameters to include.
     * @param charset    The encoding to use.
     */
    public static String format(
            final List<? extends NameValuePair> parameters,
            final String charset) {
        final StringBuilder result = new StringBuilder();
        for (final NameValuePair parameter : parameters) {
            final String encodedName = encodeFormFields(parameter.getName(), charset);
            final String encodedValue = encodeFormFields(parameter.getValue(), charset);
            if (result.length() > 0) {
                result.append(PARAMETER_SEPARATOR);
            }
            result.append(encodedName);
            if (encodedValue != null) {
                result.append(NAME_VALUE_SEPARATOR);
                result.append(encodedValue);
            }
        }
        return result.toString();
    }

    /**
     * Returns a String that is suitable for use as an <code>application/x-www-form-urlencoded</code>
     * list of parameters in an HTTP PUT or HTTP POST.
     *
     * @param parameters The parameters to include.
     * @param charset    The encoding to use.
     * @since 4.2
     */
    public static String format(
            final Iterable<? extends NameValuePair> parameters,
            final Charset charset) {
        final StringBuilder result = new StringBuilder();
        for (final NameValuePair parameter : parameters) {
            final String encodedName = encodeFormFields(parameter.getName(), charset);
            final String encodedValue = encodeFormFields(parameter.getValue(), charset);
            if (result.length() > 0) {
                result.append(PARAMETER_SEPARATOR);
            }
            result.append(encodedName);
            if (encodedValue != null) {
                result.append(NAME_VALUE_SEPARATOR);
                result.append(encodedValue);
            }
        }
        return result.toString();
    }

    /**
     * Unreserved characters, i.e. alphanumeric, plus: {@code _ - ! . ~ ' ( ) *}
     * <p/>
     * This list is the same as the {@code unreserved} list in
     * <a href="http://www.ietf.org/rfc/rfc2396.txt">RFC 2396</a>
     */
    private static final BitSet UNRESERVED = new BitSet(256);
    /**
     * Punctuation characters: , ; : $ & + =
     * <p/>
     * These are the additional characters allowed by userinfo.
     */
    private static final BitSet PUNCT = new BitSet(256);
    /**
     * Characters which are safe to use in userinfo, i.e. {@link #UNRESERVED} plus {@link #PUNCT}uation
     */
    private static final BitSet USERINFO = new BitSet(256);
    /**
     * Characters which are safe to use in a path, i.e. {@link #UNRESERVED} plus {@link #PUNCT}uation plus / @
     */
    private static final BitSet PATHSAFE = new BitSet(256);
    /**
     * Characters which are safe to use in a fragment, i.e. {@link #RESERVED} plus {@link #UNRESERVED}
     */
    private static final BitSet FRAGMENT = new BitSet(256);

    /**
     * Reserved characters, i.e. {@code ;/?:@&=+$,[]}
     * <p/>
     * This list is the same as the {@code reserved} list in
     * <a href="http://www.ietf.org/rfc/rfc2396.txt">RFC 2396</a>
     * as augmented by
     * <a href="http://www.ietf.org/rfc/rfc2732.txt">RFC 2732</a>
     */
    private static final BitSet RESERVED = new BitSet(256);


    /**
     * Safe characters for x-www-form-urlencoded data, as per java.net.URLEncoder and browser behaviour,
     * i.e. alphanumeric plus {@code "-", "_", ".", "*"}
     */
    private static final BitSet URLENCODER = new BitSet(256);

    static {
        // unreserved chars
        // alpha characters
        for (int i = 'a'; i <= 'z'; i++) {
            UNRESERVED.set(i);
        }
        for (int i = 'A'; i <= 'Z'; i++) {
            UNRESERVED.set(i);
        }
        // numeric characters
        for (int i = '0'; i <= '9'; i++) {
            UNRESERVED.set(i);
        }
        UNRESERVED.set('_'); // these are the charactes of the "mark" list
        UNRESERVED.set('-');
        UNRESERVED.set('.');
        UNRESERVED.set('*');
        URLENCODER.or(UNRESERVED); // skip remaining unreserved characters
        UNRESERVED.set('!');
        UNRESERVED.set('~');
        UNRESERVED.set('\'');
        UNRESERVED.set('(');
        UNRESERVED.set(')');
        // punct chars
        PUNCT.set(',');
        PUNCT.set(';');
        PUNCT.set(':');
        PUNCT.set('$');
        PUNCT.set('&');
        PUNCT.set('+');
        PUNCT.set('=');
        // Safe for userinfo
        USERINFO.or(UNRESERVED);
        USERINFO.or(PUNCT);

        // URL path safe
        PATHSAFE.or(UNRESERVED);
        PATHSAFE.set('/'); // segment separator
        PATHSAFE.set(';'); // param separator
        PATHSAFE.set(':'); // rest as per list in 2396, i.e. : @ & = + $ ,
        PATHSAFE.set('@');
        PATHSAFE.set('&');
        PATHSAFE.set('=');
        PATHSAFE.set('+');
        PATHSAFE.set('$');
        PATHSAFE.set(',');

        RESERVED.set(';');
        RESERVED.set('/');
        RESERVED.set('?');
        RESERVED.set(':');
        RESERVED.set('@');
        RESERVED.set('&');
        RESERVED.set('=');
        RESERVED.set('+');
        RESERVED.set('$');
        RESERVED.set(',');
        RESERVED.set('['); // added by RFC 2732
        RESERVED.set(']'); // added by RFC 2732

        FRAGMENT.or(RESERVED);
        FRAGMENT.or(UNRESERVED);
    }

    private static final int RADIX = 16;

    private static String urlencode(
            final String content,
            final Charset charset,
            final BitSet safechars,
            final boolean blankAsPlus) {
        if (content == null) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        ByteBuffer bb = charset.encode(content);
        while (bb.hasRemaining()) {
            int b = bb.get() & 0xff;
            if (safechars.get(b)) {
                buf.append((char) b);
            } else if (blankAsPlus && b == ' ') {
                buf.append('+');
            } else {
                buf.append("%");
                char hex1 = Character.toUpperCase(Character.forDigit((b >> 4) & 0xF, RADIX));
                char hex2 = Character.toUpperCase(Character.forDigit(b & 0xF, RADIX));
                buf.append(hex1);
                buf.append(hex2);
            }
        }
        return buf.toString();
    }

    /**
     * Decode/unescape a portion of a URL, to use with the query part ensure {@code plusAsBlank} is true.
     *
     * @param content     the portion to decode
     * @param charset     the charset to use
     * @param plusAsBlank if {@code true}, then convert '+' to space (e.g. for www-url-form-encoded content), otherwise leave as is.
     * @return encoded string
     */
    private static String urldecode(
            final String content,
            final Charset charset,
            final boolean plusAsBlank) {
        if (content == null) {
            return null;
        }
        ByteBuffer bb = ByteBuffer.allocate(content.length());
        CharBuffer cb = CharBuffer.wrap(content);
        while (cb.hasRemaining()) {
            char c = cb.get();
            if (c == '%' && cb.remaining() >= 2) {
                char uc = cb.get();
                char lc = cb.get();
                int u = Character.digit(uc, 16);
                int l = Character.digit(lc, 16);
                if (u != -1 && l != -1) {
                    bb.put((byte) ((u << 4) + l));
                } else {
                    bb.put((byte) '%');
                    bb.put((byte) uc);
                    bb.put((byte) lc);
                }
            } else if (plusAsBlank && c == '+') {
                bb.put((byte) ' ');
            } else {
                bb.put((byte) c);
            }
        }
        bb.flip();
        return charset.decode(bb).toString();
    }

    /**
     * Decode/unescape www-url-form-encoded content.
     *
     * @param content the content to decode, will decode '+' as space
     * @param charset the charset to use
     * @return encoded string
     */
    private static String decodeFormFields(final String content, final String charset) {
        if (content == null) {
            return null;
        }
        return urldecode(content, charset != null ? Charset.forName(charset) : Charset.forName(HTTP.UTF_8), true);
    }

    /**
     * Decode/unescape www-url-form-encoded content.
     *
     * @param content the content to decode, will decode '+' as space
     * @param charset the charset to use
     * @return encoded string
     */
    private static String decodeFormFields(final String content, final Charset charset) {
        if (content == null) {
            return null;
        }
        return urldecode(content, charset != null ? charset : Charset.forName(HTTP.UTF_8), true);
    }

    /**
     * Encode/escape www-url-form-encoded content.
     * <p/>
     * Uses the {@link #URLENCODER} set of characters, rather than
     * the set; this is for compatibilty with previous
     * releases, URLEncoder.encode() and most browsers.
     *
     * @param content the content to encode, will convert space to '+'
     * @param charset the charset to use
     * @return encoded string
     */
    private static String encodeFormFields(final String content, final String charset) {
        if (content == null) {
            return null;
        }
        return urlencode(content, charset != null ? Charset.forName(charset) :
                Charset.forName(HTTP.UTF_8), URLENCODER, true);
    }

    /**
     * Encode/escape www-url-form-encoded content.
     * <p/>
     * Uses the {@link #URLENCODER} set of characters, rather than
     * the set; this is for compatibilty with previous
     * releases, URLEncoder.encode() and most browsers.
     *
     * @param content the content to encode, will convert space to '+'
     * @param charset the charset to use
     * @return encoded string
     */
    private static String encodeFormFields(final String content, final Charset charset) {
        if (content == null) {
            return null;
        }
        return urlencode(content, charset != null ? charset : Charset.forName(HTTP.UTF_8), URLENCODER, true);
    }

    /**
     * Encode a String using the {@link #USERINFO} set of characters.
     * <p/>
     * Used by URIBuilder to encode the userinfo segment.
     *
     * @param content the string to encode, does not convert space to '+'
     * @param charset the charset to use
     * @return the encoded string
     */
    static String encUserInfo(final String content, final Charset charset) {
        return urlencode(content, charset, USERINFO, false);
    }

    /**
     * Encode a String using the {@link #FRAGMENT} set of characters.
     * <p/>
     * Used by URIBuilder to encode the userinfo segment.
     *
     * @param content the string to encode, does not convert space to '+'
     * @param charset the charset to use
     * @return the encoded string
     */
    static String encFragment(final String content, final Charset charset) {
        return urlencode(content, charset, FRAGMENT, false);
    }

    /**
     * Encode a String using the {@link #PATHSAFE} set of characters.
     * <p/>
     * Used by URIBuilder to encode path segments.
     *
     * @param content the string to encode, does not convert space to '+'
     * @param charset the charset to use
     * @return the encoded string
     */
    static String encPath(final String content, final Charset charset) {
        return urlencode(content, charset, PATHSAFE, false);
    }

}
