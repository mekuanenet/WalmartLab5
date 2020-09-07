package com.miu.lab5walmartapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var users= ArrayList<User>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        users.add(User("Meku","Anteneh","manteneh@gmail.com","1234"))
        users.add(User("Bob","Smith","bsmith@gmail.com","1234"))
        users.add(User("Maria","Petter","mpetter@gmail.com","1234"))
        users.add(User("Denny","Gilbert","dgilbert@email.com","1234"))
        users.add(User("Mo","Farah","mfarah@gmail.com","1234"))
        users.add(User("Robert","Leva","rleva@gmail.com","1234"))


        val newUser = intent.getSerializableExtra("newUser") as? User
        if (newUser != null) {
            users.add(newUser)
        }

    }
    fun signingIn(view: View) {
        var found:Boolean= false;
        val intent = Intent(this, ShoppingActivity::class.java)
        var input = userName.text.toString()

        for(user in users){
            if(user.username == userName.text.toString() && user.password == passWord.text.toString())
                found=true;
        }
        if(found) {
            intent.putExtra("message",input )
            Toast.makeText(this, "You Signed in Successfully", 4*Toast.LENGTH_LONG).show()
            startActivity(intent)
        }else{
            Toast.makeText(this, "Please check your email or password, if you forget your password click on Forgot Password? above", Toast.LENGTH_LONG).show()
        }
    }

    fun createAccount(view: View) {
        val intent= Intent(this,CreateAccountActivity::class.java)
        startActivity(intent)
    }
    private fun composeEmail(addresses: Array<String>, subject: String,message:String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
            //setType("message/rfc822")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
    fun composeEmail(view: View) {
        val address=Array(1){userName.text.toString()}
        var found=false
        lateinit var pw: String
        for(user in users){
            if(user.username == userName.text.toString()) {
                pw=user.password
                found=true
                break
            }
        }
        var message:String
        message = if (!found) {
            "You don't have account with this email."
        } else "your password is $pw without single quotes."
        composeEmail(address,"Your forgotten password",message)
    }

//Just to show Forgot password? text view is clickable
//    fun forgotPassword(view: View) {
//        //txt_forgotpassword.setText("I'm clicked")
//        Toast.makeText(this, "I'm Clicked",Toast.LENGTH_LONG).show()
//    }
}