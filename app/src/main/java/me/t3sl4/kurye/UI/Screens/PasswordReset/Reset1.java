package me.t3sl4.kurye.UI.Screens.PasswordReset;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import me.t3sl4.kurye.R;
import me.t3sl4.kurye.UI.Components.EditText.EditTextUtil;
import me.t3sl4.kurye.UI.Components.NavigationBar.NavigationBarUtil;

public class Reset1 extends AppCompatActivity {
    private EditText mailField;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_reset_1);

        NavigationBarUtil.hideNavigationBar(this);

        mailField = findViewById(R.id.editTextMailField);
        nextButton = findViewById(R.id.nextButton);

        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(Reset1.this, Reset2.class);
            if(EditTextUtil.checkNull(mailField, null)) {
                if(EditTextUtil.parseEditText(mailField).equals("username")) {
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
