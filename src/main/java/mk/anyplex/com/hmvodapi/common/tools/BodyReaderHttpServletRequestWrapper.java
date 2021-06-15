package mk.anyplex.com.hmvodapi.common.tools;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * HttpServletRequest包装类
 * @author hcw
 * @date 2019-08-22
 *
 */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final byte[] body;

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        String bodyString = HttpHelper.getBodyString(request);
        body = bodyString.getBytes(Charset.forName("UTF-8"));
    }

    /**
     * u should override getInputStream
     * ServletInputStream extends InputStream
     * ByteArrayInputStream  is defined in the getInputStream
     * * to ensure that each call reads from pos=0. The definition is invalid outside
     * */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);

        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }
}
