package com.example.myfirstapp;

import android.Manifest;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class FingerprintPopupFragment extends DialogFragment {


    private static final String KEY_NAME = "HomeSecurity";
    private KeyStore mKeyStore;
    private Cipher mCipher;
    private TextView mPopupTextView;
    private FingerprintManager mFingerprintManager;
    private KeyguardManager mKeyguardManager;
    private Intent mIntent;


    public FingerprintPopupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fingerprint_popup, container, false);

        Button dismissButton = (Button) view.findViewById(R.id.dismiss);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                getTargetFragment().onActivityResult(getTargetRequestCode(), 0, mIntent);

            }
        });

        getDialog().setTitle("Fingerprint Authentication");

        mKeyguardManager = (KeyguardManager) getActivity().getSystemService(KEYGUARD_SERVICE);
        mFingerprintManager = (FingerprintManager) getActivity().getSystemService(FINGERPRINT_SERVICE);
        mPopupTextView = (TextView) view.findViewById(R.id.popup_textview);

        if(!mFingerprintManager.isHardwareDetected()){
            mPopupTextView.setText(R.string.no_sensor);
        }else {
            // Checks whether fingerprint permission is set on manifest
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                mPopupTextView.setText(R.string.no_permission);
            }else{
                // Check whether at least one fingerprint is registered
                if (!mFingerprintManager.hasEnrolledFingerprints()) {
                    mPopupTextView.setText(R.string.no_fingerprints);
                }else{
                    // Checks whether lock screen security is enabled or not
                    if (!mKeyguardManager.isKeyguardSecure()) {
                        mPopupTextView.setText(R.string.security_disabled);
                    }else{
                        generateKey();
                        if (cipherInit()) {
                            FingerprintManager.CryptoObject mCryptoObject = new FingerprintManager.CryptoObject(mCipher);
                            FingerprintHandler mFingerprintHandler = new FingerprintHandler(getActivity(), mPopupTextView);
                            mFingerprintHandler.startAuth(mFingerprintManager, mCryptoObject);
                        }
                    }
                }
            }
        }

        return view;
    }


    protected void generateKey() {
        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }

        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }

        try {
            mKeyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean cipherInit() {
        try {
            mCipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            mKeyStore.load(null);
            SecretKey mKey = (SecretKey) mKeyStore.getKey(KEY_NAME, null);
            mCipher.init(Cipher.ENCRYPT_MODE, mKey);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }


    class FingerprintHandler extends FingerprintManager.AuthenticationCallback {


        private Activity mActivity;
        private TextView mPopup;


        public FingerprintHandler(Activity activity, TextView popup) {
            this.mActivity = activity;
            this.mPopup = popup;
        }


        public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
            CancellationSignal cancellationSignal = new CancellationSignal();
            if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
        }


        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            this.update("Fingerprint Authentication error\n" + errString, false);
        }


        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            this.update("Fingerprint Authentication help\n" + helpString, false);
        }


        @Override
        public void onAuthenticationFailed() {
            this.update("Fingerprint Authentication failed.", false);
        }


        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            this.update("Fingerprint Authentication succeeded.", true);
        }


        public void update(String e, Boolean success){
            mPopup.setText(e);
            if(success) {
                mPopup.setTextColor(ContextCompat.getColor(mActivity, R.color.colorGreen));
            }
            else mPopup.setTextColor(ContextCompat.getColor(mActivity, R.color.colorRed));
            mIntent = new Intent();
            mIntent.putExtra("authenticationResult", success);

        }


    }
}
