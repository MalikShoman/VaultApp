package com.cs.vault.ui.Decrypt;

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

import static com.cs.vault.Enc.generateKey;
import static com.cs.vault.Enc.keyword;
import static com.cs.vault.Enc.origin;
import static com.cs.vault.Enc.str;

public class DecryptFragment extends Fragment {

    private DecryptViewModel decryptViewModel;
    private EditText original;
    private TextView ciphered;
    private Button dec;
    private Button copy;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        decryptViewModel =
                new ViewModelProvider(this).get(DecryptViewModel.class);
        View root = inflater.inflate(R.layout.fragment_decrypt, container, false);

        ciphered = root.findViewById(R.id.DecPass);
        original = root.findViewById(R.id.TextPass);
        dec = root.findViewById(R.id.btn_dec);
        copy= root.findViewById(R.id.btn__copy);

        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cipher = ciphered.getText().toString().toUpperCase();

                str = "" + cipher;

                String key = generateKey(str, keyword);
                String origin_text = origin(str, key);
                original.setText(origin_text.toLowerCase());

                String original_text = origin(origin_text, key);

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(root.getWindowToken(), 0);

                Snackbar.make(root, "Your Password Decrypted Successfully", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", original.getText());
                clipboard.setPrimaryClip(clip);

                Snackbar.make(root, "Your Password Copied Successfully", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        original.setOnLongClickListener(new View.OnLongClickListener() {
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

        decryptViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

//                textView.setText(s);
            }
        });
        return root;
    }
}