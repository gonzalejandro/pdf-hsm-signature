import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
import org.bouncycastle.cms.CMSSignedDataGenerator;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;


public class CreateSignatureBase implements SignatureInterface {
    private PrivateKey privateKey;
    private Certificate[] certificateChain;
    private String tsaUrl;
    private boolean externalSigning;

    /**
     * TODO
     * @param keyStore
     * @param pin
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws CertificateException
     */
    public CreateSignatureBase(KeyStore keyStore, char[] pin)
            throws KeyStoreException, UnrecoverableKeyException,
            NoSuchAlgorithmException, IOException, CertificateException {


        Enumeration<String> aliases = keyStore.aliases();
        String alias;
        Certificate cert = null;
        while(aliases.hasMoreElements()) {
            alias = aliases.nextElement();
            setPrivateKey((PrivateKey) keyStore.getKey(alias, pin));
            Certificate[] certChain = keyStore.getCertificateChain(alias);
            if (certChain == null) {
                continue;
            }

            setCertificateChain(certChain);
            cert = certChain[0];
            if (cert instanceof X509Certificate) {
                ((X509Certificate) cert).checkValidity();
                SigUtils.checkCertificateUsage((X509Certificate) cert);
            }
            break;
        }


        if (cert == null) {
            throw new IOException("Could not find certificate");
        }
    }

    public final void setCertificateChain(Certificate[] certificateChain) {
        this.certificateChain = certificateChain;
    }

    public final void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public void setTsaUrl(String tsaUrl) {
        this.tsaUrl = tsaUrl;
    }

    @Override
    public byte[] sign(InputStream content) throws IOException {
        try {
            CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
        }
    }
}
