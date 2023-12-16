package me.t3sl4.gelkurye.Screens.PasswordReset;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Util.Component.EditText.EditTextManager;

public class Reset1 extends AppCompatActivity {
    private EditText mailField;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset_1);

        mailField = findViewById(R.id.editTextMailField);
        nextButton = findViewById(R.id.nextButton);

        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(Reset1.this, Reset2.class);
            if(EditTextManager.checkNull(mailField, null)) {
                if(EditTextManager.parseEditText(mailField).equals("username")) {
                    //kullanıcı adına göre otp kodu gönderme
                } else {
                    //e-posta adresine göre otp kodu gönderme
                }
                intent.putExtra("userVerifyMethod", mailField.getText());
                startActivity(intent);
                finish();
            } else {
                //mail ya da kullanıcı adı boş olamaz hata mesajı
            }
        });
    }
}
