package top.flyeric.auth.infrastructure.config.security.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAKeyPairUtil {

    private static final String PUBLIC_KEY_FILE = "public.key";
    private static final String PRIVATE_KEY_FILE = "private.key";

    public static PublicKey loadPublicKey() throws Exception {
        Resource pubkeyResource = new ClassPathResource(PUBLIC_KEY_FILE);
        String publicKeyContent = pubkeyResource.getContentAsString(Charset.defaultCharset());

        publicKeyContent = publicKeyContent.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public static PrivateKey loadPrivateKey() throws Exception {
        Resource privateKeyResource = new ClassPathResource(PRIVATE_KEY_FILE);
        String privateKeyContent = privateKeyResource.getContentAsString(Charset.defaultCharset());
        privateKeyContent = privateKeyContent.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public static KeyPair loadKeyPair() throws Exception {
        PublicKey publicKey = loadPublicKey();
        PrivateKey privateKey = loadPrivateKey();
        return new KeyPair(publicKey, privateKey);
    }

    public static void main(String[] args) throws Exception {
        loadKeyPair();
    }
}

