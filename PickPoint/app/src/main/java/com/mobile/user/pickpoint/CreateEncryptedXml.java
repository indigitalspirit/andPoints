package com.mobile.user.pickpoint;

import android.util.Base64;
import android.util.Log;
import android.util.Xml;

import org.spongycastle.util.io.pem.PemObject;
import org.spongycastle.util.io.pem.PemWriter;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.RSAKeyGenParameterSpec;

import javax.crypto.Cipher;

//import java.util.prefs.Preferences;

//import com.mobile.user.mobile.utils.Preferences;


/**
 * Created by user on 25.04.16.
 */
public class CreateEncryptedXml {

    /*_____________________________________GENERATE KEY PAIR ENCRYPTION STARTS_____________________________*/


        private static final String TAG = CreateEncryptedXml.class.getSimpleName();
        private static final int KEY_SIZE = 1024;

        static {
            Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
        }

        public static KeyPair generate() {
            try {
                SecureRandom random = new SecureRandom();
                RSAKeyGenParameterSpec spec = new RSAKeyGenParameterSpec(KEY_SIZE, RSAKeyGenParameterSpec.F4);
                KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "SC");
                generator.initialize(spec, random);
                return generator.generateKeyPair();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }



    public String ConvertPublicKeyToString(KeyPair key) {
        StringWriter publicStringWriter = new StringWriter();
        String pb_key = null;

        try {
            PemWriter pemWriter = new PemWriter(publicStringWriter);
            pemWriter.writeObject(new PemObject("PUBLIC KEY", key.getPublic().getEncoded()));
            pemWriter.flush();
            pemWriter.close();
            pb_key = publicStringWriter.toString();//Preferences.putString(Preferences.RSA_PUBLIC_KEY, publicStringWriter.toString());
        } catch (IOException e) {
            Log.e("RSA", e.getMessage());
            e.printStackTrace();
        }

        return pb_key;
    }



    public String ConvertPrivateKeyToString(KeyPair keyPair) {
        StringWriter privateStringWriter = new StringWriter();
        String pr_key = null;

        try {
            PemWriter pemWriter = new PemWriter(privateStringWriter);
            pemWriter.writeObject(new PemObject("PRIVATE KEY", keyPair.getPrivate().getEncoded()));
            pemWriter.flush();
            pemWriter.close();

            pr_key = privateStringWriter.toString();
        } catch (IOException e) {
            Log.e("RSA", e.getMessage());
            e.printStackTrace();
        }
        return pr_key;
    }


    /*_____________________________________GENERATE KEY PAIR ENCRYPTION ENDS_____________________________*/


    /*_____________________________________TEXT ENCRYPTION STARTS_____________________________*/


    public static byte[] encrypt(Key publicKey, byte[] toBeCiphred) {
        try {
            //Cipher rsaCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding", "SC");
            Cipher rsaCipher = Cipher.getInstance("RSA", "SC");
            rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return rsaCipher.doFinal(toBeCiphred);
        } catch (Exception e) {
            Log.e(TAG, "Error while encrypting data: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public static String encryptWithStoredKey(String text, String privateKey) {
        String strippedKey = Crypto.stripPublicKeyHeaders(privateKey);
        //String strippedKey = Crypto.stripPublicKeyHeaders(Preferences.getString(Preferences.RSA_PUBLIC_KEY));

        return encryptWithKey(strippedKey, text);
    }

    public static String encryptToBase64(Key publicKey, String toBeCiphred) {
        byte[] cyphredText = CreateEncryptedXml.encrypt(publicKey, toBeCiphred.getBytes());
        return Base64.encodeToString(cyphredText, Base64.DEFAULT);
    }

    public static String encryptWithKey(String key, String text) {
        try {
            PublicKey apiPublicKey = Crypto.getRSAPublicKeyFromString(key);
            return encryptToBase64(apiPublicKey, text);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*_____________________________________TEXT ENCRYPTION ENDS_____________________________*/


    public static byte[] decrypt(Key privateKey, byte[] encryptedText) {
        try {
            //Cipher rsaCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding", "SC");
            Cipher rsaCipher = Cipher.getInstance("RSA", "SC");
            rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
            return rsaCipher.doFinal(encryptedText);
        } catch (Exception e) {
            Log.e(TAG, "Error while decrypting data: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static String decryptFromBase64(Key key, String cyphredText) {
        byte[] afterDecrypting = CreateEncryptedXml.decrypt(key, Base64.decode(cyphredText, Base64.DEFAULT));
        return stringify(afterDecrypting);
    }

    public static String decryptWithStoredKey(String text, String prKey) {
        try {
            //String strippedKey = Crypto.stripPrivateKeyHeaders(Preferences.getString(Preferences.RSA_PRIVATE_KEY));
            String strippedKey = Crypto.stripPrivateKeyHeaders(prKey);
            PrivateKey privateKey = Crypto.getRSAPrivateKeyFromString(strippedKey);
            return decryptFromBase64(privateKey, text);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String stringify(byte[] bytes) {
        return stringify(new String(bytes));
    }

    private static String stringify(String str) {
        String aux = "";
        for (int i = 0; i < str.length(); i++) {
            aux += str.charAt(i);
        }
        return aux;
    }

    /*_____________________________________TEXT DECRYPTION ENDS_____________________________*/

    /*_____________________________________XML SERIALAIZE START_____________________________*/
    protected String GenerateXMLString(String text, String PB_key) throws IllegalArgumentException, IllegalStateException, IOException
    {

      //  Key[] keys = GenerateKeyPair();

       // String publicKey = EncodeKeyToString(keys[0], true);
       // String privateKey = EncodeKeyToString(keys[1], false);

        //String encodedText = EncryptText(keys[1], text);
        //String decodedText = DecryptText(encodedText, keys[0], publicKey);

        // Log.i("DECRIPTEDKEY", decodedText);

        KeyPair keys = generate();
        //writePrivateKeyToPreferences(keys);
        //writePublicKeyToPreferences(keys);

        //String privateKey = stripPrivateKeyHeaders(Preferences.getString(Preferences.RSA_PRIVATE_KEY));
        //String publicKey = stripPublicKeyHeaders(Preferences.getString(Preferences.RSA_PUBLIC_KEY));

        //String privateKey = ConvertPrivateKeyToString(keys);
        //privateKey = privateKey.trim();

        ///String publicKey = ConvertPublicKeyToString(keys);
        String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyH1MfvponYs2JBPePSPC\n" +
                "9CamTz0hh+4439Ie0PYowNANps2NdU1z+6up6oXQCQ9auODgVV9v3/rcObQLNmjH\n" +
                "CUdgG80xbPocs5UmHGhrEBzDYN7ByBcMmSQRcwaXb5JLBno2eEVqhA0L/wBhJf7h\n" +
                "3IwHXxX8cR+wegwVLTjTzo4zUyFjS3ZRilYjuowPzopWF9DOVVArE2MKbMHCHpI8\n" +
                "/ufvruCudJAfdDvlUxxNyfHHcr3oeWzmzmekf2MDjIVzfWNl2udk/AofECQNuijy\n" +
                "Cz77h/4+fWCI5t2Qpx2I4qT1B8JYZOdRK0DpCiQOIsP8lqrE9tPGv4wg/yUu5KyV\n" +
                "mwIDAQAB\n" +
                "-----END PUBLIC KEY-----";

        //publicKey = Crypto.stripPublicKeyHeaders(publicKey);

        String privateKey = "-----BEGIN PRIVATE KEY-----\n" +
                "MIIBPQIBAAJBALqbHeRLCyOdykC5SDLqI49ArYGYG1mqaH9/GnWjGavZM02fos4l\n" +
                "c2w6tCchcUBNtJvGqKwhC5JEnx3RYoSX2ucCAwEAAQJBAKn6O+tFFDt4MtBsNcDz\n" +
                "GDsYDjQbCubNW+yvKbn4PJ0UZoEebwmvH1ouKaUuacJcsiQkKzTHleu4krYGUGO1\n" +
                "mEECIQD0dUhj71vb1rN1pmTOhQOGB9GN1mygcxaIFOWW8znLRwIhAMNqlfLijUs6\n" +
                "rY+h1pJa/3Fh1HTSOCCCCWA0NRFnMANhAiEAwddKGqxPO6goz26s2rHQlHQYr47K\n" +
                "vgPkZu2jDCo7trsCIQC/PSfRsnSkEqCX18GtKPCjfSH10WSsK5YRWAY3KcyLAQIh\n" +
                "AL70wdUu5jMm2ex5cZGkZLRB50yE6rBiHCd5W1WdTFoe\n" +
                "-----END PRIVATE KEY-----";

       // privateKey = Crypto.stripPrivateKeyHeaders(privateKey);

        String encryptedMessage = CreateEncryptedXml.encryptWithStoredKey(text, PB_key);
        //String encryptedMessage = CreateEncryptedXml.encryptWithStoredKey(text, PB_key);
        //String decryptedMessage = CreateEncryptedXml.decryptWithStoredKey(encryptedMessage, privateKey);


        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        xmlSerializer.setOutput(writer);

        //Start Document
        xmlSerializer.startDocument("UTF-8", true);
        xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

        //________________________________________Open Tag <message>
        xmlSerializer.startTag("", "message");

       // XmlSerializer tempXml = Xml.newSerializer();
       // StringWriter tempWriter =  new StringWriter();



        //________________________________________TEXT Tag
        xmlSerializer.startTag("", "text");
        xmlSerializer.text(encryptedMessage);
        xmlSerializer.endTag("", "text");

       // xmlSerializer.startTag("", "decrypted");
     //   xmlSerializer.text(decryptedMessage);
       // xmlSerializer.endTag("", "decrypted");
        //________________________________________

        //xmlSerializer.startTag("", "dec_text");
        //xmlSerializer.text(decodedText);
        //xmlSerializer.endTag("", "dec_text");

        //________________________________________KEY Tag
        xmlSerializer.startTag("", "pbkey");
        xmlSerializer.text(publicKey);
        //xmlSerializer.text(PB_key);
        xmlSerializer.endTag("", "pbkey");
        //________________________________________

        //________________________________________PR KEY Tag
        xmlSerializer.startTag("", "prkey");
        xmlSerializer.text(privateKey);
        xmlSerializer.endTag("", "prkey");
        //________________________________________


        //________________________________________TOUCH Tag
        xmlSerializer.startTag("", "touch");
        xmlSerializer.text("89504e470d0a1a0a0000000d49484452000002800000019008060000003ef3d12500000006624b474400ff00ff00ffa0bda793000000097048597300000b1300000b1301009a9c180000000774494d4507e00217072319b2134440000009054944415478daeddddd52db3a148051d3f1fbbfb27a51e830408293d8d2fe59eb9ac3f4c892f5590eb06d0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000040036f860000c21bf672042000083f7b3a0210001ac79f7d1d1305001ac69fbd1d9304001ac69ffd9d43fe180200805e3c2100401ce3c4ef658fe72627800000021000000108005c6d1802042000000210000001080080000400400002402f7e072002100000010800200001001080000014b41b0216b8f7dbee7d70190004204dc2efebd7084100b88857c0448abf57be1e001080248e3f110800029086f12702014000d230fe000001889004000420a20d00108000000840000004200080000400400002002000e17767ff04b0bf0b0c0002100000010800c021bb218096eebda6f7ca1d62ad491080c0e59bcc108200b579054c7422646efcbdf2f50008402071fc8940000108348c3f110839d62808400068ce47671080d0dc08f67d001080080e004000020020000180437cfe0f0108008000e45a3eff070002105ee255060008400273fae79a0020000100108000c0674eea1180f089cfff018000041ee054010001482a4eff5c13000420f000a77f60ad8200c44d11a00127f5084010cf00200081e7385500108000c08b9cd6230081b09cfe81b58a00040040000255395100108000000840f8c7879901d6dd339dd6230029c1cd0c0004207080935800042000000210005672528f00841ff8fc1f0008403ccde23a001e9a1180804d050001080080000480ca9cd42300a1319fff036b150420f012a70a000210000001080080000496f09922b0564100329dcf8bb99e80b58a00c4532d00ee930840c0a602800004004000026bf94c119c6f58ab0840000004209e6c3dd502ee91ee930840603e9b0a80000482f313c0003c6d370400cba3dd092c4798270840426f66187b1ebb5ec3060fcce41530409c5817f7dce2e10001889b1b148c3f11080840407c378c3f11080840ca6e6e187b000420cd398132f6225dec03021000000108007938c145000280f80301081f7c06cdd883f80301889b1b00200001c00332084080a0de827d1f00010889395d000f152000018a6ed8367c0f682000b14942a3f96d5d603e2000f1840b8d366f9b3d30c56e08407c33250287f0c30301021058197f36169b39e60b8d79050c0020008140bcfa857e9cfe2100291b226e70002000818ba35b7c43aeb50a0210000001089539fd83de6b1504206e70008000041ee7f40f0001881009ca892bf45dabee8f0840c0a6028000a4c6132e73c75bfc81b50a0290a5dce000400002ef9cb682b50a0210000001083cc62b77b056e19bdd10e006778a613ca005af7fb121e36687f50dee89d62af978050ce20ff0408c0004003cac210001a037a77f08400000f272f48ca75e6b1c58731fb43e59c60920e2cf3504a0194f1f88076b9de7d78171751fb42649c90920d8c4b83d66e384af0110800005835904f208a77f988494d900b1e6cd7de36b2e982324e10410e0bc0ddf831320000180ff9cfe210009cd2906e6bdf503084000f18739010210c0460f4779fd8b0004107f9817200071e3e37a4e2000d8b66ddb76430053824b587bd8011080b48c20e3b02e3e5c07f1c7bab961fd118e57c0503f8c6d3e000840a63ef9b276acc5dffcb137e6e686f940785e01632334f600084040f095e5f48f55730342f10a18b0c18b3f400002c94384f9c41fe6050210dcf88c75a3e836fe8000c40609e20fcc0d042080071b000108903efe9cf0606e2000b15162a331ee00021010efc6054000d284d311cc6f000108008000240aafca30a78f73fa0708408046c41f2000c126698c0272a20d200041a420da0104204204f359fc71dd75365f1080b88102000210ba73ea9a6b5c3dd47888355f1080002002c51f0290bc9c44d518739b4ebc10a0dfb5375748673704d82c11d6e63337e7c0304f108040a448013cdcc253bc02061b93b036ae8000c4a609000840b8cf898931f7200320000100108044e1d4243fa77f7ef133800004004000f2333f3109e6328000147f602e8b3f40000200200049cf07e6c13c06108000000290aa9cfe01000210c0830c2000c1a6490e7e9a1d400002002000f9ccef4b030004201409710010804ce1f44ffc194b000108e0810640001285d312d7000004a0f07889d312001080c002421c0001c8375e3b82900610805024c4450b00029069840700084040840390d56e084af1da11d6ad15eb0948c309a00d0d98bb4ead554000129ad30ad7010f6c8000c4660258b7800004400402029012bc7604000108c0059c02020290109cfe018000040040005295d33fcc4d0001080080000458c3292080000444200002101081fe5f000420503602c5138000049a86a07f3b80000444a07f33c02dbb21000a45e048f46fe51cc33883000444e06f5120fcea87dfd7af31ee200081462148dff8fbfaf5e604b849ba219a0fae09345c57d613bcf34320007479a81a86100460356f8bff7b5c13009a6c50d47842360f5c13a8b88eac2fb8c109a0a87723744d00b031d1e489d9b5774da0cadab1f78145008000b4f7c13d5e010300084000000420000065f81c040059f8eb3a701227800000cd78120220137f0b1804200022d07e0716040022d05e071605002d43d01e07000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000040367f01fd67e0dda0cea3d90000000049454e44ae426082");
        xmlSerializer.endTag("", "touch");
        //________________________________________

        //________________________________________GPS Tag
        xmlSerializer.startTag("", "gps");
        xmlSerializer.startTag("", "X");
        xmlSerializer.text("coordinateX");
        xmlSerializer.endTag("", "X");

        xmlSerializer.startTag("", "Y");
        xmlSerializer.text("coordinateY");
        xmlSerializer.endTag("", "Y");

        xmlSerializer.endTag("", "gps");
        //________________________________________

        //end tag <message>
        xmlSerializer.endTag("", "message");
        //________________________________________Open Tag <message> Ends
        xmlSerializer.endDocument();

        String strXml = writer.toString();

        System.out.print(strXml);
        return strXml;
        //System.out.println();

        //return writer.toString().replace("\\s", "");
    }




     /*_____________________________________XML SERIALAIZE END_____________________________*/
















      /*
      public Key[] GenerateKeyPair() {
       //JAVA STANDART RSA
        // Generate key pair for 1024-bit RSA encryption and decryption
        Key publicKey = null;
        Key privateKey = null;

        byte[] keyByte = null;

        Key keys[]= new Key[2];

        String finalPublicKey=null;

        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024);
            KeyPair kp = kpg.genKeyPair();
            publicKey = kp.getPublic();
            privateKey = kp.getPrivate();

            keys[0] = publicKey;
            keys[1] = privateKey;
            // finalPublicKey = Base64.encodeToString(publicKey, Base64.DEFAULT);
            //keyByte = publicKey.getEncoded();
            //finalPublicKey = Base64.encodeToString(keyByte, Base64.DEFAULT);;//publicKey.toString();
        } catch (Exception e) {
            Log.e("Crypto", "RSA key pair error");
        }

        return  keys; // array (public key+ private
      }
        */
    /*
    public void writePublicKeyToPreferences(KeyPair key) {
        StringWriter publicStringWriter = new StringWriter();
        try {
            PemWriter pemWriter = new PemWriter(publicStringWriter);
            pemWriter.writeObject(new PemObject("PUBLIC KEY", key.getPublic().getEncoded()));
            pemWriter.flush();
            pemWriter.close();
            Preferences.putString(Preferences.RSA_PUBLIC_KEY, publicStringWriter.toString());
        } catch (IOException e) {
            Log.e("RSA", e.getMessage());
            e.printStackTrace();
        }
    }
    */


    /*
    public void writePublicKeyToPreferences(KeyPair key) {
        StringWriter publicStringWriter = new StringWriter();
        try {
            PemWriter pemWriter = new PemWriter(publicStringWriter);
            pemWriter.writeObject(new PemObject("PUBLIC KEY", key.getPublic().getEncoded()));
            pemWriter.flush();
            pemWriter.close();
            Preferences.putString(Preferences.RSA_PUBLIC_KEY, publicStringWriter.toString());
        } catch (IOException e) {
            Log.e("RSA", e.getMessage());
            e.printStackTrace();
        }
    }
    */

    /*
    public void writePrivateKeyToPreferences(KeyPair keyPair) {
        StringWriter privateStringWriter = new StringWriter();
        try {
            PemWriter pemWriter = new PemWriter(privateStringWriter);
            pemWriter.writeObject(new PemObject("PRIVATE KEY", keyPair.getPrivate().getEncoded()));
            pemWriter.flush();
            pemWriter.close();
            Preferences.putString(Preferences.RSA_PRIVATE_KEY, privateStringWriter.toString());
           // String pr_key = privateStringWriter.toString();
        } catch (IOException e) {
            Log.e("RSA", e.getMessage());
            e.printStackTrace();
        }

    }
    */




     /*
    public static String stripPublicKeyHeaders(String key) {
        //strip the headers from the key string
        StringBuilder strippedKey = new StringBuilder();
        String lines[] = key.split("\n");
        for (String line : lines) {
            if (!line.contains("BEGIN PUBLIC KEY") && !line.contains("END PUBLIC KEY") && !Strings.isNullOrEmpty(line.trim())) {
                strippedKey.append(line.trim());
            }
        }
        return strippedKey.toString().trim();
    }

    public static String stripPrivateKeyHeaders(String key) {
        StringBuilder strippedKey = new StringBuilder();
        String lines[] = key.split("\n");
        for (String line : lines) {
            if (!line.contains("BEGIN PRIVATE KEY") && !line.contains("END PRIVATE KEY") && !Strings.isNullOrEmpty(line.trim())) {
                strippedKey.append(line.trim());
            }
        }
        return strippedKey.toString().trim();
    }
    */
    /*
    public static PublicKey getRSAPublicKeyFromString(String publicKeyPEM) throws Exception {
        publicKeyPEM = stripPublicKeyHeaders(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "SC");
        //byte[] publicKeyBytes = Base64.decode(publicKeyPEM.getBytes("UTF-8"));
        byte[] publicKeyBytes = Base64.decode(publicKeyPEM.getBytes("UTF-8"), Base64.DEFAULT);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyBytes);
        return keyFactory.generatePublic(x509KeySpec);
    }

    public static PrivateKey getRSAPrivateKeyFromString(String privateKeyPEM) throws Exception {
        privateKeyPEM = stripPrivateKeyHeaders(privateKeyPEM);
        KeyFactory fact = KeyFactory.getInstance("RSA", "SC");
       // byte[] clear = Base64.decode(privateKeyPEM);
        byte[] clear = Base64.decode(privateKeyPEM, Base64.DEFAULT);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(clear);
        PrivateKey priv = fact.generatePrivate(keySpec);
        Arrays.fill(clear, (byte) 0);
        return priv;
    }
    */




 /*_____________________________________GENERATE KEY PAIR ENCRYPTION STARTS_____________________________*/
    /*
    public String EncodeKeyToString(Key key, Boolean type) {

        byte[] keyByte = null;

        String finalPublicKey=null;

        try {
            // finalPublicKey = Base64.encodeToString(publicKey, Base64.DEFAULT);
            keyByte = key.getEncoded();
            finalPublicKey = Base64.encodeToString(keyByte, Base64.DEFAULT);;//publicKey.toString();
            if (type) {
                finalPublicKey = "-----BEGIN RSA PUBLIC KEY-----" + finalPublicKey + "-----END RSA PUBLIC KEY-----";
            } else {
                finalPublicKey = "-----BEGIN RSA PRIVATE KEY-----" + finalPublicKey + "-----END RSA PRIVATE KEY-----";
            }
        } catch (Exception e) {
            Log.e("Crypto", "RSA key pair error");
        }
        Log.i("PUBKEY", finalPublicKey);
        return  finalPublicKey; // array (public key+ private

    }
    */
    /*_____________________________________GENERATE KEY PAIR ENCRYPTION ENDS_____________________________*/




    /*
    public String EncryptText(Key privateKey, String text) {

        //String publicKey = null;
        String finalHelloSrt = null;

        // Encode the original data with RSA private key
        byte[] encodedBytes = null;

        try {
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, privateKey);
            encodedBytes = c.doFinal(text.getBytes());

           // final byte[] finalEncodedBytes = encodedBytes;
            finalHelloSrt = Base64.encodeToString(encodedBytes, Base64.DEFAULT);
        } catch (Exception e) {
            //Log.e("Crypto", "RSA encryption error");
            e.printStackTrace();
        }
        return finalHelloSrt;

    }
    */


    /*_____________________________________TEXT DECRYPTION STARTS_____________________________*/
    /*
    protected String DecryptText(String Str, Key publicKey, String pbkey) {

        String finalDecodedStr = null;
        byte[] encodedBytes     = Base64.decode(Str, Base64.DEFAULT);
        //byte[] encodedPbkey     = Base64.decode(pbkey, Base64.DEFAULT);
       // byte[] encodedKey     = Base64.decode(pbkey, Base64.DEFAULT);
       // SecretKey originalKey = new SecretKeySpec(encodedKey, "RSA");

        byte[] keyBytes = Base64.



        byte[] decodedBytes = null;

        try {
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, originalKey);
            decodedBytes = c.doFinal(encodedBytes);//encodedTextBytes);//encodedBytes); //
            final byte[] finalDecodedBytes = decodedBytes;
            //decodedStr = finalDecodedBytes.toString(); //finalDecodedBytes.toString(); //Base64.encodeToString(finalDecodedBytes, Base64.DEFAULT);
            String decodedStr = new String(finalDecodedBytes);
            finalDecodedStr = decodedStr;
            System.out.println(finalDecodedStr);

        } catch (Exception e) {
            Log.e("Crypto", "RSA decryption error");
            e.printStackTrace();
        }



        return finalDecodedStr;
    }

    */

    /*
    private byte[] sign(byte[] bytes, String privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA", "SC");
        signature.initSign(Crypto.getRSAPrivateKeyFromString(privateKey));
        signature.update(bytes);
        return signature.sign();
    }


    private boolean verify(byte[] bytes, String privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA", "SC");
        signature.initSign(Crypto.getRSAPrivateKeyFromString(privateKey));
        return signature.verify(bytes);
    }
    */




}