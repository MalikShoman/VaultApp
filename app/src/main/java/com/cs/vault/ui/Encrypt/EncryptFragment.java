package com.cs.vault.ui.Encrypt;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.cs.vault.R;
import com.google.android.material.snackbar.Snackbar;


import static com.cs.vault.Enc.cipher;
import static com.cs.vault.Enc.generateKey;
import static com.cs.vault.Enc.keyword;
import static com.cs.vault.Enc.origin;
import static com.cs.vault.Enc.str;

public class EncryptFragment extends Fragment {

    private EncryptViewModel encryptViewModel;
    private EditText original;
    private TextView ciphered;
    private Button enc;
    private Button copy;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        encryptViewModel =
                new ViewModelProvider(this).get(EncryptViewModel.class);
        View root = inflater.inflate(R.layout.fragment_encrypt, container, false);

        original = root.findViewById(R.id.editDecPass);
        ciphered = root.findViewById(R.id.editTextPass);
        enc= root.findViewById(R.id.btn_enc);
        copy= root.findViewById(R.id.btn_copy);

        enc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String origin = original.getText().toString().toUpperCase();

                str = "" + origin;

                String key = generateKey(str, keyword);
                String cipher_text = cipher(str, key);
                ciphered.setText(cipher_text.toLowerCase());

                String original_text = origin(cipher_text, key);

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(root.getWindowToken(), 0);

                Snackbar.make(root, "Your Password Encrypted Successfully", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", ciphered.getText());
                clipboard.setPrimaryClip(clip);

                Snackbar.make(root, "Your Password Copied Successfully", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        ciphered.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", ciphered.getText());
                clipboard.setPrimaryClip(clip);

                Snackbar.make(root, "Your Password Copied Successfully", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                return true;
            }
        });

        encryptViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        return root;
    }
}