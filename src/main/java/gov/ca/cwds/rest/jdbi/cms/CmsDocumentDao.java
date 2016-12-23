package gov.ca.cwds.rest.jdbi.cms;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.bind.DatatypeConverter;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.rest.api.persistence.cms.CmsDocument;
import gov.ca.cwds.rest.api.persistence.cms.CmsDocumentBlobSegment;
import gov.ca.cwds.rest.jdbi.BaseDaoImpl;
import gov.ca.cwds.rest.util.jni.CmsPKCompressor;
import gov.ca.cwds.rest.util.jni.LZWEncoder;

/**
 * Data Access Object (DAO) for legacy CMS documents.
 * 
 * @author CWDS API Team
 */
public class CmsDocumentDao extends BaseDaoImpl<CmsDocument> {

  private static final Logger LOGGER = LoggerFactory.getLogger(CmsDocumentDao.class);

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public CmsDocumentDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * De-compress (inflate) a document by determining the compression type, assembling blob segments,
   * and calling appropriate library.
   * 
   * @param doc LZW or PK archive to decompress
   * @return base64-encoded String of decompressed document
   */
  public String decompressDoc(gov.ca.cwds.rest.api.persistence.cms.CmsDocument doc) {
    String retval = "";

    if (doc.getCompressionMethod().endsWith("01")) {
      LZWEncoder lzw = new LZWEncoder();
      if (!lzw.didLibraryLoad()) {
        LOGGER.warn("LZW compression not enabled!");
      } else {
        retval = decompressLZW(doc);
      }
    } else if (doc.getCompressionMethod().endsWith("02")) {
      retval = decompressPK(doc);
    } else {
      LOGGER.warn("UNSUPPORTED compression method " + doc.getCompressionMethod());
    }

    return retval;
  }

  /**
   * Decompress (inflate) an PKWare-compressed document by assembling blob segments and calling Java
   * PKWare SDK.
   * 
   * <p>
   * The DB2 SQL returns blob segments as hexadecimal.
   * </p>
   * 
   * @param doc PK archive to decompress
   * @return base64-encoded String of decompressed document
   */
  protected String decompressPK(gov.ca.cwds.rest.api.persistence.cms.CmsDocument doc) {
    String retval = "";

    CmsPKCompressor pk = new CmsPKCompressor();
    LZWEncoder lzw = new LZWEncoder();

    try {
      StringBuilder buf = new StringBuilder(doc.getDocLength().intValue() * 2);
      for (CmsDocumentBlobSegment seg : doc.getBlobSegments()) {
        buf.append(seg.getDocBlob().trim());
      }

      final byte[] bytes = pk.decompressHex(buf.toString());
      LOGGER.info("DAO: bytes len=" + bytes.length);
      retval = DatatypeConverter.printBase64Binary(bytes);
    } catch (Exception e) {
      LOGGER.error("ERROR DECOMPRESSING PK! " + e.getMessage());
      throw new RuntimeException(e);
    }

    return retval;
  }

  /**
   * Decompress (inflate) an LZW-compressed document by assembling blob segments and calling native
   * library.
   * 
   * @param doc LZW archive to decompress
   * @return base64-encoded String of decompressed document
   */
  protected String decompressLZW(gov.ca.cwds.rest.api.persistence.cms.CmsDocument doc) {
    String retval = "";
    try {
      File src = File.createTempFile("src", ".lzw");
      src.deleteOnExit();

      File tgt = File.createTempFile("tgt", ".doc");
      tgt.deleteOnExit();

      FileOutputStream fos = new FileOutputStream(src);
      for (CmsDocumentBlobSegment seg : doc.getBlobSegments()) {
        final byte[] bytes = DatatypeConverter.parseHexBinary(seg.getDocBlob().trim());
        fos.write(bytes, 0, bytes.length);
      }
      fos.flush();
      fos.close();

      // DECOMPRESS!
      // TODO: Trap std::exception in shared library and return error code.
      // The LZW library currently returns a blank when decompression fails, for safety, since
      // unhandled C++ exceptions kill the JVM.
      LZWEncoder lzw = new LZWEncoder();
      lzw.fileCopyUncompress(src.getAbsolutePath(), tgt.getAbsolutePath());

      retval =
          DatatypeConverter.printBase64Binary(Files.readAllBytes(Paths.get(tgt.getAbsolutePath())));

      // For security reasons, remove temporary documents immediately.
      // TODO: pass bytes to C++ library instead of file names.
      src.delete();
      tgt.delete();

    } catch (Exception e) {
      LOGGER.error("ERROR DECOMPRESSING LZW! " + e.getMessage());
      throw new RuntimeException(e);
    }

    return retval;
  }

}
