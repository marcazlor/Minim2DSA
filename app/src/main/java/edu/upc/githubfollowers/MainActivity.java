package edu.upc.githubfollowers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static Retrofit retrofit;
    UserService userservice;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startRetrofit();
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
         ProgressBar spinner;
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

    }
    private static void startRetrofit(){
        //HTTP &
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //Attaching Interceptor to a client
        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(interceptor).build();

        // Running Retrofit to get result from Local tracks service Interface
        //Remember when using Local host on windows the IP is 10.0.2.2 for Android
        //Also added NullOnEmptyConverterFactory when the response from server is empty
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/users/")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
   public void onFindUserClick(View view){
       EditText user= findViewById(R.id.userinput);
       ProgressBar spinner;
       spinner = (ProgressBar)findViewById(R.id.progressBar1);
       spinner.setVisibility(View.VISIBLE);
       if(user.getText().toString().equals(null))
           Toast.makeText(getApplicationContext(), "Error.Campos vacíos.", Toast.LENGTH_LONG).show();
       else {

           userservice = retrofit.create(UserService.class);
           Call<User> us = userservice.getUser(user.getText().toString());
           us.enqueue(new Callback<User>() {
               @Override
               public void onResponse(Call<User> call, Response<User> response) {
                   ProgressBar spinner;
                   spinner = (ProgressBar)findViewById(R.id.progressBar1);
                   spinner.setVisibility(View.GONE);
                   TextView nombre=findViewById(R.id.nombre);
                   TextView following=findViewById(R.id.Followingtxt);
                   ImageView image= findViewById(R.id.image_view);
                   TextView repos=findViewById(R.id.repostxt);
                   User use;
                   use=response.body();
                   if(response.code()==404)
                   {
                       Intent in =new Intent(MainActivity.this, errores.class);
                       in.putExtra("user",user.getText().toString());
                       startActivity(in);
                   }
                   nombre.setText(use.getLogin());
                   System.out.println(use.getPublicRepos());
                   System.out.println(use.getFollowing());
                   repos.setText(String.valueOf(use.getPublicRepos()));
                   following.setText(String.valueOf(use.getFollowing()));
                   Picasso.with(getApplicationContext()).load(use.getAvatar()).into(image);
               }

               @Override
               public void onFailure(Call<User> call, Throwable t) {
                   Toast.makeText(getApplicationContext(), "Error.", Toast.LENGTH_LONG).show();


               }

           });
           Call<List<Followers>> fol=userservice.getFollowers(user.getText().toString());
           fol.enqueue(new Callback<List<Followers>>() {
               @Override
               public void onResponse(Call<List<Followers>> call, Response<List<Followers>> response) {
                   List<Followers> fol = response.body();
                   mAdapter=new MyAdapter(fol);
                   recyclerView.setAdapter(mAdapter);

               }

               @Override
               public void onFailure(Call<List<Followers>> call, Throwable t) {
                   Toast.makeText(getApplicationContext(), "Error.", Toast.LENGTH_LONG).show();
               }
           });

       }


   }

}
