package spring.hometeam.utils;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPoint;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Optional;

public class PkiUtils {

    public static Optional<String> extractEmailFromCSR(String csrString) throws Exception {
        PEMParser pemParser = new PEMParser(new StringReader(csrString));
        PKCS10CertificationRequest csr = (PKCS10CertificationRequest) pemParser.readObject();

        X500Name x500Name = csr.getSubject();
        return Optional.ofNullable(x500Name.getRDNs(BCStyle.EmailAddress))
                .filter(rdns -> rdns.length > 0)
                .map(rdns -> rdns[0].getFirst().getValue().toString());
    }

    public static PublicKey extractPublicKeyFromCSR(String csrString) throws Exception {
        PEMParser pemParser = new PEMParser(new StringReader(csrString));
        PKCS10CertificationRequest csr = (PKCS10CertificationRequest) pemParser.readObject();
        pemParser.close();

        SubjectPublicKeyInfo pkInfo = csr.getSubjectPublicKeyInfo();
        X509EncodedKeySpec spec = new X509EncodedKeySpec(pkInfo.getEncoded());
        KeyFactory kf = KeyFactory.getInstance("EC");

        return kf.generatePublic(spec);
    }

    public static String compressPublicKey(PublicKey publicKey) {
        if (!(publicKey instanceof ECPublicKey)) {
            throw new IllegalArgumentException("Public key must be an instance of ECPublicKey");
        }

        ECPublicKey ecPublicKey = (ECPublicKey) publicKey;
        ECPoint point = ecPublicKey.getW();

        // x 좌표 가져 오기
        BigInteger x = point.getAffineX();

        // y 좌표의 패리티 확인 (짝수 또는 홀수)
        BigInteger y = point.getAffineY();
        boolean isYPair = y.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO);

        // 압축된 형식: x 좌표와 y 좌표의 패리티 비트
        return (isYPair ? "02" : "03") + x.toString(16);
    }

    public static String  generateCertificate(String csrString, PrivateKey caPrivateKey) throws Exception {

        PEMParser pemParser = new PEMParser(new StringReader(csrString));
        PKCS10CertificationRequest csr = (PKCS10CertificationRequest) pemParser.readObject();

        X500Name issuerName = new X500Name("CN=Double H");
        BigInteger serialNumber = BigInteger.valueOf(System.currentTimeMillis());
        Date startDate = new Date(System.currentTimeMillis());
        Date endDate = new Date(System.currentTimeMillis() + (365 * 24 * 60 * 60 * 1000)); // 유효기간 1년

        SubjectPublicKeyInfo subjectPublicKeyInfo = csr.getSubjectPublicKeyInfo();

        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        PublicKey csrPublicKey = converter.getPublicKey(subjectPublicKeyInfo);

        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                issuerName,
                serialNumber,
                startDate,
                endDate,
                csr.getSubject(),
                csrPublicKey);

        ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256withECDSA").build(caPrivateKey);

        X509Certificate certificate = new JcaX509CertificateConverter().getCertificate(certBuilder.build(contentSigner));

        StringWriter stringWriter = new StringWriter();
        try (PEMWriter pemWriter = new PEMWriter(stringWriter)) {
            pemWriter.writeObject(certificate);
        }

        return stringWriter.toString();
    }

    public static X509Certificate loadCertificateFromPem(String pemFilePath) throws Exception {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");

        try (InputStream certificateInputStream = new FileInputStream(pemFilePath)) {
            return (X509Certificate) certificateFactory.generateCertificate(certificateInputStream);
        }
    }
}
