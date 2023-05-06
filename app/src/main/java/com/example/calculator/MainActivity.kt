package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    // variable TVInput is of typt textview and has been set null initially
    private var TVInput: TextView? = null

    var lastNumeric : Boolean = false
    //this will check whether all previous were numeric or not

    var lastDot : Boolean = false
    //this will check whether there is any dot previously or not

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TVInput = findViewById(R.id.TVInput)
    }

   fun onDigit(view:View)// in this case we can use the view to print actual number from button to the screen
   //by using view as a button as text .
   {
   TVInput?.append((view as Button).text) // print actual number that is written on the button display on text area.
       //we can't just say view.text because view doesn't contain any text but the button contain text so we use view as button from there we get the text.
     // when view is a button than use it as text

       lastNumeric = true
   }

    //if we don't use ondigit function than we have to write a same stuff of code for ah and every button individually
    // private var btn1 : button? = null
    //than in override function
    //we need to do      btn1 = findViewById(R.Id.button1)
    // btn1?.setOnClickListener {TVInput?.append("1)}

    //on click case is not good in all case but in some case it is much useful than other
    //so onclick shouldn't used regularly but we can use when required i.e. when it save our time


// this section will work for clear button for our calculator
    fun onClear(view:View)
    {
        TVInput?.text = ""
    }


    // this section will stop using multiple . in a number without adding any operator
    fun onDecimalPoint(view:View)
    {//this function never work as we haven't set lastnumeric to be true before
        // so in onDigit function we set lastNumeric to be true.

        if(lastNumeric && !lastDot){
            TVInput?.append(".")
            lastNumeric = false
            lastDot = true//this will set lastDot variable to be true so that duplicacy of dot can be prevented.

            //var sentence = "hi i am rudy"
            // if(sentence.contain("rudy"))
            //{
            //TVInput?.append("rudy")}//this stuff of code checks wether sentence contain rudy if yes than return the rudy to the text area.
        }
    }

    // this section of code will check wether there is an operator added to text view before or
    //not if yes than no operator will be added furthur expect there is an - symbol in start of the string
    //since text to string is a nullable so we need to use let operator to save code from error
    fun onOperator(view:View){
        TVInput?.text?.let{
            if(lastNumeric && !isoperandadded(it.toString()))
            {
                TVInput?.append((view as Button).text)

                lastNumeric = false
                lastDot = false
            }
        }
    }

    private fun isoperandadded(value : String) : Boolean{
        return if(value.startsWith("-")){
            false
        }
        else{
            value.contains("/")||
                    value.contains("+") ||
                    value.contains("-")||
                    value.contains("*")
        }
    }
//since this method breaks the string from - sign so if - occur in start there will only one string at a time that crash the app.
    //for that we will check if string start with - sign than wee will use substring property to use only digit not sign

    fun onequal(view:View){
        if(lastNumeric){
            var tvvalue = TVInput?.text.toString()
     var prefix = ""

            try{

                if(tvvalue.startsWith("-")){
                    prefix = "-"
                    tvvalue = tvvalue.substring(1)

                    //i.e if wehave -99 at atart than it will show 99 only not -99
                }

                if(tvvalue.contains("-")){
                    val splitvalue = tvvalue.split("-")// delimiter tells where to break the string
                    var one = splitvalue[0]
                    var two = splitvalue[1]

                    //assign prefix to string1 again

                    if(prefix.isNotEmpty()){//check if prefix is not empty than follow this way to get the solution else just do as usual
                        one = prefix+one
                    }

                    //we do so because text can be showed in string format only

                    TVInput?.text = removezeroafterdot((one.toDouble() -two.toDouble()). toString())

                    //instead using the above we can use
                //var result =one.toDouble() - two.toDouble()
                    //TVInput?.text = result. toString()


                }

              else if(tvvalue.contains("+")){
                    val splitvalue = tvvalue.split("+")
                    var one = splitvalue[0]
                    var two = splitvalue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix+one
                    }

                    TVInput?.text = removezeroafterdot((one.toDouble() +two.toDouble()). toString())

                }
                else if(tvvalue.contains("/")){
                    val splitvalue = tvvalue.split("/")
                    var one = splitvalue[0]
                    var two = splitvalue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix+one
                    }

                    TVInput?.text = removezeroafterdot((one.toDouble() /two.toDouble()). toString())
                }
                else if(tvvalue.contains("*")){
                    val splitvalue = tvvalue.split("*")
                    var one = splitvalue[0]
                    var two = splitvalue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix+one
                    }

                    TVInput?.text = removezeroafterdot((one.toDouble() *two.toDouble()). toString())
                }


            }
//catch()will take care of calculation that doesnot take place
            catch(e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }


    //the function below will remove the o after  dot if there is no other value after dot except 0.
    private fun removezeroafterdot(result:String):String{
        var value = result
        if(result.contains(".0")){
            value =result.substring(0, result.length-2)
        }
        return value
    }

}