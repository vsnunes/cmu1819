package pt.ulisboa.tecnico.meic.cmu.p2photo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pt.ulisboa.tecnico.meic.cmu.p2photo.api.P2PhotoException;
import pt.ulisboa.tecnico.meic.cmu.p2photo.api.ServerConnector;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    private EditText user;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void signIn(View view) {
        intent = new Intent(this, ActionsMenu.class);
        if(checkArguments()){
            /*TODO server request*/
            startActivityForResult(intent, 1);
        }
    }

    public void signUp(View view) {
        intent = new Intent(this, chooseCloudLocalActivity.class);
        if(checkArguments()){
            /*try {
                ServerConnector sv = new ServerConnector();
            } catch (P2PhotoException e) {
                e.printStackTrace();
            }*/
            startActivityForResult(intent, 2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            /*Sign In*/
            case 1:
                /*Sign Up*/
            case 2:
                if(resultCode==RESULT_OK){
                    Toast.makeText(getApplicationContext(), "User "
                                    + data.getStringExtra("name") + " logged out",
                            Toast.LENGTH_LONG).show();
                }
                else if(resultCode==RESULT_CANCELED){
                    Toast.makeText(getApplicationContext(), "User " + user.getText().toString() + " logged out abruptly",
                            Toast.LENGTH_LONG).show();
                }
                user.setText("");
                pass.setText("");
                break;
        }
    }

    public boolean checkArguments(){
        user = (EditText) findViewById(R.id.textUser);
        pass = (EditText) findViewById(R.id.textPass);
        intent.putExtra("name", user.getText().toString());
        intent.putExtra("pass", pass.getText().toString());

        setResult(RESULT_OK, intent);

        if((user.getText().toString().matches("")) || pass.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "Name or password invalid",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
