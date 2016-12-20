package gov.ca.cwds.rest.filters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.compress.utils.IOUtils;
import org.bouncycastle.util.io.TeeOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.logging.AuditLogger;
import gov.ca.cwds.rest.api.ApiException;

/**
 * 
 * 
 * @author CWDS API Team
 */
@Provider
public class RequestResponseLoggingFilter implements Filter {
  private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

  private AuditLogger auditLogger;

  /**
   * Constructor
   * 
   * @param auditLogger The audit logger
   */
  @Inject
  public RequestResponseLoggingFilter(AuditLogger auditLogger) {
    this.auditLogger = auditLogger;
  }


  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    String uniqueId;
    if (request instanceof HttpServletRequest) {

      HttpServletRequest httpServletRequest = (HttpServletRequest) request;


      uniqueId = auditLogger.buildMDC(httpServletRequest.getRemoteAddr(), "STUBBED_USER",
          httpServletRequest.getSession().getId(), Thread.currentThread().getName());

      RequestResponseLoggingHttpServletRequest wrappedRequest =
          new RequestResponseLoggingHttpServletRequest(httpServletRequest);

      auditLogger.audit(httpServletRequest.toString());
      auditLogger.audit(requestContent(wrappedRequest));

      final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
      RequestResponseLoggingHttpServletResponseWrapper wrappedResponse =
          new RequestResponseLoggingHttpServletResponseWrapper(httpServletResponse);
      try {
        chain.doFilter(wrappedRequest, wrappedResponse);
        StringBuilder reponseStringBuilder = new StringBuilder();
        reponseStringBuilder.append(wrappedResponse.toString())
            .append(wrappedResponse.getContent());
        auditLogger
            .audit(reponseStringBuilder.toString().replaceAll("\n", " ").replaceAll("\r", ""));
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
        throw new ApiException("Unable to handle request:" + uniqueId, e);
      } finally {
        auditLogger.teardownMDC();
      }
    }
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void destroy() {}

  private String requestContent(HttpServletRequest request) throws IOException {
    String headerName;
    StringBuilder sb = new StringBuilder();
    Enumeration<String> headerNames = request.getHeaderNames();
    if (headerNames != null) {
      while (headerNames.hasMoreElements()) {
        headerName = headerNames.nextElement();
        sb.append(headerName).append(": ").append(request.getHeader(headerName));
      }
    }
    InputStream bodyInputStream = request.getInputStream();
    sb.append(new String(IOUtils.toByteArray(bodyInputStream)));

    return sb.toString().replace("\n", " ");
  }

  private class RequestResponseLoggingHttpServletRequest extends HttpServletRequestWrapper {
    private final byte[] body;
    private final HttpServletRequest wrappedRequest;

    public RequestResponseLoggingHttpServletRequest(HttpServletRequest request) throws IOException {
      super(request);
      body = IOUtils.toByteArray(request.getInputStream());
      wrappedRequest = request;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return wrappedRequest.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletRequestWrapper#getInputStream()
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
      final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
      return new ServletInputStream() {

        @Override
        public int read() throws IOException {
          return byteArrayInputStream.read();
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
        public void setReadListener(ReadListener arg0) {}
      };
    }
  }

  private class RequestResponseLoggingHttpServletResponseWrapper
      extends HttpServletResponseWrapper {

    private TeeServletOutputStream teeStream;

    private PrintWriter teeWriter;

    private ByteArrayOutputStream bos;

    private HttpServletResponse wrappedResponse;


    public RequestResponseLoggingHttpServletResponseWrapper(HttpServletResponse response) {
      super(response);
      wrappedResponse = response;
    }

    public String getContent() throws IOException {
      return bos == null ? "" : bos.toString();
    }

    @Override
    public PrintWriter getWriter() throws IOException {

      if (this.teeWriter == null) {
        this.teeWriter = new PrintWriter(new OutputStreamWriter(getOutputStream()));
      }
      return this.teeWriter;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {

      if (teeStream == null) {
        bos = new ByteArrayOutputStream();
        teeStream = new TeeServletOutputStream(getResponse().getOutputStream(), bos);
      }
      return teeStream;
    }

    @Override
    public void flushBuffer() throws IOException {
      if (teeStream != null) {
        teeStream.flush();
      }
      if (this.teeWriter != null) {
        this.teeWriter.flush();
      }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return wrappedResponse.toString();
    }


    private class TeeServletOutputStream extends ServletOutputStream {

      private final TeeOutputStream targetStream;

      public TeeServletOutputStream(OutputStream one, OutputStream two) {
        targetStream = new TeeOutputStream(one, two);
      }

      @Override
      public void write(int arg0) throws IOException {
        this.targetStream.write(arg0);
      }

      @Override
      public void flush() throws IOException {
        super.flush();
        this.targetStream.flush();
      }

      @Override
      public void close() throws IOException {
        super.close();
        this.targetStream.close();
      }

      @Override
      public boolean isReady() {
        return false;
      }

      @Override
      public void setWriteListener(WriteListener writeListener) {}
    }
  }
}
